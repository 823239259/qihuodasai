var model = "live";
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

function TradeConfig(){
	this.TradeSocketUrl = "";//ws://139.196.228.143:6066
	this.TradeSocketModelUrl = "";
	this.tradeSocketVersion = "";
	this.tradeAppVersion = "3.3";
	return this;
}

function TradeConfigTest(){ 
	this.TradeSocketUrl = "";//"ws://139.196.228.143:6066
	this.TradeSocketModelUrl = "";
	this.tradeSocketVersion = "";
	this.tradeAppVersion = "3.3";
	return this; 
}

function MarketConfig(){
	this.MarketSocketUrl = "ws://quote.vs.com:9002";
	this.username = "13677622344";
	this.password = "a123456";
	return this;
}
function MarketConfigTest(){
	this.MarketSocketUrl = "ws://192.168.0.232:9002";
	this.username = "13677622344";
	this.password = "a123456";
	return this;
}

loadConfig();
/**
 * 初始加载交易行情配置数据
 */
function loadConfig(){ 
	if(model == "dev"){
		tradeWebSocketConfig = TradeConfigTest();
		marketSocketConfig = MarketConfigTest();
	}else if(model == "live"){
		tradeWebSocketConfig = TradeConfig();
		marketSocketConfig = MarketConfig();
	}
	marketSocketUrl = marketSocketConfig.MarketSocketUrl;
	marketUserName = marketSocketConfig.username;
	marketPassword = marketSocketConfig.password;
	setTradeWebSocketUrlConfig();
}
function setTradeWebSocketUrlConfig(){
	tradeSocketUrl = tradeWebSocketConfig.TradeSocketUrl;
	tradeSocketModelUrl = tradeWebSocketConfig.TradeSocketModelUrl;
	tradeSocketVersion = tradeWebSocketConfig.tradeSocketVersion;
	tradeAppVersion = tradeWebSocketConfig.tradeAppVersion;
}
