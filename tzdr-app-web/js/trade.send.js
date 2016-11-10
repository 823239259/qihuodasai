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
	OnError : "OnError"
}
var Trade = {
			/**
			 * 登录
			 * @param {Object} username 用户账户
			 * @param {Object} password 密码
			 */ 
			doLogin:function(username,password,isMock){
				Trade.doSendMessage(TradeUrl.LoginUrl,'{"ClientNo":"'+username+'","PassWord":"'+password+'","IsMock":'+isMock+'}');
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
			 * 发送交易请求
			 * @param {Object} method
			 * @param {Object} parameters
			 */
			doSendMessage:function(method,parameters){
				socket.send('{"Method":"'+method+'","Parameters":'+parameters+'}');
			}
		}
