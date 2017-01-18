package com.tzdr.api.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jodd.util.ObjectUtil;
import jodd.util.StringUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tzdr.api.constants.DataConstant;
import com.tzdr.api.constants.ResultStatusConstant;
import com.tzdr.api.support.ApiResult;
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
import com.tzdr.common.utils.IpUtils;
import com.tzdr.common.utils.RandomCodeUtil;
import com.tzdr.domain.api.vo.ApiUserVo;
import com.tzdr.domain.web.entity.SecurityCode;

/**
 * 免认证的短信接口
 * @author zhouchen
 * 2016年3月22日 下午4:38:01
 */
@Controller
@RequestMapping(value = "/")
public class SmsController {
	private static Logger log = LoggerFactory.getLogger(SmsController.class);

	@Autowired
	private ApiUserService  apiUserService;
	
	@Autowired
	private SecurityCodeService securityCodeService;
	
	@Autowired
	private WUserService  wUserService;
	
	@Autowired
	private PasswordService  passwordService;
	
	@Autowired
	private SecurityInfoService securityInfoService;
	
	@Autowired
	private DataMapService dataMapService;
	
	/**
	 * 注册、忘记密码 系统发送通知短信统一接口
	 * @param moblie 手机号码
	 * @param type   发送类型：
	 *    1：注册验证码短信
	 *    2：忘记密码短信
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/sms",method=RequestMethod.POST)
	@ResponseBody
	public ApiResult sendSms(String mobile,int type,HttpServletRequest request,HttpServletResponse response) {
		if (!RequestUtils.isMobileNum(mobile)){
			return new ApiResult(false,ResultStatusConstant.FAIL,"mobile.parrten.error.");
		}
		
		//查看当前手机号码 上次发送成功时间 是否在一分钟
		SecurityCode oldSecurityCode = securityCodeService.getSecurityCodeByMobile(mobile);  //获取验证码信息
		if (!ObjectUtil.equals(null,oldSecurityCode)){
			if (Dates.getCurrentLongDate()-oldSecurityCode.getCreatedate()<DataConstant.SMS_SEND_INTERVAL_TIME){
				return new ApiResult(false,ResultStatusConstant.SendSms.FREQUENT_OPERATION,"one.minute.unable.repeat.send.");
			}
		}
				
		// 短信发送次数
		int sendTimes = ObjectUtil.equals(null,DataConstant.SMS_LIMIT_MAPS.get(mobile))?DataConstant.ZERO:DataConstant.SMS_LIMIT_MAPS.get(mobile);
		ApiUserVo appUserVo = apiUserService.findByMobile(mobile);
		Map<String,String> smsParams= new HashMap<String,String>();  //创建短信动态参数集合 
		String template = "ihuyi.verification.code.template";
		// 获取短信通道
		int smsChannel = 0;
		if (DataConstant.SEND_SMS_TYPE_REGIST==type){//注册验证码短信
			if (!ObjectUtil.equals(null, appUserVo)){
				return new ApiResult(false,ResultStatusConstant.SendSms.MOBILE_EXIST,"mobile.exist.");
			}
			if (sendTimes>=DataConstant.SMS_LIMIT_NUMBER){
				return new ApiResult(false,ResultStatusConstant.FAIL,"over.max.limit.number.");
			}
			
			// 获取短信通道
			smsChannel = ((DataConstant.SEND_SMS_TYPE_REGIST == type) ? dataMapService.getSmsContentRegister() : dataMapService.getSmsContentOthers());
			
			template = smsChannel == 3 ? "tzdr.alidayu.signin.code.template" : "ihuyi.verification.signin.code.template";
			if(smsChannel == 3){
				smsParams.put("typeName",DataConstant.SEND_SMS_TYPE_REGIST_MODULE);
			}else{
				smsParams.put("module",DataConstant.SEND_SMS_TYPE_REGIST_MODULE);
			}
			DataConstant.SMS_LIMIT_MAPS.put(mobile,sendTimes+DataConstant.ONE);
		}
		

		if (DataConstant.SEND_SMS_TYPE_FORGET_PWD==type){//忘记密码验证码短信
			if (ObjectUtil.equals(null, appUserVo)){
				return new ApiResult(false,ResultStatusConstant.SendSms.MOBILE_NOT_EXIST,"mobile.not.exist.");
			}	
			// 获取短信通道
			smsChannel = ((DataConstant.SEND_SMS_TYPE_REGIST == type) ? dataMapService.getSmsContentRegister() : dataMapService.getSmsContentOthers());
			if(smsChannel == 3){
				smsParams.put("typeName",DataConstant.SEND_SMS_TYPE_REGIST_MODULE);
			}else{
				smsParams.put("module", DataConstant.SEND_SMS_TYPE_FORGET_PWD_MODULE);
			}
		}
		
		if (CollectionUtils.isEmpty(smsParams)){
			return new ApiResult(false,ResultStatusConstant.FAIL,"send.fail.");
		}
		
		//保存验证码信息
		String randomCode = RandomCodeUtil.randStr(6);   //生成6为验证码
		SecurityCode securityCode=new SecurityCode(mobile,Dates.getCurrentLongDate(), randomCode);
		securityCodeService.saveSecurityCode(securityCode,mobile);  //保存验证码信息
		
		smsParams.put("code", randomCode);
		log.info("-----短信接口监听IP端口日志信息："+IpUtils.getIpAddr(request)+"--------");
		if (!SMSSender.getInstance().sendByTemplate(smsChannel, mobile, template, smsParams)) { // 判断短信发送是否成功
			return new ApiResult(false, ResultStatusConstant.FAIL, "send.fail.");
		}
		
		return new ApiResult(true,ResultStatusConstant.SUCCESS,"send.success.");
	}
	/**
	 * 短信验证码验证
	 * @param request
	 * @param mobile
	 * @param code
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/validate/sms",method = RequestMethod.POST)
	public ApiResult validateSms(HttpServletRequest request,@RequestParam("mobile") String mobile,@RequestParam("code")String code){
		if (StringUtil.isBlank(mobile)
				|| StringUtil.isBlank(code)){
			return new ApiResult(false,ResultStatusConstant.FAIL,"user.info.not.complete.");
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
		
		return new ApiResult(true,ResultStatusConstant.SUCCESS,"code.validate.success.");
	}
}
