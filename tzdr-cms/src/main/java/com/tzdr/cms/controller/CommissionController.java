package com.tzdr.cms.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.tzdr.business.service.generalize.CommissionService;
import com.tzdr.cms.constants.ViewConstants;
import com.tzdr.cms.support.BaseCmsController;
import com.tzdr.cms.utils.CommissionTreeUtils;
import com.tzdr.cms.utils.DataGridVo;
import com.tzdr.cms.utils.WebUtil;
import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.utils.BigDecimalUtils;
import com.tzdr.common.utils.ConnditionVo;
import com.tzdr.common.utils.ExportExcel;
import com.tzdr.common.utils.TypeConvert;
import com.tzdr.domain.vo.EarningsVo;
import com.tzdr.domain.vo.UserCommissionVo;
import com.tzdr.domain.web.entity.UserCommission;

/**
 * 
* @Description: 用户返利列表
* @ClassName: CommissionController
* @author wangpinqun
* @date 2015年3月20日 下午2:58:06
 */
@Controller
@RequestMapping("/admin/commission")
public class CommissionController extends BaseCmsController<UserCommission>{

	private static Logger log = LoggerFactory.getLogger(CommissionController.class);
	
	@Autowired
	private CommissionService commissionService;
	
	
	/**
	* @Description: 用户返利列表
	* @Title: limitList
	* @param request
	* @return String    返回类型
	 */
	@RequestMapping(value="list")
	public String limitList(HttpServletRequest request){
		return ViewConstants.CommissionViewJsp.LIST_VIEW;
	}
	

	@Override
	public BaseService<UserCommission> getBaseService() {
		
		return commissionService;
	}
	
