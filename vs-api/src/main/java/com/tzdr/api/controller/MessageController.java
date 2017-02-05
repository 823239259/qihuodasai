package com.tzdr.api.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.tzdr.api.constants.ResultStatusConstant;
import com.tzdr.api.support.ApiResult;
import com.tzdr.api.util.AuthUtils;
import com.tzdr.business.service.message.MessageService;
import com.tzdr.business.service.wuser.WUserService;
import com.tzdr.domain.vo.MessageVo;
import com.tzdr.domain.web.entity.Message;
import com.tzdr.domain.web.entity.WUser;
import jodd.util.StringUtil;

/**
 * @Title: MessageController.java     
 * @Description: 在线留言业务信息管理类    
 * @author： gc 
 * @company：成都盈透科技有限公司
 * @address：成都市天府软件园D4-109
 * @date： 2016年12月27日  
 */
@Controller
@RequestMapping("/message")
public class MessageController {

	@SuppressWarnings("unused")
	private static Logger log = LoggerFactory.getLogger(MessageController.class);
	
	@Autowired
	private WUserService wUserService;

	@Autowired
	private MessageService messageService;	
	
	/**
	 * @Description: TODO(保存留言信息)
	 * @Title: saveMessage
	 * @param message   留言信息
	 * @param request
	 * @param response
	 * @return
	 * @return ApiResult    返回类型
	 */
	@RequestMapping(value = "/save_message",method=RequestMethod.POST)
	@ResponseBody
	public ApiResult saveMessage(Message message,HttpServletRequest request,HttpServletResponse response){
		if(StringUtil.isBlank(message.getContent())){
			return new ApiResult(false,ResultStatusConstant.FAIL,"留言内容不能为空");
		}
		if(message != null && !StringUtil.isBlank(message.getContent())){
			message.setContent(message.getContent().replaceAll("<", "&lt;").replaceAll(">", "&gt;"));
		}
		
		String uid = AuthUtils.getCacheUser(request).getUid();  //获取用户信息
		
		WUser user  = wUserService.get(uid);
		
		message.setWuser(user);
		message.setAddtime((new Date().getTime()/1000));
		messageService.saveMessage(message);
		return new ApiResult(true,ResultStatusConstant.SUCCESS,"留言提交成功");
	} 
	
	/**
	 * @Description: TODO(获取用户留言列表信息)
	 * @Title: queryMessageList
	 * @param request
	 * @param response
	 * @return ApiResult    返回类型
	 */
	@RequestMapping(value = "/query_message_list",method=RequestMethod.POST)
	@ResponseBody
	public ApiResult queryMessageList(HttpServletRequest request,HttpServletResponse response){
		String uid = AuthUtils.getCacheUser(request).getUid();
		WUser user =  wUserService.get(uid);
		List<Message> messageList = messageService.findByWuserAndDeletedFalseOrderByAddtimeDesc(user.getId());  //获取留言列表信息
		
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
		return new ApiResult(true,ResultStatusConstant.SUCCESS,"获取留言列表信息成功",data);
	}
	
	/**
	 * @Description: TODO(删除用户留言信息)
	 * @Title: deleteMessage
	 * @param id  留言信息编号
	 * @param request
	 * @param response
	 * @return
	 * @return ApiResult    返回类型
	 */
	@RequestMapping(value = {"/delete_message"},method=RequestMethod.POST)
	@ResponseBody
	public ApiResult deleteMessage(@RequestParam("id")String id,HttpServletRequest request,HttpServletResponse response){
		if(messageService.deleteMessageById(id)){
			return new ApiResult(true,ResultStatusConstant.SUCCESS,"留言信息删除成功");
		}else{
			return new ApiResult(false,ResultStatusConstant.FAIL,"留言id不存在");
		}
	}
}
	
