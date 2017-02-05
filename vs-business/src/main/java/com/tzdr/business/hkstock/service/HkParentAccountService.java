package com.tzdr.business.hkstock.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.web.support.EasyUiPageInfo;
import com.tzdr.common.web.support.MultiListParam;
import com.tzdr.domain.hkstock.dao.HkParentAccountDao;
import com.tzdr.domain.hkstock.entity.HkParentAccount;


/**
 * 
 * @author zhouchen
 * 2015年10月16日 上午11:45:32
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class HkParentAccountService extends BaseServiceImpl<HkParentAccount,HkParentAccountDao> {
	
	/**
	 *  查询可用的 未被删除的 母账户
	 * @return
	 */
	public List<HkParentAccount>  findAvailableDatas(){
		return getEntityDao().findByDeletedFalse();
	}
	
	/**
	 * 提供后台获取数据
	 * @param easyUiPage
	 * @param searchParams
	 * @return
	 */
	public PageInfo<Object> getData(EasyUiPageInfo easyUiPage, Map<String, Object> searchParams){
		String sql="SELECT id, account_no AS accountNo, name, trade_channel AS tradeChannel FROM hk_parent_account ORDER BY account_no";
		MultiListParam multilistParam = new MultiListParam(easyUiPage,
				searchParams, Lists.newArrayList(), sql);
		return multiListPageQuery(multilistParam,HkParentAccount.class);
	}
}
