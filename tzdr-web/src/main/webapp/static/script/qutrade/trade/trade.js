//持仓发送请求次数记录
var holdFirstLoadDataIndex = 0;
//个人账户信息发送请求次数纪录
var accountFirstLoadDataIndex = 0;
//委托发送请求次数记录
var orderFirsetLoadDataIndex = 0;
//成功记录发送请求次数记录
var tradeFirsetLoadDataIndex = 0;
//止损单查询发送请求次数记录
var stopLossLoadDataIndex = 0;
//条件单查询发送请求次数记录
var conditionLoadDataIndex = 0;
//存储判断止损止盈提示
var textList=["止损","止盈"];
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
	//tradeSuccessLoadFlag = true;
	localCachePositionRecentData = {}; 
	localCachePostion = {};
	$("#hold_gdt1").html("");
	generatePostionTitle();
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
	}else if (method == "OnRspQryAccount"){
		if(stopLossLoadDataIndex == 0 && tradeWebSocketIsMock == 1){
			Trade.doQryStopLoss(username);
			stopLossLoadDataIndex++;
		} 
	}else if(method == "OnRspQryStopLoss"){
		if(conditionLoadDataIndex == 0  && tradeWebSocketIsMock == 1){
			Trade.doQryCondition(username);
			conditionLoadDataIndex++; 
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
var referCount = 0 ;
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
				$("#stoploss_top_title").hide();
				$("#condition_top_title").hide();
				$("#stop_loss_conditon_x").hide();
				$("#stopLossAndtraders").hide();
				$("#stoploss_top_title").hide();
				$("#condition_top_title").hide();
			}else if(tradeWebSocketIsMock == 1){
				$("#ismockReak").text("模拟账户:");
				$("#stoploss_top_title").show();
				$("#condition_top_title").show();
				$("#stop_loss_conditon_x").show();
				$("#stopLossAndtraders").show();
				$("#stoploss_top_title").show();
				$("#condition_top_title").show();
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
			clearInterval(tradeIntervalId);
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
			}else{
				tip("提交成功,等待交易");
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
				updateDesignatesDom(orderParam);
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
			$("#floatingProfit").text("0.00");
			$("#floatingProfit").css("color","#FFFFFF");
			/*if(referCount == 0){*/
				tradeSuccessLoadHoldData();
			/*	console.log($(".tab_position"));
				if($(".tab_position").length == 0){
					$("#floatingProfit").text("00.0");
				}*/
		/*	}*/
			/*referCount++;*/
			/*if(isBuy && locaOrderId == locaOrderId){
				tradeSuccessLoadHoldData();
				resultInsertOrderId[orderId] = null;
				isBuy = false;
			}*/
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
			//录入止损止盈请求返回
		}else if(method == "OnRspInsertStopLoss"){
			var stopLossParam = parameters;
			var message = "";
			var status = stopLossParam.Status;
			if(status == 4){
				message = stopLossParam.StatusMsg;
			}else{
				message = "提交成功,单号:【"+stopLossParam.StopLossNo+"】";
				$("#stop_loss").css("display","none");
				 $("#div_Mask").hide();
			}
			
			tip(message);
			appendStopLossData(stopLossParam);
			//查询止损止盈返回
		}else if(method == "OnRspQryStopLoss"){
			var stopLossParam = parameters;
			appendStopLossData(stopLossParam);
			//止损止盈状态返回
		}else if(method == "OnRtnStopLossState"){
			var stopLossParam = parameters;
			updateStopLossData(stopLossParam);
			var status = stopLossParam.Status;
			var stoplossType = stopLossParam.StopLossType;
			var stopLossNo = stopLossParam.StopLossNo;
			if(stoplossType == 0 || stoplossType == 2){
				stoplossType = "止损";
			}else if(stoplossType == 1){
				stoplossType = "止盈";
			}
			if(status == 2){
				status = "已触发";
			}else if(status == 3){
				status = "已取消";
			}else if(status == 4){
				status = "插入失败";
			}else if(status == 5){
				status = "触发失败"; 
			}else{
				status = "更新成功";
				$("#stop_loss").css("display","none");
				$("#div_Mask").hide();
			}
			tip(stoplossType+"单【"+stopLossNo+"】,"+status);
			//查询条件单返回
		}else if(method == "OnRspQryCondition"){
			var conditionParam = parameters;
			appendCondition(conditionParam);
			//录入条件单请求返回
		}else if(method == "OnRspInsertCondition"){
			var conditionParam = parameters;
			var message = "";
			var status = conditionParam.Status;
			if(status == 4){
				message = conditionParam.StatusMsg;
			}else{
				var conditionNo = conditionParam.StopLossNo;
				message = "添加条件单成功,单号:【"+conditionNo+"】";
				$("#condition_money_time").css("display","none");
				 $("#div_Mask").hide();
			} 
			tip(message);
			appendCondition(conditionParam);
		}else if(method == "OnRtnConditionState"){ 
			var conditionParam = parameters;
			var commodityNo = conditionParam.CommodityNo;
			var contractNo = conditionParam.ContractNo;
			var contractCode = commodityNo+contractNo;
			var conditionNo = conditionParam.StopLossNo;
			var status = conditionParam.Status; 
			if(status == 2){
				status = "已触发";
			}else if(status == 3){
				status = "已取消";
				selectCondition = {}; 
			}else if(status == 4){
				status = "插入失败";
			}else if(status == 5){
				status = "触发失败"; 
			}else{
				status = "更新成功";
				$("#condition_money_time").css("display","none");
				 $("#div_Mask").hide();
				/*if(operateConditionType == 0){
					operateConditionType = undefined;
				}*/
			}
			tip("【"+contractCode+"】条件单【"+conditionNo+"】,"+status);
			updateConditionList(conditionParam);
		}
	}else{
		/*if(referCount > 0){
			referCount--;
			tradeSuccessLoadHoldData();
		}*/
		/*if(method == "OnRspQryHold" && tradeSuccessLoadFlag){
			updateOrderUpdatePosition();
			tradeSuccessLoadFlag = false;
			localCachePositionRecentData = {};
		}*/
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
	var floatingProfit = $("#floatingProfit").text();
	var banlance = parseFloat(Number(todayAmount)+Number(unExpiredProfit)+Number(unAccountProfit)+Number(floatingProfit)).toFixed(2);;//今结存+浮盈+未结平盈+未到期平盈
	var canuse = parseFloat(banlance-deposit-frozenMoney).toFixed(2);
	localCacheCurrencyAndRate[currencyNo]  = currency == undefined ? localCacheCurrencyAndRate[currencyNo]:currency;
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
	var closeProfit = parseFloat(param.CloseProfit).toFixed(2);
	var counterFee = parseFloat(param.CounterFee).toFixed(2);
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
				'   <li class = "detail_closeProfit"  style="display:none;">'+closeProfit+'</li>'+
				'   <li class = "detail_counterFee"  style="display:none;">'+counterFee+'</li>'+
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
				'	<li class = "trade5" style="width: 150px;">'+tradeNo+'</li>'+
				'	<li class = "trade6"  style="width: 150px;">'+orderId+'</li>'+
				'	<li class = "trade7"  style="width: 150px;">'+tradeTime+'</li>'+
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
		var localCommodity  = localCacheCommodity[contractAndCommodity];
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
		var html = '<ul class="tab_content '+cls+' '+currcls+' tab_position" data-index-position = "'+postionIndex+'" data-tion-position = "'+contractAndCommodity+'" id = "'+contractAndCommodity+'"> '+
					'	<li class="position0 ml" style="width: 80px;">'+contractAndCommodity+'</li>'+
					'	<li  class = "position1" style="width: 80px;">'+holdNum+'</li>'+
					'	<li  class = "position2" style="width: 80px;" data-drection = "'+drection+'">'+drectionText+'</li>'+
					'	<li  class = "position3" style="width: 100px;">'+holdAvgPrice+'</li>'+
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
	var holdNum = parseInt(param.HoldNum);
	var drection = param.Drection;
	var holdAvgPrice = param.HoldAvgPrice;
	var exchangeNo = param.ExchangeNo;
	var currencyNo = param.CurrencyNo;
	var commdityNo = param.CommodityNo;
	var contractNo = param.ContractNo;
	var contractCode = commdityNo+contractNo;
	var openAvgPrice = param.OpenAvgPrice;
	if(isNaN(holdNum)){
		holdNum = parseInt(param.TradeNum);
	}
	if(openAvgPrice == undefined){
		openAvgPrice = param.TradePrice;
	}
	var localCommodity = localCacheCommodity[contractCode];
	var doSize = 0;
	if(localCommodity != undefined){ 
		doSize = localCommodity.DotSize;
	}
	var $holdNum = $("ul[data-tion-position='"+contractCode+"'] li[class = 'position1']");
	var $drection = $("ul[data-tion-position='"+contractCode+"'] li[class = 'position2']");
	var $holdAvgPrice = $("ul[data-tion-position='"+contractCode+"'] li[class = 'position3']");
	var $floatP = $("ul[data-tion-position='"+contractCode+"'] li[class = 'position8']");
	var $floatingProfit =$("#floatValue"+contractCode);                  
	var oldHoldNum = parseInt($holdNum.text());
	var oldDrection = parseInt($drection.attr("data-drection"));
	var oldPrice = parseFloat($holdAvgPrice.text()).toFixed(doSize) *  oldHoldNum;
	var price = parseFloat(openAvgPrice).toFixed(doSize) * holdNum;
	if(oldDrection == drection){
		oldHoldNum = oldHoldNum + holdNum;
		price = parseFloat(price + oldPrice).toFixed(doSize);
		var openAvgPrice = doGetOpenAvgPrice(price,oldHoldNum,doSize);
		$holdAvgPrice.text(openAvgPrice);
		var floatingProft = 0.00; 
		var floatP = 0.00;
		if(localCommodity != undefined){ 
			var localQuote = getLocalCacheQuote(contractCode);
			var contractSize = localCommodity.ContractSize;
			var miniTikeSize = localCommodity.MiniTikeSize;
			var currencyNo = localCommodity.CurrencyNo;
			floatP = doGetFloatingProfit(parseFloat(localQuote.LastPrice),openAvgPrice,contractSize,miniTikeSize,holdNum,drection);
			if(isNaN(floatP)){
				floatP = 0.00;
			}
			floatingProft = floatP +":"+ currencyNo; 
		}
		$floatingProfit.val(floatingProft);
		$floatP.text(floatP);
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
 * 增加止损录入单的列表
 * @param {Object} param
 */
/**
 * 止损单的索引
 */
var stoplossIndex = 0;
/**
 * 缓存选中的止损单列
 */
var selectStopLoss = {};
/**
 * 缓存止损单的信息
 */
var localCahceStopLossNo = {};
/**
 * 全局保存选中止损单的操作类型 0 - 修改，1-删除 ， 2-暂停
 */
var operationStopLossType = undefined;
function appendStopLossData(param){
		var contractCode = param.CommodityNo+param.ContractNo;
		var stopLossNo = param.StopLossNo;
		var status = param.Status;
		var statusText = analysisStopLossStatus(status);
		var exchangeNo = param.ExchangeNo; 
		var num = param.Num;
		var stopLossType = param.StopLossType;
		var stopLossTypeText = analysisStopLossType(stopLossType);
		var orderType = param.OrderType;
		var orderTypeText = lossOrderType(orderType);
		var holdAvgPrice = param.HoldAvgPrice;
		var holdDrection = param.HoldDrection;
		var holdDrectionText = analysisBusinessDirection(holdDrection);
		var insertTime = param.InsertDateTime;
		var dynamicPrice = param.DynamicPrice;
		var stopLossPrice = param.StopLossPrice;
		var stopLossDiff = param.StopLossDiff;
		var stopLossPriceText = "触发价:";
		if(stopLossType == 2){
			stopLossPriceText="追踪价差:";
			stopLossPrice = stopLossDiff;
		}
		if(status == 2 || status == 3 || status == 4 || status == 5){
			insertTime = param.TriggedTime;
		}
		var cls = "stoploss"+stoplossIndex;
		var html = '<ul class="tab_content '+cls+'" data-tion-index = "'+stoplossIndex+'" data-tion-contractCode = "'+contractCode+'" id = "'+stopLossNo+'">'
			            +'<li class="ml stoploss0" style="width: 100px">'+contractCode+'</li>'
			            +'<li class="stoploss1" style="width: 50px;" data-tion-status="'+status+'">'+statusText+'</li>'
			            +'<li class="stoploss2" style="width: 50px;" data-tion-drection="'+holdDrection+'">'+holdDrectionText+'</li>'
			            +'<li class="stoploss3" style="width: 50px;" data-tion-lossType="'+stopLossType+'">'+stopLossTypeText+'</li>'
			            +'<li class="stoploss4" style="width: 50px;">'+num+'</li>'
			            +'<li class="stoploss5" style="width: 140px;" data-tion-price="'+stopLossPrice+'">'+stopLossPriceText+stopLossPrice+'</li>'
			            +'<li class="stoploss6" style="width: 50px;"  data-tion-orderType = "'+orderType+'">'+orderTypeText+'</li>'
			            +'<li class="stoploss9" style="width: 100px;">当日有效</li>'
			            +'<li class="stoploss7" style="width: 140px;">'+insertTime+'</li>'
			            +'<li class="stoploss8" style = "display:none;">'+stopLossDiff+'</li>'
			        +'</ul>';
		if(status == 0 || status == 1){
			$("#no_stopLoss .tab_lis").after(html);
			$("#nostop_account_norecord").css("display","none");
			addStopLossBindClick(cls);
		}else if(status == 2 || status == 3 || status == 4 || status == 5){
			$("#yes_stopLoss .tab_lis").after(html);
			$("#yesstop_account_norecord").css("display","none");
		}
		updateStoplossIndex();
		localCahceStopLossNo[stopLossNo] = param; 
		tabOn();
}
/**
 * 更新止损单信息
 * @param {Object} param
 */
function updateStopLossData(param){
	var stopLossNo = param.StopLossNo;
	var contractCode = param.CommodityNo+param.ContractNo;
	var stopLossNo = param.StopLossNo;
	var status = param.Status;
	var statusText = analysisStopLossStatus(status);
	var exchangeNo = param.ExchangeNo;
	var num = param.Num;
	var stopLossType = param.StopLossType;
	var stopLossTypeText = analysisStopLossType(stopLossType);
	var stopLossDiff = param.StopLossDiff;
	var orderType = param.OrderType;
	var orderTypeText = lossOrderType(orderType);
	var holdAvgPrice = param.HoldAvgPrice;
	var holdDrection = param.HoldDrection;
	var holdDrectionText = analysisBusinessDirection(holdDrection);
	var insertTime = param.InsertDateTime;
	var dynamicPrice = param.DynamicPrice;
	var stopLossPrice = param.StopLossPrice;
	var stopLossPriceText = "触发价:";
	if(stopLossType == 2){
		stopLossPriceText = "追踪价差:";
		stopLossPrice = stopLossDiff;
	}
	if(status == 2 || status == 3 || status == 4 || status == 5){
		insertTime = param.TriggedTime;
	}
	var $status = $("#"+stopLossNo+" li[class = 'stoploss1']");
	var $holdDrection = $("#"+stopLossNo+" li[class = 'stoploss2']");
	var $stopLossType = $("#"+stopLossNo+" li[class = 'stoploss3']");
	var $num = $("#"+stopLossNo+" li[class = 'stoploss4']");
	var $stopLossPrice = $("#"+stopLossNo+" li[class = 'stoploss5']");
	var $orderType = $("#"+stopLossNo+" li[class = 'stoploss6']");
	var $insertTime = $("#"+stopLossNo+" li[class = 'stoploss7']");
	$status.text(statusText);
	$status.attr("data-tion-status",status);
	$holdDrection.html(holdDrectionText);
	$holdDrection.attr("data-tion-drection",holdDrection);
	$stopLossType.text(stopLossTypeText);
	$stopLossType.attr("data-tion-lossType",stopLossType);
	$num.text(num);
	$stopLossPrice.attr("data-tion-price",stopLossPrice);
	$stopLossPrice.text(stopLossPriceText+stopLossPrice);
	$orderType.text(orderTypeText);
	$insertTime.text(insertTime);
	if(status == 2 || status == 3 || status == 4 || status == 5){
		var html = $("#"+stopLossNo).html();
		$("#yes_stopLoss").append("<ul class = 'tab_content' id = '"+stopLossNo+"'>"+html+"</tr>");
		$("#"+stopLossNo).remove();
		isTableIndex = true;
		selectStopLoss = {};
	}else{
		if(status == 0){
			$("#loss_stop").attr("data-tion-value",2);
			$("#loss_stop").text("暂停");
		}else if(status == 1){ 
			$("#loss_stop").attr("data-tion-value",3);
			$("#loss_stop").text("启动");
		}
	}
	localCahceStopLossNo[stopLossNo] = param; 
}
/**
 * 增加条件单
 * @param {Object} param
 */
/**
 * 条件单列表索引
 */
var conditionIndex = 0;
/**
 * 缓存的条件单信息
 */
var localCacheCondition = {};
/**
 * 缓存选定的列表数据
 * @param {Object} param
 */
var selectCondition = {};
/**
 * 条件单操作标识：0-修改 1-删除 2-暂停 3-启动
 */
var operateConditionType = undefined;
function appendCondition(param){
	var commodityNo = param.CommodityNo;
	var contractNo = param.ContractNo;
	var exchangeNo = param.ExchangeNo;
	var conditionNo = param.StopLossNo; 
	var status = param.Status;
	var statusText = analysisConditionStatus(status);
	var num = param.Num;
	var contractCode = commodityNo+contractNo;
	var conditionType = param.ConditionType;
	var conditionTypeText = analysisConditionType(conditionType);
	var priceTriggerPonit = param.PriceTriggerPonit;
	var compareType = param.CompareType
	var compareTypeText = analysisConditionCompareType(compareType);
	var timeTriggerPoint = param.TimeTriggerPoint;
	var abBuyPoint = param.AB_BuyPoint;
	var abSellPoint = param.AB_SellPoint;
	var orderType = param.OrderType;
	var drection = param.Drection;
	var stopLossType = param.StopLossType;
	var stopLossDiff = param.StopLossDiff;
	var stopWinDiff = param.StopWinDiff;
	var insertTime = param.InsertDateTime;
	var additionFlag = param.AdditionFlag;
	var additionType = param.AdditionType;
	var additionTypeText = "";
	var additionPrice = param.AdditionPrice;
	if(additionFlag == 1){
		additionTypeText = "&nbsp;&nbsp;"+analysisConditionCompareType(additionType)+additionPrice;
	}
	if(conditionType == 0){
		compareTypeText = compareTypeText+priceTriggerPonit;
	}else if(conditionType == 1){
		timeTriggerPoint = timeTriggerPoint.replace(/-/g,"/");
		compareTypeText =  formatDateHHMMSS(new Date(timeTriggerPoint)); 
	} 
	if(status == 2 || status == 3 || status == 4 || status == 5){
		insertTime = param.TriggedTime;
	}
	compareTypeText = compareTypeText+additionTypeText;
	var inserOrderText = analysisBusinessBuySell(drection)+","+lossOrderType(orderType)+","+num+"手";
	var cls = "condition"+conditionIndex;
	var html = '<ul class="testclick tab_content '+cls+'" id = "'+conditionNo+'">'
					+'<li class = "ml condition0" style="width: 80px">'+contractCode+'</li>'
					+'<li class = "condition1" style="width: 50px" data-tion-status = "'+status+'">'+statusText+'</li>'
					+'<li class = "condition2" style="width: 100px" data-tion-conditionType = "'+conditionType+'">'+conditionTypeText+'</li>'
					+'<li class = "condition4" style="width: 120px">'+inserOrderText+'</li>'
					+'<li class = "condition3" style="width: 120px" data-tion-compareType = "'+compareType+'">'+compareTypeText+'</li>'
					+'<li class = "condition5" style="width: 60px">当日有效</li>'
					+'<li class = "condition6" style="width: 130px">'+insertTime+'</li>'
				+'</tr>';  
	if(status == 0 || status == 1){ 
		$("#yes_condition_list").append(html);
		$("#nocondition_account_norecord").css("display","none");
		addConditionBindClick(cls);
	}else if(status == 2 || status == 3 || status == 4 || status == 5){
		$("#no_condition_list").append(html);
		$("#yescondition_account_norecord").css("display","none");
	}
	localCacheCondition[conditionNo] = param;
	conditionIndex++;
	tabOn();
}
/**
 * 修改条件单列表信息
 * @param {Object} param
 */
function updateConditionList(param){
	var commodityNo = param.CommodityNo;
	var contractNo = param.ContractNo;
	var exchangeNo = param.ExchangeNo;
	var conditionNo = param.StopLossNo;
	var status = param.Status;
	var statusText = analysisConditionStatus(status);
	var num = param.Num;
	var contractCode = commodityNo+contractNo;
	var conditionType = param.ConditionType;
	var conditionTypeText = analysisConditionType(conditionType);
	var priceTriggerPonit = param.PriceTriggerPonit;
	var compareType = param.CompareType
	var compareTypeText = analysisConditionCompareType(compareType);
	var timeTriggerPoint = param.TimeTriggerPoint;
	var abBuyPoint = param.AB_BuyPoint;
	var abSellPoint = param.AB_SellPoint;
	var orderType = param.OrderType;
	var drection = param.Drection;
	var stopLossType = param.StopLossType;
	var stopLossDiff = param.StopLossDiff;
	var stopWinDiff = param.StopWinDiff;
	var insertTime = param.InsertTime;
	var additionFlag = param.AdditionFlag;
	var additionType = param.AdditionType;
	var additionTypeText = "";
	var additionPrice = param.AdditionPrice;
	if(additionFlag == 1){ 
		additionTypeText = "&nbsp;&nbsp;"+analysisConditionCompareType(additionType)+additionPrice;
	}  
	if(conditionType == 0){
		compareTypeText = compareTypeText+priceTriggerPonit;
	}else if(conditionType == 1){
		timeTriggerPoint = timeTriggerPoint.replace(/-/g,"/");
		compareTypeText = formatDateHHMMSS(new Date(timeTriggerPoint));
	} 
	compareTypeText = compareTypeText+additionTypeText;
	var inserOrderText = analysisBusinessBuySell(drection)+","+lossOrderType(orderType)+","+num+"手";
	if(status == 2 || status == 3 || status == 4 || status == 5){
		insertTime = param.TriggedTime;
	}
	$("#"+conditionNo+" li[class = 'condition1']").text(statusText);
	$("#"+conditionNo+" li[class = 'condition1']").attr("data-tion-status",status);
	$("#"+conditionNo+" li[class = 'condition2']").text(conditionTypeText);
	$("#"+conditionNo+" li[class = 'condition2']").attr("data-tion-conditionType",conditionType);
	$("#"+conditionNo+" li[class = 'condition3']").html(compareTypeText);
	$("#"+conditionNo+" li[class = 'condition3']").attr("data-tion-compareType",compareType);
	$("#"+conditionNo+" li[class = 'condition4']").text(inserOrderText);
	$("#"+conditionNo+" li[class = 'condition6']").text(insertTime);
	if(status == 2 || status == 3 || status == 4 || status == 5){
		var html = $("#"+conditionNo).html();
		$("#no_condition_list").append("<ul class = 'testclick1 tab_content' id = '"+conditionNo+"'>"+html+"</ul>");
		$("#"+conditionNo).remove();
		selectCondition = {};
	}else {
		if(status == 0){
			$("#condition_stop").attr("data-operate-value",2);
			$("#condition_stop").text("暂停");
		}else if(status == 1){
			$("#condition_stop").attr("data-operate-value",3);  
			$("#condition_stop").text("启动");
		}
	} 
	localCacheCondition[conditionNo] = param;
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
				'	<li  style="width: 150px;">成交编号</li>'+
				'	<li  style="width: 150px;">订单号</li>'+
				'	<li  style="width: 150px;">成交时间</li>'+
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
 * 生成止损/止盈未触发表头
 */
function generateNoStopLossTitle(){
	var html = '<ul class="tab_lis">'+
			   '   <li class="ml" style="width: 100px;">合约</li>'+
			   '   <li style="width: 50px;">状态</li>'+
			   '   <li style="width: 50px;">多空</li>'+
			   '   <li style="width: 50px;">类型</li>'+
			   '   <li style="width: 50px;">手数</li>'+
			   '   <li style="width: 140px;">触发条件</li>'+
			   '   <li style="width: 50px;">委托价</li>'+
			   '   <li style="width: 100px;">有效期</li>'+
			   '    <li style="width: 140px;">下单时间</li>'+
			   ' </ul><p class="account_NoRecord" style="color: #ccc; text-align: center; padding: 10px 0;">暂无记录</p>';
	$("#no_stopLoss").append(html);
}
/**
 * 生成止损/止盈触发表头
 */
function generateYesStopLossTitle(){
	var html = ' <ul class="tab_lis">'+
			   '     <li class="ml" style="width: 100px;">合约</li>'+
			   '     <li style="width: 50px;">状态</li>'+
			   '      <li style="width: 50px;">多空</li>'+
			   '      <li style="width: 50px;">类型</li>'+
			   '      <li style="width: 50px;">手数</li>'+
			   '      <li style="width: 140px;">触发条件</li>'+
			   '      <li style="width: 50px;">委托价</li>'+
			   '      <li style="width: 100px;">有效期</li>'+
			   '      <li style="width: 140px;">触发时间</li>'+
			   '  </ul>'+
			   '  <p class="account_NoRecord" style="color: #ccc; text-align: center; padding: 10px 0;">暂无记录</p>';
	$("#yes_stopLoss").append(html);
}
/**
 * 生成条件单表头（未触发）
 */
function generateNoConditioinPriceTitle(){
	var html = '<ul class="tab_lis">'+
			   '        <li style="width: 80px" class="ml">合约</li>'+
			   '        <li style="width: 50px">状态</li>'+
			   '        <li style="width: 100px">类型</li>'+
			   '        <li style="width: 120px">下单</li>'+
			   '        <li style="width: 120px">条件</li>'+
			   '        <li style="width: 60px">有效日期</li>'+
			   '        <li style="width: 130px">下单时间</li>'+
			   '	</ul>'+
			   ' <p class="account_NoRecord" style="color: #ccc; text-align: center; padding: 10px 0;">暂无记录</p>';
		$("#yes_condition_list").append(html);
}
/**
 * 生成条件单表头（触发）
 */
function generateYesConditioinPriceTitle(){
	var html = '<ul class="tab_lis">'+
			   '        <li style="width: 80px" class="ml">合约</li>'+
			   '        <li style="width: 50px">状态</li>'+
			   '        <li style="width: 100px">类型</li>'+
			   '        <li style="width: 120px">下单</li>'+
			   '        <li style="width: 120px">条件</li>'+
			   '        <li style="width: 60px">有效日期</li>'+
			   '        <li style="width: 130px">触发时间</li>'+
			   '	</ul>'+
			   ' <p class="account_NoRecord" style="color: #ccc; text-align: center; padding: 10px 0;">暂无记录</p>';
		$("#no_condition_list").append(html);
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
 * 生成止损止盈操作节点
 * @returns
 */
function generateStopLossHandleDom(){
	var html = '<ul class="caozuo" id="loss_caozuo" style="left: 243px;">'+
			   '     <li><a href="javascript:void(0);" class = "updateAndDelStop" data-tion-value = "2" id = "loss_stop">暂停</a></li>'+
			   '     <li><a href="javascript:void(0);" data-tion-vale = "0" id = "loss_update">修改</a></li>'+
			   '     <li><a href="javascript:void(0);" class = "updateAndDelStop" data-tion-value = "1" id = "loss_delete">删除</a></li>'+
			   ' </ul>';
		$("#loss_title").append(html);
}
/**
 * 生成条件单操作节点
 * @returns
 */
function generateConditionHandleDom(){
	var html = '<ul class="caozuo" id="condition_caozuo" style="left: 243px;">'+
			   '     <li><a href="javascript:void(0);"  id = "condition_insert">新增</a></li>'+
			   '     <li><a href="javascript:void(0);" data-operate-value = "2" class= "updateCondition" id = "condition_stop">暂停</a></li>'+
			   '     <li><a href="javascript:void(0);"  id = "condition_update">修改</a></li>'+
			   '     <li><a href="javascript:void(0);" data-operate-value = "1" class = "updateCondition" id = "condition_delete">删除</a></li>'+
			   ' </ul>';
		$("#condition_title").append(html);
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
 * 绑定止损单列表点击事件
 * @param {Object} cls
 */
var num=0;
function addStopLossBindClick(cls){
	$("."+cls).bind("click",function(){
		var $this = $(this);
		selectStopLoss["stopLossNo"]=$this.attr("id");
		var status = $("#"+selectStopLoss["stopLossNo"]+" li[class = 'stoploss1']").attr("data-tion-status");
		if(status == 0){
			$("#loss_stop").attr("data-tion-value",2);
			$("#loss_stop").text("暂停");
		}else if(status == 1){
			$("#loss_stop").attr("data-tion-value",3);  
			$("#loss_stop").text("启动");
		}
	});
}
/**
 * 绑定条件单列表点击事件
 * @param {Object} cls
 */
function addConditionBindClick(cls){
	$("."+cls).bind("click",function(){
		var $this = $(this);
		selectCondition["conditionNo"] = $this.attr("id");
		var status = $("#"+selectCondition["conditionNo"]+" li[class = 'condition1']").attr("data-tion-status");
		if(status == 0){
			$("#condition_stop").attr("data-operate-value",2);
			$("#condition_stop").text("暂停");
		}else if(status == 1){
			$("#condition_stop").attr("data-operate-value",3);  
			$("#condition_stop").text("启动");
		}
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
 * 更新止损单索引
 * @returns
 */
function updateStoplossIndex(){
	stoplossIndex++;
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
	getVersion();
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
	$("#stoploss_top_title").hide();
	$("#condition_top_title").hide();
	$("#stop_loss_conditon_x").hide();
	$("#condition_no_radio").prop("checked","checked");
	$("#stoploss_no_radio").attr("checked","checked");
	$("#marketPrice").prop("checked","checked");
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
		if(tradeWebSocketIsMock == 0){
			tradeWebSocketIsMock=1
		}
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
	$("#stop_checked").change(function(){
		var $this = $(this);
		var checked = $this.is(':checked');
		if(checked){
			$("#stop_inputprice").attr("disabled",true);
			$("#stop_diff").attr("disabled",false);
			$("#yj_tip_stop").hide();
		}else{
			$("#yj_tip_stop").show();
			$("#stop_inputprice").attr("disabled",false);
			$("#stop_diff").attr("disabled",true);
		}
	});
	$(".stop_loiss_title").bind("click",function(){
		var $this = $(this);
		var value = $this.attr("data-tion-value");
		$(".stop_loiss_title").removeClass("on").eq(value).addClass("on");
		$("#loss_popup_center .popup_center_lis").hide().eq(value).show();
	});
	$("#chioceTimeAdditional").change(function(){
		var $this = $(this);
		var val = $this.val();
		if(val == -1){
			$("#ConditoionTimePricesInput").attr("disabled",true);
		}else{
			$("#ConditoionTimePricesInput").attr("disabled",false);
		}
	})
	$("#select_price_addcondition_option").change(function(){
		var $this = $(this);
		var val = $this.val();
		if(val == -1){
			$("#ConditoionPricesInput1").attr("disabled",true);
		}else{
			$("#ConditoionPricesInput1").attr("disabled",false);
		}
	})
	$("#select_time_condition_option").change(function(){
		var $this = $(this);
		var val = $this.val();
		if(val == -1){
			$("#condtion_time_addPrice").hide()
			$("#condtion_time_addPrice").attr("disabled",true);
		}else{
			$("#condtion_time_addPrice").show();
			$("#condtion_time_addPrice").attr("disabled",false);
		}
	})
	$("#select_condition_price_addoption").change(function(){
		var $this = $(this);
		var val = $this.val();
		if(val == -1){
			$("#condtion_price_addPrice").hide();
			$("#condtion_price_addPrice").attr("disabled",true);
		}else{
			$("#condtion_price_addPrice").show();
			$("#condtion_price_addPrice").attr("disabled",false);
		}
	})
	$("#contion_time_contractCode").change(function(){
		var val = $(this).val();
		var localQuote = localCacheQuote[val];
		if(localQuote != undefined){
			var lastPrice = localQuote.LastPrice;
			$("#condtion_time_addPrice").val(lastPrice);
			$("#condition_time_lastPrice").text(lastPrice);
		}
	});
	$("#condition_price_contractCode").change(function(){
		var val = $(this).val();
		var localQuote = localCacheQuote[val];
		if(localQuote != undefined){
			var lastPrice = localQuote.LastPrice;
			$("#condition_price_inputprice").val(lastPrice);
			$("#condition_price_lastPrice").text(lastPrice);
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
	clearHandicapData();
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
			var commodityNo = $("#commodeityNo").val();
			var contractNo = $("#contractNo").val();
			var localCommodity = getLocalCacheCommodity(commodityNo+contractNo);
			var dotSize = 2;
			if(localCommodity != undefined){
				dotSize = localCommodity.DotSize;
			}
			var orderPrice = 0.00;
			if(priceType == 1){
				orderPrice = doGetMarketPrice(lastPrice, miniTikeSize, tradeDrection,dotSize);
			}else{
				orderPrice = $("#money_number").val();
			}
			var tipContent = "确认提交订单:合约【"+commodityNo+contractNo+"】,价格:【"+orderPrice+"】,手数:【"+orderNum+"】,买卖方向:【"+analysisBusinessBuySell(tradeDrection)+"】";
			layer.confirm(tipContent+"?", {
			  btn: ['确认','取消'] //按钮
			}, function(index){
				layer.close(index); // 下单后首先关闭下单按钮
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
							 layer.tips("手数输入错误", '#update_des_number');
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
	//显示止损止盈窗口
	$("#stopLossAndtraders").bind("click",function(){
		if(isLogin){
			var contractCode = selectPostion["contractCode"];
			if(contractCode == undefined){
				tip("请选择一行数据");
				return;
			}
			var $contractCode = $("ul[data-tion-position='"+contractCode+"'] li[class = 'position0']");
			var $drection = $("ul[data-tion-position='"+contractCode+"'] li[class = 'position2']");
			var $holdNum = $("ul[data-tion-position='"+contractCode+"'] li[class = 'position1']");
			var $holdAvgPrice = $("ul[data-tion-position='"+contractCode+"'] li[class = 'position3']");
			var localQuote = localCacheQuote[contractCode];
			if(localQuote == undefined){
				tip("无效的品种合约");
				return;
			}
			var localCommodity = getLocalCacheCommodity(contractCode);
			if(localCommodity == undefined){
				tip("无效的品种合约");
				return;
			}
			var dotSize = localCommodity.DotSize;
			var currencyNo  = localCommodity.CurrencyNo;
			var lastPrice = localQuote.LastPrice;
			$("#stop_contractCode").text(contractCode);
			$("#stopHoldContractCode").val(contractCode);
			$("#stop_drection").html(analysisBusinessDirection($drection.attr("data-drection")));
			$("#stop_lastPrice").text(lastPrice);
			$("#stop_inputprice").val(lastPrice);
			$("#stop_inputnum").val($holdNum.text());
			$(".stop_dw").text(currencyNo);
			$(".loss_dw").text(currencyNo);
			$("#loss_contractCode").text(contractCode);
			$("#loss_drection").html(analysisBusinessDirection($drection.attr("data-drection")));
			$("#loss_lastPrice").text(lastPrice);
			$("#loss_inputprice").val(lastPrice);
			$("#loss_inputnum").val($holdNum.text());
			$("#stopHoldAvgPrice").val($holdAvgPrice.text());
			$("#stopHoldDrection").val($drection.attr("data-drection"));
			$("#stop_confirm").attr("data-tion-operate",1);
			$("#loss_confirm").attr("data-tion-operate",1);
			var stopChecked = $("#stop_checked").is(':checked');
			if(!stopChecked){
				$("#stop_diff").attr("disabled",true);
				$("#yj_tip_stop").show();
			}
			var contractSize = localCommodity.ContractSize;
			var miniTikeSize = localCommodity.MiniTikeSize;
			var holdAvgPrice = $holdAvgPrice.text();
			var stopInputPrice = replaceNum(lastPrice+"",dotSize);
			var chaPrice = Math.abs(stopInputPrice - holdAvgPrice);
			var num = $holdNum.text();
			var scale = 0.00;
			var stopDrection = $("#stopHoldDrection").val();
			if(num.length != 0){
				var  price =  doGetFloatingProfit(holdAvgPrice,stopInputPrice,contractSize,miniTikeSize,num,stopDrection);
				$("#stop_yjks").text(price);
				$("#loss_yjks").text(-price); 
			}
			scale = (stopInputPrice - holdAvgPrice) / holdAvgPrice * 100;
			$("#stop_pricecha").text(parseFloat(chaPrice).toFixed(dotSize));
			$("#stop_scale").text(parseFloat(Math.abs(scale)).toFixed(2));
			$("#loss_chaPrice").text(parseFloat(chaPrice).toFixed(dotSize));
			$("#loss_scale").text(parseFloat(Math.abs(scale)).toFixed(2));
			$("#stopTitle").text("设置止损单");
			$("#lossTitle").text("设置止盈单");
			openUpdateStop();
		}else{
			tip("未登录,请先登录");
		}
	});
	/**
	 * 修改显示止损单窗口
	 */
	$("#loss_update").bind("click",function(){
		var stopLossNo = selectStopLoss["stopLossNo"];
		if(stopLossNo == undefined){
			tip("请选择一行数据");
			return;
		}
		var param = localCahceStopLossNo[stopLossNo];
		if(param == undefined){
			tip("错误操作,请稍后重试");
			return;
		}
		if(param.Status == 0){
			tip("运行中的状态不能修改");
			return;
		}
		var commodityNo = param.CommodityNo;
		var contractNo = param.ContractNo;
		var contractCode = commodityNo + contractNo;
		var localCommodity = localCacheCommodity[contractCode];
		if(localCommodity == undefined){
			tip("无效的合约");
			return;
		}
		var dotSize = localCommodity.DotSize;
		var localQuote = localCacheQuote[contractCode];
		var lastPrice = 0;
		if(localQuote != undefined){
			lastPrice = localQuote.LastPrice;
		}
		var num = param.Num;
		var stopLossType = param.StopLossType;
		var stopLossPrice = param.StopLossPrice;
		var stopLossDiff = param.StopLossDiff;
		var orderType = param.OrderType;
		var holdAvgPrice = param.HoldAvgPrice;
		var holdDrection = param.HoldDrection;
		var scale = 0;
		var contractSize = localCommodity.ContractSize;
		var miniTikeSize = localCommodity.MiniTikeSize;
		var stopInputPrice = replaceNum(stopLossPrice+"",dotSize);
		var chaPrice = Math.abs(stopInputPrice - holdAvgPrice);
		if(num.length != 0){
			var  price =  doGetFloatingProfit(holdAvgPrice,stopInputPrice,contractSize,miniTikeSize,num,holdDrection);
			$("#stop_yjks").text(price);
			$("#loss_yjks").text(-price); 
		}
		scale = (stopInputPrice - holdAvgPrice) / holdAvgPrice * 100;
		if(stopLossType == 0 || stopLossType == 1){
			scale = (stopLossPrice - holdAvgPrice) / holdAvgPrice * 100;
			if(stopLossType == 0){
				$("#stop_inputprice").val(stopLossPrice);
				$("#stop_contractCode").text(contractCode);
				$("#stop_drection").html(analysisBusinessDirection(holdDrection));
				$(".stop_dw").text(localCommodity.CurrencyNo);
				$("#stop_lastPrice").text(lastPrice);
				$("#stop_orderType").val(orderType);
				$("#stop_diff").val(0);
				$("#stop_pricecha").text(parseFloat(chaPrice).toFixed(dotSize));
				$("#stop_scale").text(parseFloat(Math.abs(scale)).toFixed(2));
			}else{
				$("#loss_inputprice").val(stopLossPrice);
				$("#loss_contractCode").text(contractCode);
				$("#loss_drection").html(analysisBusinessDirection(holdDrection));
				$("#loss_dw").text(localCommodity.CurrencyNo);
				$("#loss_lastPrice").text(lastPrice);
				$("#loss_orderType").val(orderType);
				$("#loss_scale").text(parseFloat(Math.abs(scale)).toFixed(2));
				$("#loss_chaPrice").text(parseFloat(chaPrice).toFixed(dotSize));
				$("#loss_scale").text(parseFloat(Math.abs(scale)).toFixed(2));
			}
			$("#stop_checked").attr("checked",false);
			$("#stop_diff").attr("disabled",true);
			$("#stop_inputprice").attr("disabled",false);
		}else if(stopLossType == 2){
			scale = stopLossDiff / holdAvgPrice * 100;
			$("#stop_diff").val(stopLossDiff);
			$("#stop_checked").attr("checked",true);
			$("#stop_diff").attr("disabled",false);
			$("#yj_tip_stop").hide();
			$("#stop_inputprice").attr("disabled",true);
			$("#stop_inputprice").val(0);
			$("#stop_drection").html(analysisBusinessDirection(holdDrection));
		}
		$("#loss_inputnum").val(num);
		$("#stop_inputnum").val(num);
		/*$("#stop_scale").text(parseFloat(Math.abs(scale)).toFixed(2));*/
		$("#stop_confirm").attr("data-tion-operate",2);
		$("#loss_confirm").attr("data-tion-operate",2);
		$("#stopHoldAvgPrice").val(holdAvgPrice);
		$("#stopHoldDrection").val(holdDrection);
		$("#stopHoldContractCode").val(contractCode);
		$("#stopTitle").text("修改止损单");
		$("#lossTitle").text("修改止盈单");
		operationStopLossType = 0;
		openUpdateStop(stopLossType);
		
	});
	$("#stop_inputprice").bind("input",function(){
		var stopContractCode = $("#stop_contractCode").text();
		var localCommodity = getLocalCacheCommodity(stopContractCode);
		var lastPrice = $("#stop_lastPrice").text();
		var stopInputPrice = $("#stop_inputprice").val();
		if(stopInputPrice <= 0 || stopInputPrice.length == 0){
			layer.tips("价格输入错误", '#stop_inputprice');
			return;
		}
		var dotSize = 2;
		if(localCommodity != undefined){
			dotSize = localCommodity.DotSize;
		}
		var contractSize = localCommodity.ContractSize;
		var miniTikeSize = localCommodity.MiniTikeSize;
		var holdAvgPrice = $("#stopHoldAvgPrice").val();
		stopInputPrice = replaceNum(stopInputPrice,dotSize);
		var chaPrice = Math.abs(stopInputPrice - holdAvgPrice);
		var num = $("#stop_inputnum").val();
		var scale = 0.00;
		var stopDrection = $("#stopHoldDrection").val();
		if(num.length != 0){
			var  price =  doGetFloatingProfit(holdAvgPrice,stopInputPrice,contractSize,miniTikeSize,num,stopDrection);
			$("#stop_yjks").text(price);
		}
		scale = (stopInputPrice - holdAvgPrice) / holdAvgPrice * 100;
		$("#stop_inputprice").val(stopInputPrice);
		$("#stop_pricecha").text(parseFloat(chaPrice).toFixed(dotSize));
		$("#stop_scale").text(parseFloat(Math.abs(scale)).toFixed(2));
	});
	$("#loss_inputprice").bind("input",function(){
		var stopContractCode = $("#loss_contractCode").text();
		var localCommodity = getLocalCacheCommodity(stopContractCode);
		var lastPrice = $("#loss_lastPrice").text();
		var stopInputPrice = $("#loss_inputprice").val();
		if(stopInputPrice <= 0 || stopInputPrice.length == 0){
			layer.tips("价格输入错误", '#loss_inputprice');
			return;
		}
		var dotSize = 2;
		if(localCommodity != undefined){
			dotSize = localCommodity.DotSize;
		}
		var contractSize = localCommodity.ContractSize;
		var miniTikeSize = localCommodity.MiniTikeSize;
		stopInputPrice = replaceNum(stopInputPrice,dotSize);
		var holdAvgPrice = $("#stopHoldAvgPrice").val();
		var chaPrice = Math.abs(stopInputPrice - holdAvgPrice);
		var scale = 0.00;
		var num = $("#loss_inputnum").val();
		var stopDrection = $("#stopHoldDrection").val();
		if(num.length != 0){
			var  price =  doGetFloatingProfit(holdAvgPrice,stopInputPrice,contractSize,miniTikeSize,num,stopDrection);
			$("#loss_yjks").text(-price);
		}
		scale = (stopInputPrice - holdAvgPrice) / holdAvgPrice * 100;
		$("#loss_chaPrice").text(parseFloat(chaPrice).toFixed(dotSize));
		$("#loss_inputprice").val(stopInputPrice);
		$("#loss_scale").text(parseFloat(Math.abs(scale)).toFixed(2));
	});
	//添加止损
	$("#stop_confirm").bind("click",function(){
		if(isLogin){
			var operate = $(this).attr("data-tion-operate");
			var contractCode = "";
			if(operate == 1){
				contractCode = selectPostion["contractCode"];
				if(contractCode == undefined){
					tip("请选择一条信息");
					return;
				}
			}else if(operate == 2){
				var stopLossNo = selectStopLoss["stopLossNo"];
				if(stopLossNo == undefined){
					tip("请选择一行数据");
					return;
				} 
				var param = localCahceStopLossNo[stopLossNo];
				contractCode = param.CommodityNo + param.ContractNo;
			}
			var localCommodity = getLocalCacheCommodity(contractCode);
			if(localCommodity == undefined){
				tip("无效的合约");
				return;
			}
			var stopInputnum = $("#stop_inputnum").val();
			if(stopInputnum <= 0 || stopInputnum.length == 0){
				layer.tips("手数输入错误", '#stop_inputnum');
				return;
			}
			var positiveInteger = /^\+?[1-9][0-9]*$/;
			if (positiveInteger.test(stopInputnum)==false){
				layer.tips("手数输入错误", '#stop_inputnum');
				return;
			}
			var stopChecked = $("#stop_checked").is(':checked');
			var stopLossType = 0;
			var stopDrection = $("#stopHoldDrection").val();
			var lastPrice = $("#stop_lastPrice").text();
			var stopDiff = $("#stop_diff").val();
			var stopTypeText = "限价止损";
			var stopLossDiff = 0;
			if(stopChecked){
				stopLossDiff = stopDiff;
				stopTypeText = "动态止损";
				stopLossType = 2;
			}else{
				var stopInputPrice = $("#stop_inputprice").val();
				if(stopInputPrice <= 0 || stopInputPrice.length == 0){
					layer.tips("价格输入错误", '#stop_inputprice');
					return;
				}
			}
			if(stopLossType == 0){
				if(stopDrection == 0){
					if(lastPrice <= stopInputPrice){
						layer.tips("输入价格应小于最新价", '#stop_inputprice');
						return;
					}
				}else if(stopDrection == 1){
					if(lastPrice >= stopInputPrice){
						layer.tips("输入价格应小于最新价", '#stop_inputprice');
						return;
					}
				}
				stopLossDiff = lastPrice - stopInputPrice;
			}
			if(stopLossDiff == 0){
				var positiveInteger = /^\+?[1-9][0-9]*$/;
				if (positiveInteger.test(stopLossDiff)==false){
					layer.tips("止损价差输入错误", '#stop_diff');
					return;
				}
				layer.tips("止损价差会导致立即触发,请重新设置", '#stop_diff');
				return;
			}
			var tipContent = "";
			if(operate == 1){
				tipContent =  "是否添加"+stopTypeText;
				tipConfirm(tipContent,doGetInsertStopLoss,cancleCallBack);
			}else if(operate == 2){
				tipContent =  "是否修改"+stopTypeText;
				tipConfirm(tipContent,doUpdateModifyStopLoss,cancleCallBack);
			}
			
		}else{
			tip("未登录,请先登录");
		}
	});
	$(".updateAndDelStop").bind("click",function(){
		var stopLossNo = selectStopLoss["stopLossNo"];
		if(stopLossNo == undefined){
			tip("请选择一行数据");
			return;
		} 
		var $this =	$(this);
		var val = $this.attr("data-tion-value");
		var textAll=$("#"+stopLossNo+" li[class = 'stoploss3']").text();
		var textTip="止损"
		operationStopLossType = val;
		var operationText = "";
		if(operationStopLossType == undefined){
			tip("请重新操作");
			return; 
		}else if(operationStopLossType == 0){
			operationText = "修改";  
		}else if(operationStopLossType == 1){
			operationText = "删除"; 
		}else if(operationStopLossType == 2){
			operationText = "暂停";
		}else if(operationStopLossType == 3){
			operationText = "启动";
		}
		for(var i=0;i<textList.length;i++){
			if(textAll.indexOf(textList[i])>=0){
				textTip=textList[i];
			}
		}
		var tipContent = "是否"+operationText+textTip+"单";
		tipConfirm(tipContent,doStopAndDelModifyStopLoss,cancleCallBack);
	});
	//添加止盈
	$("#loss_confirm").bind("click",function(){
		if(isLogin){
			var operate = $(this).attr("data-tion-operate");
			var contractCode = "";
			if(operate == 1){
				contractCode = selectPostion["contractCode"];
				if(contractCode == undefined){
					tip("请选择一条信息");
					return;
				}
			}else if(operate == 2){
				var stopLossNo = selectStopLoss["stopLossNo"];
				if(stopLossNo == undefined){
					tip("请选择一行数据");
					return;
				} 
				var param = localCahceStopLossNo[stopLossNo];
				contractCode = param.CommodityNo + param.ContractNo;
			}
			var localCommodity = getLocalCacheCommodity(contractCode);
			if(localCommodity == undefined){
				tip("无效的合约");
				return;
			}
			var stopLossType = 0;
			var stopDrection = $("#stopHoldDrection").val();
			var lastPrice = $("#loss_lastPrice").text();
			var stopTypeText = "限价止盈";
			var stopLossDiff = 0;
			var lossInputPrice = $("#loss_inputprice").val();
			if(lossInputPrice <= 0 || lossInputPrice.length == 0){
				layer.tips("价格输入错误", '#loss_inputprice');
				return;
			}
			var lossInputnum = $("#loss_inputnum").val();
			if(lossInputnum <= 0 || lossInputnum.length == 0){
				layer.tips("手数输入错误", '#loss_inputnum');
				return;
			}
			var positiveInteger = /^\+?[1-9][0-9]*$/;
			if (positiveInteger.test(lossInputnum)==false){
				layer.tips("手数输入错误", '#loss_inputnum');
				return;
			}
			if(stopLossType == 0){
				if(stopDrection == 0){
					if(lastPrice >= lossInputPrice){
						layer.tips("输入价格应大于最新价", '#loss_inputprice');
						return;
					}
				}else if(stopDrection == 1){
					if(lastPrice <= lossInputPrice){
						layer.tips("输入价格应小于最新价", '#loss_inputprice');
						return;
					}
				}
				stopLossDiff = lastPrice - lossInputPrice;
			}
			if(stopLossDiff == 0){
				layer.tips("止损价差会导致立即触发,请重新设置", '#loss_inputprice');
				return;
			}
			var tipContent = "";
			if(operate == 1){
				tipContent =  "是否添加"+stopTypeText;
				tipConfirm(tipContent,doGetInsertLossLoss,cancleCallBack);
			}else if(operate == 2){
				tipContent =  "是否修改"+stopTypeText;
				tipConfirm(tipContent,doUpdateModifyLoss,cancleCallBack);
			}
			
		}else{
			tip("未登录,请先登录");
		}
	});
	$("#condition_insert").bind("click",function(){
		$("#sub_condition_price").text("确定");
		$("#sub_condition_time").text("确定");
		$("#condition_time_time").val(formatDateHHMMSS(new Date()));
	});
	$("#condition_price_inputprice").bind("input",function(){
		var $this = $(this);
		var val = replaceNum($this.val(), 4);
		$this.val(val);
	});
	$("#condtion_time_addPrice").bind("input",function(){
		var $this = $(this);
		var val = replaceNum($this.val(), 4);
		$this.val(val);
	});
	$("#stop_diff").bind("input",function(){
		var $this = $(this);
		var val = replaceNum($this.val(), 4);
		$this.val(val);
	});
	/**
	 * 添加条件单(价格条件)
	 */
	$("#sub_condition_price").bind("click",function(){
		if(isLogin){
			//附加值
			var addoption = $("#select_condition_price_addoption").val();
			var addinputprice = 0;
			//选择的符号
			var option = $("#select_condition_option").val();
			//输入价格
			var inputprice = $("#condition_price_inputprice").val();
			if(inputprice == null || inputprice.length == 0){
				layer.tips("触发价格错误", '#condition_price_inputprice');
				return;
			}
			if(addoption != -1){
				addinputprice = $("#condtion_price_addPrice").val();
				if(addinputprice <= 0 || addinputprice.length == 0){
					layer.tips("附加触发价格错误", '#condtion_price_addPrice');
					return;
				}
				var flag = validationPriceCondition(option,inputprice,addoption,addinputprice);
				if(!flag){
					return;
				}
			}
			//手数
			var inputnum = $("#contract_price_num").val();
			if(inputnum <= 0 || inputnum.length == 0){
				layer.tips("手数输入错误", '#contract_price_num');
				return;
			}
			//方向
			var drection = $("#condition_price_drection").val();
			//最新价
			var lastPrice = $("#condition_price_lastPrice").text();
			/*if(drection == 0){
				if(inputprice >= lastPrice){
					layer.tips("输入价格必须小于最新价", '#condition_price_inputprice');
					return;
				}
			}else if(drection == 1) {
				if(inputprice <= lastPrice){
					layer.tips("输入价格必须大于最新价", '#condition_price_inputprice');
					return;
				}
			}*/
			if(option=='0'){
				if(inputprice  <= lastPrice){
					layer.tips("输入价格必须大于最新价", '#condition_price_inputprice');
					return;
				}
			}else if(option=='1'){
				if(inputprice  >= lastPrice){
					layer.tips("输入价格必须小于最新价", '#condition_price_inputprice');
					return;
				}
			}else if(option=='2'){
				if(inputprice  < lastPrice){
					layer.tips("输入价格必须大于等于最新价", '#condition_price_inputprice');
					return;
				}
			}else if(option=='3'){
				if(inputprice  > lastPrice){
					layer.tips("输入价格必须小于等于最新价", '#condition_price_inputprice');
					return;
				}
			}
			var chioceContract = $("#condition_price_contractCode").val();
			if(insertConditionCount == 0){
				var tipContent = "你确定要提交【"+chioceContract+"】条件单吗?";
				tipConfirm(tipContent,doInsertConditionByPrice,cancleCallBack);
			}else if(insertConditionCount == 1){
				var tipContent = "你确定要修改【"+chioceContract+"】条件单吗?";
				tipConfirm(tipContent,doUpdateConditionByPrice,cancleCallBack);
			}
		}else{
			tip("未登录,请先登录");
		}
	});
	/**
	 * 添加条件单(时间条件)
	 */
	$("#sub_condition_time").bind("click",function(){
		if(isLogin){
			var chioceTimeAdditional = $("#select_time_condition_option").val();
			if(chioceTimeAdditional != -1){
				var conditoionTimePricesInput = $("#condtion_time_addPrice").val();
				if(conditoionTimePricesInput <= 0 || conditoionTimePricesInput.length == 0){
					tip("附加触发价格错误");
					return;
				}
				var drection = $("#condition_drection_time").val();
				var lastPrice = $("#condition_time_lastPrice").text();
				if(drection == 0){
					if(inputprice <= lastPrice){
						layer.tips("附加价格必须大于最新价", '#condtion_time_addPrice');
						return;
					}
				}else if(drection == 1) {
					if(input >= lastPrice){
						layer.tips("附加价格必须小于最新价", '#condtion_time_addPrice');
						return;
					}
				}
			} 
			var ConditoionTimeInput = $("#condition_time_inputnum").val();
			var inserTimeInput = $("#condition_time_time").val();
			if(inserTimeInput.length == 0 ){
				tip("请选择时间");
				return ;
			}
			var flag = validationtimeCondition(inserTimeInput);
			if(!flag){
				tip("设置时间必须大于当前时间");
				return;
			}
			if(ConditoionTimeInput <= 0 || ConditoionTimeInput.length == 0){
				tip("手数输入错误");
				return;
			}
			var chioceContract = $("#contion_time_contractCode").val();
			if(insertConditionCount == 0){
				var tipContent = "你确定要提交【"+chioceContract+"】条件单吗?";
				tipConfirm(tipContent,doInsertConditionByTime,cancleCallBack);
			}else if(insertConditionCount == 1){
				var tipContent = "你确定要修改【"+chioceContract+"】条件单吗?";
				tipConfirm(tipContent,doUpdateConditionByTime,cancleCallBack);
			}
		}else{
			tip("未登录,请先登录");
		}
	});
	/**
	 * 修改条件单显示窗口
	 */
	$("#condition_update").bind("click",function(){
		if(isLogin){
			var conditionNo = selectCondition["conditionNo"];
			if(conditionNo == undefined){
				tip("请选择一条数据");
				return;
			}
			var param = localCacheCondition[conditionNo];
			if(param == undefined){
				tip("无效数据");
				return;
			}
			var conditionType = param.ConditionType;
			var commodityNo = param.CommodityNo;
			var contractNo = param.ContractNo;
			var contractCode = commodityNo+contractNo;
			var localQuote = localCacheQuote[contractCode];
			if(localQuote != undefined){
				var lastPrice = localQuote.LastPrice;
				$("#condition_time_lastPrice").text(lastPrice);
				$("#condition_price_lastPrice").text(lastPrice);
			}
			var num = param.Num;
			var priceTriggerPonit = param.PriceTriggerPonit;
			var compareType = param.CompareType;
			var timeTriggerPoint = param.TimeTriggerPoint;
			var abBuyPoint = param.AB_BuyPoint;
			var abSellPoint = param.AB_SellPoint;
			var orderType = param.OrderType;
			var drection = param.Drection;
			var additionType = param.AdditionType;
			var additionPrice = param.AdditionPrice;
			var df = new Date(timeTriggerPoint);
			var dfTime = df.getTime();
			var time = formatDateHHMMSS(new Date(dfTime));
			$("#condition_price_contractCode").val(contractCode);
			$("#select_condition_option").val(compareType);
			$("#condition_price_inputprice").val(priceTriggerPonit);
			$("#condition_price_drection").val(drection);
			$("#condition_price_orderType").val(orderType);
			$("#contract_price_num").val(num);
			if(additionPrice == undefined || additionPrice == 0 || additionPrice.length == 0){
				$("#select_time_condition_option").val(-1);
				$("#select_condition_price_addoption").val(-1);
			    $("#condtion_price_addPrice").val("");
				$("#condtion_time_addPrice").val("");
				$("#condtion_price_addPrice").hide();
				$("#condtion_time_addPrice").hide();
			}else{
				$("#select_time_condition_option").val(additionType);
				$("#select_condition_price_addoption").val(additionType);
				$("#condtion_price_addPrice").val(additionPrice);
				$("#condtion_time_addPrice").val(additionPrice);
				$("#condtion_time_addPrice").attr("disabled",false);
				$("#condtion_price_addPrice").attr("disabled",false);
				$("#condtion_time_addPrice").show();
				$("#condtion_price_addPrice").show();
			}
			$("#condition_orderType_time").val(orderType);
			$("#contion_time_contractCode").val(contractCode); 
			$("#condition_time_time").val(time);
			$("#condition_drection_time").val(drection);
			$("#condition_time_inputnum").val(num);
			insertConditionCount = 1; 
			$("#condition_price_contractCode").attr("disabled",true);
			$("#contion_time_contractCode").attr("disabled",true);
			if(conditionType == 0){  //价格
				$("#sub_condition_price").text("修改");
			}else if(conditionType == 1){//时间
				$("#sub_condition_price").text("修改");
			}
			openCondition(conditionType);
		}else{
			tip("未登录,请先登录")
		}
	});
	/**
	 * 暂停-2，删除-1,启动-3
	 */
	$(".updateCondition").bind("click",function(){
		if(isLogin){
			var conditionNo = selectCondition["conditionNo"];
			if(conditionNo == undefined){
				tip("请选择一行数据");
				return;
			}
			var $this = $(this);
			var modifyFlag = $this.attr("data-operate-value");
			operateConditionType = modifyFlag;
			if(operateConditionType == undefined){
				tip("请重新操作");
				return;
			}
			var modifyFlagText = "";
			if(operateConditionType == 1){
				modifyFlagText = "删除";
			}else if(operateConditionType == 2){
				modifyFlagText = "暂停";
			}else if(operateConditionType == 3){
				modifyFlagText = "启动";
			}
			var tipContent = "是否要"+modifyFlagText+"条件单";
			tipConfirm(tipContent,doUpdateAndDelCondition,cancleCallBack);
		}else{
			tip("未登录,请先登录");
		}
		
	});
}
/**
 * 初始化增加条件单弹出框
 */
function initConditionData(){
	if(isLogin){
		var contractCode = $("#condition_price_contractCode").val();
		var localQuote = localCacheQuote[contractCode];
		if(localQuote != undefined){
			var lastPrice = localQuote.LastPrice;
			$("#condition_price_inputprice").val(lastPrice);
			$("#condtion_time_addPrice").val(lastPrice);
			$("#condition_time_lastPrice").text(lastPrice);
			$("#condition_price_lastPrice").text(lastPrice);
		}
		$("#select_condition_option").val(0);
		$("#condition_price_drection").val(0);
		$("#condition_price_orderType").val(1); 
		$("#contract_price_num").val(1);
		$("#select_condition_price_addoption").val(-1);
		$("#condtion_price_addPrice").val("");
		$("#condition_orderType_time").val(1);
		$("#condition_time_time").val("");
		$("#select_time_condition_option").val(-1);
		$("#condition_drection_time").val(0);
		$("#condition_time_inputnum").val(1); 
		insertConditionCount = 0; 
		$("#condtion_price_addPrice").attr("disabled",true);
		$("#condtion_time_addPrice").attr("disabled",true);
		$("#condtion_price_addPrice").hide();
		$("#condtion_time_addPrice").hide();
	    $("#condition_price_contractCode").attr("disabled",false);
	    $("#contion_time_contractCode").attr("disabled",false);
	    openCondition();
	}else{
		tip("未登录,请先登录");
	}
}
/**
 * 打开条件单窗口
 * @param state
 */
function openCondition(state){
	if(state == 0 || state == 2){
		$("#condition_title_price").show();
		$("#condtion_title_time").hide();
		$("#condition_content_price").show();
		$("#condition_content_time").hide();
		$("#condition_title_price").addClass("on");
		$("#condtion_title_time").removeClass("on");
	}else if(state == 1){
		$("#condition_title_price").hide();
		$("#condtion_title_time").show();
		$("#condition_content_price").hide();
		$("#condition_content_time").show();
		$("#condition_title_price").removeClass("on");
		$("#condtion_title_time").addClass("on");
	}else{
		var flag = $("#condition_title_price").is(".on");
		if(flag){
			$("#condition_content_price").show();
			$("#condition_content_time").hide();
		}else{
			$("#condition_content_time").show();
			$("#condition_content_price").hide();
		}
		$("#condition_title_price").show();
		$("#condtion_title_time").show();
	}
	$("#div_Mask").show();
    $("#condition_money_time").css("display","block");
    var popupHeight = $("#condition_money_time").outerHeight()/2;
    var popupWidth = $("#condition_money_time").outerWidth()/2;
    $("#condition_money_time").css({
        top:"50%",
        left:"50%",
        marginTop: -(popupHeight+15),
        marginLeft: -(popupWidth)
    });
}
/**
 * 打开修改和添加的止损单窗口
 */
function openUpdateStop(stopLossType){
	if(stopLossType == 0 || stopLossType == 2){
		$("#stopTitle").show();
		$("#lossTitle").hide();
		$("#stopContent").show();
		$("#lossContent").hide();
		$("#stopTitle").addClass("on");
		$("#lossTitle").removeClass("on");
	}else if(stopLossType == 1){
		$("#stopTitle").hide();
		$("#lossTitle").show();
		$("#stopContent").hide();
		$("#lossContent").show();
		$("#stopTitle").removeClass("on");
		$("#lossTitle").addClass("on");
	}else{
		var flag = $("#stopTitle").is(".on");
		if(flag){
			$("#stopContent").show();
			$("#lossContent").hide();
		}else{
			$("#lossContent").show();
			$("#stopContent").hide();
		}
		$("#stopTitle").show();
		$("#lossTitle").show();
	}
	$("#stop_loss").css("display","block");
    $("#div_Mask").show();
    var popupHeight = $("#stop_loss").outerHeight()/2;
    var popupWidth = $("#stop_loss").outerWidth()/2;
    $("#stop_loss").css({
        top:"50%",
        left:"50%",
        marginTop: -(popupHeight+15),
        marginLeft: -(popupWidth)
    });
}
/**
 * 添加止损操作
 */
function doGetInsertStopLoss(){
	if(isLogin){
			var contractCode = selectPostion["contractCode"];
			var $drection = $("ul[data-tion-position='"+contractCode+"'] li[class = 'position2']");
			var $holdAvgPrice = $("ul[data-tion-position='"+contractCode+"'] li[class = 'position3']");
			var localQuote = localCacheQuote[contractCode];
			if(localQuote == undefined){
				tip("无效的合约");
				return;
			}
			var lastPrice = $("#stop_lastPrice").text();
			var stopinputprice = $("#stop_inputprice").val();
			var stopNumber = $("#stop_inputnum").val();
			var stopDiff = $("#stop_diff").val();
			var stopLossType = 0;
			var stopChecked = $("#stop_checked").is(':checked');
			if(stopChecked){
				stopLossType = 2;
			}
			var stopOrderType = $("#stop_orderType").val();
			var stopLossDiff = 0;
			var drection = $drection.attr("data-drection");
			if(stopLossType == 0){
				stopLossDiff = lastPrice - stopinputprice;
			}else if(stopLossType == 2){
				stopinputprice = 0;
				stopLossDiff = stopDiff;
				if(drection == 0){
					stopinputprice = Number(lastPrice) - Number(stopDiff);
				}else if(drection == 1){
					stopinputprice =  Number(stopDiff) + Number(lastPrice);
				}
			}
			var exchangeNo = localQuote.ExchangeNo;
			var commodityNo = localQuote.CommodityNo;
			var contractNo = localQuote.ContractNo; 
			var tradeparam = createInsertStopLossParam(exchangeNo,commodityNo,contractNo,stopNumber,stopLossType,parseFloat(Math.abs(stopLossDiff)).toFixed(2),$holdAvgPrice.text(),drection,stopOrderType,parseFloat(stopinputprice).toFixed(2));
			if(tradeparam == undefined){
				tip("交易错误,请重试"); 
			}
			inserStopLoss(tradeparam);
		}else{
			tip("未登录,请先登录");
		}
}
/**
 * 添加止盈操作
 */
function doGetInsertLossLoss(){
	if(isLogin){
			var contractCode = selectPostion["contractCode"];
			var $drection = $("ul[data-tion-position='"+contractCode+"'] li[class = 'position2']");
			var $holdAvgPrice = $("ul[data-tion-position='"+contractCode+"'] li[class = 'position3']");
			var localQuote = localCacheQuote[contractCode];
			var lastPrice = $("#loss_lastPrice").text();
			var lossChoicePrices2 = $("#loss_inputprice").val();
			var stopNumber = $("#loss_inputnum").val();
			var stopLossType = 1; 
			var choiceStopPrices4 = $("#loss_orderType").val();
			var drection = $drection.attr("data-drection");
			var	stopLossDiff = lossChoicePrices2 - lastPrice;
			var exchangeNo = localQuote.ExchangeNo;
			var commodityNo = localQuote.CommodityNo;
			var contractNo = localQuote.ContractNo; 
			var tradeparam = createInsertStopLossParam(exchangeNo,commodityNo,contractNo,stopNumber,stopLossType,parseFloat(Math.abs(stopLossDiff)).toFixed(2),$holdAvgPrice.text(),drection,choiceStopPrices4,parseFloat(lossChoicePrices2).toFixed(2));
			if(tradeparam == undefined){
				tip("交易错误,请重试"); 
			}
			inserStopLoss(tradeparam);
		}
}
/**
 * 修改止损操作
 */
function doUpdateModifyStopLoss(){
	if(isLogin){
			var stopLossNo = selectStopLoss["stopLossNo"];
			if(stopLossNo == undefined){
				tip("请选择一行数据");
				return;
			}
			if(operationStopLossType == undefined){
				tip("请重新操作");
				return;
			}
			var modifyFlag = operationStopLossType; 
			var num = $("#stop_inputnum").val();
			var stopLossType = 0;
			var stopChecked = $("#stop_checked").is(':checked');
			if(stopChecked){
				stopLossType = 2;
			}
			var orderType = $("#stop_orderType").val();
			var lastPrice = $("#stop_lastPrice").text();
			var stopinputprice = $("#stop_inputprice").val();
			var drection = $("#stopHoldDrection").val();
			var stopDiff = $("#stop_diff").val();
			var stopLossDiff = 0;
			if(stopLossType == 0){
				stopLossDiff = lastPrice - stopinputprice;
			}else if(stopLossType == 2){
				stopLossDiff = stopDiff;
				if(drection == 0){
					stopinputprice = Number(lastPrice) - Number(stopinputprice);
				}else if(drection == 1){
					stopinputprice = Number(stopinputprice) + Number(lastPrice);
				}
			} 
			var tradeParam = createModifyStopLossParam(stopLossNo,modifyFlag,num,stopLossType,orderType,parseFloat(Math.abs(stopLossDiff)).toFixed(2),stopinputprice);
			doModifyStopLoss(tradeParam)
		}
}
/**
 * 修改止盈操作
 */
function doUpdateModifyLoss(){
	if(isLogin){
		var stopLossNo = selectStopLoss["stopLossNo"];
		if(stopLossNo == undefined){
			tip("请选择一行数据");
			return;
		}
		if(operationStopLossType == undefined){
			tip("请重新操作");
			return;
		}
		var modifyFlag = operationStopLossType; 
		var num = $("#loss_inputnum").val();
		var stopLossType = 1;
		var orderType = $("#loss_orderType").val();
		var lastPrice = $("#loss_lastPrice").text();
		var uLossPrice = $("#loss_inputprice").val();
		var drection = $("#stopHoldDrection").val();
		var lossDiff = lastPrice - uLossPrice;
		var tradeParam = createModifyStopLossParam(stopLossNo,modifyFlag,num,stopLossType,orderType,parseFloat(Math.abs(lossDiff)).toFixed(2),uLossPrice);
		doModifyStopLoss(tradeParam)
	}
}
/**
 * 暂停-删除止损单操作
 */
function doStopAndDelModifyStopLoss(){
	var stopLossNo = selectStopLoss["stopLossNo"];
	if(stopLossNo == undefined){
		tip("请选择一行数据");
		return;
	}
	if(operationStopLossType == undefined){
		tip("请重新操作");
		return;
	}
	var contractCode = $("#"+stopLossNo).attr("data-tion-contractCode");
	var localQuote = localCacheQuote[contractCode];
	if(localQuote == undefined){
		tip("无效的合约");
		return;
	}
	var lastPrice = localQuote.LastPrice;
	var modifyFlag = operationStopLossType;
	var num = $("#"+stopLossNo+" li[class = 'stoploss4']").text();
	var stopLossType = $("#"+stopLossNo+" li[class = 'stoploss3']").attr("data-tion-lossType");
	var orderType = $("#"+stopLossNo+" li[class = 'stoploss6']").attr("data-tion-orderType");
	var stopLossDiff = $("#"+stopLossNo+" li[class = 'stoploss8']").text();
	var stopLossPrice = $("#"+stopLossNo+" li[class = 'stoploss5']").attr("data-tion-price");
	if(stopLossDiff == 0){
		stopLossDiff = lastPrice - stopLossPrice;	
	}
	var tradeParam = createModifyStopLossParam(stopLossNo,modifyFlag,num,stopLossType,orderType,Math.abs(stopLossDiff),stopLossPrice);
	doModifyStopLoss(tradeParam);
}
/**
 * 添加条件单操作（价格条件）
 */
function doInsertConditionByPrice(){
	if(isLogin){
		var contractCode = $("#condition_price_contractCode").val();
		var compareType = $("#select_condition_option").val();
		var priceTriggerPonit = $("#condition_price_inputprice").val();
		var additionType = $("#select_condition_price_addoption").val();
		var additionPrice = $("#condtion_price_addPrice").val();
		var drection = $("#condition_price_drection").val();
		var orderType = $("#condition_price_orderType").val();
		var num = $("#contract_price_num").val();
		//1-有附加条件，0-没有附加条件
		var flag = 1; 
		if(additionType == -1){
			additionPrice = 0; 
			flag = 0;
			additionType = 0
		}
		var localCommdity = localCacheCommodity[contractCode];
		if(localCommdity == undefined){
			tip("无效的合约");
			return;
		}
		var exchangeNo = localCommdity.ExchangeNo;
		var commodityNo = localCommdity.CommodityNo;
		var contractNo = localCommdity.MainContract; 
		var tradeparam = createInsertCondition(exchangeNo,commodityNo,contractNo,num,0,priceTriggerPonit,compareType,"",0,0,orderType,drection,0,0,0,flag,additionType,additionPrice);
		insertCondition(tradeparam);
	}
}
/**
 * 增加条件单操作（时间格式）
 */
function doInsertConditionByTime(){
	if(isLogin){
		var contractCode = $("#contion_time_contractCode").val();
		var timeTriggerPoint = formatDateYYYMMDD(new Date)+ " "+$("#condition_time_time").val();
		var additionType = $("#select_time_condition_option").val();
		var additionPrice = $("#condtion_time_addPrice").val();
		var drection = $("#condition_drection_time").val();
		var orderType = $("#condition_orderType_time").val();
		var num = $("#condition_time_inputnum").val();
		var flag = 1;
		if(additionType == -1){
			additionPrice = 0; 
			flag = 0;
			additionType = 0
		}
		var localCommdity = localCacheCommodity[contractCode];
		if(localCommdity == undefined){
			tip("无效的合约");
			return;
		}
		var exchangeNo = localCommdity.ExchangeNo;
		var commodityNo = localCommdity.CommodityNo;
		var contractNo = localCommdity.MainContract; 
		var tradeParam = createInsertCondition(exchangeNo,commodityNo,contractNo,num,1,0,0,timeTriggerPoint,0,0,orderType,drection,0,0,0,flag,additionType,additionPrice);
		insertCondition(tradeParam);
	}
}
/**
 * 修改条件单（价格条件）
 */
function doUpdateConditionByPrice(){
	if(isLogin){
		var conditionNo = selectCondition["conditionNo"];
		if(conditionNo == undefined){
			tip("请选择一条数据");
			return;
		}
		var contractCode = $("#condition_price_contractCode").val();
		var compareType = $("#select_condition_option").val();
		var priceTriggerPonit = $("#condition_price_inputprice").val();
		var additionType = $("#select_condition_price_addoption").val();
		var additionPrice = $("#condtion_price_addPrice").val();
		var drection = $("#condition_price_drection").val();
		var orderType = $("#condition_price_orderType").val();
		var num = $("#contract_price_num").val();
		var flag = 1;
		if(additionType == -1){
			additionPrice = 0; 
			flag = 0;
			additionType = 0
		}
		var tradeParam = createUpdateConditioin(conditionNo,0,num,0,priceTriggerPonit,compareType,"",0,0,orderType,drection,0,0,0,flag,additionType,additionPrice);
		updateCondition(tradeParam);
		operateConditionType = 0;
	}
}
/**
 * 修改条件单（时间条件）
 */
function doUpdateConditionByTime(){
	if(isLogin){
		var conditionNo = selectCondition["conditionNo"];
		if(conditionNo == undefined){
			tip("请选择一条数据");
			return;
		}
		var contractCode = $("#contion_time_contractCode").val();
		var timeTriggerPoint = formatDateYYYMMDD(new Date)+ " "+$("#condition_time_time").val();
		var additionType = $("#select_time_condition_option").val();
		var additionPrice = $("#condtion_time_addPrice").val();
		var drection = $("#condition_drection_time").val();
		var orderType = $("#condition_orderType_time").val();
		var num = $("#condition_time_inputnum").val();
		var flag = 1;
		if(additionType == -1){
			additionPrice = 0; 
			flag = 0;
			additionType = 0
		}
		var tradeParam = createUpdateConditioin(conditionNo,0,num,1,0,0,timeTriggerPoint,0,0,orderType,drection,0,0,0,flag,additionType,additionPrice);
		updateCondition(tradeParam);
		operateConditionType = 0;
	}
}
/**
 * 暂停-删除-启动条件单
 */
function doUpdateAndDelCondition(){ 
	var conditionNo = selectCondition["conditionNo"];
	var tradeparam = createUpdateConditioin(conditionNo,operateConditionType,0,0,0,0,"",0,0,0,0,0,0,0,0,0,0);
	updateCondition(tradeparam);
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
	/*tip("合约【"+commodeityNo+contractNo+"】提交成功,等待交易");*/
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
	/*tip("提交成功,等待交易");*/
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
	/*tip("提交成功,等待交易");*/
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
	/*tip("合约【"+contractCode+"】提交成功,等待交易");*/
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
	/*tip("提交成功,等待撤单");*/
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
	/*tip("合约【"+contractCode+"】提交成功,等待撤单");*/
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
	/*tip("合约【"+contractCode+"】提交成功,等待交易");*/
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
	var dotSize = 2;
	if(localCommodity != undefined && localQuote != undefined){
		miniTikeSize = localCommodity.MiniTikeSize;
		lastPrice = localQuote.LastPrice;
		dotSize = localCommodity.DotSize;
	}
	if(validationInputPrice(lastPrice)){
		tip("最新价格错误");
		return false;
	}
	var limitPirce = doGetMarketPrice(lastPrice,miniTikeSize,drection,dotSize);
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
 * 更新资金明细中的浮动盈亏
 */
function sumListfloatingProfit(){
	var p =  $(".tab_position");
	if(p.length == 0){
		return;
	}
	var currencySize = localCurrencyNo.length;
	for(var i = 0 ; i < currencySize ; i++){
		var currencyNo = localCurrencyNo[i];
		if(currencyNo == undefined || currencyNo.length <= 0){
			continue;
		}
		var postionDom = $(".position-currency"+currencyNo);
		if(postionDom.html() == undefined || postionDom.length <= 0){
			continue;
		}
		var  floatingProfitDom = $("ul[data-tion-fund='"+currencyNo+"'] li[class = 'detail_floatingProfit']");
		var  todayAmount = $("ul[data-tion-fund='"+currencyNo+"'] li[class = 'detail_todayAmount']").text();
		var  closeProfit = $("ul[data-tion-fund='"+currencyNo+"'] li[class = 'detail_closeProfit']").text();
		var  counterFee = $("ul[data-tion-fund='"+currencyNo+"'] li[class = 'detail_counterFee']").text();
		var  deposit = $("ul[data-tion-fund='"+currencyNo+"'] li[class = 'detail_deposit']").text();
		var  todayBlance = $("ul[data-tion-fund='"+currencyNo+"'] li[class = 'detail_todayBalance']");
		floatingProfitDom.text(0.00);
		var price  = 0.00;
		$.each(postionDom,function(i,item){
			var $this = $(this);
			price = price + Number($this.find("li[class = 'position10']").text());
		});
		floatingProfitDom.text(parseFloat(price).toFixed(2));
		var balance = Number(todayAmount)+Number(closeProfit)-Number(counterFee)+Number(price);
		todayBlance.text(parseFloat(balance).toFixed(2));
	}
}
/**
 * 更新持仓总盈亏
 */
function updateHoldProfit(){
	var p =  $(".tab_position");
	if(p.length == 0){
		return;
	}
	var price = 0.00;
	var floatingProfit = $("#floatingProfit");
	$.each(p,function(i,item){
		var $this = $(item);
		var currencyNo  = $this.find(".position6").text();
		var floatingProfiting = $this.find(".position10").text();
		var currencyRate = localCacheCurrencyAndRate[currencyNo];
		var total = floatingProfiting*currencyRate;
		if(isNaN(total)){
			total = 0;
		}
		price = price + total;
		if(price < 0){
			floatingProfit.css("color","#0bffa4");
		}else if(price > 0){
			floatingProfit.css("color","#ff5500");
		}else if(price == 0){
			floatingProfit.css("color","#FFFFFF");
		}
		floatingProfit.text(parseFloat(price).toFixed(2));
	})
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
	$("#no_condition_list").html("");
	$("#yes_condition_list").html("");
	$("#yes_stopLoss").html("");
	$("#no_stopLoss").html("")
	$("#todayBalance").html("0.00");
	$("#deposit").html("0.00");
	$("#todayCanUse").html("0.00");
	$("#floatingProfit").html("0.00");
	$("#closeProfit").html("0.00");
	$(".caozuo").hide();
	$("#stoploss_top_title").hide();
	$("#condition_top_title").hide();
	$("#stop_loss_conditon_x").hide();
	generatePostionTitle();
	generateDesignateTitle();
	generateAccountTitle();
	generateOrderTitle();
	generateTradeSuccessTitle();
	generateNoStopLossTitle();
	generateYesStopLossTitle();
	generateNoConditioinPriceTitle();
	generateYesConditioinPriceTitle();
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
	stoplossIndex = 0;
	selectStopLoss = {};
	localCahceStopLossNo = {};
	operationStopLossType = undefined;
	conditionIndex = 0;
	localCacheCondition = {};
	selectCondition = {};
	operateConditionType = undefined;
}
/**
 * 条件单验证价格条件符合条件
 * @param {Object} priceType
 * @param {Object} price
 * @param {Object} addPriceType
 * @param {Object} addPrice
 */
/**
 * 验证价格的组合排列：第一位表示原始条件代码，第二位表示附加条件代码，第三位（原始价格-附加价格）如果结果小于0则为代码：0，如果大于0则为代码：1
 * 组合中的数组组合都是允许通过的选择和输入
 */
var priceConditionArr = ["010","101","030","301","301","210","211","320","321","230","231"];
function validationPriceCondition(priceType,price,addPriceType,addPrice){
	var flag = false;
	if(priceType == addPriceType){
		tip("初始条件和附加条件不能一致");
		return;
	}
	if(price == addPrice){
		tip("初始价格和附加价格不能一致");
		return;
	}
	var chaPrice =  price - addPrice;
	var code = 0;
	if(chaPrice > 0){
		code = 1;
	}
	code = priceType+""+addPriceType+""+code;
	var size = priceConditionArr.length;
	for(var i = 0 ; i < size ; i ++){
		if(priceConditionArr[i] == code){
			flag = true; 
			break; 
		}
	}
	if(!flag){
		tip("两个价格必须形成一个区间");
	}
	return flag;
}
/**
 * 条件单时间条件时间验证
 * @param {Object} param
 */
function validationtimeCondition(time){
	var date = new Date();
	var now = date.getTime();
	var time = formatDateYYYMMDD(date)+" "+time;
	var timeDate = new Date(time).getTime();
	if(now > timeDate){
		return false;
	}
	return true;
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