package com.tzdr.business.service.account;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import jodd.util.ObjectUtil;
import jodd.util.StringUtil;

import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;
import com.tzdr.business.cms.exception.AccountException;
import com.tzdr.business.cms.exception.ParentAccountException;
import com.tzdr.business.cms.service.auth.AuthService;
import com.tzdr.business.cms.service.user.UserService;
import com.tzdr.business.service.combine.CombineInfoService;
import com.tzdr.business.service.datamap.DataMapService;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.exception.BusinessException;
import com.tzdr.common.utils.Dates;
import com.tzdr.common.web.support.EasyUiPageInfo;
import com.tzdr.common.web.support.MultiListParam;
import com.tzdr.domain.cms.entity.organization.Organization;
import com.tzdr.domain.cms.entity.user.User;
import com.tzdr.domain.constants.Constant;
import com.tzdr.domain.dao.account.AccountDao;
import com.tzdr.domain.vo.AccountVo;
import com.tzdr.domain.vo.WellGoldHandVo;
import com.tzdr.domain.web.entity.Account;
import com.tzdr.domain.web.entity.ParentAccount;

/**
 * 
 * @zhouchen
 * 2014年12月26日
 */
@Service
@Transactional
public class AccountService extends BaseServiceImpl<Account,AccountDao> {
	public static final Logger logger = LoggerFactory.getLogger(AccountService.class);
	@Autowired
	private AuthService authService;
	
	@Autowired
	private UserService userService;
		
	public void setAuthService(AuthService authService) {
		this.authService = authService;
	}

	@Autowired
	private ParentAccountService parentAccountService;
		
	public void setParentAccountService(ParentAccountService parentAccountServic) {
		this.parentAccountService = parentAccountServic;
	}
	
	@Autowired
	private CombineInfoService  combineInfoService;
	
	@Autowired
	private DataMapService dataMapService;
	
	@Override
	public void update(Account account) {
		String accountId = account.getId();
		Account  dbAccount= getEntityDao().get(accountId);
		if (ObjectUtil.equals(dbAccount, null)){
			throw new ParentAccountException("business.update.not.found.data",null);
		}
		String accountNo = account.getAccount();
		if (!StringUtil.equals(dbAccount.getAccount(),accountNo)){
			List<Account> accountList = getEntityDao().findByAccountAndDeletedFalseAndStatusIn(accountNo,getStatusInParams());
			if (!CollectionUtils.isEmpty(accountList)){
				throw new AccountException("account.update.account.number.exist",new Object[]{accountNo});
			}
		}
		
		
		String assetId = account.getAssetId();
		if (!StringUtil.equals(dbAccount.getAssetId(),assetId)){
			List<Account> accounts = getEntityDao().findByAssetIdAndDeletedFalseAndStatusIn(assetId,getStatusInParams());
			if (!CollectionUtils.isEmpty(accounts)){
				throw new AccountException("account.update.assert.id.exist",new Object[]{assetId});
			}
		}
		
		dbAccount.setAccount(account.getAccount());
		dbAccount.setPassword(account.getPassword());
		dbAccount.setAssetId(account.getAssetId());
		dbAccount.setAccountName(account.getAccountName());
		ParentAccount  parentAccount  = parentAccountService.get(account.getParentAccount().getId());
		dbAccount.setParentAccount(parentAccount);
		
		//获取组合信息
		combineInfoService.saveAccountCombineInfo(dbAccount);
				
				
		setOperateLog(dbAccount,"更新子账户","edit");
		super.update(dbAccount);
	}
	
	public void updateAccount(Account account) {
		super.update(account);
	}
	
	public List<Account>  findByAssetId(String assetid){
		List<Account> accounts = getEntityDao().findByAssetIdAndDeletedFalseAndStatusIn(assetid,getStatusInParams());
		return accounts;
	}
	
	
	public List<Account>  findByAccountNo(String accountNo){
		List<Account> accounts = getEntityDao().findByAccountAndDeletedFalseAndStatusIn(accountNo,getStatusInParams());
		return accounts;
	}
	
	
	@Override
	public void save(Account account) {
		List<Account> accounts = getEntityDao().findByAssetIdAndDeletedFalseAndStatusIn(account.getAssetId(),getStatusInParams());
		if (!CollectionUtils.isEmpty(accounts)){
			throw new AccountException("account.save.assert.id.exist",null);
		}
		
		
		List<Account> accountList = getEntityDao().findByAccountAndDeletedFalseAndStatusIn(account.getAccount(),getStatusInParams());
		if (!CollectionUtils.isEmpty(accountList)){
			throw new AccountException("account.save.account.number.exist",null);
		}
		
		ParentAccount  parentAccount  = parentAccountService.get(account.getParentAccount().getId());
		account.setParentAccount(parentAccount);
		
		//获取组合信息
		combineInfoService.saveAccountCombineInfo(account);
				
		setOperateLog(account,"新增子账户","add");
		super.save(account);
	}

