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
function setTradeCookie(key, name) {
	var Days = 30;
	var exp = new Date();
	exp.setTime(exp.getTime() + Days*24*60*60*1000);
	document.cookie = key + "="+ escape (name) + ";expires=";
}
/**
 * 获取cookie
 * @param {Object} key
 */
function getTradeCookie(key){
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
function delTradeCookie(key){
	var exp = new Date();
	exp.setTime(exp.getTime() - 1);
	var cval=getTradeCookie(key);
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
/**
 * 获取日期格式 YYYY-MM-DD
 * @param {Object} date
 */
function formatDateYYYMMDD(date) {  
    var y = date.getFullYear();  
    var m = date.getMonth() + 1;  
    m = m < 10 ? '0' + m : m;  
    var d = date.getDate();  
    d = d < 10 ? ('0' + d) : d;
    return y + '-' + m + '-' + d;  
}; 
/**
 * 获取时间格式
 * @param {Object} now
 */
function formatDateHHMMSS(now){
      var   hour=now.getHours();     
      var   minute=now.getMinutes();  
      var   ss = now.getSeconds();
    return hour+":"+minute+":"+ss;
}
/**
 * 字符串保留小数
 * @param {Object} value
 * @param {Object} size
 */
function replaceNum(value,size){
	value = value.replace(/[^\d.]/g,""); //清除"数字"和"."以外的字符  
    value = value.replace(/^\./g,""); //验证第一个字符是数字而不是  
    value = value.replace(/\.{2,}/g,"."); //只保留第一个. 清除多余的  
    value = value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");  
    value = value.replace(/^(\-)*(\d+)\.(\d\d\d\d).*$/,'$1$2.$3'); //只能输入四位个小数  
    return value;
}
