package com.tzdr.business.app.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.domain.app.vo.NewsVo;
import com.tzdr.domain.dao.operational.OperationalConfigDao;
import com.tzdr.domain.web.entity.OperationalConfig;

/**
 * 新闻模块 service
 * @author zhouchen
 * 2016年3月25日 下午2:26:24
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class NewsService extends BaseServiceImpl<OperationalConfig, OperationalConfigDao>{

	
	/**
	 * 
	 * @param columnName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<NewsVo> findNews(String columnName){
		
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT t2.id nid, t2.`name` title, DATE_FORMAT(str_to_date( t2.define_release_time, '%Y-%m-%d %H:%i:%s' ),'%m-%d' )  AS issueDate FROM w_operational_config t1, w_operational_config t2  ");
		sql.append(" WHERE t1.id = t2.parent_id AND t2.deleted = '0'  AND t2.is_enable = '1' AND t2.is_release = '1' AND t1.deleted = '0'  ");
		sql.append(" AND t1.is_enable = '1' AND t2.type =3 AND t1.name=? And t1.type=2 ORDER BY t2.is_top DESC, t2.define_release_time DESC  ");
		
		List<NewsVo> dataList = this.getEntityDao().queryListBySql(sql.toString(), NewsVo.class, null, new Object[]{columnName});
		
		return dataList;
	}
}
