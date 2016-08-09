package com.tzdr.business.service.datamap;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import jodd.util.ObjectUtil;
import jodd.util.StringUtil;

import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Maps;
import com.tzdr.business.cms.service.auth.AuthService;
import com.tzdr.business.exception.DataMapException;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.common.config.ActivityConfig;
import com.tzdr.common.exception.BusinessException;
import com.tzdr.common.utils.Dates;
import com.tzdr.domain.api.constants.Constant;
import com.tzdr.domain.cache.DataDicKeyConstants;
import com.tzdr.domain.cms.entity.user.User;
import com.tzdr.domain.dao.DataMapDao;
import com.tzdr.domain.entity.DataMap;

/**
 * 
 * @zhouchen 2014年12月26日
 */
@Service
@Transactional
public class DataMapService extends BaseServiceImpl<DataMap, DataMapDao> {
	public static final Logger logger = LoggerFactory
			.getLogger(DataMapService.class);

	@Autowired
	private AuthService authService;

	@Override
	public void update(DataMap dataMap) throws BusinessException {
		DataMap dbDataMap = getEntityDao().get(dataMap.getId());
		if (ObjectUtil.equals(null, dbDataMap)) {
			throw new DataMapException("business.update.not.found.data", null);
		}
		dbDataMap.setTypeKey(dataMap.getTypeKey());
		dbDataMap.setTypeName(dataMap.getTypeName());
		dbDataMap.setValueKey(dataMap.getValueKey());
		dbDataMap.setValueName(dataMap.getValueName());
		dbDataMap.setWeight(dataMap.getWeight());
		setOperateLog(dbDataMap, "更新数据字典", "edit");
		super.update(dbDataMap);
	}

	public void updateDataMap(DataMap dataMap) throws BusinessException {
		setOperateLog(dataMap, "更新数据字典", "edit");
		super.update(dataMap);
	}

	@Override
	public void removes(Serializable... ids) throws BusinessException {
		for (Serializable id : ids) {
			DataMap dbDataMap = getEntityDao().get(id);
			if (ObjectUtil.equals(dbDataMap, null)) {
				throw new DataMapException("business.delete.not.found.data",
						null);
			}
			dbDataMap.setDeleted(Boolean.TRUE);
			setOperateLog(dbDataMap, "删除数据字典", "edit");
			super.update(dbDataMap);
		}
	}

	@Override
	public void save(DataMap datamap) {

		setOperateLog(datamap, "新增数据字典", "add");
		super.save(datamap);
	}

	public List<DataMap>  findByTypeKey(String typeKey){
		return this.getEntityDao().findByTypeKey(typeKey);
	}
	
	private void setOperateLog(DataMap datamap, String operateContent,
			String operateType) {
		User loginUser = authService.getCurrentUser();
		datamap.setOperateContent(operateContent);
		if (StringUtil.equals(operateType, "edit")) {
			datamap.setUpdateTime(Dates.getCurrentLongDate());
			datamap.setUpdateUser(loginUser.getRealname());
			datamap.setUpdateUserOrg(loginUser.getOrganization().getName());
			datamap.setUpdateUserId(loginUser.getId());
			return;
		}

		datamap.setCreateTime(Dates.getCurrentLongDate());
		datamap.setCreateUser(loginUser.getRealname());
		datamap.setCreateUserOrg(loginUser.getOrganization().getName());
		datamap.setCreateUserId(loginUser.getId());
		return;
	}

	/**
	 * 提现审核金额获取
	 * @return
	 */
	public DataMap getWithDrawMoney() {
		List<DataMap> maps = getEntityDao()
				.findByDeletedFalseAndTypeKeyAndValueKey(
						DataDicKeyConstants.WITHDRAW_MONEY_TYPE_KEY,
						DataDicKeyConstants.WITHDRAW_MONEY_VALUE_KEY);
		if (CollectionUtils.isEmpty(maps)) {
			return null;
		}
		return maps.get(0);
	}

	/**
	 * 获取提现审核的 最大值 最小值
	 * @return
	 */
	public Map<String,String> getWithDrawAuditMoney() {
		DataMap  dataMap = this.getWithDrawMoney();
		if (ObjectUtil.equals(null, dataMap)){
			return null;
		}
		String valueName = dataMap.getValueName();
		String []withdrawMoneys=valueName.split(DataDicKeyConstants.WITHDRAWAL_AUDIT_MONEY_SPLIT);
		if (2 !=withdrawMoneys.length){
			return null;
		}
		
		Map<String,String> moneyMap=Maps.newHashMap();
		moneyMap.put(DataDicKeyConstants.WITHDRAWAL_AUDIT_MIN_MONEY,withdrawMoneys[0]);
		moneyMap.put(DataDicKeyConstants.WITHDRAWAL_AUDIT_MAX_MONEY,withdrawMoneys[1]);
		return moneyMap;
	}
	public DataMap getMaintenanceStartTime() {
		List<DataMap> maps = getEntityDao()
				.findByDeletedFalseAndTypeKeyAndValueKey(
						DataDicKeyConstants.M_START_TYPE_KEY,
						DataDicKeyConstants.M_START_VALUE_KEY);
		if (CollectionUtils.isEmpty(maps)) {
			return null;
		}
		return maps.get(0);
	}

