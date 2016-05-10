	var initData={dataGridId:'untreatedData'}
	var gridParams={
				nowrap: true,
				autoRowHeight: false,
				striped: true,//True 就把行条纹化。（即奇偶行使用不同背景色）
				collapsible:true,
				url:basepath+'admin/message/easyuiPage', 
				rownumbers:true,
				toolbar:'#untreatedtb',
				singleSelect:true,
				pagination:true,
				queryParams:{search_EQ_status:0},
				fitColumns:true,// 自适应列宽
				frozenColumns:[[
		            {field:'ck',checkbox:true}
				]],
				columns:[[
				    {field:'id',hidden:true},
				    {field:'wuser',hidden:true},
					{field:'mobile',title:'手机号',width:100,sortable:true,formatter:function(value,row,index){
						return row.wuser.mobile;
					}},
					{field:'type',title:'反馈类型',width:200,sortable:true},
					{field:'content',title:'反馈内容摘要',width:200,sortable:true},
					{field:'addtime',title:'反馈时间',width:150,sortable:true,formatter: function(value,row,index){
						if (validateIsNull(value)){
							return;
						}
						return getFormatDateByLong(value,'yyyy-MM-dd hh:mm:ss');
					}},
					{field:'endtime',title:'客服回复时间',width:150,sortable:true,hidden:true,formatter:function(value,row,index){
						if (validateIsNull(value)){
							return;
						}
						return getFormatDateByLong(value,'yyyy-MM-dd hh:mm:ss');
					}}
				]],
				onLoadSuccess:function(data){
					message.loadSuccess(data,initData.dataGridId);
					}
	}

$(document).ready(function(){
	$("#"+initData.dataGridId).datagrid(gridParams);
	$('#messageTab').tabs({
	    onSelect:function(title,index){
	    	//alert(gridParams.columns[0][6].hidden);
	    	if (title == '未处理'){
	    		initData.dataGridId="untreatedData";
	    		gridParams.toolbar='#untreatedtb';
	    		gridParams.columns[0][6].hidden=true;
	    		gridParams.queryParams={search_EQ_status:0};
	    		$("#"+initData.dataGridId).datagrid(gridParams);
	    	}
	    	
	    	if (title == '已处理'){
	    		initData.dataGridId="processingData";
	    		gridParams.toolbar='#processingtb';
	    		gridParams.queryParams={search_EQ_status:1};
	    		gridParams.columns[0][6].hidden=false;
	    		$("#"+initData.dataGridId).datagrid(gridParams);
	    	}
	    }
	});
});



var message={
	list:{
		doSearch:function(){
			$('#edatagrid').datagrid('load',{
				search_LIKE_username : $('#name').val(),
				search_EQ_deleted:false
			});
		}
	},
	openWin:function(width,height,title,type,id){
		
		var rowData=$('#'+id).datagrid('getSelected');
		if (null == rowData){
			eyWindow.walert("提示",'请选择相应数据行！', 'info');
			return;
		}
		var  url = basepath+'admin/message/edit?fromType='+type+'&id='+rowData.id;
		//var html = '<iframe scrolling="yes" frameborder="0"  src="'+basepath+'admin/user/edit?fromType=edit&id='+rowData.id+'" style="width:100%;height:100%;"></iframe>';
		$('#addWin').window({collapsible:false,minimizable:false,maximizable:false,width:width,height:height,title:title,loadingMessage:'正在加载,请等待......',iconCls:'icon-edit',closed:true,modal:true,href:url});
		$('#addWin').window('open');
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
	},
	saveOrUpdate:function(url,iframe){
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
					$("#untreatedData").datagrid('reload');
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