	/**
	* @Description: 获取返利数据
	* @Title: listData
	* @param request
	* @param resp
	* @throws Exception
	* @return void    返回类型
	 */
	@RequestMapping(value = "/listData")
	public void listData(HttpServletRequest request,HttpServletResponse resp) throws Exception {
		
		try {
			ConnditionVo connVo = new ConnditionVo(request);
			
			if(connVo.isExcel()){  //判断是否导出
				//获取返利数据
				List<UserCommissionVo> result = commissionService.queryUserCommissionVo(connVo);
				result = CommissionTreeUtils.buildCommissionTree(result);   //创建返利用户tree结果
				List<UserCommissionVo> data = this.conductUserCommissionVoList(result);  //处理返利数据
				//封装分页数据+小计+合计
				this.createTotalData(data,null,null,false);
				connVo.isNotExcel(data,resp,"返利报表.xls");
				return;
			}
			
			DataGridVo<UserCommissionVo> grid = new DataGridVo<UserCommissionVo>();
			
			PageInfo<UserCommissionVo> dataPage = new PageInfo<UserCommissionVo>(request);
			//获取分页数据
			dataPage = commissionService.queryPageDataListVo(dataPage, connVo);
			
			//获取返利数据
			List<UserCommissionVo> result = dataPage == null ? null : dataPage.getPageResults();
			result = CommissionTreeUtils.buildCommissionTree(result);   //创建返利用户tree结果
			List<UserCommissionVo> data = this.conductUserCommissionVoList(result);  //处理返利数据
			
			//获取合计数据
			UserCommissionVo totalUserCommissionVo = commissionService.getDataTotalVo(connVo);
			//封装分页数据+小计+合计
			this.createTotalData(data,totalUserCommissionVo,dataPage == null ? 0: dataPage.getTotalCount(),true);
			grid.add(data);
			grid.setTotal(dataPage == null ? 0: dataPage.getTotalCount());
			WebUtil.printText(JSON.toJSONString(grid), resp);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 求合计
	 * @param data List<UserCommissionVo>
	 */
	private void createTotalData(List<UserCommissionVo> data,UserCommissionVo totalUserCommissionVo,Long totalRows,boolean isPage) {
		
		Double userManageMoney = 0.00;           //小计用户管理费
		Double userMoney = 0.00;                 //小计用户返利
		Double subordinateManageMoney = 0.00;    //小计用户下级管理费
		Double subordinateMoney = 0.00;          //小计用户下级返利
		Double totalMoney = 0.00;                //小计返利合计
		if(data != null && !data.isEmpty()){
			for (UserCommissionVo userCommissionVo : data) {
				userCommissionVo.setParentNode(null);
				userCommissionVo.setChildrenNotes(null);
				userManageMoney = BigDecimalUtils.add(userManageMoney, userCommissionVo.getUserManageMoney());
				userMoney = BigDecimalUtils.addRound(userMoney, userCommissionVo.getUserMoney());
				subordinateManageMoney = BigDecimalUtils.addRound(subordinateManageMoney, userCommissionVo.getSubordinateManageMoney());
				subordinateMoney = BigDecimalUtils.addRound(subordinateMoney, userCommissionVo.getSubordinateMoney());
				totalMoney =  BigDecimalUtils.addRound(totalMoney, userCommissionVo.getTotalMoney());
				String userGrade = userCommissionVo.getUserGrade();
				userCommissionVo.setUserGrade(userGrade == null || "0".equals(userGrade) ? "股民1级" : "1".equals(userGrade) ? "股师": "股神");
			}
		}

		//构建合计行
		UserCommissionVo  subtotal = new UserCommissionVo();
		subtotal.setUserManageMoney(BigDecimalUtils.round(userManageMoney, 2));
		subtotal.setUserMoney(BigDecimalUtils.round(userMoney, 2));
		subtotal.setSubordinateManageMoney(BigDecimalUtils.round(subordinateManageMoney, 2));
		subtotal.setSubordinateMoney(BigDecimalUtils.round(subordinateMoney, 2));
		subtotal.setTotalMoney(BigDecimalUtils.round(totalMoney, 2));
		subtotal.setTrueName(isPage ? "小计：" : "合计：");
		data.add( subtotal);
		
		if(totalUserCommissionVo != null && isPage){
			totalUserCommissionVo.setUserManageMoney(BigDecimalUtils.round2(totalUserCommissionVo.getUserManageMoney(), 2));
			totalUserCommissionVo.setUserMoney(BigDecimalUtils.round2(totalUserCommissionVo.getUserMoney(), 2));
			totalUserCommissionVo.setSubordinateManageMoney(BigDecimalUtils.round2(totalUserCommissionVo.getSubordinateManageMoney(), 2));
			totalUserCommissionVo.setSubordinateMoney(BigDecimalUtils.round2(totalUserCommissionVo.getSubordinateMoney(), 2));
			totalUserCommissionVo.setTotalMoney(BigDecimalUtils.round2(totalUserCommissionVo.getTotalMoney(), 2));
			totalUserCommissionVo.setTrueName("合计：");
			data.add(totalUserCommissionVo);
		}
	}
	
	/**
	* @Description: 处理返利数据
	* @Title: conductUserCommissionVoList
	* @param result
	* @return List<UserCommissionVo>    返回类型
	 */
	private List<UserCommissionVo> conductUserCommissionVoList(List<UserCommissionVo> result){
		
		if(result == null || result.isEmpty()) return result;
		
		List<UserCommissionVo>  data = new ArrayList<UserCommissionVo>();
		
		for (UserCommissionVo userCommissionVo : result) {
			data.add(userCommissionVo);
			if(userCommissionVo.getChildrenNotes() != null && !userCommissionVo.getChildrenNotes().isEmpty()){
				data = this.getChildren(userCommissionVo, data);
			}
		}
		return data;
	}
	
	/**
	* @Description: 获取孩子节点数据
	* @Title: getChildren
	* @param userCommissionVo
	* @param data
	* @return List<UserCommissionVo>    返回类型
	 */
	private List <UserCommissionVo> getChildren(UserCommissionVo userCommissionVo,List<UserCommissionVo> data){
		
		List <UserCommissionVo> chilUserCommissionVoList = userCommissionVo.getChildrenNotes();
		
		for (UserCommissionVo chilUserCommissionVo : chilUserCommissionVoList) {
			data.add(chilUserCommissionVo);
			if(chilUserCommissionVo.getChildrenNotes() != null && !chilUserCommissionVo.getChildrenNotes().isEmpty()){
				this.getChildren(chilUserCommissionVo, data);
			}
		}
		return data;
	}
}
