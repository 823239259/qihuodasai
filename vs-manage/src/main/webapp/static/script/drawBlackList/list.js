var drawBlackList = {
	openImportWindow : function() {
		$('#excelImport').dialog('open');
	},
	importExcelData : function() {
		if ($('#uploadFileInput').val()) {
			var $form = $('#excelImport form');
			var path = $form.attr('action');
			var url = path.substring(0, path.lastIndexOf('/'));
			eyWindow.wprogress("上传提示","正在上传....请稍等，谢谢！");
			$form.submit();
			setTimeout(function() {
				$.post(url + "/queryImportExcel", function(data) {
					eyWindow.closeProgress();
					if (data == "") {
						// Check.messageBox("提示","导入数据成功!");
						Check.messageBox("提示", "导入数据成功!");
						$("#edatagrid").datagrid('reload');
						$('#excelImport').dialog('close');
					} else {
						Check.messageBox("提示", data, "error");
					}
				});
			}, 2000);
		}
	},
	/**
	 * 删除数据
	 * @param modelName
	 */
	deleteData:function(modelName){
		var rows = $("#edatagrid").datagrid('getChecked');
		if (!rows || rows.length==0){
			eyWindow.walert("删除提示","请选择要删除的行", 'info');
			return;
		}
		var comfirmMsg = "<p><b>手机号:</b>"+rows[0].mobile+"</p><p><b>原因:</b>"+rows[0].reason+"</p>";
		// 删除数据
		$.messager.confirm("确认提示",comfirmMsg, function(result){
			if (result){
					datagridUtils.deleteData(rows,"admin/"+modelName+"/batchDelete","edatagrid");
			}
		});
	}
}
