	var initData={dataGridId:'auditData'}
	var gridParams={
				nowrap: true,
				autoRowHeight: false,
				striped: false,//True 就把行条纹化。（即奇偶行使用不同背景色）
				collapsible:true,
				url:basepath+'admin/Activity/getDatas', 
				rownumbers:true,
				toolbar:'#audittb',
				pagination:true,
				singleSelect:true,
				fitColumns:true,// 自适应列宽
				frozenColumns:[[
		            {field:'ck',checkbox:true}
				]],
				columns:[[
				    {field:'id',hidden:true},
				    {field:'uid',hidden:true},
					{field:'userPhone',title:'手机号',width:100,sortable:true},
					{field:'realName',title:'客户姓名',width:100,sortable:true},
					{field:'kudoName',title:'奖品名称',width:100,sortable:true},
					{field:'kudoGetTime',title:'获奖时间',width:100,sortable:true},
					{field:'kudoUseTime',title:'使用时间',width:150,sortable:true},
					{field:'activityType',title:'活动类型',width:200,sortable:true,formatter:function(val,rec){
						if(val==1){
							return "微信抽奖";
						}else if(val==2){
							return "开箱壕礼";
						}else if(val==3){
							return "新春礼包";
						}
					}},
					{field:'kudoStatus',title:'状态',width:100,sortable:true,formatter:function(val,rec){
						if(val == 1){
							return '未使用';
						}else if(val == 2){
							return '已使用';
						}else if(val == 3){
							return '申请使用';
						}
					}}
				]],
				onLoadSuccess:function(data){
					Activity.list.loadSuccess(data,initData.dataGridId);
					},
//			    rowStyler:function(index,row){
//						if (row.status == 0){
//							return 'background-color:pink;color:blue;font-weight:bold;';
//						}
//					}
	}

$(document).ready(function(){
	//设置默认时间
//	$('#startTime3').val(getFormatDate(null,"yyyy-MM-dd")+' 00:00:00');
//	$('#endTime3').val(getFormatDate(null,"yyyy-MM-dd")+' 23:59:59');
	//gridParams.queryParams={search_EQ_status:0};
	$("#"+initData.dataGridId).datagrid(gridParams);
	$('#activityTab').tabs({
	    onSelect:function(title,index){
	    	if (title == '开箱壕礼'){
	    		initData.dataGridId="auditData";
	    		gridParams.toolbar='#audittb';
	    		gridParams.frozenColumns=[[{field:'ck',checkbox:true}]];
	    	//	gridParams.queryParams={search_GTE_status:0};
	    		$("#"+initData.dataGridId).datagrid(gridParams);
	    	}
	    	
	    }
	});
});



var Activity={
	list:{
		doSearch1:function(){
			$('#auditData').datagrid('load',{
				search_LIKE_userPhone : $('#search_LIKE_mobile').val(),
				search_datetime_GTE_kudoGetTime:$("#startTime3").val(),
				search_datetime_LTE_kudoGetTime:$("#endTime3").val(),
				search_IN_activityType : $("#activityType").combobox('getValue'),
				search_EQ_kudoStatus:$("#kudoStatus").combobox('getValue'),
			});
	},
	usekudo:function(){
		var rows = $("#auditData").datagrid('getChecked');
		if (!rows || rows.length==0){
			eyWindow.walert("提示","请选择数据", 'info');
			return;
		}
		if(rows[0].kudoStatus==2){
			eyWindow.walert("提示","已使用过的奖品不可以再使用！！", 'info');
			return;
		}
		
		var ids = [];
		for (var i=0;i<rows.length;i++)
		{
			ids.push(rows[i].id);
			
		}
		
		// 处理数据
		$.messager.confirm("确认提示","是否确认使用奖品？", function(result){
			if (result){
				ajaxPost({
					url : basepath + "admin/Activity/usekudo",
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
	changekudo:function(){
		var rows = $("#auditData").datagrid('getChecked');
		if (!rows || rows.length==0){
			eyWindow.walert("提示","请选择数据", 'info');
			return;
		}
		if(rows[0].kudoStatus==2){
			eyWindow.walert("提示","已使用状态不能再进行未使用变更！！", 'info');
			return;
		}
		if(rows[0].kudoStatus==1){
			eyWindow.walert("提示","已是未使用状态,无需变更！！", 'info');
			return;
		}
		
		var ids = [];
		for (var i=0;i<rows.length;i++)
		{
			ids.push(rows[i].id);
			
		}
		
		// 处理数据
		$.messager.confirm("确认提示","是否确认奖品状态变更为未使用？", function(result){
			if (result){
				ajaxPost({
					url : basepath + "admin/Activity/changekudo",
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
}