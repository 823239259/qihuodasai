$(document).ready(function(){
	$("#handlerUsername").numberbox({
		onChange:function(newValue,oldValue){
			usernameIsExits();
		}
	});
	
});

function usernameIsExits() {
	var username = $("#handlerUsername").val();
	if (!username && username.length == 0) {
		return;
	}
	$.post(Check.rootPath() + "/admin/wUser/isUsernameExist" 
			,{"username":username} ,function(data){
		if (data == "Y") {
			$("#userExitsLabel").html("手机号已存在");
			$("#userExitsLabel").css({"color":"red"});
			$("#handlerUsername_token").val("false");
		}
		else {
			$("#userExitsLabel").html("校验通过");
			$("#userExitsLabel").css({"color":"green"});
			$("#handlerUsername_token").val("true");
		}
	});
};

function validationCode() {
	usernameIsExits();
	if ($("#handlerUsername_token").val() == "false") {
		return false;
	}
	else {
		return true;
	}
}

/**
 * 提交
 */
function addAgentSave() {
	if (!validationCode()) {
		return;
	}
	$.post(Check.rootPath() + "/admin/agent/saveAgents" 
			,$("#voForm").serialize() ,
			function(data){
				if (data == "success") {
					Check.messageBox("提示","更新成功");
					$("#dg003").datagrid('reload');
					addAgentClose();
				}
				else {
					Check.messageBox("提示",data,"error");
				}
	});
	
};

/**
 * 新增状态
 */
function addAgentOpen() {
	validationCode();
	$("#addAgent").show();
	$("#addAgent").window('open');
		
};

function addAgentClose() {
	$("#addAgent").show();
	$("#addAgent").window('close');
		
};

/**
 * 修改状态
 */
function updateAgentOpen() {
	
	if (Check.validateSelectItems($("#dg003"),1)) {
		
		var rows = $("#dg003").datagrid('getSelections');
		var row = rows[0];
		var rebateStr = row.rebate.toFixed(4);
		$("#updateRebateValId").numberbox('setValue',rebateStr.substring(0,rebateStr.length - 2));
		$("#updateAgentNameId").val(row.uname);
		$("#updateAgent").show();
		$("#updateAgent").window('open');
	}
		
};

/**
 * 修改状态
 */
function updateAgentClose() {
	$("#updateAgent").show();
	$("#updateAgent").window('close');
		
};
/**
 * 生置状态
 */
function bankPayOpen() {
	var rows = $("#dg002").datagrid('getSelections');
	if (Check.validateSelectItems($("#dg002"),1)) {
		
		if (rows[0].statusValue - 0 == 2) {
			$.messager.show({
	            title:'提示',
	            msg:'已充值后不能在充值',
	            timeout:5000,
	            showType:'slide'
	        });
		}
		else {
			$("#bankAmountId").numberbox('setValue',"");
			$("#bankTradeNoId").val("");
			$("#bankId").combobox('reload');
			$("#bankPay").show();
			$("#bankPay").window('open');
		}
		
	}
	
};


/**
 * 提交
 */
function updateAgent() {
	var rows = $("#dg003").datagrid('getSelections');
	var id = rows[0].id;
	var rebate = rows[0].rebate - 0;
	var rechargeAmount = $("#updateRebateValId").val() - 0;
	var agentName = $("#updateAgentNameId").val();
	
	if (rechargeAmount > 100) {
		Check.messageBox("提示","修改后的返点值必需小于100","warning");
	}
	else {
		$.post(Check.rootPath() + "/admin/agent/updateAgents" 
				,{"id":id, "rebate":rechargeAmount,"uname":agentName} ,function(data){
			if (data == "success") {
				Check.messageBox("提示","更新成功");
				$("#dg003").datagrid('reload');
				updateAgentClose();
			}
			else {
				var str =data.substring(data.indexOf("</br>"),data.indexOf("</body>"));
				Check.messageBox("提示",str,"error");
			}
		});
	}
	
};


/**
 * 打开修改代理树框
 */
function updateAgentTreeOpen() {
	$("#newSuperiorMobile").val('');
	$("#newSuperiorName").val('');
	if (Check.validateSelectItems($("#dg003"),1)) {
		var rows = $("#dg003").datagrid('getSelections');
		var row = rows[0];
		/*var rebateStr = row.rebate.toFixed(4);*/
		$("#updateAgentTreeOpen").show();
		$("#updateAgentTreeOpen").window('open');
	}	
};

/**
 * 关闭代理树
 */
function updateAgentTreeClose() {
	$("#updateAgentTreeOpen").show();
	$("#updateAgentTreeOpen").window('close');
};

/**
 * 获取用户信息
 */
function getWUser(){
	if (Check.validateSelectItems($("#dg003"),1)) {
		var rows = $("#dg003").datagrid('getSelections');
		var row = rows[0];
		
		var mobile = $("#newSuperiorMobile").val();
		if(!mobile){
			Check.messageBox("提示","请填写手机号码");
			return;
		}
		
		$.post(Check.rootPath() + "/admin/agent/getWUser" 
				,{"mobile":mobile} ,function(data){
			if(data.success) {
				var trueName = data.data.trueName;
				var uid = data.data.uid;
				$("#newSuperiorName").val(trueName == null ? '':trueName);
				$("#uid").val(uid == null ? '':uid);
			}
			else {
				Check.messageBox("提示",data.message,"error");
			}
		},"json");
	}
}

/**
 * 修改代理树
 */
function updateAgentTree(){
	if (Check.validateSelectItems($("#dg003"),1)) {
		var rows = $("#dg003").datagrid('getSelections');
		var row = rows[0];
		
		var mobile = $("#newSuperiorMobile").val();
		var uid = $("#uid").val();
		if(!mobile){
			Check.messageBox("提示","请填写手机号码");
			return;
		}else if(!uid){
			Check.messageBox("提示","该用户不存在");
			return;
		}
		
		$.post(Check.rootPath() + "/admin/agent/updateAgentTree" 
				,{"mobile":mobile,subordinateUid:row.id} ,function(data){
			if(data.success) {
				Check.messageBox("提示","修改成功");
				$("#dg003").datagrid('reload');
				updateAgentTreeClose();
			}
			else {
				Check.messageBox("提示",data.message,"error");
			}
		},"json");
	}
}
