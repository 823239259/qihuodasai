// 行情配置
var QuoteConfig = {
//	url : "ws://quote.vs.com:9002",
	url: "ws://192.168.0.213:9002",
	username: "13677622344",
	password: "a123456"
};

var HeartBeat00 = {
	lastHeartBeatTimestamp : 1,	// 最后心跳时间
	oldHeartBeatTimestamp : 0,	// 上一次心跳时间
	intervalCheckTime : 8000000,   // 间隔检查时间：8秒
	check : function(){
		if (this.lastHeartBeatTimestamp == this.oldHeartBeatTimestamp) { // 心跳未更新——上次心跳时间与最新心跳时间一致
			console.log('lastHeartBeatTimestamp:'+this.lastHeartBeatTimestamp+',oldHeartBeatTimestamp:'+this.oldHeartBeatTimestamp);
			layer.msg('行情服务器断开，正在刷新重连...', {
				icon: 7
			});
			mui.app_refresh("simulationQuote");
		} else {
			this.oldHeartBeatTimestamp = this.lastHeartBeatTimestamp; // 更新上次心跳时间
		}
	}
};	
setInterval(function(){
	HeartBeat00.check();
}, HeartBeat00.intervalCheckTime);	// 间隔8秒检查一次

var quoteSocket = null;
/**
 * 连接行情服务器
 */
function connectQuoteServer() {
	quoteSocket = new WebSocket(QuoteConfig.url);
}
/**
 * 初始化行情服务事件处理
 */
function initQuoteClient() {
	if(quoteSocket == null) {
		connectQuoteServer();
	}

	quoteSocket.onopen = function(evt) {
		Quote.doLogin(QuoteConfig.username, QuoteConfig.password);
		
	}
	quoteSocket.onmessage = function(evt) {
		quoteHandleData(evt);
	}
	quoteSocket.onclose = function(evt) {
		quoteSocket = null;
	}
}

/**
 * 缓存订阅合约属性信息。以原油举例：
 * {"CommodityName":"国际原油","CommodityNo":"CL","ContractSize":10,"CurrencyNo":"USD",
 * 		"DotSize":2,"ExchangeNo":"NYMEX","Index":1,"MainContract":"1704","MiniTikeSize":0.01,
 * 		"TradingTimeSeg":[{"DateFlag":"1","IsDST":"N","TimeBucketBeginTime":"07:00:00","TradingState":"3","TradingTimeBucketID":0},
 * 						  {"DateFlag":"2","IsDST":"N","TimeBucketBeginTime":"06:00:00","TradingState":"5","TradingTimeBucketID":1}]}
 */
var CacheQuoteBase = {
	jCacheContractAttribute: {}, // key 为CommodityNo
	setCacheContractAttribute: function(jQuote) {
		this.jCacheContractAttribute[jQuote.CommodityNo] = jQuote;
	},
	getCacheContractAttribute: function(commodityNo, attr) {
		if(isEmpty(this.jCacheContractAttribute[commodityNo]) || isEmpty(this.jCacheContractAttribute[commodityNo][attr])) {
			return 0;
		}
		return this.jCacheContractAttribute[commodityNo][attr];
	}
};
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
 * 
 * 用行情服务器的配置增加/覆盖订阅合约配置：
 * {"CommodityName":"国际原油","CommodityNo":"CL","ContractSize":10,"CurrencyNo":"USD",
 * 		"DotSize":2,"ExchangeNo":"NYMEX","Index":1,"MainContract":"1704","MiniTikeSize":0.01,
 * 		"TradingTimeSeg":[{"DateFlag":"1","IsDST":"N","TimeBucketBeginTime":"07:00:00","TradingState":"3","TradingTimeBucketID":0},
 * 						  {"DateFlag":"2","IsDST":"N","TimeBucketBeginTime":"06:00:00","TradingState":"5","TradingTimeBucketID":1}]}
 */
