package com.tzdr.web.controller.login;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jodd.util.Base64;
import jodd.util.StringUtil;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.code.kaptcha.Constants;
import com.tzdr.business.service.generalize.GeneralizeService;
import com.tzdr.business.service.userInfo.UserInfoService;
import com.tzdr.business.service.userTrade.UserTradeService;
import com.tzdr.business.service.wuser.LoginLogService;
import com.tzdr.business.service.wuser.WUserService;
import com.tzdr.common.utils.ConfUtil;
import com.tzdr.common.utils.Dates;
import com.tzdr.common.utils.IpAddressUtils;
import com.tzdr.common.utils.IpUtils;
import com.tzdr.common.utils.Md5Utils;
import com.tzdr.common.utils.StringCodeUtils;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.domain.web.entity.UserInfo;
import com.tzdr.domain.web.entity.WUser;
import com.tzdr.web.constants.ViewConstants;
import com.tzdr.web.utils.UserLoginFail;
import com.tzdr.web.utils.UserLoginFailBean;
import com.tzdr.web.utils.UserSessionBean;

/**
* @Description: TODO(登录业务信息管理控制层)
* @ClassName: LoginController
* @author wangpinqun
* @date 2014年12月27日 下午4:46:28
 */
@Controller
@RequestMapping("/")
public class LoginController{
	
	private static Logger log = LoggerFactory.getLogger(LoginController.class);
	private ResourceBundle prop = ResourceBundle.getBundle("webconf");
	@Autowired
	private WUserService wUserService;
	@Autowired
	private UserTradeService userTradeService;
	@Autowired
	private GeneralizeService generalizeService;
	
	@Autowired
	private UserInfoService userInfoService;
	
	@Autowired
	private LoginLogService loginLogService;


