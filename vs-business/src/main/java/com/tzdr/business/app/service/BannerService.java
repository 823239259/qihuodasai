package com.tzdr.business.app.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.domain.app.vo.BannerVo;
import com.tzdr.domain.dao.operational.OperationalConfigDao;
import com.tzdr.domain.web.entity.OperationalConfig;

/**  
 * @Title: BannerService.java     
 * @Description: banner业务信息管理类    
 * @author： WangPinQun 
 * @E-mail：wangpinqun@tzdr.com
 * @company： 上海信闳投资管理有限公司重庆分公司
 * @address：重庆市渝北区黄山大道中段70号两江新界3栋13楼
 * @date： 2016年3月22日 下午1:47:36    
 * @version： V1.0  
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class BannerService extends BaseServiceImpl<OperationalConfig, OperationalConfigDao>{

	/**
	* @Title: findBannerVos    
	* @Description: 获取banner信息
	* @param type banner类型
	* @return
	 */
	@SuppressWarnings("unchecked")
	public List<BannerVo> findBannerVos(int type){
		
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT c.id,c.img_path,c.link_url FROM w_operational_config c ");
		sql.append(" WHERE c.type=? AND c.is_enable='1' and c.deleted='0' ");
		sql.append(" order by c.value_type asc ");
		List<BannerVo> dataList = this.getEntityDao().queryListBySql(sql.toString(), BannerVo.class, null, new Object[]{type});
		
		return dataList;
	}
}
