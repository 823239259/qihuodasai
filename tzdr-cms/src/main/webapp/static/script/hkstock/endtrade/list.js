$(document).ready(function(){
	$('#endTradeTab').tabs({
	    onSelect:function(title,index){
	    	if (title == '待审核列表【审1】'){
	    		$("#firsteAudit").datagrid("options").url=basepath+"admin/hkstock/endtrade/getDatas?type=0";
	    		$("#firsteAudit").datagrid("reload");
	    	}
	    	if (title == '待审核列表【审2】'){
	    		$("#recheckAudit").datagrid("options").url=basepath+"admin/hkstock/endtrade/getDatas?type=1";
	    		$("#recheckAudit").datagrid("reload");
	    	}
	    	if (title == '审核记录'){
	    		$("#auditData").datagrid("options").url=basepath+"admin/hkstock/endtrade/getDatas?type=2";
	    		$("#auditData").datagrid("reload");
	    	}
	    }
	});
});

/**
 * 审核通过-打开窗口 
 * **/
function endOfTradePassOpen(tabType) {
	var tab = null;   //数据列表ID
	if(tabType == 1){
		tab = "#firsteAudit";  //初审
	}else{
		tab = "#recheckAudit"; //复审
	}
	if (Check.validateSelectItems($(tab),1)) {
		$("#tabType").val(tabType);
		$("#endOfTrade").show();
		$("#endOfTrade").window('open');
	}
};

/**
 * 审核通过-提交审核数据 
 * **/
function updateEndOfTradePass() {
	
	if (!$("#endOfForm").form("validate")) {
		return false;
	}
	
	var tab = null;   //数据列表ID
	
	var tabType = $("#tabType").val();
	
	if(tabType == 1){
		tab = "#firsteAudit";  //初审
	}else{
		tab = "#recheckAudit"; //复审
	}
	
	var rows = $(tab).datagrid('getSelections');
	ajaxPost({
		url : basepath + "admin/hkstock/endtrade/endTradeAuditPass",
		cache : false,
		async : false,
		data : {"tabType":tabType,"groupId":rows[0].groupId,"amount":$("#endOfTradeAmount").numberbox("getValue")},
		success : function(data) {
			if (data.success) {
				$(tab).datagrid('reload');
				eyWindow.walert("成功提示", data.message, 'info');
				endOfTradeClose();
				return;
			}
			eyWindow.walert("错误提示", data.message, 'error');
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			eyWindow.walert("错误提示", "系统异常，错误类型textStatus: "+textStatus+",异常对象errorThrown: "+errorThrown, 'error');
		}
	});
	
};

/**
 * 审核通过-关闭窗口 
 * **/
function endOfTradeClose() {
	$("#endOfTrade").show();
	$("#endOfTrade").window('close');
		
};

/**
 * 审核不通过-打开窗口 
 * **/
function endOfTradeNoPassOpen(tabType) {
	var tab = null;   //数据列表ID
	if(tabType == 1){
		tab = "#firsteAudit";  //初审
	}else{
		tab = "#recheckAudit"; //复审
	}
	if (Check.validateSelectItems($(tab),1)) {
		$("#tabType").val(tabType);
		$("#endOfTradeNoPass").show();
		$("#failCause_id").val("");
		$("#endOfTradeNoPass").window('open');
	}
		
};

/**
 * 审核不通过-提交审核数据 
 * **/
function updateEndOfTradeNoPass() {
	
	if (!$("#noPassForm").form("validate")) {
		return false;
	}
	
	var tab = null;   //数据列表ID
	
	var tabType = $("#tabType").val();
	
	if(tabType == 1){
		tab = "#firsteAudit";  //初审
	}else{
		tab = "#recheckAudit"; //复审
	}
	
	$.messager.progress({
        title:'提示',
        msg:'执行中'
    });
	
	var rows = $(tab).datagrid('getSelections');
	
	ajaxPost({
		url : basepath + "admin/hkstock/endtrade/endTradeAuditNoPass",
		cache : false,
		async : false,
		data : {"ajax":"1","tabType":tabType,"groupId":rows[0].groupId,"failCause":$("#failCause").val()} ,
		success : function(data) {
			if (data.success) {
				$(tab).datagrid('reload');
				eyWindow.walert("成功提示", data.message, 'info');
				endOfTradeNoPassClose();
				return;
			}
			eyWindow.walert("错误提示", data.message, 'error');
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			eyWindow.walert("错误提示", "系统异常，错误类型textStatus: "+textStatus+",异常对象errorThrown: "+errorThrown, 'error');
		}
	});
};

/**
 * 审核不通过-关闭窗口 
 * **/
function endOfTradeNoPassClose() {
	$("#endOfTradeNoPass").show();
	$("#endOfTradeNoPass").window('close');
};
