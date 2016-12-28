package com.tzdr.api.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jodd.util.ObjectUtil;
import jodd.util.StringUtil;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tzdr.api.constants.DataConstant;
import com.tzdr.api.constants.ResultStatusConstant;
import com.tzdr.api.support.ApiResult;
import com.tzdr.api.util.AuthUtils;
import com.tzdr.api.util.PasswordUtils;
import com.tzdr.api.util.RequestUtils;
import com.tzdr.business.api.service.ApiUserService;
import com.tzdr.business.cms.service.user.PasswordService;
import com.tzdr.business.service.datamap.DataMapService;
import com.tzdr.business.service.securityInfo.SecurityInfoService;
import com.tzdr.business.service.securitycode.SecurityCodeService;
import com.tzdr.business.service.wuser.WUserService;
import com.tzdr.common.api.ihuyi.SMSSender;
import com.tzdr.common.utils.Dates;
import com.tzdr.common.utils.RandomCodeUtil;
import com.tzdr.common.utils.ValidatorUtil;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.domain.api.vo.ApiUserVo;
import com.tzdr.domain.web.entity.SecurityCode;
import com.tzdr.domain.web.entity.UserVerified;
import com.tzdr.domain.web.entity.WUser;

/**  
 * @Title: UserSecurityController.java     
 * @Description: 账户实名认证业务信息管理类    
 * @author： WangPinQun 
 * @E-mail：wangpinqun@tzdr.com
 * @company： 上海信闳投资管理有限公司重庆分公司
 * @address：重庆市渝北区黄山大道中段70号两江新界3栋13楼
 * @date： 2016年3月22日 下午3:03:58    
 * @version： V1.0  
 */
@Controller
@RequestMapping("/user/security")
public class UserSecurityController {
	
	public static final Logger logger = LoggerFactory.getLogger(UserPayController.class);

	@Autowired
	private ApiUserService  apiUserService;
	
	@Autowired
	private DataMapService dataMapService;
	
	@Autowired
	private SecurityInfoService securityInfoService;
	
	@Autowired
	private SecurityCodeService securityCodeService;
	
	@Autowired
	private WUserService  wUserService;
	
	@Autowired
	private PasswordService  passwordService;
	
