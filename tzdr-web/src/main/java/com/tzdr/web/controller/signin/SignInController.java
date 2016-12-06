package com.tzdr.web.controller.signin;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tzdr.business.cms.cpp.MockTradeAccountService;
import com.tzdr.business.cms.service.messagePrompt.MessagePromptService;
import com.tzdr.business.service.datamap.DataMapService;
import com.tzdr.business.service.securitycode.SecurityCodeService;
import com.tzdr.business.service.wuser.WUserService;
import com.tzdr.common.api.ihuyi.SMSSender;
import com.tzdr.common.utils.ConfUtil;
import com.tzdr.common.utils.IpAddressUtils;
import com.tzdr.common.utils.IpUtils;
import com.tzdr.common.utils.Md5Utils;
import com.tzdr.common.utils.RandomCodeUtil;
import com.tzdr.common.utils.StringCodeUtils;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.domain.constants.Constant;
import com.tzdr.domain.web.entity.GeneralizeChannel;
import com.tzdr.domain.web.entity.SecurityCode;
import com.tzdr.domain.web.entity.WUser;
import com.tzdr.web.constants.Constants;
import com.tzdr.web.constants.ViewConstants;
import com.tzdr.web.thread.RegistP2pThread;
import com.tzdr.web.utils.SendCodeMaxCount;
import com.tzdr.web.utils.SendCodeMaxCountBean;
import com.tzdr.web.utils.UserSessionBean;

/**
 * @Description: 注册业务信息管理控制层
 * @ClassName: SignInController
 * @author wangpinqun
 * @date 2014年12月25日 上午10:16:36
 */
@Controller
@RequestMapping("/")
public class SignInController {

	private static Logger log = LoggerFactory.getLogger(SignInController.class);

	@Value("#{config['mobile.rule']}")
	private String mobileRule;

	@Value("#{config['login.password.rule']}")
	private String passwordRule;

	@Autowired
	private WUserService wUserService;

	@Autowired
	private SecurityCodeService securityCodeService;

	@Autowired
	private DataMapService dataMapService;

	@Autowired
	private MessagePromptService messagePromptService;
	
	@Autowired
	private MockTradeAccountService mockTradeAccountService;
	private static Object lock = new Object();

	/**
	 * @Description: TODO(访问注册页面)
	 * @Title: toSignIn
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return String 返回类型
	 */
	@RequestMapping(value = "/signin")
	public String signIn(ModelMap modelMap, String sourceCode, String backData, String key, HttpServletRequest request,
			HttpServletResponse response) {
		int source = Constant.RegistSource.WEB_REGIST;
		String generalizeUid = this.getGeneralizeId(modelMap, request, response); // 推广人编号
		modelMap.put("generalizeUid", generalizeUid);
		String backUrl = this.getChannelFrom(false, request, response);
		log.info("sourceCode:" + sourceCode + ",backData:" + backData + ",key:" + key);
		String mySourceCode = ConfUtil.getContext("lg.sourceCode");
		if (!StringUtil.isBlank(sourceCode) && !StringUtil.isBlank(backData) && !StringUtil.isBlank(key)) {
			String mykey = Md5Utils.hash("sourceCode=000" + mySourceCode + "&backData="
					+ Base64.decodeToString(backData) + ConfUtil.getContext("tzdr.Interface.key")
					+ ConfUtil.getContext("tzdr.Interface.version") + ConfUtil.getContext("signin.code"));
			log.info("sourceCode:" + sourceCode + "mySourceCode:" + mySourceCode + ",backData:"
					+ Base64.decodeToString(backData) + ",key:" + key + ",myKey:" + mykey);
			if (sourceCode.equals(mySourceCode) && key.equals(mykey)) {
				backUrl = Base64.decodeToString(backData);
			}
			source = Constant.RegistSource.BBS_REGIST;
		}
		if (StringUtil.equals(sourceCode, "p2p")) {
			backUrl = backData;
			source = Constant.RegistSource.P2P_REGIST;
		}
		modelMap.put("backData", backUrl);
		modelMap.put("source", source);
		return ViewConstants.SignInViewJsp.SIGNIN_VIEW;
	}

	/**
	 * @Description: TODO(验证手机号码是否存在 )
	 * @Title: isExistMobile
	 * @param mobile
	 *            手机号码
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 * @return JsonResult 返回类型
	 */
	@RequestMapping(value = "/is_exist_mobile")
	@ResponseBody
	public JsonResult isExistMobile(String mobile, ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response) {
		JsonResult jsonResult = new JsonResult(true);
		Map<Object, Object> data = new HashMap<Object, Object>();
		data.put("isExist", false);
		if (wUserService.getWUserByMobile(mobile) != null) { // 判断手机号码是否已存在
			data.put("isExist", true);
		}
		jsonResult.setData(data);
		return jsonResult;
	}

