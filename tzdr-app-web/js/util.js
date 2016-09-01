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
 * 生成报单引用 
 */
function doGetOrderRef() {
	return new Date().getTime();
}
/**
 * 平仓处理 
 * @param {Object} param
 */
function selling(param) {
	var sellingParam = param;
	for(var i = 0; i < sellingParam.length; i++) {
		var sellings = sellingParam[i];
		Trade.doInsertOrder(sellings.ExchangeNo,
							sellings.CommodityNo,
							sellings.ContractNo,
							sellings.OrderNum,
							sellings.Drection,
							sellings.PriceType,
							sellings.LimitPrice,
							sellings.TriggerPrice,
							sellings.OrderRef);

	}
}
/**
 * 撤单处理 
 * @param {Object} param
 */
function cancleOrder(param) {
	var cancleParam = param;
	for(var i = 0; i < cancleParam.length; i++) {
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
			colseCallBack(param);
		}
	})
}
function colseCallBack(){}