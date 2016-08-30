package com.tzdr.web.controller.hsiSpread;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jodd.util.Base64;
import jodd.util.StringUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tzdr.business.service.datamap.DataMapService;
import com.tzdr.business.service.securitycode.SecurityCodeService;
import com.tzdr.business.service.wuser.WUserService;
import com.tzdr.common.api.ihuyi.SMSSender;
import com.tzdr.common.utils.ConfUtil;
import com.tzdr.common.utils.IpAddressUtils;
import com.tzdr.common.utils.IpUtils;
import com.tzdr.common.utils.Md5Utils;
import com.tzdr.common.utils.MessageUtils;
import com.tzdr.common.utils.RandomCodeUtil;
import com.tzdr.common.utils.StringCodeUtils;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.domain.web.entity.SecurityCode;
import com.tzdr.domain.web.entity.WUser;
import com.tzdr.web.constants.Constants;
import com.tzdr.web.constants.ViewConstants;
import com.tzdr.web.utils.SendCodeMaxCount;
import com.tzdr.web.utils.SendCodeMaxCountBean;
import com.tzdr.web.utils.UserSessionBean;

@Controller
@RequestMapping("/hsiSpread")
public class HsiLoginController {
	private static Logger log = LoggerFactory.getLogger(HsiLoginController.class);
	
	@Autowired
	private DataMapService dataMapService;
	
	@Value("#{config['mobile.rule']}")
	private String mobileRule;
	
	@Value("#{config['login.password.rule']}")
	private String passwordRule;
	@Autowired
	private SecurityCodeService securityCodeService;
	@Autowired
	private WUserService wUserService;
	private static Object lock = new Object();
	@RequestMapping(value = "/index")
	public String index(ModelMap modelMap,HttpServletRequest request,HttpServletResponse response){

		return ViewConstants.HsiSpreadJsp.INDEX;
	}
	@RequestMapping(value = "/success")
	public String success(ModelMap modelMap,HttpServletRequest request,HttpServletResponse response){
		
		return ViewConstants.HsiSpreadJsp.SUCCESS;
	}
	

	
	/**
	* @Description: 下方短信验证码信息
	* @Title: sendMobileCode
	* @param mobile    手机号码
	* @param modelMap
	* @param request
	* @param response
	* @return
	* @return JsonResult    返回类型
	 */
	@RequestMapping(value = "/send_mobile_code")
	@ResponseBody
	public JsonResult sendMobileCode(String mobile,ModelMap modelMap,HttpServletRequest request,HttpServletResponse response){
		JsonResult  jsonResult = new JsonResult(true);
		
		if(wUserService.getWUserByMobile(mobile) != null){ //判断手机号码是否已存在
			jsonResult.setMessage("mobileIsExist");
			return jsonResult;
		}
		if(StringUtil.isBlank(mobile)){
			jsonResult.setMessage("mobileIsNull");
			return jsonResult;
		}
		String mobileRule = ConfUtil.getContext("mobile.rule");
		
		Pattern p = Pattern.compile(mobileRule);  
		  
	    Matcher m = p.matcher(mobile); 
		if(!m.matches()){
			jsonResult.setMessage("mobileFormatError");
			return jsonResult;
		}
		SecurityCode oldSecurityCode = securityCodeService.getSecurityCodeByMobile(mobile);  //获取验证码信息
		if(oldSecurityCode != null && new Date().getTime()/1000 - oldSecurityCode.getCreatedate() < Constants.SEND_SMS_MAX_TIME){
			jsonResult.setMessage("highOperation");
			return jsonResult;
		}
		jsonResult.setMessage("sendMobileMessage");
		return jsonResult;
		
	}
	
	
	/**
	* @Description: 发送短信
	* @Title: sendMobileMessage
	* @param mobile    手机号码
	* @param modelMap
	* @param request
	* @param response
	* @return
	* @return JsonResult    返回类型
	 */
	@RequestMapping(value = "/send_mobile_message")
	@ResponseBody
	public JsonResult sendMobileMessage(String mobile,ModelMap modelMap,HttpServletRequest request,HttpServletResponse response){
		JsonResult  jsonResult = new JsonResult(true);
		ConcurrentHashMap<String, SendCodeMaxCountBean> sendSMSCodeMaxCountMap = SendCodeMaxCount.sendSMSCodeMaxCountMap;
		SendCodeMaxCountBean  sendSMSCodeMaxCountData = !sendSMSCodeMaxCountMap.containsKey(mobile) ? null : sendSMSCodeMaxCountMap.get(mobile);
		if(sendSMSCodeMaxCountData != null && Constants.SIGNIN_SEND_CODE_MAX_COUNT_SESSION < sendSMSCodeMaxCountData.getSendCodeCount()){
			SendCodeMaxCount.addSendSMSCodeMaxCountMap(mobile, request, response);
			jsonResult.setMessage("sendMobileCodeFail");
			return jsonResult;
		}else{
			SendCodeMaxCount.addSendSMSCodeMaxCountMap(mobile, request, response);
		}
		
		String randomCode = RandomCodeUtil.randStr(6);   //生成6为验证码
		
		SecurityCode securityCode = new SecurityCode();  //创建验证对象
		securityCode.setMobile(mobile);            
		Long createdate = new Date().getTime()/1000;     //生成验证码时间
		securityCode.setCreatedate(createdate);
		securityCode.setSecurityCode(randomCode); 
		securityCodeService.saveSecurityCode(securityCode,mobile);  //保存验证码信息
		
		Map<String,String> smsParams= new HashMap<String,String>();  //创建短信动态参数集合 
		smsParams.put("module", "注册");
		smsParams.put("code", randomCode);
		if(!SMSSender.getInstance().sendByTemplate(dataMapService.getSmsContentRegister(), mobile, "ihuyi.verification.signin.code.template", smsParams)){ //判断短信发送是否成功
			jsonResult.setMessage("sendMobileCodeFail");
		  return jsonResult;
		}
		
		Map<Object, Object> tokenParams = new HashMap<Object, Object>();
		tokenParams.put("token", Md5Utils.hash(randomCode+mobile));
		jsonResult.setData(tokenParams);
		return jsonResult;
	}
	
