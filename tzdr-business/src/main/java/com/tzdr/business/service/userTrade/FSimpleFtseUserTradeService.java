package com.tzdr.business.service.userTrade;


import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.exception.BusinessException;
import com.tzdr.common.web.support.EasyUiPageInfo;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.domain.vo.FSimpleFtseUserTradeWebVo;
import com.tzdr.domain.vo.ftse.FSimpleFtseVo;
import com.tzdr.domain.web.entity.FSimpleFtseUserTrade;
import com.tzdr.domain.web.entity.WUser;
import com.tzdr.domain.web.entity.future.FSimpleCoupon;

/**
 * 
 * 
 * <p></p>
 * @author WangPinQun
 * @see 
 * FSimpleFtseUserTradeService
 * @version 2.0
 * 2015年9月16日下午14:33:13
 */
public interface FSimpleFtseUserTradeService extends BaseService<FSimpleFtseUserTrade> { 
	
	/**
	 * 
	 * @param fSimpleFtseUserTrade FSimpleFtseUserTrade
	 * @param mobile String
	 * @return FSimpleFtseUserTrade
	 */
	public FSimpleFtseUserTrade executePayable(FSimpleFtseUserTrade fSimpleFtseUserTrade,
			String mobile,BigDecimal payable)  throws Exception;
	
	/**
	 * 
	 * @param fSimpleFtseUserTrade FSimpleFtseUserTrade
	 * @param mobile String
	 * @param remark String
	 * @return FSimpleFtseUserTrade
	 */
	public FSimpleFtseUserTrade executePayable(FSimpleFtseUserTrade fSimpleFtseUserTrade,
			String mobile,BigDecimal payable,String remark,int businessType)  throws Exception;
	
	/**
	 * 使用了优惠券
	 * @param fSimpleFtseUserTrade
	 * @param coupon
	 * @param mobile
	 * @param payable
	 * @param remark
	 * @param businessType
	 * @return
	 * @throws Exception
	 */
	public FSimpleFtseUserTrade executePayable(FSimpleFtseUserTrade fSimpleFtseUserTrade, FSimpleCoupon coupon,
			String mobile,BigDecimal payable,String remark,int businessType)  throws Exception;
	
	public PageInfo<FSimpleFtseUserTradeWebVo> findDataList(String pageIndex,String perPage,String type,String uid);
	
	/**
	* @Title: findDataList    
	* @Description: 国际期货列表信息查询 
	* @param pageIndex
	* @param perPage
	* @param types  业务类型
	* @param uid    用户编号
	* @return
	 */
	public PageInfo<FSimpleFtseUserTradeWebVo> findDataList(String pageIndex,String perPage,String uid);

	/**
	 * 
	 * @param fSimpleFtseUserTrade FSimpleFtseUserTrade
	 * @param mobile String
	 * @return FSimpleFtseUserTrade
	 */
	public FSimpleFtseUserTrade activePayable(FSimpleFtseUserTrade fSimpleFtseUserTrade,
			String mobile,BigDecimal payable,String  uid)  throws Exception;
	
	/**
	 * 根据申请ID查询相应申请方案
	 * @param id
	 * @return
	 */
	FSimpleFtseUserTrade findById(String id) throws Exception;
	
	/**
	 * 页面列表的分页查询
	 * @param easyUiPage
	 * @param searchParams
	 * @param type
	 * @return
	 */
	PageInfo<Object> queryWellGoldDatas(EasyUiPageInfo easyUiPage,Map<String, Object> searchParams,int typeint ,int businessType) throws Exception;
	
	/**
	 * 申请方案审核通过，或者是修改
	 * @param id
	 * @param simpleFtseUserTrade
	 */
	JsonResult auditPass(String id, FSimpleFtseUserTrade simpleFtseUserTrade) throws Exception;
	
	/**
	 * 申请方案审核不通过处理
	 * @param id
	 */
	JsonResult auditNotPass(String id) throws Exception;
	
	/**
	 * 录入结算信息
	 * @param wellGoldA50
	 * @return
	 * @throws Exception
	 */
	JsonResult inputResult(FSimpleFtseVo wellGoldA50) throws Exception;
	
	/**
	 * 结算A50方案
	 * @param wellGoldA50
	 * @return
	 * @throws Exception
	 */
	JsonResult settlementA50(FSimpleFtseVo wellGoldA50) throws Exception;
	/**
	 * 用户是否有过A50申请方案
	 */
	public boolean isHave(String uid ,int type);

	FSimpleFtseUserTrade crudeActivePayable(
			FSimpleFtseUserTrade fSimpleFtseUserTrade, String mobile,
			BigDecimal payable, String uid) throws Exception;

	

	/**
	 * 追加保证金
	 * @param fSimpleUserTrade  方案信息
	 * @param appendMoney      追加金额
	 * @param wuser    用户帐号信息
	 * @throws Exception
	 */
	public void addAppendTraderBond(FSimpleFtseUserTrade fSimpleFtseUserTrade,Double appendMoney,String rate,Double dollar,WUser wuser) throws Exception;
	
	

	
	
	
	/**
	 * 国际期货合并--页面列表的分页查询
	 * @param easyUiPage
	 * @param searchParams
	 * @param type
	 * @return
	 */
	PageInfo<Object> queryInternationFutureDatas(EasyUiPageInfo easyUiPage,Map<String, Object> searchParams,int queryType) throws Exception;

	/**
	 * 收益报表查询
	 * @param easyUiPage
	 * @param searchParams
	 * @param type
	 * @return
	 */
	PageInfo<Object> queryPageDatas(EasyUiPageInfo easyUiPage,Map<String, Object> searchParams,int businessType) throws Exception;

	/**
	 * 终止操盘和更新优惠券
	 * @param fSimpleFtseUserTrade
	 * @param coupon
	 * @throws Exception
	 */
	void updateFSimpleFtseUserTradeAndFSimpleCoupon(FSimpleFtseUserTrade fSimpleFtseUserTrade, FSimpleCoupon coupon) throws BusinessException;
	
	/**
	 * 分配账号时，判断是否是已分配
	 */
	String passSaveAccount(String id);
	/**
	 * 获取用户首次操盘的第一条数据
	 * @param pageIndex
	 * @param perPage
	 * @param uid
	 * @return
	 */
	public PageInfo<FSimpleFtseUserTradeWebVo> findFristTradeDataByUid(String pageIndex,String perPage,String uid);
	
	/**
	 * 查询制定结算时间之间的结算数据 
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	public List<FSimpleFtseUserTrade> findLossPlan(Long beginTime,Long endTime);
}
