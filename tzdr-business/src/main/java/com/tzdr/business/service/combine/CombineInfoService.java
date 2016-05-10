package com.tzdr.business.service.combine;

import java.util.List;

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
import com.hundsun.t2sdk.interfaces.T2SDKException;
import com.tzdr.business.exception.CombineInfoException;
import com.tzdr.business.service.account.ParentAccountService;
import com.tzdr.business.service.api.hundsun.HundsunJres;
import com.tzdr.common.api.hundsun.data.Combinfo;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.domain.dao.combine.CombineInfoDao;
import com.tzdr.domain.web.entity.Account;
import com.tzdr.domain.web.entity.CombineInfo;
import com.tzdr.domain.web.entity.ParentAccount;

/**
 * 
 * @zhouchen 2014年12月26日
 */
@Service
@Transactional
public class CombineInfoService extends
		BaseServiceImpl<CombineInfo, CombineInfoDao> {
	public static final Logger logger = LoggerFactory
			.getLogger(CombineInfoService.class);

	@Autowired
	private ParentAccountService  parentAccountService;
	/**
	 * 获取combine信息
	 */
	public void refreshCombineInfos() {
		List<ParentAccount> accounts = parentAccountService.findByAccountGenre(0);
		if (CollectionUtils.isEmpty(accounts)){
			return ;
		}
		
		HundsunJres hundsunJres = null;
		String userToken = null;
		try {
			hundsunJres = HundsunJres.getInstance();
			userToken = hundsunJres.Login();
			for (ParentAccount parentAccount:accounts){
				
				String fundAccount =  parentAccount.getAccountNo();
				
				List<Combinfo> combinfoList = hundsunJres.funcAmCombinfoQry(
						userToken,fundAccount, null);
				if (CollectionUtils.isEmpty(combinfoList) 
						|| StringUtil.isBlank(combinfoList.get(0).getFundAccount())) {
					continue;
				}
				List<CombineInfo>  combineInfos = getEntityDao().findByFundAccount(fundAccount);
				if (!CollectionUtils.isEmpty(combineInfos)){
					getEntityDao().deleteInBatch(combineInfos);
				}
				
				
				List<CombineInfo> list = Lists.newArrayList();
				for (Combinfo combinfo : combinfoList) {
					CombineInfo combineInfo  = new CombineInfo(combinfo);
					list.add(combineInfo);
					if (list.size()==500){
						saves(list);
						list.clear();
					}
				}
				saves(list);	
			}
					
		} catch (T2SDKException e) {
			logger.error("恒生接口调用失败。", e);
			throw new CombineInfoException("refresh.combine.info.fail",null);
		}
		finally {
			if (!ObjectUtil.equals(null, hundsunJres)
					&& StringUtil.isNotBlank(userToken)) {
				hundsunJres.Logout(userToken);
			}
		}
	}
	
	/**
	 * 新增修改子账户时，根据子账户 单元序号 和 母账户编号  查询 并保存
	 * @param account
	 */
	public void saveAccountCombineInfo(Account  account){
		String fundAccount = account.getParentAccount().getAccountNo();
		Long assetId = NumberUtils.toLong(account.getAssetId());
		
		CombineInfo combineInfo = getEntityDao().findByAssetIdAndFundAccount(assetId, fundAccount);
		if (!ObjectUtil.equals(null, combineInfo)){
			return ;
		}
		
		HundsunJres hundsunJres = null;
		String userToken = null;
		try {
			hundsunJres = HundsunJres.getInstance();
			userToken = hundsunJres.Login();
			List<Combinfo> combinfoList = hundsunJres.funcAmCombinfoQry(
					userToken,fundAccount, null);
			if (CollectionUtils.isEmpty(combinfoList)) {
				logger.error("根据母账户编号未能找到对应的组合信息");
				throw new CombineInfoException("update.combine.info.parent.account.not.find.data",new Object[]{fundAccount});
			}
	
			boolean  flag = false;
			for (Combinfo combinfo : combinfoList) {
				if (assetId.equals(combinfo.getAssetId())){
					save(new CombineInfo(combinfo));
					flag = true;
					break;
				}
			}
			
			//未找到相应的组合信息
			if (!flag){
				logger.error("根据母账户编号和子账户单元序号未能找到对应的组合信息");
				throw new CombineInfoException("update.combine.info.assetid.not.find.data",new Object[]{fundAccount,account.getAssetId()});
			}
		} catch (T2SDKException e) {
			logger.error("恒生接口调用失败。", e);
			throw new CombineInfoException("refresh.combine.info.fail",null);
		}
		finally {
			if (!ObjectUtil.equals(null, hundsunJres)
					&& StringUtil.isNotBlank(userToken)) {
				hundsunJres.Logout(userToken);
			}
		}
	}
	
	public  String getHundSunCombineId(String assetId){
		if (StringUtil.isBlank(assetId)){
			return null;
		}
		
		CombineInfo combineInfo  = getEntityDao().findByAssetId(NumberUtils.toLong(assetId));
		if (ObjectUtil.equals(null, combineInfo)){
			return null;
		}
		return combineInfo.getCombineId();
	}
	
	
	
	/**
	 * 新增修改母账户账户时，根据账户 单元序号 和 母账户编号  查询 并保存
	 * @param account
	 */
	public void saveAccountCombineInfo(String fundAccount,String assetid){
	
		Long assetId = NumberUtils.toLong(assetid);
		CombineInfo combineInfo = getEntityDao().findByAssetIdAndFundAccount(assetId, fundAccount);
		if (!ObjectUtil.equals(null, combineInfo)){
			return ;
		}
		
		HundsunJres hundsunJres = null;
		String userToken = null;
		try {
			hundsunJres = HundsunJres.getInstance();
			userToken = hundsunJres.Login();
			List<Combinfo> combinfoList = hundsunJres.funcAmCombinfoQry(
					userToken,fundAccount, null);
			if (CollectionUtils.isEmpty(combinfoList)) {
				logger.error("根据母账户编号未能找到对应的组合信息");
				throw new CombineInfoException("update.combine.info.parent.account.not.find.data",new Object[]{fundAccount});
			}
	
			boolean  flag = false;
			for (Combinfo combinfo : combinfoList) {
				if (assetId.equals(combinfo.getAssetId())){
					save(new CombineInfo(combinfo));
					flag = true;
					break;
				}
			}
			
			//未找到相应的组合信息
			if (!flag){
				logger.error("根据母账户编号和管理单元序号未能找到对应的组合信息");
				throw new CombineInfoException("update.combine.info.assetid.not.find.data",new Object[]{fundAccount,assetid});
			}
		} catch (T2SDKException e) {
			logger.error("恒生接口调用失败。", e);
			throw new CombineInfoException("refresh.combine.info.fail",null);
		}
		finally {
			if (!ObjectUtil.equals(null, hundsunJres)
					&& StringUtil.isNotBlank(userToken)) {
				hundsunJres.Logout(userToken);
			}
		}
	}
}
