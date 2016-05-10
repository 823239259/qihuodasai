package com.tzdr.business.service.userTrade;



import java.util.Map;

import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.web.support.EasyUiPageInfo;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.domain.vo.ftse.FSimpleParitiesVo;
import com.tzdr.domain.web.entity.FSimpleParities;

/**
 * 
 * 
 * <p></p>
 * @author WangPinQun
 * @see 
 * FSimpleParitiesService
 * @version 2.0
 * 2015年9月16日下午14:33:13
 */
public interface FSimpleParitiesService extends BaseService<FSimpleParities> {
	
	/**
	 * 获取最新一条汇率信息
	 * @param type
	 * @return
	 */
	public FSimpleParities getFSimpleParities(Integer type); 
	
	/**
	 * 汇率记录页面，分页查询
	 * @param easyUiPage
	 * @param searchParams
	 * @return
	 */
	PageInfo<Object> queryParitiesDatas(EasyUiPageInfo easyUiPage,Map<String, Object> searchParams) throws Exception;
	
	/**
	 * 新增汇率信息
	 * @param simpleParities
	 * @return
	 */
	JsonResult createParities(FSimpleParitiesVo simpleParities) throws Exception;
}
