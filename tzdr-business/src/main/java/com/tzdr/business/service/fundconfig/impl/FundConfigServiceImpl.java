package com.tzdr.business.service.fundconfig.impl;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import jodd.util.ObjectUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tzdr.business.exception.DataMapException;
import com.tzdr.business.service.datamap.DataMapService;
import com.tzdr.business.service.fundconfig.FundConfigService;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.common.exception.BusinessException;
import com.tzdr.common.utils.BigDecimalUtils;
import com.tzdr.common.utils.Dates;
import com.tzdr.domain.dao.fundconfig.FundConfigDao;
import com.tzdr.domain.web.entity.FundConfig;

/**
 * 配资倍数和金额实现层
 * @ClassName FundConfigServiceImpl
 * @author L.Y
 * @email meiluohai@163.com
 * @date 2015年4月27日
 */
@Service("fundConfigService")
@Transactional
public class FundConfigServiceImpl extends BaseServiceImpl<FundConfig, FundConfigDao> implements FundConfigService {
	public static final Logger logger = LoggerFactory
			.getLogger(DataMapService.class);

	@Override
	public void update(FundConfig fundConfig) throws BusinessException {
		FundConfig fc = null;
		Double fundAmount = null; //配资金额
		
		if (ObjectUtil.equals(null, fundConfig) 
				|| ObjectUtil.equals(null, fc = getEntityDao().get(fundConfig.getId())) 
				|| ObjectUtil.equals(null, fundAmount = fundConfig.getFundAmount())) {
			throw new DataMapException("business.update.not.found.data", null);
		}
		
		fc.setFundAmount(fundAmount);
		fc.setModifyDate(Dates.getCurrentLongDate());
//		fc.setTimes(times);
		fc.setCashDeposit(BigDecimalUtils.divRound(fundAmount, fc.getTimes())); //保证金 BigDecimalUtils.convertsToInt(fundAmount / times)
		super.update(fc);
	}

	@Override
	public void removes(Serializable... ids) throws BusinessException {
		if(ObjectUtil.equals(null, ids)) {
			throw new DataMapException("business.delete.not.found.data",
					null);
		}
//		fundConfig.setDeleted(Boolean.TRUE);
//		super.update(fundConfig);
		super.removeById(ids);
	}
	
	@Override
	public void save(FundConfig fundConfig) {
		Integer times = fundConfig.getTimes(); //倍数
		Double fundAmount = fundConfig.getFundAmount(); //配资金额
		
		if (ObjectUtil.equals(null, times) && ObjectUtil.equals(null, fundAmount)) {
			throw new DataMapException("business.add.data.is.null", null);
		}
		
		try {
			findAmountByTimes(times);//验证倍数唯一性
		} catch (Exception e) {
			throw new DataMapException("business.add.data.is.exists", null);
		}
		fundConfig.setCreateDate(Dates.getCurrentLongDate());
		fundConfig.setCashDeposit(BigDecimalUtils.divRound(fundAmount, times)); //保证金 BigDecimalUtils.convertsToInt(fundAmount / times)
		super.save(fundConfig);
	}
	
	@Override
	public int findMaxLever(double money) {
		String sql = "SELECT ifnull(wfc.times,0) FROM w_fund_config wfc WHERE wfc.cash_deposit>=? ORDER BY wfc.times DESC LIMIT 0,1";
		List<Object> params = new ArrayList<Object>();
		params.add(money);
		BigInteger b = (BigInteger) nativeQueryOne(sql, params);
		return b.intValue();
	};
	
	@Override
	public double findAmountByTimes(int times){
		FundConfig fundConfig=getEntityDao().findByTimes(times);
		if(fundConfig==null){
			return 0;
		}
		return fundConfig.getFundAmount();
	};
	
	@Override
	public List<FundConfig> findOrderByTimesAsc(){
		return getEntityDao().findByIdIsNotNullOrderByTimesAsc();
	}
}