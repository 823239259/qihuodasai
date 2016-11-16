/**
 * websocket地址
 */
var tradeWebsocketUrl = null; 
var tradeWebsocketModelUrl = null;
var tradeWebSocketVersion = null;
var appVersion = null;
function tradeConfigInstance(){
	this.url = tradeSocketUrl;
	this.tradeSocketModelUrl = tradeSocketModelUrl;
	this.tradeSocketVersion = tradeSocketVersion;
	this.appVersion = tradeAppVersion;
	return this;
}
/**
 * 加载服务器地址
 */
function initTradeConfig(){
	var configObject = tradeConfigInstance();
	setTradeWebSoketUrl(configObject);
}
/**
 * 设置交易地址
 * @param obj
 */
function setTradeWebSoketUrl(obj){
	if(obj != null){
		tradeWebsocketUrl = obj.url;
		tradeWebsocketModelUrl = obj.TradeSocketModelUrl;
		tradeSocketVersion = obj.tradeSocketVersion;
		appVersion = obj.appVersion;
	}
}
/**
 * 设置交易模式  isMock:0-实盘，1-模拟盘
 */
function setTradeWebSocketIsMock(isMock){
	tradeWebSocketIsMock = isMock;
}