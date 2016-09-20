package com.tzdr.business.api.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.tzdr.business.service.recharge.RechargeListService;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.common.utils.Dates;
import com.tzdr.common.utils.StringCodeUtils;
import com.tzdr.common.utils.TypeConvert;
import com.tzdr.domain.dao.rechargelist.RechargeListDao;
import com.tzdr.domain.web.entity.RechargeList;


/**
 * 充值 服务service
 * @author zhouchen
 * 2015年7月7日 下午12:02:06
 */
@Service
@Transactional
public class ApiRechargeService extends BaseServiceImpl<RechargeList,RechargeListDao> {
	public static final Logger logger = LoggerFactory.getLogger(ApiRechargeService.class);	

	
	@Autowired
	private RechargeListService rechargeListService;
	/**
	 * 根据流水号查询是否存在该交易记录
	 * @param tradeNo
	 * @return
	 */
	public List<RechargeList> queryByTradeNo(String tradeNo){
		return this.getEntityDao().findByTradeNo(tradeNo);
	}
	
	/**
	 * 生成随机字符串
	 * @return
	 * @date 2015年1月4日
	 * @author zhangjun
	 */
	private String getRandomStr(int length){
		String orderId=StringCodeUtils.getRandomStr(length);
		List<RechargeList> chargelist=getEntityDao().findByTradeNo(orderId);
		if(!CollectionUtils.isEmpty(chargelist)){
			return getRandomStr(length);
		}
		return orderId;
	}
	
	/**
	 * 支付宝自动充值  保存记录
	 * @param charge
	 */
	public void  autoAliPay(RechargeList charge){
		//生成订单号
		String 	orderId=this.getRandomStr(20);
		charge.setAddtime(Dates.getCurrentLongDate());
		charge.setNo(orderId);
		//匹配成功 直接充值
		if (charge.getStatus()==TypeConvert.RECHARGE_LIST_PAYS_STATUS_SUCCESS){
			charge.setOktime(Dates.getCurrentLongDate());
			
			this.getEntityDao().save(charge);
			this.rechargeListService.addUpdateRechargeList(charge,
					TypeConvert.USER_FUND_C_TYPE_RECHARGE,
					TypeConvert.payRemark(TypeConvert.SYS_TYPE_ALIBABA_ACCOUNTS_NAME, charge.getActualMoney()));
			return ;
			
		}
		
		this.getEntityDao().save(charge);
	}
	/**
	 * 微信自动充值  保存记录
	 * @param charge
	 */
	public void  autoWechat(RechargeList charge){
		//生成订单号
		String 	orderId=this.getRandomStr(20);
		charge.setAddtime(Dates.getCurrentLongDate());
		charge.setNo(orderId);
		//匹配成功 直接充值
		if (charge.getStatus()==TypeConvert.RECHARGE_LIST_PAYS_STATUS_SUCCESS){
			charge.setOktime(Dates.getCurrentLongDate());
			
			this.getEntityDao().save(charge);
			this.rechargeListService.addUpdateRechargeList(charge,
					TypeConvert.USER_FUND_C_TYPE_RECHARGE,
					TypeConvert.payRemark(TypeConvert.SYS_TYPE_WECHAT_ACCOUNTS_NAME, charge.getActualMoney()));
			return ;
			
		}
		
		this.getEntityDao().save(charge);
	}
}
