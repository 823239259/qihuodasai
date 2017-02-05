package com.tzdr.cms.controller.FTogetherTrade;

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
import com.tzdr.business.service.togetherFuture.FTogetherRecordService;
import com.tzdr.cms.constants.ViewConstants;
import com.tzdr.cms.hkstock.controller.hkEarnings.HkEarningsReportController;
import com.tzdr.cms.support.BaseCmsController;
import com.tzdr.cms.utils.DataGridVo;
import com.tzdr.cms.utils.WebUtil;
import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.utils.ConnditionVo;
import com.tzdr.common.utils.TypeConvert;
import com.tzdr.domain.hkstock.vo.HkEarningsVo;
import com.tzdr.domain.vo.FTogetherRecordVo;
import com.tzdr.domain.web.entity.FTogetherRecord;

@Controller
@RequestMapping("/admin/togetherRecord")
public class FTogetherRecordController extends BaseCmsController<FTogetherRecord>{
	
	Logger logger=LoggerFactory.getLogger(FTogetherRecordController.class);
	@Autowired
	private FTogetherRecordService fTogetherRecordService;
	
	@Override
	public BaseService<FTogetherRecord> getBaseService() {
		// TODO Auto-generated method stub
		return this.fTogetherRecordService;
	}

	@Override
	public void setResourceIdentity(String resourceIdentity) {
		// TODO Auto-generated method stub
		super.setResourceIdentity("sys:accountant:togetherRecord");
	}
	
	@RequestMapping(value="/list")
	public String list(){
		return ViewConstants.togetherFuture.RECORD_LIST;
	}
	
	/**
	 * 获取数据
	 * 
	 * @param
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	
	@RequestMapping(value = "/getData")
	public void getData(HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		
		
		// 查看权限
		if (permissionList != null) {
			this.permissionList.assertHasViewPermission();
		}
		try {
			DataGridVo<FTogetherRecordVo> grid = new DataGridVo<FTogetherRecordVo>();
			ConnditionVo connVo = new ConnditionVo(req);
			if(connVo.isExcel()){  //判断是否导出
				List<FTogetherRecordVo> data = fTogetherRecordService.getRecordVos(connVo);
				data = data == null ? new ArrayList<FTogetherRecordVo>() : data;
				//封装分页数据
				FTogetherRecordVo vo =this.createTotalData(data);
				data.add(vo);
				connVo.isNotExcel(data,resp,"期货合买报表.xls");
				return;
			}
			PageInfo<FTogetherRecordVo> dataPage = new PageInfo<FTogetherRecordVo>(req);
			
			//获取分页数据
			dataPage = fTogetherRecordService.getDatas(dataPage, connVo);
			List<FTogetherRecordVo> data = dataPage.getPageResults() == null ? new ArrayList<FTogetherRecordVo>() : dataPage.getPageResults();
			List<FTogetherRecordVo> datas = fTogetherRecordService.getRecordVos(connVo);
			//合计
			FTogetherRecordVo vo1 =this.createTotalDatas(datas);
			//总计
			FTogetherRecordVo vo2 =this.createTotalData(data);
//			//封装分页数据+合计
			data.add(vo2);
			data.add(vo1);
			this.createTotalData(data);
			grid.add(data);
			grid.setTotal(dataPage.getTotalCount());
				WebUtil.printText(JSON.toJSONString(grid), resp);
				
		} catch (Exception e) {
			// TODO: handle exception
			logger.debug(e.getMessage());
		}
		
	}
	
	private FTogetherRecordVo createTotalData(List<FTogetherRecordVo> data){
		BigDecimal totalPayMoney = new BigDecimal("0");
		BigDecimal totalPoundage = new BigDecimal("0");
		BigDecimal totalAchieveProfitLoss = new BigDecimal("0");
		BigDecimal totalExpectSettlementMoney = new BigDecimal("0");
		BigDecimal totalActualSettlementMoney = new BigDecimal("0");

		if(data !=null&& !data.isEmpty()){
			for(FTogetherRecordVo vo : data){

				if(null != vo.getPayMoney() ){
					totalPayMoney = totalPayMoney.add(vo.getPayMoney());
				}
				if(null != vo.getPoundage()){
					totalPoundage = totalPoundage.add(vo.getPoundage());
				}
				if(null != vo.getActualSettlementMoney() ){
					totalActualSettlementMoney = totalActualSettlementMoney.add(vo.getActualSettlementMoney());
				}
				if(null != vo.getExpectSettlementMoney()){
					totalExpectSettlementMoney = totalExpectSettlementMoney.add(vo.getExpectSettlementMoney());
				}
				if(null != vo.getAchieveProfitLoss()){
					totalAchieveProfitLoss = totalAchieveProfitLoss.add(vo.getAchieveProfitLoss());
				}
			}
			FTogetherRecordVo totalVo = new FTogetherRecordVo();
			totalVo.setAchieveProfitLoss(totalAchieveProfitLoss);
			totalVo.setActualSettlementMoney(totalActualSettlementMoney);
			totalVo.setExpectSettlementMoney(totalExpectSettlementMoney);
			totalVo.setPayMoney(totalPayMoney);
			totalVo.setPoundage(totalPoundage);
			totalVo.setUserName("合计：");
			return totalVo;
		}else{
			return new FTogetherRecordVo();
		}
		
		
	}
	
	private FTogetherRecordVo createTotalDatas(List<FTogetherRecordVo> datas){
		BigDecimal totalPayMoney = new BigDecimal("0");
		BigDecimal totalPoundage = new BigDecimal("0");
		BigDecimal totalAchieveProfitLoss = new BigDecimal("0");
		BigDecimal totalExpectSettlementMoney = new BigDecimal("0");
		BigDecimal totalActualSettlementMoney = new BigDecimal("0");

		if(datas !=null&& !datas.isEmpty()){
			for(FTogetherRecordVo vo : datas){

				if(null != vo.getPayMoney() ){
					totalPayMoney = totalPayMoney.add(vo.getPayMoney());
				}
				if(null != vo.getPoundage()){
					totalPoundage = totalPoundage.add(vo.getPoundage());
				}
				if(null != vo.getActualSettlementMoney() ){
					totalActualSettlementMoney = totalActualSettlementMoney.add(vo.getActualSettlementMoney());
				}
				if(null != vo.getExpectSettlementMoney()){
					totalExpectSettlementMoney = totalExpectSettlementMoney.add(vo.getExpectSettlementMoney());
				}
				if(null != vo.getAchieveProfitLoss()){
					totalAchieveProfitLoss = totalAchieveProfitLoss.add(vo.getAchieveProfitLoss());
				}
			}
			FTogetherRecordVo totalVo = new FTogetherRecordVo();
			totalVo.setAchieveProfitLoss(totalAchieveProfitLoss);
			totalVo.setActualSettlementMoney(totalActualSettlementMoney);
			totalVo.setExpectSettlementMoney(totalExpectSettlementMoney);
			totalVo.setPayMoney(totalPayMoney);
			totalVo.setPoundage(totalPoundage);
			totalVo.setUserName("总计：");
			return totalVo;
		}else{
			return new FTogetherRecordVo();
		}
		
	}
}