	/**
	 * 系统发送通知短信统一接口
	 * @param moblie 手机号码
	 * @param type   发送类型：
	 *   
	 *    1: 修改绑定手机原手机号验证短信
	 *    2： 提现密码短信
	 *    3： 修改绑定手机新手机号验证短信
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/send_sms",method=RequestMethod.POST)
	@ResponseBody
	public ApiResult sendSms(String mobile,int type,HttpServletRequest request) {
	
		if (!RequestUtils.isMobileNum(mobile)){
			return new ApiResult(false,ResultStatusConstant.ValidateCode.MOBILE_ERROR,"mobile.parrten.error.");
		}
		
		//查看当前手机号码 上次发送成功时间 是否在一分钟
		SecurityCode oldSecurityCode = securityCodeService.getSecurityCodeByMobile(mobile);  //获取验证码信息
		if (!ObjectUtil.equals(null,oldSecurityCode)){
			if (Dates.getCurrentLongDate()-oldSecurityCode.getCreatedate()<DataConstant.SMS_SEND_INTERVAL_TIME){
				return new ApiResult(false,ResultStatusConstant.SendSms.FREQUENT_OPERATION,"one.minute.unable.repeat.send.");
			}
		}
		ApiUserVo appUserVo = apiUserService.findByMobile(mobile);
		Map<String,String> smsParams= new HashMap<String,String>();  //创建短信动态参数集合 
		String template = "ihuyi.verification.code.template";
		
		if (DataConstant.SEND_SMS_TYPE_UPDATE_PHONE==type){ 
			if (ObjectUtil.equals(null, appUserVo)){
				return new ApiResult(false,ResultStatusConstant.SendSms.MOBILE_NOT_EXIST,"mobile.not.exist.");
			}		
			smsParams.put("module", DataConstant.SEND_SMS_TYPE_UPDATE_PHONE_MODULE);
		}
		
		if (DataConstant.SEND_SMS_TYPE_UPDATE_PHONE_NEW==type){ 
			if (!ObjectUtil.equals(null, appUserVo)){
				return new ApiResult(false,ResultStatusConstant.SendSms.MOBILE_EXIST,"mobile.is.exist.");
			}		
			smsParams.put("module", DataConstant.SEND_SMS_TYPE_UPDATE_PHONE_MODULE);
		}
		
		if (DataConstant.SEND_SMS_TYPE_FORGET_WITHDRAW_PWD==type){
			if (ObjectUtil.equals(null, appUserVo)){
				return new ApiResult(false,ResultStatusConstant.SendSms.MOBILE_NOT_EXIST,"mobile.not.exist.");
			}		
			smsParams.put("module", DataConstant.SEND_SMS_TYPE_FORGET_WITHDRAW_PWD_MODULE);
		}
		
		if (CollectionUtils.isEmpty(smsParams)){
			return new ApiResult(false,ResultStatusConstant.FAIL,"send.fail.");
		}
		
		//保存验证码信息
		String randomCode = RandomCodeUtil.randStr(6);   //生成6为验证码
		SecurityCode securityCode=new SecurityCode(mobile,Dates.getCurrentLongDate(), randomCode);
		securityCodeService.saveSecurityCode(securityCode,mobile);  //保存验证码信息
		
		smsParams.put("code", randomCode);
		// 获取短信通道
		int smsChannel = dataMapService.getSmsContentOthers();
		
		if (!SMSSender.getInstance().sendByTemplate(smsChannel, mobile, template, smsParams)) { // 判断短信发送是否成功
			return new ApiResult(false, ResultStatusConstant.FAIL, "send.fail.");
		}
		
		return new ApiResult(true,ResultStatusConstant.SUCCESS,"send.success.");
	}
	
	
	
	/**
	* @Title: validateCard    
	* @Description: 实名认证 
	* @param name  实名
	* @param card  身份证
	* @param modelMap
	* @param request
	* @param response
	* @return
	 */
	@RequestMapping(value = "/validatecard",method=RequestMethod.POST)
	@ResponseBody
	public ApiResult validateCard(String name,String card,ModelMap modelMap,HttpServletRequest request,HttpServletResponse response){
		
		String uid = AuthUtils.getCacheUser(request).getUid();  //获取用户信息
		
		if (StringUtil.isBlank(name)){  //判断实名是否为空
			return new ApiResult(false,ResultStatusConstant.ValidateCardConstant.NAME_NOT_NULL,"The name cannot be empty");
		}
		if (StringUtil.isBlank(card)){ //判断身份证号码是否为空
			return new ApiResult(false,ResultStatusConstant.ValidateCardConstant.CARD_NOT_NULL,"The card cannot be empty");
		}
		
		if(!ValidatorUtil.verifycard(card)){  //判断身份证号码格式是否正确
			return new ApiResult(false,ResultStatusConstant.ValidateCardConstant.CARD_FORMAT_ERROR,"Id card format error");
		}
		
		UserVerified usercard = securityInfoService.findByIdCard(card);  //根据填入的身份证去 用户认证资料信息数据库表中 查找
		if (!ObjectUtil.equals(null, usercard)){  //判断该身份证号码是否已被实名认证过
			return new ApiResult(false,ResultStatusConstant.ValidateCardConstant.CARD_IS_SECURITY,"The id card number has been real-name authentication");
		}
		
		UserVerified userverified = securityInfoService.findByUserId(uid);
		
		if (StringUtil.isNotBlank(userverified.getIdcard()) && StringUtil.isNotBlank(userverified.getTname())){ //判断该用户是否已经实名认证过
			return new ApiResult(false,ResultStatusConstant.ValidateCardConstant.IS_SECURITY,"You've been real-name authentication");
		}
		
		Integer validatecount = userverified.getValidatenum() == null ? 0 : userverified.getValidatenum();  //Validatenum验证次数
		if (validatecount >= DataConstant.VALIDATE_CARD_MAX_TIME){  //判断用户实名认证失败次数是否大于最高次数
			userverified.setStatus(DataConstant.Idcard.NOPASS);//验证失败
			securityInfoService.update(userverified);
			return new ApiResult(false,ResultStatusConstant.ValidateCardConstant.VALIDATE_OVER_LIMITED,"Authentication failure frequency is too high");
		}

		//开始验证
		boolean flag=securityInfoService.vilidateCard(card, name);
		if (flag){
			userverified.setIdcard(card);
			userverified.setTname(name); 
			userverified.setStatus(DataConstant.Idcard.NOCOMPLETE);//验证未上传照片
			securityInfoService.update(userverified);
			return new ApiResult(true,ResultStatusConstant.SUCCESS,"Authentication failure");
		}
		// 认证失败
		validatecount+=1;
		userverified.setValidatenum(validatecount);
		if(validatecount>=3){
			userverified.setStatus(DataConstant.Idcard.NOPASS);//验证失败
		}
		securityInfoService.update(userverified);
		return new ApiResult(false,ResultStatusConstant.ValidateCardConstant.CARD_SECURITY_FAIL,"success identity");
	}
	
	
	/**
	 * @Title:realNameAuth    
	 * @Description:  实名认证-图片上传
	 * @param response
	 * @param request
	 * @date 2016年12月14日
	 * @author gaochao
	 * @return
	 */
	@RequestMapping(value = "/realNameAuth")
	@ResponseBody
	public ApiResult realNameAuth(String idcardPath,String idcardBack,String idcardFront,ModelMap modelMap,HttpServletResponse response,HttpServletRequest request){
		String uid = AuthUtils.getCacheUser(request).getUid();  //获取用户信息
		UserVerified userverified = securityInfoService.findByUserId(uid);
		if (StringUtil.isNotBlank(userverified.getIdcardBack()) 
				&& StringUtil.isNotBlank(userverified.getIdcardFront()) 
				&& StringUtil.isNotBlank(userverified.getIdcardPath())){ //判断身份证是否以上传过
			return new ApiResult(false,ResultStatusConstant.ValidateCardConstant.IDCRAD_PHOTO_EXIST,"Idcard photo already exists");
		}
		
		if(StringUtils.isBlank(idcardPath) &&
				StringUtils.isBlank(idcardPath)&&StringUtils.isBlank(idcardPath)){
			return new ApiResult(false,ResultStatusConstant.ValidateCardConstant.IDCRAD_PHOTO_NOT_NULL,"Idcard photo is not update success");
		}
		
		userverified.setIdcardFront(idcardFront);
		userverified.setIdcardBack(idcardBack);
		userverified.setIdcardPath(idcardPath);
		userverified.setStatus(DataConstant.Idcard.UPLOADSTATUS);//审核中
		userverified.setLastSubmitVerifiedTime(new Date().getTime()/1000);
		securityInfoService.update(userverified);
		return new ApiResult(true,ResultStatusConstant.SUCCESS,"Idcard photo upload success");
		
	}
	/**
	* @Title: updatePhone    
	* @Description: 修改绑定手机
	* @param oldCode  原手机验证码
	* @param newMobile 新手机号码
	* @param newCode   新验证码
	* @param modelMap
	* @param response
	* @param request
	* @return
	 */
	@RequestMapping(value = "/upphone",method=RequestMethod.POST)
	@ResponseBody
	public ApiResult updatePhone(String oldCode,String newMobile,String newCode,ModelMap modelMap,HttpServletResponse response,HttpServletRequest request){
		
		String uid = AuthUtils.getCacheUser(request).getUid();  //获取用户编号
		
		WUser oldWuser =  wUserService.get(uid);  //获取用户信息
		
		String mobile = oldWuser.getMobile();  //获取用户手机号码
		
		if(StringUtil.isBlank(oldCode)){  //判断原手机验证码是否为空
			return new ApiResult(false,ResultStatusConstant.BindPhoneConstant.OLD_CODE_NOT_NULL,"The oldCode cannot be empty");
		}
		
		if(StringUtil.isBlank(newMobile)){ //判断新手机是否为空
			return new ApiResult(false,ResultStatusConstant.BindPhoneConstant.PHONE_NOT_NULL,"The newMobile cannot be empty");
		}
		
		if(StringUtil.isBlank(newCode)){  //判断新验证码是否为空
			return new ApiResult(false,ResultStatusConstant.BindPhoneConstant.NEW_CODE_NOT_NULL,"The newCode cannot be empty");
		}
		
		if (!RequestUtils.isMobileNum(newMobile)){  //判断新手机号码格式是否正确
			return new ApiResult(false,ResultStatusConstant.BindPhoneConstant.PHONE_FORMAT_ERROR,"The newMobile format error");
		}
	
		WUser wuser = securityInfoService.getUsesrbyMobile(newMobile); 
		if (!ObjectUtil.equals(null, wuser)){ //判断新手机号码是否已经绑定过
			return new ApiResult(false,ResultStatusConstant.BindPhoneConstant.PHONE_EXIST,"The newMobile is exist");
		}
		
		SecurityCode oldSecurityCode = securityCodeService.getSecurityCodeByMobile(mobile);  //获取原手机验证码信息
		if (ObjectUtil.equals(null,oldSecurityCode) || !StringUtil.equals(oldCode, oldSecurityCode.getSecurityCode())){  //判断原验证码是否正确
			return new ApiResult(false,ResultStatusConstant.BindPhoneConstant.OLD_CODE_ERROR,"The oldCode is error");
		}
		
		if((Dates.getCurrentLongDate()-oldSecurityCode.getCreatedate()) > DataConstant.VALIDATE_CODE_INVALID_TIME){  //判断原验证码是否过期
			//判断验证码是否失效
			return new ApiResult(false,ResultStatusConstant.BindPhoneConstant.OLD_CODE_OVER_TIME,"The oldCode is overtime");
		}
		
		SecurityCode newSecurityCode = securityCodeService.getSecurityCodeByMobile(newMobile);  //获取新手机验证码信息
		
		if (ObjectUtil.equals(null,newSecurityCode) || !StringUtil.equals(newCode, newSecurityCode.getSecurityCode())){ //判断新验证码是否正确
			return new ApiResult(false,ResultStatusConstant.BindPhoneConstant.NEW_CODE_ERROR,"The newCode is error");
		}
		
		if((Dates.getCurrentLongDate()-newSecurityCode.getCreatedate()) > DataConstant.VALIDATE_CODE_INVALID_TIME){ //判断新验证码是否失效
			//判断验证码是否失效
			return new ApiResult(false,ResultStatusConstant.BindPhoneConstant.NEW_CODE_OVER_TIME,"The newCode is overtime");
		}
		
		 //更新手机号
		this.securityInfoService.updatUserMobile(oldWuser,newMobile);
		AuthUtils.clearCacheUser(oldWuser.getId());//清除旧手机缓存
		return new ApiResult(true,ResultStatusConstant.SUCCESS,"Successful binding");
	}
	
	
	
