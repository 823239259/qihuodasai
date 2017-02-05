$(document).ready(function(){
	$("#edatagrid").datagrid({
		nowrap: true,
		autoRowHeight: false,
		striped: true,//True 就把行条纹化。（即奇偶行使用不同背景色）
		collapsible:true,
		url:basepath+'admin/notEnoughBalance/data',
		toolbar:'#tb',
		rownumbers:true,
		fitColumns:true,// 自适应列宽
		frozenColumns:[[
            {field:'ck',checkbox:true}
		]],
		columns:[[
		    {field:'uid',hidden:true},
			{field:'mobile',title:'手机号码',width:200,sortable:true},
			{field:'uname',title:'用户姓名',width:200,sortable:true},
			{field:'interest',title:'利息(元/天)',width:100,sortable:true,formatter: function(value,row,index){
				return moneyUtils.format(value,2);
			}},
			{field:'fee',title:'管理费(元/天)',width:100,sortable:true,formatter: function(value,row,index){
				return moneyUtils.format(value,2);
			}},
			{field:'balance',title:'账户余额',width:100,sortable:true,formatter: function(value,row,index){
				return moneyUtils.format(value,2);
			}},
			{field:'noticeStatus',title:'通知状态',width:150,sortable:true}
		]],
		onLoadSuccess:function(data){
			datagridUtils.loadSuccess(data,"edatagrid");
			}
		});
});
var notEnoghBalance={
		checkedData :[],
		reloadData:function(){
			$('#edatagrid').datagrid('clearSelections');//清除选中
			$("#edatagrid").datagrid('reload');
		},
		changeNoticeStatus:function(status){
			var rows = $("#edatagrid").datagrid('getChecked');
			if (!rows || rows.length==0){
				eyWindow.walert("更新提示","请选择数据", 'info');
				return;
			}
			notEnoghBalance.checkedData.length=0;
			var uids = [];
			for (var i=0;i<rows.length;i++)
			{
				notEnoghBalance.checkedData.push(rows[i]);
				uids.push(rows[i].uid);
			}
			
			// 重置密码
			$.messager.confirm("确认提示","确认是否修改选中账户状态？", function(result){
				if (result){
					ajaxPost({
						url : basepath + 'admin/notEnoughBalance/changeNoticeStatus',
						cache : false,
						async : false,
						data : {
							"uids" : uids.join(","),
							"noticeStatus":status
						},
						success : function(data) {
							if (data.success) {
								$("#edatagrid").datagrid('reload');
								$('#edatagrid').datagrid('clearSelections');//清除选中
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
		sendSms:function(){
			var rows = $("#edatagrid").datagrid('getChecked');
			if (!rows || rows.length==0){
				eyWindow.walert("更新提示","请选择数据", 'info');
				return;
			}
			
			var uids = [];
			for (var i=0;i<rows.length;i++)
			{
				uids.push(rows[i].uid);
			}
			
			
			// 重置密码
			$.messager.confirm("确认提示","确认是否采用短信通知用户？", function(result){
				if (result){
					ajaxPost({
						url : basepath + 'admin/notEnoughBalance/sendSms',
						cache : false,
						async : false,
						data : {
							"uids" : uids.join(",")
						},
						success : function(data) {
							if (data.success) {
								$("#edatagrid").datagrid('reload');
								$('#edatagrid').datagrid('clearSelections');//清除选中
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