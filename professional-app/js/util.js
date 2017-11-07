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
	//layer.alert(tipContent);
}
/**
 * 提示层
 * @param tioContent
 */
function tip(tipContent){
	mui.toast(tipContent);
	/*layer.msg(tipContent);*/
}
var Btn = {
		confirmed: function() {
			return ['确认'];
		},
		confirmedAndCancle: function() {
			return ['取消', '确认'];
		}
	}
	/**
	 * 确认弹窗窗口
	 * @param {Object} alertContent 描述内容
	 * @param {Object} alertTitle 标题
	 * @param {Object} btnArray 按钮点的数组 :
	 * @param {Object} successCallBack 确定的回调
	 * @param {Object} colseCallBack  取消的回调
	 * @param {Object} param  参数
	 */
function alertProtype(alertContent, alertTitle, btnArray, successCallBack, colseCallBack,param) {
	mui.confirm(alertContent, alertTitle, btnArray, function(e) {
		if(e.index == 1) {
			successCallBack(param);
		} else {
			
			if(colseCallBack != null || colseCallBack != undefined)
			
				colseCallBack(param);
		}
	})
}
function colseCallBack(param){}
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
    return hour+":"+checkTime(minute)+":"+checkTime(ss);
}
/**
 * 在时间中当小于 10 时前面的 0补足
 */
