// 交易配置
var TradeConfig = {
	version : "3.3",	// 版本
	url_real : "ws://192.168.0.213:7002", // 实盘地址
	model : "1", // 实盘：0；	模拟盘：1
	client_source : "N_WEB",	// 客户端渠道
	username : "000013",		// 账号(新模拟盘——000008、直达实盘——000140、易盛模拟盘——Q517029969)
	password : "YTEyMzQ1Ng==" 	// 密码：base64密文(明文：a123456——YTEyMzQ1Ng==     888888——ODg4ODg4	 74552102——NzQ1NTIxMDI=		123456=MTIzNDU2)
};
mui.app_request('/user/getTradeAccount', {}, function(result) {
			if(result.success == true) {
				TradeConfig.username = result.data.tradeProfessionalAccount;
				TradeConfig.password = result.data.tradeProfessionalPwd;
			}
		}, function(res) {});
var tradeSocket = null;

// 是否更新持仓盈亏：连接上交易，且接收完持仓信息再开始根据最新行情更新持仓盈亏
var ifUpdateHoldProfit = false;
// 是否更新账户浮动盈亏：连接上交易，且接收完账户资金信息再开始根据行情更新
var ifUpdateAccountProfit = false;
// 心跳检查，超过指定时间则刷新重连
var HeartBeat = {
	lastHeartBeatTimestamp : 1,	// 最后心跳时间
	oldHeartBeatTimestamp : 0,	// 上一次心跳时间
	intervalCheckTime : 8000,   // 间隔检查时间：8秒
	check : function(){
		if (this.lastHeartBeatTimestamp == this.oldHeartBeatTimestamp) { // 心跳未更新——上次心跳时间与最新心跳时间一致
			layer.msg('交易服务器断开，正在刷新重连...', {
				icon: 7
			});
			mui.app_refresh("simulationQuote");
		} else {
			this.oldHeartBeatTimestamp = this.lastHeartBeatTimestamp; // 更新上次心跳时间
		}
	}
};

setInterval(function(){
		HeartBeat.check();
}, HeartBeat.intervalCheckTime);	// 间隔8秒检查一次

/**
 * 连接交易服务器
 */
function connectTradeServer() {
	tradeSocket = new WebSocket(TradeConfig.url_real);
}

/**
 * 交易初始化加载
 */
function initTradeClient() {
	if(tradeSocket == null) {
		connectTradeServer();
	}

	tradeSocket.onopen = function() {
		Trade.doLogin(TradeConfig.username, TradeConfig.password, TradeConfig.model, TradeConfig.version, TradeConfig.client_source);
		
	}

	tradeSocket.onmessage = function(evt) {
		handleMessage(evt);
	}

	tradeSocket.onclose = function(evt) {
		vsLog("onclose【" + evt.data + "】");
	}

	tradeSocket.onerror = function(evt) {
		vsLog("onerror【" + evt.data + "】");
	}
}

/**
 * 交易接口返回处理
 * 
 * @param {Object} evt
 */
function handleMessage(evt) {
	var dataString = evt.data;
	var jData = JSON.parse(dataString);
	var method = jData.Method;
	var parameters = jData.Parameters;
	if (method != "OnRspQryHoldTotal" &&		// 持仓返回结束，初始化浮动盈亏，开启动态盈亏计算
	    method != "OnRspQryAccount" &&
	 	method != "OnRspQryHisTrade") { 		// 资金返回结束需要做合并处理
		if (parameters == null || typeof(parameters) == "undefined") {
			vsLog("返回参数为空不做处理：" + dataString);
			return;
		}
	}
	if (method == "OnRtnHeartBeat") {
		vsLog("OnRtnHeartBeat：" + dataString);
		HeartBeat.lastHeartBeatTimestamp = parameters.Ref; // 更新心跳最新时间戳
		return;
	}
	
    switch (method){
	    	case "OnRspLogin":{	// 登录回复
			var code = parameters.Code;
			var loginMessage = parameters.Message;
			//登录成功加载
			if(code == 0) {
				layer.msg('交易连接成功');
				initTradeInfo(); 	// 初始化交易信息
			} else {
				layer.msg(loginMessage, {
					icon: 5
				});
			}
		}	break;
		case "OnRspLogout":{		// 登出回复
			var code = parameters.Code;
			var loginMessage = parameters.Message;
			if(code == 1) {
				layer.msg(loginMessage, {
					icon: 5
				});
			}
		}	break;
	    		
	    	case "OnRspQryHoldTotal":	{	// 查询持仓回复
			
			// 持仓返回结束更新持仓盈亏
			if (isEmpty(parameters)) {
				ifUpdateHoldProfit = true; // 可以使用最新行情更新持仓盈亏
			} else {
				appendHold(parameters);
				initHoldFloatingProfit(parameters); // 根据订阅行情初始化持仓盈亏
			}
		}	break;
		
	    	case "OnRspQryOrder":{	// 查询订单回复
			appendOrder(parameters); // 增加订单
			appendApply(parameters); // 挂单记录
		}	break;
		
	    	case "OnRspQryTrade":{	// 查询成交单回复
			appendTrade(parameters);
		}	break;
		
			case "OnRspQryHisTrade":{	// 查询历史成交单回复
			appendHisTrade(parameters);
		}	break;
		
	    	case "OnRspQryAccount":	{	// 查询个人账户信息回复
			if (isEmpty(parameters)) { // 资金返回结束显示
				CacheAccount.updateTotalAccount(); // 初始化汇总账户资金信息
				ifUpdateAccountProfit = true; // 是否可以更新账户盈亏标志：资金信息显示完毕就可以更新盈亏
			} else {
				appendAccount(parameters); // 追加资金明细
			}
		}	break;
	    
	    	case "OnRspOrderInsert":{	// 下单请求回复
	    	
	    	layerMessageOnRspOrderInsert(parameters);
	    	
			// 新增委托单并初始化状态
			appendOrder(parameters);
			// 排队中委托单放入挂单列表
			appendApply(parameters);
			
	    	}	break;
	    	
	    	case "OnRtnOrderState":{		// 委托状态通知：排队触发、改单触发、撤销触发、成交触发
			updateOrder(parameters);		// 更新委托记录
			updateApply(parameters);		// 更新挂单记录
	    	}	break;
	    	
	    	case "OnRtnOrderTraded":{	// 成交单通知
	    		// 新增成交记录
	    		appendTrade(parameters);
	    		layerMessageOnRtnOrderTraded(parameters);
	    		// 新增/更新持仓记录（可通过持仓变化通知接口更新-暂不处理）
	    	}	break;
	    	
	    	case "OnRtnHoldTotal":{	// 持仓变化通知
	    		updateHold(parameters); // 更新持仓
	    	}	break;
	    	
	    	case "OnRtnMoney":{	// 资金变化通知
	    		// 更新资金账户信息
	    		updateAccount(parameters);
	    		// 更新资金汇总信息
	    		CacheAccount.updateTotalAccount(parameters);
	    	}	break;
	    	
			case "OnRspQryStopLoss":{
	    		//止损单
	    		appendStopLoss(parameters);
	    	}break;
	    	case "OnRtnStopLossState":{
	    		//止损单通知
	    		updateStopLoss(parameters);
	    	}break;
	    	case "OnRspInsertStopLoss":{
	    		//止损单录入请求回复
	    		dealInsertStopLoss(parameters);
	    	}break;
	    	case "OnRspQryCondition":{
	    		//条件单查询返回
	    		initConditionList(parameters);
	    	}break;
	    	case "OnRspInsertCondition":{
	    		//处理条件单录入请求回复
	    		dealRspInsertCondition(parameters);
	    	}break;
	    	case "OnRtnConditionState":{
	    		//条件单状态改变通知
//	    		console.log(JSON.stringify(parameters));
	    		doRtnConditionState(parameters);
	    	}break;
	    	
	   		case "OnError":{		// 错误通知——TODO
	    		// 提示服务器错误，根据相应错误进行客户端处理
	    		doError(parameters);
	    	}	break;
	    	default:
	    		vsLog("暂未识别的回报：" + dataString);
	    		break;
    }
}

