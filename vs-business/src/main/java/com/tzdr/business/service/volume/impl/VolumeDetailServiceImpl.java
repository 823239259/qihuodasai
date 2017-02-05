package com.tzdr.business.service.volume.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import jodd.util.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.tzdr.business.service.volume.VolumeDeductibleService;
import com.tzdr.business.service.volume.VolumeDetailService;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.utils.ConnditionVo;
import com.tzdr.common.utils.Dates;
import com.tzdr.common.utils.TypeConvert;
import com.tzdr.domain.dao.volume.VolumeDetailDao;
import com.tzdr.domain.vo.UserVolumeDetailVo;
import com.tzdr.domain.vo.VolumeDetailVo;
import com.tzdr.domain.vo.VolumeDetailVoNew;
import com.tzdr.domain.web.entity.VolumeDeductible;
import com.tzdr.domain.web.entity.VolumeDetail;

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
public class VolumeDetailServiceImpl extends BaseServiceImpl<VolumeDetail,VolumeDetailDao> implements VolumeDetailService {
	
	@Autowired
	private VolumeDeductibleService volumeDeductibleService;

	@Override
	public PageInfo<VolumeDetailVoNew> queryDataListVo(
			PageInfo<VolumeDetailVoNew> pageInfo, ConnditionVo connVo) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer(
				  " SELECT d.id,d.`code`,v.`name`,v.type,v.use_type,v.money,d.real_pay_amount,d.use_date_value,"
				+ " d.uid,d.remark,d.use_state,v.end_date_value, "
				+ " (SELECT MAX(v.tname) FROM w_user_verified v WHERE v.uid = d.uid) use_username, u.mobile "
				+ " FROM w_volume_deductible v LEFT JOIN w_volume_detail d ON v.id = d.v_id "
				+ "LEFT JOIN w_user u ON u.id=d.uid WHERE 1=1 ");
		
		String name = connVo.getValueStr("name");
		if (name != null) {
			sql.append(" AND v.name LIKE ?");
			params.add("%"+ name + "%");
		}
		String type = connVo.getValueStr("type");
		if (type != null) {
			sql.append(" AND v.type = ?");
			params.add(type);
		}
		String useType = connVo.getValueStr("useType");
		if (useType != null) {
			sql.append(" AND v.use_type = ?");
			params.add(useType);
		}
		String useDateValueStart = connVo.getValueStr("useDateValue_start");
		String useDateValueEnd = connVo.getValueStr("useDateValue_end");
		if (useDateValueStart != null && useDateValueEnd != null) {
			long startLong = TypeConvert.strToZeroDate1000(useDateValueStart, 0);
			long endLong = TypeConvert.strToZeroDate1000(useDateValueEnd, 1,-1);
			sql.append(" AND (d.use_date_value >= ? AND d.use_date_value <= ?)");
			params.add(startLong);
			params.add(endLong);
		}
		
		String useState = connVo.getValueStr("stateType");
		
		if(useState != null){
			if(useState.equals("1")){
				sql.append(" AND d.use_state = 1");
			}else{
				long nowDate = TypeConvert.dbDefaultDate(); 
				if(useState.equals("-3")){
					sql.append(" AND d.use_state = -1  AND (v.end_date_value <=?)" );
					params.add(nowDate);
				}else if(useState.equals("-2")){
					sql.append(" AND d.use_state = -1  AND (d.uid is null or d.uid = '') AND (v.end_date_value > ?)" );
					params.add(nowDate);
				}else{
					sql.append(" AND d.use_state = -1  AND (d.uid is not null and d.uid <> '') AND (v.end_date_value > ?)") ;
					params.add(nowDate);
				}
			}
		}
		
		String mobile = connVo.getValueStr("mobile");
		if (!StringUtil.isBlank(mobile)) {
			sql.append(" AND u.mobile LIKE ?");
			params.add("%" + mobile + "%");
		}
		