function checkTime(i){
    if (i<10){
        i = '0' + i;
    }
    return i;
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

function nullOrInsertTime(insertTime,StatusMsg){
	
	if(insertTime==''){
		return StatusMsg;
	}else{
		return  insertTime;
	}
}

/**
 * 缓存订阅合约属性信息。以原油举例：
 * {"CommodityName":"国际原油","CommodityNo":"CL","ContractSize":10,"CurrencyNo":"USD",
 * 		"DotSize":2,"ExchangeNo":"NYMEX","Index":1,"MainContract":"1704","MiniTikeSize":0.01,
 * 		"TradingTimeSeg":[{"DateFlag":"1","IsDST":"N","TimeBucketBeginTime":"07:00:00","TradingState":"3","TradingTimeBucketID":0},
 * 						  {"DateFlag":"2","IsDST":"N","TimeBucketBeginTime":"06:00:00","TradingState":"5","TradingTimeBucketID":1}]}
 */
var CacheQuoteBase = {
	jCacheContractAttribute: {}, // key 为CommodityNo
	setCacheContractAttribute: function(jQuote) {
		this.jCacheContractAttribute[jQuote.CommodityNo] = jQuote;
	},
	getCacheContractAttribute: function(commodityNo, attr) {
		if(isEmpty(this.jCacheContractAttribute[commodityNo]) || isEmpty(this.jCacheContractAttribute[commodityNo][attr])) {
			return 0;
		}
		return this.jCacheContractAttribute[commodityNo][attr];
	}
};

var CacheQuoteBase00 = {
	jCacheContractAttribute: {}, // key 为CommodityNo
	setCacheContractAttribute: function(jQuote) {
		this.jCacheContractAttribute[jQuote.CommodityNo] = jQuote;
	},
	getCacheContractAttribute: function(commodityNo, attr) {
		if(isEmpty(this.jCacheContractAttribute[commodityNo]) || isEmpty(this.jCacheContractAttribute[commodityNo][attr])) {
			return 0;
		}
		return this.jCacheContractAttribute[commodityNo][attr];
	}
};

var CacheQuoteBase11 = {
	jCacheContractAttribute: {}, // key 为CommodityNo
	setCacheContractAttribute: function(jQuote) {
		this.jCacheContractAttribute[jQuote.CommodityNo] = jQuote;
	},
	getCacheContractAttribute: function(commodityNo, attr) {
		if(isEmpty(this.jCacheContractAttribute[commodityNo]) || isEmpty(this.jCacheContractAttribute[commodityNo][attr])) {
			return 0;
		}
		return this.jCacheContractAttribute[commodityNo][attr];
	}
};

/**
 * 缓存订阅合约行情信息，包括
 * 		{"CommodityNo":"CL","ContractNo":"1703","ErrorCode":0,"ErrorMsg":"订阅成功","ExchangeNo":"CME",
 * 		"LastQuotation":{"AskPrice1":53.88,"AskPrice2":53.89,"AskPrice3":53.9,"AskPrice4":53.92,"AskPrice5":53.95,
 * 						"AskQty1":1,"AskQty2":2,"AskQty3":33,"AskQty4":2,"AskQty5":12,
 * 						"AveragePrice":0,"BidPrice1":53.79,"BidPrice2":53.76,"BidPrice3":53.73,"BidPrice4":53.72,"BidPrice5":53.67,
 * 						"BidQty1":3,"BidQty2":2,"BidQty3":1,"BidQty4":1,"BidQty5":16,
 * 						"ChangeRate":0.03715400334386611,"ChangeValue":0.02000000000000313,"ClosingPrice":0,
 * 						"CommodityNo":"CL","ContractNo":"1703","DateTimeStamp":"2017-02-04 07:03:37","ExchangeNo":"CME",
 * 						"HighPrice":54.22,"LastPrice":53.85,"LastVolume":1,"LimitDownPrice":0,"LimitUpPrice":0,"LowPrice":53.4,"OpenPrice":53.68,
 * 						"Position":542680,"PreClosingPrice":0,"PrePosition":0,"PreSettlePrice":53.83,"SettlePrice":1486163017351,
 * 						"TotalAskQty":0,"TotalBidQty":0,"TotalTurnover":0,"TotalVolume":440576}}
 * 
 * 用行情服务器的配置增加/覆盖订阅合约配置：
 * {"CommodityName":"国际原油","CommodityNo":"CL","ContractSize":10,"CurrencyNo":"USD",
 * 		"DotSize":2,"ExchangeNo":"NYMEX","Index":1,"MainContract":"1704","MiniTikeSize":0.01,
 * 		"TradingTimeSeg":[{"DateFlag":"1","IsDST":"N","TimeBucketBeginTime":"07:00:00","TradingState":"3","TradingTimeBucketID":0},
 * 						  {"DateFlag":"2","IsDST":"N","TimeBucketBeginTime":"06:00:00","TradingState":"5","TradingTimeBucketID":1}]}
 */
var CacheQuoteSubscribe = {
	jCacheContractQuote: {}, // key 为contract(jQuote.CommodityNo + jQuote.ContractNo)
	setCacheContractQuote: function(jQuote) {
		// 根据行情服务器的配置更新到合约行情缓存配置
		jQuote.CommodityName = CacheQuoteBase.getCacheContractAttribute(jQuote.CommodityNo, "CommodityName");
		jQuote.CurrencyNo = CacheQuoteBase.getCacheContractAttribute(jQuote.CommodityNo, "CurrencyNo");
		jQuote.DotSize = CacheQuoteBase.getCacheContractAttribute(jQuote.CommodityNo, "DotSize");
		jQuote.ExchangeNo = CacheQuoteBase.getCacheContractAttribute(jQuote.CommodityNo, "ExchangeNo");
		jQuote.Index = CacheQuoteBase.getCacheContractAttribute(jQuote.CommodityNo, "Index");
		jQuote.MainContract = CacheQuoteBase.getCacheContractAttribute(jQuote.CommodityNo, "MainContract");
		jQuote.ContractSize = CacheQuoteBase.getCacheContractAttribute(jQuote.CommodityNo, "ContractSize");
		jQuote.MiniTikeSize = CacheQuoteBase.getCacheContractAttribute(jQuote.CommodityNo, "MiniTikeSize");
		jQuote.TradingTimeSeg = CacheQuoteBase.getCacheContractAttribute(jQuote.CommodityNo, "TradingTimeSeg");

		this.jCacheContractQuote[jQuote.CommodityNo + jQuote.ContractNo] = jQuote;
	},
	getCacheContractQuote: function(contract, attr1, attr2) {
		if(isEmpty(this.jCacheContractQuote[contract]) || isEmpty(this.jCacheContractQuote[contract][attr1])) {
			return 0;
		}
		if(arguments.length == 2) {
			//		vsLog("this.jCacheContractQuote[" + contract + "][" + attr1 + "]=" + this.jCacheContractQuote[contract][attr1]);
			return this.jCacheContractQuote[contract][attr1];
		}
		//	vsLog("this.jCacheContractQuote[" + contract + "][" + attr1 + "][" + attr2 + "]=" + this.jCacheContractQuote[contract][attr1][attr2]);
		return this.jCacheContractQuote[contract][attr1][attr2];
	}
};

var CacheQuoteSubscribe00 = {
	jCacheContractQuote: {}, // key 为contract(jQuote.CommodityNo + jQuote.ContractNo)
	setCacheContractQuote: function(jQuote) {
		// 根据行情服务器的配置更新到合约行情缓存配置
		jQuote.CommodityName = CacheQuoteBase.getCacheContractAttribute(jQuote.CommodityNo, "CommodityName");
		jQuote.CurrencyNo = CacheQuoteBase.getCacheContractAttribute(jQuote.CommodityNo, "CurrencyNo");
		jQuote.DotSize = CacheQuoteBase.getCacheContractAttribute(jQuote.CommodityNo, "DotSize");
		jQuote.ExchangeNo = CacheQuoteBase.getCacheContractAttribute(jQuote.CommodityNo, "ExchangeNo");
		jQuote.Index = CacheQuoteBase.getCacheContractAttribute(jQuote.CommodityNo, "Index");
		jQuote.MainContract = CacheQuoteBase.getCacheContractAttribute(jQuote.CommodityNo, "MainContract");
		jQuote.ContractSize = CacheQuoteBase.getCacheContractAttribute(jQuote.CommodityNo, "ContractSize");
		jQuote.MiniTikeSize = CacheQuoteBase.getCacheContractAttribute(jQuote.CommodityNo, "MiniTikeSize");
		jQuote.TradingTimeSeg = CacheQuoteBase.getCacheContractAttribute(jQuote.CommodityNo, "TradingTimeSeg");

		this.jCacheContractQuote[jQuote.CommodityNo + jQuote.ContractNo] = jQuote;
	},
	getCacheContractQuote: function(contract, attr1, attr2) {
		if(isEmpty(this.jCacheContractQuote[contract]) || isEmpty(this.jCacheContractQuote[contract][attr1])) {
			return 0;
		}
		if(arguments.length == 2) {
			//		vsLog("this.jCacheContractQuote[" + contract + "][" + attr1 + "]=" + this.jCacheContractQuote[contract][attr1]);
			return this.jCacheContractQuote[contract][attr1];
		}
		//	vsLog("this.jCacheContractQuote[" + contract + "][" + attr1 + "][" + attr2 + "]=" + this.jCacheContractQuote[contract][attr1][attr2]);
		return this.jCacheContractQuote[contract][attr1][attr2];
	}
};

	var formatFloat = function(num, digit) {
		var m = Math.pow(10, Math.abs(digit)); 
		if(digit < 0){ return num / m; }
		else if(digit > 0){ 
			return num * m; 
		}else{ 
			return num; 
		}
}


