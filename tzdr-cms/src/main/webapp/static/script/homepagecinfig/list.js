$(document).ready(function(){
	$("#edatagrid").datagrid({
		nowrap: true,
		autoRowHeight: false,
		striped: true,//True 就把行条纹化。（即奇偶行使用不同背景色）
		collapsible:true,
		url:basepath+'admin/homepagecinfig/easyuiPage',
		toolbar:'#tb',
		rownumbers:true,
		pagination:true,
		fitColumns:true,// 自适应列宽
		frozenColumns:[[
            {field:'ck',checkbox:true}
		]],
		queryParams:{search_EQ_deleted:false},
		columns:[[
		    {field:'id',hidden:true},
		    {field:'typeKey',title:'类型key',width:200,sortable:true},
			{field:'typeName',title:'类型中文名称',width:100,sortable:true},
		    {field:'valueKey',title:'值key',width:100,sortable:true},
		    {field:'valueName',title:'值中文名称',width:100,sortable:true},
			{field:'valueData',title:'值',width:100,sortable:true},
			{field:'weight',title:'权重',width:100,sortable:true}
		]],
		onLoadSuccess:function(data){
			datagridUtils.loadSuccess(data,"edatagrid");
			}
		});
});


var datamap={
		doSearch:function(){
			$('#edatagrid').datagrid('load',{
				search_LIKE_typeKey : $('#typeKey').val(),
				search_EQ_deleted:false
			});
		}
}