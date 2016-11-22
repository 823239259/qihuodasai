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
 * 合约交易成功加载持仓信息的标志
 */
var tradeSuccessLoadFlag = false;
/**
 * 合约交易成功查询持仓信息
 */
function tradeSuccessLoadHoldData(){
	tradeSuccessLoadFlag = true;
	Trade.doHold(username);
	
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
/**
 * 保存已下单的数据
 */
var resultInsertOrderId = {};
function handleData(evt){
	var dataString = evt.data;
	var data = JSON.parse(dataString);
	var method = data.Method;
	var parameters = data.Parameters;
	linearlyLoadData(method);
	if (parameters != null) {
		if (method == "OnRspLogin") {
			$("#trade_login").text("登录");
			$("#firm_btn").text("立即登录");
			$("#simulation_btn").text("立即登录");
			if(tradeWebSocketIsMock == 0){
				$("#ismockReak").text("实盘账户:");
			}else if(tradeWebSocketIsMock == 1){
				$("#ismockReak").text("模拟账户:");
			}
			var code = parameters.Code;
			var loginMessage = parameters.Message;
			//登录成功加载
			if (code == 0) {
				LoginForwardInitLoadData();
				$("#show_login").hide();
				$("#show_user_info").show();
				$("#top_username").text(username);
				$(".caozuo").show();
				$(".signLogin_close").click();
				setIsLogin(true);
				loginFail = false;
				anotherPlace = false;
				layer.msg('交易服务器连接成功', {icon: 4});
			} else {
				loginFail = -2;
				tipAlert(loginMessage);
				//登录失败清理数据
				loginOut();
			}
			//查询个人账户信息回复
		} else if (method == "OnRspQryAccount") {
			var accountParam = parameters;
			updateBalance(accountParam);
			addAndUpdateFundsDetails(accountParam);
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
			if(inserOrderStatus == 5){
				tip("交易失败:合约【"+insertOrderParam.ContractCode+"】,原因【"+insertOrderParam.StatusMsg+"】");
			}
			resultInsertOrderId[insertOrderParam.OrderID] = insertOrderParam.OrderID;
			//订单状态通知
		} else if (method == "OnRtnOrderState") {
			var orderParam = parameters;
			updateOrder(orderParam);
			var orderId = orderParam.OrderID;
			var orderStatusWeHooks = orderParam.OrderStatus;
			//当订单状态改变
			var contractCode = orderParam.ContractCode;
			if (orderStatusWeHooks == 3 || orderStatusWeHooks == 4 || orderStatusWeHooks == 5) {
				delDesignatesDom(orderId);
			} else if (orderStatusWeHooks == 0) {
				appendDesignates(orderParam);
			} else if (orderStatusWeHooks == 1 || orderStatusWeHooks == 2) {
				updateDesignatesDom(orderParam);
			}
			var cacaleOrderId = selectDesgnate["orderId"];
			var contractCode = selectDesgnate["contraction"];
			if(orderStatusWeHooks == 4){
				tip("撤单成功:合约【"+orderParam.ContractCode+"】,订单号【"+orderId+"】");
			}
			if(orderStatusWeHooks == 5){
				tip("交易失败:合约【"+orderParam.ContractCode+"】,原因【"+orderParam.StatusMsg+"】");
			}
			if(isUpdateOrder && cacaleOrderId==orderId){
				var orderPrice = orderParam.OrderPrice;
				var orderNum = orderParam.OrderNum;
				isUpdateOrder = false;
				tip("改单成功:合约【"+contractCode+"】,委托价【"+orderPrice+"】,委托量【"+orderNum+"】");
			}
			//订单成交通知
		} else if (method == "OnRtnOrderTraded") {
			var tradeParam = parameters;
			appendTradeSuccess(tradeParam);
			appendPostionAndUpdate(tradeParam);
			var orderId = tradeParam.OrderID;
			var locaOrderId = resultInsertOrderId[orderId];
			if(isBuy && locaOrderId == locaOrderId){
				tradeSuccessLoadHoldData();
				resultInsertOrderId[orderId] = null;
				isBuy = false;
			}
			tip("交易成功：合约【"+tradeParam.ContractCode+"】,交易手数:【"+tradeParam.TradeNum+"】,交易价格:【"+tradeParam.TradePrice+"】");
			//资金变化通知
		} else if (method == "OnRtnMoney") {
			var accountParam = parameters;
			updateBalance(accountParam)
			updateFundsDetails(accountParam);
		} else if (method == "OnError") {
			var code = parameters.Code;
			var loginMessage = parameters.Message;
			tip(loginMessage);
		}else if(method == "OnRspLogout"){
			$("#trade_login").text("登录");
			$("#firm_btn").text("立即登录");
			$("#simulation_btn").text("立即登录");
			var code = parameters.Code;
			var loginMessage = parameters.Message;
			loginFail = true;
			if(code == 1){
				anotherPlace = true;
			}
		}
	}else{
		if(method == "OnRspQryHold" && tradeSuccessLoadFlag){
			updateOrderUpdatePosition();
			tradeSuccessLoadFlag = false;
			localCachePositionRecentData = {};
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
var localCacheCurrencyAndRate = {};
var loadCachFloatingProfit = {};
var loadCachCloseProfit = {};
var loadCachTodayBanlance = 0;
var loadCachTodayCanuse = 0;
function updateBalance(parama){
	var currencyNo = parama.CurrencyNo;
	var accountNo = parama.AccountNo;
	var cachBanlace = loadCachBanlance[accountNo];
	var deposit = parama.Deposit;
	var currency = parama.CurrencyRate; 
	var closeProfit = parama.CloseProfit;
	var frozenMoney =parama.FrozenMoney;
	var todayAmount = parama.TodayAmount;
	var unExpiredProfit = parama.UnExpiredProfit;
	var unAccountProfit = parama.UnAccountProfit;
	var floatingProfit = $("#floatingProfit").val();
	var banlance = parseFloat(Number(todayAmount)+Number(unExpiredProfit)+Number(unAccountProfit)+Number(floatingProfit)).toFixed(2);;//今结存+浮盈+未结平盈+未到期平盈
	var canuse = parseFloat(banlance-deposit-frozenMoney).toFixed(2);
	localCacheCurrencyAndRate[currencyNo]  = currency;
	loadCachBanlance[accountNo] = banlance;
	loadCachDeposit[accountNo] = deposit;
	loadCachCanuse[accountNo] = canuse;
	loadCachFloatingProfit[accountNo] = floatingProfit;
	loadCachCloseProfit[accountNo] = closeProfit;
	if(currency != undefined)
		loadCurrencyRate[accountNo] = currency;
	if(cachBanlace == undefined || cachBanlace.length <= 0){
		loadCachAccountNo[uehIndex] = accountNo;
		uehIndex++;
	}
	var $banlance = 0.00;
	var $deposit = 0.00
	var $canuse = 0.00
	var $floatFit = 0.00;
	var $clostFit = 0.00;
	for(var i = 0 ; i < uehIndex; i++){
		var ac = loadCachAccountNo[i]; 
		var cr = loadCurrencyRate[ac];
		$banlance = $banlance + loadCachBanlance[ac] * cr;
		$deposit = $deposit + loadCachDeposit[ac] * cr;
		$canuse = $canuse + loadCachCanuse[ac]  * cr;
		$clostFit = $clostFit + loadCachCloseProfit[ac]  * cr;
		$floatFit = $floatFit + loadCachFloatingProfit[ac] * cr;
	}
	loadCachTodayBanlance = $banlance;
	loadCachTodayCanuse = $canuse;
	$("#todayBalance").text(parseFloat($banlance).toFixed(2));
	$("#deposit").text(parseFloat($deposit).toFixed(2));
	$("#todayCanUse").text(parseFloat($canuse).toFixed(2));
	var color = "#FFFFFF";
	var $closeProfit = parseFloat($clostFit).toFixed(2);
	if($closeProfit < 0){
		color = "#0bffa4";
	}else if($clostFit > 0){
		color = "#ff5500";
	}
	$("#closeProfit").text($closeProfit);
	$("#closeProfit").css("color",color);
};
/**
 * 增加或更新资金明细
 * @param param
 */
function addAndUpdateFundsDetails(param){
	if(validationFundDetailsIsExsit(param)){
		addFundsDetails(param);
	}else{
		updateFundsDetails(param);
	}
}
/**
 * 全局缓存资金资金明细的列表信息
 */
var localCacheFundDetail = {};
/**
 * 资金明细的索引
 */
var fundsDetailsIndex = 0;
/**
 * 全局保存币种
 */
var localCurrencyNo = [];
/**
 * 增加资金明细
 * @param param
 */
function addFundsDetails(param){
	var currencyNo = param.CurrencyNo;
	var acccoutNo = param.AccountNo;
/*	if(currencyNo == "HKD"){
		currencyNo = "HKD-HKFE";
	}*/
	var deposit = parseFloat(param.Deposit).toFixed(2);
	var floatingProfit = parseFloat(param.FloatingProfit).toFixed(2);
	var keepDepositf = parseFloat(param.Deposit).toFixed(2);
	var oldBalance = parseFloat(param.OldBalance).toFixed(2);
	var oldAmount = parseFloat(param.OldAmount).toFixed(2);
	var todayAmount = parseFloat(param.TodayAmount).toFixed(2);
	var frozenMoney = parseFloat(param.FrozenMoney).toFixed(2);
	var currencyRate = param.CurrencyRate;
	var unExpiredProfit = parseFloat(param.UnExpiredProfit).toFixed(2);
	var unAccountProfit = parseFloat(param.UnAccountProfit).toFixed(2);
	var todayBalance = parseFloat(Number(todayAmount)+Number(unExpiredProfit)+Number(unAccountProfit)+Number(0)).toFixed(2);
	var todayCanUse =  parseFloat(todayBalance-keepDepositf-frozenMoney).toFixed(2);
	var profitRate = "";
	var cls = "currencyNo"+currencyNo;
	var funds_cls = "funds-index"+fundsDetailsIndex;
	var html = '<ul class="tab_content '+cls+' '+funds_cls+'" data-tion-fund = "'+currencyNo+'" data-tion-account = "'+acccoutNo+'">'+
				'	<li class="ml detail_currency">'+currencyNo+'</li>'+
				'	<li class = "detail_todayBalance">'+todayBalance+'</li>'+
				'	<li class = "detail_todayCanUse">'+todayCanUse+'</li>'+
				'	<li class = "detail_deposit">'+deposit+'</li>'+
				'	<li class = "detail_floatingProfit">'+floatingProfit+'</li>'+
				'	<li class = "detail_keepDepositf">'+keepDepositf+'</li>'+
				'	<li class = "detail_oldBalance">'+oldBalance+'</li>'+
				'	<li class = "detail_oldAmount">'+oldAmount+'</li>'+
				'	<li class = "detail_todayAmount">'+todayAmount+'</li>'+
				'	<li class = "detail_frozenMoney">'+frozenMoney+'</li>'+
				'	<li class = "detail_profitRate">'+profitRate+'</li>'+
				'	<li class = "detail_currencyRate" style="display:none;">'+currencyRate+'</li>'+
				'</ul>';
	$("#account_gdt1").append(html);
	tabOn();
	localCacheFundDetail[currencyNo]=param;
	addFundDetailBindClick(currencyNo);
	localCurrencyNo[fundsDetailsIndex] = currencyNo;
	updateFundsDetailsIndex();
	$(".account_NoRecord").css("display","none");
} 
/**
 * 更新资金明细
 * @param param
 */
function updateFundsDetails(param){
	var accountNo = param.AccountNo;
	var deposit = parseFloat(param.Deposit).toFixed(2);
	var keepDepositf = parseFloat(param.Deposit).toFixed(2);
	var todayAmount = param.TodayAmount;
	var frozenMoney = parseFloat(param.FrozenMoney).toFixed(2);
	var floatingProfit = $("ul[data-tion-account='"+accountNo+"'] li[class = 'detail_floatingProfit']").text();
	if(isNaN(floatingProfit)){
		floatingProfit = 0;
	}
	var unExpiredProfit =param.UnExpiredProfit;
	var unAccountProfit =param.UnAccountProfit;
	var todayBalance = parseFloat(Number(todayAmount)+Number(unExpiredProfit)+Number(unAccountProfit)+Number(0)).toFixed(2);
	var todayCanUse =  parseFloat(todayBalance-keepDepositf-frozenMoney).toFixed(2);
	var profitRate = "";
	var $detailTodayBalance = $("ul[data-tion-account='"+accountNo+"'] li[class = 'detail_todayBalance']");
	var $detailTodayCanUse = $("ul[data-tion-account='"+accountNo+"'] li[class = 'detail_todayCanUse']");
	var $detailDeposit = $("ul[data-tion-account='"+accountNo+"'] li[class = 'detail_deposit']");
	var $detailKeepDeposit = $("ul[data-tion-account='"+accountNo+"'] li[class = 'detail_keepDepositf']");
	var $detailTodayAmount = $("ul[data-tion-account='"+accountNo+"'] li[class = 'detail_todayAmount']");
	var $detailFrozenMoney = $("ul[data-tion-account='"+accountNo+"'] li[class = 'detail_frozenMoney']");
	var $detailProfitRate = $("ul[data-tion-account='"+accountNo+"'] li[class = 'detail_profitRate']");
	var $detailCurrencyRate = $("ul[data-tion-account='"+accountNo+"'] li[class = 'detail_currencyRate']");
	var currencyRate = $detailCurrencyRate.text();
	$detailTodayBalance.text(todayBalance);
	$detailTodayCanUse.text(todayCanUse);
	$detailDeposit.text(deposit);
	$detailKeepDeposit.text(keepDepositf);
	$detailTodayAmount.text(parseFloat(todayAmount).toFixed(2));
	$detailFrozenMoney.text(frozenMoney);
	$detailProfitRate.text(profitRate);
}
var orderIndex = 0;
/**
 * 添加用户委托信息
 * @param {Object} param 委托信息的json对象
 */
function appendOrder(param){
	var contractCode = param.ContractCode;
	var drectionText = analysisBusinessBuySell(param.Drection);
	var contractNo = param.ContractNo;
	var commodityNo = param.CommodityNo;
	var localCommodity  = getLocalCacheCommodity(commodityNo+contractNo);
	var orderPrice = param.OrderPrice;
	if(localCommodity != undefined){
		var doSize = localCommodity.DotSize;
		orderPrice = parseFloat(orderPrice).toFixed(doSize);
	}
	var orderStatus = param.OrderStatus;
	var ordreStatusText = analysisOrderStatus(orderStatus);
	var orderNum = param.OrderNum;
	var tradeNum = param.TradeNum;
	var triggerPrice = param.TriggerPrice;
	var priceType = param.OrderPriceType;
	var tradePrice = param.TradePrice;
	var orderId = param.OrderID;
	var statusMsg = param.StatusMsg;
	var insertDateTime = param.InsertDateTime;
	var cls = "order-index" + orderIndex;
	var html = '<ul class="tab_content '+cls+'" data-order-order = "'+orderId+'" data-index-order = "'+orderIndex+'" data-tion-order = "'+contractCode+'">'+
				'	<li class="ml order0">'+contractCode+'</li>'+
				'	<li class = "order1" style="width: 50px;">'+drectionText+'</li>'+
				'	<li class = "order2" style="width: 50px;">'+orderPrice+'</li>'+
				'	<li class = "order3" style="width: 50px;">'+orderNum+'</li>'+
				'	<li class = "order4" style="width: 70px;">'+analysisOrderPriceType(priceType)+'</li>'+
				'	<li class = "order5" style="width: 70px;">'+ordreStatusText+'</li>'+
				'	<li class = "order6" style="width: 70px;">'+tradePrice+'</li>'+
				'	<li class = "order7" style="width: 50px;">'+tradeNum+'</li>'+
				'	<li class = "order8" style="width: 120px;">'+insertDateTime+'</li>'+
				'	<li class = "order9"  style="width: 100px;">'+orderId+'</li>'+
				'	<li class = "order10" style="width: 80px;">'+statusMsg+'</li>'+
				'</ul>';
	$("#order_gdt1").append(html);
	tabOn();
	addOrderBindClick(cls);
	updateOrderIndex();
	$(".order_NoRecord").css("display","none");
};
/**
 * 修改用户委托信息
 * @param {Object} param
 */
function updateOrder(param){
	var contractCode = param.ContractCode;
	var orderId = param.OrderID;
	var statusMsg = param.StatusMsg;
	var $desgPrice = $("ul[data-order-order='"+orderId+"'] li[class = 'order2']");
	var $desgNumber = $("ul[data-order-order='"+orderId+"'] li[class = 'order3']");
	var $orderStatus = $("ul[data-order-order='"+orderId+"'] li[class = 'order5']");
	var $orderPrice = $("ul[data-order-order='"+orderId+"'] li[class = 'order6']");
	var $tradeNum = $("ul[data-order-order= '"+orderId+"'] li[class = 'order7']");
	var $statusMsg = $("ul[data-order-order= '"+orderId+"'] li[class = 'order10']");
	var orderStatus = param.OrderStatus;
	var tradeNum = param.TradeNum;
	var orderPrice = param.TradePrice;
	$orderStatus.text(analysisOrderStatus(orderStatus));
	$tradeNum.text(tradeNum);	
	$statusMsg.text(statusMsg);
	$orderPrice.text(orderPrice);
	$desgPrice.text(param.OrderPrice);
	$desgNumber.text(param.OrderNum);
};
/**
 * 缓存挂单的列表信息
 */
var localCacheDesignate = {};
/**
 * 缓存挂单列表信息的品种+合约
 */
var localCachedesignateContractCode = {};
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
   var drection = param.Drection;
   var drectionText = analysisBusinessBuySell(drection);
   var orderPrice = param.OrderPrice;
   var orderNum = param.OrderNum;
   var tradeNum = param.TradeNum;
   var orderSysId = param.OrderSysID;
   var exchangeNo = param.ExchangeNo;
   var orderId = param.OrderID;
   var triggerPrice = param.TriggerPrice;
   var cls = "des-index"+designateIndex;
   var html = '<ul class="tab_content '+cls+' tab_des" data-order-des = "'+orderId+'"   data-index-des = "'+designateIndex+'" data-tion-des= "'+contractCode+'">'+
				'	<li class="ml des0">'+contractCode+'</li>'+
				'	<li class = "des1">'+contractCode+'</li>'+
				'	<li class = "des2" data-drection = "'+drection+'">'+drectionText+'</li>'+
				'	<li class = "des3" style="width: 120px;">'+orderPrice+'</li>'+
				'	<li class = "des4">'+orderNum+'</li>'+
				'	<li class = "des5">'+(orderNum - tradeNum)+'</li>'+
				'	<li class = "des6"  style = "display:none;">'+orderSysId+'</li>'+
				'	<li class = "des7"  style = "display:none;">'+exchangeNo+'</li>'+
				'	<li class = "des8"  style = "display:none;">'+commodityNo+'</li>'+
				'	<li class = "des9"  style = "display:none;">'+contractNo+'</li>'+
				'	<li class = "des10" style = "display:none;">'+orderId+'</li>'+
				'	<li class = "des11" style = "display:none;">'+triggerPrice+'</li>'+
				'</ul>';
   $("#des_gdt1").append(html);
   tabOn();
   localCacheDesignate[contractCode] = createDesignatesParam(param);
   localCachedesignateContractCode[designateIndex] = orderId;
   addDesignateBindClick(cls);
   updateDesignateIndex();
   $(".des_NoRecord").css("display","none");
};
/**
 * 修改挂单中的订单信息
 * @param {Object} param
 */
function updateDesignatesDom(param){
	var contractCode = param.ContractCode;
	var orderId = param.OrderID;
	var drection = param.Drection;
	var drectionText = analysisBusinessBuySell(drection);
	var orderNum = parseInt(param.OrderNum);
	var tradeNum = parseInt(param.TradeNum);
	var orderPrice = param.OrderPrice;
	var orderStatus = param.OrderStatus;
	var $gdNum = $("ul[data-order-des='"+orderId+"'] li[class = 'des5']");
	var $orderPrice = $("ul[data-order-des='"+orderId+"'] li[class = 'des3']");
	var $orderNum = $("ul[data-order-des='"+orderId+"'] li[class = 'des4']");
	var holdNum = orderNum - tradeNum;
	if(holdNum == 0){
		//当挂单为0时，清理dom节点和存储数据
		delDesignatesDom(contractCode);
		deleteDesignatesContractCode(contractCode);
	}else if(holdNum != 0){
		$gdNum.text(holdNum);
		$orderNum.text(orderNum);
		$orderPrice.text(orderPrice);
		//更新储存数据
		var desiContract = localCacheDesignate[contractCode];
		desiContract.cdNum = holdNum;
		desiContract.drection = drectionText;
	}
};
/**
 * 成交记录索引
 */
var tradesIndex = 0;
/**
 * 添加用户成交记录信息
 * @param {Object} param
 */
function appendTradeSuccess(param){
	var drection = param.Drection;
	var drectionText = analysisBusinessBuySell(drection);
	var orderId = param.OrderID;
	var contractCode = param.ContractCode;
	var tradePrice = param.TradePrice;
	var tradeNum = param.TradeNum;
	var currencyNo = param.CurrencyNo;
	var tradeNo = param.TradeNo;
	var orderId = param.OrderID;
	var tradeTime = param.TradeDateTime;
	var exchangeNo = param.ExchangeNo;
	var cls = 'trade-index'+tradesIndex;
	var html = '<ul class="tab_content '+cls+'"  data-index-trade = "'+tradesIndex+'" data-tion-trade = "'+contractCode+'">'+
				'	<li class="ml trade0">'+contractCode+'</li>'+
				'	<li class = "trade1" style="width: 40px;">'+drectionText+'</li>'+
				'	<li class = "trade2" style="width: 70px;">'+tradePrice+'</li>'+
				'	<li class = "trade3" style="width: 50px;">'+tradeNum+'</li>'+
				/*'	<li class = "trade4"  style="width: 70px;">'+currencyNo+'</li>'+*/
				'	<li class = "trade5" style="width: 250px;">'+tradeNo+'</li>'+
				'	<li class = "trade6"  style="width: 80px;">'+orderId+'</li>'+
				'	<li class = "trade7"  style="width: 120px;">'+tradeTime+'</li>'+
				'	<li class = "trade8" style="width: 40px;">'+exchangeNo+'</li>'+
				'</ul>';
	$("#trade_gdt1").append(html);
	tabOn();
	addTradeSuccessBindClick(cls);
	updateTradesIndex();
	$(".trade_NoRecord").css("display","none");
};
/**
 * 添加或修改用户持仓信息
 * @param {Object} param 用户持仓信息
 */
function appendPostionAndUpdate(param){
	//验证持仓信息是否在列表中存在
	if(validationPostionIsExsit(param)){
		addPostion(param);
	}else{
		if(tradeSuccessLoadFlag){
			loadCachecentPositionData(param);
		}else{
			updatePostion(param);
		}
	}
};
/**
 * 缓存持仓的列表信息
 */
var localCachePostion = {};
/**
 * 缓存持仓列表品种+合约
 */
var localCachePositionContractCode = {};
/**
 * 全局的保存当前选中持仓的信息
 */
var selectPostion = {};
/**
 * 持仓信息的索引
 */
var postionIndex = 0;
/**
 * 重新加载最新持仓信息数据的保存
 */
var localCachePositionRecentData = {}; 
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
		var commodityNo = param.CommodityNo;
		var contractNo = param.ContractNo;
		var openAvgPrice = param.OpenAvgPrice;
		if(holdNum == undefined){
			holdNum = param.TradeNum;
		}
		if(openAvgPrice == undefined){
			openAvgPrice = param.TradePrice;
		}
		if(holdAvgPrice == undefined){
			holdAvgPrice = openAvgPrice;
		}
		var floatP = 0.00;
		var contractAndCommodity = commodityNo+contractNo;
		var localCommodity  = getLocalCacheCommodity(contractAndCommodity);
		if(localCommodity != undefined){
			var lastPrice = getLocalCacheQuote(contractAndCommodity);
			var contractSize = localCommodity.ContractSize;
			var miniTikeSize = localCommodity.MiniTikeSize;
			currencyNo = localCommodity.CurrencyNo;
			exchangeNo = localCommodity.ExchangeNo;
			floatP = doGetFloatingProfit(parseFloat(lastPrice),openAvgPrice,contractSize,miniTikeSize,holdNum,drection);
			if(isNaN(floatP)){
				floatP = 0.00;
			}
			floatingProfit = floatP +":"+ currencyNo; 
		}
		if(floatingProfit == undefined){
			floatingProfit = 0;
		}
		if(currencyNo == undefined){
			currencyNo = "";
		}
		var currcls  = "position-currency"+currencyNo;
		var cls = "postion-index"+postionIndex;
		var html = '<ul class="tab_content '+cls+' '+currcls+' tab_position" data-index-position = "'+postionIndex+'" data-tion-position = "'+contractCode+'" id = "'+contractCode+'"> '+
					'	<li class="position0 ml" style="width: 80px;">'+contractCode+'</li>'+
					'	<li  class = "position1" style="width: 80px;">'+holdNum+'</li>'+
					'	<li  class = "position2" style="width: 80px;padding-left:10px" data-drection = "'+drection+'">'+drectionText+'</li>'+
					'	<li  class = "position3" style="width: 80px;">'+holdAvgPrice+'</li>'+
					'	<li  class = "position4"  style="width: 160px;">'+floatingProfit+'</li>'+
					'	<li  class = "position5"  style="width: 80px;">'+exchangeNo+'</li>'+
					'	<li  class = "position6"  style="width: 80px;">'+currencyNo+'</li>'+
					'	<li  class = "position7"  style = "display:none;">'+commodityNo+'</li>'+
					'	<li  class = "position8"  style = "display:none;">'+contractNo+'</li>'+
					'	<li  class = "position9"  style = "display:none;">'+openAvgPrice+'</li>'+
					'	<li  class = "position10"  style = "display:none;">'+floatP+'</li>'+
					'</ul>';
		$("#hold_gdt1").append(html);
		tabOn();
		//存储数据
		localCachePostion[contractCode] = createPostionsParam(param);
		localCachePositionContractCode[postionIndex] = contractCode;
		addPositionBindClick(cls);
		updatePositionIndex();
		$(".hold_NoRecord").css("display","none");
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
	var exchangeNo = param.ExchangeNo;
	var currencyNo = param.CurrencyNo;
	var openAvgPrice = param.OpenAvgPrice;
	if(isNaN(holdNum)){
		holdNum = parseInt(param.TradeNum);
	}
	if(openAvgPrice == undefined){
		openAvgPrice = param.TradePrice;
	}
	var $holdNum = $("ul[data-tion-position='"+contractCode+"'] li[class = 'position1']");
	var $drection = $("ul[data-tion-position='"+contractCode+"'] li[class = 'position2']");
	var $holdAvgPrice = $("ul[data-tion-position='"+contractCode+"'] li[class = 'position3']");
	var $openAvgPrice = $("ul[data-tion-position='"+contractCode+"'] li[class = 'position9']");
	var $floatP = $("ul[data-tion-position='"+contractCode+"'] li[class = 'position10']");
	var $floatingProfit = $("ul[data-tion-position='"+contractCode+"'] li[class = 'position4']");
	var oldHoldNum = parseInt($holdNum.text());
	var oldDrection = parseInt($drection.attr("data-drection"));
	var oldPrice = parseFloat($openAvgPrice.text()).toFixed(2) *  oldHoldNum;
	var price = parseFloat(openAvgPrice).toFixed(2) * holdNum;
	if(oldDrection == drection){
		oldHoldNum = oldHoldNum + holdNum;
		price = parseFloat(price + oldPrice).toFixed(2);
		var localCommodity = getLocalCacheCommodity(contractCode);
		var doSize = 0;
		if(localCommodity != undefined){
			doSize = localCommodity.DotSize;
		}
		var openAvgPrice = doGetOpenAvgPrice(price,oldHoldNum,doSize);
		$openAvgPrice.text(openAvgPrice);
		$holdAvgPrice.text(openAvgPrice);
		var commdityNo = param.CommodityNo;
		var contractNo = param.ContractNo;
		var floatingProft = 0.00; 
		var floatP = 0.00;
		var contractAndCommodity = commdityNo+contractNo;
		var localCommodity  = getLocalCacheCommodity(contractAndCommodity);
		if(localCommodity != undefined){
			var lastPrice = getLocalCacheQuote(contractAndCommodity);
			var contractSize = localCommodity.ContractSize;
			var miniTikeSize = localCommodity.MiniTikeSize;
			var currencyNo = localCommodity.CurrencyNo;
			floatP = doGetFloatingProfit(parseFloat(lastPrice),openAvgPrice,contractSize,miniTikeSize,holdNum,drection);
			if(isNaN(floatP)){
				floatP = 0.00;
			}
			floatingProft = floatP +":"+ currencyNo; 
		}
		$floatingProfit.text(floatingProft);
		$floatP.text(floatP);
		$floatingProfit.css("width","160px");
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
		$holdNum.text(Math.abs(oldHoldNum));
		$drection.html(drectionText);
		$drection.attr("data-drection",drection);
		//更新储存数据
		var postContract = localCachePostion[contractCode];
		postContract.holdNum = oldHoldNum;
		postContract.drection = drectionText;
	}
}
/**
 * 缓存最新持仓信息
 * @param param
 */
function loadCachecentPositionData(param){
	var commodityNo = param.CommodityNo;
	var contractNo =param.ContractNo;
	var contractCode = commodityNo+contractNo;
	var cache = localCachePositionRecentData[contractCode];
	if(cache == undefined){
		var array = new Array();
		array[0] = param;
		localCachePositionRecentData[contractCode] = array;
	}else{
		cache[cache.length] = param;
	}
}
/**
 * 交易成功更新持仓信息(计算开仓（持仓）均价)
 */
function updateOrderUpdatePosition(param){
	console.log(localCachePositionRecentData);
	$(".tab_position").each(function(){
		var $this = $(this);
		var contractCode = $this.attr("data-tion-position");
		var cache = localCachePositionRecentData[contractCode];
		if(cache == undefined){
			delPositionDom(contractCode);
			deletePositionsContractCode(contractCode);
		}else{
			var length = cache.length;
			var price = 0.00;
			var holdNum = 0;
			for(var i = 0 ; i < length ; i++){
				var data = cache[i];
				holdNum = holdNum + data.HoldNum;
				price = price + data.HoldAvgPrice * data.HoldNum;
			}
			var localCommodity = getLocalCacheCommodity(contractCode);
			var doSize = 2;
			if(localCommodity != undefined){
				doSize = localCommodity.DotSize;
			}
			var holdAvgPrice = doGetOpenAvgPrice(price, holdNum, doSize);
			console.log(holdAvgPrice);
			var $holdAvgPrice = $("ul[data-tion-position='"+contractCode+"'] li[class = 'position3']");
			var $openAvgPrice = $("ul[data-tion-position='"+contractCode+"'] li[class = 'position9']");
			$holdAvgPrice.text(holdAvgPrice);
			$openAvgPrice.text(holdAvgPrice);
		}
	});
}
/**
 * 根据行情更新持仓列表
 * @param param
 */
function updatePositionByQuote(param){
	var commodityNo = param.CommodityNo;
	var contractNo = param.ContractNo;
	var currencyNo = param.CurrencyNo;
	var exchangeNo = param.ExchangeNo;
	var contractCode = commodityNo + contractNo;
	var positionDom = $("ul[data-tion-position='"+contractCode+"']");
	if(positionDom.html() == undefined){
		return;
	}
	var localCommodity = getLocalCacheCommodity(contractCode);
	if(localCommodity != undefined){
		currencyNo = localCommodity.CurrencyNo;
	}
	var $exchangeNo = $("ul[data-tion-position='"+contractCode+"'] li[class = 'position5']");
	var $currencyNo = $("ul[data-tion-position='"+contractCode+"'] li[class = 'position6']");
	var $commodityNo = $("ul[data-tion-position='"+contractCode+"'] li[class = 'position7']");
	var $contractNo = $("ul[data-tion-position='"+contractCode+"'] li[class = 'position8']");
	$exchangeNo.text(exchangeNo);
	$currencyNo.text(currencyNo);
	$commodityNo.text(commodityNo);
	$contractNo.text(contractNo);
}
/**
 * 根据行情更新挂单列表
 * @param param
 */
function updateDesignateByQuote(param){
	var commodityNo = param.CommodityNo;
	var contractNo = param.ContractNo;
	var exchangeNo = param.ExchangeNo;
	var contractCode = commodityNo + contractNo;
	var designateDom = $("ul[data-tion-des='"+contractCode+"']");
	if(designateDom.html() == undefined){
		return;
	}
	var $exchangeNo = $("ul[data-tion-des='"+contractCode+"'] li[class = 'des7']");
	var $commodityNo = $("ul[data-tion-des='"+contractCode+"'] li[class = 'des8']");
	var $contractNo = $("ul[data-tion-des='"+contractCode+"'] li[class = 'des9']");
	$commodityNo.text(commodityNo);
	$contractNo.text(contractNo);
	$exchangeNo.text(exchangeNo);
}
/**
 * 验证持仓信息是否存在 
 */
function validationPostionIsExsit(param){
	var contractCode = param.ContractCode;
	var positionParam = localCachePostion[contractCode];
	if(positionParam == undefined || positionParam == "undefined" || positionParam == null || $("ul[data-tion-position='"+contractCode+"']").html == undefined){
		return true;
	}else{
		return false;
	}
}
/**
 * 验证资金明细是否存在 
 */
function validationFundDetailsIsExsit(param){
	var currencyNo = param.CurrencyNo;
	var fundDetails = localCacheFundDetail[currencyNo];
	if(fundDetails == undefined || fundDetails == "undefined" || fundDetails == null){
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
				'	<li class="ml" style="width: 80px;">合约代码</li>'+
				'	<li style="width: 80px;">持仓数量</li>'+
				'	<li style="width: 80px;">买卖</li>'+
				'	<li style="width: 100px;">持仓均价</li>'+
				'	<li style="width: 160px;">浮动盈利</li>'+
				'	<li style="width: 80px;">交易所</li>'+
				'	<li style="width: 80px;">币种</li>'+
				'</ul><p class="hold_NoRecord" style="color: #ccc; text-align: center; padding: 10px 0;">暂无记录</p>';
	$("#hold_gdt1").html(html);
}
/**
 * 生成委托信息表头
 */
function generateOrderTitle(){
	var html = '<ul class="tab_lis">'+
				'	<li class="ml">合约代码</li>'+
				'	<li  style = "width:50px;">买卖</li>'+
				'	<li  style="width: 50px;">委托价</li>'+
				'	<li style = "width:50px;">委托量</li>'+
				'	<li style="width: 70px;">订单类型</li>'+
				'	<li  style="width: 70px;">委托状态</li>'+
				'	<li style = "width:70px;" >成交均价</li>'+
				'	<li style = "width:50px;"  >成交量</li>'+
				'	<li style = "width:120px;">委托时间</li>'+
				'	<li style = "width:80px;">订单号</li>'+
				'   <li style="width: 80px;">反馈信息</li>'+
				'</ul><p class="order_NoRecord" style="color: #ccc; text-align: center; padding: 10px 0;">暂无记录</p>';
	$("#order_gdt1").html(html);
}
/**
 * 生成挂单信息表头
 */
function generateDesignateTitle(){
	var html = '<ul class="tab_lis">'+
				'	<li class="ml">合约代码</li>'+
				'	<li>合约名称</li>'+
				'	<li>买卖</li>'+
				'	<li  style="width: 120px;">委托价</li>'+
				'	<li>委托量</li>'+
				'	<li>挂单量</li>'+
				'</ul><p class="des_NoRecord" style="color: #ccc; text-align: center; padding: 10px 0;">暂无记录</p>';
	$("#des_gdt1").html(html);
}
/**
 * 生成成交记录表头
 */
function generateTradeSuccessTitle(){
	var html = '<ul class="tab_lis">'+
				'	<li class="ml">合约代码</li>'+
				'	<li  style="width: 40px;">买卖</li>'+
				'	<li  style="width: 70px;">成交均价</li>'+
				'	<li  style="width: 50px;">成交量</li>'+
				/*'	<li  style="width: 70px;">币种</li>'+*/
				'	<li  style="width: 250px;">成交编号</li>'+
				'	<li  style="width: 80px;">订单号</li>'+
				'	<li  style="width: 120px;">成交时间</li>'+
				'	<li  style="width: 40px;">交易所</li>'+
				'</ul><p class="trade_NoRecord" style="color: #ccc; text-align: center; padding: 10px 0;">暂无记录</p>';
	$("#trade_gdt1").append(html);
}
/**
 * 生成资金明细表头
 */
function generateAccountTitle(){
	var html = '<ul class="tab_lis">'+
				'	<li class="ml">币种</li>'+
				'	<li>今权益</li>'+
				'	<li>今可用</li>'+
				'	<li>保证金</li>'+
				'	<li>今日浮盈</li>'+
				'	<li>维持保证金</li>'+
				'	<li>昨权益</li>'+
				'	<li>昨结存</li>'+
				'	<li>今结存</li>'+
				'	<li>冻结资金</li>'+
				'	<li>盈利率</li>'+
				'</ul><p class="account_NoRecord" style="color: #ccc; text-align: center; padding: 10px 0;">暂无记录</p>';
	$("#account_gdt1").append(html);
}
/**
 * 生成持仓操作节点
 */
function generateHoldHandleDom(){
	var html =  '<ul class="caozuo" style = "display:none;">'+
			    '	<li><a href="javascript:void(0);" id = "allPosition">全部平仓</a></li>'+
				'	<li><a href="javascript:void(0);" id = "aPosition">平仓</a></li>'+
				'	<li><a href="javascript:void(0);" id = "positionBckhand">反手</a></li>'+
			    '</ul>';
	$("#hold_title").append(html);
}
/**
 * 生成挂单操作节点
 */
function generateDesHandleDom(){
	var html =  '<ul class="caozuo"  style = "display:none;">'+
				'	<li><a href="javascript:void(0);" id = "allDesOrder">全撤</a></li>'+	
				'	<li><a href="javascript:void(0);" id = "desOrder">撤单</a></li>'+
				'	<li><a href="javascript:void(0);" id = "updateDesOrder">改单</a></li>'+
				'</ul>';
	$("#des_title").append(html);
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
			var orderId = $this.attr("data-order-des");
			selectDesgnate["contraction"] = $this.attr("data-tion-des");
			selectDesgnate["designateIndex"] = $this.attr("data-index-des");
			var orderPrice = $("ul[data-order-des='"+orderId+"'] li[class = 'des3']").text();
			var orderNum = $("ul[data-order-des='"+orderId+"'] li[class = 'des4']").text();
			var orderId = $("ul[data-order-des='"+orderId+"'] li[class = 'des10']").text();
			selectDesgnate["orderPrice"] = orderPrice;
			selectDesgnate["orderNum"] = orderNum;
			selectDesgnate["orderId"] = orderId;
		});
	});
}
/**
 * 绑定交易成功点击事件
 * @param cls
 */
