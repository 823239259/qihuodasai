package com.tzdr.business.service.togetherFuture;

import java.util.List;
import java.util.Map;

import com.tzdr.common.baseservice.BaseService;
import com.tzdr.domain.web.entity.FTogetherRecordDetail;

/**
 * Created by huangkai on 2016/5/20.
 */
public interface FTogetherRecordDetailService extends BaseService<FTogetherRecordDetail> {
	/**
	 * 根据方案ID查询对应按合买时间升序排列的合买记录
	 * @param tradeId
	 * @return
	 */
	public List<FTogetherRecordDetail> queryTogetherRecordDetails(String tradeId,Integer direction);

	/**
	 * 根据方案id 和 方向 查询合买总份数和退回份数
	 * @param tradeId
	 * @param direction
	 * @return
	 */
	public Map<String, Object> queryCopies(String tradeId,Integer direction);
	
	
	/**
	 * 根据方案id 和 方向 查询对应的合买记录
	 * @param tradeId
	 * @param direction
	 * @return
	 */
	public List<Map<String, Object>> queryRecords(String tradeId,Integer direction);
	
}
