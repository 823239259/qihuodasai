$(document).ready(function(){
	$("#edatagrid").datagrid({
		nowrap: true,
		autoRowHeight: false,
		striped: true,//True 就把行条纹化。（即奇偶行使用不同背景色）
		collapsible:true,
		url:basepath+'admin/fundConfig/easyuiPage',
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
		    {field:'times', title:'倍数', width:50, sortable:true},
			{field:'fundAmount', title:'配资金额', width:50, sortable:true}
		]],
		onLoadSuccess:function(data){
			datagridUtils.loadSuccess(data,"edatagrid");
			}
		});
});

/*var datamap={
		doSearch:function(){
			$('#edatagrid').datagrid('load',{
				search_LIKE_typeKey : $('#typeKey').val(),
				search_EQ_deleted:false
			});
		}
}*/