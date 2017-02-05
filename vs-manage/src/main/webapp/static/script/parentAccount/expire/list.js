function endOfPlanOpen() {
	if (Check.validateSelectItems($("#dg003"),1)) {
		$.messager.confirm('提示', '请确认是否要终结方案?', function(r){
			if (r){
				$.messager.progress({
					title:'提示',
					msg:'执行中'
				});
				var rows = $("#dg003").datagrid('getSelections');
				$.post(Check.rootPath() + "/admin/parentAccount/expire/endOfProgram" 
						,{"groupId":rows[0].groupId} ,
						function(data){
							if (data == "success") {
								Check.messageBox("提示","终结成功");
								$("#dg003").datagrid('reload');
							}
							else if (data == "subSuccess") {
								Check.messageBox("提示","终结方案申请成功!");
								$("#dg003").datagrid('reload');
							}
							else {
								Check.messageBox("提示",data,"error");
							}
							$.messager.progress('close');
						});
				
				
			}
		});
	}
	
	
	
};