	@Override
    public void removes(Serializable... ids) throws BusinessException {
    	for (Serializable id : ids){
    		Account account = getEntityDao().get(id);
    		if (ObjectUtil.equals(account, null)){
    			throw new AccountException("business.delete.not.found.data",null);
    		}
    		
    		if (NumberUtils.toShort("0") != account.getStatus()){
    			throw new AccountException("account.is.used.cant.delete",new Object[]{account.getAccountName(),account.getAccount()});
    		}
    		
    		account.setDeleted(Boolean.TRUE);
    		setOperateLog(account,"删除子账户","edit");
    		super.update(account);
    	}
    }
 
	/**
	 * 设置状态查询条件
	 * @return
	 */
	private List<Short> getStatusInParams(){
		return Lists.newArrayList(NumberUtils.toShort("0"),NumberUtils.toShort("1"),NumberUtils.toShort("2"));
	}
	
	private void setOperateLog(Account account,String operateContent,String operateType){
		User   loginUser = authService.getCurrentUser();	
		account.setOperateContent(operateContent);
		if (StringUtil.equals(operateType,"edit")){
			account.setUpdateTime(Dates.getCurrentLongDate());
			account.setUpdateUser(loginUser.getRealname());
			account.setUpdateUserOrg(loginUser.getOrganization().getName());
			account.setUpdateUserId(loginUser.getId());
			return ;
		}
		
		account.setCreateTime(Dates.getCurrentLongDate());
		account.setCreateUser(loginUser.getRealname());
		account.setCreateUserId(loginUser.getId());
		account.setCreateUserOrg(loginUser.getOrganization().getName());
		return ;
	}
	
	public  List<Account> findByParentAccountAndStatus(ParentAccount parentAccount,short status){
		return getEntityDao().findByParentAccountAndStatusAndDeletedFalse(parentAccount,status);		
	}
	
	public synchronized Account findOneByParentAccountAndStatus(ParentAccount parentAccount,short status){		 
		List<Account> accountList=findByParentAccountAndStatus(parentAccount,status);
		Account account=null;
		if(accountList.size()>0){
			//随机数据
			int random=(int)(Math.random()*accountList.size());
			account=accountList.get(random);
		}
		return account;
	}
	 
	
	public void batchImportData(List<Account> accounts){
		for (Account account : accounts){
			
			 List<Account> list = this.findByAccountNo(account.getAccount());
        	 if (!CollectionUtils.isEmpty(list)){
        		 throw new AccountException("account.import.account.no.exist",new Object[]{account.getAccount()}); 
        	 }
        	 
        	 List<Account> assetIdList = this.findByAssetId(account.getAssetId());
        	 if (!CollectionUtils.isEmpty(assetIdList)){
        		 throw new AccountException("account.import.assert.id.exist",new Object[]{account.getAccount(),account.getAssetId()}); 
        	 }
        	 
        	//获取组合信息
 			combineInfoService.saveAccountCombineInfo(account);
 			
			super.save(account);
		}
	}
	
	public void cancelAccount(String[] ids) {
		for (String id : ids) {
			Account  account = this.get(id);
			if (ObjectUtil.equals(null, account)) {
				throw new AccountException("根据accountId 未找到对应的数据",null);
			}

			account.setStatus(NumberUtils.toShort("3"));
			super.update(account);
		}
	}
	
	
	/**
	 * 获取账户列表
	 * @param easyUiPage
	 * @param searchParams
	 * @return
	 */
	public PageInfo<Object> queryList(EasyUiPageInfo  easyUiPage,Map<String, Object> searchParams) {
		PageInfo<Object> pageInfo = new PageInfo<Object>(easyUiPage.getRows(), easyUiPage.getPage());
		User   loginUser = authService.getCurrentUser();
		String code = loginUser.getOrganization().getCode();
		//params  查询参数  依次 存入
		List<Object> params = Lists.newArrayList();
		params.add(code+"%");
		String sql = "select temp.* from ((SELECT wus.mobile, uv.tname, ac.id, ac.`status` useStatus, ac.create_time createTime,"
				+ " ac.endtime, ac.account_name accountName, ac.account, ac.create_user createUser, "
				+ " ac.asset_id assetId, ac.insurance_no insuranceNo, pa.account_name parentAccountName FROM w_account ac, w_parent_account pa,"
				+ " sys_user us, sys_organization org, w_user_trade ut, w_user_verified uv, w_user wus WHERE "
				+ " ac.deleted = 0 AND pa.id = ac.parent_accound_id AND us.id = ac.create_user_id AND"
				+ " us.organization_id = org.id AND org.`code` LIKE ?  AND ut.uid = wus.id AND ac.`status` IN (1, 2, 3)"
				+ " AND wus.id = uv.uid AND ac.id = ut.account_id ";
		if (code.length() >= Organization.DEFAULT_CODE_LENGTH){
			sql = sql + " and  ac.create_user_id=? ";
			params.add(loginUser.getId());
		}
		params.add(code+"%");
		sql = sql + ") UNION ( SELECT '' mobile, '' tname, ac.id, ac.`status` useStatus, ac.create_time createTime,"
				+ " ac.endtime, ac.account_name accountName, ac.account, ac.create_user createUser, ac.asset_id assetId, ac.insurance_no insuranceNo,"
				+ " pa.account_name parentAccountName FROM w_account ac, w_parent_account pa, sys_user us, "
				+ " sys_organization org WHERE ac.deleted = 0 AND pa.id = ac.parent_accound_id "
				+ " AND us.id = ac.create_user_id AND ac.`status` = 0 AND us.organization_id = org.id "
				+ " AND org.`code` LIKE ?  ";		
		if (code.length() >= Organization.DEFAULT_CODE_LENGTH){
			sql = sql + " and  ac.create_user_id=? ";
			params.add(loginUser.getId());
		}
		sql = sql+" )) temp  ";
		
		MultiListParam  multilistParam  = new MultiListParam(easyUiPage, searchParams, params, sql);
		pageInfo = multiListPageQuery(multilistParam,AccountVo.class);
		return pageInfo;
	}
	
	
	public List<Account> getByParentAccount(ParentAccount parentAccount){
		return getEntityDao().findByParentAccountAndDeletedFalse(parentAccount);
	}
	
