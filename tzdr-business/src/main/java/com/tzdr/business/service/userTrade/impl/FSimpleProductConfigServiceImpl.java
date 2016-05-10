package com.tzdr.business.service.userTrade.impl;


import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.tzdr.business.service.userTrade.FSimpleProductConfigService;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.web.support.EasyUiPageInfo;
import com.tzdr.common.web.support.MultiListParam;
import com.tzdr.domain.dao.userTrade.FSimpleConfigDao;
import com.tzdr.domain.vo.cms.FSimpleConfigVo;
import com.tzdr.domain.web.entity.FSimpleConfig;

/**
 * 
 * @author LiuYang
 *
 * 2015年9月17日 下午4:55:32
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class FSimpleProductConfigServiceImpl extends
		BaseServiceImpl<FSimpleConfig, FSimpleConfigDao> implements FSimpleProductConfigService {

	@SuppressWarnings("unused")
	private static Logger log = LoggerFactory
			.getLogger(FSimpleProductConfigServiceImpl.class);

	@Override
	public FSimpleConfig getFSimpleConfigByType(Integer type) {
		if(type == null){
			return null;
		}
		List<FSimpleConfig> fSimpleConfigList = this.getEntityDao().findByType(type);
		FSimpleConfig fSimpleConfig = null;
		if(fSimpleConfigList != null && !fSimpleConfigList.isEmpty()){
			fSimpleConfig = fSimpleConfigList.get(0);
		}
		return fSimpleConfig;
	}

	@Override
	public PageInfo<Object> getData(EasyUiPageInfo easyUiPage, Map<String, Object> searchParams) {
		// TODO Auto-generated method stub
		String sql="SELECT id,"
				+ "type,"
				+ "type_name AS typeName,"
				+ "tran_fees AS tranFees,"
				+ "tran_fees_array AS tranFeesArray,"
				+ "fee_manage AS feeManage,"
				+ "tran_lever AS tranLever,"
				+ "trader_bond AS traderBond,"
				+ "trader_money AS traderMoney,"
				+ "line_loss AS lineLoss,"
				+ "update_user AS updateUser,"
				+ "update_time AS updateTime "
				+ "FROM f_simple_config "
				+ "WHERE type in (1,2,3,4)";
		
		MultiListParam multilistParam = new MultiListParam(easyUiPage,
				searchParams, Lists.newArrayList(), sql);
		
		return multiListPageQuery(multilistParam, FSimpleConfigVo.class);
	}
	
	
}
