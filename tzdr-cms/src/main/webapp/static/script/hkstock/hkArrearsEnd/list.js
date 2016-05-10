
$(function(){
	$('#edatagrid').datagrid({
		nowrap : true,// 在同一行中显示数据
		autoRowHeight : false,// 自动设置行高
		striped : true,// True 就把行条纹化。（即奇偶行使用不同背景色）
		collapsible : true,// 折叠面板
		toolbar : '#tb',// 工具栏
		rownumbers : true,// 显示行号
		pagination : true,// 底部显示分页工具栏
		url:basepath+'admin/hkstock/hkArrearsEnd/getData',
		singleSelect : true,// 单选
		fitColumns : true,// 自适应列宽
		// 冻结在左侧的列
		frozenColumns : [ [ {
			field : 'ck',
			checkbox : true
		} ] ],
		columns : [ [ {
			field : 'groupId',
			hidden : true
		}, {
			field : 'tname',
			title : '客户姓名',
			width : 100,
			sortable : true
		}, {
			field : 'mobile',
			title : '手机号码',
			width : 160,
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
		}, {
			field : 'accountNo',
			title : '交易账户号',
			width : 100,
			sortable : true
		}, {
			field : 'programNo',
			title : '方案编号',
			width : 150,
			sortable : true
		}, {
			field : 'avlBal',
			title : '账户余额（元）',
			width : 150,
			sortable : true,
			formatter : function(value, row, index) {
				return moneyUtils.format(value, 2);
			}
		}, {
			field : 'money',
			title : '欠费金额（元）',
			width : 150,
			sortable : true,
			formatter : function(value, row, index) {
				return moneyUtils.format(value, 2);
			}
		}, {
			field : 'addTime',
			title : '欠费时间',
			width : 200,
			sortable : true,
			formatter : function(value, row, index) {
				if(value!=null){
					return getSmpFormatDateByLong(value*1000, true);
				}
				return "";
			}
		} ] ],
		onLoadSuccess : function(data) {
			datagridUtils.loadSuccess(data, 'edatagrid');
		}
	});
	$('#dgdetail').datagrid({
		nowrap : true,// 在同一行中显示数据
		autoRowHeight : false,// 自动设置行高
		striped : true,// True 就把行条纹化。（即奇偶行使用不同背景色）
		collapsible : true,// 折叠面板
		rownumbers : true,// 显示行号
		pagination : true,// 底部显示分页工具栏
		url:basepath+'admin/hkstock/hkArrearsEnd/getDetail',
		fitColumns : true,// 自适应列宽
		columns : [ [ {
			field : 'addTime',
			title : '欠费时间',
			width : 200,
			sortable : true,
			formatter : function(value, row, index) {
				if(value!=null){
					return getSmpFormatDateByLong(value*1000, true);
				}
				return "";
			}
		}, {
			field : 'money',
			title : '欠费金额（元）',
			width : 150,
			sortable : true,
			formatter : function(value, row, index) {
				return moneyUtils.format(value, 2);
			}
		} ] ],
		onLoadSuccess : function(data) {
			datagridUtils.loadSuccess(data, 'dgdetail');
		}
	});
})

var arrearsEndOptions = {
	endPlan:function(){
		var rowData = $('#edatagrid').datagrid('getSelected');
		if (rowData==null){
			eyWindow.walert("提示", '请选择要操作的记录', 'info');
			return;
		}
		$.messager.confirm("提示", "确定要终结方案?", function (r) {
			if (r) {
				$.messager.progress({
	                title:'提示',
	                msg:'终结方案执行中...'
	            });
				ajaxPost({
					url : basepath + 'admin/hkstock/hkArrearsEnd/endPlan',
					cache : false,
					async : false,
					data : {groupIds : rowData.groupId},
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
	},
	openMoneyDetail:function() {	
		var rowData = $('#edatagrid').datagrid('getSelected');
		if (rowData==null){
			eyWindow.walert("提示", '请选择要操作的记录', 'info');
			return;
		}
		$("#search_EQ_groupId").val(rowData.groupId);
		$("#detailBtn").click();
		$("#moneyDetail").window('open');		
	}
}

