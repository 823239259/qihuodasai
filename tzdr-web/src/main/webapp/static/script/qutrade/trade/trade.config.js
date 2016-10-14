/**
 * 生成环境：live,测试环境:dev
 */
var model = "live";
/**
 * websocket地址
 */
var tradeWebsocketUrl = null;
/**
 * 定义生产环境
 */
function tradeConfigNewinstance(){
	this.url = "ws://socket.vs.com:6060";
	return this;
}
/**
 * 定义测试环境服务器
 */
function tradeConfigNewinstanceTest(){
	this.url = "ws://192.168.0.213:6060";
	return this;
}
/**
 * 加载服务器地址
 */
function initTradeConfig(){
	var configObject = null;
	if(model == "live"){
		configObject = tradeConfigNewinstance();
	}else if(model == "dev"){
		configObject = tradeConfigNewinstanceTest();
	}
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