var marketCommdity = {};

function setMarketCommdity(key, value) {
	marketCommdity[key] = value;
}

function getMarketCommdity(key) {
	return marketCommdity[key];
}
var marketCommdityLastPrice = {};

function setMarketCommdityLastPrice(key, value) {
	marketCommdityLastPrice[key] = value;
}

function getMarketCommdityLastPrice(key) {
	return marketCommdityLastPrice[key];
}
var marketNotSubCommdity = {};

function setMarketNotSubCommdity(key, value) {
	marketNotSubCommdity[key] = value;
}
var marketSubCommdity = {};

function setMarketSubCommdity(key, value) {
	marketSubCommdity[key] = value;
}
var localCacheQuote = {};
/**
 * 行情品种查询是否成功
 */
var queryCommodityIsFlag = false;

function getQueryCommodityIsFlag() {
	return queryCommodityIsFlag;
}
/**
 * 添加最新一条行情行情到全局缓存
 * @param {Object} param
 */
function setLocalCacheQuote(param) {
	var commodityNo = param.CommodityNo;
	var contractNo = param.ContractNo;
	var contractCode = commodityNo + contractNo;
	localCacheQuote[contractCode] = param;
}
/**
 * 获取缓存的行情数据
 * @param {Object} key
 */
function getLocalCacheQuote(key) {
	return localCacheQuote[key];
}
var reconnect = null;
var commodityNoList = "";
var marketSocket = null;
var firstTimeLength = 1;
var commoditysData;
var OnRspQryCommodityDateL = 1;