	/**
	* @Description: TODO(访问登录页面)
	* @Title: toLogin
	* @param modelMap
	* @param request
	* @param response
	* @return String    返回类型
	 */
	@RequestMapping(value = "/login")
	public String login(ModelMap modelMap,String uid,String sourceCode,String backData,String key,HttpServletRequest request,HttpServletResponse response){
		boolean isNeedCode = false;
		Object obj =  request.getSession().getAttribute(com.tzdr.web.constants.Constants.LOGIN_FAIL_MAX_COUNT_SESSION_KEY);
		int loginFailCount = obj == null ? 0:(int)obj;
		if(com.tzdr.web.constants.Constants.LOGIN_FAIL_MAX_COUNT_SESSION < loginFailCount){
			isNeedCode = true;
		}
		modelMap.put("isNeedCode", isNeedCode);
		String backUrl = null;
		log.info("sourceCode:"+sourceCode+",backData:"+backData+",key:"+key);
		if(!StringUtil.isBlank(sourceCode) && !StringUtil.isBlank(backData) && !StringUtil.isBlank(key)){
			String mySourceCode = ConfUtil.getContext("lg.sourceCode");
			String mykey = Md5Utils.hash("sourceCode=000"+mySourceCode+"&backData="+Base64.decodeToString(backData)+ConfUtil.getContext("tzdr.Interface.key")+ConfUtil.getContext("tzdr.Interface.version")+ConfUtil.getContext("login.code"));
			log.info("sourceCode:"+sourceCode+"mySourceCode:"+mySourceCode+",backData:"+Base64.decodeToString(backData)+",key:"+key+",myKey:"+mykey);
			if(sourceCode.equals(mySourceCode) && key.equals(mykey)){
				backUrl = Base64.decodeToString(backData);
			}
		}
		modelMap.put("backData", backUrl);
		return ViewConstants.LOGIN_VIEW;
	}

	
	/**
	* @Description: TODO(登录)
	* @Title: login
	* @param loginName   帐号
	* @param password    密码
	* @param code        验证码
	* @param modelMap
	* @param request
	* @param response
	* @return JsonResult    返回类型
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "login_operation")
	@ResponseBody 
	public JsonResult loginOperation(String loginName,String password,String code,ModelMap modelMap,HttpServletRequest request,HttpServletResponse response){
		JsonResult  jsonResult = new JsonResult(true);
		if(StringUtil.isBlank(loginName) || StringUtil.isBlank(password)){
			jsonResult.setMessage("loginFail");
			return jsonResult;
		}
		Object obj =  request.getServletContext().getAttribute(com.tzdr.web.constants.Constants.LOGIN_FAIL_MAX_COUNT_SESSION_KEY);
		ConcurrentHashMap<String, UserLoginFailBean> userLoginFailMap =  (ConcurrentHashMap<String, UserLoginFailBean>) (obj == null ? null : obj);
		UserLoginFailBean  loginFailData = userLoginFailMap == null || userLoginFailMap.isEmpty() || !userLoginFailMap.containsKey(loginName) ? null : userLoginFailMap.get(loginName);
		String sessionCode = (String)request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
		if(loginFailData != null && com.tzdr.web.constants.Constants.LOGIN_FAIL_MAX_COUNT_SESSION < loginFailData.getLoginFailCount() && (StringUtil.isBlank(code) || !code.equalsIgnoreCase(sessionCode))){
			loginFailData.setLoginFailCount(loginFailData.getLoginFailCount() + 1);
			Map<Object, Object> data = new HashMap<Object, Object>();
			data.put("isNeedCode", false);
			if(com.tzdr.web.constants.Constants.LOGIN_FAIL_MAX_COUNT_SESSION < loginFailData.getLoginFailCount()){
				data.put("isNeedCode", true);
			}
			request.getSession().setAttribute(com.tzdr.web.constants.Constants.LOGIN_FAIL_MAX_COUNT_SESSION_KEY,loginFailData.getLoginFailCount());  //添加或修改session登录失败次数
			UserLoginFail.addUserLoginFailBean(loginFailData, request, response);   //修改登录失败次数
			jsonResult.setData(data);
			jsonResult.setMessage("codeError");
			return jsonResult;
		}
		WUser wUser = wUserService.login(loginName, password); //登录
		if(wUser == null){   //判断是否登录成功
			if(loginFailData == null){
				loginFailData = new UserLoginFailBean();
				loginFailData.setUserName(loginName);
				loginFailData.setLoginFailCount(1);
				loginFailData.setValidDate(new Date());
			}else{
				loginFailData.setLoginFailCount(loginFailData.getLoginFailCount() + 1);
			}
			Map<Object, Object> data = new HashMap<Object, Object>();
			data.put("isNeedCode", false);
			if(obj != null && com.tzdr.web.constants.Constants.LOGIN_FAIL_MAX_COUNT_SESSION < loginFailData.getLoginFailCount()){
				data.put("isNeedCode", true);
			}
			request.getSession().setAttribute(com.tzdr.web.constants.Constants.LOGIN_FAIL_MAX_COUNT_SESSION_KEY,loginFailData.getLoginFailCount());  //添加或修改session登录失败次数
			UserLoginFail.addUserLoginFailBean(loginFailData, request, response);   //添加或修改application登录失败次数
			jsonResult.setData(data);
			jsonResult.setMessage("loginFail");
			return jsonResult;
		}
		//查询是否配资过
		int num=userTradeService.findUserTradeByUidId(wUser.getId());
		if(loginFailData != null){
			UserLoginFail.removeUserLoginFailBean(loginFailData, request, response);    //删除application登录失败次数(包含失效数据)
		}
		request.getSession().removeAttribute(com.tzdr.web.constants.Constants.LOGIN_FAIL_MAX_COUNT_SESSION_KEY);   //清空是否需要验证码登录session登录失败次数
		UserSessionBean userSessionBean = new UserSessionBean();   //session对象
		userSessionBean.setId(wUser.getId());
		userSessionBean.setEmail(wUser.getEmail());
		userSessionBean.setMobile(wUser.getMobile());
		userSessionBean.setUname(wUser.getUname());
		String ipAddr = IpUtils.getIpAddr(request);
		wUserService.updateWUserByUserId(wUser.getId(),ipAddr);   //记录登录信息
		
		
		// 记录登录成功日志
		loginLogService.saveLog(ipAddr,IpAddressUtils.getAffiliationCity(ipAddr,"utf-8"), wUser.getId());
		request.getSession().setAttribute(com.tzdr.web.constants.Constants.TZDR_USER_SESSION, userSessionBean); //保存都信息
		String userName = null;  //用户名称
		if(!StringUtil.isBlank(userSessionBean.getMobile())){  //手机号码加*
			String mobile = userSessionBean.getMobile();
			userName = StringCodeUtils.buildMobile(mobile);
		}
		request.getSession().setAttribute(com.tzdr.web.constants.Constants.TZDR_USERNAME_SESSION, userName);
		Map<Object,Object> data = new HashMap<Object, Object>();
		data.put("key",  Base64.encodeToString(wUser.getId()));
		data.put("userName", userName);
		if(num>0){
			data.put("hasTrade", true);
		}
		jsonResult.setData(data);
		return jsonResult;
	} 
	
	/**
	 * 添加cookie
	 * @param domain
	 * @param cookiename
	 * @param value
	 * @param response
	 * @date 2015年7月13日
	 * @author zhangjun
	 */
	private void addCookie(String domain,String cookiename,String value,HttpServletResponse response){
		Cookie mainCookie = new Cookie("tzdrUser",value);  
		mainCookie.setDomain(domain);  
		mainCookie.setPath("/");  
		mainCookie.setMaxAge(60*30);
        response.addCookie(mainCookie);  
	}
	
