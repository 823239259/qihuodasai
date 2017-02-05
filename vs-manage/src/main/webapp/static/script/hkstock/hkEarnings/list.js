$(function() {
	$('#edatagrid').datagrid({
		nowrap : true,// 在同一行中显示数据
		autoRowHeight : false,// 自动设置行高
		collapsible : true,// 折叠面板
		toolbar : '#tb',// 工具栏
		rownumbers : true,// 显示行号
		pagination : true,// 底部显示分页工具栏
		singleSelect : true,// 单选
		fitColumns : true,// 自适应列宽
		columns : [ [ {
			field : 'mobile',
			title : '用户名',
			width : 150
		}, {
			field : 'tname',
			title : '实名',
			width : 100,
			sortable : true
		}, {
			field : 'account',
			title : '恒生账户',
			width : 150
		}, {
			field : 'hsBelongBroker',
			title : '所属券商户',
			width : 150
		}, {
			field : 'groupId',
			title : '方案号',
			width : 150
		}, {
			field : 'leverMoney',
			title : '保证金',
			width : 150
		}, {
			field : 'deduction_lever_money',
			title : '抵扣保证金',
			width : 150

		},{
			field : 'lever',
			title : '倍数',
			width : 150
		}, {
			field : 'opDay',
			title : '天数',
			width : 150
		}, {
			field : 'managerMoney',
			title : '管理费',
			width : 150
		}, {
			field : 'revokeManagerMoney',
			title : '配资管理费撤回',
			width : 200
		}, {
			field : 'interestMoney',
			title : '应收利息',
			width : 150
		}, {
			field : 'revokeInterest',
			title : '配资利息撤回',
			width : 200
		}, {
			field : 'deductMoney',
			title : '抵扣金额',
			width : 150
		}, {
			field : 'realIncomeMoney',
			title : '实收利息',
			width : 150
		}, {
			field : 'freemoney',
			title : '佣金差',
			width : 150
		}, {
			field : 'transmoney',
			title : '过户费差',
			width : 150
		}, {
			field : 'profitMoney',
			title : '盈利分成',
			width : 150,
			formatter: function(value,row,index){
				if (value==0){
					return "";
				}
				return value;
			}
		}, {
			field : 'totalmoney',
			title : '收益小计',
			width : 150
		}, {
			field : 'activityTypeStr',
			title : '方案类型',
			width : 150,
			formatter: function(value,row,index){
				if (row.hsBelongBroker=='小计' || row.hsBelongBroker=='合计'){
					return "";
				}
				return value;
			}
		} ] ],
		onLoadSuccess : function(data) {
			//datagridUtils.loadSuccess(data, 'edatagrid');
		}
	});
	$('#hkedatagrid').datagrid({
		nowrap : true,// 在同一行中显示数据
		autoRowHeight : false,// 自动设置行高
		collapsible : true,// 折叠面板
		toolbar : '#hktb',// 工具栏
		rownumbers : true,// 显示行号
		pagination : true,// 底部显示分页工具栏
		singleSelect : true,// 单选
		fitColumns : true,// 自适应列宽
		columns : [ [ {
			field : 'mobile',
			title : '用户名',
			width : 150
		}, {
			field : 'tname',
			title : '实名',
			width : 100,
			sortable : true
		}, {
			field : 'account',
			title : '恒生账户',
			width : 150
		}, {
			field : 'hsBelongBroker',
			title : '所属券商户',
			width : 150
		}, {
			field : 'groupId',
			title : '方案号',
			width : 150
		}, {
			field : 'leverMoney',
			title : '保证金',
			width : 150
		}, {
			field : 'deduction_lever_money',
			title : '抵扣保证金',
			width : 150
		},{
			field : 'lever',
			title : '倍数',
			width : 150
		}, {
			field : 'opDay',
			title : '天数',
			width : 150
		}, {
			field : 'managerMoney',
			title : '管理费',
			width : 150
		}, {
			field : 'revokeManagerMoney',
			title : '配资管理费撤回',
			width : 200
		}, {
			field : 'interestMoney',
			title : '应收利息',
			width : 150
		}, {
			field : 'revokeInterest',
			title : '配资利息撤回',
			width : 200
		}, {
			field : 'deductMoney',
			title : '抵扣金额',
			width : 150
		}, {
			field : 'realIncomeMoney',
			title : '实收利息',
			width : 150
		}, {
			field : 'freemoney',
			title : '佣金差',
			width : 150
		}, {
			field : 'transmoney',
			title : '过户费差',
			width : 150
		}, {
			field : 'totalmoney',
			title : '收益小计',
			width : 150
		} ] ],
		onLoadSuccess : function(data) {
			//datagridUtils.loadSuccess(data, 'hkedatagrid');
		}
	});
	$('#earningsTab').tabs(
			{
				onSelect : function(title, index) {
					if (title == 'A股收益日报表') {
						$('#edatagrid').datagrid('options').url = basepath
								+ 'admin/reports/earnings/listData';
						$('#edatagrid').datagrid('reload');
					}
					if (title == '港股收益日报表') {
						$('#hkedatagrid').datagrid('options').url = basepath
								+ 'admin/hkstock/hkEarnings/getData';
						$('#hkedatagrid').datagrid('reload');
					}
				}
			});
})
