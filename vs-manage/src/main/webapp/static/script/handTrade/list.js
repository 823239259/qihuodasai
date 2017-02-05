$(document).ready(function(){
	$('#handTradeTab').tabs({
	    onSelect:function(title,index){
	    	if (title == '待审核列表'){
	    		$("#edatagrid").datagrid("options").url=basepath+"admin/handTrade/getDatas?type=0";
	    		$("#edatagrid").datagrid("reload");
	    	}
	    	
	    	if (title == '已审核列表'){
	    		$("#hasAuditData").datagrid("options").url=basepath+"admin/handTrade/getDatas?type=1";
	    		$("#hasAuditData").datagrid("reload");
	    	}
	    	
	    	if (title == '审核通过划款失败列表'){
	    		$("#transferFailData").datagrid("options").url=basepath+"admin/handTrade/transferFailData";
	    		$("#transferFailData").datagrid("reload");
	    	}
	    }
	});
});



var handTrade={
	auditTrade:function(isPass){
		
		if (!datagridUtils.checkSelected("edatagrid","请选择需要审核的记录！")){
			return;
		}
		//
		var rows = $("#edatagrid").datagrid('getChecked');
		$.messager.confirm("确认提示","请确认是否对您选择的数据进行审核操作？", function(result){
			if (result){
				ajaxPost({
					url : basepath + "admin/handTrade/audit",
					cache : false,
					async : false,
					data : {
						"tradeId" :rows[0].tradeId,
						"isPass":isPass
					},
					success : function(data) {
						if (data.success) {
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
		});
	},
	afterHandTransfer:function(){
		if (!datagridUtils.checkSelected("transferFailData","请选择需要处理的记录！")){
			return;
		}
		//
		var rows = $("#transferFailData").datagrid('getChecked');
		$.messager.confirm("确认提示","请确认是否对您选择的数据进行操作？", function(result){
			if (result){
				ajaxPost({
					url : basepath + "admin/handTrade/afterHandTransfer",
					cache : false,
					async : false,
					data : {
						"tradeId" :rows[0].id,
					},
					success : function(data) {
						if (data.success) {
							$("#transferFailData").datagrid('reload');
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