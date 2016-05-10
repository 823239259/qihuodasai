package com.tzdr.web.controller.securityInfo;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jodd.util.StringUtil;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tzdr.business.cms.service.user.PasswordService;
import com.tzdr.business.service.drawMoney.UserBankService;
import com.tzdr.business.service.securityInfo.SecurityInfoService;
import com.tzdr.common.utils.RandomCodeUtil;
import com.tzdr.common.utils.StringCodeUtils;
import com.tzdr.common.utils.ValidatorUtil;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.domain.constants.Constant;
import com.tzdr.domain.web.entity.UserBank;
import com.tzdr.domain.web.entity.UserVerified;
import com.tzdr.domain.web.entity.WUser;
import com.tzdr.web.constants.Constants;
import com.tzdr.web.constants.ViewConstants;
import com.tzdr.web.utils.SendCodeMaxCount;
import com.tzdr.web.utils.SendCodeMaxCountBean;
import com.tzdr.web.utils.UserSessionBean;


/**
 * 安全信息controller
 * <P>title:@SecurityController.java</p>																								
 * <P>Description：</p>
 * <p>Copyright: Copyright (c) 2014 tzdr</p>
 * <p>Company: 上海信闳</p>
 * History：
 * @author:zhangjun
 * @date 2014年12月23日
 * @version 1.0
 */
@Controller
@RequestMapping("/securityInfo")
public class SecurityInfoController{

	private static Logger log = LoggerFactory.getLogger(SecurityInfoController.class);
	@Autowired
	private SecurityInfoService securityInfoService;
	@Autowired
	private UserBankService  userBankService;
	@Autowired
	private PasswordService passwordService;
	/**
	 * 到安全信息首页
	 * @param request
	 * @return
	 * @throws Exception
	 * @date 2014年12月25日
	 * @author zhangjun
	 */
	@RequestMapping(value = "/secInfo")
	public String secInfo(HttpServletRequest request) throws Exception {
		UserSessionBean userSessionBean=(UserSessionBean) request.getSession().getAttribute(Constants.TZDR_USER_SESSION);
		//重新查询用户信息，因为涉及到修改了手机号和邮箱
		WUser user=this.securityInfoService.getUsesrbyId(userSessionBean.getId());
		List<UserBank> banks=userBankService.findUserDefaultBankByuserId(userSessionBean.getId());
		if(banks!=null && banks.size()>0){
			UserBank bank=banks.get(0);
			request.setAttribute("bank", bank.getCard().substring(0, 4)+"********"+ bank.getCard().substring(bank.getCard().length()-4,bank.getCard().length()));
		}
		
		String email=user.getEmail();
		if(StringUtil.isNotBlank(email)){
			email=securityInfoService.buildEmail(email);
			request.setAttribute("email", email);
		}
		
		UserVerified userverified=this.securityInfoService.findByUserId(user.getId());
		if(userverified==null){
			userverified=new UserVerified();
			userverified.setWuser(user);
			securityInfoService.save(userverified);
		}
		//构建身份证信息用*号表示
		//userverified=securityInfoService.buildUserInfo(userverified);
		if(userverified!=null && StringUtil.isNotBlank(userverified.getIdcard())){
			String cardstr=StringCodeUtils.buildIdCard(userverified.getIdcard());
			cardstr=cardstr.substring(0,2)+"**********"+cardstr.substring(cardstr.length()-2,cardstr.length());
			request.setAttribute("idcard", cardstr);
		}
		request.setAttribute("user", user);
		request.setAttribute("mobile", StringCodeUtils.buildMobile(user.getMobile()));
		request.setAttribute("userverified", userverified);
		return ViewConstants.SecurityViewJsp.SECURITY_MAIN_VIEW;
	}
	

	
	/**
	 * 验证身份证
	 * @return
	 * @date 2014年12月24日
	 * @author zhangjun
	 */
	@RequestMapping(value = "/validateCard")
	@ResponseBody
	public JsonResult validateCard(HttpServletRequest request,HttpServletResponse response){
		String name=request.getParameter("name");
		String cardNo=request.getParameter("card");
		UserSessionBean userSessionBean=(UserSessionBean) request.getSession().getAttribute(Constants.TZDR_USER_SESSION);
		UserVerified userverified=securityInfoService.findByUserId(userSessionBean.getId());
		UserVerified usercard=securityInfoService.findByIdCard(cardNo);
		JsonResult jsonResult = new JsonResult(false);
		jsonResult.setSuccess(true);
		if(usercard!=null){
			jsonResult.setMessage("exsit");
			return jsonResult;
		}else if(!ValidatorUtil.verifycard(cardNo)){
			jsonResult.setMessage("false");
			return jsonResult;
		}else if(StringUtil.isNotBlank(userverified.getIdcard()) || StringUtil.isNotBlank(userverified.getTname())){
			jsonResult.setMessage("haveRealName");
			return jsonResult;
		}
		
		Integer validatecount=userverified.getValidatenum()==null?0:userverified.getValidatenum();
		 validatecount=validatecount==null?0:validatecount;
		if(userverified!=null &&  validatecount<3){
			//开始验证
			boolean flag=securityInfoService.vilidateCard(cardNo, name);
			if(flag==false){
				validatecount+=1;
				userverified.setValidatenum(validatecount);
				if(validatecount>=3){
					userverified.setStatus(Constants.Idcard.NOPASS);//验证失败
				}
				securityInfoService.update(userverified);
				jsonResult.setMessage("false");
				return jsonResult;
			}else{
				//身份证号码用星号表示
				//String card=securityInfoService.buildStr(cardNo, 2, 4, 2);
				userverified.setIdcard(cardNo);
				userverified.setTname(name);
				userverified.setStatus(Constants.Idcard.NOCOMPLETE);//验证未上传照片
				securityInfoService.update(userverified);
				jsonResult.setMessage("success");
			}
		}else{
			if(userverified!=null){
			userverified.setStatus(Constants.Idcard.NOPASS);//验证失败
			securityInfoService.update(userverified);
			jsonResult.setMessage("maxnum");
			}
		}
		return jsonResult;
	}
	
	

