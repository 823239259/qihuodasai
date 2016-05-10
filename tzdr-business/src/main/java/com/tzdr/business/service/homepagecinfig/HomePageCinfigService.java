package com.tzdr.business.service.homepagecinfig;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import jodd.util.ObjectUtil;
import jodd.util.StringUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tzdr.business.cms.service.auth.AuthService;
import com.tzdr.business.exception.HomePageCinfigException;
import com.tzdr.business.service.tradecalendar.TradeDayService;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.common.exception.BusinessException;
import com.tzdr.common.utils.Dates;
import com.tzdr.common.utils.TypeConvert;
import com.tzdr.domain.cms.entity.user.User;
import com.tzdr.domain.dao.HomePageCinfigDao;
import com.tzdr.domain.entity.HomePageCinfig;

/**
 * 
 * @WangPinQun 2015年12月20日
 */
@Service
@Transactional
public class HomePageCinfigService extends BaseServiceImpl<HomePageCinfig, HomePageCinfigDao> {
	public static final Logger logger = LoggerFactory
			.getLogger(HomePageCinfigService.class);

	@Autowired
	private AuthService authService;

	@Autowired
	private  TradeDayService tradeDayService;
	
	@Override
	public void update(HomePageCinfig dataMap) throws BusinessException {
		HomePageCinfig dbDataMap = getEntityDao().get(dataMap.getId());
		if (ObjectUtil.equals(null, dbDataMap)) {
			throw new HomePageCinfigException("business.update.not.found.data", null);
		}
		dbDataMap.setTypeKey(dataMap.getTypeKey());
		dbDataMap.setTypeName(dataMap.getTypeName());
		dbDataMap.setValueKey(dataMap.getValueKey());
		dbDataMap.setValueName(dataMap.getValueName());
		dbDataMap.setWeight(dataMap.getWeight());
		dbDataMap.setValueData(dataMap.getValueData());
		if(!dbDataMap.getIsUserUpdate()){
			setOperateLog(dbDataMap, "更新数据字典", "edit");
		}
		super.update(dbDataMap);
	}

	public void updateBatchDataMap(Map<String, String> params){
		
		String currentDay = Dates.format(new Date(),Dates.CHINESE_DATE_FORMAT_LONG);
		
		if(params == null || params.size() <= 0 || !tradeDayService.isTradeDay(currentDay)) {return;}
		
		for (Map.Entry<String, String> entry:params.entrySet()) {
			List<HomePageCinfig> homePageCinfigs = this.getEntityDao().findByTypeKey(entry.getKey());
			if(homePageCinfigs == null || homePageCinfigs.size() <= 0){ continue;}
			for (HomePageCinfig homePageCinfig : homePageCinfigs) {
				String valueData = homePageCinfig.getValueData();
				if(StringUtil.isBlank(valueData)) {continue;}
				String[] valueDatas = valueData.split("-");
				if(valueDatas == null || valueDatas.length != 2) {continue;}
			     List<HomePageCinfig> oldHomePageCinfigs =this.getEntityDao().findByValueKey(homePageCinfig.getValueKey()+entry.getValue());
				 if(oldHomePageCinfigs == null || oldHomePageCinfigs.size() <= 0){continue;}
				 for (HomePageCinfig oldHomePageCinfig : oldHomePageCinfigs) {
					 String oldValueData = oldHomePageCinfig.getValueData();
					 if(StringUtil.isBlank(oldValueData) || !TypeConvert.isNumber(oldValueData)){continue;};
				     int min = Integer.parseInt(valueDatas[0]);
				     int max = Integer.parseInt(valueDatas[1]);
				     Random random = new Random();
				     int randomNum = random.nextInt(max)%(max-min+1) + min;
				     if(TypeConvert.isDouble(oldValueData)){
				    	 oldHomePageCinfig.setValueData(String.valueOf(Double.valueOf(oldValueData)+randomNum));
				     }else{
				    	 oldHomePageCinfig.setValueData(String.valueOf(Integer.valueOf(oldValueData)+randomNum));
				     }
				     oldHomePageCinfig.setIsUserUpdate(true);
				     this.update(oldHomePageCinfig);
				}
			}
		}
	}
	
	public void updateDataMap(HomePageCinfig dataMap) throws BusinessException {
		setOperateLog(dataMap, "更新数据字典", "edit");
		super.update(dataMap);
	}

	@Override
	public void removes(Serializable... ids) throws BusinessException {
		for (Serializable id : ids) {
			HomePageCinfig dbDataMap = getEntityDao().get(id);
			if (ObjectUtil.equals(dbDataMap, null)) {
				throw new HomePageCinfigException("business.delete.not.found.data",
						null);
			}
			dbDataMap.setDeleted(Boolean.TRUE);
			setOperateLog(dbDataMap, "删除数据字典", "edit");
			super.update(dbDataMap);
		}
	}

	@Override
	public void save(HomePageCinfig datamap) {

		setOperateLog(datamap, "新增数据字典", "add");
		super.save(datamap);
	}

	public List<HomePageCinfig>  findByTypeKey(String typeKey){
		return this.getEntityDao().findByTypeKey(typeKey);
	}
	
	private void setOperateLog(HomePageCinfig datamap, String operateContent,
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
	 * 更新部分信息
	 * @param homePageCinfig
	 */
   public void updateInfo(HomePageCinfig homePageCinfig){
	   super.update(homePageCinfig);
   }
}
