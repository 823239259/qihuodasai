package com.tzdr.cms.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jodd.util.StringUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.tzdr.business.service.contractsave.ContractsaveService;
import com.tzdr.cms.constants.ViewConstants;
import com.tzdr.cms.utils.DataGridVo;
import com.tzdr.cms.utils.WebUtil;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.domain.vo.ContractsaveVo;

/**
 * 保存配资协议到第三方
 * @author Administrator
 *
 */

@Controller
@RequestMapping("/admin/contractsave")
public class ContractsaveController {
	private static Logger log = LoggerFactory.getLogger(ContractsaveController.class);
	
	@Autowired
	private ContractsaveService contractsaveService;
	@RequestMapping(value="/list")
	public String list(HttpServletRequest  request){
		return ViewConstants.ContractsaveViewJsp.LIST_VIEW;
	}
	
	@RequestMapping(value = "/listData")
	public void listData(@ModelAttribute ContractsaveVo vo,
			HttpServletRequest request,HttpServletResponse resp) throws Exception {
		try {
			DataGridVo<ContractsaveVo> grid = new DataGridVo<ContractsaveVo>();
			PageInfo<ContractsaveVo> dataPage = new PageInfo<ContractsaveVo>(request);
			PageInfo<ContractsaveVo> datas = this.contractsaveService.queryData(dataPage,vo);
			for(ContractsaveVo savevo:datas.getPageResults()){
				String viewinfo="<a href='javascript:void(0)' onclick='showview("+savevo.getSaveid()+")'>查看</a>";
				savevo.setViewsaveinfo(viewinfo);
				Long stime=savevo.getSavedate();
	    		 Date date = new Date(stime);
	    		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
	    		String savetime=sdf.format(date);
	    		savevo.setSavetime(savetime);
			}
			grid.add(datas.getPageResults());
			grid.setTotal(datas.getTotalCount());
			WebUtil.printText(JSON.toJSONString(grid), resp);
		} 
		catch (Exception e) {
			log.error("查询保存配资协议到第三方错误"+e.getMessage());
		}
	}
	
	
	@RequestMapping(value = "/viewInfo")
	public String viewInfo(String saveId,HttpServletResponse response,HttpServletRequest request){
		if(StringUtil.isNotBlank(saveId)){
			String url=contractsaveService.getUrlById(Long.valueOf(saveId));
			request.setAttribute("url", url);
		}
		return ViewConstants.ContractsaveViewJsp.VIEW_INFO;
	}
}
