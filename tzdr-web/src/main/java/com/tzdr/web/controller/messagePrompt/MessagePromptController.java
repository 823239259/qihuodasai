package com.tzdr.web.controller.messagePrompt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.tzdr.business.cms.service.messagePrompt.MessagePromptService;
import com.tzdr.common.web.support.JsonResult;

/**
 * 国际期货风控管理的邮件提示
 * 
 * @chendong 2016年08月16日
 */
@Controller
@RequestMapping("/prompt")
public class MessagePromptController {

	@Autowired
	private MessagePromptService messagePromptService;

	/**
	 * 发送邮件提示
	 * 
	 * @param submitType
	 * @param userName
	 * @return
	 */
	@RequestMapping(value = "/sendPrompt", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult sendMessage(String submitType, String userName) {
		
		JsonResult jsonResult = new JsonResult();
		messagePromptService.sendMessage(submitType, userName);

		return jsonResult;
	}

}
