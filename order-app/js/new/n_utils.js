/**
 * log是否激活。由log激活标志确定：激活 true，关闭 false
 */
var vsLog = (function(logMsg) {
	var logActive = false;
	return function(logMsg) {
		if(logActive) {
			console.log(logMsg);
		}
	}
})();

/**
 * 检查对象是否为空:
 * @param {Object} obj
 * @return {Boolean}		空返回true，不为空返回false
 */
function isEmpty(obj) {
	if (obj == null || typeof(obj) == "undefined" || obj.length == 0) {
		return true;
	}
	return false;
}

/**
 * 根据元素ID获取元素的innerHTML
 * @param {HTMLString} elementID
 */
function getInnerHTMLByID(elementID) {
	var $tmpDOM = document.getElementById(elementID);
	if (isEmpty($tmpDOM)) {
		return "";
	}
	return $tmpDOM.innerHTML;
}

/**
 * 将模板填充数据后显示到对应元素
 * @param viewId 显示元素目标ID
 * @param tplId 显示模板
 * @param jData 填充模板数据。json对象
 * @param fillType 填充方式，1:before，前置插入; 2:after，追加插入；0：替换。默认为替换
 */
function tplFillData(viewId, tplId, jData, fillType) {
	if (isEmpty(jData)) {
		return;
	}
	if(document.getElementById(viewId)==null){
		return;
	}
	var tpl = document.getElementById(tplId).innerHTML;
	laytpl(tpl).render(jData, function(html) {
		if(fillType === FillType.before) { // before
			document.getElementById(viewId).innerHTML =  html + document.getElementById(viewId).innerHTML;
		} else if(fillType === FillType.after) { // after
			document.getElementById(viewId).innerHTML =  document.getElementById(viewId).innerHTML + html;
		} else { // replace
			document.getElementById(viewId).innerHTML = html;
		}
	});
}

function getDrectionName(drection) {
	if (drection === 0) {
		return "买";
	} else if (drection === 1) {
		return "卖";
	}
	return drection;
}

function getDrectionName2(drection) {
	if (drection === 0) {
		return "多";
	} else if (drection === 1) {
		return "空";
	}
	return drection;
}

/**
 * 生成报单引用 
 */
function buildOrderRef() {
	return TradeConfig.client_source + new Date().getTime();
}

/**
 * 将传入的浮点数保留指定小数位
 * @param {Number} num	需要处理的浮点数
 * @param {Number} dotSize	需要保留的小数位数
 * @returns {String} 	处理后的浮点数
 */
function toFixedFloatNumber(num, dotSize) {
	if (isNaN(num) || !num) {
		return 0.0;
	}
	return parseFloat(num).toFixed(dotSize);
}
/**
 * 行情接口调用工具
 */
