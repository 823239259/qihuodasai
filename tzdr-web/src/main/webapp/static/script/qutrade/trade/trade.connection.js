var socket = null;
/**
 * 用户名
 */
var username = getCookie("trade_account");
/**
 * 密码
 */
var password = getCookie("trade_password");
/**
 * 缓存的用户名
 */
var endLoginAccount = getCookie("trade_endLoginAccount");
/**
 * 缓存的密码
 */
var endLoginPassword = getCookie("trade_endLoginPassword");
/**
 * 交易是否连接成功,true-成功，false-失败
 */
var connectionStatus = false;
/**
 * 是否登陆成功 ,true -登录，false -未登录
 */
var isLogin = false;
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
	delCookie("trade_account");
	delCookie("trade_password");
	username = null;
	password = null;
	$("#show_login").show();
	$("#show_user_info").hide();
	socket = null;
	setIsLogin(false);
	
	/*initTrade();*/
}
/**
 * 缓存本地登录
 * @param {Object} account 登录用户名
 * @param {Object} password 登录密码
 */
function loginCache(account,password){
	setCookie("trade_account",account,"s600");
	setCookie("trade_password",password,"s600");
	setCookie("trade_endLoginAccount",account,"s600");
	setCookie("trade_endLoginPassword",password,"s600");
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
function tradeLoginOut(account){
	Trade.doLoginOut(username);
	loginOut();
}
/**
 * 交易初始化加载
 */
function initLoad() {
	if (socket == null) return;
	socket.onopen = function() {
		Trade.doLogin(username , password);
		changeConnectionStatus();
	}
	socket.onmessage = function(evt) {
		handleData(evt);
	}
	socket.onclose = function() {
//		reconnect();
	}
}

/**
 * 初始化交易
 */
function initTradeConnect(){
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
