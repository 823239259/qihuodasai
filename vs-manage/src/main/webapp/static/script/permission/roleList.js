$(document).ready(function(){
	$("#edatagrid").datagrid({
		nowrap: true,
		autoRowHeight: false,
		striped: true,//True 就把行条纹化。（即奇偶行使用不同背景色）
		collapsible:true,
		url:basepath+'admin/role/easyuiPage',
		rownumbers:true,
		pagination:true,
		toolbar:'#tb',
		idField:'id',
		fitColumns:true,// 自适应列宽
		queryParams:{search_EQ_deleted:false},
		frozenColumns:[[
		                {field:'ck',checkbox:true}
		]],
		columns:[[
		    {field:'id',hidden:true},
			{field:'name',title:'角色名称',width:100,sortable:true},
			{field:'role',title:'角色标志',width:50,sortable:true},
			{field:'description',title:'描述',width:300,sortable:true},
			{field:'show',title:'是否显示',width:50,sortable:true}
		]],
		onLoadSuccess:function(data){
			datagridUtils.loadSuccess(data,"edatagrid");
			}
		});
});

var cmsrole = {
		doSearch:function(){
			$('#edatagrid').datagrid('load',{
				search_LIKE_name : $('#rolename').val(),
				search_EQ_deleted:false
			});
		},
		openEditTab:function(){
			var rows = $("#edatagrid").datagrid('getChecked');
			if (!rows || rows.length==0 || rows.length>1){
				eyWindow.walert("修改提示","请选择要修改的行且只能选择一行。", 'info');
				return;
			}
			
			tabUtils.addTopTab('tabPanel','修改角色','admin/role/editRole?fromType=edit&id='+rows[0].id)
		},
		deleteRole:function(){
			var rows = $("#edatagrid").datagrid('getChecked');
			if (!rows || rows.length==0){
				eyWindow.walert("删除提示","请选择要删除的行", 'info');
				return;
			}
			// 删除数据
			$.messager.confirm("确认提示","确认是否删除选中的数据？", function(result){
				if (result){
					datagridUtils.deleteData(rows,"admin/role/batchDelete","edatagrid");
				}
			});
		}
}