var Quote = {
	/**
	 * 发送请求
	 * @param method
	 * @param parameters
	 */
	doSendMessage: function(method, parameters) {
		quoteSocket.send('{"Method":"' + method + '","Parameters":' + parameters + '}');
	},
	/**
	 * 登录请求
	 * @param username
	 * @param password
	 */
	doLogin: function(username, password) {
		Quote.doSendMessage(QuoteMethod.LoginMethod, '{"UserName":"' + username + '","PassWord":"' + password + '","Source":"web"}');
	},
	/**
	 * 登录请求
	 * @param username
	 */
	doLoginOut: function(username) {
		Quote.doSendMessage(QuoteMethod.LogoutMethod, '{"UserName":"' + username + '"}');
	},
	/**
	 * 查询品种请求
	 * @param exchangeNo
	 */
	doQryCommodity: function(exchangeNo) {
		Quote.doSendMessage(QuoteMethod.QryCommodityMethod, '{"ExchangeNo":"' + exchangeNo + '"}');
	},
	/**
	 * 查询所有品种
	 */
	doAllQryCommodity: function() {
		Quote.doSendMessage(QuoteMethod.QryCommodityMethod, null);
	},
	/**
	 * 查询合约请求
	 * @param exchangeNo
	 * @param commodityNo
	 */
	doQryContract: function(exchangeNo, commodityNo) {
		Quote.doSendMessage(QuoteMethod.QryContractMethod, '{"ExchangeNo":"' + exchangeNo + '","CommodityNo":"' + commodityNo + '"}');
	},
	/**
	 * 订阅请求
	 * @param exchangeNo
	 * @param commodityNo
	 * @param contractNo
	 */
	doSubscribe: function(exchangeNo, commodityNo, contractNo) {
		Quote.doSendMessage(QuoteMethod.SubscribeMethod, '{"ExchangeNo":"' + exchangeNo + '","CommodityNo":"' + commodityNo + '","ContractNo":"' + contractNo + '"}');
	},
	/**
	 * 取消订阅请求
	 * @param exchangeNo
	 * @param commodityNo
	 * @param contractNo
	 */
	doUnSubscribe: function(exchangeNo, commodityNo, contractNo) {
		Quote.doSendMessage(QuoteMethod.UnSubscribeMethod, '{"ExchangeNo":"' + exchangeNo + '","CommodityNo":"' + commodityNo + '","ContractNo":"' + contractNo + '"}');
	},
	/**
	 * 查询历史请求
	 * @param exchangeNo
	 * @param commodityNo
	 * @param contractNo
	 * @param hisQuoteType
	 * @param beginTime
	 * @param endTime
	 * @param count
	 */
	doQryHistory: function(exchangeNo, commodityNo, contractNo, hisQuoteType, beginTime, endTime, count) {
		Quote.doSendMessage(QuoteMethod.QryHistoryMethod, '{"ExchangeNo":"' + exchangeNo + '","CommodityNo":"' + commodityNo + '","ContractNo":"' + contractNo + '","HisQuoteType":' + hisQuoteType + ',"BeginTime":"' + beginTime + '","EndTime":"' + endTime + '","Count":' + count + '}');
	},
	/**
	 * 查询初始化历史数据请求
	 * @param exchangeNo
	 * @param commodityNo
	 * @param contractNo
	 */
	doQryFirstHistory: function(exchangeNo, commodityNo, contractNo) {
		Quote.doSendMessage(QuoteMethod.QryHistoryMethod, '{"ExchangeNo":"' + exchangeNo + '","CommodityNo":"' + commodityNo + '","ContractNo":"' + contractNo + '"}');
	},
	/**
	 * 查询初始化历史数据请求
	 * @param exchangeNo
	 * @param commodityNo
	 * @param contractNo
	 */
	QryDepthQuoteGroup: function() {
		Quote.doSendMessage(QuoteMethod.QryDepthQuoteGroupMethod,null);
	}
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
	 * 查询历史成交记录
	 * @param {Object} username 用户账户
	 * @param {Object} beginTime 开始时间
	 * @param {Object} endTime 结束时间
	 */
	doHisTrade: function(username,beginTime,endTime) {
		Trade.doSendMessage(TradeMethod.QryHisTradeUrl, '{"ClientNo":"' + username + '","BeginTime":"' + beginTime + '","EndTime":"' + endTime + '"}')
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
		var param = '{"ExchangeNo":"' + CacheQuoteSubscribe.getCacheContractQuote(contract, "ExchangeNo") + '",' +
			' "CommodityNo":"' + CacheQuoteSubscribe.getCacheContractQuote(contract, "CommodityNo") + '",' +
			' "ContractNo":"' + CacheQuoteSubscribe.getCacheContractQuote(contract, "ContractNo") + '",' +
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
//		vsLog('{"Method":"'+method+'","Parameters":'+parameters+'}');
		
		tradeSocket.send('{"Method":"' + method + '","Parameters":' + parameters + '}');
	},
	/**
	 * 订单版 开仓请求
	 * @param {Object} ClientNo 交易账号（000013）
	 * @param {Object} PlatForm_User 平台账号（手机号）
	 * @param {Object} ProductID 产品编号，在后台配置的，用户选择
	 * @param {Object} CommodityNo 品种代码
	 * @param {Object} ContractNo 合约代码
	 * @param {Object} OrderNum 订单数量
	 * @param {Object} Direction 开仓买卖方向（0：买，1：卖）
	 * @param {Object} StopWin 订单的止盈金额（人民币）
	 * @param {Object} StopLoss 订单的止损金额（人民币）
	 * @param {Object} Deposit 滑点保证金（人民币）
	 * @param {Object} Fee Fee	该订单的手续费(总和) （人民币）
	 */
	doOpenOrderGW:function(
		ClientNo,PlatForm_User,ProductID,
		CommodityNo,ContractNo,OrderNum,
		Direction,StopWin,StopLoss,Deposit,Fee
		){
		var param = '{"ClientNo":"' + ClientNo + '",' +
			' "PlatForm_User":' + PlatForm_User + ',' +
			' "ProductID":' + ProductID + ',' +
			' "CommodityNo":' + CommodityNo + ',' +
			' "ContractNo":' + ContractNo + ',' +
			' "OrderNum":' + OrderNum + ',' +
			' "Direction":"' + Direction + '",' +
			' "StopWin":' + StopWin + ',' +
			' "StopLoss":' + StopLoss + ',' +
			' "Deposit":' + Deposit + ',' +
			' "Fee":' + Fee +
			'}';
		
		Trade.doSendMessage(TradeMethod.OpenOrderGW, param);
	},
	/**
	 * 订单版 平仓请求
	 * @param {Object} ClientNo
	 * @param {Object} PlatForm_User
	 * @param {Object} OrderID
	 */
	doCloseOrderGW:function(ClientNo,PlatForm_User,OrderID){
		var param = '{"ClientNo":"' + ClientNo + '",' +
			' "PlatForm_User":' + PlatForm_User + ',' +
			' "OrderID":' + OrderID + 
			'}';
		Trade.doSendMessage(TradeMethod.CloseOrderGW, param);	
	},
	/**
	 * 订单版 修改订单（修改止损止盈）
	 * @param {Object} ClientNo
	 * @param {Object} PlatForm_User
	 * @param {Object} OrderID
	 * @param {Object} StopWin
	 * @param {Object} StopLoss
	 */
	doChangeOrderGW:function(
		ClientNo,PlatForm_User,OrderID,StopWin,StopLoss
		){
		
		var param = '{"ClientNo":"' + ClientNo + '",' +
			' "PlatForm_User":' + PlatForm_User + ',' +
			' "OrderID":' + OrderID + ',' +
			' "StopWin":' + StopWin + ',' +
			' "StopLoss":' + StopLoss +
			'}';
		Trade.doSendMessage(TradeMethod.ChangeOrderGW, param);	
	},
	
	/**
	 * 订单版  查询订单
	 * @param {Object} ClientNo
	 */
	doQryOrderGW:function(ClientNo){
		
		var param = '{"ClientNo":"' + ClientNo + 
			'}';
		Trade.doSendMessage(TradeMethod.QryOrderGW, param);		
	},
	/**
	 * 订单版  查询历史订单
	 * @param {Object} ClientNo
	 * @param {Object} BeginTime
	 * @param {Object} EndTime
	 */
	doQryHisOrderGW:function(
		ClientNo,BeginTime,EndTime
		){
		var param = '{"ClientNo":"' + ClientNo + '",' +
			' "BeginTime":' + BeginTime + ',' +
			' "EndTime":' + EndTime + 
			'}';
		Trade.doSendMessage(TradeMethod.QryHisOrderGW, param);		
	}
	
	
	
}
// 填充类型：1:before，前置插入; 2:after，追加插入；0：替换。默认为替换
var FillType = {
	repalce: 0,
	before: 1,
	after: 2
};

