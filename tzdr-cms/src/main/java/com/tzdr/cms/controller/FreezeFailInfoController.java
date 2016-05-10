package com.tzdr.cms.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.tzdr.business.service.failinfo.FreezeFailInfoService;
import com.tzdr.business.service.wuser.WUserService;
import com.tzdr.cms.constants.ViewConstants;
import com.tzdr.cms.support.BaseCmsController;
import com.tzdr.cms.utils.DataGridVo;
import com.tzdr.cms.utils.WebUtil;
import com.tzdr.cms.vo.WuserVo;
import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.utils.ConnditionVo;
import com.tzdr.domain.web.entity.FreezeFailInfo;
import com.tzdr.domain.web.entity.WUser;

/**
 * 
 * <p></p>
 * @author QingLiu
 * @see 冻结失败
 * @version 2.0
 * 2015年2月7日上午11:57:00
 */
@Controller
@RequestMapping("/admin/freezeFailInfo")
public class FreezeFailInfoController extends BaseCmsController<FreezeFailInfo> {
	
	private static Logger log = LoggerFactory
			.getLogger(FreezeFailInfoController.class);
	
	@Autowired
	private FreezeFailInfoService freezeFailInfoService;
	
	
	public FreezeFailInfoController() {
		setResourceIdentity("sys:riskmanager:freezingFailure");
	}
	@RequestMapping(value = "/list")
	public String list(HttpServletRequest request,HttpServletResponse resp) throws Exception {
		return ViewConstants.FreezeFailInfoViewJsp.LIST_VIEW;
	}
	
	@RequestMapping(value = "/listData")
	public void listData(HttpServletRequest request,HttpServletResponse resp) throws Exception {
		
		try {
			DataGridVo<FreezeFailInfo> grid = new DataGridVo<FreezeFailInfo>();
			PageInfo<FreezeFailInfo> page = new PageInfo<FreezeFailInfo>(request);
			ConnditionVo connVo = new ConnditionVo(request);
			PageInfo<FreezeFailInfo> datas = this.freezeFailInfoService.queryDataPage(page, connVo);
			for (FreezeFailInfo data:datas.getPageResults()) {
				grid.add(data);
			}
			grid.setTotal(datas.getTotalCount());
			WebUtil.printText(JSON.toJSONString(grid), resp);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	@Override
	public BaseService<FreezeFailInfo> getBaseService() {
		return freezeFailInfoService;
	}


}
