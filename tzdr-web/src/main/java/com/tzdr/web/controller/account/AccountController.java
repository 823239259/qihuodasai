package com.tzdr.web.controller.account;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jodd.util.Base64;
import jodd.util.StringUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tzdr.business.service.drawMoney.UserBankService;
import com.tzdr.business.service.future.FutureAccountService;
import com.tzdr.business.service.pay.UserFundService;
import com.tzdr.business.service.securityInfo.SecurityInfoService;
import com.tzdr.business.service.userTrade.FSimpleFtseUserTradeService;
import com.tzdr.business.service.userTrade.UserTradeService;
import com.tzdr.business.service.wuser.WUserService;
import com.tzdr.common.utils.BigDecimalUtils;
import com.tzdr.common.utils.SysStringUtils;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.domain.vo.UserTradeArrearageVo;
import com.tzdr.domain.vo.UserTradeVo;
import com.tzdr.domain.vo.future.FutureAccountVo;
import com.tzdr.domain.web.entity.FSimpleFtseUserTrade;
import com.tzdr.domain.web.entity.UserBank;
import com.tzdr.domain.web.entity.UserVerified;
import com.tzdr.domain.web.entity.WUser;
import com.tzdr.domain.web.entity.future.FaccountInfo;
import com.tzdr.web.constants.Constants;
import com.tzdr.web.constants.ViewConstants;
import com.tzdr.web.utils.UserSessionBean;

/**
* @Description: TODO(用户帐号业务信息管理控制层)
* @ClassName: AccountController
* @author wangpinqun
* @date 2015年1月4日 下午2:23:30
 */
@Controller
@RequestMapping("/user")
public class AccountController {
	
	private static Logger log = LoggerFactory.getLogger(AccountController.class);

	@Autowired
	private SecurityInfoService securityInfoService;
	
	@Autowired
	private UserTradeService userTradeService;
	
	@Autowired
	private WUserService wUserService;
	
	@Autowired
	private UserBankService  userBankService;
	
	@Autowired
	private UserFundService userFundService;
	
	@Autowired
	private FutureAccountService futureAccountService;
	@Autowired
	private FSimpleFtseUserTradeService fSimpleFtseUserTradeService;

	private static Object lock = new Object();
	
