$(document).ready(function(){
	$("#edatagrid").datagrid({
		nowrap: true,
		autoRowHeight: false,
		striped: true,//True 就把行条纹化。（即奇偶行使用不同背景色）
		collapsible:true,
		url:basepath+'admin/permission/easyuiPage',
		rownumbers:true,
		toolbar:'#tb',
		pagination:true,
		fitColumns:true,// 自适应列宽
		columns:[[
		    {field:'id',hidden:true},
			{field:'name',title:'名称',width:100,sortable:true},
			{field:'description',title:'描述',width:300,sortable:true}
		]],
		onLoadSuccess:function(data){
			datagridUtils.loadSuccess(data,"edatagrid");
			}
		});
});

var permission = {
		doSearch:function(){
			$('#edatagrid').datagrid('load',{
				search_LIKE_name : $('#name').val()
			});
		}
}