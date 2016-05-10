$(document).ready(function(){
	$("#edatagrid").datagrid({
		nowrap: true,
		autoRowHeight: false,
		striped: true,//True 就把行条纹化。（即奇偶行使用不同背景色）
		collapsible:true,
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
			{field:'dateTime',title:'日期',width:100,sortable:true,formatter : function(value, row, index) {
				if(value!=null){
					return getSmpFormatDateByLong(value*1000, false);
				}
				return "";
			}},
			{field:'isTrade',title:'是否交易日',width:100,sortable:true,formatter: function(value,row,index){
				if (value){
					return "是";
				}
				return "否";
			}}
		]],
		onLoadSuccess:function(data){
			datagridUtils.loadSuccess(data,"edatagrid");
			}
	});
	$("#hkedatagrid").datagrid({
		nowrap: true,
		autoRowHeight: false,
		striped: true,//True 就把行条纹化。（即奇偶行使用不同背景色）
		collapsible:true,
		toolbar:'#hktb',
		rownumbers:true,
		pagination:true,
		fitColumns:true,// 自适应列宽
		queryParams:{search_EQ_deleted:false},
		frozenColumns:[[
            {field:'ck',checkbox:true}
		]],
		columns:[[
		    {field:'id',hidden:true},
			{field:'dateTime',title:'日期',width:100,sortable:true,formatter : function(value, row, index) {
				if(value!=null){
					return getSmpFormatDateByLong(value*1000, false);
				}
				return "";
			}},
			{field:'isTrade',title:'是否交易日',width:100,sortable:true,formatter: function(value,row,index){
				if (value){
					return "是";
				}
				return "否";
			}}
		]],
		onLoadSuccess:function(data){
			datagridUtils.loadSuccess(data,"hkedatagrid");
			}
	});
	$('#tradeDayTab').tabs(
		{
			onSelect : function(title, index) {
				if (title == 'A股交易日') {
					$('#edatagrid').datagrid('options').url = basepath+'admin/tradeDay/easyuiPage';
					$('#edatagrid').datagrid('reload');
				}
				if (title == '港股股交易日') {
					$('#hkedatagrid').datagrid('options').url = basepath+'admin/hkstock/hkTradeDay/easyuiPage';
					$('#hkedatagrid').datagrid('reload');
				}
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
var hkTradeDay={
		openEditwin:function(width,height,title,modelName){
			var rowData=$('#hkedatagrid').datagrid('getSelected');
			if (null == rowData){
				eyWindow.walert("修改提示",'请选择要修改的行', 'info');
				return;
			}
			//var html = '<iframe scrolling="yes" frameborder="0"  src="'+basepath+'admin/user/edit?fromType=edit&id='+rowData.id+'" style="width:100%;height:100%;"></iframe>';
			$('#addWin').window({collapsible:false,minimizable:false,maximizable:false,width:width,height:height,title:title,loadingMessage:'正在加载,请等待......',iconCls:'icon-edit',closed:true,modal:true,href:basepath+'admin/'+modelName+'/edit?fromType=edit&id='+rowData.id});
			$('#addWin').window('open');
		},
		doSearch:function(){
			$('#hkedatagrid').datagrid('load',{
				search_date_GTE_dateTime:$("#hkstartTime").val(),
				search_date_LTE_dateTime:$("#hkendTime").val(),
				search_EQ_deleted:false
			});
		},
		showCreateWin:function(){
			$('#hkcreateWin').window('open');
		},
		createCalenr:function(){
			//alert(type);
			if ($("#hkcreateForm").form('validate') == false) {
				return;
			}
			ajaxPost({
				url : basepath + "admin/hkstock/hkTradeDay/createCalener",
				cache : false,
				async : false,
				data :{year:$("#hkyear").val()},
				success : function(data) {
					if (data.success) {
						$('#hkcreateWin').window('close');
						$("#hkedatagrid").datagrid('reload');
						eyWindow.walert("成功提示", data.message, 'info');
						return;
					}
					eyWindow.walert("错误提示", data.message, 'error');
				},
				error : function() {
					eyWindow.walert("错误提示", "系统异常", 'error');
				}
			});
		},
		saveOrUpdate:function(url){
			if ($("#addForm").form('validate') == false) {
				return;
			}
			//alert($("#addForm").serialize());
			ajaxPost({
				url : basepath + url,
				cache : false,
				async : false,
				data : $("#addForm").serialize(),
				success : function(data) {
					if (data.success) {
						$('#addWin').window('close');
						$("#hkedatagrid").datagrid('reload');
						eyWindow.walert("成功提示", data.message, 'info');
						return;
					}
					eyWindow.walert("错误提示", data.message, 'error');
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					eyWindow.walert("错误提示", "系统异常，错误类型textStatus: " + textStatus
							+ ",异常对象errorThrown: " + errorThrown, 'error');
				}
			});
		}
}