function subscribeHold(exchageNo, commodityNo, contractNo) {
	masendMessage('Subscribe', '{"ExchangeNo":"' + exchageNo + '","CommodityNo":"' + commodityNo + '","ContractNo":"' + contractNo + '"}');
}
mui.plusReady(function() {
	var Transfer = plus.webview.currentWebview();
	var CommodityNo = document.getElementById("CommodityNo");
	var mainTitleFirst = document.getElementsByClassName("mainTitleFirst")[0];
	mainTitleFirst.innerHTML = Transfer.name[0];
	CommodityNo.innerHTML = Transfer.name[2] + Transfer.name[1];
	$("#doSize").val(Transfer.name[4])
	init(Transfer.name);
	$("#MainContract").text(Transfer.name[2] + Transfer.name[1]);
	initMarketSocket();

	function initMarketSocket() {
		marketSocket = new WebSocket(marketSocketUrl);
		var marketLoadParam = {}
		marketSocket.onopen = function(evt) {
			masendMessage('Login', '{"UserName":"' + marketUserName + '","PassWord":"' + marketPassword + '"}');
			$("#netWorkTips").css("display", "none");
			mui.toast("行情服务器连接成功！");
			$(".mui-bar").css("background-color", "#2B2B2B");
		};
		marketSocket.onclose = function(evt) {
			//	    	if(reconnect != false){
			//		  		if(username==null){
			//		  			alertProtype("行情服务器连接超时,点击确定重新连接","提示",Btn.confirmed(),null,null,null);
			//		  		}
			//	    	}
		};
		//以下为更新代码
		var updateStart=0,updateEnd=0,timeArr=0,timez;
		
		//以上为更新代码
		
		marketSocket.onmessage = function(evt) {
			
			updateStart=(new Date()).getTime();
//			console.log(updateStart);
			timez=updateStart-updateEnd;
			

			var data = evt.data;
			var jsonData = JSON.parse(data);
			var method = jsonData.Method;
			if(method == "OnRspLogin") {
				sendHistoryMessage(0);
				masendMessage('QryCommodity', null);
			} else if(method == "OnRspQryHistory") {
				var historyParam = jsonData;
				if(historyParam.Parameters == null) {
					return
				};
				if(firstTimeLength == 1) {
					getSubscript(historyParam.Parameters.ColumNames);
					firstTimeLength = 2;
				}
				if(historyParam.Parameters.HisQuoteType == 0) {
					handleTime(historyParam);
					handleVolumeChartData(historyParam);
				} else {
					processingData(historyParam);
					processingCandlestickVolumeData(historyParam);
				}
			} else if(method == "OnRtnQuote") {
				
				var quoteParam = jsonData;
				if(quoteParam.Parameters == null) return;
				var subscribeParam = quoteParam.Parameters;
				var newCommdityNo = subscribeParam.CommodityNo;
				var newContractNo = subscribeParam.ContractNo;
				marketLoadParam[newCommdityNo] = subscribeParam;
				lightChartData(quoteParam);   //绘制闪电图
				$("#refresh").removeClass("rotateClass");
				var commodityNo = $("#commodeityNo").val();
				var totalVolume = $("#volumePricesNumber").text()
				if(commodityNo == quoteParam.Parameters.CommodityNo) {
//					console.log(timez);   //打印时间差
					if(timez==updateStart || timez>=3000){  //如果时间差大于XXX毫秒才执行更新
//						console.log('我更新了');
						dealOnRtnQuoteData(jsonData, totalVolume);     //更新图表
					}else{
						timeArr+=timez;   //如果时间差不满足大于xxx毫秒，就把差值加到一个容器变量中
						if(timeArr>=3000){
//							console.log('我终于更新了');
							dealOnRtnQuoteData(jsonData, totalVolume);
							timeArr=0;
						}
					}
				}
				//如果是当前合约与品种更新行情数据，和浮动盈亏
				if(valiationIsPresent(newCommdityNo, newContractNo)) {
					updateLoadWebParam(subscribeParam);
					insertDATA(quoteParam);
					setFiveMarket(subscribeParam);
					setHandicap(subscribeParam);
				}
				updateStopAndLossLastPrice(subscribeParam);
				updateDesignateByQuote(subscribeParam);
				updateFloatProfit(subscribeParam);
				//计算浮动盈亏总和
				sumListfloatingProfit();
				//更新账户资产
				updateAccountBalance();
				setMarketCommdityLastPrice(newCommdityNo + newContractNo, subscribeParam.LastPrice);
				setLocalCacheQuote(subscribeParam);
			} else if(method == "OnRspQryCommodity") {
				if(OnRspQryCommodityDateL == 1) {
					commoditysData = jsonData.Parameters;
				}
				var commoditys = jsonData.Parameters;
				if(commoditys == null) return;
				var size = commoditys.length;
				var tradeTitleHtml = document.getElementById("tradeTitle");
				for(var i = 0; i < size; i++) {
					
					var comm = commoditys[i];
					var newCommdityNo = comm.CommodityNo;
					var newContractNo = comm.MainContract;
					var newExchangeNo = comm.ExchangeNo;
					
					/**
			        * 缓存行情信息
			        */
			        CacheQuoteBase.setCacheContractAttribute(comm);
					
					//如果是当前合约与品种更新乘数
					if(valiationIsPresent(newCommdityNo, newContractNo)) {
						$("#contractSize").val(comm.ContractSize);
					}
					var commdityAndContract = newCommdityNo + newContractNo;
					setMarketCommdity(commdityAndContract, comm);
					//验证在执行交易请求数据时，是否还有未订阅的持仓信息，
					var comContract = marketNotSubCommdity[commdityAndContract];
					if(comContract != undefined) {
						if(marketSubCommdity[commdityAndContract] == undefined) {
							subscribeHold(newExchangeNo, newCommdityNo, newContractNo);
							setMarketSubCommdity(commdityAndContract, commdityAndContract);
						}
					}
					$("#chioceContract").append("<option value='" + commdityAndContract + "'>" + comm.CommodityName + "</option>")
					$("#chioceContract1").append("<option value='" + commdityAndContract + "'>" + comm.CommodityName + "</option>")
					if(comm.IsUsed==0){
			        		continue
			        	}
					if(Transfer.name[2] == newCommdityNo) {
						tradeTitleHtml.innerHTML += "<option value=" + commdityAndContract + " selected>" + comm.CommodityName + "</option>"
					} else {
						tradeTitleHtml.innerHTML += "<option value='" + commdityAndContract + "'>" + comm.CommodityName + "</option>"
					}

				}
				queryCommodityIsFlag = true;
			} else if(method == "OnRspSubscribe") {
				var quoteParam = jsonData;
			}
			updateEnd=(new Date()).getTime();
		};
		marketSocket.onerror = function(evt) {};
	}

	function sendHistoryMessage(num) {
		rawData = [];
		dayCandlestickChartData = [];
		timeData.timeLabel = [];
		timeData.prices = [];
		volumeChartData.time = [];
		volumeChartData.volume = [];
		volumeTime = [];
		volumeV = [];
		newData = [];
		CandlestickVolumeData = {
			time: [],
			volume: []
		};
		lightChartTime = {
			"time": [],
			"price": []
		}
		var exchangeNo = $("#exchangeNo").val();
		var commodityNo = $("#commodeityNo").val();
		var contractNo = $("#contractNo").val();
		masendMessage('QryHistory', '{"ExchangeNo":"' + exchangeNo + '","CommodityNo":"' + commodityNo + '","ContractNo":"' + contractNo + '","HisQuoteType":' + num + '}');
		masendMessage('Subscribe', '{"ExchangeNo":"' + exchangeNo + '","CommodityNo":"' + commodityNo + '","ContractNo":"' + contractNo + '"}');
	}
	/**
	 * 更新行情数据 
	 * @param {Object} param
	 */
	function updateLoadWebParam(param) {
		var doSize = 2;
		var size = $("#doSize").val();
		if(size != undefined || size.length > 0) {
			doSize = size;
		}
		$("#lastPrice").text(parseFloat(param.LastPrice).toFixed(doSize));
		$("#sellLastPrice").text(parseFloat(param.AskPrice1).toFixed(doSize));
		$("#buyLastPrice").text(parseFloat(param.BidPrice1).toFixed(doSize));
		$("#totalVolume").text(param.TotalVolume);
		$("#askQty").text(param.AskQty1);
		$("#bidQty").text(param.BidQty1);
		var newCommdityNo = param.CommodityNo;
		var newContractNo = param.ContractNo;
		var comm = marketCommdity[newCommdityNo + newContractNo];
		/*if(comm != undefined && $("input[type = 'radio']:checked").val() == 1){ 
			$("#buyBtn_P").text(parseFloat(doGetMarketPrice(param.LastPrice,comm.MiniTikeSize,0)).toFixed(doSize));
			$("#sellBtn_P").text(parseFloat(doGetMarketPrice(param.LastPrice,comm.MiniTikeSize,1)).toFixed(doSize));
		}*/
	}
	/**
	 * 更新浮动盈亏 
	 */
	function updateFloatProfit(param) {
		var isFlag = false;
		var newContract = param.CommodityNo + param.ContractNo;
		for(var i = 0; i < postionIndex; i++) {
			if(newContract == localCachePositionContractCode[i]) {
				isFlag = true;
				break;
			}
		}
		if(isFlag) {
			var lastPrice = param.LastPrice;
			var comm = marketCommdity[newContract];
			if(comm == undefined) return;
			var $thisFloat = $("#floatValue" + newContract);
			var $thisAvgPrice = $("li[data-tion-position = " + newContract + "] span[class = 'position3']");
			var $thisHoldNum = $("li[data-tion-position = " + newContract + "] span[class = 'position2']");
			var $thisDrection = $("li[data-tion-position = " + newContract + "] span[class = 'position1']");
			var $thisFloatP = $("li[data-tion-position = " + newContract + "] span[class = 'position8']");
			var drection = $thisDrection.attr("data-drection");
			//验证该平仓数据是否存在列表中  
			if($thisAvgPrice.text() == undefined) {
				return;
			}
			var floatP = doGetFloatingProfit(parseFloat(lastPrice), parseFloat($thisAvgPrice.text()), comm.ContractSize, comm.MiniTikeSize, parseInt($thisHoldNum.text()), drection);
			var floatProfit = floatP + ":" + comm.CurrencyNo;
			$thisFloat.val(floatProfit);
			$thisFloatP.text(floatP);
			if(parseFloat(floatP) < 0) {
				$thisFloat.css("color", "green");
			} else if(parseFloat(floatP) > 0) {
				$thisFloat.css("color", "red");
			} else {
				$thisFloat.css("color", "white");
			}
		}

	}
	/**
	 * 验证是否是当前品种和合约 
	 * @param {Object} commodeityNo 品种
	 * @param {Object} contractNo 合约
	 */
	function valiationIsPresent(commodeityNo, contractNo) {
		if(commodeityNo == $("#commodeityNo").val() && contractNo == $("#contractNo").val()) {
			return true;
		} else {
			return false;
		}
	}
	/**
	 * 更新盘口信息
	 * @param {Object} param
	 */
	function setHandicap(param) {
		//卖价
		var askPrice = param.AskPrice1;
		//买价
		var bidPrice1 = param.BidPrice1;
		//最新价
		var lastPrice = param.LastPrice;
		//开仓价
		var openPrice = param.OpenPrice;
		//最高价
		var highPrice = param.HighPrice;
		//最低价
		var lowPrice = param.LowPrice;
		//昨收
		var preClosingPrice = param.PreClosingPrice;
		//结算价
		var settlePrice = param.SettlePrice;
		//卖量
		var totalAskQty = param.TotalAskQty;
		//买量
		var totalBidQty = param.TotalBidQty;
		//涨幅
		var changeRate = param.ChangeRate;
		//涨跌值
		var changeValue = param.ChangeValue;
		//当日成交量
		var totalVolume = param.TotalVolume;
		//持仓量
		var position = param.Position;
		//昨结
		var preSettlePrice = param.PreSettlePrice;
		var commodityNo = param.CommodityNo;
		var contractNo = param.ContractNo;
		var contractCode = commodityNo + contractNo;
		var localCommodity = getMarketCommdity(contractCode);
		var dotSize = 0;
		if(localCommodity != undefined) {
			dotSize = localCommodity.DotSize;
		}
		var color = "#FFFFFF";
		if(askPrice > preSettlePrice) {
			color = "#ff5500";
		} else if(askPrice < preSettlePrice) {
			color = "#0bffa4";
		} else {
			color = "#FFFFFF";
		}

		//		$("#pkmj_sell").text(parseFloat(askPrice).toFixed(dotSize));
		//		$("#pkmj_sell").css("color",color);
		if(bidPrice1 > preSettlePrice) {
			color = "#ff5500";
		} else if(bidPrice1 < preSettlePrice) {
			color = "#0bffa4";
		} else {
			color = "#FFFFFF";
		}
		//		$("#pkmj_buy").text(parseFloat(bidPrice1).toFixed(dotSize));
		//		$("#pkmj_buy").css("color",color);
		if(lastPrice > preSettlePrice) {
			color = "#ff5500";
		} else if(lastPrice < preSettlePrice) {
			color = "#0bffa4";
		} else {
			color = "#FFFFFF";
		}
		$("#pklastparice").text(parseFloat(lastPrice).toFixed(dotSize));
		$("#pklastparice").css("color", color);
		if(openPrice > preSettlePrice) {
			color = "#ff5500";
		} else if(openPrice < preSettlePrice) {
			color = "#0bffa4";
		} else {
			color = "#FFFFFF";
		}
		$("#pkopenprice").text(parseFloat(openPrice).toFixed(dotSize));
		$("#pkopenprice").css("color", color);
		if(highPrice > preSettlePrice) {
			color = "#ff5500";
		} else if(highPrice < preSettlePrice) {
			color = "#0bffa4";
		} else {
			color = "#FFFFFF";
		}
		$("#pkhightprice").text(parseFloat(highPrice).toFixed(dotSize));
		$("#pkhightprice").css("color", color);
		if(lowPrice > preSettlePrice) {
			color = "#ff5500";
		} else if(lowPrice < preSettlePrice) {
			color = "#0bffa4";
		} else {
			color = "#FFFFFF";
		}
		$("#pklowprice").text(parseFloat(lowPrice).toFixed(dotSize));
		$("#pklowprice").css("color", color);
		if(settlePrice > preSettlePrice) {
			color = "#ff5500";
		} else if(settlePrice < preSettlePrice) {
			color = "#0bffa4";
		} else {
			color = "#FFFFFF";
		}
		$("#pkjs").text(parseFloat(settlePrice).toFixed(dotSize));
		$("#pkjs").css("color", color);
		if(totalAskQty > preSettlePrice) {
			color = "#ff5500";
		} else if(totalAskQty < preSettlePrice) {
			color = "#0bffa4";
		} else {
			color = "#FFFFFF";
		}
		//		$("#pkml_sell").text(totalAskQty);
		//		$("#pkml_sell").css("color",color);
		if(totalBidQty > preSettlePrice) {
			color = "#ff5500";
		} else if(totalBidQty < preSettlePrice) {
			color = "#0bffa4";
		} else {
			color = "#FFFFFF";
		}
		//		$("#pkml_buy").text(totalBidQty);
		//		$("#pkml_buy").css("color",color);
		if(changeValue > 0) {
			color = "#ff5500";
		} else if(changeValue < 0) {
			color = "#0bffa4";
		} else {
			color = "#FFFFFF";
		}
		$("#pkzd").text(parseFloat(changeValue).toFixed(dotSize) + "/" + parseFloat(changeRate).toFixed(2) + "%");
		$("#pkzd").css("color", color);
		$("#pktrademl").text(totalVolume);
		$("#pktrademl").css("color", "#FFFFFF");
		$("#pkccml").text(position);
		$("#pkccml").css("color", "#FFFFFF");
		$("#pkzj").text(parseFloat(preSettlePrice).toFixed(dotSize));
		$("#pkzj").css("color", "#FFFFFF");
		$("#pkccml").css("color", "#FFFFFF");
	}
	/**
	 * 设置五档行情
	 */
	function setFiveMarket(param) {
		var commodityNo = param.CommodityNo;
		var contractNo = param.ContractNo;
		var contractCode = commodityNo + contractNo;
		var localCommodity = getMarketCommdity(contractCode);
		var dotSize = 0;
		if(localCommodity != undefined) {
			dotSize = localCommodity.DotSize;
		}
		//昨日结算价
		var preSettlePrice = parseFloat(param.PreSettlePrice).toFixed(dotSize);
		var bidPrice1 = parseFloat(param.BidPrice1).toFixed(dotSize);
		var bidPrice2 = parseFloat(param.BidPrice2).toFixed(dotSize);
		var bidPrice3 = parseFloat(param.BidPrice3).toFixed(dotSize);
		var bidPrice4 = parseFloat(param.BidPrice4).toFixed(dotSize);
		var bidPrice5 = parseFloat(param.BidPrice5).toFixed(dotSize);
		var bidQty1 = param.BidQty1;
		var bidQty2 = param.BidQty2;
		var bidQty3 = param.BidQty3;
		var bidQty4 = param.BidQty4;
		var bidQty5 = param.BidQty5;
		var askPrice1 = parseFloat(param.AskPrice1).toFixed(dotSize);
		var askPrice2 = parseFloat(param.AskPrice2).toFixed(dotSize);
		var askPrice3 = parseFloat(param.AskPrice3).toFixed(dotSize);
		var askPrice4 = parseFloat(param.AskPrice4).toFixed(dotSize);
		var askPrice5 = parseFloat(param.AskPrice5).toFixed(dotSize);
		var askQty1 = param.AskQty1;
		var askQty2 = param.AskQty2;
		var askQty3 = param.AskQty3;
		var askQty4 = param.AskQty4;
		var askQty5 = param.AskQty5;
		var color = "#FFFFFF";
		if(bidPrice1 > preSettlePrice) {
			color = "#ff5500";
		} else if(bidPrice1 < preSettlePrice) {
			color = "#0bffa4"
		} else {
			color = "#FFFFFF";
		}
		$("#buy_0").text(bidPrice1);
		$("#buy_1").text(bidQty1);
		$("#buy_0").css("color", color);
		//		$("#buy_1").css("color",color);
		if(bidPrice2 > preSettlePrice) {
			color = "#ff5500";
		} else if(bidPrice2 < preSettlePrice) {
			color = "#0bffa4"
		} else {
			color = "#FFFFFF";
		}
		$("#buy_2").text(bidPrice2);
		$("#buy_3").text(bidQty2);
		$("#buy_2").css("color", color);
		//		$("#buy_3").css("color",color);
		if(bidPrice3 > preSettlePrice) {
			color = "#ff5500";
		} else if(bidPrice3 < preSettlePrice) {
			color = "#0bffa4"
		} else {
			color = "#FFFFFF";
		}
		$("#buy_4").text(bidPrice3);
		$("#buy_5").text(bidQty3);
		$("#buy_4").css("color", color);
		//		$("#buy_5").css("color",color);
		if(bidPrice4 > preSettlePrice) {
			color = "#ff5500";
		} else if(bidPrice4 < preSettlePrice) {
			color = "#0bffa4"
		} else {
			color = "#FFFFFF";
		}
		$("#buy_6").text(bidPrice4);
		$("#buy_7").text(bidQty4);
		$("#buy_6").css("color", color);
		//		$("#buy_7").css("color",color);
		if(bidPrice5 > preSettlePrice) {
			color = "#ff5500";
		} else if(bidPrice5 < preSettlePrice) {
			color = "#0bffa4"
		} else {
			color = "#FFFFFF";
		}
		$("#buy_8").text(bidPrice5);
		$("#buy_9").text(bidQty5);
		$("#buy_8").css("color", color);
		//		$("#buy_9").css("color",color);
		if(askPrice1 > preSettlePrice) {
			color = "#ff5500";
		} else if(askPrice1 < preSettlePrice) {
			color = "#0bffa4";
		} else {
			color = "#FFFFFF";
		}
		$("#sell_0").text(askPrice1);
		$("#sell_1").text(askQty1);
		$("#sell_0").css("color", color);
		//		$("#sell_1").css("color",color);
		if(askPrice2 > preSettlePrice) {
			color = "#ff5500";
		} else if(askPrice2 < preSettlePrice) {
			color = "#0bffa4";
		} else {
			color = "#FFFFFF";
		}
		$("#sell_2").text(askPrice2);
		$("#sell_3").text(askQty2);
		$("#sell_2").css("color", color);
		//		$("#sell_3").css("color",color);
		if(askPrice3 > preSettlePrice) {
			color = "#ff5500";
		} else if(askPrice3 < preSettlePrice) {
			color = "#0bffa4";
		} else {
			color = "#FFFFFF";
		}
		$("#sell_4").text(askPrice3);
		$("#sell_5").text(askQty3);
		$("#sell_4").css("color", color);
		//		$("#sell_5").css("color",color);
		if(askPrice4 > preSettlePrice) {
			color = "#ff5500";
		} else if(askPrice4 < preSettlePrice) {
			color = "#0bffa4";
		} else {
			color = "#FFFFFF";
		}
		$("#sell_6").text(askPrice4);
		$("#sell_7").text(askQty4);
		$("#sell_6").css("color", color);
		//		$("#sell_7").css("color",color);
		if(askPrice5 > preSettlePrice) {
			color = "#ff5500";
		} else if(askPrice5 < preSettlePrice) {
			color = "#0bffa4";
		} else {
			color = "#FFFFFF";
		}
		$("#sell_8").text(askPrice5);
		$("#sell_9").text(askQty5);
		$("#sell_8").css("color", color);
		//		$("#sell_9").css("color",color);
	}
	var changeValue = document.getElementById("changeValue");
	var rose = document.getElementById("rose");
	var freshPrices = document.getElementById("freshPrices");
	var volumePricesNumber = document.getElementById("volumePricesNumber");
	var buyPrices = document.getElementById("buyPrices");
	var buyPricesNumber = document.getElementById("buyPricesNumber");
	var sellPrices = document.getElementById("sellPrices");
	var sellPricesNumber = document.getElementById("sellPricesNumber");
	/*
	 * 切换合约
	 * */
	$("#tradeTitle").change(function() {
		var commoditysDataP = commoditysData.Parameters;
		var valSelect = $("#tradeTitle").val();
		var exchangeNo = $("#exchangeNo").val();
		var commodityNo = $("#commodeityNo").val();
		var contractNo = $("#contractNo").val();
		var allMess = commodityNo + contractNo;
		var positionDom = $("li[data-tion-position = '" + allMess + "']");
		if(positionDom.html() == undefined) {
			masendMessage('UnSubscribe', '{"ExchangeNo":"' + exchangeNo + '","CommodityNo":"' + commodityNo + '","ContractNo":"' + contractNo + '"}');
		}
		for(var i = 0; i < commoditysData.length; i++) {
			var str = commoditysData[i].CommodityNo + commoditysData[i].MainContract;
			if(str == valSelect) {
				$("#exchangeNo").val(commoditysData[i].ExchangeNo);
				$("#commodeityNo").val(commoditysData[i].CommodityNo);
				$("#contractNo").val(commoditysData[i].MainContract);
				$("#CommodityName").val(commoditysData[i].CommodityName);
				$("#doSize").val(commoditysData[i].DotSize);
				$("#contractSize").val(commoditysData[i].ContractSize);
				$("#miniTikeSize").val(commoditysData[i].MiniTikeSize);
			}
		}
		var CommodityNo = document.getElementById("CommodityNo");
		var mainTitleFirst = document.getElementsByClassName("mainTitleFirst")[0];
		mainTitleFirst.innerHTML = $("#CommodityName").val();
		CommodityNo.innerHTML = $("#commodeityNo").val() + $("#contractNo").val();
		$("#MainContract").text($("#commodeityNo").val() + $("#contractNo").val());
		$("#xj").removeAttr("checked");
		$("#sj").prop("checked", true);
		var type = $('input:radio[name="prices"]:checked').val();
		if(type == 1) {
			$("#buyFuture table td:nth-child(1) span").css({ "background": "url('../../images/inputRadioBgC.png') no-repeat", "background-size": "100% auto" });
			$("#buyFuture table td:nth-child(2) span").css({ "background": "url('../../images/inputRadioBg.png') no-repeat", "background-size": "100% auto" });
		}
		$("#orderPrice").val("");
		$("#orderPrice").attr("placeholder", "市价");
		$("#buyBtn_P").text("市价");
		$("#sellBtn_P").text("市价");
		sendHistoryMessage(0);
	})

	function insertDATA(DATA) {
		var doSize = $("#doSize").val();
		buyPrices.innerHTML = DATA.Parameters.AskPrice1.toFixed(doSize);
		buyPricesNumber.innerHTML = DATA.Parameters.AskQty1;
		sellPrices.innerHTML = DATA.Parameters.BidPrice1.toFixed(doSize);
		sellPricesNumber.innerHTML = DATA.Parameters.BidQty1;
		volumePricesNumber.innerHTML = DATA.Parameters.TotalVolume;
		freshPrices.innerText = DATA.Parameters.LastPrice.toFixed(doSize);
		if(Number(DATA.Parameters.AskPrice1) - Number(DATA.Parameters.PreSettlePrice) < 0) {
			buyPrices.className = "PricesLeft greenFont";
		} else if(Number(DATA.Parameters.AskPrice1) - Number(DATA.Parameters.PreSettlePrice) > 0) {
			buyPrices.className = "PricesLeft redFont";
		} else {
			buyPrices.className = "PricesLeft whiteFont";
		};
		if(Number(DATA.Parameters.BidPrice1) - Number(DATA.Parameters.PreSettlePrice) < 0) {
			sellPrices.className = "PricesLeft greenFont";
		} else if(Number(DATA.Parameters.BidPrice1) - Number(DATA.Parameters.PreSettlePrice) > 0) {
			sellPrices.className = "PricesLeft redFont";
		} else {
			sellPrices.className = "PricesLeft whiteFont";
		};
		if(Number(DATA.Parameters.LastPrice) - Number(DATA.Parameters.PreSettlePrice) < 0) {
			freshPrices.className = "greenFont";
		} else {
			freshPrices.className = "redFont";
		}
		if(Number(DATA.Parameters.ChangeRate) == 0) {
			changeValue.className = "whiteFont";
		} else if(Number(DATA.Parameters.ChangeRate) > 0) {
			changeValue.className = "redFont";
		} else if(Number(DATA.Parameters.ChangeRate) < 0) {
			changeValue.className = "greenFont";
		}
		rose.innerHTML = DATA.Parameters.ChangeValue.toFixed(doSize) + "/";
		changeValue.innerHTML = DATA.Parameters.ChangeRate.toFixed(2) + "%";
		if(Number(DATA.Parameters.ChangeValue) == 0) {
			rose.className = "whiteFont";
		} else if(Number(DATA.Parameters.ChangeValue) > 0) {
			rose.className = "redFont";
		} else if(Number(DATA.Parameters.ChangeValue) < 0) {
			rose.className = "greenFont";
		}
	}

	function drawChart(val) {
		rawData = [];
		CandlestickChartOption = null;
		CandlestickVolumeChartOption = null;
		CandlestickVolumeData = {
			time: [],
			volume: []
		}
		newData = [];
		sendHistoryMessage(val);
		$("#CandlestickChart").css("opacity", "1");
	}
	var selectButonNum = 0;
	/*
	 获取K线类型
	 **/
	$("#list_type ul li").on("tap", function() {
		$("#selectButon").text($(this).text())
		var val = $(this).val();
		if($(this).hasClass("on") != false) {
			return;
		}
		$(this).addClass("on");
		$(this).siblings('li').removeClass('on');
		if(val == 1) {
			drawChart(val)
		} else if(val == 5) {
			drawChart(val)
		} else if(val == 15) {
			drawChart(val)
		} else if(val == 30) {
			drawChart(val);
		} else if(val == 1440) {
			drawChart(val)
		}
		$("#list_type").css({
			"display": "none"
		});
	});

	$("#selectButon").click(function() {
		if(selectButonNum == 0) {
			drawChart(1);
			selectButonNum = 1;
		}
		$("#list_type ").css({
			"display": "block"
		});
		$("#headerMenu table td").removeClass("mui-active");
		$("#selectButon").addClass("mui-active");
		$("#chartAllDiv").addClass("mui-active");
		$("#Handicap").removeClass("mui-active");
		$("#TimeChart1").removeClass("mui-active");
		$("#CandlestickChart").addClass("mui-active");
		$("#trade").removeClass("mui-active");
		$(".BuyDiv").css({ "display": "block" });
		$("#lightDiagram").removeClass("mui-active");
		$("#lightChartDiv").removeClass("mui-active");
	});
	document.getElementsByClassName("mui-content")[0].addEventListener("tap", function() {
		$("#list_type ").css({
			"display": "none"
		})
	})
	document.getElementById("timeChartMenu").addEventListener("tap", function() {
		if($("#timeChartMenu").hasClass("mui-active") == true) {
			return;
		}
		$("#headerMenu table td").removeClass("mui-active");
		$("#TimeChart1").css("opacity", "0").addClass("mui-active");
		$("#timeChartMenu").addClass("mui-active");
		$("#chartAllDiv").addClass("mui-active");
		$("#Handicap").removeClass("mui-active");
		$("#CandlestickChart").removeClass("mui-active");
		$("#trade").removeClass("mui-active");
		$(".BuyDiv").css({ "display": "block" });
		$("#lightDiagram").removeClass("mui-active");
		$("#lightChartDiv").removeClass("mui-active");
		time = [];
		prices = [];
		timeLabel = []
		timeData = {
			"time": time,
			"prices": prices,
			"timeLabel": timeLabel
		};
		volumeChartTime = [];
		volumeChartPrices = [];
		volumeChartData = {
			"time": volumeChartTime,
			"volume": volumeChartPrices
		};
		var timePrice = [];
		var val = $("#timeChartMenu").val();
		sendHistoryMessage(val);
		var length = $("#positionList .position3").length;
		var text = $("#CommodityNo").text();
		var x = 0;
		if(length != 0) {
			for(var i = 0; i < length; i++) {
				var text1 = $("#positionList .position0").eq(i).text();
				if(text.indexOf(text1) >= 0) {
					x = Number($("#positionList .position3").eq(i).text());
				}
			}
		}
		var option2 = setOption1(x);
		var option1 = volumeChartSetOption(volumeChartData);
		timeChart.resize();
		timeChart.setOption(option2);
		timeChart.resize();
		volumeChart.resize();
		volumeChart.setOption(option1);
		volumeChart.resize();
		$("#TimeChart1").css("opacity", "1");
	});
	document.getElementById("tradeMenu").addEventListener("tap", function() {
		if(vadationIsLogin()) {
			if(plus.storage.getItem("QuotationGuidanceTrade3") == null) {
				plus.storage.setItem("QuotationGuidanceTrade3", "1");
				var height = window.innerHeight;
				var width = window.innerWidth;
				$(".top").css({
					"height": height + "px",
					"width": width + "px",
					"position": "fixed",
					"z-index": "999",
					"display": "block",
					"top": "0px",
					"background": "url('../../images/tipsBG.png') no-repeat",
					"background-size": "60% auto",
					"background-position": "center 85%",
				})
			}
			$("#headerMenu table td").removeClass("mui-active");
			$("#chartAllDiv").removeClass("mui-active");
			$("#tradeMenu").addClass("mui-active");
			$("#trade").addClass("mui-active");
			$("#Handicap").removeClass("mui-active");
			$(".BuyDiv").css({ "display": "none" });
			$("#lightDiagram").removeClass("mui-active");
			$("#lightChartDiv").removeClass("mui-active");
		}
	});

	document.getElementById("HandicapButton").addEventListener("tap", function() {
		$("#headerMenu table td").removeClass("mui-active");
		$("#chartAllDiv").removeClass("mui-active");
		$("#HandicapButton").addClass("mui-active");
		$("#Handicap").addClass("mui-active");
		$("#trade").removeClass("mui-active");
		$("#lightDiagram").removeClass("mui-active");
		$("#lightChartDiv").removeClass("mui-active");
		$(".BuyDiv").css({ "display": "block" });
		$("#lightChartDiv").removeClass("mui-active");
	});
	document.getElementById("lightDiagram").addEventListener("tap", function() {
		if($("#lightDiagram").hasClass("mui-active") == true) {
			return;
		}
		$("#headerMenu table td").removeClass("mui-active");
		$("#lightDiagram").addClass("mui-active");
		$("#chartAllDiv").addClass("mui-active");
		$("#lightChartDiv").addClass("mui-active");
		$("#trade").removeClass("mui-active");
		$("#Handicap").removeClass("mui-active");
		$("#TimeChart1").removeClass("mui-active");
		$("#CandlestickChart").removeClass("mui-active");
		$(".BuyDiv").css({ "display": "block" });
		var option = lightChartDealData(lightChartTime);
		lightChart.setOption(option);
		lightChart.resize();
	});
	document.getElementById("backClose").addEventListener("tap", function() {
		var re = plus.webview.getWebviewById("quotationMain");
		if(re != null && re != undefined) {
			re.reload();
		}
		masendMessage('Logout', '{"UserName":"' + marketUserName + '"}');
		marketSocket.close();
		reconnect = false;
		if(username != null) {
			Trade.doLoginOut(username);
			socket.close();
			loginOutFlag = true;
			loginFail = true;
		};
	});
	document.getElementById("refresh").addEventListener("tap", function() {
		reconnectPage()
	});

	function reconnectPage() {
		plus.webview.getWebviewById("transactionDetails").reload();
	}
	var checkNetWorkSateInterval = setInterval(function() {
		reconnectMarketSocket()
	}, 500);
	var netWorkState = true;
	var netWorkTipsTimeout = null;
	var checkNetWorkStateNum = 0;

	function reconnectMarketSocket() {
		var netWorkStateType = mui.checkNetworkState();
		var netWorkTips = document.getElementById("netWorkTips");
		if(netWorkStateType[plus.networkinfo.getCurrentType()] == "None connection") {
			netWorkState = false;
			if(checkNetWorkStateNum == 0) {
				netWorkTips.style.display = "block";
				netWorkTipsTimeout = setTimeout(function() {
					netWorkTips.style.display = "none";
					$(".mui-bar").css("background-color", "#e9392a");
				}, 2000);
				checkNetWorkStateNum++;
			}
		} else {
			if(netWorkState == false) {
				initMarketSocket();
			}
			checkNetWorkStateNum = 0;
			netWorkState = true;
			clearTimeout(netWorkTipsTimeout);
		}
	}
});