	/**
	 * 实名认证,图片上传
	 * @param result
	 * @param request
	 * @return
	 * @date 2014年12月23日
	 * @author zhangjun
	 */
	@RequestMapping(value = "/realNameAuth")
	@ResponseBody
	public JsonResult realNameAuth(HttpServletResponse response,HttpServletRequest request){
		UserSessionBean userSessionBean=(UserSessionBean) request.getSession().getAttribute(Constants.TZDR_USER_SESSION);
		UserVerified userverified=securityInfoService.findByUserId(userSessionBean.getId());
		String idcardPath=request.getParameter("idcardPath");
		String idcardBack=request.getParameter("idcardBack");
		String idcardFront=request.getParameter("idcardFront");
		JsonResult jsonResult = new JsonResult(false);
		jsonResult.setSuccess(true);
		if(StringUtils.isNotBlank(idcardPath) &&
				StringUtils.isNotBlank(idcardPath)&&StringUtils.isNotBlank(idcardPath)){
			userverified.setIdcardFront(idcardFront);
			userverified.setIdcardBack(idcardBack);
			userverified.setIdcardPath(idcardPath);
			userverified.setStatus(Constants.Idcard.UPLOADSTATUS);//审核中
			userverified.setLastSubmitVerifiedTime(new Date().getTime()/1000);
			securityInfoService.update(userverified);
			//String card=securityInfoService.buildStr(userverified.getIdcard(), 2, 4, 2);
			jsonResult.setMessage("success");
		}
		return jsonResult;
	}
	
	
	/**
	 * 修改邮箱时候检查验证码是否正确
	 * @param response
	 * @param request
	 * @return
	 * @date 2014年12月27日
	 * @author zhangjun
	 */
	@RequestMapping(value = "/checkEmailCode")
	@ResponseBody
	public JsonResult checkEmailCode(HttpServletResponse response,HttpServletRequest request){
		UserSessionBean userSessionBean=(UserSessionBean) request.getSession().getAttribute(Constants.TZDR_USER_SESSION);
		UserVerified userverified=securityInfoService.findByUserId(userSessionBean.getId());
		String code=request.getParameter("emailcode");
		String emailcode=userverified.getEmailActivecode();
		long nowtime=new Date().getTime();
		long vtime=userverified.getValidateEmailTime();
		long minit=nowtime-vtime;
		JsonResult jsonResult = new JsonResult(false);
		jsonResult.setSuccess(true);
		 if(minit>300000){
			 jsonResult.setMessage("timeout");
		 }else{
			 if(code.equals(emailcode)){
				 jsonResult.setMessage("success");
			 }else{
				 jsonResult.setMessage("diffcode");
			 }
		 }
		return jsonResult;
	}
	
