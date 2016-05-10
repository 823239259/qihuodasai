package com.tzdr.business.service.userfundmoney.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jodd.util.StringUtil;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.tzdr.business.service.userfundmoney.UserFundMoneyService;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.utils.DateUtils;
import com.tzdr.domain.dao.pay.UserFundDao;
import com.tzdr.domain.vo.UserFundMoneyVo;
import com.tzdr.domain.web.entity.UserFund;

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

@Service("userFundMoneyService")
@Transactional(propagation=Propagation.REQUIRED)
public class UserFundMoneyServiceImpl extends BaseServiceImpl<UserFund,UserFundDao> implements UserFundMoneyService{

	@SuppressWarnings("unchecked")
	@Override
	public PageInfo<UserFundMoneyVo> queryData(PageInfo<UserFundMoneyVo> dataPage,
			UserFundMoneyVo userFundMoneyVo, String type) {
		StringBuffer sqlBuf = new StringBuffer("select account.account_name as aname,abs(f.money) money,");
		sqlBuf.append( " date_format(from_unixtime(f.addtime),'%Y-%m-%d %H:%i:%s')AS adtime,f.lid,u.tname,uta.account,");
		sqlBuf.append(" users.mobile from w_user_fund f , w_user users, (SELECT * from w_user_trade uts group by  uts.group_id ) uta ,");
		sqlBuf.append(" w_account account,w_user_verified u where u.uid=f.uid and users.id=u.uid and users.id=f.uid ");
		sqlBuf.append("  and uta.group_id=f.lid and account.id=uta.account_id  ");
		if (userFundMoneyVo != null) {
			if(StringUtil.isNotBlank(userFundMoneyVo.getStarttime())){
				String startime=userFundMoneyVo.getStarttime();
				Date enddate=DateUtils.stringToDate(startime, "yyyy-MM-dd HH:mm:ss");
				long stime=enddate.getTime()/1000;
				sqlBuf.append(" and f.addtime>="+stime);
			}
			if(StringUtil.isNotBlank(userFundMoneyVo.getEndtime())){
				String endtime=userFundMoneyVo.getEndtime();
				Date enddate=DateUtils.stringToDate(endtime, "yyyy-MM-dd HH:mm:ss");
				long etime=enddate.getTime()/1000;
				sqlBuf.append(" and f.addtime<="+etime);
		  }
		}
		sqlBuf.append(" and f.type=? ");
		List<Object> params = Lists.newArrayList();
		params.add(type);
		sqlBuf.append("order by f.addtime desc");
		PageInfo<UserFundMoneyVo> page= this.getEntityDao().queryDataPageBySql(dataPage, sqlBuf.toString(),
			UserFundMoneyVo.class, params.toArray());
		return page;

	}

	
}