	/**
	* @Description: TODO(  个人中心首页)
	* @Title: account
	* @param modelMap
	* @param request
	* @param response
	* @return String    返回类型
	 */
	@RequestMapping(value="/account")
	public String account(ModelMap modelMap,HttpServletRequest request,HttpServletResponse response){
		UserSessionBean userSessionBean = (UserSessionBean)request.getSession().getAttribute(Constants.TZDR_USER_SESSION);  //获取用户账户信息
		UserVerified userVerified = securityInfoService.findByUserId(userSessionBean.getId());          //获取用户安全信息
		int safetyCount = 0;  		//安全验证个数
		List<UserBank> banks=userBankService.findUserBankByuserId(userSessionBean.getId());
		if(userVerified != null){
			if(banks != null && banks.size()>0){   //判断是否绑定手机号码
				safetyCount += 1;
			}
			if(userVerified.getEmailActive() != null && userVerified.getEmailActive() == 1){     //判断是否绑定邮箱
				safetyCount += 1;
			}
			if(userVerified.getIdcard() != null && !StringUtil.isBlank(userVerified.getIdcard())){    //判断实名是否认证
				safetyCount += 1;
			}
			if(userVerified.getDrawMoneyPwd() != null && !StringUtil.isBlank(userVerified.getDrawMoneyPwd())){  //判断是否设置提款密码
				safetyCount += 1;
			}
		}
		
		
		WUser newWuser = wUserService.getUser(userSessionBean.getId());  //获取新的用户帐号信息
//		Double totalLending = 0.00;     //操盘中总配资金额
//		Double totalLeverMoney = 0.00;  //操盘中总风险保证金
		//获取用户进行中的配资列表信息
//		List<UserTradeVo> userTradeVoList = userTradeService.queryUserTradeVoByWuserAndStatus(newWuser.getId(), (short)1);
//		if(userTradeVoList != null && !userTradeVoList.isEmpty()){
//			for (UserTradeVo userTradeVo : userTradeVoList) {
//				totalLending = BigDecimalUtils.add2(totalLending, userTradeVo.getTotalMoney());
//				totalLeverMoney = BigDecimalUtils.add2(totalLeverMoney, userTradeVo.getTotalLeverMoney());
//				totalLeverMoney = BigDecimalUtils.add2(totalLeverMoney, userTradeVo.getTotalAppendLeverMoney());
//			}
//		}
		if (newWuser.getActivityType() == WUser.ActivityType.ACTIVITY_6600) {
			modelMap.put("activityType6600", 2);
		}
		else {
			modelMap.put("activityType6600", 0);
		}
		
		//获取用户配资累计信息
		Double totalAccrual = userTradeService.getSumAccrualByWuserAndStatus(newWuser.getId(), (short)2);  //累计盈亏
		totalAccrual = BigDecimalUtils.round2(totalAccrual, 2);
		//盈亏率
		Double totalAccrualInterest = BigDecimalUtils.add2(newWuser.getTotalDeposit(), BigDecimalUtils.add2(newWuser.getTotalManagerMo(), newWuser.getTotalInterestMo()));
		totalAccrualInterest = totalAccrualInterest == null || totalAccrualInterest == 0 || totalAccrual == 0 ? 0.00 : BigDecimalUtils.div(totalAccrual, totalAccrualInterest, 4) * 100;  
		totalAccrualInterest = BigDecimalUtils.round1(totalAccrualInterest,2);
		if(banks!=null && banks.size()>0){
			modelMap.put("isbank", true);    	
		}
		
		modelMap.put("wUser", newWuser);    	   						   //用户帐号信息
//		modelMap.put("totalAssets", BigDecimalUtils.round2(BigDecimalUtils.add2(BigDecimalUtils.add2(newWuser.getFrzBal(),newWuser.getAvlBal()),totalLeverMoney),2));  //帐号总资产
		modelMap.put("userName", request.getSession().getAttribute(Constants.TZDR_USERNAME_SESSION));   //用户名称
		modelMap.put("userVerified", userVerified);                    //用户安全认证信息
		int indexTime = SysStringUtils.sayHelloTime();
		modelMap.put("sayHelloTime", (indexTime == 0 ? "晚上" : indexTime == 1 ? "早上" : indexTime == 2 ? "上午" : indexTime == 3 ? "中午" : "下午"));   //问候时间
		modelMap.put("safetyCount", safetyCount);                      //认证个数
//		modelMap.put("totalLending", totalLending);    		 //操盘中总配资金额
//		modelMap.put("totalLeverMoney", totalLeverMoney);    //操盘中总风险保证金
		modelMap.put("totalAccrual", totalAccrual);                    //累计盈亏
		modelMap.put("totalAccrualInterest", totalAccrualInterest);    //盈亏率
//		modelMap.put("userTradeVoList", userTradeVoList);    	   	   //操盘中配资列表
		//参加8800活动
		if(WUser.UserType.WEB_REGIST.equals(newWuser.getUserType())&&WUser.ActivityType.ACTIVITY_6600==newWuser.getActivityType()){
			modelMap.put("isActivityType", 1);   
		}
		if(WUser.ActivityType.ACTIVITY_6600==newWuser.getActivityType()){
			modelMap.put("activity", 1);   
		}
		//获取股指期货的账户信息
		FutureAccountVo futuresAccountVo=futureAccountService.getFuturesAccountVo(newWuser.getId());
		modelMap.put("futuresAccount", futuresAccountVo);
		
		return ViewConstants.AccountViewJsp.ACCOUNT_INDEX_VIEW;
	}
	
