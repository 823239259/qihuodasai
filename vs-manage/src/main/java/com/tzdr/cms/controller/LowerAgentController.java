package com.tzdr.cms.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.tzdr.business.cms.service.auth.AuthService;
import com.tzdr.business.cms.service.user.UserService;
import com.tzdr.business.service.generalize.AgentReturnInfoService;
import com.tzdr.business.service.wuser.WUserService;
import com.tzdr.cms.constants.ViewConstants;
import com.tzdr.cms.support.BaseCmsController;
import com.tzdr.cms.utils.DataGridVo;
import com.tzdr.cms.utils.WebUtil;
import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.utils.ConnditionVo;
import com.tzdr.domain.vo.AgentReturnInfoCmsVo;
import com.tzdr.domain.vo.ProgramAgentCmsVo;
import com.tzdr.domain.web.entity.WUser;

/**
 * @author zhouchen
 * @author LiuQing
 * @version 创建时间：2015年1月14日 上午11:01:04
 * 方案监控
 */
@Controller
@RequestMapping("/admin/lowerAgent")
public class LowerAgentController extends BaseCmsController<WUser> {
	private static Logger log = LoggerFactory.getLogger(LowerAgentController.class);

	@Autowired
	private WUserService wuserService;
	
	@Autowired
	private AgentReturnInfoService agentReturnInfoService;
	
	
	@Override
	public BaseService<WUser> getBaseService() {
		return wuserService;
	}

	public LowerAgentController() {
		setResourceIdentity("sys:customerService:lowerAgent");
	}
	
	
	@RequestMapping(value = "/childListAgents")
	public String childListAgents(HttpServletRequest request,HttpServletResponse resp) throws Exception {
		return ViewConstants.WuserViewJsp.LIST_AGENTS_CHILD_VIEW;
	}
	
	/**
	 * 代理商
	 * @param request HttpServletRequest
	 * @param resp HttpServletResponse
	 * TODO 刘庆 未完成工作有【1：查询下级详细列表、2：定时数据采集】
	 * @throws Exception
	 */
	@RequestMapping(value = "/agentsChildList")
	public void agentsChildList(HttpServletRequest request,HttpServletResponse resp) throws Exception {
			try {
				ConnditionVo connVo = new ConnditionVo(request);
				if(connVo.getValueStr("first")==null){
					return;
				}
				//AgentReturnInfoService agentReturnInfoService = SpringUtils.getBean(AgentReturnInfoService.class);
				//agentReturnInfoService.agentDayReturnIncomeExecute();
				
				DataGridVo<AgentReturnInfoCmsVo> grid = new DataGridVo<AgentReturnInfoCmsVo>();
				PageInfo<AgentReturnInfoCmsVo> dataPage = new PageInfo<AgentReturnInfoCmsVo>(request);
				dataPage.setCurrentPage(dataPage.getCurrentPage() - 1);
				dataPage = this.agentReturnInfoService.queryAgentReturnInfoCmsVoByConn(dataPage, connVo);
				grid.add(dataPage.getPageResults());
				grid.setTotal(dataPage.getTotalCount());
				WebUtil.printText(JSON.toJSONString(grid), resp);
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
	}
	
    /**
     * 
     * @param request HttpServletRequest
     * @param resp HttpServletResponse
     * @throws Exception
     */
	@RequestMapping(value = "/agentsDetailChildList")
	public void agentsDetailChildList(HttpServletRequest request,HttpServletResponse resp) throws Exception {
			try {
				DataGridVo<ProgramAgentCmsVo> grid = new DataGridVo<ProgramAgentCmsVo>();
				PageInfo<ProgramAgentCmsVo> dataPage = new PageInfo<ProgramAgentCmsVo>(request);
				dataPage.setCurrentPage(dataPage.getCurrentPage() - 1);
				ConnditionVo connVo = new ConnditionVo(request);
				dataPage = this.agentReturnInfoService.queryProgramAgentCmsVoByConn(dataPage, connVo);
				grid.add(dataPage.getPageResults());
				grid.setTotal(dataPage.getTotalCount());
				WebUtil.printText(JSON.toJSONString(grid), resp);
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
	}
}