	public Account  findByAccountAndStatusAndDeletedFalse(String accountNo){
		List<Account> accounts = getEntityDao().findByAccountAndStatusAndDeletedFalse(accountNo,(short)0);
		if(CollectionUtils.isEmpty(accounts)){
			return null;
		}
		return accounts.get(0);
	}
	

	public void saveAccount(Account account) {
		List<Account> accounts = getEntityDao().findByAssetIdAndDeletedFalseAndStatusIn(account.getAssetId(),getStatusInParams());
		if (!CollectionUtils.isEmpty(accounts)){
			throw new AccountException("account.save.assert.id.exist",null);
		}
		
		
		List<Account> accountList = getEntityDao().findByAccountAndDeletedFalseAndStatusIn(account.getAccount(),getStatusInParams());
		if (!CollectionUtils.isEmpty(accountList)){
			throw new AccountException("account.save.account.number.exist",null);
		}
		
		ParentAccount  parentAccount  = parentAccountService.get(account.getParentAccount().getId());
		account.setParentAccount(parentAccount);
		
		//获取组合信息
		combineInfoService.saveAccountCombineInfo(account);
				
		setAccountOperateLog(account,"手工新增子账户","add");
		super.save(account);
	}
		
	private void setAccountOperateLog(Account account,String operateContent,String operateType){
		String username =  dataMapService.getAccountUserName();
		User   loginUser = userService.findByUsername(username);
		account.setOperateContent(operateContent);	
		account.setCreateTime(Dates.getCurrentLongDate());
		account.setCreateUser(loginUser.getRealname());
		account.setCreateUserId(loginUser.getId());
		account.setCreateUserOrg(loginUser.getOrganization().getName());
		return ;
	}
	
	/**
	 * 保存涌金版子账户
	 * @param account
	 */
	public Account saveWellGoldAccount(WellGoldHandVo wellGoldHandVo,int type) {
		
		List<Account> accountList = getEntityDao().findByAccountAndDeletedFalseAndStatusIn(wellGoldHandVo.getAccount(),getStatusInParams());
		if (type != Constant.HandTradeType.WELL_GOLD_TYPE && !CollectionUtils.isEmpty(accountList)){
			throw new AccountException("account.save.account.number.exist",null);
		}
		
		Account account = new Account();
		account.setStatus(NumberUtils.toShort("1"));
		account.setAccount(wellGoldHandVo.getAccount());
		account.setAccountName(wellGoldHandVo.getAccountName());
		account.setPassword(wellGoldHandVo.getPassword());		
		ParentAccount  parentAccount  = parentAccountService.get(wellGoldHandVo.getParentAccountId());
		account.setParentAccount(parentAccount);
		account.setInsuranceNo(wellGoldHandVo.getInsuranceNo()); //保险单号
		account.setAssetId(wellGoldHandVo.getAssetId());//单元序号
		
		String accountType = null;
		switch (type) {
		case Constant.HandTradeType.CASH_RIVER_TYPE: accountType = "钱江版";break;
		case Constant.HandTradeType.WELL_GOLD_TYPE: accountType = "涌金版";break;
		case Constant.HandTradeType.TIERCE_TYPE: accountType = "同花顺手动";break;
		}		
		setAccountOperateLog(account,"新增"+accountType+"子账户","add");
		
		// 保存帐号信息
		if(type==Constant.HandTradeType.CASH_RIVER_TYPE){
			saveAccount(account);
		}else{
			super.save(account);
		}
		return account;
	}
}
