function excelImportWuser() {
	$('#activityWuserImportId').dialog('open');
}

function changeStatusActivity() {
	$.messager.confirm('平仓', '请确定是否需要平仓方案?', function(r){
		if (r){
			if (Check.validateSelectItems($("#dg003"),1)) {
				var rows = $("#dg003").datagrid('getSelections');
				var row = rows[0];
				if (row.groupId) {
					changeStatus(3)
				}
				else {
					Check.messageBox("提示","没有方案需要平仓!","info");
				}
			}
		}
	});
}

function endActivitySolation() {
	$.messager.confirm('结束方案', '请确定是否需要结束方案?', function(r){
		if (r){
			if (Check.validateSelectItems($("#dg003"),1)) {
				var rows = $("#dg003").datagrid('getSelections');
				var row = rows[0];
				if (row.groupId) {
					endSolation();
				}
				else {
					Check.messageBox("提示","没有方案需要结束!","info");
				}
			}
		}
	});
	
}

/**
if (Check.validateSelectItems($("#dg003"),1)) {
	var rows = $("#dg003").datagrid('getSelections');
	var row = rows[0];
	if (row.userType && row.userType == 1 && row.groupId) {
		
	}
	else {
		Check.messageBox("提示","更新成功");
	}
}
**/

/**
 * 更新用户为普通用户
 */
function updateWuserToPain() {
	$.messager.confirm('变更为普通账户', '确定需要变更为普通账户?', function(r){
		if (r){
			if (Check.validateSelectItems($("#dg003"),1)) {
				var rows = $("#dg003").datagrid('getSelections');
				var row = rows[0];
				if (row.userType && row.userType == 0) {
					$.post(Check.rootPath() + "/admin/activityWuser/updateWuserToPain",{"uid":row.id}
					,function(data){
						if (data == "success") {
							Check.messageBox("提示","变更为普通账户成功!");
							$("#dg003").datagrid('reload');
						}
						else {
							Check.messageBox("提示",data,"error");
						}
					});
				}
				else {
					Check.messageBox("提示","已经配资不能变更为普通账户!","error");
				}
			}
		}
	});
	
	
}

function resetPassword() {
	if ($.easyui.greaterSelectItems($("#dg003"),1)) {
		$.messager.confirm('提示', '请确定是否需要重置密码?', function(r){
			var rows = $("#dg003").datagrid('getSelections');
			var row = rows[0];
				
			if (r){
				 $.messager.progress({
		                title:'提示',
		                msg:'发送中...'
		            });
				
				var ids = "";
				$(rows).each(function(){
					ids += $(this).attr("id") + ",";
				});
				$.post(Check.rootPath() + "/admin/activityWuser/resetPassword",{"uids":ids}
				,function(data){
					if (data == "success") {
						Check.messageBox("提示","密码重置成功!<br/>已发送短信至用户手机。");
						$("#dg003").datagrid('reload');
						$.messager.progress('close');
						alibaPayClose();
					}
					else {
						Check.messageBox("提示",data,"error");
						$.messager.progress('close');
					}
				});
			}
			
		});
	}
	
};

/**
 * 
 * @param typeValue beforeBegin:活动开始前提醒 beforeEnd: 结束前提醒 end:活动结束提醒
 * @param confirmMessage
 */
function activitySendMessage(typeValue,confirmMessage) {
	$.messager.confirm('提示', confirmMessage, function(r){
		if (r){
			$.messager.progress({
				title:'提示',
				msg:'短信发送中...'
			});
			$.post(Check.rootPath() + "/admin/activityWuser/sendActivitySms",{"type":typeValue}
					,function(data){
						if (data == "success") {
							$.messager.progress('close');
							Check.messageBox("提示","发送成功");
							$("#dg003").datagrid('reload');
						}
						else {
							$.messager.progress('close');
							Check.messageBox("提示",data,"error");
						}
					});
					
			
		}
	});
};



function excelImport() {
	$("#fileUploadForm").click();
	setTimeout(function(){
		$.post(Check.rootPath() + "/admin/activityWuser/queryImportExcel"
				,function(data){
			if (data == "success") {
				Check.messageBox("提示","导入数据成功!");
				$("#dg003").datagrid('reload');
				$('#activityWuserImportId').dialog('close');
			}
			else {
				Check.messageBox("提示",data,"error");
			}
		});
	},2000);
}
/**
 * 选择活动类型时触发按钮显示隐藏事件
 * @param activityType
 */
function onSelectComebox(activityType) {
	if(activityType) {
		if(1 == activityType) {
			showOrHideBtns(true);
			return;
		} else if(2 == activityType) {
			showOrHideBtns(false);
			return;
		}
	}
}
/**
 * 选择行时触发按钮显示隐藏事件
 */
function onClickRow() {
	var rows = $("#dg003").datagrid('getSelections'),
		_row = rows[0];
	
	if(_row) {
		var _type = _row.activityTypeStr;
		if('6600活动' == _type) {
			showOrHideBtns(false);
		} else {
			showOrHideBtns(true);
		}
	}
}
/**
 * 显示或隐藏 按钮
 * @param flag
 */
function showOrHideBtns(flag) {
	if(!flag) {
		$('#dg003Toolbar').find('a[iconCls]').each(function() {
			if($(this).index() != 2) {
				$(this).css('display', 'none');
			}
		});
	} else {
		$('#dg003Toolbar').find('a[iconCls]').each(function() {
			$(this).css('display', '');
		});
	}
}