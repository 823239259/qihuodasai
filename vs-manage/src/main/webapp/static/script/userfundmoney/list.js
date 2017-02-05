	var gridParams={
				nowrap: true,
				autoRowHeight: false,
				striped: true,
				collapsible:true,
				url:basepath+'admin/config/banner/easyuiPage',
				rownumbers:true,
				toolbar:'#tb',
				pagination:true,
				fitColumns:true,// 自适应列宽
				queryParams:{search_EQ_deleted:false},
				frozenColumns:[[
		            {field:'ck',checkbox:true}
				]],
				columns:[[
					{field:'name',title:'手机号码',width:150,sortable:true,hidden:false},
				    {field:'linkUrl',title:'真实姓名',width:150,sortable:true,hidden:false},
				    {field:'summary',title:'方案编号',width:150,sortable:true,hidden:false},
				    {field:'sss',title:'恒生账号',width:150,sortable:true,hidden:false},
				    {field:'dd',title:'恒生客户姓名',width:150,sortable:true,hidden:false},
				    {field:'dds',title:'追加保证金金额',width:200,sortable:true,hidden:false},
				    {field:'cc',title:'追加时间',width:200,sortable:true,hidden:false}
				
				]],
				onLoadSuccess:function(data){
					datagridUtils.loadSuccess(data,"edatagrid");
				}
	}

$(document).ready(function(){
	var type = $("#configType").val();
	$("#edatagrid").datagrid(gridParams);
});
	
