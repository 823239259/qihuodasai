package com.tzdr.web.controller.ExtensionSign;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tzdr.business.service.extension.ActivityRewardService;
import com.tzdr.business.service.generalize.GeneralizeChannelService;
import com.tzdr.business.service.generalize.GeneralizeService;
import com.tzdr.business.service.securitycode.SecurityCodeService;
import com.tzdr.business.service.wuser.WUserService;
import com.tzdr.common.utils.IpAddressUtils;
import com.tzdr.common.utils.IpUtils;
import com.tzdr.common.utils.StringCodeUtils;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.domain.constants.ExtensionConstants;
import com.tzdr.domain.web.entity.ActivityReward;
import com.tzdr.domain.web.entity.GeneralizeChannel;
import com.tzdr.domain.web.entity.GeneralizeVisit;
import com.tzdr.domain.web.entity.SecurityCode;
import com.tzdr.domain.web.entity.WUser;
import com.tzdr.web.constants.Constants;
import com.tzdr.web.constants.ViewConstants;
import com.tzdr.web.thread.RegistP2pThread;
import com.tzdr.web.utils.UserSessionBean;

import jodd.util.Base64;
import jodd.util.StringUtil;

@Controller
@RequestMapping(value = "/extendsion/sign")
public class ExtendsionSignController {
	@Autowired
	private GeneralizeChannelService channelService;
	@Autowired
	private WUserService wUserService;
	@Autowired
	private SecurityCodeService securityCodeService;
	@Autowired
	private GeneralizeService generalizeService;
	@Autowired
	private ActivityRewardService activityRewardService;
	/**
	 * 上线推广注册页面
	 * @param request
	 * 	2016.08.01
	 * @return
	 */
	@RequestMapping(value = "/view",method = RequestMethod.GET)
	public String extendSignView(ModelMap modelMap,HttpServletRequest request,
								@RequestParam(value = "channelCode",required = false)String channelCode,
								@RequestParam(value = "activity",required = false) String activity){
		GeneralizeVisit  generalizeVisit = new GeneralizeVisit();
		String ip = IpUtils.getIpAddr(request);
		generalizeVisit.setClieantIp(ip);
		generalizeVisit.setCreatedate(new Date().getTime()/1000);
		generalizeVisit.setDeleted(false);
		generalizeVisit.setCity(IpAddressUtils.getAffiliationCity(ip, "utf-8"));
		generalizeVisit.setGeneralizeId(null);
		generalizeVisit.setParam(channelCode);
		generalizeVisit.setUrl(request.getRequestURL().toString()+"?"+request.getQueryString());
		generalizeVisit.setActivity(ExtensionConstants.ACTIVITY_TYPE);
		generalizeService.saveGeneralizeVisit(generalizeVisit);
		modelMap.put("channelCode", channelCode);
		return ViewConstants.SignInViewJsp.EXTENDSIONSIGN_VEIW;
	}
	/**
	 * 跳转到抽奖的页面
	 */
	@RequestMapping(value = "/luck/view",method = RequestMethod.GET)
	public String extenLuckView(){
		return ViewConstants.SignInViewJsp.EXTENDSION_LUCK_VEIW;
	}
	private Object lock = new Object();
	/**
	 * 注册
	 * @param channelCode
	 * @param source
	 * @param mobile
	 * @param code
	 * @param password
	 * @param parentGeneralizeId
	 * @param yzmCode
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/sign",method = RequestMethod.POST)
	@ResponseBody
	public JsonResult extensionSign(@RequestParam("channelCode") String channelCode,
							Integer source,
							String mobile,
							String code, 
							String password,
							String parentGeneralizeId,
							String yzmCode,
							ModelMap modelMap,
							HttpServletRequest request,
							HttpServletResponse response ){
		JsonResult  jsonResult = new JsonResult(true);
		if(wUserService.getWUserByMobile(mobile) != null){ //判断手机号码是否已存在
			jsonResult.setMessage("mobileIsExist");
			return jsonResult;
		}
		SecurityCode securityCode = securityCodeService.getSecurityCodeByMobile(mobile);  //获取验证码信息
		if(securityCode == null || StringUtil.isBlank(code) || !code.equals(securityCode.getSecurityCode())){   //判断验证码是否正确
			jsonResult.setMessage("codeError");
			return jsonResult;
		}else if((new Date().getTime()/1000)-(securityCode.getCreatedate()) > 5*60){  //判断验证码是否失效
			jsonResult.setMessage("codeTimeOut");
			return jsonResult;
		}else if(!StringUtil.isBlank(parentGeneralizeId) && wUserService.findByGeneralizeId(parentGeneralizeId) == null){
			jsonResult.setMessage("generalizeIdError");
			return jsonResult;
		}
		
		WUser wUser = new WUser();     //创建注册对象信息
		wUser.setSource(source == null ? 1 : source);
		WUser platformDefaultWuser = wUserService.queryByUserType(Constants.TZDR_DEFAULT_USERTYPE).get(0);  //获取平台默认用户
		wUser.setUserType("0");
		wUser.setParentNode(platformDefaultWuser);
		wUser.setPassword(password);
		wUser.setMobile(mobile);
		wUser.setCtime((new Date().getTime()/1000));
		wUser.setRegIp(IpUtils.getIpAddr(request));
		GeneralizeChannel generalizeChannel = getChannel(channelCode);
		if(generalizeChannel != null){
			String channelName = generalizeChannel.getTypeThreeTitle();
			if(channelName == null || channelName.length() <= 0){
				channelName = generalizeChannel.getTypeTwoTitle();
				if(channelName == null || channelName.length() <= 0){
					channelName= generalizeChannel.getTypeOneTitle();
				}
			}
			wUser.setChannel(channelName);   //设置渠道
			wUser.setKeyword(generalizeChannel.getUrlKey());//设置关键字
		}
		if(!StringUtil.isBlank(parentGeneralizeId) ){
			WUser generalizeWuser = null;
			if(!StringUtil.isBlank(parentGeneralizeId)){
				generalizeWuser = wUserService.findByGeneralizeId(parentGeneralizeId); 
			}
			if(generalizeWuser != null){
				wUser.setRebate(generalizeWuser.getSubordinateDefaultRebate() == null? 0.00:generalizeWuser.getSubordinateDefaultRebate());
				wUser.setParentNode(generalizeWuser);
			}
		}
		wUser.setLastLoginTime((new Date().getTime()/1000));
		String ip = IpUtils.getIpAddr(request);
		wUser.setLastLoginIp(ip);
		wUser.setRegCity(IpAddressUtils.getAffiliationCity(ip, "utf-8"));
		synchronized (lock) {
			wUserService.saveWUser(wUser);
		}
		
		//p2p 同步注册
		new RegistP2pThread(mobile,password,wUser.getLoginSalt()).start();
		wUser = wUserService.login(mobile, password); //登录
		UserSessionBean userSessionBean = new UserSessionBean();
		userSessionBean.setId(wUser.getId());
		userSessionBean.setEmail(wUser.getEmail());
		userSessionBean.setMobile(wUser.getMobile());
		userSessionBean.setUname(wUser.getUname());
		request.getSession().setAttribute(Constants.TZDR_USER_SESSION, userSessionBean); //保存都信息
		//设置cookie
		ResourceBundle prop = ResourceBundle.getBundle("webconf");
		String cookieCodestr=prop.getString("cookieconf");
		String domain=prop.getString("cookiedomain");
		Long nowdate=new Date().getTime()/1000;
		String cookieval=wUser.getMobile()+","+nowdate+","+cookieCodestr;
		cookieval= Base64.encodeToString(cookieval);
		Cookie mainCookie = new Cookie("tzdrUser",cookieval);  
		mainCookie.setDomain(domain);  
		mainCookie.setPath("/");  
		mainCookie.setMaxAge(60*30);
        response.addCookie(mainCookie); 
        
		String userName = null;  //用户名称
		if(!StringUtil.isBlank(wUser.getMobile())){  //手机号码加*
			String userMobile = wUser.getMobile();
			userName = StringCodeUtils.buildMobile(userMobile);
		}
		request.getSession().setAttribute(com.tzdr.web.constants.Constants.TZDR_USERNAME_SESSION, userName);
		Map<Object, Object> data = new HashMap<Object, Object>();
		data.put("volumeNum", wUser.getVolumeNum());
		data.put("volumePrice", wUser.getVolumePrice());
		data.put("key",  Base64.encodeToString(wUser.getId()));
		data.put("userName", userName);
		jsonResult.setData(data);
		return jsonResult;
	}
	/**
	 * 跳转到注册成功页面
	 * @return
	 */
	@RequestMapping(value="/extensionSignSuc",method = RequestMethod.GET)
	public String extensionSignSucView(){
		return ViewConstants.SignInViewJsp.EXTENDSION_SUCCESS_FUL_VEIW;
	}
	/**
	 * 大转盘抽奖活动增加抽奖用户的账户金额
	 * @param money
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/luckDraw" , method = RequestMethod.POST)
	@ResponseBody
	public JsonResult luckDraw(@RequestParam("money") Double money,HttpServletRequest request){
		UserSessionBean userSessionBean = (UserSessionBean)request.getSession().getAttribute(Constants.TZDR_USER_SESSION);
		String id = userSessionBean.getId();
		JsonResult resultJson = null;
		if(money > 0){
			 boolean flag = wUserService.luckDrawUpdateUser(money, id);
			 if(flag)
				 resultJson = new JsonResult("领取奖励成功，奖金已自动发放到账户余额");
			 else
				 resultJson = new JsonResult("该用户已抽过一次");
		}else{
			resultJson = new JsonResult("金额错误");
		}
		return resultJson;
	}
	/**
	 * 验证是是否有奖励/抽奖
	 * @param id
	 * @param activity
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/validationTip",method = RequestMethod.POST)
	public JsonResult validationTip(HttpServletRequest request,@RequestParam(value = "activity",required = false) String activity){
		UserSessionBean userSessionBean = (UserSessionBean)request.getSession().getAttribute(Constants.TZDR_USER_SESSION);
		JsonResult resultJson = new JsonResult(); 
		if(userSessionBean == null){
			resultJson.appendData("islogin", false);
			return resultJson;
		}
		String id = userSessionBean.getId();
		//目前活动初始化，默认001
		activity = ExtensionConstants.ACTIVITY_TYPE;
		Integer luck = 0;//是否可以抽奖
		Integer luckNum = 0;//抽奖次数
		Integer subsidy = 0;//是否有补贴
		Integer lucktip = 0;//抽奖是否需要提示
		Integer subsidytip = 0;//补贴是否需要提示
		Double subsidymoney = 0.00;//补贴的金额
		//查询用户是否还有抽奖机会
		ActivityReward activityReward = activityRewardService.findByUidAndActivity(id, activity,false,ExtensionConstants.REWARD_TYPE_LUCK_DRAW);
		//查询是否还有补贴的机会
		List<ActivityReward> rewards = activityRewardService.doGetActivitySubsidy(id, activity,true,ExtensionConstants.REWARD_TYPE_SUBSIDY,false);
		if(activityReward != null){
			luck = 1;
			luckNum = 1;
			if(!activityReward.getIstip()){
				lucktip = 1;
			}
		}
		if(rewards != null){
			if(rewards.size() > 0){
				subsidytip = 1;
				subsidy = 1;
			}
			for (ActivityReward reward : rewards) {
				subsidymoney += reward.getMoney();
				
			}
		}
		resultJson.appendData("luck", luck);
		resultJson.appendData("luckNum", luckNum);
		resultJson.appendData("lucktip",lucktip);
		resultJson.appendData("subsidy", subsidy);
		resultJson.appendData("subsidyMoney", subsidymoney);
		resultJson.appendData("subsidytip", subsidytip);
		resultJson.appendData("islogin", true);
		return resultJson;
	} 
	public GeneralizeChannel getChannel(String params){
		List<GeneralizeChannel> generaList = channelService.findByParamAndDeletedFalse(params);
		GeneralizeChannel channel2 = null;
		if(generaList.size() > 0){
			channel2 = generaList.get(0);
		}
		return channel2;
	}
}
