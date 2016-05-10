package com.tzdr.business.service.endplan;

import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.utils.ConnditionVo;
import com.tzdr.domain.vo.EndPlanVo;
import com.tzdr.domain.vo.ParentAccountExpireEndVo;

/**
 * 
 * 
 * <p></p>
 * @author LiuQing
 * @see
 * @version 2.0
 * 2015年5月11日上午9:39:15
 */
public interface EndPlanService {
	

	/**
	 * 
	 * @param page PageInfo<EndPlanVo>
	 * @param connVo ConnditionVo
	 * @return PageInfo<EndPlanVo>
	 */
	public PageInfo<EndPlanVo> queryEndPlan(PageInfo<EndPlanVo> page,ConnditionVo connVo);
	
	/**
	 * 
	 * @param page PageInfo<EndPlanVo>
	 * @param connVo ConnditionVo
	 * @return PageInfo<EndPlanVo>
	 */
	public PageInfo<EndPlanVo> queryEndPlan02(PageInfo<EndPlanVo> page,ConnditionVo connVo);
	
	/**
	 * 
	 * @param page PageInfo<EndPlanVo>
	 * @param connVo ConnditionVo
	 * @return PageInfo<EndPlanVo>
	 */
	public PageInfo<EndPlanVo> queryAllEndPlan(PageInfo<EndPlanVo> page,ConnditionVo connVo);
	
	
	/**
	 * 审核不通过方法
	 * @param connVo ConnditionVo connVo
	 */
	public void updateEndPlanFail(ConnditionVo connVo);
	
	/**
	 * 审核不通过方法
	 * @param connVo ConnditionVo connVo
	 */
	public void updateEndPlanSuccessful(ConnditionVo connVo);
	
	/**
	 * 提交终结计划
	 * @param connVo ConnditionVo
	 */
	public void updateEndPlanSubmit(ConnditionVo connVo);
	

	/**
	 * 
	 * @param page PageInfo<ParentAccountExpireEndVo>
	 * @param connVo ConnditionVo
	 * @return PageInfo<ParentAccountExpireEndVo>
	 */
	public PageInfo<ParentAccountExpireEndVo> queryParentAccountExpire(PageInfo<ParentAccountExpireEndVo> page,
			ConnditionVo connVo);
	
	
	/**
	 * 当前母账户日期小于等于5天的方案 自动限制子账户买入。
	 */
	public void autoLimitParentAccount();
	
	
	/**
	 * 方案延期短信
	 */
	public void programmeExtensionSendMessage();
	
	

}
