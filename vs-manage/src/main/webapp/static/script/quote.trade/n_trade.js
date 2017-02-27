var tradeSocket = null;
var trade_url = "ws://192.168.0.213:7001";
var trade_model = "1"; // 实盘：0；模拟盘：1
var trade_username = null;
var trade_password = null; 	// base64密文(明文：a123456——YTEyMzQ1Ng==     888888——ODg4ODg4)
var trade_version = "3.2";
var trade_source = "vs_system";


/**
 * 连接交易服务器
 */
function connectTradeServer() {
	tradeSocket = new WebSocket(trade_url);
}

/**
 * 为账号，密码赋值
 */

function evaluation(tranAccount,tranPassword){
	trade_username = tranAccount;
	trade_password = tranPassword;
	console.log("trade_username: "+trade_username);
	console.log("trade_password: "+trade_password);
}

/**
 * 交易初始化加载
 */
function initTradeClient(tranAccount,tranPassword) {
	
	if(tradeSocket == null) {
		connectTradeServer();
	}

	tradeSocket.onopen = function() {
		Trade.doLogin(trade_username, trade_password, trade_model, trade_version, trade_source);
	}

	tradeSocket.onmessage = function(evt) {
		handleMessage(evt);
	}

	tradeSocket.onclose = function(evt) {
		console.log("onclose【" + evt + "】");
	}

	tradeSocket.onerror = function(evt) {
		console.log("onerror【" + evt.data + "】");
	}
}

/**
 * 交易接口方法列表
 */
var TradeMethod = {
	/**
	 * 登录url
	 */
	LoginUrl: "Login",
	/**
	 * 登出url
	 */
	LoginOut: "Logout",
	/**
	 * 获取个人账户信息url
	 */
	QryAccountUrl: "QryAccount",
	/**
	 * 获取订单信息url
	 */
	QryOrderUrl: "QryOrder",
	/**
	 * 获取成交记录url
	 */
	QryTradeUrl: "QryTrade",
	/**
	 * 获取持仓信息url
	 */
	QryHoldUrl: "QryHold",
	/**
	 * 报单录入url
	 */
	InsertOrderUrl: "InsertOrder",
	/**
	 * 报单撤销url
	 */
	CancelOrderUrl: "CancelOrder",
	/**
	 * 改单url
	 */
	ModifyOrderUrl: "ModifyOrder",
	/**
	 * 错误通知
	 */
	OnError: "OnError",
	/**
	 * 止损单录入url
	 */
	InsertStopLoss: "InsertStopLoss",
	/**
	 * 获取止损单url
	 */
	QryStopLoss: "QryStopLoss",
	/**
	 * 止损单修改
	 */
	ModifyStopLoss: "ModifyStopLoss",
	/**
	 * 条件单录入请求url
	 */
	InsertCondition: "InsertCondition",
	/**
	 * 条件单修改请求url
	 */
	ModifyCondition: "ModifyCondition",
	/**
	 * 条件单查询url
	 */
	QryCondition: "QryCondition"
}

/**
 * 交易接口调用工具
 */