/**
 * 根据合约价格精度处理价格小数位数
 * @param price	需处理原价格
 * @param commodityNo	根据哪个品种的小数位处理
 * @returns
 */
function fixedPriceByContract(price, commodityNo) {
	var dotSize = CacheQuoteBase.getCacheContractAttribute(commodityNo, "DotSize");
	if(isEmpty(dotSize)) {
		dotSize = 0;
	}

	return parseFloat(price).toFixed(dotSize);
}


function getNowFormatDate(date) {
	var seperator1 = "-";
	var seperator2 = ":";
	var month = date.getMonth() + 1;
	var strDate = date.getDate();
	var getMinutes = date.getMinutes();
	var getSeconds = "00"
	if(month >= 1 && month <= 9) {
		month = "0" + month;
	}
	if(strDate >= 0 && strDate <= 9) {
		strDate = "0" + strDate;
	}
	if(Number(getMinutes) < 10) {
		getMinutes = "0" + getMinutes;
	}
	var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate +
		" " + date.getHours() + seperator2 + getMinutes +
		seperator2 + getSeconds;
	return currentdate;
}


/**
 * 根据合约价格精度处理价格小数位数
 * @param price	需处理原价格
 * @param commodityNo	根据哪个品种的小数位处理
 * @returns
 */
function fixedPriceByContract(price, commodityNo) {
	var dotSize = CacheQuoteBase.getCacheContractAttribute(commodityNo, "DotSize");
	if(isEmpty(dotSize)) {
		dotSize = 0;
	}

	return parseFloat(price).toFixed(dotSize);
}



