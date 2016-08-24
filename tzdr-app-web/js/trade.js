//全局的保存当前选中持仓的组合合约号
var positionWholeContract = null;
//全局的保存当前选中持仓的索引
var positionWholeIndex = 0;
//全局的保存当前选中挂单的orderid
var designateWholeContracts = null;
//全局的保存当前选中挂单的索引
var designateWholeIndex = 0;
//
var orderWholeContracts = null;
//
var tradeWholeContracts = null;
//持仓
var positions = {};
//持仓-合约名称为key
var positionsContract = {};
//保存合约名称
var positionContractCode = {};
//挂单
var designates = {};
//挂单-合约名称为key
var designatesContract = {};
//委托
var entrusts = {};
//成交
var trades = {};
var positionsIndex = 0;
var designatesIndex = 0;
var entrustsIndex = 0;
var tradesIndex = 0;
var kong = "<span style='color:green;'>空</span>";
var duo = "<span style='color:red;'>多</span>";
var username = "Q517029969";
var password = $.base64.encode("123456");
socket.onopen = function() {
	Trade.doLogin(username, password);
}
socket.onclose = function() {
	alert("链接关闭");
}
socket.onmessage = function(evt) {
	var dataString = evt.data;
	var data = JSON.parse(dataString);
	var method = data.Method;
	var parameters = data.Parameters;
	if (parameters != null) {
		if (method == "OnRspLogin") {
			var code = parameters.Code;
			var loginMessage = parameters.Message;
			if (code == 0) {
				initDom();
			} else {
				alert(loginMessage);
			}
			//查询个人账户信息回复
		} else if (method == "OnRspQryAccount") {
			var accountParam = parameters;
			updateBalance(accountParam);
			//查询订单信息回复
		} else if (method == "OnRspQryOrder") {
			var orderStatus = parameters.OrderStatus;
			var orderParam = parameters;
			appendOrder(orderParam);
			if (orderStatus < 3) {
				appendDesignates(orderParam);
			}
			//查询成交记录回复
		} else if (method == "OnRspQryTrade") {
			appendTradeSuccess(parameters);
			//查询持仓信息回复
		} else if (method == "OnRspQryHold") {
			var positionParam = parameters;
			appendPostionAndUpdate(positionParam);
			//报单录入请求回复
		} else if (method == "OnRspOrderInsert") {
			var insertOrderParam = parameters;
			appendOrder(insertOrderParam);
			appendDesignates(insertOrderParam);
			//订单状态通知
		} else if (method == "OnRtnOrderState") {
			var orderParam = parameters;
			updateOrder(orderParam);
			var orderStatusWeHooks = orderParam.OrderStatus;
			//当订单状态改变
			var orderId = orderParam.OrderID;
			if (orderStatusWeHooks == 3 || orderStatusWeHooks == 4) {
				delDesignatesDomByOrderId(orderId);
			} else if (orderStatusWeHooks == 0) {
				appendDesignates(orderParam);
			} else if (orderStatusWeHooks == 1 || orderStatusWeHooks == 2) {
				updateDesignatesDom(orderParam);
			}
			//订单成交通知
		} else if (method == "OnRtnOrderTraded") {
			var tradeParam = parameters;
			appendTradeSuccess(tradeParam);
			appendPostionAndUpdate(tradeParam);
			//资金变化通知
		} else if (method == "OnRtnMoney") {
			var accountParam = parameters;
			updateBalance(accountParam)
		}
	}
}
/**
 * 请求数据-初始化dom 
 */
function initDom(){
	Trade.doAccount(username);
	Trade.doOrder(username);
	Trade.doTrade(username);
	Trade.doHold(username);
}
/**
 * 追加和更新持仓信息 
 * @param {Object} positionParam
 */
function appendPostionAndUpdate(positionParam){
	if(validationPostionIsExsit(positionParam)){
		appendPosition(positionParam);
	}else{
		updatePositionDom(positionParam);
	}
}
/**
 * 追加持仓信息 
 * @param {Object} data
 */