	/**
	 * 发送手机验证码
	 * @param response
	 * @param request
	 * @return
	 * @date 2014年12月25日
	 * @author zhangjun
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/sendPhoneCode")
	@ResponseBody
	public JsonResult sendPhoneCode(HttpServletResponse response,HttpServletRequest request){
		UserSessionBean userSessionBean=(UserSessionBean) request.getSession().getAttribute(Constants.TZDR_USER_SESSION);
		WUser user=this.securityInfoService.getUsesrbyId(userSessionBean.getId());
		UserVerified userverified=securityInfoService.findByUserId(user.getId());
		String mobile=request.getParameter("mobile");
		String type=request.getParameter("type");
		String yzmCode = request.getParameter("yzmCode");
		String title="";
		JsonResult jsonResult = new JsonResult(false);
		jsonResult.setSuccess(true);
		SendCodeMaxCountBean  sendCodeMaxCountData = null;
		String sessionCode = null;
		
		if(StringUtil.isNotBlank(mobile)){
			WUser wuser=securityInfoService.getUsesrbyMobile(mobile);
			if(wuser!=null){
				jsonResult.setMessage("exsit");
				return jsonResult;
			}
			Object obj =  request.getServletContext().getAttribute(Constants.SIGNIN_SEND_CODE_MAX_COUNT_SESSION_KEY);
			ConcurrentHashMap<String, SendCodeMaxCountBean> sendCodeMaxCountMap =  (ConcurrentHashMap<String, SendCodeMaxCountBean>) (obj == null ? null : obj);
			sendCodeMaxCountData = sendCodeMaxCountMap == null || sendCodeMaxCountMap.isEmpty() || !sendCodeMaxCountMap.containsKey(mobile) ? null : sendCodeMaxCountMap.get(mobile);
			sessionCode = (String)request.getSession().getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
			if(sendCodeMaxCountData != null && Constants.SIGNIN_SEND_CODE_MAX_COUNT_SESSION < sendCodeMaxCountData.getSendCodeCount() && (StringUtil.isBlank(yzmCode) || !yzmCode.equalsIgnoreCase(sessionCode))){
				sendCodeMaxCountData.setSendCodeCount(sendCodeMaxCountData.getSendCodeCount() + 1);
				Map<Object, Object> data = new HashMap<Object, Object>();
				data.put("isNeedSendCode", true);
				SendCodeMaxCount.addSendCodeMaxCountBean(sendCodeMaxCountData, Constants.SIGNIN_SEND_CODE_MAX_COUNT_SESSION_KEY, request, response);   //修改单个手机号码发送短信次数
				jsonResult.setData(data);
				jsonResult.setMessage("yzmStrError");
				return jsonResult;
			}
		}else{
			mobile=user.getMobile();
		}
		if("phone".equals(type)){
			title="修改绑定手机 ";
		}else{
			title="忘记提现密码";
		}
		
		Long codestart=new Date().getTime();
		String randomStr=RandomCodeUtil.randStr(6);
		userverified.setValidatePhoneTime(codestart);
		userverified.setPhonecode(randomStr);
		securityInfoService.sendSms(mobile,"安全信息-"+title,randomStr,Constant.Source.TZDR);
		securityInfoService.update(userverified);
		if(StringUtil.isNotBlank(request.getParameter("mobile"))){
			if(sendCodeMaxCountData != null && !StringUtil.isBlank(yzmCode) && yzmCode.equalsIgnoreCase(sessionCode)){
				log.info("用户手机号码："+user.getMobile()+",新号码："+mobile);
				SendCodeMaxCount.removeSendCodeMaxCountBean(sendCodeMaxCountData, Constants.SIGNIN_SEND_CODE_MAX_COUNT_SESSION_KEY, request, response);    //删除application单手机号码短信下发次数(包含失效数据)
			}else{
				if(sendCodeMaxCountData == null){  //存储下发短信手机号码信息
					sendCodeMaxCountData = new SendCodeMaxCountBean();
					sendCodeMaxCountData.setSendCodeCount(1);
					sendCodeMaxCountData.setUserName(mobile);
					sendCodeMaxCountData.setValidDate(new Date());
				}else{
					sendCodeMaxCountData.setSendCodeCount(sendCodeMaxCountData.getSendCodeCount() + 1);
				}
				SendCodeMaxCount.addSendCodeMaxCountBean(sendCodeMaxCountData, Constants.SIGNIN_SEND_CODE_MAX_COUNT_SESSION_KEY, request, response);   //修改单个手机号码发送短信次数
			}
		}
		
		jsonResult.setMessage("success");
		return jsonResult;
	}
	
	/**
	 * 到手机绑定修改下一步验证
	 * @param response
	 * @param request
	 * @return
	 * @date 2014年12月25日
	 * @author zhangjun
	 */
	@RequestMapping(value = "/checkBingphone")
	@ResponseBody
	public JsonResult checkBingphone(HttpServletResponse response,HttpServletRequest request){
		UserSessionBean userSessionBean=(UserSessionBean) request.getSession().getAttribute(Constants.TZDR_USER_SESSION);
		JsonResult jsonResult = new JsonResult(false);
		jsonResult.setSuccess(true);
		UserVerified userverified=securityInfoService.findByUserId(userSessionBean.getId());
		String code=request.getParameter("code");
		String phonecode=userverified.getPhonecode();
		long nowtime=new Date().getTime();
		long vtime=userverified.getValidatePhoneTime();
		long difftime=nowtime-vtime;
		 if(difftime>300000){
			 jsonResult.setMessage("timeout");
		 }else{
			 if(code.equals(phonecode)){
				 jsonResult.setMessage("success");
			 }else{
				 jsonResult.setMessage("diffcode");
			 }
		 }
		return jsonResult;
	}
	
