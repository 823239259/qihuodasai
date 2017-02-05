package com.tzdr.common.api.bbpay;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bebepay.component.comm.RandomUtil;
import com.bebepay.component.encrypt.AES;
import com.bebepay.component.encrypt.EncryUtil;
import com.bebepay.component.encrypt.RSA;
import com.bebepay.component.httpclient.HttpClient4Util;
import com.bebepay.component.httpclient.HttpParameter;
import com.bebepay.component.httpclient.HttpResp;
import com.bebepay.component.model.RespondJson;
import com.bebepay.component.util.FundsSettleUtil;
import com.bebepay.component.util.PayUtil;
import com.bebepay.component.util.ReturnOrderQueryUtil;
import com.tzdr.common.api.bbpay.util.BbConfigUtil;
import com.tzdr.common.api.bbpay.vo.PayParamsObject;


/**
 * 币币支付接口
 * @zhouchen
 * 2015年10月13日
 */
public class Bibipay {
	public static final Logger logger = LoggerFactory.getLogger(Bibipay.class);
	
	/**
	 * 结算时返回值 转换jsonArray size
	 */
	private static final int SETTLEMONEY_RETURNDATA_SIZE = 1;

	/**
	 * 操作成功状态
	 */
	public static final int HANDLE_SUCCESS_STATUS=1;
	
	/**
	 * 用户提现付款成功
	 */
	public static final String WITHDRAW_PAY_SUCCESS="01";
	
	/**
	 * 用户提现付款失败
	 */
	public static final String  WITHDRAW_PAY_FAIL="02";
	
	
	private static Bibipay instance;
	public static synchronized Bibipay getInstance() {
		if (instance == null) {
			instance = new Bibipay();
		}
		return instance;
	}
	// 加密方式
	private String bebeEncry;
	// 结算类型
	private String  settleType;
	// 商户账户编号
	private String merchantaccount;
	//币币支付交易订单查询请求地址
	private String queryInUrl ;
	// 从配置文件读取币币的公钥
	private String bbPublicKey ;
	// 从配置文件读取商户自己的私钥
	private String merchantPrivateKey;
	private String areturl;
	private String sreturl;
	
	public Bibipay() {
		// 商户账户编号
		merchantaccount = BbConfigUtil.getContext("merchantAccount");
		// 从配置文件读取币币的公钥
		bbPublicKey =BbConfigUtil.getContext("bbPublicKey");
		// 从配置文件读取商户自己的私钥
		merchantPrivateKey = BbConfigUtil.getContext("merchantPrivateKey");
		areturl =BbConfigUtil.getContext("areturl");
		sreturl =BbConfigUtil.getContext("sreturl");
		queryInUrl= BbConfigUtil.getContext("queryInUrl");
		//结算类型
		settleType = BbConfigUtil.getContext("settleType");
		bebeEncry = BbConfigUtil.getContext("bebe.encry");
	}

	/**
	 * 支付
	 * @param sendObj
	 * @return
	 * @throws Exception
	 * @date 2015年6月9日
	 * @author zhangjun
	 */
	public Map<String,Object> pcpay(PayParamsObject sendObj) throws Exception {
		Map<String,Object> dataMap=new HashMap<String,Object>();
		TreeMap<String, Object> map = new TreeMap<String, Object>();
		map.put("order",sendObj.getOrder());
		map.put("transtime", sendObj.getTranstime());
		map.put("amount", sendObj.getAmount());
		map.put("productcategory", sendObj.getProductcategory());
		map.put("productname", sendObj.getProductname());
		map.put("productdesc", sendObj.getProductdesc());
		map.put("productprice", sendObj.getProductprice());
		map.put("productcount", sendObj.getProductcount());
		map.put("merrmk", sendObj.getMerrmk());
		map.put("userua", sendObj.getUserua());
		map.put("userip", sendObj.getUserip());
		map.put("areturl", areturl);
		map.put("sreturl", sreturl);
		map.put("pnc", sendObj.getPnc());
		//加密方式"0"表示RSA，"1"表示MD5
		map.put("encry", bebeEncry);
		
		PayUtil bbUtil = new PayUtil();
		//调用币币提供的工具包里面的方法，进行组装，加签，加密操作。
		HashMap<String, String> resultMap = bbUtil.assemble(map);
		String data = resultMap.get("data");
		String merchantaccount = resultMap.get("merchantaccount");
		String payUrl = resultMap.get("payUrl");
		String encryptkey = resultMap.get("encryptkey");
		
		dataMap.put("mobilePayUrl", payUrl);
		dataMap.put("merchantaccount", merchantaccount);
		dataMap.put("data", data);
		dataMap.put("encryptkey", encryptkey);
	
		logger.info("encryptkey---------->"+encryptkey);
		logger.info("data----------->"+data);
		logger.info("payUrl----------->"+payUrl);
		logger.info("merchantaccount----------->"+merchantaccount);
		return dataMap;
	}
	
