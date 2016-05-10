/**
 * 
 */
package com.tzdr.web.controller.activities;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tzdr.business.service.future.FSimpleCouponService;
import com.tzdr.common.utils.Dates;
import com.tzdr.common.utils.StringCodeUtils;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.domain.web.entity.future.FSimpleCoupon;
import com.tzdr.web.constants.Constants;
import com.tzdr.web.constants.ViewConstants;
import com.tzdr.web.utils.LotteryTimesUtil;
import com.tzdr.web.utils.UserSessionBean;

/**
 * <B>说明: 周年庆活动</B>
 * 
 * @chen.ding 2016年3月11日
 */
@Controller
@RequestMapping("/activity/anniversary")
public class AnniversaryController {

	@Autowired
	private FSimpleCouponService fSimpleCouponService;

	private static final String prize_name = "周年抽奖活动";

	@RequestMapping(value = "/index")
	public String index(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {
		UserSessionBean userSessionBean = (UserSessionBean) request.getSession().getAttribute(Constants.TZDR_USER_SESSION);
		Boolean isunpack = false;
		boolean islogin = false;
		if (null != userSessionBean) {
			islogin = true;
			FSimpleCoupon fSimpleCoupon = fSimpleCouponService.haveCoupon(userSessionBean.getId(), prize_name);
			if (null != fSimpleCoupon) {
				String name = fSimpleCoupon.getName().substring(7);
				modelMap.put("mykudo", name);
				isunpack = true;
			}
		}
		modelMap.put("isunpack", isunpack);
		modelMap.put("islogin", islogin);
		modelMap.put("mobile", islogin ? StringCodeUtils.buildMobile(userSessionBean.getMobile()) : "");

		return ViewConstants.ActiviesJsp.ANNIVERSARY_SPREE_INDEX;
	}

	@RequestMapping(value = "/indexSSO")
	public String indexSSO(HttpServletRequest request, HttpServletResponse response) {
		return "redirect:/activity/anniversary/index";
	}

	@RequestMapping(value = "/unpack.json")
	@ResponseBody
	public JsonResult unpackJson(HttpServletRequest request, HttpServletResponse response) throws Exception {
		JsonResult jsonResult = new JsonResult(false, "-1", "system.error.");
		UserSessionBean userSessionBean = (UserSessionBean) request.getSession().getAttribute(Constants.TZDR_USER_SESSION);

		if (null == userSessionBean) {
			jsonResult.setSuccess(true);
			jsonResult.setCode("0");
			jsonResult.setMessage("user.not.login.");
		} else {
			FSimpleCoupon fSimpleCoupon = fSimpleCouponService.haveCoupon(userSessionBean.getId(), prize_name);
			if (null == fSimpleCoupon) {
				// 周年抽奖开始时间
				String startTimeStr = LotteryTimesUtil.getValueByKey("web.znfl.activity.time.start") + " " + LotteryTimesUtil.getValueByKey("web.znfl.lottery.time.start");
				String endTimeStr = LotteryTimesUtil.getValueByKey("web.znfl.activity.time.end") + " " + LotteryTimesUtil.getValueByKey("web.znfl.lottery.time.end");
				long startTime = Dates.toDate(startTimeStr).getTime() / 1000;
				long endTime = Dates.toDate(endTimeStr).getTime() / 1000;
				long currTime = Dates.getCurrentLongDate();

				jsonResult.setSuccess(true);
				if (currTime >= startTime && currTime <= endTime) {
					String result = this.fSimpleCouponService.unpackDistilCoupon(userSessionBean.getId(), userSessionBean.getUname(), userSessionBean.getMobile(), startTime);
					jsonResult.setCode(("".equals(result) ? "2" : "1"));
					jsonResult.setObj(result.substring(7));
					jsonResult.setMessage("success!");
				} else if (currTime < startTime) {
					jsonResult.setCode("3");
					jsonResult.setMessage("activity.time.yet.to.com!");
				} else {
					jsonResult.setCode("4");
					jsonResult.setMessage("activity.over!");
				}
			}
		}

		return jsonResult;
	}

}
