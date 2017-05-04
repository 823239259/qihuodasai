package com.tzdr.business.app.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jodd.util.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.tzdr.business.service.contract.ContractParitiesService;
import com.tzdr.business.service.datamap.DataMapService;
import com.tzdr.business.service.future.FSimpleCouponService;
import com.tzdr.business.service.recharge.RechargeListService;
import com.tzdr.business.service.userTrade.FSimpleConfigService;
import com.tzdr.business.service.userTrade.FSimpleParitiesService;
import com.tzdr.business.service.userTrade.FinternationFutureAppendLevelMoneyService;
import com.tzdr.common.api.contact.BusinessTypeEnum;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.common.utils.Dates;
import com.tzdr.common.utils.TypeConvert;
import com.tzdr.domain.app.vo.FTradeParamsVo;
import com.tzdr.domain.app.vo.FTradeVo;
import com.tzdr.domain.app.vo.OutDiskVo;
import com.tzdr.domain.app.vo.UserFTradeDetailsVo;
import com.tzdr.domain.app.vo.UserFTradeVo;
import com.tzdr.domain.dao.userTrade.FSimpleFtseUserTradeDao;
import com.tzdr.domain.web.entity.FSimpleFtseUserTrade;
import com.tzdr.domain.web.entity.FSimpleParities;
import com.tzdr.domain.web.entity.FinternationFutureAppendLevelMoney;
import com.tzdr.domain.web.entity.WUser;
import com.tzdr.domain.web.entity.future.FSimpleCoupon;