var CacheQuoteSubscribe = {
	jCacheContractQuote: {}, // key 为contract(jQuote.CommodityNo + jQuote.ContractNo)
	setCacheContractQuote: function(jQuote) {
		// 根据行情服务器的配置更新到合约行情缓存配置
		jQuote.CommodityName = CacheQuoteBase.getCacheContractAttribute(jQuote.CommodityNo, "CommodityName");
		jQuote.CurrencyNo = CacheQuoteBase.getCacheContractAttribute(jQuote.CommodityNo, "CurrencyNo");
		jQuote.DotSize = CacheQuoteBase.getCacheContractAttribute(jQuote.CommodityNo, "DotSize");
		jQuote.ExchangeNo = CacheQuoteBase.getCacheContractAttribute(jQuote.CommodityNo, "ExchangeNo");
		jQuote.Index = CacheQuoteBase.getCacheContractAttribute(jQuote.CommodityNo, "Index");
		jQuote.MainContract = CacheQuoteBase.getCacheContractAttribute(jQuote.CommodityNo, "MainContract");
		jQuote.ContractSize = CacheQuoteBase.getCacheContractAttribute(jQuote.CommodityNo, "ContractSize");
		jQuote.MiniTikeSize = CacheQuoteBase.getCacheContractAttribute(jQuote.CommodityNo, "MiniTikeSize");
		jQuote.TradingTimeSeg = CacheQuoteBase.getCacheContractAttribute(jQuote.CommodityNo, "TradingTimeSeg");

		this.jCacheContractQuote[jQuote.CommodityNo + jQuote.ContractNo] = jQuote;
	},
	getCacheContractQuote: function(contract, attr1, attr2) {
		if(isEmpty(this.jCacheContractQuote[contract]) || isEmpty(this.jCacheContractQuote[contract][attr1])) {
			return 0;
		}
		if(arguments.length == 2) {
			//		vsLog("this.jCacheContractQuote[" + contract + "][" + attr1 + "]=" + this.jCacheContractQuote[contract][attr1]);
			return this.jCacheContractQuote[contract][attr1];
		}
		//	vsLog("this.jCacheContractQuote[" + contract + "][" + attr1 + "][" + attr2 + "]=" + this.jCacheContractQuote[contract][attr1][attr2]);
		return this.jCacheContractQuote[contract][attr1][attr2];
	}
};



/**
 * 行情消息处理
 * @param {Object} evt
 */
function quoteHandleData(evt) {
	
	var data = evt.data;
	var jsonData = JSON.parse(data);
	var method = jsonData.Method;
	
	if(isEmpty(jsonData.Parameters)) {
		vsLog("行情返回参数为空不做处理：" + dataString);
		return;
	}
	
	if(method == "OnRspLogin") { // 登录行情服务器
		layer.msg('行情连接成功');

		Quote.doAllQryCommodity(); // 查询服务器支持品种用于订阅
	}else if(method == "OnRspQryCommodity") { // 行情服务器支持的品种
		initQuoteList(jsonData.Parameters); // 初始化行情列表
		subscribe(jsonData.Parameters); //订阅支持的主行情合约	并缓存基础属性
	}else if(method == "OnRspSubscribe") { // 订阅成功信息
		initQuoteInfo(jsonData.Parameters); // 初始化行情价格（订阅成功返回最新的行情状态）
		CacheQuoteSubscribe.setCacheContractQuote(jsonData.Parameters); // 缓存初始化行情信息
		initTradeContractOption(jsonData.Parameters); // 初始化交易合约选项
	}else if(method == "OnRtnQuote") { // 最新行情
		HeartBeat00.lastHeartBeatTimestamp = new Date().getTime();
		updateQuoteInfo(jsonData.Parameters); // 更新最新行情信息
//		UpdateHoldProfit(jsonData.Parameters); // 更新持仓盈亏
		dealOnRtnQuoteData(jsonData);
		//闪电图
		if(is_shandian) {
			lightChartData(jsonData);
		}
	}else if(method == "OnRspQryHistory"){
		
		if(is_k) {
			processingData(jsonData);
		}

		//分时图
		if(is_fenshi) {
			handleTimeChartData(jsonData);
		}
	}
		
}

/**
 * 显示行情列表
 */
