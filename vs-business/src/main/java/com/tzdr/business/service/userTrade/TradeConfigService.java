package com.tzdr.business.service.userTrade;

import java.util.List;

import jodd.util.ObjectUtil;
import jodd.util.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.tzdr.business.cms.exception.ParentAccountException;
import com.tzdr.business.cms.service.auth.AuthService;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.common.exception.BusinessException;
import com.tzdr.common.utils.BigDecimalUtils;
import com.tzdr.common.utils.Dates;
import com.tzdr.domain.cms.entity.user.User;
import com.tzdr.domain.dao.userTrade.TradeConfigDao;
import com.tzdr.domain.web.entity.TradeConfig;

/**
 * 
 * @zhouchen
 * 2014年12月26日
 */
@Service
public class TradeConfigService extends BaseServiceImpl<TradeConfig,TradeConfigDao> {
	
	@Autowired
	private AuthService authService;
	
	
	@Autowired
	private  TradeConfigDao tradeConfigDao;
	
	@Override
	public void update(TradeConfig config) throws BusinessException {
		TradeConfig  dbTradeConfig  = get(config.getId());
		if (ObjectUtil.equals(null,config)){
			throw new ParentAccountException("business.update.not.found.data",null);
		}
		dbTradeConfig.setDayRangeStart(config.getDayRangeStart());
		dbTradeConfig.setDayRangeEnd(config.getDayRangeEnd());
		dbTradeConfig.setDepositRangeStart(config.getDepositRangeStart());
		dbTradeConfig.setDepositRangeEnd(config.getDepositRangeEnd());
		dbTradeConfig.setMultipleRangeStart(config.getMultipleRangeStart());
		dbTradeConfig.setMultipleRangeEnd(config.getMultipleRangeEnd());
		dbTradeConfig.setYearInterest(config.getYearInterest());
		dbTradeConfig.setYearManagementFee(config.getYearManagementFee());
		
		dbTradeConfig.setDailyInterest(BigDecimalUtils.div(config.getYearInterest(),dbTradeConfig.getInterestDay(),5));
		dbTradeConfig.setDailyManagementFee(BigDecimalUtils.div(config.getYearManagementFee(),dbTradeConfig.getManagementFeeDay(),5));
		
		setOperateLog(dbTradeConfig,"更新参数信息","edit");
		super.update(dbTradeConfig);
	}
	
	@Override
	public void save(TradeConfig tradeConfig) throws BusinessException {
		tradeConfig.setDailyInterest(BigDecimalUtils.div(tradeConfig.getYearInterest(),tradeConfig.getInterestDay(),5));
		tradeConfig.setDailyManagementFee(BigDecimalUtils.div(tradeConfig.getYearManagementFee(),tradeConfig.getManagementFeeDay(),5));
	
		setOperateLog(tradeConfig,"新增配资参数","add");
		super.save(tradeConfig);
	}
	/**
	 * 根据条件查找配置
	 * @param day 使用天数
	 * @param deposit 保证金
	 * @param multiple 倍数
	 * @return
	 */
	public TradeConfig  findTradeConfig(Integer day,Double deposit,Integer multiple){
		return tradeConfigDao.findTradeConfig(day, deposit, multiple);
	}
	
	/**
	 * 设置计算管理费或利息的天数
	 * @param days
	 * @param type = 1 设置管理费天数，  type =2 设置利息天数
	 */
	public void updateDays(Double days,int type){
		List<TradeConfig>  tradeConfigs  = getAll();
		if (CollectionUtils.isEmpty(tradeConfigs)){
			return ;
		}
		
		if (1==type){
			for (TradeConfig config : tradeConfigs){
				config.setManagementFeeDay(days);
				config.setDailyManagementFee(BigDecimalUtils.div(config.getYearManagementFee(),days,5));
				setOperateLog(config,"更新计算管理费的天数","edit");
				update(config);
			}
			return;
		}
		
		
		if (2==type){
			for (TradeConfig config : tradeConfigs){
				config.setInterestDay(days);
				config.setDailyInterest(BigDecimalUtils.div(config.getYearInterest(),days,5));
				setOperateLog(config,"更新计算利息的天数","edit");
				update(config);
			}
			return;
		}
	}
	
	
	private void setOperateLog(TradeConfig tradeConfig,String operateContent,String operateType){
		User   loginUser = authService.getCurrentUser();	
		tradeConfig.setOperateContent(operateContent);
		if (StringUtil.equals(operateType,"edit")){
			tradeConfig.setUpdateTime(Dates.getCurrentLongDate());
			tradeConfig.setUpdateUser(loginUser.getRealname());
			tradeConfig.setUpdateUserOrg(loginUser.getOrganization().getName());
			tradeConfig.setUpdateUserId(loginUser.getId());
			return ;
		}
		
		tradeConfig.setCreateTime(Dates.getCurrentLongDate());
		tradeConfig.setCreateUser(loginUser.getRealname());
		tradeConfig.setCreateUserOrg(loginUser.getOrganization().getName());
		tradeConfig.setCreateUserId(loginUser.getId());
		return ;
	}
}
