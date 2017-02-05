package com.tzdr.cms.controller.bbpay;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tzdr.business.service.datamap.DataMapService;
import com.tzdr.business.service.pay.BankChannelService;
import com.tzdr.cms.constants.ViewConstants;
import com.tzdr.cms.support.BaseCmsController;
import com.tzdr.common.baseservice.BaseService;
import com.tzdr.domain.cache.DataDicKeyConstants;
import com.tzdr.domain.entity.DataMap;
import com.tzdr.domain.web.entity.PaymentSupportBank;

@Controller
@RequestMapping("/admin/bankChannel")
public class BankChannelController  extends BaseCmsController<PaymentSupportBank>{

@Autowired
private BankChannelService bankChannelService;


@Autowired
private DataMapService dataMapService;


	@RequestMapping("/list")
	public String index(HttpServletRequest request){
		int withdrawSetting = dataMapService.getWithDrawSetting();
		request.setAttribute("withdrawSetting",withdrawSetting);
		int pgbWithdrawSetting = dataMapService.getPgbWithDrawSetting();
		request.setAttribute("pgbWithdrawSetting",pgbWithdrawSetting);

		return ViewConstants.bankChannel.LIST_VIEW;
	}
	
	@RequestMapping("/querybank")
	@ResponseBody
	public List<PaymentSupportBank> querybank(){
		List<PaymentSupportBank> pks=bankChannelService.queryBank();
		return pks;
	}
	
	@Override
	public void setResourceIdentity(String resourceIdentity) {
		super.setResourceIdentity("sys:finance:bankChannel");
	}
	
	@RequestMapping("/bankupdate")
	public String bankupdate(HttpServletRequest request){
		String  withdrawSetting = request.getParameter("withdrawSetting");
		List<DataMap>   dataMaps = dataMapService.findByTypeKey(DataDicKeyConstants.WITHDRAW_SETTING);
		if (!CollectionUtils.isEmpty(dataMaps)){
			DataMap  withdrawSettingMap = dataMaps.get(0);
			withdrawSettingMap.setValueKey(withdrawSetting);
			dataMapService.updateInfo(withdrawSettingMap);
		}
		for (int i=0;i<15;i++){
			String id=request.getParameter("id"+i);
			PaymentSupportBank bc=bankChannelService.get(id);
			if(request.getParameter("supportUmpay"+i)!=null){
			bc.setSupportUmpay(Integer.valueOf(request.getParameter("supportUmpay"+i)));
			}else if(request.getParameter("supportUmpay"+i)==null){
				bc.setSupportUmpay(0);
			}
			if(request.getParameter("supportBbpay"+i)!=null){
			bc.setSupportBbpay(Integer.valueOf(request.getParameter("supportBbpay"+i)));
			}else if(request.getParameter("supportBbpay"+i)==null){
				bc.setSupportBbpay(0);
			}
			if(request.getParameter("supportPayEase"+i)!=null){
				bc.setSupportPayEase(Integer.valueOf(request.getParameter("supportPayEase"+i)));
			}else if(request.getParameter("supportPayEase"+i)==null){
					bc.setSupportPayEase(0);
			}
			bankChannelService.save(bc);
		}
		
		request.setAttribute("withdrawSetting",withdrawSetting);
		return ViewConstants.bankChannel.LIST_VIEW;
	}

	@Override
	public BaseService<PaymentSupportBank> getBaseService() {
		// TODO Auto-generated method stub
		return bankChannelService;
	}
}