function initQuoteList(jQuoteList) {
	
	tplFillData("list", "tplQuoteList", jQuoteList, FillType.repalce);
}

/**
 * 订阅行情
 */
function subscribe(param) {
	for(var i = 0; i < param.length; i++) {
		var data = param[i];
		if(data.IsUsed!=0){
			var commodityNo = data.CommodityNo;
			var mainContract = data.MainContract;
			var exchangeNo = data.ExchangeNo;
	
			CacheQuoteBase.setCacheContractAttribute(data); //缓存订阅合约信息
			Quote.doSubscribe(exchangeNo, commodityNo, mainContract); //订阅合约
		}
	}
}

/**
 * 初始化行情列表信息
 */
function initQuoteInfo(jQuote){
	var contract = jQuote.CommodityNo + jQuote.ContractNo;
	if(isEmpty(contract)) { // 不处理异常行情
		vsLog(JSON.stringify(jQuote));
		return;
	}
	$('#'+contract+'-price').text(fixedPriceByContract(jQuote.LastQuotation.LastPrice, jQuote.CommodityNo));
	$('#'+contract+'-zdf').text(parseFloat(jQuote.LastQuotation.ChangeRate).toFixed(2) + "%");
}

// 初始化交易合约选项
function initTradeContractOption(jQuote){
	
	tplFillData("contract", "tplTradeContractOption", jQuote, FillType.after);
}

function dealOnRtnQuoteData(data, totalVolume){
	
	var CommodityNo = $("#commodityNo").val();
	var Parameters = data.Parameters;
	if(CommodityNo == undefined) {
		return;
	}
	if(CommodityNo != Parameters.CommodityNo) {
		return;
	}
	
	var range = checkRange();
	var totalVolume = $("#totalVolume").val();
	if(totalVolume == "") {
		totalVolume = 0;
		$("#totalVolume").val(Parameters.TotalVolume);
		return
	}
	
	if(range == 1440) {
		return;
	}
	
	var Volume = Number(Parameters.TotalVolume) - Number(totalVolume);
	if(Volume < 0) {
		Volume = Parameters.TotalVolume;
	}
	
	var lastVolume1 = 0;
	var freshVolume1 = 0;
	
	if(CandlestickData != undefined) {
		lastVolume1 = Number(CandlestickVolumeData.volume[CandlestickVolumeData.volume.length - 1]);
		freshVolume1 = lastVolume1 + Volume;
	}
	if(freshVolume1 < 0) {
		freshVolume1 = Parameters.TotalVolume;
	}
	var lastPrices = fixedPriceByContract(Parameters.LastPrice, CommodityNo);
	var oldTime1;
	var oldTime;
	var DateTimeStamp = Parameters.DateTimeStamp.replace(/-/g, "/");
	var nowShjian = Math.round(new Date(DateTimeStamp).getTime() / 1000)
	var time1 = parseInt(nowShjian / (60 * range));
	var newTime = Number(time1 * 60 * range);
	var time5 = new Date(parseInt(newTime) * 1000);
	var time6 = getNowFormatDate(time5);
	var length = $("#positionList .position3").length;
	var positionValue = getPositionValue();
	
	
}

function checkRange() {
	var range = 1;
	var length = $("#candlestickNav a").length;
	for(var i = 0; i < length; i++) {
		if($("#candlestickNav a").eq(i).hasClass("mui-active") == true) {
			var data = $("#candlestickNav a").eq(i).attr("data");
			if(data == 0) {
				range = 1
			} else {
				range = data;
			}
		}
	}
	return range;
}


/**
 * 更新行情列表信息
 */
function updateQuoteInfo(jQuote){
	
	var contract = jQuote.CommodityNo + jQuote.ContractNo;
	$('#'+contract+'-zdf').text(parseFloat(jQuote.ChangeRate).toFixed(2) + '%');
	$('#'+contract+'-price').text(fixedPriceByContract(jQuote.LastPrice, jQuote.CommodityNo));
	if(SuperCommodityNo==jQuote.CommodityNo){
		
		
		
	}
}













