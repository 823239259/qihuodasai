package com.tzdr.cms.hkstock.controller.hkEarnings;

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
import com.tzdr.business.hkstock.service.HkUserTradeService;
import com.tzdr.cms.support.BaseCmsController;
import com.tzdr.cms.utils.DataGridVo;
import com.tzdr.cms.utils.WebUtil;
import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.utils.ConnditionVo;
import com.tzdr.common.utils.TypeConvert;
import com.tzdr.domain.hkstock.entity.HkUserTrade;
import com.tzdr.domain.hkstock.vo.HkEarningsVo;
import com.tzdr.domain.vo.EarningsVo;

/**
 * 港股收益日报表
 * @Description: 
 * @author liuhaichuan
 * @date 2015年10月27日
 *
 */
@Controller
@RequestMapping("/admin/hkstock/hkEarnings")
public class HkEarningsReportController extends BaseCmsController<HkUserTrade> {
	
	Logger logger=LoggerFactory.getLogger(HkEarningsReportController.class);
	
	@Autowired
	private HkUserTradeService hkUserTradeService;

	@Override
	public BaseService<HkUserTrade> getBaseService() {
		// TODO Auto-generated method stub
		return hkUserTradeService;
	}
	
	@Override
	public void setResourceIdentity(String resourceIdentity) {
		// TODO Auto-generated method stub
		super.setResourceIdentity("sys:finance:earnings");
	}
	

	/**
	 * 获取数据
	 * 
	 * @param easyUiPage
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
			DataGridVo<HkEarningsVo> grid = new DataGridVo<HkEarningsVo>();
			ConnditionVo connVo = new ConnditionVo(req);
			if(connVo.isExcel()){  //判断是否导出
				List<HkEarningsVo> data = hkUserTradeService.getEarningsData(connVo);
				data = data == null ? new ArrayList<HkEarningsVo>() : data;
				//封装分页数据+小计+合计
				this.createTotalData(data,null,null,false);
				connVo.isNotExcel(data,resp,"港股收益日报表.xls");
				return;
			}
			PageInfo<HkEarningsVo> dataPage = new PageInfo<HkEarningsVo>(req);
			
			//获取分页数据
			dataPage = hkUserTradeService.queryPageDataListVo(dataPage, connVo);
			List<HkEarningsVo> data = dataPage.getPageResults() == null ? new ArrayList<HkEarningsVo>() : dataPage.getPageResults();
			//获取合计数据
			HkEarningsVo totalHkEarningsVo = hkUserTradeService.getDataTotalVo(connVo);
			//封装分页数据+小计+合计
			this.createTotalData(data,totalHkEarningsVo,dataPage.getTotalCount(),true);
			grid.add(data);
			grid.setTotal(dataPage.getTotalCount());
				WebUtil.printText(JSON.toJSONString(grid), resp);
		} catch (Exception e) {
			// TODO: handle exception
			logger.debug(e.getMessage());
		}
		
	}
	
	/**
	 * 求合计
	 * @param data List<EarningsVo>
	 */
	private void createTotalData(List<HkEarningsVo> data,HkEarningsVo totalHkEarningsVo,Long totalRows,boolean isPage) {
		BigDecimal leverMoney = new BigDecimal("0");
		BigDecimal managerMoney = new BigDecimal("0");
		BigDecimal interestMoney = new BigDecimal("0");
		BigDecimal totalfreeMoney = new BigDecimal(0);
		BigDecimal totaltransMoney = new BigDecimal(0);
		BigDecimal totalMoney = new BigDecimal(0);
		//BigDecimal opDay = new BigDecimal(0);
		int opDay = 0;
		Integer lever = 0;
		
		int frequency = data != null && !data.isEmpty() ? data.size() : 0;
		
		if(data != null && !data.isEmpty()){
			for(HkEarningsVo vo:data) {
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
				if (vo.getFreemoney() != null) {
					totalfreeMoney = 
							TypeConvert.add(totalfreeMoney.doubleValue(), vo.getFreemoney().doubleValue());
				}
				if (vo.getTransmoney() != null) {
					totaltransMoney = 
							TypeConvert.add(totaltransMoney.doubleValue(), vo.getTransmoney().doubleValue());
				}
				if (vo.getTotalmoney() != null) {
					totalMoney = 
							TypeConvert.add(totalMoney.doubleValue(), vo.getTotalmoney().doubleValue());
				}
			}
		}
		
		HkEarningsVo total = new HkEarningsVo();
		total.setLever(frequency != 0 ? TypeConvert.scale(new BigDecimal(lever / frequency),0).intValue() : 0);
		//total.setOpDay(TypeConvert.scale(opDay.divide(new BigDecimal(frequency),BigDecimal.ROUND_FLOOR),0));
		total.setOpDay(opDay);
		total.setLeverMoney(TypeConvert.scale(leverMoney,2).doubleValue());
		total.setManagerMoney(TypeConvert.scale(managerMoney, 2));
		total.setInterestMoney(TypeConvert.scale(interestMoney, 2));
		total.setFreemoney(TypeConvert.scale(totalfreeMoney, 2));
		total.setTransmoney(TypeConvert.scale(totaltransMoney, 2));
		total.setTotalmoney(TypeConvert.scale(totalMoney, 2));
		total.setHsBelongBroker(isPage ? "小计" : "合计");
			
		data.add(total);
		
		if(totalHkEarningsVo != null && isPage){
			int totalFrequency = totalRows.intValue();
			totalHkEarningsVo.setLever(totalFrequency != 0 ? TypeConvert.scale(new BigDecimal(totalHkEarningsVo.getLever() / totalFrequency),0).intValue() : 0);
			totalHkEarningsVo.setOpDay(totalHkEarningsVo.getOpDay() == null ? 0 : totalHkEarningsVo.getOpDay());
			totalHkEarningsVo.setLeverMoney(TypeConvert.doubleToBigDecimalByScale(totalHkEarningsVo.getLeverMoney(),2).doubleValue());
			totalHkEarningsVo.setManagerMoney(TypeConvert.scale(totalHkEarningsVo.getManagerMoney() == null ? new BigDecimal(0) : totalHkEarningsVo.getManagerMoney(), 2));
			totalHkEarningsVo.setInterestMoney(TypeConvert.scale(totalHkEarningsVo.getInterestMoney() == null ? new BigDecimal(0) : totalHkEarningsVo.getInterestMoney(), 2));
			totalHkEarningsVo.setFreemoney(totalHkEarningsVo.getFreemoney() == null ? new BigDecimal(0) : TypeConvert.scale(totalHkEarningsVo.getFreemoney(), 2));
			totalHkEarningsVo.setTransmoney(TypeConvert.scale(totaltransMoney, 2));
			totalHkEarningsVo.setTotalmoney(TypeConvert.scale(totalHkEarningsVo.getTotalmoney() == null ? new BigDecimal(0) : totalHkEarningsVo.getTotalmoney(), 2));
			totalHkEarningsVo.setHsBelongBroker("合计");
			data.add(totalHkEarningsVo);
		}
	}
}
