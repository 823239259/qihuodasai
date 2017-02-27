$(document).ready(function(){
	$("#edatagrid").datagrid({
		nowrap: true,
		autoRowHeight: false,
		striped: true,//True 就把行条纹化。（即奇偶行使用不同背景色）
		collapsible:true,
		checkOnSelect:true,
		singleSelect:true,
		url:basepath+'admin/parities/getDatas',
		toolbar:'#tb',
		sortName:'createTime',
		sortOrder:'DESC',
		rownumbers:true,
		pagination:true,
		fitColumns:true,// 自适应列宽
		queryParams:{search_EQ_deleted:false},
		frozenColumns:[[
            {field:'ck',checkbox:true}
		]],
		columns:[[
		    {field:'id',hidden:true},
			{field:'typeName',title:'汇率类型',width:100,sortable:true},
			{field:'parities',title:'汇率',width:100,sortable:true},
			{field:'createUser',title:'创建人',width:100,sortable:true},
			{field:'createTime',title:'创建时间',width:100,sortable:true},
			{field:'updateUser',title:'最后修改人',width:100,sortable:true},
			{field:'updateTime',title:'最后修改时间',width:100,sortable:true},
		]],
		onLoadSuccess:function(data){
			datagridUtils.loadSuccess(data,"edatagrid");
			}
		});
	$("#edatagrid2").datagrid({
		nowrap: true,
		autoRowHeight: false,
		striped: true,//True 就把行条纹化。（即奇偶行使用不同背景色）
		collapsible:true,
		checkOnSelect:true,
		singleSelect:true,
		url:basepath+'admin/poundageParities/getPoundageDatas',
		toolbar:'#tb2',
		sortName:'createTime',
		sortOrder:'DESC',
		rownumbers:true,
		pagination:true,
		fitColumns:true,// 自适应列宽
		queryParams:{search_EQ_deleted:false},
		frozenColumns:[[
		                {field:'ck',checkbox:true}
		                ]],
		                columns:[[
		                          {field:'id',hidden:true},
		                          {field:'typeName',title:'汇率类型',width:100,sortable:true},
		                          {field:'parities',title:'汇率',width:100,sortable:true},
		                          {field:'createUser',title:'创建人',width:100,sortable:true},
		                          {field:'createTime',title:'创建时间',width:100,sortable:true},
		                          {field:'updateUser',title:'最后修改人',width:100,sortable:true},
		                          {field:'updateTime',title:'最后修改时间',width:100,sortable:true},
		                          ]],
		                          onLoadSuccess:function(data){
		                        	  datagridUtils.loadSuccess(data,"edatagrid2");
		                          }
	});
});

function doSearch(){
	$('#edatagrid').datagrid('load',{
		search_date_GTE_createTime:$("#startTime").val(),
		search_date_GTE_createTime:$("#endTime").val(),
		search_EQ_deleted:false
	});
}
function showCreateWin(type){
	var id = "";
	if(type==2){
		if (!datagridUtils.checkSelected("edatagrid","请选择需要修改的记录！")){
			return;
		}
		id = $("#edatagrid").datagrid('getChecked')[0].id;
	}
	var html = '<iframe scrolling="yes" frameborder="0"  src="'+basepath+'admin/parities/edit?id='+id+'" style="width:100%;height:100%;"></iframe>';
	$('#addWin').window({collapsible:false,minimizable:false,maximizable:false,width:650,height:300,title:"新增/修改汇率",loadingMessage:'正在加载,请等待......',iconCls:'icon-add',closed:true,modal:true,content:html});
	$('#addWin').window('open');
}
function showCreateWin2(type){
	var id = "";
	if(type==2){
		if (!datagridUtils.checkSelected("edatagrid2","请选择需要修改的记录！")){
			return;
		}
		id = $("#edatagrid2").datagrid('getChecked')[0].id;
	}
	var html = '<iframe scrolling="yes" frameborder="0"  src="'+basepath+'admin/poundageParities/edit?id='+id+'" style="width:100%;height:100%;"></iframe>';
	$('#addWin2').window({collapsible:false,minimizable:false,maximizable:false,width:650,height:300,title:"新增/修改汇率",loadingMessage:'正在加载,请等待......',iconCls:'icon-add',closed:true,modal:true,content:html});
	$('#addWin2').window('open');
}