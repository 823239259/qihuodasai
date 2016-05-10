package com.tzdr.web.controller.activities;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tzdr.business.service.activity.ActivityKudoService;
import com.tzdr.business.service.activity.ActivityUserService;
import com.tzdr.business.service.datamap.DataMapService;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.domain.vo.activity.ActivityKudoWebVo;
import com.tzdr.domain.web.entity.activity.ActivityUser;
import com.tzdr.web.constants.Constants;
import com.tzdr.web.constants.ViewConstants;
import com.tzdr.web.utils.LotteryTimesUtil;
import com.tzdr.web.utils.UserSessionBean;

/**
 * 股指活动
 * <P>title:@SIFActivitesController.java</p>																								
 * <P>Description：</p>
 * <p>Copyright: Copyright (c) 2016 tzdr</p>
 * <p>Company: 上海信闳</p>
 * History：
 * @author:WangPinQun
 * @date 2016年01月07日
 * @version 1.0
 */
@Controller
@RequestMapping("/sifactivites")
public class SIFActivitesController {

	@Autowired
	private DataMapService dataMapService;
	
	@Autowired
	private ActivityKudoService activityKudoService;
	
	@Autowired
	private ActivityUserService activityUserService;
	
	/**
	 * WEB开箱壕礼活动
	 */
	private final static int activityType=2;
	
	/**
	* @Description: 开箱壕礼页面
	* @Title: hxhl
	* @param modelMap
	* @param request
	* @param response
	* @return String    返回类型
	 */
	@RequestMapping(value="/kxhl")
	public String kxhl(ModelMap modelMap,HttpServletRequest request,HttpServletResponse response){
		
		UserSessionBean userSessionBean = (UserSessionBean) request.getSession().getAttribute(Constants.TZDR_USER_SESSION);
		
		//手机号码
		String mobile=null;
		
		//开箱次数
		int unpackingNum = 0; 
		
		//用户前一日交易手数
		int yesterdayTradNum = 0;
		
		//我的宝箱信息
		List<ActivityKudoWebVo> ActivityKudoWebVoList =  null;
		
		if(userSessionBean!=null){
			//用户参赛信息
			ActivityUser activityUser = activityUserService.getActivityUser(userSessionBean.getId(), activityType);
			if(activityUser != null){
				unpackingNum = activityUser.getUnpackingNum() == null ? 0 : activityUser.getUnpackingNum();
				yesterdayTradNum = activityUser.getYesterdayTradNum() == null ? 0 : activityUser.getYesterdayTradNum();
			}
			//我的宝箱信息
			ActivityKudoWebVoList = activityKudoService.findActivityKudoWebVos(userSessionBean.getId(), activityType);
		}
		
		//活动时间开始时间
		String activityTimeStart = LotteryTimesUtil.getValueByKey("web.kxhl.activity.time.start") + " " + LotteryTimesUtil.getValueByKey("web.kxhl.lottery.time.start"); 
		//活动时间结束时间
		String activityTimeEnd = LotteryTimesUtil.getValueByKey("web.kxhl.activity.time.end") + " " + LotteryTimesUtil.getValueByKey("web.kxhl.lottery.time.end");
		
		modelMap.put("mobile", mobile);
		modelMap.put("yesterdayTradNum", yesterdayTradNum);
		modelMap.put("unpackingNum", unpackingNum);
		modelMap.put("ActivityKudoWebVoList", ActivityKudoWebVoList);
		modelMap.put("activityTimeStart", activityTimeStart);
		modelMap.put("activityTimeEnd", activityTimeEnd);
		
		return ViewConstants.SIFActivitesJsp.KXHL;
	}
	
	/**
	 * 获取开箱壕礼奖品
	 * @param modelMap
	 * @param request
	 * @param resp
	 * @throws Exception
	 */
	@RequestMapping(value = "/getkxhlPrize", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult getkxhlPrize(ModelMap modelMap,HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		JsonResult jsonResult = new JsonResult(true);
		
		UserSessionBean userSessionBean = (UserSessionBean) request.getSession().getAttribute(Constants.TZDR_USER_SESSION);
		
		Map<Object,Object> reusltMap = new HashMap<Object, Object>();
		
		//判断是否是活动时间内:1、活动日期；2、活动时间；3：交易日时间；
		int lotteryTimeResult = activityKudoService.isLotteryTime(LotteryTimesUtil.getValueByKey("web.kxhl.activity.time.start"), LotteryTimesUtil.getValueByKey("web.kxhl.activity.time.end"), LotteryTimesUtil.getValueByKey("web.kxhl.lottery.time.start"), LotteryTimesUtil.getValueByKey("web.kxhl.lottery.time.end"));
		
		if(lotteryTimeResult == -1){   //未到活动日期
			reusltMap.put("reusltCode", -100);
			jsonResult.setData(reusltMap);
			return jsonResult;
		}else if(lotteryTimeResult == -2){  //活动已结束
			reusltMap.put("reusltCode", -200);
			jsonResult.setData(reusltMap);
			return jsonResult;
		}else if(lotteryTimeResult == -3){  //未到活动时间
			reusltMap.put("reusltCode", -300);
			jsonResult.setData(reusltMap);
			return jsonResult;
		}else if(lotteryTimeResult == -4){  //非交易日时间
			reusltMap.put("reusltCode", -400);
			jsonResult.setData(reusltMap);
			return jsonResult;
		}
		
		//开始抽奖
		reusltMap = activityKudoService.updateLotteryPrizeOfWeb(userSessionBean.getId(), userSessionBean.getMobile());
		jsonResult.setData(reusltMap);
		return jsonResult;
	}
}
