var TradeUrl = {
	/**
	 * 登录url
	 */
	LoginUrl:"Login",
	/**
	 * 登出url
	 */
	LoginOut:"Logout",
	/**
	 * 获取个人账户信息url
	 */
	QryAccountUrl:"QryAccount",
	/**
	 * 获取订单信息url
	 */
	QryOrderUrl:"QryOrder",
	/**
	 * 获取成交记录url
	 */
	QryTradeUrl:"QryTrade",
	/**
	 * 获取持仓信息url
	 */
	QryHoldUrl:"QryHold",
	/**
	 * 报单录入url
	 */
	InsertOrderUrl:"InsertOrder",
	/**
	 * 报单撤销url
	 */
	CancelOrderUrl:"CancelOrder",
	/**
	 * 改单url
	 */
	ModifyOrderUrl:"ModifyOrder",
	/**
	 * 错误通知
	 */
	OnError : "OnError",
	/**
	 * 止损单录入url
	 */
	InsertStopLoss:"InsertStopLoss",
	/**
	 * 获取止损单url
	 */
	QryStopLoss:"QryStopLoss",
	/**
	 * 止损单修改
	 */
	ModifyStopLoss:"ModifyStopLoss",
	/**
	 * 条件单录入请求url
	 */
	InsertCondition:"InsertCondition",
	/**
	 * 条件单修改请求url
	 */
	ModifyCondition:"ModifyCondition",
	/**
	 * 条件单查询url
	 */
	QryCondition:"QryCondition"
	
}
var Trade = {
			/**
			 * 登录
			 * @param {Object} username 用户账户
			 * @param {Object} password 密码
			 */ 
			doLogin:function(username,password,isMock,version){
				Trade.doSendMessage(TradeUrl.LoginUrl,'{"ClientNo":"'+username+'","PassWord":"'+password+'","IsMock":'+isMock+',"Version":"'+version+'"}');
			},
			/**
			 * 登出
			 * @param {Object} username
			 */
			doLoginOut:function(username){
				Trade.doSendMessage(TradeUrl.LoginOut,'{"ClientNo":"'+username+'"}');
			},
			/**
			 * 获取个人账户信息
			 * @param {Object} username 用户账户
			 */
			doAccount:function(username){
				Trade.doSendMessage(TradeUrl.QryAccountUrl,'{"ClientNo":"'+username+'"}');
			},
			/**
			 * 获取订单信息
			 * @param {Object} username 用户账户
			 */
			doOrder:function(username){
				Trade.doSendMessage(TradeUrl.QryOrderUrl,'{"ClientNo":"'+username+'"}');
			},
			/**
			 * 查询成交记录信息
			 * @param {Object} username 用户账户
			 */
			doTrade:function(username){
				Trade.doSendMessage(TradeUrl.QryTradeUrl,'{"ClientNo":"'+username+'"}')
			},
			/**
			 * 查询持仓信息
			 * @param {Object} username 用户账户
			 */
			doHold:function(username){
				Trade.doSendMessage(TradeUrl.QryHoldUrl,'{"ClientNo":"'+username+'"}');
			},
			/**
			 * 报单录入请求
			 * @param {Object} exchangeNo 交易所代码
			 * @param {Object} commodeityNo 品种代码
			 * @param {Object} contractNo 合约代码
			 * @param {Object} orderNum 订单数量
			 * @param {Object} drection 买卖方向（0：买，1：卖）
			 * @param {Object} priceType 价格类型:限价0，市价1，止损2
			 * @param {Object} limitPrice 订单价格
			 * @param {Object} triggerPrice 触发价(止损单需要)
			 * @param {Object} orderRef 报单引用，用户自己生成
			 */
			doInsertOrder:function(exchangeNo,commodeityNo,contractNo,orderNum,drection,priceType,limitPrice,triggerPrice,orderRef){
				var param = '{"ExchangeNo":"'+ exchangeNo +'",'
							+' "CommodityNo":"'+ commodeityNo +'",'
							+' "ContractNo":"'+ contractNo +'",'
							+' "OrderNum":'+ orderNum +','
							+' "Drection":' +drection +','
							+' "PriceType":'+ priceType +','
							+' "LimitPrice":'+ limitPrice +','
							+' "TriggerPrice":'+ triggerPrice +','
							+' "OrderRef":"'+ orderRef +'"}';
				Trade.doSendMessage(TradeUrl.InsertOrderUrl,param);
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
			doCancelOrder:function(orderSysId,orderId,exchangeNo,commodityNo,contractNo,orderNum,drection,orderPrice){
				var param = '{"OrderSysID":"'+orderSysId+'",'
							+' "OrderID":"'+orderId+'",'
							+' "ExchangeNo":"'+exchangeNo+'",'
							+' "CommodityNo":"'+commodityNo+'",'
							+' "ContractNo":"'+contractNo+'",'
							+' "OrderNum":'+orderNum+','
							+' "Drection":'+drection+','
							+' "OrderPrice":'+orderPrice+'}';
				Trade.doSendMessage(TradeUrl.CancelOrderUrl,param);
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
			doModifyOrder:function(orderSysId,orderId,exchangeNo,commodityNo,contractNo,orderNum,drection,orderPrice,triggerPrice){
				var param = '{"OrderSysID":"'+orderSysId+'",'
								+' "OrderID":"'+orderId+'",'
								+' "ExchangeNo":"'+exchangeNo+'",'
								+' "CommodityNo":"'+commodityNo+'",'
								+' "ContractNo":"'+contractNo+'",'
								+' "OrderNum":'+orderNum+','
								+' "Drection":'+drection+','
								+' "OrderPrice":'+orderPrice+','
								+' "TriggerPrice":'+triggerPrice+'}';
				Trade.doSendMessage(TradeUrl.ModifyOrderUrl,param);
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
			doInsertStopLoss:function(exchangeNo,commodityNo,contractNo,num,stopLossType,stopLossDiff,holdAvgPrice,holdDrection,orderType,stopLossPrice){
				var param = '{"ExchangeNo":"'+exchangeNo+'",'
							+' "CommodityNo":"'+commodityNo+'",'
							+' "ContractNo":"'+contractNo+'",'
							+' "Num":'+num+','
							+' "StopLossType":'+stopLossType+','
							+' "StopLossDiff":'+stopLossDiff+','
							+' "HoldAvgPrice":'+holdAvgPrice+','
							+' "HoldDrection":'+holdDrection+','
							+' "OrderType":'+orderType+','
							+' "StopLossPrice":'+stopLossPrice+'}';
				Trade.doSendMessage(TradeUrl.InsertStopLoss,param);
			},
			/**
			 * 获取止损单请求
			 * @param {Object} username
			 */
			doQryStopLoss:function(username){
				Trade.doSendMessage(TradeUrl.QryStopLoss,'{"ClientNo":"'+username+'"}');
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
			doModifyStopLoss:function(stopLossNo,modifyFlag,num,stopLossType,orderType,stopLossDiff,stopLossPrice){
				var param = '{"StopLossNo":"'+stopLossNo+'",'
							+' "ModifyFlag":'+modifyFlag+','
							+' "Num":'+num+','
							+' "StopLossType":'+stopLossType+','
							+' "StopLossDiff":'+stopLossDiff+','
							+' "StopLossPrice":'+stopLossPrice+','
							+' "OrderType":'+orderType+'}';
				Trade.doSendMessage(TradeUrl.ModifyStopLoss,param);
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
			doInsertCondition:function(exchangeNo,
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
										additionPrice){
				var param = '{"ExchangeNo":"'+exchangeNo+'",'
							+' "CommodityNo":"'+commodityNo+'",'
							+' "ContractNo":"'+contractNo+'",'
							+' "Num":'+num+','
							+' "ConditionType":'+conditionType+','
							+' "PriceTriggerPonit":'+priceTriggerPonit+','
							+' "CompareType":'+compareType+','
							+' "TimeTriggerPoint":"'+timeTriggerPoint+'",'
							+' "AB_BuyPoint":'+abBuyPoint+','
							+' "AB_SellPoint":'+abSellPoint+','
							+' "OrderType":'+orderType+','
							+' "Drection":'+drection+','
							+' "StopLossType":'+stopLossType+','
							+' "StopLossDiff":'+stopLossDiff+','
							+' "StopWinDiff":'+stopWinDiff+','
							+' "AdditionFlag":'+additionFlag+','
							+' "AdditionType":'+additionType+','
							+' "AdditionPrice":'+additionPrice+'}';
							console.log(param); 
				Trade.doSendMessage(TradeUrl.InsertCondition,param);
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
			doUpdateModifyCondition:function(conditionNo,
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
											additionPrice){
				var param = '{"ConditionNo":"'+conditionNo+'",'
							+' "ModifyFlag":'+modifyFlag+','
							+' "Num":'+num+','
							+' "ConditionType":'+conditionType+','
							+' "PriceTriggerPonit":'+priceTriggerPonit+','
							+' "CompareType":'+compareType+','
							+' "TimeTriggerPoint":"'+timeTriggerPoint+'",'
							+' "AB_BuyPoint":'+abBuyPoint+','
							+' "AB_SellPoint":'+abSellPoint+','
							+' "OrderType":'+orderType+','
							+' "Drection":'+drection+','
							+' "StopLossType":'+stopLossType+','
							+' "StopLossDiff":'+stopLossDiff+','
							+' "StopWinDiff":'+stopWinDiff+','
							+' "AdditionFlag":'+additionFlag+','
							+' "AdditionType":'+additionType+','
							+' "AdditionPrice":'+additionPrice+'}';
							console.log(param); 
				Trade.doSendMessage(TradeUrl.ModifyCondition,param);
			},
			/**
			 * 查询条件单请求
			 * @param {Object} username
			 */
			doQryCondition:function(username){
				Trade.doSendMessage(TradeUrl.QryCondition,'{"ClientNo":"'+username+'"}');
			},
			/**
			 * 发送交易请求
			 * @param {Object} method
			 * @param {Object} parameters
			 */
			doSendMessage:function(method,parameters){
				socket.send('{"Method":"'+method+'","Parameters":'+parameters+'}');
			}
		}
