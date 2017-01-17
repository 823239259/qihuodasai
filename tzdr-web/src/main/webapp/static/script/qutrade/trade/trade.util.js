/**
 * 获取市场价格
 * @param {Object} price 最新价格
 * @param {Object} miniTikeSize 最小变动单位
 * @param {Object} drection 买卖方向(0-买，1-卖)
 */
function doGetMarketPrice(price,miniTikeSize,drection,dotSize){
	var base = 20;
	var priceRange = parseFloat(miniTikeSize) * base;
	price =  parseFloat(price);
	var newPrice = 0.00;
	if(drection == 0){
		newPrice = price + priceRange;
	}else if(drection == 1){
		newPrice = price - priceRange;
	}
	return parseFloat(newPrice).toFixed(dotSize);
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
 * @param {Object} dosize
 */
function doGetOpenAvgPrice(price,num,dosize){
	return parseFloat(price / num).toFixed(dosize);
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
	var paramLength = param.length;
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
/**
 * 改单处理 
 * @param {Object} param 需要撤单的list
 */
function modifyOrder(param) {
	var modifyParam = param;
	for(var i = 0; i < modifyParam.length; i++) {
		var cancle = modifyParam[i];
		Trade.doModifyOrder(
					cancle.orderSysId,
					cancle.orderId,
					cancle.exchangeNo,
					cancle.commodityNo,
					cancle.contractNo,
					cancle.orderNum,
					cancle.drection,
					cancle.orderPrice,
					cancle.triggerPrice);
	}
}
/**
 * 止损处理
 * @param {Object} param
 */
function inserStopLoss(param){
	Trade.doInsertStopLoss(
						param.exchangeNo,
						param.commodityNo,
						param.contractNo,
						param.num,
						param.stopLossType,
						param.stopLossDiff,
						param.holdAvgPrice,
						param.holdDrection,
						param.orderType,
						param.stopLossPrice);
}
/**
 * 修改止损单处理
 * @param {Object} param
 */
function doModifyStopLoss(param){
	Trade.doModifyStopLoss(
						   param.stopLossNo,
						   param.modifyFlag,
						   param.num,
						   param.stopLossType,
						   param.orderType,
						   param.stopLossDiff,
						   param.stopLossPrice);
}
/**
 * 条件增加单处理
 * @param {Object} param
 */
function insertCondition(param){
	Trade.doInsertCondition(
							param.exchangeNo,
							param.commodityNo,
							param.contractNo,
							param.num,
							param.conditionType,
							param.priceTriggerPonit,
							param.compareType,
							param.timeTriggerPoint,
							param.abBuyPoint,
							param.abSellPoint,
							param.orderType,
							param.drection,
							param.stopLossType,
							param.stopLossDiff,
							param.stopWinDiff,
							param.additionFlag,
							param.additionType,
							param.additionPrice
	);
}
/**
 * 条件单修改处理
 * @param {Object} param
 */
function updateCondition(param){
		Trade.doUpdateModifyCondition(
									param.conditionNo,
									param.modifyFlag,
									param.num,
									param.conditionType,
									param.priceTriggerPonit,
									param.compareType,
									param.timeTriggerPoint,
									param.abBuyPoint,
									param.abSellPoint,
									param.orderType,
									param.drection,
									param.stopLossType,
									param.stopLossDiff,
									param.stopWinDiff,
									param.additionFlag,
									param.additionType,
									param.additionPrice
		);
}
var kong = "<span style='color:green;'>空</span>";
var duo = "<span style='color:red;'>多</span>";
/**
 * 解析多空方向
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
 * 解析多空方向
 * @param direction 0-买，1-卖
 */
function analysisBusinessBuySell(direction){
	var directionText = "";
	if(direction == 0){
		directionText = "买";
	}else if(direction == 1){
		directionText = "卖";
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
/**
 * 解析订单类型
 * @param priceType
 */
function analysisOrderPriceType(priceType){
	var priceTypeText = "";
	if(priceType == 0){
		priceTypeText = "限价";
	}else if(priceType == 1){
		priceTypeText = "市价";
	}else if(priceType == 2){
		priceTypeText = "对手价";
	}
	return priceTypeText;
}
/**
 * 解析止损单的状态
 * @param {Object} status
 */
function analysisStopLossStatus(status){
	var lossStatus = "";
	if(status == 0){
		lossStatus = "运行中";
	}else if(status == 1){
		lossStatus = "暂停";
	}else if(status == 2){
		lossStatus = "已触发";
	}else if(status == 3){
		lossStatus = "已取消";
	}else if(status == 4){
		lossStatus = "插入失败";
	}else if(status == 5){
		lossStatus = "触发失败";
	}
	return lossStatus;
}
/**
 * 解析止损单类别
 * @param {Object} param
 */
function analysisStopLossType(param){
	if(param == 0){
		param = "限价止损";
	}else if(param == 1){
		param = "限价止盈";
	}else if(param == 2){
		param = "动态止损";
	}
	return param;
}
/**
 * 解析止损单价格类型
 * @param {Object} orderType
 */
function lossOrderType(orderType){
	if(orderType == 1){
		orderType = "市价";
	}else if(orderType == 2){
		orderType = "对手价";
	}
	return orderType;
}
/**
 * 解析条件单状态
 * @param {Object} status
 */
function analysisConditionStatus(status){
	if(status == 0){
		status = "运行中";
	}else if(status == 1){
		status = "暂停";
	}else if(status == 2){
		status = "已触发";	
	}else if(status == 3){
		status = "已取消";
	}else if(status == 4){
		status = "插入失败";
	}else if(status == 5){
		status = "触发失败";
	}
	return status;
}
/**
 * 解析条件单类型
 * @param {Object} type
 */
function analysisConditionType(type){
	if(type == 0){
		type = "价格条件";
	}else if(type == 1){
		type = "时间条件";
	}else if(type == 2){
		type = "（双向价格）AB单 "
	}
	return type;
}
/**
 * 解析价格触发方式
 * @param {Object} compareType
 */
function analysisConditionCompareType(compareType){
	if(compareType == 0){
		compareType = ">";
	}else if(compareType == 1){
		compareType = "<";
	}else if(compareType == 2){
		compareType = ">=";
	}else if(compareType == 3){
		compareType = "<=";
	}
	return compareType;
}
/**
 * 解析条件单StopLossType
 * @param {Object} stopLossType
 */
function analysisCondityionStopLossType(stopLossType){
	if(stopLossType == 0){
		stopLossType = "止损";
	}else if(stopLossType == 1){
		stopLossType = "止盈";
	}else if(stopLossType == 2){
		stopLossType = "止损+止盈";
	}else if(stopLossType == 3){
		stopLossType = "浮动止损";
	}else if(stopLossType == 4){
		stopLossType = "不设置止损";
	}
	return stopLossType;
}