/**
 * 条件单状态改变通知
 * @param {Object} value
 */
function doRtnConditionState(value){
	
	if(value.Status==0){
		layer.msg('【'+value.CommodityNo+value.ContractNo+'】条件单【'+ value.ConditionNo+'】,改单后已运行');
	}else if(value.Status==1){
		layer.msg('【'+value.CommodityNo+value.ContractNo+'】条件单【'+ value.ConditionNo+'】,已暂停');
	}else if(value.Status==2){
		layer.msg('【'+value.CommodityNo+value.ContractNo+'】条件单【'+ value.ConditionNo+'】,已触发');
	}else if(value.Status==3){
		layer.msg('【'+value.CommodityNo+value.ContractNo+'】条件单【'+ value.ConditionNo+'】,已取消');
	}else if(value.Status==4){
		layer.msg('【'+value.CommodityNo+value.ContractNo+'】条件单【'+ value.ConditionNo+'】,插入失败');
	}else if(value.Status==5){
		layer.msg('【'+value.CommodityNo+value.ContractNo+'】条件单【'+ value.ConditionNo+'】,触发失败');
	}
	if(value.Status>2){
		tplFillData("condition-notrigger-list", "tplNotriggerConditionList", value, FillType.before);
	}else if(value.Status==2){
			var CommodityNo = value.CommodityNo;
			var ContractNo = value.ContractNo;
			var ConditionNo = value.ConditionNo;
			$('#condition-list tr').each(function(){
				var _this = $(this);
				if($("#condition-list tr").eq(_this.index()).attr('id')==CommodityNo+ContractNo+'-condition'){
					if($('#'+CommodityNo+ContractNo+'-condition-ConditionNo').text()==ConditionNo){
						_this.remove();
						tplFillData("condition-notrigger-list", "tplNotriggerConditionList", value, FillType.before);
					}
				}
			});
	}else{
	
		$('#condition-list').children().each(function(){
			var _this = $(this);
			if(_this.hasClass('clickBg')){
				$("#condition-list tr").eq(_this.index()).find('td').eq(0).text(value.CommodityNo+value.ContractNo);
				$("#condition-list tr").eq(_this.index()).find('td').eq(1).text(dealStatus(value.Status));
				$("#condition-list tr").eq(_this.index()).find('td').eq(2).text(dealConditionType(value.ConditionType));
				$("#condition-list tr").eq(_this.index()).find('td').eq(3).text(value.TimeTriggerPoint+' '+dealCompareType(value.CompareType,value.PriceTriggerPonit,value.AdditionFlag,value.AdditionType,value.AdditionPrice,value.CommodityNo));
				$("#condition-list tr").eq(_this.index()).find('td').eq(4).text(dealDrection(value.Drection)+','+dealOrderType0(value.OrderType)+','+value.Num+'手');
				$("#condition-list tr").eq(_this.index()).find('td').eq(5).text('当日有效');
				$("#condition-list tr").eq(_this.index()).find('td').eq(6).text(value.InsertDateTime);
				
				$("#condition-list tr").eq(_this.index()).find('td').eq(7).text(value.ConditionNo);
				$("#condition-list tr").eq(_this.index()).find('td').eq(8).text(value.Num);
				$("#condition-list tr").eq(_this.index()).find('td').eq(9).text(value.ConditionType);
				$("#condition-list tr").eq(_this.index()).find('td').eq(10).text(value.PriceTriggerPonit);
				$("#condition-list tr").eq(_this.index()).find('td').eq(11).text(value.CompareType);
				$("#condition-list tr").eq(_this.index()).find('td').eq(12).text(value.TimeTriggerPoint);
				$("#condition-list tr").eq(_this.index()).find('td').eq(13).text(value.AB_BuyPoint);
				$("#condition-list tr").eq(_this.index()).find('td').eq(14).text(value.AB_SellPoint);
				$("#condition-list tr").eq(_this.index()).find('td').eq(15).text(value.OrderType);
				$("#condition-list tr").eq(_this.index()).find('td').eq(16).text(value.StopLossType);
				$("#condition-list tr").eq(_this.index()).find('td').eq(17).text(value.Drection);
				$("#condition-list tr").eq(_this.index()).find('td').eq(18).text(value.StopLossDiff);
				$("#condition-list tr").eq(_this.index()).find('td').eq(19).text(value.AdditionFlag);
				$("#condition-list tr").eq(_this.index()).find('td').eq(20).text(value.AdditionType);
				$("#condition-list tr").eq(_this.index()).find('td').eq(21).text(value.AdditionPrice);
				$("#condition-list tr").eq(_this.index()).find('td').eq(22).text(value.Status);
				$("#condition-list tr").eq(_this.index()).find('td').eq(23).text(CacheQuoteSubscribe.getCacheContractQuote(value.CommodityNo + value.ContractNo, "LastQuotation", "LastPrice"));
				
				if(value.Status==0){
					$('#suspendConditionList').text('暂停');
				}
				
				if(value.Status==1){
					$('#suspendConditionList').text('启动');
				}
			}
		});
	}
}