function appendPosition(data){
	var holdParam = data;
	var drection = holdParam.Drection;
	var drectionText = analysisPositionDrection(drection);
	var orderId = holdParam.TradeNo;
	var contractCode = holdParam.ContractCode;
	var holdNum = holdParam.HoldNum;
	if(holdNum == undefined){
		holdNum = holdParam.TradeNum;
	}
	var price = holdParam.OpenAvgPrice;
	if(price == undefined){
		price = holdParam.TradePrice;
	}
	var floatingProft = 0.00;
	if(validationLastPrice()){
		floatingProft = price;
	}else{
		floatingProft = doGetFloatingProfit($("#lastPrice").text(),$("#contractSize").val(),drection);
	}
	var cls = 'position-index'+positionsIndex;
	var html = '<li data-tion-position = '+contractCode+' data-index = '+positionsIndex+' contract-code-position = '+contractCode+'   class = "'+cls+'" style="width: 720px;">'
				+ '<a class="mui-navigate-right" >'
				+ '		<p>'
				+ '			<span class = "position0">'+holdParam.ExchangeNo+""+contractCode+'</span>'
				+ '			<span class = "position1" data-drection = '+drection+'>'+drectionText+'</span>'
				+ '			<span class = "position2">'+holdNum+'</span>'
				+ '			<span class = "position3">'+price+'</span>'
				+ '			<span class = "position4">'+floatingProft+'</span>'
				+ '			<span class = "position5" style = "display:none">'+holdParam.CommodityNo+'</span>'
				+ '			<span class = "position6" style = "display:none">'+holdParam.ContractNo+'</span>'
				+ '         <span class = "position7" style = "display:none">'+holdParam.ExchangeNo+'</span>'
				+ '		</p>'
				+ '	</a>'
				+ '</li>';
	$("#positionList").append(html);
	positions[orderId] = holdParam;
	//添加储存数据
	positionsContract[contractCode] = createPostionsParam(holdParam);
	positionContractCode[positionsIndex] = contractCode;
	addPositionBindClick(cls);
	updatePositionIndex();
}
/**
 * 验证持仓信息是否存在 
 */
function validationPostionIsExsit(param){
	var contractCode = param.ContractCode;
	var positionParam = positionsContract[contractCode];
	if(positionParam == undefined || positionParam == "undefined" || positionParam == null){
		return true;
	}else{
		return false;
	}
}
/**
 * 
 * 更新持仓信息 
 * @param {Object} positonParam 最新数据
 */
function updatePositionDom(positonParam){
	var contractCode = positonParam.ContractCode;
	var orderId = positonParam.TradeNo;
	var drection = positonParam.Drection;
	var orderNum = parseInt(positonParam.HoldNum);
	if(isNaN(orderNum)){
		orderNum = parseInt(positonParam.TradeNum);
	}
	var newprice = positonParam.OpenAvgPrice;
	if(newprice == undefined){
		newprice = positonParam.TradePrice;
	}
	var drectionText = null;
	var $thisHoldNum = $("li[contract-code-position='"+contractCode+"'] span[class = 'position2']");
	var $thisDrectionText = $("li[contract-code-position='"+contractCode+"'] span[class = 'position1']");
	var $thisPrcie = $("li[contract-code-position='"+contractCode+"'] span[class = 'position3']");
	var holdNum =  parseInt($thisHoldNum.text());
	var oldDrection = parseInt($thisDrectionText.attr("data-drection"));
	var oldPrice = parseFloat($thisPrcie.text()) *  holdNum;
	var price = parseFloat(newprice) * orderNum;
	if(drection == oldDrection){
		holdNum = holdNum + orderNum;
		price = price + oldPrice;
		drectionText = analysisPositionDrection(drection);
	}else if(drection != oldDrection){
		holdNum = holdNum - orderNum;
		price = oldPrice - price;
		if(holdNum == 0){	
			//当持仓为空时，清理dom节点和存储数据
			delPositionDom(contractCode);
			deletePositionsContractCode(contractCode);
		//当drection为0时，上一次更新数据为’多‘，holdNum 小于0时表示这次这次更新数据买卖方向变为’空‘
		}else if(holdNum < 0 && oldDrection == 0){
			drectionText = kong;
			drection = 1;
		//当drection为1时，上一次数据更新为’空‘，holdNum小于0时表示这次更新数据买卖方向变为’多‘
		}else if(holdNum < 0 && oldDrection == 1){
			drectionText = duo;
			drection = 0;
		}else if(holdNum > 0 && oldDrection == 0){
			drectionText =duo;
			drection = 0;
		}else if(holdNum > 0 && oldDrection == 1){
			drectionText = kong;
			drection = 1;
		}
	}
	if(holdNum != 0){
		//$thisPrcie.text(doGetOpenAvgPrice(price,holdNum));
		$thisHoldNum.text(holdNum);
		$thisDrectionText.html(drectionText);
		$thisDrectionText.attr("data-drection",drection);
		//添加数据到本地储存
		positions[orderId] = positonParam;
		//更新储存数据
		var postContract = positionsContract[contractCode];
		postContract.holdNum = holdNum;
		postContract.drection = drectionText;
	}
}
/**
 * 追加或更新挂单信息 
 * @param {Object} param
 */
