$(document).ready(function(){
	$("#edatagrid").datagrid({
		nowrap: true,
		autoRowHeight: false,
		striped: true,//True 就把行条纹化。（即奇偶行使用不同背景色）
		collapsible:true,
		url:basepath+'admin/marginRemind/data',
		toolbar:'#tb',
		rownumbers:true,
		fitColumns:true,// 自适应列宽
		frozenColumns:[[
            {field:'ck',checkbox:true}
		]],
		columns:[[ 
		    {field:'groupId',title:'方案编号',sortable:true}, 
			{field:'account',title:'恒生账户',width:100,sortable:true},
			{field:'mobile',title:'手机号码',width:200,sortable:true},
			{field:'uname',title:'用户姓名',width:200,sortable:true},
			/*{field:'totalLeverMoney',title:'风险保证金',width:100,sortable:true,formatter: function(value,row,index){
				return moneyUtils.format(value,2);
			}},
			{field:'totalLending',title:'配资金额',width:100,sortable:true,formatter: function(value,row,index){
				return moneyUtils.format(value,2);
			}},
			{field:'totalOperateMoney',title:'总操盘资金',width:100,formatter: function(value,row,index){
				return moneyUtils.format(value,2);
			}},*/
			{field:'warning',title:'亏损补仓线',width:100,sortable:true,formatter: function(value,row,index){
				return moneyUtils.format(value,2);
			}},
			{field:'open',title:'亏损平仓线',width:100,sortable:true,formatter: function(value,row,index){
				return moneyUtils.format(value,2);
			}},
			{field:'assetTotalValue',title:'资产总值',width:100,formatter: function(value,row,index){
				return moneyUtils.format(value,2);
			}},
			{field:'minimumDeposit',title:'最低补保证金',width:150,formatter: function(value,row,index){
				return moneyUtils.format(value,2);
			}},
			{field:'noticeStatus',title:'通知状态',width:150,sortable:true},
			{field:'lastNoticeTime',title:'上次通知时间',width:160,sortable:true,formatter: function(value,row,index){
				return getFormatDateByLong(value,'yyyy-MM-dd hh:mm:ss');
			}}
		]],
		onLoadSuccess:function(data){
			datagridUtils.loadSuccess(data,"edatagrid");
			}
		});
});
var marginRemind={
		doSearch:function(){
			$('#edatagrid').datagrid('load',{
				search_EQ_noticeStatus : $("#callNoticeStatus").combobox('getValue'),
				search_date_GTE_lastNoticeTime:$("#lastNoticeTime1").val(),
				search_date_LTE_lastNoticeTime:$("#lastNoticeTime2").val(),
			});
		},
		checkedData :[],
		changeNoticeStatus:function(status){
			var rows = $("#edatagrid").datagrid('getChecked');
			if (!rows || rows.length==0){
				eyWindow.walert("更新提示","请选择数据", 'info');
				return;
			}
			marginRemind.checkedData.length=0;
			var groupIds = [];
			for (var i=0;i<rows.length;i++)
			{
				marginRemind.checkedData.push(rows[i]);
				groupIds.push(rows[i].groupId);
			}
			
			// 重置密码
			$.messager.confirm("确认提示","确认是否修改选中账户状态？", function(result){
				if (result){
					ajaxPost({
						url : basepath + 'admin/marginRemind/changeNoticeStatus',
						cache : false,
						async : false,
						data : {
							"groupIds" : groupIds.join(","),
							"noticeStatus":status
						},
						success : function(data) {
							if (data.success) {
								marginRemind.changeSuccess(status);
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
		changeSuccess:function(status){
			var len = marginRemind.checkedData.length;
			if (len == 0 ){
				return ;
			}
			
			// 已通知
			if (status == 1){
				for (var i=0;i<len;i++)
				{ 
	                 var rowIndex = $('#edatagrid').datagrid('getRowIndex',marginRemind.checkedData[i]);
	                 $('#edatagrid').datagrid('deleteRow',rowIndex);
	                 $('#edatagrid').datagrid('clearSelections');//清除选中
				}
				//刷新 如果太卡可以不刷新   
				$("#edatagrid").datagrid('reload');
			}
			
			if (status == 2){
				for (var i=0;i<len;i++)
				{ 
	                 var rowIndex = $('#edatagrid').datagrid('getRowIndex',marginRemind.checkedData[i]);
					 $('#edatagrid').datagrid('updateRow',{index:rowIndex,row:{noticeStatus:'未接通'}});
					 $('#edatagrid').datagrid('clearSelections');//清除选中
				}
			}
		},
		reloadData:function(){
			$("#edatagrid").datagrid('reload');
		},
		sendSms:function(type){
			var rows = $("#edatagrid").datagrid('getChecked');
			if (!rows || rows.length==0){
				eyWindow.walert("更新提示","请选择数据", 'info');
				return;
			}
			
			var groupIds = [];
			for (var i=0;i<rows.length;i++)
			{
				groupIds.push(rows[i].groupId);
			}
			
			// 重置密码
			$.messager.confirm("确认提示","确认是否采用短信通知用户？", function(result){
				if (result){
					ajaxPost({
						url : basepath + 'admin/marginRemind/sendSms',
						cache : false,
						async : false,
						data : {
							"groupIds" : groupIds.join(","),
							"type":type
						},
						success : function(data) {
							if (data.success) {
								eyWindow.walert("成功提示", data.message, 'info');
								$("#edatagrid").datagrid('reload');
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