	/**
	 * 更新手机号
	 * @param response
	 * @param request
	 * @return
	 * @date 2014年12月25日
	 * @author zhangjun
	 */
	@RequestMapping(value = "/updatePhone")
	@ResponseBody
	public JsonResult updatePhone(HttpServletResponse response,HttpServletRequest request){
		UserSessionBean userSessionBean=(UserSessionBean) request.getSession().getAttribute(Constants.TZDR_USER_SESSION);
		WUser user = securityInfoService.getUsesrbyId(userSessionBean.getId());
		UserVerified userverified=securityInfoService.findByUserId(userSessionBean.getId());
		String newmobile=request.getParameter("newmobile");
		String code=request.getParameter("mobilecode");
		String phonecode=userverified.getPhonecode();
		JsonResult jsonResult = new JsonResult(false);
		jsonResult.setSuccess(true);
		long nowtime=new Date().getTime();
		long vtime=userverified.getValidatePhoneTime();
		long minit=(nowtime-vtime)/60000;
		if(StringUtil.isNotBlank(newmobile)){
			WUser wuser=securityInfoService.getUsesrbyMobile(newmobile);
			if(wuser!=null){
				jsonResult.setMessage("exsit");
				return jsonResult;
			}
		}
		 if(minit>5){
			 jsonResult.setMessage("timeout");
		 }else{
			 if(code.equals(phonecode)){
				 //更新手机号
				 user.setMobile(newmobile);
				 this.securityInfoService.updatUserMobile(user,newmobile);
				 userSessionBean.setMobile(newmobile);
				 String userName = StringCodeUtils.buildMobile(newmobile);
				 //重新设置session
				 request.getSession().setAttribute(com.tzdr.web.constants.Constants.TZDR_USER_SESSION,userSessionBean);
				 request.getSession().setAttribute(com.tzdr.web.constants.Constants.TZDR_USERNAME_SESSION,userName);

				 jsonResult.setMessage("success");
			 }else{
				 jsonResult.setMessage("diffcode");
			 }
		 }
		return jsonResult;
	}

