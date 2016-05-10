var viewData = {
	dataGridId : 'edatagrid',
	toolDivId : 'tb'
}

$(function() {
	$('#' + viewData.dataGridId).datagrid({
		nowrap : true,// 在同一行中显示数据
		autoRowHeight : false,// 自动设置行高
		striped : true,// True 就把行条纹化。（即奇偶行使用不同背景色）
		collapsible : true,// 折叠面板
		url : basepath + 'admin/hkstock/hkParentAccount/getData',// 请求路径
		toolbar : '#' + viewData.toolDivId,// 工具栏
		rownumbers : true,// 显示行号
		pagination : true,// 底部显示分页工具栏
		singleSelect : true,// 单选
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
			field : 'accountNo',
			title : '账户编号',
			width : 50,
			sortable : true
		},{
			field : 'name',
			title : '账户名称',
			width : 60,
			sortable : true
		}, {
			field : 'tradeChannel',
			title : '账户交易通道',
			width : 80,
			sortable : true,
			formatter : function(value, row, index) {
				switch (value) {
				case 0:
					return "TTS";
				default:
					break;
				}
				return "异常";
			}
		} ] ],
		onLoadSuccess : function(data) {
			datagridUtils.loadSuccess(data, viewData.dataGridId);
		}
	});
})

var parentAccountOptions = {

	openEditWin : function(width, height, title, isUpdate) {
		var url=basepath + 'admin/hkstock/hkParentAccount/toEdit?';
		if(isUpdate){
			var rowData = $('#' + viewData.dataGridId).datagrid('getSelected');
			if (null == rowData) {
				eyWindow.walert("提示", '请选择要操作的记录', 'info');
				return;
			}
			url+='id='+ rowData.id
		}
	
		$('#addWin').window(
				{
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
					href : url
							
				});
		$('#addWin').window('open');
	}
}