function masendMessage(method, parameters) {
	marketSocket.send('{"Method":"' + method + '","Parameters":' + parameters + '}');
}

function getSubscript(data) {
	for(var i = 0; i <= data.length - 1; i++) {
		if(data[i] == "DateTimeStamp") {
			DateTimeStampSubscript = i;
		} else if(data[i] == "LastPrice") {
			LastPriceSubscript = i;
		} else if(data[i] == "OpenPrice") {
			OpenPriceSubscript = i;
		} else if(data[i] == "LowPrice") {
			LowPriceSubscript = i
		} else if(data[i] == "HighPrice") {
			HighPriceSubscript = i
		} else if(data[i] == "Volume") {
			VolumeSubscript = i;
		}
	}
}

function dealOnRtnQuoteData(data, totalVolume) {
	var dosizeL = Number($("#doSize").val());
	var Parameters = data.Parameters;
	var text = $("#selectButon").text();
	var range = 1
	if(text == "K线" || text == "1分") {
		range = 1;
	} else if(text == "5分") {
		range = 5;
	} else if(text == "15分") {
		range = 15;
	} else if(text == "30分") {
		range = 30;
	} else if(text == "日K") {
		return
	}
	if(totalVolume == "-") {
		totalVolume = 0;
		return
	}
	var Volume = Number(Parameters.TotalVolume) - Number(totalVolume);
	var lastVolume = Number(volumeChartData.volume[volumeChartData.volume.length - 1]);
	if(Volume <= 0) {
		Volume = 0;
	}
	var freshVolume = lastVolume + Volume;
	var lastVolume1 = 0;
	var freshVolume1 = 0;
	if(chartDataC != undefined) {
		lastVolume1 = Number(CandlestickVolumeData.volume[CandlestickVolumeData.volume.length - 1]);
		freshVolume1 = lastVolume1 + Volume;
	}
	var lastPrices = Number(Parameters.LastPrice).toFixed(dosizeL);
	var DateTimeStamp = Parameters.DateTimeStamp;
	var DateTimeStamp1 = DateTimeStamp.replace(/-/g, "/");
	if(timeData.time[timeData.time.length - 1] == undefined) {
		return
	}
	var oldTime1 = (timeData.time[timeData.time.length - 1]).replace(/-/g, "/");
	var oldTime = Math.round(new Date(oldTime1).getTime() / 1000);
	var nowShjian = Math.round(new Date(DateTimeStamp1).getTime() / 1000)
	var time1 = parseInt(nowShjian / (60 * range));
	var newTime = Number(time1 * 60 * range);
	var time5 = new Date(parseInt(newTime) * 1000);
	var time6 = getNowFormatDate(time5);
	var time2 = time6.split(" ");
	var str1 = time2[1].split(":");
	var str2 = str1[0] + ":" + str1[1];
	var x = 0;
	var length = $("#positionList .position3").length;
	var text = $("#CommodityNo").text();
	if(length != 0) {
		for(var i = 0; i < length; i++) {
			var text1 = $("#positionList .position0").eq(i).text();
			if(text.indexOf(text1) >= 0) {
				x = Number($("#positionList .position3").eq(i).text());
			}
		}
	}
	if(oldTime == newTime) {
		var lastPrices1 = lastPrices;
		if(timeData.prices[timeData.prices.length - 1] == lastPrices1 && volumeChartData.volume[volumeChartData.volume.length - 1] == freshVolume) {

		} else {
			timeData.prices[timeData.prices.length - 1] = lastPrices1;
			volumeChartData.volume[volumeChartData.volume.length - 1] = freshVolume;
			drawChartTime(x);
		}
	} else {
		timeData.timeLabel.push(str2);
		timeData.time.push(time6);
		timeData.prices.push(lastPrices);
		volumeChartData.time.push(str2);
		volumeChartData.volume.push(0);
		drawChartTime(x);
	}
	if(chartDataC != undefined) {
		var oldTime2 = (chartDataC.time[chartDataC.time.length - 1]).replace(/-/g, "/");
		var oldTime3 = Math.round(new Date(oldTime2).getTime() / 1000);
		if(oldTime3 == newTime) {
			var length = chartDataC.values.length;
			var lowPrices = Number(chartDataC.values[length - 1][2]);
			var highPrices = Number(chartDataC.values[length - 1][3]);
			var closePrices = lastPrices;
			if(lastPrices >= highPrices) {
				highPrices = lastPrices
			}
			if(lowPrices >= highPrices) {
				lowPrices = lastPrices
			}
			chartDataC.values[length - 1] = [chartDataC.values[length - 1][0], closePrices, lowPrices, highPrices];
			CandlestickVolumeData.volume[CandlestickVolumeData.volume.length - 1] = freshVolume1;
		} else {
			chartDataC.categoryData.push(str2)
			chartDataC.time.push(time6);
			chartDataC.values.push([lastPrices, lastPrices, lastPrices, lastPrices]);
			CandlestickVolumeData.time.push(str2)
			CandlestickVolumeData.volume.push(0);
			chartDataC.categoryData = chartDataC.categoryData.slice(-40);
			chartDataC.values = chartDataC.values.slice(-40);
			CandlestickVolumeData.volume = CandlestickVolumeData.volume.slice(-40);
			CandlestickVolumeData.time = CandlestickVolumeData.time.slice(-40);
		}
	}
	drawChartCandlestick(x);
}

function drawChartTime(x) {
	var value = $("#timeChartMenu").hasClass("mui-active");
	if(value) {
		var option = setOption1(x);
		timeChart.setOption(option);
		timeChart.resize();
		var option1 = volumeChartSetOption(volumeChartData);
		volumeChart.setOption(option1);
		volumeChart.resize();
		timeChart.group = "group1";
		volumeChart.group = "group1";
	}
}

function drawChartCandlestick(x) {
	var value = $("#selectButon").hasClass("mui-active");
	if(chartDataC != undefined) {
		if(value) {
			var option2 = setOption(chartDataC, x);
			myChart.setOption(option2);
			var option3 = CandlestickVolumeChartSetoption1(CandlestickVolumeData);
			CandlestickVolumeChart.resize();
			CandlestickVolumeChart.setOption(option3);
			CandlestickVolumeChart.resize();
			CandlestickVolumeChart.group = "group2";
			myChart.group = "group2";
		}
	}
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