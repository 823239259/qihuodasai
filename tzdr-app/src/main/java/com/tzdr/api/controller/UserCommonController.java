package com.tzdr.api.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jodd.util.StringUtil;

import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tzdr.api.constants.DataConstant;
import com.tzdr.api.constants.ResultStatusConstant;
import com.tzdr.api.support.ApiResult;
import com.tzdr.api.util.AuthUtils;
import com.tzdr.api.util.RequestUtils;
import com.tzdr.business.service.datamap.DataMapService;
import com.tzdr.business.service.future.FSimpleCouponService;
import com.tzdr.business.service.userTrade.FSimpleParitiesService;
import com.tzdr.business.service.wuser.WUserService;
import com.tzdr.domain.cache.CacheManager;
import com.tzdr.domain.cache.DataDicKeyConstants;
import com.tzdr.domain.constants.Constant;
import com.tzdr.domain.web.entity.FSimpleParities;
import com.tzdr.domain.web.entity.WUser;

/**  
 * @Title: CommController.java     
 * @Description: 用户公共接口业务信息管理累  
 * @author： WangPinQun 
 * @E-mail：wangpinqun@tzdr.com
 * @company： 上海信闳投资管理有限公司重庆分公司
 * @address：重庆市渝北区黄山大道中段70号两江新界3栋13楼
 * @date： 2016年3月23日 下午1:37:58    
 * @version： V1.0  
 */
@Controller
@RequestMapping("/user")
public class UserCommonController {
	
	public static final Logger logger = LoggerFactory.getLogger(UserCommonController.class);
	
	@Autowired
	private WUserService  wUserService;
	
	@Autowired
	private DataMapService dataMapService;
	
	@Autowired
	private FSimpleParitiesService fSimpleParitiesService;
	
	@Autowired
	private FSimpleCouponService fSimpleCouponService;
	
	/**
	* @Title: getbalancerate    
	* @Description: 获取用户余额以及当前汇率信息
	* @param businessType 业务类型     如： 1：股指期货追加保证金查询；2：股指期货终结方案查询；3：网银充值查询；
	* @param modelMap
	* @param request
	* @param response
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/getbalancerate" , method = RequestMethod.POST)
	@ResponseBody
	public ApiResult getbalancerate(Integer businessType,Integer couponBusinessType ,ModelMap modelMap,HttpServletRequest request,HttpServletResponse response) throws Exception{
	
		String uid = AuthUtils.getCacheUser(request).getUid();  //获取用户编号
		
		WUser wuser =  wUserService.get(uid);  //获取用户信息
		
		Map<String, Object> dataMap = new HashMap<String, Object>();

		dataMap.put("balance", wuser.getAvlBal());   //当前余额
		dataMap.put("username", wuser.getUserVerified().getTname());   //用户实名
		dataMap.put("isCertification",RequestUtils.isCertification(wuser));
		
		if (DataConstant.BUSINESSTYPE_WITHDRAW==businessType){
			// 获取提现手续费
			Double drawHandleFee = DataConstant.DEFAULT_HANDLE_FEE;
			int withdrawSetting = dataMapService.getWithDrawSetting();
			String handleFeeStr = null;
			if (Constant.PaymentChannel.UM_PAY == withdrawSetting){
				//提现手续费
			    handleFeeStr = CacheManager.getDataMapByKey(DataDicKeyConstants.WITHDRAW_HANDLE_FEE,DataConstant.MAX_5000_HANDLE_FEE_CONFIG);
			}
			if (Constant.PaymentChannel.BB_PAY == withdrawSetting){
				//提现手续费
				handleFeeStr = CacheManager.getDataMapByKey(DataDicKeyConstants.WITHDRAW_HANDLE_FEE,DataDicKeyConstants.BB_FEE);
			}
			if (StringUtil.isNotBlank(handleFeeStr)){
				drawHandleFee = NumberUtils.toDouble(handleFeeStr);
			}
			dataMap.put("drawHandleFee",drawHandleFee);	
			// 是否设置提现密码
			dataMap.put("isSetDrawPwd",RequestUtils.isSetDrawPwd(wuser));
			
		}
		
		if(businessType != null && businessType == DataConstant.BUSINESSTYPE_ADDBOND){
			String rate = dataMapService.findByTypeKey("exchangeRate").get(0).getValueKey();
			dataMap.put("rate", new BigDecimal(rate)); //当前固定汇率
		}else if(businessType != null && businessType == DataConstant.BUSINESSTYPE_ENDTRADE && couponBusinessType != null){
			FSimpleParities fSimpleParities = fSimpleParitiesService.getFSimpleParities(DataConstant.PARITIESTYPE);
			dataMap.put("rate", fSimpleParities.getParities());
			// 折扣券
			List<Map<String, Object>> discounts = new ArrayList<>();
			discounts = this.fSimpleCouponService.queryCouponByUserId(uid, DataConstant.BUSINESSTYPE_ENDTRADE_COUPONS, couponBusinessType);
			dataMap.put("coupons", discounts);
		}
		
		return new ApiResult(true,ResultStatusConstant.SUCCESS,null,dataMap);
	}
}
