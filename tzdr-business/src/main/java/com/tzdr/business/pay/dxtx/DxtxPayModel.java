package com.tzdr.business.pay.dxtx;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import com.tzdr.common.utils.HttpClientUtils;

/**
 * 盾行天下的支付请求数据model
 * @author username
 *
 */
@SuppressWarnings("serial")
public class DxtxPayModel implements Serializable{
	/**
	 * 商户订单号
	 */
	private String oBizcode;
	/**
	 * 应用key
	 */
	private String aAppkey;
	/**
	 * 终端唯一标示（ip地址MD5加密）
	 */
	private String oTermKey;
	/**
	 * 通知地址（不传就已后台配置为准）
	 */
	private String oAddress;
	/**
	 * H5同步通知地址（不传就已后台配置为准）
	 */
	private String oShowaddress;
	/**
	 * 支付类型(1:支付宝，2：微信，3：银联，4:微信公众号，5:微信APP,6:微信扫码)，H5收银台模式以商户后台支付配置为准。
	 */
	private Integer oPaymodeId;
	/**
	 * 商品id，在后台获取
	 */
	private Integer oGoodsId;
	/**
	 * 商品名称（不传就已后台配置为准）
	 */
	private String oGoodsName;
	/**
	 * 商品价格 单位元
	 */
	private Double oPrice;
	/**
	 * 商户私有信息,放置需要回传的信息(utf-8)
	 */
	private String oPrivateinfo;
	public String getoBizcode() {
		return oBizcode;
	}
	public void setoBizcode(String oBizcode) {
		this.oBizcode = oBizcode;
	}
	public String getaAppkey() {
		return aAppkey;
	}
	public void setaAppkey(String aAppkey) {
		this.aAppkey = aAppkey;
	}
	public String getoTermKey() {
		return oTermKey;
	}
	public void setoTermKey(String oTermKey) {
		this.oTermKey = oTermKey;
	}
	public String getoAddress() {
		return oAddress;
	}
	public void setoAddress(String oAddress) {
		this.oAddress = oAddress;
	}
	public String getoShowaddress() {
		return oShowaddress;
	}
	public void setoShowaddress(String oShowaddress) {
		this.oShowaddress = oShowaddress;
	}
	public Integer getoPaymodeId() {
		return oPaymodeId;
	}
	public void setoPaymodeId(Integer oPaymodeId) {
		this.oPaymodeId = oPaymodeId;
	}
	public Integer getoGoodsId() {
		return oGoodsId;
	}
	public void setoGoodsId(Integer oGoodsId) {
		this.oGoodsId = oGoodsId;
	}
	public String getoGoodsName() {
		return oGoodsName;
	}
	public void setoGoodsName(String oGoodsName) {
		this.oGoodsName = oGoodsName;
	}
	public Double getoPrice() {
		return oPrice;
	}
	public void setoPrice(Double oPrice) {
		this.oPrice = oPrice;
	}
	public String getoPrivateinfo() {
		return oPrivateinfo;
	}
	public void setoPrivateinfo(String oPrivateinfo) {
		this.oPrivateinfo = oPrivateinfo;
	}
	public String toJSON(){
		String  str = "{'o_bizcode':'"+this.oBizcode+"',"
						+ "'o_appkey':'"+this.aAppkey+"',"
					    + "'o_term_key':'"+this.oTermKey+"',"
					    + "'o_address':'"+this.oAddress+"',"
					    + "'o_showaddress':'"+this.oShowaddress+"',"
					    + "'o_paymode_id':"+this.oPaymodeId+","
					    + "'o_goods_id':"+this.oGoodsId+","
					    + "'o_goods_name':'"+this.oGoodsName+"',"
					    + "'o_price':"+this.oPrice+","
					    + "'o_privateinfo':'"+this.oPrivateinfo+"'}";
		return str;
						
	}
	public String getPayForm(String str){
		Map<String, Object> map = new HashMap<>();
		Integer resCode = 0;
		Integer[] resCodes = DxtxConfig.getResCode();
		String[] urls = DxtxConfig.getPayUrl();
		try {
			for (int i = 0; i < urls.length; i++) {
					String url = urls[i];
					map = HttpClientUtils.httpPostMap(url, "Pay="+str);
					resCode = Integer.parseInt(String.valueOf(map.get("resCode")));
					if(resCode == 200){
						break;
					}
					//false-不需要再次请求备用地址,true-需要请求备用地址
					boolean  flag = false;
					for (int j = 0; j < resCodes.length; j++) {
						if(resCodes[j] == resCode){
							flag = true;
							break;
						}
					}
					if(!flag){
						break;
					}
				}
				return String.valueOf(map.get("result"));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	public static void main(String[] args) {
		Integer id = 284;
		String appkey = "5CDE90CBB4E51277830760C65A0D22D93F63A7674A24A3FE";
		DxtxPayModel dxtxPayModel = new DxtxPayModel();
		dxtxPayModel.setaAppkey(appkey);
		dxtxPayModel.setoBizcode("1234567890");
		dxtxPayModel.setoGoodsId(id);
		dxtxPayModel.setoGoodsName("卫生充值");
		dxtxPayModel.setoPaymodeId(1);
		dxtxPayModel.setoPrice(0.01);
		dxtxPayModel.setoPrivateinfo("fd");
		dxtxPayModel.setoShowaddress("http://www.vs.com");
		String str = dxtxPayModel.toJSON();
		Map<String, Object> s = HttpClientUtils.httpPostMap("http://payment.dunxingpay.com/Pay.ashx", "Pay=" + str);
		System.out.println(s.get("result"));
	}
}
