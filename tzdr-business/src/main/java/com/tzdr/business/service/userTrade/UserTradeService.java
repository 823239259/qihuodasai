package com.tzdr.business.service.userTrade;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.hundsun.t2sdk.interfaces.T2SDKException;
import com.tzdr.common.api.hundsun.data.Combostock;
import com.tzdr.common.api.hundsun.data.StockCurrent;
import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.utils.ConnditionVo;
import com.tzdr.common.web.support.EasyUiPageInfo;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.domain.vo.EndMoneyVo;
import com.tzdr.domain.vo.EndProgramVo;
import com.tzdr.domain.vo.MonitorSchemeVo;
import com.tzdr.domain.vo.MonthEndVo;
import com.tzdr.domain.vo.NotEnoughBalanceVo;
import com.tzdr.domain.vo.PositionDetailsVo;
import com.tzdr.domain.vo.SettingNotWarningVo;
import com.tzdr.domain.vo.SettingWarningVo;
import com.tzdr.domain.vo.UserTradeCmsVo;
import com.tzdr.domain.vo.UserTradeSummaryVo;
import com.tzdr.domain.vo.UserTradeTransfer;
import com.tzdr.domain.vo.UserTradeVo;
import com.tzdr.domain.web.entity.ParentAccount;
import com.tzdr.domain.web.entity.UserTrade;
import com.tzdr.domain.web.entity.WUser;

/**
* @Description: TODO(配资业务信息管理业务接口)
* @ClassName: TradeService
* @author wangpinqun
* @author QingLiu
* @date 2015年1月4日 下午7:35:29
 */
public interface UserTradeService extends BaseService<UserTrade> {

	/**
	* @Description: TODO(根据条件获取配资列表信息)
	* @Title: findByWuserAndStatusOrderByAddtimeDesc
	* @param uid     用户编号
	* @param status  配资状态
	* @return
	* @return List<UserTrade>    返回类型
	 */
	public List<UserTrade> findByWuserAndStatusOrderByAddtimeDesc(String uid,short status);
	
	/**
	* @Description: TODO(根据条件获取用户配资累计盈亏)
	* @Title: getSumAccrualByWuserAndStatus
	* @param uid  用户编号
	* @param status  配资状态
	* @return Double    返回类型
	 */
	public Double getSumAccrualByWuserAndStatus(String uid,short status);
	
	/**
	* @Description: TODO(根据条件获取用户配资信息)
	* @Title: queryUserTradeVoByWuserAndStatus
	* @param uid   用户编号
	* @param status  状态
	* @return List<UserTradeVo>    返回类型
	 */
	public List<UserTradeVo> queryUserTradeVoByWuserAndStatus(String uid,short status,short status1);
	
	
	/**
	 * 获取补仓提醒 列表数据
	 * @return
	 * @throws T2SDKException 
	 */
	public List<UserTradeCmsVo> queryMarginRemindData(EasyUiPageInfo easyUiPage,
			Map<String, Object> searchParams);
	
	/**
	 * 获取账户下的方案
	 * @param account
	 * @return
	 */
	public List<UserTrade> findByAccount(String account);
	
	/**
	 * 修改方案 补仓提醒状态
	 * @param account
	 * @return
	 */
	public void  changeNoticeStatus(String [] groupIds,Short noticeStatus);
	
	
	/**
	 * 查找满足扣费的 方案
	 * @param status
	 * @param Starttime 交易开始时间
	 * @return
	 */
	public List<UserTrade> findByStatusAndStarttime(short status,Long Starttime);
	
	
	/**
	 * 查找满足扣费的 方案【开户中，且操盘开始时间小于或等于当天】
	 * @param status  操盘状态
	 * @param auditStatus 开户审核状态
	 * @param Starttime 交易开始时间
	 * @return
	 */
	public List<UserTrade> findOpeningAccountUserTrade(short status,short auditStatus, Long Starttime);
	
	/**
	 * 查找满足扣费的 方案
	 * @param statusOne 操盘中状态
	 * @param statusTwo 开户中状态
	 * @param auditStatus  开户审核中状态
	 * @param Starttime    交易开始时间
	 * @return
	 */
	public List<UserTrade> findSymbolDeductionUserTrade(short statusOne,short statusTwo,short auditStatus,Long Starttime);
	
	
	/**
	 * 增加方案并打通恒生帐户
	 * @param account
	 * @return
	 */
	public UserTradeTransfer  addUserTradeAndOpenHundsun(UserTrade userTrade,WUser user,String volumeDetailId);
	