	/**
	 * 到修改邮箱绑定页面
	 * @param result
	 * @param request
	 * @return
	 * @date 2014年12月25日
	 * @author zhangjun
	 */
	@RequestMapping(value = "/toBindingEmail")
	@ResponseBody
	public JsonResult toBindingEmail(HttpServletResponse response,HttpServletRequest request){
		UserVerified userverified=new UserVerified();
		UserSessionBean userSessionBean=(UserSessionBean) request.getSession().getAttribute(Constants.TZDR_USER_SESSION);
		JsonResult jsonResult = new JsonResult(false);
		jsonResult.setSuccess(true);
		long codestart=new Date().getTime();
		userverified.setValidatePhoneTime(codestart);
		String randomStr=RandomCodeUtil.randStr(6);
		userverified.setEmailActivecode(randomStr);
		userverified.setValidateEmailTime(codestart);
		String email=userSessionBean.getEmail();
		boolean flag=securityInfoService.sendEmail(userverified.getTname(),email, randomStr, "投资达人");
		if(flag){
			jsonResult.setMessage("success");
		}
		return jsonResult;//ViewConstants.SecurityViewJsp.SECURITY_BINDING_EMAIL;
		
	}
	
	
	/**
	 * 验证绑定的邮箱
	 * @param result
	 * @param request
	 * @return
	 * @date 2014年12月23日
	 * @author zhangjun
	 */
	@RequestMapping(value = "/bindingEmail")
	@ResponseBody
	public JsonResult bindingEmail(HttpServletResponse response,HttpServletRequest request){
		UserSessionBean userSessionBean=(UserSessionBean) request.getSession().getAttribute(Constants.TZDR_USER_SESSION);
		WUser user = securityInfoService.getUsesrbyId(userSessionBean.getId());
		UserVerified userverified=securityInfoService.findByUserId(userSessionBean.getId());
		String email=request.getParameter("email");
		JsonResult jsonResult = new JsonResult(false);
		jsonResult.setSuccess(true);
		WUser wuser=securityInfoService.getUserByEmail(email);
		if(wuser!=null){
			jsonResult.setMessage("exsit");
			return jsonResult;
		}
		if(!email.equals(userverified.getValidateemail())){
			jsonResult.setMessage("emailerror");
			return jsonResult;
		}
		
		String code=request.getParameter("emailcode");
		String emailcode=userverified.getEmailActivecode();
		long nowtime=new Date().getTime();
		long vtime=userverified.getValidateEmailTime();
		long difftime=nowtime-vtime;
		 if(difftime>24*60*60000){
			 jsonResult.setMessage("timeout");
		 }else{
			 if(code.equals(emailcode)){
				 //更新邮箱
				 securityInfoService.updateEmail(user,email);
				 userSessionBean.setEmail(email);
				 //重新设置session
				 request.getSession().setAttribute(com.tzdr.web.constants.Constants.TZDR_USER_SESSION,userSessionBean);
				
			 }else{
				 jsonResult.setMessage("diffcode");
			 }
		 }
		return jsonResult;
	}

	
	/**
	 * 发送邮件验证码
	 * @param response
	 * @param request
	 * @return
	 * @date 2014年12月25日
	 * @author zhangjun
	 */
	@RequestMapping(value = "/sendEmailCode")
	@ResponseBody
	public JsonResult sendEmailCode(HttpServletResponse response,HttpServletRequest request){
		UserSessionBean userSessionBean=(UserSessionBean) request.getSession().getAttribute(Constants.TZDR_USER_SESSION);
		UserVerified userverified=securityInfoService.findByUserId(userSessionBean.getId());
		JsonResult jsonResult = new JsonResult(false);
		jsonResult.setSuccess(true);
		String email=request.getParameter("email");
		long codestart=new Date().getTime();
		String randomStr=RandomCodeUtil.randStr(6);
		userverified.setValidateEmailTime(codestart);
		userverified.setEmailActivecode(randomStr);
		if(StringUtil.isNotBlank(email)){
			WUser wuser=securityInfoService.getUserByEmail(email);
			if(wuser!=null){
				jsonResult.setMessage("exsit");
				return jsonResult;
			}
			userverified.setValidateemail(email);
		}else{
			WUser wuser=securityInfoService.getUsesrbyMobile(userSessionBean.getMobile());
			email=wuser.getEmail();
		}
			
		boolean flag=securityInfoService.sendEmail(userverified.getTname(),email, randomStr, "投资达人");
		//securityInfoService.sendSms("18022301330","安全信息修改绑定手机",randomStr);
		securityInfoService.update(userverified);
		if(flag)
			jsonResult.setMessage("success");
		return jsonResult;
	}


