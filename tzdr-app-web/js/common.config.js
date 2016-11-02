var model = "dev";
var tradeSocketUrl = "";
var marketSocketUrl = "";
var marketUserName = "";
var marketPassword = "";
/**
 * 交易配置配置
 */
var TradeConfig = {
	TradeSocketUrl:"ws://trade.vs.com:6060"
}
/**
 * 测试交易配置
 */ 
var TradeConfigTest = {
//	TradeSocketUrl:"ws://139.224.24.206:6066"
//	TradeSocketUrl:"ws://192.168.2.123:6060"
//	TradeSocketUrl:"ws://192.168.0.213:6060"
	TradeSocketUrl:"ws://139.224.24.206:6066"
	
}
/**
 * 行情配置
 */
var MarketConfig = {
	MarketSocketUrl:"ws://quote.vs.com:9002",
	username:"13677622344",
	password:"a123456"
}
/**
 * 测试行情配置
 */
var MarketConfigTest = { 
//	MarketSocketUrl:"ws://192.168.0.213:9002",
	MarketSocketUrl:"ws://quote.vs.com:9002",
	username:"13677622344",
	password:"a123456"
}
loadConfig();
/**
 * 初始加载交易行情配置数据
 */
function loadConfig(){ 
	if(model == "dev"){
		tradeSocketUrl = TradeConfigTest.TradeSocketUrl;
		marketSocketUrl = MarketConfigTest.MarketSocketUrl;
		marketUserName = MarketConfigTest.username;
		marketPassword = MarketConfigTest.password;
	}else if(model == "live"){
		tradeSocketUrl = TradeConfig.TradeSocketUrl;
		marketSocketUrl = MarketConfig.MarketSocketUrl;
		marketUserName = MarketConfig.username;
		marketPassword = MarketConfig.password;
	}
}
