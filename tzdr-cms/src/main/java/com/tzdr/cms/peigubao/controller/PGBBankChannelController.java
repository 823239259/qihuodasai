package com.tzdr.cms.peigubao.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tzdr.business.pgb.service.PGBBankChannelService;
import com.tzdr.business.service.datamap.DataMapService;
import com.tzdr.cms.constants.ViewConstants;
import com.tzdr.cms.support.BaseCmsController;
import com.tzdr.common.baseservice.BaseService;
import com.tzdr.domain.cache.DataDicKeyConstants;
import com.tzdr.domain.entity.DataMap;
import com.tzdr.domain.pgb.entity.PGBPaymentSupportBank;

@Controller
@RequestMapping("/admin/pgbbankChannel")
public class PGBBankChannelController  extends BaseCmsController<PGBPaymentSupportBank>{

@Autowired
private PGBBankChannelService pgbbankChannelService;


@Autowired
private DataMapService dataMapService;


	
	@RequestMapping(value = "/pgbquerybank", method = RequestMethod.POST)
	@ResponseBody
	public List<PGBPaymentSupportBank> querybank(){
		List<PGBPaymentSupportBank> pks=pgbbankChannelService.queryBank();
		return pks;
	}
	
	@Override
	public void setResourceIdentity(String resourceIdentity) {
		super.setResourceIdentity("sys:finance:bankChannel");
	}
	
	@RequestMapping("/pgbbankupdate")
	@ResponseBody
	public String bankupdate(HttpServletRequest request){
		String  pgbWithdrawSetting = request.getParameter("pgbWithdrawSetting");
		List<DataMap>   dataMaps = dataMapService.findByTypeKey(DataDicKeyConstants.PGB_WITHDRAW_SETTING);
		if (!CollectionUtils.isEmpty(dataMaps)){
			DataMap  withdrawSettingMap = dataMaps.get(0);
			withdrawSettingMap.setValueKey(pgbWithdrawSetting);
			dataMapService.updateInfo(withdrawSettingMap);
		}
		for (int i=0;i<15;i++){
			String id=request.getParameter("pgbid"+i);
			PGBPaymentSupportBank bc=pgbbankChannelService.get(id);
			if(request.getParameter("pgbsupportUmpay"+i)!=null){
			bc.setSupportUmpay(Integer.valueOf(request.getParameter("pgbsupportUmpay"+i)));
			}else if(request.getParameter("pgbsupportUmpay"+i)==null){
				bc.setSupportUmpay(0);
			}
			if(request.getParameter("pgbsupportBbpay"+i)!=null){
			bc.setSupportBbpay(Integer.valueOf(request.getParameter("pgbsupportBbpay"+i)));
			}else if(request.getParameter("pgbsupportBbpay"+i)==null){
				bc.setSupportBbpay(0);
			}
			pgbbankChannelService.save(bc);
		}
		
		return "success";
	}

	@Override
	public BaseService<PGBPaymentSupportBank> getBaseService() {
		// TODO Auto-generated method stub
		return pgbbankChannelService;
	}
}
