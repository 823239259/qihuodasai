package com.tzdr.business.hkstock.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.utils.Dates;
import com.tzdr.common.web.support.EasyUiPageInfo;
import com.tzdr.common.web.support.MultiListParam;
import com.tzdr.domain.cms.entity.user.User;
import com.tzdr.domain.hkstock.dao.HkFreeDiffDao;
import com.tzdr.domain.hkstock.entity.HkFreeDiff;

import com.tzdr.domain.hkstock.vo.HkFreeDiffVo;

/**
 * 
 * @author zhouchen
 * 2015年10月16日 上午11:45:32
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class HkFreeDiffService extends BaseServiceImpl<HkFreeDiff,HkFreeDiffDao> {
	
	public PageInfo<Object> getData(EasyUiPageInfo easyUiPage, Map<String, Object> searchParams) {
		String sql = "SELECT d.id,"
				+ "pa.name AS parentaccount,"
				+ "d.account,"
				+ "tra.group_id AS groupid,"
				+ "d.type,"
				+ "d.money,"
				+ "d.addtime,"
				+ "d.create_user AS lrr "
				+ "FROM hk_free_diff d,hk_parent_account pa,hk_user_trade tra "
				+ "WHERE tra.account_no = d.account "
				+ "AND "
				+ "pa.id=tra.parent_account_id "
				+ "ORDER BY d.addtime DESC";
		MultiListParam multilistParam = new MultiListParam(easyUiPage, searchParams, Lists.newArrayList(), sql);
		return multiListPageQuery(multilistParam, HkFreeDiffVo.class);
	}
	
	/**
	 * 判断是否已存在
	 * @param account
	 * @param addtime
	 * @param type
	 * @return
	 */
	public boolean exists(String account, Long addtime, String type){
		int count=getEntityDao().getCount(account, addtime, type);
		return count>0;
	}
	
	/**
	 * 批量新增
	 * @param list
	 * @return
	 */
	public String saveBatch(List<HkFreeDiff> list){
		for(HkFreeDiff freeDiff:list){
			if(exists(freeDiff.getAccount(), freeDiff.getAddtime().longValue(), freeDiff.getType())){
				return "类型为"+freeDiff.getTypevalue()+"的交易账户" + freeDiff.getAccount() +"在"+freeDiff.getCreatedate()+ "已存在";
			}
			save(freeDiff);
		}
		return "";
	}
}
