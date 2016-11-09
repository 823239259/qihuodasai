/**
 * 生成环境：live,测试环境:dev
 */
var model = "dev";
/**
 * websocket地址
 */
var tradeWebsocketUrl = null;
/**
 * websocket模式,0-实盘，1-模拟盘
 */
var tradeWebSocketIsMock = 0;
/**
 * 全局使用的交易配置对象
 */
var tradeWebSocket = null;
/**
 * 定义生产环境
 */
function tradeConfigNewinstance(){
	this.url = "ws://socket.vs.com:6060";
	this.isMockUrl = "ws://139.224.24.206:6066";
	return this;
}
/**
 * 定义测试环境服务器
 */
function tradeConfigNewinstanceTest(){
	//this.url = "ws://192.168.0.213:6060";
	this.url = "ws://139.224.24.206:6066";
	this.isMockUrl = "ws://139.224.24.206:6066";
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
		if(tradeWebSocketIsMock == 0){
			this.tradeWebsocketUrl = tradeWebSocket.url;
		}else if(tradeWebSocketIsMock == 1){
			this.tradeWebsocketUrl = tradeWebSocket.isMockUrl;
		}
	}
}
/**
 * 设置登录盘口模式
 * @param ismock 0-实盘，1-模拟盘
 */
function setTradeWebSocketIsMock(ismock){
	tradeWebSocketIsMock = ismock;
}
