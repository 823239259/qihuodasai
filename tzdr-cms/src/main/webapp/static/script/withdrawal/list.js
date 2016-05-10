	var initData={dataGridId:'allData'}
	var gridParams={
				nowrap: true,
				autoRowHeight: false,
				striped: true,//True 就把行条纹化。（即奇偶行使用不同背景色）
				collapsible:true,
				url:basepath+'admin/withdrawal/getDatas', 
				rownumbers:true,
				toolbar:'#alltb',
				showFooter:true,
				pagination:true,
				fitColumns:true,// 自适应列宽
				frozenColumns:[[
		            {field:'ck',checkbox:true}
				]],
				columns:[[
				    {field:'id',hidden:true},
				    {field:'tname',title:'客户姓名',width:100,sortable:true},
					{field:'mobile',title:'手机号',width:100,sortable:true},
					{field:'paymentChannelStr',title:'网银通道',width:100},
					{field:'bank',title:'提现银行',width:200,sortable:true},
					{field:'card',title:'银行卡号',width:200,sortable:true},
					{field:'money',title:'提现金额',width:200,sortable:true},
					{field:'addtime',title:'提现时间',width:150,sortable:true,formatter: function(value,row,index){
						if (validateIsNull(value)){
							return;
						}
						return getFormatDateByLong(value,'yyyy-MM-dd hh:mm:ss');
					}},
					{field:'status',hidden:false,title:'提现状态',width:100,sortable:true},
					{field:'oktime',title:'到账时间',width:150,hidden:false,sortable:true,formatter: function(value,row,index){
						if (validateIsNull(value)){
							return;
						}
						return getFormatDateByLong(value,'yyyy-MM-dd hh:mm:ss');
					}},
					{field:'sourceStr',title:'来源网站',width:150,formatter: function(value,row,index){
						if(row.card=='合计'){
							return '';
						}else{
							return value;
						}
					}},
				]],
				onLoadSuccess:function(data){
					withdrawal.loadSuccess(data,initData.dataGridId);
					}
	}

$(document).ready(function(){
	$("#"+initData.dataGridId).datagrid(gridParams);
	$('#withdrawalTab').tabs({
	    onSelect:function(title,index){
	    	//alert(gridParams.columns[0][6].hidden);
	    	if (title == '所有记录'){
	    		initData.dataGridId="allData";
	    		gridParams.toolbar='#alltb';
	    		gridParams.queryParams={};
		    	gridParams.columns[0][8].hidden=false;
		    	gridParams.columns[0][9].hidden=false;
	    		$("#"+initData.dataGridId).datagrid(gridParams);
	    	}
	    	
	    	if (title == '提现失败'){
	    		initData.dataGridId="failData";
	    		gridParams.toolbar='#failtb';
	    		gridParams.queryParams={search_EQ_status:4};
	    		gridParams.columns[0][8].hidden=true;
	    		gridParams.columns[0][9].hidden=true;
	    		$("#"+initData.dataGridId).datagrid(gridParams);
	    	}
	    	if (title == '提现处理中审核'){
	    		initData.dataGridId="processingData";
	    		gridParams.toolbar='#processingtb';
	    		gridParams.queryParams={search_EQ_status:21,search_LTE_addtime:parseInt(new Date().getTime()/1000)-86400};
	    		gridParams.columns[0][8].hidden=true;
	    		gridParams.columns[0][9].hidden=true;
	    		$("#"+initData.dataGridId).datagrid(gridParams);
	    	}
	    	
	    	if (title == '提现成功'){
	    		initData.dataGridId="successData";
	    		gridParams.toolbar='#successtb';
	    		gridParams.queryParams={search_EQ_status:31};
	    		gridParams.columns[0][8].hidden=true;
	    		gridParams.columns[0][9].hidden=false;
	    		$("#"+initData.dataGridId).datagrid(gridParams);
	    		
	    	}
	    }
	});
});



var withdrawal={
	list:{
		doSearch1:function(){
			$('#allData').datagrid('load',{
				search_LIKE_mobile : $('#mobile1').val(),
				search_LIKE_bank : $('#bank1').val(),
				search_EQ_status : $('#status').combobox('getValue'),
				search_date_GTE_addtime:$("#startTime").val(),
				search_date_LTE_addtime:$("#endTime").val(),
				search_date_GTE_oktime:$("#startTime1").val(),
				search_date_LTE_oktime:$("#endTime1").val(),
				search_LIKE_tname:$("#tname").val(),
				search_EQ_paymentChannel : $('#paymentChannel').combobox('getValue'),
				search_EQ_source : $('#source1').combobox('getValue')
			});
		},
		doSearch2:function(){
			$('#processingData').datagrid('load',{
				search_LIKE_mobile : $('#mobile2').val(),
				search_EQ_status:21,
				search_LTE_addtime:parseInt(new Date().getTime()/1000)-86400,
				search_EQ_source : $('#source2').combobox('getValue')
			});
		},
		doSearch3:function(){
			$('#failData').datagrid('load',{
				search_LIKE_mobile : $('#mobile3').val(),
				search_EQ_status:4,
				search_EQ_source : $('#source3').combobox('getValue')
			});
		},
		doSearch4:function(){
			$('#successData').datagrid('load',{
				search_LIKE_mobile : $('#mobile4').val(),
				search_EQ_status:31,
				search_EQ_source : $('#source4').combobox('getValue')
			});
		}
	},
	changeStatus:function(status){
		var rows = $("#processingData").datagrid('getChecked');
		if (!rows || rows.length==0){
			eyWindow.walert("提示","请选择数据", 'info');
			return;
		}
		
		var ids = [];
		for (var i=0;i<rows.length;i++)
		{
			ids.push(rows[i].id);
		}
		
		ajaxPost({
			url : basepath + "admin/withdrawal/changeStatus",
			cache : false,
			async : false,
			data : {
				"ids" :ids.join(","),
				"status":status
			},
			success : function(data) {
				if (data.success) {
					$("#processingData").datagrid('reload');
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