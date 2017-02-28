var quoteSocket = null;
var quote_url = "ws://quote.vs.com:9002";
var quote_username = "13677622344";
var quote_password = "a123456";
/**
 * 连接行情服务器
 */
function connectQuoteServer() {
	quoteSocket = new WebSocket(quote_url);
}
/**
 * 初始化行情服务事件处理
 */
function initQuoteClient() {
	if(quoteSocket == null) {
		connectQuoteServer();
	}

	quoteSocket.onopen = function(evt) {
		Quote.doLogin(quote_username, quote_password);
	}
	quoteSocket.onmessage = function(evt) {
		quoteHandleData(evt);
	}
	quoteSocket.onclose = function(evt) {
		initQuoteData();
		console.log("行情退出");
	}
}


/**
 * 行情接口方法列表
 */
var QuoteMethod = {
	/**
	 * 登录Method
	 */
	LoginMethod: "Login",
	/**
	 * 登出Method
	 */
	LogoutMethod: "Logout",
	/**
	 * 查询品种Method
	 */
	QryCommodityMethod: "QryCommodity",
	/**
	 * 查询合约Method
	 */
	QryContractMethod: "QryContract",
	/**
	 * 订阅Method
	 */
	SubscribeMethod: "Subscribe",
	/**
	 * 取消订阅Method
	 */
	UnSubscribeMethod: "UnSubscribe",
	/**
	 * 查询历史数据Method
	 */
	QryHistoryMethod: "QryHistory"
}

/**
 * 行情接口调用工具
 */
var Quote = {
	/**
	 * 发送请求
	 * @param method
	 * @param parameters
	 */
	doSendMessage: function(method, parameters) {
		quoteSocket.send('{"Method":"' + method + '","Parameters":' + parameters + '}');
	},
	/**
	 * 登录请求
	 * @param username
	 * @param password
	 */
	doLogin: function(username, password) {
		Quote.doSendMessage(QuoteMethod.LoginMethod, '{"UserName":"' + username + '","PassWord":"' + password + '","Source":"web"}');
	},
	/**
	 * 登出请求
	 * @param username
	 */
	doLoginOut: function(username) {
		Quote.doSendMessage(QuoteMethod.LogoutMethod, '{"UserName":"' + username + '"}');
	},
	/**
	 * 查询品种请求
	 * @param exchangeNo
	 */
	doQryCommodity: function(exchangeNo) {
		Quote.doSendMessage(QuoteMethod.QryCommodityMethod, '{"ExchangeNo":"' + exchangeNo + '"}');
	},
	/**
	 * 查询所有品种
	 */
	doAllQryCommodity: function() {
		Quote.doSendMessage(QuoteMethod.QryCommodityMethod, null);
	},
	/**
	 * 查询合约请求
	 * @param exchangeNo
	 * @param commodityNo
	 */
	doQryContract: function(exchangeNo, commodityNo) {
		Quote.doSendMessage(QuoteMethod.QryContractMethod, '{"ExchangeNo":"' + exchangeNo + '","CommodityNo":"' + commodityNo + '"}');
	},
	/**
	 * 订阅请求
	 * @param exchangeNo
	 * @param commodityNo
	 * @param contractNo
	 */
	doSubscribe: function(exchangeNo, commodityNo, contractNo) {
		Quote.doSendMessage(QuoteMethod.SubscribeMethod, '{"ExchangeNo":"' + exchangeNo + '","CommodityNo":"' + commodityNo + '","ContractNo":"' + contractNo + '"}');
	},
	/**
	 * 取消订阅请求
	 * @param exchangeNo
	 * @param commodityNo
	 * @param contractNo
	 */
	doUnSubscribe: function(exchangeNo, commodityNo, contractNo) {
		Quote.doSendMessage(QuoteMethod.UnSubscribeMethod, '{"ExchangeNo":"' + exchangeNo + '","CommodityNo":"' + commodityNo + '","ContractNo":"' + contractNo + '"}');
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
	doQryHistory: function(exchangeNo, commodityNo, contractNo, hisQuoteType, beginTime, endTime, count) {
		Quote.doSendMessage(QuoteMethod.QryHistoryMethod, '{"ExchangeNo":"' + exchangeNo + '","CommodityNo":"' + commodityNo + '","ContractNo":"' + contractNo + '","HisQuoteType":' + hisQuoteType + ',"BeginTime":"' + beginTime + '","EndTime":"' + endTime + '","Count":' + count + '}');
	},
	/**
	 * 查询初始化历史数据请求
	 * @param exchangeNo
	 * @param commodityNo
	 * @param contractNo
	 */
	doQryFirstHistory: function(exchangeNo, commodityNo, contractNo) {
		Quote.doSendMessage(QuoteMethod.QryHistoryMethod, '{"ExchangeNo":"' + exchangeNo + '","CommodityNo":"' + commodityNo + '","ContractNo":"' + contractNo + '"}');
	}
}


/**
 * 行情消息处理
 * @param {Object} evt
 */
function quoteHandleData(evt){
	var data = evt.data;
	var jsonData = JSON.parse(data);
	var method = jsonData.Method;
	var parameters = jsonData.Parameters;
	if(method == "OnRspLogin"){ // 登录行情服务器
		Quote.doAllQryCommodity();
	}else if(method == "OnRspQryCommodity"){ // 行情服务器支持的品种
		subscribe(parameters);
		initTradeClient(trade_username,trade_password);//连接交易
	}
}

/**
 * 订阅行情
 */
function subscribe(param){
	for (var i = 0; i < param.length; i++) {
		var data = param[i];
		cacheContractAttribute(data); //缓存订阅合约信息
	}
	
}

// 缓存订阅合约属性信息
var jCacheContractAttribute = new Object();
/**
 * 缓存订阅合约属性，包括
 * 		交易所	ExchangeNo
 * 		品种名称 CommodityName
 * 		品种代码 CommodityNo
 * 		主力合约	MainContract
 * 		每手乘数 ContractSize
 * 		币种 CurrencyNo
 * 		小数位数	DotSize
 * 		最小变动单位	MiniTikeSize
 */
function cacheContractAttribute(jQuote) {
	jCacheContractAttribute[jQuote.CommodityNo] = jQuote;
}
function getCacheContractAttribute(commodityNo, attr) {
	if (isEmpty(jCacheContractAttribute[commodityNo]) || isEmpty(jCacheContractAttribute[commodityNo][attr])) {
		return 0;
	}
	return jCacheContractAttribute[commodityNo][attr];
}


function initQuoteData(){
	quoteSocket = null;
	jCacheContractAttribute = new Object();
	
}

