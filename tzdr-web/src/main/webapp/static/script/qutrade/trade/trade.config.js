/**
 * 生成环境：live,测试环境:dev
 */
var model = "dev";
/**
 * websocket地址
 */
var tradeWebsocketUrl = null;
/**
 * 模拟socket地址
 */
var tradeWebSocketModelUrl = null;
/**
 * websocket模式,0-实盘，1-模拟盘
 */
var tradeWebSocketIsMock = 0;
/**
 * websocket交易版本
 */
var tradeWebSocketVersion = null;
/**
 * webcms后台配置版本
 */
var tradeWebCmsVersion = null;
/**
 * 全局使用的交易配置对象
 */
var tradeWebSocket = null;
/**
 * 定义生产环境
 */
function tradeConfigNewinstance(){
	this.url = "";
	this.isMockUrl = "";
	this.version = "";
	this.webVersion = "3.0.0";
	return this;
}
/**
 * 定义测试环境服务器
 */
function tradeConfigNewinstanceTest(){
	//this.url = "ws://192.168.0.213:6060";
	this.url = "";
	this.isMockUrl = "";
	this.version = "";
	this.webVersion = "3.0.0";
	return this;
}
/**
 * 加载服务器地址
 */
function initTradeConfig(){
	if(model == "live"){
		tradeWebSocket = tradeConfigNewinstance();
	}else if(model == "dev"){
		tradeWebSocket = tradeConfigNewinstanceTest();
	}
	setTradeWebSoketUrl();
}
/**
 * 设置交易地址
 * @param obj
 */
function setTradeWebSoketUrl(){
	if(tradeWebSocket != null){
		tradeWebsocketUrl = tradeWebSocket.url;
		tradeWebSocketModelUrl = tradeWebSocket.isMockUrl;
		tradeWebSocketVersion = tradeWebSocket.version;
		tradeWebCmsVersion = tradeWebSocket.webVersion;
	}
}
/**
 * 设置登录盘口模式
 * @param ismock 0-实盘，1-模拟盘
 */
function setTradeWebSocketIsMock(ismock){
	tradeWebSocketIsMock = ismock;
}
