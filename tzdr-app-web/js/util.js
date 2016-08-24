/**
 * 获取url参数 
 * @param {Object} name
 */
function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    if (r != null) return unescape(r[2]); return null; //返回参数值
}
/**
 * 生成报单引用 
 */
function doGetOrderRef(){
	return new Date().getTime();
}
/**
 * 平仓处理 
 * @param {Object} param
 */
function selling(param){
	var sellingParam = param;
	for(var i = 0 ; i < sellingParam.length ; i ++){
		var selling = sellingParam[i];
		Trade.doInsertOrder(selling.ExchangeNo,
							selling.CommodityNo,
							selling.ContractNo,
							selling.OrderNum,
							selling.Drection,
							selling.PriceType,
							selling.LimitPrice,
							selling.TriggerPrice,
							selling.OrderRef);
		
	}
}
/**
 * 撤单处理 
 * @param {Object} param
 */
function cancleOrder(param){
	var cancleParam = param;
	for (var i = 0 ; i < cancleParam.length ; i++) {
		var cancle = cancleParam[i];
		Trade.doCancelOrder(cancle.orderSysId,
							cancle.orderId,
							cancle.exchangeNo,
							cancle.commodityNo,
							cancle.contractNo,
							cancle.orderNum,
							cancle.drection,
							cancle.orderPrice);
	}
}