/**
 * 条件单录入请求回复--信息处理
 */
function dealRspInsertCondition(value){
	
	if(value.Status==0){
		layer.msg('设置条件单成功!');
		tplFillData("condition-list", "tplConditionList", value, FillType.before);
	}else{
		layer.msg('设置条件单失败，原因:【'+value.StatusMsg+'】');
	}
	
}


/**
 * 条件单列表
 * @param {Object} parameters
 */
function initConditionList(parameters){
	
	if(parameters.Status<=1){
		tplFillData("condition-list", "tplConditionList", parameters, FillType.before);
	}
	
	if(parameters.Status>1){
		tplFillData("condition-notrigger-list", "tplNotriggerConditionList", parameters, FillType.before);
	}
	
}


/**
 * 止损单录入请求回复
 * @param {Object} parameters
 */
function dealInsertStopLoss(parameters){
	
	if(parameters.Status==0){
		layer.msg('提交成功,单号【'+ parameters.StopLossNo+'】');
	}else{
		layer.msg('提交失败,原因:【'+parameters.StatusMsg+'】');
	}
	
}

/**
 * 对止损单中的数据操作发起请求后，返回的通知
 * @param {Object} parameters
 */
function updateStopLoss(v){
	//在触发列表中新增一条记录
	if(v.Status>2){
		tplFillData("stopLoss-list00", "tplStopLossList00", v, FillType.before);
	}else if(v.Status==2){
		var CommodityNo = v.CommodityNo;
		var ContractNo = v.ContractNo;
		var StopLossNo = v.StopLossNo;
		$('#stopLoss-list tr').each(function(){
			var _this = $(this);
			if($("#stopLoss-list tr").eq(_this.index()).attr('id')==CommodityNo+ContractNo+'-stopLoss'){
				if($('#'+CommodityNo+ContractNo+'-stopLoss-StopLossNo').text()==StopLossNo){
					_this.remove();
					tplFillData("stopLoss-list00", "tplStopLossList00", v, FillType.before);
				}
			}
		});
	}else{
		$('#stopLoss-list').children().each(function(){
			var _this = $(this);
			if(_this.hasClass('clickBg')){
				
				$("#stopLoss-list tr").eq(_this.index()).find('td').eq(0).text(v.CommodityNo+v.ContractNo);
				$("#stopLoss-list tr").eq(_this.index()).find('td').eq(1).text(dealStatus(v.Status));
				$("#stopLoss-list tr").eq(_this.index()).find('td').eq(2).text(dealStatus(dealMoreOrNull(v.HoldDrection)));
				$("#stopLoss-list tr").eq(_this.index()).find('td').eq(3).text(dealStopLossType(v.StopLossType));
				$("#stopLoss-list tr").eq(_this.index()).find('td').eq(4).text(v.Num );
				$("#stopLoss-list tr").eq(_this.index()).find('td').eq(5).text(isZero(v.StopLossPrice,v.StopLossDiff)?'触发价:'+fixedPriceByContract(v.StopLossPrice,v.CommodityNo):'追踪价差:'+v.StopLossDiff);
				$("#stopLoss-list tr").eq(_this.index()).find('td').eq(6).text(dealOrderType(v.OrderType) );
				$("#stopLoss-list tr").eq(_this.index()).find('td').eq(7).text('当日有效');
				$("#stopLoss-list tr").eq(_this.index()).find('td').eq(8).text(v.InsertDateTime);
				
				
				$("#stopLoss-list tr").eq(_this.index()).find('td').eq(9).text(v.StopLossNo);
				$("#stopLoss-list tr").eq(_this.index()).find('td').eq(10).text(v.StopLossType);
				$("#stopLoss-list tr").eq(_this.index()).find('td').eq(11).text(v.StopLossPrice);
				$("#stopLoss-list tr").eq(_this.index()).find('td').eq(12).text(v.StopLossDiff);
				$("#stopLoss-list tr").eq(_this.index()).find('td').eq(13).text(v.OrderType);
				$("#stopLoss-list tr").eq(_this.index()).find('td').eq(14).text(v.CommodityNo);
				$("#stopLoss-list tr").eq(_this.index()).find('td').eq(15).text(v.HoldDrection);
				$("#stopLoss-list tr").eq(_this.index()).find('td').eq(16).text(v.HoldAvgPrice);
				$("#stopLoss-list tr").eq(_this.index()).find('td').eq(17).text(v.Status);
				
				//0:运行中
				if(v.Status==0){
					$('#zhanting').text('暂停');
				}
				//1:运行中
				if(v.Status==1){
					$('#zhanting').text('启动');
				}
			}
		});
		
	}
	
}