var Trade = {
	/**
	 * 登录
	 * @param {Object} username 用户账户
	 * @param {Object} password 密码
	 */
	doLogin: function(username, password, isMock, version, source) {
		Trade.doSendMessage(TradeMethod.LoginUrl, '{"ClientNo":"' + username + '","PassWord":"' + password + '","IsMock":' + isMock + ',"Version":"' + version + '","Source":"' + source + '"}');
	},
	/**
	 * 登出
	 * @param {Object} username
	 */
	doLoginOut: function(username) {
		Trade.doSendMessage(TradeMethod.LoginOut, '{"ClientNo":"' + username + '"}');
	},
	/**
	 * 获取个人账户信息
	 * @param {Object} username 用户账户
	 */
	doAccount: function(username) {
		Trade.doSendMessage(TradeMethod.QryAccountUrl, '{"ClientNo":"' + username + '"}');
	},
	/**
	 * 获取订单信息
	 * @param {Object} username 用户账户
	 */
	doOrder: function(username) {
		Trade.doSendMessage(TradeMethod.QryOrderUrl, '{"ClientNo":"' + username + '"}');
	},
	/**
	 * 查询成交记录信息
	 * @param {Object} username 用户账户
	 */
	doTrade: function(username) {
		Trade.doSendMessage(TradeMethod.QryTradeUrl, '{"ClientNo":"' + username + '"}')
	},
	/**
	 * 查询持仓信息
	 * @param {Object} username 用户账户
	 */
	doHold: function(username) {
		Trade.doSendMessage(TradeMethod.QryHoldUrl, '{"ClientNo":"' + username + '"}');
	},
	/**
	 * 报单录入请求
	 * @param {Object} contract 合约编码
	 * 					 exchangeNo 交易所代码（由contract查询）
	 * 					 commodeityNo 品种代码（由contract查询）
	 * 					 contractNo 合约代码（由contract查询）
	 * @param {Object} orderNum 订单数量
	 * @param {Object} drection 买卖方向（0：买，1：卖）
	 * @param {Object} priceType 价格类型:限价0，市价1，止损2
	 * @param {Object} limitPrice 订单价格
	 * @param {Object} triggerPrice 触发价(止损单需要)
	 * @param {Object} orderRef 报单引用，本地客户端生成
	 */
	doInsertOrder: function(contract, orderNum, drection, priceType, limitPrice, triggerPrice, orderRef) {
		var param = '{"ExchangeNo":"' + jCacheContractAttribute[contract].ExchangeNo + '",' +
			' "CommodityNo":"' + jCacheContractAttribute[contract].CommodityNo + '",' +
			' "ContractNo":"' + jCacheContractAttribute[contract].MainContract + '",' +
			' "OrderNum":' + orderNum + ',' +
			' "Drection":' + drection + ',' +
			' "PriceType":' + priceType + ',' +
			' "LimitPrice":' + limitPrice + ',' +
			' "TriggerPrice":' + triggerPrice + ',' +
			' "OrderRef":"' + orderRef + '"}';
		Trade.doSendMessage(TradeMethod.InsertOrderUrl, param);
	},
	/**
	 * 撤单请求
	 * @param {Object} orderSysId 系统编号
	 * @param {Object} orderId 订单号
	 * @param {Object} exchangeNo 交易所代码
	 * @param {Object} commodityNo 品种代码
	 * @param {Object} contractNo 合约代码
	 * @param {Object} orderNum 订单数量
	 * @param {Object} drection 买卖方向（0：买，1：卖）
	 * @param {Object} orderPrice 订单价格
	 */
	doCancelOrder: function(orderSysId, orderId, exchangeNo, commodityNo, contractNo, orderNum, drection, orderPrice) {
		var param = '{"OrderSysID":"' + orderSysId + '",' +
			' "OrderID":"' + orderId + '",' +
			' "ExchangeNo":"' + exchangeNo + '",' +
			' "CommodityNo":"' + commodityNo + '",' +
			' "ContractNo":"' + contractNo + '",' +
			' "OrderNum":' + orderNum + ',' +
			' "Drection":' + drection + ',' +
			' "OrderPrice":' + orderPrice + '}';
		Trade.doSendMessage(TradeMethod.CancelOrderUrl, param);
	},
	/**
	 * 改单请求
	 * @param {Object} orderSysId 系统编号
	 * @param {Object} orderId 订单号
	 * @param {Object} exchangeNo 交易所代码
	 * @param {Object} commodityNo 品种代码
	 * @param {Object} contractNo 合约代码
	 * @param {Object} orderNum 订单数量
	 * @param {Object} drection 买卖方向（0：买，1：卖）
	 * @param {Object} orderPrice 订单价格
	 * @param {Object} triggerPrice 触发价格
	 */
	doModifyOrder: function(orderSysId, orderId, exchangeNo, commodityNo, contractNo, orderNum, drection, orderPrice, triggerPrice) {
		var param = '{"OrderSysID":"' + orderSysId + '",' +
			' "OrderID":"' + orderId + '",' +
			' "ExchangeNo":"' + exchangeNo + '",' +
			' "CommodityNo":"' + commodityNo + '",' +
			' "ContractNo":"' + contractNo + '",' +
			' "OrderNum":' + orderNum + ',' +
			' "Drection":' + drection + ',' +
			' "OrderPrice":' + orderPrice + ',' +
			' "TriggerPrice":' + triggerPrice + '}';
		Trade.doSendMessage(TradeMethod.ModifyOrderUrl, param);
	},
	/**
	 * 增加止损单
	 * @param {Object} exchangeNo
	 * @param {Object} commodityNo
	 * @param {Object} contractNo
	 * @param {Object} num
	 * @param {Object} stopLossType
	 * @param {Object} stopLossDiff
	 * @param {Object} holdAvgPrice
	 * @param {Object} holdDrection
	 * @param {Object} orderType
	 * @param {Object} StopLossPrice
	 */
	doInsertStopLoss: function(exchangeNo, commodityNo, contractNo, num, stopLossType, stopLossDiff, holdAvgPrice, holdDrection, orderType, stopLossPrice) {
		var param = '{"ExchangeNo":"' + exchangeNo + '",' +
			' "CommodityNo":"' + commodityNo + '",' +
			' "ContractNo":"' + contractNo + '",' +
			' "Num":' + num + ',' +
			' "StopLossType":' + stopLossType + ',' +
			' "StopLossDiff":' + stopLossDiff + ',' +
			' "HoldAvgPrice":' + holdAvgPrice + ',' +
			' "HoldDrection":' + holdDrection + ',' +
			' "OrderType":' + orderType + ',' +
			' "StopLossPrice":' + stopLossPrice + '}';
		Trade.doSendMessage(TradeMethod.InsertStopLoss, param);
	},
	/**
	 * 获取止损单请求
	 * @param {Object} username
	 */
	doQryStopLoss: function(username) {
		Trade.doSendMessage(TradeMethod.QryStopLoss, '{"ClientNo":"' + username + '"}');
	},
	/**
	 * 修改止损单
	 * @param {Object} stopLossNo
	 * @param {Object} modifyFlag
	 * @param {Object} num
	 * @param {Object} stopLossType
	 * @param {Object} orderType
	 * @param {Object} stopLossDiff
	 * @param {Object} stopLossPrice
	 */
	doModifyStopLoss: function(stopLossNo, modifyFlag, num, stopLossType, orderType, stopLossDiff, stopLossPrice) {
		var param = '{"StopLossNo":"' + stopLossNo + '",' +
			' "ModifyFlag":' + modifyFlag + ',' +
			' "Num":' + num + ',' +
			' "StopLossType":' + stopLossType + ',' +
			' "StopLossDiff":' + stopLossDiff + ',' +
			' "StopLossPrice":' + stopLossPrice + ',' +
			' "OrderType":' + orderType + '}';
		Trade.doSendMessage(TradeMethod.ModifyStopLoss, param);
	},
	/**
	 * 条件单录入请求
	 * @param {Object} exchangeNo
	 * @param {Object} commodityNo
	 * @param {Object} contractNo
	 * @param {Object} num
	 * @param {Object} conditionType
	 * @param {Object} priceTriggerPonit
	 * @param {Object} compareType
	 * @param {Object} timeTriggerPoint
	 * @param {Object} abBuyPoint
	 * @param {Object} abSellPoint
	 * @param {Object} orderType
	 * @param {Object} drection
	 * @param {Object} stopLossType
	 * @param {Object} stopLossDiff
	 * @param {Object} stopWinDiff
	 * @param {Object} additionFlag
	 * @param {Object} additionType
	 * @param {Object} additionPrice
	 */
	doInsertCondition: function(exchangeNo,
		commodityNo,
		contractNo,
		num,
		conditionType,
		priceTriggerPonit,
		compareType,
		timeTriggerPoint,
		abBuyPoint,
		abSellPoint,
		orderType,
		drection,
		stopLossType,
		stopLossDiff,
		stopWinDiff,
		additionFlag,
		additionType,
		additionPrice) {
		var param = '{"ExchangeNo":"' + exchangeNo + '",' +
			' "CommodityNo":"' + commodityNo + '",' +
			' "ContractNo":"' + contractNo + '",' +
			' "Num":' + num + ',' +
			' "ConditionType":' + conditionType + ',' +
			' "PriceTriggerPonit":' + priceTriggerPonit + ',' +
			' "CompareType":' + compareType + ',' +
			' "TimeTriggerPoint":"' + timeTriggerPoint + '",' +
			' "AB_BuyPoint":' + abBuyPoint + ',' +
			' "AB_SellPoint":' + abSellPoint + ',' +
			' "OrderType":' + orderType + ',' +
			' "Drection":' + drection + ',' +
			' "StopLossType":' + stopLossType + ',' +
			' "StopLossDiff":' + stopLossDiff + ',' +
			' "StopWinDiff":' + stopWinDiff + ',' +
			' "AdditionFlag":' + additionFlag + ',' +
			' "AdditionType":' + additionType + ',' +
			' "AdditionPrice":' + additionPrice + '}';
		Trade.doSendMessage(TradeMethod.InsertCondition, param);
	},
	/**
	 * 修改条件单请求
	 * @param {Object} conditionNo
	 * @param {Object} modifyFlag
	 * @param {Object} num
	 * @param {Object} conditionType
	 * @param {Object} priceTriggerPonit
	 * @param {Object} compareType
	 * @param {Object} timeTriggerPoint
	 * @param {Object} abBuyPoint
	 * @param {Object} abSellPoint
	 * @param {Object} orderType
	 * @param {Object} drection
	 * @param {Object} stopLossType
	 * @param {Object} stopLossDiff
	 * @param {Object} stopWinDiff
	 * @param {Object} additionFlag
	 * @param {Object} additionType
	 * @param {Object} additionPrice
	 */
	doUpdateModifyCondition: function(conditionNo,
		modifyFlag,
		num,
		conditionType,
		priceTriggerPonit,
		compareType,
		timeTriggerPoint,
		abBuyPoint,
		abSellPoint,
		orderType,
		drection,
		stopLossType,
		stopLossDiff,
		stopWinDiff,
		additionFlag,
		additionType,
		additionPrice) {
		var param = '{"ConditionNo":"' + conditionNo + '",' +
			' "ModifyFlag":' + modifyFlag + ',' +
			' "Num":' + num + ',' +
			' "ConditionType":' + conditionType + ',' +
			' "PriceTriggerPonit":' + priceTriggerPonit + ',' +
			' "CompareType":' + compareType + ',' +
			' "TimeTriggerPoint":"' + timeTriggerPoint + '",' +
			' "AB_BuyPoint":' + abBuyPoint + ',' +
			' "AB_SellPoint":' + abSellPoint + ',' +
			' "OrderType":' + orderType + ',' +
			' "Drection":' + drection + ',' +
			' "StopLossType":' + stopLossType + ',' +
			' "StopLossDiff":' + stopLossDiff + ',' +
			' "StopWinDiff":' + stopWinDiff + ',' +
			' "AdditionFlag":' + additionFlag + ',' +
			' "AdditionType":' + additionType + ',' +
			' "AdditionPrice":' + additionPrice + '}';
		Trade.doSendMessage(TradeMethod.ModifyCondition, param);
	},
	/**
	 * 查询条件单请求
	 * @param {Object} username
	 */
	doQryCondition: function(username) {
		Trade.doSendMessage(TradeMethod.QryCondition, '{"ClientNo":"' + username + '"}');
	},
	/**
	 * 发送交易请求
	 * @param {Object} method
	 * @param {Object} parameters
	 */
	doSendMessage: function(method, parameters) {
		
		tradeSocket.send('{"Method":"' + method + '","Parameters":' + parameters + '}');
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
	if (method != "OnRspQryHold" &&		// 持仓返回结束需要做合并处理
	    method != "OnRspQryAccount" && method != "OnRspQryTrade") { 		// 资金返回结束需要做合并处理
		if (parameters == null || typeof(parameters) == "undefined") {
			vsLog("返回交易参数不做处理【" + dataString + "】");
			return;
		}
	}
	    
    switch (method){
    	case "OnRspLogin":{	// 登录回复
			var code = parameters.Code;
			var loginMessage = parameters.Message;
			//登录成功加载
			if(code == 0) {
				// 查询持仓
				Trade.doSendMessage(TradeMethod.QryHoldUrl,'{"ClientNo":"'+trade_username+'"}');
			} else {
				Check.messageBox("提示","结算的账户号或者密码不正确");
				return;
			}
    	}	
    	break;
    	case "OnRspLogout":{		// 登出回复
			var code = parameters.Code;
			var loginMessage = parameters.Message;
			if(code == 0) {
				//退出成功
			}else{
				Check.messageBox("提示","登录交易系统没有正常退出");
				return;
			}
		}	
    	break;
    			
    	case "OnRspQryHold":{	// 查询持仓信息回复  有持仓 则不能结算
			
			// 第一条数据返回
			if (parameters == null || typeof(parameters) == "undefined") {//没有持仓
				// 查询委托（委托包含挂单）
				Trade.doOrder(trade_username);
				// 查询条件单
				Trade.doQryCondition(trade_username);
				//查询订单成交记录
				Trade.doTrade(trade_username); 
				
			} else {//有持仓
				Check.messageBox("提示","有持仓，不能结算");
				Trade.doLoginOut(tranAccount,"");
				return;
			}
    	}	
    	break;
    	
    	case "OnRspQryOrder":{	// 查询订单信息回复
			
			var orderStatus = parameters.OrderStatus;
			if(orderStatus < 3) { // 未成交信息全部撤单
				var orderSysId = parameters.OrderSysID;
				var order = parameters.OrderID;
				var exchangeNo = parameters.ExchangeNo;
				var commodityNo = parameters.CommodityNo;
				var contractNo = parameters.ContractNo;
				var orderNum = parameters.OrderNum;
				var drection = parameters.Drection;
				var orderPrice = parameters.OrderPrice;
				// 撤单请求
				Trade.doCancelOrder(orderSysId, orderId, exchangeNo, commodityNo, contractNo, orderNum, drection, orderPrice);
			}			
		}	
    	break;

    	case "OnRspQryCondition":{//条件单查询回复   
    					
			var status = parameters.Status;
			if(status < 2) { // 未触发条件单全部撤销
				var conditionNo = parameters.ConditionNo;
				var num = parameters.Num;
				var conditionType = parameters.ConditionType;
				var priceTriggerPonit = parameters.PriceTriggerPonit;
				var compareType = parameters.CompareType;
				var timeTriggerPoint = parameters.TimeTriggerPoint;
				var abBuyPoint = parameters.AB_BuyPoint;
				var abSellPoint = parameters.AB_SellPoint;
				var orderType = parameters.OrderType;
				var stopLossType = parameters.StopLossType;
				var drection = parameters.Drection;
				var stopLossDiff = parameters.StopLossDiff;
				var stopWinDiff = parameters.StopWinDiff;
				var additionFlag = parameters.AdditionFlag;
				var additionType = parameters.AdditionType;
				var additionPrice = parameters.AdditionPrice;
				// 条件单修改请求（删除）
				var modifyFlag = 1
				Trade.ModifyCondition(conditionNo,modifyFlag,num,conditionType,priceTriggerPonit,compareType,timeTriggerPoint,abBuyPoint,abSellPoint,orderType,
						drection,stopLossType,stopLossDiff,stopWinDiff,additionFlag,additionType,additionPrice);
			}
    	}	
    	break;
    	case "OnRspQryTrade":{	// 查询成交记录回复
    		console.log("parameters: "+parameters);
    		if(isEmpty(parameters)){
    			console.log("2222222222");
    			//延迟5秒 查询个人账户
    			setTimeout("Trade.doAccount(trade_username)",5000);
    		}else{
    			console.log("%%%%%%%%");
	    		var tradeNo = parameters.TradeNo;
	    		var commodityNo = parameters.CommodityNo;
	    		var contractNo = parameters.ContractNo;
	    		var exchangeNo = parameters.ExchangeNo;
	    		var orderSysID = parameters.OrderSysID;
	    		var orderRef = parameters.OrderRef;
	    		var orderID = parameters.OrderID;
	    		var drection = parameters.Drection;
	    		var tradeNum = parameters.TradeNum;
	    		var tradePrice = parameters.TradePrice;
	    		var tradeDateTime = parameters.TradeDateTime;
	    		var tradeFee = parameters.TradeFee;
	    		var clientNo = parameters.ClientNo;
	    		var clientNo = trade_username;
	    		var currencyNo = getCacheContractAttribute(commodityNo, "CurrencyNo");  //获取对应的币种简称??
	    		var tradeType = "正常单";
	    		var orderType = "客户单";//or 强平单
	    		var orderUserno = null;
	    		if(orderType == "客户单"){
	    			orderUserno = clientNo;
	    		}else if(orderType == "强平单"){
	    			orderUserno = "QPServer";
	    		}
	    		var buyNum = 0;
	    		var sellNum = 0;
	    		if(drection == 0){
	    			buyNum = tradeNum;
	    		}else{
	    			sellNum = tradeNum;
	    		}
	    		var rows = $("#hasAuditData").datagrid('getSelections');
	    		var id = rows[0].id;
	    		
    			$.post(Check.rootPath() + "/admin/internation/future/detailSave",
					{	
						"tradeDate":tradeDateTime,
						"userNo":clientNo,
						"currencyNo":currencyNo,
						"exchangeNo":exchangeNo,
						"commodityNo":commodityNo+contractNo,
						"buyNum":buyNum,
						"sellNum":sellNum,
						"tradePrice":tradePrice,
						"free":tradeFee,
						"orderType":orderType,
						"orderUserno":orderUserno,
						"tradeType":tradeType,
						"tradeNo":tradeNo,
						"fastId":id
					} ,
					function(data){
						if(data.success == true){
							console.log(data.message);
						}
				});
    			
    		}
		}	
    	break;
    	
    	case "OnRspQryAccount":	{	// 查询个人账户信息回复
			// 资金返回结束显示
			if (isEmpty(parameters)) {
				todayMoeny = cacheTotalAccount.TodayAmount;
				testcheck(trade_username,todayMoeny);
				
			} else {
				mergeAccount(parameters);  // 合并资金信息
			}
    	}	
    	break;
    
	}

	if(method == "OnError") {
		var code = parameters.Code;
		var loginMessage = parameters.Message;
		console.log("OnErrorMessage: "+loginMessage);
	} 
	
}

/**
 * 账户资金缓存信息
 */
var cacheAccount = new Object();
/**
 * 账户资金汇总缓存信息
 */
var cacheTotalAccount = new Object();
cacheTotalAccount.TodayAmount = 0.0;
cacheTotalAccount.TodayCanUse = 0.0;
cacheTotalAccount.FloatingProfit = 0.0;
cacheTotalAccount.CloseProfit = 0.0;
cacheTotalAccount.FrozenMoney = 0.0;
cacheTotalAccount.Deposit = 0.0;
cacheTotalAccount.CounterFee = 0.0;
cacheTotalAccount.RiskRate = 0.0;
/**
 * 合并账户资金信息
 */
function mergeAccount(jAccount) {
	var accountVO = new AccountVO(jAccount);
	// 以币种为key
	var sKey = accountVO.CurrencyNo;
	cacheAccount[sKey] = accountVO;
	
	cacheTotalAccount.TodayAmount += accountVO.TodayAmount * accountVO.CurrencyRate;
	cacheTotalAccount.TodayCanUse += (accountVO.TodayAmount - accountVO.FrozenMoney - accountVO.Deposit) * accountVO.CurrencyRate;
	cacheTotalAccount.FloatingProfit += accountVO.FloatingProfit * accountVO.CurrencyRate;
	cacheTotalAccount.CloseProfit += accountVO.CloseProfit * accountVO.CurrencyRate;
	cacheTotalAccount.FrozenMoney += accountVO.FrozenMoney * accountVO.CurrencyRate;
	cacheTotalAccount.Deposit += accountVO.Deposit * accountVO.CurrencyRate;
	cacheTotalAccount.CounterFee += accountVO.CounterFee * accountVO.CurrencyRate;
	cacheTotalAccount.RiskRate += accountVO.RiskRate;
}








