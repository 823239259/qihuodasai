package com.tzdr.api.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jodd.util.ObjectUtil;
import jodd.util.StringUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tzdr.api.constants.BusinessTypeEnum;
import com.tzdr.api.constants.DataConstant;
import com.tzdr.api.constants.ResultStatusConstant;
import com.tzdr.api.support.ApiResult;
import com.tzdr.api.util.AuthUtils;
import com.tzdr.business.app.service.FTradeService;
import com.tzdr.business.cms.service.messagePrompt.MessagePromptService;
import com.tzdr.business.cms.service.messagePrompt.PromptTypes;
import com.tzdr.business.service.OutDisk.OutDiskParametersService;
import com.tzdr.business.service.OutDisk.OutDiskPriceService;
import com.tzdr.business.service.future.FSimpleCouponService;
import com.tzdr.business.service.tradeDetail.TradeDetailService;
import com.tzdr.business.service.userTrade.FSimpleConfigService;
import com.tzdr.business.service.userTrade.FSimpleFtseUserTradeService;
import com.tzdr.business.service.wuser.WUserService;
import com.tzdr.domain.app.vo.FTradeApplyVo;
import com.tzdr.domain.app.vo.UserFTradeDetailsVo;
import com.tzdr.domain.app.vo.UserFTradeVo;
import com.tzdr.domain.web.entity.FSimpleConfig;
import com.tzdr.domain.web.entity.FSimpleFtseUserTrade;
import com.tzdr.domain.web.entity.OutDiskParameters;
import com.tzdr.domain.web.entity.OutDiskPrice;
import com.tzdr.domain.web.entity.TradeDetail;
import com.tzdr.domain.web.entity.WUser;
import com.tzdr.domain.web.entity.future.FSimpleCoupon;

/**  
 * @Title: FTradeController.java     
 * @Description: 期货产品管理信息    
 * @author： WangPinQun 
 * @E-mail：wangpinqun@tzdr.com
 * @company： 上海信闳投资管理有限公司重庆分公司
 * @address：重庆市渝北区黄山大道中段70号两江新界3栋13楼
 * @date： 2016年3月22日 上午9:32:13    
 * @version： V1.0  
 */
@Controller
@RequestMapping("/user/ftrade")
public class UserFTradeController {
	
	public static final Logger logger = LoggerFactory.getLogger(UserFTradeController.class);
	@Autowired
	private MessagePromptService messagePromptService;
	
	@Autowired
	private FTradeService fTradeService;
	
	
	@Autowired
	private WUserService  wUserService;
	
	@Autowired
	private FSimpleCouponService fSimpleCouponService;
	
	
	@Autowired
	private FSimpleConfigService fSimpleConfigService;
	
	
	@Autowired
	private OutDiskParametersService outDiskParametersService;
	
	@Autowired
	private OutDiskPriceService outDiskPriceService;
	
	
	@Autowired
	private FSimpleFtseUserTradeService fSimpleFtseUserTradeService;
	
	@Autowired
	private TradeDetailService tradeDetailService;
	
