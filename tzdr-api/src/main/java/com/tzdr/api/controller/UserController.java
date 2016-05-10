package com.tzdr.api.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jodd.util.ObjectUtil;
import jodd.util.StringUtil;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.tzdr.api.constants.DataConstant;
import com.tzdr.api.constants.ResultStatusConstant;
import com.tzdr.api.request.RequestObj;
import com.tzdr.api.support.ApiResult;
import com.tzdr.api.thread.RegistP2pThread;
import com.tzdr.api.util.PasswordUtils;
import com.tzdr.api.util.RequestUtils;
import com.tzdr.business.api.service.ApiTradeService;
import com.tzdr.business.api.service.ApiUserService;
import com.tzdr.business.service.feededuction.FeeDuductionService;
import com.tzdr.business.service.securitycode.SecurityCodeService;
import com.tzdr.business.service.userTrade.UserTradeService;
import com.tzdr.business.service.wuser.WUserService;
import com.tzdr.common.utils.BigDecimalUtils;
import com.tzdr.common.utils.Dates;
import com.tzdr.common.utils.IpAddressUtils;
import com.tzdr.common.utils.IpUtils;
import com.tzdr.domain.api.entity.ApiToken;
import com.tzdr.domain.api.vo.ApiUserVo;
import com.tzdr.domain.api.vo.UserInfoVo;
import com.tzdr.domain.vo.UserTradeCmsVo;
import com.tzdr.domain.vo.UserTradeVo;
import com.tzdr.domain.web.entity.SecurityCode;
import com.tzdr.domain.web.entity.WUser;