	/**
	* @Description: TODO(注销操作)
	* @Title: logout
	* @param modelMap
	* @param request
	* @param response
	* @return String    返回类型
	 */
	@RequestMapping("/logout")
	public String  logout(String sourceCode,String backData,String key,ModelMap modelMap,HttpServletRequest request,HttpServletResponse response){
		
		request.getSession().invalidate();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
		String casServerUrl = ConfUtil.getContext("SSO.casServerUrl");
		String callbackUrl = ConfUtil.getContext("SSO.stock.logout.callback.url");
		return "redirect:" + casServerUrl + "logout?service=" + (StringUtils.isNotEmpty(callbackUrl) ? callbackUrl : basePath);
		
		/*String backUrl = null;
		Boolean isOpan = false;
		try {
			log.info("sourceCode:"+sourceCode+",backData:"+backData+",key:"+key);
			if(!StringUtil.isBlank(sourceCode) && !StringUtil.isBlank(backData) && !StringUtil.isBlank(key)){
				String mySourceCode = ConfUtil.getContext("lg.sourceCode");
				String mykey = Md5Utils.hash("sourceCode=000"+mySourceCode+"&backData="+Base64.decodeToString(backData)+ConfUtil.getContext("tzdr.Interface.key")+ConfUtil.getContext("tzdr.Interface.version")+ConfUtil.getContext("login.code"));
				log.info("sourceCode:"+sourceCode+"mySourceCode:"+mySourceCode+",backData:"+Base64.decodeToString(backData)+",key:"+key+",myKey:"+mykey);
				if(sourceCode.equals(mySourceCode) && key.equals(mykey)){
					backUrl = Base64.decodeToString(backData);
					log.info("backUrl:"+backUrl);
				}
			}
			isOpan = Boolean.valueOf(ConfUtil.getContext("lg.isOpen"));
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		if(backUrl != null && !StringUtil.isBlank(backUrl)){
			return "redirect:"+backUrl; 
		}else{
			if(!isOpan){
				 return "redirect:/";  
			}
			int kcbSourceCode = 5;
			String kcbLogoutUrl = ConfUtil.getContext("singleLogout.url");
			String kcbLogoutkey = ConfUtil.getContext("singleLogout.key");
			String myBackData = ConfUtil.getContext("tzdr.web");
			String kcbKey =  Md5Utils.hash("sourceCode=000"+kcbSourceCode+"&backData="+myBackData+kcbLogoutkey);
			log.info("kcbLogoutUrl:"+kcbLogoutUrl+",kcbLogoutkey:"+kcbLogoutkey+",myBackData:"+myBackData+",kcbKey:"+kcbKey);
			return "redirect:"+kcbLogoutUrl+"?sourceCode="+kcbSourceCode+"&backData="+Base64.encodeToString(myBackData)+"&key="+kcbKey;  
		}*/
	} 
	
