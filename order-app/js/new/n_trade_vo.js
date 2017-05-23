// 持仓实体
function HoldOrderVO(jHoldOrder) {
	//	名称	说明	数据类型	长度	出现次数
	//	ClientNo	交易账号	String		
	//	ExchangeNo	交易所编号	String		
	//	CurrencyNo	币种	String		停止使用
	//	CommodityNo	商品代码	String		
	//	ContractNo	合约代码	String		
	//	ContractCode	组合合约号	String		停止使用
	//	TradeDateTime	成交日期	String		
	//	TradeNo	成交号	String		
	//	Drection	买卖方向（0买1卖）	Int		
	//	HoldNum	持仓数量	Int		
	//	OpenAvgPrice	开仓均价	Double		
	//	HoldAvgPrice	持仓均价	Double		
	//	HoldStatus	持仓状态（0昨仓，1今仓）	int		
	//	FloatingProfit	浮动盈亏	Double		停止使用
	this.ClientNo = jHoldOrder.ClientNo;
	this.ExchangeNo = jHoldOrder.ExchangeNo;
//	this.CurrencyNo = jHoldOrder.CurrencyNo;
	this.CommodityNo = jHoldOrder.CommodityNo;
	this.ContractNo = jHoldOrder.ContractNo;
//	this.ContractCode = jHoldOrder.ContractCode;
	this.TradeDateTime = jHoldOrder.TradeDateTime;
	this.TradeNo = jHoldOrder.TradeNo;
	this.Drection = jHoldOrder.Drection;
	this.HoldNum = jHoldOrder.HoldNum;
	this.OpenAvgPrice = jHoldOrder.OpenAvgPrice;
	this.HoldAvgPrice = jHoldOrder.HoldAvgPrice;
	this.HoldStatus = jHoldOrder.HoldStatus;
//	this.FloatingProfit = jHoldOrder.FloatingProfit;
	return this;
}

// 挂单实体
function ApplyOrderVO(jApplyOrder) {
	return new OrderVO(jApplyOrder);
}

// 委托信息实体
function OrderVO(jOrder) {
	//	名称	说明	数据类型	长度	出现次数
	//	ClientNo	账户号	String		停止使用
	//	OrderSysID	系统编号，交易服务器产生	String		为显示预留，不能作为更新的key
	//	OrderRef	报单引用，用户自己生成	String		
	//	OrderID	订单号	String		
	//	ExchangeNo	交易所编号	String		
	//	CommodityNo	品种代码	String		
	//	ContractNo	合约代码	String		
	//	ContractCode	组合合约号（品种+合约）	String		停止使用
	//	OrderNum	订单数量	Int		
	//	Drection	买卖方向（0：买，1：卖）	Int		
	//	PriceType	价格类型:限价0，市价1	Int		
	//	OrderPrice	订单价格，如果是市价可以不填这个字段	Double		
	//	TriggerPrice	触发价	Double		
	//	TradeNum	成交手数	Int		
	//	TradePrice	成交均价	Double		
	//	OrderStatus	订单状态:
	//						0-单已提交
	//						1-排队中
	//						2-部分成交
	//						3-完全成交
	//						4-已撤单
	//						5-下单失败
	//						6-未知	Int		
	//	StatusMsg	状态说明	String		
	//	InsertDateTime	下单日期时间	String		
	//	ValidDate	有效日期（直达为空）	String		停止使用
	//	InsertUser	下单人	String		
//	this.ClientNo = jOrder.ClientNo;
	this.OrderSysID = jOrder.OrderSysID;
	this.OrderRef = jOrder.OrderRef;
	this.OrderID = jOrder.OrderID;
	this.ExchangeNo = jOrder.ExchangeNo;
	this.CommodityNo = jOrder.CommodityNo;
	this.ContractNo = jOrder.ContractNo;
//	this.ContractCode = jOrder.ContractCode;
	this.OrderNum = jOrder.OrderNum;
	this.Drection = jOrder.Drection;
	this.PriceType = jOrder.PriceType;
	this.OrderPrice = jOrder.OrderPrice;
	this.TriggerPrice = jOrder.TriggerPrice;
	this.TradeNum = jOrder.TradeNum;
	this.TradePrice = jOrder.TradePrice;
	this.OrderStatus = jOrder.OrderStatus;
	this.StatusMsg = jOrder.StatusMsg;
	this.InsertDateTime = jOrder.InsertDateTime;
//	this.ValidDate = jOrder.ValidDate;
	this.InsertUser = jOrder.InsertUser;
	return this;
}

// 成交
function TradeOrderVO(jTradeOrder) {
	//	名称	说明	数据类型	长度	出现次数
	//	ClientNo	账户号	String		停止使用
	//	TradeNo	成交号	String		
	//	CommodityNo	品种代码	String		
	//	ExchangeNo	交易所编号	String		
	//	ContractNo	合约代码	String		
	//	ContractCode	组合合约号（品种+合约）	String		停止使用
	//	OrderSysID	系统编号，交易服务器产生	String		
	//	OrderRef	报单引用，用户自己生成	String		
	//	OrderID	订单号	String		
	//	Drection	买卖方向（0：买，1：卖）	Int		
	//	TradeNum	成交手数	Int		
	//	TradePrice	成交价格	Double		
	//	TradeDateTime	成交时间	String		
	//	TradeFee	成交总的手续费	Double		
	//	CurrencyNo	币种	String		停止使用
//	this.ClientNo = jTradeOrder.ClientNo;
	this.TradeNo = jTradeOrder.TradeNo;
	this.CommodityNo = jTradeOrder.CommodityNo;
	this.ExchangeNo = jTradeOrder.ExchangeNo;
	this.ContractNo = jTradeOrder.ContractNo;
//	this.ContractCode = jTradeOrder.ContractCode;
	this.OrderSysID = jTradeOrder.OrderSysID;
	this.OrderRef = jTradeOrder.OrderRef;
	this.OrderID = jTradeOrder.OrderID;
	this.Drection = jTradeOrder.Drection;
	this.TradeNum = jTradeOrder.TradeNum;
	this.TradePrice = jTradeOrder.TradePrice;
	this.TradeDateTime = jTradeOrder.TradeDateTime;
	this.TradeFee = jTradeOrder.TradeFee;
//	this.CurrencyNo = jTradeOrder.CurrencyNo;
	return this;
}

