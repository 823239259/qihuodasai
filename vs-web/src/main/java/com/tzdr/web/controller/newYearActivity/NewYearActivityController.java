package com.tzdr.web.controller.newYearActivity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tzdr.business.service.wuser.WUserService;
import com.tzdr.common.utils.EmailUtils;
import com.tzdr.domain.web.entity.WUser;
import com.tzdr.web.constants.Constants;
import com.tzdr.web.utils.UserSessionBean;
import com.tzdr.common.utils.Dates;
import java.util.Date;

@Controller
@RequestMapping("/nyActivity")
public class NewYearActivityController {
	private static Logger log = LoggerFactory.getLogger(NewYearActivityController.class);
	
	private static final  Date stockStartTime = Dates.parse("2016-02-16 00:00:00)", Dates.CHINESE_DATETIME_FORMAT_LINE);
			
	@Autowired
	private WUserService wuserService;
	@RequestMapping("/stock")
	@ResponseBody
	public String sendStockEmail(ModelMap modelMap,HttpServletRequest request,HttpServletResponse response,String mobile){
		
		UserSessionBean userSessionBean = (UserSessionBean)request.getSession().getAttribute(Constants.TZDR_USER_SESSION);  //获取用户账户信息
		if (userSessionBean==null){
			return "login";
		}
		if (stockStartTime.after(new Date())){
			return "开门红包2月16日开始领取，请先申请操盘方案！";
		}
		WUser user=wuserService.get(userSessionBean.getId()); 
		String usermobile=user.getMobile();
		if(usermobile!=null){
			EmailUtils.getInstance().sendMail("caoyi@tzdr.com", "投资达人春节重磅福利我要开门红包","投资达人春节重磅福利我要开门红包,电话：【"+usermobile+"】");
		}
		return "success";
	}
	
	
	@RequestMapping("/stockReceive")
	@ResponseBody
	public String sendStockReceiveEmail(ModelMap modelMap,HttpServletRequest request,HttpServletResponse response,String mobile){
		
		UserSessionBean userSessionBean = (UserSessionBean)request.getSession().getAttribute(Constants.TZDR_USER_SESSION);  //获取用户账户信息
		if (userSessionBean==null){
			return "login";
		}
		if (stockStartTime.after(new Date())){
			return "实盘资金2月16日开始赠送，请先申请操盘方案！";
		}
		WUser user=wuserService.get(userSessionBean.getId()); 
		String usermobile=user.getMobile();
		if(usermobile!=null){
			EmailUtils.getInstance().sendMail("caoyi@tzdr.com", "投资达人春节重磅福利我要领取","投资达人春节重磅福利我要领取,电话：【"+usermobile+"】");
		}
		return "success";
	}
	
	
	
	@RequestMapping("/future")
	@ResponseBody
	public String sendFutureEmail(ModelMap modelMap,HttpServletRequest request,HttpServletResponse response,String mobile){
		UserSessionBean userSessionBean = (UserSessionBean)request.getSession().getAttribute(Constants.TZDR_USER_SESSION);  //获取用户账户信息
		if (userSessionBean==null){
			return "login";
		}
		WUser user=wuserService.get(userSessionBean.getId()); 
		String usermobile=user.getMobile();
		if(usermobile!=null){
			EmailUtils.getInstance().sendMail("caoyi@tzdr.com", "投资达人春节感恩回馈活动","投资达人春节感恩回馈活动,电话：【"+usermobile+"】");
		}
		return "success";
	}
}
