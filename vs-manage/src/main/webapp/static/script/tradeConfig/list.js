$(document).ready(function(){
	$("#edatagrid").datagrid({
		nowrap: true,
		autoRowHeight: false,
		striped: true,//True 就把行条纹化。（即奇偶行使用不同背景色）
		collapsible:true,
		url:basepath+'admin/tradeConfig/easyuiPage',
		toolbar:'#tb',
		rownumbers:true,
		pagination:true,
		fitColumns:true,// 自适应列宽
		queryParams:{search_EQ_deleted:false},
		frozenColumns:[[
            {field:'ck',checkbox:true}
		]],
		columns:[[
		    {field:'id',hidden:true},
		    {field:'dayRangeStart',hidden:true},
		    {field:'dayRangeEnd',hidden:true},
		    {field:'depositRangeStart',hidden:true},
		    {field:'depositRangeEnd',hidden:true},
		    {field:'multipleRangeStart',hidden:true},
		    {field:'multipleRangeEnd',hidden:true},
		    {field:'days',title:'期限(天)',width:100,formatter: function(value,row,index){
				return row.dayRangeStart +"-"+row.dayRangeEnd;
			}},
			{field:'deposit',title:'保证金',width:100,formatter: function(value,row,index){
				return row.depositRangeStart +"-"+row.depositRangeEnd;
			}},
			{field:'multiple',title:'倍数',width:100,formatter: function(value,row,index){
				return row.multipleRangeStart +"-"+row.multipleRangeEnd;
			}},
			{field:'interestDay',title:'利息计算天数',width:100,sortable:true},
			{field:'yearInterest',title:'利息(年)',width:100,sortable:true},
			{field:'dailyInterest',title:'利息(日)',width:100,sortable:true},
			{field:'managementFeeDay',title:'管理费计算天数',width:100,sortable:true},
			{field:'yearManagementFee',title:'管理费(年)',width:100,sortable:true},
			{field:'dailyManagementFee',title:'管理费(日)',width:100,sortable:true}
		]],
		onLoadSuccess:function(data){
			datagridUtils.loadSuccess(data,"edatagrid");
			}
		});
});
var tradeConfig={
		doSearch:function(){
			$('#edatagrid').datagrid('load',{
				search_GTE_dayRangeStart:$("#dayRangeStart").val(),
				search_LTE_dayRangeEnd:$("#dayRangeEnd").val(),
				search_GTE_depositRangeStart:$("#depositRangeStart").val(),
				search_LTE_depositRangeEnd:$("#depositRangeEnd").val(),
				search_GTE_multipleRangeStart:$("#multipleRangeStart").val(),
				search_LTE_multipleRangeEnd:$("#multipleRangeEnd").val(),
				search_EQ_deleted:false
			});
		},
		showUpdateDaysWin:function(title,type){
			$('#updateDaysWin').panel({title:title});
			$("#submitDays").unbind('click');
			$("#submitDays").bind('click', function() {
				tradeConfig.updateDays(type);
			});
			$('#updateDaysWin').window('open');
		},
		updateDays:function(type){
			//alert(type);
			if ($("#updateDaysForm").form('validate') == false) {
				return;
			}
			ajaxPost({
				url : basepath + "admin/tradeConfig/setDays",
				cache : false,
				async : false,
				data :{days:$("#days").val(),utype:type},
				success : function(data) {
					if (data.success) {
						$('#updateDaysWin').window('close');
						$("#edatagrid").datagrid('reload');
						$("#days").val("")
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
}