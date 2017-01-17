package com.tzdr.api.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jodd.util.ObjectUtil;
import jodd.util.StringUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tzdr.api.constants.DataConstant;
import com.tzdr.api.constants.ResultStatusConstant;
import com.tzdr.api.support.ApiResult;
import com.tzdr.api.util.AuthUtils;
import com.tzdr.business.app.service.CouponService;
import com.tzdr.business.service.wuser.WUserService;
import com.tzdr.common.utils.Dates;
import com.tzdr.domain.app.vo.UserCouponVo;
import com.tzdr.domain.web.entity.UserVerified;
import com.tzdr.domain.web.entity.WUser;
import com.tzdr.domain.web.entity.future.FSimpleCoupon;

/**  
 * @Title: UserCouponController.java     
 * @Description:用户优惠劵信息管理类    
 * @author： WangPinQun 
 * @E-mail：wangpinqun@tzdr.com
 * @company： 上海信闳投资管理有限公司重庆分公司
 * @address：重庆市渝北区黄山大道中段70号两江新界3栋13楼
 * @date： 2016年3月24日 下午2:35:36    
 * @version： V1.0  
 */
@Controller
@RequestMapping("/user/coupon")
public class UserCouponController {

	public static final Logger logger = LoggerFactory.getLogger(UserCouponController.class);
	
	@Autowired
	private CouponService couponService;
	
	@Autowired
	private WUserService  wUserService;
	
	/**
	* @Title: list    
	* @Description: 用户优惠劵列表信息
	* @param modelMap
	* @param request
	* @param response
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/list" , method = RequestMethod.POST)
	@ResponseBody
	public ApiResult list(ModelMap modelMap,HttpServletRequest request,HttpServletResponse response) throws Exception{
	
		String uid = AuthUtils.getCacheUser(request).getUid();  //获取用户信息
		WUser user  = wUserService.get(uid);
		if(ObjectUtil.equals(null, user)){
			return new ApiResult(false,ResultStatusConstant.FAIL,"user.info.not.exist.");
		}
		
		List<UserCouponVo> dataList = couponService.findUserCouponVos(uid);  //获取用户优惠劵列表信息
		
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("couponList", dataList);
		
		return new ApiResult(true,ResultStatusConstant.SUCCESS,null,dataMap);
	}
	
	/**
	* @Title: employ    
	* @Description: 用户使用现金红包
	* @param id  红包编号
	* @param modelMap
	* @param request
	* @param response
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/employ" , method = RequestMethod.POST)
	@ResponseBody
	public ApiResult employ(String id,ModelMap modelMap,HttpServletRequest request,HttpServletResponse response) throws Exception{
	
		if(StringUtil.isBlank(id)){  //红包编号不能为空
			return new ApiResult(false,ResultStatusConstant.CouponEmployConstant.ID_NOT_NULL,"The id cannot be empty");
		}
		
		String uid = AuthUtils.getCacheUser(request).getUid();  //获取用户信息
		
		WUser wuser = wUserService.get(uid);   //获取用户信息
		
		//获取优惠券信息
		FSimpleCoupon fSimpleCoupon = couponService.getFSimpleCoupon(id, uid);
		
		if(fSimpleCoupon == null){   //未找到该优惠券
			return new ApiResult(false,ResultStatusConstant.CouponEmployConstant.UN_FIND_FCOUPON_DATA,"The data was not found");
		}
		
		if(fSimpleCoupon.getType() != DataConstant.COUPON_TYPE_1){  //数据类型有误无法使用
			return new ApiResult(false,ResultStatusConstant.CouponEmployConstant.FCOUPON_TYPE_ERROR,"The type of coupons is errors");
		}
		
		if(fSimpleCoupon.getStatus() == DataConstant.COUPON_STATUS_3){ //判断是否已经使用
			return new ApiResult(false,ResultStatusConstant.CouponEmployConstant.FCOUPON_IS_USE,"The coupons is already in use");
		}
		
		//判断是否已经过期
		if(fSimpleCoupon.getStatus() == DataConstant.COUPON_STATUS_2 && Dates.getCurrentLongDay(0, 0, 0) > fSimpleCoupon.getDeadline()){
			return new ApiResult(false,ResultStatusConstant.CouponEmployConstant.FCOUPON_OVER_TIME,"The coupon has expired");
		}
		
		String mobile = wuser.getMobile();   //获取手机号码
		
		UserVerified userVerified = wuser.getUserVerified();  //获取用户安全认证信息
		
		String userName = userVerified == null  ? "" : userVerified.getTname();  //获取用户姓名
		
		//使用优惠券【红包】，并且把优惠券现金划账到用户达人账户余额上
		couponService.updateFSimpleCoupon(fSimpleCoupon, mobile, userName, fSimpleCoupon.getMoney(), uid);
		
		return new ApiResult(true,ResultStatusConstant.SUCCESS,"Successful employ");
	}
}