function layerMessageOnRtnOrderTraded(parameters){
	
	var CommodityName = CacheQuoteBase.getCacheContractAttribute(parameters.CommodityNo, "CommodityName");
	var DirectionStr;
	if(parameters.Drection==0){
		DirectionStr='买';
	}
	if(parameters.Drection==1){
		DirectionStr='卖';
	}
	
	var TradeNum = parameters.TradeNum;
	var TradeNo = parameters.TradeNo;
	var TradePrice = fixedPriceByContract(parameters.TradePrice,parameters.CommodityNo);
	layer.msg("成交（"+CommodityName+",价格:"+TradePrice+","+DirectionStr+TradeNum+"手）");
}


function layerMessageOnRspOrderInsert(parameters){
	
	var CommodityName = CacheQuoteBase.getCacheContractAttribute(parameters.CommodityNo, "CommodityName");
	var DirectionStr;
	if(parameters.Drection==0){
		DirectionStr='买';
	}
	if(parameters.Drection==1){
		DirectionStr='卖';
	}
	
	var price;
	if(parameters.OrderPriceType==1){
		price = '市价';
	}
	if(parameters.OrderPriceType==0){
		price = fixedPriceByContract(parameters.OrderPrice,parameters.CommodityNo);
	}
	
	var OrderNum = parameters.OrderNum;
	var OrderID = parameters.OrderID;
	
	if(parameters.OrderStatus<4){
		layer.msg("委托成功（"+CommodityName+","+price+","+DirectionStr+OrderNum+"手,委托号:"+OrderID+"）");
	}else{
		
		layer.msg("委托失败（"+CommodityName+","+price+","+DirectionStr+OrderNum+"手,失败原因:"+parameters.StatusMsg+"）");
	}
}

/**
 * 返回错误消息处理
 */
function doError(parameters){
	
	layer.msg(parameters.Message);
}
/**
 * 查询止损表
 */
function appendStopLoss(jQuoteList){
	if(jQuoteList.Status==0||jQuoteList.Status==1){
		
		tplFillData("stopLoss-list", "tplStopLossList", jQuoteList, FillType.before);
	}
	if(jQuoteList.Status==2||jQuoteList.Status==3||jQuoteList.Status==4||jQuoteList.Status==5){
		
		tplFillData("stopLoss-list00", "tplStopLossList00", jQuoteList, FillType.before);
	}
}

/**
 * 初始化交易数据，包括
 * 		持仓
 * 		挂单
 * 		委托
 * 		成交
 * 		资金
 */
function initTradeInfo() {
	// 查询持仓
	Trade.doSendMessage(TradeMethod.QryHoldUrl,'{"ClientNo":"'+TradeConfig.username+'"}');
	// 查询委托（委托包含挂单）
	Trade.doSendMessage(TradeMethod.QryOrderUrl, '{"ClientNo":"' + TradeConfig.username + '"}');
	// 查询成交
	Trade.doSendMessage(TradeMethod.QryTradeUrl, '{"ClientNo":"' + TradeConfig.username + '"}');
	// 查询资金
	Trade.doSendMessage(TradeMethod.QryAccountUrl, '{"ClientNo":"' + TradeConfig.username + '"}');
	// 查询历史成交
	qryHisTrade();
}

/**
 * 追加持仓
 * @param {HoldOrderVO} holdOrder
 */
function appendHold(holdOrder){
	tplFillData("holdList", "tplHoldList", holdOrder, FillType.after);
	for(var i=0;i<$(".hold-Drection").length;i++){
		if($(".hold-Drection").eq(i).text()=="多"){
			$(".hold-Drection").eq(i).css('color','red');
		}else{
			$(".hold-Drection").eq(i).css('color','forestgreen');
		}
	}
}
/**
 * 更新持仓
 * @param {HoldOrderVO} holdOrder
 */
