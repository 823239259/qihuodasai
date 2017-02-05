package com.tzdr.business.service.volume;

import java.util.List;

import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.utils.ConnditionVo;
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
 * 2015年4月27日下午5:43:09
 */
public interface VolumeDetailService extends BaseService<VolumeDetail> {
	
	/**
	 * 
	 * @param page PageInfo<VolumeDetailVoNew>
	 * @param connVo ConnditionVo
	 * @return PageInfo<VolumeDetailVoNew>
	 */
	public PageInfo<VolumeDetailVoNew> queryDataListVo(PageInfo<VolumeDetailVoNew> pageInfo,
			ConnditionVo connVo);
	
	public VolumeDetail getNewVolumeDetail(String uid);
	
	/**
	* @Description: 根据用户编号以及查询类型获取用户所有劵
	* @Title: findVolumeByUId
	* @param uid  用户编号
	* @param useState 查询类型 ；值为：null(所有用户劵)、-1：未使用、1：已使用、2：已过期
	* @return List<UserVolumeDetailVo>    返回类型
	 */
	public List<UserVolumeDetailVo> findVolumeByUId(String uid,Integer useState);
	
    /**
     * 
     * @param volumeDeductibleId String
     * @return int
     */
	public int queryDetailMaxCodeByVolumeId(String volumeDeductibleId);
	
	/**
	 * 
	 * @param details List<VolumeDetail>
	 */
	public void saveCollection(List<VolumeDetail> details,VolumeDeductible volumeDeductible);
	
	/**
	 * 查询可用的现金劵
	 * @param uid
	 * @param useState
	 * @return
	 * @date 2015年5月6日
	 * @author zhangjun
	 */
	public List<UserVolumeDetailVo> findVolume(String uid,Integer useState);
	/**
	 * 发放抵扣券时验证是否发放
	 * @param volumeid
	 * @return
	 */
	public VolumeDetail findByVolumeid(String volumeid);
	
}

