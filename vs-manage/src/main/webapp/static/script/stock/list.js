var gridParams = {
	nowrap : true,
	autoRowHeight : false,
	striped : true,// True 就把行条纹化。（即奇偶行使用不同背景色）
	collapsible : true,
	url : '',
	rownumbers : true,
	toolbar : '#tb',
	pagination : true,
	fitColumns : true,// 自适应列宽
	queryParams : {
		search_EQ_deleted : false
	},
	frozenColumns : [ [ {
		field : 'ck',
		checkbox : true
	} ] ],
	columns : [ [ {
		field : 'id',
		hidden : true
	}, {
		field : 'name',
		title : '股票名称',
		width : 200,
		sortable : true,
		hidden : false
	}, {
		field : 'code',
		title : '股票代码',
		width : 200,
		sortable : true,
		hidden : false
	}, {
		field : 'effectiveDate',
		title : '限制日期',
		width : 200,
		sortable : true
	}, {
		field : 'createUser',
		title : '创建人',
		width : 100,
		sortable : true
	}, {
		field : 'createTime',
		title : '创建时间',
		width : 150,
		sortable : true,
		formatter : function(value, row, index) {
			return getFormatDateByLong(value, 'yyyy-MM-dd hh:mm:ss');
		}
	} ] ],
	onLoadSuccess : function(data) {
		datagridUtils.loadSuccess(data, "edatagrid");
	}
}

$(document).ready(function() {
	var type = $("#stockType").val();
	// 1:限制股 2：停牌股
	switch (Number(type)) {
	case 1:
		gridParams.url = basepath + 'admin/stock/restrict/easyuiPage';
		gridParams.queryParams = {
			search_EQ_deleted : false,
			search_EQ_type : 1
		};
		break;
	case 2:
		gridParams.url = basepath + 'admin/stock/suspended/easyuiPage';
		gridParams.columns[0][3].title = '停牌日期';
		gridParams.queryParams = {
			search_EQ_deleted : false,
			search_EQ_type : 2
		};
		break;
	default:
		break;
	}

	$("#edatagrid").datagrid(gridParams);
});

var stock = {
	saveInfo : function(type) {
		if (validateIsNull($("#effectiveDate").val())) {
			eyWindow.walert("错误提示", "请输入日期！", 'error');
			return;
		}
		if (type == 1) {
			baseUtils.saveOrUpdate('admin/stock/restrict/create');
		} else {
			baseUtils.saveOrUpdate('admin/stock/suspended/create');
		}
	},
	updateInfo : function(type) {
		if (validateIsNull($("#effectiveDate").val())) {
			eyWindow.walert("错误提示", "请输入日期！", 'error');
			return;
		}
		if (type == 1) {
			baseUtils.saveOrUpdate('admin/stock/restrict/update');
		} else {
			baseUtils.saveOrUpdate('admin/stock/suspended/update');
		}
	},
	exportTempExcel : function(url) {
		var path = Check.rootPath() + url + "/downloadTemplate";
		window.location.href = path;
	},
	openImportWindow : function() {
		$('#excelImport').dialog('open');
	},
	importExcelData : function() {
		if ($('#uploadFileInput').val()) {
			var $form = $('#excelImport form');
			var path = $form.attr('action');
			var url = path.substring(0, path.lastIndexOf('/'));
			$form.submit();
			setTimeout(function() {
				$.post(url + "/queryImportExcel", function(data) {
					if (data == "") {
						// Check.messageBox("提示","导入数据成功!");
						alert("导入数据成功!");
						$("#edatagrid").datagrid('reload');
						$('#excelImport').dialog('close');
					} else {
						Check.messageBox("提示", data, "error");
					}
				});
			}, 2000);
		}
	}
}