function updateHold(holdOrder){
	var contract = holdOrder.CommodityNo + holdOrder.ContractNo;
	var $HoldOrder = document.getElementById(contract + "-hold");
	
	var holdNum = parseInt(holdOrder.HoldNum);
	if (holdNum == 0) {	// 如果持仓手数为0则删除
		if (!isEmpty($HoldOrder)) {
			var currencyNo = document.getElementById(contract + "-hold-CurrencyNo").innerHTML;
			CacheHoldFloatingProfit.setHoldFloatingProfit(contract, {"currencyNo" : currencyNo, "floatingProfit" : 0.0}); // 更新缓存持仓浮盈为0
			
			$HoldOrder.remove();
		}
		return;
	}
	
	if (isEmpty($HoldOrder)) {	// 不存在持仓：新增
		appendHold(holdOrder);
		initHoldFloatingProfit(holdOrder); // 根据订阅行情初始化持仓盈亏
	} else { //存在：修改持仓数量、方向、开仓均价、持仓均价
		document.getElementById(contract + "-hold-HoldNum").innerHTML = holdNum;
		document.getElementById(contract + "-hold-Drection").innerHTML = getDrectionName2(holdOrder.Drection);
		document.getElementById(contract + "-hold-OpenAvgPrice").innerHTML = fixedPriceByContract(holdOrder.OpenAvgPrice,holdOrder.CommodityNo);
		document.getElementById(contract + "-hold-HoldAvgPrice").innerHTML = holdOrder.HoldAvgPrice;
	}
	
	
}

/**
 * 追加订单
 * @param {OrderVO} orderInfo		订单信息
 */
function appendOrder(orderInfo) {
//	layer.msg(orderInfo.StatusMsg, {icon: 7});
	tplFillData("orderList", "tplOrderList", orderInfo, FillType.before);
}
/**
 * 更新订单记录
 * @param {OrderVO} orderInfo		订单信息
 */
function updateOrder(orderInfo) {
	// 需更新：状态、委托价、委托数量、成交均价、已成交手数、已撤单手数（TODO-暂未定字段）
	var $OrderStatus = document.getElementById(orderInfo.OrderRef + "-OrderList-OrderStatus");
	var $StatusMsg = document.getElementById(orderInfo.OrderRef + "-OrderList-StatusMsg");
	var $OrderPrice = document.getElementById(orderInfo.OrderRef + "-OrderList-OrderPrice");
	var $OrderNum = document.getElementById(orderInfo.OrderRef + "-OrderList-OrderNum");
	var $TradePrice = document.getElementById(orderInfo.OrderRef + "-OrderList-TradePrice");
	var $TradeNum = document.getElementById(orderInfo.OrderRef + "-OrderList-TradeNum");
	$OrderStatus.innerHTML = OrderType.getOrderTypeName(orderInfo.OrderStatus);
	$StatusMsg.innerHTML = orderInfo.StatusMsg;
	$OrderPrice.innerHTML = $OrderPrice.innerHTML == "市价" ? "市价" : fixedPriceByContract(orderInfo.OrderPrice,orderInfo.CommodityNo);
	$OrderNum.innerHTML = orderInfo.OrderNum;
	$TradePrice.innerHTML = orderInfo.TradePrice;
	$TradeNum.innerHTML = orderInfo.TradeNum;

	if(orderInfo.OrderStatus==4){
		$('#'+orderInfo.OrderRef+'-OrderList-RevokeNum').text(orderInfo.OrderNum-orderInfo.TradeNum);
	}else{
		$('#'+orderInfo.OrderRef+'-OrderList-RevokeNum').text('0');
	}
}

/**
 * 追加挂单信息
 * @param {OrderVO} orderInfo		订单信息
 */
function appendApply(orderInfo) {
	var orderStatus = orderInfo.OrderStatus;
	if( orderStatus < 3 ) { // 订单已提交、排队中、部分成交 显示到挂单列表
		tplFillData("applyOrderList", "tplApplyOrderList", orderInfo, FillType.before);
	}
}
/**
 * 更新挂单记录
 * @param {OrderVO} orderInfo		订单信息
 */
function updateApply(orderInfo) {
	// 2 排队中 状态，新增/更新挂单列表
	// 2.2 部分成交、完全成交、已撤单、下单失败、未知 状态，需处理挂单
	var orderStatus = orderInfo.OrderStatus;
	var $ApplyOrderList = document.getElementById(orderInfo.OrderRef + "-ApplyOrderList");
	if( orderStatus < 3 ) { // 订单已提交、排队中、部分成交 处理挂单列表
		if (isEmpty($ApplyOrderList)) { // 不存在挂单，新增
			tplFillData("applyOrderList", "tplApplyOrderList", orderInfo, FillType.before);
		} else {		// 存在，修改委托价、委托量、挂单量
			var $OrderPrice = document.getElementById(orderInfo.OrderRef + "-ApplyOrderList-OrderPrice");
			var $OrderNum = document.getElementById(orderInfo.OrderRef + "-ApplyOrderList-OrderNum");
			var $ApplyOrderNum = document.getElementById(orderInfo.OrderRef + "-ApplyOrderList-ApplyOrderNum");
			$OrderPrice.innerHTML = fixedPriceByContract(orderInfo.OrderPrice,orderInfo.CommodityNo);
			$OrderNum.innerHTML = orderInfo.OrderNum;
			$ApplyOrderNum.innerHTML = orderInfo.OrderNum - orderInfo.TradeNum;
		}
	} else { // 完全成交、已撤单、下单失败、未知  状态删除挂单记录
		if (!isEmpty($ApplyOrderList)) { // 存在，删除挂单
			$ApplyOrderList.remove();
		}
	}
}


/**
 * 追加成交单记录
 * @param {Object} tradeInfo		成交明细
 */
function appendTrade(tradeInfo) {
	tplFillData("tradeList", "tplTradeList", tradeInfo, FillType.before);
}
/**
 * 历史成交记录查询
 * @param {Object} param		历史成交记录
 */
var indexhis=1;
function appendHisTrade(param) {
	if(param == null && indexhis==1){
		$("#table_div").addClass("display_none");
		$("#notext_hisTradeList").removeClass("display_none");
	}
	if(param != null){
		$("#notext_hisTradeList").addClass("display_none");
		$("#table_div").removeClass("display_none");
		param.index=indexhis;
		tplFillData("hisTradeList", "tplHisTradeList", param, FillType.after);
		indexhis++;
	}
}

