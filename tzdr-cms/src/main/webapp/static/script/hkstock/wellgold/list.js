$(document).ready(function(){
	$('#wellGoldTab').tabs({
	    onSelect:function(title,index){
	    	if (title == '待审核方案列表'){
	    		$("#edatagrid").datagrid("options").url=basepath+"admin/hkstock/wellgold/getDatas?type=0";
	    		$("#edatagrid").datagrid("reload");
	    	}
	    	
	    	if (title == '已审核记录'){
	    		$("#hasAuditData").datagrid("options").url=basepath+"admin/hkstock/wellgold/getDatas?type=1";
	    		$("#hasAuditData").datagrid("reload");
	    	}
	    }
	});
});

var wellGold={
		
	auditTrade:function(isPass){
		
		if (!datagridUtils.checkSelected("edatagrid","请选择需要审核的记录！")){
			return;
		}
		
		var rows = $("#edatagrid").datagrid('getChecked');
		var tradeId = rows[0].tradeId;
		if (isPass){
			var html = '<iframe scrolling="yes" frameborder="0"  src="'+basepath+'admin/hkstock/wellgold/edit?tradeId='+tradeId+'" style="width:100%;height:100%;"></iframe>';
			$('#addWin').window({collapsible:false,minimizable:false,maximizable:false,width:650,height:400,title:"填写账户信息",loadingMessage:'正在加载,请等待......',iconCls:'icon-add',closed:true,modal:true,content:html});
			$('#addWin').window('open');
			return;
		}
		
		var rows = $("#edatagrid").datagrid('getChecked');
		$.messager.confirm("确认提示","请确认是否不通过审核？", function(result){
			if (result){
				ajaxPost({
					url : basepath + "admin/hkstock/wellgold/notpass",
					cache : false,
					async : false,
					data : {
						"tradeId" :tradeId
					},
					success : function(data) {
						if (data.success) {
							$("#edatagrid").datagrid('reload');
							eyWindow.walert("成功提示", data.message, 'info');
							return;
						}
						eyWindow.walert("错误提示", data.message, 'error');
					},
					error : function(XMLHttpRequest, textStatus, errorThrown) {
						eyWindow.walert("错误提示", "系统异常，错误类型textStatus: "+textStatus+",异常对象errorThrown: "+errorThrown, 'error');
					}
				});
			}
		});
	}
}