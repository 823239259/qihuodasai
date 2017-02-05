package com.tzdr.business.service.operational;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import jodd.util.ObjectUtil;
import jodd.util.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.tzdr.business.cms.service.auth.AuthService;
import com.tzdr.business.exception.OperationalConfigException;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.exception.BusinessException;
import com.tzdr.common.utils.Dates;
import com.tzdr.domain.cms.entity.user.User;
import com.tzdr.domain.constants.Constant;
import com.tzdr.domain.dao.operational.OperationalConfigDao;
import com.tzdr.domain.vo.OperationalConfigVo;
import com.tzdr.domain.web.entity.OperationalConfig;

/**
 * 运营配置
 * @zhouchen
 * 2015年1月12日
 */
@Service
public class OperationalConfigService  extends BaseServiceImpl<OperationalConfig,OperationalConfigDao> {
	
	
	@Autowired
	private AuthService authService;
	
	
	@Override
	public void update(OperationalConfig config) throws BusinessException {
		
		String configId = config.getId();
		OperationalConfig  dbConfig= getEntityDao().get(configId);
		if (ObjectUtil.equals(dbConfig, null)){
			throw new OperationalConfigException("business.update.not.found.data",null);
		}
		
		/**
		 * 1:友情链接 2：新闻栏目 3：新闻 4：banner 5：合作伙伴
		 */
		switch (config.getType()) {
		case 1:
			dbConfig.setName(config.getName());
			dbConfig.setLinkUrl(config.getLinkUrl());
			dbConfig.setIsEnable(config.getIsEnable());
			break;
		case 2:
			dbConfig.setName(config.getName());
			dbConfig.setIsEnable(config.getIsEnable());	
			break;
		case 3:
			dbConfig.setName(config.getName());
			dbConfig.setParentConfig(get(config.getParentConfig().getId()));
			dbConfig.setContent(config.getContent());
			dbConfig.setIsTop(config.getIsTop());
			dbConfig.setSummary(config.getSummary());
			dbConfig.setKeyWord(config.getKeyWord());
			dbConfig.setDefineReleaseTime(config.getDefineReleaseTime());
			break;
		case 4:
			dbConfig.setName(config.getName());
			dbConfig.setLinkUrl(config.getLinkUrl());
			dbConfig.setIsEnable(config.getIsEnable());
			dbConfig.setImgPath(config.getImgPath());
			dbConfig.setValueType(config.getValueType());
			break;
		case 5:
			dbConfig.setName(config.getName());
			dbConfig.setLinkUrl(config.getLinkUrl());
			dbConfig.setIsEnable(config.getIsEnable());
			dbConfig.setImgPath(config.getImgPath());
			break;
		case 8:
			dbConfig.setName(config.getName());
			dbConfig.setLinkUrl(config.getLinkUrl());
			dbConfig.setIsEnable(config.getIsEnable());
			dbConfig.setImgPath(config.getImgPath());
			dbConfig.setValueType(config.getValueType());
			break;
			case 9:
				dbConfig.setName(config.getName());
				dbConfig.setLinkUrl(config.getLinkUrl());
				dbConfig.setIsEnable(config.getIsEnable());
				dbConfig.setImgPath(config.getImgPath());
				dbConfig.setValueType(config.getValueType());
				break;
		default:
			break;
		}
		
		setOperateLog(dbConfig,"更新配置","edit");
		super.update(dbConfig);
	}
	
	@Override
	public void save(OperationalConfig o) throws BusinessException {
		setOperateLog(o,"新增配置","add");
		if (3==o.getType()){
			o.setParentConfig(get(o.getParentConfig().getId()));			
		}
		super.save(o);
	}
	
	@Override
	public void removes(Serializable... ids) throws BusinessException {
		for (Serializable id : ids){
			OperationalConfig config = getEntityDao().get(id);
    		if (ObjectUtil.equals(config, null)){
    			throw new OperationalConfigException("business.delete.not.found.data",null);
    		}
    		config.setDeleted(Boolean.TRUE);
    		setOperateLog(config,"删除配置","edit");
    		super.update(config);
    	}
	}
	
	/**
	 * 设置是否禁用
	 * @param ids
	 * @param isEnable
	 */
	public void setIsEnable(String []ids,boolean isEnable){
		for(String id:ids){
			OperationalConfig   config = get(id);
			if (ObjectUtil.equals(null, config)){
				throw new OperationalConfigException("business.update.not.found.data",null);
			}
			config.setIsEnable(isEnable);
			super.update(config);
		}
	}
	
	/**
	 * 设置发布
	 * @param ids
	 * @param
	 */
	public void setIsRelease(String []ids,boolean isRelease){
		for(String id:ids){
			OperationalConfig   config = get(id);
			if (ObjectUtil.equals(null, config)){
				throw new OperationalConfigException("business.update.not.found.data",null);
			}
			config.setIsRelease(isRelease);
			config.setReleaseTime(Dates.getCurrentLongDate());
			super.update(config);
		}
	}
	
