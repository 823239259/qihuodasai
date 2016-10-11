/**
 * 获取url参数 
 * @param {Object} name
 */
function getUrlParam(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
	var r = window.location.search.substr(1).match(reg); //匹配目标参数
	if(r != null) return unescape(r[2]);
	return null; //返回参数值
}
/**
 * 判空 
 * @param {Object} str
 * @return {Boolean} 如果为空返回true,否则false
 */
function isEmpty(str) {
	return str == null || str == undefined || str.length == 0 || str.length == "undefined" ? true : false;
}
/**
 *  设置cookie
 * @param {Object} key
 * @param {Object} name
 * @param {Object} time
 */
function setCookie(key, name , time) {
	var Days = 30;
	var exp = new Date();
	exp.setTime(exp.getTime() + Days*24*60*60*1000);
	document.cookie = key + "="+ escape (name) + ";expires=" + exp.toGMTString();
}
/**
 * 获取cookie
 * @param {Object} key
 */
function getCookie(key){
	var arr,reg=new RegExp("(^| )"+key+"=([^;]*)(;|$)");
	if(arr=document.cookie.match(reg))
		return unescape(arr[2]);
	else
		return null;
}
/**
 * 删除cookie
 * @param {Object} key
 */
function delCookie(key){
	var exp = new Date();
	exp.setTime(exp.getTime() - 1);
	var cval=getCookie(key);
	if(cval!=null)
		document.cookie= key + "="+cval+";expires="+exp.toGMTString();
}
/**
 * 验证是否是JSON对象
 * @param obj
 * @returns {Boolean}
 */
function isJson(obj){
	var isjson = typeof(obj) == "object" && Object.prototype.toString.call(obj).toLowerCase() == "[object object]" && !obj.length; 
	return isjson;
}
/**
 * 提示询问层
 * @param tipContent
 */
function tipConfirm(tipContent,successCallBack,cancleCallBack){
	//询问框
	layer.confirm(tipContent+"?", {
	  btn: ['确认','取消'] //按钮
	}, function(){
		successCallBack();
	}, function(){
		cancleCallBack();
	});
}
function cancleCallBack(){}
/**
 * 弹出层
 * @param tipContent
 */
function tipAlert(tipContent){
	layer.alert(tipContent);
}
/**
 * 提示层
 * @param tioContent
 */
function tip(tipContent){
	layer.msg(tipContent);
}
