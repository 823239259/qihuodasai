package com.tzdr.business.service.account;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jodd.util.ObjectUtil;
import jodd.util.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.tzdr.business.cms.exception.ParentAccountException;
import com.tzdr.business.cms.service.auth.AuthService;
import com.tzdr.business.service.combine.CombineInfoService;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.exception.BusinessException;
import com.tzdr.common.utils.BigDecimalUtils;
import com.tzdr.common.utils.ConnditionVo;
import com.tzdr.common.utils.Dates;
import com.tzdr.common.utils.TypeConvert;
import com.tzdr.domain.cms.entity.user.User;
import com.tzdr.domain.dao.account.ParentAccountDao;
import com.tzdr.domain.vo.ParentAccountVo;
import com.tzdr.domain.web.entity.Account;
import com.tzdr.domain.web.entity.ParentAccount;

/**
 * 
 * @zhouchen
 * 2014年12月26日
 */
@Service
@Transactional
public class ParentAccountService extends BaseServiceImpl<ParentAccount,ParentAccountDao> {
	
	@Autowired
	private AuthService authService;
	
	@Autowired
	private AccountService  accountService;
	
	@Autowired
	private CombineInfoService  combineInfoService;
		
	public void setAuthService(AuthService authService) {
		this.authService = authService;
	}
	
	
	public PageInfo<ParentAccountVo> queryDataByPageInfo(PageInfo<ParentAccountVo> page,ConnditionVo conn) {
		StringBuffer sql = new StringBuffer(
				  " SELECT w.*,s.realname user FROM w_parent_account w "
				+ " LEFT JOIN sys_user s ON w.create_user_id = s.id  "
				+ " WHERE 1=1 AND w.deleted = FALSE");
		List<Object> params = new ArrayList<Object>();
		if (conn != null) {
			if (conn.getValueStr("accountName") != null) {
				sql.append(" AND w.account_name LIKE ?");
				params.add("%" + conn.getValueStr("accountName") + "%");
			}
			if (conn.getValueStr("accountNo") != null) {
				sql.append(" AND w.account_no LIKE ?");
				params.add("%" + conn.getValueStr("accountNo") + "%");
			}
			if (conn.getValueStr("accountType") != null) {
				sql.append(" AND w.account_type = ?");
				params.add(conn.getValueStr("accountType"));
			}
			if (conn.getValueStr("createDateStr_start") != null) {
				sql.append(" AND w.create_time > ?");
				params.add(TypeConvert.strToZeroDate1000(conn.getValueStr("createDateStr_start"),0) );
			}
			if (conn.getValueStr("createDateStr_end") != null) {
				sql.append(" AND  w.create_time < ?");
				params.add(TypeConvert.strToZeroDate1000(conn.getValueStr("createDateStr_end"),1,-1));
			}
			if (conn.getValueStr("user") != null) {
				sql.append(" AND s.realname LIKE ?");
				params.add("%" + conn.getValueStr("user") + "%");
			}
			if (conn.getValueStr("accountGenre") != null) {
				sql.append(" AND w.account_genre = ?");
				params.add(conn.getValueStr("accountGenre"));
			}
		}
		
		return this.getEntityDao().queryPageBySql(page,sql.toString(), ParentAccountVo.class, conn, params.toArray());
	}
	
	/**
	 * 查找账户
	 * @param account String
	 * @return Account
	 */
	public ParentAccount findByParentAccountId(String account) {
		Map<String,Object> equals = new HashMap<String,Object>();
		equals.put("accountNo", account);
		equals.put("deleted", false);
		List<ParentAccount> accounts = this.getEntityDao().queryBySimple(equals, null, null);
		if (accounts != null && accounts.size() > 0) {
			return accounts.get(0);
		}
		else {
			return null;
		}
	}

