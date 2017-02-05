package com.tzdr.domain.dao.message;

import java.util.List;

import com.tzdr.common.dao.BaseJpaDao;
import com.tzdr.domain.web.entity.Message;
import com.tzdr.domain.web.entity.WUser;

/**
* @Description: TODO(在线留言业务信息管理持久层)
* @ClassName: MessageDao
* @author wangpinqun
* @date 2014年12月27日 下午5:40:28
 */
public interface MessageDao extends BaseJpaDao<Message, String>{
 
	
	/**
	* @Description: TODO(根据用户获取未删除留言列表信息，并且按留言提交时间进行排序)
	* @Title: findByWuserOrderByAddtimeDesc 
	* @param uId   用户帐号编号
	* @return List<Message>    返回类型
	 */
	public List<Message> findByWuserAndDeletedFalseOrderByAddtimeDesc(WUser wuser);
}
