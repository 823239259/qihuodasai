package com.tzdr.web.controller.generalize;

import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jodd.util.StringUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tzdr.business.service.generalize.GeneralizeService;
import com.tzdr.common.utils.IpAddressUtils;
import com.tzdr.common.utils.IpUtils;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.domain.web.entity.GeneralizeVisit;
import com.tzdr.web.constants.Constants;

@Controller
@RequestMapping("/")
public class VisitRecordController {
	
	@SuppressWarnings("unused")
	private static Logger log = LoggerFactory.getLogger(VisitRecordController.class);

	@Autowired
	private GeneralizeService generalizeService;
	
	@RequestMapping(value = "/visitrecord")
	@ResponseBody
	public JsonResult visitrecord(String url,String p,String uid,String from,ModelMap modelMap,HttpServletRequest request,HttpServletResponse response){
		JsonResult  jsonResult = new JsonResult(true);
		if((!StringUtil.isBlank(p) && !"null".equals(p)) || (!StringUtil.isBlank(uid) && !"null".equals(uid))){
			GeneralizeVisit generalizeVisit = new GeneralizeVisit();    //访问记录
			if(!StringUtil.isBlank(uid) && !"null".equals(uid)){        //佣金推广
				Cookie userIdCookie = new Cookie(Constants.TZDR_GENERALIZEUID, uid);
				userIdCookie.setMaxAge(Constants.USERID_COOKIEMAX_VALUE);
				response.addCookie(userIdCookie);
				request.getSession().setAttribute(Constants.TZDR_GENERALIZEUID, uid);
				generalizeVisit.setGeneralizeId(uid);
			}
			if(!StringUtil.isBlank(p) && !"null".equals(p)){         //渠道推广
				Cookie channelCookie = new Cookie(Constants.TZDR_CHANNEL, p);
				channelCookie.setMaxAge(Constants.USERID_COOKIEMAX_VALUE);
				response.addCookie(channelCookie);
				request.getSession().setAttribute(Constants.TZDR_CHANNEL, p);
				generalizeVisit.setParam(p);
			}
			if(!StringUtil.isBlank(from) && !"null".equals(from)){   //推广返回地址
				Cookie channelFromCookie = new Cookie(Constants.TZDR_CHANNEL_FROM, from);
				channelFromCookie.setMaxAge(Constants.USERID_COOKIEMAX_VALUE);
				channelFromCookie.setPath("/");
				response.addCookie(channelFromCookie);
				request.getSession().setAttribute(Constants.TZDR_CHANNEL_FROM, from);
			}
			generalizeVisit.setUrl(url);   
			String ip = IpUtils.getIpAddr(request);
			generalizeVisit.setClieantIp(ip);
			generalizeVisit.setCity(IpAddressUtils.getAffiliationCity(ip, "utf-8"));
			generalizeVisit.setCreatedate((new Date().getTime()/1000));
			generalizeService.saveGeneralizeVisit(generalizeVisit);
		}
		return jsonResult;
	}
}