	public DataMap getMaintenanceEndTime() {
		List<DataMap> maps = getEntityDao()
				.findByDeletedFalseAndTypeKeyAndValueKey(
						DataDicKeyConstants.M_END_TYPE_KEY,
						DataDicKeyConstants.M_END_VALUE_KEY);
		if (CollectionUtils.isEmpty(maps)) {
			return null;
		}
		return maps.get(0);
	}

	/**
	 * 
	 * 	是否维护时间
	 * 
	 * @return
	 */
	public boolean isMaintenanceTime(){
		if (ObjectUtil.equals(getMaintenanceStartTime(), null)) {
			return false;
		}
		if (ObjectUtil.equals(getMaintenanceEndTime(), null)) {
			return false;
		}
		String startTime=getMaintenanceStartTime().getValueName();
		Date startDate=Dates.parse(startTime, "yyyy-MM-dd HH:mm:ss");
		String endTime=getMaintenanceEndTime().getValueName();
		Date endDate=Dates.parse(endTime, "yyyy-MM-dd HH:mm:ss");
		Date now = new Date();
		if(now.getTime()>=startDate.getTime()&&now.getTime()<=endDate.getTime()){
			return true;
		}
		return false;
	}
	
	public String getBigAccountType() {
		List<DataMap> maps = getEntityDao()
				.findByDeletedFalseAndTypeKeyAndValueKey(
						DataDicKeyConstants.BIG_ACCOUNT_KEY,
						DataDicKeyConstants.BIG_ACCOUNT_VALUE_KEY);
		if (CollectionUtils.isEmpty(maps)) {
			return null;
		}
		return maps.get(0).getValueName();
	}
	
	public String getBigAccountMoney() {
		List<DataMap> maps = getEntityDao()
				.findByDeletedFalseAndTypeKeyAndValueKey(
						DataDicKeyConstants.BIG_ACCOUNT_MIN_KEY,
						DataDicKeyConstants.BIG_ACCOUNT_MIN_VALUE_KEY);
		if (CollectionUtils.isEmpty(maps)) {
			return null;
		}
		return maps.get(0).getValueName();
	}
	
	public String getAccountUserName() {
		List<DataMap> maps = getEntityDao()
				.findByDeletedFalseAndTypeKeyAndValueKey(
						DataDicKeyConstants.ACCOUNT_USERNAME_KEY,
						DataDicKeyConstants.ACCOUNT_USERNAME_VALUE_KEY);
		if (CollectionUtils.isEmpty(maps)) {
			return null;
		}
		return maps.get(0).getValueName();
	}
	
	/**
	 * 获取 是否启用涌金版标志
	 * ValueName=0 关闭
	 * ValueName=1 开启
	 * @return
	 */
	public String getStartWellGold() {
		List<DataMap> maps = getEntityDao()
				.findByDeletedFalseAndTypeKeyAndValueKey(
						DataDicKeyConstants.START_WELLGOLD_TYPE_KEY,
						DataDicKeyConstants.START_WELLGOLD_VALUE_KEY);
		if (CollectionUtils.isEmpty(maps)) {
			return DataDicKeyConstants.START_WELLGOLD_DEFAULT;
		}
		return maps.get(0).getValueName();
	}
	
	public String maxLeverMoney() {
		List<DataMap> maps = getEntityDao()
				.findByDeletedFalseAndTypeKeyAndValueKey(
						DataDicKeyConstants.MAX_LEVER_MONEY_KEY,
						DataDicKeyConstants.MAX_LEVER_MONEY_VAULE_KEY);
		if (CollectionUtils.isEmpty(maps)) {
			return DataDicKeyConstants.MAX_LEVER_MONEY_DEFAULT;
		}
		return maps.get(0).getValueName();
	}
	public String isOpenMaxLeverMoney() {
		List<DataMap> maps = getEntityDao()
				.findByDeletedFalseAndTypeKeyAndValueKey(
						DataDicKeyConstants.START_MAX_LEVER_MONEY_KEY,
						DataDicKeyConstants.START_MAX_LEVER_MONEY_VAULE_KEY);
		if (CollectionUtils.isEmpty(maps)) {
			return DataDicKeyConstants.START_MAX_LEVER_MONEY_DEFAULT;
		}
		return maps.get(0).getValueName();
	}
	
