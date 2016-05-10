package com.tzdr.business.service.userTrade;


import java.util.List;
import java.util.Map;
import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.web.support.EasyUiPageInfo;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.domain.vo.ftse.FSimpleConfigVo;
import com.tzdr.domain.web.entity.FSimpleConfig;

/**
 * 
 * 
 * <p></p>
 * @author WangPinQun
 * @see 
 * FSimpleConfigService
 * @version 2.0
 * 2015年9月16日下午14:33:13
 */
public interface FSimpleConfigService extends BaseService<FSimpleConfig> {
	
	/**
	 * 获取方案配置信息
	 * @param type 配置信息类型 
	 * @return
	 */
	public FSimpleConfig getFSimpleConfigByType(Integer type);
	
	/**
	 * 获取某一类配置信息
	 * @param type  配置信息类别
	 * @return
	 */
	public List<FSimpleConfig> findFSimpleConfigsByType(Integer type); 
	
	/**
	 * 获取某个配置项信息
	 * @param type  类型
	 * @param tranLever 手数
	 * @return  
	 */
	public FSimpleConfig getFSimpleConfig(Integer type,String tranLever);
	
	/**
	 * 新增方案配置，或者更新方案配置
	 * @param simpleConfig
	 * @return
	 * @throws Exception
	 */
	public JsonResult saveOrUpdateConfig(FSimpleConfigVo simpleConfig,int type) throws Exception;
	
	/**
	 * 删除一条方案配置信息
	 * @param simpleConfig
	 * @return
	 * @throws Exception
	 */
	public JsonResult deleteConfig(FSimpleConfigVo simpleConfig) throws Exception;
	
	/**
	 * 查询方案配置列表
	 * @param easyUiPage
	 * @param searchParams
	 * @return
	 * @throws Exception
	 */
	public PageInfo<Object> getPageDatas(EasyUiPageInfo easyUiPage,Map<String, Object> searchParams,int type) throws Exception;
}
