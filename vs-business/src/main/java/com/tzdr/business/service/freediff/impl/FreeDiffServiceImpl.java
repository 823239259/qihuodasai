package com.tzdr.business.service.freediff.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jodd.util.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.tzdr.business.cms.service.auth.AuthService;
import com.tzdr.business.service.freediff.FreeDiffService;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.exception.BusinessException;
import com.tzdr.common.utils.Dates;
import com.tzdr.domain.cms.entity.user.User;
import com.tzdr.domain.dao.freediff.FreeDiffDao;
import com.tzdr.domain.vo.FreeDiffVo;
import com.tzdr.domain.web.entity.FreeDiff;

/**
 * 
 * <P>title:@UserFundMoneyServiceImpl.java</p>																								
 * <P>Description：</p>
 * <p>Copyright: Copyright (c) 2015 tzdr</p>
 * <p>Company: 上海信宏</p>
 * History：
 * @author:zhangjun
 * @date 2015年3月31日
 * @version 1.0
 */

@Service("freeDiffServiceImpl")
public class FreeDiffServiceImpl extends BaseServiceImpl<FreeDiff,FreeDiffDao> implements FreeDiffService{

	@Autowired
	private  AuthService  authService;
	@Override
	public PageInfo<FreeDiffVo> queryData(PageInfo<FreeDiffVo> dataPage,
			FreeDiffVo freeDiffVo) {
		List<Object> params = Lists.newArrayList();
		String sql=" select  pa.account_name parentaccount,tra.group_id groupid, d.money,d.account,date_format(from_unixtime(d.addtime),'%Y-%m-%d')";
			sql+="  createdate,d.id,d.create_user lrr,d.type from w_free_diff d ,w_account  a,w_parent_account pa,";
			sql+="	(select * from w_user_trade group by account_id) tra where ";
			sql+=" a.account_name=d.account and a.parent_accound_id=pa.id and tra.account_id=a.id ";
			if(StringUtil.isNotBlank(freeDiffVo.getStarttime())){
				Date starttime=Dates.parse(freeDiffVo.getStarttime(), "yyyy-MM-dd");
				sql+=" and d.addtime>=?";
				params.add(starttime.getTime()/1000);
			}
			if(StringUtil.isNotBlank(freeDiffVo.getEndtime())){
				Date endtime=Dates.parse(freeDiffVo.getEndtime(), "yyyy-MM-dd");
				sql+=" and d.addtime<=?";
				params.add(endtime.getTime()/1000);
			}
			if(StringUtil.isNotBlank(freeDiffVo.getParentaccount())){
				sql+=" and pa.account_name like '%"+freeDiffVo.getParentaccount()+"%'";
			}
			if(StringUtil.isNotBlank(freeDiffVo.getQtype())){
				sql+=" and d.type=?";
				params.add(freeDiffVo.getQtype());
			}
			if(StringUtil.isNotBlank(freeDiffVo.getMinmoney())){
				sql+=" and d.money>=?";
				params.add(freeDiffVo.getMinmoney());
			}
			
			if(StringUtil.isNotBlank(freeDiffVo.getMaxmoney())){
				sql+=" and d.money<=?";
				params.add(freeDiffVo.getMaxmoney());
			}

			if(StringUtil.isNotBlank(freeDiffVo.getAccount())){
				sql+=" and d.account=?";
				params.add(freeDiffVo.getAccount());
			}

			if(StringUtil.isNotBlank(freeDiffVo.getLrr())){
				sql+=" and d.create_user=?";
				params.add(freeDiffVo.getLrr());
			}
			
			
			sql+=" order by d.addtime desc";
			
			PageInfo<FreeDiffVo> page= getEntityDao().queryPageBySql(dataPage, sql,
					FreeDiffVo.class,null, params.toArray());
				return page;	
		
	}

	@Override
	public FreeDiff getEntity(String account, String addtime,String type) {
		long time=0;
		if(StringUtil.isNotBlank(addtime)){
			time=Dates.parse(addtime, "yyyy-MM-dd").getTime()/1000;
		}
		return this.getEntityDao().findData(account,time,type);
	}

	@Override
	public boolean checkAccount(String account) {
		//查询恒生子账户是否存在
		String sql=" select account_name account from w_account where account_name=?";
		List<Object> params = Lists.newArrayList();
		params.add(account);
		List<FreeDiffVo> records = nativeQuery(sql, params, FreeDiffVo.class);
		if(records!=null &&records.size()>0){
			return true;
		}
		return false;
	}


