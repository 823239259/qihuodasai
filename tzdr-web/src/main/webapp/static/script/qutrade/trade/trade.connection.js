var socket = null;
/**
 * 用户名
 */
var username = getTradeCookie("trade_account");
/**
 * 密码
 */
var password = getTradeCookie("trade_password");
/**
 * 缓存的用户名
 */
var endLoginAccount = getTradeCookie("trade_endLoginAccount");
/**
 * 缓存的密码
 */
var endLoginPassword = getTradeCookie("trade_endLoginPassword");
/**
 * 交易是否连接成功,true-成功，false-失败
 */
var connectionStatus = false;
/**
 * 是否登陆成功 ,true -登录，false -未登录
 */
var isLogin = false;
/**
 * 登录时，登录失败
 */
var loginFail = false;
/**
 * 是否在另一处登录
 */
var anotherPlace = false;
/**
 * 设置登录状态
 * @param flag
 */
function setIsLogin(flag){
	isLogin = flag;
}
/**
 * 更新连接状态
 */
function changeConnectionStatus(){
	if(socket == null){
		connectionStatus = false;
	}else{
		connectionStatus = true;
	}
}
/**
 * 交易连接
 */
function tradeConnection(){
	socket = new WebSocket(tradeWebsocketUrl);
}
/**
 * 交易连接断开的处理
 */
function tradeConnectionClose(){
	Trade.doLoginOut(username);
	socket == null;
	changeConnectionStatus();
	setIsLogin(false);
}
/**
 * 本地退出登录
 */
function loginOut(account,password){
	delTradeCookie("trade_account");
	delTradeCookie("trade_password");
	username = null;
	password = null;
	$("#show_login").show();
	$("#show_user_info").hide();
	socket = null;
	setIsLogin(false);
	clearTradListData();
	clearLocalCacheData();
	/*initTrade();*/
}
/**
 * 缓存本地登录
 * @param {Object} account 登录用户名
 * @param {Object} password 登录密码
 */
function loginCache(account,password){
	setTradeCookie("trade_account",account);
	setTradeCookie("trade_password",password);
	setTradeCookie("trade_endLoginAccount",account);
	setTradeCookie("trade_endLoginPassword",password);
}
/**
 * 交易登录
 * @param account
 * @param password
 */
function tradeLogin(){
	if(username != null && password != null){
		initTradeConnect();
		var loginInterval = setInterval(function(){
			if(connectionStatus && isLogin){
				loginCache(username , password);
				clearInterval(loginInterval);
			}
		}, 500); 
	}
}
/**
 * 交易登录退出
 * @param account
 */
function tradeLoginOut(){
	Trade.doLoginOut(username);
	loginOut();
	layer.closeAll();
}
/**
 * 交易初始化加载
 */
function initLoad() {
	if (socket == null) return false;
	socket.onopen = function() {
		layer.closeAll();
		Trade.doLogin(username , password);
		changeConnectionStatus();
	}
	socket.onmessage = function(evt) {
		handleData(evt);
	}
	socket.onclose = function() {
		socket = null;
		//更新交易连接状态
		changeConnectionStatus();
		if(anotherPlace){
			tipAlert("你的账号已在另一处登录,如不是本人操作,请联系客服");
			clearLocalCacheData();
			loginOut();
		}else{
			//不是手动登出，则重连交易服务器
			if(!loginFail){
				//交易连接断开重连
				reconnect();
			}
		}
	}
	return true;
}

/**
 * 初始化交易
 */
function initTradeConnect(){
	layer.msg('正在连接交易服务器...', {icon: 16});
	/**
	 * 交易连接 -->trade.connection
	 */
	tradeConnection();
	/**
	 * 交易数据初始化加载 --> trade.connection
	 */
	initLoad();
	
}
function initTrade(){
	/**
	 * 初始化交易配置 --> trade.config
	 */
	initTradeConfig();
	/**
	 * 交易登录（初始化） -->trade.connection
	 */
	tradeLogin();
}
/**
 * 重新连接交易服务器
 */
function reconnect(){
	layer.msg('交易连接断开,正在重新连接...', {icon: 16});
	if(socket == null){
		initTrade();
	}
}