// 资金账户实体
function AccountVO(jAccount) {
	//	名称	说明	数据类型	长度	备注
	//	ClientNo	交易账号	String		
	//	ClientName	用户姓名	String		停止使用
	//	AccountNo	账户ID	String		停止使用
	//	CurrencyNo	币种	String		
	//	CurrencyName	货币名称	String		
	//	CurrencyRate	汇率（与基币）	Double		
	//	OldCanUse	昨可用	Double		
	//	OldBalance	昨权益	Double		
	//	OldCanCashOut	昨可提	Double		
	//	OldAmount	昨结存	Double——————上个交易日结算的资金，不参与任何计算
	//	InMoney	入金	Double		
	//	OutMoney	出金	Double		
	//	TodayBalance	今权益	Double		停止使用————客户端计算
	//	TodayCanUse	今可用	Double		停止使用————客户端计算
	//	TodayCanCashOut	今可提	Double		
	//	TodayAmount	今结存	Double——————
	//	CounterFee	佣金（手续费）	Double		
	//	CloseProfit	逐笔平盈	Double		
	//	DayCloseProfit	盯市平盈	Double		
	//	FloatingProfit	逐笔浮盈	Double		
	//	DayFloatingProfit	盯市浮盈	Double		
	//	Premium	权利金	Double		
	//	Deposit	保证金	Double		
	//	TotalProfit	净盈利	Double		
	//	KeepDeposit	维持保证金	Double		
	//	FrozenMoney	冻结资金	Double		
	//	UnExpiredProfit	未到期平盈	Double		
	//	UnAccountProfit	未结平盈	Double		
	//	RiskRate	风险率	Double		
	this.ClientNo = jAccount.ClientNo;
//	this.ClientName = jAccount.ClientName;
//	this.AccountNo = jAccount.AccountNo;
	this.CurrencyNo = jAccount.CurrencyNo;
	this.CurrencyName = jAccount.CurrencyName;
	this.CurrencyRate = jAccount.CurrencyRate;
	this.OldCanUse = jAccount.OldCanUse;
	this.OldBalance = jAccount.OldBalance;
	this.OldCanCashOut = jAccount.OldCanCashOut;
	this.OldAmount = jAccount.OldAmount;
	this.InMoney = jAccount.InMoney;
	this.OutMoney = jAccount.OutMoney;
//	this.TodayBalance = jAccount.TodayBalance;
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

// 资金变化实体
function MoneyVO(jMoney) {
	//	名称	说明	数据类型	长度	出现次数
	//ClientNo	交易账号	String		
	//CurrencyNo	币种	String		
	//TodayBalance	今权益（停止使用） 请客户端根据浮动盈亏更新【今权益】和【今可用】字段	Double		停止使用
	//TodayAmount	今结存	Double		
	//CloseProfit	平仓盈亏	Double		
	//FrozenMoney	冻结资金	Double		
	//Fee	手续费	Double		
	//Deposit	保证金	Double		
	//KeepDeposit	维持保证金	Double		
	//InMoney	入金	Double		
	//OutMoney	出金	Double		
	//UnExpiredProfit	未到期平盈	Double		
	//UnAccountProfit	未结平盈	Double		
	//floatingProfit	浮盈	Double		停止使用
	//TodayCanUse	今可用	Double		停止使用
	this.ClientNo = jMoney.ClientNo;
	this.CurrencyNo = jMoney.CurrencyNo;
//	this.TodayBalance = jMoney.TodayBalance;
	this.TodayAmount = jMoney.TodayAmount;
	this.CloseProfit = jMoney.CloseProfit;
	this.FrozenMoney = jMoney.FrozenMoney;
	this.Fee = jMoney.Fee;
	this.Deposit = jMoney.Deposit;
	this.KeepDeposit = jMoney.KeepDeposit;
	this.InMoney = jMoney.InMoney;
	this.OutMoney = jMoney.OutMoney;
	this.UnExpiredProfit = jMoney.UnExpiredProfit;
	this.UnAccountProfit = jMoney.UnAccountProfit;
//	this.floatingProfit = jMoney.floatingProfit;
//	this.TodayCanUse = jMoney.TodayCanUse;
	return this;
}

/**
 * 合约浮盈实体
 * 
 * @param {Object} currencyNo	币种
 * @param {Object} floatingProfit		盈亏
 */
function ContractFloatingProfitVO(jContractFloatingProfitVO) {
	this.currencyNo = jContractFloatingProfitVO.currencyNo;
	this.floatingProfit = jContractFloatingProfitVO.floatingProfit;
	return this;
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
	 * 历史成交记录url
	 */
	QryHisTradeUrl: "QryHisTrade",
	/**
	 * 获取持仓信息url
	 */
	QryHoldUrl: "QryHoldTotal",
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
// 订单状态。	eg：OrderType.getOrderTypeName("4");
var OrderType = {
	"0": "订单已提交",
	"1": "排队中",
	"2": "部分成交",
	"3": "完全成交",
	"4": "已撤单",
	"5": "下单失败",
	"6": "未知",

	getOrderTypeName: function(orderType) {
		return this[orderType];
	}
};