/**  
 * @Title: FTradeService.java     
 * @Description: 期货业务信息管理类    
 * @author： WangPinQun 
 * @E-mail：wangpinqun@tzdr.com
 * @company： 上海信闳投资管理有限公司重庆分公司
 * @address：重庆市渝北区黄山大道中段70号两江新界3栋13楼
 * @date： 2016年3月22日 上午10:07:41    
 * @version： V1.0  
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class FTradeService extends BaseServiceImpl<FSimpleFtseUserTrade, FSimpleFtseUserTradeDao> {

	/**
	 * 综合操盘类型名称
	 */
	private final static String  defaultTypeName = "综合操盘";  
	
	/**
	 * 综合操盘类型
	 */
	private final static Integer  defaultType = 8;  
	
	/**
	 * 汇率类型
	 */
	private final static int PARITIESTYPE = 1;
	
	@Autowired
	private ContractParitiesService contractParitiesService;
	
	@Autowired
	private RechargeListService rechargeListService;
	
	@Autowired
	private DataMapService dataMapService;
	
	@Autowired
	private FinternationFutureAppendLevelMoneyService finternationFutureAppendLevelMoneyService;
	
	@Autowired
	private FSimpleParitiesService fSimpleParitiesService;
	
	@Autowired
	private FSimpleCouponService fSimpleCouponService;
	
	
	@Autowired
	private FSimpleConfigService fSimpleConfigService;
	
	/**
	* @Title: findGoods    
	* @Description: 获取期货产品信息 
	* @return
	 */
	@SuppressWarnings("unchecked")
	public List<FTradeVo> findGoods(){
		StringBuffer sql  = new StringBuffer();
		sql.append(" SELECT f.type,f.type_name,f.tran_fees FROM f_simple_config f ");
		sql.append(" WHERE f.type in(?,?,?,?) GROUP BY f.type ORDER BY IF(f.type=7,'5.5',f.type) ASC ");
		List<FTradeVo> dataList = this.getEntityDao().queryListBySql(sql.toString(), FTradeVo.class, null, new Object[]{5,6,7,9});
		FTradeVo ftradeVo = new FTradeVo();
		ftradeVo.setType(defaultType);
		ftradeVo.setTypeName(defaultTypeName);
		
		List<OutDiskVo> outDiskVoList = this.findOutDiskVos(); //获取综合操盘交易品种列表信息 
		ftradeVo.setOutDiskVoList(outDiskVoList);
		dataList = dataList == null ? new ArrayList<FTradeVo>() : dataList;
		dataList.add(ftradeVo);
		return dataList;
	}
	
	/**
	* @Title: findOutDiskVos    
	* @Description: 获取综合操盘交易品种列表信息 
	* @return
	 */
	@SuppressWarnings("unchecked")
	public List<OutDiskVo> findOutDiskVos(){
		
		StringBuffer sql  = new StringBuffer();
		sql.append("SELECT p.trade_type,p.main_contract,p.price,trad_time FROM w_out_disk_price p ORDER BY p.trade_type ASC ");
		List<OutDiskVo> outDiskVoList = this.getEntityDao().queryListBySql(sql.toString(), OutDiskVo.class, null, new Object[]{});
		
		return outDiskVoList;
	}
	
	/**
	* @Title: findUserFTradeVos    
	* @Description: 获取用户期货列表信息
	* @return
	 */
	@SuppressWarnings("unchecked")
	public List<UserFTradeVo> findUserFTradeVos(String uid){
		
		if(StringUtil.isBlank(uid)){
			return null;
		}
		
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT f.id,f.trader_total,f.line_loss,f.tran_lever,f.app_time,f.state_type,f.business_type ");
		sql.append(" FROM f_simple_ftse_user_trade f ");
		sql.append(" WHERE f.uid=? AND f.business_type in(?,?,?,?,?) ");
		sql.append(" ORDER BY f.app_time DESC ");
		List<UserFTradeVo> userFTradeVoList = this.getEntityDao().queryListBySql(sql.toString(), UserFTradeVo.class, null, new Object[]{uid,0,6,7,8,9});
		return userFTradeVoList;
	}
	
	@SuppressWarnings("unchecked")
	public UserFTradeDetailsVo getUserFTradeDetailsVo(String uid,String id){
		
		if(StringUtil.isBlank(uid) || StringUtil.isBlank(id)){
			return null;
		}
		
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT f.id,f.app_time,f.tran_lever,f.trader_bond,f.append_trader_bond ");
		sql.append(" ,f.trader_total,f.line_loss,f.fee_manage,f.tran_fees,f.tran_account ");
		sql.append(" ,f.tran_password,f.end_time,f.tran_profit_loss,f.end_parities ");
		sql.append(" ,f.tran_fees_total,f.tran_actual_lever,f.crude_tran_actual_lever,f.mdtran_actual_lever ");
		sql.append(" ,f.hsi_tran_actual_lever,f.mntran_actual_lever,f.mbtran_actual_lever,f.ag_tran_actual_lever,f.lhsi_tran_actual_lever ");
		sql.append(" ,f.daxtran_actual_lever,f.nikkei_tran_actual_lever,f.daxtran_min_actual_lever,f.xhstock_market_lever ");
		sql.append(" ,f.ame_copper_market_lever,f.ame_silver_market_lever,f.small_crude_oil_market_lever,f.h_stock_market_lever,f.natural_gas_actual_lever ");
		sql.append(" ,f.end_amount,f.state_type,f.business_type ");
		sql.append(" FROM f_simple_ftse_user_trade f ");
		sql.append(" WHERE f.id=? AND f.uid=? ");
		
		List<UserFTradeDetailsVo> userFTradeDetailsVoList =  this.getEntityDao().queryListBySql(sql.toString(), UserFTradeDetailsVo.class, null, new Object[]{id,uid});
		
		//方案详情信息
		UserFTradeDetailsVo userFTradeDetailsVo =  null;
		
		Integer businessType = null;
		
		if(!CollectionUtils.isEmpty(userFTradeDetailsVoList)){  //判断集合是否为空
			userFTradeDetailsVo = userFTradeDetailsVoList.get(0);
			businessType = userFTradeDetailsVo.getBusinessType();
			List<OutDiskVo> outDiskVoList = null;
			if(businessType != null && businessType == 8){
				outDiskVoList = this.findOutDiskVos(); //获取综合操盘交易品种列表信息 
			}else if(businessType != null && businessType != 8){
				outDiskVoList = this.findOutDiskVos(businessType); //获取综合操盘交易品种列表信息 
			}
			userFTradeDetailsVo.setOutDiskVoList(outDiskVoList);
		}
		
		return userFTradeDetailsVo;
	}
	
	/**
	* @Title: findOutDiskVos    
	* @Description: 获取非综合操盘交易品种列表信息【富时A50、恒指期货、国际原油】 
	* @return
	 */
	@SuppressWarnings("unchecked")
	public List<OutDiskVo> findOutDiskVos(Integer businessType){
		
		if(businessType == null){
			return null;
		}
		
		StringBuffer sql  = new StringBuffer();
		sql.append(" SELECT p.business_type AS trade_type,p.contract AS main_contract ");
		sql.append(" FROM w_contract_parities p ");
		sql.append(" WHERE p.business_type=? ");
		List<OutDiskVo> outDiskVoList = this.getEntityDao().queryListBySql(sql.toString(), OutDiskVo.class, null, new Object[]{businessType});
		
		return outDiskVoList;
	}
	
	/**
	* @Title: addbond    
	* @Description: 追加保证金
	* @param wuser
	* @param fSimpleFtseUserTrade
	* @param addbond
	 * @throws Exception 
	 */
	public void addbond(WUser wuser,FSimpleFtseUserTrade fSimpleFtseUserTrade,BigDecimal addbond) throws Exception{

		Integer type = fSimpleFtseUserTrade.getBusinessType();
		String remark=BusinessTypeEnum.getBusinessTypeToBusiness(type);
		remark = remark + "追加保证金";
		fSimpleFtseUserTrade.setAppendTraderBond(TypeConvert.scale(fSimpleFtseUserTrade.getAppendTraderBond().add(addbond),2));
		
		//追加保证金划款
		rechargeListService.futureHandlerSaveRechargeStateWeb(fSimpleFtseUserTrade.getProgramNo(),
				wuser.getMobile(), addbond.toString(), remark, TypeConvert.SYS_TYPE_OFFSET_ACCOUNTS);
		
		//更新股指追加保证金
		this.update(fSimpleFtseUserTrade);
		
		//获取固定汇率
		String rate = dataMapService.findByTypeKey("exchangeRate").get(0).getValueKey();
		
		//换算美元[追加美元]
		Double dollar = Math.floor((addbond.doubleValue() / Double.parseDouble(rate))*100)/100;
		
		//创建追加保证金记录
		FinternationFutureAppendLevelMoney appendLevelMoney = new FinternationFutureAppendLevelMoney(
				wuser.getId(),fSimpleFtseUserTrade.getProgramNo(),addbond.doubleValue(),Double.parseDouble(rate),dollar,type);
		
		finternationFutureAppendLevelMoneyService.save(appendLevelMoney);  //保存追加保证金记录
	} 
	
	/**
	* @Title: endtrade    
	* @Description: 申请终结
	* @param fSimpleFtseUserTrade
	* @param discount
	 */
	public void endtrade(FSimpleFtseUserTrade fSimpleFtseUserTrade,FSimpleCoupon discount){
		
		FSimpleParities fSimpleParities = fSimpleParitiesService.getFSimpleParities(PARITIESTYPE);
		
		fSimpleFtseUserTrade.setEndParities(fSimpleParities.getParities());  //申请终结当前汇率
		fSimpleFtseUserTrade.setStateType(2);  //申请终结方案
		fSimpleFtseUserTrade.setAppEndTime(Dates.getCurrentLongDate());  //申请终结时间
		
		if(discount != null){
			fSimpleFtseUserTrade.setDiscountId(discount.getId());
			fSimpleFtseUserTrade.setDiscountMoney(discount.getMoney());
			// 更新优惠券状态为已使用
			discount.setStatus((short)3);
			discount.setUseTime(TypeConvert.dbDefaultDate());
			this.fSimpleCouponService.update(discount);  //使用折扣劵
		}
		this.getEntityDao().update(fSimpleFtseUserTrade);  //申请终结
	}
	
	
	/**
	 * 获取 恒指期货、A50、国际原油 操盘参数
	 * @param businessType
	 * @return
	 */
	public List<FTradeParamsVo> queryFtradeParams(int businessType){
		StringBuffer sql = new StringBuffer("SELECT tran_lever,tran_fees,line_loss,fee_manage,trader_bond,trader_money trader_total from f_simple_config where type=? order by CAST(tran_lever as SIGNED) asc, trader_bond asc");
		List<FTradeParamsVo> ftradeParamsVos = this.getEntityDao().queryListBySql(sql.toString(), FTradeParamsVo.class, null, businessType);
		return ftradeParamsVos;
	}
	
	/**
	 * 获取国际综合 操盘参数
	 * @return
	 */
	public List<FTradeParamsVo> queryFtradeParams(){
		StringBuffer sql = new StringBuffer(" SELECT atran_actual_lever tran_lever, htran_actual_lever, ytran_actual_lever, mntran_actual_lever, mbtran_actual_lever, daxtran_actual_lever, nikkei_tran_actual_lever, mdtran_actual_lever, hstran_actual_lever, agtran_actual_lever,"
				+ "h_index_actual_lever,xh_index_actual_lever,a_copper_actual_lever,a_silver_actual_lever,sma_actual_lever,daxtran_min_actual_lever,natural_gas_actual_lever,line_loss, trader_bond, trader_total FROM w_out_disk_parameters ORDER BY trader_bond ASC ");
		List<FTradeParamsVo> ftradeParamsVos = this.getEntityDao().queryListBySql(sql.toString(), FTradeParamsVo.class, null);
		return ftradeParamsVos;
	}
}
