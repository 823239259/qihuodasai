package com.tzdr.domain.dao.userTrade;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.tzdr.common.dao.BaseJpaDao;
import com.tzdr.domain.web.entity.FSimpleFtseUserTrade;

/**
 * 
 * 
 * <p></p>
 * @author WangPinQun
 * @see 富时A50抢险版
 * FSimpleFtseUserTradeDao
 * @version 2.0
 * 2015年9月16日下午14:33:13
 */
public interface FSimpleFtseUserTradeDao extends BaseJpaDao<FSimpleFtseUserTrade, String> {
	List<FSimpleFtseUserTrade> findById(String id);
	List<FSimpleFtseUserTrade> findByUid(String uid);
	List<FSimpleFtseUserTrade> findByUidAndBusinessType(String id,int type);
	@Query(value = "select tran_fees,end_actual_money,end_actual_money,voucher_id,voucher_money,voucher_actual_money,discount_actual_money,discount_money,discount_id,discount_actual_money,"
			 + "app_time,source,business_type,golden_money, update_time,state_type,use_tran_day,end_time,end_parities,end_amount_cal,tran_profit_loss,tran_fees_total,app_end_time,tran_password,"
			 + "tran_account,app_starttime,ag_tran_fees,lhsi_tran_fees,md_tran_fees,nikkei_tran_fees,dax_tran_fees,mb_tran_fees,mn_tran_fees,hsi_tran_fees,crude_tran_fees,fee_manage,line_loss,trader_total,"
			 + "tran_lever,append_trader_bond,program_no,ag_tran_fees,id,deleted,version,"
			 + "IF(SUM(tran_actual_lever) IS NULL,0,SUM(tran_actual_lever)) AS tran_actual_lever,"
			 + "IF(SUM(crude_tran_actual_lever) IS NULL ,0,SUM(crude_tran_actual_lever)) AS crude_tran_actual_lever,"
		     + "IF(SUM(hsi_tran_actual_lever) IS NULL,0,SUM(hsi_tran_actual_lever)) AS hsi_tran_actual_lever,"
		     + "IF(SUM(mdtran_actual_lever) IS NULL , 0 ,SUM(mdtran_actual_lever)) AS mdtran_actual_lever,"
		     + "IF(SUM(mntran_actual_lever) IS NULL ,0 ,SUM(mntran_actual_lever)) AS mntran_actual_lever,"
		     + "IF(SUM(mbtran_actual_lever) IS NULL ,0 ,SUM(mbtran_actual_lever)) AS mbtran_actual_lever,"
		     + "IF(SUM(daxtran_actual_lever) IS NULL , 0 ,SUM(daxtran_actual_lever)) AS daxtran_actual_lever,"
		     + "IF(SUM(nikkei_tran_actual_lever) IS NULL ,0 ,SUM(nikkei_tran_actual_lever)) AS nikkei_tran_actual_lever,"
		     + "IF(SUM(ag_tran_actual_lever) IS NULL , 0 ,SUM(ag_tran_actual_lever)) AS ag_tran_actual_lever,"
		     + "IF(SUM(lhsi_tran_actual_lever) IS NULL , 0 ,SUM(lhsi_tran_actual_lever)) AS lhsi_tran_actual_lever,"
		     + "IF(SUM(end_amount) IS NULL ,0,SUM(end_amount)) AS end_amount ,"
		     + "IF(SUM(trader_bond) IS NULL,0, SUM(trader_bond)) AS trader_bond ,"
		     + "uid FROM f_simple_ftse_user_trade WHERE state_type = 6 AND  end_time BETWEEN ?1 AND ?2  GROUP BY uid",nativeQuery = true)
	List<FSimpleFtseUserTrade> findLossPlan(Long beginTime,Long endTime);
	@Query("from FSimpleFtseUserTrade where tranProfitLoss < 0 and uid = ?1")
	List<FSimpleFtseUserTrade> findByUidFristLoss(String uid);
	
	@Query(value="from FSimpleFtseUserTrade where stateType=4 and uid=?1")
	List<FSimpleFtseUserTrade> findByUidAndStateType(String uid);
}