function addTradeSuccessBindClick(cls){
	$(function(){
		$("."+cls+"").bind("click",function(){
			var $this = $(this);
		});
	});
}
/**
 * 绑定资金明细点击事件
 * @param cls
 */
function addFundDetailBindClick(cls){
	$(function(){
		$("."+cls+"").bind("click",function(){
			var $this = $(this);
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
 * 更新成交记录索引
 * @returns
 */
function updateTradesIndex(){
	tradesIndex++;
}
/**
 * 更新资金列表索引
 */
function updateFundsDetailsIndex(){
	fundsDetailsIndex++;
}
/**
 * 删除持仓中的元素节点 
 * @param {Object} 删除节点
 */
function delPositionDom(contractCode){
	$(function(){
		$("ul[data-tion-position='"+contractCode+"']").remove();
		if($(".tab_position").length == 0){
			$(".hold_NoRecord").css("display","block");
		}
	});
}
/**
 * 删除挂单中的元素节点 
 * @param {Object} orderId
 */
function delDesignatesDom(orderId){
	$("ul[data-order-des='"+orderId+"']").remove();
	if($(".tab_des").length == 0){
		$(".des_NoRecord").css("display","block");
	}
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
 * 初始化全局缓存持仓合约的对象数组
 */
function deleteAllPositionsLocalCache(){
	localCachePostion = {};
}
/**
 * 初始化缓存持仓列表品种+合约
 */
function deleteAllPositionContractCode(){
	localCachePositionContractCode = {};
}
/**
 * 清除选中的持仓合约
 */
function deleteSelectPostion(){
	selectPostion = {};
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
/**
 * 初始化全局缓存挂单合约的对象数组
 */
function deleteAllDesgnatesLocalCache(){
	localCacheDesignate = {};
}
/**
 * 初始化缓存挂单列表信息的品种+合约
 */
function deleteAllDesgnatesContractCode(){
	localCachedesignateContractCode = {};
}
/**
 * 清除选中的持仓合约
 */
function deleteSelectDesgnate(){
	selectDesgnate = {};
}
function addBindsss(cls){
	$("."+cls+"").bind("click",function(){
		var $this = $(this);
		var text = $this.text();
		$("#quotation_account").val(text);
		$("#more_account").css("display","none");
	});
}
/**
 * 加载用户的账户信息
 */
function loadOperateLogin(){
	$.ajax({
		url:"..../../user/operateLogin",
		type:"get",
		success:function(result){
			if(result){
				var _data = result.data;
				if(_data != undefined){
					var data = _data.data;
					var dataLength = data.length;
					for(var i = 0  ; i < dataLength ; i ++){
						var _data = data[i];
						var cls = "selectAccount"+i;
						var  html = '<p class = "'+cls+'">'+_data.tranAccount+'</p>';
						$("#more_account").append(html);
						addBindsss(cls);
					}
				}
			}
		}
	});
}
/**
 * 版本是否获取成功
 */
var isGetVersion = false;
var uid = undefined;
$(function(){
	/**
	 * 初始化交易配置 --> trade.config
	 */
	initTradeConfig();
	//getVersion();
	validateIsGetVersion();
	$(".signLogin_span").bind("click",function(){
		var $this = $(this);
		var ismock = $this.attr("data-tion");
		setTradeConfig(ismock);
	});
	
	if(uid != undefined && uid.length > 0 ){
		loadOperateLogin();
	}
	$("#quotation_account").mouseover(function(){
		$("#more_account").css("display","block");
	});
	$("#more_account").mouseover(function(){
		$("#more_account").css("display","block");
	});
	$("#quotation_account").mouseout(function(){
		$("#more_account").css("display","none");
	});
	$("#quotation_account").blur(function(){
		$("#more_account").css("display","none");
	});
	$(".backPassword").click(function(){
		layer.open({
			  type: 1,
			  title:"忘记密码",
			  skin: 'layui-layer-rim', //加上边框
			  area: ['330px', '160px'], //宽高
			  content: $("#back_passwork").html(),
			  btn:['没有账号?立即申请','关闭'],
			  yes:function(){
				  window.open("../../outDisk/index");
				  layer.closeAll();
			  },
			  btn2: function(){
					 layer.closeAll();
				 }
			});
	});
	
	$(".open_account").click(function(){
		layer.open({
			  type: 1,
			  title:"忘记密码",
			  skin: 'layui-layer-rim', //加上边框
			  area: ['360px', '170px'], //宽高
			  content: $("#open_account").html(),
			  btn:['关闭'],
			  btn2: function(){
					 layer.closeAll();
				 }
			});
	});
	bindOpertion();
	$("#select_commodity").change(function(){
		var contractCode = $("#select_commodity").val();
		selectCommodity(contractCode);
	});
	$("#show_login").show();
	$("#show_user_info").hide();
	$("#firm_btn").click(function(){
		if(tradeWebSocketIsMock == 0){
			username = $("#firm_name").val();
			if(username == undefined || username.length == 0){
				layer.tips("请输入交易账号", "#firm_name",{tips:3});
				return;
			}
			password = $.base64.encode($("#firm_password").val());
			if(password == undefined || password.length == 0){
				layer.tips("请输入交易密码", "#firm_password",{tips:3});
				return;
			}
			if($("#firm_btn").text() == "登录中"){
				return;
			}
			$("#firm_btn").text("登录中");
		}
		tradeLogin();
	});
	$("#simulation_btn").click(function(){
		if(tradeWebSocketIsMock == 1){
			username = $("#simulation_mame").val();
			if(username == undefined || username.length == 0){
				layer.tips("请输入模拟交易账号", "#simulation_mame",{tips:3});
				return;
			}
			password = $.base64.encode($("#simulation_password").val());
			if(password == undefined || password.length == 0){
				layer.tips("请输入模拟交易密码", "#simulation_password",{tips:3});
				return;
			}
			if($("#simulation_btn").text() == "登录中"){
				return;
			}
			$("#simulation_btn").text("登录中");
		}
		tradeLogin();
	});
	$("#float_buy").text("市价");
	$("#float_sell").text("市价");
	$("#trade_loginOut").click(function(){
		tipConfirm("确认退出当前登录吗", tradeLoginOut, cancleCallBack);
	});
	$('#money_number').bind('input propertychange', function() {  
	    $('#float_buy').text($(this).val());  
	    $('#float_sell').text($(this).val());  
	});  
	$("input[type = 'radio']").bind("click",function(){
		var $this = $(this);
		var val = $this.val();
		var money = $("#money_number").val();
		if(val == 1){
			$("#float_buy").text("市价");
			$("#float_sell").text("市价");
		}else if(val == 0){
			$("#float_buy").text(money);
			$("#float_sell").text(money);
		}
	});
});
/**
 * 获取交易版本
 */
function getVersion(){
	$.ajax({
		url:"../../socket/config/getVersion",
		type:"get",
		data:{
			appVersion:tradeWebCmsVersion
		},
		success:function(result){
			if(result.success){
				var data = result.data.data;
				tradeWebsocketUrl = data.socketUrl;
				tradeWebSocketVersion = data.socketVersion;
				tradeWebSocketModelUrl = data.socketModelUrl;
				isGetVersion = true;
			}
		}
	});
}
/**
 * 验证socket版本是否获取成功
 */
function validateIsGetVersion(){
	var i = 0;
	var initIsGetVersion = setInterval(function(){
		i++;
		if(!isGetVersion){
			if(i > 50){
				isGetVersion = true;
			}
		}
		if(isGetVersion){
			initSocketTrade();
			clearInterval(initIsGetVersion);
		}
	}, 200);
}
function initSocketTrade(){
	var mock = getTradeCookie("isMock");
	if(mock == null){
		mock = 0;
	}
	setTradeConfig(mock);
	/**
	 * 初始化交易
	 */
	initTrade();
}
function selectCommodity(param){
	var contractCode = param;
	var localCommodity = localCacheCommodity[contractCode];
	var localQoute = localCacheQuote[contractCode];
	var miniTikeSize = localCommodity.MiniTikeSize;
	var lastPrice = localQoute.LastPrice;
	$("#trade_data #lastPrice").val(lastPrice);
	$("#trade_data #miniTikeSize").val(miniTikeSize);
	$("#trade_data #contractSize").val(localCommodity.ContractSize);
	$("#trade_data #exchangeNo").val(localCommodity.ExchangeNo);
	$("#trade_data #commodeityNo").val(localCommodity.CommodityNo);
	$("#trade_data #contractNo").val(localCommodity.MainContract);
	$("#trade_data #doSize").val(localCommodity.DotSize);
	$("#money_number").val(localQoute.LastPrice);
	$("#commodity_title").text(localCommodity.CommodityName+"  "+contractCode);
	var val = $('input:radio:checked').val();
	if(val == 0){
		var money = $("#money_number").val();
		$("#float_buy").text(money);
		$("#float_sell").text(money);
	}
	//$("#float_buy").text(doGetMarketPrice(lastPrice, miniTikeSize, 0));
	//$("#float_sell").text(doGetMarketPrice(lastPrice, miniTikeSize, 1));
	setMoneyNumberIndex(0);
	 var left_xiangmu   = $(".futuresList .left_xiangmu");
	left_xiangmu.each(function(){
		 left_xiangmu.removeClass('on');
	 });
	var obj = $("ul[data-tion-com='"+contractCode+"']");
	obj.addClass('on');
	obj.click();
	setLocalCacheSelect(contractCode);
	clearRightData();
	updateRight(localQoute);
}
/**
 * 绑定交易操作事件
 */
/**
 * 标识是否是改单操作
 * 
 */
var isUpdateOrder = false;
/**
 * 是否是下单操作
 */
var isBuy = false;
function bindOpertion(){
	$(".trade_buy").bind("click",function(){
		if(isLogin){
			var $this = $(this);
			var lastPrice = $("#lastPrice").val();
			var miniTikeSize = $("#miniTikeSize").val();
			var orderNum = $("#entrust_number").val();
			var priceType = $("input[type='radio']:checked").val();
			var tradeDrection = $this.attr("data-tion-buy");
			var orderPrice = 0.00;
			if(priceType == 1){
				orderPrice = doGetMarketPrice(lastPrice, miniTikeSize, tradeDrection);
			}else{
				orderPrice = $("#money_number").val();
			}
			var commodityNo = $("#commodeityNo").val();
			var contractNo = $("#contractNo").val();
			var tipContent = "确认提交订单:合约【"+commodityNo+contractNo+"】,价格:【"+orderPrice+"】,手数:【"+orderNum+"】,买卖方向:【"+analysisBusinessBuySell(tradeDrection)+"】";
			layer.confirm(tipContent+"?", {
			  btn: ['确认','取消'] //按钮
			}, function(){
				doInsertOrder(orderNum,tradeDrection,orderPrice);
			}, function(){
				cancleCallBack();
			});
		}else{
			tip("未登录,请先登录");
		}
	});
	$("#allPosition").bind("click",function(){
		if(isLogin){
			if(postionIndex == 0){
				tip("没有需要平仓的数据");
				return;
			}
			var positionFlag = false;
			for(var i = 0 ; i < postionIndex;i++){
				if( $(".postion-index"+i+"").html() == undefined){
					continue;
				}else{
					positionFlag = true;
				}
			}
			if(!positionFlag){
				tip("没有需要平仓的数据");
				return;
			}
			var tipContent = "确认全部平仓";
			tipConfirm(tipContent,doInsertAllSellingOrder,cancleCallBack);
		}else{
			tip("未登录,请先登录");
		}
	});
	
	$("#aPosition").bind("click",function(){
		if(isLogin){
			var contractCode = selectPostion["contractCode"];
			var postionIndex = selectPostion["postionIndex"];
			if(contractCode == undefined || $(".postion-index"+postionIndex+"").html() == undefined){
				tip("请选择一项需要平仓的数据");
				return;
			}
			var tipContent = "确认平仓合约【"+contractCode+"】";
			tipConfirm(tipContent,doInsertSellingOrder,cancleCallBack);
		}else{
			tip("未登录,请先登录");
		}
	});
	$("#positionBckhand").bind("click",function(){
		if(isLogin){
			var contractCode = selectPostion["contractCode"];
			var postionIndex = selectPostion["postionIndex"];
			if(contractCode == undefined  || $(".postion-index"+postionIndex+"").html() == undefined){
				tip("请选择一项需要反手的数据");
				return;
			}
			var tipContent = "确认反手操作合约【"+contractCode+"】"; 
			tipConfirm(tipContent,doInsertBackhandOrder,cancleCallBack);
		}else{
			tip("未登录,请先登录");
		}
	});
	$("#allDesOrder").bind("click",function(){
		if(isLogin){
			if(designateIndex == 0){
				tip("没有需要撤单的数据");
				return;
			}
			var designateFlag = false;
			for(var i = 0 ; i < designateIndex ; i++){
				if($(".des-index"+i+"").html() == undefined){
					continue;
				}else{
					designateFlag = true;
				}
			}
			if(!designateFlag){
				tip("没有需要撤单的数据");
				return;
			}
			var tipContent = "确认全部撤单合约"; 
			tipConfirm(tipContent,doInsertAllCancleOrder,cancleCallBack);
		}else{
			tip("未登录,请先登录");
		}
	});
	$("#desOrder").bind("click",function(){
		if(isLogin){
			var contractCode = selectDesgnate["contraction"];
			var designateIndex = selectDesgnate["designateIndex"];
			if(contractCode == undefined || $(".des-index"+designateIndex+"").html() == undefined){
				tip("请选择一项需要撤单的数据");
				return;
			}
			var tipContent = "确认撤单合约【"+contractCode+"】"; 
			tipConfirm(tipContent,doInsertCancleOrder,cancleCallBack);
		}else{
			tip("未登录,请先登录");
		}
	});
	$("#updateDesOrder").bind("click",function(){
		if(isLogin){
			var contractCode = selectDesgnate["contraction"];
			var designateIndex = selectDesgnate["designateIndex"];
			if(contractCode == undefined || $(".des-index"+designateIndex+"").html() == undefined){
				tip("请选择一项需要改单的数据");
				return;
			}
			var orderPrice = selectDesgnate["orderPrice"];
			var orderNum = selectDesgnate["orderNum"];
			if(orderPrice == undefined){
				orderPrice = "";
			}
			if(orderNum == undefined){
				orderNum = 0;
			}
			layer.open({
				  type: 1,
				  title:"改单",
				  area: ['100px', '200px'],
				  skin: 'layui-layer-rim', //加上边框
				  area: ['420px', '240px'], //宽高
				  content: '<div  style = "background:#F2F2F2;padding:50px 50px 50px 50px">'+
							'	<p>委托价格：<input style="background:#FFFFFF;border:1px solid  #ABABAB" type="text" value = "'+orderPrice+'"  id="update_des_price"/></p>'+
							'	<p style = "padding-top:10px;">委托数量：<input  style="background:#FFFFFF;border:1px solid  #ABABAB" type="number" value = "'+orderNum+'"   id="update_des_number"/></p>'+
							'</div>',
					 btn: ['确认改单', '关闭'], //
					 yes: function(){
						 var price = $("#update_des_price").val();
						 var number = $("#update_des_number").val();
						 if(price <= 0){
							 layer.tips("价格输入错误", '#update_des_price');
							 return;
						 }
						 if(number <= 0){
							 layer.tips("数量输入错误", '#update_des_number');
							 return;
						 }
						 var tipContent = "确认改单合约【"+contractCode+"】"; 
						 tipConfirm(tipContent,doInsertChangeSingleOrder,cancleCallBack);
					 },
					 btn2: function(){
						 layer.closeAll();
					 }
				});
			//$("#change").show();
			//
		}else{
			tip("未登录,请先登录");
		}
	});
}
/**
 * 下单
 */
function doInsertOrder(orderNum,tradeDrection,orderPrice){
	var lastPrice = $("#lastPrice").val();
	if(validationInputPrice(lastPrice)){
		tip("最新价格错误");
		return;
	}
	if(isNaN(orderNum) || validationInputPrice(orderNum)){
		tip("购买手数输入错误");
		return;
	}
	if(validationInputPrice(orderPrice)){
		tip("交易价格错误");
		return ;
	}
	var exchanageNo = $("#exchangeNo").val();
	var commodeityNo = $("#commodeityNo").val();
	var contractNo = $("#contractNo").val();
	var priceType = $("input[type='radio']:checked").val();
	Trade.doInsertOrder(exchanageNo,commodeityNo,contractNo,orderNum,tradeDrection,priceType,orderPrice,0,doGetOrderRef());
	tip("合约【"+commodeityNo+contractNo+"】提交成功,等待交易");
	isBuy = true;
}
/**
 * 全部平仓操作
 */
function doInsertAllSellingOrder(){
	for(var i = 0 ; i < postionIndex;i++){
		var contractCode = localCachePositionContractCode[i];
		if(contractCode == undefined || $(".postion-index"+i+"").html() == undefined){
			continue;
		}
		var tradeParam = doGetSellingBasicParam(contractCode);
		if(!tradeParam){
			continue;
		}
		var param = new Array();
		param[0] = tradeParam;
		closing(param);
	}
	tip("提交成功,等待交易");
}
/**
 * 平仓操作
 */
function doInsertSellingOrder(){
	var contractCode = selectPostion["contractCode"];
	var postionIndex = selectPostion["postionIndex"];
	if(contractCode == undefined){
		tip("未选择平仓信息");
		return;
	}
	var tradeParam = doGetSellingBasicParam(contractCode);
	if(!tradeParam){
		return;
	}
	var param = new Array();
	param[0] = tradeParam;
	closing(param);
	tip("提交成功,等待交易");
}
/**
 * 反手操作
 */
function doInsertBackhandOrder(){
	var contractCode = selectPostion["contractCode"];
	var postionIndex = selectPostion["postionIndex"];
	if(contractCode == undefined){
		tip("未选择反手信息");
		return;
	}
	var tradeParam = doGetSellingBasicParam(contractCode);
	var exchangeNo = tradeParam.ExchangeNo;
	var commodityNo = tradeParam.CommodityNo;
	var contractNo = tradeParam.ContractNo;
	var orderNum = tradeParam.OrderNum * 2;
	var tradeDrection = tradeParam.Drection;
	var contractCode = commodityNo + contractNo;
	var orderPrice = tradeParam.LimitPrice;
	Trade.doInsertOrder(exchangeNo,commodityNo,contractNo,orderNum,tradeDrection,0,orderPrice,0,doGetOrderRef());
	tip("合约【"+contractCode+"】提交成功,等待交易");
	isBuy = true;
}
/**
 * 全部撤单操作
 */
function doInsertAllCancleOrder(){
	for(var i = 0 ; i < designateIndex ; i++){
		var contractCode = localCachedesignateContractCode[i];
		if(contractCode == undefined || $(".des-index"+i+"").html() == undefined){
			continue;
		}
		var tradeParam = doGetCancleOrderBasicParam(contractCode);
		var param = new Array();
		param[0] = tradeParam
		cancleOrder(param);
	}
	tip("提交成功,等待撤单");
}
/**
 * 撤单操作
 */
function doInsertCancleOrder(){
	var orderId = selectDesgnate["orderId"];
	var contractCode = selectDesgnate["contraction"];
	var designateIndex = selectDesgnate["designateIndex"];
	if(contractCode == undefined || $(".des-index"+designateIndex+"").html() == undefined){
		return;
	}
	var tradeParam = doGetCancleOrderBasicParam(orderId);
	var param = new Array();
	param[0] = tradeParam
	cancleOrder(param);
	tip("合约【"+contractCode+"】提交成功,等待撤单");
}
/**
 * 改单操作
 */

function doInsertChangeSingleOrder(){
	var orderId = selectDesgnate["orderId"];
	var contractCode = selectDesgnate["contraction"];
	var designateIndex = selectDesgnate["designateIndex"];
	if(contractCode == undefined || $(".des-index"+designateIndex+"").html() == undefined){
		return;
	}
	 var orderPrice = $("#update_des_price").val();
	 var orderNum = $("#update_des_number").val();
	if(validationInputPrice(orderPrice)){
		tip("改单价格错误");
		return;
	}
	if(validationInputPrice(orderNum)){
		tip("改单数量错误");
		return;
	}
	var tradeParam = doGetModifyOrderBasicParam(orderId);
	tradeParam.orderPrice = orderPrice;
	tradeParam.orderNum = orderNum;
	var param = new Array();
	param[0]=tradeParam;
	modifyOrder(param);
	layer.closeAll();
	tip("合约【"+contractCode+"】提交成功,等待交易");
	isUpdateOrder = true;
}
/**
 * 获取平仓的基本信息
 * @param obj
 */
function doGetSellingBasicParam(obj){
	var contract = obj;
	var $contractCode  = contract;
	var $drection =  $("ul[data-tion-position='"+contract+"'] li[class = 'position2']").attr("data-drection");
	var $holdNum =  $("ul[data-tion-position='"+contract+"'] li[class = 'position1']").text();
	var $openAvgPrice =  $("ul[data-tion-position='"+contract+"'] li[class = 'position9']").text();
	var $floatingProfit =  $("ul[data-tion-position='"+contract+"'] li[class = 'position4']").text();
	var $commodityNo =  $("ul[data-tion-position='"+contract+"'] li[class = 'position7']").text();
	var $contractNo =  $("ul[data-tion-position='"+contract+"'] li[class = 'position8']").text();
	var $exchangeNo =  $("ul[data-tion-position='"+contract+"'] li[class = 'position5']").text();
	var drection = 0;
	if($drection == 0){
		drection = 1;	
	}
	var contractCode = $commodityNo + $contractNo;
	var localCommodity = getLocalCacheCommodity(contractCode);
	var localQuote = getLocalCacheQuote(contractCode);
	var miniTikeSize = 0.00;
	var lastPrice = 0.00;
	if(localCommodity != undefined && localQuote != undefined){
		miniTikeSize = localCommodity.MiniTikeSize;
		lastPrice = localQuote.LastPrice;
	}
	if(validationInputPrice(lastPrice)){
		tip("最新价格错误");
		return false;
	}
	var limitPirce = doGetMarketPrice(lastPrice,miniTikeSize,drection);
	console.log(limitPirce);
	if(validationInputPrice(limitPirce)){
		tip("平仓价格错误");
		return false;
	}
	var sellingParam = createSellingParam($exchangeNo,$commodityNo,$contractNo,$holdNum,drection,0,Math.abs(limitPirce),0,doGetOrderRef());
	return sellingParam;
}
/**
 * 获取撤单的基本信息
 * @param obj
 */
function doGetCancleOrderBasicParam(obj){
		var contract = obj;
		var $orderId  = contract;
		var $drection =  $("ul[data-order-des='"+contract+"'] li[class = 'des2']").attr("data-drection");
		var $orderPrice =  $("ul[data-order-des='"+contract+"'] li[class = 'des3']").text();
		var $orderNum =  $("ul[data-order-des='"+contract+"'] li[class = 'des4']").text();
		var $cdNum =  $("ul[data-order-des='"+contract+"'] li[class = 'des5']").text();
		var $OrderSysID =  $("ul[data-order-des='"+contract+"'] li[class = 'des6']").text();
		var $commodityNo =  $("ul[data-order-des='"+contract+"'] li[class = 'des8']").text();
		var $contractNo =  $("ul[data-order-des='"+contract+"'] li[class = 'des9']").text();
		var $exchangeNo =  $("ul[data-order-des='"+contract+"'] li[class = 'des7']").text();
		var $orderId =  $("ul[data-order-des='"+contract+"'] li[class = 'des10']").text();
		var cancleOrderParam = createCancleOrderParam($OrderSysID,$orderId,$exchangeNo,$commodityNo,$contractNo,$orderNum,$drection,Math.abs($orderPrice));
	return cancleOrderParam;
}
/**
 * 获取改单的基本信息
 * @param obj
 */
function doGetModifyOrderBasicParam(obj){
		var contract = obj;
		var $contractCode  = contract;
		var $drection =  $("ul[data-order-des='"+contract+"'] li[class = 'des2']").attr("data-drection");
		var $orderPrice =  $("ul[data-order-des='"+contract+"'] li[class = 'des3']").text();
		var $orderNum =  $("ul[data-order-des='"+contract+"'] li[class = 'des4']").text();
		var $cdNum =  $("ul[data-order-des='"+contract+"'] li[class = 'des5']").text();
		var $OrderSysID =  $("ul[data-order-des='"+contract+"'] li[class = 'des6']").text();
		var $commodityNo =  $("ul[data-order-des='"+contract+"'] li[class = 'des8']").text();
		var $contractNo =  $("ul[data-order-des='"+contract+"'] li[class = 'des9']").text();
		var $exchangeNo =  $("ul[data-order-des='"+contract+"'] li[class = 'des7']").text();
		var $orderId =  $("ul[data-order-des='"+contract+"'] li[class = 'des10']").text();
		var $triggerPrice =  $("ul[data-order-des='"+contract+"'] li[class = 'des11']").text();
		var modifyOrderParam = createModifyOrderParam($OrderSysID,$orderId,$exchangeNo,$commodityNo,$contractNo,$orderNum,$drection,Math.abs($orderPrice),$triggerPrice);
	return modifyOrderParam;
}
/**
 * 计算列表的浮动盈亏
 */
function sumListfloatingProfit(){
	var currencySize = localCurrencyNo.length;
	for(var i = 0 ; i < currencySize ; i++){
		var currencyNo = localCurrencyNo[i];
		if(currencyNo == undefined || currencyNo.length <= 0){
			continue;
		}
		var  floatingProfitDom = $("ul[data-tion-fund='"+currencyNo+"'] li[class = 'detail_floatingProfit']");
		var p =  $(".tab_position");
		if(p.length == 0){
			floatingProfitDom.text(0.00);
			continue ;
		}
		var postionDom = $(".position-currency"+currencyNo);
		if(postionDom.html() == undefined || postionDom.length <= 0){
			continue
		}
		var price  = 0.00;
		$.each(postionDom,function(i,item){
			var $this = $(this);
			price = price + Number($this.find("li[class = 'position10']").text());
		});
		floatingProfitDom.text(parseFloat(price).toFixed(2));
	}
}
/**
 * 更新持仓总盈亏
 */
function updateHoldProfit(){
	var price = 0.00;
	for (var i = 0; i < fundsDetailsIndex; i++) {
		if($(".funds-index"+i).html() != undefined){
			var floatingProfit = $("#floatingProfit");
			var floating = $(".funds-index"+i+" li[class = 'detail_floatingProfit']").text();
			var currencyRate = $(".funds-index"+i+" li[class = 'detail_currencyRate']").text();
			var total = floating*currencyRate;
			/*if(isNaN(total)){
				total = 0;
			}*/
			price = price + total;
			if(price < 0){
				floatingProfit.css("color","#0bffa4");
			}else if(price > 0){
				floatingProfit.css("color","#ff5500");
			}else if(price == 0){
				floatingProfit.css("color","#FFFFFF");
			}
			floatingProfit.text(parseFloat(price).toFixed(2));
		}
	}
}
/**
 * 更新账户资产
 */
function updateAccountBalance(){
	var floatingProfit = $("#floatingProfit").text();
	var todayBalance = $("#todayBalance");
	var todayCanUse = $("#todayCanUse");
	todayBalance.text(parseFloat(loadCachTodayBanlance+Number(floatingProfit)).toFixed(2));
	todayCanUse.text(parseFloat(loadCachTodayCanuse+Number(floatingProfit)).toFixed(2));
}
/**
 * 清除交易列表的数据并生成操作按钮
 */
function clearTradListData(){
	$("#account_gdt1").html("");
	$("#order_gdt1").html("");
	$("#des_gdt1").html("");
	$("#trade_gdt1").html("");
	$("#hold_gdt1").html("");
	$("#todayBalance").html("0.00");
	$("#deposit").html("0.00");
	$("#todayCanUse").html("0.00");
	$("#floatingProfit").html("0.00");
	$("#closeProfit").html("0.00");
	$(".caozuo").hide();
	generatePostionTitle();
	generateDesignateTitle();
	generateAccountTitle();
	generateOrderTitle();
	generateTradeSuccessTitle();
}
/**
 * 清理全局缓存数据
 */
function clearLocalCacheData(){
	holdFirstLoadDataIndex = 0;
	accountFirstLoadDataIndex = 0;
	orderFirsetLoadDataIndex = 0;
	tradeFirsetLoadDataIndex = 0;
	deleteAllDesgnatesLocalCache();
	deleteAllDesgnatesContractCode();
	deleteSelectDesgnate();
	deleteAllPositionsLocalCache();
	deleteAllPositionContractCode();
	deleteSelectPostion();
	postionIndex = 0;
	designateIndex = 0;
	localCacheFundDetail = {};
	uehIndex = 0;
	loadCachBanlance = {};
	loadCachDeposit = {};
	loadCachCanuse = {};
	loadCurrencyRate = {};
	loadCachAccountNo = {};
	localCacheCurrencyAndRate = {};
	orderIndex=0;
	fundsDetailsIndex = 0;
	loadCachFloatingProfit = {};
	loadCachCloseProfit = {};
	localCurrencyNo = [];
	loadCachTodayBanlance = 0;
	loadCachTodayCanuse = 0;
	tradeSuccessLoadFlag = false;
	localCachePositionRecentData={};
	resultInsertOrderId={};
	isUpdateOrder = false;
	isBuy = false;
	isGetVersion = false;
}
/**
 * 输入价格或数量验证 
 */
function validationInputPrice(obj){
	if(obj == undefined || obj == null || obj.length == 0 || parseFloat(obj) <= 0){
		return true;
	}else{
		return false;
	}
}

function tabOn() {
	/*交易ul li  odd even odd  li:nth-of-type(even)*/
	//$(".quotation_detailed .quotation_detailed_title .tab_content:even").addClass("even");
	$(".quotation_detailed .quotation_detailed_title .tab_content").click(function() {
		var _this = $(this);
		$(".quotation_detailed .quotation_detailed_title .tab_content").removeClass("on");
		_this.addClass("on");
	});
}