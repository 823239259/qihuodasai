// 持仓实体
function HoldOrderVO(jHoldOrder) {

	this.ClientNo=jHoldOrder.ClientNo;
	this.ExchangeNo=jHoldOrder.ExchangeNo;
	this.CurrencyNo=jHoldOrder.CurrencyNo;
	this.CommodityNo=jHoldOrder.CommodityNo;
	this.ContractNo=jHoldOrder.ContractNo;
	this.ContractCode=jHoldOrder.ContractCode;
	this.TradeDateTime=jHoldOrder.TradeDateTime;
	this.TradeNo=jHoldOrder.TradeNo;
	this.Drection=jHoldOrder.Drection;
	this.HoldNum=jHoldOrder.HoldNum;
	this.OpenAvgPrice=jHoldOrder.OpenAvgPrice;
	this.HoldAvgPrice=jHoldOrder.HoldAvgPrice;
	this.HoldStatus=jHoldOrder.HoldStatus;
	this.FloatingProfit=jHoldOrder.FloatingProfit;
	return this;
}

// 挂单实体
function ApplyOrderVO(jApplyOrder) {
	return new OrderVO(jApplyOrder);
}

// 委托信息实体
function OrderVO(jOrder) {
	this.ClientNo=jOrder.ClientNo;
	this.OrderSysID=jOrder.OrderSysID;
	this.OrderRef=jOrder.OrderRef;
	this.OrderID=jOrder.OrderID;
	this.ExchangeNo=jOrder.ExchangeNo;
	this.CommodityNo=jOrder.CommodityNo;
	this.ContractNo=jOrder.ContractNo;
	this.ContractCode=jOrder.ContractCode;
	this.OrderNum=jOrder.OrderNum;
	this.Drection=jOrder.Drection;
	this.PriceType=jOrder.PriceType;
	this.OrderPrice=jOrder.OrderPrice;
	this.TriggerPrice=jOrder.TriggerPrice;
	this.TradeNum=jOrder.TradeNum;
	this.TradePrice=jOrder.TradePrice;
	this.OrderStatus=jOrder.OrderStatus;
	this.StatusMsg=jOrder.StatusMsg;
	this.InsertDateTime=jOrder.InsertDateTime;
	this.ValidDate=jOrder.ValidDate;
	this.InsertUser=jOrder.InsertUser;
	return this;
}

// 成交
function TradeOrderVO(jTradeOrder) {

	this.ClientNo=jTradeOrder.ClientNo;
	this.TradeNo=jTradeOrder.TradeNo;
	this.CommodityNo=jTradeOrder.CommodityNo;
	this.ExchangeNo=jTradeOrder.ExchangeNo;
	this.ContractNo=jTradeOrder.ContractNo;
	this.Drection=jTradeOrder.Drection;
	this.TradeNum=jTradeOrder.TradeNum;
	this.TradePrice=Number(jTradeOrder.TradePrice).toFixed(6);
	this.TradeDateTime=jTradeOrder.TradeDateTime;
	this.TradeFee=Number(jTradeOrder.TradeFee).toFixed(6);
	this.CurrencyNo=getCacheContractAttribute(this.CommodityNo, "CurrencyNo");
	return this;
}

// 资金账户实体
function AccountVO(jAccount) {
	
	this.ClientNo = jAccount.ClientNo;
	this.ClientName = jAccount.ClientName;
	this.AccountNo = jAccount.AccountNo;
	this.CurrencyNo = jAccount.CurrencyNo;
	this.CurrencyName = jAccount.CurrencyName;
	this.CurrencyRate = jAccount.CurrencyRate;
	this.OldCanUse = jAccount.OldCanUse;
	this.OldBalance = jAccount.OldBalance;
	this.OldCanCashOut = jAccount.OldCanCashOut;
	this.OldAmount = jAccount.OldAmount;
	this.InMoney = jAccount.InMoney;
	this.OutMoney = jAccount.OutMoney;
	this.TodayBalance = jAccount.TodayBalance;
	this.TodayCanUse = jAccount.TodayCanUse;
	this.TodayCanCashOut = jAccount.TodayCanCashOut;
	this.TodayAmount = jAccount.TodayAmount;
	this.CounterFee = jAccount.CounterFee;
	this.CloseProfit = jAccount.CloseProfit;
	this.DayCloseProfit = jAccount.DayCloseProfit;
	this.FloatingProfit = jAccount.FloatingProfit;
	this.DayFloatingProfit = jAccount.DayFloatingProfit;
	this.Premium = jAccount.Premium;
	this.Deposit = jAccount.Deposit;
	this.TotalProfit = jAccount.TotalProfit;
	this.KeepDeposit = jAccount.KeepDeposit;
	this.FrozenMoney = jAccount.FrozenMoney;
	this.UnExpiredProfit = jAccount.UnExpiredProfit;
	this.UnAccountProfit = jAccount.UnAccountProfit;
	this.RiskRate = jAccount.RiskRate;
	return this;
}