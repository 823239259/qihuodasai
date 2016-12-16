function openMoneyDetail() {
	if ($.easyui.validateSelectItems($("#dg"),1)) {
		var rows = $("#dg").datagrid('getSelections');
		$("#uid").val(rows[0].id);
		$("#detailBtn").click();
		$("#moneyDetail").window('open');
	}
}

/**
 * 变为6600 等活动用户
 */
function insteadActivityUser() {
	$.messager.confirm('提示', '确定修改为6600活动用户?', function(flag) {
		if(flag) {
			if ($.easyui.validateSelectItems($("#dg"),1)) {
				var _dg = $("#dg"), 
				rows = _dg.datagrid('getSelections');
				$.messager.progress({
					title:'提示',
					msg:'终结方案执行中...'
				});
				$.post($.easyui.path() + "/admin/wUser/insteadActivityUser" 
						,{"uid":rows[0].id} ,function(result) {
							if (result.success) {
								$.messager.progress('close');
								Check.messageBox("提示", "已修改！");
								_dg.datagrid('reload');
							}
							else {
								$.messager.progress('close');
								Check.messageBox("提示", result.message, "error");
							}
						});
			}
		}
	});
}
