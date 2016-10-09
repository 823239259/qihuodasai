/**
 * 初始化交易
 */
initTrade();
//持仓发送请求次数记录
var holdFirstLoadDataIndex = 0;
//个人账户信息发送请求次数纪录
var accountFirstLoadDataIndex = 0;
//委托发送请求次数记录
var orderFirsetLoadDataIndex = 0;
//成功记录发送请求次数记录
var tradeFirsetLoadDataIndex = 0;
/**
 * 用户登陆成功加载数据
 */
function LoginForwardInitLoadData() {
	if (holdFirstLoadDataIndex == 0) {
		Trade.doHold(username);
		holdFirstLoadDataIndex++;
	}
}
/**
 * 线性加载数据
 * @param {Object} method 返回的数据标识
 */
function linearlyLoadData(method) {
	if (method == "OnRspQryHold") {
		if (orderFirsetLoadDataIndex == 0) {
			Trade.doOrder(username);
			orderFirsetLoadDataIndex++;
		}
	} else if (method == "OnRspQryOrder") {
		if (tradeFirsetLoadDataIndex == 0) {
			Trade.doTrade(username);
			tradeFirsetLoadDataIndex++;
		}
	} else if (method == "OnRspQryTrade") {
		if (accountFirstLoadDataIndex == 0) {
			Trade.doAccount(username);
			accountFirstLoadDataIndex++;
		}
	}
}
/**
 * 处理返回数据
 * @param {Object} EVT
 */
