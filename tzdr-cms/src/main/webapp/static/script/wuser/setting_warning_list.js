function settingWarning() {
	var rows = $("#dg002").datagrid('getSelections');
	
	if ($.easyui.validateSelectItems($("#dg002"),1)) {
		
		$.messager.confirm('提示', '确认设置预警!', function(r){
    		if (r){
    			$.post($.easyui.path() + "/admin/setWaring/updateSettingState",{"id":rows[0].id},
    					function(data){
    				if (data == "success") {
    					$.easyui.messageBox("提示","设置成功");
    					$("#dg002").datagrid('reload');
    				}
    				else {
    					$.easyui.messageBox("提示",data,"error");
    				}
    			});
    		}
		});
		
	}
};