function appendDesignatesAndUpdate(param){
	if(validationDesignatesIsExsit(param)){
		appendDesignates(param);
	}else{
		updateDesignatesDom(param);
	}
}
/**
 * 追加 挂单信息
 * @param {Object} data
 */
function appendDesignates(data){
	console.log(data);
	var designatesParam = data;
	var drection = designatesParam.Drection;
	var orderNum = designatesParam.OrderNum;
	var tradeNum = designatesParam.TradeNum;
	var drectionText = analysisDrection(drection);
	var orderStatus = designatesParam.OrderStatus
	var cdNum = orderNum - tradeNum;
	if(orderStatus == 4){
		if(cdNum == 0)return;
	}
	var orderId = designatesParam.OrderID;
	var desContract = designatesParam.ContractCode;
	var cls = 'designate-index'+designatesIndex;
	var html =   '<li data-tion-designates = '+orderId+' data-orderId = '+orderId+' data-index='+designatesIndex+' contract-code-designates = '+desContract+'   class = "'+cls+'" style="width: 720px;">'
				+'	<a class="mui-navigate-right" >'
				+'		<p >'
				+'			<span class = "desig0">'+desContract+'</span>'
				+'			<span class = "desig1" data-drection = '+drection+'>'+drectionText+'</span>'
				+'			<span class = "desig2">'+designatesParam.OrderPrice+'</span>'
				+'			<span class = "desig3">'+designatesParam.OrderNum+'</span>'
				+'			<span class = "desig4">'+cdNum+'</span>'
				+'			<span class = "desig5">'+designatesParam.InsertDateTime+'</span>'
				+'			<span class = "desig6" style = "display:none;">'+designatesParam.OrderSysID+'</span>'
				+ '			<span class = "desig7" style = "display:none">'+designatesParam.CommodityNo+'</span>'
				+ '			<span class = "desig8" style = "display:none">'+designatesParam.ContractNo+'</span>'
				+ '         <span class = "desig9" style = "display:none">'+designatesParam.ExchangeNo+'</span>'
				+ '         <span class = "desig10" style = "display:none">'+orderId+'</span>'
				+'		</p>'
				+'	</a>'
				+'</li>';
	$("#postersOrder").append(html);
	designates[orderId] = designatesParam;
	addDesignatesBindClick(cls);
	designatesContract[desContract] = createDesignatesParam(designatesParam);
	updateDesignatesIndex();
}
/**
 * 验证挂单信息是否存在 
 */
function validationDesignatesIsExsit(param){
	var contractCode = param.ContractCode;
	var designatesParam = designatesContract[contractCode];
	if(designatesParam == undefined || designatesParam == "undefined" || designatesParam == null){
		return true;
	}else{
		return false;
	}
}
/**
 * 
 * 更新挂单信息 
 * @param {Object} positonParam 最新数据
 */
function updateDesignatesDom(designatesParam){
	var contractCode = designatesParam.ContractCode;
	var orderId = designatesParam.OrderID;
	var drection = designatesParam.Drection;
	var drectionText = analysisDrection(drection);
	var orderNum = parseInt(designatesParam.OrderNum);
	var orderStatus = designatesParam.OrderStatus;
	var $thisHoldNum = $("li[data-tion-designates='"+orderId+"'] span[class = 'desig4']");
	var holdNum =  parseInt($thisHoldNum.text());
	if(orderStatus == 2){
		holdNum = holdNum - orderNum;
	}
	if(holdNum == 0){
		//当挂单为0时，清理dom节点和存储数据
		delDesignatesDomByOrderId(orderId);
		//deleteDesignatesContractCode(contractCode);
	}else if(holdNum != 0){
		$thisHoldNum.text(holdNum);
		//添加数据到本地储存
		designates[orderId] = designatesParam;
		//更新储存数据
		var desiContract = designatesContract[contractCode];
		desiContract.cdNum = holdNum;
		desiContract.drection = drectionText;
	}
}
/**
 * 追加订单(委托)信息 
 * @param {Object} data
 */