	/**
	 * 获取没有欠费的用户，和对应的配资方案id
	 * @return
	 */
	public List<UserTradeCmsVo> getNoArrearsUserTrades();

	
	/**
	 * 获取用户的配资方案包括（status 1 和 2）
	 * @return
	 */
	public PageInfo<Object> queryUserTrade(String uid,int countOfCurrentPage, int currentPage,Map<String, Object> searchParams);
	/**
	 * 获取用户的合买配资方案包括
	 */
	public PageInfo<Object> queryUserTogetherTrade(String uid, int countOfCurrentPage,
			int currentPage, Map<String, Object> searchParams);
	
	
	/**
	 * 获取用户的配资方案补仓
	 * @return
	 */
	public List<UserTradeCmsVo> queryNeedAdd(String uid);

	
	/*public List<UserTrade> queryPageDataByConndition(DataPage<UserTrade> page);*/
	
	/**
	 * 
	 * @param page DataPage<WUser> 
	 * @param equals Map<String,Object> 等值查询
	 * @param isLike Map<String,String> like 查询
	 * @param orders Map<String,Boolean> 排序查询
	 * @return DataPage<WUser>
	 */
	/*public List<UserTrade> queryByConndition(Map<String,Object> equals,Map<String,String> isLike
			,Map<String,String> groupNames,Map<String,Boolean> orders);*/
	
	/**
	 * 未设置警告
	 * @param page DataPage<RechargeList>
	 * @return DataPage<RechargeList>
	 */
	public PageInfo<SettingNotWarningVo> queryNotSettingWarning(PageInfo<SettingNotWarningVo> page,SettingNotWarningVo vo);
	
	/**
	 * 已设置警告
	 * @param page DataPage<RechargeList>
	 * @return DataPage<RechargeList>
	 */
	//public DataPage<UserTrade> querySettingWarning(DataPage<UserTrade> page,SettingWarningVo vo);
	
	/**
	 * 未设置警告
	 * @param page DataPage<RechargeList>
	 * @return DataPage<RechargeList>
	 */
	public PageInfo<SettingWarningVo> querySettingWarning(PageInfo<SettingWarningVo> page,SettingWarningVo vo);
	
	/**
	 * 
	 * @param page DataPage<WUser> 
	 * @param equals Map<String,Object> 等值查询
	 * @param isLike Map<String,String> like 查询
	 * @param orders Map<String,Boolean> 排序查询
	 * @return DataPage<WUser>
	 */
	public PageInfo<UserTrade> queryDataPageByConndition(PageInfo<UserTrade> page,
			Map<String,Object> equals,Map<String,String> isLike,Map<String,String> groupNames,Map<String,String> poly,Map<String,Boolean> orders);
	
	
	
	
	/**
	 * 获取余额不足以扣去次日管理费的列表数据
	 * @return
	 */
	public List<NotEnoughBalanceVo> getNotEnoughBalanceList(EasyUiPageInfo  easyUiPage,Map<String, Object> searchParams);
	
	
	/**
	 * 根据限制买入状态获取数据
	 * @return
	 */
	public PageInfo<Object>  findBuyLimitBuyStatus(EasyUiPageInfo  easyUiPage,Map<String, Object> searchParams,Short limitBuyStaus);
	
	
	/**
	 * 欠费状态更新
	 * @param groupIds
	 * @param isArrearage
	 */
	public void  changeArrearageStatus(String [] groupIds,boolean isArrearage);
	
	/**
	 * 操作账户状态更新
	 * @param groupIds 配资组合id
	 * @param operationStatus 账户状态
	 * @param sysId 若为定时任务 system
	 */
	public void  changeOperationStatus(String [] groupIds,Integer operationStatus,String sysId);
	
	/**
	 * 获取配资组合下的方案
	 * @param account
	 * @return
	 */
	public List<UserTrade> findByGroupId(String groupId);
	
	/**
	 * 设置为预警值
	 * @param groupId String
	 * @param stateValue Integer
	 */
	public void updateUserTradeByGroupId(String groupId,Integer stateValue);
	
	/**
	 * status
	 * @param id String
	 * @param stateValue Integer
	 */
	public void updateUserTradeById(String id,Integer stateValue);
	
	
	/**
	 * 配资分页
	 * @param 
	 */
	public PageInfo<Object> findTrade(PageInfo<Object> pageInfo);
	/**
	 * 通过groupID查找合买详情
	 * @param groupId
	 * @return
	 */
	public UserTradeCmsVo publicFindByGroupId(String groupId);
	