	/**
	 * @Description: 下方短信验证码信息
	 * @Title: sendMobileCode
	 * @param mobile
	 *            手机号码
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 * @return JsonResult 返回类型
	 */
	@RequestMapping(value = "/send_mobile_code", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public JsonResult sendMobileCode(String mobile, String yzmCode, ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response) {
		JsonResult jsonResult = new JsonResult(true);

		if (wUserService.getWUserByMobile(mobile) != null) { // 判断手机号码是否已存在
			jsonResult.setMessage("mobileIsExist");
			return jsonResult;
		}
		if (StringUtil.isBlank(mobile)) {
			jsonResult.setMessage("mobileIsNull");
			return jsonResult;
		}
		String mobileRule = ConfUtil.getContext("mobile.rule");

		Pattern p = Pattern.compile(mobileRule);

		Matcher m = p.matcher(mobile);
		if (!m.matches()) {
			jsonResult.setMessage("mobileFormatError");
			return jsonResult;
		}

		String sessionCode = (String) request.getSession()
				.getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
		if (StringUtil.isBlank(yzmCode) || !yzmCode.equalsIgnoreCase(sessionCode)) {
			jsonResult.setMessage("yzmStrError");
			return jsonResult;
		}

		SecurityCode oldSecurityCode = securityCodeService.getSecurityCodeByMobile(mobile); // 获取验证码信息
		if (oldSecurityCode != null
				&& new Date().getTime() / 1000 - oldSecurityCode.getCreatedate() < Constants.SEND_SMS_MAX_TIME) {
			jsonResult.setMessage("highOperation");
			return jsonResult;
		}
		jsonResult.setMessage("sendMobileMessage");
		return jsonResult;

	}

	/**
	 * @Description: 发送短信
	 * @Title: sendMobileMessage
	 * @param mobile
	 *            手机号码
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 * @return JsonResult 返回类型
	 */
	@RequestMapping(value = "/send_mobile_message")
	@ResponseBody
	public JsonResult sendMobileMessage(String mobile, String yzmCode, ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response) {
		JsonResult jsonResult = new JsonResult(true);
		ConcurrentHashMap<String, SendCodeMaxCountBean> sendSMSCodeMaxCountMap = SendCodeMaxCount.sendSMSCodeMaxCountMap;
		SendCodeMaxCountBean sendSMSCodeMaxCountData = !sendSMSCodeMaxCountMap.containsKey(mobile) ? null
				: sendSMSCodeMaxCountMap.get(mobile);
		if (sendSMSCodeMaxCountData != null
				&& Constants.SIGNIN_SEND_CODE_MAX_COUNT_SESSION < sendSMSCodeMaxCountData.getSendCodeCount()) {
			SendCodeMaxCount.addSendSMSCodeMaxCountMap(mobile, request, response);
			jsonResult.setMessage("sendMobileCodeFail");
			return jsonResult;
		} else {
			SendCodeMaxCount.addSendSMSCodeMaxCountMap(mobile, request, response);
		}

		String randomCode = RandomCodeUtil.randStr(6); // 生成6为验证码

		SecurityCode securityCode = new SecurityCode(); // 创建验证对象
		securityCode.setMobile(mobile);
		Long createdate = new Date().getTime() / 1000; // 生成验证码时间
		securityCode.setCreatedate(createdate);
		securityCode.setSecurityCode(randomCode);
		securityCodeService.saveSecurityCode(securityCode, mobile); // 保存验证码信息

		Map<String, String> smsParams = new HashMap<String, String>(); // 创建短信动态参数集合
		String templateKey = "ihuyi.verification.signin.code.template";
		int channel = dataMapService.getSmsContentRegister();
		if(channel == 3){
			templateKey = "tzdr.alidayu.signin.code.template";
			smsParams.put("typeName", "注册");
			smsParams.put("code", randomCode);
		}else{
			smsParams.put("module", "注册");
			smsParams.put("code", randomCode);
		}
		if (!SMSSender.getInstance().sendByTemplate(channel, mobile,
				templateKey, smsParams)) { // 判断短信发送是否成功
			jsonResult.setMessage("sendMobileCodeFail");
			return jsonResult;
		}

		Map<Object, Object> tokenParams = new HashMap<Object, Object>();
		tokenParams.put("token", Md5Utils.hash(randomCode + mobile));
		jsonResult.setData(tokenParams);
		return jsonResult;
	}

	/**
	 * @Description: 注册协议
	 * @Title: websiteAgreement
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return String 返回类型
	 */
	@RequestMapping(value = "/websiteAgreement")
	public String websiteAgreement(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {
		return ViewConstants.SignInViewJsp.SIGNINWEBSITEAGREEMENT;
	}

	/**
	 * @Description: 注册用户信息
	 * @Title: signIn
	 * @param mobile
	 *            手机号码
	 * @param code
	 *            验证码
	 * @param password
	 *            密码
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 * @return JsonResult 返回类型
	 */
	@RequestMapping(value = "/signin_operation")
	@ResponseBody
	public JsonResult signInOperation(Integer source, final String mobile, String code, final String password,
			String parentGeneralizeId, String yzmCode, ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response) {
		JsonResult jsonResult = new JsonResult(true);
		if (wUserService.getWUserByMobile(mobile) != null) { // 判断手机号码是否已存在
			jsonResult.setMessage("mobileIsExist");
			return jsonResult;
		}
		SecurityCode securityCode = securityCodeService.getSecurityCodeByMobile(mobile); // 获取验证码信息
		if (securityCode == null || StringUtil.isBlank(code) || !code.equals(securityCode.getSecurityCode())) { // 判断验证码是否正确
			jsonResult.setMessage("codeError");
			return jsonResult;
		} else if ((new Date().getTime() / 1000) - (securityCode.getCreatedate()) > 5 * 60) { // 判断验证码是否失效
			jsonResult.setMessage("codeTimeOut");
			return jsonResult;
		} else if (!StringUtil.isBlank(parentGeneralizeId)
				&& wUserService.findByGeneralizeId(parentGeneralizeId) == null) {
			jsonResult.setMessage("generalizeIdError");
			return jsonResult;
		}

		WUser wUser = new WUser(); // 创建注册对象信息
		wUser.setSource(source);
		WUser platformDefaultWuser = wUserService.queryByUserType(Constants.TZDR_DEFAULT_USERTYPE).get(0); // 获取平台默认用户
		wUser.setUserType("0");
		wUser.setParentNode(platformDefaultWuser);
		wUser.setPassword(password);
		wUser.setMobile(mobile);
		wUser.setCtime((new Date().getTime() / 1000));
		wUser.setRegIp(IpUtils.getIpAddr(request));
		wUser.setChannel(this.getChannel(modelMap, request, response)); // 设置渠道
		String generalizeId = this.getGeneralizeId(modelMap, request, response); // 推广人编号
		if (!StringUtil.isBlank(parentGeneralizeId) || !StringUtil.isBlank(generalizeId)) {
			WUser generalizeWuser = null;
			if (!StringUtil.isBlank(parentGeneralizeId)) {
				generalizeWuser = wUserService.findByGeneralizeId(parentGeneralizeId);
			}
			if (generalizeWuser == null && !StringUtil.isBlank(generalizeId)) {
				generalizeWuser = wUserService.findByGeneralizeId(generalizeId);
			}
			if (generalizeWuser != null) {
				wUser.setRebate(generalizeWuser.getSubordinateDefaultRebate() == null ? 0.00
						: generalizeWuser.getSubordinateDefaultRebate());
				wUser.setParentNode(generalizeWuser);
			}
		}
		wUser.setLastLoginTime((new Date().getTime() / 1000));
		String ip = IpUtils.getIpAddr(request);
		wUser.setLastLoginIp(ip);
		wUser.setRegCity(IpAddressUtils.getAffiliationCity(ip, "utf-8"));
		synchronized (lock) {
			wUserService.saveWUser(wUser);
		}

		// p2p 同步注册

//		new RegistP2pThread(mobile, password, wUser.getLoginSalt()).start();
		wUser = wUserService.login(mobile, password); // 登录
		UserSessionBean userSessionBean = new UserSessionBean();
		userSessionBean.setId(wUser.getId());
		userSessionBean.setEmail(wUser.getEmail());
		userSessionBean.setMobile(wUser.getMobile());
		userSessionBean.setUname(wUser.getUname());
		request.getSession().setAttribute(Constants.TZDR_USER_SESSION, userSessionBean); // 保存都信息
		// 设置cookie
		ResourceBundle prop = ResourceBundle.getBundle("webconf");
		String cookieCodestr = prop.getString("cookieconf");
		String domain = prop.getString("cookiedomain");
		Long nowdate = new Date().getTime() / 1000;
		String cookieval = wUser.getMobile() + "," + nowdate + "," + cookieCodestr;
		cookieval = Base64.encodeToString(cookieval);
		Cookie mainCookie = new Cookie("tzdrUser", cookieval);
		mainCookie.setDomain(domain);
		mainCookie.setPath("/");
		mainCookie.setMaxAge(60 * 30);
		response.addCookie(mainCookie);

		String userName = null; // 用户名称
		if (!StringUtil.isBlank(wUser.getMobile())) { // 手机号码加*
			String userMobile = wUser.getMobile();
			userName = StringCodeUtils.buildMobile(userMobile);
		}
		request.getSession().setAttribute(com.tzdr.web.constants.Constants.TZDR_USERNAME_SESSION, userName);
		Map<Object, Object> data = new HashMap<Object, Object>();
		data.put("volumeNum", wUser.getVolumeNum());
		data.put("volumePrice", wUser.getVolumePrice());
		data.put("key", Base64.encodeToString(wUser.getId()));
		data.put("userName", userName);
		String from = this.getChannelFrom(true, request, response); // 删除推广地址
		if (!StringUtil.isBlank(from)) {
			request.getSession().removeAttribute(Constants.FIRSTURL_SESSION);
		}
		data.put("from", from);
		jsonResult.setData(data);
		try {
			new Thread(new Runnable() {
				@Override
				public void run() {
					// 用户注册成功之后给用户手机发送短信
					SMSSender.getInstance().sendByTemplate(1, mobile, "ihuyi.verification.signin.success.template", null);
					mockTradeAccountService.openMockAccount(mobile, password);
					messagePromptService.registNotice(mobile, "web", "", "");
				}
			});
		} catch (Exception e) {
			jsonResult.setMessage("模拟盘账号开通失败");
		}
		return jsonResult;
	}
	/**
	 * @Description: 访问注册成功页面
	 * @Title: toSignInSucess
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return String 返回类型
	 */
	@RequestMapping(value = "signinsucess")
	public String signInSucess(String volumeNum, String volumePrice, Integer type, String backData, Integer source,
			ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {
		modelMap.put("volumeNum", StringUtil.isBlank(volumeNum) || volumeNum.equals("null") ? null : volumeNum);
		modelMap.put("volumePrice", StringUtil.isBlank(volumePrice) || volumePrice.equals("null") ? null : volumePrice);
		modelMap.put("type", type == null ? 0 : type);
		modelMap.put("backUrl", StringUtil.isBlank(backData) || backData.equals("null") ? null : backData);
		modelMap.put("source", source == null ? 0 : source);
		return ViewConstants.SignInViewJsp.SIGNINSUCESS_VIEW;
	}

	/**
	 * @Description: 获取推广人编号
	 * @Title: getGeneralizeUid
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return String 返回类型
	 */
	private String getGeneralizeId(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {
		String generalizeId = (String) request.getSession().getAttribute(Constants.TZDR_GENERALIZEUID); // 推广人编号
		if (StringUtil.isBlank(generalizeId)) {
			Cookie[] cookies = request.getCookies();
			if (cookies != null && cookies.length > 0) {
				for (Cookie cookie : cookies) {
					String cookieName = cookie.getName();
					if (Constants.TZDR_GENERALIZEUID.equals(cookieName)) {
						generalizeId = cookie.getValue();
					}
				}
			}
		}
		return generalizeId;
	}
	/**
	 * @Description: 获取推广渠道
	 * @Title: getChannel
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return String 返回类型
	 */
	private String getChannel(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {
		String channel = (String) request.getSession().getAttribute(Constants.TZDR_CHANNEL); // 推广渠道
		if (StringUtil.isBlank(channel)) {
			Cookie[] cookies = request.getCookies();
			if (cookies != null && cookies.length > 0) {
				for (Cookie cookie : cookies) {
					String cookieName = cookie.getName();
					if (Constants.TZDR_CHANNEL.equals(cookieName)) {
						channel = cookie.getValue();
					}
				}
			}
		}
		return channel;
	}

	/**
	 * @Description: 获取推广渠道来源URL
	 * @Title: getChannelFrom
	 * @param isRemove
	 * @param request
	 * @param response
	 * @return String 返回类型
	 */
	private String getChannelFrom(boolean isRemove, HttpServletRequest request, HttpServletResponse response) {
		String channelFrom = (String) request.getSession().getAttribute(Constants.TZDR_CHANNEL_FROM); // 推广渠道
		if (StringUtil.isBlank(channelFrom)) {
			Cookie[] cookies = request.getCookies();
			if (cookies != null && cookies.length > 0) {
				for (Cookie cookie : cookies) {
					String cookieName = cookie.getName();
					if (Constants.TZDR_CHANNEL_FROM.equals(cookieName)) {
						channelFrom = cookie.getValue();
						if (isRemove) {
							Cookie cookieFrom = new Cookie(Constants.TZDR_CHANNEL_FROM, null);
							cookieFrom.setMaxAge(0);
							cookieFrom.setPath("/");
							response.addCookie(cookieFrom);
						}
						break;
					}
				}
			}
		} else {
			request.getSession().removeAttribute(Constants.TZDR_CHANNEL_FROM);
			Cookie[] cookies = request.getCookies();
			if (cookies != null && cookies.length > 0) {
				for (Cookie cookie : cookies) {
					String cookieName = cookie.getName();
					if (Constants.TZDR_CHANNEL_FROM.equals(cookieName)) {
						Cookie cookieFrom = new Cookie(Constants.TZDR_CHANNEL_FROM, null);
						if (isRemove) {
							cookieFrom.setMaxAge(0);
							cookieFrom.setPath("/");
							response.addCookie(cookieFrom);
						}
						break;
					}
				}
			}
		}
		return channelFrom;
	}

	/**
	 * @Description: 访问快速注册页面
	 * @Title: fastsignin
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return String 返回类型
	 */
	@RequestMapping("/fastsignin")
	public String fastsignin(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {
		// return ViewConstants.SignInViewJsp.BAZAAR_FASTSIGNIN_VIEW;
		int source = Constant.RegistSource.WEB_REGIST;
		String backUrl = this.getChannelFrom(false, request, response);
		modelMap.put("backData", backUrl);
		modelMap.put("source", source);
		// return ViewConstants.SignInViewJsp.STOCKSIGNIN_VIEW;
		// return ViewConstants.NOT_FOUND_VIEW;
		return "redirect:http://m.tzdr.com/Home/Public/newreg.html";

	}

	/**
	 * 访问快速注册页面2
	 * 
	 * @MethodName newFastsignin
	 * @author L.Y
	 * @date 2015年5月13日
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/newfastsignin")
	public String newFastsignin(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {
		// return ViewConstants.SignInViewJsp.NEW_FASTSIGNIN_VIEW;
		return ViewConstants.NOT_FOUND_VIEW;
	}

	/**
	 * 股票 注册页面
	 * 
	 * @MethodName stocksignin
	 * @author L.Y
	 * @date 2015年7月23日
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/stocksignin")
	public String stocksignin(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {
		// return ViewConstants.SignInViewJsp.STOCKSIGNIN_VIEW;
		// return ViewConstants.SignInViewJsp.BAZAAR_FASTSIGNIN_VIEW;
		return ViewConstants.NOT_FOUND_VIEW;
	}

	/**
	 * 操作手机号码限制缓存
	 * 
	 * @param type
	 *            -1：清空； 0：查询 ； 1：删除某个手机号码限制（mobile必须填）；
	 * @param mobile
	 *            需要删除的手机号码
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/operationSMSMap")
	@ResponseBody
	public JsonResult operationSendSMSCodeMaxCountMap(Integer type, String mobile, ModelMap modelMap,
			HttpServletRequest request, HttpServletResponse response) {
		JsonResult jsonResult = new JsonResult(true);
		if (type == null) {
			jsonResult.setSuccess(false);
			return jsonResult;
		} else if (-1 == type) {
			SendCodeMaxCount.sendSMSCodeMaxCountMap.clear();
		} else if (0 == type) {
			jsonResult.setObj(SendCodeMaxCount.sendSMSCodeMaxCountMap);
		} else if (1 == type) {
			if (StringUtil.isBlank(mobile)) {
				jsonResult.setSuccess(false);
			} else {
				SendCodeMaxCount.sendSMSCodeMaxCountMap.remove(mobile);
			}
		}
		return jsonResult;
	}

	@RequestMapping(value = "/regist/futures")
	public String annivesarySignIn(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {
		int source = Constant.RegistSource.WEB_REGIST;
		String backUrl = this.getChannelFrom(false, request, response);
		modelMap.put("backData", backUrl);
		modelMap.put("source", source);
		return ViewConstants.SignInViewJsp.ANNIVERSARY_SIGH_IN;
	}

	@RequestMapping(value = "/regist/hkstocks")
	public String hkStockSignIn(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {
		int source = Constant.RegistSource.WEB_REGIST;
		String backUrl = this.getChannelFrom(false, request, response);
		modelMap.put("backData", backUrl);
		modelMap.put("source", source);
		return ViewConstants.SignInViewJsp.HKSTOCK_SIGN_IN;
	}

	/**
	 * @Description: 下方短信验证码信息
	 * @Title: sendMobileCode
	 * @param mobile
	 *            手机号码
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 * @return JsonResult 返回类型
	 */
	@RequestMapping(value = "/send_mobile_sms_code")
	@ResponseBody
	public JsonResult sendMobileSMSCode(String mobile, String yzmCode, ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response) {
		JsonResult jsonResult = new JsonResult(true);
		String sessionCode = (String) request.getSession()
				.getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
		if (wUserService.getWUserByMobile(mobile) != null) { // 判断手机号码是否已存在
			jsonResult.setMessage("mobileIsExist");
			return jsonResult;
		}
		if (StringUtil.isBlank(mobile)) {
			jsonResult.setMessage("mobileIsNull");
			return jsonResult;
		}
		String mobileRule = ConfUtil.getContext("mobile.rule");

		Pattern p = Pattern.compile(mobileRule);

		Matcher m = p.matcher(mobile);
		if (!m.matches()) {
			jsonResult.setMessage("mobileFormatError");
			return jsonResult;
		}

		if (StringUtil.isBlank(yzmCode) || !yzmCode.equalsIgnoreCase(sessionCode)) {
			jsonResult.setMessage("yzmStrError");
			return jsonResult;
		}

		SecurityCode oldSecurityCode = securityCodeService.getSecurityCodeByMobile(mobile); // 获取验证码信息
		if (oldSecurityCode != null
				&& new Date().getTime() / 1000 - oldSecurityCode.getCreatedate() < Constants.SEND_SMS_MAX_TIME) {
			jsonResult.setMessage("highOperation");
			return jsonResult;
		}

		ConcurrentHashMap<String, SendCodeMaxCountBean> sendSMSCodeMaxCountMap = SendCodeMaxCount.sendSMSCodeMaxCountMap;
		SendCodeMaxCountBean sendSMSCodeMaxCountData = !sendSMSCodeMaxCountMap.containsKey(mobile) ? null
				: sendSMSCodeMaxCountMap.get(mobile);
		if (sendSMSCodeMaxCountData != null
				&& Constants.SIGNIN_SEND_CODE_MAX_COUNT_SESSION < sendSMSCodeMaxCountData.getSendCodeCount()) {
			SendCodeMaxCount.addSendSMSCodeMaxCountMap(mobile, request, response);
			jsonResult.setMessage("sendMobileCodeFail");
			return jsonResult;
		} else {
			SendCodeMaxCount.addSendSMSCodeMaxCountMap(mobile, request, response);
		}

		String randomCode = RandomCodeUtil.randStr(6); // 生成6为验证码

		SecurityCode securityCode = new SecurityCode(); // 创建验证对象
		securityCode.setMobile(mobile);
		Long createdate = new Date().getTime() / 1000; // 生成验证码时间
		securityCode.setCreatedate(createdate);
		securityCode.setSecurityCode(randomCode);
		securityCodeService.saveSecurityCode(securityCode, mobile); // 保存验证码信息

		Map<String, String> smsParams = new HashMap<String, String>(); // 创建短信动态参数集合
		smsParams.put("module", "注册");
		smsParams.put("code", randomCode);
		if (!SMSSender.getInstance().sendByTemplate(dataMapService.getSmsContentRegister(), mobile,
				"ihuyi.verification.signin.code.template", smsParams)) { // 判断短信发送是否成功
			jsonResult.setMessage("sendMobileCodeFail");
			return jsonResult;
		}

		Map<Object, Object> tokenParams = new HashMap<Object, Object>();
		tokenParams.put("token", Md5Utils.hash(randomCode + mobile));
		jsonResult.setData(tokenParams);

		return jsonResult;

	}

	@RequestMapping(value = "/regist/commodity")
	public String currencyStockSignIn(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {
		int source = Constant.RegistSource.WEB_REGIST;
		String backUrl = this.getChannelFrom(false, request, response);
		modelMap.put("backData", backUrl);
		modelMap.put("source", source);
		return ViewConstants.SignInViewJsp.CURRENCY_STOCK_SIGN_IN;
	}
}