/**
 * 追加资金账户记录
 * @param {AccountVO} account		资金账户
 */
function appendAccount(account) {
	tplFillData("accountList", "tplAccountList", account, FillType.before); // 显示资金列表
	
	CacheAccount.initCacheAccount(account); // 设置/更新账户信息
}
/**
 * 更新资金账户信息
 * @param {MoneyVO} jMoneyVO		资金变化信息
 */
function updateAccount(jMoneyVO) {
	// 更新账户列表显示
	var currencyNo = jMoneyVO.CurrencyNo;
	// 出入金
	document.getElementById(currencyNo + "-accountList-Money").innerHTML = toFixedFloatNumber(jMoneyVO.InMoney - jMoneyVO.OutMoney, 2);
	// 手续费
	document.getElementById(currencyNo + "-accountList-CounterFee").innerHTML = toFixedFloatNumber(jMoneyVO.Fee, 2);
	// 平仓盈亏
	document.getElementById(currencyNo + "-accountList-CloseProfit").innerHTML = toFixedFloatNumber(jMoneyVO.CloseProfit, 2);
	// 平仓盈亏率
	document.getElementById(currencyNo + "-accountList-CloseProfitRate").innerHTML = toFixedFloatNumber(jMoneyVO.CloseProfit/jMoneyVO.TodayAmount, 2) + "%";
	// 当前结存
	document.getElementById(currencyNo + "-accountList-TodayAmount").innerHTML = toFixedFloatNumber(jMoneyVO.TodayAmount, 2);
	
	// 逐笔浮盈
	var floatingProfit = parseFloat(document.getElementById(currencyNo + "-accountList-FloatingProfit").innerHTML);
	// 当前权益 = 当前结存 + 逐笔浮盈
	var todayBalance = jMoneyVO.TodayAmount + floatingProfit;
	document.getElementById(currencyNo + "-accountList-TodayBalance").innerHTML = toFixedFloatNumber(todayBalance, 2);
	
	// 保证金
	document.getElementById(currencyNo + "-accountList-Deposit").innerHTML = toFixedFloatNumber(jMoneyVO.Deposit, 2);
	// 挂单保证金
	document.getElementById(currencyNo + "-accountList-FrozenMoney").innerHTML = toFixedFloatNumber(jMoneyVO.FrozenMoney, 2);
	// 可用资金 = 当前权益 - 保证金 - 冻结资金
	document.getElementById(currencyNo + "-accountList-TodayCanUse").innerHTML = toFixedFloatNumber(todayBalance - jMoneyVO.FrozenMoney - jMoneyVO.Deposit, 2);
	
	// 更新资金信息：静态金额
	CacheAccount.updateCacheAccount(jMoneyVO);
}

/**
 * 更新资金浮盈
 */
function updateAccountFloatingProfit() {
	// 遍历持仓浮盈，根据币种合并逐笔浮盈
	for(var contract in CacheHoldFloatingProfit.jHoldFloatingProfit) {
			var contractFloatingProfit = new ContractFloatingProfitVO(CacheHoldFloatingProfit.jHoldFloatingProfit[contract]);
	//		vsLog("累计浮盈：" + JSON.stringify(contractFloatingProfit));
			if (isEmpty(CacheHoldFloatingProfit.jCurrencyNoFloatingProfit[contractFloatingProfit.currencyNo])) { // 不存在币种
				// 新增币种
				CacheHoldFloatingProfit.jCurrencyNoFloatingProfit[contractFloatingProfit.currencyNo] = toFixedFloatNumber(contractFloatingProfit.floatingProfit, 2);
			} else { // 存在叠加
				var tmp1 = parseFloat(CacheHoldFloatingProfit.jCurrencyNoFloatingProfit[contractFloatingProfit.currencyNo]);
				var tmp2 = parseFloat(contractFloatingProfit.floatingProfit);
				CacheHoldFloatingProfit.jCurrencyNoFloatingProfit[contractFloatingProfit.currencyNo] =  toFixedFloatNumber(tmp1 + tmp2, 2);
			}
	}
	if(CacheHoldFloatingProfit.jCurrencyNoFloatingProfit.hasOwnProperty('0')){
		delete CacheHoldFloatingProfit.jCurrencyNoFloatingProfit['0'];
	}
	
	// 根据币种更新资金账户：逐笔浮盈、逐笔浮盈率、当前权益、可用资金
	for (var currencyNo in CacheHoldFloatingProfit.jCurrencyNoFloatingProfit) {
		var floatingProfit = parseFloat(CacheHoldFloatingProfit.jCurrencyNoFloatingProfit[currencyNo]);
		
		// 性能优化——币种浮盈无变化，不处理本次资金浮盈
		var oldFloatingProfit = document.getElementById(currencyNo + "-accountList-FloatingProfit").innerHTML;
		if (floatingProfit == oldFloatingProfit) {
			vsLog("性能优化——币种浮盈无变化，不处理本次资金浮盈【之前：" + oldFloatingProfit + ",币种：" + currencyNo + ",之后：" + floatingProfit + "】");
			break;
		}
		
		// 逐笔浮盈
		document.getElementById(currencyNo + "-accountList-FloatingProfit").innerHTML = floatingProfit;
		// 逐笔浮盈率 = 逐笔浮盈率 / 当前结存
		var todayAmount = parseFloat(document.getElementById(currencyNo + "-accountList-TodayAmount").innerHTML);
		document.getElementById(currencyNo + "-accountList-FloatingProfitRate").innerHTML = toFixedFloatNumber(floatingProfit / (todayAmount < 0 ? -todayAmount : todayAmount) * 100, 2) + "%";
		
		// 当前权益 = 当前结存 + 逐笔浮盈
		var todayBalance = todayAmount + floatingProfit;
		document.getElementById(currencyNo + "-accountList-TodayBalance").innerHTML = todayBalance;
		
		// 可用资金 = 当前权益 - 保证金 - 冻结资金
		var deposit = document.getElementById(currencyNo + "-accountList-Deposit").innerHTML; // 保证金
		var frozenMoney = document.getElementById(currencyNo + "-accountList-FrozenMoney").innerHTML; // 冻结资金
		var todayCanUse = todayBalance - deposit - frozenMoney;
		document.getElementById(currencyNo + "-accountList-TodayCanUse").innerHTML = todayCanUse;
		
		// 更新缓存的浮盈资金：逐笔浮盈、当前权益、可用资金
		CacheAccount.updateCacheAccountFloatingProfit(currencyNo, floatingProfit, todayBalance, todayCanUse);
	}
	// 清空币种盈亏
	CacheHoldFloatingProfit.jCurrencyNoFloatingProfit = {};
	
	// 更新账户盈亏
	CacheAccount.updateTotalAccount();
}

