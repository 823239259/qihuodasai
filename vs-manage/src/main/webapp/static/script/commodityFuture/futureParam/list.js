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
		url : basepath + 'admin/commodityFutureParam/getData',// 请求路径
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
			field : 'typeName',
			title : '交易品种',
			width : 50,
			sortable : true
		},
		// {field:'tranFees',title:'手续费',width:60,sortable:true,formatter:function(value,row,index){
		// return moneyUtils.format(value,2);
		// }},
		{
			field : 'tranFeesArray',
			title : '手续费（元）',
			width : 60,
			sortable : true
		}, {
			field : 'feeManage',
			title : '管理费（元）',
			width : 60,
			sortable : true,
			formatter : function(value, row, index) {
				return moneyUtils.format(value, 2);
			}
		}, {
			field : 'tranLever',
			title : '开仓手数',
			width : 100,
			sortable : true
		}, {
			field : 'traderBond',
			title : '单手保证金（元）',
			width : 100,
			sortable : true,
			formatter : function(value, row, index) {
				return moneyUtils.format(value, 2);
			}
		}, {
			field : 'traderMoney',
			title : '单手操盘金（元）',
			width : 100,
			sortable : true,
			formatter : function(value, row, index) {
				return moneyUtils.format(value, 2);
			}
		}, {
			field : 'lineLoss',
			title : '平仓保留金额（元）',
			width : 60,
			sortable : true,
			formatter : function(value, row, index) {
				return moneyUtils.format(value, 2);
			}
		}, {
			field : 'updateTime',
			title : '修改时间',
			width : 200,
			sortable : true,
			formatter : function(value, row, index) {
				if(value!=null){
					return getSmpFormatDateByLong(value*1000, true);
				}
				return "";
			}
		}, {
			field : 'updateUser',
			title : '操作人',
			width : 80,
			sortable : true
		} ] ],
		onLoadSuccess : function(data) {
			datagridUtils.loadSuccess(data, "edatagrid");
		}
	});
})

var futureParamOptions = {
	auditParam : function(width, height, title) {
		var rowData = $('#' + viewData.dataGridId).datagrid('getSelected');
		if (null == rowData) {
			eyWindow.walert("提示", '请选择要操作的记录', 'info');
			return;
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
					href : basepath + 'admin/commodityFutureParam/toEdit?id='
							+ rowData.id
				});
		$('#addWin').window('open');
	}
}