	/**
	 * 设置取现密码
	 * @param response
	 * @param request
	 * @return
	 * @date 2014年12月25日
	 * @author zhangjun
	 */
	@RequestMapping(value = "/updateMoneyPwd")
	@ResponseBody
	public JsonResult updateMoneyPwd(HttpServletResponse response,HttpServletRequest request){
		UserSessionBean userSessionBean=(UserSessionBean) request.getSession().getAttribute(Constants.TZDR_USER_SESSION);
		UserVerified userverified=securityInfoService.findByUserId(userSessionBean.getId());
		WUser user = this.securityInfoService.getUsesrbyId(userSessionBean.getId());
		String password=request.getParameter("moneypassword");
		String againNewpassword=request.getParameter("aginmoneypassword");
		JsonResult jsonResult = new JsonResult(false);
		jsonResult.setSuccess(true);
		if(StringUtil.isNotBlank(password) && StringUtil.isNotBlank(againNewpassword) ){
			if(!password.equals(againNewpassword))
				jsonResult.setMessage("diffpwd");
			else{
				//查询是否和登陆密码相同
				String drawpwd=passwordService.encryptPassword(password, user.getLoginSalt());
				user=this.securityInfoService.getUsesrbyId(user.getId());
				if(user.getPassword().equals(drawpwd)){
					jsonResult.setMessage("loginpwd");
				}else{
					//修改
					this.securityInfoService.updatUserMoneyPwd(password,user,userverified);
					jsonResult.setMessage("success");
				}
			}
		}
		return jsonResult;
	}
	
	/**
	 * 忘记提现密码
	 * @param response
	 * @param request
	 * @return
	 * @date 2015年1月20日
	 * @author zhangjun
	 */
	@RequestMapping(value = "/toForgetdrawPwd")
	public String  toForgetdrawPwd(ModelMap modelMap, HttpServletResponse response,HttpServletRequest request){
		UserSessionBean userSessionBean=(UserSessionBean) request.getSession().getAttribute(Constants.TZDR_USER_SESSION);
		WUser user=this.securityInfoService.getUsesrbyId(userSessionBean.getId());
		UserVerified userverified=securityInfoService.findByUserId(user.getId());
		modelMap.put("userverified", userverified);
		String mobile=StringCodeUtils.buildMobile(user.getMobile());
		modelMap.put("user", user);
		modelMap.put("mobile", mobile);
		modelMap.put("userverified", userverified);
		return ViewConstants.SecurityViewJsp.FORGET_DRAW_PWD;
	}
	
