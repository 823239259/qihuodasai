$(document).ready(function(){
	$("#edatagrid").datagrid({
		nowrap: true,
		autoRowHeight: false,
		striped: true,//True 就把行条纹化。（即奇偶行使用不同背景色）
		collapsible:true,
		url:basepath+'admin/notCancelAccounts/getDatas',
		toolbar:'#tb',
		rownumbers:true,
		pagination:true,
		fitColumns:true,// 自适应列宽
		frozenColumns:[[
            {field:'ck',checkbox:true}
		]],
		columns:[[
		    {field:'id',hidden:true},
		    {field:'mobile',title:'手机号',width:100,sortable:true},
			{field:'account',title:'交易帐号',width:100,sortable:true},
			{field:'assetId',title:'单元序号',width:100,sortable:true},
			{field:'feeType',title:'账户类型',width:100},
			{field:'status',title:'账户状态',width:200},
			{field:'endtime',title:'终结时间',width:150,sortable:true,formatter: function(value,row,index){
				return getFormatDateByLong(value,'yyyy-MM-dd hh:mm:ss');
			}},
		]],
		onLoadSuccess:function(data){
			datagridUtils.loadSuccess(data,"edatagrid");
			}
		});
});


var subAccount={
		doSearch:function(){
			$('#edatagrid').datagrid('load',{
				search_LIKE_account : $('#search_account').val(),
				search_date_GTE_endtime:$("#startTime").val(),
				search_date_LTE_endtime:$("#endTime").val(),
				search_IN_feeType:$("#feeType").combobox('getValue')
			});
		},
		cancelAccount:function(){
			var rows = $("#edatagrid").datagrid('getChecked');
			if (!rows || rows.length==0){
				eyWindow.walert("注销提示","请选择需要注销的账户", 'info');
				return;
			}
			var ids = [];
			for (var i=0;i<rows.length;i++)
			{
				ids.push(rows[i].id);
			}
			
			// 重置密码
			$.messager.confirm("确认提示","确认注销选中账户？", function(result){
				if (result){
					ajaxPost({
						url : basepath + 'admin/notCancelAccounts/cancelAccount',
						cache : false,
						async : false,
						data : {
							"ids" : ids.join(","),
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