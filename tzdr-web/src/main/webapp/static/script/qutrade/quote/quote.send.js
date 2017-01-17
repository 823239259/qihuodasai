var QuoteUrl = {
	/**
	 * 登录URL
	 */
	LoginUrl : "Login",
	/**
	 * 登出URL
	 */
	LogoutUrl : "Logout",
	/**
	 * 查询品种URL
	 */
	QryCommodityUrl : "QryCommodity",
	/**
	 * 查询合约URL
	 */
	QryContractUrl : "QryContract",
	/**
	 * 订阅URL
	 */
	SubscribeUrl : "Subscribe",
	/**
	 * 取消订阅URL
	 */
	UnSubscribeUrl : "UnSubscribe",
	/**
	 * 查询历史数据URL
	 */
	QryHistoryUrl : "QryHistory"
}
var Quote = {
		/**
		 * 发送请求
		 * @param method
		 * @param parameters
		 */
		doSendMessage:function(method,parameters){
			quoteSocket.send('{"Method":"'+method+'","Parameters":'+parameters+'}');
		},
		/**
		 * 登录请求
		 * @param username
		 * @param password
		 */
		doLogin:function(username,password){
			Quote.doSendMessage(QuoteUrl.LoginUrl, '{"UserName":"'+username+'","PassWord":"'+password+'"}');
		},
		/**
		 * 登录请求
		 * @param username
		 */
		doLoginOut:function(username){
			Quote.doSendMessage(QuoteUrl.LogoutUrl, '{"UserName":"'+username+'"}');
		},
		/**
		 * 查询品种请求
		 * @param exchangeNo
		 */
		doQryCommodity:function(exchangeNo){
			Quote.doSendMessage(QuoteUrl.QryCommodityUrl, '{"ExchangeNo":"'+exchangeNo+'"}');
		},
		/**
		 * 查询所有品种
		 */
		doAllQryCommodity:function(){
			Quote.doSendMessage(QuoteUrl.QryCommodityUrl, null);
		},
		/**
		 * 查询合约请求
		 * @param exchangeNo
		 * @param commodityNo
		 */
		doQryContract:function(exchangeNo,commodityNo){
			Quote.doSendMessage(QuoteUrl.QryContractUrl, '{"ExchangeNo":"'+exchangeNo+'","CommodityNo":"'+commodityNo+'"}');
		},
		/**
		 * 订阅请求
		 * @param exchangeNo
		 * @param commodityNo
		 * @param contractNo
		 */
		doSubscribe:function(exchangeNo,commodityNo,contractNo){
			Quote.doSendMessage(QuoteUrl.SubscribeUrl, '{"ExchangeNo":"'+exchangeNo+'","CommodityNo":"'+commodityNo+'","ContractNo":"'+contractNo+'"}');
		},
		/**
		 * 取消订阅请求
		 * @param exchangeNo
		 * @param commodityNo
		 * @param contractNo
		 */
		doUnSubscribe:function(exchangeNo,commodityNo,contractNo){
			Quote.doSendMessage(QuoteUrl.UnSubscribeUrl, '{"ExchangeNo":"'+exchangeNo+'","CommodityNo":"'+commodityNo+'","ContractNo":"'+contractNo+'"}');
		},
		/**
		 * 查询历史请求
		 * @param exchangeNo
		 * @param commodityNo
		 * @param contractNo
		 * @param hisQuoteType
		 * @param beginTime
		 * @param endTime
		 * @param count
		 */
		doQryHistory:function(exchangeNo,commodityNo,contractNo,hisQuoteType,beginTime,endTime,count){
			Quote.doSendMessage(QuoteUrl.QryHistoryUrl, '{"ExchangeNo":"'+exchangeNo+'","CommodityNo":"'+commodityNo+'","ContractNo":"'+contractNo+'","HisQuoteType":"'+hisQuoteType+'","BeginTime":"'+beginTime+'","EndTime":"'+endTime+'","Count":"'+count+'"}');
		},
		/**
		 * 查询初始化历史数据请求
		 * @param exchangeNo
		 * @param commodityNo
		 * @param contractNo
		 */
		doQryFirstHistory:function(exchangeNo,commodityNo,contractNo){
			Quote.doSendMessage(QuoteUrl.QryHistoryUrl, '{"ExchangeNo":"'+exchangeNo+'","CommodityNo":"'+commodityNo+'","ContractNo":"'+contractNo+'"}');
		},
		/**
		 * 按类型查询历史数据
		 * @param exchangeNo
		 * @param commodityNo
		 * @param contractNo
		 */
		doQryHistoryALL:function(exchangeNo,commodityNo,contractNo,hisQuoteType){
			console.log("111")
			Quote.doSendMessage(QuoteUrl.QryHistoryUrl,'{"ExchangeNo":"'+exchangeNo+'","CommodityNo":"'+commodityNo+'","ContractNo":"'+contractNo+'","HisQuoteType":'+hisQuoteType+'}');
		}
}