	/**
	 * 设置置顶
	 * @param ids
	 * @param
	 */
	public void setTop(String []ids,boolean isTop){
		for(String id:ids){
			OperationalConfig   config = get(id);
			if (ObjectUtil.equals(null, config)){
				throw new OperationalConfigException("business.update.not.found.data",null);
			}
			config.setIsTop(isTop);
			super.update(config);
		}
	}
	/**
	 * 设置操作记录
	 * @param operationalConfig
	 * @param operateContent
	 * @param operateType
	 */
	private void setOperateLog(OperationalConfig operationalConfig,String operateContent,String operateType){
		User   loginUser = authService.getCurrentUser();	
		operationalConfig.setOperateContent(operateContent);
		if (StringUtil.equals(operateType,"edit")){
			operationalConfig.setUpdateTime(Dates.getCurrentLongDate());
			operationalConfig.setUpdateUser(loginUser.getRealname());
			operationalConfig.setUpdateUserOrg(loginUser.getOrganization().getName());
			operationalConfig.setUpdateUserId(loginUser.getId());
			return ;
		}
		
		operationalConfig.setCreateTime(Dates.getCurrentLongDate());
		operationalConfig.setCreateUser(loginUser.getRealname());
		operationalConfig.setCreateUserOrg(loginUser.getOrganization().getName());
		operationalConfig.setCreateUserId(loginUser.getId());
		return ;
	}
	
	/**
	 * 查询新闻栏目和banner以及链接
	 * @param type
	 * @return
	 * @date 2015年1月22日
	 * @author zhangjun
	 */
	public List<OperationalConfig> findData(int type) {
		return this.getEntityDao().findByIsEnableTrueAndDeletedFalseAndType(type);
	}
	
	/**
	 * 查询新闻栏目下的新闻
	 * @param type
	 * @param config
	 * @return
	 * @date 2015年1月22日
	 * @author zhangjun
	 */
	public List<OperationalConfig> findNews(int type,OperationalConfig config) {
		return this.getEntityDao().findByIsEnableTrueAndDeletedFalseAndTypeAndParentConfig(type,config);
	}

	/**
	 * 查询新闻
	 * @param type
	 * @param
	 * @return
	 * @date 2015年2月2日
	 * @author zhangjun
	 */
	public List<OperationalConfigVo> findTopNews(int type, int newstype) {
		String sql="select t2.id,t2.name,from_unixtime(t2.create_Time,'%Y-%m-%d') as addtime,t2.define_release_time as defineReleaseTime from w_operational_config t1,"
				+ " w_operational_config t2 where t1.id=t2.parent_id "  
				+" and t2.deleted='0' and t2.is_enable='1' and t2.is_release='1'"
				+" and t1.deleted='0' and t1.is_enable='1' and t1.name !='达人动态(APP)' "
				+ " and t2.type=? order by t2.is_top desc, t2.define_release_time desc  limit  6 ";
		List<Object> params = Lists.newArrayList();
		params.add(newstype);
		List<OperationalConfigVo> news = nativeQuery(sql, params, OperationalConfigVo.class);
		return news;
		
	}

	/**
	 * 分页查询新闻
	 * @param pageIndex
	 * @param perPage
	 * @date 2015年2月2日
	 * @author zhangjun
	 */
	public PageInfo<OperationalConfig> getNewspage(String pageIndex, String perPage,int type,String colid) {
		PageInfo<OperationalConfig> pageInfo=new PageInfo<OperationalConfig>(Integer.valueOf(perPage),Integer.valueOf(pageIndex)+1);
		Map<String,Object> map=new HashMap<String,Object>();
		Map<String,Boolean> sortmap=new LinkedHashMap<String,Boolean>();
		sortmap.put("isTop", false);
		sortmap.put("defineReleaseTime", false);
		
		map.put("EQ_type", type);
		if(StringUtil.isNotBlank(colid)){
			map.put("EQ_parentConfig.id", colid);
		}
		map.put("NOT_parentConfig.name",Constant.APP_NEWS_COLUNM);
		map.put("EQ_deleted", false);
		map.put("EQ_isRelease", true);
		map.put("EQ_isEnable", true);
		pageInfo=this.query(pageInfo, map,sortmap);
		return pageInfo;
	}

	/**
	 * 查询banner
	 * @param type
	 * @param status
	 * @return
	 * @date 2015年2月9日
	 * @author zhangjun
	 */
	public List<OperationalConfig> findBanners(int type,int status) {
		return this.getEntityDao().findBanners(type,status);
	}
	
	public List<OperationalConfig> findBanner(int type) {
		return this.getEntityDao().findBanner(type);
	}
	/**
	 * 根据名称查询新闻列
	 * @param colname 新闻列表名称
	 * @return
	 * @date 2015年2月5日
	 * @author zhangjun
	 */
	public OperationalConfig getColByName(String colname) {
		return this.getEntityDao().getOperationalConfigByIsEnableTrueAndDeletedFalseAndName(colname);
		
	}
	
	
	/**
	 * 查询新闻栏目和banner以及链接 排除名称
	 * @param type
	 * @return
	 * @date 2015年1月22日
	 * @author zhangjun
	 */
	public List<OperationalConfig> findData(int type,List<String> names ) {
		return this.getEntityDao().findByIsEnableTrueAndDeletedFalseAndTypeAndNameNotIn(type, names);
	}
	/**
	 * 查询栏目
	 * @param type
	 * @param name
	 * @return
	 */
	public List<OperationalConfig> findDataOrderByNameAndType(int type){
		return this.getEntityDao().findDataOrderByNameAndType(type);
	}
}