	/**
	* @Description: TODO(异步获取账户相关信息)
	* @Title: queryAccount
	* @param modelMap
	* @param request
	* @param response
	* @return JsonResult    返回类型
	 */
	@RequestMapping(value = "/queryAccount")
	@ResponseBody
	public JsonResult queryAccount(ModelMap modelMap,HttpServletRequest request,HttpServletResponse response){
		JsonResult  jsonResult = new JsonResult(true);
		UserSessionBean userSessionBean = (UserSessionBean)request.getSession().getAttribute(Constants.TZDR_USER_SESSION);  //获取用户账户信息
		WUser newWuser = wUserService.getUser(userSessionBean.getId());  //获取新的用户帐号信息
		Double totalLending = 0.00;     //操盘中总配资金额
		Double totalLeverMoney = 0.00;  //操盘中总风险保证金
		//获取用户进行中的配资列表信息
		//List<UserTradeVo> userTradeVoList = userTradeService.queryUserTradeVoByWuserAndStatus(userSessionBean.getId(), (short)1, (short)0);
		List<UserTradeVo> userTradeVoList = userTradeService.queryNewUserTradeVoByWuserAndStatus(userSessionBean.getId(), (short)1, (short)0);
		if(userTradeVoList != null && !userTradeVoList.isEmpty()){
			for (UserTradeVo userTradeVo : userTradeVoList) {
				totalLending = BigDecimalUtils.add2(totalLending, userTradeVo.getTotalMoney());
				totalLeverMoney = BigDecimalUtils.add2(totalLeverMoney, userTradeVo.getTotalLeverMoney());
				totalLeverMoney = BigDecimalUtils.add2(totalLeverMoney, userTradeVo.getTotalAppendLeverMoney());
			}
		}
		
		/**
		 * A股欠费方案列表
		 */
		List<UserTradeArrearageVo> userATradeArrearageVos = userFundService.queryUserTradeArrearageVo(userSessionBean.getId(), null,new Integer[]{11,12});
		
		/**
		 * 港股欠费方案列表
		 */
		List<UserTradeArrearageVo> userHkTradeArrearageVos = userFundService.queryHkUserTradeArrearageVo(userSessionBean.getId(), null,new Integer[]{11,12});
		
		/**
		 * 欠费方案列表
		 */
		List<UserTradeArrearageVo> userTradeArrearageVos = new ArrayList<UserTradeArrearageVo>();
		
		if(userATradeArrearageVos != null && !userATradeArrearageVos.isEmpty()){  //A股欠费方案列表
			for (UserTradeArrearageVo userTradeArrearageVo : userATradeArrearageVos) {
				userTradeArrearageVos.add(userTradeArrearageVo);
			}
		}
		
		if(userHkTradeArrearageVos != null && !userHkTradeArrearageVos.isEmpty()){  //港股欠费方案列表
			for (UserTradeArrearageVo userTradeArrearageVo : userHkTradeArrearageVos) {
				userTradeArrearageVos.add(userTradeArrearageVo);
			}
		}
		
		Map<Object, Object> data = new HashMap<Object, Object>();
		data.put("avlBal", BigDecimalUtils.round2(newWuser.getAvlBal(), 2));
		data.put("frzBal", BigDecimalUtils.round2(newWuser.getFrzBal(),2));
		data.put("totalAssets", BigDecimalUtils.round2(BigDecimalUtils.add2(BigDecimalUtils.add2(newWuser.getFrzBal(),newWuser.getAvlBal()),totalLeverMoney),2));  //帐号总资产
		data.put("totalLending", BigDecimalUtils.round1(totalLending,2));    		 //操盘中总配资金额
		data.put("totalLeverMoney", BigDecimalUtils.round1(totalLeverMoney,2));    //操盘中总风险保证金
		data.put("userTradeVoList", userTradeVoList);    //操盘中配资列表
		data.put("userTradeArrearageVos", userTradeArrearageVos);  //欠费方案列表
		jsonResult.setData(data);
		return jsonResult;
	}
	
