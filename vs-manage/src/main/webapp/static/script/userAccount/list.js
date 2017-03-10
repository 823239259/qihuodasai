function openAccountDetail() {
	if ($.easyui.validateSelectItems($("#dg"),1)) {
		var rows = $("#dg").datagrid('getSelections');
		$("#uid").val(rows[0].id);
		$("#accountBtn").click();
		$("#accountDetail").window('open');
	}
}
