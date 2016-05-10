package com.tzdr.cms.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jodd.util.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSON;
import com.tzdr.business.cms.service.user.UserService;
import com.tzdr.business.service.datamap.DataMapService;
import com.tzdr.business.service.drawMoneyData.DrawMoneyDataService;
import com.tzdr.cms.constants.Constants;
import com.tzdr.cms.constants.ViewConstants;
import com.tzdr.cms.support.BaseCmsController;
import com.tzdr.cms.utils.DataGridVo;
import com.tzdr.cms.utils.WebUtil;
import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.domain.cms.entity.user.User;
import com.tzdr.domain.entity.DataMap;
import com.tzdr.domain.vo.DrawMoneyDataVo;
import com.tzdr.domain.web.entity.DrawMoneyData;

/**
 * 设置取款数据
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/admin/drawMoneyData")
public class DrawMoneyDataController extends BaseCmsController<DrawMoneyData>  {
	@Autowired
	private DrawMoneyDataService drawMoneyDataService;
	
	@Autowired
	private DataMapService dataMapService;
	@Autowired
	private UserService userService;
	public DrawMoneyDataController() {
		setResourceIdentity("sys:finance:drawMoneyData");
	}
	
	@RequestMapping(value="/list")
	public String list(HttpServletRequest  request){
		return ViewConstants.DrawMoneyDataViewJsp.LIST_VIEW;
	}
	
	@RequestMapping(value = "/listData")
	public void listData(@ModelAttribute DrawMoneyDataVo vo,
			HttpServletRequest request,HttpServletResponse resp) throws Exception {
		try {
			DataGridVo<DrawMoneyDataVo> grid = new DataGridVo<DrawMoneyDataVo>();
			PageInfo<DrawMoneyDataVo> dataPage = new PageInfo<DrawMoneyDataVo>(request);
			
			PageInfo<DrawMoneyDataVo> datas = this.drawMoneyDataService.queryData(dataPage,vo);
			grid.add(datas.getPageResults());
			grid.setTotal(datas.getTotalCount());
			WebUtil.printText(JSON.toJSONString(grid), resp);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/edit")
	public String edit(HttpServletRequest request,@RequestParam("fromType") String fromType,
			@RequestParam(value="id",required=false) String id) throws Exception {
		List<User> list=userService.findByUsersDeleteFalse();
		List<DataMap> datamap=dataMapService.findByTypeKey("audittype");
		request.setAttribute("datamap",datamap);
		request.setAttribute("list",list);
		
		request.setAttribute("fromType",fromType);
		if (StringUtil.equals(fromType,Constants.ADD)){
			return ViewConstants.DrawMoneyDataViewJsp.EDIT;			
		}
		
		if (StringUtil.equals(fromType,Constants.EDIT)){
			DrawMoneyData  entity  = drawMoneyDataService.get(id);
			request.setAttribute("entity",entity);
			return ViewConstants.DrawMoneyDataViewJsp.EDIT;	
		}
		return ViewConstants.ERROR_VIEW;
	}

	
	
	@Override
	public BaseService<DrawMoneyData> getBaseService() {
		return drawMoneyDataService;
	}

}