	/**
	* @Description: 补费-欠费方案费用
	* @Title: lateTradeFee
	* @param modelMap
	* @param request
	* @param response
	* @return JsonResult    返回类型
	 */
	@RequestMapping(value = "/lateTradeFee", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult lateTradeFee(ModelMap modelMap,String tradeId,Double latePayMoney,HttpServletRequest request,HttpServletResponse response){
		synchronized(lock) {
			JsonResult  jsonResult = new JsonResult(true);
			UserSessionBean userSessionBean = (UserSessionBean)request.getSession().getAttribute(Constants.TZDR_USER_SESSION);  //获取用户账户信息
			WUser newWuser = wUserService.getUser(userSessionBean.getId());  //获取新的用户帐号信息
			double avlBal = BigDecimalUtils.round2(newWuser.getAvlBal(), 2);  //余额
			//获取A股方案欠费信息
			List<UserTradeArrearageVo> userTradeArrearageVos = userFundService.queryUserTradeArrearageVo(userSessionBean.getId(), tradeId,new Integer[]{11,12});
			UserTradeArrearageVo userTradeArrearageVo = userTradeArrearageVos == null || userTradeArrearageVos.isEmpty() ? null : userTradeArrearageVos.get(0);
			if(userTradeArrearageVo == null){  //判断是否找到A股信息，未找到继续找港股欠费信息
				userTradeArrearageVos = userFundService.queryHkUserTradeArrearageVo(userSessionBean.getId(), tradeId,new Integer[]{11,12});
				userTradeArrearageVo = userTradeArrearageVos == null || userTradeArrearageVos.isEmpty() ? null : userTradeArrearageVos.get(0);
			}
			
			if(userTradeArrearageVo != null){
				if(BigDecimalUtils.round2(userTradeArrearageVo.getMoney(), 2) > avlBal){
					jsonResult.setMessage("balanceLack");
					return jsonResult;	
				}
			}else{
				jsonResult.setMessage("notFindData");
				return jsonResult;
			}
			
			//补费+补仓线以上+解除限制买入
			userFundService.updateUserFund(newWuser, tradeId);
			return jsonResult;
		}
	}
	
	/**
	 * 获取方案盈亏信息
	 * @param modelMap
	 * @param groupId  
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getTradeAccrual", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult getTradeAccrual(ModelMap modelMap,String groupId,HttpServletRequest request,HttpServletResponse response){
		
		JsonResult  jsonResult = new JsonResult(true);
		
		if(StringUtil.isBlank(groupId)){
			jsonResult.setMessage("groupIdNotNull");
			return jsonResult;
		}
		
		UserSessionBean userSessionBean = (UserSessionBean)request.getSession().getAttribute(Constants.TZDR_USER_SESSION);  //获取用户账户信息
		
		Double tradeAccrual = userTradeService.getTradeAccrual(userSessionBean.getId(), groupId);
		
		Map<Object, Object> data = new HashMap<Object, Object>();
		data.put("tradeAccrual", tradeAccrual);
		jsonResult.setData(data);
		
		return jsonResult;
	}
	
	/**
	 * 内转功能
	 * @param outAccount	转出账户
	 * @param intoAccount 转入账户
	 * @param money			操作金额
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/convert", method=RequestMethod.POST)
	@ResponseBody
	public JsonResult convert(int outAccount, int intoAccount, BigDecimal money, HttpServletRequest request,HttpServletResponse response){
		JsonResult  jsonResult = new JsonResult(false);
		if(!checkMoney(money)){
			jsonResult.setMessage("请输入正确的金额,最小金额为分！");
			return jsonResult;
		}
		UserSessionBean userSessionBean = (UserSessionBean)request.getSession().getAttribute(Constants.TZDR_USER_SESSION);  //获取用户账户信息
		WUser wUser = wUserService.getUser(userSessionBean.getId());  //获取帐号信息
		FaccountInfo faccountInfo=futureAccountService.findOrSave(userSessionBean.getId());
		
		try {
			//从平台账户转到期货账户
			if(outAccount==1 && intoAccount==2){
				if(futureAccountService.isRange(wUser, money)){
					futureAccountService.transferMoney(faccountInfo, wUser, false, money);
					jsonResult.setSuccess(true);
					jsonResult.setMessage("内转成功");
				}else{
					jsonResult.setMessage("余额不足");
				}
			}
			//从期货账户转到平台账户
			else if(outAccount==2 && intoAccount==1){
				if(futureAccountService.isRange(faccountInfo, money)){
					futureAccountService.transferMoney(faccountInfo, wUser, true, money);
					jsonResult.setSuccess(true);
					jsonResult.setMessage("内转成功");
				}else{
					jsonResult.setMessage("余额不足");
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.debug(e.getMessage());
			jsonResult.setMessage("系统异常");
		}
		return jsonResult;
	}
	//用户操盘中账户
	@RequestMapping(value = "/operateLogin",method=RequestMethod.GET)
	@ResponseBody 
	public JsonResult operateLogin(HttpServletRequest request,HttpServletResponse httpServletResponse){
		UserSessionBean userSessionBean = (UserSessionBean)request.getSession().getAttribute(Constants.TZDR_USER_SESSION);  //获取用户账户信息
		WUser wUser=wUserService.getUser(userSessionBean.getId());
		JsonResult apiResult=new JsonResult();
		if(wUser!=null){
			List<FSimpleFtseUserTrade> list=fSimpleFtseUserTradeService.findByUidAndStateType(wUser.getId());
			apiResult.appendData("data", list);
		}
		return apiResult;
	}
	/**
	 * 检测内转金额
	 * @param money
	 * @return
	 */
	private boolean checkMoney(BigDecimal money){
		Pattern pattern = Pattern.compile("^[0-9]+([.]{1}[0-9]{1,2})?$");
        Matcher isMoney = pattern.matcher(money.toString());
        if(isMoney.matches() ){
              return true;
        }
		return false;
	}
	@RequestMapping(value = "/redirectVsNet")
	public String redirectVsNet(HttpServletRequest request,HttpServletResponse response){
		UserSessionBean userSessionBean = (UserSessionBean)request.getSession().getAttribute(Constants.TZDR_USER_SESSION);  //获取用户账户信息
		StringBuffer url = new StringBuffer();
		url.append(request.getParameter("url"));
		byte[] b = null;  
        String s = null; 
        try {  
        	if(userSessionBean != null){
        		String mobile = userSessionBean.getMobile();
        		b = mobile.getBytes("utf-8");  
    		   if (b != null) {  
    				s = Base64.encodeToString(b);
    				url.append("?o="+s+"");
    		   }
        	}
        	
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
        } 
		return "redirect:"+url.toString()+"";
	}
}
