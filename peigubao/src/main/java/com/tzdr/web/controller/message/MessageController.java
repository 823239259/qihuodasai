package com.tzdr.web.controller.message;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jodd.util.StringUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tzdr.business.service.message.MessageService;
import com.tzdr.business.service.wuser.WUserService;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.domain.cache.CacheManager;
import com.tzdr.domain.cache.DataDicKeyConstants;
import com.tzdr.domain.entity.DataMap;
import com.tzdr.domain.vo.MessageVo;
import com.tzdr.domain.web.entity.Message;
import com.tzdr.web.constants.Constants;
import com.tzdr.web.constants.ViewConstants;
import com.tzdr.web.utils.UserSessionBean;

@Controller
@RequestMapping("/message")
public class MessageController{

	@SuppressWarnings("unused")
	private static Logger log = LoggerFactory.getLogger(MessageController.class);
	
	@Autowired
	private WUserService wUserService;

	@Autowired
	private MessageService messageService;
	
	/**
	* @Description: TODO(访问在线留言页面)
	* @Title: userMessage
	* @param modelMap
	* @param request
	* @param response
	* @return String    返回类型
	 */
	@RequestMapping( value = "/usermessage")
	public String userMessage(ModelMap modelMap,HttpServletRequest request,HttpServletResponse response){
		UserSessionBean userSessionBean = (UserSessionBean)request.getSession().getAttribute(Constants.TZDR_USER_SESSION);  //获取用户账户信息
		List<DataMap> messageTypeList = CacheManager.getDataMapListByTypeKey(DataDicKeyConstants.MESSAGE); //获取留言类型列表信息
		List<Message> messageList = messageService.findByWuserAndDeletedFalseOrderByAddtimeDesc(userSessionBean.getId());  //获取留言列表信息
		modelMap.put("messageTypeList", messageTypeList);
		modelMap.put("messageList", messageList);
		return ViewConstants.MessageViewJsp.MESSAGE_INDEX_VIEW;
	}
	
	/**
	* @Description: TODO(保存留言信息)
	* @Title: saveMessage
	* @param message   留言信息
	* @param modelMap
	* @param request
	* @param response
	* @return
	* @return JsonResult    返回类型
	 */
	@RequestMapping(value = "/save_message")
	@ResponseBody
	public JsonResult saveMessage(Message message,ModelMap modelMap,HttpServletRequest request,HttpServletResponse response){
		JsonResult  jsonResult = new JsonResult(true);
		if(message != null && !StringUtil.isBlank(message.getContent())){
			message.setContent(message.getContent().replaceAll("<", "&lt;").replaceAll(">", "&gt;"));
		}
		UserSessionBean userSessionBean = (UserSessionBean)request.getSession().getAttribute(Constants.TZDR_USER_SESSION);  //获取用户账户信息
		if(userSessionBean!=null){
		message.setWuser(wUserService.getUser(userSessionBean.getId()));
		message.setAddtime((new Date().getTime()/1000));
		messageService.saveMessage(message);
		}
		return jsonResult;
	}
	
	/**
	* @Description: TODO(获取用户留言列表信息)
	* @Title: queryMessageList
	* @param modelMap
	* @param request
	* @param response
	* @return JsonResult    返回类型
	 */
	@RequestMapping(value = "/query_message_list")
	@ResponseBody
	public JsonResult queryMessageList(ModelMap modelMap,HttpServletRequest request,HttpServletResponse response){
		JsonResult  jsonResult = new JsonResult(true);
		UserSessionBean userSessionBean = (UserSessionBean)request.getSession().getAttribute(Constants.TZDR_USER_SESSION);  //获取用户账户信息
		List<Message> messageList = messageService.findByWuserAndDeletedFalseOrderByAddtimeDesc(userSessionBean.getId());  //获取留言列表信息
		List<MessageVo> messageVoList = new ArrayList<MessageVo>();
		if(messageList != null && !messageList.isEmpty()){
			for (Message message : messageList) {
				MessageVo messageVo = new MessageVo();
				messageVo.setId(message.getId());
				messageVo.setAddtime(message.getAddtime());
				messageVo.setContent(message.getContent());
				messageVo.setEndtime(message.getEndtime());
				messageVo.setRecontent(message.getRecontent());
				messageVo.setReid(message.getReid());
				messageVo.setStatus(message.getStatus());
				messageVo.setTitle(message.getTitle());
				messageVo.setType(message.getType());
				messageVoList.add(messageVo);
			}
			messageList = null;
		}
		Map<Object, Object> data = new HashMap<Object, Object>();
		data.put("messageList", messageVoList);
		jsonResult.setData(data);
		return jsonResult;
	}
	
	/**
	* @Description: TODO(删除用户留言信息)
	* @Title: deleteMessage
	* @param id  留言信息编号
	* @param modelMap
	* @param request
	* @param response
	* @return
	* @return JsonResult    返回类型
	 */
	@RequestMapping(value = {"/delete_message/{id}"})
	@ResponseBody
	public JsonResult deleteMessage(@PathVariable String id,ModelMap modelMap,HttpServletRequest request,HttpServletResponse response){
		JsonResult  jsonResult = new JsonResult(true);
		messageService.deleteMessageById(id);
		return jsonResult;
	}
}