	/**
	* @Title: list    
	* @Description: 获取用户期货产品方案列表信息 
	* @param modelMap
	* @param request
	* @param response
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/list" , method = RequestMethod.POST)
	@ResponseBody
	public ApiResult list(ModelMap modelMap,HttpServletRequest request,HttpServletResponse response) throws Exception{
	
		String uid = AuthUtils.getCacheUser(request).getUid();  //获取用户信息
		
		List<UserFTradeVo> dataList = fTradeService.findUserFTradeVos(uid);
		
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("tradeList", dataList);
		
		return new ApiResult(true,ResultStatusConstant.SUCCESS,null,dataMap);
	}
	
	/**
	* @Title: details    
	* @Description: 获取用户期货产品方案详情信息 
	* @param id  方案编号
	* @param modelMap
	* @param request
	* @param response
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/details" , method = RequestMethod.POST)
	@ResponseBody
	public ApiResult details(String id,ModelMap modelMap,HttpServletRequest request,HttpServletResponse response) throws Exception{
	
		String uid = AuthUtils.getCacheUser(request).getUid();  //获取用户信息
		
		UserFTradeDetailsVo details = fTradeService.getUserFTradeDetailsVo(uid, id);
		
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("details", details);
		
		return new ApiResult(true,ResultStatusConstant.SUCCESS,null,dataMap);
	}
	/**
	 * 获取交易明细
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getFstTradeDetail",method = RequestMethod.POST)
	public ApiResult getTradeDetail(HttpServletRequest request,@RequestParam("id")String id){
		ApiResult apiResult = new ApiResult();
		try {
			apiResult.setSuccess(true);
			List<TradeDetail> details = tradeDetailService.doGetByFtseId(id);
			apiResult.setData(details);
		} catch (Exception e) {
			apiResult.setSuccess(false);
		}
		return apiResult;
	}
	/**
	* @Title: details    
	* @Description: 追加保证金
	* @param id  方案编号
	* @param addBond  追加金额
	* @param modelMap
	* @param request
	* @param response
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/addbond" , method = RequestMethod.POST)
	@ResponseBody
	public ApiResult addbond(String id,Double addBond,ModelMap modelMap,HttpServletRequest request,HttpServletResponse response) throws Exception{
	
		String uid = AuthUtils.getCacheUser(request).getUid();  //获取用户信息
		
		if (StringUtil.isBlank(id)){  //判断方案编号是否为空
			return new ApiResult(false,ResultStatusConstant.AddbondConstant.ID_NOT_NULL,"The id cannot be empty");
		}
		
		if (addBond == null){  //判断追金额是否为空
			return new ApiResult(false,ResultStatusConstant.AddbondConstant.ADDBOND_NOT_NULL,"The addBond cannot be empty");
		}
		
        BigDecimal payMoney = new BigDecimal(addBond);  //追加保证金
		
		BigDecimal defaultMinAppendMoney = new BigDecimal(DataConstant.FTRADE_DEFAULT_MIN_ADD_BOND);  //默认最小追加保证金

		if(payMoney.compareTo(defaultMinAppendMoney) < 0){   //追加金额是否低于默认最小追加保证金
			return new ApiResult(false,ResultStatusConstant.AddbondConstant.ADDBOND_TOO_SMALL,"The addBond is too small");
		}
		
		WUser wuser = wUserService.get(uid);
		
		BigDecimal avlBal = new BigDecimal(wuser.getAvlBal().toString());  //获取用户余额
		
		if(avlBal.compareTo(payMoney) < 0 ) {   //判断追加保证金是否大于用户余额
			return new ApiResult(false,ResultStatusConstant.AddbondConstant.BALANCE_NOT_ENOUGH,"The balance is not enough");
		}
		
		FSimpleFtseUserTrade fSimpleFtseUserTrade = fTradeService.get(id); //获取方案信息
		
		if(fSimpleFtseUserTrade == null || !uid.equals(fSimpleFtseUserTrade.getUid())){   //未找到该方案
			return new ApiResult(false,ResultStatusConstant.AddbondConstant.UN_FIND_DATA,"The data was not found");
		}else if(fSimpleFtseUserTrade.getStateType() == DataConstant.FTRADE_IS_OVER){   //已完结
			return new ApiResult(false,ResultStatusConstant.AddbondConstant.DATA_IS_OVER,"The data is over");
		}
		
		fTradeService.addbond(wuser, fSimpleFtseUserTrade, payMoney);  //追加保证金
		messagePromptService.sendMessage(PromptTypes.isAddBond, wuser.getMobile());
		return new ApiResult(true,ResultStatusConstant.SUCCESS,"Successful addBond",null);
	}
	
	/**
	* @Title: details    
	* @Description: 申请终结方案
	* @param id  方案编号
	* @param addBond  追加金额
	* @param modelMap
	* @param request
	* @param response
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/endtrade" , method = RequestMethod.POST)
	@ResponseBody
	public ApiResult endtrade(String id,String cId,Integer businessType,ModelMap modelMap,HttpServletRequest request,HttpServletResponse response) throws Exception{
	
		String uid = AuthUtils.getCacheUser(request).getUid();  //获取用户信息
		WUser wuser = wUserService.get(uid);
		if (StringUtil.isBlank(id)){  //判断方案编号是否为空
			return new ApiResult(false,ResultStatusConstant.EndtradeConstant.ID_NOT_NULL,"The id cannot be empty");
		}
		
		FSimpleFtseUserTrade fSimpleFtseUserTrade = fTradeService.get(id);
		
		if(fSimpleFtseUserTrade == null || !uid.equals(fSimpleFtseUserTrade.getUid())){
			return new ApiResult(false,ResultStatusConstant.EndtradeConstant.UN_FIND_FRADE_DATA,"The ftrade data was not found");
		}
		
		if(fSimpleFtseUserTrade.getStateType() == DataConstant.FTRADE_STATE_TYPE_2){   //不能重复申请
			return new ApiResult(false,ResultStatusConstant.EndtradeConstant.ALREADY_SUBMIT,"The data is already submit");
		}
		
		if(fSimpleFtseUserTrade.getStateType() != DataConstant.FTRADE_STATE_TYPE_4){  //判断是不是操盘中 
			return new ApiResult(false,ResultStatusConstant.EndtradeConstant.UN_STOCK_OPERATION,"The data was not stock operation");
		}
		
		if(StringUtil.isBlank(cId) && businessType != null){  //判断使用折扣劵【折扣劵编号为空】
			return new ApiResult(false,ResultStatusConstant.EndtradeConstant.CID_NOT_NULL,"The cId cannot be empty");
		}

		if(StringUtil.isNotBlank(cId) && businessType == null){  //判断使用折扣劵【业务类型为空】
			return new ApiResult(false,ResultStatusConstant.EndtradeConstant.BUSINESSTYPE_NOT_NULL,"The businessType cannot be empty");
		}
		
		FSimpleCoupon discount = null; //折扣券信息
		
		if(StringUtil.isNotBlank(cId) && businessType != null){   //判断是否使用折扣劵
			discount = fSimpleCouponService.get(cId);
			if(discount == null){  //为找到折扣劵信息
				return new ApiResult(false,ResultStatusConstant.EndtradeConstant.UN_FIND_COUPON_DATA,"The Coupon data was not found");
			}
			if(!fSimpleCouponService.isCouponValid(discount,DataConstant.COUPON_TYPE_3, businessType)) {  //判断该折扣劵是否可以使用
				return new ApiResult(false,ResultStatusConstant.EndtradeConstant.COUPON_EEROR,"The Coupon not available");
			}
		}
		
		fTradeService.endtrade(fSimpleFtseUserTrade, discount);  //申请终结方案
		messagePromptService.sendMessage(PromptTypes.isEndScheme, wuser.getMobile());
		return new ApiResult(true,ResultStatusConstant.SUCCESS,"Successful endTrade",null);
	}
	
	

	/**
	 * 国际期货操盘申请
	 * @param traderBond 操盘保证金
	 * @param tranLever  操盘手数
	 * @param businessType 业务类型 0.富时A50  6.原油 7.恒指 8.国际综合 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/apply" , method = RequestMethod.POST)
	@ResponseBody
	public ApiResult apply(BigDecimal traderBond, Integer tranLever,int businessType
			,HttpServletRequest request,HttpServletResponse response) throws Exception{
		if (ObjectUtil.equals(null, traderBond) 
				||ObjectUtil.equals(null, tranLever)
				|| !BusinessTypeEnum.isBusinessType(businessType)){
			return new ApiResult(false,ResultStatusConstant.FAIL,"params.is.error.");
		}
		Map<String, Object> dataMap = new HashMap<String, Object>();
		//获取用户信息
		String uid = AuthUtils.getCacheUser(request).getUid();  
		WUser wuser = wUserService.get(uid);		
		//应付金额
		BigDecimal payable = new BigDecimal(DataConstant.ZERO);
		//A50 \ 国际期货 \ 原油  \ 小恒指
		if (BusinessTypeEnum.A50.getValue()==businessType 
				||BusinessTypeEnum.CRUDE.getValue()==businessType 
				||BusinessTypeEnum.HSI.getValue()==businessType
				||BusinessTypeEnum.LHSI.getValue()==businessType){
			int cfgBusinessType = (BusinessTypeEnum.A50.getValue()==businessType)?BusinessTypeEnum.A50_CONFIG.getValue():businessType;
			FSimpleConfig fSimpleConfig = fSimpleConfigService.getFSimpleConfig(cfgBusinessType,String.valueOf(tranLever));  //获取配置方案信息
			if (ObjectUtil.equals(null, fSimpleConfig)){
				return new ApiResult(false,ResultStatusConstant.FAIL,"not.fund.config.params.");	
			}
			// 传入的保证金是否与系统匹配
			if (DataConstant.ZERO != traderBond.compareTo(fSimpleConfig.getTraderBond())){
				return new ApiResult(false,ResultStatusConstant.FAIL,"traderBond.is.error.params.");	
			}
			
			// 如果是恒指获取配置的固定手续费
			/* if (BusinessTypeEnum.HSI.getValue()==businessType){
				 String hsiTranFees = MessageUtils.message("tzdr.app.hsi.tran.fees");
				 if (StringUtils.isNotBlank(hsiTranFees)){
					 fSimpleConfig.setTranFees(new BigDecimal(hsiTranFees));
				 }
			 }*/
			//管理费
			BigDecimal manageFee = ObjectUtil.equals(null,fSimpleConfig.getFeeManage())?new BigDecimal(DataConstant.ZERO):fSimpleConfig.getFeeManage();
			//应付金额：保证金+管理费
			payable =  traderBond.add(manageFee);
			dataMap.put("confirmInfo", new FTradeApplyVo(fSimpleConfig, payable, wuser.getAvlBal()));
		}
		
		// 国际综合
		if (BusinessTypeEnum.MULTIPLE.getValue()==businessType){
			List<OutDiskParameters> outDiskParametersList = outDiskParametersService.findByTraderBond(traderBond);
			if (CollectionUtils.isEmpty(outDiskParametersList)){
				return new ApiResult(false,ResultStatusConstant.FAIL,"not.fund.config.params.");	
			}
			//应付金额
			payable =  traderBond;
			dataMap.put("confirmInfo", new FTradeApplyVo(outDiskParametersList.get(DataConstant.ZERO), payable, wuser.getAvlBal()));
		}
		
		// 代金券
		List<Map<String, Object>> vouchers = this.fSimpleCouponService.queryCouponByUserId(uid,DataConstant.BUSINESSTYPE_APPLY_COUPONS, businessType);
		dataMap.put("voucherList",vouchers);
		return new ApiResult(true,ResultStatusConstant.SUCCESS,"apply.Successful",dataMap);
	}
	
	/**
	 *  支付确认接口
	 * @param businessType
	 * @param traderBond
	 * @param tranLever
	 * @param vid
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/handle")
	@ResponseBody
	public synchronized ApiResult handle(int businessType,BigDecimal traderBond, Integer tranLever,String vid,
			HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		if (ObjectUtil.equals(null, traderBond) 
				||ObjectUtil.equals(null, tranLever)
				|| !BusinessTypeEnum.isBusinessType(businessType)){
			return new ApiResult(false,ResultStatusConstant.FAIL,"params.is.error.");
		}
		// 国际综合
		if (businessType == BusinessTypeEnum.MULTIPLE.getValue()){
			return this.multipleHandle(businessType, traderBond, vid, request);
		}
		
		//获取用户信息
		String uid = AuthUtils.getCacheUser(request).getUid();  
		WUser wuser = wUserService.get(uid);		
		//应付金额
		BigDecimal payable = new BigDecimal(DataConstant.ZERO);
		
		//A50 \ 国际期货 \ 原油 \ 小恒指
		int cfgBusinessType = (BusinessTypeEnum.A50.getValue()==businessType)?BusinessTypeEnum.A50_CONFIG.getValue():businessType;
		FSimpleConfig fSimpleConfig = fSimpleConfigService.getFSimpleConfig(cfgBusinessType,String.valueOf(tranLever));  //获取配置方案信息
		if (ObjectUtil.equals(null, fSimpleConfig)){
			return new ApiResult(false,ResultStatusConstant.FAIL,"not.fund.config.params.");	
		}
		// 传入的保证金是否与系统匹配
		if (DataConstant.ZERO != traderBond.compareTo(fSimpleConfig.getTraderBond())){
			return new ApiResult(false,ResultStatusConstant.FAIL,"traderBond.is.error.params.");	
		}
		//管理费
		BigDecimal manageFee = ObjectUtil.equals(null,fSimpleConfig.getFeeManage())?new BigDecimal(DataConstant.ZERO):fSimpleConfig.getFeeManage();
		//应付金额
		payable =  traderBond.add(manageFee);
		//交易费
		BigDecimal totalTranFees = new BigDecimal(DataConstant.ZERO).add(fSimpleConfig.getTranFees());
		// 如果是恒指获取配置的固定手续费
		/* if (BusinessTypeEnum.HSI.getValue()==businessType){
			 String hsiTranFees = MessageUtils.message("tzdr.app.hsi.tran.fees");
			 if (StringUtils.isNotBlank(hsiTranFees)){
				 totalTranFees= new BigDecimal(hsiTranFees);
			 }
		 }*/
		 
		BigDecimal avlBal = new BigDecimal(wuser.getAvlBal().toString());
		// 验证代金券
		FSimpleCoupon voucher = this.fSimpleCouponService.get(vid);
		BigDecimal voucherActualMoney = null; // 代金券使用金额
		if(this.fSimpleCouponService.isCouponValid(voucher,DataConstant.BUSINESSTYPE_APPLY_COUPONS, businessType)) {
			voucherActualMoney = new BigDecimal(voucher.getMoney()+"");
			payable = payable.subtract(voucherActualMoney);
			if(payable.compareTo(BigDecimal.ZERO) < DataConstant.ZERO ) {
				voucherActualMoney = voucherActualMoney.add(payable);
				payable = BigDecimal.ZERO;
			}
			traderBond = traderBond.subtract(voucherActualMoney);
			if(traderBond.compareTo(BigDecimal.ZERO) < DataConstant.ZERO ) {
				traderBond = BigDecimal.ZERO;
			}
		}
		//如果用户余额不足
		if (avlBal.compareTo(payable) < DataConstant.ZERO ) {
			return new ApiResult(false,ResultStatusConstant.HandleFTrade.USER_INSUFFICIENT_BALANCE,"user.insufficient.balance.");	
		}
		// 插入方案数据
		FSimpleFtseUserTrade fSimpleFtseUserTrade = new FSimpleFtseUserTrade();
		fSimpleFtseUserTrade.setUid(uid);
		fSimpleFtseUserTrade.setTraderTotal(fSimpleConfig.getTraderMoney());
		fSimpleFtseUserTrade.setTraderBond(traderBond);
		fSimpleFtseUserTrade.setTranLever(tranLever);
		fSimpleFtseUserTrade.setLineLoss(fSimpleConfig.getLineLoss());
		fSimpleFtseUserTrade.setFeeManage(fSimpleConfig.getFeeManage());
		fSimpleFtseUserTrade.setTranFees(totalTranFees);
		//设置来源
		fSimpleFtseUserTrade.setSource(2);
		//审核中
		fSimpleFtseUserTrade.setStateType(1);
		fSimpleFtseUserTrade.setBusinessType(businessType);  //国际原油
		//入金金额(美元)
		BigDecimal goldenMoney = new BigDecimal(DataConstant.ZERO).add(fSimpleConfig.getGoldenMoney());
		fSimpleFtseUserTrade.setGoldenMoney(goldenMoney);
		// 设置代金券相关信息
		if(this.fSimpleCouponService.isCouponValid(voucher,DataConstant.BUSINESSTYPE_APPLY_COUPONS, businessType)) {
			fSimpleFtseUserTrade.setVoucherId(voucher.getId());
			fSimpleFtseUserTrade.setVoucherMoney(voucher.getMoney());
			fSimpleFtseUserTrade.setVoucherActualMoney(voucherActualMoney);
			this.fSimpleFtseUserTradeService.executePayable(fSimpleFtseUserTrade, voucher, wuser.getMobile(), payable,BusinessTypeEnum.getBussinessFundRemark(businessType),businessType);
		} else {
			this.fSimpleFtseUserTradeService.executePayable(fSimpleFtseUserTrade, wuser.getMobile(), payable,BusinessTypeEnum.getBussinessFundRemark(businessType),businessType);
		}
		messagePromptService.sendMessage(PromptTypes.isFutures, wuser.getMobile());
		return new ApiResult(true,ResultStatusConstant.SUCCESS,"handle.Successful",fSimpleFtseUserTrade);		
	}
	
	/**
	 * 国际综合操盘确认支付 
	 * @param businessType
	 * @param traderBond
	 * @param voucherId
	 * @param request
	 * @param response
	 * @param attr
	 * @return
	 * @throws Exception
	 */
	public synchronized ApiResult multipleHandle(int businessType,BigDecimal traderBond,String voucherId,
			HttpServletRequest request) throws Exception{
		
		List<OutDiskPrice> outDiskPrice = outDiskPriceService.findAllOutDiskPrice();
		List<OutDiskParameters> outDiskParametersList = outDiskParametersService.findByTraderBond(traderBond);
		if(CollectionUtils.isEmpty(outDiskParametersList) || CollectionUtils.isEmpty(outDiskPrice)){
			return new ApiResult(false,ResultStatusConstant.FAIL,"not.fund.config.params.");	
		}
		OutDiskParameters outDiskParameters= outDiskParametersList.get(DataConstant.ZERO);
		//获取用户信息
		String uid = AuthUtils.getCacheUser(request).getUid();  
		WUser wuser = wUserService.get(uid);		
		//应付金额
		BigDecimal payable = new BigDecimal(DataConstant.ZERO).add(traderBond);
        //用户余额
		BigDecimal avlBal = new BigDecimal(wuser.getAvlBal().toString());
		// 验证代金券
		FSimpleCoupon voucher = this.fSimpleCouponService.get(voucherId);
		BigDecimal voucherActualMoney = null; // 代金券使用金额
		if(this.fSimpleCouponService.isCouponValid(voucher,DataConstant.BUSINESSTYPE_APPLY_COUPONS, businessType)) {
			voucherActualMoney = new BigDecimal(voucher.getMoney()+"");
			//支付金额 = 保证金  - 优惠券
			payable = payable.subtract(voucherActualMoney);
			if(payable.compareTo(BigDecimal.ZERO) < 0) {
				voucherActualMoney = voucherActualMoney.add(payable);
				payable = BigDecimal.ZERO;
			}
			traderBond = traderBond.subtract(voucherActualMoney);
			if(traderBond.compareTo(BigDecimal.ZERO) < DataConstant.ZERO ) {
				traderBond = BigDecimal.ZERO;
			}
		}
		//如果用户余额不足
		if (avlBal.compareTo(payable) < DataConstant.ZERO ) {
			return new ApiResult(false,ResultStatusConstant.HandleFTrade.USER_INSUFFICIENT_BALANCE,"user.insufficient.balance.");	
		}		
		// 插入方案数据
		FSimpleFtseUserTrade fSimpleFtseUserTrade = new FSimpleFtseUserTrade();
		fSimpleFtseUserTrade.setUid(uid);
		fSimpleFtseUserTrade.setTraderTotal(outDiskParameters.getTraderTotal());//总操盘资金($)
		fSimpleFtseUserTrade.setTraderBond(traderBond);  //总保证金额
		fSimpleFtseUserTrade.setLineLoss(outDiskParameters.getLineLoss()); //亏损平仓线($)
		fSimpleFtseUserTrade.setFeeManage(new BigDecimal(DataConstant.ZERO));  //管理费
		fSimpleFtseUserTrade.setTranFees(outDiskPrice.get(0).getPrice());  //交易手续费
		
		fSimpleFtseUserTrade.setCrudeTranFees(outDiskPrice.get(1).getPrice());  //国际原油交易手续费
		fSimpleFtseUserTrade.setHsiTranFees(outDiskPrice.get(2).getPrice());  //恒生指数交易手续费
		//设置来源
		fSimpleFtseUserTrade.setSource(2);  //平台来源   1：网站平台    2：APP平台
		//设置新增品种价格
		fSimpleFtseUserTrade.setMdTranFees(outDiskPrice.get(3).getPrice());
		fSimpleFtseUserTrade.setMnTranFees(outDiskPrice.get(4).getPrice());
		fSimpleFtseUserTrade.setMbTranFees(outDiskPrice.get(5).getPrice());
		fSimpleFtseUserTrade.setDaxTranFees(outDiskPrice.get(6).getPrice());
		fSimpleFtseUserTrade.setNikkeiTranFees(outDiskPrice.get(7).getPrice());
		fSimpleFtseUserTrade.setLhsiTranFees(outDiskPrice.get(8).getPrice());
		fSimpleFtseUserTrade.setAgTranFees(outDiskPrice.get(9).getPrice());
		fSimpleFtseUserTrade.sethSTranFees(outDiskPrice.get(10).getPrice());
		fSimpleFtseUserTrade.setxHSTranFees(outDiskPrice.get(11).getPrice());
		fSimpleFtseUserTrade.setAmeCTranFees(outDiskPrice.get(12).getPrice());
		fSimpleFtseUserTrade.setAmeSTranFees(outDiskPrice.get(13).getPrice());
		fSimpleFtseUserTrade.setSmallCTranFees(outDiskPrice.get(14).getPrice());
		fSimpleFtseUserTrade.setDaxMinTranFees(outDiskPrice.get(15).getPrice());
		fSimpleFtseUserTrade.setNaturalGasFess(outDiskPrice.get(16).getPrice());
		//审核中
		fSimpleFtseUserTrade.setStateType(1); //状态【1.开户中、2.申请结算、3.待结算、4.操盘中  5.审核不通过 、6.已结算】
		fSimpleFtseUserTrade.setBusinessType(businessType); 
		fSimpleFtseUserTrade.setGoldenMoney(outDiskParameters.getGoldenMoney());  //入金金额(美元)
		// 设置代金券相关信息
		if(this.fSimpleCouponService.isCouponValid(voucher,DataConstant.BUSINESSTYPE_APPLY_COUPONS, businessType)) {
			fSimpleFtseUserTrade.setVoucherId(voucher.getId());
			fSimpleFtseUserTrade.setVoucherMoney(voucher.getMoney());  //代金券面额
			fSimpleFtseUserTrade.setVoucherActualMoney(voucherActualMoney);  //代金券使用金额
			this.fSimpleFtseUserTradeService.executePayable(fSimpleFtseUserTrade, voucher, wuser.getMobile(), payable,BusinessTypeEnum.getBussinessFundRemark(businessType),businessType);
		} else {
			this.fSimpleFtseUserTradeService.executePayable(fSimpleFtseUserTrade, wuser.getMobile(), payable,BusinessTypeEnum.getBussinessFundRemark(businessType),businessType);
		}
		messagePromptService.sendMessage(PromptTypes.isFutures, wuser.getMobile());
		return new ApiResult(true,ResultStatusConstant.SUCCESS,"handle.Successful",fSimpleFtseUserTrade);		
	}
}
