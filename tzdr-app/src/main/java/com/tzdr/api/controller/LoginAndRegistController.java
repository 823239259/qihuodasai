package com.tzdr.api.controller;

import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jodd.util.ObjectUtil;
import jodd.util.StringUtil;
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
import com.tzdr.api.support.CacheUser;
import com.tzdr.api.util.AuthUtils;
import com.tzdr.api.util.PasswordUtils;
import com.tzdr.api.util.RequestUtils;
import com.tzdr.business.api.service.ApiTradeService;
import com.tzdr.business.api.service.ApiUserService;
import com.tzdr.business.cms.cpp.MockTradeAccountService;
import com.tzdr.business.cms.service.messagePrompt.MessagePromptService;
import com.tzdr.business.service.feededuction.FeeDuductionService;
import com.tzdr.business.service.future.FSimpleCouponService;
import com.tzdr.business.service.generalize.GeneralizeChannelService;
import com.tzdr.business.service.securitycode.SecurityCodeService;
import com.tzdr.business.service.userTrade.FSimpleFtseUserTradeService;
import com.tzdr.business.service.userTrade.UserTradeService;
import com.tzdr.business.service.wuser.LoginLogService;
import com.tzdr.business.service.wuser.WUserService;
import com.tzdr.common.api.ihuyi.SMSSender;
import com.tzdr.common.utils.Dates;
import com.tzdr.common.utils.IpAddressUtils;
import com.tzdr.common.utils.IpUtils;
import com.tzdr.common.utils.MessageUtils;
import com.tzdr.domain.api.vo.ApiUserVo;
import com.tzdr.domain.constants.Constant;
import com.tzdr.domain.web.entity.FSimpleFtseUserTrade;
import com.tzdr.domain.web.entity.GeneralizeChannel;
import com.tzdr.domain.web.entity.WUser;
import com.tzdr.domain.web.entity.future.FSimpleCoupon;

/**
 * <B>说明: </B>用户操作相关接口
 * @zhouchen
 * 2016年1月20日
 */
@Controller
@RequestMapping(value = "/")
public class LoginAndRegistController {

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
	
	@Autowired
	private FSimpleCouponService fSimpleCouponService;
	@Autowired
	private GeneralizeChannelService channelService;
	
	@Autowired
	private FSimpleFtseUserTradeService fSimpleFtseUserTradeService;
	@Autowired
	private MessagePromptService messagePromptService;
	
	@Autowired
	private MockTradeAccountService mockTradeAccountService;
	@Autowired
	private LoginLogService loginLogService;
	
	private static Object lock = new Object();
	
