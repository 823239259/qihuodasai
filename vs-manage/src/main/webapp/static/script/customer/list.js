
/**
 * 新增状态
 */
function addCustomerOpen() {
	$('#voForm').form('clear');
	$("#addCustomer").show();
	$("#addCustomer").window('open');
};

/**
 * 关闭新增
 */
function addCustomerClose() {
	$("#addCustomer").show();
	$("#addCustomer").window('close');
};

/**
 * 新增客户
 */
function addCustomerSave() {
	if ($("#voForm").form('validate') == false) {
		return;
	}
	if(!$("#mobile").val().match(/^(((13[0-9])|(14[7])|(15[0-9])|(17[0-9])|(18[0-9]))+\d{8})$/)){
		eyWindow.walert("提示", '手机号码填写有误', 'info');
		return;
	}
	ajaxPost({
		url : basepath  + "admin/customer/saveCustomer",
		cache : false,
		async : false,
		data : $("#voForm").serialize(),
		success : function(data) {
			if (data.success) {
				addCustomerClose();
				$("#dg003").datagrid('reload');
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

function showDetail() {
	var _dg = $("#dg003"),
	rows = _dg.datagrid('getChecked');
	
	if(Check.validateSelectItems($("#dg003"),1)) {
		var _obj = rows[0],
			_id = _obj.id,
			_name = _obj.name,
			_mobile = _obj.mobile,
			_isSignIn = _obj.isSignIn,
			_signInTime = _obj.signInTime || '',
			_isTrade = _obj.isTrade,
			_firstTradeTime = _obj.firstTradeTime || '';
			_lastTradeTime = _obj.lastTradeTime || '';
			_realName = _obj.realName;
			_createTime = _obj.createTime || '';
		tabUtils.addTopTab('tabPanel', '详细信息【'+_name+"】", 'admin/customer/details/'+_id +'?name='+_name+'&createTime='+_createTime+'&realName='+_realName+'&lastTradeTime='+_lastTradeTime+'&mobile='+_mobile+'&isSignIn='+_isSignIn+'&signInTime='+_signInTime+'&isTrade='+_isTrade+'&firstTradeTime='+_firstTradeTime);
	}
}

/**
 * 分配
 */
function assign(){
	var _dg = $("#dg003"),
		rows = _dg.datagrid('getChecked');
	if (!rows || rows.length==0){
		eyWindow.walert("分配提示","请选择数据", 'info');
		return;
	}
	var newBelongMarket = $("#newBelongMarket").combobox('getValue')
	if(!newBelongMarket){
		eyWindow.walert("分配提示","请选择要分配的销售人员", 'info');
		return;
	}
	var checkedData = [];
	checkedData.length=0;
	var groupIds = [];
	for (var i=0;i<rows.length;i++)
	{
		if(rows[i].belongMarket==newBelongMarket){
			eyWindow.walert("分配提示","新老销售重复，不能进行分配", 'info');
			return;
		}
		checkedData.push(rows[i]);
		groupIds.push(rows[i].id);
	}
	
	//分配
	$.messager.confirm("确认提示","确认是否分配选中的客户？", function(result){
		if (result){
			ajaxPost({
				url : basepath + 'admin/customer/assign',
				cache : false,
				async : false,
				data : {
					"ids" : groupIds.join(","),
					"belongMarket":newBelongMarket
				},
				success : function(data) {
					if (data.success) {
						_dg.datagrid('reload');
						_dg.datagrid('clearSelections');//清除选中
						eyWindow.walert("成功提示", data.message, 'info');
						return;
					}
					eyWindow.walert("错误提示", data.message, 'error');
				},
				error : function() {
					eyWindow.walert("错误提示", "系统异常", 'error');
				}
			});
		}
	});
}
