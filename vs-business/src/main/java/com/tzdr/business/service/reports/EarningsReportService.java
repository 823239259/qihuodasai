package com.tzdr.business.service.reports;

import java.util.List;

import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.utils.ConnditionVo;
import com.tzdr.domain.vo.EarningsVo;

/**
 * 
 * 
 * <p></p>
 * @author LiuQing
 * @see
 * @version 2.0
 * 2015年3月20日 下午1:10:15
 */
public interface EarningsReportService {
	
	/**
	 * 
	 * @param connVo ConnditionVo
	 * @return List<EarningsVo>
	 */
	public List<EarningsVo> queryDataListVo(ConnditionVo connVo);
	
	/**
	* @Title: queryPageDataListVo    
	* @Description: 获取A股收益日报表信息
	* @param dataPage  分页参数
	* @param connVo  查询条件
	* @return
	 */
	public PageInfo<EarningsVo> queryPageDataListVo(PageInfo<EarningsVo> dataPage,ConnditionVo connVo);
	
	/**
	* @Title: getDataTotalVo    
	* @Description: 获取A股收益日报表合计信息
	* @param connVo
	* @return
	 */
	public EarningsVo getDataTotalVo(ConnditionVo connVo);

}
