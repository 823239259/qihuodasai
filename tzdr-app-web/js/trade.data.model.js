/**
 * 创建持仓数据模型
 * @param {Object} param
 */
function createPostionsParam(param) {
	this.contratCode = param.ContractCode;
	this.drection = analysisPositionDrection(param.Drection);
	this.holdNum = param.HoldNum;
	this.openAvgPrice = param.OpenAvgPrice;
	this.floatingProfit = param.FloatingProfit;
	return this;
}
/**
 * 创建挂单数据模型
 * @param {Object} param
 */
function createDesignatesParam(param) {
	var orderStatus = param.OrderStatus;
	var orderNum = param.OrderNum;
	var tradeNum = param.TradeNum;
	var cdNum = 0;
	if (orderStatus == 4) {
		cdNum = orderNum - tradeNum;
	}
	this.contratCode = param.ContractCode;
	this.drection = analysisDrection(param.Drection);
	this.orderPrice = param.OrderPrice;
	this.orderNum = param.OrderNum;
	this.cdNum = cdNum;
	this.insertDateTime = param.InsertDateTime;
	return this;
}
/**
 * 创建交易数据 
 * @param {Object} ExchangeNo
 * @param {Object} CommodityNo
 * @param {Object} OrderNum
 * @param {Object} Drection
 * @param {Object} PriceType
 * @param {Object} LimitPrice
 * @param {Object} TriggerPrice
 * @param {Object} OrderRef
 */
function createSellingParam(ExchangeNo, CommodityNo, ContractNo, OrderNum, Drection, PriceType, LimitPrice, TriggerPrice, OrderRef) {
	this.ExchangeNo = ExchangeNo;
	this.CommodityNo = CommodityNo;
	this.ContractNo = ContractNo;
	this.OrderNum = OrderNum;
	this.Drection = Drection;
	this.PriceType = PriceType;
	this.LimitPrice = LimitPrice;
	this.TriggerPrice = TriggerPrice;
	this.OrderRef = OrderRef;
	return this;
}
/**
 * 创建撤单数据 
 * @param {Object} orderSysId
 * @param {Object} orderId
 * @param {Object} exchangeNo
 * @param {Object} commodityNo
 * @param {Object} contractNo
 * @param {Object} orderNum
 * @param {Object} drection
 * @param {Object} orderPrice
 */
function createCancleOrderParam(orderSysId, orderId, exchangeNo, commodityNo, contractNo, orderNum, drection, orderPrice) {
	this.orderSysId = orderSysId;
	this.orderId = orderId;
	this.exchangeNo = exchangeNo;
	this.commodityNo = commodityNo;
	this.contractNo = contractNo;
	this.orderNum = orderNum;
	this.drection = drection;
	this.orderPrice = orderPrice;
	return this;
}