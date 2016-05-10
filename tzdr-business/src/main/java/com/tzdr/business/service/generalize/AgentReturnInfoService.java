package com.tzdr.business.service.generalize;

import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.utils.ConnditionVo;
import com.tzdr.domain.vo.AgentReturnInfoCmsVo;
import com.tzdr.domain.vo.AgentReturnInfoVo;
import com.tzdr.domain.vo.ProgramAgentCmsVo;
import com.tzdr.domain.web.entity.AgentReturnInfo;

/**
 * 
 * 
 * <p></p>
 * @author LiuQing
 * @see 
 * @version 2.0
 * 2015年4月18日上午9:46:04
 */
public interface AgentReturnInfoService extends BaseService<AgentReturnInfo> {
	
	/**
	 * 
	 * @param page PageInfo<AgentReturnInfoVo>
	 * @param conn ConnditionVo
	 * @return PageInfo<AgentReturnInfoVo>
	 */
	public PageInfo<AgentReturnInfoVo> queryAgentReturnInfoVoByConn(PageInfo<AgentReturnInfoVo> page,ConnditionVo conn);
	
	/**
	 * 
	 * @param page PageInfo<AgentReturnInfoVo>
	 * @param conn ConnditionVo
	 * @return PageInfo<AgentReturnInfoVo>
	 */
	public PageInfo<AgentReturnInfoCmsVo> queryAgentReturnInfoCmsVoByConn(PageInfo<AgentReturnInfoCmsVo> page,ConnditionVo conn);
	
	
	/**
	 * 
	 * @param page PageInfo<ProgramAgentCmsVo>
	 * @param conn ConnditionVo
	 * @return PageInfo<ProgramAgentCmsVo>
	 */
	public PageInfo<ProgramAgentCmsVo> queryProgramAgentCmsVoByConn(PageInfo<ProgramAgentCmsVo> page,ConnditionVo conn);
	
	
	/**
	 * 
	 */
	public void agentDayReturnIncomeExecute();
	
	
	
	
	
}