	/**
	 * 获取配资组合下的方案Group(合并为group)
	 * @param 
	 */
	public UserTradeCmsVo findByGroupIdAndUid(String groupId,String uid);
	
	/**
	 * 获取配资组合下的方案Group(合并为group) 延迟访问恒生接口
	 * @param 
	 */
	public UserTradeCmsVo findByGroupIdAndUidLazy(String groupId,String uid);
	
	/**
	 * 延迟访问恒生接口 获取操盘信息
	 * @param 
	 */
	public UserTradeCmsVo findTradingUserInfo(String groupId,String uid);
	
	/**
	 * 查询欠费待终结方案
	 * @param page
	 * @param vo
	 * @return
	 */
	public PageInfo<EndProgramVo> queryEnd(PageInfo<EndProgramVo> page, EndProgramVo vo);
	

	/**
	 * 查询欠费待终结方案
	 * @param page
	 * @param conn
	 * @return
	 */
	public PageInfo<EndProgramVo> queryEnd(PageInfo<EndProgramVo> page, ConnditionVo conn);
	
	/**
	 * 获取监控方案列表
	 * @return
	 */
	public PageInfo<Object> queryMonitorList(EasyUiPageInfo easyUiPage,
			Map<String, Object> searchParams, ConnditionVo connVo);
	
	/**
	 * 获取UserTrade
	 * @param groupId String
	 * @return List<UserTrade>
	 */
	public List<UserTrade> findByGroupIdOrderByAddtimeAsc(String groupId);
	
	
	/**
	 * 合计组保证金
	 * @return Double
	 */
	public BigDecimal totalLevelMoneyByGroupId(String groupId);
	

	
	
	public List<UserTrade> findByGroupIdAndWuser(String groupId,WUser wuser);
	
	/**
	 * 
	 * 
	 * @author QingLiu
	 * 方案终结
	 * If the end is true successful program.
	 * @param groupId String
	 * @return Boolean
	 */
	public void endOfProgram(String groupId);
	
	/**
	 * 获取合计对象
	 * @param groupId
	 * @return List<UserTrade>
	 */
	public EndMoneyVo endOfProgrameSum(String groupId);
	
	/**
	 * T+1 终结划账
	 */
	public void endOfTransferMoney();
	
	/**
	 * T+1 佣金划账
	 */
	public void endOfCommission();
	
	
	/**
	 * 平仓
	 * @param groupIds
	 */
	public  void openStock(String [] groupIds,String sysId);
	
	/**
	 * 根据配资组合id 查看是否在补仓线以上
	 * @param groupId
	 * @return
	 */
	public  boolean isUpMarginLine(String groupId);
	/**
	 * 当前持仓明细
	 * @param groupIds
	 */
	public  List<PositionDetailsVo> findPositionDetail(String groupId);
	
	
	public UserTrade findOneByGroupIdOrderByAddtimeAsc(String groupId);
	
	public boolean addBond(ParentAccount parentAccount,UserTrade userTrade,double addMoney,WUser userTemp);
	
	public UserTradeCmsVo findTradeByGroupIdAndUid( String groupId,String uid);
	
	public  UserTradeTransfer addMoreUserTradeAndOpenHundsun(UserTrade userTrade,WUser user,ParentAccount parentAccount,UserTradeCmsVo userTradeCmsVo,String volumeDetailId);
	
	public boolean extractableProfit(UserTrade userTrade,WUser user,ParentAccount parentAccount,double extractableProfitMoney);
	
	/**
	 * 查询已经终结方案，但未注销的恒生账户
	 * @return
	 */
	public PageInfo<Object> queryNoCancelledList(EasyUiPageInfo  easyUiPage,Map<String, Object> searchParams); 
	
	/**
	 * 是不是6600活动用户
	 * @return
	 */
	public boolean isActity_6600(WUser wuser) ;
	
	
	/**
	 * 查询失败的配资
	 * @return
	 */
	public PageInfo<Object> queryTradeFailList(EasyUiPageInfo  easyUiPage,Map<String, Object> searchParams); 

	
	public List<UserTrade> findUserTradeStartToday();

	
	

	/**
	 * 审核 失败配资
	 * @param groupIds
	 */
	public void  reviewTrade(String [] idArray);
	
