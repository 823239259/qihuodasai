package com.tzdr.web.controller.ifRedenvelope;

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

@Controller
@RequestMapping("/ifredenvelope")
public class ifredenvelopeController {
	private static Logger log = LoggerFactory.getLogger(ifredenvelopeController.class);
	@Autowired
	private WUserService wuserService;
	@RequestMapping("/email")
	@ResponseBody
	public String sendEmail(ModelMap modelMap,HttpServletRequest request,HttpServletResponse response,String mobile){
		UserSessionBean userSessionBean = (UserSessionBean)request.getSession().getAttribute(Constants.TZDR_USER_SESSION);  //获取用户账户信息
		if (userSessionBean==null){
			return "login";
		}
		WUser user=wuserService.get(userSessionBean.getId()); 
		String usermobile=user.getMobile();
		if(usermobile!=null){
			EmailUtils.getInstance().sendMail("caoyi@tzdr.com", "国际期货抢红包活动","电话：【"+usermobile+"】");
		}
		return "success";
	}
	
}
