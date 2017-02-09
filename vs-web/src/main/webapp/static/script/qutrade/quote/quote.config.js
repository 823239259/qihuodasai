/**
 * 生成环境：live,测试环境:dev
 */
var model = "dev";
/**
 * websocket地址
 */
var quoteWebsocketUrl = null;
/**
 * 标识用户登录的端
 */
var Source="web";
/**
 * 定义生产环境
 */
function quoteConfigNewinstance(){
	this.url = "ws://quote.vs.com:9002";
	return this;
}
/**
 * 定义测试环境服务器
 */
function quoteConfigNewinstanceTest(){
	this.url = "ws://quote.vs.com:9002";
	return this;
}
/**
 * 加载服务器地址
 */
function initQuoteConfig(){
	var configObject = null;
	if(model == "live"){
		configObject = quoteConfigNewinstance();
	}else if(model == "dev"){
		configObject = quoteConfigNewinstanceTest();
	}
	setQuoteWebSoketUrl(configObject);
}
/**
 * 设置交易地址
 * @param obj
 */
function setQuoteWebSoketUrl(obj){
	if(obj != null){
		this.quoteWebsocketUrl = obj.url;
	}
}