	/**
	* @Description: TODO(验证用户是否已经登录)
	* @Title: userLoginCheck
	* @param modelMap
	* @param request
	* @param response
	* @return
	* @return JsonResult    返回类型
	 */
	@RequestMapping(value = "/user_login_check")
	@ResponseBody
	public JsonResult userLoginCheck(ModelMap modelMap,HttpServletRequest request,HttpServletResponse response){
		JsonResult  jsonResult = new JsonResult(true);
		Object object = request.getSession().getAttribute(com.tzdr.web.constants.Constants.TZDR_USER_SESSION);
		//获取用户登录信息
		Map<Object,Object> data = new HashMap<Object, Object>();
		//判断用户是否登录
		if(object == null){
			boolean isNeedCode = false;
			Object obj =  request.getSession().getAttribute(com.tzdr.web.constants.Constants.LOGIN_FAIL_MAX_COUNT_SESSION_KEY);
			int loginFailCount = obj == null ? 0:(int)obj;
			if(com.tzdr.web.constants.Constants.LOGIN_FAIL_MAX_COUNT_SESSION < loginFailCount){
				isNeedCode = true;
			}
			data.put("isNeedCode", isNeedCode);
			jsonResult.setMessage("notLogin");
			jsonResult.setData(data);
			return jsonResult;
		}
		UserSessionBean userSessionBean = (UserSessionBean)object;
		data.put("id", userSessionBean.getId());   //返回用户ID
		data.put("userName", request.getSession().getAttribute(com.tzdr.web.constants.Constants.TZDR_USERNAME_SESSION).toString());  //返回用户名称
		if(!StringUtil.isBlank(userSessionBean.getMobile())){   //返回用户手机号码
			String mobile = userSessionBean.getMobile();
			data.put("mobile", StringCodeUtils.buildMobile(mobile));
		}
		if(!StringUtil.isBlank(userSessionBean.getEmail())){    //返回用户邮箱
			String email = userSessionBean.getEmail();
			data.put("email", StringCodeUtils.buildEmail(email));
		}
		String generalizeUid = this.getGeneralizeId(modelMap, request, response);  //推广人编号
		data.put("generalizeUid", generalizeUid);
		jsonResult.setData(data);
		return jsonResult;
	}
	
	/**
	 * @Description: 论股获取用户信息
	 * @param modelMap
	 * @param uid  用户编号
	 * @param sourceCode   渠道来源
	 * @param key   密钥
	 * @param request
	 * @param response
	 * @return  
	 */
	@RequestMapping(value = "/getUserInfo")
	@ResponseBody
	public JsonResult getUserInfo(ModelMap modelMap,String uid,String sourceCode,String key,HttpServletRequest request,HttpServletResponse response){
		JsonResult  jsonResult = new JsonResult(true);
		Map<Object,Object> data = null;
		UserInfo userInfo = null;
		WUser wuser = null;
		log.info("uid:"+uid+",sourceCode:"+sourceCode+",key:"+key);
		if(!StringUtil.isBlank(sourceCode) && !StringUtil.isBlank(uid) && !StringUtil.isBlank(key)){
			String mySourceCode = ConfUtil.getContext("lg.sourceCode");
			String mykey = Md5Utils.hash("sourceCode=000"+mySourceCode+ConfUtil.getContext("tzdr.Interface.key")+ConfUtil.getContext("tzdr.Interface.version")+ConfUtil.getContext("getUserInfo.code"));
			log.info("sourceCode:"+sourceCode+"mySourceCode:"+mySourceCode+",uid:"+uid+",key:"+key+",myKey:"+mykey);
			if(sourceCode.equals(mySourceCode) && key.equals(mykey)){
				userInfo = userInfoService.getUserInfoByUId(uid);
				wuser = wUserService.get(uid);
			}else{
				jsonResult.setSuccess(false);
				jsonResult.setMessage("No permission");
				return jsonResult;
			}
		}
		if(wuser != null){
			data = new HashMap<Object, Object>();
			data.put("uid", wuser.getId());
			String mobile =  null;
			if(!StringUtil.isBlank(wuser.getMobile())){   //返回用户手机号码
				mobile = wuser.getMobile();
			}
			String email = null;
			data.put("mobile", StringUtil.isBlank(mobile) ? null : StringCodeUtils.buildMobile(mobile));
			if(!StringUtil.isBlank(wuser.getEmail())){    //返回用户邮箱
				email = wuser.getEmail();
			}
			data.put("email", StringUtil.isBlank(email) ? null : StringCodeUtils.buildEmail(email));
			data.put("uname", wuser.getUname());
			data.put("ctime", Dates.format(new Date(wuser.getCtime() * 1000), Dates.CHINESE_DATETIME_FORMAT_LINE));
		}
		if(userInfo != null){
			data.put("marriage", userInfo.getMarriage());
			data.put("education", userInfo.getEducation());
			data.put("province", userInfo.getProvince());
			data.put("city", userInfo.getCity());
			data.put("address", userInfo.getAddress());
			data.put("industry", userInfo.getIndustry());
			data.put("position", userInfo.getPosition());
		}
		jsonResult.setData(data);
		return jsonResult;
	}
	
