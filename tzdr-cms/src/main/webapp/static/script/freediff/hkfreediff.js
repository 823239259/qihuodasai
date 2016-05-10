$(function() {
	$('#edatagrid').datagrid(
			{
				nowrap : true,// 在同一行中显示数据
				autoRowHeight : false,// 自动设置行高
				collapsible : true,// 折叠面板
				toolbar : '#tb',// 工具栏
				rownumbers : true,// 显示行号
				pagination : true,// 底部显示分页工具栏
				singleSelect : false,// 单选
				fitColumns : true,// 自适应列宽
				// 冻结在左侧的列
				frozenColumns : [ [ {
					field : 'ck',
					checkbox : true
				} ] ],
				columns : [ [ {
					field : 'id',
					hidden : true
				}, {
					field : 'parentaccount',
					title : '恒生母账户',
					width : 150
				}, {
					field : 'account',
					title : '恒生子账户名',
					width : 150
				}, {
					field : 'groupid',
					title : '达人方案号',
					width : 150
				}, {
					field : 'money',
					title : '金额(元)',
					width : 150

				}, {
					field : 'type',
					title : '类型',
					width : 150
				}, {
					field : 'createdate',
					title : '产生时间',
					width : 150
				}, {
					field : 'lrr',
					title : '录入者',
					width : 150
				} ] ],
				onLoadSuccess : function(data) {
					datagridUtils.loadSuccess(data, 'edatagrid');
					if (data) {
						var i = -1;
						$.each(data.rows, function(index, item) {
							if (item.id == null) {
								i = index;
							}
						});
						if (i > -1) {
							// 删除合计行的复选框
							$('#edatagrid').datagrid('getPanel').find(
									'input:checkbox').eq(i + 1).remove();
						}
					}
				},
				onClickRow : function(index, row) {
					if (row.id == null) {
						$('#edatagrid').datagrid('unselectRow', index);
					}
				},
				onCheckAll : function(rows) {
					$.each(rows, function(index, row) {
						if (row.id == null) {
							$('#edatagrid').datagrid('unselectRow', index);
						}
					});
				}
			});
	$('#hkedatagrid').datagrid(
			{
				nowrap : true,// 在同一行中显示数据
				autoRowHeight : false,// 自动设置行高
				collapsible : true,// 折叠面板
				toolbar : '#hktb',// 工具栏
				rownumbers : true,// 显示行号
				pagination : true,// 底部显示分页工具栏
				singleSelect : false,// 单选
				fitColumns : true,// 自适应列宽
				// 冻结在左侧的列
				frozenColumns : [ [ {
					field : 'ck',
					checkbox : true
				} ] ],
				columns : [ [ {
					field : 'id',
					hidden : true
				}, {
					field : 'parentaccount',
					title : '母账户',
					width : 150
				}, {
					field : 'account',
					title : '交易账户名',
					width : 150
				}, {
					field : 'groupid',
					title : '达人方案号',
					width : 150
				}, {
					field : 'money',
					title : '金额(元)',
					width : 150
				}, {
					field : 'type',
					title : '类型',
					width : 150,
					formatter : function(value, row, index) {
						switch (value) {
						case '1':
							return "佣金差";
						case '2':
							return "过户费差";
						default:
							break;
						}
						return "";
					}
				}, {
					field : 'addtime',
					title : '产生时间',
					width : 150,
					formatter : function(value, row, index) {
						if (value != null) {
							return getSmpFormatDateByLong(value * 1000, false);
						}
						return "";
					}
				}, {
					field : 'lrr',
					title : '录入者',
					width : 150
				} ] ],
				onLoadSuccess : function(data) {
					datagridUtils.loadSuccess(data, 'hkedatagrid');
					if (data) {
						var i = -1;
						$.each(data.rows, function(index, item) {
							if (item.id == null) {
								i = index;
							}
						});
						if (i > -1) {
							// 删除合计行的复选框
							$('#hkedatagrid').datagrid('getPanel').find(
									'input:checkbox').eq(i + 1).remove();
						}
					}
				},
				onClickRow : function(index, row) {
					if (row.id == null) {
						$('#hkedatagrid').datagrid('unselectRow', index);
					}
				},
				onCheckAll : function(rows) {
					$.each(rows, function(index, row) {
						if (row.id == null) {
							$('#hkedatagrid').datagrid('unselectRow', index);
						}
					});
				}
			});
	$('#freediffTab').tabs(
			{
				onSelect : function(title, index) {
					if (title == 'A股佣金过户费差') {
						$('#edatagrid').datagrid('options').url = basepath
								+ 'admin/freediff/listData';
						$('#edatagrid').datagrid('reload');
					}
					if (title == '港股佣金过户费差') {
						$('#hkedatagrid').datagrid('options').url = basepath
								+ 'admin/hkstock/hkFreeDiff/getData';
						$('#hkedatagrid').datagrid('reload');
					}
				}
			});
})