	/**
	* @Description: 注册用户信息
	* @Title: signIn
	* @param mobile  手机号码
	* @param code    验证码
	* @param password  密码
	* @param modelMap 
	* @param request
	* @param response
	* @return
	* @return JsonResult    返回类型
	 */
	@RequestMapping(value = "/signin_operation")
	@ResponseBody
	public JsonResult signInOperation(Integer source,String mobile,String code, String password,ModelMap modelMap,HttpServletRequest request,HttpServletResponse response){
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
		}
		
		WUser wUser = new WUser();     //创建注册对象信息
		wUser.setSource(source);
		WUser platformDefaultWuser = wUserService.queryByUserType(Constants.TZDR_DEFAULT_USERTYPE).get(0);  //获取平台默认用户
		wUser.setUserType("0");
		wUser.setParentNode(platformDefaultWuser);
		wUser.setPassword(password);
		wUser.setMobile(mobile);
		wUser.setCtime((new Date().getTime()/1000));
		wUser.setRegIp(IpUtils.getIpAddr(request));
		wUser.setChannel(this.getChannel(modelMap, request, response));   //设置渠道
		wUser.setLastLoginTime((new Date().getTime()/1000));
		String ip = IpUtils.getIpAddr(request);
		wUser.setLastLoginIp(ip);
		wUser.setRegCity(IpAddressUtils.getAffiliationCity(ip, "utf-8"));
		synchronized (lock) {
			wUserService.saveWUser(wUser);
		}

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
		SMSSender.getInstance().send(dataMapService.getSmsContentRegister(), mobile, MessageUtils.message("hsisign.success",new Object[]{mobile,password}));
		request.getSession().setAttribute(com.tzdr.web.constants.Constants.TZDR_USERNAME_SESSION, userName);
		Map<Object, Object> data = new HashMap<Object, Object>();
		data.put("name", mobile);
		data.put("password", password);
		jsonResult.setData(data);
		jsonResult.setMessage("success");
		if(jsonResult.getMessage()!=null&&jsonResult.getMessage().equals("success")){
			//用户注册成功之后给用户手机发送短信
			SMSSender.getInstance().sendByTemplate(1, mobile, "ihuyi.verification.signin.success.template", null);
		}
		return jsonResult;
	}
	/**
	* @Description: 获取推广渠道
	* @Title: getChannel
	* @param modelMap
	* @param request
	* @param response
	* @return String    返回类型
	 */
	private String getChannel(ModelMap modelMap,HttpServletRequest request,HttpServletResponse response){
		String channel = (String) request.getSession().getAttribute(Constants.TZDR_CHANNEL);  //推广渠道
		if(StringUtil.isBlank(channel)){
			Cookie[] cookies = request.getCookies();
			if(cookies != null && cookies.length > 0){
				for(Cookie cookie:cookies){
					String cookieName = cookie.getName();
					if(Constants.TZDR_CHANNEL.equals(cookieName)){
						channel = cookie.getValue();
					}
				}
			}
		}
		return channel;
	}
	
}
