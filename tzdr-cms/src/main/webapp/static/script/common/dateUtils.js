//扩展Date的format方法
Date.prototype.format = function (format) {
var o = {
"M+": this.getMonth() + 1,
"d+": this.getDate(),
"h+": this.getHours(),
"m+": this.getMinutes(),
"s+": this.getSeconds(),
"q+": Math.floor((this.getMonth() + 3) / 3),
"S": this.getMilliseconds()
}
if (/(y+)/.test(format)) {
format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
}
for (var k in o) {
if (new RegExp("(" + k + ")").test(format)) {
format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
}
}
return format;
}
/**
*转换日期对象为日期字符串
* @param date 日期对象
* @param isFull 是否为完整的日期数据,
* 为true时, 格式如"2000-03-05 01:05:04"
* 为false时, 格式如 "2000-03-05"
* @return 符合要求的日期字符串
*/
function getSmpFormatDate(date, isFull) {
var pattern = "";
if (isFull == true || isFull == undefined) {
pattern = "yyyy-MM-dd hh:mm:ss";
} else {
pattern = "yyyy-MM-dd";
}
return getFormatDate(date, pattern);
}
/**
*转换当前日期对象为日期字符串
* @param date 日期对象
* @param isFull 是否为完整的日期数据,
* 为true时, 格式如"2000-03-05 01:05:04"
* 为false时, 格式如 "2000-03-05"
* @return 符合要求的日期字符串
*/
function getSmpFormatNowDate(isFull) {
return getSmpFormatDate(new Date(), isFull);
}
/**
*转换long值为日期字符串
* @param l long值
* @param isFull 是否为完整的日期数据,
* 为true时, 格式如"2000-03-05 01:05:04"
* 为false时, 格式如 "2000-03-05"
* @return 符合要求的日期字符串
*/
function getSmpFormatDateByLong(l, isFull) {
return getSmpFormatDate(new Date(l), isFull);
}
/**
*转换long值为日期字符串
* @param l long值
* @param pattern 格式字符串,例如：yyyy-MM-dd hh:mm:ss
* @return 符合要求的日期字符串
*/
function getFormatDateByLong(l, pattern) {
	if (null ==l || "null"==l || l==0){
		return "";
	}
return getFormatDate(new Date(l*1000), pattern);
}
/**
*转换日期对象为日期字符串
* @param l long值
* @param pattern 格式字符串,例如：yyyy-MM-dd hh:mm:ss
* @return 符合要求的日期字符串
*/
function getFormatDate(date, pattern) {
if (date == undefined) {
date = new Date();
}
if (pattern == undefined) {
pattern = "yyyy-MM-dd hh:mm:ss";
}
return date.format(pattern);
} 

/**
*转换日期对象为日期字符串
* @param l long值
* @param pattern 格式字符串,例如：yyyy-MM-dd hh:mm:ss
* @param months 增减月数
* @param days 增减天数
* @param hours 增减小时
* @return 符合要求的日期字符串
*/
function getFormatDate(date, pattern,months,days,hours) {
if (date == undefined) {
	date = new Date();
	if(months != null){
		date.setMonth(date.getMonth() + months);
	}
	
	if(days != null){
		date.setDate(date.getDate() + days);
	}
	
	if(hours != null){
		date.setHours(date.getHours() + hours);
	}
}
if (pattern == undefined) {
pattern = "yyyy-MM-dd hh:mm:ss";
}
return date.format(pattern);
} 


/**
 * 转换从插件中获取到的数据 为long
 * @param dateStr
 * @returns
 */
function  dateToLong(dateStr){
	if (validateIsNull(dateStr)){
		return;
	}
	return new Date(dateStr).getTime()/1000;
}

function convertLongDay(dateStr){
	if (validateIsNull(dateStr)){
		return;
	}
	return getFormatDate(new Date(dateStr),"yyyyMMdd");
}

function  convertStartTime(dateStr){
	if (validateIsNull(dateStr)){
		return;
	}
	return new Date(dateStr).getTime()/1000;
}

function  convertEndTime(dateStr){
	if (validateIsNull(dateStr)){
		return;
	}
	
	return new Date(dateStr).getTime()/1000+24*60*60;
}