function appendOrder(data){
	var orderParam = data;
	var drection = orderParam.Drection;
	var orderStatus = orderParam.OrderStatus;
	var orderNum = orderParam.OrderNum;
	var tradeNum = orderParam.TradeNum;
	var cdNum = 0;
	var drectionText = analysisDrection(drection);
	var orderStatusText = analysisOrderStatus(orderStatus);
	if(orderStatus == 4){
		cdNum = orderNum - tradeNum;
	}
	var orderId = orderParam.OrderID;
	var cls = 'entrust'+entrustsIndex;
	var html = '<li data-tion-order='+orderId+' contract-code-order = '+orderParam.ContractCode+'  class = "'+cls+'" style="width: 960px;">'
				+'	<a class="mui-navigate-right" >'
				+'		<p >'
				+'			<span class = "order0">'+orderParam.ContractCode+'</span>'
				+'			<span class = "order1">'+orderStatusText+'</span>'
				+'			<span class = "order2">'+drectionText+'</span>'
				+'			<span class = "order3">'+orderParam.OrderPrice+'</span>'
				+'			<span class = "order4">'+orderNum+'</span>'
				+'			<span class = "order5">'+tradeNum+'</span>'
				+'			<span class = "order6">'+cdNum+'</span>'
				+'			<span class = "order7">'+orderParam.InsertDateTime+'</span>'
				+'		</p>'
				+'	</a>'
				+'</li>';
	$("#Entrust").append(html);
	entrusts[orderId] = orderParam;
	addOrderBindClick(cls);
	updateEntrustsIndex();
}
/**
 * 更新订单(委托)信息 
 * @param {Object} data
 */
function updateOrder(data){
	var orderParam = data;
	var orderId = data.OrderID;
	var $thisOrderStatusText = $("li[data-tion-order='"+orderId+"'] span[class = 'order1']");
	var $thisTradeNum = $("li[data-tion-order= '"+orderId+"'] span[class = 'order5']");
	var $thiscdNum = $("li[data-tion-order='"+orderId+"'] span[class = 'order6']");
	var orderStatus = orderParam.OrderStatus;
	var tradeNum = orderParam.TradeNum;
	var orderNum = orderParam.OrderNum;
	var cdNum = 0;
	if(orderStatus == 4){
		cdNum = orderNum = tradeNum;
	}
	$thisOrderStatusText.text(analysisOrderStatus(orderStatus));
	$thiscdNum.text(cdNum);
	$thisTradeNum.text(tradeNum);	
}
/**
 * 追加交易成功的信息 
 * @param {Object} data
 */
function appendTradeSuccess(data){
	var tradeParam = data;
	var drection = tradeParam.Drection;
	var drectionText = analysisDrection(drection);
	var orderId = tradeParam.OrderID;
	var cls = 'trade'+tradesIndex;
	var html = '<li data-tion-trade = '+orderId+' contract-code-trade = '+tradeParam.ContractCode+'   class = "'+cls+'" style="width: 600px;">'
				+'<a class="mui-navigate-right" >'
				+'	<p>'
				+'		<span class = "trade0">'+tradeParam.ContractCode+'</span>'
				+'		<span class = "trade1">'+drectionText+'</span>'
				+'      <span class = "trade2">'+tradeParam.TradePrice+'</span>'
				+'		<span class = "trade3">'+tradeParam.TradeNum+'</span>'
				+'		<span class = "trade4">'+tradeParam.TradeDateTime+'</span>'
				+'	</p>'
				+'	</a>'
				+'</li>';
	$("#Deal").append(html);
	trades[orderId] = tradeParam;
	addTradeBindClick(cls);
	updateTradesIndex();
}
/**
 * 删除持仓中的元素节点 
 * @param {Object} orderId 删除节点的订单Id
 */
function delPositionDom(contractCode){
	$(function(){
		$("li[data-tion-position='"+contractCode+"']").remove();
	});
}
/**
 * 删除成功中的元素节点
 * @param {Object} orderId
 */
function delTradeDom(orderId){
	$("li[data-tion-trade='"+orderId+"']").remove();
}
/**
 * 删除委托中的元素节点 
 * @param {Object} orderId
 */
function delOrderDom(orderId){
	$("li[data-tion-order='"+orderId+"']").remove();
}
/**
 * 删除 挂单中的元素节点
 * @param {Object} contractCode
 */
