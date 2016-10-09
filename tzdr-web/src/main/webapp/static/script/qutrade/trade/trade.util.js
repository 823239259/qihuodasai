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
 * @param {Object} lastPrice 最新价
 * @param {Object} tradeAvgPrice 均价
 * @param {Object} contractSize 每手乘数
 * @param {Object} miniTikeSize 最小变动单位
 * @param {Object} orderNum 订单量
 * @param {Object} drection 买卖方向（0-买，1-买）
 */
function doGetFloatingProfit(lastPrice,tradeAvgPrice,contractSize,miniTikeSize,orderNum,drection){
	var price = 0.00;
	if(drection == 0){
		price = lastPrice - tradeAvgPrice;
	}else if(drection == 1){
		price = tradeAvgPrice - lastPrice;
	}
	return parseFloat((price * contractSize) * orderNum / miniTikeSize).toFixed(2);
}
/**
 * 计算开仓均价 
 * @param {Object} price
 * @param {Object} num
 */
function doGetOpenAvgPrice(price,num){
	return Math.round(parseFloat(price / num).toFixed(2));
}
/**
 * 生成报单引用 
 */
function doGetOrderRef() {
	return new Date().getTime();
}
/**
 * 平仓处理
 * @param {Object} param 需要平仓的合约list
 */
function closing(param){
	var paramLength = param;
	for(var i = 0; i < paramLength; i++) {
		var closing = param[i];
		Trade.doInsertOrder(closing.ExchangeNo,
							closing.CommodityNo,
							closing.ContractNo,
							closing.OrderNum,
							closing.Drection,
							closing.PriceType,
							closing.LimitPrice,
							closing.TriggerPrice,
							closing.OrderRef);

	}
}
/**
 * 撤单处理 
 * @param {Object} param 需要撤单的list
 */
function cancleOrder(param) {
	var cancleParam = param;
	for(var i = 0; i < cancleParam.length; i++) {
		var cancle = cancleParam[i];
		Trade.doCancelOrder(cancle.orderSysId,
			cancle.orderId,
			cancle.exchangeNo,
			cancle.commodityNo,
			cancle.contractNo,
			cancle.orderNum,
			cancle.drection,
			cancle.orderPrice);
	}
}
var kong = "<span style='color:green;'>空</span>";
var duo = "<span style='color:red;'>多</span>";
/**
 * 解析买卖方向
 * @param direction 0-买，1-卖
 */
function analysisBusinessDirection(direction){
	var directionText = "";
	if(direction == 0){
		directionText = duo;
	}else if(direction == 1){
		directionText = kong;
	}
	return directionText;
}
/**
 *  解析交易状态
 * @param {Object} orderStatus
 */
function analysisOrderStatus(orderStatus){
	var orderStatusText = "";
	if(orderStatus == 0){
		orderStatusText = "已提交";
	}else if(orderStatus == 1){
		orderStatusText = "排队中";
	}else if(orderStatus == 2){
		orderStatusText = "部分提交";
	}else if(orderStatus == 3){
		orderStatusText = "完全成交";
	}else if(orderStatus == 4){
		orderStatusText = "已撤单";
	}else if(orderStatus == 5){
		orderStatusText = "下单失败";
	}else if(orderStatus == 6){
		orderStatusText == "未知";
	}
	return orderStatusText;
}
