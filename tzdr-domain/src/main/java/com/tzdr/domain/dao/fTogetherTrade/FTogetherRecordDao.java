package com.tzdr.domain.dao.fTogetherTrade;

import java.util.List;

import com.tzdr.common.dao.BaseJpaDao;
import com.tzdr.domain.web.entity.FTogetherRecord;

/**
 * Created by huangkai on 2016/5/20.
 */
public interface FTogetherRecordDao extends BaseJpaDao<FTogetherRecord, String> {
	
	/**
	 * 查询用户具体方案具体方向是否已经参与过
	 * @param tradeID
	 * @param direction
	 * @param uid
	 * @return
	 */
	public FTogetherRecord findByTradeIdAndDirectionAndUid(String tradeID,Integer direction,String uid );
	
	
	/**
	 * 查询用户的所有合买记录
	 * @param tradeID
	 * @return
	 */
	public List<FTogetherRecord> findByTradeId(String tradeID);
	
	/**
	 * 查询用户的所有合买记录
	 * @param tradeID
	 * @return
	 */
	public List<FTogetherRecord> findByTradeIdAndUid(String tradeID,String uid);
}
