package com.tzdr.web.controller.spot;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import jodd.util.ObjectUtil;
import jodd.util.StringUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tzdr.business.service.securityInfo.SecurityInfoService;
import com.tzdr.business.service.spot.SpotBookingService;
import com.tzdr.business.service.spot.SpotSalesmanService;
import com.tzdr.common.utils.ValidatorUtil;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.domain.web.entity.SpotBooking;
import com.tzdr.domain.web.entity.SpotSalesman;
import com.tzdr.domain.web.entity.UserVerified;
import com.tzdr.web.constants.Constants;
import com.tzdr.web.constants.ViewConstants;
import com.tzdr.web.utils.UserSessionBean;

/**
 * 现货预约
 * @ClassName SpotBookingController
 * @author L.Y
 * @email liuyuan@peigubao.com
 * @date 2015年10月9日
 */
@Controller
@RequestMapping("/")
public class SpotBookingController {
	
	private static Logger logger = LoggerFactory.getLogger(SpotBookingController.class);
	
	@Autowired
	private SpotBookingService spotBookingService;
	
	@Autowired
	private SpotSalesmanService spotSalesmanService;
	
	@Autowired
	private SecurityInfoService securityInfoService;
	
	/**
	 * 现货预约页面
	 * @MethodName index
	 * @author L.Y
	 * @date 2015年10月9日
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/spot")
	public String index(HttpServletResponse resp, HttpServletRequest req, ModelMap model) throws Exception {
		//获取用户账户信息
		UserSessionBean userSessionBean = (UserSessionBean)req.getSession().getAttribute(Constants.TZDR_USER_SESSION);
		String mobile = null;
		
		model.put("isLogin", false);
		//是否登陆
		if(!ObjectUtil.equals(null, userSessionBean) && StringUtil.isNotBlank(mobile = userSessionBean.getMobile())) {
			//查询是否预约
			model.put("isLogin", true);
			UserVerified userVerified = securityInfoService.findByUserId(userSessionBean.getId());
			model.put("mobile", mobile);
			model.put("autonym", 
					ObjectUtil.equals(null, userVerified) || 
					StringUtil.isBlank(userVerified.getTname()) ? 
							"" : userVerified.getTname());
			SpotBooking sb = spotBookingService.findByUid(userSessionBean.getId());
			model.put("isBooking", false);
			if(!ObjectUtil.equals(null, sb)) {
				model.put("isBooking", true);
				SpotSalesman ss = spotSalesmanService.get(sb.getSid());
				model.put("salesmanName", ss.getName());
				model.put("salesmanMobile", ss.getMobile());
			}
		}
		return ViewConstants.SpotJsp.SPOT_PAGE;
	}
	
	private static Object lock = new Object();
	/**
	 * 现货预约
	 * @MethodName booking
	 * @author L.Y
	 * @date 2015年10月9日
	 * @param resp response
	 * @param req request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/spot/booking")
	public String booking(HttpServletResponse resp, HttpServletRequest req, 
			ModelMap model, @Valid SpotBooking newer, BindingResult result) throws Exception {
		
		if (result.hasErrors()) {
			logger.warn(ValidatorUtil.getErrorMessage(result));
			//暂未给出提示（前端给出提示）
//			return ViewConstants.SpotJsp.SPOT_PAGE;
			return "redirect:/spot";
		}
		
		synchronized (lock) {
			//获取用户账户信息
			UserSessionBean userSessionBean = (UserSessionBean)req.getSession().getAttribute(Constants.TZDR_USER_SESSION);
			String uid = null;
			
			if(!ObjectUtil.equals(null, userSessionBean) && StringUtil.isNotBlank(uid = userSessionBean.getId())) {
				//是否已预约
				SpotBooking sb = spotBookingService.findByUid(uid);
				if(ObjectUtil.equals(null, sb)) { //未预约
					newer.setUid(uid);
					spotBookingService.save(newer);
				}
			}
			
		}
		return "redirect:/spot";
	}
	
	/**
	 * 是否 已预约
	 * @MethodName isBooking
	 * @author L.Y
	 * @date 2015年10月10日
	 * @param req request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/spot/is_booking")
	@ResponseBody
	public JsonResult isBooking(HttpServletRequest req) throws Exception {
		//获取用户账户信息
		String uid = null;
		JsonResult jr = new JsonResult(Boolean.FALSE);
		UserSessionBean userSessionBean = (UserSessionBean)req.getSession().getAttribute(Constants.TZDR_USER_SESSION);
		
		//未登陆
		if(ObjectUtil.equals(null, userSessionBean) || StringUtil.isBlank(uid = userSessionBean.getId())) {
			jr.setMessage("未登陆..");
			return jr;
		}
		
		jr.setMessage("您已预约，请勿重复预约!");
		//查询是否预约
		if(ObjectUtil.equals(null, spotBookingService.findByUid(uid))) { //未预约
			jr.setMessage("");
			jr.setSuccess(Boolean.TRUE);
		}
		return jr;
	}
}