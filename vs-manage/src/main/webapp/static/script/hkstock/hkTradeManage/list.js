$(document).ready(function() {
	$("#edatagrid").datagrid({
		nowrap : true,
		autoRowHeight : false,
		striped : true,// True 就把行条纹化。（即奇偶行使用不同背景色）
		collapsible : true,
		url : basepath + 'admin/hkstock/hkTradeManage/getData',
		toolbar : '#tb',
		rownumbers : true,
		pagination : true,
		selectOnCheck : true,
		ctrlSelect:true,
		fitColumns : true,// 自适应列宽
		frozenColumns : [ [ {
			field : 'ck',
			checkbox : true
		} ] ],
		columns : [ [ {
			field : 'tradeId',
			hidden : true
		}, {
			field : 'mobile',
			title : '手机号码',
			width : 114,
			sortable : true
		}, {
			field : 'tname',
			title : '真实姓名',
			width : 80,
			sortable : true
		}, {
			field : 'userTypeValue',
			title : '用户类型',
			width : 80,
			sortable : false
		}, {
			field : 'accountName',
			title : '交易账户名',
			width : 100,
			sortable : true
		}, {
			field : 'accountNo',
			title : '交易账号',
			width : 100,
			sortable : true
		}, {
			field : 'groupId',
			title : '方案编号',
			width : 75,
			sortable : true
		}, {
			field : 'leverMoney',
			title : '保证金（元）',
			width : 120,
			sortable : true,
			formatter : function(value, row, index) {
				return moneyUtils.format(value, 2);
			}
		}, {
			field : 'appendLeverMoney',
			title : '累计追加保证金（元）',
			width : 125,
			sortable : true,
			formatter : function(value, row, index) {
				return moneyUtils.format(value, 2);
			}
		}, {
			field : 'money',
			title : '配资金额（港元）',
			width : 120,
			sortable : true,
			formatter : function(value, row, index) {
				return moneyUtils.format(value, 2);
			}
		}, {
			field : 'totalLeverMoney',
			title : '总操盘资金（港元）',
			width : 120,
			sortable : true,
			formatter : function(value, row, index) {
				return moneyUtils.format(value, 2);
			}
		}, {
			field : 'warning',
			title : '亏损警戒线（港元）',
			width : 120,
			sortable : true,
			formatter : function(value, row, index) {
				return moneyUtils.format(value, 2);
			}
		}, {
			field : 'open',
			title : '亏损平仓线（港元）',
			width : 120,
			sortable : true,
			formatter : function(value, row, index) {
				return moneyUtils.format(value, 2);
			}
		}, {
			field : 'lever',
			title : '配资倍数',
			width : 80
		}, {
			field : 'startdays',
			title : '配资天数',
			width : 80,
			sortable : true
		}, {
			field : 'tradingDays',
			title : '已使用天数',
			width : 100
		}, {
			field : 'investAccrual',
			title : '操盘盈亏（港元）',
			width : 120,
			formatter : function(value, row, index) {
				return moneyUtils.format(value, 2);
			}
		}, {
			field : 'accrual',
			title : '投资盈利（元）',
			width : 120,
			formatter : function(value, row, index) {
				return moneyUtils.format(value, 2);
			}
		}, {
			field : 'status',
			title : '方案状态',
			width : 80,
			sortable : true,
			formatter : function(value, row, index) {
				switch (value) {
				case 0:
					return "验资中";
				case 1:
					return "操盘中";
				case 2:
					return "方案终结";
				default:
					break;
				}
				return "异常";
			}
		}, {
			field : 'tradeChannel',
			title : '交易账户类型',
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
		}, {
			field : 'addtime',
			title : '方案创建时间',
			width : 160,
			sortable : true,
			formatter : function(value, row, index) {
				if (value != null) {
					return getSmpFormatDateByLong(value * 1000, true);
				}
				return "";
			}
		}, {
			field : 'starttime',
			title : '方案开始日期',
			width : 120,
			sortable : true,
			formatter : function(value, row, index) {
				if (value != null) {
					return getSmpFormatDateByLong(value * 1000, false);
				}
				return "";
			}
		}, {
			field : 'endtime',
			title : '方案终止时间',
			width : 160,
			sortable : true,
			formatter : function(value, row, index) {
				if (value != null) {
					return getSmpFormatDateByLong(value * 1000, true);
				}
				return "";
			}
		}, {
			field : 'estimateEndtime',
			title : '预计结束日期',
			width : 120,
			sortable : true,
			formatter : function(value, row, index) {
				if (value != null) {
					return getSmpFormatDateByLong(value * 1000, false);
				}
				return "";
			}
		} ] ],
		onLoadSuccess : function(data) {
			datagridUtils.loadSuccess(data, "edatagrid");
		}
	});
});

var allTrade = {
	openChildDetail : function(url, fromType, title) {
		if (Check.validateSelectItems($("#edatagrid"), 1)) {
			var rows = $('#edatagrid').datagrid('getSelections');
			baseUtils.openIframeWin(800, 400, title, url + '?fromType='
					+ fromType + '&groupId=' + rows[0].groupId);
		}
	},
	applyEndTrade:function(){
		var rows = $('#edatagrid').datagrid('getSelections');
		if(rows.length<=0){
			eyWindow.walert("提示", '请选择要操作的记录', 'info');
			return;
		}
	
		for(var i=0;i<rows.length;i++){
			if(rows[i].status!=1){
				eyWindow.walert("提示", '您选择了不是“操盘中”的方案', 'info');
				return;
			}
		}
		
		$.messager.confirm("提示", "确定要申请终结方案?", function (r) {
			if (r) {
				$.messager.progress({
	                title:'提示',
	                msg:'申请终结方案执行中...'
	            });
				var groupIds=new Array();
				for(var i=0;i<rows.length;i++){
					groupIds.push(rows[i].groupId);
				}
				ajaxPost({
					url : basepath + 'admin/hkstock/hkArrearsEnd/endPlan',
					cache : false,
					async : false,
					data : {'groupIds' : groupIds.join(",")},
					success : function(data) {
						if (data.success) {
							$("#edatagrid").datagrid('reload');
							eyWindow.walert("成功提示", data.message, 'info');
							return;
						}
						eyWindow.walert("错误提示", data.message, 'error');
					},
					error : function() {
						eyWindow.walert("错误提示", "系统异常", 'error');
					}
				});
			}
		});
	}
}