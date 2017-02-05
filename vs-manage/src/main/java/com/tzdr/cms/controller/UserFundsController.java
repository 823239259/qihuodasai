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
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hundsun.t2sdk.interfaces.T2SDKException;
import com.tzdr.business.service.stock.StockCurrentHistoryService;
import com.tzdr.cms.constants.ViewConstants;
import com.tzdr.cms.support.BaseCmsController;
import com.tzdr.cms.utils.DataGridVo;
import com.tzdr.cms.utils.WebUtil;
import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.utils.BigDecimalUtils;
import com.tzdr.common.utils.ConnditionVo;
import com.tzdr.domain.vo.EarningsVo;
import com.tzdr.domain.vo.UserCommissionVo;
import com.tzdr.domain.vo.UserFundsNewVo;
import com.tzdr.domain.web.entity.StockCurrentHistory;

@Controller
@RequestMapping("/admin/userFund")
public class UserFundsController extends BaseCmsController<StockCurrentHistory> {
	private static Logger log = LoggerFactory
			.getLogger(UserFundsController.class);

	@Autowired
	private StockCurrentHistoryService stockCurrentHistoryService;

	@Override
	public BaseService<StockCurrentHistory> getBaseService() {
		return stockCurrentHistoryService;
	}
	public UserFundsController() {
		setResourceIdentity("sys:accountant:userFund");
	}
	
	/**
	 * 显示列表列表
	 * @param request
	 * @throws T2SDKException 
	 */
	@RequestMapping(value="list")
	public String list(HttpServletRequest request){
		return ViewConstants.WithdrawalViewJsp.USER_FUNDS_REPORT_LIST_VIEW;
	}

