$(document).ready(function(){
	$('#wellGoldTab').tabs({
	    onSelect:function(title,index){
	    	if (title == '待审核方案列表'){
	    		$("#edatagrid").datagrid("options").url=basepath+"admin/wellGold/getDatas?type=0";
	    		$("#edatagrid").datagrid("reload");
	    	}
	    	
	    	if (title == '方案审核记录'){
	    		$("#hasAuditData").datagrid("options").url=basepath+"admin/wellGold/getDatas?type=1";
	    		$("#hasAuditData").datagrid("reload");
	    	}
	    	
	    	if (title == '审核通过划款失败列表'){
	    		$("#transferFailData").datagrid("options").url=basepath+"admin/wellGold/transferFailData";
	    		$("#transferFailData").datagrid("reload");
	    	}
	    }
	});
	$('#search_EQ_feeType').combobox({
	    onChange:function(newValue,oldValue){
	    	if(newValue != ""){
				$("#search_EQ_auditStatus").combobox('setValue','1');
			}else{
				$("#search_EQ_auditStatus").combobox('setValue','');
			}
	    }
	});
});

function format(value){
	if(value=="是"){
		return '<a style="color:red;">'+value+'</a>';
	}else{
		return '<a >'+value+'</a>';
	}
}

var wellGold={
	auditTrade:function(isPass){
		
		if (!datagridUtils.checkSelected("edatagrid","请选择需要审核的记录！")){
			return;
		}
		
		var rows = $("#edatagrid").datagrid('getChecked');
		var tradeId = rows[0].tradeId;
		if (isPass){
			var activityType = rows[0].activityType;
			var html = '<iframe scrolling="yes" frameborder="0"  src="'+basepath+'admin/wellGold/edit?tradeId='+tradeId+'&activityType='+activityType+'" style="width:100%;height:100%;"></iframe>';
			$('#addWin').window({collapsible:false,minimizable:false,maximizable:false,width:650,height:400,title:"填写账户信息",loadingMessage:'正在加载,请等待......',iconCls:'icon-add',closed:true,modal:true,content:html});
			$('#addWin').window('open');
			return;
		}
		
		var rows = $("#edatagrid").datagrid('getChecked');
		$.messager.confirm("确认提示","请确认是否不通过审核？", function(result){
			if (result){
				ajaxPost({
					url : basepath + "admin/wellGold/notPass",
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