/**
 * 缓存账户信息
 */
var CacheAccount = {
	jCacheAccount : {},	// key 为CurrencyNo
	initCacheAccount : function(jAccount) { // 初始化账户信息
		var accountVO = new AccountVO(jAccount);
		// 以币种为key
		this.jCacheAccount[accountVO.CurrencyNo] = accountVO;
	},
	updateCacheAccount : function(jMoneyVO) { // 更新资金信息：静态金额
		var moneyVO = new MoneyVO(jMoneyVO);
		var currencyNo = moneyVO.CurrencyNo;
		var accountVO = new AccountVO(this.jCacheAccount[currencyNo]);
		// 入金
		accountVO.InMoney = moneyVO.InMoney;
		// 出金
		accountVO.OutMoney = moneyVO.OutMoney;
		// 手续费
		accountVO.CounterFee = moneyVO.Fee;
		// 平仓盈亏
		accountVO.CloseProfit = moneyVO.CloseProfit;
		// 今结存
		accountVO.TodayAmount = moneyVO.TodayAmount;
		// 保证金
		accountVO.Deposit = moneyVO.Deposit;
		// 挂单保证金
		accountVO.FrozenMoney = moneyVO.FrozenMoney;
		this.jCacheAccount[currencyNo] = accountVO;
	},
	updateCacheAccountFloatingProfit : function(currencyNo, floatingProfit, todayBalance, todayCanUse) { // 更新缓存的浮盈资金：逐笔浮盈、当前权益、可用资金
		var accountVO = new AccountVO(this.jCacheAccount[currencyNo]);
		// 逐笔浮盈
		accountVO.FloatingProfit = floatingProfit;
		// 当前权益
		accountVO.TodayBalance = todayBalance;
		// 可用资金
		accountVO.TodayCanUse = todayCanUse;
		this.jCacheAccount[currencyNo] = accountVO;
	},
	updateTotalAccount : function() { // 更新汇总账户资金信息
		if (isEmpty(this.jCacheAccount)) {
			return 0;
		}
		
		var jCacheTotalAccount = {
			TodayBalance : 0.0,	// 今权益
			TodayCanUse : 0.0,	// 今可用
			FloatingProfit : 0.0,	// 浮动盈亏
			CloseProfit : 0.0,	// 平仓盈亏
			FrozenMoney : 0.0,	// 冻结资金
			Deposit : 0.0,	// 保证金
			CounterFee : 0.0,	// 手续费
			RiskRate : 0.0	// 风险率
		};
		for(var currencyNo in this.jCacheAccount) {
			var accountVO = new AccountVO(this.jCacheAccount[currencyNo]);
			// 逐笔浮盈：直接从当前资金账户获取
			var floatingProfit = accountVO.FloatingProfit;
			jCacheTotalAccount.FloatingProfit += floatingProfit * accountVO.CurrencyRate;
			
			// 今权益 = 今结存 + 浮盈
			var tmpTodayBalance = accountVO.TodayAmount + floatingProfit;
			jCacheTotalAccount.TodayBalance += tmpTodayBalance * accountVO.CurrencyRate;
			
			// 今可用=今权益-冻结资金-保证金
			jCacheTotalAccount.TodayCanUse += (tmpTodayBalance - accountVO.FrozenMoney - accountVO.Deposit) * accountVO.CurrencyRate;
			// 平仓盈亏
			jCacheTotalAccount.CloseProfit += accountVO.CloseProfit * accountVO.CurrencyRate;
			// 冻结资金
			jCacheTotalAccount.FrozenMoney += accountVO.FrozenMoney * accountVO.CurrencyRate;
			// 保证金
			jCacheTotalAccount.Deposit += accountVO.Deposit * accountVO.CurrencyRate;
			// 手续费
			vsLog("手续费：" + accountVO.CounterFee);
			jCacheTotalAccount.CounterFee += accountVO.CounterFee * accountVO.CurrencyRate;
		}
		// 风险率 = 保证金 / 今权益 * 100%
		jCacheTotalAccount.RiskRate = jCacheTotalAccount.Deposit / jCacheTotalAccount.TodayBalance / 100;
		vsLog("jCacheTotalAccount.RiskRate=========" + jCacheTotalAccount.RiskRate);
		
		tplFillData("accountInfo", "tplAccountInfo", jCacheTotalAccount, FillType.repalce); // 更新资金汇总信息
	}
};


