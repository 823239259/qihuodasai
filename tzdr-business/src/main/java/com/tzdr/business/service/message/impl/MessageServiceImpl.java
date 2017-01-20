package com.tzdr.business.service.message.impl;

import java.util.List;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jodd.util.StringUtil;

import com.tzdr.business.cms.service.auth.AuthService;
import com.tzdr.business.service.message.MessageService;
import com.tzdr.business.service.wuser.WUserService;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.common.utils.Dates;
import com.tzdr.domain.cms.entity.user.User;
import com.tzdr.domain.dao.message.MessageDao;
import com.tzdr.domain.web.entity.Message;

/**
* @Description: TODO(在线留言业务信息管理实现层)
* @ClassName: MessageServiceImpl
* @author wangpinqun
* @date 2014年12月27日 下午5:48:09
 */
@Service("messageService")
public class MessageServiceImpl extends BaseServiceImpl<Message, MessageDao> implements MessageService {

	@Autowired
	private WUserService wUserService;
	
	@Autowired
	private AuthService authService;
	
	@Override
	public void saveMessage(Message message) {
		super.save(message);
	}

	@Override
	public List<Message> findByWuserAndDeletedFalseOrderByAddtimeDesc(String uId) {
		if(StringUtil.isBlank(uId)){
			return null;
		}
		return getEntityDao().findByWuserAndDeletedFalseOrderByAddtimeDesc(wUserService.getUser(uId));
	}

	@Override
	public boolean deleteMessageById(String id) {
		Message message = super.get(id);
		if(message == null){
			return false;
		}
		message.setDeleted(true);  //逻辑删除
		super.update(message);
		return true;
	}

	@Override
	public void replyMessage(Message message) {
		String  id  = message.getId();
		Message  dbMessage = get(id);
		dbMessage.setEndtime(Dates.getCurrentLongDate());
		dbMessage.setRecontent(message.getRecontent());
		
		User  loginUser = authService.getCurrentUser();
		dbMessage.setReid(String.valueOf(loginUser.getId()));
		dbMessage.setReplyUserName(loginUser.getRealname());
		dbMessage.setStatus(NumberUtils.toShort("1"));
		super.update(dbMessage);
	}

}
