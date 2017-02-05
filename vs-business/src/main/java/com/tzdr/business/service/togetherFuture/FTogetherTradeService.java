package com.tzdr.business.service.togetherFuture;

import java.util.List;
import java.util.Map;

import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.web.support.EasyUiPageInfo;
import com.tzdr.domain.app.vo.FTogetherTradeWebVo;
import com.tzdr.domain.app.vo.FtogetherLineDataVo;
import com.tzdr.domain.app.vo.UserFTogetherTradeVo;
import com.tzdr.domain.vo.TogetherConjunctureVo;
import com.tzdr.domain.web.entity.FTogetherTrade;
import com.tzdr.domain.web.entity.TogetherFuture;

/**
 * Created by huangkai on 2016/5/20.
 */
public interface FTogetherTradeService extends BaseService<FTogetherTrade> {
	
	/**
	 * 立即结算
	 * @param fTogetherTrade
	 */
	public void instantSettle(FTogetherTrade fTogetherTrade);
	
	/**
	 * 根据方案ID将未满单的份数进行退款
	 * @return
	 */
	public void refundNotFullCopies(FTogetherTrade fTogetherTrade);
	
	/**
	 * 获取合买方案列表
	 * @return
	 */
	public List<FTogetherTradeWebVo> queryTogetherTrades(Integer  pageNo);
	
	/**
	 * 查询胜率排行
	 * @return
	 */
	public List<Map<String, Object>> queryWinRank();
	
	
	/**
	 * 查询盈利率率排行
	 * @return
	 */
	public List<Map<String, Object>> queryProfitRank();
	
	/**
	 * 查询用户的盈利率排名
	 * @param uid
	 */
	public Map<String, Object> queryUserProfitRank(String uid);
	/**
	 * 根据Id查询方案详情
	 * @param tradeId
	 * @return
	 */
	public FTogetherTradeWebVo findTradeById(String tradeId);

	/**
	 * 获取数据
	 * @param easyUiPage
	 * @param searchParams
	 * @return
	 */
    public PageInfo<Object> queryTogetherTradeList(EasyUiPageInfo easyUiPage,
                                                   Map<String, Object> searchParams);

	/**
	 * 获得看涨记录合集
	 * @param easyUiPage
	 * @param searchParams
	 * @return
	 */
	public PageInfo<Object> getProfitValue(EasyUiPageInfo easyUiPage,
												   Map<String, Object> searchParams);


    /**
     * 获取用户的合买方案列表
     * @param uid
     * @return
     */
	public List<UserFTogetherTradeVo> queryUserTogetherTrades(String uid);
	
	/**
	 * 保存方案
	 * @param future
	 * @param name
	 * @param OpenTime
	 * @param operateTime
	 * @return
	 */
	public String add(TogetherFuture future,String name,Long OpenTime,Integer operateTime);
	
	/**
	 * 根据时间更新conjuncture表
	 * @param fTogetherTrade
	 */
	public void updateByTime(FTogetherTrade fTogetherTrade);
	
	/**
	 * 得到方案详情
	 * @param fTogetherTrade
	 * @return
	 */
	Map<String,Object> getTradeValue(FTogetherTrade fTogetherTrade);
	
	/**
	 * 根据方案id取方案行情详情
	 * @param TradeId
	 * @return
	 */
	public TogetherConjunctureVo getMarke(FTogetherTrade fTogetherTrade);
	
	
	/**
	 * 获取行情数据
	 * @return
	 */
	public FtogetherLineDataVo queryLineData(String tradeID);
	
	/**
	 * 校验是否新用户，未操盘A股、期货、期货合买 
	 * @param uid
	 * @return
	 */
	public boolean checkIsNewUser(Object uid);
	
	

	/**
	 * 检测是否在活动期间免除90元费用
	 * @param payMoney
	 * @return
	 */
	public  boolean  checkActivityTime();
}
