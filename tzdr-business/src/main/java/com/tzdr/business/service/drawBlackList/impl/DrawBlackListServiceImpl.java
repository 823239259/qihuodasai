package com.tzdr.business.service.drawBlackList.impl;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import jodd.util.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;
import com.tzdr.business.cms.exception.AccountException;
import com.tzdr.business.cms.service.auth.AuthService;
import com.tzdr.business.service.drawBlackList.DrawBlackListService;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.domain.cms.entity.user.User;
import com.tzdr.domain.dao.drawBlackList.DrawBlackListDao;
import com.tzdr.domain.vo.DrawBlackListVo;
import com.tzdr.domain.web.entity.Account;
import com.tzdr.domain.web.entity.DrawBlackList;

/**
 * 
 * <P>title:@DrawBlackListServiceImpl.java</p>																								
 * <P>Description：</p>
 * <p>Copyright: Copyright (c) 2015 tzdr</p>
 * <p>Company: 上海信宏</p>
 * History：
 * @author:zhangjun
 * @date 2015年5月22日
 * @version 1.0
 */
@Transactional
@Service("drawBlackListService")
public class DrawBlackListServiceImpl  extends BaseServiceImpl<DrawBlackList,DrawBlackListDao> implements DrawBlackListService{
	@Autowired
	private  AuthService  authService;
	@Override
	public PageInfo<DrawBlackListVo> queryData(
			PageInfo<DrawBlackListVo> dataPage, DrawBlackListVo vo) {
		List<Object> params = Lists.newArrayList();
		String sql="select t.id, t.uid,t.reason,t.create_user createUser,DATE_FORMAT(FROM_UNIXTIME(t.create_time),'%Y-%m-%d %H:%i:%s') createtime,v.tname,u.mobile ,u.avl_bal avlBal,u.frz_bal frzBal, \r";
			   sql+="(select sum(money) from w_user_trade trade  where trade.status in (0,1) and trade.uid=t.uid) as money, \r";
			   sql+="(select sum(abs(money)) from w_user_fund fund where fund.pay_status=0 and  fund.uid=t.uid) as debts \r";
			   sql+=" from w_draw_black_list t,w_user u,w_user_verified v \r";
			   sql+=" where u.id=t.uid and v.uid=u.id and v.uid=t.uid \r";
	   if(StringUtil.isNotBlank(vo.getMobile())){
		    sql+=" and u.mobile like '%"+vo.getMobile()+"%'";
	   }
	   
	   if(StringUtil.isNotBlank(vo.getTname())){
		    sql+=" and v.tname like '%"+vo.getTname()+"%'";
	   }
	   PageInfo<DrawBlackListVo> page= getEntityDao().queryPageBySql(dataPage, sql,
			    		DrawBlackListVo.class,null, params.toArray());
				return page;	
	}

	@Override
	public DrawBlackList getDrawBlackListByUid(String uid) {

		return this.getEntityDao().findByUid(uid);
	}

	@Override
	public void saveEntity(String uid, String reason) {
		DrawBlackList entity=new DrawBlackList();
		User user = authService.getCurrentUser();
		entity.setReason(reason);
		entity.setCreateUser(user.getRealname());
		entity.setCreateUserId(user.getId());
		entity.setCreateTime(new Date().getTime()/1000);
		entity.setUid(uid);
		super.save(entity);
		
	}

	@Override
	public List<DrawBlackList> getEntityByUid(String uid) {
		return this.getEntityDao().getEntityByUid(uid);
	}

	/**
	 * 批量导入
	 */
	@Override
	public void batchImportData(List<DrawBlackList> drawBlackLists) {
		for (DrawBlackList entity : drawBlackLists){
			super.save(entity);
		}
	}
}

