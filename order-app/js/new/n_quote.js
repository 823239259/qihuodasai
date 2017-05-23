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
	intervalCheckTime : 8000,   // 间隔检查时间：8秒
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
//		layer.msg('行情服务器断开，请刷新重连...', {
//			icon: 7
//		});
		//		window.reload(); // 行情连接断开后刷新页面重连行情服务器
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
	} else if(method == "OnRspQryCommodity") { // 行情服务器支持的品种
		initQuoteList(jsonData.Parameters); // 初始化行情列表
		queryOptional();
		subscribe(jsonData.Parameters); //订阅支持的主行情合约	并缓存基础属性

	} else if(method == "OnRspSubscribe") { // 订阅成功信息

		initQuoteInfo(jsonData.Parameters); // 初始化行情价格（订阅成功返回最新的行情状态）

		CacheQuoteSubscribe.setCacheContractQuote(jsonData.Parameters); // 缓存初始化行情信息

		initTradeContractOption(jsonData.Parameters); // 初始化交易合约选项

		initTradeClient(); // 初始化交易
	} else if(method == "OnRtnQuote") { // 最新行情

		HeartBeat00.lastHeartBeatTimestamp = new Date().getTime();
		updateQuoteInfo(jsonData.Parameters); // 更新最新行情信息
		UpdateHoldProfit(jsonData.Parameters); // 更新持仓盈亏
		dealOnRtnQuoteData(jsonData);
		//闪电图
		if(is_shandian) {
			lightChartData(jsonData);
		}

	} else if(method == "OnRspQryHistory") { // 历史行情
		//k线图
		jsonDataTwo = jsonData;
		if(is_k) {
			processingData(jsonData);
		}

		//分时图
		if(is_fenshi) {
			handleTimeChartData(jsonData);
		}
	}
}

// 初始化交易合约选项
function initTradeContractOption(jQuote) {
	tplFillData("contract", "tplTradeContractOption", jQuote, FillType.after);
	//pricesContract
	tplFillData("pricesContract", "tplTradeContractOption", jQuote, FillType.after);
	//timeContract
	tplFillData("timeContract", "tplTradeContractOption", jQuote, FillType.after);
	//select000
//	tplFillData("select000", "tplTradeContractOption", jQuote, FillType.after);
}
/**
 * 显示行情列表
 */
