function isEmpty(obj) {
	if (obj == null || typeof(obj) == "undefined" || obj.length === 0) {
		return true;
	}
	return false;
}

/**
 * 将模板填充数据后显示到对应元素
 * @param viewId 显示元素目标ID
 * @param tplId 显示模板
 * @param jData 填充模板数据。json对象
 * @param fillType 填充方式，1:before，前置插入; 2:after，追加插入；0：替换。默认为替换
 */
function tplFillData(viewId, tplId, jData, fillType) {
	if (jData == null || typeof(jData) == "undefined") {
		return;
	}
	
	var tpl = document.getElementById(tplId).innerHTML;
	laytpl(tpl).render(jData, function(html) {
		if(fillType === 1) { // before
			document.getElementById(viewId).innerHTML =  html + document.getElementById(viewId).innerHTML;
		} else if(fillType === 2) { // after
			document.getElementById(viewId).innerHTML =  document.getElementById(viewId).innerHTML + html;
		} else {
			document.getElementById(viewId).innerHTML = html;
		}
	});
}

// log是否激活，激活true，关闭false
var logActive = true;
function vsLog(logMsg) {
	if(logActive) {
		console.log(logMsg);
	}
}

function getDrectionName(drection) {
	if (drection === 0) {
		return "买";
	} else if (drection === 1) {
		return "卖";
	}
	return drection;
}

function getDrectionName2(drection) {
	if (drection === 0) {
		return "多";
	} else if (drection === 1) {
		return "空";
	}
	return drection;
}

/**
 * 生成报单引用 
 */
function buildOrderRef() {
	return "WEB" + new Date().getTime();
}

/**
 * 将传入的浮点数保留指定小数位
 * @param num	需要处理的浮点数
 * @param dotSize	需要保留的小数位数
 * @returns	处理后的浮点数
 */
function toFixedFloatNumber(num, dotSize) {
	return parseFloat(num).toFixed(dotSize);
}