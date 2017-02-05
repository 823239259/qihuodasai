package com.tzdr.business.service.realdeal;

import java.util.List;

import javax.transaction.Transactional;

import jodd.util.ObjectUtil;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;
import com.tzdr.business.exception.RealDealException;
import com.tzdr.business.service.api.hundsun.HundsunJres;
import com.tzdr.common.api.hundsun.data.Realdeal;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.common.exception.EmailExceptionHandler;
import com.tzdr.common.utils.Dates;
import com.tzdr.common.utils.Exceptions;
import com.tzdr.domain.dao.realdeal.RealdealDao;
import com.tzdr.domain.web.entity.RealdealEntity;

/**
 * 
 * @zhouchen
 * 20?4年?2月26日
 */
@Service
@Transactional
public class RealDealService extends BaseServiceImpl<RealdealEntity,RealdealDao> {
	
	public static final Logger logger = LoggerFactory.getLogger(RealDealService.class);
	
	public void  batchSaveRealDeal(List<Object[]> list,long realDate){
		try 
		{
			String sql = "INSERT INTO w_realdeal(id,ampayoff_type,batch_no,business_amount,business_balance,"
					+"business_no,business_price,business_time,combine_id,entrust_direction,entrust_no,"
					+"exchange_type,fund_account,gh_fare,init_date,jy_fare,position_str,seat_no,stock_account,"
					+"stock_code,total_fare,yh_fare,yj_fare) "
					+ " VALUES (?,?, ?, ?, ?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			batchSave(sql, 1000, list);
		} catch (Exception e) {
			logger.error(Exceptions.getStackTraceAsString(e));
			// 回滚数据,即删除当天新增数据
			deleteByInitDate(realDate);
			throw new RealDealException("query.real.deal.fail",null);
		}
	}
	
	/**
	 * 查询具体某一天的 历史成交记录
	 * @param realDate
	 * @return
	 */
	public List<RealdealEntity> findByInitDate(long realDate){
		return getEntityDao().findByInitDate(realDate);
	}
	/**
	 * 删除某天的历史记录
	 * @param realDate
	 */
	public void deleteByInitDate(long realDate){
		String  sql = " delete from w_realdeal where init_date=?";
		List<Object> params = Lists.newArrayList();
		params.add(realDate);
		nativeUpdate(sql,params);
	}
	
	/**
	 * fundaccout 和 combineId 查询历史成交记录
	 * @param realDate
	 * @return
	 */
	public List<RealdealEntity> findByFundAccountAndCombineIdAndInitDateBetween(String fundAccount,String combineId,long start,long end){
		return getEntityDao().findByFundAccountAndCombineIdAndInitDateBetween(fundAccount,combineId,start,end);
	}
	
	
	public void  saveRealDeals(){
		Long  initDate = Dates.getBeforeTwoDaysLong();
		List<RealdealEntity> entities = this.findByInitDate(initDate);
		if (!CollectionUtils.isEmpty(entities)){
			return;
		}
		
		try {
			HundsunJres hundsunJres = HundsunJres.getInstance();
			String userToken = hundsunJres.Login();
			
			List<Realdeal> realdeals = 	 hundsunJres.funcAmRealdealHistoryQry(userToken,initDate,0,0);
			hundsunJres.Logout(userToken);
			
			if (CollectionUtils.isEmpty(realdeals)){
				return;
			}
			List<Object[]> list= Lists.newArrayList();
			for (Realdeal realdeal : realdeals){
				RealdealEntity  destEntity = new RealdealEntity();
				BeanUtils.copyProperties(destEntity, realdeal);		
				list.add(destEntity.getObjectArray());
			}
			
			if (CollectionUtils.isEmpty(list)){
				return;
			}
			this.batchSaveRealDeal(list, initDate);
			
		} catch (Exception e) {
			logger.error("历史明细定时任务查询失败；；"+Exceptions.getStackTraceAsString(e));
			EmailExceptionHandler.getInstance().HandleException(e, "历史明细定时任务查询失败。", this.getClass().getName()+".executeInternal");
		}
	}
	
	/**
	 * 获取账户 在某天购买的股票数
	 * @param realDate
	 * @param fundAccount
	 * @param combineId
	 * @return
	 */
	public String getAccountReaclNumber(String realDate,String fundAccount,String combineId){
		String sql = " SELECT   CAST(SUM(rea.business_amount) as CHAR)  from w_realdeal rea where rea.combine_id=? and rea.fund_account=? and rea.init_date=?";
		List<Object> params = Lists.newArrayList();
		params.add(combineId);
		params.add(fundAccount);
		params.add(realDate);
		Object object = nativeQueryOne(sql,params);
		 if (ObjectUtil.equals(null, object)){
				return "0";
		 }
		return String.valueOf(object);
	}
	
	/**
	 * 获取账户某天购买股票金额
	 * @param realDate
	 * @param fundAccount
	 * @param combineId
	 * @return
	 */
	public double getAccountReaclMoney(String realDate,String fundAccount,String combineId){
		 String sql = "SELECT SUM(rea.business_balance) from w_realdeal rea where rea.combine_id=? and rea.fund_account=? and rea.init_date=?";
		 List<Object> params = Lists.newArrayList();
		 params.add(combineId);
		 params.add(fundAccount);
		 params.add(realDate);
		 Object object = nativeQueryOne(sql,params);
		 if (ObjectUtil.equals(null, object)){
				return 0;
		 }
		 return  (double) object;
	}
}