	/**
	 * 重新设置提现密码
	 * @param response
	 * @param request
	 * @return
	 * @date 2014年12月29日
	 * @author zhangjun
	 */
	@RequestMapping(value = "/resetMoneyPwd")
	@ResponseBody
	public JsonResult resetMoneyPwd(HttpServletResponse response,HttpServletRequest request){
		UserSessionBean userSessionBean=(UserSessionBean) request.getSession().getAttribute(Constants.TZDR_USER_SESSION);
		WUser user = securityInfoService.getUsesrbyId(userSessionBean.getId());
		UserVerified userverified=securityInfoService.findByUserId(userSessionBean.getId());
		String olddrawmoneypwd=request.getParameter("olddrawmoneypwd");
		String password=request.getParameter("moneypassword");
		String againNewpassword=request.getParameter("aginmoneypassword");
		JsonResult jsonResult = new JsonResult(false);
		jsonResult.setSuccess(true);
		if(StringUtil.isNotBlank(olddrawmoneypwd)){
			String moneypwd=this.securityInfoService.getBuildPwd(olddrawmoneypwd, user);
			if(!moneypwd.equals(userverified.getDrawMoneyPwd())){
				jsonResult.setMessage("diffpwd");
			}else {
				if(StringUtil.isNotBlank(password) && StringUtil.isNotBlank(againNewpassword)){
					if(!password.equals(againNewpassword))
						jsonResult.setMessage("diffaginpwd");
					else{//查询是否和登陆密码相同
						String drawpwd=passwordService.encryptPassword(password, user.getLoginSalt());
						user=this.securityInfoService.getUsesrbyId(user.getId());
						if(user.getPassword().equals(drawpwd)){
							jsonResult.setMessage("loginpwd");
						}else{
							//修改
							this.securityInfoService.updatUserMoneyPwd(password,user,userverified);
							jsonResult.setMessage("success");
						}
					}
				}
			}
		}
		return jsonResult;
	}
	/**
	 * 修改密码
	 * @param response
	 * @param request
	 * @return
	 * @date 2014年12月25日
	 * @author zhangjun
	 */
	@RequestMapping(value = "/updatePwd")
	@ResponseBody
	public JsonResult updatePwd(HttpServletResponse response,HttpServletRequest request){
		UserSessionBean userSessionBean=(UserSessionBean) request.getSession().getAttribute(Constants.TZDR_USER_SESSION);
		WUser user = this.securityInfoService.getUsesrbyId(userSessionBean.getId());
		String password=request.getParameter("password");
		String newpassword=request.getParameter("newpassword");
		String againNewpassword=request.getParameter("againNewpassword");
		JsonResult jsonResult = new JsonResult(false);
		jsonResult.setSuccess(true);
		//session里面没有存如密码
		WUser wuser=this.securityInfoService.getUsesrbyMobile(user.getMobile());
		if(wuser!=null){
			String userpwd=wuser.getPassword();
			String pwd=securityInfoService.getBuildPwd(password,wuser);
			if(!pwd.equals(userpwd)){
				jsonResult.setMessage("diffpwd");
			}else{
				String newpwd=passwordService.encryptPassword(newpassword, user.getLoginSalt());
				UserVerified userverified=securityInfoService.findByUserId(user.getId());
				if(StringUtil.isNotBlank(userverified.getDrawMoneyPwd())){
					if(newpwd.equals(userverified.getDrawMoneyPwd())){
						jsonResult.setMessage("samepwd");
						return jsonResult;
					}
				}
				if(!newpassword.equals(againNewpassword)){
					jsonResult.setMessage("diffagainpwd");
				}else{
					securityInfoService.updatUserPwd(wuser,newpassword);
					jsonResult.setMessage("true");
				}
			}
		}
		
		return jsonResult;
	}
	
	
}
