/**
 * 发送socket请求
 * @param {Object} method 方法名
 * @param {Object} parameters 参数
 */
function sendMessage(method,parameters){
	    	socket.send('{"Method":"'+method+'","Parameters":'+parameters+'}');
}
var TradeUrl = {
	/**
	 * soketurl
	 */
	SocketUrl:"ws://192.168.0.213:6060",
	
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
			doLogin:function(username,password){
				sendMessage(TradeUrl.LoginUrl,'{"ClientNo":"'+username+'","PassWord":"'+password+'"}');
			},
			/**
			 * 登出
			 * @param {Object} username
			 */
			doLoginOut:function(username){
				sendMessage(TradeUrl.LoginOut,'{"ClientNo":"'+username+'"}');
			},
			/**
			 * 获取个人账户信息
			 * @param {Object} username 用户账户
			 */
			doAccount:function(username){
				sendMessage(TradeUrl.QryAccountUrl,'{"ClientNo":"'+username+'"}');
			},
			/**
			 * 获取订单信息
			 * @param {Object} username 用户账户
			 */
			doOrder:function(username){
				sendMessage(TradeUrl.QryOrderUrl,'{"ClientNo":"'+username+'"}');
			},
			/**
			 * 查询成交记录信息
			 * @param {Object} username 用户账户
			 */
			doTrade:function(username){
				sendMessage(TradeUrl.QryTradeUrl,'{"ClientNo":"'+username+'"}')
			},
			/**
			 * 查询持仓信息
			 * @param {Object} username 用户账户
			 */
			doHold:function(username){
				sendMessage(TradeUrl.QryHoldUrl,'{"ClientNo":"'+username+'"}');
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
				sendMessage(TradeUrl.InsertOrderUrl,param);
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
				sendMessage(TradeUrl.CancelOrderUrl,param);
			}
		}
var MarketUrl = {
			SocketUrl:"ws://192.168.0.213:9006",
			/**
			 * 登录url
			 */
			Login:"Login",
			/**
			 * 品种url
			 */
			QryCommodityUrl:"QryCommodity",
			/**
			 * 合约url
			 */
			QryContractUrl:"QryContract",
			/**
			 * 订阅url
			 */
			SubscribeUrl:"Subscribe",
			/**
			 * 历史url
			 */
			QryHistoryUrl:"QryHistory"
}
/**
 * 获取市场价格
 * @param {Object} price 最新价格
 * @param {Object} miniTikeSize 最小变动单位
 * @param {Object} drection 买卖方向(0-买，1-卖)
 */
function doGetMarketPrice(price,miniTikeSize,drection){
	var base = 20;
	var priceRange = parseFloat(miniTikeSize) * base;
	price =  parseFloat(price);
	var newPrice = 0.00;
	if(drection == 0){
		newPrice = price + priceRange;
	}else if(drection == 1){
		newPrice = price - priceRange;
	}
	return parseFloat(newPrice).toFixed(2);
}
/**
 * 计算浮动盈亏
 * @param {Object} lastPrice
 * @param {Object} tradeAvgPrice
 * @param {Object} contractSize
 */
function doGetFloatingProfit(lastPrice,tradeAvgPrice,contractSize,orderNum){
	var price = lastPrice - tradeAvgPrice;
	return parseFloat((price * contractSize) * orderNum).toFixed(2);
}
/**
 * 连接交易
 */
var socket = new WebSocket(TradeUrl.SocketUrl);

var username = localStorage.getItem("account");

var password = localStorage.getItem("password");