	/**
	 * 限制当天配资数
	 * @return 
	 */
	public String isLimitTodayTradeNum(){
		List<DataMap> maps = getEntityDao()
				.findByDeletedFalseAndTypeKeyAndValueKey(
						DataDicKeyConstants.LIMIT_TODAY_TRADE_NUM,
						DataDicKeyConstants.IS_LIMIT_TODAY_TRADE_NUM);
		if (CollectionUtils.isEmpty(maps)) {
			return DataDicKeyConstants.lIMIT_TODAY_TRADE_NUM;
		}
		return maps.get(0).getValueName();
	}
	
	
	/**
	 * 获取 是否启用同花顺手动
	 * ValueName=0 关闭
	 * ValueName=1 开启
	 * @return
	 */
	public String getStartTierce() {
		List<DataMap> maps = getEntityDao()
				.findByDeletedFalseAndTypeKeyAndValueKey(
						DataDicKeyConstants.START_TIERCE_HANDLE_TYPE_KEY,
						DataDicKeyConstants.START_TIERCE_HANDLE_VALUE_KEY);
		if (CollectionUtils.isEmpty(maps)) {
			return DataDicKeyConstants.START_TIERCE_HANDLE_DEFAULT;
		}
		return maps.get(0).getValueName();
	}
	
	
	/**
	 * 获取当日最大操盘个数
	 * @return
	 */
	public int getHoldMaxTradeNum() {
		List<DataMap> maps = getEntityDao()
				.findByDeletedFalseAndTypeKeyAndValueKey(
						Constant.HOLD_MAX_TRADENUM_TYPE_KEY,
						Constant.HOLD_MAX_TRADENUM_VALUE_KEY);
		if (CollectionUtils.isEmpty(maps)) {
			return Constant.DEFAULT_HOLD_MAX_TRADENUM;
		}
		return NumberUtils.toInt(maps.get(0).getValueName());
	}
	
	/**
	 * 更新部分信息
	 * @param dataMap
	 */
   public void updateInfo(DataMap dataMap){
	   super.update(dataMap);
   }
   
	/**
	 * 获取注册短信通道
	 */
	public int getSmsContentRegister() {
		List<DataMap> maps = this.getEntityDao().findByTypeKey(DataDicKeyConstants.SMS_CONTENT_REGISTER);
		if (CollectionUtils.isEmpty(maps)) {
			return Integer.parseInt(DataDicKeyConstants.SMS_CONTENT_DEFAULT);
		}
		return Integer.parseInt(maps.get(0).getValueKey());
	}
	
	/**
	 * 获取其它短信通道
	 */
	public int getSmsContentOthers() {
		List<DataMap> maps = this.getEntityDao().findByTypeKey(DataDicKeyConstants.SMS_CONTENT_OTHERS);
		if (CollectionUtils.isEmpty(maps)) {
			return Integer.parseInt(DataDicKeyConstants.SMS_CONTENT_DEFAULT);
		}
		return Integer.parseInt(maps.get(0).getValueKey());
	}
	
	
	   
		/**
		 * 配股宝获取注册短信通道
		 */
		public int getPgbSmsContentRegister() {
			List<DataMap> maps = this.getEntityDao().findByTypeKey(DataDicKeyConstants.PGB_SMS_CONTENT_REGISTER);
			if (CollectionUtils.isEmpty(maps)) {
				return Integer.parseInt(DataDicKeyConstants.SMS_CONTENT_DEFAULT);
			}
			return Integer.parseInt(maps.get(0).getValueKey());
		}
		
		/**
		 * 配股宝获取其它短信通道
		 */
		public int getPgbSmsContentOthers() {
			List<DataMap> maps = this.getEntityDao().findByTypeKey(DataDicKeyConstants.PGB_SMS_CONTENT_OTHERS);
			if (CollectionUtils.isEmpty(maps)) {
				return Integer.parseInt(DataDicKeyConstants.SMS_CONTENT_DEFAULT);
			}
			return Integer.parseInt(maps.get(0).getValueKey());
		}
		
		
	   
   /**
    * 获取提现设置 的渠道
    * @return
    */
   public int getWithDrawSetting(){
	   List<DataMap> dataMaps = getEntityDao().findByTypeKey(DataDicKeyConstants.WITHDRAW_SETTING);
	   if (CollectionUtils.isEmpty(dataMaps)){
		   return 0;
	   }
	   else
	   {
		   String withdrawSetting = dataMaps.get(0).getValueKey();
		   return NumberUtils.toInt(withdrawSetting);
	   }
   }
   
   
   
	/**
	* 获取配股宝提现设置 的渠道
	* @return
	*/
	public int getPgbWithDrawSetting(){
	   List<DataMap> dataMaps = getEntityDao().findByTypeKey(DataDicKeyConstants.PGB_WITHDRAW_SETTING);
	   if (CollectionUtils.isEmpty(dataMaps)){
		   return 0;
	   }
	   else
	   {
		   String withdrawSetting = dataMaps.get(0).getValueKey();
		   return NumberUtils.toInt(withdrawSetting);
	   }
	}
	public boolean activityExpired(){
		boolean flag = false;
		SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd HH:ss");
			List<DataMap> dataMapsEnd = findByTypeKey("activityOnlineEndTime");
			if(dataMapsEnd != null && dataMapsEnd.size() > 0){
				String dateEndTime = dataMapsEnd.get(0).getValueName();
				Date endDate;
				try {
					endDate = df.parse(dateEndTime);
					long end = endDate.getTime();
					if(new Long(end).longValue() > new Long(ActivityConfig.now_time).longValue()){
						flag = true;
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		return flag;
	}
}
