var pro = {};
/**
 * @describer 公共JS
 * @author WXF
 * @time 
 */
/***************************************************************/
/**
 * 保留两位小数点
 * @param {number} num
 * 
 */
pro.parseTwoFloat = function(num) {
	var arr = num.toString().split('.')[1];
	if(arr == undefined) {
		return num + '.00';
	} else {
		if(arr.length == 1) {
			return num + '0';
		} else if(arr.length == 2) {
			return num;
		} else {
			return num.toFixed(2);
		}
	}
};

/**
 * 调用原生js拨打电话
 * @param {} 
 * 
 */
pro.callService = function(){
	mui.plusReady(function(){
		plus.nativeUI.confirm("账户余额不足，请拨打400-852-8008索要模拟金。",function(e){
			if(e.index==1){
				plus.device.dial("4008528008",false);
			}
		},
		"400-852-8008",["取消","呼叫"]);
	});
}

export default pro