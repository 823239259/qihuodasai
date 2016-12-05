var tradeSocketUrl = "";
var tradeSocketModelUrl = "";
var tradeSocketVersion = "";
var tradeAppVersion = "";
var marketSocketUrl = "";
var marketUserName = "";
var marketPassword = "";
/**
 * 全局保存交易配置对象
 */
var tradeWebSocketConfig = null;
/**
 * 全局保存行情配置对象
 */
var marketSocketConfig = null;
/**
 * 登录模式：0-实盘，1-模拟盘
 */
var tradeWebSocketIsMock = localStorage.getItem("isMock");
/**
 * 交易配置配置
 */
loadConfig();
/**
 * 初始加载交易行情配置数据
 */
function loadConfig(){ 
	marketSocketUrl = tzdr.constants.MarketSocketUrl;
	marketUserName = tzdr.constants.MarketUsername;
	marketPassword = tzdr.constants.MarketPassword;
	tradeSocketUrl = tzdr.constants.TradeSocketUrl;;
	tradeSocketModelUrl = tzdr.constants.TradeSocketModelUrl;
	tradeSocketVersion = tzdr.constants.tradeSocketVersion;
	tradeAppVersion = tzdr.constants.tradeAppVersion;
}
