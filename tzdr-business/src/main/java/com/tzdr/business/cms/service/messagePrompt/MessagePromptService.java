package com.tzdr.business.cms.service.messagePrompt;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tzdr.business.service.datamap.DataMapService;

import com.tzdr.common.utils.EmailUtils;
import com.tzdr.domain.entity.DataMap;


@Service
public class MessagePromptService {
	public static final Logger logger = LoggerFactory.getLogger(MessagePromptService.class);

	@Autowired
	private DataMapService dataMapService;

	public void sendMessage(final String submitType,final String userName) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				String fristParam = "";
				String twoParam = "";
				String url = "";
				String typeKey = "";
				String emailTemplet = "";
				if (submitType.equalsIgnoreCase(PromptTypes.isFutures)) {
					fristParam = "申请列表";
					twoParam = "期货方案";
					url = "http://manage.vs.com/admin/internation/future/list";
					typeKey = "riskEmail";
					emailTemplet = "messagePrompt";
				} else if (submitType.equalsIgnoreCase(PromptTypes.isAddBond)) {
					fristParam = "补充保证金";
					twoParam = "补充保证金";
					url = "http://manage.vs.com/admin/internation/future/list";
					typeKey = "riskEmail";
					emailTemplet = "messagePrompt";
				} else if (submitType.equalsIgnoreCase(PromptTypes.isEndScheme)) {
					fristParam = "方案管理";
					twoParam = "申请结算方案的";
					url = "http://manage.vs.com/admin/internation/future/list";
					typeKey = "riskEmail";
					emailTemplet = "messagePrompt";
				} else if (submitType.equalsIgnoreCase(PromptTypes.isAlipayRecharge)) {
					fristParam = "支付宝充值审核";
					twoParam = "支付宝充值";
					url = "http://manage.vs.com/admin/rechargeReview/list";
					typeKey = "fundEmail";
					emailTemplet = "messagePrompt";
				} else if (submitType.equalsIgnoreCase(PromptTypes.isBankReCharge)) {
					fristParam = "银行转账充值审核";
					twoParam = "银行转账充值";
					url = "http://manage.vs.com/admin/rechargeReview/list";
					typeKey = "fundEmail";
				}else if(submitType.equalsIgnoreCase(PromptTypes.isInternetBanking)){
					fristParam = "网银充值";
					twoParam = "网银充值成功";
					url = "http://manage.vs.com/admin/recharge/rechargeQuery";
					typeKey = "fundEmail";
					emailTemplet = "messagePrompt";
				} else if (submitType.equalsIgnoreCase(PromptTypes.isLineTransfer)) {
					fristParam = "线下转账待审核";
					twoParam = "线下转账提现";
					url = "http://manage.vs.com/admin/withdrawAudit/list";
					typeKey = "fundEmail";
					emailTemplet = "messagePrompt";
				} else if (submitType.equalsIgnoreCase(PromptTypes.isTheTrial)) {
					fristParam = "提现待审核【初审】";
					twoParam = "提现";
					url = "http://manage.vs.com/admin/withdrawAudit/list";
					typeKey = "fundEmail";
					emailTemplet = "messagePrompt";
				} else if (submitType.equalsIgnoreCase(PromptTypes.isReview)) {
					fristParam = "提现待审核【复审】";
					twoParam = "提现";
					url = "http://manage.vs.com/admin/withdrawAudit/list";
					typeKey = "reviewEmail";
					emailTemplet = "messagePrompt";
				}else if(submitType.equalsIgnoreCase(PromptTypes.isWechatTransfer)){
					fristParam = "微信充值";
					twoParam = "微信充值审核";
					url = "http://manage.vs.com/admin/rechargeReview/list";
					typeKey = "fundEmail";
					emailTemplet = "messagePrompt";
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
							boolean b=emailUtils.sendBatchMailTemp("审核", null, emailTemplet, ".ftl", list, null,
									dataMap2.getValueName());
							if(b){
								logger.info("发送给"+dataMap2.getTypeName()+"的邮件成功！！");
							}else{
								logger.info("发送给"+dataMap2.getTypeName()+"的邮件失败！！");
							}
							
						} catch (Exception e) {
							logger.info("邮件发送异常",e);

						}
					}

				}
			}
		}).start();
		
	}
	/**
	 * 
	 * @param registName 注册人
	 * @param sourceName 来源网站
	 * @param channel    渠道
	 * @param channelKeyWords 渠道关键字
	 */
	public void  registNotice(final String registMobile,final String sourceName,final String channel,final String channelKeyWords){
		new Thread(new Runnable() {
			@Override
			public void run() {
				List<DataMap> dataMapList = dataMapService.findByTypeKey("registNoticeEmail");
				EmailUtils emailUtils = EmailUtils.getInstance();
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				for (DataMap dataMap2 : dataMapList) {
					List<String> list = new ArrayList<>();
					list.add(dataMap2.getTypeName());
					list.add(registMobile);
					list.add(df.format(new Date().getTime()));
					list.add(sourceName);
					list.add(channel);
					list.add(channelKeyWords);
					try {
						boolean b=emailUtils.sendBatchMailTemp("注册通知", null, "registNotice", ".ftl", list, null,
								dataMap2.getValueName());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
			}
		}).start();
	}
}
