package com.tzdr.cms.controller.monthUserTradeParams;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;




import com.tzdr.business.service.MonthUserTradeService.MonthUserTradeService;
import com.tzdr.business.service.monthUserTradeParams.MonthUserTradeParamsService;
import com.tzdr.cms.constants.ViewConstants;
import com.tzdr.cms.support.BaseCmsController;
import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.domain.web.entity.MonthUserTradeParams;


@Controller
@RequestMapping("/admin/monthTrade")
public class MonthUserTradeParamsController extends BaseCmsController<MonthUserTradeParams> {
	
	@Autowired
	private MonthUserTradeParamsService monthUserTradeParamsService;
	
	@Autowired
	private MonthUserTradeService monthUserTradeService;
	
	@Override
	public BaseService<MonthUserTradeParams> getBaseService() {
		// TODO Auto-generated method stub
		return this.monthUserTradeParamsService;
	}

	@Override
	public void setResourceIdentity(String resourceIdentity) {
		// TODO Auto-generated method stub
		super.setResourceIdentity("sys:settingParams:monthTrade");
	}
	
	@RequestMapping(value="/list")
	public String list(Model model){
		List<MonthUserTradeParams> list = this.monthUserTradeParamsService.getAll();
		if(list.size() >0 &&! list.isEmpty()){
			model.addAttribute("params",list.get(0));
		}
		return ViewConstants.MonthTrade.LIST_VIEW;
	}
	
	@ResponseBody
	@RequestMapping(value="/updateParams",method=RequestMethod.POST)
	public JsonResult updateParams(Double minTotalMoney,Double maxTotalMoney,String recommendHoldMoney,
								String leverConfig,Double interestCoefficient,Double manageFeeCoefficient,
								String recommendHoldMonths){
		JsonResult jsonResult = new JsonResult(false);
		List<MonthUserTradeParams> list=this.monthUserTradeParamsService.getAll();
		if(list.size()>0 && !list.isEmpty()){
			MonthUserTradeParams monthUserTradeParams = list.get(0);
			monthUserTradeParams.setRecommendHoldMonths(recommendHoldMonths);
			monthUserTradeParams.setLeverConfig(leverConfig);
			monthUserTradeParams.setManageFeeCoefficient(manageFeeCoefficient);
			monthUserTradeParams.setMaxTotalMoney(maxTotalMoney);
			monthUserTradeParams.setRecommendHoldMoney(recommendHoldMoney);
			monthUserTradeParams.setMinTotalMoney(minTotalMoney);
			monthUserTradeParams.setInterestCoefficient(interestCoefficient);
			this.monthUserTradeParamsService.update(monthUserTradeParams);
			jsonResult.setSuccess(true);
			jsonResult.setMessage("更新成功");
			return jsonResult;
		}else {
			MonthUserTradeParams params = new MonthUserTradeParams();
			params.setRecommendHoldMonths(recommendHoldMonths);
			params.setLeverConfig(leverConfig);
			params.setManageFeeCoefficient(manageFeeCoefficient);
			params.setMaxTotalMoney(maxTotalMoney);
			params.setRecommendHoldMoney(recommendHoldMoney);
			params.setMinTotalMoney(minTotalMoney);
			params.setInterestCoefficient(interestCoefficient);
			this.monthUserTradeParamsService.save(params);
			jsonResult.setSuccess(true);
			jsonResult.setMessage("更新成功");
			return jsonResult;
		}
	}
}