	@Override
	public void update(ParentAccount parentAccount) {
		String parentAccountId = parentAccount.getId();
		ParentAccount  dbAccount= getEntityDao().get(parentAccountId);
		if (ObjectUtil.equals(dbAccount, null)){
			throw new ParentAccountException("business.update.not.found.data",null);
		}
		
		String accountNo = parentAccount.getAccountNo();
		if (!StringUtil.equals(dbAccount.getAccountNo(),accountNo)){
			ParentAccount  tempParentAccount = getEntityDao().findByAccountNoAndDeletedFalse(parentAccount.getAccountNo());
			if (tempParentAccount!=null){
				throw new ParentAccountException("parent.account.save.account.number.exist",null);
			}
		}
		String unitNumber = parentAccount.getUnitNumber();
		String priorityNo = parentAccount.getPriorityNo();
		//钱江版
		if(parentAccount.getAccountGenre()==0){
			if (!StringUtil.equals(dbAccount.getUnitNumber(),unitNumber)){
				List<ParentAccount>  unitNumberList = getEntityDao().findByUnitNumberAndDeletedFalse(unitNumber);
				if (!CollectionUtils.isEmpty(unitNumberList)){
					throw new ParentAccountException("parent.account.update.unit.number.exist",new Object[]{unitNumber});
				}
			}
			
			if (!StringUtil.equals(dbAccount.getPriorityNo(),priorityNo)){
				List<ParentAccount>  priorityNoList = getEntityDao().findByPriorityNoAndDeletedFalse(priorityNo);
				if (!CollectionUtils.isEmpty(priorityNoList)){
					throw new ParentAccountException("parent.account.update.priority.no.exist",new Object[]{priorityNo});
				}
			}
		}
		
		
		dbAccount.setFundsBalance(BigDecimalUtils.addRound(BigDecimalUtils.subRound(parentAccount.getTotalFunds(),dbAccount.getTotalFunds()),dbAccount.getFundsBalance()));
		dbAccount.setAccountName(parentAccount.getAccountName());
		dbAccount.setAccountNo(parentAccount.getAccountNo());
		dbAccount.setAccountType(parentAccount.getAccountType());
		dbAccount.setPriorityNo(parentAccount.getPriorityNo());
		dbAccount.setSecuritiesBusiness(parentAccount.getSecuritiesBusiness());
		dbAccount.setSubFunds(parentAccount.getSubFunds());
		dbAccount.setTotalFunds(parentAccount.getTotalFunds());
		dbAccount.setUnitNumber(parentAccount.getUnitNumber());
		dbAccount.setAccountGenre(parentAccount.getAccountGenre());
		
		dbAccount.setAllocationDate(parentAccount.getAllocationDate());
		dbAccount.setAmountStart(parentAccount.getAmountStart());
		dbAccount.setAmountEnd(parentAccount.getAmountEnd());
		dbAccount.setMultipleStart(parentAccount.getMultipleStart());
		dbAccount.setMultipleEnd(parentAccount.getMultipleEnd());
		dbAccount.setNewAndOldState(parentAccount.getNewAndOldState());
		
		if (StringUtil.equals("A",parentAccount.getAccountType())){
			dbAccount.setStatus(parentAccount.getStatus());
		}
		if (StringUtil.equals("R",parentAccount.getAccountType())){
			dbAccount.setMultipleLimit(parentAccount.getMultipleLimit());
		}
		setOperateLog(dbAccount,"更新母账户","edit");
		super.update(dbAccount);
		
		//钱江版
		if(parentAccount.getAccountGenre()==0){		
			combineInfoService.saveAccountCombineInfo(accountNo,unitNumber);
		}
	}
	

	public  void updateParentAccount(ParentAccount parentAccount) {
		super.update(parentAccount);
	}
	
