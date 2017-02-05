package com.tzdr.cms.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jodd.util.StringUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.tzdr.business.cms.service.auth.AuthService;
import com.tzdr.business.cms.service.user.UserService;
import com.tzdr.business.service.exception.RuntimeTzdrException;
import com.tzdr.business.service.generalize.AgentReturnInfoService;
import com.tzdr.business.service.generalize.GeneralizeService;
import com.tzdr.business.service.wuser.UserVerifiedService;
import com.tzdr.business.service.wuser.WUserService;
import com.tzdr.cms.constants.ViewConstants;
import com.tzdr.cms.support.BaseCmsController;
import com.tzdr.cms.utils.DataGridVo;
import com.tzdr.cms.utils.WebUtil;
import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.utils.ConnditionVo;
import com.tzdr.common.utils.MessageUtils;
import com.tzdr.common.utils.SpringUtils;
import com.tzdr.common.utils.TypeConvert;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.domain.cms.entity.user.User;
import com.tzdr.domain.vo.AgentReturnInfoCmsVo;
import com.tzdr.domain.vo.AgentVo;
import com.tzdr.domain.vo.ProgramAgentCmsVo;
import com.tzdr.domain.web.entity.UserVerified;
import com.tzdr.domain.web.entity.WUser;

/**
 * @author zhouchen
 * @author LiuQing
 * @version 创建时间：2015年1月14日 上午11:01:04
 * 方案监控
 */
@Controller
@RequestMapping("/admin/agent")
public class AgentController extends BaseCmsController<WUser> {
	private static Logger log = LoggerFactory.getLogger(AgentController.class);

	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthService authService;
	

	@Autowired
	private WUserService wuserService;
	
	@Autowired
	private AgentReturnInfoService agentReturnInfoService;
	
	
	@Override
	public BaseService<WUser> getBaseService() {
		return wuserService;
	}