	/**
	 * 设置/修改用户的提现密码
	 * @param drawPwdRequest
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/set_withdraw_pwd",method=RequestMethod.POST)
	@ResponseBody
	public ApiResult setWithdrawPwd(String password,String code,HttpServletResponse response,HttpServletRequest request){
		String uid = AuthUtils.getCacheUser(request).getUid();
		
		if (StringUtil.isBlank(password) || StringUtil.isBlank(code)){
			return new ApiResult(false,ResultStatusConstant.FAIL,"params.error.");
		}
		if (!PasswordUtils.validatePwd(password)){
			return new ApiResult(false,ResultStatusConstant.SetWithDrawPwd.PASSWORD_PATTERN_ERROR,"password.pattern.error.");
		}
		
		SecurityCode securityCode = securityCodeService.getSecurityCodeByMobile(AuthUtils.getCacheUser(request).getMobile());  //获取验证码信息
		if (ObjectUtil.equals(null,securityCode) 
				|| !StringUtil.equals(code, securityCode.getSecurityCode())){
			return new ApiResult(false,ResultStatusConstant.SetWithDrawPwd.VALIDATE_CODE_ERROR,"validate.code.error");
		}
		
		UserVerified userverified=securityInfoService.findByUserId(uid);
		
		WUser user = securityInfoService.getUsesrbyId(uid);
		if (ObjectUtil.equals(null, user) || ObjectUtil.equals(null, userverified)){
			return new ApiResult(false,ResultStatusConstant.SetWithDrawPwd.USER_INFO_NOT_EXIST,"user.info.not.exist.");
		}
		
		//查询是否和登陆密码相同
		String drawpwd=passwordService.encryptPassword(password, user.getLoginSalt());
		if (StringUtil.equals(drawpwd,user.getPassword())){
			return new ApiResult(false,ResultStatusConstant.SetWithDrawPwd.SAME_WITH_LOGIN_PASSWORD,"same.with.login.password.");
		}
		
		this.securityInfoService.updatUserMoneyPwd(password,user,userverified);
		return new ApiResult(true,ResultStatusConstant.SUCCESS,"setting.success.");
	}
	
	/**
	 * 发送邮件验证码
	 * @param response
	 * @param request
	 * @return
	 * @date 2014年12月25日
	 * @author zhangjun
	 */
	/**
	 * @param email
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/sendEmailCode")
	@ResponseBody
	public ApiResult sendEmailCode(HttpServletResponse response,HttpServletRequest request){
		String uid = AuthUtils.getCacheUser(request).getUid();
		UserVerified userverified=securityInfoService.findByUserId(uid);
		WUser user = securityInfoService.getUsesrbyId(uid);
		long codestart=new Date().getTime();
		String randomStr=RandomCodeUtil.randStr(6);
		userverified.setValidateEmailTime(codestart);
		userverified.setEmailActivecode(randomStr);
		String email = request.getParameter("email");
		//如果从后台传入email则表示注册，否则从user表里拿数据表示修改邮箱
		if(StringUtil.isNotBlank(email)){
			WUser wuser=securityInfoService.getUserByEmail(email);
			if(wuser!=null){
				return new ApiResult(false,ResultStatusConstant.Email.EMAIL_YET_REGISTER,"Email has been registered");
			}
			userverified.setValidateemail(email);
		}else{
			email=user.getEmail();
		}
		
		boolean flag=securityInfoService.sendEmail(userverified.getTname(),email, randomStr, "维胜");
		securityInfoService.update(userverified);
		if(flag)
			return new ApiResult(true,ResultStatusConstant.SUCCESS,"Email code send success");
		else
			return new ApiResult(false,ResultStatusConstant.FAIL,"Email code send fail");
	}
	/**
	 * 验证修改邮箱时验证码是否正确
	 * @param response
	 * @param request
	 * @return
	 * @date 2014年12月27日
	 * @author zhangjun
	 */
	@RequestMapping(value = "/checkEmailCode")
	@ResponseBody
	public ApiResult checkEmailCode(HttpServletResponse response,HttpServletRequest request){
		String uid = AuthUtils.getCacheUser(request).getUid();
		UserVerified userverified=securityInfoService.findByUserId(uid);
		
		String code=request.getParameter("emailcode");
		
		String emailcode=userverified.getEmailActivecode();
		
		long nowtime=new Date().getTime();
		long vtime=userverified.getValidateEmailTime();
		long minit=nowtime-vtime;
		
		if(minit>300000){
			return new ApiResult(false,ResultStatusConstant.Email.CODE_TIME_OUT,"验证码超时");
		}else{
			if(code.equals(emailcode)){
				return new ApiResult(true,ResultStatusConstant.SUCCESS,"Email bind success");
			}else{
				return new ApiResult(false,ResultStatusConstant.Email.CODE_ERROR,"验证码错误");
		    }
	    }
	}
	/**
	 * 绑定/修改邮箱
	 * @param result
	 * @param request
	 * @return
	 * @date 2014年12月23日
	 * @author zhangjun
	 */
	@RequestMapping(value = "/bindingEmail")
	@ResponseBody
	public ApiResult bindingEmail(@RequestParam("email")String email,@RequestParam("code")String code,HttpServletResponse response,HttpServletRequest request){
		String uid = AuthUtils.getCacheUser(request).getUid();
		WUser user = securityInfoService.getUsesrbyId(uid);
		UserVerified userverified=securityInfoService.findByUserId(uid);
		
		WUser wuser=securityInfoService.getUserByEmail(email);
		if(wuser!=null){
			return new ApiResult(false,ResultStatusConstant.Email.EMAIL_YET_REGISTER,"邮箱也被注册");
		}
		if(!email.equals(userverified.getValidateemail())){
			return new ApiResult(false,ResultStatusConstant.Email.TWO_EMAIL_EQUALLY,"修改密码和原密码一样");
		}
		
		String emailcode=userverified.getEmailActivecode();
		long nowtime=new Date().getTime();
		long vtime=userverified.getValidateEmailTime();
		long difftime=nowtime-vtime;
		if(difftime>24*60*60000){
			return new ApiResult(false,ResultStatusConstant.Email.CODE_TIME_OUT,"验证码超时");
		}else{
			if(code.equals(emailcode)){
				 //绑定、更新邮箱
				securityInfoService.updateEmail(user,email);
				return new ApiResult(true,ResultStatusConstant.SUCCESS,"Email bind success");
			}else{
				return new ApiResult(false,ResultStatusConstant.Email.CODE_ERROR,"验证码错误");
			}
		}
	}
}
