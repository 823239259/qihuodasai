package com.tzdr.business.service.wuser;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.utils.ConnditionVo;
import com.tzdr.domain.vo.ActivityWuserVo;
import com.tzdr.domain.vo.AgentVo;
import com.tzdr.domain.vo.TransferVo;
import com.tzdr.domain.vo.UserVerifiedVo;
import com.tzdr.domain.vo.WuserListVo;
import com.tzdr.domain.vo.WuserParentVo;
import com.tzdr.domain.vo.WuserVo;
import com.tzdr.domain.web.entity.UserFund;
import com.tzdr.domain.web.entity.WUser;

/**
* @Description: TODO(用户帐号业务信息管理业务层)
* @ClassName: WUserService
* @author wangpinqun
* @date 2014年12月25日 下午1:24:03
 */
public interface WUserService  extends BaseService<WUser>{

	/**
	* @Description: TODO(根据注册手机号码获取用户帐号信息)
	* @Title: getWUserByMobile
	* @param mobile
	* @return
	* @return WUser    返回类型
	 */
	public WUser getWUserByMobile(String mobile);
	
	/**
	* @Description: TODO(保存用户帐号、安全验证信息)
	* @Title: saveWUser  
	* @param wUser       用户帐号信息
	* @return void    返回类型
	 */
	public void saveWUser(WUser wUser);
	
	/**
	 * 活动用户保存
	 * @param wuser WUser
	 */
	public WUser saveActivityWUser(WUser wuser);
	/**
	 * 活动用户保存
	 * @param wuser WUser
	 * @param  isSms boolean if true is send message 
	 */
	public WUser saveActivityWUser(WUser wuser,boolean isSms);
	
	/**
	* @Description: TODO(登录)
	* @Title: login
	* @param loginName  登录帐号
	* @param password   密码
	* @return WUser    返回类型
	 */
	public WUser login(String loginName,String password);
	
	/**
	* @Description: TODO(更新用户账户信息)
	* @Title: updateUser
	* @param wUser  用户账户信息
	* @return void    返回类型
	 */
	public void updateUser(WUser wUser);

	public WUser getUser(String id);
	
	public WUser getUserByEmail(String email);
	
	public List<WUser> queryAll();
	
	public List<WUser> queryNotSystemWuser();
	
	
	public WUser queryWuserByUsername(String username);
	
	/**
	 * 分页查询
	 * @param page DataPage<WUser>
	 * @return DataPage<WUser>
	 */
	public PageInfo<WUser> queryDataPage(PageInfo<WUser> page);
	
	
	/**
	 * 
	 * @param page PageInfo<WuserListVo>
	 * @return PageInfo<WuserListVo>
	 */
	public PageInfo<WuserListVo> queryDataPageWuserListVo(PageInfo<WuserListVo> page
			,ConnditionVo connVo);
	
	/**
	 * 分页查询
	 * @param page DataPage<WUser>
	 * @return DataPage<WUser>
	 */
	public PageInfo<ActivityWuserVo> queryActivityDataPage(PageInfo<ActivityWuserVo> page,ActivityWuserVo activityUserVo);
	
	/**
	 * 查询出活动用户数据
	 * @return DataPage<WUser>
	 */
	public List<ActivityWuserVo> queryActivityData();
	
	/**
	 * 分页查询
	 * @param page DataPage<WUser>
	 * @return DataPage<WUser>
	 */
	public PageInfo<WUser> queryDataPage(PageInfo<WUser> page,WUser wuser);
	
	/**
	 * 查询数据
	 * @param page DataPage<WUser>
	 * @param wuser WUser
	 * @return DataPage<WUser>
	 */
	public PageInfo<WUser> queryDataPageByWuserOne(PageInfo<WUser> page,WUser wuser);
	
	/**
	 * 
	 * @param page
	 * @param wuser
	 * @return
	 */
	public PageInfo<WUser> queryDataPageByWuserTwo(PageInfo<WUser> page,WUser wuser);
	
	/**
	 * 
	 * @param page
	 * @param wuser
	 * @return
	 */
	public PageInfo<WUser> queryDataPageByWuserThree(PageInfo<WUser> page,WUser wuser);
	
	/**
	* @Title: queryDataPageByUserVerifiedVoThree    
	* @Description: 根据条件获取照片认证记录
	* @param dataPage
	* @param connVo 条件
	* @return
	 */
	public PageInfo<UserVerifiedVo> queryDataPageByUserVerifiedVoThree(PageInfo<UserVerifiedVo> dataPage,ConnditionVo connVo);
	
	/**
	 * 代理商查询
	 * @param page
	 * @param wuser
	 * @return DataPage<WUser>
	 */
	public PageInfo<WUser> queryDataPageByAgents(PageInfo<WUser> page,AgentVo agentVo);
	
	/**
	 * 
	 * @param page DataPage<WUser> 
	 * @param equals Map<String,Object> 等值查询
	 * @param isLike Map<String,String> like 查询
	 * @param orders Map<String,Boolean> 排序查询
	 * @return DataPage<WUser>
	 */
	public PageInfo<WUser> queryDataPageByConndition(PageInfo<WUser> page,
			Map<String,Object> equals,Map<String,String> isLike,Map<String,Boolean> orders);
	
