$(document).ready(function(){
	$("#edatagrid").datagrid({
		nowrap: true,
		autoRowHeight: false,
		striped: true,//True 就把行条纹化。（即奇偶行使用不同背景色）
		collapsible:true,
		url:basepath+'admin/withdrawAudit/userFundDetail',
		toolbar:'#tb',
		rownumbers:true,
		showFooter:true,
		pagination:true,
		fitColumns:true,// 自适应列宽
		queryParams:{search_EQ_uid:$("#userId").val()},
		columns:[[
		    {field:'id',hidden:true},
		    {field:'money',hidden:true},
		    {field:'addtime',title:'提交时间',width:180,sortable:true,formatter: function(value,row,index){
				return getFormatDateByLong(value,'yyyy-MM-dd hh:mm:ss');
			}},
		 	{field:'uptime',title:'到账时间',width:180,sortable:true,formatter: function(value,row,index){
				return getFormatDateByLong(value,'yyyy-MM-dd hh:mm:ss');
			}},
			{field:'typevalue',title:'类型',width:100},
			{field:'money1',title:'收入',width:150,formatter: function(value,row,index){
				//显示小计或总计
				if(!validateIsNull(value)){
					return value;
				}
				if (Number(row.money) < 0){
					return "";
				}
				return row.money;
			}},
			{field:'money2',title:'支出',width:150,formatter: function(value,row,index){
				//显示小计或总计
				if(!validateIsNull(value)){
					return value;
				}
				if (Number(row.money) > 0){
					return "";
				}
				return row.money;
			}},
			{field:'payStatusValue',title:'支付状态',width:100},
			{field:'amount',title:'平台余额',width:150,sortable:true}
		]],
		onLoadSuccess:function(data){
			datagridUtils.loadSuccess(data,"edatagrid");
			}
		});
});


var userFund={
		doSearch:function(){
			$('#edatagrid').datagrid('load',{
				search_EQ_uid:$("#userId").val(),
				search_EQ_type:$("#fundType").combobox('getValue'),
				search_date_GTE_addtime:$("#startTime").val(),
				search_date_LTE_addtime:$("#endTime").val()
			});
		}
}