function handleData(evt){
	var dataString = evt.data;
	var data = JSON.parse(dataString);
	var method = data.Method;
	var parameters = data.Parameters;
	if (parameters != null) {
		if (method == "OnRspLogin") {
			var code = parameters.Code;
			var loginMessage = parameters.Message;
			//登录成功加载
			if (code == 0) {
				LoginForwardInitLoadData();
				$("#show_login").hide();
				$("#show_user_info").show();
				setIsLogin(true);
			} else {
				//登录失败清理数据
				loginOut();
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
			var inserOrderStatus = insertOrderParam.OrderStatus;
			if (inserOrderStatus < 3) {
				appendDesignates(insertOrderParam);
			}
			//订单状态通知
		} else if (method == "OnRtnOrderState") {
			var orderParam = parameters;
			updateOrder(orderParam);
			var orderStatusWeHooks = orderParam.OrderStatus;
			//当订单状态改变
			var orderId = orderParam.OrderID;
			if (orderStatusWeHooks == 3 || orderStatusWeHooks == 4 || orderStatusWeHooks == 5) {
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
		} else if (method = "OnError") {
			var code = parameters.Code;
			var loginMessage = parameters.Message;
		}
	}
}
/**
 * 修改用户账户信息
 * @param {Object} parama 用户信息的json对象
 */
var uehIndex = 0;
var loadCachBanlance = {};
var loadCachDeposit = {};
var loadCachCanuse = {};
var loadCurrencyRate = {};
var loadCachAccountNo = {};
function updateBalance(parama){
	var accountNo = accountParam.AccountNo;
	var cachBanlace = loadCachBanlance[accountNo];
	var banlance = accountParam.TodayBalance;
	var deposit = accountParam.Deposit;
	var canuse = accountParam.TodayCanUse;
	var currency = accountParam.CurrencyRate; 
	loadCachBanlance[accountNo] = banlance;
	loadCachDeposit[accountNo] = deposit;
	loadCachCanuse[accountNo] = canuse;
	if(currency != undefined)
		loadCurrencyRate[accountNo] = currency;
	if(cachBanlace == undefined || cachBanlace.length <= 0){
		loadCachAccountNo[uehIndex] = accountNo;
		uehIndex++;
	}
	var $banlance = 0.00;
	var $deposit = 0.00
	var $canuse = 0.00
	$(function(){
		for(var i = 0 ; i < uehIndex; i++){
			var ac = loadCachAccountNo[i]; 
			$banlance = $banlance + loadCachBanlance[ac] * loadCurrencyRate[ac];
			$deposit = $deposit + loadCachDeposit[ac] * loadCurrencyRate[ac];
			$canuse = $canuse + loadCachCanuse[ac]  * loadCurrencyRate[ac];
		}
		$("#todayBalance").text(parseFloat($banlance).toFixed(2));
		$("#deposit").text(parseFloat($deposit).toFixed(2));
		$("#todayCanUse").text(parseFloat($canuse).toFixed(2));
		$("#floatingProfit").text(parama.floatingProfit);
		$("#closeProfit").text(param.CloseProfit);
	});
};
var orderIndex = 0;
/**
 * 添加用户委托信息
 * @param {Object} param 委托信息的json对象
 */
function appendOrder(param){
	var contractCode = param.ContractCode;
	var drectionText = analysisBusinessDirection(param.drection);
	var orderPrice = param.orderPrice;
	var orderStatus = param.OrderStatus;
	var ordreStatusText = analysisOrderStatus(orderStatus);
	var orderNum = param.OrderNum;
	var tradeNum = param.TradeNum;
	var triggerPrice = param.TriggerPrice;
	var tradePrice = param.TradePrice;
	var orderId = param.OrderID;
	var cls = "order-index" + orderIndex;
	var html = '<ul class="tab_content '+cls+'" data-index-order = "'+orderIndex+'" data-tion-order = "'+contractCode+'">'+
				'	<li class="ml order0">'+contractCode+'</li>'+
				'	<li class = "order1">'+drectionText+'</li>'+
				'	<li class = "order2">'+orderPrice+'</li>'+
				'	<li class = "order3">'+orderNum+'</li>'+
				'	<li class = "order4">'+triggerPrice+'</li>'+
				'	<li class = "order5">'+ordreStatusText+'</li>'+
				'	<li class = "order6">'+tradePrice+'</li>'+
				'	<li class = "order7">'+tradeNum+'</li>'+
				'	<li class = "order8">""</li>'+
				'	<li class = "order9">'+orderId+'</li>'+
				'</ul>';
	$("#order_title").append(html);
	addOrderBindClick(cls);
	updateOrderIndex();
};
/**
 * 修改用户委托信息
 * @param {Object} param
 */
function updateOrder(param){
	var contractCode = param.ContractCode;
	var $orderStatus = $("li[data-tion-order='"+contractCode+"'] span[class = 'order5']");
	var $tradeNum = $("li[data-tion-order= '"+contractCode+"'] span[class = 'order7']");
	var orderStatus = param.OrderStatus;
	var tradeNum = param.TradeNum;
	$orderStatus.text(analysisOrderStatus(orderStatus));
	$tradeNum.text(tradeNum);	
};
/**
 * 缓存挂单的列表信息
 */
var localCacheDesignate = {};
/**
 * 挂单的索引
 */
var designateIndex = 0;
/**
 * 全局的保存当前选中挂单的信息
 */
var selectDesgnate = {};
/**
 * 添加用户挂单信息
 * @param {Object} param 用户挂单信息的json对象
 */
function appendDesignates(param){
   var contractCode = param.ContractCode;
   var contractNo = param.ContractNo;
   var commodityNo = param.CommodityNo;
   var drectionText = analysisBusinessDirection(param.Drection);
   var orderPrice = param.OrderPrice;
   var orderNum = param.OrderNum;
   var tradeNum = param.TradeNum;
   var orderSysId = param.OrderSysID;
   var exchangeNo = param.ExchangeNo;
   var orderId = param.OrderID;
   var cls = "des-index"+designateIndex;
   var html = '<ul class="tab_content '+cls+'" data-index-des = "'+designateIndex+'" data-tion-des= "'+contractCode+'">'+
				'	<li class="ml des0">'+contractCode+'</li>'+
				'	<li class = "des1">'+contractCode+'</li>'+
				'	<li class = "des2">'+drectionText+'</li>'+
				'	<li class = "des3">'+orderPrice+'</li>'+
				'	<li class = "des4">'+orderNum+'</li>'+
				'	<li class = "des5">'+(orderNum - tradeNum)+'</li>'+
				'	<li class = "des6"  style = "display:none;">'+orderSysId+'</li>'+
				'	<li class = "des7"  style = "display:none;">'+exchangeNo+'</li>'+
				'	<li class = "des8"  style = "display:none;">'+commodityNo+'</li>'+
				'	<li class = "des9"  style = "display:none;">'+contractNo+'</li>'+
				'	<li class = "des10" style = "display:none;">'+orderId+'</li>'+
				'</ul>';
   $("#des_title").html(html);
   localCacheDesignate[contractCode] = createDesignatesParam(param);
   addDesignateBindClick(cls);
   updateDesignateIndex();
};
/**
 * 修改挂单中的订单信息
 * @param {Object} param
 */
function updateDesignatesDom(param){
	var contractCode = param.ContractCode;
	var orderId = param.OrderID;
	var drection = param.Drection;
	var drectionText = analysisDrection(drection);
	var orderNum = parseInt(param.OrderNum);
	var orderStatus = param.OrderStatus;
	var $gdNum = $("ul[data-tion-des='"+contractCode+"'] li[class = 'desig5']");
	var holdNum =  parseInt($gdNum.text());
	if(orderStatus == 2){
		holdNum = holdNum - orderNum;
	}
	if(holdNum == 0){
		//当挂单为0时，清理dom节点和存储数据
		delDesignatesDom(contractCode);
		deleteDesignatesContractCode(contractCode);
	}else if(holdNum != 0){
		$thisHoldNum.text(Math.abs(holdNum));
		//添加数据到本地储存
		designates[orderId] = designatesParam;
		//更新储存数据
		var desiContract = designatesContract[contractCode];
		desiContract.cdNum = holdNum;
		desiContract.drection = drectionText;
	}
};
/**
 * 移除挂单中订单信息
 * @param {Object} param
 */
function delDesignatesDom(param){};
/**
 * 添加用户成交记录信息
 * @param {Object} param
 */
function appendTradeSuccess(param){};
/**
 * 添加或修改用户持仓信息
 * @param {Object} param 用户持仓信息
 */
function appendPostionAndUpdate(param){
	//验证持仓信息是否在列表中存在
	if(validationPostionIsExsit(param)){
		addPostion(param);
	}else{
		updatePostion(param);
	}
};
/**
 * 缓存持仓的列表信息
 */
var localCachePostion = {};
/**
 * 全局的保存当前选中持仓的信息
 */
var selectPostion = {};
/**
 * 持仓信息的索引
 */
var postionIndex = 0;
/**
 * 增加持仓信息
 * @param param
 */
function addPostion(param){
	if(isJson(param)){
		var contractCode = param.ContractCode;
		var holdNum = param.HoldNum;
		var drection = param.Drection;
		var drectionText = analysisBusinessDirection(drection);
		var holdAvgPrice = param.HoldAvgPrice;
		var floatingProfit = param.FloatingProfit;
		var exchangeNo = param.ExchangeNo;
		var currencyNo = param.CurrencyNo;
		if(holdNum == undefined){
			holdNum = param.TradeNum;
		}
		if(holdAvgPrice == undefined){
			holdAvgPrice = param.TradePrice;
		}
		var cls = "postion-index"+postionIndex;
		var html = '<ul class="tab_content '+cls+'" data-index-position = "'+postionIndex+'" data-tion-position = "'+contractCode+'" id = "'+contractCode+'"> '+
					'	<li class="position0 ml" style="width: 100px;">'+contractCode+'</li>'+
					'	<li  class = "position1">'+holdNum+'</li>'+
					'	<li  class = "position2" data-drection = "'+drection+'">'+drectionText+'</li>'+
					'	<li  class = "position3">'+holdAvgPrice+'</li>'+
					'	<li  class = "position4">'+floatingProfit+'</li>'+
					'	<li  class = "position5">'+exchangeNo+'</li>'+
					'	<li  class = "position6">'+currencyNo+'</li>'+
					'</ul>';
		$("#hold_title").append(html);
		//存储数据
		localCachePostion[contractCode] = createPostionsParam(param);
		addPositionBindClick(cls);
		updatePositionIndex();
	}
}
/**
 * 更新持仓信息
 * @param param
 */
function updatePostion(param){
	var contractCode = param.ContractCode;
	var holdNum = parseInt(param.HoldNum);
	var drection = param.Drection;
	var holdAvgPrice = param.HoldAvgPrice;
	var floatingProfit = param.FloatingProfit;
	var exchangeNo = param.ExchangeNo;
	var currencyNo = param.CurrencyNo;
	if(isNaN(holdNum)){
		holdNum = parseInt(param.TradeNum);
	}
	if(holdAvgPrice == undefined){
		holdAvgPrice = param.TradePrice;
	}
	var $holdNum = $("ul[data-tion-position='"+contractCode+"'] li[class = 'position1']");
	var $drection = $("ul[data-tion-position='"+contractCode+"'] li[class = 'position2']");
	var $holdAvgPrice = $("ul[data-tion-position='"+contractCode+"'] li[class = 'position3']");
	var $floatingProfit = $("ul[data-tion-position='"+contractCode+"'] li[class = 'position4']");
	var oldHoldNum = parseInt($holdNum.text());
	var oldDrection = parseInt($drection.attr("data-drection"));
	var oldPrice = parseFloat($holdAvgPrice.text()) *  oldHoldNum;
	var price = parseFloat(newprice) * holdNum;
	if(oldDrection == drection){
		oldHoldNum = oldHoldNum + holdNum;
		price = price + oldPrice;
		var openAvgPrice = doGetOpenAvgPrice(price,oldHoldNum);
		$holdAvgPrice.text(openAvgPrice);
		//TODO 计算浮动盈利 ,这里需要行情数据，最新价格，ContractSize,MiniTikeSize
		var commdityNo = holdParam.CommodityNo;
		var contractNo = holdParam.ContractNo;
		
		var floatingProft = 0.00; 
		var floatP = 0.00;
		$floatingProft.val(floatingProft);
		if(floatP < 0 ){
			$floatingProfit.css("color","green");
		}else if(floatP > 0){
			$floatingProfit.css("color","red");
		}else{
			$floatingProfit.css("color","white");
		}
	}else{
		oldHoldNum = oldHoldNum - holdNum;
		//当持仓为空时，清理dom节点和存储数据
		if(oldHoldNum == 0){	
			delPositionDom(contractCode);
			deletePositionsContractCode(contractCode);
		//当drection为0时，上一次更新数据为’多‘，holdNum 小于0时表示这次这次更新数据买卖方向变为’空‘
		}else if(oldHoldNum < 0 && oldDrection == 0){
			drectionText = kong;
			drection = 1;
		//当drection为1时，上一次数据更新为’空‘，holdNum小于0时表示这次更新数据买卖方向变为’多‘
		}else if(oldHoldNum < 0 && oldDrection == 1){
			drectionText = duo;
			drection = 0;
		}else if(oldHoldNum > 0 && oldDrection == 0){
			drectionText =duo;
			drection = 0;
		}else if(oldHoldNum > 0 && oldDrection == 1){
			drectionText = kong;
			drection = 1;
		}
	}
	if(oldHoldNum != 0){
		var drectionText = analysisBusinessDirection(drection);
		$thisHoldNum.text(Math.abs(oldHoldNum));
		$thisDrectionText.html();
		$thisDrectionText.attr("data-drection",drection);
		//更新储存数据
		var postContract = localCachePostion[contractCode];
		postContract.holdNum = oldHoldNum;
		postContract.drection = drectionText;
	}
}
/**
 * 验证持仓信息是否存在 
 */
function validationPostionIsExsit(param){
	var contractCode = param.ContractCode;
	var positionParam = localCachePostion[contractCode];
	if(positionParam == undefined || positionParam == "undefined" || positionParam == null){
		return true;
	}else{
		return false;
	}
}
/**
 * 生成持仓信息表头
 */
function generatePostionTitle(){
	var html = '<ul class="tab_lis">'+
				'	<li class="ml" style="width: 100px;">合约代码</li>'+
				'	<li>持仓数量</li>'+
				'	<li>买卖</li>'+
				'	<li>持仓均价</li>'+
				'	<li>浮动盈利</li>'+
				'	<li>交易所</li>'+
				'	<li>币种</li>'+
				'</ul>';
	$("#hold_title").html(html);
}
/**
 * 生成委托信息表头
 */
function generateOrderTitle(){
	var html = '<ul class="tab_lis">'+
				'	<li class="ml">合约代码</li>'+
				'	<li>买卖</li>'+
				'	<li>委托价</li>'+
				'	<li>委托量</li>'+
				'	<li>触发价</li>'+
				'	<li>委托状态</li>'+
				'	<li>成交均价</li>'+
				'	<li>成交量</li>'+
				'	<li>撤单时间</li>'+
				'	<li>订单号</li>'+
				'</ul>';
	$("#order_title").html(html);
}
/**
 * 生成挂单信息表头
 */
function generateDesignateTitle(){
	var html = '<ul class="tab_lis">'+
				'	<li class="ml">合约代码</li>'+
				'	<li>合约名称</li>'+
				'	<li>买卖</li>'+
				'	<li>委托价</li>'+
				'	<li>委托量</li>'+
				'	<li>挂单量</li>'+
				'</ul>';
	$("#des_title").html(html);
}
/**
 * 绑定持仓列表的点击事件
 * @param str
 */
function addPositionBindClick(cls){
	$("."+cls+"").bind("click",function(){
		var $this = $(this);
		selectPostion["contractCode"] = $this.attr("data-tion-position");
		selectPostion["postionIndex"] = $this.attr("data-index-position");
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
		});
	});
}
/**
 * 绑定挂单点击事件
 * @param {Object} cls
 */
