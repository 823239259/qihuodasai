function excelImportWuser() {
	$('#freeImportId').dialog('open');
}


function exportTempExcel(){
	var path=Check.rootPath() +"/admin/freediff/downloadTemplate";
	window.location.href=path;
	
}
function excelImport() {
	$("#fileUploadForm").click();
	setTimeout(function(){
		$.post(Check.rootPath() + "/admin/freediff/queryImportExcel"
				,function(data){
			if (data=="") {
				//Check.messageBox("提示","导入数据成功!");
				alert("导入数据成功!");
				$("#edatagrid").datagrid('reload');
				$('#freeImportId').dialog('close');
			}else {
				Check.messageBox("提示",data,"error");
			}
		});
	},2000);
	
	
}