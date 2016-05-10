function openSchemeDetail() {
	
	if ($.easyui.validateSelectItems($("#dg003"),1)) {
		var rows = $("#dg003").datagrid('getSelections');
		$("#show_scheme_id").val(rows[0].id);
		$("#schemeBtn").click();
		$("#scheme_id_window").window('open');
	}
	
}