	/**
	 * @Description: 论股更新用户信息接口
	 * @param modelMap
	 * @param uid
	 * @param sourceCode
	 * @param key
	 * @param uname
	 * @param marriage
	 * @param education
	 * @param province
	 * @param city
	 * @param address
	 * @param industry
	 * @param position
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/updateUserInfo")
	@ResponseBody
	public JsonResult updateUserInfo(ModelMap modelMap,String uid,String sourceCode,String key,String uname,String marriage,String education,String province,String city,String address,String industry,String position,HttpServletRequest request,HttpServletResponse response){
		JsonResult  jsonResult = new JsonResult(true);
		UserInfo userInfo = null;
		WUser wuser = null;
		log.info("uid:"+uid+",sourceCode:"+sourceCode+",key:"+key);
		if(!StringUtil.isBlank(sourceCode) && !StringUtil.isBlank(uid) && !StringUtil.isBlank(key)){
			String mySourceCode = ConfUtil.getContext("lg.sourceCode");
			String mykey = Md5Utils.hash("sourceCode=000"+mySourceCode+ConfUtil.getContext("tzdr.Interface.key")+ConfUtil.getContext("tzdr.Interface.version")+ConfUtil.getContext("updateUserInfo.code"));
			log.info("sourceCode:"+sourceCode+"mySourceCode:"+mySourceCode+",uid:"+uid+",key:"+key+",myKey:"+mykey);
			if(sourceCode.equals(mySourceCode) && key.equals(mykey)){
				userInfo = userInfoService.getUserInfoByUId(uid);
				wuser = wUserService.get(uid);
			}else{
				jsonResult.setSuccess(false);
				jsonResult.setMessage("No permission");
				return jsonResult;
			}
		}
		if(wuser != null){
			if(uname != null){
				wuser.setUname(uname);
			}
			wUserService.update(wuser);
		}
		if(userInfo != null){
			if(marriage != null){
				userInfo.setMarriage(marriage);		
			}
			if(education != null){
				userInfo.setEducation(education);
			}
			if(province != null){
				userInfo.setProvince(province);
			}
			if(city != null){
				userInfo.setCity(city);
			}
			if(address != null){
				userInfo.setAddress(address);
			}
			if(industry != null){
				userInfo.setIndustry(industry);
			}
			if(position != null){
				userInfo.setPosition(position);
			}
			userInfoService.update(userInfo);
		}
		return jsonResult;
	}
	
	/**
	 * @Description 访问达人论股
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/stockindex")
	public String stockindex(ModelMap modelMap,HttpServletRequest request,HttpServletResponse response){
		String drlg = ConfUtil.getContext("discussStock.url");
		return "redirect:"+drlg; 
	}
	
	/**
	* @Description: 获取推广人编号
	* @Title: getGeneralizeUid
	* @param modelMap
	* @param request
	* @param response
	* @return String    返回类型 
	 */
	private String getGeneralizeId(ModelMap modelMap,HttpServletRequest request,HttpServletResponse response){
		String generalizeId = (String) request.getSession().getAttribute(com.tzdr.web.constants.Constants.TZDR_GENERALIZEUID);  //推广人编号
		if(StringUtil.isBlank(generalizeId)){
			Cookie[] cookies = request.getCookies();
			if(cookies != null && cookies.length > 0){
				for(Cookie cookie:cookies){
					String cookieName = cookie.getName();
					if(com.tzdr.web.constants.Constants.TZDR_GENERALIZEUID.equals(cookieName)){
						generalizeId = cookie.getValue();
					}
				}
			}
		}
		return generalizeId;
	}
}
