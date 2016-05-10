package com.tzdr.business.service.userTrade;


import java.util.Map;

import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.web.support.EasyUiPageInfo;
import com.tzdr.domain.web.entity.FSimpleConfig;

/**
 * 
 * 
 * <p></p>
 * @author LiuYang
 * @see 
 * FSimpleProductConfigService
 * @version 2.0
 * 2015年9月17日下午14:33:13
 */
public interface FSimpleProductConfigService extends BaseService<FSimpleConfig> {
	
	/**
	 * 获取方案配置信息
	 * @param type 配置信息类型 
	 * @return
	 */
	public FSimpleConfig getFSimpleConfigByType(Integer type);
	
	
	/**
	 * 提供后台获取数据
	 * @param easyUiPage
	 * @param searchParams
	 * @return
	 */
	public PageInfo<Object> getData(EasyUiPageInfo easyUiPage, Map<String, Object> searchParams);
}
