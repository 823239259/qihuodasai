$(document).ready(function(){
	$("#edatagrid").datagrid({
		pageSize: 50,//每页显示的记录条数，默认为10
		nowrap: true,
		autoRowHeight: false,
		striped: true,//True 就把行条纹化。（即奇偶行使用不同背景色）
		collapsible:true,
		url:basepath+'admin/parentAccount/listData',
		toolbar:'#tb',
		rownumbers:true,
		singleSelect:true,
		pagination:true,
		fitColumns:true,// 自适应列宽
		frozenColumns:[[
            {field:'ck',checkbox:true}
		]],
		columns:[[
		    {field:'id',hidden:true},
			{field:'accountNo',title:'账户编号',width:100},
			{field:'accountName',title:'账户名称',width:100},
			{field:'accountGenreStr',title:'账户交易通道',width:100},
			{field:'priorityNo',title:'账户优先级编号',width:200},
			{field:'accountType',title:'账户类型',width:100,sortable:true},
			{field:'securitiesBusiness',title:'所属券商',width:100},
			{field:'totalFunds',title:'资金总额(元)',width:100,formatter: function(value,row,index){
				return moneyUtils.format(value,2);
			}},
			{field:'fundsBalance',title:'资金余额(元)',width:100,formatter: function(value,row,index){
				return moneyUtils.format(value,2);
			}},
			{field:'subFunds',title:'暂停金额',width:100,formatter: function(value,row,index){
				return moneyUtils.format(value,2);
			}},
			{field:'multipleStr',title:'配资倍数',width:100},
			{field:'amountStr',title:'账户金额',width:100},
			{field:'newAndOldStateStr',title:'新老用户规则',width:100},
			{field:'allocationDateStr',title:'截止日期',width:100},
			{field:'createTimeStr',title:'创建时间',width:200},
			{field:'createUser',title:'创建人',width:100}
		]]
	/**
	,
		onLoadSuccess:function(data){
			datagridUtils.loadSuccess(data,"edatagrid");
			}**/
		});
});


var parentAccount={
		doSearch:function(){
			$('#edatagrid').datagrid('load',{
				search_LIKE_accountName : $('#search_accountName').val(),
				search_LIKE_accountNo : $('#search_accountNo').val(),
				search_LIKE_accountType : $("#search_accountType").combobox('getValue'),
				search_date_GTE_createTime:$("#startTime").val(),
				search_date_LTE_createTime:$("#endTime").val(),
				search_LIKE_createUser:$("#search_user").val(),
				search_EQ_deleted:false
			});
		},
		openChangePwdWin:function(){
			var rows = $("#edatagrid").datagrid('getChecked');
			if (!rows || rows.length !=1 ){
				eyWindow.walert("修改提示","请选择要修改的行且只能选择一行", 'info');
				return;
			}
			$('#addWin').window({collapsible:false,minimizable:false,maximizable:false,width:500,height:260,title:'修改密码',loadingMessage:'正在加载,请等待......',iconCls:'icon-edit',closed:true,modal:true,href:basepath+'admin/parentAccount/changePassword?id='+rows[0].id});
			$('#addWin').window('open');
		},
		openEditBalanceWin:function(){ 
			var rows = $("#edatagrid").datagrid('getChecked');
			if (!rows || rows.length !=1 ){
				eyWindow.walert("修改提示","请选择要修改的行且只能选择一行", 'info');
				return;
			}
			$('#addWin').window({collapsible:false,minimizable:false,maximizable:false,width:600,height:230,title:'修改账户余额',loadingMessage:'正在加载,请等待......',iconCls:'icon-edit',closed:true,modal:true,href:basepath+'admin/parentAccount/toUpdateBalance?id='+rows[0].id});
			$('#addWin').window('open');
		},
		updatePasswod:function(){
			if ($("#updateForm").form('validate') == false) {
				return;
			}
			
			ajaxPost({
				url : basepath + "admin/parentAccount/updatePasswod",
				cache : false,
				async : false,
				data : {
					"id" : $("#accountid").val(),
					"password" : $("#password").val(),
					"newPasswod" : $("#password1").val()
				},
				success : function(data) {
					//$('#updatePwdWin').window('close');
					if (data.success) {
						$('#addWin').window('close');
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
			var isChecked = true;
			$("input[name=newAndOldStateStr]").each(function(){
				if ($(this).attr("checked")) {
					isChecked = false;
				}
			});
			if (isChecked && url != "admin/parentAccount/updateBalance") {
				Check.messageBox("提示","【新老用户抓取】为必选项!","warning");
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