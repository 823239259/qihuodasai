$(document).ready(function(){
	$("#edatagrid").datagrid({
		nowrap: true,
		autoRowHeight: false,
		striped: true,//True 就把行条纹化。（即奇偶行使用不同背景色）
		collapsible:true,
		url:basepath+'admin/endFailTrades/getDatas',
		toolbar:'#tb',
		rownumbers:true,
		pagination:true,
		fitColumns:true,// 自适应列宽
		frozenColumns:[[
            {field:'ck',checkbox:true}
		]],
		columns:[[
		    {field:'mobile',title:'手机号码',width:200,sortable:true},
			{field:'uname',title:'用户姓名',width:200,sortable:true},
			{field:'account',title:'恒生账户',width:100,sortable:true},
			{field:'groupId',title:'方案编号',width:100,sortable:true},
			{field:'endtime',title:'提交时间',width:160,sortable:true,formatter: function(value,row,index){
				return getFormatDateByLong(value,'yyyy-MM-dd hh:mm:ss');
			}}
		]],
		onLoadSuccess:function(data){
			datagridUtils.loadSuccess(data,"edatagrid");
			}
		});
});
var endFailTrade={
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
		endSuccess:function(){
			var rows = $("#edatagrid").datagrid('getChecked');
			if (!rows || rows.length==0){
				eyWindow.walert("提示","请选择数据", 'info');
				return;
			}
			var ids = [];
			for (var i=0;i<rows.length;i++)
			{
				ids.push(rows[i].groupId);
			}
			
			// 重置密码
			$.messager.confirm("确认提示","确认是否完结选中方案？", function(result){
				if (result){
					ajaxPost({
						url : basepath + 'admin/endFailTrades/endSuccess',
						cache : false,
						async : false,
						data : {
							"groupIds" : ids.join(",")
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