	public AgentController() {
		setResourceIdentity("sys:customerService:agent");
	}
	
	
	@RequestMapping(value = "/listAgents")
	public String listAgents(HttpServletRequest request,HttpServletResponse resp) throws Exception {
		return ViewConstants.WuserViewJsp.LIST_AGENTS_VIEW;
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
				
				//AgentReturnInfoService agentReturnInfoService = SpringUtils.getBean(AgentReturnInfoService.class);
				//agentReturnInfoService.agentDayReturnIncomeExecute();
				
				DataGridVo<AgentReturnInfoCmsVo> grid = new DataGridVo<AgentReturnInfoCmsVo>();
				PageInfo<AgentReturnInfoCmsVo> dataPage = new PageInfo<AgentReturnInfoCmsVo>(request);
				dataPage.setCurrentPage(dataPage.getCurrentPage() - 1);
				ConnditionVo connVo = new ConnditionVo(request);
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
	
	
	
	
	
	
	
	/**
	 * 代理商
	 * @param request HttpServletRequest
	 * @param resp HttpServletResponse
	 * @throws Exception
	 */
	@RequestMapping(value = "/agentsList")
	public void agentsList(@ModelAttribute AgentVo agentVo,HttpServletRequest request,HttpServletResponse resp) throws Exception {
			try {
				DataGridVo<AgentVo> grid = new DataGridVo<AgentVo>();
				PageInfo<WUser> dataPage = new PageInfo<WUser>(request);
				dataPage.setCurrentPage(dataPage.getCurrentPage() - 1);
				PageInfo<WUser> wuseres = this.wuserService.queryDataPageByAgents(dataPage, agentVo);
				for (WUser wu:wuseres.getPageResults()) {
					AgentVo vo = new AgentVo(wu);
					vo.setChTitle("股神");
					List childs = this.generalizeService.queryChildsCountByParentId(wu.getId());
					if (childs != null) {
						vo.setChildNumber(childs.size());
						
						if (vo.getUname() == null || "".equals(vo.getUname())) {
							UserVerified uv = userVerifiedService.queryUserVerifiedByUid(wu.getId());
							if (uv != null) {
								vo.setUname(uv.getTname());
							}
							else {
								vo.setUname(wu.getUname());
							}
						}
						
						vo.setAllChildNumber(this.generalizeService.queryChildsSize(wu.getId()));
					}
					if (wu.getUserId() != null) {
						User user = this.userService.get(wu.getUserId());
						vo.setCreateUsername(user.getRealname());;
					}
					grid.add(vo);
				}
				//this.userTradeService.queryPageDataByConndition(null);
				grid.setTotal(wuseres.getTotalCount());
				WebUtil.printText(JSON.toJSONString(grid), resp);
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
	}
	
	@Autowired
	private GeneralizeService generalizeService;
	
	@Autowired
	private UserVerifiedService userVerifiedService;
	
	@RequestMapping(value = "/saveAgents")
	public void saveAgents(@ModelAttribute AgentVo vo, HttpServletRequest request,HttpServletResponse resp) {
		List<WUser> wuserList = wuserService.queryByUserType(TypeConvert.USER_TYPE_CMS);
		WUser parent = null;
		if (wuserList != null && wuserList.size() > 0) {
			parent = wuserList.get(0);
		}
		else {
			throw new RuntimeTzdrException("com.tzdr.business.agent.message",null);
		}
		Double rebate = vo.getRebate();
		Double parentRebate = 0D;
		Double parentDefaultRebate = 0D;
		
		if (parent != null) {
			parentRebate = parent.getRebate();
			parentRebate = parentRebate == null?0D:parentRebate;
			parentDefaultRebate = parent.getSubordinateDefaultRebate();
			parentDefaultRebate = parentDefaultRebate == null?0D:parentDefaultRebate;
			parentDefaultRebate = parentDefaultRebate > parentRebate?0D:parentDefaultRebate;
		}
		
		User user = this.authService.getCurrentUser();
		if (rebate == null || rebate == 0D ) {
			if (parentDefaultRebate > parentRebate) {
				rebate = 0D;
			}
			else {
				rebate = parentDefaultRebate;
			}
		}
		else if (rebate > 0 && rebate <= 100) {
			if (rebate > parentRebate) {
				rebate = parentDefaultRebate;
			}
		}
		else if (rebate > 100){
			throw new RuntimeTzdrException("com.tzdr.business.value.not.greetmac",null);
		}
		/*if (parent != null && vo.getRebate() > parent.getRebate()) {
			throw new RuntimeTzdrException("com.tzdr.business.agent.error",
					new String[]{"本级代理不能返点大于" + parent.getRebate() + "%"});
		}*/
		
		WUser wuser = new WUser();
		wuser.setUserType(TypeConvert.AGENT_TYPE);
		wuser.setMobile(vo.getMobile());
		wuser.setPassword(vo.getPassword());
		wuser.setUname(vo.getUname());
		wuser.setRebate(rebate);
		wuser.setFund(0D);
		wuser.setIncome(0D);
		wuser.setIsDel((short)0);
		wuser.setScore(0D);
		wuser.setParentNode(parent);
		wuser.setScore1(0D);
		wuser.setScore2(0D);
		wuser.setXumoney(0D);
		wuser.setCtime(TypeConvert.dbDefaultDate());
		if (user != null) {
			wuser.setUserId(user.getId());
		}
		this.wuserService.saveWUser(wuser);
		WebUtil.printText("success", resp);
	}
	
	@RequestMapping(value = "/updateAgents")
	public void updateAgents(@ModelAttribute AgentVo vo, HttpServletRequest request,HttpServletResponse resp) {
		WUser wuser = this.wuserService.getUser(vo.getId());

		wuser.setUname(vo.getUname());
		
		WUser parentNode = wuser.getParentNode();
		if (parentNode != null) {
			parentNode = this.wuserService.get(parentNode.getId());
			if (parentNode.getRebate() == null || parentNode.getRebate() < vo.getRebate()) {
				Double rebate = parentNode.getRebate();
				if (parentNode.getRebate() == null) {
					rebate = 0D;
				}
				throw new RuntimeTzdrException("com.tzdr.business.agent.current.parent.value",
						new Object[]{rebate});
			}
		}
		if(wuser.getRebate()>vo.getRebate()){
			wuserService.lowerUserRebates(wuser.getMobile());
		}

//		if (vo.getRebate() < (wuser.getRebate() == null?0:wuser.getRebate()) ) {
//			throw new RuntimeTzdrException("com.tzdr.business.value.greet.old.value",null);
//		}
		User user = this.authService.getCurrentUser();
		if (user != null) {
			wuser.setUserId(user.getId());
		}
		wuser.setRebate(vo.getRebate());
		this.wuserService.updateUser(wuser);
		WebUtil.printText("success", resp);
	}
	
	/**
	 * 获取用户信息
	 * @param request
	 * @param mobile  手机号码
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getWUser")
	@ResponseBody
	public JsonResult getWUser(HttpServletRequest request,String mobile) throws Exception {
		JsonResult result=new JsonResult(true);
		if(StringUtil.isBlank(mobile)){
			result.setSuccess(false);
			result.setMessage(MessageUtils.message("no.mobile"));
			return result;
		}
		
		WUser wuser = wuserService.findByMobileOrEmail(mobile);
		
		if(wuser == null || !mobile.equals(wuser.getMobile())){
			result.setSuccess(false);
			result.setMessage(MessageUtils.message("no.user"));
			return result;
		}
		
//		UserVerified userVerified = userVerifiedService.queryUserVerifiedByUi(wuser.getId());
		
		Map<Object,Object> data = new HashMap<Object, Object>();
		
		data.put("trueName", wuser.getUname()/*userVerified == null ? null : userVerified.getTname()*/);
		data.put("uid",wuser.getId());
		
		result.setData(data);
		
		return result;
	}
	
	/**
	 * 获取用户信息
	 * @param request
	 * @param mobile  手机号码
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateAgentTree")
	@ResponseBody
	public JsonResult updateAgentTree(HttpServletRequest request,String mobile,String subordinateUid) throws Exception {
		JsonResult result=new JsonResult(true);
		try {
			if(StringUtil.isBlank(mobile)){
				result.setSuccess(false);
				result.setMessage(MessageUtils.message("no.mobile"));
				return result;
			}else if(StringUtil.isBlank(subordinateUid)){
				result.setSuccess(false);
				result.setMessage(MessageUtils.message("no.migrate.uid"));
				return result;
			}
			
			WUser wuser = wuserService.findByMobileOrEmail(mobile);   //获取新上级
			
			WUser subordinateWuser = wuserService.get(subordinateUid);  //获取待迁移代理树
			
			if(wuser == null || !mobile.equals(wuser.getMobile()) || subordinateWuser == null){
				result.setSuccess(false);
				result.setMessage(MessageUtils.message("no.user"));
				return result;
			}else if(mobile.equals(subordinateWuser.getMobile())){
				result.setSuccess(false);
				result.setMessage(MessageUtils.message("no.migrate.oneself"));
				return result;
			}
			
			//迁移代理树
			wuserService.updateAgentTree(mobile, subordinateUid);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
			result.setMessage(e.getMessage());
			return result;
		}
	}
}
