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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tzdr.business.service.activity.ActivityKudoService;
import com.tzdr.common.utils.StringCodeUtils;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.web.constants.Constants;
import com.tzdr.web.constants.ViewConstants;
import com.tzdr.web.utils.UserSessionBean;

/**
 * <B>说明: </B>新春大礼包controller
 * 
 * @chen.ding 2016年1月26日
 */
@Controller
@RequestMapping("/activity/newyearspree")
public class NewYearSpreeController {

	/**
	 * 活动类型：1-微信抽奖；2-web抽奖（开箱壕礼）；3-新春礼包
	 */
	private final int ACTIVITY_TYPE = 3;

	@Autowired
	private ActivityKudoService activityKudoService;

	/**
	 * 新春大礼包活动页
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/index")
	public String index(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {
		UserSessionBean userSessionBean = (UserSessionBean) request.getSession().getAttribute(Constants.TZDR_USER_SESSION);

		boolean islogin = (null == userSessionBean ? false : true);
		modelMap.put("islogin", islogin);
		modelMap.put("mobile", islogin ? StringCodeUtils.buildMobile(userSessionBean.getMobile()) : "");

		if (islogin) {
			boolean isRecivie = this.activityKudoService.isRecivieActivityKudo(ACTIVITY_TYPE, userSessionBean.getId());
			modelMap.put("isRecivie", isRecivie);

			if (isRecivie) {
				modelMap.put("mykudo", this.activityKudoService.getMyActivityKudo(ACTIVITY_TYPE, userSessionBean.getId(), userSessionBean.getMobile()));
			}
		}

		return ViewConstants.ActiviesJsp.NEWYEAR_SPREE_INDEX;
	}

	/**
	 * 新春大礼包活动页登录拦截
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/indexSSO")
	public String indexSSO(HttpServletRequest request, HttpServletResponse response) {
		return "redirect:/activity/newyearspree/index";
	}

	/**
	 * 领取我的新春大礼包：0-未登录，1-领取成功，2-老用户不能领取新春大礼包，3-已领过新春大礼包
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/recivieMyKudo")
	public String recivieMyKudo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		UserSessionBean userSessionBean = (UserSessionBean) request.getSession().getAttribute(Constants.TZDR_USER_SESSION);

		if (null != userSessionBean) {
			this.activityKudoService.recivieMyActivityKudo(ACTIVITY_TYPE, userSessionBean.getId(), userSessionBean.getMobile());
			return "redirect:/activity/newyearspree/index";
		}

		return "redirect:/activity/newyearspree/indexSSO";
	}

	/**
	 * 领取我的新春大礼包：0-未登录，1-领取成功，2-老用户不能领取新春大礼包，3-已领过新春大礼包
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/recivieMyKudo.json")
	@ResponseBody
	public JsonResult recivieMyKudoJson(HttpServletRequest request, HttpServletResponse response) throws Exception {
		JsonResult jsonResult = new JsonResult(false, "-1", "system.error.");
		UserSessionBean userSessionBean = (UserSessionBean) request.getSession().getAttribute(Constants.TZDR_USER_SESSION);

		if (null != userSessionBean) {
			jsonResult.setSuccess(true);
			jsonResult.setCode(this.activityKudoService.recivieMyActivityKudo(ACTIVITY_TYPE, userSessionBean.getId(), userSessionBean.getMobile()) + "");
			jsonResult.setMessage("success!");
		} else {
			jsonResult.setSuccess(true);
			jsonResult.setCode("0");
			jsonResult.setMessage("user.not.login.");
		}

		return jsonResult;
	}

	/**
	 * 立即使用我的礼包
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/useMyKudo")
	public String useMyKudo(@RequestParam("id") String id, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UserSessionBean userSessionBean = (UserSessionBean) request.getSession().getAttribute(Constants.TZDR_USER_SESSION);

		if (null != userSessionBean || "".equals(id)) {
			this.activityKudoService.useMyActivityKudo(id);
			return "redirect:/activity/newyearspree/index";
		}

		return "redirect:/activity/newyearspree/indexSSO";
	}

	/**
	 * 立即使用我的礼包
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/useMyKudo.json")
	@ResponseBody
	public JsonResult useMyKudoJson(@RequestParam("id") String id, HttpServletRequest request, HttpServletResponse response) throws Exception {
		JsonResult jsonResult = new JsonResult(false, "-1", "system.error.");
		UserSessionBean userSessionBean = (UserSessionBean) request.getSession().getAttribute(Constants.TZDR_USER_SESSION);

		if (null != userSessionBean || "".equals(id)) {
			this.activityKudoService.useMyActivityKudo(id);
			jsonResult.setSuccess(true);
			jsonResult.setCode("1");
			jsonResult.setMessage("success!");
		} else {
			jsonResult.setSuccess(true);
			jsonResult.setCode("0");
			jsonResult.setMessage("user.not.login.");
		}

		return jsonResult;
	}

}