function addDesignateBindClick(cls){
	$(function(){
		$("."+cls+"").bind("click",function(){
			var $this = $(this);
			selectDesgnate["contraction"] = $this.attr("data-tion-des");
			selectDesgnate["designateIndex"] = $this.attr("data-index-des");
		});
	});
}
/**
 * 更新持仓索引
 */
function updatePositionIndex(){
	postionIndex++;
}
/**
 * 更新委托索引
 */
function updateOrderIndex(){
	orderIndex++;
}
/**
 * 更新挂单索引
 */
function updateDesignateIndex(){
	designateIndex++;
}
/**
 * 删除持仓中的元素节点 
 * @param {Object} 删除节点
 */
function delPositionDom(contractCode){
	$(function(){
		$("ul[data-tion-position='"+contractCode+"']").remove();
	});
}
/**
 * 删除挂单中的元素节点 
 * @param {Object} orderId
 */
function delDesignatesDom(contractionCode){
	$("ul[data-tion-des='"+contractionCode+"']").remove();
}
/**
 * 移除全局缓存持仓的品种合约
 * @param {Object} param
 */
function deletePositionsContractCode(param){
	if(!delete localCachePostion[param]){
		localCachePostion[param] = null;
	}
}
/**
 * 移除全局缓存挂单的品种合约
 * @param {Object} param
 */
function deleteDesignatesContractCode(param){
	if(!delete localCacheDesignate[param]){
		localCacheDesignate[param] = null;
	}
}
$(function(){
	$("#show_login").show();
	$("#show_user_info").hide();
	$("#trade_login").click(function(){
		username = $("#quotation_account").val();
		password = $("#quotation_password").val();
		tradeLogin();
	});
	$("#trade_loginOut").click(function(){
		tradeLoginOut(username);
	});
});

