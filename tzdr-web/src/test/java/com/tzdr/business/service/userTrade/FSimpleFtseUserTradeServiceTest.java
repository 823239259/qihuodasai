package com.tzdr.business.service.userTrade;


import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import com.tzdr.domain.web.entity.FSimpleConfig;
import com.tzdr.domain.web.entity.FSimpleFtseUserTrade;
import com.tzdr.domain.web.entity.FSimpleParities;

@ContextConfiguration(locations = { "/applicationContext-test.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class FSimpleFtseUserTradeServiceTest {

	@Autowired
	private FSimpleFtseUserTradeService fSimpleFtseUserTradeService;
	
	@Autowired
	private FSimpleConfigService fSimpleConfigService;
	
	@Autowired
	private FSimpleParitiesService fSimpleParitiesService;
//	@Test
	@Ignore
	public void saveFSimpleParities(){
		FSimpleParities fSimpleParities = new FSimpleParities();
		fSimpleParities.setType(0);
		fSimpleParities.setTypeName("富时A50");
		fSimpleParities.setParities(new BigDecimal(6.3122));
		fSimpleParities.setCreateTime(new Date().getTime()/1000);
		fSimpleParities.setCreateUser("admin");
		fSimpleParities.setCreateUserId(1l);
		fSimpleParitiesService.save(fSimpleParities);
	}
	
//	@Test
	@Ignore
	public void saveFSimpleConfig(){
		FSimpleConfig fSimpleConfig = new FSimpleConfig();
		fSimpleConfig.setType(0);
		fSimpleConfig.setTypeName("富时A50");
		fSimpleConfig.setTranFees(new BigDecimal(90.00));
		fSimpleConfig.setFeeManage(new BigDecimal(0.00));
		fSimpleConfig.setTraderBond(new BigDecimal(3000.00));
		fSimpleConfig.setTraderMoney(new BigDecimal(2750.00));
		fSimpleConfig.setLineLoss(new BigDecimal(2500.00));
		fSimpleConfig.setTranLever("1,3,5,8,10");
		fSimpleConfig.setCreateTime(new Date().getTime()/1000);
		fSimpleConfig.setCreateUser("admin");
		fSimpleConfig.setCreateUserId(1l);
		fSimpleConfigService.save(fSimpleConfig);
	}
	
//	@Test
	@Ignore
	public void saveNewFSimpleConfig(){
		FSimpleConfig fSimpleConfig = new FSimpleConfig();
		fSimpleConfig.setType(5);
		fSimpleConfig.setTypeName("富时A50");
		fSimpleConfig.setTranFees(new BigDecimal(78.00));
		fSimpleConfig.setFeeManage(new BigDecimal(0.00));
		fSimpleConfig.setTraderBond(new BigDecimal(33000.00));
		fSimpleConfig.setTraderMoney(new BigDecimal(64500.00));
		fSimpleConfig.setLineLoss(new BigDecimal(60000.00));
		fSimpleConfig.setTranLever("24");
		fSimpleConfig.setCreateTime(new Date().getTime()/1000);
		fSimpleConfig.setCreateUser("admin");
		fSimpleConfig.setCreateUserId(1l);
		fSimpleConfig.setGoldenMoney(new BigDecimal(215));
		fSimpleConfigService.save(fSimpleConfig);
	}
	
	@Test
//	@Ignore
	public void find(){
		/*FSimpleConfig fSimpleConfig = fSimpleConfigService.getFSimpleConfigByType(0);
		Assert.notNull(fSimpleConfig);*/
		
		/*FSimpleParities fSimpleParities = fSimpleParitiesService.getFSimpleParities(0);
		Assert.notNull(fSimpleParities);*/
		
		/*List<FSimpleConfig> fSimpleConfigList = fSimpleConfigService.findFSimpleConfigsByType(5);
		Assert.notNull(fSimpleConfigList);*/
		
		FSimpleConfig fSimpleConfig = fSimpleConfigService.getFSimpleConfig(5,"4");
		Assert.notNull(fSimpleConfig);
	}
	
//	@Test
	@Ignore
	public void updateFSimpleConfig(){
		FSimpleFtseUserTrade fSimpleFtseUserTrade =  fSimpleFtseUserTradeService.get("40288aff4fd95ee2014fd98f23b70000");
		if(fSimpleFtseUserTrade != null && fSimpleFtseUserTrade.getStateType() == 1){
			fSimpleFtseUserTrade.setStateType(4);
			fSimpleFtseUserTrade.setAppStarttime(new Date().getTime()/1000);
			fSimpleFtseUserTrade.setTranAccount("tzdr009");
			fSimpleFtseUserTrade.setTranPassword("a123456");
			fSimpleFtseUserTrade.setUpdateTime(new Date().getTime()/1000);
			fSimpleFtseUserTradeService.update(fSimpleFtseUserTrade);
		}else if(fSimpleFtseUserTrade != null && (fSimpleFtseUserTrade.getStateType() == 2 || fSimpleFtseUserTrade.getStateType() == 4)){
			FSimpleParities fSimpleParities = fSimpleParitiesService.getFSimpleParities(0);
			BigDecimal endParities = fSimpleParities != null ? fSimpleParities.getParities() : new BigDecimal(6.3122);
			if(fSimpleFtseUserTrade.getStateType() == 4 ){
				if(fSimpleParities != null){
					fSimpleFtseUserTrade.setEndParities(endParities);
				}
			}
			if(fSimpleFtseUserTrade.getEndParities() == null){
				fSimpleFtseUserTrade.setEndParities(endParities);
			}
			Integer tranActualLever = 2;
			BigDecimal tranFeesTotal = fSimpleFtseUserTrade.getTranFees().multiply(new BigDecimal(tranActualLever), MathContext.DECIMAL32);
			BigDecimal tranProfitLoss = new BigDecimal(1000.00);
			BigDecimal actualTranProfitLoss = tranProfitLoss.multiply(endParities, MathContext.DECIMAL32);
			fSimpleFtseUserTrade.setEndAmount(fSimpleFtseUserTrade.getTraderBond().add(actualTranProfitLoss).subtract(tranFeesTotal));
			fSimpleFtseUserTrade.setEndTime(new Date().getTime()/1000);
			fSimpleFtseUserTrade.setStateType(3);
			fSimpleFtseUserTrade.setTranActualLever(tranActualLever);
			fSimpleFtseUserTrade.setTranFeesTotal(tranFeesTotal);
			fSimpleFtseUserTrade.setTranProfitLoss(tranProfitLoss);
			fSimpleFtseUserTrade.setUseTranDay(3);
			fSimpleFtseUserTradeService.update(fSimpleFtseUserTrade);
		}
	}
}
