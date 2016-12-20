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
	$("#positionList").html("");  
	loadPositionTitle(); 
	localCachePositionRecentData = {}; 
	localCachePostion = {};
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
		if(stopLossLoadDataIndex == 0){
			Trade.doQryStopLoss(username);
			stopLossLoadDataIndex++;
		}
	}else if(method == "OnRspQryStopLoss"){
		if(conditionLoadDataIndex == 0){
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
var  referCount = 0;
function handleData(evt){
	var dataString = evt.data;
	var data = JSON.parse(dataString);
	var method = data.Method;
	var parameters = data.Parameters;
	linearlyLoadData(method);
	if (parameters != null) {
		if (method == "OnRspLogin") {
			$("#trade_login").text("登录");
			var code = parameters.Code;
			var loginMessage = parameters.Message;
			//登录成功加载
			if (code == 0) {
				LoginForwardInitLoadData();
				setIsLogin(true);
				loginFail = false;
				anotherPlace = false;
			} else {
				loginFail = -2;
				alertProtype(loginMessage,"登录提示",Btn.confirmed());
				tipAlert(loginMessage);
				//登录失败清理数据
				loginOut();
			}
			plus.nativeUI.closeWaiting();
			//查询个人账户信息回复
		} else if (method == "OnRspQryAccount") {
			var accountParam = parameters;
			updateBalance(accountParam);
			//addAndUpdateFundsDetails(accountParam);
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
			var commdityNo  = positionParam.CommodityNo;
			var contractNo =positionParam.ContractNo;
			var commdityAndcontract = commdityNo+contractNo;
			var comm = getMarketCommdity(commdityAndcontract);
			if(comm != undefined){ 
				if(marketSubCommdity[commdityAndcontract] == undefined){
					subscribeHold(comm.ExchangeNo,commdityNo,contractNo); 
					setMarketSubCommdity(commdityAndcontract,commdityAndcontract);
				} 
			}else{
				setMarketNotSubCommdity(commdityAndcontract,commdityAndcontract);
			}
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
			}else
			if(orderStatusWeHooks == 5){
				tip("交易失败:合约【"+orderParam.ContractCode+"】,原因【"+orderParam.StatusMsg+"】");
			}else
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
			if(referCount == 0){
				tradeSuccessLoadHoldData();
			}
			referCount++;
			/*if(isBuy && orderId == locaOrderId){   
				tradeSuccessLoadHoldData();
				resultInsertOrderId[orderId] = null; 
				localCachePositionRecentData = {}; 
				localCachePostion = {};
			    
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
			$("#switchAccount").text("登录账户");
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
				message = "添加止损单失败"
			}else{
				message = "提交成功,单号:【"+stopLossParam.StopLossNo+"】";
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
			}
			tip(stoplossType+"单【"+stopLossNo+"】,"+status);
			//查询条件单返回
		}else if(method == "OnRspQryCondition"){
			var conditionParam = parameters;
			appendCondition(conditionParam);
			//录入条件单请求返回
		}else if(method == "OnRspInsertCondition"){
			var conditionParam = parameters;
			updateCondition(conditionParam);
		}
	}else{  
		/*if(method == "OnRspQryHold" && tradeSuccessLoadFlag){
			updateOrderUpdatePosition();
			tradeSuccessLoadFlag = false;
			isBuy = false;
			localCachePositionRecentData = {};
		}*/
		if(referCount > 0){ 
			referCount--;
			tradeSuccessLoadHoldData();
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
var loadCachCurrecyRate = {};
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
	var counterFee = parama.CounterFee;
	var todayAmount = parama.TodayAmount;   
	var unExpiredProfit = parama.UnExpiredProfit;
	var unAccountProfit = parama.UnAccountProfit;
	if(counterFee == undefined){
		counterFee = parama.Fee; 
	}
	var banlance = parseFloat(Number(todayAmount)+Number(unExpiredProfit)+Number(unAccountProfit)+Number(0)).toFixed(2);;
	/*var banlance = parseFloat(Number(todayAmount)+Number(closeProfit)-Number(counterFee)).toFixed(2);*///今结存+浮盈+未结平盈+未到期平盈
	var canuse = parseFloat(banlance-deposit-frozenMoney).toFixed(2);
	localCacheCurrencyAndRate[currencyNo]  = currency == undefined ? localCacheCurrencyAndRate[currencyNo] : currency;
	loadCachCurrecyRate[accountNo] = currency;
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
	/*$("#closeProfit").text($closeProfit);
	$("#closeProfit").css("color",color);*/
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
 * @param {Objfont-size: 14px;ct} param 委托信息的json对象
 */
function appendOrder(param){
	var contractCode = param.ContractCode;
	var drectionText = analysisBusinessBuySell(param.Drection);
	var contractNo = param.ContractNo;
	var commodityNo = param.CommodityNo; 
	var localCommodity  = getMarketCommdity(commodityNo+contractNo);
	var orderPrice = param.OrderPrice;
	if(localCommodity != undefined){
		var doSize = Number(localCommodity.DotSize);
		var numOrder=Number(parseFloat(orderPrice))
		orderPrice = numOrder.toFixed(doSize);;
	}
	var orderStatus = param.OrderStatus;
	var ordreStatusText = analysisOrderStatus(orderStatus);
	var orderNum = param.OrderNum;
	var tradeNum = param.TradeNum;
	var cdNum = 0;
	if(orderStatus == 4){
		cdNum = orderNum - tradeNum;
	}
	var triggerPrice = param.TriggerPrice;
	var priceType = param.OrderPriceType;
	var tradePrice = param.TradePrice;
	var orderId = param.OrderID;
	var statusMsg = param.StatusMsg;
	var insertDateTime = param.InsertDateTime;
	if(insertDateTime == undefined || insertDateTime.length == 1){
		insertDateTime = "-"; 
	}
	if(insertDateTime=="" || insertDateTime.length==0 ){
		insertDateTime=0;
	}
	var cls = "order-index" + orderIndex;
	var clsen = 'entrust'+orderIndex; 
	var html = '<li   class = "'+cls+' '+clsen+' EntrustOreder  myLi "" data-order-order = "'+orderId+'" data-index-order = "'+orderIndex+'" data-tion-order = "'+contractCode+'">'
				+'	<a class="mui-navigate-right" >'
				+'		'
				+'			<span class = "order0">'+contractCode+'</span>'
				+'			<span class = "order1">'+ordreStatusText+'</span>'
				+'			<span class = "order2">'+drectionText+'</span>'
				+'			<span class = "order3">'+orderPrice+'</span>'
				+'			<span class = "order4">'+orderNum+'</span>'
				+'			<span class = "order5">'+tradeNum+'</span>'
				+'			<span class = "order6">'+cdNum+'</span>'
				+'			<span class = "dateTimeL order7">'+insertDateTime+'</span>'
				+'		'
				+'	</a>'
				+'</li>';
	$("#Entrust").append(html);
	//tabOn();
	if(insertDateTime=="" || insertDateTime.length==0 ){
		document.getElementsByClassName("order7")[orderIndex].style.opacity=0;
		
	}
	addOrderBindClick(cls);
	updateOrderIndex();
};
/**
 * 修改用户委托信息
 * @param {Object} param
 */
function updateOrder(param){
	var contractCode = param.ContractCode;
	var orderId = param.OrderID;
	var statusMsg = param.StatusMsg;
	var $desgPrice = $("li[data-order-order='"+orderId+"'] span[class = 'order3']");
	var $desgNumber = $("li[data-order-order='"+orderId+"'] span[class = 'order4']");
	var $orderStatus = $("li[data-order-order='"+orderId+"'] span[class = 'order1']");
	var $tradeNum = $("li[data-order-order= '"+orderId+"'] span[class = 'order5']");
	var orderStatus = param.OrderStatus;
	var tradeNum = param.TradeNum;
	var orderPrice = param.TradePrice;
	$orderStatus.text(analysisOrderStatus(orderStatus));
	$tradeNum.text(tradeNum);	
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
   var insertDateTime = param.InsertDateTime;
   var localCommodity = getMarketCommdity(commodityNo+contractNo);
   var dotSize = 2;
   if(localCommodity != undefined){
   		dotSize = Number(localCommodity.DotSize);
   }
   var cls = "des-index"+designateIndex;
   var html =   '<li    class = "'+cls+' Guadan  myLi" " data-order-des = "'+orderId+'"  data-index-des = "'+designateIndex+'" data-tion-des= "'+contractCode+'">'
				+'	<a class="mui-navigate-right" >' 
				+'		'
				+'			<span class = "desig0">'+contractCode+'</span>'
				+'			<span class = "desig1" data-drection = '+drection+'>'+drectionText+'</span>'
				+'			<span class = "desig2">'+parseFloat(orderPrice).toFixed(dotSize)+'</span>'
				+'			<span class = "desig3">'+orderNum+'</span>' 
				+'			<span class = "desig4">'+(orderNum - tradeNum)+'</span>'
				+'			<span class = "desig5 dateTimeL">'+insertDateTime+'</span>'
				+'			<span class = "desig6" style = "display:none;">'+orderSysId+'</span>'
				+ '			<span class = "desig7" style = "display:none">'+commodityNo+'</span>'
				+ '			<span class = "desig8" style = "display:none">'+contractNo+'</span>'
				+ '         <span class = "desig9" style = "display:none">'+exchangeNo+'</span>'
				+ '         <span class = "desig10" style = "display:none">'+orderId+'</span>'
				+ '	        <span class = "desig11" style = "display:none;">'+triggerPrice+'</span>'
				+'		'
				+'	</a>'
				+'</li>';
   $("#postersOrder").append(html);
  // tabOn();
   localCacheDesignate[contractCode] = createDesignatesParam(param);
   localCachedesignateContractCode[designateIndex] = orderId;
   addDesignateBindClick(cls);
   updateDesignateIndex();
};
/**
 * 修改挂单中的订单信息
 * @param {Object} param
 */
function updateDesignatesDom(param){
	var contractCode = param.ContractCode;
	var contractNo = param.ContractNo;
    var commodityNo = param.CommodityNo;
	var orderId = param.OrderID;
	var drection = param.Drection;
	var drectionText = analysisBusinessBuySell(drection);
	var orderNum = parseInt(param.OrderNum);
	var tradeNum = parseInt(param.TradeNum);
	var orderPrice = param.OrderPrice;
	var orderStatus = param.OrderStatus;
	var $gdNum = $("li[data-order-des='"+orderId+"'] span[class = 'desig4']");
	var $orderPrice = $("li[data-order-des='"+orderId+"'] span[class = 'desig2']");
	var $orderNum = $("li[data-order-des='"+orderId+"'] span[class = 'desig3']");
	var holdNum = orderNum - tradeNum;
	var localCommodity = getMarketCommdity(commodityNo+contractNo);
    var dotSize = 2;
    if(localCommodity != undefined){
    		dotSize = Number(localCommodity.DotSize);
    }
	if(holdNum == 0){
		//当挂单为0时，清理dom节点和存储数据
		delDesignatesDom(contractCode);
		deleteDesignatesContractCode(contractCode);
	}else if(holdNum != 0){ 
		$gdNum.text(holdNum);
		$orderNum.text(orderNum);
		$orderPrice.text(parseFloat(orderPrice).toFixed(dotSize));
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
	var html = '<li  class = "'+cls+' DealLi myLi" "   data-index-trade = "'+tradesIndex+'" data-tion-trade = "'+contractCode+'">'
				+'<a class="mui-navigate-right" >'
				+'	'
				+'		<span class = "trade0">'+contractCode+'</span>'
				+'		<span class = "trade1">'+drectionText+'</span>'
				+'      <span class = "trade2">'+tradePrice+'</span>'
				+'		<span class = "trade3">'+tradeNum+'</span>'
				+'		<span class = "trade4 dateTimeL">'+tradeTime+'</span>'
				+'	'
				+'	</a>'
				+'</li>';
	$("#Deal").append(html);
	//tabOn();
	addTradeSuccessBindClick(cls);
	updateTradesIndex();
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
		updatePostion(param);
		/*if(tradeSuccessLoadFlag){
			loadCachecentPositionData(param);
		}else{
			
		}*/
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
		var accoutNo = param.AccountNo;
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
		var localCommodity  = getMarketCommdity(contractAndCommodity);
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
		var clspo = 'position-index'+postionIndex; 
		var html = '<li   class = "'+cls+' '+clspo+' '+currcls+' tab_position PositionLi myLi"  data-index-position = "'+postionIndex+'" data-tion-position = "'+contractCode+'" id = "'+contractCode+'">'
				+ '<a class="mui-navigate-right" >'
				+ '		'
				+ '			<span class = "position0">'+contractCode+'</span>'
				+ '			<span class = "position1" data-drection = '+drection+'>'+drectionText+'</span>'
				+ '			<span class = "position2">'+holdNum+'</span>'
				+ '			<span class = "position3">'+holdAvgPrice+'</span>'
				+ '			<span class = "position4 dateTimeL"><input readonly = "readonly" type="text" value = "'+floatingProfit+'" style="border-left:0px;border-top:0px;border-right:0px;border-bottom:1px ;background-color:transparent;font-size:12px;width:160px;" id = "floatValue'+contractCode+'" /></span>'
				+ '			<span class = "position5" style = "display:none">'+commodityNo+'</span>'
				+ '			<span class = "position6" style = "display:none">'+contractNo+'</span>'
				+ '         <span class = "position7" style = "display:none">'+exchangeNo+'</span>'
				+ '			<span class = "position8" style = "display:none">'+floatP+'</span>'
				+ '     	<span class = "position9" style = "display:none">'+currencyNo+'</span>'
				+ '     	<span class = "position10" style = "display:none">'+accoutNo+'</span>'
				+ '		'
				+ '	</a>'
				+ '</li>'; 
		$("#positionList").append(html);
		/*tabOn();*/
		//存储数据
		localCachePostion[contractCode] = createPostionsParam(param);
		localCachePositionContractCode[postionIndex] = contractCode;
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
	var exchangeNo = param.ExchangeNo;
	var currencyNo = param.CurrencyNo;
	var openAvgPrice = param.OpenAvgPrice;
	if(isNaN(holdNum)){
		holdNum = parseInt(param.TradeNum);
	}
	if(openAvgPrice == undefined){
		openAvgPrice = param.TradePrice;
	}
	var $holdNum = $("li[data-tion-position='"+contractCode+"'] span[class = 'position2']");
	var $drection = $("li[data-tion-position='"+contractCode+"'] span[class = 'position1']");
	var $holdAvgPrice = $("li[data-tion-position='"+contractCode+"'] span[class = 'position3']");
	var $floatP = $("li[data-tion-position='"+contractCode+"'] span[class = 'position8']");
	var $floatingProfit =$("#floatValue"+contractCode);
	var oldHoldNum = parseInt($holdNum.text());
	var oldDrection = parseInt($drection.attr("data-drection"));
	var oldPrice = parseFloat($holdAvgPrice.text()).toFixed(2) *  oldHoldNum;
	var price = parseFloat(openAvgPrice).toFixed(2) * holdNum;
	if(oldDrection == drection){
		oldHoldNum = oldHoldNum + holdNum;
		price = parseFloat(price + oldPrice).toFixed(2);
		var localCommodity = getMarketCommdity(contractCode);
		var doSize = 0;
		if(localCommodity != undefined){ 
			doSize = Number(localCommodity.DotSize);
		}
		var openAvgPrice = doGetOpenAvgPrice(price,oldHoldNum,doSize);
		$holdAvgPrice.text(openAvgPrice);
		var commdityNo = param.CommodityNo;
		var contractNo = param.ContractNo;
		var floatingProft = 0.00; 
		var floatP = 0.00;
		var contractAndCommodity = commdityNo+contractNo;
		var localCommodity  = getMarketCommdity(contractAndCommodity);
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
			var localCommodity = getMarketCommdity(contractCode);
			var doSize = 2;
			if(localCommodity != undefined){
				doSize = Number(localCommodity.DotSize);
			}
			var holdAvgPrice = doGetOpenAvgPrice(price, holdNum, doSize);
			var $openAvgPrice = $("ul[data-tion-position='"+contractCode+"'] li[class = 'position3']");
			var $holdNum = $("ul[data-tion-position='"+contractCode+"'] li[class = 'position2']");
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
	var positionDom = $("li[data-tion-position='"+contractCode+"']");
	if(positionDom.html() == undefined){
		return;
	}
	var localCommodity = getLocalCacheCommodity(contractCode);
	if(localCommodity != undefined){
		currencyNo = localCommodity.CurrencyNo;
	}
	var $exchangeNo = $("li[data-tion-position='"+contractCode+"'] span[class = 'position7']");
	var $currencyNo = $("li[data-tion-position='"+contractCode+"'] span[class = 'position9']");
	var $commodityNo = $("li[data-tion-position='"+contractCode+"'] span[class = 'position5']");
	var $contractNo = $("li[data-tion-position='"+contractCode+"'] span[class = 'position6']");
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
	var designateDom = $("li[data-tion-des='"+contractCode+"']");
	if(designateDom.html() == undefined){
		return;
	}
	var $exchangeNo = $("li[data-tion-des='"+contractCode+"'] span[class = 'desig9']");
	var $commodityNo = $("li[data-tion-des='"+contractCode+"'] span[class = 'desig7']");
	var $contractNo = $("li[data-tion-des='"+contractCode+"'] span[class = 'desig8']");
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
		var stopLossDiff = param.StopLossDiff;
		var orderType = param.OrderType;
		var orderTypeText = lossOrderType(orderType);
		var holdAvgPrice = param.HoldAvgPrice;
		var holdDrection = param.HoldDrection;
		var holdDrectionText = analysisBusinessDirection(holdDrection);
		var insertTime = param.InsertDateTime;
		var dynamicPrice = param.DynamicPrice;
		var stopLossPrice = param.StopLossPrice;
		var stopLossDiff = param.StopLossDiff;
		var cls = "stoploss"+stoplossIndex;
		var html =  '<tr class="testclick1 '+cls+'" data-tion-index = "'+stoplossIndex+'" id = "'+stopLossNo+'">'
				    +'	<td class = "stoploss0">'+contractCode+'</td>'
				    +'  <td class = "stoploss1" data-tion-status="'+status+'">'+statusText+'</td>'
					+'	<td class = "stoploss2" data-tion-drection="'+holdDrection+'">'+holdDrectionText+'</td>'
					+'	<td class = "stoploss3" data-tion-lossType="'+stopLossType+'">'+stopLossTypeText+'</td>'
					+'	<td class = "stoploss4">'+num+'</td>'
					+'	<td class = "stoploss5">'+stopLossPrice+'</td>'
					+'	<td class = "stoploss6" data-tion-orderType = "'+orderType+'">'+orderTypeText+'</td>'
					+'	<td class = "stoploss7">'+insertTime+'</td>'
					+'  <td class = "stoploss8" style = "display:none;">'+stopLossDiff+'</td>'
					+'</tr>';
		if(status == 0 || status == 1){
			$("#clickTableBody").append(html);
			addStopLossBindClick(cls);
		}else if(status == 2 || status == 3 || status == 4 || status == 5){
			$("#over-clickTableBody").append(html);
		}
		stoplossIndex++;
		localCahceStopLossNo[stopLossNo] = param; 
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
	var $status = $("#"+stopLossNo+" td[class = 'stoploss1']");
	var $holdDrection = $("#"+stopLossNo+" td[class = 'stoploss2']");
	var $stopLossType = $("#"+stopLossNo+" td[class = 'stoploss3']");
	var $num = $("#"+stopLossNo+" td[class = 'stoploss4']");
	var $stopLossPrice = $("#"+stopLossNo+" td[class = 'stoploss5']");
	var $orderType = $("#"+stopLossNo+" td[class = 'stoploss6']");
	var $insertTime = $("#"+stopLossNo+" td[class = 'stoploss7']");
	$status.text(statusText);
	$status.attr("data-tion-status",status);
	$holdDrection.html(holdDrectionText);
	$holdDrection.attr("data-tion-drection",holdDrection);
	$stopLossType.text(stopLossTypeText);
	$stopLossType.attr("data-tion-lossType",stopLossType);
	$num.text(num);
	$stopLossPrice.text(stopLossPrice);
	$orderType.text(orderTypeText);
	$insertTime.text(insertTime);
	if(status == 2 || status == 3 || status == 4 || status == 5){
		var html = $("#"+stopLossNo).html();
		$("#over-clickTableBody").append("<tr class = 'testclick1' id = '"+stopLossNo+"'>"+html+"</tr>");
		$("#"+stopLossNo).remove();
		isTableIndex = true;
	}else{
		if(status == 0){
			$("#suspendCondition1").val(2);
			$("#suspendCondition1").text("暂停");
		}else if(status == 1){ 
			$("#suspendCondition1").val(3);  
			$("#suspendCondition1").text("启动");
		}
	}
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
function appendCondition(param){
	var commodityNo = param.CommodityNo;
	var contractNo = param.ContractNo;
	var exchangeNo = param.ExchangeNo;
	var conditionNo = param.ConditionNo;
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
		additionTypeText = analysisConditionCompareType(additionType)+additionPrice;
	}
	compareTypeText = compareTypeText+priceTriggerPonit+"<br/>"+additionTypeText;
	var inserOrderText = analysisBusinessBuySell(drection)+","+lossOrderType(stopLossType)+","+num+"手";
	var cls = "condition"+conditionIndex;
	var html = '<tr class="testclick tab_condition '+cls+'" id = "'+conditionNo+'">'
					+'<td class = "condition0">'+contractCode+'</td>'
					+'<td class = "condition1" data-tion-status = "'+status+'">'+statusText+'</td>'
					+'<td class = "condition2" data-tion-conditionType = "'+conditionType+'">'+conditionTypeText+'</td>'
					+'<td class = "condition3" data-tion-compareType = "'+compareType+'">'+compareTypeText+'</td>'
					+'<td class = "condition4">'+inserOrderText+'</td>'
					+'<td class = "condition5">'+formatDateYYYMMDD(new Date())+'</td>'
					+'<td class = "condition6">'+insertTime+'</td>'
				+'</tr>';
	if(status == 0 || status == 1){ 
		$("#thodyCondition").append(html);
		addConditionBindClick(cls);
	}else if(status == 2 || status == 3 || status == 4 || status == 5){
		$("#over-thbodyCondition").append(html);
	}
	localCacheCondition[conditionNo] = param;
	conditionIndex++;
}
/**
 * 修改条件单列表信息
 * @param {Object} param
 */
function updateCondition(param){
	var commodityNo = param.CommodityNo;
	var contractNo = param.ContractNo;
	var exchangeNo = param.ExchangeNo;
	var conditionNo = param.ConditionNo;
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
		additionTypeText = analysisConditionCompareType(additionType)+additionPrice;
	}
	compareTypeText = compareTypeText+priceTriggerPonit+"<br/>"+additionTypeText;
	var inserOrderText = analysisBusinessBuySell(drection)+","+lossOrderType(stopLossType)+","+num+"手";
	$("#"+conditionNo+" td[class = 'condition1']").text(statusText);
	$("#"+conditionNo+" td[class = 'condition1']").attr("data-tion-status",status);
	$("#"+conditionNo+" td[class = 'condition2']").text(conditionTypeText);
	$("#"+conditionNo+" td[class = 'condition2']").attr("data-tion-conditionType",conditionType);
	$("#"+conditionNo+" td[class = 'condition3']").text(compareTypeText);
	$("#"+conditionNo+" td[class = 'condition3']").attr("data-tion-compareType",compareType);
	$("#"+conditionNo+" td[class = 'condition4']").text(inserOrderText);
	if(status == 2 || status == 3 || status == 4 || status == 5){
		var html = $("#"+conditionNo).html();
		$("#over-thbodyCondition").append("<tr class = 'testclick1' id = '"+conditionNo+"'>"+html+"</tr>");
		$("#"+conditionNo).remove();
	}
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
				'</ul>';
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
				'	<li style = "width:70px;">委托量</li>'+
				'	<li style="width: 70px;">订单类型</li>'+
				'	<li  style="width: 70px;">委托状态</li>'+
				'	<li style = "width:70px;" >成交均价</li>'+
				'	<li style = "width:50px;"  >成交量</li>'+
				'	<li style = "width:120px;">撤单时间</li>'+
				'	<li style = "width:80px;">订单号</li>'+
				'   <li style="width: 80px;">反馈信息</li>'+
				'</ul>';
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
				'</ul>';
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
				'</ul>';
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
				'</ul>';
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
		 $this.addClass("toggleClassBack").siblings().removeClass("toggleClassBack");
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
			 $this.addClass("toggleClassBack").siblings().removeClass("toggleClassBack");
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
			var orderPrice = $("li[data-order-des='"+orderId+"'] span[class = 'desig2']").text();
			var orderNum = $("li[data-order-des='"+orderId+"'] span[class = 'desig3']").text();
			var orderId = $("li[data-order-des='"+orderId+"'] span[class = 'desig10']").text();
			selectDesgnate["orderPrice"] = orderPrice;
			selectDesgnate["orderNum"] = orderNum;
			selectDesgnate["orderId"] = orderId;
			 $this.addClass("toggleClassBack").siblings().removeClass("toggleClassBack");
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
			 $this.addClass("toggleClassBack").siblings().removeClass("toggleClassBack");
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
			 $this.addClass("toggleClassBack").siblings().removeClass("toggleClassBack");
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
		var status = $("#"+selectStopLoss["stopLossNo"]+" td[class = 'stoploss1']").attr("data-tion-status");
		if(status == 0){
			$("#suspendCondition1").val(2);
			$("#suspendCondition1").text("暂停");
		}else if(status == 1){
			$("#suspendCondition1").val(3);  
			$("#suspendCondition1").text("启动");
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
		$("li[data-tion-position='"+contractCode+"']").remove();
	});
}
/**
 * 删除挂单中的元素节点 
 * @param {Object} orderId
 */
function delDesignatesDom(orderId){
	$("li[data-order-des='"+orderId+"']").remove();
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
 * 加载持仓的标题
 */
function loadPositionTitle(){
	var html = ' <li class="PositionLi" ><a class="mui-navigate-right"><span>合约名称</span><span>多空</span><span>手数</span><span>持仓均价</span><span class="dateTimeFloat">浮动盈亏</span></a></li>';
	$("#positionList").html(html);
}
/**
 * 加载用户的账户信息
 */
function loadOperateLogin(){
	$.ajax({
		url:basepath+"/user/operateLogin",
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
 * 条件单操作的类型0-增加，1-修改
 */
var insertConditionCount = 0;
$(function(){
	var validateQueryCommodity = setInterval(function(){
			if(getQueryCommodityIsFlag()){ 
				/**
				 * 初始化交易配置 --> trade.config
				 */
				initTradeConfig();
				validateIsGetVersion();
				getVersion();
				if(username == null){
					$("#switchAccount").text("登录账号");
				}
				bindOpertion();
				clearInterval(validateQueryCommodity);
			}
		},500);
	
	$("#switchAccount").click(function(){  
		if(isLogin){
			alertProtype("是否切换当前账号","提示",Btn.confirmedAndCancle(),switchAccount,null,null);
		}else{ 
			loginOut();
			openLogin();
		} 
	});
	$("#insertCondition").click(function(){
		insertConditionCount = 0;
		$("#chioceContract").attr("disabled",false);
	});
	/**
	 * 增加止损单监听文本框
	 */
	$("#stopChoicePrices1").bind("input",function(){  
		var holdAvgPrice =$("#stopHoldAvgPrice").val();
		var stopChoicePrices1 = $("#stopChoicePrices1").val();
		if(stopChoicePrices1 <= 0 || stopChoicePrices1.length == 0){ 
			tip("请输入正确价格");
			return;
		} 
		var stopEvenPrice = $("#stopEvenPrice").text();
		var stopType = $("#choiceStopPrices").val();
		var stopDrection = $("#stopBorderLeft").attr("data-tion-drection");
		var scale = 0.00;
		if(stopType == 0){
			/*var chaPrice = stopEvenPrice - stopChoicePrices1;
			if(stopDrection == 0){
				if(chaPrice <= 0){
					tip("输入价格已不合适宜");
					return;
				}
			}else if(stopDrection == 1){
				if(chaPrice >= 0){
					tip("输入价格已不合适宜");
					return;
				}
			}*/
			if(stopDrection == 0){
				scale = (holdAvgPrice - stopChoicePrices1) / stopChoicePrices1 * 100;
			}else if(stopDrection == 1){
				scale = (stopChoicePrices1 - holdAvgPrice) / stopChoicePrices1 * 100;
			}
		}else if(stopType == 2){
			scale = stopChoicePrices1 / stopEvenPrice * 100;
		}
		$("#Increase").text(parseFloat(scale).toFixed(2)+"%");
	});
	/**
	 * 修改止损单监听文本框
	 */
	$("#stopChoicePrices3").bind("input",function(){  
		var holdAvgPrice =$("#stopHoldAvgPrice1").val();
		var stopChoicePrices3 = $("#stopChoicePrices3").val();
		if(stopChoicePrices3 <= 0 || stopChoicePrices3.length == 0){ 
			tip("请输入正确价格");
			return;
		}
		var stopEvenPrice = $("#stopEvenPrice1").text();
		var stopType = $("#choiceStopPrices2").val();
		var stopDrection = $("#stopBorderLeft1").attr("data-tion-drection");
		var scale = 0.00;
		if(stopType == 0){
			/*var chaPrice = stopEvenPrice - stopChoicePrices3;
			if(stopDrection == 0){
				if(chaPrice <= 0){
					tip("输入价格已不合适宜");
					return;
				}
			}else if(stopDrection == 1){
				if(chaPrice >= 0){
					tip("输入价格已不合适宜");
					return;
				}
			}*/
			if(stopDrection == 0){
				scale = (holdAvgPrice - stopChoicePrices3) / stopChoicePrices3 * 100;
			}else if(stopDrection == 1){
				scale = (stopChoicePrices3 - holdAvgPrice) / stopChoicePrices3 * 100;
			}
		}else if(stopType == 2){
			scale = stopChoicePrices3 / stopEvenPrice * 100;
		}
		$("#Increase2").text(parseFloat(scale).toFixed(2)+"%");
	});
	/**
	 * 增加止盈 彈框，監聽止盈輸入
	 */
	$("#lossChoicePrices2").bind("input",function(){
		$("#lossIncrease1").text(0);
		var lossChoicePrices2 = $("#lossChoicePrices2").val();
		if(lossChoicePrices2 <= 0 || lossChoicePrices2.length == 0){
			tip("请输入正确价格");
			return;
		}
		var drection = $("#lossDrection").attr("data-tion-drection");
		var lossEventPrice = $("#lossEventPrice").text();
		var holdAvgPrice = $("#lossHoldAvgPrice").val();
		var chaPrice = lossEventPrice - lossChoicePrices2;
		/*if(drection == 0){
			if(chaPrice >= 0){
				tip("输入价格已不合适宜");
				return;
			}
		}else if(drection == 1){
			if(chaPrice <= 0){
				tip("输入价格已不合适宜");
				return;
			}
		}*/
		var scale = 0.00;
		if(drection == 0){
			scale = (lossChoicePrices2 - holdAvgPrice) / lossChoicePrices2 * 100;
		}else if(drection == 1){
			scale = (holdAvgPrice - lossChoicePrices2) / lossChoicePrices2 * 100;
		}
		$("#lossIncrease1").text(parseFloat(scale).toFixed(2)+"%");
	});
	/**
	 * 修改止盈单窗口监听输入框
	 */
	$("#uLossPrice").bind("input",function(){
		$("#lossIncrease2").text(0);
		var uLossPrice = $("#uLossPrice").val();
		if(uLossPrice <= 0 || uLossPrice.length == 0){
			tip("请输入正确价格");
			return;
		}
		var drection = $("#uDrection").attr("data-tion-drection");
		var lossEventPrice = $("#uEvenPrice").text();
		var holdAvgPrice = $("#ulossHoldAvgPrice1").val();
		var chaPrice = lossEventPrice - uLossPrice;
		/*if(drection == 0){
			if(chaPrice >= 0){
				tip("输入价格已不合适宜");
				return;
			} 
		}else if(drection == 1){
			if(chaPrice <= 0){
				tip("输入价格已不合适宜");
				return;
			}
		}*/
		var scale = 0.00;
		if(drection == 0){
			scale = (uLossPrice - holdAvgPrice) / uLossPrice * 100;
		}else if(drection == 1){
			scale = (holdAvgPrice - uLossPrice) / uLossPrice * 100;
		}
		$("#lossIncrease2").text(parseFloat(scale).toFixed(2)+"%");
	});
}); 
function initSocketTrade(){
	setTradeConfig(tradeWebSocketIsMock);
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
		var dotSize = Number(localCommodity.DotSize);
		$("#trade_data #lastPrice").val(lastPrice);
		$("#trade_data #miniTikeSize").val(miniTikeSize);
		$("#trade_data #contractSize").val(localCommodity.ContractSize);
		$("#trade_data #exchangeNo").val(localCommodity.ExchangeNo);
		$("#trade_data #commodeityNo").val(localCommodity.CommodityNo);
		$("#trade_data #contractNo").val(localCommodity.MainContract);
		$("#trade_data #doSize").val(Number(localCommodity.DotSize));
		$("#money_number").val(localQoute.LastPrice);
		$("#commodity_title").text(localCommodity.CommodityName+"  "+contractCode);
		$("#float_buy").text(doGetMarketPrice(lastPrice, miniTikeSize, 0,dotSize));
		$("#float_sell").text(doGetMarketPrice(lastPrice, miniTikeSize, 1,dotSize));
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
var buyOrderPrice = 0.00;
function bindOpertion(){
	$(".buy").bind("click",function(){
		if(isLogin){
			if(validationLastPrice()){
				mui.toast("最新价格错误,请稍后重试!");
				return;
			}
			var $this = $(this);
			var orderNum = $("#orderNumber").val();
			if(orderNumber <= 0){
				mui.toast("请输入正确的手数");
				return;
			}
			if(orderNumber > 200){
				mui.toast("最多只能输入200手");
				return;
			}
			var priceType = $("input[type='radio']:checked").val();
			var orderPrice = $("#orderPrice").val();
			var tradeDrection = $this.attr("data-tion-buy"); 
			var lastPrice = $("#lastPrice").text();
			var miniTikeSize = $("#miniTikeSize").val();
			var commodityNo = $("#commodeityNo").val();
			var contractNo = $("#contractNo").val();
			var dotSize = 2;
			var localCommodity = getMarketCommdity(commodityNo+contractNo);
			if(localCommodity != undefined){
				dotSize = Number(localCommodity.DotSize);
			}
			if(priceType == 0){
				if(orderPrice <= 0 || orderPrice.length <= 0){
					mui.toast("请输入正确的价格");
					return;
				}
			}else if(priceType == 1){ 
				orderPrice = doGetMarketPrice(lastPrice,miniTikeSize,tradeDrection,dotSize);
			}
			buyOrderPrice = orderPrice;
			var content = "确定提交订单："+commodityNo+contractNo+",价格("+orderPrice+"),手数("+orderNum+"),方向("+analysisBusinessBuySell(tradeDrection)+")?";
			alertProtype(content,"确认下单?",Btn.confirmedAndCancle(),doInsertOrder,null,$this);
		}else{
			tip("未登录,请先登录");
		}
	});
	$("#allOpen").bind("click",function(){
		if(isLogin){
			var tab_position = $(".tab_position");
			if(tab_position.length <= 0){
				tip("没有需要平仓的数据");
				return;
			}
			var tipContent = "确认全部平仓?"; 
			alertProtype(tipContent,"确认下单吗?",Btn.confirmedAndCancle(),doInsertAllSellingOrder);
		}else{
			tip("未登录,请先登录");
		}
	});
	
	$("#Open").bind("click",function(){
		if(isLogin){
			var contractCode = selectPostion["contractCode"];
			var postionIndex = selectPostion["postionIndex"];
			if(contractCode == undefined || $(".postion-index"+postionIndex+"").html() == undefined){
				tip("请选择一项需要平仓的数据");
				return;
			}
			var tipContent = "确认平仓合约【"+contractCode+"】";
			alertProtype(tipContent,"确认下单吗?",Btn.confirmedAndCancle(),doInsertSellingOrder);
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
	$("#fullWithdrawal").bind("click",function(){
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
			alertProtype(tipContent,"确认撤单吗?",Btn.confirmedAndCancle(),doInsertAllCancleOrder,cancleCallBack);
		}else{
			tip("未登录,请先登录");
		}
	});
	$("#kilAnorder").bind("click",function(){
		if(isLogin){
			var contractCode = selectDesgnate["contraction"];
			var designateIndex = selectDesgnate["designateIndex"];
			if(contractCode == undefined || $(".des-index"+designateIndex+"").html() == undefined){
				tip("请选择一项需要撤单的数据");
				return;
			}
			var tipContent = "确认撤单合约【"+contractCode+"】"; 
			alertProtype(tipContent,"确认撤单?",Btn.confirmedAndCancle(),doInsertCancleOrder);
		}else{
			tip("未登录,请先登录");
		}
	});
	$(".marketBuy").bind("click",function(){
		if(isLogin){
			var $this = $(this);
			var lastPrice = $("#freshPrices").text();
			if(lastPrice <= 0 || lastPrice == undefined || lastPrice == null || isNaN(lastPrice)){
				alertProtype("交易错误","提示",Btn.confirmed());
				return;
			}
			var commodityNo = $("#commodeityNo").val();
			var contractNo = $("#contractNo").val();
			var orderNum = $("#orderNum").val();
			var miniTikeSize = $("#miniTikeSize").val();
			var orderNum = $("#orderNum").val();
			if(orderNum <= 0 ){
				mui.toast("请输入正确的手数"); 
				return; 
			}
			if(orderNum > 200){
				mui.toast("最多只能输入200手"); 
				return;
			}
			var drection = $this.attr("data-tion-buy");
			var localCommodity = getMarketCommdity(commodityNo+contractNo);
			var dotSize = 2;
			if(localCommodity != undefined){ 
				dotSize = Number(localCommodity.DotSize);
			}
			var limitPrice = doGetMarketPrice(lastPrice,miniTikeSize,drection,dotSize);
			buyOrderPrice = limitPrice;
			var content = "确定提交订单："+commodityNo+contractNo+",价格("+limitPrice+"),手数("+orderNum+"),方向("+analysisBusinessBuySell(drection)+")?";
			var isFlag = alertProtype(content,"确认下单?",Btn.confirmedAndCancle(),marketBuy,null,$this);
		}else{
			tip("未登录,请先登录");
		}
	})
	$("#changeSingle").bind("click",function(){
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
				orderPrice = 0;
			}
			if(orderNum == undefined){
				orderNum = 0;
			}
			var col1 = $("#col1");
			var col2 = $("#col2");
			var add_div = $("#add_div");
			//清空数据
			col1.val(orderPrice);
			col2.val(orderNum);
			add_div.removeClass("mui-hidden");
			mui("#popover").popover("toggle");
		}else{
			tip("未登录,请先登录");
		}
	});
	/**
	 * 添加止损单
	 */
	$("#insertStopData").bind("click",function(){
		if(isLogin){
			var contractCode = selectPostion["contractCode"];
			if(contractCode == undefined){
				tip("请选择一条信息");
				return;
			}
			var localQuote = getLocalCacheQuote(contractCode);
			if(localQuote == undefined){
				tip("无效的合约");
				return;
			}
			var lastPrice = $("#stopEvenPrice").text();
			var stopChoicePrices1 = $("#stopChoicePrices1").val();
			if(stopChoicePrices1 <= 0 || stopChoicePrices1.length == 0){
				tip("请输入正确的回撤价");
				return;  
			}
			var stopNumber = $("#stopNumber").val();
			if(isNaN(stopNumber) || stopNumber <= 0 || stopNumber.length == 0){
				tip("请输入手数");
				return;
			}
			var stopDrection = $("#stopBorderLeft").attr("data-tion-drection");
			var stopLossType = $("#choiceStopPrices").val();
			var stopLossDiff = 0;
			var typeText = "限价止损";
			if(stopLossType == 0){
				if(stopDrection == 0){
					if(lastPrice <= stopChoicePrices1){
						tip("不符合最小变动价规定，请重新设置");
						return;
					}
				}else if(stopDrection == 1){
					if(lastPrice >= stopChoicePrices1){
						tip("不符合最小变动价规定，请重新设置");
						return;
					}
				}
				stopLossDiff = lastPrice - stopChoicePrices1;
			}else if(stopLossType == 2){
				stopLossDiff = stopChoicePrices1;
				typeText = "动态止损";
			}
			if(stopLossDiff == 0){
				alertProtype("止损价差会导致立即触发,请重新设置","提示",Btn.confirmed());
				return;
			}
			alertProtype("是否添加"+typeText,"提示",Btn.confirmedAndCancle(),doGetInsertStopLoss);
		}else{
			tip("未登录,请先登录");
		}
	});
	/**
	 * 修改止损-动态单
	 */
	$("#insertStopData1").bind("click",function(){
		if(isLogin){
			var stopLossNo = selectStopLoss["stopLossNo"];
			if(stopLossNo == undefined){
				tip("请选择一条信息");
				return;
			}
			var contractCode = $("#stopEvenTd1").text();
			var localQuote = getLocalCacheQuote(contractCode);
			if(localQuote == undefined){
				tip("无效的合约");
				return;
			}
			var lastPrice = $("#stopEvenPrice1").text();
			var stopChoicePrices3 = $("#stopChoicePrices3").val();
			if(stopChoicePrices3 <= 0 || stopChoicePrices3.length == 0){
				tip("请输入正确的回撤价");
				return;  
			}
			var stopNumber = $("#stopNumber1").val();
			if(isNaN(stopNumber) || stopNumber <= 0 || stopNumber.length == 0){
				tip("请输入手数");
				return;
			}
			var stopDrection = $("#stopBorderLeft1").attr("data-tion-drection");
			var stopLossType = $("#choiceStopPrices2").val();
			var stopLossDiff = 0;
			var typeText = "限价止损";
			if(stopLossType == 0){
				var chaPrice = lastPrice - stopChoicePrices3;
				if(stopDrection == 0){
					if(chaPrice <= 0 ){
						tip("不符合最小变动价规定，请重新设置");
						return;
					}
				}else if(stopDrection == 1){
					if(chaPrice >= 0) {
						tip("不符合最小变动价规定，请重新设置");
						return;
					}
				}
				stopLossDiff = lastPrice - stopChoicePrices1;
			}else if(stopLossType == 2){
				stopLossDiff = stopChoicePrices1;
				typeText = "动态止损";
			}
			if(stopLossDiff == 0){
				alertProtype("止损价差会导致立即触发,请重新设置","提示",Btn.confirmed());
				return;
			}
			alertProtype("是否修改【"+contractCode+"】"+typeText+"止损","提示",Btn.confirmedAndCancle(),doUpdateModifyStopLoss);
		}else{
			tip("未登录,请先登录");
		}
	});
	/**
	 * 添加止盈单
	 */
	$("#insertLossData").bind("click",function(){
		if(isLogin){
			var contractCode = selectPostion["contractCode"];
			if(contractCode == undefined){
				tip("请选择一条信息");
				return;
			}
			var localQuote = getLocalCacheQuote(contractCode);
			if(localQuote == undefined){
				tip("无效的合约");
				return;
			}
			var lastPrice = $("#lossEventPrice").text();
			var lossChoicePrices2 = $("#lossChoicePrices2").val();
			if(lossChoicePrices2 <= 0 || lossChoicePrices2.length == 0){
				tip("请输入正确的回撤价");
				return;  
			}
			var stopNumber = $("#lossNumber").val();
			if(isNaN(stopNumber) || stopNumber <= 0 || stopNumber.length == 0){
				tip("请输入手数");
				return;
			}
			var lossDrection = $("#lossDrection").attr("data-tion-drection");
			var stopLossDiff = 0;
			var chaPrice  = lastPrice - lossChoicePrices2;
			if(lossDrection == 0){
				if(chaPrice >= 0){
					tip("不符合最小变动价规定，请重新设置");
					return;
				}
			}else if(lossDrection == 1){
				if(chaPrice <= 0){
					tip("不符合最小变动价规定，请重新设置");
					return;
				}
			}
			stopLossDiff = lossChoicePrices2 - lastPrice;
			if(stopLossDiff == 0){
				alertProtype("止盈价差会导致立即触发,请重新设置","提示",Btn.confirmed());
				return;
			}
			alertProtype("是否添加止盈价吗?","提示",Btn.confirmedAndCancle(),doGetInsertLossLoss);
		}else{
			tip("未登录,请先登录");
		}
	});
	/**
	 * 修改止盈单
	 */
	$("#uinsertLossData1").bind("click",function(){
		if(isLogin){
			var stopLossNo = selectStopLoss["stopLossNo"];
			if(stopLossNo == undefined){
				tip("请选择一条信息");
				return;
			}
			var contractCode = $("#stopEvenTd1").text();
			var localQuote = getLocalCacheQuote(contractCode);
			if(localQuote == undefined){
				tip("无效的合约");
				return;
			}
			var lastPrice = $("#uEvenPrice").text();
			var stopChoicePrices3 = $("#uLossPrice").val();
			if(stopChoicePrices3 <= 0 || stopChoicePrices3.length == 0){
				tip("请输入正确的回撤价");
				return;  
			}
			var stopNumber = $("#ulossNumber").val();
			if(isNaN(stopNumber) || stopNumber <= 0 || stopNumber.length == 0){
				tip("请输入手数");
				return;
			}
			var stopDrection = $("#uDrection").attr("data-tion-drection");
			var chaPrice = lastPrice - stopChoicePrices3;
			if(stopDrection == 0){
				if(chaPrice >= 0 ){
					tip("不符合最小变动价规定，请重新设置");
					return;
				}
			}else if(stopDrection == 1){
				if(chaPrice <= 0) {
					tip("不符合最小变动价规定，请重新设置");
					return;
				}
			}
			var stopLossDiff = lastPrice - stopChoicePrices1;
			if(stopLossDiff == 0){
				alertProtype("止盈价差会导致立即触发,请重新设置","提示",Btn.confirmed());
				return;
			}
			alertProtype("是否修改【"+contractCode+"】止盈","提示",Btn.confirmedAndCancle(),doUpdateModifyLoss);
		}else{
			tip("未登录,请先登录")
		}
	});
	/**
	 * 赋值，确定操作是修改-删除-暂停
	 */
	$(".updateStopLoss").bind("click",function(){
		if(isLogin){
			var stopLossNo = selectStopLoss["stopLossNo"];
			if(stopLossNo == undefined){
				tip("请选择一行数据");
				return;
			} 
			var $this = $(this);
			var modifyFlag = $this.val();
			operationStopLossType = modifyFlag;
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
			alertProtype("是否"+operationText+"止损单？","提示",Btn.confirmedAndCancle(),doStopAndDelModifyStopLoss);
		}else{
			tip("未登录,请先登录")
		}
		
	});
	/**
	 * 修改止损/止盈单，显示修改止损、止盈单窗口
	 */
	$("#modifyCondition1").bind("click",function(){
		if(isLogin){
			operationStopLossType = 0;
			var stopLossNo = selectStopLoss["stopLossNo"];
			if(stopLossNo == undefined){
				tip("请选择一行数据");
				return;
			}
			var status = $("#"+stopLossNo+" td[class = 'stoploss1']").attr("data-tion-status");
			if(status == 0){
				tip("运行中的止损/止盈单不能修改,请先暂停");
				return;
			}
			var contractCode = $("#"+stopLossNo+" td[class = 'stoploss0']").text();
			var localQuote = getLocalCacheQuote(contractCode);
			if(localQuote == undefined){
				tip("无效的合约");
				return;
			}
			var holdAvgPrice = $("li[data-tion-position='"+contractCode+"'] span[class = 'position3']");
			if(holdAvgPrice == undefined){
				tip("无效持仓合约");
				return;
			}
			holdAvgPrice = holdAvgPrice.text();
			var stopDrection = $("#"+stopLossNo+" td[class = 'stoploss2']");
			var drection = stopDrection.attr("data-tion-drection");
			var drectionText = stopDrection.text();
			var stopChoicePrices1 = $("#"+stopLossNo+" td[class = 'stoploss5']").text();
			var num = $("#"+stopLossNo+" td[class = 'stoploss4']").text();
			var orderType = $("#"+stopLossNo+" td[class = 'stoploss6']").attr("data-tion-orderType");
			var stopEvenPrice = localQuote.LastPrice;
			var stoplossType = $("#"+stopLossNo+" td[class = 'stoploss3']");
			var lossType = stoplossType.attr("data-tion-lossType");
			$("#stopHoldAvgPrice1").val(holdAvgPrice);
			$("#stopEvenTd1").text(contractCode);
			$("#stopBorderLeft1").text(drectionText); 
			$("#stopBorderLeft1").attr("data-tion-drection",drection);
			$("#stopEvenPrice1").text(stopEvenPrice);
			$("#stopChoicePrices1").text(holdAvgPrice);
			$("#choiceStopPrices2").val(lossType);
			$("#stopChoicePrices3").val(stopChoicePrices1);
			$("#stopNumber1").val(num); 
			
			$("#stopHoldAvgPrice1").val(holdAvgPrice);
			$("#choiceStopPrices3").val(orderType);
			$("#ulossContractCode").text(contractCode);
			$("#uDrection").text(drectionText);  
			$("#uDrection").attr("data-tion-drection",drection);
			$("#uEvenPrice").text(stopEvenPrice);
			$("#uLossPrice").val(stopChoicePrices1);
			$("#ulossNumber").val(num);
			$("#uchoiceLossPrices").val(orderType);
			$("#ulossHoldAvgPrice1").val(holdAvgPrice);
			var scale = 0.00; 
			var chaPrice = stopEvenPrice - stopChoicePrices1;
			if(lossType == 0){
				scale = (holdAvgPrice - stopChoicePrices1) / stopChoicePrices1 * 100;
				$("#Increase2").text(parseFloat(scale).toFixed(2)+"%");
			}else if(lossType == 1){
			   scale = (stopChoicePrices1 - holdAvgPrice) / stopChoicePrices1 * 100;
			   $("#lossIncrease2").text(parseFloat(scale).toFixed(2)+"%");
			}else if(lossType == 2){
				scale = stopChoicePrices1 / stopEvenPrice * 100;
				$("#Increase2").text(parseFloat(scale).toFixed(2)+"%");
			}
			mui("#popoverLoss1").popover("toggle");
			if(lossType == 0 || lossType == 2){
				$(".ustopTitle").show();
				$(".ulossTitle").hide();
			}else if(lossType == 1){
				$(".ustopTitle").hide();
				$(".ulossTitle").show(); 
			}
		}else{
			tip("未登录,请先登录");
		}
	});
	/**
	 * 添加条件单(价格条件)
	 */
	$("#insertConditionTable").bind("click",function(){
		if(isLogin){
			var chioceAdditional = $("#chioceAdditional").val();
			var conditoionPricesInput = $("#ConditoionPricesInput").val();
			if(conditoionPricesInput == null || conditoionPricesInput.length == 0){
				tip("触发价格错误");
				return;
			}
			if(chioceAdditional != -1){
				var conditoionPricesInput1 = $("#ConditoionPricesInput1").val();
				if(ConditoionPricesInput1 <= 0 || ConditoionPricesInput1.length){
					tip("附加触发价格错误");
					return;
				}
			}
			var conditoionPricesInput3 = $("#ConditoionPricesInput3").val();
			if(conditoionPricesInput3 <= 0 || conditoionPricesInput3.length == 0){
				tip("手数输入错误");
				return;
			} 
			var chioceContract = $("#chioceContract").val();
			if(insertConditionCount == 0){
				alertProtype("你确定要提交【"+chioceContract+"】条件单吗?","提示",Btn.confirmedAndCancle(),doInsertConditionByPrice);
			}else if(insertConditionCount == 1){
				alertProtype("你确定要修改【"+chioceContract+"】条件单吗?","提示",Btn.confirmedAndCancle(),doUpdateConditionByPrice);
			}
		}else{
			tip("未登录,请先登录");
		}
	});
	/**
	 * 添加条件单(时间条件)
	 */
	$("#insertConditionTable1").bind("click",function(){
		if(isLogin){
			var chioceTimeAdditional = $("#chioceTimeAdditional").val();
			if(chioceTimeAdditional != -1){
				var ConditoionTimePricesInput = $("#ConditoionPricesInput1").val();
				if(ConditoionTimePricesInput <= 0 || ConditoionTimePricesInput.length){
					tip("附加触发价格错误");
					return;
				}
			}
			var ConditoionTimeInput = $("#ConditoionTimeInput").val();
			if(ConditoionTimeInput <= 0 || ConditoionTimeInput.length == 0){
				tip("手数输入错误");
				return;
			}
			var chioceContract = $("#chioceContract").text();
			if(insertConditionCount == 0){
				alertProtype("你确定要提交【"+chioceContract+"】条件单吗?","提示",Btn.confirmedAndCancle(),doInsertConditionByTime);
			}else if(insertConditionCount == 1){
				alertProtype("你确定要修改【"+chioceContract+"】条件单吗?","提示",Btn.confirmedAndCancle(),doUpdateConditionByTime);
			}
			
		}else{
			tip("未登录,请先登录");
		}
	});
	/**
	 * 修改条件单显示窗口
	 */
	$("#modifyCondition").bind("click",function(){
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
			var df = new DateTimeFormat();
			var dateTime = df.parse(timeTriggerPoint);
			var hours = dateTime.getHours();
			var minutes = dateTime.getMinutes();
			var time = hours+":"+minutes;
			$("#chioceContract").val(contractCode);
			$("#chiocePrices").val(compareType);
			$("#ConditoionPricesInput").val(priceTriggerPonit);
			$("#chioceAdditional").val(additionType);
			$("#ConditoionPricesInput1").val(additionType);
			$("#shopDrection").val(drection);
			$("#chiocePricesSelect").val(orderType);
			$("#ConditoionPricesInput3").val(num);
			
			
			$("#chioceContract1").val(contractCode);
			$("#insertTimeInput").val(time);
			$("#chioceTimeAdditional").val(additionType);
			$("#ConditoionTimePricesInput").val(additionPrice);
			$("#shopDrectionTime").val(drection);
			$("#chiocePricesSelectTime").val(orderType);
			$("#ConditoionTimeInput").val(num);
			insertConditionCount = 1; 
			$("#chioceContract").attr("disabled",true);
			mui("#popoverConditoion").popover("toggle");
		}else{
			tip("未登录,请先登录");
		}
	});
}

$("#add").bind("click",function(){
	var contractCode = selectDesgnate["contraction"];
	var tipContent = "确认改单合约【"+contractCode+"】"; 
	alertProtype(tipContent,"确认改单?",Btn.confirmedAndCancle(),doInsertChangeSingleOrder,cancleCallBack);
	var add_div = $("#add_div");
	add_div.addClass("mui-hidden");
	mui("#popover").popover("toggle");
});
/**
 * 显示止损盈亏的窗口
 */
$("#stopLoss").bind("click",function(){
	var contractCode = selectPostion["contractCode"];
	if(contractCode == undefined){
		tip("请选择一条信息");
		return;
	}
	var $contractCode = $("li[data-tion-position='"+contractCode+"'] span[class = 'position0']");
	var $drection = $("li[data-tion-position='"+contractCode+"'] span[class = 'position1']");
	var $holdNum = $("li[data-tion-position='"+contractCode+"'] span[class = 'position2']");
	var $holdAvgPrice = $("li[data-tion-position='"+contractCode+"'] span[class = 'position3']");
	var localQuote = getLocalCacheQuote($contractCode.text());
	if(localQuote == undefined){
		tip("无效的品种合约");
		return;
	}
	$("#stopEvenTd").text($contractCode.text()); 
	$("#stopBorderLeft").text($drection.text());
	$("#stopBorderLeft").attr("data-tion-drection",$drection.attr("data-drection"));
	$("#stopEvenPrice").text(localQuote.LastPrice);
	$("#stopNumber").val($holdNum.text());  
	$("#stopHoldAvgPrice").val($holdAvgPrice.text());
	$("#stopChoicePrices1").val($holdAvgPrice.text());
	$("#lossContractCode").text($contractCode.text());
	$("#lossDrection").text($drection.text());
	$("#lossDrection").attr("data-tion-drection",$drection.attr("data-drection"));
	$("#lossNumber").val($holdNum.text());
	$("#lossEventPrice").text(localQuote.LastPrice);
	$("#lossHoldAvgPrice").val($holdAvgPrice.text());
	mui("#popoverLoss").popover("toggle");
});
/**
 * 下单
 */
/**
 * 是否是下单操作
 */
var isBuy = false;
function doInsertOrder(param){ 
	if(validationLastPrice()){
		alertProtype("交易错误","提示",Btn.confirmed());
		return;
	}
	var $this = param;
	var tradeDrection = $this.attr("data-tion-buy");
	var orderNumber = $("#orderNumber").val();
	//var priceType = $("input[type='radio']:checked").val();
	if(orderNumber == null || isNaN(orderNumber) || orderNumber <= 0 || orderNumber.length <= 0){
		alertProtype("手数输入错误数量","提示",Btn.confirmed());
		return;
	}
	/*if(priceType == 1){
		src=" = doGetMarketPrice($("#lastPrice").text(),$("#miniTikeSize").val(),tradeDrection);
	}*/
	if(buyOrderPrice == null || isNaN(buyOrderPrice) || buyOrderPrice <= 0 || buyOrderPrice.length <= 0 ){
		alertProtype("价格输入错误","提示",Btn.confirmed());
		return;
	}
	var exchanageNo = $("#exchangeNo").val();
	var commodeityNo = $("#commodeityNo").val();
	var contractNo = $("#contractNo").val();
	Trade.doInsertOrder(exchanageNo,commodeityNo,contractNo,orderNumber,tradeDrection,0,buyOrderPrice,0,doGetOrderRef());
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
	isBuy = true;
}
/**
 * 全部撤单操作
 */
function doInsertAllCancleOrder(){
	for(var i = 0 ; i < designateIndex ; i++){
		var order = localCachedesignateContractCode[i];
		if(order == undefined || $(".des-index"+i+"").html() == undefined){
			continue;
		}
		var tradeParam = doGetCancleOrderBasicParam(order);
		var param = new Array();
		param[0] = tradeParam
		cancleOrder(param);
	}
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
	 var orderPrice = $("#col1").val();
	 var orderNum = $("#col2").val();
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
	isUpdateOrder = true;
}
/**
 * 添加止损操作
 */
function doGetInsertStopLoss(){
	if(isLogin){
			var contractCode = selectPostion["contractCode"];
			var $drection = $("li[data-tion-position='"+contractCode+"'] span[class = 'position1']");
			var $holdNum = $("li[data-tion-position='"+contractCode+"'] span[class = 'position2']");
			var $holdAvgPrice = $("li[data-tion-position='"+contractCode+"'] span[class = 'position3']");
			var localQuote = getLocalCacheQuote(contractCode);
			var lastPrice = $("#stopEvenPrice").text();
			var stopChoicePrices1 = $("#stopChoicePrices1").val();
			var stopNumber = $("#stopNumber").val();
			var stopLossType = $("#choiceStopPrices").val();
			var choiceStopPrices1 = $("#choiceStopPrices1").val();
			var stopLossDiff = 0;
			var drection = $drection.attr("data-drection");
			if(stopLossType == 0){
				stopLossDiff = lastPrice - stopChoicePrices1;
			}else if(stopLossType == 2){
				stopLossDiff = stopChoicePrices1;
				if(drection == 0){
					stopChoicePrices1 = Number(stopChoicePrices1) + Number(lastPrice);
				}else if(drection == 1){
					stopChoicePrices1 =  Number(stopChoicePrices1) - Number(lastPrice);
				}
			}
			var exchangeNo = localQuote.ExchangeNo;
			var commodityNo = localQuote.CommodityNo;
			var contractNo = localQuote.ContractNo; 
			var tradeparam = createInsertStopLossParam(exchangeNo,commodityNo,contractNo,$holdNum.text(),stopLossType,parseFloat(Math.abs(stopLossDiff)).toFixed(2),$holdAvgPrice.text(),drection,choiceStopPrices1,parseFloat(stopChoicePrices1).toFixed(2));
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
			var $drection = $("li[data-tion-position='"+contractCode+"'] span[class = 'position1']");
			var $holdNum = $("li[data-tion-position='"+contractCode+"'] span[class = 'position2']");
			var $holdAvgPrice = $("li[data-tion-position='"+contractCode+"'] span[class = 'position3']");
			var localQuote = getLocalCacheQuote(contractCode);
			var lastPrice = $("#lossEventPrice").text();
			var lossChoicePrices2 = $("#lossChoicePrices2").val();
			var stopNumber = $("#lossNumber").val();
			var stopLossType = 1; 
			var choiceStopPrices4 = $("#choiceStopPrices4").val();
			var drection = $drection.attr("data-drection");
			var	stopLossDiff = lossChoicePrices2 - lastPrice;
			var exchangeNo = localQuote.ExchangeNo;
			var commodityNo = localQuote.CommodityNo;
			var contractNo = localQuote.ContractNo; 
			var tradeparam = createInsertStopLossParam(exchangeNo,commodityNo,contractNo,$holdNum.text(),stopLossType,parseFloat(Math.abs(stopLossDiff)).toFixed(2),$holdAvgPrice.text(),drection,choiceStopPrices4,parseFloat(lossChoicePrices2).toFixed(2));
			if(tradeparam == undefined){
				tip("交易错误,请重试"); 
			}
			inserStopLoss(tradeparam);
		}else{
			tip("未登录,请先登录");
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
			var num = $("#stopNumber1").val();
			var stopLossType = $("#choiceStopPrices2").val();
			var orderType = $("#choiceStopPrices3").val();
			var lastPrice = $("#stopEvenPrice1").text();
			var stopChoicePrices3 = $("#stopChoicePrices3").val();
			var drection = $("#stopBorderLeft1").attr("data-tion-drection");
			var stopLossDiff = 0;
			if(stopLossType == 0){
				stopLossDiff = lastPrice - stopChoicePrices3;
			}else if(stopLossType == 2){
				stopLossDiff = stopChoicePrices3;
				if(drection == 0){
					stopChoicePrices3 = Number(stopChoicePrices3) + Number(lastPrice);
				}else if(drection == 1){
					stopChoicePrices3 = Number(stopChoicePrices3) - Number(lastPrice);
				}
			} 
			var tradeParam = createModifyStopLossParam(stopLossNo,modifyFlag,num,stopLossType,orderType,parseFloat(Math.abs(stopLossDiff)).toFixed(2),stopChoicePrices3);
			doModifyStopLoss(tradeParam)
		}else{
			tip("未登录,请先登录");
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
	var contractCode = $("#"+stopLossNo+" td[class = 'stoploss0']").text();
	var localQuote = getLocalCacheQuote(contractCode);
	if(localQuote == undefined){
		tip("无效的合约");
		return;
	}
	var lastPrice = localQuote.LastPrice;
	var modifyFlag = operationStopLossType;
	var num = $("#"+stopLossNo+" td[class = 'stoploss4']").text();
	var stopLossType = $("#"+stopLossNo+" td[class = 'stoploss3']").attr("data-tion-lossType");
	var orderType = $("#"+stopLossNo+" td[class = 'stoploss6']").attr("data-tion-orderType");
	var stopLossDiff = $("#"+stopLossNo+" td[class = 'stoploss8']").text();
	var stopLossPrice = $("#"+stopLossNo+" td[class = 'stoploss5']").text();
	if(stopLossDiff == 0){
		stopLossDiff = lastPrice - stopLossPrice;	
	}
	var tradeParam = createModifyStopLossParam(stopLossNo,modifyFlag,num,stopLossType,orderType,Math.abs(stopLossDiff),stopLossPrice);
	doModifyStopLoss(tradeParam);
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
		var num = $("#ulossNumber").val();
		var stopLossType = 1;
		var orderType = $("#uchoiceLossPrices").val();
		var lastPrice = $("#uEvenPrice").text();
		var uLossPrice = $("#uLossPrice").val();
		var drection = $("#stopBorderLeft1").attr("data-tion-drection");
		var lossDiff = lastPrice - uLossPrice;
		var tradeParam = createModifyStopLossParam(stopLossNo,modifyFlag,num,stopLossType,orderType,parseFloat(Math.abs(lossDiff)).toFixed(2),uLossPrice);
		doModifyStopLoss(tradeParam)
	}else{
		tip("未登录,请先登录");
	}
}
/**
 * 添加条件单操作（价格条件）
 */
function doInsertConditionByPrice(){
	if(isLogin){
		var contractCode = $("#chioceContract").val();
		var compareType = $("#chiocePrices").val();
		var priceTriggerPonit = $("#ConditoionPricesInput").val();
		var additionType = $("#chioceAdditional").val();
		var additionPrice = $("#ConditoionPricesInput1").val();
		var drection = $("#shopDrection").val();
		var orderType = $("#chiocePricesSelect").val();
		var num = $("#ConditoionPricesInput3").val();
		var flag = true;
		if(additionPrice <= 0 || additionPrice.length == 0){
			flag = false;
		}
		var localCommdity = getMarketCommdity(contractCode);
		if(localCommdity == undefined){
			tip("无效的合约");
			return;
		}
		var exchangeNo = localCommdity.ExchangeNo;
		var commodityNo = localCommdity.CommodityNo;
		var contractNo = localCommdity.ContractNo;
		var tradeparam = createInsertCondition(exchangeNo,commodityNo,contractNo,num,0,priceTriggerPonit,compareType,"","","",orderType,drection,"","","",flag,additionType,additionPrice);
		insertCondition(tradeparam);
	}
}
/**
 * 增加条件单操作（时间格式）
 */
function doInsertConditionByTime(){
	if(isLogin){
		var contradeCode = $("#chioceContract1").val();
		var timeTriggerPoint = formatDateYYYMMDD(new Date())+" "+$("#insertTimeInput").val();
		var additionType = $("#chioceTimeAdditional").val();
		var additionPrice = $("#ConditoionTimePricesInput").val();
		var drection = $("#shopDrectionTime").val();
		var orderType = $("#chiocePricesSelectTime").val();
		var num = $("#ConditoionTimeInput").val();
		var flag = true;
		if(additionPrice <= 0 || additionPrice.length == 0){
			flag = false;
		}
		var localCommdity = getMarketCommdity(contractCode);
		if(localCommdity == undefined){
			tip("无效的合约");
			return;
		}
		var exchangeNo = localCommdity.ExchangeNo;
		var commodityNo = localCommdity.CommodityNo;
		var contractNo = localCommdity.ContractNo; 
		var tradeParam = createInsertCondition(exchangeNo,commodityNo,contractNo,num,1,"","",timeTriggerPoint,"","",orderType,drection,"","","",flag,additionType,additionPrice);
		insertCondition(tradeParam);
	}else{
		tip("未登录,请先登录");
	}
}
/**
 * 修改条件单（价格条件）
 */
function doUpdateConditionByPrice(){
	if(isLogin){
		var conditionNo = selectCondition[conditionNo];
		if(conditionNo == undefined){
			tip("请选择一条数据");
			return;
		}
		var contractCode = $("#chioceContract").val();
		var compareType = $("#chiocePrices").val();
		var priceTriggerPonit = $("#ConditoionPricesInput").val();
		var additionType = $("#chioceAdditional").val();
		var additionPrice = $("#ConditoionPricesInput1").val();
		var drection = $("#shopDrection").val();
		var orderType = $("#chiocePricesSelect").val();
		var num = $("#ConditoionPricesInput3").val();
		var flag = true;
		if(additionPrice <= 0 || additionPrice.length == 0){
			flag = false;
		}
		var tradeParam = createUpdateConditioin(conditionNo,0,num,0,priceTriggerPonit,compareType,"","","",orderType,drection,"","","",flag,additionType,additionPrice);
		updateCondition(tradeParam);
	}else{
		tip("未登录,请先登录");		
	}
}
/**
 * 修改条件单（时间条件）
 */
function doUpdateConditionByTime(){
	if(isLogin){
		var conditionNo = selectCondition[conditionNo];
		if(conditionNo == undefined){
			tip("请选择一条数据");
			return;
		}
		var contradeCode = $("#chioceContract1").val();
		var timeTriggerPoint = formatDateYYYMMDD(new Date())+" "+$("#insertTimeInput").val();
		var additionType = $("#chioceTimeAdditional").val();
		var additionPrice = $("#ConditoionTimePricesInput").val();
		var drection = $("#shopDrectionTime").val();
		var orderType = $("#chiocePricesSelectTime").val();
		var num = $("#ConditoionTimeInput").val();
		var flag = true;
		if(additionPrice <= 0 || additionPrice.length == 0){
			flag = false;
		}
		var tradeParam = createUpdateConditioin(conditionNo,0,num,1,"","",timeTriggerPoint,"","",orderType,drection,"","","",flag,additionType,additionPrice);
		updateCondition(tradeParam);
	}else{
		tip("未登录,请先登录");		
	}
}
/**
 * 获取平仓的基本信息
 * @param obj
 */
function doGetSellingBasicParam(obj){
	var contract = obj;
	var $contractCode  = contract;
	var $drection =  $("li[data-tion-position='"+contract+"'] span[class = 'position1']").attr("data-drection");
	var $holdNum =  $("li[data-tion-position='"+contract+"'] span[class = 'position2']").text();
	var $openAvgPrice =  $("li[data-tion-position='"+contract+"'] span[class = 'position3']").text();
	var $commodityNo =  $("li[data-tion-position='"+contract+"'] span[class = 'position5']").text();
	var $contractNo =  $("li[data-tion-position='"+contract+"'] span[class = 'position6']").text();
	var $exchangeNo =  $("li[data-tion-position='"+contract+"'] span[class = 'position7']").text();
	var drection = 0;
	if($drection == 0){
		drection = 1;	
	}
	var contractCode = $commodityNo + $contractNo;
	var localCommodity = getMarketCommdity(contractCode);
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
		var $drection =  $("li[data-order-des='"+contract+"'] span[class = 'desig1']").attr("data-drection");
		var $orderPrice =  $("li[data-order-des='"+contract+"'] span[class = 'desig2']").text();
		var $orderNum =  $("li[data-order-des='"+contract+"'] span[class = 'desig3']").text();
		var $cdNum =  $("li[data-order-des='"+contract+"'] span[class = 'desig4']").text();
		var $OrderSysID =  $("li[data-order-des='"+contract+"'] span[class = 'desig6']").text();
		var $commodityNo =  $("li[data-order-des='"+contract+"'] span[class = 'desig7']").text();
		var $contractNo =  $("li[data-order-des='"+contract+"'] span[class = 'desig8']").text();
		var $exchangeNo =  $("li[data-order-des='"+contract+"'] span[class = 'desig9']").text(); 
		var $orderId =  $("li[data-order-des='"+contract+"'] span[class = 'desig10']").text();
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
		var $drection =  $("li[data-order-des='"+contract+"'] span[class = 'desig1']").attr("data-drection");
		var $orderPrice =  $("li[data-order-des='"+contract+"'] span[class = 'desig2']").text();
		var $orderNum =  $("li[data-order-des='"+contract+"'] span[class = 'desig3']").text();
		var $cdNum =  $("li[data-order-des='"+contract+"'] span[class = 'desig4']").text();
		var $OrderSysID =  $("li[data-order-des='"+contract+"'] span[class = 'desig6']").text();
		var $commodityNo =  $("li[data-order-des='"+contract+"'] span[class = 'desig7']").text();
		var $contractNo =  $("li[data-order-des='"+contract+"'] span[class = 'desig8']").text();
		var $exchangeNo =  $("li[data-order-des='"+contract+"'] span[class = 'desig9']").text();
		var $orderId =  $("li[data-order-des='"+contract+"'] span[class = 'desig10']").text();
		var $triggerPrice =  $("li[data-order-des='"+contract+"'] span[class = 'desig11']").text();
		var modifyOrderParam = createModifyOrderParam($OrderSysID,$orderId,$exchangeNo,$commodityNo,$contractNo,$orderNum,$drection,Math.abs($orderPrice),$triggerPrice);
	return modifyOrderParam; 
}
/**
 * 行情页面买卖处理 
 * @param {Object} param
 */
function marketBuy(param){
		var $this = param;
		var lastPrice = $("#freshPrices").text();
		if(lastPrice <= 0 || lastPrice == undefined || lastPrice == null || isNaN(lastPrice)){
			alertProtype("交易错误","提示",Btn.confirmed());
			return;
		}
		var exchangeNo = $("#exchangeNo").val();
		var commodityNo = $("#commodeityNo").val();
		var contractNo = $("#contractNo").val();
		var contractSize = $("#contractSize").val();
		var miniTikeSize = $("#miniTikeSize").val();
		var orderNum = $("#orderNum").val();
		var drection = $this.attr("data-tion-buy");
		//var localCommodity = getMarketCommdity(commodityNo+contractNo);
		/*var dotSize = 2;
		if(localCommodity != undefined){
			dotSize = localCommodity.DotSize;
		}*/
		//var limitPrice = doGetMarketPrice(lastPrice,miniTikeSize,drection,dotSize);
		var priceType = 0;
		Trade.doInsertOrder(exchangeNo,commodityNo,contractNo,orderNum,drection,0,buyOrderPrice,0,doGetOrderRef());
		tip("合约【"+commodityNo+contractNo+"】提交成功,等待交易");
	}
/**
 * 计算列表的浮动盈亏总和
 */
function sumListfloatingProfit(){
	var positionDom =  $(".tab_position");
	var price = 0.00;
	$.each(positionDom, function(i,item) {
		var $this = $(this);
		var $floatP =$this.find("span[class = 'position8']");
	    var $contractCode = $this.find("span[class = 'position0']");
	    var localCommodity = getMarketCommdity($contractCode.text());
	    if(localCommodity != undefined){
	    	var currencyNo = localCommodity.CurrencyNo; 
	    	var currencyRate = localCacheCurrencyAndRate[currencyNo];
			price = price + Number($floatP.text() * currencyRate);
	    }
	});
	if(isNaN(price)){ 
		return;
	}
	$("#floatingProfit").val(parseFloat(price).toFixed(2));
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
		}
	}
}
/**
 * 更新账户资产 
 */
function updateAccountBalance(){
	if(isLogin){ 
		var floatingProfit = $("#floatingProfit").val();
		var todayBalance = $("#todayBalance");
		var todayCanUse = $("#todayCanUse"); 
		todayBalance.text(parseFloat(loadCachTodayBanlance+Number(floatingProfit)).toFixed(2));
		todayCanUse.text(parseFloat(loadCachTodayCanuse+Number(floatingProfit)).toFixed(2));
	}
}
/**
 * 更新止损止盈最新价格
 */
function updateStopAndLossLastPrice(param){
	if(isLogin){
		var lastPrice = param.LastPrice;
		var commodityNo = param.CommodityNo;
		var contractNo = param.ContractNo;
		var contractCode = commodityNo+contractNo;
		var ulossContractCode = $("#ulossContractCode").text();
		var stopEvenTd = $("#stopEvenTd").text();
		if(contractCode == ulossContractCode){
			$("#stopEvenPrice1").text(lastPrice);
			$("#uEvenPrice").text(lastPrice);	
		}
		if(contractCode == stopEvenTd){
			$("#stopEvenPrice").text(lastPrice);
			$("#lossEventPrice").text(lastPrice); 
		}
	}
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
	$("#todayBalance").html(0.00);
	$("#deposit").html(0.00);
	$("#todayCanUse").html(0.00);
	$("#floatingProfit").html(0.00);
	$("#closeProfit").html(0.00);
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
	buyOrderPrice=0.00;
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
/**
 * 验证登录
 */
function vadationIsLogin(){
	if(username == null){
		alertProtype("你还未登录,请先登录","提示",Btn.confirmedAndCancle(),openLogin);
		return false;
	}
	return true;
}
/**
 * 登录按钮事件
 */
function switchAccount(){
	tradeLoginOut();
	$("#switchAccount").text("登录账户");
	openLogin();
}
/**
 * 跳转到登录页面
 */
function openLogin(){ 
	loginOutFlag = true;
	loginOutTip=true;
	mui.openWindow({
		url:"../login/operateLogin.html",
		id : "operateLogin",
		 extras:{
					commdityNo:$("#commodeityNo").val(),
					name : tranferParam,
					backpageID:"transactionDetails"
				}
	});
}
/**
 * 验证最新价格 
 */
function validationLastPrice(){
	var lastPrice = $("#lastPrice").text();
	if(lastPrice == undefined || lastPrice == null || lastPrice.length == 0 || parseFloat(lastPrice) <= 0){
		return true;
	}else{
		return false;
	}
}
/**
 * 下单操作的tip遮罩层
 */
function setTimeOutInsertOrder(){
	tradeSetTimeOut = setTimeout(function(){
					plus.nativeUI.closeWaiting(); 
				},10000);
}
/**
 * 清理数据列表
 */
function clearTradListData(){
	$("#account_gdt1").html("");
	$("#Entrust").html("");
	$("#postersOrder").html("");
	$("#positionList").html("");
	$("#Deal").html("");
	$("#todayBalance").text("0.00");
	$("#deposit").text("0.00");
	$("#todayCanUse").text("0.00");
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