function delDesignatesDom(contractCode){
	$("li[data-tion-designates='"+contractCode+"']").remove();
}
/**
 * 删除挂单中的元素节点 
 * @param {Object} orderId
 */
function delDesignatesDomByOrderId(orderId){
	$("li[data-orderId='"+orderId+"']").remove();
}

/**
 * 解析买卖方向 
 * @param {Object} drection
 */
function analysisDrection(drection){
	var drectionText = "";
	if(drection == 0 ){
		drectionText = "买";
	}else if(drection == 1){
		drectionText = "卖";
	}
	return drectionText;
}
/**
 * 解析持仓买卖方向
 * @param {Object} drection
 */
function analysisPositionDrection(drection){
	var drectionText = '';
	if(drection == 0){
		drectionText = duo;
	}else if(drection == 1){
		drectionText = kong;
	}
	return drectionText;
}
/**
 *  解析交易状态
 * @param {Object} orderStatus
 */
function analysisOrderStatus(orderStatus){
	var orderStatusText = "";
	if(orderStatus == 0){
		orderStatusText = "已提交";
	}else if(orderStatus == 1){
		orderStatusText = "排队中";
	}else if(orderStatus == 2){
		orderStatusText = "部分提交";
	}else if(orderStatus == 3){
		orderStatusText = "完全提交";
	}else if(orderStatus == 4){
		orderStatusText = "已撤单";
	}else if(orderStatus == 5){
		orderStatusText = "下单失败";
	}else if(orderStatus == 6){
		orderStatusText == "未知";
	}
	return orderStatusText;
}
/**
 * 绑定持仓点击事件
 * @param {Object} cls
 */
function addPositionBindClick(cls){
	$(function(){
		$("."+cls+"").bind("click",function(){
			var $this = $(this);
			positionWholeContract = $this.attr("data-tion-position");
		    positionWholeIndex = $this.attr("data-index");
		});
	});
}
/**
 * 绑定成功点击事件
 * @param {Object} cls
 */
function addTradeBindClick(cls){
	$(function(){
		$("."+cls+"").bind("click",function(){
			var $this = $(this);
			tradeWholeContracts = $this.attr("data-tion-trade");
		});
	});
}
/**
 * 绑定委托点击事件
 * @param {Object} cls
 */
function addOrderBindClick(cls){
	$(function(){
		$("."+cls+"").bind("click",function(){
			var $this = $(this);
			orderWholeContracts = $this.attr("data-tion-order");
		});
	});
}
/**
 * 绑定挂单点击事件
 * @param {Object} cls
 */
function addDesignatesBindClick(cls){
	$(function(){
		$("."+cls+"").bind("click",function(){
			var $this = $(this);
			designateWholeContracts = $this.attr("data-tion-designates");
			designateWholeIndex = $this.attr("data-index");
		});
	});
}
/**
 * 更新用户资金信息
 * @param {Object} accountParam
 */
function updateBalance(accountParam){
	$(function(){
		$("#todayBalance").text((parseFloat(accountParam.TodayBalance)).toFixed(2));
		$("#deposit").text((parseFloat(accountParam.Deposit)).toFixed(2));
		$("#todayCanUse").text((parseFloat(accountParam.TodayCanUse)).toFixed(2));
	});
}
/**
 * 更新持仓索引 
 */
function updatePositionIndex(){
	positionsIndex++;
	
}
/**
 * 更新撤单索引 
 */
function updateDesignatesIndex(){
	designatesIndex++;
}
/**
 * 更新委托索引 
 */
function updateEntrustsIndex(){
	entrustsIndex++;
}
/**
 * 更新交易成功索引 
 */
function updateTradesIndex(){
	tradesIndex++;
}
/**
 * 移除全局缓存持仓的单条信息
 * @param {Object} param
 */
function deletePositions(param){
	if(!delete positions[param]){
		positions[param] = null;
	}
}
/**
 * 移除全局缓存持仓的品种合约
 * @param {Object} param
 */
function deletePositionsContractCode(param){
	if(!delete positionsContract[param]){
		positionsContract[param] = null;
	}
}
/**
 * 移除全局缓存挂单的品种合约
 * @param {Object} param
 */
function deleteDesignatesContractCode(param){
	if(!delete designatesContract[param]){
		positionsContract[param] = null;
	}
}


/**
 * 计算开仓均价 
 * @param {Object} price
 * @param {Object} num
 */
function doGetOpenAvgPrice(price,num){
	return parseFloat(price / num).toFixed(2);
}