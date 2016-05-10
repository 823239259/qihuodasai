
/**
 * 打开新增/修改联系
 */
function customerDetailsOpen(type) {
	if(type==0){
		var customerId = $("#customerId").val();
		$('#voDetailsForm').form('clear');
		$("#customerId").val(customerId);
		$("#customerDetails").window({title:'新增联系'});
	}else{
		var _dg = $("#detail_dg"),
		rows = _dg.datagrid('getChecked');
		if(!Check.validateSelectItems($("#detail_dg"),1)) {
			return;
		}
		var _id = rows[0].id;
		_contactTime = getFormatDateByLong(rows[0].contactTime,"yyyy-MM-dd");
		_remark = rows[0].remark;
		$("#id").val(_id);
		$('#contactTimeStr').datebox('setValue', _contactTime);
		$("#remark").val(_remark);
		$("#customerDetails").window({title:'修改联系'});
	}
	$("#type").val(type);
	$("#customerDetails").show();
	$("#customerDetails").window('open');
};

/**
 * 关闭新增/修改联系
 */
function customerDetailsClose() {
	$("#customerDetails").show();
	$("#customerDetails").window('close');
};

/**
 * 新增/修改联系
 */
function customerDetailsSave() {
	if ($("#voDetailsForm").form('validate') == false) {
		return;
	}
	var operateUrl = "saveCustomerDetails";   //新增联系
	var type = $("#type").val();
	if(type == 1){
		operateUrl = "updateCustomerDetails";  //修改联系
	}
	ajaxPost({
		url : basepath + "admin/customer/"+operateUrl,
		cache : false,
		async : false,
		data : $("#voDetailsForm").serialize(),
		success : function(data) {
			if (data.success) {
				customerDetailsClose();
				$("#detail_dg").datagrid('reload');
				eyWindow.walert("成功提示", data.message, 'info');
				return;
			}
			eyWindow.walert("错误提示", data.message, 'error');
		},
		error : function() {
			eyWindow.walert("错误提示", "系统异常", 'error');
		}
	});
};

function isSignInStr(value,rowData,rowIndex) {
    if (value == 0){
    	return "否"; 	
    }else if (value == 1){
    	return "是"; 
    }  
}
function isTradeStr(value,rowData,rowIndex) {
    if (value == 0){
    	return "否"; 	
    }else if (value == 1){
    	return "是"; 
    }  
}
function longTimetoStr(value) {
    if (value){
    	return getFormatDateByLong(value,'yyyy-MM-dd hh:mm:ss')
    }else if (value == 2){
    	return ""; 
    }  
}