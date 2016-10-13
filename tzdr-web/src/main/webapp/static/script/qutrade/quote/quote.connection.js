var quoteSocket = null;
var quote_username = "13677622344";
var quote_password = "a123456";
/**
 * 行情是否连接成功,true-成功，false-失败
 */
var quoteConnectStatus = false;
/**
 * 更新连接状态
 */
function changeQuoteConnectionStatus(){
	if(quoteSocket == null){
		quoteConnectStatus = false;
	}else{
		quoteConnectStatus = true;
	}
}
/**
 * 连接行情服务器
 */
function quoteConnection(){
	quoteSocket = new WebSocket(quoteWebsocketUrl);
}
/**
 * 行情初始化加载
 */
function initQuoteLoad(){
	if(quoteSocket == null)return false;
	quoteSocket.onopen = function(){
		Quote.doLogin(quote_username, quote_password);
	}
	quoteSocket.onmessage = function(evt) {
		quoteHandleData(evt);
	}
	quoteSocket.onclose = function() {
		quoteSocket = null;
		initQuote();
	}
	return true;
}
/**
 * 初始化行情
 */
function initQuote(){
	/**
	 * 行情配置加载 -- > quote.config
	 */
	initQuoteConfig();
	/**
	 * 行情连接  --> quote.connection
	 */
	quoteConnection();
	/**
	 * 行情数据加载
	 */
	initQuoteLoad();
	/*var quoteInterval = setInterval(function(){
		var result = initQuoteLoad();
		if(result){
			clearInterval(quoteInterval);
		}
	}, 500);*/
}