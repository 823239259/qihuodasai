var socket = null;
/**
 * 交易地址
 */
var socketUrl = null;
/**
 * 用户名
 */
var username =localStorage.getItem("trade_account");
/**
 * 密码
 */
var password = localStorage.getItem("trade_password");
/**
 * 缓存的用户名
 */
var endLoginAccount = localStorage.getItem("trade_endLoginAccount");
/**
 * 缓存的密码
 */
var endLoginPassword = localStorage.getItem("trade_endLoginPassword");
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
 * 连接交易服务器的计时Id
 */
var tradeIntervalId = null;
/**
 * 版本是否获取成功
 */
var isGetVersion = false;
/**
 * 设置登录状态
 * @param flag
 */
function setIsLogin(flag){
	isLogin = flag;
}
/**
 * 验证是否登录
 */
function vadationIsLogin(){
	if(!isLogin){
		alertProtype("你还未登录,请先登录","提示",Btn.confirmedAndCancle(),openLogin);
		return false;
	} 
	return true;
}
/**
 * 验证是否登录
 */
function vadationIsLoginMuiTip(){
	if(isLogin){
		return true;
	}else{
		tip("未登录,请先登录");
		return false
	}
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
	if(socketUrl.length > 0){
		socket = new WebSocket(socketUrl);  
	}
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
function loginOut(){ 
	localStorage.removeItem("trade_account");
	localStorage.removeItem("trade_password");
	localStorage.removeItem("isMock");
	username = null;
	password = null;
	socket = null;
	setIsLogin(false);
	clearTradListData();
	reconnectInit();
	/*clearTradListData();
	clearLocalCacheData();*/
}
/**
 * 缓存本地登录
 * @param {Object} account 登录用户名
 * @param {Object} password 登录密码
 */
function loginCache(account,password){
	localStorage.setItem("trade_account",account);
	localStorage.setItem("trade_password",password);
	localStorage.setItem("trade_endLoginAccount",account);
	localStorage.setItem("trade_endLoginPassword",password);
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
}
/**
 * 根据交易模式设置交易配置信息
 * @param ismock
 */
function setTradeConfig(ismock){
	setTradeWebSocketIsMock(ismock);
	if(tradeWebSocketIsMock == 0){
		socketUrl = tradeWebsocketUrl;
	}else if(tradeWebSocketIsMock == 1){
		socketUrl = tradeWebsocketModelUrl;
	}
}
/**
 * 交易初始化加载
 */
function initLoad() {
	    plus.nativeUI.showWaiting("正在连接交易服务器...");
	    if(socket == null){
	    	return;
	    }
		socket.onopen = function() {   
			/*layer.closeAll();*/ 
			Trade.doLogin(username , password,tradeWebSocketIsMock,tradeWebSocketVersion); 
			//更新交易连接状态
			changeConnectionStatus();
		}
		socket.onmessage = function(evt) {
			handleData(evt);
		} 
		socket.onclose = function() {
			clearInterval(tradeIntervalId);
			socket = null;
			//更新交易连接状态
			changeConnectionStatus();
			//不是手动登出，则重连交易服务器
			if(loginFail == false){ 
				//交易连接断开重连
				tradeReconnect();
			}else{
				if(anotherPlace && loginFail){
					alertProtype("您的账号在另一地点登录，您被迫下线。如果不是您本人操作，那么您的密码很可能已被泄露，建议您及时致电：400-852-8008","下线提示",Btn.confirmed(),null,null,null);
					//clearLocalCacheData();
					loginOut();
				}
			}
		}
}
/**
 * 重新连接交易服务器
 */
function tradeReconnect(){
	//layer.msg('交易连接断开,正在重新连接...', {icon: 16});
	if(socket == null){
		initTrade();
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
	 * 交易数据初始化加载 --> trade.connections
	 */
	initLoad();
	tradeIntervalId = setInterval(function(){
			/*layer.msg('正在连接交易服务器...', {icon: 16});*/
			if(connectionStatus){
				/*layer.msg('交易服务器连接成功', {icon: 4});*/
				clearInterval(tradeIntervalId);
			}
		}
	, 2000);
}
function initTrade(){
	/**
	 * 交易登录（初始化） -->trade.connection
	 */
	tradeLogin();
}


/**
 * 获取版本信息
 */
function getVersion(){ 
	$.ajax({ 
		url:tzdr.constants.api_domain+"/socket/config/getVersions",
		type:"post", 
		data:{
			appVersions:appVersion
		},
		timeout:5000,
		success:function(result){
			if(result.success){
				var data = result.data; 
				tradeWebsocketUrl = data.socketUrl;
				tradeWebSocketVersion = data.socketVersion;
				tradeWebsocketModelUrl = data.socketModelUrl;
				isGetVersion = true; 
			} 
		} ,
		error:function(result){
		}
	});  
}
/**
 * 验证socket版本是否获取成功
 */
function validateIsGetVersion(){
	var i = 0;
	var initIsGetVersion = setInterval(function(){
		i++;
//		console.log(i);
		if(isGetVersion == false){
			if(i > 50){ 
				isGetVersion = true;
			}
		}
		if(isGetVersion == true){
			initSocketTrade();
			clearInterval(initIsGetVersion);
		}
	}, 200);
}
