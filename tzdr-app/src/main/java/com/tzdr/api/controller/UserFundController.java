package com.tzdr.api.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSONObject;
import com.tzdr.api.constants.ResultStatusConstant;
import com.tzdr.api.support.ApiResult;
import com.tzdr.api.util.AuthUtils;
import com.tzdr.business.app.service.FundService;
import com.tzdr.business.service.wuser.WUserService;
import com.tzdr.domain.app.vo.UserFundVo;
import com.tzdr.domain.web.entity.WUser;
import jodd.util.ObjectUtil;


/**  
 * @Title: UserFundController.java     
 * @Description: 用户资金明细信息管理类    
 * @author： WangPinQun 
 * @E-mail：wangpinqun@tzdr.com
 * @company： 上海信闳投资管理有限公司重庆分公司
 * @address：重庆市渝北区黄山大道中段70号两江新界3栋13楼
 * @date： 2016年3月24日 上午11:03:07    
 * @version： V1.0  
 */
@Controller
@RequestMapping("/user/fund")
public class UserFundController {

	public static final Logger logger = LoggerFactory.getLogger(UserFundController.class);

	@Autowired
	private FundService fundService;
	
	@Autowired
	private WUserService  wUserService;

	/**
	 * @Title: list    
	 * @Description: 获取用户资金明细列表信息 
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
        
		//求所有的收入记录
		List<Map<String, Object>> indataLisMap = fundService.getFundbytype(uid, new Integer[]{1,3,13,15,19,16,21,23,24,25,26});
		
		Integer incomeNum = 0;  //收入笔数
		BigDecimal incomeMoney = new BigDecimal("0.00");  //收入总金额
		
		if(!CollectionUtils.isEmpty(indataLisMap)){
			for (Map<String, Object> map : indataLisMap) {
				if(map != null){
					incomeNum = map.get("count") == null ? 0 : new Integer(map.get("count").toString());
					incomeMoney = map.get("summoney") == null ? new BigDecimal("0.00") : new BigDecimal(map.get("summoney").toString());
				}
			}
		}
		
		//求所有的支出记录
		List<Map<String, Object>> outdataLisMap = fundService.getFundbytype(uid, new Integer[]{2,4,10,11,12,17,18,20,27});
		
		Integer outlayNum = 0;  //支出笔数
		BigDecimal outlayMoney = new BigDecimal("0.00");  //支出总金额
		
		if(!CollectionUtils.isEmpty(outdataLisMap)){
			for (Map<String, Object> map : outdataLisMap) {
				if(map != null){
					outlayNum = map.get("count") == null ? 0 : new Integer(map.get("count").toString());
					outlayMoney = map.get("summoney") == null ? new BigDecimal("0.00") : new BigDecimal(map.get("summoney").toString());
				}
			}
		}
		
		List<UserFundVo> dataList = fundService.findUserFundVos(uid);  //获取用户资金明细
        
		WUser wuser = wUserService.get(uid);  //获取用户信息
		
		BigDecimal avlBal = new BigDecimal(wuser.getAvlBal().toString());  //获取用户余额
		
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("incomeNum", incomeNum);
		dataMap.put("incomeMoney", incomeMoney);
		dataMap.put("outlayNum", outlayNum);
		dataMap.put("outlayMoney", outlayMoney);
		dataMap.put("balance", avlBal);
		dataMap.put("fundList", dataList);

		return new ApiResult(true,ResultStatusConstant.SUCCESS,"success",dataMap);
	}
}
