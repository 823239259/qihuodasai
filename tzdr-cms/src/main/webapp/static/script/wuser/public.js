/**
 * @author LiuQing
 */
function Check() {
};

/**
 * false is not projecName
 */
var INIT_CHECK_PATH = false;

Check.messageBox = function messageBox(title,bodyText,type) {
	if (!type || type == "") {
		type = "info"
	}
	 $.messager.alert(title,bodyText,type);
};
Check.submitForm = function submitForm(id) {
	setTimeout(function(){
		$("#" + id).submit();
	},100);
};
/**
 * 查询语句
 * @param gridId String
 * @param formId String
 */
Check.datagridQuery = function datagridQuery(gridId,formId) {
	$("#"+gridId).datagrid({"queryParams":Check.loadFormData($("#" + formId))});
};
/**
 * 
 * @param form 表单
 * @returns {Object}
 */
Check.loadFormData = function loadFormData(form) {
	var dataArray = $(form).serializeArray();
	var obj = new Object();
	$.each(dataArray,function(i,field){
		obj[field.name] = field.value;
	});
	return obj;
};

Check.setWidthWindow = function setWidthWindow(obj) {
	$(obj).css({"width":$(window).width()});
};

Check.setHeightWindow = function setHeightWindow(obj) {
	$(obj).css({"height":$(window).height()});
};
/**
 * 设置weight
 * @param obj
 */
Check.setWh = function setWh(obj) {
	$(obj).css({"height":$(window).height(),"width":$(window).width()});
};

/**
 * 获得根目录
 */
Check.rootPath = function getRootPath(){
	/**
    var curWwwPath=window.document.location.href;
    var pathName=window.document.location.pathname;
    var pos=curWwwPath.indexOf(pathName);
    var localhostPath=curWwwPath.substring(0,pos);
    var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
    if (INIT_CHECK_PATH) {
    	return(localhostPath+projectName);
    }
    else {
    	return localhostPath;
    }
    **/
	if (basepath == "/") {
		return "";
	}
	else {
		return basepath;
	}
};

/**
 * @param DataGrid datagrid easyuiObject
 * @param Integer itemSize 充许选择项长部
 */
Check.validateSelectItems = function isSelectItemNum(datagrid,itemSize) {
	if (itemSize <= 0) {
		return true;
	}
	var rows = $(datagrid).datagrid('getSelections');
	var rowLength = rows.length;
	if (rowLength <= 0) {
		$.messager.show({
            title:'提示',
            msg:'请选择[' + itemSize+ ']条数据',
            timeout:5000,
            showType:'slide'
        });
		return false;
	}
	else if (rowLength != itemSize) {
		$.messager.show({
            title:'提示',
            msg:'您已选[' + rowLength + ']条数据,请选择[' + itemSize + ']条数据',
            timeout:5000,
            showType:'slide'
        });
		return false;
	}
	return true;
};

/**
 * @param DataGrid datagrid easyuiObject
 * @param Integer itemSize 充许选择项长部
 */
Check.greaterSelectItems = function greaterItemNum(datagrid,itemSize) {
	var rows = $(datagrid).datagrid('getSelections');
	var rowLength = rows.length;
	if (rowLength < itemSize) {
		$.messager.show({
            title:'提示',
            msg:'请至少选择[' + itemSize + ']条数据',
            timeout:5000,
            showType:'slide'
        });
		return false;
	}
	return true;
};

/**
 * 
 * @param datagridId 表格ID
 * @param formId 表单ID
 */
Check.exportExcel = function exportExcel(datagridId,formId) {
	var excelTypeHidden = document.createElement("input");
	excelTypeHidden.setAttribute("type","hidden");
	excelTypeHidden.setAttribute("id","excelType9000000_0000_0001");
	excelTypeHidden.setAttribute("name","excelType9000000_0000_0001");
	excelTypeHidden.setAttribute("value","true");
	var url = $("#" + datagridId).datagrid("options").url;
	document.getElementById(formId).appendChild(excelTypeHidden);
	$("#" + formId).attr("action",url);
	$.easyui.submitForm(formId);
	setTimeout(function(){
		$("#excelType9000000_0000_0001").remove();
	},200);
};

/**
 * 查询对象
 */
$.extend({easyui:{}});
$.extend($.easyui,{
	datagridQuery:function(gridId,formId){
		Check.datagridQuery(gridId,formId)
	},
	path:function(){
		return Check.rootPath();
	},
	messageBox:function(title,bodyText,type){
		Check.messageBox(title,bodyText,type);
	},
	submitForm:function(id) {
		Check.submitForm(id); 
	},
	exportExcel:function(datagridId,formId) {
		Check.exportExcel(datagridId,formId);
	},
	validateSelectItems:function(datagrid,itemSize){
		return Check.validateSelectItems(datagrid,itemSize);
	},
	greaterSelectItems:function(datagrid,itemSize) {
		return Check.greaterSelectItems(datagrid,itemSize);
	}
});