	/**
	 * 支付
	 * @param sendObj
	 * @return
	 * @throws Exception
	 * @date 2015年6月9日
	 * @author zhangjun
	 */
	public Map<String,Object> pcpay(PayParamsObject sendObj,String app_sreturl) throws Exception {
		Map<String,Object> dataMap=new HashMap<String,Object>();
		TreeMap<String, Object> map = new TreeMap<String, Object>();
		map.put("order",sendObj.getOrder());
		map.put("transtime", sendObj.getTranstime());
		map.put("amount", sendObj.getAmount());
		map.put("productcategory", sendObj.getProductcategory());
		map.put("productname", sendObj.getProductname());
		map.put("productdesc", sendObj.getProductdesc());
		map.put("productprice", sendObj.getProductprice());
		map.put("productcount", sendObj.getProductcount());
		map.put("merrmk", sendObj.getMerrmk());
		map.put("userua", sendObj.getUserua());
		map.put("userip", sendObj.getUserip());
		map.put("areturl", areturl);
		map.put("sreturl", app_sreturl);
		map.put("pnc", sendObj.getPnc());
		//加密方式"0"表示RSA，"1"表示MD5
		map.put("encry", bebeEncry);
		
		PayUtil bbUtil = new PayUtil();
		//调用币币提供的工具包里面的方法，进行组装，加签，加密操作。
		HashMap<String, String> resultMap = bbUtil.assemble(map);
		String data = resultMap.get("data");
		String merchantaccount = resultMap.get("merchantaccount");
		String payUrl = resultMap.get("payUrl");
		String encryptkey = resultMap.get("encryptkey");
		
		dataMap.put("mobilePayUrl", payUrl);
		dataMap.put("merchantaccount", merchantaccount);
		dataMap.put("data", data);
		dataMap.put("encryptkey", encryptkey);
	
		logger.info("encryptkey---------->"+encryptkey);
		logger.info("data----------->"+data);
		logger.info("payUrl----------->"+payUrl);
		logger.info("merchantaccount----------->"+merchantaccount);
		return dataMap;
	}
	
