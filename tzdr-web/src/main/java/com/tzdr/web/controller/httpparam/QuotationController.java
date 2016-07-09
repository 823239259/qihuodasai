package com.tzdr.web.controller.httpparam;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tzdr.web.constants.HttpUrlConstants;
import com.tzdr.web.utils.HttpRequest;

@Controller
@RequestMapping("/Quotation")
public class QuotationController {
	@RequestMapping(value = "/doGetQk")
	@ResponseBody
	public JSONObject doGetQuotationK(HttpServletRequest request,@RequestParam("commodity") String commodity,@RequestParam("contract") String contract){
		JSONObject jsonParam = new JSONObject();
		jsonParam.put("commodity", commodity);
		jsonParam.put("contract", contract);
		String resultData =  HttpRequest.httpPost(HttpUrlConstants.QUOTATION_QRYHISTORY, jsonParam.toJSONString() );
		if(resultData.length() > 0){
			JSONObject json = JSONObject.parseObject(resultData);
			String data = json.getString("data");
			if(data != null && data.length() > 0){
				return json;
			}
		}
		JSONObject resultJson = new JSONObject();
		resultJson.put("success", true);
		return resultJson;
	}
	@RequestMapping(value = "/doGetCommodity")
	@ResponseBody
	public JSONObject doGetCommodity(){
		JSONObject resultJson = new JSONObject();
		String resultData =  HttpRequest.httpPost(HttpUrlConstants.QUOTATION_QRYCOMMODITY,null);
		if(resultData.length() > 0){
			JSONObject json = JSONObject.parseObject(resultData);
			String data = json.getString("data");
			if(data != null && data.length() > 0){
				JSONArray reqArr = new JSONArray();
				JSONArray array = JSONArray.parseArray(data);
				for(int i = 0 ; i < array.size() ; i++){
					JSONArray arr = new JSONArray();
					JSONObject jObject = array.getJSONObject(i);
					String jCommodity = jObject.getString("CommodityNo");
					arr.add(jObject);
					for(int j = 0 ; j < array.size() ; j++){
						JSONObject obj = array.getJSONObject(j);
						String commodity = obj.getString("CommodityNo");
						if(jCommodity.equals(commodity)){
							arr.add(obj);
							array.remove(j);
						}
					}
					if(arr.size() > 0){
						JSONObject initJsonObject = arr.getJSONObject(0);
						int minContract = initJsonObject.getIntValue("ContractNo");
						for (int k = 0; k < arr.size(); k++) {
							JSONObject jsonObject = arr.getJSONObject(k);
							int contract = jsonObject.getIntValue("ContractNo");
							if(contract < minContract) {
								minContract = contract;
								initJsonObject = jsonObject;
							}
						}
						reqArr.add(initJsonObject);
					}
				}
				JSONArray jsonArray = new JSONArray();
				for (int i = 0; i < reqArr.size(); i++) {
					JSONObject jsonParam = new JSONObject();
					JSONObject jObject = reqArr.getJSONObject(i);
					String contractNo = jObject.getString("ContractNo");
					jsonParam.put("exchange", jObject.getString("ExchangeNo"));
					jsonParam.put("contract", contractNo);
					jsonParam.put("commodity", jObject.getString("CommodityNo"));
					String respString = HttpRequest.httpPost(HttpUrlConstants.QUOTATION_LAST,jsonParam.toJSONString());
					JSONObject rJson = JSONObject.parseObject(respString);
					String rData = rJson.getString("data");
					if(rData != null && rData.length() > 0){
						rJson.put("contract", contractNo);
						rJson.put("commodityName", this.validationCommodity(jObject.getString("CommodityNo")));
						jsonArray.add(rJson);
					}
				}
				resultJson.put("data", jsonArray);
			}
		}
		resultJson.put("success", true);
		return resultJson;
	}
	private String validationCommodity(String commodityNo){
		String commodityName = null;
		switch (commodityNo) {
		case "CL":
			commodityName = "美原油";
			break;
		case "CN":
			commodityName = "富时A50";
			break;
		case "HSI":
			commodityName = "恒生指数";
			break;
		case "NYMEX":
			commodityName = "芝加哥商业交易所" ;
			break;
		case "SGX":
			commodityName = "新加坡交易所";
			break;
		case "HKEX":
			commodityName = "香港期货交易所";
			break;
		default:
			break;
		}
		return commodityName;
	}
}
