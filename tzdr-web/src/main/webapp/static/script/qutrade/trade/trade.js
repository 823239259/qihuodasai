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
	linearlyLoadData(method);
	if (parameters != null) {
		if (method == "OnRspLogin") {
			var code = parameters.Code;
			var loginMessage = parameters.Message;
			//登录成功加载
			if (code == 0) {
				LoginForwardInitLoadData();
				$("#show_login").hide();
				$("#show_user_info").show();
				$("#top_username").text(username);
				setIsLogin(true);
			} else {
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
			//订单状态通知
		} else if (method == "OnRtnOrderState") {
			var orderParam = parameters;
			updateOrder(orderParam);
			var orderStatusWeHooks = orderParam.OrderStatus;
			//当订单状态改变
			var contractCode = orderParam.ContractCode;
			if (orderStatusWeHooks == 3 || orderStatusWeHooks == 4 || orderStatusWeHooks == 5) {
				delDesignatesDom(contractCode);
			} else if (orderStatusWeHooks == 0) {
				appendDesignates(orderParam);
			} else if (orderStatusWeHooks == 1 || orderStatusWeHooks == 2) {
				updateDesignatesDom(orderParam);
			}
			var orderId = orderParam.OrderID;
			var cacaleOrderId = selectDesgnate["orderId"];
			var contractCode = selectDesgnate["contraction"];
			if(orderStatusWeHooks == 4  && cacaleOrderId==orderId){
				tip("撤单成功:合约【"+contractCode+"】,订单号【"+orderId+"】");
			}
			if(orderStatusWeHooks == 5){
				tip("交易失败:合约【"+orderParam.ContractCode+"】,原因【"+orderParam.StatusMsg+"】");
			}
			if(isChangeOrder && cacaleOrderId==orderId){
				var orderPrice = orderParam.OrderPrice;
				var orderNum = orderParam.OrderNum;
				tip("改单成功:合约【"+contractCode+"】,委托价【"+orderPrice+"】,委托量【"+orderNum+"】");
			}
			//订单成交通知
		} else if (method == "OnRtnOrderTraded") {
			var tradeParam = parameters;
			appendTradeSuccess(tradeParam);
			appendPostionAndUpdate(tradeParam);
			tip("交易成功：合约【"+tradeParam.ContractCode+"】,交易手数:【"+tradeParam.TradeNum+"】,交易价格:【"+tradeParam.TradePrice+"】");
			//资金变化通知
		} else if (method == "OnRtnMoney") {
			var accountParam = parameters;
			updateBalance(accountParam)
			updateFundsDetails(accountParam);
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
var localCacheCurrencyAndRate = {};
function updateBalance(parama){
	var currencyNo = parama.CurrencyNo;
	var accountNo = parama.AccountNo;
	var cachBanlace = loadCachBanlance[accountNo];
	var banlance = parama.TodayBalance;
	var deposit = parama.Deposit;
	var canuse = parama.TodayCanUse;
	var currency = parama.CurrencyRate; 
	localCacheCurrencyAndRate[currencyNo]  = currency;
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
		$("#floatingProfit").text(parseFloat(parama.floatingProfit).toFixed(2));
		$("#closeProfit").text(parseFloat(param.CloseProfit).toFixed(2));
	});
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
var localCacheFundDetail = {};
/**
 * 增加资金明细
 * @param param
 */
function addFundsDetails(param){
	var currencyNo = param.CurrencyNo;
	var todayBalance = parseFloat(param.TodayBalance).toFixed(2);
	var todayCanUse = parseFloat(param.TodayCanUse).toFixed(2);
	var deposit = parseFloat(param.Deposit).toFixed(2);
	var floatingProfit = parseFloat(param.FloatingProfit).toFixed(2);
	var keepDepositf = parseFloat(param.KeepDeposit).toFixed(2);
	var oldBalance = parseFloat(param.OldBalance).toFixed(2);
	var oldAmount = parseFloat(param.OldAmount).toFixed(2);
	var todayAmount = parseFloat(param.TodayAmount).toFixed(2);
	var frozenMoney = parseFloat(param.FrozenMoney).toFixed(2);
	var currencyRate = param.CurrencyRate;
	var profitRate = "";
	var cls = "currencyNo"+currencyNo;
	var html = '<ul class="tab_content '+cls+'" data-tion-fund = "'+currencyNo+'">'+
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
	$("#account_title .tab_lis").after(html);
	tabOn();
	localCacheFundDetail[currencyNo]=param;
	addFundDetailBindClick(currencyNo);
} 
/**
 * 更新资金明细
 * @param param
 */
function updateFundsDetails(param){
	var currencyNo = param.AccountNo;
	var todayBalance = parseFloat(param.TodayBalance).toFixed(2);
	var todayCanUse = parseFloat(param.TodayCanUse).toFixed(2);
	var deposit = parseFloat(param.Deposit).toFixed(2);
	var floatingProfit = parseFloat(param.floatingProfit).toFixed(2);
	var keepDepositf = parseFloat(param.KeepDeposit).toFixed(2);
	var todayAmount = parseFloat(param.TodayAmount).toFixed(2);
	var frozenMoney = parseFloat(param.FrozenMoney).toFixed(2);
	var currencyRate = param.CurrencyRate;
	var profitRate = "";
	var $detailTodayBalance = $("ul[data-tion-fund='"+currencyNo+"'] li[class = 'detail_todayBalance']");
	var $detailTodayCanUse = $("ul[data-tion-fund='"+currencyNo+"'] li[class = 'detail_todayCanUse']");
	var $detailDeposit = $("ul[data-tion-fund='"+currencyNo+"'] li[class = 'detail_deposit']");
	//var $detailFloatingProfit = $("ul[data-tion-fund='"+currencyNo+"'] li[class = 'detail_floatingProfit']");
	var $detailKeepDeposit = $("ul[data-tion-fund='"+currencyNo+"'] li[class = 'detail_keepDepositf']");
	var $detailTodayAmount = $("ul[data-tion-fund='"+currencyNo+"'] li[class = 'detail_todayAmount']");
	var $detailFrozenMoney = $("ul[data-tion-fund='"+currencyNo+"'] li[class = 'detail_frozenMoney']");
	var $detailProfitRate = $("ul[data-tion-fund='"+currencyNo+"'] li[class = 'detail_profitRate']");
	var $detailCurrencyRate = $("ul[data-tion-fund='"+currencyNo+"'] li[class = 'detail_currencyRate']");
	$detailTodayBalance.text(todayBalance);
	$detailTodayCanUse.text(todayCanUse);
	$detailDeposit.text(deposit);
	//$detailFloatingProfit.text(floatingProfit);
	$detailKeepDeposit.text(keepDepositf);
	$detailTodayAmount.text(todayAmount);
	$detailFrozenMoney.text(frozenMoney);
	$detailProfitRate.text(profitRate);
	$detailCurrencyRate.text(currencyRate);
}
var orderIndex = 0;
/**
 * 添加用户委托信息
 * @param {Object} param 委托信息的json对象
 */
function appendOrder(param){
	var contractCode = param.ContractCode;
	var drectionText = analysisBusinessDirection(param.Drection);
	var orderPrice = param.OrderPrice;
	var orderStatus = param.OrderStatus;
	var ordreStatusText = analysisOrderStatus(orderStatus);
	var orderNum = param.OrderNum;
	var tradeNum = param.TradeNum;
	var triggerPrice = param.TriggerPrice;
	var tradePrice = param.TradePrice;
	var orderId = param.OrderID;
	var insertDateTime = param.InsertDateTime;
	var cls = "order-index" + orderIndex;
	var html = '<ul class="tab_content '+cls+'" data-index-order = "'+orderIndex+'" data-tion-order = "'+contractCode+'">'+
				'	<li class="ml order0">'+contractCode+'</li>'+
				'	<li class = "order1" style="width: 50px;">'+drectionText+'</li>'+
				'	<li class = "order2">'+orderPrice+'</li>'+
				'	<li class = "order3" style="width: 50px;">'+orderNum+'</li>'+
				'	<li class = "order4">'+triggerPrice+'</li>'+
				'	<li class = "order5">'+ordreStatusText+'</li>'+
				'	<li class = "order6" style="width: 150px;">'+tradePrice+'</li>'+
				'	<li class = "order7" style="width: 50px;">'+tradeNum+'</li>'+
				'	<li class = "order8" style="width: 120px;">'+insertDateTime+'</li>'+
				'	<li class = "order9">'+orderId+'</li>'+
				'</ul>';
	$("#order_title .tab_lis").after(html);
	tabOn();
	addOrderBindClick(cls);
	updateOrderIndex();
};
/**
 * 修改用户委托信息
 * @param {Object} param
 */
function updateOrder(param){
	var contractCode = param.ContractCode;
	var $orderStatus = $("ul[data-tion-order='"+contractCode+"'] li[class = 'order5']");
	var $tradeNum = $("ul[data-tion-order= '"+contractCode+"'] li[class = 'order7']");
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
   var drectionText = analysisBusinessDirection(drection);
   var orderPrice = param.OrderPrice;
   var orderNum = param.OrderNum;
   var tradeNum = param.TradeNum;
   var orderSysId = param.OrderSysID;
   var exchangeNo = param.ExchangeNo;
   var orderId = param.OrderID;
   var triggerPrice = param.TriggerPrice;
   var cls = "des-index"+designateIndex;
   var html = '<ul class="tab_content '+cls+'"  data-index-des = "'+designateIndex+'" data-tion-des= "'+contractCode+'">'+
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
   $("#des_title .tab_lis").after(html);
   tabOn();
   localCacheDesignate[contractCode] = createDesignatesParam(param);
   localCachedesignateContractCode[designateIndex] = contractCode;
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
	var drectionText = analysisBusinessDirection(drection);
	var orderNum = parseInt(param.OrderNum);
	var tradeNum = parseInt(param.TradeNum);
	var orderPrice = param.OrderPrice;
	var orderStatus = param.OrderStatus;
	var $gdNum = $("ul[data-tion-des='"+contractCode+"'] li[class = 'des5']");
	var $orderPrice = $("ul[data-tion-des='"+contractCode+"'] li[class = 'des3']");
	var $orderNum = $("ul[data-tion-des='"+contractCode+"'] li[class = 'des4']");
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
	var drectionText = analysisBusinessDirection(drection);
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
				'	<li class = "trade1" style="width: 30px;">'+drectionText+'</li>'+
				'	<li class = "trade2" style="width: 120px;">'+tradePrice+'</li>'+
				'	<li class = "trade3" style="width: 50px;">'+tradeNum+'</li>'+
				'	<li class = "trade4">'+currencyNo+'</li>'+
				'	<li class = "trade5" style="width: 50px;">'+tradeNo+'</li>'+
				'	<li class = "trade6">'+orderId+'</li>'+
				'	<li class = "trade7">'+tradeTime+'</li>'+
				'	<li class = "trade8">'+exchangeNo+'</li>'+
				'</ul>';
	$("#trade_title .tab_lis").after(html);
	tabOn();
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
		
		var cls = "postion-index"+postionIndex;
		var html = '<ul class="tab_content '+cls+'" data-index-position = "'+postionIndex+'" data-tion-position = "'+contractCode+'" id = "'+contractCode+'"> '+
					'	<li class="position0 ml" style="width: 80px;">'+contractCode+'</li>'+
					'	<li  class = "position1" style="width: 50px;">'+holdNum+'</li>'+
					'	<li  class = "position2" style="width: 30px;padding-left:10px" data-drection = "'+drection+'">'+drectionText+'</li>'+
					'	<li  class = "position3" style="width: 120px;">'+holdAvgPrice+'</li>'+
					'	<li  class = "position4">'+floatingProfit+'</li>'+
					'	<li  class = "position5">'+exchangeNo+'</li>'+
					'	<li  class = "position6">'+currencyNo+'</li>'+
					'	<li  class = "position7"  style = "display:none;">'+commodityNo+'</li>'+
					'	<li  class = "position8"  style = "display:none;">'+contractNo+'</li>'+
					'	<li  class = "position9"  style = "display:none;">'+openAvgPrice+'</li>'+
					'	<li  class = "position10"  style = "display:none;">'+floatP+'</li>'+
					'</ul>';
		$("#hold_title .tab_lis").after(html);
		tabOn();
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
	var $holdNum = $("ul[data-tion-position='"+contractCode+"'] li[class = 'position1']");
	var $drection = $("ul[data-tion-position='"+contractCode+"'] li[class = 'position2']");
	var $openAvgPrice = $("ul[data-tion-position='"+contractCode+"'] li[class = 'position9']");
	var $floatP = $("ul[data-tion-position='"+contractCode+"'] li[class = 'position10']");
	var $floatingProfit = $("ul[data-tion-position='"+contractCode+"'] li[class = 'position4']");
	var oldHoldNum = parseInt($holdNum.text());
	var oldDrection = parseInt($drection.attr("data-drection"));
	var oldPrice = parseFloat($openAvgPrice.text()) *  oldHoldNum;
	var price = parseFloat(openAvgPrice) * holdNum;
	if(oldDrection == drection){
		oldHoldNum = oldHoldNum + holdNum;
		price = price + oldPrice;
		var openAvgPrice = doGetOpenAvgPrice(price,oldHoldNum);
		$openAvgPrice.text(openAvgPrice);
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
 * 生成成交记录表头
 */
function generateTradeSuccessTitle(){
	var html = '<ul class="tab_lis">'+
				'	<li class="ml">合约代码</li>'+
				'	<li>买卖</li>'+
				'	<li>成交均价</li>'+
				'	<li>成交量</li>'+
				'	<li>币种</li>'+
				'	<li>成交编号</li>'+
				'	<li>订单号</li>'+
				'	<li>成交时间</li>'+
				'	<li>交易所</li>'+
				'</ul>';
	$("#trade_title").append(html);
}
/**
 * 生成持仓操作节点
 */
function generateHoldHandleDom(){
	var html =  '<ul class="caozuo">'+
			    '	<li><a href="javascript:void(0);" id = "allSelling">全部平仓</a></li>'+
				'	<li><a href="javascript:void(0);" id = "selling">平仓</a></li>'+
				'	<li><a href="javascript:void(0);" id = "backhand">反手</a></li>'+
			    '</ul>';
	$("#hold_title").append(html);
}
/**
 * 生成挂单操作节点
 */
function generateDesHandleDom(){
	var html =  '<ul class="caozuo">'+
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
			var contractCode = $this.attr("data-tion-des");
			selectDesgnate["contraction"] = contractCode;
			selectDesgnate["designateIndex"] = $this.attr("data-index-des");
			var orderPrice = $("ul[data-tion-des='"+contractCode+"'] li[class = 'des3']").text();
			var orderNum = $("ul[data-tion-des='"+contractCode+"'] li[class = 'des4']").text();
			var orderId = $("ul[data-tion-des='"+contractCode+"'] li[class = 'des10']").text();
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
function delDesignatesDom(contractCode){
	$("ul[data-tion-des='"+contractCode+"']").remove();
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
function addBindsss(cls){
	$("."+cls+"").bind("click",function(){
		var $this = $(this);
		var text = $this.text();
		$("#quotation_account").val(text);
		$("#more_account").css("display","none");
	});
}
$(function(){
	$.ajax({
		url:basepath+"/user/operateLogin",
		type:"get",
		success:function(result){
			if(result){
				var data = result.data.data;
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
	});
	$("#quotation_account").mouseover(function(){
		$("#more_account").css("display","block");
	});
	$("#more_account").mouseover(function(){
		$("#more_account").css("display","block");
	});
	$("#quotation_account").mouseout(function(){
		$("#more_account").css("display","none");
	});
	//$("#").val("${ctx}/userftse/trade_list");
	bindOpertion();
	$("#select_commodity").click(function(){
		var contractCode = $(this).val();
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
		$("#float_buy").text(doGetMarketPrice(lastPrice, miniTikeSize, 0));
		$("#float_sell").text(doGetMarketPrice(lastPrice, miniTikeSize, 1));
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
	});
	$("#show_login").show();
	$("#show_user_info").hide();
	$("#trade_login").click(function(){
		username = $("#quotation_account").val();
		password = $.base64.encode($("#quotation_password").val());
		tradeLogin();
	});
	$("#trade_loginOut").click(function(){
		tradeLoginOut(username);
	});
});
/**
 * 绑定交易操作事件
 */
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
			var tipContent = "确认提交订单:合约【"+commodityNo+contractNo+"】,价格:【"+orderPrice+"】,手数:【"+orderNum+"】";
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
	$("#allSelling").bind("click",function(){
		if(isLogin){
			var tipContent = "确认全部平仓";
			tipConfirm(tipContent,doInsertAllSellingOrder,cancleCallBack);
		}else{
			tip("未登录,请先登录");
		}
	});
	
	$("#selling").bind("click",function(){
		if(isLogin){
			var contractCode = selectPostion["contractCode"];
			if(contractCode == undefined){
				tip("请选择一项需要平仓的数据");
				return;
			}
			var tipContent = "确认平仓合约【"+contractCode+"】";
			tipConfirm(tipContent,doInsertSellingOrder,cancleCallBack);
		}else{
			tip("未登录,请先登录");
		}
	});
	$("#backhand").bind("click",function(){
		if(isLogin){
			var contractCode = selectPostion["contractCode"];
			if(contractCode == undefined){
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
			var tipContent = "确认全部撤单合约"; 
			tipConfirm(tipContent,doInsertAllCancleOrder,cancleCallBack);
		}else{
			tip("未登录,请先登录");
		}
	});
	$("#desOrder").bind("click",function(){
		if(isLogin){
			var contractCode = selectDesgnate["contraction"];
			if(contractCode == undefined){
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
			if(contractCode == undefined){
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
							'	<p style = "padding-top:10px;">委托数量：<input  style="background:#FFFFFF;border:1px solid  #ABABAB" type="number" value = "'+orderNum+'"  id="update_des_number"/></p>'+
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
						 tipConfirm(tipContent,doInsertChangeSingleOrder,cancleCallBack());
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
	Trade.doInsertOrder(exchanageNo,commodeityNo,contractNo,orderNum,tradeDrection,0,orderPrice,0,doGetOrderRef());
	tip("合约【"+commodeityNo+contractNo+"】提交成功,等待交易");
}
/**
 * 全部平仓操作
 */
function doInsertAllSellingOrder(){
	for(var i = 0 ; i < postionIndex;i++){
		var contractCode = localCachePositionContractCode[i];
		if(contractCode == undefined){
			continue;
		}
		var tradeParam = doGetSellingBasicParam(contractCode);
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
}
/**
 * 全部撤单操作
 */
function doInsertAllCancleOrder(){
	for(var i = 0 ; i < designateIndex ; i++){
		var contractCode = localCachedesignateContractCode[i];
		if(contractCode == undefined || $(".data-index-des"+i+"").html() == undefined){
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
	var contractCode = selectDesgnate["contraction"];
	var designateIndex = selectDesgnate["designateIndex"];
	if(contractCode == undefined || $(".des-index"+designateIndex+"").html() == undefined){
		return;
	}
	var tradeParam = doGetCancleOrderBasicParam(contractCode);
	var param = new Array();
	param[0] = tradeParam
	cancleOrder(param);
	tip("合约【"+contractCode+"】提交成功,等待撤单");
}
/**
 * 改单操作
 */
/**
 * 是否是改单操作
 */
var isChangeOrder = false;
function doInsertChangeSingleOrder(){
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
	var tradeParam = doGetModifyOrderBasicParam(contractCode);
	tradeParam.orderPrice = orderPrice;
	tradeParam.orderNum = orderNum;
	var param = new Array();
	param[0]=tradeParam;
	modifyOrder(param);
	layer.closeAll();
	isChangeOrder = true
	tip("合约【"+contractCode+"】提交成功,等待交易");
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
		return;
	}
	var limitPirce = doGetMarketPrice(lastPrice,miniTikeSize,drection);
	console.log(limitPirce);
	if(validationInputPrice(limitPirce)){
		tip("平仓价格错误");
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
		var $contractCode  = contract;
		var $drection =  $("ul[data-tion-des='"+contract+"'] li[class = 'des2']").attr("data-drection");
		var $orderPrice =  $("ul[data-tion-des='"+contract+"'] li[class = 'des3']").text();
		var $orderNum =  $("ul[data-tion-des='"+contract+"'] li[class = 'des4']").text();
		var $cdNum =  $("ul[data-tion-des='"+contract+"'] li[class = 'des5']").text();
		var $OrderSysID =  $("ul[data-tion-des='"+contract+"'] li[class = 'des6']").text();
		var $commodityNo =  $("ul[data-tion-des='"+contract+"'] li[class = 'des8']").text();
		var $contractNo =  $("ul[data-tion-des='"+contract+"'] li[class = 'des9']").text();
		var $exchangeNo =  $("ul[data-tion-des='"+contract+"'] li[class = 'des7']").text();
		var $orderId =  $("ul[data-tion-des='"+contract+"'] li[class = 'des10']").text();
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
		var $drection =  $("ul[data-tion-des='"+contract+"'] li[class = 'des2']").attr("data-drection");
		var $orderPrice =  $("ul[data-tion-des='"+contract+"'] li[class = 'des3']").text();
		var $orderNum =  $("ul[data-tion-des='"+contract+"'] li[class = 'des4']").text();
		var $cdNum =  $("ul[data-tion-des='"+contract+"'] li[class = 'des5']").text();
		var $OrderSysID =  $("ul[data-tion-des='"+contract+"'] li[class = 'des6']").text();
		var $commodityNo =  $("ul[data-tion-des='"+contract+"'] li[class = 'des8']").text();
		var $contractNo =  $("ul[data-tion-des='"+contract+"'] li[class = 'des9']").text();
		var $exchangeNo =  $("ul[data-tion-des='"+contract+"'] li[class = 'des7']").text();
		var $orderId =  $("ul[data-tion-des='"+contract+"'] li[class = 'des10']").text();
		var $triggerPrice =  $("ul[data-tion-des='"+contract+"'] li[class = 'des11']").text();
		var modifyOrderParam = createModifyOrderParam($OrderSysID,$orderId,$exchangeNo,$commodityNo,$contractNo,$orderNum,$drection,Math.abs($orderPrice),$triggerPrice);
	return modifyOrderParam;
}
/**
 * 计算列表的浮动盈亏
 */
function sumListfloatingProfit(){
	var price  = 0;
	for (var i = 0; i < postionIndex; i++) {
		var obj = $(".postion-index"+i+"");
		if(obj.html() == undefined){
			continue;
		}
		var currencyNo =  $("ul[data-index-position='"+i+"'] li[class = 'position6']").text();
		var float =  $("ul[data-index-position='"+i+"'] li[class = 'position10']").text();
		var currencyObj = $(".currencyNo"+currencyNo+"");
		if(currencyObj.html() == undefined){
			continue;
		}
		var  floatingProfitDom = $("ul[data-tion-fund='"+currencyNo+"'] li[class = 'detail_floatingProfit']");
		var  detailCurrencyRateDom = $("ul[data-tion-fund='"+currencyNo+"'] li[class = 'detail_currencyRate']");
		var floatingProfitText = floatingProfitDom.text();
		var detailCurrencyRateText = parseFloat(detailCurrencyRateDom.text()).toFixed(3);
		price = price + float * detailCurrencyRateText;
		floatingProfitDom.text(parseFloat(price).toFixed(2));
	}
}
/**
 * 清除交易列表的数据并生成操作按钮
 */
function clearTradListData(){
	$("#account_title").html("");
	$("#order_title").html("");
	$("#des_title").html("");
	$("#trade_title").html("");
	$("#hold_title").html("");
	generateHoldHandleDom();
	generateDesHandleDom();
	bindOpertion();
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