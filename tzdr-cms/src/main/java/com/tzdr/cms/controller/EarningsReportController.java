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
import com.tzdr.business.service.reports.EarningsReportService;
import com.tzdr.cms.constants.ViewConstants;
import com.tzdr.cms.support.BaseCmsController;
import com.tzdr.cms.utils.DataGridVo;
import com.tzdr.cms.utils.WebUtil;
import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.utils.ConnditionVo;
import com.tzdr.common.utils.TypeConvert;
import com.tzdr.common.web.support.EasyUiPageInfo;
import com.tzdr.domain.vo.EarningsVo;
import com.tzdr.domain.web.entity.WUser;


/**
 * 
 * <p>用户近制器</p>
 * @author LiuQing
 * @see
 * @version 2.0
 * 2015年1月8日下午4:26:47
 */
@Controller
@RequestMapping("/admin/reports/earnings")
public class EarningsReportController  extends BaseCmsController<WUser> {
	
	private static Logger log = LoggerFactory.getLogger(EarningsReportController.class);
	
	@Autowired
	private  EarningsReportService earningsReportService;
	
	
	@RequestMapping(value = "/listReport")
	public String listReport(HttpServletRequest request,HttpServletResponse resp) throws Exception {
		return ViewConstants.EarningsReportViewJsp.LIST_VIEW;
	}
	
