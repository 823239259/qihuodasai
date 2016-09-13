package com.tzdr.api.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import com.alibaba.fastjson.JSONObject;
import com.tzdr.api.constants.DataConstant;
import com.tzdr.api.constants.ResultStatusConstant;
import com.tzdr.api.request.RequestObj;
import com.tzdr.api.support.ApiResult;
import com.tzdr.api.support.CacheUser;
import com.tzdr.api.thread.RegistP2pThread;
import com.tzdr.api.util.AuthUtils;
import com.tzdr.api.util.PasswordUtils;
import com.tzdr.api.util.RequestUtils;
import com.tzdr.business.api.service.ApiUserService;
import com.tzdr.business.service.datamap.DataMapService;
import com.tzdr.business.service.future.FSimpleCouponService;
import com.tzdr.business.service.generalize.GeneralizeChannelService;
import com.tzdr.business.service.generalize.GeneralizeService;
import com.tzdr.business.service.securitycode.SecurityCodeService;
import com.tzdr.business.service.wuser.WUserService;
import com.tzdr.common.utils.Dates;
import com.tzdr.common.utils.IpAddressUtils;
import com.tzdr.common.utils.IpUtils;
import com.tzdr.common.utils.MessageUtils;
import com.tzdr.domain.api.vo.ApiUserVo;
import com.tzdr.domain.constants.Constant;
import com.tzdr.domain.constants.ExtensionConstants;
import com.tzdr.domain.entity.DataMap;
import com.tzdr.domain.web.entity.GeneralizeChannel;
import com.tzdr.domain.web.entity.GeneralizeVisit;
import com.tzdr.domain.web.entity.SecurityCode;
import com.tzdr.domain.web.entity.WUser;
import com.tzdr.domain.web.entity.future.FSimpleCoupon;

import jodd.util.ObjectUtil;
import jodd.util.StringUtil;

@Controller
@RequestMapping(value = "/activity")
public class ActivityController {
	private static Logger logger = LoggerFactory.getLogger(ActivityController.class);
	@Autowired
	private DataMapService dataMapService;
	@Autowired
	private GeneralizeChannelService channelService;
	@Autowired
	private WUserService wUserService;
	@Autowired
	private SecurityCodeService securityCodeService;
	@Autowired
	private GeneralizeService generalizeService;
	@Autowired
	private ApiUserService  apiUserService;
	@Autowired
	private FSimpleCouponService fSimpleCouponService;
	/**
	 * 上线推广注册页面
	 * 
	 * @param request
	 *            2016.08.01
	 * @return
	 */
	@RequestMapping(value = "/view", method = RequestMethod.POST)
	@ResponseBody
	public ApiResult extendSignView(ModelMap modelMap, HttpServletRequest request,
			@RequestParam(value = "channelCode", required = false) String channelCode,
			@RequestParam(value = "activity", required = false) String activity) {
		Map<String, Object> resultMap = new HashMap<>();
		if(dataMapService.activityExpired()){
			GeneralizeVisit generalizeVisit = new GeneralizeVisit();
			String ip = IpUtils.getIpAddr(request);
			generalizeVisit.setClieantIp(ip);
			generalizeVisit.setCreatedate(new Date().getTime() / 1000);
			generalizeVisit.setDeleted(false);
			generalizeVisit.setCity(IpAddressUtils.getAffiliationCity(ip, "utf-8"));
			generalizeVisit.setGeneralizeId(null);
			generalizeVisit.setParam(channelCode);
			generalizeVisit.setUrl(request.getRequestURL().toString() + "?" + request.getQueryString());
			generalizeVisit.setActivity(ExtensionConstants.ACTIVITY_TYPE);
			generalizeService.saveGeneralizeVisit(generalizeVisit);
			resultMap.put("channelCode", channelCode);
			List<DataMap> dataMaps = dataMapService.findByTypeKey("websiteRecord");
			resultMap.put("footNote", dataMaps != null && dataMaps.size() >0 ?dataMaps.get(0).getValueName():"");
		}
		return new ApiResult(true, ResultStatusConstant.SUCCESS, "query.success" , resultMap);
	}
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
	public ApiResult signInOperation(RequestObj requestObj,HttpServletRequest request,HttpServletResponse response){
		
		String mobile=requestObj.getMobile();
		String code=requestObj.getCode();
		String password=requestObj.getPassword();
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
		
		WUser wUser = new WUser();     //创建注册对象信息
		wUser.setSource(Constant.RegistSource.APP_TZDR_REGIST);
		WUser platformDefaultWuser = wUserService.queryByUserType(DataConstant.TZDR_DEFAULT_USERTYPE).get(0);  //获取平台默认用户
		wUser.setUserType("0");
		wUser.setParentNode(platformDefaultWuser);
		wUser.setPassword(password);
		wUser.setMobile(mobile);
		wUser.setCtime((new Date().getTime()/1000));
		wUser.setRegIp(IpUtils.getIpAddr(request));
		wUser.setLastLoginTime(Dates.getCurrentLongDate());
		String ip = IpUtils.getIpAddr(request);
		wUser.setLastLoginIp(ip);
		wUser.setRegCity(IpAddressUtils.getAffiliationCity(ip, "utf-8"));
		//设置渠道
		GeneralizeChannel generalizeChannel = getChannel(channel);
		if (generalizeChannel != null) {
			String channelName = generalizeChannel.getTypeThreeTitle();
			if (channelName == null || channelName.length() <= 0) {
				channelName = generalizeChannel.getTypeTwoTitle();
				if (channelName == null || channelName.length() <= 0) {
					channelName = generalizeChannel.getTypeOneTitle();
				}
			}
			wUser.setChannel(channelName); // 设置渠道
			wUser.setKeyword(generalizeChannel.getUrlKey());// 设置关键字
		}
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
		new RegistP2pThread(mobile,password,wUser.getLoginSalt()).start();
		JSONObject  jsonObject = new JSONObject();
		//登录成功 返回用户唯一标志token和对应由密码种子+uid生成的key值
		String appToken = AuthUtils.createToken(wUser.getId());
		jsonObject.put(DataConstant.APP_TOKEN,appToken);
		String secretKey = AuthUtils.createSecretKey(wUser);
		jsonObject.put(DataConstant.SECRET_KEY,secretKey);
		// 缓存用户信息
		DataConstant.CACHE_USER_MAP.put(appToken,new CacheUser(wUser,secretKey));
		logger.info("注册成功");
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
}