function initQuoteList(jQuoteList) {
	
	tplFillData("quote-list", "tplQuoteList", jQuoteList, FillType.repalce);
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
function initQuoteInfo(jQuote) {
	var contract = jQuote.CommodityNo + jQuote.ContractNo;
	if(isEmpty(contract)) { // 不处理异常行情
		vsLog(JSON.stringify(jQuote));
		return;
	}
//	document.getElementById(contract + "-price").innerText = fixedPriceByContract(jQuote.LastQuotation.LastPrice, jQuote.CommodityNo);
	$('#'+contract+'-price').text(fixedPriceByContract(jQuote.LastQuotation.LastPrice, jQuote.CommodityNo));
	$('.'+contract+'-price').text(fixedPriceByContract(jQuote.LastQuotation.LastPrice, jQuote.CommodityNo));
//	document.getElementById(contract + "-zd").innerText = fixedPriceByContract(jQuote.LastQuotation.ChangeValue, jQuote.CommodityNo);
	$('#'+contract+'-zd').text(fixedPriceByContract(jQuote.LastQuotation.ChangeValue, jQuote.CommodityNo));
	$('.'+contract+'-zd').text(fixedPriceByContract(jQuote.LastQuotation.ChangeValue, jQuote.CommodityNo));
//	document.getElementById(contract + "-zdf").innerText = parseFloat(jQuote.LastQuotation.ChangeRate).toFixed(2) + "%";
	$('#'+contract+'-zdf').text(parseFloat(jQuote.LastQuotation.ChangeRate).toFixed(2) + "%");
	$('.'+contract+'-zdf').text(parseFloat(jQuote.LastQuotation.ChangeRate).toFixed(2) + "%");
}

/**
 * 更新行情列表信息
 */
function updateQuoteInfo(jQuote) {
	var contract = jQuote.CommodityNo + jQuote.ContractNo;
//	document.getElementById(contract + "-TotalVolume").innerText = jQuote.TotalVolume;
	$('#'+contract+'-TotalVolume').text(jQuote.TotalVolume);
	$('.'+contract+'-TotalVolume').text(jQuote.TotalVolume);
	document.getElementById(contract + "-price").innerHTML = fixedPriceByContract(jQuote.LastPrice, jQuote.CommodityNo);
	$('.'+contract+'-price').text(fixedPriceByContract(jQuote.LastPrice, jQuote.CommodityNo));
	if(typeof($('#candlestick_data_id div:eq(0)').attr('id')) != 'undefined') {
		if($('#candlestick_data_id div:eq(0)').attr('id') == contract + "-line-candlestick-price") {
			var a = $('#candlestick_data_id div:eq(0)').attr('id');
			document.getElementById(contract + "-line-candlestick-price").innerText = fixedPriceByContract(jQuote.LastPrice, jQuote.CommodityNo);
			if(jQuote.LastPrice-jQuote.PreSettlePrice>0){
				$('#'+contract+'-line-candlestick-price').css('color','red');
			}
			if(jQuote.LastPrice-jQuote.PreSettlePrice<0){
				$('#'+contract+'-line-candlestick-price').css('color','forestgreen');
			}
			
			document.getElementById(contract + "-line-candlestick-time").innerText = jQuote.DateTimeStamp;
			document.getElementById(contract + "-line-candlestick-zd").innerText = fixedPriceByContract(jQuote.ChangeValue, jQuote.CommodityNo);
			if(jQuote.ChangeValue>0){
				$('#'+contract+'-line-candlestick-zd').css('color','red');
				$('#'+contract+'-line-candlestick-zd').text('+'+fixedPriceByContract(jQuote.ChangeValue, jQuote.CommodityNo));
			}
			if(jQuote.ChangeValue<0){
				$('#'+contract+'-line-candlestick-zd').css('color','forestgreen');
			}
			
			document.getElementById(contract + "-line-candlestick-zdf").innerText = parseFloat(jQuote.ChangeRate).toFixed(2) + "%";
			if(jQuote.ChangeRate>0){
				$('#'+contract+'-line-candlestick-zdf').css('color','red');
				$('#'+contract+'-line-candlestick-zdf').text('+'+parseFloat(jQuote.ChangeRate).toFixed(2) + "%");
			}
			if(jQuote.ChangeRate<0){
				$('#'+contract+'-line-candlestick-zdf').css('color','forestgreen');
			}
			
			document.getElementById(contract + "-line-candlestick-openPrice").innerText = fixedPriceByContract(jQuote.OpenPrice, jQuote.CommodityNo);
			if(jQuote.OpenPrice-jQuote.PreSettlePrice>0){
				$('#'+contract+'-line-candlestick-openPrice').css('color','red');
			}
			if(jQuote.OpenPrice-jQuote.PreSettlePrice<0){
				$('#'+contract+'-line-candlestick-openPrice').css('color','forestgreen');
			}
			
			document.getElementById(contract + "-line-candlestick-highPrice").innerText = fixedPriceByContract(jQuote.HighPrice, jQuote.CommodityNo);
			if(jQuote.HighPrice-jQuote.PreSettlePrice>0){
				$('#'+contract+'-line-candlestick-highPrice').css('color','red');
			}
			if(jQuote.HighPrice-jQuote.PreSettlePrice<0){
				$('#'+contract+'-line-candlestick-highPrice').css('color','forestgreen');
			}
			
			document.getElementById(contract + "-line-candlestick-totalVolume").innerText = jQuote.TotalVolume;
			document.getElementById(contract + "-line-candlestick-lowPrice").innerText = fixedPriceByContract(jQuote.LowPrice, jQuote.CommodityNo);
			if(jQuote.LowPrice-jQuote.PreSettlePrice>0){
				$('#'+contract+'-line-candlestick-lowPrice').css('color','red');
			}
			if(jQuote.LowPrice-jQuote.PreSettlePrice<0){
				$('#'+contract+'-line-candlestick-lowPrice').css('color','forestgreen');
			}
			
			
			$("#hy-line-candlestick-price").html($("#"+contract + "-line-candlestick-price").text());
			$("#hy-line-candlestick-zd").html($("#"+contract + "-line-candlestick-zd").text());
			$("#hy-line-candlestick-zdf").html($("#"+contract + "-line-candlestick-zdf").text());
			
			$('#pklastparice').text(fixedPriceByContract(jQuote.LastPrice, jQuote.CommodityNo));
			if(jQuote.LastPrice-jQuote.PreSettlePrice>0){
				$('#pklastparice').css('color','red');
			}
			if(jQuote.LastPrice-jQuote.PreSettlePrice<0){
				$('#pklastparice').css('color','forestgreen');
			}
			
			$('#sell_8').text(fixedPriceByContract(jQuote.AskPrice5, jQuote.CommodityNo));
			if(jQuote.AskPrice5-jQuote.PreSettlePrice>0){
				$('#sell_8').css('color','red');
			}
			if(jQuote.AskPrice5-jQuote.PreSettlePrice<0){
				$('#sell_8').css('color','forestgreen');
			}
			
			
			$('#sell_9').text(jQuote.AskQty5);
			$('#pkopenprice').text(fixedPriceByContract(jQuote.OpenPrice, jQuote.CommodityNo));
			if(jQuote.OpenPrice-jQuote.PreSettlePrice>0){
				$('#pkopenprice').css('color','red');
			}
			if(jQuote.OpenPrice-jQuote.PreSettlePrice<0){
				$('#pkopenprice').css('color','forestgreen');
			}
			
			
			$('#sell_6').text(fixedPriceByContract(jQuote.AskPrice4, jQuote.CommodityNo));
			if(jQuote.AskPrice4-jQuote.PreSettlePrice>0){
				$('#sell_6').css('color','red');
			}
			if(jQuote.AskPrice4-jQuote.PreSettlePrice<0){
				$('#sell_6').css('color','forestgreen');
			}
			
			
			$('#sell_7').text(jQuote.AskQty4);
			$('#pkhightprice').text(fixedPriceByContract(jQuote.HighPrice, jQuote.CommodityNo));
			if(jQuote.HighPrice-jQuote.PreSettlePrice>0){
				$('#pkhightprice').css('color','red');
			}
			if(jQuote.HighPrice-jQuote.PreSettlePrice<0){
				$('#pkhightprice').css('color','forestgreen');
			}
			
			
			
			$('#sell_4').text(fixedPriceByContract(jQuote.AskPrice3, jQuote.CommodityNo));
			if(jQuote.AskPrice3-jQuote.PreSettlePrice>0){
				$('#sell_4').css('color','red');
			}
			if(jQuote.AskPrice3-jQuote.PreSettlePrice<0){
				$('#sell_4').css('color','forestgreen');
			}
			
			
			$('#sell_5').text(jQuote.AskQty3);
			$('#pklowprice').text(fixedPriceByContract(jQuote.LowPrice, jQuote.CommodityNo));
			if(jQuote.LowPrice-jQuote.PreSettlePrice>0){
				$('#pklowprice').css('color','red');
			}
			if(jQuote.LowPrice-jQuote.PreSettlePrice<0){
				$('#pklowprice').css('color','forestgreen');
			}
			
			
			$('#sell_2').text(fixedPriceByContract(jQuote.AskPrice2, jQuote.CommodityNo));
			if(jQuote.AskPrice2-jQuote.PreSettlePrice>0){
				$('#sell_2').css('color','red');
			}
			if(jQuote.AskPrice2-jQuote.PreSettlePrice<0){
				$('#sell_2').css('color','forestgreen');
			}
			
			$('#sell_3').text(jQuote.AskQty2);
			$('#pkzd').text(fixedPriceByContract(jQuote.ChangeValue, jQuote.CommodityNo));
			if(jQuote.ChangeValue>0){
				$('#pkzd').css('color','red');
			}
			if(jQuote.ChangeValue<0){
				$('#pkzd').css('color','forestgreen');
			}
			
			$('#sell_0').text(fixedPriceByContract(jQuote.AskPrice1, jQuote.CommodityNo));
			if(jQuote.AskPrice1-jQuote.PreSettlePrice>0){
				$('#sell_0').css('color','red');
			}
			if(jQuote.AskPrice1-jQuote.PreSettlePrice<0){
				$('#sell_0').css('color','forestgreen');
			}
			
			$('#sell_1').text(jQuote.AskQty1);
			$('#pktrademl').text(jQuote.TotalVolume);
			$('#buy_0').text(fixedPriceByContract(jQuote.BidPrice1, jQuote.CommodityNo));
			if(jQuote.BidPrice1-jQuote.PreSettlePrice>0){
				$('#buy_0').css('color','red');
			}
			if(jQuote.BidPrice1-jQuote.PreSettlePrice<0){
				$('#buy_0').css('color','forestgreen');
			}
			
			$('#buy_1').text(jQuote.BidQty1);
			$('#pkccml').text(jQuote.Position);
			$('#buy_2').text(fixedPriceByContract(jQuote.BidPrice2, jQuote.CommodityNo));
			if(jQuote.BidPrice2-jQuote.PreSettlePrice>0){
				$('#buy_2').css('color','red');
			}
			if(jQuote.BidPrice2-jQuote.PreSettlePrice<0){
				$('#buy_2').css('color','forestgreen');
			}
			
			$('#buy_3').text(jQuote.BidQty2);

			$('#pkzj').text(fixedPriceByContract(jQuote.PreSettlePrice, jQuote.CommodityNo));
			$('#buy_4').text(fixedPriceByContract(jQuote.BidPrice3, jQuote.CommodityNo));
			if(jQuote.BidPrice3-jQuote.PreSettlePrice>0){
				$('#buy_4').css('color','red');
			}
			if(jQuote.BidPrice3-jQuote.PreSettlePrice<0){
				$('#buy_4').css('color','forestgreen');
			}
			
			
			$('#buy_5').text(jQuote.BidQty3);

			$('#buy_6').text(fixedPriceByContract(jQuote.BidPrice4, jQuote.CommodityNo));
			if(jQuote.BidPrice4-jQuote.PreSettlePrice>0){
				$('#buy_6').css('color','red');
			}
			if(jQuote.BidPrice4-jQuote.PreSettlePrice<0){
				$('#buy_6').css('color','forestgreen');
			}
			
			$('#buy_7').text(jQuote.BidQty4);

			$('#buy_8').text(fixedPriceByContract(jQuote.BidPrice5, jQuote.CommodityNo));
			if(jQuote.BidPrice5-jQuote.PreSettlePrice>0){
				$('#buy_8').css('color','red');
			}
			if(jQuote.BidPrice5-jQuote.PreSettlePrice<0){
				$('#buy_8').css('color','forestgreen');
			}
			
			$('#buy_9').text(jQuote.BidQty5);

			$('#xin0').text(fixedPriceByContract(jQuote.LastPrice, jQuote.CommodityNo));
			if(jQuote.LastPrice-jQuote.PreSettlePrice>0){
				$('#xin0').css('color','red');
			}
			if(jQuote.LastPrice-jQuote.PreSettlePrice<0){
				$('#xin0').css('color','forestgreen');
			}
			
			$('#xin1').text(jQuote.TotalVolume);

			$('#buy00').text(fixedPriceByContract(jQuote.BidPrice1, jQuote.CommodityNo));
			if(jQuote.BidPrice1-jQuote.PreSettlePrice>0){
				$('#buy00').css('color','red');
			}
			if(jQuote.BidPrice1-jQuote.PreSettlePrice<0){
				$('#buy00').css('color','forestgreen');
			}
			
			$('#buy11').text(jQuote.BidQty1);

			$('#sell00').text(fixedPriceByContract(jQuote.AskPrice1, jQuote.CommodityNo));
			if(jQuote.AskPrice1-jQuote.PreSettlePrice>0){
				$('#sell00').css('color','red');
			}
			if(jQuote.AskPrice1-jQuote.PreSettlePrice<0){
				$('#sell00').css('color','forestgreen');
			}
			$('#sell11').text(jQuote.AskQty1);

			$('#_sell00_').text(fixedPriceByContract(jQuote.AskPrice1, jQuote.CommodityNo));
			if(jQuote.AskPrice1-jQuote.PreSettlePrice>0){
				$('#_sell00_').css('color','red');
			}
			if(jQuote.AskPrice1-jQuote.PreSettlePrice<0){
				$('#_sell00_').css('color','forestgreen');
			}
			
			
			$('#_sell01_').text(jQuote.AskQty1);
			var buyLength = jQuote.BidQty1/(jQuote.BidQty1+jQuote.AskQty1);
			$("#buyLength").css("width",parseInt(buyLength*100)+"px");
			$('#_buy00_').text(fixedPriceByContract(jQuote.BidPrice1, jQuote.CommodityNo));
			if(jQuote.BidPrice1-jQuote.PreSettlePrice>0){
				$('#_buy00_').css('color','red');
			}
			if(jQuote.BidPrice1-jQuote.PreSettlePrice<0){
				$('#_buy00_').css('color','forestgreen');
			}
			
			$('#_buy01_').text(jQuote.BidQty1);
			var sellLength = jQuote.AskQty1/(jQuote.BidQty1+jQuote.AskQty1);
			$("#sellLength").css("width",parseInt(sellLength*100)+"px");
			
			
		}

		//更新止损止盈中的最新价格
		if(StopCommodityNo != null) {
			if(jQuote.CommodityNo == StopCommodityNo) {
				$('#stopLast').text(fixedPriceByContract(jQuote.LastPrice,jQuote.CommodityNo));
				$('#zhiyin00Last').text(fixedPriceByContract(jQuote.LastPrice,jQuote.CommodityNo));
			}
		}


	}
	if(jQuote.ChangeValue>0){
		$('#'+contract+'-zd').css('background-color','red');
		$('.'+contract+'-zd').css('background-color','red').text("+"+fixedPriceByContract(jQuote.ChangeValue, jQuote.CommodityNo));
		document.getElementById(contract + "-zd").innerText = "+"+fixedPriceByContract(jQuote.ChangeValue, jQuote.CommodityNo);
	}
	if(jQuote.ChangeValue<0){
		$('#'+contract+'-zd').css('background-color','forestgreen');
		$('.'+contract+'-zd').css('background-color','forestgreen').text(fixedPriceByContract(jQuote.ChangeValue, jQuote.CommodityNo));
		document.getElementById(contract + "-zd").innerText = fixedPriceByContract(jQuote.ChangeValue, jQuote.CommodityNo);
	}
	if(jQuote.ChangeValue=0){
		$('.'+contract+'-zd').text(fixedPriceByContract(jQuote.ChangeValue, jQuote.CommodityNo));
		document.getElementById(contract + "-zd").innerText = fixedPriceByContract(jQuote.ChangeValue, jQuote.CommodityNo);
	}
	
	if(jQuote.ChangeRate>0){
		$('#'+contract+'-zdf').css('background-color','red');
		$('.'+contract+'-zdf').css('background-color','red').text("+"+parseFloat(jQuote.ChangeRate).toFixed(2) + "%");
		document.getElementById(contract + "-zdf").innerText = "+"+parseFloat(jQuote.ChangeRate).toFixed(2) + "%";
	}
	if(jQuote.ChangeRate<0){
		$('#'+contract+'-zdf').css('background-color','forestgreen');
		$('.'+contract+'-zdf').css('background-color','forestgreen').text(parseFloat(jQuote.ChangeRate).toFixed(2) + "%");
		document.getElementById(contract + "-zdf").innerText = parseFloat(jQuote.ChangeRate).toFixed(2) + "%";
	}
	if(jQuote.ChangeRate=0){
		$('.'+contract+'-zdf').text(parseFloat(jQuote.ChangeRate).toFixed(2) + "%");
		document.getElementById(contract + "-zdf").innerText = parseFloat(jQuote.ChangeRate).toFixed(2) + "%";
	}
	var a = Number(fixedPriceByContract(jQuote.ChangeValue, jQuote.CommodityNo));
	if(Number(fixedPriceByContract(jQuote.ChangeValue, jQuote.CommodityNo))<0){
		
	}
}

/**
 * 根据合约价格精度处理价格小数位数
 * @param price	需处理原价格
 * @param commodityNo	根据哪个品种的小数位处理
 * @returns
 */
function fixedPriceByContract(price, commodityNo) {
	var dotSize = CacheQuoteBase.getCacheContractAttribute(commodityNo, "DotSize");
	if(isEmpty(dotSize)) {
		dotSize = 0;
	}

	return parseFloat(price).toFixed(dotSize);
}

/**
 * 更新持仓盈亏
 */
function UpdateHoldProfit(jQuote) {
	if(ifUpdateHoldProfit) { // 见n_trade.js
		var contract = jQuote.CommodityNo + jQuote.ContractNo;
		updateHoldFloatingProfit(contract, jQuote.LastPrice);
	}
}
var optionalMyList = [];

function queryOptional() {
	mui.app_request('/contract/optional/list', {}, function(result) {
		if(result.success == true) {
			document.getElementById("myQuote-list").innerHTML="";
			optionalMyList=[];
			data = result.data;
			for(var i = 0; i < data.length; i++) {
				var CommodityName = CacheQuoteBase.getCacheContractAttribute(data[i].commodityCode, "CommodityName");
				var MainContract = CacheQuoteBase.getCacheContractAttribute(data[i].commodityCode, "MainContract");
				var price=$('#'+data[i].commodityCode+MainContract+'-price').text();
				var TotalVolume=$('#'+data[i].commodityCode+MainContract+'-TotalVolume').text();
				var zd=$('#'+data[i].commodityCode+MainContract+'-zd').text();
				var zdf=$('#'+data[i].commodityCode+MainContract+'-zdf').text();
				optionalMyList.push({ "CommodityNo": data[i].commodityCode, "CommodityName": CommodityName, "MainContract": MainContract, "id": data[i].id ,"price":price,"TotalVolume":TotalVolume,"zd":zd,"zdf":zdf})
			}
			if(optionalMyList.length == 0) {
				return;
			}
			tplFillData("myQuote-list", "tplQuoteListMy", optionalMyList, FillType.repalce);
		}
	}, function(res) {});
}

function dealOnRtnQuoteData(data, totalVolume) {
	var CommodityNo = $("#commodityNo").val();
	var Parameters = data.Parameters;
	if(CommodityNo == undefined) {
		return
	}
	if(CommodityNo != Parameters.CommodityNo) {
		return
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
	if($("#timeChartButton").hasClass("mui-active")) {
		if(timeData.timeLabel[timeData.timeLabel.length - 1] == undefined) {
			return
		}
		var lastVolume = Number(volumeChartData.volume[volumeChartData.volume.length - 1]);
		var freshVolume = lastVolume + Volume;
		oldTime1 = (timeData.timeLabel[timeData.timeLabel.length - 1]).replace(/-/g, "/");
		oldTime = Math.round(new Date(oldTime1).getTime() / 1000);
		if(oldTime == newTime) {
			var lastPrices1 = lastPrices;
			if(timeData.prices[timeData.prices.length - 1] == lastPrices1 && volumeChartData.volume[volumeChartData.volume.length - 1] == freshVolume) {

			} else {
				timeData.prices[timeData.prices.length - 1] = lastPrices1;
				volumeChartData.volume[volumeChartData.volume.length - 1] = freshVolume;
				drawChartTime(positionValue);
			}
		} else {
			timeData.timeLabel.push(time6);
			timeData.prices.push(lastPrices);
			volumeChartData.time.push(time6);
			volumeChartData.volume.push(Volume);
			drawChartTime(positionValue);
		}
		$("#totalVolume").val(Parameters.TotalVolume);
	}
	if($("#chiocecandlestickButton").hasClass("mui-active")==false) {
		return
	}
	if(CandlestickData != undefined && CandlestickData.values.length != 0) {
		var oldTime2 = (CandlestickData.categoryData[CandlestickData.categoryData.length - 1]).replace(/-/g, "/");
		var oldTime3 = Math.round(new Date(oldTime2).getTime() / 1000);
		if(oldTime3 == newTime) {
			var length = CandlestickData.values.length;
			var lowPrices = Number(CandlestickData.values[length - 1][2]);
			var highPrices = Number(CandlestickData.values[length - 1][3]);
			var closePrices = lastPrices;
			if(lastPrices >= highPrices) {
				highPrices = lastPrices
			}
			if(lowPrices >= highPrices) {
				lowPrices = lastPrices
			}
			CandlestickData.values[length - 1] = [CandlestickData.values[length - 1][0], closePrices, lowPrices, highPrices];
			CandlestickVolumeData.volume[CandlestickVolumeData.volume.length - 1] = freshVolume1;
			
		} else {
			CandlestickData.categoryData.push(time6)
			CandlestickData.values.push([lastPrices, lastPrices, lastPrices, lastPrices]);
			CandlestickVolumeData.time.push(time6)
			CandlestickVolumeData.volume.push(Volume);
			CandlestickData.categoryData = CandlestickData.categoryData.slice(-500);
			CandlestickData.values = CandlestickData.values.slice(-500);
			CandlestickVolumeData.time = CandlestickVolumeData.time.slice(-500);
			CandlestickVolumeData.volume = CandlestickVolumeData.volume.slice(-500);
		}
		$("#totalVolume").val(Parameters.TotalVolume);
	} else {
		return
	}
//	$("#totalVolume").val(Parameters.TotalVolume)
	drawChartCandlestick(positionValue);
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

function getNowFormatDate(date) {
	var seperator1 = "-";
	var seperator2 = ":";
	var month = date.getMonth() + 1;
	var strDate = date.getDate();
	var getMinutes = date.getMinutes();
	var getSeconds = "00"
	if(month >= 1 && month <= 9) {
		month = "0" + month;
	}
	if(strDate >= 0 && strDate <= 9) {
		strDate = "0" + strDate;
	}
	if(Number(getMinutes) < 10) {
		getMinutes = "0" + getMinutes;
	}
	var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate +
		" " + date.getHours() + seperator2 + getMinutes +
		seperator2 + getSeconds;
	return currentdate;
}

function drawChartCandlestick(positionValue) {
	//	var value=$(".carbon_time").eq(0).hasClass("active")
	//	if(CandlestickData != undefined){
	//		if(!value){
	if(CandlestickData.categoryData == null) {
		return
	}
	var option = setOptionCandlestick(CandlestickData, positionValue);
	var option1 = volumeChartCandlestickSetOption(CandlestickVolumeData);
	CandlestickChart.setOption(option);
	CandlestickChart.resize();
	CandlestickChart.group = "group2";
	CandlestickVolumeChart.setOption(option1);
	CandlestickVolumeChart.resize();
	CandlestickVolumeChart.group = "group2";
	//     	}
	//	}
}

function drawChartTime(positionValue) {
	//	var value=$(".carbon_time").eq(1).hasClass("active")
	//	if(value){
	//		if(CandlestickData.volume==null){
	//	       		return
	//	    }
	var option = setOptionTime(timeData, positionValue);
	timeChart.setOption(option);
	timeChart.resize();
	timeChart.group = "group1";
	var volumeChartOption = volumeChartSetOption(volumeChartData)
	volumeChart.setOption(volumeChartOption);
	volumeChart.resize();
	volumeChart.group = "group1";
	//	}
}