	/**
	 * 
	 * @param page DataPage<WUser> 
	 * @param equals Map<String,Object> 等值查询
	 * @param isLike Map<String,String> like 查询
	 * @param orders Map<String,Boolean> 排序查询
	 * @return DataPage<WUser>
	 */
	public List<WUser> queryByConndition(Map<String,Object> equals,Map<String,String> isLike,Map<String,Boolean> orders);
	
	/**
	* @Description: TODO(根据用户类型获取用户列表信息)
	* @Title: queryByUserType
	* @param userType  用户类型
	* @return List<WUser>    返回类型
	 */
	public List<WUser> queryByUserType(String userType);
	
	/**
	 * 修改余额提醒通知状态
	 * @param accounts
	 * @param noticeStatus
	 */
	public void changeNoticeStatus(String[] accounts, Integer noticeStatus); 

	/**
	* @Description: TODO(获取前10名佣金按降序排名)
	* @Title: queryByCommissionDesc
	* @return List<WUser>    返回类型
	 */
	public List<WUser> queryByCommissionDesc();
	
	/**
	* @Description: TODO(根据用户编号获取用户列表信息)
	* @Title: findByIdIn
	* @param ids  用户编号  
	* @return List<WUser>    返回类型
	 */
	public List<WUser> findByIdIn(Collection<String> ids);
	
	
	/**
	 * @see 爆仓后用户金额及拆分充值记录自动维护
	 * 当用户充值存在爆仓时
	 * @param userId String 
	 * @param arrearsUserFund UserFund 欠费对像
	 */
	public void warehouseExplosion(String userId,UserFund arrearsUserFund);
	
	/**
	 * 查询出T+1的数据
	 * @return List<UserTrade>
	 */
	public List<TransferVo> queryUserTradeTodayAndOneBy();
	
	/**
	 * 查询出T+1的数据
	 * @return List<UserTrade>
	 */
	public List<TransferVo> queryUserTradeTodayAndOneBySuccessful();
	
	/**
	 * 查询所有的注册用户数
	 * @return
	 * @date 2015年1月30日
	 * @author zhangjun
	 */
	public long countUser();

	/**
	 * 获取用户的盈利数据
	 * @return
	 * @date 2015年2月2日
	 * @author zhangjun
	 */
	public List<WuserVo> findProfit(int topnum);

	/**
	 * 获取配置用户总数
	 * @return
	 * @date 2015年2月5日
	 * @author zhangjun
	 */
	public List<WuserVo> getUserCount();
	
	/**
	 * 查询所有配资金额的总数
	 * @return
	 * @date 2015年2月5日
	 * @author zhangjun
	 */
	public List<WuserVo> getUserTradecount();
	
	/**
	* @Description: TODO(根据用户推广编号获取用户信息)
	* @Title: findByGeneralizeId
	* @param generalizeId     推广编号
	* @return WUser    返回类型
	 */
	public WUser findByGeneralizeId(String generalizeId);
	
	/**
	* @Description: TODO(根据登录名称获取用户信息)
	* @Title: findByMobileOrEmail
	* @param loginName          登录名称     
	* @return WUser    返回类型
	 */
	public WUser findByMobileOrEmail(String loginName);
	
	/**
	* @Description:(根据用户编号获取用户列表信息)
	* @Title: 找8800活动
	* @param activityType  
	* @return List<WUser>    返回类型
	 */
	public List<WUser> findByActivityTypeAndUserType(int activityType,String userType);
	
	
	/**
	* @Description:
	* @Title: 8800活动定时发短信
	 */
	public void executeEndDay() throws InterruptedException;
	
	/**
	 * 查询出活动用户数量
	 * @return int
	 */
	public int activityWuserCount();
	
	/**
	* @Description: TODO(生成推广编号)
	* @Title: getNewGeneralizeId
	* @return String    返回类型
	 */
	public String getNewGeneralizeId();
	
	/**
	* @Description: TODO(记录登录时间和IP)
	* @Title: updateWUser
	* @param  userId  用户编号
	* @param ip   
	* @return void    返回类型
	 */
	public void updateWUserByUserId(String userId,String ip);
	
	/**
	 * 查询出用户数量
	 * @param mobile String
	 * @return int
	 */
	public int queryWuserCountByMobile(String mobile);
	
	/**
	 * 根据用户ip查询出注册用户数量
	 * @param ip
	 * @return
	 * @date 2015年3月11日
	 * @author zhangjun
	 */
	public int findUsersByIp(String ip);

	public WUser saveUser(WUser wuser);
	
	/**
	 * 代理迁移
	 * @param newSuperiorUid  新上级用户编号
	 * @param subordinateMobile   下级用户手机号码
	 */
	public void updateAgentTree(String newSuperiorUid,String subordinateMobile) throws Exception;
	
	/**
	 * 次日余额不足  短信通知用户
	 * @param uids
	 * @param noticeStatus
	 */
	public void smsNoticeUser(String[] uids,int noticeStatus);

	/**
	 * 变为6600活动
	 * @MethodName updateActivityUser
	 * @author L.Y
	 * @date 2015年6月12日
	 * @param uid 用户id
	 */
	public void updateActivityUser(String uid) throws Exception; 
	
	/**
	 * 根据用户id 查询他的所有上级
	 * @param uid
	 * @return
	 */
	public List<WuserParentVo>  queryUserParents(String uid);

	/**
	 * 调用存储过程
	 * @param mobile
	 * @return
	 */
	public void lowerUserRebates(String mobile);
}