	@Override
	public void save(ParentAccount parentAccount) {
		ParentAccount  tempParentAccount = getEntityDao().findByAccountNoAndDeletedFalse(parentAccount.getAccountNo());
		if (tempParentAccount!=null){
			throw new ParentAccountException("parent.account.save.account.number.exist",null);
		}
		//钱江版
		if(parentAccount.getAccountGenre()==0){
			List<ParentAccount>  unitNumberList = getEntityDao().findByUnitNumberAndDeletedFalse(parentAccount.getUnitNumber());
			if (!CollectionUtils.isEmpty(unitNumberList)){
				throw new ParentAccountException("parent.account.save.unit.number.exist",null);
			}
			
			List<ParentAccount>  priorityNoList = getEntityDao().findByPriorityNoAndDeletedFalse(parentAccount.getPriorityNo());
			if (!CollectionUtils.isEmpty(priorityNoList)){
				throw new ParentAccountException("parent.account.save.priority.no.exist",null);
			}
		}
		parentAccount.setFundsBalance(parentAccount.getTotalFunds());
		if (!StringUtil.equals("A",parentAccount.getAccountType())){
			parentAccount.setStatus(Boolean.FALSE);
		}
		if (!StringUtil.equals("R",parentAccount.getAccountType())){
			parentAccount.setMultipleLimit(0);
		}
		
		setOperateLog(parentAccount,"新增母账户","add");
		super.save(parentAccount);
		//钱江版
		if(parentAccount.getAccountGenre()==0){			
			combineInfoService.saveAccountCombineInfo(parentAccount.getAccountNo(),parentAccount.getUnitNumber());
		}
	}
	

	public void saveParentAccount(ParentAccount parentAccount) {
		super.save(parentAccount);
	}

	@Override
    public void removes(Serializable... ids) throws BusinessException {
    	for (Serializable id : ids){
    		ParentAccount parentAccount = getEntityDao().get(id);
    		if (ObjectUtil.equals(parentAccount, null)){
    			throw new ParentAccountException("business.delete.not.found.data",null);
    		}
    		List<Account> accounts = 	accountService.getByParentAccount(parentAccount);
    		if (!CollectionUtils.isEmpty(accounts)){
    			for (Account account : accounts){
    				account.setDeleted(Boolean.TRUE);
    				accountService.updateAccount(account);
    			}
    		}
    		parentAccount.setDeleted(Boolean.TRUE);
    		setOperateLog(parentAccount,"删除母账户","edit");
    		super.update(parentAccount);
    	}
    }
 
	public List<ParentAccount>  findAvailableDatas(){
		return getEntityDao().findByDeletedFalse();
	}
	
	
	private void setOperateLog(ParentAccount parentAccount,String operateContent,String operateType){
		User   loginUser = authService.getCurrentUser();	
		parentAccount.setOperateContent(operateContent);
		if (StringUtil.equals(operateType,"edit")){
			parentAccount.setUpdateTime(Dates.getCurrentLongDate());
			parentAccount.setUpdateUser(loginUser.getRealname());
			parentAccount.setUpdateUserOrg(loginUser.getOrganization().getName());
			parentAccount.setUpdateUserId(loginUser.getId());
			return ;
		}
		
		parentAccount.setCreateTime(Dates.getCurrentLongDate());
		parentAccount.setCreateUser(loginUser.getRealname());
		parentAccount.setCreateUserOrg(loginUser.getOrganization().getName());
		parentAccount.setCreateUserId(loginUser.getId());
		return ;
	}
	/*
	 * 增加删除标志
	 * 
	*/
	public List<ParentAccount> findByAccountTypeOrderByPriorityNo(String accountType){
		return getEntityDao().findByAccountTypeAndDeletedFalseOrderByPriorityNoAsc(accountType);
	}
	
	public ParentAccount findByAccountNo(String accountNo){
		return getEntityDao().findByAccountNoAndDeletedFalse(accountNo);
	}
	
	public ParentAccount findByPriorityNoAndDeletedFalse(String priorityNo){
		 List<ParentAccount>  priorityNoList = getEntityDao().findByPriorityNoAndDeletedFalse(priorityNo);
		 if (!CollectionUtils.isEmpty(priorityNoList)){
			 return priorityNoList.get(0);
		 }
		 return null;
	}
	
	
	public List<ParentAccount>  findByAccountGenre(int accountGenre){
		return getEntityDao().findByDeletedFalseAndAccountGenre(accountGenre);
	}
}
