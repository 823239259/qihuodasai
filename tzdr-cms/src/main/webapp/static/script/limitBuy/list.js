	var initData={dataGridId:'notlimitData'}
	var gridParams={
				nowrap: true,
				autoRowHeight: false,
				striped: true,//True 就把行条纹化。（即奇偶行使用不同背景色）
				collapsible:true,
				url:basepath+'admin/limitBuy/data',
				rownumbers:true,
				toolbar:'#notlimit',
				pagination:true,
				fitColumns:true,// 自适应列宽
				queryParams:{limitBuyStaus:1},
				frozenColumns:[[
		            {field:'ck',checkbox:true}
				]],
				columns:[[
				    {field:'groupId',hidden:true},
					{field:'account',title:'恒生帐号',width:200,sortable:true}
				]],
				onLoadSuccess:function(data){
					datagridUtils.loadSuccess(data,initData.dataGridId);
					}
	}

$(document).ready(function(){
	//$("#"+initData.dataGridId).datagrid(gridParams);
	$('#limitbuyTab').tabs({
	    onSelect:function(title,index){
	    	//alert(gridParams.columns[0][6].hidden);
	    	//gridParams.columns[0][6].hidden=false;
	    	if (title == '未处理限制买入'){
	    		initData.dataGridId="notlimitData";
	    		gridParams.toolbar='#notlimit';
	    		gridParams.queryParams={limitBuyStaus:1};
	    		$("#"+initData.dataGridId).datagrid(gridParams);
	    	}
	    	
	    	if (title == '已处理限制买入'){
	    		initData.dataGridId="haslimitData";
	    		gridParams.toolbar='#haslimit';
	    		gridParams.queryParams={limitBuyStaus:2};
	    		$("#"+initData.dataGridId).datagrid(gridParams);
	    		
	    	}
	    	
	    	if (title == '未处理取消限制买入'){
	    		initData.dataGridId="notcanclelimitData";
	    		gridParams.toolbar='#notcanclelimit';
	    		gridParams.queryParams={limitBuyStaus:4};
	    		$("#"+initData.dataGridId).datagrid(gridParams);
	    	}
	    	
	    	if (title == '已处理取消限制买入'){
	    		initData.dataGridId="hascanclelimitData";
	    		gridParams.toolbar='#hascanclelimit';
	    		gridParams.queryParams={limitBuyStaus:3};
	    		$("#"+initData.dataGridId).datagrid(gridParams);
	    		
	    	}
	    }
	});
});



var limitbuy={
		doSearch:function(){
			$('#edatagrid').datagrid('load',{
				search_LIKE_username : $('#name').val(),
				search_EQ_deleted:false
			});
		},
	checkedData :[],
	changeLimitBuyStatus:function(status,datagridID){
		var rows = $("#"+datagridID).datagrid('getChecked');
		if (!rows || rows.length==0){
			eyWindow.walert("更新提示","请选择数据", 'info');
			return;
		}
		limitbuy.checkedData.length=0;
		var groupIds = [];
		for (var i=0;i<rows.length;i++)
		{
			limitbuy.checkedData.push(rows[i]);
			groupIds.push(rows[i].groupId);
		}
		
		// 重置密码
		$.messager.confirm("确认提示","确认已经处理了选中的数据？", function(result){
			if (result){
				ajaxPost({
					url : basepath + 'admin/limitBuy/changeLimitBuyStatus',
					cache : false,
					async : false,
					data : {
						"groupIds" : groupIds.join(","),
						"limitBuyStatus":status
					},
					success : function(data) {
						if (data.success) {
							 $("#"+datagridID).datagrid("reload");
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
	}
}