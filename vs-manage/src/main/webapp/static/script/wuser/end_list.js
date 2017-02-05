/**
 * 终结方案
 */
function endSolation(id, suffUrl) {
	if ($.easyui.validateSelectItems($("#" + id),1)) {
		var rows = $("#" + id).datagrid('getSelections');
		if (rows[0].isManualDelay==1 ||  rows[0].isManualDelay==2){
			Check.messageBox("提示", "该方案已经延期，不能终结！");
			return ;
		}
		
		$.messager.confirm("提示", "确定要终结方案?", function (r) {
			if (r) {
				var row = rows[0];
	            $.messager.progress({
	                title:'提示',
	                msg:'终结方案执行中...'
	            });
				$.post($.easyui.path() + "/admin/arrearsEnd/" + suffUrl 
						,{"groupId":row.groupId} ,function(data){
						if (data == "success" || data.success) {
				            $.messager.progress('close');
							Check.messageBox("提示","更新成功");
							$("#" + id).datagrid('reload');
						}
						else {
							 $.messager.progress('close');
							Check.messageBox("提示", "更新失败", "error");
						}
				});
				
			}
		});
		
	}
}

function changetype(tid){ 
	if ($.easyui.validateSelectItems($("#" + tid),1)) {
		var rows = $("#"+tid).datagrid('getSelections');
		if (rows[0].isManualDelay!=1 && rows[0].isManualDelay!=2){
			Check.messageBox("提示", "该方案不能解除！");
			return ;
		}
		$.messager.confirm("确认提示","确认限买已解除？", function(r){
			if(r){
			var tradeId=rows[0].tradeId;
			$.messager.progress({
	            title:'提示',
	            msg:'执行中...'
	        });
			$.post($.easyui.path() + "/admin/arrearsEnd/changeType" 
					,{"tradeId":tradeId} ,function(result){
				if (result.success) {
					$("#" + tid).datagrid('reload');
		            $.messager.progress('close');
					Check.messageBox("提示", result.message);
				}
				else {
					 $.messager.progress('close');
					Check.messageBox("提示", result.message, "error");
				}
					});
			}
		});
		
	}		
	
}


function changeStatus(){
	var rows = $("#dg003").datagrid('getSelections');
	if (!rows || rows.length==0){
		eyWindow.walert("更新提示","请选择数据", 'info');
		return;
	}
	var groupIds = [];
	for (var i=0;i<rows.length;i++)
	{
		groupIds.push(rows[i].groupId);
	}
	
	// 重置密码
	$.messager.confirm("确认提示","确认是否修改选中账户状态？", function(result){
		if (result){
			ajaxPost({
				url : basepath + 'admin/monitor/changeOperationStatus',
				cache : false,
				async : false,
				data : {
					"groupIds" : groupIds.join(","),
					"operationStatus":status
				},
				success : function(data) {
					if (data.success) {
						$("#dg003").datagrid('reload');
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
/**
 * @param tid tableID
 */
function openMoneyDetail(tid) {	
	if ($.easyui.validateSelectItems($("#"+tid),1)) {
		var rows = $("#"+tid).datagrid('getSelections');
		$("#groupId").val(rows[0].groupId);
		$("#detailBtn").click();
		$("#moneyDetail").window('open');
	}			
}
function openSettlementScheme() {
	if ($.easyui.validateSelectItems($("#y_jin_dg003"), 1)) {
		var rows = $("#y_jin_dg003").datagrid('getSelections');
		$("#groupId2").val(rows[0].groupId);
		$('#settlement_scheme').window('open');
	}
}
/**
 * 涌金版 终结方案
 */
function closeSettlementScheme() {
	var _groupId = $('#groupId2').val(),
		_money = $('#money').val();
    $.messager.progress({
        title:'提示',
        msg:'终结方案执行中...'
    });
	$.post($.easyui.path() + "/admin/arrearsEnd/endSolationReview" 
			,{"groupId":_groupId, 'finishedMoney':_money} ,function(result){
		if (result.success) {
            $.messager.progress('close');
			Check.messageBox("提示", "更新成功");
			$('#settlement_scheme').window('close');
			$("#y_jin_dg003").datagrid('reload');
		}
		else {
			 $.messager.progress('close');
			Check.messageBox("提示", "更新失败", "error");
		}
	});
}

$(document).ready(function(){
	$('#dg003').datagrid({   
	    rowStyler:function(index,row){   
	        if (row.isManualDelay==1 ){   
	            return 'background-color:yellow;';   
	        }   
	    }   
	});
	
	$('#ths_jin_dg003').datagrid({   
	    rowStyler:function(index,row){   
	        if (row.isManualDelay==2){   
	            return 'background-color:yellow;';   
	        }   
	    }   
	});
});