		return this.getEntityDao().queryPageBySql(pageInfo, sql.toString(), VolumeDetailVoNew.class, connVo, params.toArray());
	}

	@Override
	public VolumeDetail getNewVolumeDetail(String uid) {
		
		List<VolumeDetail> newVolumeDetails = this.getEntityDao().findNewVolumeDetail(new Date().getTime()/1000);
		
		VolumeDetail newVolumeDetail = null;
		if (newVolumeDetails != null && newVolumeDetails.size() > 0) {
			newVolumeDetail = newVolumeDetails.get(0);
			newVolumeDetail.setUid(uid);
			newVolumeDetail.setTimeValueOfGetVolume(Dates.getCurrentLongDate());
			this.getEntityDao().update(newVolumeDetail);
		}
		return newVolumeDetail;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<UserVolumeDetailVo> findVolume(String uid,Integer useState) {
		
		if(StringUtil.isBlank(uid)){
			return null;
		}
		List<Object> params = Lists.newArrayList();
		StringBuffer sql = new StringBuffer("SELECT v.id as id,v.code as code,d.money as money,d.name as name,d.use_type as useType, ");
		sql.append(" d.startup_date_value as startupDateValue,d.end_date_value as endDateValue, ");
		sql.append(" if(UNIX_TIMESTAMP() > end_date_value AND v.use_state = -1,2,v.use_state) as useState,v.remark as remark, ");
		sql.append(" v.use_date_value as userDateValue,v.time_value_of_get_volume as timeValueOfGetVolume,v.uid as uid ");
		sql.append(" FROM w_volume_detail v,w_volume_deductible d ");
		sql.append(" WHERE v.v_id=d.id AND v.uid=?  ");
		params.add(uid);
		sql.append(" AND v.use_state=? ");
		params.add(useState);
	
		sql.append(" AND d.end_date_value >= ?");
		params.add(new Date().getTime()/1000);
		sql.append(" AND d.startup_date_value <= ?");
		params.add(new Date().getTime()/1000);
		sql.append(" ORDER BY v.time_value_of_get_volume DESC ");

		List<UserVolumeDetailVo> userVolumeDetailVoList = nativeQuery(sql.toString(), params, UserVolumeDetailVo.class);
		
		return userVolumeDetailVoList;
	}

	
	@Override
	@SuppressWarnings("unchecked")
	public List<UserVolumeDetailVo> findVolumeByUId(String uid,Integer useState) {
		
		if(StringUtil.isBlank(uid)){
			return null;
		}
		List<Object> params = Lists.newArrayList();
		StringBuffer sql = new StringBuffer("SELECT v.id as id,v.code as code,d.money as money,d.name as name,d.use_type as useType, ");
		sql.append(" d.startup_date_value as startupDateValue,d.end_date_value as endDateValue, ");
		sql.append(" if(UNIX_TIMESTAMP() > end_date_value AND v.use_state = -1,2,v.use_state) as useState,v.remark as remark, ");
		sql.append(" v.use_date_value as userDateValue,v.time_value_of_get_volume as timeValueOfGetVolume,v.uid as uid ");
		sql.append(" FROM w_volume_detail v,w_volume_deductible d ");
		sql.append(" WHERE v.v_id=d.id AND v.uid=? ");
		params.add(uid);
		if(useState == null){  //获取所有用户劵
			sql.append(" ORDER BY useState ASC,d.money,v.time_value_of_get_volume DESC ");
		}else if(useState != null && (useState == -1 || useState == 1)){  //获取用户未、已使用劵
			sql.append(" AND v.use_state=? ");
			params.add(useState);
			if(useState == -1){
				sql.append(" AND d.end_date_value >= ?");
				params.add(new Date().getTime()/1000);
				sql.append(" ORDER BY v.time_value_of_get_volume DESC ");
			}else{
				sql.append(" ORDER BY v.use_date_value DESC ");
			}
		}else if(useState != null && useState == 2){  //获取用户已过期劵
			sql.append(" AND d.end_date_value< ? AND v.use_state=-1 ");
			params.add(new Date().getTime()/1000);
			sql.append(" ORDER BY d.end_date_value DESC ");
		}
		
		List<UserVolumeDetailVo> userVolumeDetailVoList = nativeQuery(sql.toString(), params, UserVolumeDetailVo.class);
		
		return userVolumeDetailVoList;
	}

	@Override
	public int queryDetailMaxCodeByVolumeId(String volumeDeductibleId) {
		
		List<Map<String,Object>> data = 
				this.getEntityDao().queryMapBySql(
						" SELECT max(v.`code`) typeCode from w_volume_detail v WHERE v.v_id = ?", 
						volumeDeductibleId);
		if (data != null && data.size() > 0) {
			Object maxObj = data.get(0).get("typeCode");
			if (maxObj != null) {
				String typeCode = String.valueOf(maxObj);
				int indexValue = new Integer(typeCode);
				return (indexValue + 1);
			}
		}
		return TypeConvert.VOLUME_INI;
	}

	@Override
	public void saveCollection(List<VolumeDetail> details,VolumeDeductible volumeDeductible) {
		
		if (details != null && details.size() > 0) {
			for (VolumeDetail detail:details) {
				this.save(detail);
			}
		}
		volumeDeductibleService.update(volumeDeductible);
	}

	@Override
	public VolumeDetail findByVolumeid(String volumeid) {
		
		return get(volumeid);
	}
}

 