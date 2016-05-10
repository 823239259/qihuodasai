package com.tzdr.business.service.volume.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tzdr.business.service.volume.VolumeDeductibleService;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.utils.ConnditionVo;
import com.tzdr.domain.dao.volume.VolumeDeductibleDao;
import com.tzdr.domain.vo.VolumeDeductibleVo;
import com.tzdr.domain.web.entity.VolumeDeductible;

/**
 * 
 * 
 * <p></p>
 * @author LiuQing
 * @see
 * @version 2.0
 * 2015年4月27日下午5:45:30
 */
@Service
@Transactional(propagation=Propagation.REQUIRED)
public class VolumeDeductibleServiceImpl extends BaseServiceImpl<VolumeDeductible,VolumeDeductibleDao> 
                                                      implements VolumeDeductibleService {

	@Override
	public PageInfo<VolumeDeductibleVo> queryDataListVo(
			PageInfo<VolumeDeductibleVo> pageInfo, ConnditionVo connVo) {
		StringBuffer sql = new StringBuffer(
				  " SELECT w.id,w.name,w.deal_start_date_value,w.deal_end_date_value,w.type,w.type_code,"
				+ " w.use_type,w.money, w.release_num,w.create_time,"
				+ " (SELECT count(*) FROM w_volume_detail d WHERE d.v_id = w.id AND d.use_state=1) use_num,"
				+ " w.startup_date_value,w.end_date_value,w.end_day_value,w.state_value,w.deadline_type,"
				+ " w.limit_type,(SELECT s.realname FROM sys_user s WHERE s.id = w.create_user_id) createUserName"
				+ " FROM w_volume_deductible w WHERE 1 = 1 ");
		
		//sql.append(" ORDER BY w.create_time DESC");
		return this.getEntityDao()
				.queryPageBySql(pageInfo,sql.toString(),VolumeDeductibleVo.class,connVo,null);
		
	}

	@Override
	public synchronized String queryMaxTypeCode() {
		List<Map<String,Object>> data = 
				this.getEntityDao().queryMapBySql(" SELECT MAX(type_code) typeCode FROM w_volume_deductible", null);
		if (data != null && data.size() > 0) {
			Object maxObj = data.get(0).get("typeCode");
			if (maxObj != null) {
				String typeCode = String.valueOf(maxObj);
				String indexCode = typeCode.substring(1);
				int indexValue = new Integer(indexCode);
				return "B" + (indexValue + 1);
			}
		}
		return "B1001";
	}

	

	
	
}

