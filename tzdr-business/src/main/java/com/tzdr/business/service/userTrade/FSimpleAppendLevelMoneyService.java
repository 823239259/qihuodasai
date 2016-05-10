package com.tzdr.business.service.userTrade;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tzdr.business.cms.service.auth.AuthService;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.utils.ConnditionVo;
import com.tzdr.common.utils.Dates;
import com.tzdr.domain.cms.entity.user.User;
import com.tzdr.domain.dao.userTrade.FSimpleAppendLevelMoneyDao;
import com.tzdr.domain.vo.FSimpleAppendLevelMoneyVo;
import com.tzdr.domain.web.entity.FSimpleAppendLevelMoney;

/**
 * 股指追加保证金 service
 * @WangPinQun
 * 2015年07月29日
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class FSimpleAppendLevelMoneyService extends BaseServiceImpl<FSimpleAppendLevelMoney,FSimpleAppendLevelMoneyDao> {
	
	@Autowired
	private AuthService authService;
	
	public void updateFSimpleAppendLevelMoney(String [] idArray,int status){
		for (String id:idArray){
			FSimpleAppendLevelMoney fSimpleAppendLevelMoney = super.get(id);
			if(fSimpleAppendLevelMoney.getStatus()==status){
				continue;
			}
			fSimpleAppendLevelMoney.setStatus(status);
			fSimpleAppendLevelMoney.setUpdateTime(Dates.getCurrentLongDate());
			User  loginUser = authService.getCurrentUser();
			fSimpleAppendLevelMoney.setUpdateUserId(loginUser.getId());
			fSimpleAppendLevelMoney.setUpdateUser(loginUser.getRealname());
			fSimpleAppendLevelMoney.setOperateContent("处理追加保证记录");
			super.update(fSimpleAppendLevelMoney);
		}
	}
	
	@SuppressWarnings("unchecked")
	public PageInfo<FSimpleAppendLevelMoneyVo> queryList(PageInfo<FSimpleAppendLevelMoneyVo> pageInfo, ConnditionVo connVo) {
		
		List<Object> params = new ArrayList<Object>();
		
		StringBuffer sql = new StringBuffer(" SELECT m.id,u.mobile,v.tname as realName,t.trader_bond as traderBond,t.tran_lever as tranLever,t.tran_account as tranAccount, ");
		sql.append(" t.tran_password as tranPassword,m.append_money as appendMoney,m.append_date as appendDate,m.update_time as handleDate,m.`status`,t.business_type ");
		sql.append(" FROM f_simple_append_level_money m ");
		sql.append(" LEFT JOIN f_simple_user_trade t ON m.program_no=t.program_no ");
		sql.append(" LEFT JOIN w_user u ON m.uid=u.id ");
		sql.append(" LEFT JOIN w_user_verified v ON m.uid=v.uid ");
		sql.append(" ORDER BY m.`status` ASC,m.append_date ASC ");

		return this.getEntityDao().queryPageBySql(pageInfo, sql.toString(), FSimpleAppendLevelMoneyVo.class, connVo, params.toArray());
	}
	
	@SuppressWarnings("unchecked")
	public List<FSimpleAppendLevelMoneyVo> queryList(ConnditionVo connVo) {
		
		List<Object> params = new ArrayList<Object>();
		
		StringBuffer sql = new StringBuffer(" SELECT m.id,u.mobile,v.tname as realName,t.trader_bond as traderBond,t.tran_lever as tranLever,t.tran_account as tranAccount, ");
		sql.append(" t.tran_password as tranPassword,m.append_money as appendMoney,m.append_date as appendDate,m.update_time as handleDate,m.`status`,t.business_type ");
		sql.append(" FROM f_simple_append_level_money m ");
		sql.append(" LEFT JOIN f_simple_user_trade t ON m.program_no=t.program_no ");
		sql.append(" LEFT JOIN w_user u ON m.uid=u.id ");
		sql.append(" LEFT JOIN w_user_verified v ON m.uid=v.uid ");
		sql.append(" ORDER BY m.`status` ASC,m.append_date ASC ");

		return this.getEntityDao().queryListBySql(sql.toString(), FSimpleAppendLevelMoneyVo.class, connVo, params.toArray());
	}
	
}
