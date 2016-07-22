$(document).ready(function(){
	$("#edatagrid").datagrid({
		nowrap: true,
		autoRowHeight: false,
		striped: true,//True 就把行条纹化。（即奇偶行使用不同背景色）
		collapsible:true,
		url:basepath+'admin/smsChannel/easyuiPage',
		toolbar:'#tb',
		rownumbers:true,
		pagination:true,
		fitColumns:true,// 自适应列宽
		frozenColumns:[[
            {field:'ck',checkbox:true}
		]],
		queryParams:{search_EQ_deleted:false, search_LIKE_typeKey:"smsContent"},
		columns:[[
		    {field:'id',hidden:true},
		    {field:'typeKey',hidden:true,},
			{field:'typeName',title:'内容',width:100,sortable:true,},
			{field:'valueName',title:'短信通道',width:100,sortable:true},
			{field:'subordinateSites',title:'所属网站',width:100,sortable:true,
				formatter: function(value,row,index){
					if(row.typeKey=="smsContentRegisterPGB"||row.typeKey=="smsContentOthersPGB"){
						return "配股宝";
					}
					if(row.typeKey=="smsContentRegister"||row.typeKey=="smsContentOthers"){
						return "维胜";
					}
				}},
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