package com.tzdr.web.controller.coupon;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;





import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tzdr.business.service.future.FSimpleCouponService;
import com.tzdr.business.service.wuser.WUserService;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.utils.Dates;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.domain.vo.future.FSimpleCouponWebVo;
import com.tzdr.domain.web.entity.UserVerified;
import com.tzdr.domain.web.entity.WUser;
import com.tzdr.domain.web.entity.future.FSimpleCoupon;
import com.tzdr.web.constants.Constants;
import com.tzdr.web.constants.ViewConstants;
import com.tzdr.web.utils.UserSessionBean;

/**  
 * @Title: FSimpleCouponController.java     
 * @Description: 我的优惠券   
 * @author： WangPinQun 
 * @E-mail：wangpinqun@tzdr.com
 * @company： 上海信闳投资管理有限公司重庆分公司
 * @address：重庆市渝北区黄山大道中段70号两江新界3栋13楼
 * @date： 2016年3月7日 下午3:17:20    
 * @version： V1.0  
 */
@Controller
@RequestMapping("/user/coupon")
public class FSimpleCouponController {
	
	@Autowired
	private FSimpleCouponService fSimpleCouponService;
	
	@Autowired
	private WUserService wUserService;

	@RequestMapping(value = "/list")
	public String list(ModelMap modelMap,HttpServletRequest request,HttpServletResponse response){
		return ViewConstants.FSimpleCouponJsp.LIST;
	}
	
	/**
	* @Title: findData    
	* @Description: 获取用户列表信息 
	* @param response
	* @param request
	* @return
	 */
	@RequestMapping(value = "/findData")
	@ResponseBody
	public PageInfo<FSimpleCouponWebVo> findData(HttpServletResponse response,HttpServletRequest request){

		UserSessionBean userSessionBean=(UserSessionBean) request.getSession().getAttribute(Constants.TZDR_USER_SESSION);
		if (userSessionBean == null) {
			return null;
		}
		String pageIndex = request.getParameter("pageIndex");
		String perPage = request.getParameter("perPage");	
		PageInfo<FSimpleCouponWebVo> pageInfo = this.fSimpleCouponService.findDataList(pageIndex, perPage,userSessionBean.getId(), "配股宝");
		
		return pageInfo;
	}
	
	/**
	 * 使用优惠券【红包】
	 * @param id  优惠券编号
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/employ" , method = RequestMethod.POST)
	@ResponseBody
	public JsonResult employ(String id,ModelMap modelMap,HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		JsonResult jsonResult = new JsonResult(true);
		
		UserSessionBean userSessionBean=(UserSessionBean) request.getSession().getAttribute(Constants.TZDR_USER_SESSION);
		
		WUser wuser = wUserService.get(userSessionBean.getId());   //获取用户信息
		
		//获取优惠券信息
		FSimpleCoupon fSimpleCoupon = fSimpleCouponService.getFSimpleCoupon(id, userSessionBean.getId());
		
		if(fSimpleCoupon == null){   //未找到该优惠券
			jsonResult.setMessage("notFindData");
			return jsonResult;
		}
		
		if(fSimpleCoupon.getType() != 1){  //数据类型有误无法使用
			jsonResult.setMessage("notUse");
			return jsonResult;
		}
		
		if(fSimpleCoupon.getStatus() == 3){ //判断是否已经使用
			jsonResult.setMessage("isUse");
			return jsonResult;
		}
		
		//判断是否已经过期
		if(fSimpleCoupon.getStatus() == 2 && Dates.getCurrentLongDay(0, 0, 0) > fSimpleCoupon.getDeadline()){
			jsonResult.setMessage("isOutDate");
			return jsonResult;
		}
		
		String mobile = wuser.getMobile();   //获取手机号码
		
		UserVerified userVerified = wuser.getUserVerified();  //获取用户安全认证信息
		
		String userName = userVerified == null  ? "" : userVerified.getTname();  //获取用户姓名
		
		//使用优惠券【红包】，并且把优惠券现金划账到用户达人账户余额上
		fSimpleCouponService.updateFSimpleCoupon(fSimpleCoupon, mobile, userName, fSimpleCoupon.getMoney(), userSessionBean.getId());
		
		return jsonResult;
	}
}
