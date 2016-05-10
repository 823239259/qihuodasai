$(document).ready(function(){
	$("#edatagrid").datagrid({
		nowrap: true,
		autoRowHeight: false,
		striped: true,//True 就把行条纹化。（即奇偶行使用不同背景色）
		collapsible:true,
		url:basepath+'admin/investor/easyuiPage',
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
			{field:'realName',title:'真实姓名',width:100,sortable:true},
			{field:'idCard',title:'身份证号',width:100,sortable:true},
			{field:'createUser',title:'创建人',width:100,sortable:true},
			{field:'createTime',title:'创建时间',width:150,sortable:true,formatter: function(value,row,index){
				if (validateIsNull(value)){
					return;
				}
				return getFormatDateByLong(value,'yyyy-MM-dd hh:mm:ss');
			}}
		]],
		onLoadSuccess:function(data){
			datagridUtils.loadSuccess(data,"edatagrid");
			}
		});
});
var tradeDay={
		doSearch:function(){
			$('#edatagrid').datagrid('load',{
				search_date_GTE_dateTime:$("#startTime").val(),
				search_date_LTE_dateTime:$("#endTime").val(),
				search_EQ_deleted:false
			});
		},
		showCreateWin:function(){
			$('#createWin').window('open');
		},
		createCalenr:function(){
			//alert(type);
			if ($("#createForm").form('validate') == false) {
				return;
			}
			ajaxPost({
				url : basepath + "admin/tradeDay/createCalener",
				cache : false,
				async : false,
				data :{year:$("#year").val()},
				success : function(data) {
					if (data.success) {
						$('#createWin').window('close');
						$("#edatagrid").datagrid('reload');
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