	/**
	 * 币币支付资金结算--即取款接口
	 * @param smObj
	 * @return
	 * @throws Exception
	 */
	public JSONObject settleMoney(Integer amount,String bankConfig) throws Exception{
		/***********资金结算  **********/
		Map<String, Object> map = new HashMap<String, Object>();
		//结算类型 1201：自助结算，1302：委托结算(可以不传金额)
		map.put("settle_type",settleType);
		//结算金额 单位（分）
		map.put("amount",amount);
		/**
		 * 委托结算 
		 * 格式：银行支行全称~|~银行卡号~|~开户人~|~结算金额~|~1：私人，2：公司~|~商户订单号
		 * 格式：105653013127~|~6212263100019044747~|~周不以林~|~5~|~1~|~1281305441996801
		 */
		map.put("bank_config",bankConfig);
		//自助结算
		//map.put("bank_config", "1281305441996801");
		//加密方式"0"表示RSA，"1"表示MD5方式
		map.put("encry",bebeEncry);
		JSONObject resultJson = new JSONObject();
		FundsSettleUtil util = new FundsSettleUtil();
		TreeMap<String, String> backMap = util.fundsSettle(map);
		logger.info("------------币币支付委托结算结果返回："+backMap+"--------------------");
		if (CollectionUtils.isEmpty(backMap)){
			logger.info("币币支付委托结算返回结果为空，参数："+bankConfig);
			resultJson.put("status", 0);
			resultJson.put("msg", "币币支付委托结算返回结果为空");
			return resultJson;
		}
		String returnData = backMap.get("returnData");
		if (CollectionUtils.isEmpty(backMap)){
			logger.info("币币支付委托结算返回结果:"+returnData+"，参数："+bankConfig);
			resultJson.put("status", 0);
			resultJson.put("msg", "币币支付委托结算返回结果为空,"+returnData);
			return resultJson;
		}
		JSONArray jsonArray = JSONObject.parseArray(returnData);
		if (jsonArray.isEmpty() || SETTLEMONEY_RETURNDATA_SIZE !=jsonArray.size()){
			logger.info("币币支付委托结算返回结果:"+returnData+"，参数："+bankConfig);
			resultJson.put("status", 0);
			resultJson.put("msg", "币币支付委托结算返回结果异常,"+returnData);
			return resultJson;
		}
		logger.info("币币支付提现接口返回结果："+returnData);
		return (JSONObject) jsonArray.get(0);
	}
	
	
	/**
	 * 根据币币支付订单id查询 订单出款状态
	 * @param bbOrderId
	 * @throws Exception 
	 */
	public TreeMap<String, String> queryOutOrder(String bbOrderId) throws Exception{
		/***********出款交易订单查询 **********/
		Map<String, Object> map = new HashMap<String, Object>();
		//商户订单号
		//map.put("orderid", "12345677654321");
		//币币订单号
		map.put("bborderid",bbOrderId);
		//加密方式"0"表示RSA，"1"表示MD5
		map.put("encry",bebeEncry);
		ReturnOrderQueryUtil util = new ReturnOrderQueryUtil();
		TreeMap<String, String> backMap = util.returnOrderQuery(map);
		logger.info("币币提现出款订单查询结果："+backMap);
		if (CollectionUtils.isEmpty(backMap)){
			return null;
		}
		/**
		 * 状态值返回： 系统业务只针对01、02进行处理
		 * 00：代付款
		 * 01：已付款
		 * 02：付款失败
		 * 03：付款失败申请中
		 * 04：拒付
		 * 05：待复核
		 * 06：待制单
		 */
		return backMap;

	}

	/**
	 * 进账订单查询
	 */
	public String queryOrder(String bborderid,String orderid) throws Exception {
		TreeMap<String, Object> map = new TreeMap<String, Object>();
		map.put("orderid", orderid);
		map.put("bborderid",bborderid);
		String data;
		String encryptkey;
		// 生成RSA签名
		String sign = EncryUtil.handleRSA(map, merchantPrivateKey);
		map.put("sign", sign);
		//生成AES密匙
		String merchantAesKey = RandomUtil.getRandom(16);
		// 生成data密文
		String info = JSON.toJSONString(map);
		data = AES.encryptToBase64(info, merchantAesKey);
		// 使用RSA算法将商户自己随机生成的AESkey加密
		encryptkey = RSA.encrypt(merchantAesKey, bbPublicKey);
		//发送请求得到结果
		HttpClient4Util util = new HttpClient4Util(30000, false);
		HttpParameter httpParameter = new HttpParameter();
		httpParameter.add("merchantaccount", merchantaccount);
		httpParameter.add("data", data);
		httpParameter.add("encryptkey", encryptkey);
		HttpResp httpRes = util.doPost(this.queryInUrl, httpParameter, "utf-8");
		String contentStr = httpRes.getText("utf-8");
		util.shutdown();
		String retStr = "";
		//商户进行验签解密
		if (contentStr.indexOf("error_code") < 0) {
			try {
				// 将返回的字符串转为json对象，并通过解密获取明文处理结果
				RespondJson rj = JSONObject.parseObject(contentStr,RespondJson.class);
				String retData = rj.getData();
				String retEncryptkey = rj.getEncryptkey();
				// 对币币返回的结果进行验签
				TreeMap<String, String> retmap = EncryUtil.checkDecryptAndSign(retData,retEncryptkey, bbPublicKey, merchantPrivateKey);
				if (null != retmap) {
					// 验签通过
					String aeskey = RSA.decrypt(retEncryptkey,merchantPrivateKey);
					//得到明文、做进一步业务处理
					retStr = AES.decryptFromBase64(retData,aeskey);
				} else {
					retStr = "验签失败!";
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			retStr = contentStr;
		}
		return retStr;
	}
	public static void main(String[] args) throws Exception {
		Bibipay.getInstance().queryOutOrder("1431195231aa711233");
	}
}

