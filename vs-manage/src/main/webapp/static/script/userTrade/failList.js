$(document).ready(function(){
	$("#edatagrid").datagrid({
		nowrap: true,
		autoRowHeight: false,
		striped: true,//True 就把行条纹化。（即奇偶行使用不同背景色）
		collapsible:true,
		url:basepath+'admin/tradeFail/getDatas',
		toolbar:'#tb',
		rownumbers:true,
		pagination:true,
		fitColumns:true,// 自适应列宽
		frozenColumns:[[
            {field:'ck',checkbox:true}
		]],
		columns:[[
		    {field:'id',hidden:true},
		    {field:'stockAssets',hidden:true},
			{field:'account',title:'恒生账户',width:100,sortable:true},
			{field:'mobile',title:'手机号码',width:200,sortable:true},
			{field:'uname',title:'用户姓名',width:200,sortable:true},
			{field:'totalLeverMoney',title:'风险保证金',width:100,sortable:true,formatter: function(value,row,index){
				return moneyUtils.format(value,2);
			}},
			{field:'totalLending',title:'配资金额',width:100,sortable:true,formatter: function(value,row,index){
				return moneyUtils.format(value,2);
			}},
			{field:'totalOperateMoney',title:'总操盘资金',width:100,formatter: function(value,row,index){
				return moneyUtils.format(value,2);
			}},
			{field:'warning',title:'亏损补仓线',width:100,sortable:true,formatter: function(value,row,index){
				return moneyUtils.format(value,2);
			}},
			{field:'open',title:'亏损平仓线',width:100,sortable:true,formatter: function(value,row,index){
				return moneyUtils.format(value,2);
			}}
		]],
		onLoadSuccess:function(data){
			datagridUtils.loadSuccess(data,"edatagrid");
			}
		});
});
var failTrade={
		doSearch:function(){
			$('#edatagrid').datagrid('load',{
				search_LIKE_username : $('#name').val(),
				search_EQ_deleted:false
			});
		},
		reloadData:function(){
			$('#edatagrid').datagrid('clearSelections');//清除选中
			$("#edatagrid").datagrid('reload');
		},
		review:function(){
			var rows = $("#edatagrid").datagrid('getChecked');
			if (!rows || rows.length==0){
				eyWindow.walert("提示","请选择数据", 'info');
				return;
			}
			var ids = [];
			for (var i=0;i<rows.length;i++)
			{
				ids.push(rows[i].id);
			}
			
			// 重置密码
			$.messager.confirm("确认提示","确认是否审核通过？", function(result){
				if (result){
					ajaxPost({
						url : basepath + 'admin/tradeFail/review',
						cache : false,
						async : false,
						data : {
							"ids" : ids.join(",")
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