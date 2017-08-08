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

export default pro