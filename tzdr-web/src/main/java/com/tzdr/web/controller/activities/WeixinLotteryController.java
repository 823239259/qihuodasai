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
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.web.constants.Constants;
import com.tzdr.web.constants.ViewConstants;
import com.tzdr.web.utils.LotteryTimesUtil;
import com.tzdr.web.utils.UserSessionBean;

/**
 * <B>说明: </B>微信抽奖活动control
 * @chen.ding
 * 2016年1月20日
 */
@Controller
@RequestMapping("/weixin/lottery")
public class WeixinLotteryController {

	@Autowired
	private ActivityKudoService activityKudoService;

	/**
	 * 活动类型：1-微信抽奖；2-web抽奖
	 */
	private final int ACTIVITY_TYPE = 1;

	/**
	 * 登录页
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/login")
	public String login(HttpServletRequest request, HttpServletResponse response) {
		return ViewConstants.ActiviesJsp.WEIXIN_LOTTERY_LOGIN;
	}
	
	/**
	 * 抽奖页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/index")
	public String index(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {
		return ViewConstants.ActiviesJsp.WEIXIN_LOTTERY_INDEX;
	}
	/**
	 * 抽奖页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/luck/view")
	public String view(@RequestParam(value = "channelCode",required = false)String channelCode,
			@RequestParam(value = "activity",required = false) String activity) {
		return ViewConstants.SignInViewJsp.EXTENDSIONSIGN_VEIW;
	}
	/**
	 * 抽奖页面-拦截登录
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/indexSSO")
	public String indexSSO(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {
		return "redirect:/weixin/lottery/index";
	}

	/**
	 * 我的奖品数据：-1，系统错误；0，未登录；1，获取成功
	 * 
	 * @param uid
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/myKudo")
	@ResponseBody
	public JsonResult myKudo(HttpServletRequest request, HttpServletResponse response) {
		JsonResult jsonResult = new JsonResult(false, "-1", "system.error.");
		UserSessionBean userSessionBean = (UserSessionBean) request.getSession().getAttribute(Constants.TZDR_USER_SESSION);

		if (userSessionBean != null) {
			jsonResult.setSuccess(true);
			jsonResult.setCode("1");
			jsonResult.setMessage("success!");
			jsonResult.setObj(this.activityKudoService.findActivityKudoWebVos(userSessionBean.getId(), ACTIVITY_TYPE));
		} else {
			jsonResult.setSuccess(true);
			jsonResult.setCode("0");
			jsonResult.setMessage("user.not.login.");
		}

		return jsonResult;
	}

	/**
	 * 奖品设置页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/prize")
	public String prize(HttpServletRequest request, HttpServletResponse response) {
		return ViewConstants.ActiviesJsp.WEIXIN_LOTTERY_PRIZE;
	}

	/**
	 * 活动规则页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/rule")
	public String rule(HttpServletRequest request, HttpServletResponse response) {
		return ViewConstants.ActiviesJsp.WEIXIN_LOTTERY_RULE;
	}

	/**
	 * 抽奖开箱：-1，系统错误；0，未登录；1，开箱成功-获得奖品；2-开箱时间未到；3，本周日未持有方案；4，
	 * 本周一没有新开方案；5-本次开启宝箱机会已使用，敬请期待下一次；6-奖品已送完
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/unpacking")
	@ResponseBody
	public JsonResult unpacking(HttpServletRequest request, HttpServletResponse response) {
		JsonResult jsonResult = new JsonResult(false, "-1", "system.error.");
		UserSessionBean userSessionBean = (UserSessionBean) request.getSession().getAttribute(Constants.TZDR_USER_SESSION);

		if (userSessionBean != null) {
			jsonResult.setSuccess(true);
			if (!LotteryTimesUtil.isLotteryTime()) {
				jsonResult.setCode("2");
				jsonResult.setMessage("unpacking.time.yet.to.com.");
				return jsonResult;
			}

			this.activityKudoService.unpacking(jsonResult, ACTIVITY_TYPE, userSessionBean.getId(), userSessionBean.getMobile());
		} else {
			jsonResult.setSuccess(true);
			jsonResult.setCode("0");
			jsonResult.setMessage("user.not.login.");
		}

		return jsonResult;
	}

}
