package com.tzdr.business.service.message;

import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import com.tzdr.business.service.wuser.WUserService;
import com.tzdr.domain.web.entity.Message;

@ContextConfiguration(locations = { "/applicationContext-test.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class MessageServiceTest {
	
	@Autowired
	private WUserService wUserService;

	@Autowired
	private MessageService messageService;
	
//	@Ignore
	@Test
	public void saveMessage(){
		Message message = new Message();
		message.setWuser(wUserService.getUser("1"));
		message.setAddtime((new Date().getTime()/1000));
		message.setContent("为什么免费体验，既然是免费怎么还需要交钱才能体验呢？");
		message.setDeleted(false);
		message.setType("其他问题");
		message.setStatus((short)0);
		message.setVersion((long)1);
		messageService.saveMessage(message);
	}
	
//	@Ignore
//	@Test
	public void findByWuserAndDeletedFalseOrderByAddtimeDesc(){
		List<Message> messageList = messageService.findByWuserAndDeletedFalseOrderByAddtimeDesc("1");
		Assert.notNull(messageList);
	}
	
//	@Ignore
//	@Test
	public void deleteMessageById(){
		messageService.deleteMessageById("402892434a991379014a99138d2a0000");
	}
}
