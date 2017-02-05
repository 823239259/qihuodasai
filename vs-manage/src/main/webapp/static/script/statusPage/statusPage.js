function homeUpdate() {
	$.messager.confirm('提示', '确认要更新?', function(r){
		if (r){
			$.messager.progress({
                title:'提示',
                msg:'更新中...'
            });
			$.post(Check.rootPath() + "/admin/status/statusUpdate" 
					,{"templateName":"homepage"} ,function(data){
						if (data == "success") {
							Check.messageBox("提示","更新成功");
						}
						else {
							Check.messageBox("提示",data,"error");
						}
						 $.messager.progress('close');
					});
			
		}
	});
}

function refreshToken(environment){
	ajaxPost({
		url : basepath + "admin/status/refreshToken",
		cache : false,
		async : false,
		data : {environment:environment},
		success : function(data) {
			if (data.success) {
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


function openQuartzWin(){
	$('#addWin').window('open');
}



function updateQuartz(){
	var nextFireTime = $("#nextFireTime").val();
	if (validateIsNull(nextFireTime)){
		eyWindow.walert("错误提示","请选择下次执行时间！", 'error');
		return;
	}
	
	ajaxPost({
		url : basepath + "admin/status/updateQuartz",
		cache : false,
		async : false,
		data : {triggerName:$("#triggerName").combobox('getValue'),nextFireTime:$("#nextFireTime").val()},
		success : function(data) {
			if (data.success) {
				$('#addWin').window('close'); 
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

function openFailQuartzWin(){
	$('#openFailQuartzWin').window('open');
}

function executeFailQuartz(){
	var nextFireTime = $("#managementFeeTime").datebox('getValue');
	if (validateIsNull(nextFireTime)){
		eyWindow.walert("错误提示","请选择需要执行的管理费时间！", 'error');
		return;
	}
	ajaxPost({
		url : basepath + "admin/status/executeFailQuartz",
		cache : false,
		async : false,
		data : {'nextFireTime':nextFireTime},
		success : function(data) {
			if (data.success) {
				$('#openFailQuartzWin').window('close'); 
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

function handleUserFundFail(environment){
	ajaxPost({
		url : basepath + "admin/status/handleUserFundFail",
		cache : false,
		async : false,
		success : function(data) {
			if (data.success) {
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