	/**
	 * 获取数据
	 * @param request
	 * @throws T2SDKException 
	 */
	@RequestMapping(value="data")
	public void getData(HttpServletRequest request,HttpServletResponse response){
		try {
			ConnditionVo connVo = new ConnditionVo(request);
			if(connVo.getValueStr("first")==null){
				return;
			}

			if(connVo.isExcel()){  //判断是否导出
				List<UserFundsNewVo> data = stockCurrentHistoryService.getUserFunds(connVo);
				data = data == null ? new ArrayList<UserFundsNewVo>() : data;
				//封装分页数据+小计+合计
				this.createTotalData(data,null,null,false);
				connVo.isNotExcel(data,response,"达人资金表.xls");
				return;
			}
			
			DataGridVo<UserFundsNewVo> grid = new DataGridVo<UserFundsNewVo>();
			
			PageInfo<UserFundsNewVo> dataPage = new PageInfo<UserFundsNewVo>(request);
			//获取分页数据
			dataPage = stockCurrentHistoryService.queryPageDataListVo(dataPage, connVo);
			List<UserFundsNewVo> data = dataPage.getPageResults() == null ? new ArrayList<UserFundsNewVo>() : dataPage.getPageResults();
			//获取合计数据
			UserFundsNewVo UserFundsNewVo = stockCurrentHistoryService.getDataTotalVo(connVo);
			//封装分页数据+小计+合计
			this.createTotalData(data,UserFundsNewVo,dataPage.getTotalCount(),true);
			grid.add(data);
			grid.setTotal(dataPage.getTotalCount());
			WebUtil.printText(JSON.toJSONString(grid), response);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 求合计
	 * @param data List<UserCommissionVo>
	 */
	private void createTotalData(List<UserFundsNewVo> data,UserFundsNewVo totalUserFundsNewVo,Long totalRows,boolean isPage) {
		
		data.add(this.getSubtotal(data, isPage));
		
		if(isPage){
			UserFundsNewVo userFunds = getDefaultUserFundsVo(isPage);
			if(totalUserFundsNewVo != null && isPage){
				userFunds = totalUserFundsNewVo;
				userFunds.setRealName("合计：");
			}
			data.add(userFunds);
		}
	}
	
	
	/**
	 * 获取用户资金明细各项的小计，有分页：小计，无分页：合计
	 * @param vos	
	 * @return
	 */
	private UserFundsNewVo getSubtotal(List<UserFundsNewVo> vos,boolean isPage){
		UserFundsNewVo userFunds=getDefaultUserFundsVo(isPage);
		if(vos!=null && vos.size()>0){
			for(UserFundsNewVo vo:vos){
				//保证金（卡）
				userFunds.setCardCapitalMargin(userFunds.getCardCapitalMargin().add(vo.getCardCapitalMargin()));
				//保证金（余额）
				userFunds.setBalanceCapitalMargin(BigDecimalUtils.add(userFunds.getBalanceCapitalMargin(), vo.getBalanceCapitalMargin()));
				//配资金额
				userFunds.setAmountCapital(BigDecimalUtils.add(userFunds.getAmountCapital(), vo.getAmountCapital()));
				//前一日余额
				userFunds.setLastdayBalance(BigDecimalUtils.add(userFunds.getLastdayBalance(), vo.getLastdayBalance()));
				//收入（充值）
				userFunds.setIncomeRecharge(BigDecimalUtils.add(userFunds.getIncomeRecharge(), vo.getIncomeRecharge()));
				//收入（返利）
				userFunds.setIncomeRebate(BigDecimalUtils.add(userFunds.getIncomeRebate(), vo.getIncomeRebate()));
				//收入（其它）
				userFunds.setIncomeOther(BigDecimalUtils.add(userFunds.getIncomeOther(), vo.getIncomeOther()));
				//盈利
				userFunds.setProfit(BigDecimalUtils.add(userFunds.getProfit(), vo.getProfit()));
				//管理费
				userFunds.setManagementFee(BigDecimalUtils.add(userFunds.getManagementFee(), vo.getManagementFee()));
				//收益分成
				userFunds.setProfitMoney(BigDecimalUtils.add(userFunds.getProfitMoney(), vo.getProfitMoney()));
				//利息
				userFunds.setInterestFee(BigDecimalUtils.add(userFunds.getInterestFee(), vo.getInterestFee()));
				//抵扣券
				userFunds.setDeductionFee(BigDecimalUtils.add(userFunds.getDeductionFee(), vo.getDeductionFee()));
				//实收利息
				userFunds.setActualFee(BigDecimalUtils.add(userFunds.getActualFee(), vo.getActualFee()));
				//提现
				userFunds.setDrawFee(BigDecimalUtils.add(userFunds.getDrawFee(), vo.getDrawFee()));
				//提现(冻结中)
				userFunds.setDrawingFee(BigDecimalUtils.add(userFunds.getDrawingFee(), vo.getDrawingFee()));
				//补仓欠费
				userFunds.setCoverMoney(BigDecimalUtils.add(userFunds.getCoverMoney(), vo.getCoverMoney()));
				//账户资产
				userFunds.setAllMoney(BigDecimalUtils.add(userFunds.getAllMoney(), vo.getAllMoney()));
				//平台余额
				userFunds.setPlatBalance(BigDecimalUtils.add(userFunds.getPlatBalance(), vo.getPlatBalance()));
			}
		}
		return userFunds;
	}
	
	/**
	 * 获取默认的用户资金明细
	 * @return
	 */
	private UserFundsNewVo getDefaultUserFundsVo(boolean isPage){
		UserFundsNewVo userFunds=new UserFundsNewVo();
		userFunds.setRealName(isPage ? "小计：" : "合计：");
		userFunds.setCardCapitalMargin(new BigDecimal(0));
		userFunds.setBalanceCapitalMargin(0);
		userFunds.setAmountCapital(0);
		userFunds.setLastdayBalance(0);
		userFunds.setIncomeRecharge(0);
		userFunds.setIncomeRebate(0);
		userFunds.setIncomeOther(0);
		userFunds.setProfit(0);
		userFunds.setManagementFee(0);
		userFunds.setProfitMoney(0);
		userFunds.setInterestFee(0);
		userFunds.setDrawFee(0);
		userFunds.setDrawingFee(0);
		userFunds.setCoverMoney(0);
		userFunds.setAllMoney(0);
		userFunds.setPlatBalance(0);
		return userFunds;
	}
}
