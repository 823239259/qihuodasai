// 交易配置
var TradeConfig = {
	version : "3.3",	// 版本
	url_real : "ws://192.168.0.213:6102", // 实盘地址
//	url_real : "ws://192.168.0.147:7001",
	model : "1", // 实盘：0；	模拟盘：1
	client_source : "N_WEB",	// 客户端渠道
	username : "000030",		// 账号(新模拟盘——000008、直达实盘——000140、易盛模拟盘——Q517029969)
	password : "YTEyMzQ1Ng==" 	// 密码：base64密文(明文：a123456——YTEyMzQ1Ng==     888888——ODg4ODg4	 74552102——NzQ1NTIxMDI=		123456=MTIzNDU2)
};
/*
mui.app_request('/user/getTradeAccount', {}, function(result) {
			if(result.success == true) {
				TradeConfig.username = result.data.tradeProfessionalAccount;
				TradeConfig.password = result.data.tradeProfessionalPwd;
			}
		}, function(res) {});*/
var tradeSocket = null;

// 是否更新持仓盈亏：连接上交易，且接收完持仓信息再开始根据最新行情更新持仓盈亏
var ifUpdateHoldProfit = false;
// 是否更新账户浮动盈亏：连接上交易，且接收完账户资金信息再开始根据行情更新
var ifUpdateAccountProfit = false;

// 心跳检查，超过指定时间则刷新重连
var HeartBeat = {
	lastHeartBeatTimestamp : 1,	// 最后心跳时间
	oldHeartBeatTimestamp : 0,	// 上一次心跳时间
	intervalCheckTime : 8000000,   // 间隔检查时间：8秒
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
function handleMessage(evt){
	
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
		}break;
		
		case "OnRspLogout":{		// 登出回复
			var code = parameters.Code;
			var loginMessage = parameters.Message;
			if(code == 1) {
				layer.msg(loginMessage, {
					icon: 5
				});
			}
		}	break;
		
		case "OnRspQryHoldTotal":{
			// 持仓返回结束更新持仓盈亏
			if (isEmpty(parameters)) {
				ifUpdateHoldProfit = true; // 可以使用最新行情更新持仓盈亏
			} else {
//				appendHold(parameters);
//				initHoldFloatingProfit(parameters); // 根据订阅行情初始化持仓盈亏
			}
		}break;
		
		case "OnRspQryOrderGW":{//查询订单回复
			
			appendOrder(parameters); // 订单列表
			
		}break;
		case "OnRtnOrderStateChgGW":{//开仓请求订单变化通知
			
			console.log('开仓请求订单变化通知'+JSON.stringify(parameters));
			if(parameters.Status==1){//开仓成功
				mui.toast('开仓成功:'+parameters.StatusMsg);
				appendOrderAfter(parameters);//订单列表追加
			}else if(parameters.Status==3){//平仓成功
				mui.toast('平仓成功:'+parameters.StatusMsg);
				
			}else if(parameters.Status==4){//平仓失败
				mui.toast('平仓失败:'+parameters.StatusMsg);
				$('#'+parameters.OrderID+'-PositionListOrder').remove();
				tplFillData("settlementSheet00", "plSettlementSheet", parameters, FillType.before);
				
			}else if(parameters.Status==2){//开仓失败
				mui.toast('开仓失败:'+parameters.StatusMsg);
			}
			
		}break;
		case "OnRspOpenOrderGW":{
//			layer.msg(parameters.StatusMsg);
		}break;
		
	}
}

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
function updateHoldFloatingProfit(contract, lastPrice){
	
}

/**
 * 初始化交易信息
 */
function initTradeInfo(){
	
	// 查询订单
	Trade.doQryOrderGW(TradeConfig.username);
}

/**
 * 查询订单回复后
 */
function appendOrder(orderInfo){
	//订单列表
	if(orderInfo.Status==1){
		tplFillData("positionListOrder", "tplPositionListOrder", orderInfo, FillType.before);
	}else{
		//结算单列表
		tplFillData("settlementSheet00", "plSettlementSheet", orderInfo, FillType.before);
	}
	var len = $("#positionListOrder > li").length;
	if(len == 0){
		$("#noOrder").show();
		$("#allClosePosition").hide();
	}else{
		$("#noOrder").hide();
		$("#allClosePosition").show();
	}
	
	//平仓
	$('.closePositionClass').on('tap',function(){
		
		var _this = $(this);
//		mui.confirm('',function(e){
//			
//		});
		var OrderID =  _this.parent().parent().find('.li-none').text();
		var ClientNo = TradeConfig.username;
		var PlatForm_User = phone;
		Trade.doCloseOrderGW(ClientNo,PlatForm_User,OrderID);
		_this.remove();
	});
	//反手 先平仓再相反的方向买
	$('.backhandClass').on('tap',function(){
		
		var _this = $(this);
		var OrderID =  _this.parent().parent().find('.li-none').text();
		var ClientNo = TradeConfig.username;
		var PlatForm_User = phone;
		Trade.doCloseOrderGW(ClientNo,PlatForm_User,OrderID);
		
		//反方向买
		var ClientNo = TradeConfig.username;
		var PlatForm_User = phone;
		var ProductID = '';
		var CommodityNo = _this.parent().parent().find('.li-CommodityNo').text();
		var ContractNo = _this.parent().parent().find('.li-ContractNo').text();
		var OrderNum = 	_this.parent().parent().find('.li-OrderNum').text();
		var Deposit = _this.parent().parent().find('.li-Deposit').text();//滑点保证金
		var StopWin = _this.parent().parent().find('.li-StopWin').text(); //触发止盈
		var StopLoss = _this.parent().parent().find('.li-StopLoss').text();//触发止损
		var Fee = _this.parent().parent().find('.li-Fee').text();
		//0:买入 1：卖出
		var Open_Direction = _this.parent().parent().find('.li-Open_Direction').text();
		
		if(Open_Direction==0){
				var Direction = 1;
				Trade.doOpenOrderGW(ClientNo,PlatForm_User,ProductID,CommodityNo,
				ContractNo,OrderNum,Direction,StopWin,StopLoss,Deposit,Fee);
		}
		
		if(Open_Direction==1){
				var Direction = 0;
				Trade.doOpenOrderGW(ClientNo,PlatForm_User,ProductID,CommodityNo,
				ContractNo,OrderNum,Direction,StopWin,StopLoss,Deposit,Fee);
		}
		_this.remove();
	});
	
	
	
}
/**
 * //订单列表追加记录
 * @param {Object} orderInfo
 */
function appendOrderAfter(orderInfo){
	console.log(JSON.stringify(orderInfo));
	tplFillData("positionListOrder", "tplPositionListOrder", orderInfo, FillType.before);
//	tplFillData("settlementSheet00", "plSettlementSheet", orderInfo, FillType.before);
}

function layerMessage(parameters){
	
	switch(parameters.Status){
		case 1:
		 
		  break;
		case 9:
			layer.msg('订单不存在');
		  	break;
		default:
	  
	}
}
