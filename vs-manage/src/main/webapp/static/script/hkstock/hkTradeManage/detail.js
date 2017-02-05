$(document).ready(function() {
	$("#edatagrid").datagrid({
		nowrap : true,
		autoRowHeight : false,
		striped : true,// True 就把行条纹化。（即奇偶行使用不同背景色）
		collapsible : true,
		url : basepath + 'admin/hkstock/hkTradeManage/queryChildTrades',
		toolbar : '#tb',
		rownumbers : true,
		pagination : true,
		fitColumns : true,// 自适应列宽
		queryParams : {
			search_EQ_groupId : $("#groupId").val()
		},
		columns : [ [ {
			field : 'tradeId',
			hidden : true
		}, {
			field : 'programNo',
			title : '子方案ID',
			width : 114,
			sortable : true
		}, {
			field : 'totalLeverMoney',
			title : '总操盘资金（港元）',
			width : 100,
			formatter : function(value, row, index) {
				return moneyUtils.format(value, 2);
			}
		}, {
			field : 'money',
			title : '配资金额（港元）',
			width : 100,
			sortable : true,
			formatter : function(value, row, index) {
				return moneyUtils.format(value, 2);
			}
		}, {
			field : 'leverMoney',
			title : '配资保证金（元）',
			width : 100,
			sortable : true,
			formatter : function(value, row, index) {
				return moneyUtils.format(value, 2);
			}
		}, {
			field : 'feeDay',
			title : '配资管理费（元）',
			width : 80
		}, {
			field : 'apr',
			title : '配资利息（元）',
			width : 90,
			sortable : true
		}, {
			field : 'addtime',
			title : '配资日期',
			width : 160,
			sortable : true,
			formatter : function(value, row, index) {
				if(value!=null){
					return getSmpFormatDateByLong(value*1000, false);
				}
				return "";
			}
		} ] ],
		onLoadSuccess : function(data) {
			datagridUtils.loadSuccess(data, "edatagrid");
		}
	});
});