	/**
	 * 定时任务取消配资冻结
	 * @param 
	 */
	public void thawUserTrade();
	
	
	/**
	 * 低于补偿线  定时任务  发送短信提醒
	 */
	public void sendMarginRemindMsg();
	
	
	/**
	 * 查询所有配资方案
	 */
	public PageInfo<Object> queryAllTrades(EasyUiPageInfo easyUiPage,
			Map<String, Object> searchParams);
	
	

	/**
	 * 查询配资方案 终结之后T+1划款失败数据
	 */
	public PageInfo<Object> queryEndFailTrades(EasyUiPageInfo easyUiPage,
			Map<String, Object> searchParams);

	/**
	 * 改变划款失败的状态  
	 * @param groupIdArray
	 */
	public  void endSuccessTrade(String [] groupIdArray);
	
	/**
	 * 查询配资金额
	 * @param uid
	 * @param status
	 * @param status2
	 * @return
	 * @date 2015年3月6日
	 * @author zhangjun
	 */
	public List<UserTradeVo> queryUserTotalLending(String uid,
			short status,short status2);
	
	/**
	 * 获取系统中 所有母账户下的 组合资产值
	 * @return
	 */
	public  List<StockCurrent>  getAllStockCurrents();
	
	/**
	 * 根据groupid查询子方案
	 */
	public PageInfo<Object> queryChildTrades(EasyUiPageInfo easyUiPage,
			Map<String, Object> searchParams);
	
	/**
	 * 根据groupid查询子方案
	 */
	public double findActivityByGroupId(String groupId);
	
	public boolean transferMoney(String fundAccount, String parentCombineId,
			String childrenCombineId, double money, String remark);
	
	/**
	 * 配资状态为【配资中】自动定时划账到子账户接口
	 */
	public void adjustmentTransfer();
	
	/**
	 * 根据uid查询活动的方案
	 */
	public int findUserTradeByUidId(String uid);
	
	
	/**
	 * 查询15：30下一个交易日开始的方案
	 */
	public List<UserTrade> findUserTradeStartNextToday() ;
	
	/**
	 * 15：30解冻下一个交易日开始的方案
	 */
	public void thawNextUserTrade();
	
	
	/**
	 * 查询当天用户所有持仓信息
	 * @return
	 */
	public  List<Combostock>  getAllCombostocks();
	
	/**
	 * 获取用户以及用户代理总配资额
	 * @param userId 用户编号
	 * @param date 时间
	 * @return
	 */
	public Double getTradeMoneyByUserId(String userId,String date);
	
	/**
	 * 
	 * @param userTrade 用户配资信息
	 * @param user 用户信息
	 * @return
	 */
	public ParentAccount findAviParentAccount(UserTrade userTrade, WUser user);
	
	/**
	 * 根据用户编号以及方案状态，获取用户方案列表信息
	 * @param uid   用户编号
	 * @param status  方案状态
	 * @param status2 方案状态
	 * @return  用户方案列表
	 */
	public List<UserTradeVo> queryUserTradeVoByUidAndStatus(String uid,short status, short status2);
	
	/**
	 * 根据用户编号和groupId获取方案信息
	 * @param uid   用户编号
	 * @param groupId  
	 * @return UserTradeVo
	 */
	public UserTradeVo getUserTradeVoByGroupId(String uid,String groupId);
	
	/**
	 * 涌金版方案终结
	 * @param groupId String
	 * @param assetTotalValue Double
	 */
	public void endOfWellGoldProgram(String groupId,Double assetTotalValue);
	
	
	/**
	 * 查询涌金版方案监控数据
	 * @param dataPage
	 * @param connVo
	 * @return
	 */
	public PageInfo<MonitorSchemeVo> queryMonitorData(
			PageInfo<MonitorSchemeVo> dataPage, ConnditionVo connVo,short feeType,int handType);

	/**
	 * 提交欠费终结方案 审核
	 * @MethodName endSolationReview
	 * @author L.Y
	 * @date 2015年5月13日
	*/
	public void endSolationReview(EndProgramVo vo) throws Exception;
	
	/**
	 * 获取某方案信息
	 * @param programNo 方案编号
	 * @param wuser   用户信息
	 * @return
	 */
	public UserTrade findByProgramNoAndWuser(String programNo,WUser wuser);
	
	
	/**
	 * 取消限制买入（必须满足条件：1、操盘中、 2：没有欠费、3、高于补仓线）
	 * @param userTrade  方案信息
	 */
	public void cancelAstrictBuy(UserTrade userTrade);
	