/**
 * 根据订阅行情初始化持仓盈亏
 * 
 * @param {HoldOrderVO} holdOrder
 */
function initHoldFloatingProfit(holdOrder) {
	var contract = holdOrder.CommodityNo + holdOrder.ContractNo;
	updateHoldFloatingProfit(contract, CacheQuoteSubscribe.getCacheContractQuote(contract, "LastQuotation", "LastPrice"));
}

/**
 * 更新持仓浮动盈亏
 * 
 * @param {Object} contract	合约
 * @param {Object} lastPrice		最新价
 */
function updateHoldFloatingProfit(contract, lastPrice) {
	var $commodityNo = document.getElementById(contract + "-hold-CommodityNo");
	if (isEmpty($commodityNo)) { //  不存在该合约的持仓则不更新
		return;
	}
	var commodityNo = $commodityNo.value;
	var drection = document.getElementById(contract + "-hold-Drection").innerHTML;
	var OpenAvgPrice = parseFloat(document.getElementById(contract + "-hold-OpenAvgPrice").innerHTML);
	var HoldNum = document.getElementById(contract + "-hold-HoldNum").innerHTML;
	
	// 价差 = 最新价-开仓价
	var diff = lastPrice - OpenAvgPrice;
	// 合约乘数 = 最小变动价格 / 最小变动点数
	var mult = CacheQuoteBase.getCacheContractAttribute(commodityNo, "ContractSize")/CacheQuoteBase.getCacheContractAttribute(commodityNo, "MiniTikeSize");
	// 浮动盈亏 = (价差/最小变动) * (合约乘数 * 最小变动) * 手数 = 价差 * 合约乘数(最小变动价格 / 最小变动点数) * 手数
	var tmpFloatingProfit = parseFloat(diff * mult * HoldNum).toFixed(2);
	var drection0;
	if(drection=='空'){
		drection0 = 1;
	}
	
	if(drection=='多'){
		drection0 = 0;
	}
	
	if(drection0 === 1) { // 空反向
		tmpFloatingProfit = -tmpFloatingProfit;
	}
	
	// 性能优化——持仓浮盈无变化，不处理本次持仓浮盈
	if (tmpFloatingProfit == CacheHoldFloatingProfit.getHoldFloatingProfitByContract(contract)) {
		vsLog("性能优化——持仓浮盈无变化，不处理本次持仓浮盈");
		return;
	}
	
	var $FloatingProfit = document.getElementById(contract + "-hold-FloatingProfit");
	$FloatingProfit.innerHTML = tmpFloatingProfit + ":" + CacheQuoteBase.getCacheContractAttribute(commodityNo, "CurrencyNo");
	if (tmpFloatingProfit >= 0) { // 盈利
		$FloatingProfit.style.color = "red";
	} else {		// 亏损
		$FloatingProfit.style.color = "forestgreen";
	}
	document.getElementById(contract + "-hold-CurrencyNo").innerHTML=CacheQuoteBase.getCacheContractAttribute(commodityNo, "CurrencyNo");
	
	
	// 缓存合约浮盈。以合约为key，value包含：币种，最新浮盈
	var currencyNo = document.getElementById(contract + "-hold-CurrencyNo").innerText;
	CacheHoldFloatingProfit.setHoldFloatingProfit(contract, {"currencyNo" : currencyNo, "floatingProfit" : tmpFloatingProfit});
}

var CacheHoldFloatingProfit = {
	jHoldFloatingProfit : {},	// 持仓合约对应浮盈
	setHoldFloatingProfit : function(contract, jContractFloatingProfitVO) {
		this.jHoldFloatingProfit[contract] = jContractFloatingProfitVO;
		
		// 账户信息准备完毕后，更新资金盈亏
		if (ifUpdateAccountProfit) {
			updateAccountFloatingProfit();
		} else { // 没准备好之前，等待100毫秒再更新
			setTimeout(function(){
				updateAccountFloatingProfit();
			}, 100);
		}
	},
	getHoldFloatingProfitByContract: function(contract) {	// 根据合约获取浮盈
		if (isEmpty(this.jHoldFloatingProfit[contract])) {
			return "";
		}
		return toFixedFloatNumber(this.jHoldFloatingProfit[contract].floatingProfit, 2);
	},
	
	jCurrencyNoFloatingProfit : {}	// 币种对应浮盈
};

/**
 * 下单
 * 		下单后未成交返回方法：OnRspOrderInsert；OnRtnOrderState；OnRtnMoney；
 * 		下单成交后返回方法：OnRspOrderInsert；OnRtnOrderState；OnRtnMoney；OnRtnOrderTraded；OnRtnHoldTotal
 * @param drection 买卖方向（0：买，1：卖）
 */
function orderInsert(order, drection) {
	if(isEmpty(order.contract)) {
		layer.msg("请选择交易合约");
		return false;
	}
	layer.confirm(
		"确认提交订单:合约【" + order.contract + "】,价格:【" + (order.priceType == 1 ? "市价" : order.limitPrice) + "】,手数:【" + order.orderNum + "】,买卖方向:【" + getDrectionName(drection) + "】", 
		{
			icon: 3,
			title: '确认下单？'
		},
		function(index) { // 确认
			Trade.doInsertOrder(order.contract, order.orderNum, drection, order.priceType, order.limitPrice, 0, buildOrderRef());
			layer.close(index);
		},
		function(index) { // 取消
			layer.close(index);
		}
	);
}







