package com.tzdr.business.service.message;

import java.util.List;

import com.tzdr.common.baseservice.BaseService;
import com.tzdr.domain.web.entity.Message;

/**
* @Description: TODO(在线留言业务信息管理业务接口层)
* @ClassName: MessageService
* @author wangpinqun
* @date 2014年12月27日 下午5:45:27
 */
public interface MessageService extends BaseService<Message>{

	/**
	* @Description: TODO(保存在线留言信息)
	* @Title: saveMessage
	* @param message    在线留言信息
	* @return void    返回类型
	 */
	public void saveMessage(Message message);
	
	/**
	* @Description: TODO(根据用户编号获取用户未删除留言列表信息，并且按留言提交时间进行排序)
	* @Title: findByWuserOrderByAddtimeDesc
	* @param uId  用户帐号编号
	* @return List<Message>    返回类型
	 */
	public List<Message> findByWuserAndDeletedFalseOrderByAddtimeDesc(String uId);
	
	/**
	* @Description: TODO(根据留言编号，删除留言信息,逻辑删除)
	* @Title: deleteMessageById  
	* @param id   留言编号
	* @return void    返回类型
	 */
	public void deleteMessageById(String id);
	
	/**
	 * 回复反馈
	 * @param message
	 */
	public void replyMessage(Message message);
}
