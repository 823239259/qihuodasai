package com.tzdr.business.service.pay.impl;



import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tzdr.business.service.pay.UserFundFailService;
import com.tzdr.business.service.pay.UserFundService;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.common.utils.BigDecimalUtils;
import com.tzdr.common.utils.DateUtils;
import com.tzdr.common.utils.TypeConvert;
import com.tzdr.domain.dao.pay.UserFundFailDao;
import com.tzdr.domain.web.entity.UserFund;
import com.tzdr.domain.web.entity.UserFundFail;


/**
 * 
 * <P>Description：</p>
 * <p>Copyright: Copyright (c) 2015 tzdr</p>
 * <p>Company: 上海信闳</p>
 * History：
 * @author:WangPinQun
 * @date 2015年06月25日
 * @version 1.0
 */
@Transactional(propagation=Propagation.REQUIRED)
@Service("userFundFailService")
public class UserFundFailServiceImpl extends BaseServiceImpl<UserFundFail,UserFundFailDao> implements UserFundFailService{
	public static final Logger logger = LoggerFactory.getLogger(UserFundFailServiceImpl.class);

	@Autowired
	private UserFundService userFundService;
	
	@Override
	public void saveUserFundByStatusAndType(int type) {
		
		List<UserFundFail>  userFundFailList = this.getEntityDao().findByRunStatusAndType((short)0, type);
		
		if(userFundFailList != null && !userFundFailList.isEmpty()){
			for (UserFundFail userFundFail : userFundFailList) {
				UserFund userFund = new UserFund();
				userFund.setUid(userFundFail.getUid());
				userFund.setType(userFundFail.getType());
				userFund.setMoney(userFundFail.getMoney());
				userFund.setAddtime((new Date().getTime()/1000));
				userFund.setUptime((new Date().getTime()/1000));
				String sourceStr = "来源于"+ TypeConvert.long1000ToDateStr(
						TypeConvert.strToZeroDate1000(TypeConvert.long1000ToDateStr(userFundFail.getAddtime()),0))+"。";
				String remark = DateUtils.dateTimeToString(new Date(), "yyyy-MM-dd HH:mm:ss")+"收入佣金"+BigDecimalUtils.round2(userFundFail.getMoney(),2)+"元。"+sourceStr;
				if(userFundFail.getTypeStatus()!= null && userFundFail.getTypeStatus() == 1){
					remark = DateUtils.dateTimeToString(new Date(), "yyyy-MM-dd HH:mm:ss")+userFundFail.getRemark().substring(19);
				}
				userFund.setRemark(remark);
				userFund.setPayStatus((short)1);
				userFund.setTypeStatus(1);
				userFundService.arrearsProcess(userFund);
				userFundFail.setRunStatus((short)1);
				userFundFail.setPayStatus((short)1);
				userFundFail.setUptime((new Date().getTime()/1000));
				this.update(userFundFail);  
			}
		}
	}
}