	public EarningsReportController() {
		setResourceIdentity("sys:accountant:earnings");
	}
	
	
	@RequestMapping(value = "/listData")
	public void listData(EasyUiPageInfo easyUiPage,HttpServletRequest request,HttpServletResponse resp) throws Exception {
		try {
			
			DataGridVo<EarningsVo> grid = new DataGridVo<EarningsVo>();
			ConnditionVo connVo = new ConnditionVo(request);
			
			if(connVo.isExcel()){  //判断是否导出
				List<EarningsVo> data = earningsReportService.queryDataListVo(connVo);
				data = data == null ? new ArrayList<EarningsVo>() : data; 
				//封装分页数据+小计+合计
				this.createTotalData(data,null,null,false);
				connVo.isNotExcel(data,resp,"收益日报-管理费-利息收益.xls");
				return;
			}
			
			PageInfo<EarningsVo> dataPage = new PageInfo<EarningsVo>(request);
			//获取分页数据
			dataPage = earningsReportService.queryPageDataListVo(dataPage, connVo);
			List<EarningsVo> data = dataPage.getPageResults() == null ? new ArrayList<EarningsVo>() : dataPage.getPageResults();
			//获取合计数据
			EarningsVo totalEarningsVo = earningsReportService.getDataTotalVo(connVo);
			//封装分页数据+小计+合计
			this.createTotalData(data,totalEarningsVo,dataPage.getTotalCount(),true);
			grid.add(data);
			grid.setTotal(dataPage.getTotalCount());
			WebUtil.printText(JSON.toJSONString(grid), resp);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 求合计
	 * @param data List<EarningsVo>
	 */
	private void createTotalData(List<EarningsVo> data,EarningsVo totalEarningsVo,Long totalRows,boolean isPage) {
		
		BigDecimal leverMoney = new BigDecimal("0");
		BigDecimal managerMoney = new BigDecimal("0");
		BigDecimal interestMoney = new BigDecimal("0");
		BigDecimal backMoney = new BigDecimal(0);
		BigDecimal totalfreeMoney = new BigDecimal(0);
		BigDecimal totaltransMoney = new BigDecimal(0);
		
		// 配资利息撤回、配资管理费撤回
		BigDecimal revokeManagerMoney = new BigDecimal(0);
		BigDecimal revokeInterest = new BigDecimal(0);
		
		// 盈利分成
		BigDecimal profitMoney = new BigDecimal(0);
		BigDecimal totalMoney = new BigDecimal(0);
		//BigDecimal opDay = new BigDecimal(0);
		int opDay = 0;
		Integer lever = 0;
		
		int frequency = data != null && !data.isEmpty() ? data.size() : 0;
		if(data != null && !data.isEmpty()){
			for(EarningsVo vo:data) {
				//倍数
				lever += vo.getLever();
				//保证金
				if (vo.getLeverMoney() != null) {
					leverMoney = 
							TypeConvert.add(leverMoney.doubleValue(), vo.getLeverMoney());
				}
				//天数
				if (vo.getOpDay() != null) {
					//opDay = TypeConvert.add(opDay, vo.getOpDay());
					opDay += vo.getOpDay();
				}
				//管理费
				if (vo.getManagerMoney() != null) {
					managerMoney = 
							TypeConvert.add(managerMoney.doubleValue(), vo.getManagerMoney().doubleValue());
				}
				//代收利息
				if (vo.getInterestMoney() != null) {
					interestMoney = 
							TypeConvert.add(interestMoney.doubleValue(), vo.getInterestMoney().doubleValue());
				}
				//返利
				if (vo.getBackMoney() != null) {
					backMoney = 
							TypeConvert.add(backMoney.doubleValue(), vo.getBackMoney().doubleValue());
				}
				if (vo.getFreemoney() != null) {
					totalfreeMoney = 
							TypeConvert.add(totalfreeMoney.doubleValue(), vo.getFreemoney().doubleValue());
				}
				if (vo.getProfitMoney() > 0) {
					profitMoney = TypeConvert.add(profitMoney.doubleValue(), vo.getProfitMoney());
				}
				if (vo.getTotalmoney() != null) {
					totalMoney = 
							TypeConvert.add(totalMoney.doubleValue(), vo.getTotalmoney().doubleValue());
				}
				// 配资利息撤回、配资管理费撤回
				if (vo.getRevokeManagerMoney() !=null){
					revokeManagerMoney = TypeConvert.add(revokeManagerMoney.doubleValue(), vo.getRevokeManagerMoney().doubleValue());
				}
				if (vo.getRevokeManagerMoney() !=null){
					revokeInterest = TypeConvert.add(revokeInterest.doubleValue(), vo.getRevokeInterest().doubleValue());
				}
			}
		}
		
		
		EarningsVo total = new EarningsVo();
		total.setLever(frequency != 0 ? TypeConvert.scale(new BigDecimal(lever / frequency),0).intValue() : 0);
		//total.setOpDay(TypeConvert.scale(opDay.divide(new BigDecimal(frequency),BigDecimal.ROUND_FLOOR),0));
		total.setOpDay(opDay);
		total.setLeverMoney(TypeConvert.scale(leverMoney,2).doubleValue());
		total.setManagerMoney(TypeConvert.scale(managerMoney, 2));
		total.setInterestMoney(TypeConvert.scale(interestMoney, 2));
		total.setFreemoney(TypeConvert.scale(totalfreeMoney, 2));
		total.setTransmoney(TypeConvert.scale(totaltransMoney, 2));
		total.setProfitMoney(TypeConvert.scale(profitMoney, 2).doubleValue());
		total.setTotalmoney(TypeConvert.scale(totalMoney, 2));
		// 配资利息撤回、配资管理费撤回
		total.setRevokeInterest(TypeConvert.scale(revokeInterest, 2));
		total.setRevokeManagerMoney(TypeConvert.scale(revokeManagerMoney, 2));
		
		total.setHsBelongBroker(isPage ? "小计：" : "合计：");
		total.setActivityType(-1);
		
		data.add(total);
		
		if(totalEarningsVo != null && isPage){
			int totalFrequency = totalRows.intValue();
			totalEarningsVo.setLever(totalFrequency != 0 ? TypeConvert.scale(new BigDecimal(totalEarningsVo.getLever() / totalFrequency),0).intValue() : 0);
			totalEarningsVo.setOpDay(totalEarningsVo.getOpDay() == null ? 0 : totalEarningsVo.getOpDay());
			totalEarningsVo.setLeverMoney(TypeConvert.doubleToBigDecimalByScale(totalEarningsVo.getLeverMoney(),2).doubleValue());
			totalEarningsVo.setManagerMoney(TypeConvert.scale(totalEarningsVo.getManagerMoney() == null ? new BigDecimal(0) : totalEarningsVo.getManagerMoney(), 2));
			totalEarningsVo.setInterestMoney(TypeConvert.scale(totalEarningsVo.getInterestMoney() == null ? new BigDecimal(0) : totalEarningsVo.getInterestMoney(), 2));
			totalEarningsVo.setFreemoney(totalEarningsVo.getFreemoney() == null ? new BigDecimal(0) : TypeConvert.scale(totalEarningsVo.getFreemoney(), 2));
			totalEarningsVo.setTransmoney(TypeConvert.scale(totaltransMoney, 2));
			totalEarningsVo.setProfitMoney(TypeConvert.doubleToBigDecimalByScale(totalEarningsVo.getProfitMoney(), 2).doubleValue());
			totalEarningsVo.setTotalmoney(TypeConvert.scale(totalEarningsVo.getTotalmoney() == null ? new BigDecimal(0) : totalEarningsVo.getTotalmoney(), 2));
			// 配资利息撤回、配资管理费撤回
			totalEarningsVo.setRevokeInterest(TypeConvert.scale(totalEarningsVo.getRevokeInterest()==null?new BigDecimal(0):totalEarningsVo.getRevokeInterest(), 2));
			totalEarningsVo.setRevokeManagerMoney(TypeConvert.scale(totalEarningsVo.getRevokeManagerMoney()==null?new BigDecimal(0):totalEarningsVo.getRevokeManagerMoney(), 2));
			
			totalEarningsVo.setHsBelongBroker("合计：");
			totalEarningsVo.setActivityType(-1);
			data.add(totalEarningsVo);
		}
	}
	

	@Override
	public BaseService<WUser> getBaseService() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