	/**
	 * 查询新增配资用户列表
	 * @param dataPage
	 * @param userTradeSummaryVo
	 * @return
	 */
	public PageInfo<UserTradeSummaryVo> queryTradeSummaryData(
			PageInfo<UserTradeSummaryVo> dataPage,
			UserTradeSummaryVo userTradeSummaryVo);
	
	/**
	 * 
	 * @param uid
	 * @param status
	 * @param status2
	 * @return
	 */
	public List<UserTradeVo> queryNewUserTradeVoByWuserAndStatus(String uid,
			short status, short status2);
	
	/**
	 * 获取盈亏方案信息
	 * @param uid   用户编号
	 * @param groupId  信闳方案账户
	 * @return
	 */
	public Double getTradeAccrual(String uid,String groupId);
	
	/**
	 * 获取用户配资信息
	 * @param uid  用户信息 
	 * @param date 时间
	 * @return
	 */
	public List<UserTradeVo> queryUserTradeVos(String uid,String date);
	
	/**
	 * 手动触发 短信通知用户 已到达补仓线
	 * @param groupIds
	 */
	public void smsNoticeMarginUser(String[] groupIds,Short noticeStatus,int type);
	
	/**
	 * 获取用某天的配资，去除涌金，手工开户失败配资
	 * @param uid
	 * @param date
	 * @return
	 */
	public List<UserTradeVo> queryUserDayTrades(String uid, String date);
	
	
	/**
	 * 根据uid查询活动的方案
	 */
	public int findUserTradesNotBespoke(String uid);
	
	/**
	 * 获取用户A股当前操盘中的融资额
	 * @param uid  用户编号
	 * @param status  状态
	 * @return
	 */
	public Double getSumMoneyUserTradesByUidAndStatus(String uid,int status);
	
	/**
	 * 获取用户A股当前操盘中的个数
	 * @param uid  用户编号
	 * @return
	 */
	public int getCountUserTradesByUid(String ui);
	
	

	/**
	 * 重构生成方案，移除恒生api相关内容 
	 * @param userTrade
	 * @param user
	 * @param volumeDetailId
	 * @return
	 */
	public UserTrade   buildUserTrade(UserTrade userTrade,WUser user,String volumeDetailId,String scope);
	
	
	/**
	 * 创建 并保存月月配方案
	 * @param userTrade
	 * @param user
	 * @return
	 */
	public UserTrade   buildUserMonthTrade(UserTrade userTrade,WUser user,Integer tradeMonth);
	

	/**
	 * 根据活动类型、状态、预计结束时间查询
	 * @param status
	 * @param andActivityType
	 * @param estimateEndtime
	 * @return
	 */
	public List<UserTrade> findByStatusAndActivityTypeAndEstimateEndtime(Short status,int andActivityType,Long estimateEndtime);

	/**
	 * 延期月月配方案
	 * @param usertrade  延期方案
	 * @param delayMonth 延期月数
	 * @param isJob  是否定时任务
	 */
	public JsonResult delayMonthTrade(UserTrade  usertrade,Integer delayMonth,boolean isJob);
	
	
	/**
	 * 查询即将到期的月月配方案
	 * @param status
	 * @param andActivityType
	 * @param start
	 * @param end
	 * @return
	 */
	public List<UserTrade> findSoonExpireMonthTrades(Short status,int andActivityType,Long start,Long end);
	
	
	/**
	 * 查询用户是否申请过配资方案
	 */
	public boolean  isHaveNoTrade(WUser user);
	
	/**
	 * 具体结束时间只有10天的方案
	 * @param page
	 * @param conn
	 * @return
	 */
	public PageInfo<MonthEndVo> monthNearEndList(PageInfo<MonthEndVo> page, ConnditionVo conn);
	/**
	 * 已经到期的方案
	 * @param page
	 * @param conn
	 * @return
	 */
	public PageInfo<MonthEndVo> monthEndList(PageInfo<MonthEndVo> page, ConnditionVo conn);
	/**
	 * 方案详情里面的资金明细
	 * @param userToken
	 * @param userTrade
	 * @return
	 */
	public List<Map<String,Object>> getdetailList(WUser userToken,UserTrade userTrade );
	/**
	 * 求方案详情里的总操盘费用
	 * @param userToken
	 * @param userTrade
	 * @return
	 */
	public List<Map<String,Object>> getSumMoney(WUser userToken,String groupId );
}
