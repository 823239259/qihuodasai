package com.tzdr.business.service.drawMoneyData.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tzdr.business.cms.service.auth.AuthService;
import com.tzdr.business.service.drawMoneyData.DrawMoneyDataService;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.exception.BusinessException;
import com.tzdr.domain.cms.entity.user.User;
import com.tzdr.domain.dao.drawMoneyData.DrawMoneyDataDao;
import com.tzdr.domain.vo.DrawMoneyDataVo;
import com.tzdr.domain.web.entity.DrawMoneyData;
@Service("drawMoneyDataService")
@Transactional
public class DrawMoneyDataServiceImpl extends BaseServiceImpl<DrawMoneyData,DrawMoneyDataDao> implements DrawMoneyDataService{
	private static Logger log = LoggerFactory
			.getLogger(DrawMoneyDataServiceImpl.class);
	@Autowired
	private  AuthService  authService;
	@Override
	public PageInfo<DrawMoneyDataVo> queryData(
			PageInfo<DrawMoneyDataVo> dataPage, DrawMoneyDataVo vo) {
		String sql=" select (select u.realname  from sys_user u where u.id=t.first_audit_id) as firstauditname,";
				sql+="(select u.realname  from sys_user u where u.id=t.re_audit_id) as reauditname,";
				sql+="(select u.realname  from sys_user u where u.id=t.final_audit_id) as finalAuditname,";
				sql+="concat(t.minmoney,'-',t.maxmoney) as money,t.create_user createUser,DATE_FORMAT(FROM_UNIXTIME(t.create_time),'%Y-%m-%d %H:%i:%s') createDate,";
				sql+=" DATE_FORMAT(FROM_UNIXTIME(t.final_date),'%Y-%m-%d %H:%i:%s') finalDate, t.* from w_draw_money_data t";
		List<Object> params=new ArrayList<Object>();
		PageInfo<DrawMoneyDataVo> page= getEntityDao().queryPageBySql(dataPage, sql,
				DrawMoneyDataVo.class,null, params.toArray());
			return page;	
	}
	
	@Override
	public void save(DrawMoneyData o) throws BusinessException {
		o.setCreateTime(new Date().getTime()/1000);
		User user = authService.getCurrentUser();
		o.setCreateUserId(user.getId());
		o.setCreateUser(user.getRealname());
		super.save(o);
	}
	
	@Override
	public void update(DrawMoneyData o) throws BusinessException {
		DrawMoneyData entity=this.get(o.getId());
		entity.setCreateTime(new Date().getTime()/1000);
		o.setCreateTime(entity.getCreateTime());
		o.setCreateUser(entity.getCreateUser());
		o.setCreateUserId(entity.getCreateUserId());
		try {
			BeanUtils.copyProperties(entity, o);
		} catch (Exception e) {
			log.error("提现设置赋值错误"+e.getMessage());
		} 
		User user = authService.getCurrentUser();
		entity.setFinalAuditId(user.getId());
		entity.setFinalDate(new Date().getTime()/1000);
		super.update(entity);
	}

	@Override
	public DrawMoneyData getAduitMoneyByType(String type) {
		List<DrawMoneyData> list=this.getEntityDao().getAduitMoneyByType(type);
		return list.get(0);
	}

	@Override
	public DrawMoneyData getAduitMoneyByType(String type, Double money) {
		List<DrawMoneyData> list=this.getEntityDao().getAduitMoneyByTypeAndMoney(type,money);
		if(list!=null &&list.size()>0){
			return list.get(0);
		}
		return null;
	}
	

}
