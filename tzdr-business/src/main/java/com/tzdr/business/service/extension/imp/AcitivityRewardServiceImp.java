package com.tzdr.business.service.extension.imp;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tzdr.business.service.datamap.DataMapService;
import com.tzdr.business.service.extension.ActivityRewardService;
import com.tzdr.business.service.pay.UserFundService;
import com.tzdr.business.service.userTrade.FSimpleFtseUserTradeService;
import com.tzdr.business.service.wuser.WUserService;
import com.tzdr.common.api.ihuyi.SMSSender;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.common.utils.TypeConvert;
import com.tzdr.domain.constants.ExtensionConstants;
import com.tzdr.domain.dao.extension.ActivityRewaryDao;
import com.tzdr.domain.web.entity.ActivityReward;
import com.tzdr.domain.web.entity.FSimpleFtseUserTrade;
import com.tzdr.domain.web.entity.UserFund;
import com.tzdr.domain.web.entity.WUser;
@Transactional(propagation=Propagation.REQUIRED)
@Service("activityRewardService")
public class AcitivityRewardServiceImp extends BaseServiceImpl<ActivityReward,ActivityRewaryDao> implements ActivityRewardService{
	private Logger logger = LoggerFactory.getLogger(AcitivityRewardServiceImp.class);
	@Autowired
	private FSimpleFtseUserTradeService fSimpleFtseUserTradeService;
	@Autowired
	private WUserService wUserService;
	@Autowired
	private UserFundService userFundService;
	@Autowired
	private DataMapService dataMapService;
	@Override
	public void doSave(ActivityReward activityReward) {
		super.save(activityReward);
	}
	@Override
	public void doSaveActivityReward(Long startTime, Long endTime) {
		if(dataMapService.activityExpired()){
			List<FSimpleFtseUserTrade> fSimpleFtseUserTrades = fSimpleFtseUserTradeService.findLossPlan(startTime, endTime);
			List<ActivityReward> activityRewards = new ArrayList<>();
			//单日总的交易手数
			for (FSimpleFtseUserTrade fSimpleFtseUserTrade : fSimpleFtseUserTrades) {
				String uid = fSimpleFtseUserTrade.getUid();
				//验证该用户在同一天是否有补贴
				ActivityReward reward = this.doGetValidationIsReward(uid , ExtensionConstants.ACTIVITY_TYPE, startTime, endTime, ExtensionConstants.REWARD_TYPE_SUBSIDY);
				if(reward != null){
					continue;
				}
				int actualLever = fSimpleFtseUserTrade.getTranActualLever()
									+fSimpleFtseUserTrade.getCrudeTranActualLever()
									+fSimpleFtseUserTrade.getHsiTranActualLever()
									+fSimpleFtseUserTrade.getMntranActualLever()
									+fSimpleFtseUserTrade.getMbtranActualLever()
									+fSimpleFtseUserTrade.getDaxtranActualLever()
									+fSimpleFtseUserTrade.getNikkeiTranActualLever()
									+fSimpleFtseUserTrade.getMdtranActualLever()
									+fSimpleFtseUserTrade.getLhsiTranActualLever()
									+fSimpleFtseUserTrade.getAgTranActualLever();
				//补贴金额
				Double subMoney = 0.00;
				Long createTime = new Date().getTime()/1000;
				try {
					if(actualLever >= 10){
						Double endAmount = fSimpleFtseUserTrade.getEndAmount().doubleValue();
						Double bondAmount = fSimpleFtseUserTrade.getTraderBond().doubleValue();
						if(endAmount != null && bondAmount != null){
							DecimalFormat df = new DecimalFormat("0.00");
							Double jAmount =  new Double(df.format(fSimpleFtseUserTrade.getTranProfitLoss().subtract(fSimpleFtseUserTrade.getEndParities()).doubleValue()));
							if(jAmount < 0){
								subMoney = Math.abs(jAmount);
								if(actualLever >= ExtensionConstants.SUBSIDYLEVER10 && actualLever < ExtensionConstants.SUBSIDYLEVER20 ){
									if(subMoney > ExtensionConstants.SUBSIDY10MONEY){
										subMoney = ExtensionConstants.SUBSIDY10MONEY;
									}
								}else if(actualLever >= ExtensionConstants.SUBSIDYLEVER20 && actualLever < ExtensionConstants.SUBSIDYLEVER40){
									if(subMoney > ExtensionConstants.SUBSIDY20MONEY){
										subMoney =ExtensionConstants.SUBSIDY20MONEY;
									}
								}else if(actualLever >= ExtensionConstants.SUBSIDYLEVER40){
									if(subMoney > ExtensionConstants.SUBSIDY40MONEY){
										subMoney =ExtensionConstants.SUBSIDY40MONEY;
									}
								}
								BigDecimal b =  new   BigDecimal(subMoney);
								subMoney = b.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();
								ActivityReward activityReward = new ActivityReward();
								activityReward.setIstip(false);
				    			activityReward.setIsvalid(true);
				    			activityReward.setReward_type(ExtensionConstants.REWARD_TYPE_SUBSIDY);
				    			activityReward.setUid(uid);
				    			activityReward.setType(ExtensionConstants.SubsidyType.SUBA50);
				    			activityReward.setMoney(subMoney);
				    			activityReward.setActivity(ExtensionConstants.ACTIVITY_TYPE);
				    			activityReward.setCreateTime(createTime);
				    			activityRewards.add(activityReward);
							}
						}else{
							logger.info(uid + "补贴异常");
						}
					}
				} catch (Exception e) {
					logger.info(uid + "补贴异常");
				}
			}
			if(activityRewards.size() > 0){
				this.saves(activityRewards);
			}
			//获取短信通道
			Integer smsChannel = dataMapService.getSmsContentOthers();
			Map<String,String> smsParams= new HashMap<String,String>();  //创建短信动态参数集合 
			for (ActivityReward activityReward : activityRewards) {
				String uid = activityReward.getUid();
					Double addMoney = activityReward.getMoney();
					WUser user = wUserService.getUser(uid);
					if(user != null){
						user.setFund(user.getFund() + addMoney);
						//更新用户的账户余额
						wUserService.update(user);
						//增加充值记录
						UserFund userFund = new UserFund();
						userFund.setUid(user.getId());
						userFund.setMoney(addMoney);
						userFund.setType(TypeConvert.USER_FUND_C_TYPE_ARTIFICIAL_RECHARGE);
						userFund.setRemark("免损补贴：" + addMoney + "元");
						userFundService.rechargeOperation(userFund, TypeConvert.TAKE_DEPOSIT_TYPE_INSTORE);
						smsParams.put("money", String.valueOf(addMoney));
						try{
							//发送短信通知用户
							SMSSender.getInstance().sendByTemplate(smsChannel , user.getMobile(), "subdsion.ihuyi.code.template",smsParams );
						} catch (Exception e) {
							logger.info("通知补贴到账的短信发送异常");
						}
					}
			}
		}
	}

	@Override
	public ActivityReward findByUidAndActivity(String uid, String activity,Boolean isvalid,String rewardType) {
		return getEntityDao().doGetActivityLuckDraw(uid,activity,isvalid,rewardType);
	}
	public List<ActivityReward> doGetActivitySubsidy(String uid, String activity,Boolean isvalid,String rewardType ,Boolean istip){
		List<ActivityReward> activityRewards = getEntityDao().doGetActivitySubsidy(uid, activity, isvalid, rewardType, istip);
		List<ActivityReward> rewards = activityRewards;
		for (ActivityReward reward : rewards) {
			reward.setIstip(true);
		}
		getEntityDao().saves(rewards);
		return activityRewards;
	}
	@Override
	public ActivityReward doGetValidationIsReward(String uid , String activity, Long startTime, Long endTime,String rewardType) {
		return getEntityDao().doGetValidationIsReward(uid, activity, startTime, endTime, rewardType);
	}
	@Override
	public boolean doUpdateReward(ActivityReward activityReward) {
		getEntityDao().update(activityReward);
		return true;
	}
	@Override
	public ActivityReward doGetById(String id) {
		return getEntityDao().findById(id);
	}
}
