/**
 * websocket地址
 */
var tradeWebsocketUrl = null;
function tradeConfigInstance(){
	this.url = tradeSocketUrl;
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
		this.tradeWebsocketUrl = obj.url;
	}
}