	/**
	 * 系统注册接口
	 * @param requestObj 用户注册接口请求类
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/regist",method=RequestMethod.POST)
	@ResponseBody
	public ApiResult signInOperation(@RequestBody RequestObj requestObj,HttpServletRequest request,HttpServletResponse response){
		final String mobile=requestObj.getMobile();//电话
		final String password=requestObj.getPassword();//密码
		String parentGeneralizeId=requestObj.getParentGeneralizeId();//推广码
	    String channel = requestObj.getChannel();//渠道
	    String source = requestObj.getSource();//来源    
		if (StringUtil.isBlank(mobile)
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
		if (!ObjectUtil.equals(null, appUserVo)){//验证手机号码是否已被注册
			return new ApiResult(false,ResultStatusConstant.Regist.MOBILE_EXIST,"mobile.exist.");
		}
		WUser wUser = new WUser();     //创建注册对象信息
		
		if(StringUtil.isNotBlank(parentGeneralizeId) 
				&& ObjectUtil.equals(null, wUserService.findByGeneralizeId(parentGeneralizeId))){
			return new ApiResult(false,ResultStatusConstant.Regist.ERROR_GENERALIZE_CODE,"error.generalize.code.");
		}
		//推广人编号
		if(StringUtil.isNotBlank(parentGeneralizeId)){
			WUser generalizeWuser = wUserService.findByGeneralizeId(parentGeneralizeId);
			if(generalizeWuser == null){
				return new ApiResult(false,ResultStatusConstant.Regist.ERROR_GENERALIZE_CODE,"error.generalize.code.");
			}else{
				//wUser.setRebate(generalizeWuser.getSubordinateDefaultRebate() == null? 0.00:generalizeWuser.getSubordinateDefaultRebate());
				wUser.setParentNode(generalizeWuser);
			}
		}else{
			WUser platformDefaultWuser = wUserService.queryByUserType(DataConstant.TZDR_DEFAULT_USERTYPE).get(0);  //获取平台默认用户
			wUser.setParentNode(platformDefaultWuser);
		}
		
		wUser.setSource(Integer.parseInt(source));
		wUser.setUserType("0");
		wUser.setPassword(password);
		wUser.setMobile(mobile);
		wUser.setCtime((new Date().getTime()/1000));
		wUser.setRegIp(IpUtils.getIpAddr(request));
		GeneralizeChannel generalizeChannel = getChannel(channel);
		String channelName = "";
		String channelKeyWords = "";
		if (generalizeChannel != null) {
			channelName = generalizeChannel.getTypeThreeTitle();
			if (channelName == null || channelName.length() <= 0) {
				channelName = generalizeChannel.getTypeTwoTitle();
				if (channelName == null || channelName.length() <= 0) {
					channelName = generalizeChannel.getTypeOneTitle();
				}
			}
			channelKeyWords = generalizeChannel.getUrlKey();
			wUser.setChannel(channelName); // 设置渠道
			wUser.setKeyword(channelKeyWords);// 设置关键字
		}else{
			wUser.setChannel(channel);
		}
		final String emailChannelName = channelName;
		final String emailChannelKeyWords = channelKeyWords;
		
		wUser.setLastLoginTime(Dates.getCurrentLongDate());
		String ip = IpUtils.getIpAddr(request);
		wUser.setLastLoginIp(ip);
		//wUser.setRegCity(IpAddressUtils.getAffiliationCity(ip, "utf-8"));
		try {
			synchronized (lock) {
				wUserService.saveWUser(wUser);
				//活动发放注册券
				String couponName=MessageUtils.message("regist.app.coupon");
				List<FSimpleCoupon> coupons=this.fSimpleCouponService.findByStatusAndName((short)1, couponName);
				//发放
				if(checkRegistCoupon(coupons)){
					FSimpleCoupon f=coupons.get(0);
					f.setUserId(wUser.getId());
					f.setUserPhone(mobile);
					f.setStatus((short)2);
					f.setGrantTime(new Date().getTime()/1000);
					if(f.getCycle()!=null && f.getCycle()!=0){
						Long grantTime = f.getGrantTime();
						Long deadLine = Dates.toDate(Dates.dateAddDay(Dates.parseLong2Date(grantTime),f.getCycle())).getTime() / 1000;
						f.setDeadline(deadLine);
					}
					fSimpleCouponService.update(f);
				}
			}
		} catch (Exception e) {
		}
		//p2p 同步注册
		//new RegistP2pThread(mobile,password,wUser.getLoginSalt()).start();
		JSONObject  jsonObject = new JSONObject();
		//登录成功 返回用户唯一标志token和对应由密码种子+uid生成的key值
		String appToken = AuthUtils.createToken(wUser.getId());
		jsonObject.put(DataConstant.APP_TOKEN,appToken);
		String secretKey = AuthUtils.createSecretKey(wUser);
		jsonObject.put(DataConstant.SECRET_KEY,secretKey);
		// 缓存用户信息
		DataConstant.CACHE_USER_MAP.put(appToken,new CacheUser(wUser,secretKey));
		// 用户注册成功之后给用户手机发送短信
		try {
			SMSSender.getInstance().sendByTemplate(1, mobile, "ihuyi.verification.signin.success.template", null);
			/*mockTradeAccountService.openMockAccount(mobile, password);*/
			messagePromptService.registNotice(mobile, source, emailChannelName, emailChannelKeyWords);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ApiResult(true,ResultStatusConstant.SUCCESS,"regist.success.",jsonObject);
	}
	
	public GeneralizeChannel getChannel(String params) {
		List<GeneralizeChannel> generaList = channelService.findByParamAndDeletedFalse(params);
		GeneralizeChannel channel2 = null;
		if (generaList.size() > 0) {
			channel2 = generaList.get(0);
		}
		return channel2;
	}
	/**
	 * 系统登录接口
	 * @param requestObj
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "login",method=RequestMethod.POST)
	@ResponseBody 
	public ApiResult login(@RequestBody RequestObj requestObj,
			HttpServletRequest request,HttpServletResponse response){
		
		String loginName=requestObj.getLoginName();
		String password=requestObj.getPassword();
		
		if (StringUtil.isBlank(loginName)
				|| StringUtil.isBlank(password)){//后台验证用户名和密码
			return new ApiResult(false,ResultStatusConstant.Login.LOGIN_INFO_ERROR,"user.info.not.complete.");
		}
		if (!PasswordUtils.validatePwd(password)){
			return new ApiResult(false,ResultStatusConstant.Login.PASSWORD_PATTERN_ERROR,"password.pattern.error.");
		}
		WUser wUser = wUserService.login(loginName, password); //登录
		if(ObjectUtil.equals(null, wUser)){   //判断是否登录成功
			return new ApiResult(false,ResultStatusConstant.FAIL,"login.fail.");
		}
		String ipAddr = IpUtils.getIpAddr(request);
		wUserService.updateWUserByUserId(wUser.getId(), ipAddr);   //记录登录信息
		
		JSONObject  jsonObject = new JSONObject();
		//登录成功 返回用户唯一标志token和对应由密码种子+uid生成的key值
		String appToken = AuthUtils.createToken(wUser.getId());
		jsonObject.put(DataConstant.APP_TOKEN,appToken);
		String secretKey = AuthUtils.createSecretKey(wUser);
		jsonObject.put(DataConstant.SECRET_KEY,secretKey);
		// 缓存用户信息
		DataConstant.CACHE_USER_MAP.put(appToken,new CacheUser(wUser,secretKey));
		return new ApiResult(true,ResultStatusConstant.SUCCESS,"login.success.",jsonObject);
	} 
	
	
	/**
	 * 检查注册券是否存在
	 * @return
	 */
	public boolean checkRegistCoupon(List<FSimpleCoupon> coupons){
		//判断查询数据是否为空
		if(CollectionUtils.isEmpty(coupons)){
			return false;
		}
		FSimpleCoupon f = coupons.get(0);
		//判断优惠券状态是否为1
		if(f.getStatus()!=1){
			return false;
		}
		//判断优惠券截止日期是否过期
		if(f.getDeadline() != null && f.getDeadline() < Dates.getCurrentLongDate()){
			return false;
		}
		return true;
	}
	
	/**
	 * 用户操盘中账户
	 * @param request
	 * @param httpServletResponse
	 * @return
	 */
	@RequestMapping(value = "/operateLogin",method=RequestMethod.POST)
	@ResponseBody 
	public ApiResult operateLogin(HttpServletRequest request,HttpServletResponse httpServletResponse){
		
		String mobile=request.getParameter("mobile");
		WUser wUser=wUserService.getWUserByMobile(mobile);
		ApiResult apiResult=new ApiResult();
		if(wUser!=null){
			List<FSimpleFtseUserTrade> list=fSimpleFtseUserTradeService.findByUidAndStateType(wUser.getId());
			apiResult.setData(list);
		}
		return apiResult;
	}
}
