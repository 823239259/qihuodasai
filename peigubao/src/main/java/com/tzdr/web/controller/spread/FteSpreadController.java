package com.tzdr.web.controller.spread;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tzdr.common.utils.EmailUtils;
/**
 * A50 推广
 * @author LiuYang
 *
 * 2015年10月19日 下午4:11:09
 */
@Controller
@RequestMapping("/ftsespread")
public class FteSpreadController {
	private static Logger log = LoggerFactory.getLogger(FteSpreadController.class);
	@RequestMapping(value = "/sentEmail")
	public void freetrade(ModelMap modelMap,HttpServletRequest request,HttpServletResponse response,String mobile,String type){
		if(type.equals("1")){
			EmailUtils.getInstance().sendMail("shangyanxi@tzdr.com", "新A50客户来了，赶紧联系！【A50专题模拟盘报名】","客户电话："+mobile);
		}else if(type.equals("2")){
			EmailUtils.getInstance().sendMail("shangyanxi@tzdr.com", "新A50客户来了，赶紧联系！【A50专题实盘报名】","客户电话："+mobile);
		}else if(type.equals("3")){
			EmailUtils.getInstance().sendMail("shangyanxi@tzdr.com", "新美原油客户来了，赶紧联系！【美原油专题模拟盘报名】","客户电话："+mobile);
		}else if(type.equals("4")){
			EmailUtils.getInstance().sendMail("shangyanxi@tzdr.com", "新美原油客户来了，赶紧联系！【美原油专题实盘报名】","客户电话："+mobile);
		}else if(type.equals("5")){
			EmailUtils.getInstance().sendMail("shangyanxi@tzdr.com", "新恒生指数客户来了，赶紧联系！【恒生指数专题模拟盘报名】","客户电话："+mobile);
		}else if(type.equals("6")){
			EmailUtils.getInstance().sendMail("shangyanxi@tzdr.com", "新恒生指数客户来了，赶紧联系！【恒生指数专题实盘报名】","客户电话："+mobile);
		}
		
		
		
		
		
		
	}
	
	

}