/**
 * <B>说明: </B>用户操作相关接口
 * @zhouchen
 * 2016年1月20日
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {

	@Autowired
	private ApiUserService  apiUserService;
	
	@Autowired
	private SecurityCodeService securityCodeService;
	
	@Autowired
	private WUserService   wUserService;
	
	@Autowired
	private UserTradeService userTradeService;
	
	@Autowired
	private ApiTradeService apiTradeService;
	
	@Autowired
	private FeeDuductionService feeDuductionService;

	private static Object lock = new Object();
	
	/**
	 * 系统注册接口
	 * @param requestObj
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/regist",method=RequestMethod.POST)
	@ResponseBody
	public ApiResult signInOperation(@RequestBody RequestObj requestObj,HttpServletRequest request,HttpServletResponse response){
		
		String mobile=requestObj.getMobile();
		String code=requestObj.getCode();
		String password=requestObj.getPassword();
		String parentGeneralizeId=requestObj.getParentGeneralizeId();
	    String channel = requestObj.getChannel();
		if (StringUtil.isBlank(mobile)
				|| StringUtil.isBlank(code)
				|| StringUtil.isBlank(password)){
			return new ApiResult(false,ResultStatusConstant.FAIL,"user.info.not.complete.");
		}
		
		if (!PasswordUtils.validatePwd(password)){
			return new ApiResult(false,ResultStatusConstant.Regist.PASSWORD_PATTERN_ERROR,"password.pattern.error.");
		}
		
		if (!RequestUtils.isMobileNum(mobile)){
			return new ApiResult(false,ResultStatusConstant.FAIL,"mobile.parrten.error.");
		}
		
		ApiUserVo appUserVo = apiUserService.findByMobile(mobile);
		if (!ObjectUtil.equals(null, appUserVo)){
			return new ApiResult(false,ResultStatusConstant.Regist.MOBILE_EXIST,"mobile.exist.");
		}
		
		SecurityCode securityCode = securityCodeService.getSecurityCodeByMobile(mobile);  //获取验证码信息
		if (ObjectUtil.equals(null,securityCode) 
				|| !StringUtil.equals(code, securityCode.getSecurityCode())){
			return new ApiResult(false,ResultStatusConstant.Regist.ERROR_CODE,"error.code.");
		}
		
		if((Dates.getCurrentLongDate()-securityCode.getCreatedate()) > DataConstant.VALIDATE_CODE_INVALID_TIME){ 
			//判断验证码是否失效
			return new ApiResult(false,ResultStatusConstant.Regist.INVALID_CODE,"invalid.code.");
		}
		
		if(StringUtil.isNotBlank(parentGeneralizeId) 
				&& ObjectUtil.equals(null, wUserService.findByGeneralizeId(parentGeneralizeId))){
			return new ApiResult(false,ResultStatusConstant.Regist.ERROR_GENERALIZE_CODE,"error.generalize.code.");
		}
		
		ApiToken  apiToken = (ApiToken) request.getAttribute(DataConstant.API_TOKEN);
		WUser wUser = new WUser();     //创建注册对象信息
		wUser.setSource(RequestUtils.getSource(apiToken));
		WUser platformDefaultWuser = wUserService.queryByUserType(DataConstant.TZDR_DEFAULT_USERTYPE).get(0);  //获取平台默认用户
		wUser.setUserType("0");
		wUser.setParentNode(platformDefaultWuser);
		wUser.setPassword(password);
		wUser.setMobile(mobile);
		wUser.setCtime((new Date().getTime()/1000));
		wUser.setRegIp(IpUtils.getIpAddr(request));
		//设置渠道
		wUser.setChannel(channel);  
		//推广人编号
		if(StringUtil.isNotBlank(parentGeneralizeId)){
			WUser generalizeWuser = wUserService.findByGeneralizeId(parentGeneralizeId); 
			if(generalizeWuser != null){
				wUser.setRebate(generalizeWuser.getSubordinateDefaultRebate() == null? 0.00:generalizeWuser.getSubordinateDefaultRebate());
				wUser.setParentNode(generalizeWuser);
			}
		}
		wUser.setLastLoginTime(Dates.getCurrentLongDate());
		String ip = IpUtils.getIpAddr(request);
		wUser.setLastLoginIp(ip);
		wUser.setRegCity(IpAddressUtils.getAffiliationCity(ip, "utf-8"));
		synchronized (lock) {
			wUserService.saveWUser(wUser);
		}
		//p2p 同步注册
		new RegistP2pThread(mobile,password,wUser.getLoginSalt()).start();
		
		return new ApiResult(true,ResultStatusConstant.SUCCESS,"regist.success.");
	}
	
	
	/**
	 * 系统登录接口
	 * @param requestObj
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/login",method=RequestMethod.POST)
	@ResponseBody 
	public ApiResult login(@RequestBody RequestObj requestObj,
			HttpServletRequest request,HttpServletResponse response){
		
		String loginName=requestObj.getLoginName();
		String password=requestObj.getPassword();
		
		if (StringUtil.isBlank(loginName)
				|| StringUtil.isBlank(password)){
			return new ApiResult(false,ResultStatusConstant.Login.LOGIN_INFO_ERROR,"user.info.not.complete.");
		}
		if (!PasswordUtils.validatePwd(password)){
			return new ApiResult(false,ResultStatusConstant.Login.PASSWORD_PATTERN_ERROR,"password.pattern.error.");
		}
		WUser wUser = wUserService.login(loginName, password); //登录
		if(ObjectUtil.equals(null, wUser)){   //判断是否登录成功
			return new ApiResult(false,ResultStatusConstant.FAIL,"login.fail.");
		}
		wUserService.updateWUserByUserId(wUser.getId(), IpUtils.getIpAddr(request));   //记录登录信息
		JSONObject  jsonObject = new JSONObject();
		jsonObject.put("uid",wUser.getId());
		return new ApiResult(true,ResultStatusConstant.SUCCESS,"login.success.",jsonObject);
	} 
	
	
	/**
	 * 获取账户信息
	 * @param uid
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/info",method=RequestMethod.POST)
	@ResponseBody 
	public ApiResult info(@RequestBody RequestObj requestObj,HttpServletRequest request,HttpServletResponse response){
		
		String uid=requestObj.getUid();
		
		if (StringUtil.isBlank(uid)){
			return new ApiResult(false,ResultStatusConstant.FAIL,"uid.is.null.");
		}
		
		UserInfoVo infoVo = apiUserService.queryUserInfo(uid);
		if (ObjectUtil.equals(null, infoVo)){
			return new ApiResult(false,ResultStatusConstant.UserInfo.USER_INFO_NOT_EXIST,"user.info.not.exist.");
		}
		
		//获取用户进行中的配资列表信息
		Double totalLending = 0.00;     //操盘中总配资金额
		Double totalLeverMoney = 0.00;  //操盘中总风险保证金
		List<UserTradeVo> userTradeVoList = userTradeService.queryUserTradeVoByWuserAndStatus(uid, (short)1, (short)0);
		if(!CollectionUtils.isEmpty(userTradeVoList)){
			for (UserTradeVo userTradeVo : userTradeVoList) {
					totalLending = BigDecimalUtils.add2(totalLending, userTradeVo.getTotalMoney());
					totalLeverMoney = BigDecimalUtils.add2(totalLeverMoney, userTradeVo.getTotalLeverMoney());
					totalLeverMoney = BigDecimalUtils.add2(totalLeverMoney, userTradeVo.getTotalAppendLeverMoney());
			}
		}
				
		//帐号总资产
		infoVo.setTotalFund(BigDecimalUtils.round2(BigDecimalUtils.add2(BigDecimalUtils.add2(infoVo.getFreezeFund(),infoVo.getBalance()),totalLeverMoney),2));  
		 //操盘中总配资金额
		infoVo.setTradeFund(BigDecimalUtils.round1(totalLending,2));    		
		//操盘中总风险保证金
		infoVo.setCashFund(BigDecimalUtils.round1(totalLeverMoney,2));    
		
		//获取补仓方案个数
		infoVo.setMarginCallNum(apiTradeService.findUserMarginTrades(uid).size());
		//校验是否够扣第二天费用
		infoVo.setEnough(isEnough(uid,infoVo.getBalance()));
		
		return new ApiResult(true,ResultStatusConstant.SUCCESS,"query.success.",infoVo);
	} 
	
	
	/**
	 * 校验是否足够次日扣费
	 * @param uid
	 * @return
	 */
	private boolean isEnough(String uid,double balance){
		UserTradeCmsVo userTradeCmsVo = apiTradeService.findUserTradeIds(uid);
		if (ObjectUtil.equals(null, userTradeCmsVo)){
			return true;
		}
		
		String userTradeIds = userTradeCmsVo.getUserTradeId();
		String [] tempTradeIds = userTradeIds.split(DataConstant.SPLIT_SIGN);
		if (ArrayUtils.isEmpty(tempTradeIds)){
			return true;
		}
		// 如果余额小于 所有配资方案费用
		double  totalDeductionMoney = feeDuductionService.getTotalDeductionMoney(tempTradeIds);
		if (balance >= totalDeductionMoney){
			return true;
		}	
		return false;
	}
}
