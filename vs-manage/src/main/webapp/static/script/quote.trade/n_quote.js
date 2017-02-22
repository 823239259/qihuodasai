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
		quoteSocket = null;
		window.reload(); // 行情连接断开后刷新页面重连行情服务器
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
 * 显示行情列表
 */
function displayQuoteList(jQuoteList) {
	tplFillData("quote-list", "tplQuoteList", jQuoteList);
}

/**
 * 订阅行情
 */
function subscribe(param){
	for (var i = 0; i < param.length; i++) {
		var data = param[i];
		var commodityNo = data.CommodityNo;
		var mainContract = data.MainContract;
		var exchangeNo = data.ExchangeNo;
		var currencyNo = data.CurrencyNo;
		cacheContractAttribute(data); //缓存订阅合约信息
		//Quote.doSubscribe(exchangeNo, commodityNo, mainContract); //订阅合约
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

// 缓存订阅合约初始行情信息
var jCacheContractQuote = new Object();
/**
 * 缓存订阅合约行情信息，包括
 * 		{"CommodityNo":"CL","ContractNo":"1703","ErrorCode":0,"ErrorMsg":"订阅成功","ExchangeNo":"CME",
 * 		"LastQuotation":{"AskPrice1":53.88,"AskPrice2":53.89,"AskPrice3":53.9,"AskPrice4":53.92,"AskPrice5":53.95,
 * 						"AskQty1":1,"AskQty2":2,"AskQty3":33,"AskQty4":2,"AskQty5":12,
 * 						"AveragePrice":0,"BidPrice1":53.79,"BidPrice2":53.76,"BidPrice3":53.73,"BidPrice4":53.72,"BidPrice5":53.67,
 * 						"BidQty1":3,"BidQty2":2,"BidQty3":1,"BidQty4":1,"BidQty5":16,
 * 						"ChangeRate":0.03715400334386611,"ChangeValue":0.02000000000000313,"ClosingPrice":0,
 * 						"CommodityNo":"CL","ContractNo":"1703","DateTimeStamp":"2017-02-04 07:03:37","ExchangeNo":"CME",
 * 						"HighPrice":54.22,"LastPrice":53.85,"LastVolume":1,"LimitDownPrice":0,"LimitUpPrice":0,"LowPrice":53.4,"OpenPrice":53.68,
 * 						"Position":542680,"PreClosingPrice":0,"PrePosition":0,"PreSettlePrice":53.83,"SettlePrice":1486163017351,
 * 						"TotalAskQty":0,"TotalBidQty":0,"TotalTurnover":0,"TotalVolume":440576}}
 */
function cacheContractQuote(jQuote) {
	jCacheContractQuote[jQuote.CommodityNo] = jQuote;
}
function getCacheContractQuote(commodityNo, attr1, attr2) {
	if (isEmpty(jCacheContractQuote[commodityNo]) || isEmpty(jCacheContractQuote[commodityNo][attr1])) {
		return 0;
	}
	if (arguments.length == 2) {
//		vsLog("jCacheContractQuote[" + commodityNo + "][" + attr1 + "]=" + jCacheContractQuote[commodityNo][attr1]);
		return jCacheContractQuote[commodityNo][attr1];
	}
//	vsLog("jCacheContractQuote[" + commodityNo + "][" + attr1 + "][" + attr2 + "]=" + jCacheContractQuote[commodityNo][attr1][attr2]);
	return jCacheContractQuote[commodityNo][attr1][attr2];
}

/**
 * 初始化行情列表信息
 */
function initQuoteInfo(jQuote) {
	var contract = jQuote.CommodityNo + jQuote.ContractNo;
	document.getElementById(contract + "-price").innerText = fixedPriceByContract(jQuote.LastQuotation.LastPrice, jQuote.CommodityNo);
	document.getElementById(contract + "-zd").innerText = fixedPriceByContract(jQuote.LastQuotation.ChangeValue, jQuote.CommodityNo);
	document.getElementById(contract + "-zdf").innerText = parseFloat(jQuote.LastQuotation.ChangeRate).toFixed(2) + "%";
	
	cacheContractQuote(jQuote); // 缓存初始化行情信息
}

/**
 * 初始化交易合约选项
 */
function initTradeContractOption(jQuote) {
	tplFillData("contract", "tplTradeContractOption", jQuote, 2);
}

/**
 * 更新行情列表信息信息
 */
function updateQuoteInfo(jQuote) {
	var contract = jQuote.CommodityNo + jQuote.ContractNo;
	
	document.getElementById(contract + "-line").style = "background-color: goldenrod;";
	setTimeout(function(){
		document.getElementById(contract + "-line").style = "";
	}, 500);
	document.getElementById(contract + "-price").innerText = fixedPriceByContract(jQuote.LastPrice, jQuote.CommodityNo);
	document.getElementById(contract + "-zd").innerText = fixedPriceByContract(jQuote.ChangeValue, jQuote.CommodityNo);
	document.getElementById(contract + "-zdf").innerText = parseFloat(jQuote.ChangeRate).toFixed(2) + "%";
	
	UpdateHoldProfit(contract, jQuote.LastPrice); // 更新持仓盈亏
}

/**
 * 根据合约价格精度处理价格小数位数
 * @param price
 * @param contract = commodityNo + contractNo
 * @returns
 */
function fixedPriceByContract(price, commodityNo) {
	var dotSize  = getCacheContractAttribute(commodityNo, "DotSize");
	return parseFloat(price).toFixed(dotSize);
}

/**
 * 更新持仓盈亏
 */
function UpdateHoldProfit(contract, lastPrice) {
	if (ifUpdateHoldProfit) { // 见n_trade.js
		updateHoldFloatingProfit(contract, lastPrice);
	}
}
