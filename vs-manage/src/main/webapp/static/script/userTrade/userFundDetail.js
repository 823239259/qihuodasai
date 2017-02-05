var gridParams={
		nowrap: true,
		autoRowHeight: false,
		striped: true,//True 就把行条纹化。（即奇偶行使用不同背景色）
		collapsible:true,
		url:basepath+'admin/allTrades/queryUserFunds',
		toolbar:'#tb',
		rownumbers:true,
		fitColumns:true,// 自适应列宽
		showFooter:true,
		queryParams:{},
		columns:[[
		    {field:'id',hidden:true},
		    {field:'addtime',title:'提取时间',width:160,sortable:true,formatter: function(value,row,index){
				return getFormatDateByLong(value,'yyyy-MM-dd hh:mm:ss');
			}},
		    {field:'money',title:'提取金额',width:114,sortable:true,formatter:function(value,row,index){
				if (isNaN(value)){
					return value;					
				}
				return Math.abs(value);
			}},
		    {field:'payStatusValue',title:'状态',width:100}
		]],
		onLoadSuccess:function(data){
			//datagridUtils.loadSuccess(data,"edatagrid");
			}
		};


$(document).ready(function(){
	var fromType = $("#fromType").val();
	if (fromType==1){
		gridParams.queryParams={groupId:$("#groupId").val(),type:16};
		$("#edatagrid").datagrid(gridParams);
		return;
	}
	
	if (fromType==2){
		gridParams.columns[0][1].title="添加时间";
		gridParams.columns[0][2].title="添加金额";
		gridParams.queryParams={groupId:$("#groupId").val(),type:17};
		$("#edatagrid").datagrid(gridParams);
		return;
	}
});