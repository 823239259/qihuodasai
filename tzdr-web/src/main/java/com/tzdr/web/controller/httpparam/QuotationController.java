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
	public JSONObject doGetQuotationK(HttpServletRequest request,@RequestParam("commodity") String commodity,@RequestParam("contract") String contract,@RequestParam(value = "beginTime",required = false)String beginTime){
		JSONObject jsonParam = new JSONObject();
		jsonParam.put("commodity", commodity);
		jsonParam.put("contract", contract);
		jsonParam.put("beginTime", beginTime);
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
		JSONArray jsonArray = new JSONArray();
		//请求品种
		String resultDataCommodity = HttpRequest.httpPost(HttpUrlConstants.QUOTATION_QRYCOMMODITY, null);
		System.out.println(resultDataCommodity);
		if(resultDataCommodity.length() > 0){
			JSONObject resultCommodityJson = JSONObject.parseObject(resultDataCommodity);
			String commodityData = resultCommodityJson.getString("data");
			if(commodityData != null && commodityData.length() > 0 ){
				JSONArray commodityArray = JSONArray.parseArray(commodityData);
				for (int i = 0 ; i < commodityArray.size(); i++) {
					JSONObject contractParaJson = new JSONObject();
					JSONObject json = commodityArray.getJSONObject(i);
					String doSize = json.getString("DotSize");
					contractParaJson.put("exchange", json.getString("ExchangeNo"));
					contractParaJson.put("commodity", json.getString("CommodityNo"));
					//请求合约
					String resultDataContract = HttpRequest.httpPost(HttpUrlConstants.QUOTATION_QRYCOMTRACT, contractParaJson.toJSONString());
					if(resultDataContract.length() > 0){
						JSONObject resultContractJson = JSONObject.parseObject(resultDataContract);
						String contractData = resultContractJson.getString("data");
						if(contractData != null && contractData.length() > 0 ){
							JSONArray contractArray = JSONArray.parseArray(contractData);
							JSONArray reqArr = new JSONArray();
							for(int j = 0 ; j < contractArray.size() ; j++){
								JSONArray arr = new JSONArray();
								JSONObject jObject = contractArray.getJSONObject(j);
								String jCommodity = jObject.getString("CommodityNo");
								arr.add(jObject);
								for(int k = 0 ; k < contractArray.size() ; k++){
									JSONObject obj = contractArray.getJSONObject(k);
									String commodity = obj.getString("CommodityNo");
									if(jCommodity.equals(commodity)){
										arr.add(obj);
										contractArray.remove(k);
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
									initJsonObject.put("doSize", doSize);
									reqArr.add(initJsonObject);
								}
							}
							
							for (int j = reqArr.size() - 1; j >= 0; j--) {
								JSONObject jsonParam = new JSONObject();
								JSONObject jObject = reqArr.getJSONObject(j);
								String contractNo = jObject.getString("ContractNo");
								jsonParam.put("exchange", jObject.getString("ExchangeNo"));
								jsonParam.put("contract", contractNo);
								jsonParam.put("commodity", jObject.getString("CommodityNo"));
								String respString = HttpRequest.httpPost(HttpUrlConstants.QUOTATION_LAST,jsonParam.toJSONString());
								JSONObject rJson = JSONObject.parseObject(respString);
								String rData = rJson.getString("data");
								if(rData != null && rData.length() > 0){
									rJson.put("DotSize", jObject.getString("doSize"));
									rJson.put("contract", contractNo);
									rJson.put("commodityName", this.validationCommodity(jObject.getString("CommodityNo")));
									jsonArray.add(rJson);
								}
							}
							
						}
					}
				}
			}
		}
		/*JSONObject resultJson = new JSONObject();
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
		resultJson.put("success", true);*/
		resultJson.put("data", jsonArray);
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
		case "GC":
			commodityName = "纽约金";
			break;
		case "DAX":
			commodityName = "德国DAX指数";
			break;
		default:
			break;
		}
		return commodityName;
	}
}