var hkFreeDiffOptions = {
	openEditIframeWin : function(width, height, title) {
		if (Check.validateSelectItems($("#hkedatagrid"), 1)) {
			var rows = $('#hkedatagrid').datagrid('getSelections');
			var html = '<iframe scrolling="yes" frameborder="0"  src="'
					+ basepath
					+ 'admin/hkstock/hkFreeDiff/edit?fromType=edit&id='
					+ rows[0].id
					+ '" style="width:100%;height:100%;"></iframe>';
			$('#addWin').window({
				collapsible : false,
				minimizable : false,
				maximizable : false,
				width : width,
				height : height,
				title : title,
				loadingMessage : '正在加载,请等待......',
				iconCls : 'icon-edit',
				closed : true,
				modal : true,
				content : html
			});
			$('#addWin').window('open');
		}
	},
	deleteData : function() {
		var rows = $("#hkedatagrid").datagrid('getChecked');
		if (!rows || rows.length == 0) {
			eyWindow.walert("删除提示", "请选择要删除的行", 'info');
			return;
		}
		// 删除数据
		$.messager.confirm("确认提示", "确认是否删除选中的数据？", function(result) {
			if (result) {
				datagridUtils.deleteData(rows,
						"admin/hkstock/hkFreeDiff/batchDelete", "hkedatagrid");
			}
		});
	},
	exportTempExcel : function() {
		var path = Check.rootPath()
				+ "/admin/hkstock/hkFreeDiff/downloadTemplate";
		window.location.href = path;
	},
	excelImportWuser : function() {
		$('#hkfreeImportId').dialog('open');
	},
	excelImport : function() {
		$("#hkfileUploadForm").click();
		setTimeout(function() {
			$.post(Check.rootPath()
					+ "admin/hkstock/hkFreeDiff/queryImportExcel", function(
					data) {
				if (data == "") {
					Check.messageBox("提示", "导入数据成功!");
					$("#hkedatagrid").datagrid('reload');
					$('#hkfreeImportId').dialog('close');
				} else {
					Check.messageBox("提示", data, "error");
				}
			});
		}, 2000);
	},
	saveOrUpdate : function(url) {
		if ($("#addForm").form('validate') == false) {
			return;
		}
		ajaxPost({
			url : basepath + url,
			cache : false,
			async : false,
			data : $("#addForm").serialize(),
			success : function(data) {
				if (data.success) {
					parent.$('#addWin').window('close');
					parent.$('#hkedatagrid').datagrid('reload');
					parent.eyWindow.walert("成功提示", data.message, 'info');
					return;
				}
				eyWindow.walert("错误提示", data.message, 'error');
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				eyWindow.walert("错误提示", "系统异常，错误类型textStatus: " + textStatus
						+ ",异常对象errorThrown: " + errorThrown, 'error');
			}
		});
	}
}

function excelImportWuser() {
	$('#freeImportId').dialog('open');
}

function exportTempExcel() {
	var path = Check.rootPath() + "/admin/freediff/downloadTemplate";
	window.location.href = path;

}
function excelImport() {
	$("#fileUploadForm").click();
	setTimeout(function() {
		$.post(Check.rootPath() + "/admin/freediff/queryImportExcel", function(
				data) {
			if (data == "") {
				// Check.messageBox("提示","导入数据成功!");
				alert("导入数据成功!");
				$("#edatagrid").datagrid('reload');
				$('#freeImportId').dialog('close');
			} else {
				Check.messageBox("提示", data, "error");
			}
		});
	}, 2000);
}