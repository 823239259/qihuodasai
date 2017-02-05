package com.tzdr.web.controller.merchants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tzdr.common.utils.EmailUtils;
import com.tzdr.web.controller.spread.FteSpreadController;

@Controller
@RequestMapping("/merchants")
public class MerchantsController {
	private static Logger log = LoggerFactory.getLogger(FteSpreadController.class);
	@RequestMapping(value = "/sentEmail",method=RequestMethod.POST)
	public void freetrade(ModelMap modelMap,HttpServletRequest request,HttpServletResponse response,String mobile,String name,String message){
		if(name!=null&&mobile!=null&&message!=null){
			EmailUtils.getInstance().sendMail("shangyanxi@tzdr.com", "招商","姓名:"+ name +". 客户电话:"+mobile + ". 留言:"+ message);
		}
			
		
	}
}
