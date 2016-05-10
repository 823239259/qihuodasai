package com.tzdr.cms.controller;

import javax.servlet.http.HttpServletRequest;

import jodd.util.ObjectUtil;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tzdr.business.service.message.MessageService;
import com.tzdr.cms.constants.ViewConstants;
import com.tzdr.cms.support.BaseCmsController;
import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.utils.Dates;
import com.tzdr.common.utils.MessageUtils;
import com.tzdr.common.utils.ValidatorUtil;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.domain.web.entity.Message;

@Controller
@RequestMapping("/admin/message")
public class MessageController extends BaseCmsController<Message> {
	private static Logger log = LoggerFactory
			.getLogger(MessageController.class);

	@Autowired
	private MessageService messageService;

	@Override
	public BaseService<Message> getBaseService() {
		return messageService;
	}
	public MessageController() {
		setResourceIdentity("sys:customerService:message");
	}
	
	/**
	 * 列表页面
	 * @param request
	 * @return
	 */
	@RequestMapping(value="list")
	public String limitList(HttpServletRequest request){
		return ViewConstants.MessageViewJsp.LIST_VIEW;
	}
	
	
	
	@RequestMapping(value = "/edit")
	public String edit(HttpServletRequest request,@RequestParam("fromType") String fromType,
			@RequestParam(value="id",required=false) String id) throws Exception 
	{
		request.setAttribute("fromType",fromType);
		Message  message  =  messageService.get(id);
		request.setAttribute("message",message);
		request.setAttribute("addtime",Dates.format(Dates.parseLong2Date(message.getAddtime()),Dates.CHINESE_DATETIME_FORMAT_LINE));
		request.setAttribute("endtime",ObjectUtil.equals(null, message.getEndtime())?"":Dates.format(Dates.parseLong2Date(message.getEndtime()),Dates.CHINESE_DATETIME_FORMAT_LINE));
		return ViewConstants.MessageViewJsp.EDIT_VIEW;	
		
	}
	
	
	@RequestMapping(value = "/reply", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult reply(Message message,BindingResult result) throws Exception {
		//修改权限	
		if (permissionList != null) {
	         this.permissionList.assertHasUpdatePermission();
	     }
		if (result.hasErrors())
		{
			return new JsonResult(false, ValidatorUtil.getErrorMessage(result));
		}	
		messageService.replyMessage(message);
		return new JsonResult(MessageUtils.message("update.success"));
	}

	
	
	public static void main(String[] args) {
		PostMethod method = new PostMethod("http://192.168.10.197:9000/app/services");
		HttpClient client=new HttpClient();	
		client.getParams().setContentCharset("UTF-8");
		method.setRequestHeader("ContentType",
				"application/x-www-form-urlencoded;charset=UTF-8");

		NameValuePair[] data = { new NameValuePair("email","zhangjun@tzdr.com"),
				new NameValuePair("name", "zhangjun"),
				new NameValuePair("OPT","2"),
				new NameValuePair("pwd","123456") };

		method.setRequestBody(data);
		try {
			client.executeMethod(method);

			String SubmitResult = method.getResponseBodyAsString();
 System.out.println(SubmitResult);
			Document doc = DocumentHelper.parseText(SubmitResult);
			
		} catch (Exception e) {
			
	    }
	}
}

