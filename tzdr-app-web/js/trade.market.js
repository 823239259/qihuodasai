var marketSocket = new WebSocket("ws://192.168.0.213:9006");
var initSubscribe = 0;
var marketLoadParam = {}
marketSocket.onopen = function() {
	Market.doLogin("13677622344", "a123456");
}
marketSocket.onclose = function() {
	alert("行情关闭");
}
marketSocket.onmessage = function(evt) {
		var data = evt.data;
		var jsonData = JSON.parse(data);
		var method = jsonData.Method;
		if (method == "OnRspLogin") {
			Market.doCommodity("");
		} else if (method == "OnRspQryCommodity") {
			var commoditys = jsonData.Parameters;
			var size = commoditys.length;
			if (initSubscribe == 0) {
				for (var i = 0; i < size; i++) {
					var comm = commoditys[i];
					Market.doSubscribe(comm.ExchangeNo, comm.CommodityNo, comm.MainContract);
				}
				initSubscribe++;
			}
			var newCommdityNo = commoditys.CommodityNo;
			var newContractNo = commoditys.ContractNo;
			//如果是当前合约与品种更新乘数
			if (valiationIsPresent(newCommdityNo, newContractNo)) {
				$("#contractSize").val(commoditys.ContractSize);
			}
		} else if (method == "OnRtnQuote") {
			var subscribeParam = jsonData.Parameters;
			var newCommdityNo = subscribeParam.CommodityNo;
			var newContractNo = subscribeParam.ContractNo;
			marketLoadParam[newCommdityNo] = subscribeParam;
			//如果是当前合约与品种更新行情数据，和浮动盈亏
			if (valiationIsPresent(newCommdityNo, newContractNo)) {
				updateLoadWebParam(subscribeParam);
				updateFloatProfit();
			}
		}
	}
	/**
	 * 更新行情数据 
	 * @param {Object} param
	 */
function updateLoadWebParam(param) {
	$("#lastPrice").text(param.LastPrice);
	$("#sellLastPrice").text(param.AskPrice1);
	$("#buyLastPrice").text(param.BidPrice1);
	$("#totalVolume").text(param.TotalVolume);
	$("#askQty").text(param.AskQty1);
	$("#bidQty").text(param.BidQty1);
	$("#buyBtn_P").text(param.BidPrice1);
	$("#sellBtn_P").text(param.AskPrice1);
}
/**
 * 更新浮动盈亏 
 */
function updateFloatProfit() {
	for (var i = 0; i < positionsIndex; i++) {
		var $this = $("li[contract-code-position='" + positionContractCode[i] + "'] span[class = 'position4']");
		var $thisAvgPrice = $("li[contract-code-position='" + positionContractCode[i] + "'] span[class = 'position3']");
		var $thisHoldNum = $("li[contract-code-position='" + positionContractCode[i] + "'] span[class = 'position2']");
		//验证该平仓数据是否存在列表中
		if ($this.html() == undefined) {
			continue;
		}
		var floatProfit = doGetFloatingProfit(parseFloat($("#lastPrice").text()), parseFloat($thisAvgPrice.text()) , $("#contractSize").val(),$("#miniTikeSize").val(),parseInt($thisHoldNum.text()));
		$this.text(floatProfit);
	}
}
/**
 * 验证是否是当前品种和合约 
 * @param {Object} commodeityNo 品种
 * @param {Object} contractNo 合约
 */
function valiationIsPresent(commodeityNo, contractNo) {
	if (commodeityNo == $("#commodeityNo").val() && contractNo == $("#contractNo").val()) {
		return true;
	} else {
		return false;
	}
}