	@Override
	public void save(FreeDiff o) throws BusinessException {
		User user = authService.getCurrentUser();
		o.setCreateUser(user.getRealname());
		o.setCreateUserId(user.getId());
		super.save(o);
	}
	
	@Override
	public void update(FreeDiff o) throws BusinessException {
		FreeDiff entity=this.get(o.getId());
		entity.setMoney(o.getMoney());
		entity.setAddtime(o.getAddtime());
		entity.setAccount(o.getAccount());
		entity.setCreateTime(new Date().getTime()/1000);
		entity.setType(o.getType());
		User user = authService.getCurrentUser();
		entity.setCreateUser(user.getRealname());
		entity.setCreateUserId(user.getId());
		super.update(entity);
	}

	@Override
	public String savesByVo(List<FreeDiffVo> vos) throws Exception {
		String messageText="";
		List<FreeDiff> list=new ArrayList<FreeDiff>();
		User user = authService.getCurrentUser();
		for(FreeDiffVo vo:vos){
			FreeDiff entity=new FreeDiff();
			entity.setAccount(vo.getAccount());
			entity.setMoney(vo.getMoney());
			entity.setType(vo.getQtype());
			String date=vo.getCreatedate();
			entity.setAddtime(Dates.parse(date,"yyyy-MM-dd").getTime()/1000);
			entity.setCreateUser(user.getRealname());
			entity.setCreateUserId(user.getId());
			list.add(entity);
		}
		for(FreeDiff entity:list){
				FreeDiff diff=getEntityDao().findData(entity.getAccount(),entity.getAddtime(),entity.getType());
				if(diff!=null){
					try {
						Date date=Dates.parseLong2Date(entity.getAddtime());
						messageText=entity.getAccount()+"在"+Dates.format(date,"yyyy-MM-dd")+"存在";
						return messageText;
					} catch (Exception e) {
						throw new Exception(messageText);
					}
				}
				if(this.checkAccount(entity.getAccount())){
					this.save(entity);
				}
		}
		return messageText;
	}

	@Override
	public FreeDiffVo queryTotalData(FreeDiffVo freeDiffVo) {
		List<Object> params = Lists.newArrayList();
		String sql=" select ifnull(sum(temp.money),0) as money,count(*) countnum from(select  pa.account_name parentaccount,tra.group_id groupid, d.money,d.account,date_format(from_unixtime(d.addtime),'%Y-%m-%d')";
		sql+="  createdate,d.id,d.create_user lrr,d.type from w_free_diff d ,w_account  a,w_parent_account pa,";
		sql+="	(select * from w_user_trade group by account_id) tra where ";
		sql+=" a.account_name=d.account and a.parent_accound_id=pa.id and tra.account_id=a.id ";
		if(StringUtil.isNotBlank(freeDiffVo.getStarttime())){
			Date starttime=Dates.parse(freeDiffVo.getStarttime(), "yyyy-MM-dd");
			sql+=" and d.addtime>=?";
			params.add(starttime.getTime()/1000);
		}
		if(StringUtil.isNotBlank(freeDiffVo.getEndtime())){
			Date endtime=Dates.parse(freeDiffVo.getEndtime(), "yyyy-MM-dd");
			sql+=" and d.addtime<=?";
			params.add(endtime.getTime()/1000);
		}
		if(StringUtil.isNotBlank(freeDiffVo.getParentaccount())){
			sql+=" and pa.account_name like '%"+freeDiffVo.getParentaccount()+"%'";
		}
		if(StringUtil.isNotBlank(freeDiffVo.getQtype())){
			sql+=" and d.type=?";
			params.add(freeDiffVo.getQtype());
		}
		if(StringUtil.isNotBlank(freeDiffVo.getMinmoney())){
			sql+=" and d.money>=?";
			params.add(freeDiffVo.getMinmoney());
		}
		
		if(StringUtil.isNotBlank(freeDiffVo.getMaxmoney())){
			sql+=" and d.money<=?";
			params.add(freeDiffVo.getMaxmoney());
		}

		if(StringUtil.isNotBlank(freeDiffVo.getAccount())){
			sql+=" and d.account=?";
			params.add(freeDiffVo.getAccount());
		}

		if(StringUtil.isNotBlank(freeDiffVo.getLrr())){
			sql+=" and d.create_user=?";
			params.add(freeDiffVo.getLrr());
		}
		
		
		sql+=" order by d.addtime desc ) temp";
		List<FreeDiffVo> fundVos = nativeQuery(sql, params, FreeDiffVo.class);
		if(fundVos!=null){
			return fundVos.get(0);
		}
		return null;
	}
}

