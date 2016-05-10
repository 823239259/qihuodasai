	var initData={dataGridId:'auditData'}
	var gridParams={
				nowrap: true,
				autoRowHeight: false,
				striped: true,//True 就把行条纹化。（即奇偶行使用不同背景色）
				collapsible:true,
				url:basepath+'admin/appendMoneyFail/getDatas', 
				rownumbers:true,
				toolbar:'#audittb',
				pagination:true,
				fitColumns:true,// 自适应列宽
				singleSelect:true,
				frozenColumns:[[
		            {field:'ck',checkbox:true}
				]],
				columns:[[
				    {field:'id',hidden:true},
				    {field:'uid',hidden:true},
					{field:'mobile',title:'手机号',width:100,sortable:true},
					{field:'realName',title:'客户姓名',width:100,sortable:true},
					{field:'accountName',title:'交易账户名',width:100,sortable:true},
					{field:'accountNo',title:'交易账户',width:100,sortable:true},
					{field:'groupId',title:'方案编号',width:150,sortable:true},
					{field:'appendMoney',title:'追加保证金金额',width:200,sortable:true},
					{field:'appendDate',title:'追加时间',width:150,sortable:true},
					{field:'handlerName',title:'处理人',width:100,sortable:true},
					{field:'handleDate',title:'处理时间',width:150,sortable:true},
					{field:'feeType',title:'帐户类型',width:100},
					{field:'status',title:'状态',width:100,sortable:true,formatter:function(val,rec){
						if(val == 0){
							return '未处理';
						}else{
							return '已处理';
						}
					}},
					{field:'activityTypeStr',title:'方案类型',width:100},
					{field:'activityType',hidden:true}
				]],
				onLoadSuccess:function(data){
					appendMoneyFail.loadSuccess(data,initData.dataGridId);
					},
			    rowStyler:function(index,row){
						if (row.status == 0){
							return 'background-color:pink;color:blue;font-weight:bold;';
						}
					}
	}

$(document).ready(function(){
	//设置默认时间
	$('#startTime3').val(getFormatDate(null,"yyyy-MM-dd",null,-1,null)+' 17:00:00');
	$('#endTime3').val(getFormatDate(null,"yyyy-MM-dd")+' 23:59:59');
	$('#startTime4').val(getFormatDate(null,"yyyy-MM-dd")+' 00:00:00');
	$('#endTime4').val(getFormatDate(null,"yyyy-MM-dd")+' 23:59:59');
	//gridParams.queryParams={search_EQ_status:0};
	//$("#"+initData.dataGridId).datagrid(gridParams);
	$('#appendMoneyFailTab').tabs({
	    onSelect:function(title,index){
	    	if (title == '处理列表'){
	    		initData.dataGridId="auditData";
	    		gridParams.toolbar='#audittb';
//	    		gridParams.columns[0][9].hidden=true;
//	    		gridParams.columns[0][10].hidden=true;
	    		gridParams.frozenColumns=[[{field:'ck',checkbox:true}]];
	    		gridParams.queryParams={search_EQ_status:0,search_datetime_GTE_appendDate:getFormatDate(null,"yyyy-MM-dd",null,-1,null)+' 17:00:00',search_datetime_LTE_appendDate:getFormatDate(null,"yyyy-MM-dd")+' 23:59:59'};
	    		$("#"+initData.dataGridId).datagrid(gridParams);
	    	}
	    	
	    	/*if (title == '已处理列表'){
	    		initData.dataGridId="hasAuditData";
	    		gridParams.toolbar='#hasAaudittb';
	    		gridParams.columns[0][9].hidden=false;
	    		gridParams.columns[0][10].hidden=false;
	    		gridParams.frozenColumns="";
	    		gridParams.queryParams={search_EQ_status:1,search_datetime_GTE_appendDate:getFormatDate(null,"yyyy-MM-dd")+' 00:00:00',search_datetime_LTE_appendDate:getFormatDate(null,"yyyy-MM-dd")+' 23:59:59'};
	    		$("#"+initData.dataGridId).datagrid(gridParams);
	    	}*/
	    }
	});
});



var appendMoneyFail={
	list:{
		doSearch1:function(){
			$('#auditData').datagrid('load',{
				search_LIKE_mobile : $('#search_LIKE_mobile').val(),
				search_LIKE_accountNo : $('#search_LIKE_accountNo').val(),
				search_LIKE_accountName:$("#search_LIKE_accountName").val(),
				search_datetime_GTE_appendDate:$("#startTime3").val(),
				search_datetime_LTE_appendDate:$("#endTime3").val(),
				search_datetime_GTE_handleDate:$("#startTime3_1").val(),
				search_datetime_LTE_handleDate:$("#endTime3_1").val(),
				search_IN_feeType : $("#feeType").combobox('getValue'),
				search_EQ_status:$("#status").combobox('getValue'),
				search_LIKE_handlerName : $('#search_LIKE_handlerName').val(),
				search_EQ_activityType:$("#activityType").combobox('getValue'),
			});
		}/*,
		doSearch2:function(){
			$('#hasAuditData').datagrid('load',{
				search_EQ_status:1,
				search_LIKE_mobile : $('#search_LIKE_mobile1').val(),
				search_LIKE_accountNo : $('#search_LIKE_accountNo1').val(),
				search_LIKE_accountName:$("#search_LIKE_accountName1").val(),
				search_datetime_GTE_appendDate:$("#startTime4").val(),
				search_datetime_LTE_appendDate:$("#endTime4").val(),
				search_IN_feeType : $("#feeType1").combobox('getValue'),
				search_LIKE_handlerName : $('#search_LIKE_handlerName').val()
			});
		}*/
	},
	handleFail:function(){
		var rows = $("#auditData").datagrid('getChecked');
		if (!rows || rows.length==0){
			eyWindow.walert("提示","请选择数据", 'info');
			return;
		}
		var ids = [];
		for (var i=0;i<rows.length;i++)
		{
			ids.push(rows[i].id+":"+rows[i].activityType);
		}
		
		// 删除数据
		$.messager.confirm("确认提示","请确认您选择的记录是否已处理？", function(result){
			if (result){
				ajaxPost({
					url : basepath + "admin/appendMoneyFail/handleFail",
					cache : false,
					async : false,
					data : {
						"ids" :ids.join(",")
					},
					success : function(data) {
						if (data.success) {
							$("#auditData").datagrid('reload');
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
		});
	},
	loadSuccess:function(data,datagridID){
		//alert($('#'+datagridID).closest('div.datagrid-wrap').html());
		if ($('#'+datagridID).closest('div.datagrid-wrap').find('.noDataInfo')){
			$('#'+datagridID).closest('div.datagrid-wrap').find('.noDataInfo').remove();
		}
		
		if(data.total>0){
			$('#'+datagridID).closest('div.datagrid-wrap').find('div.datagrid-pager').show();
	        return;
		}
		/*$('#'+datagridID).datagrid('appendRow',{
			username: '<div style="text-align:center;color:red">没有相关记录！</div>' }).datagrid('mergeCells', { index: 0, field:filedName, colspan: filedNum });*/
		 //隐藏分页导航条，这个需要熟悉datagrid的html结构，直接用jquery操作DOM对象，easyui datagrid没有提供相关方法隐藏导航条
		$('#'+datagridID).closest('div.datagrid-wrap').find('div.datagrid-pager').hide();
		$('#'+datagridID).closest('div.datagrid-wrap').append('<div id="noDataInfo" class="noDataInfo" style="text-align:center;color:red;padding:5px;">没有相关记录！</div>');
	}
}