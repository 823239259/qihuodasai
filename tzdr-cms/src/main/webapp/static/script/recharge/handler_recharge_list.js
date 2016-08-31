
//function update
function handlerClose() {
	$("#addHandler").window('close');
};

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
		if (data.success) {
			$("#userExitsLabel").html("校验通过");
			$("#userExitsLabel").css({"color":"green"});
			
			$("#userAmount").text(data.obj);
		}
		else {
			$("#userExitsLabel").html("手机号不存在");
			$("#userExitsLabel").css({"color":"red"});
		}
	});
};

/**
 * 提交
 */
function confirmHandlerPay() {
	
	if (!$("#bankPayForm").form("validate")) {
		return;
	}
	
	$.messager.progress({
		title:'提示',
		msg:'执行中...'
	});
	
	$.post(Check.rootPath() + "/admin/recharge/handlerSaveRechargeState" 
			,{"actualMoney":$("#handlerAmountId").val(),"sysType":$("#sysTypeId").combobox('getValue'),
		"no":$("#noId").val(),"mobileNo":$("#handlerUsername").val(),"remark":$("#handlerReasonId").val()} ,
			function(data){
				if (data == "success") {
					Check.messageBox("提示","更新成功");
					$("#dg003").datagrid('reload');
					handlerClose();
					$.messager.progress('close');
				}
				else {
					Check.messageBox("提示",data,"error");
					$.messager.progress('close');
				}
	});
	
};

/**
 * 新增状态
 */
function handlerPayOpen() {
	$("#handlerUsername").val("");
	$("#handlerAmountId").numberbox("setValue","");
	$("#handlerReasonId").val("");
	$("#addHandler").show();
	$("#addHandler").window('open');
		
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
function confirmBankPay() {
	var rows = $("#dg002").datagrid('getSelections');
	var id = rows[0].id;
	var money = rows[0].money - 0;
	var rechargeAmount = $("#bankAmountId").val() - 0;
	if (rows[0].tradeNo != $("#bankTradeNoId").val()) {
		Check.messageBox("提示","流水号与转账记录流水号不匹配充值失败!","warning");
		return;
	}
	
	if (money != rechargeAmount) {
		$.messager.confirm('提示', '输入金额与用户提交金额不相等，请确认充值金额是否正确?', function(r){
    		if (r){
    			
    			$.messager.progress({
    				title:'提示',
    				msg:'执行中...'
    			});
    			
    			$.post(Check.rootPath() + "/admin/recharge/updateRechargeState" 
    					,{"id":id, "stateValue":2,"tradeNo":$("#bankTradeNoId").val(),"actualMoney":rechargeAmount} ,function(data){
    				if (data == "success") {
    					Check.messageBox("提示","更新成功");
    					$("#dg002").datagrid('reload');
    					alibaPayClose();
    					$.messager.progress('close');
    				}
    				else {
    					Check.messageBox("提示",data,"error");
    					$.messager.progress('close');
    				}
    			});
    			
    		}
		});
	}
	else {
		$.messager.progress({
			title:'提示',
			msg:'执行中...'
		});
		
		$.post(Check.rootPath() + "/admin/recharge/updateRechargeState" 
				,{"id":id, "stateValue":2,"tradeNo":$("#bankTradeNoId").val(),"actualMoney":rechargeAmount} ,function(data){
			if (data == "success") {
				Check.messageBox("提示","更新成功");
				$("#dg002").datagrid('reload');
				alibaPayClose();
				$.messager.progress('close');
			}
			else {
				Check.messageBox("提示",data,"error");
				$.messager.progress('close');
			}
		});
	}
	
};

function bankPayClose() {
	$("#bankPay").window('close');
};



