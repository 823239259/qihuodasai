package com.tzdr.business.service.togetherFuture;

import java.math.BigDecimal;
import java.util.List;

import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.utils.ConnditionVo;
import com.tzdr.domain.app.vo.UserFTogetherRecordVo;
import com.tzdr.domain.vo.FTogetherRecordVo;
import com.tzdr.domain.web.entity.FTogetherRecord;
import com.tzdr.domain.web.entity.WUser;

/**
 * Created by huangkai on 2016/5/20.
 */
public interface FTogetherRecordService extends BaseService<FTogetherRecord> {
	

	/**
	 * 查询用户具体方案的合买记录
	 * @param tradeID
	 * @return
	 */
	public List<FTogetherRecord> findByTradeIdAndUid(String tradeID,String uid);
	
	
	/**
	 * 查询用户的所有合买记录
	 * @param tradeID
	 * @return
	 */
	public List<FTogetherRecord> findByTradeId(String tradeID);
	/**
	 * 查询用户具体方案具体方向是否已经参与过
	 * @param tradeID
	 * @param direction
	 * @param uid
	 * @return
	 */
	public FTogetherRecord findByTradeIdAndDirectionAndUid(String tradeID,Integer direction,String uid);
	
	/**
	 * 查询用户的某个具体合买记录
	 * @param uid
	 * @param recordId
	 * @return
	 */
	public  UserFTogetherRecordVo  queryUserTogetherRecord(String uid,String recordId); 
	
	/**
	 * 支付时生成参与记录
	 * @param fTogetherRecord
	 */
	public void createTogetherRecord(FTogetherRecord fTogetherRecord,BigDecimal payMoney,Integer copies,WUser wuser);
	/**
	 * 页面分页
	 * @param dataPage
	 * @param connVo
	 * @return
	 */
	public PageInfo<FTogetherRecordVo> getDatas(PageInfo<FTogetherRecordVo> dataPage,ConnditionVo connVo);
	/**
	 * 生产excel
	 * @param connVo
	 * @return
	 */
	public List<FTogetherRecordVo> getRecordVos(ConnditionVo connVo);
}
