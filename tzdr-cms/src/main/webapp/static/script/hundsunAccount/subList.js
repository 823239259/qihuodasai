$(document).ready(function(){
	$("#edatagrid").datagrid({
		nowrap: true,
		autoRowHeight: false,
		striped: true,//True 就把行条纹化。（即奇偶行使用不同背景色）
		collapsible:true,
		url:basepath+'admin/subAccount/getDatas',
		toolbar:'#tb',
		rownumbers:true,
		pagination:true,
		fitColumns:true,// 自适应列宽
		frozenColumns:[[
            {field:'ck',checkbox:true}
		]],
		columns:[[
		    {field:'id',hidden:true},
		    {field:'accountName',title:'恒生帐户名称',width:100,sortable:true},
			{field:'account',title:'恒生帐号',width:100,sortable:true},
			{field:'assetId',title:'单元序号',width:200,sortable:true},
			{field:'insuranceNo',title:'保险单号',width:150,sortable:true},
			{field:'parentAccountName',title:'母账户名称',width:100,sortable:true},
			{field:'useStatus',title:'使用状态',width:200},
			{field:'tname',title:'客户实名',width:100,sortable:true},
			{field:'mobile',title:'客户手机号',width:120,sortable:true},
			{field:'endtime',title:'账户终结时间',width:150,sortable:true,formatter: function(value,row,index){
				return getFormatDateByLong(value,'yyyy-MM-dd hh:mm:ss');
			}},
			{field:'createTime',title:'创建时间',width:150,sortable:true,formatter: function(value,row,index){
				return getFormatDateByLong(value,'yyyy-MM-dd hh:mm:ss');
			}},
			{field:'createUser',title:'创建人',width:100,sortable:true}
		]],
		onLoadSuccess:function(data){
			datagridUtils.loadSuccess(data,"edatagrid");
			}
		});
});


var subAccount={
		doSearch:function(){
			$('#edatagrid').datagrid('load',{
				search_LIKE_account : $('#search_account').val(),
				search_LIKE_useStatus : $("#userstatus").combobox('getValue'),
				search_date_GTE_createTime:$("#startTime").val(),
				search_date_LTE_createTime:$("#endTime").val(),
				search_LIKE_parentAccountName:$('#search_parentAccountName').val()
			});
		},
		refreshCombine:function(){
			ajaxPost({
				url : basepath + "admin/combine/refresh",
				cache : false,
				async : false,
				data : {},
				success : function(data) {
					if (data.success) {
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