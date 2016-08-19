package com.tzdr.business.cms.service.messagePrompt;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tzdr.business.service.datamap.DataMapService;

import com.tzdr.common.utils.EmailUtils;
import com.tzdr.domain.entity.DataMap;

@Service
public class MessagePromptService {

	@Autowired
	private DataMapService dataMapService;

	public void sendMessage(String submitType, String userName) {

		String fristParam = "";
		String twoParam = "";
		String url = "";
		String typeKey = "";
		if (submitType.equalsIgnoreCase(PromptTypes.isFutures)) {
			fristParam = "申请列表";
			twoParam = "期货方案";
			url = "http://test.manage.vs.com/admin/internation/future/list";
			typeKey = "riskEmail";
		} else if (submitType.equalsIgnoreCase(PromptTypes.isAddBond)) {
			fristParam = "申请列表";
			twoParam = "补充保证金";
			url = "http://test.manage.vs.com/admin/internation/future/list";
			typeKey = "riskEmail";
		} else if (submitType.equalsIgnoreCase(PromptTypes.isEndScheme)) {
			fristParam = "申请列表";
			twoParam = "申请结算";
			url = "http://test.manage.vs.com/admin/internation/future/list";
			typeKey = "riskEmail";
		} else if (submitType.equalsIgnoreCase(PromptTypes.isAlipayRecharge)) {
			fristParam = "支付宝充值审核";
			twoParam = "支付宝充值";
			url = "http://test.manage.vs.com/admin/rechargeReview/list";
			typeKey = "fundEmail";
		} else if (submitType.equalsIgnoreCase(PromptTypes.isBankReCharge)) {
			fristParam = "银行转账充值审核";
			twoParam = "银行转账充值";
			url = "http://test.manage.vs.com/admin/rechargeReview/list";
			typeKey = "fundEmail";
		} else if (submitType.equalsIgnoreCase(PromptTypes.isLineTransfer)) {
			fristParam = "线下转账待审核";
			twoParam = "线下转账提现";
			url = "http://test.manage.vs.com/admin/withdrawAudit/list";
			typeKey = "fundEmail";
		} else if (submitType.equalsIgnoreCase(PromptTypes.isTheTrial)) {
			fristParam = "提现待审核【初审】";
			twoParam = "提现";
			url = "http://test.manage.vs.com/admin/withdrawAudit/list";
			typeKey = "fundEmail";
		} else if (submitType.equalsIgnoreCase(PromptTypes.isReview)) {
			fristParam = "提现待审核【复审】";
			twoParam = "提现";
			url = "http://test.manage.vs.com/admin/withdrawAudit/list";
			typeKey = "reviewEmail";
		}
		if (typeKey != null && typeKey.length() > 0) {
			List<DataMap> dataMapList = dataMapService.findByTypeKey(typeKey);
			for (DataMap dataMap2 : dataMapList) {
				List<String> list = new ArrayList<>();
				list.add(fristParam);
				list.add(dataMap2.getTypeName());
				list.add(userName);
				list.add(twoParam);
				list.add(url);
				EmailUtils emailUtils = EmailUtils.getInstance();
				try {
					emailUtils.sendBatchMailTemp("审核", null, "messagePrompt", ".ftl", list, null,
							dataMap2.getValueName());
				} catch (Exception e) {
					e.printStackTrace();

				}
			}

		}
	}

}
