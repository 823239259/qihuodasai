/**
 * 网银充值打开充值确认框
 */
function netBankPayOpen() {
	var rows = $("#dg003").datagrid('getSelections');
	if (Check.validateSelectItems($("#dg003"),1)) {
		
		if (rows[0].statusValue - 0 == 21) {
			$.messager.show({
	            title:'提示',
	            msg:'已充值后不能在充值',
	            timeout:5000,
	            showType:'slide'
	        });
		}
		else {
			$("#netBankAmountId").numberbox('setValue',"");
			$("#netBankTradeNoId").val("");
			//$("#bankId").combobox('reload');
			$("#netBankPay").show();
			$("#netBankPay").window('open');
		}
		
	}
};
/**
 * 提交
 */
function confirmNetBankPay() {
	
	if (!$("#netBankPayForm").form("validate")) {
		return false;
	}
	var rows = $("#dg003").datagrid('getSelections');
	var id = rows[0].id;
	var money = rows[0].money - 0;
	var rechargeAmount = $("#netBankAmountId").val() - 0;
	/**
	if (rows[0].tradeNo != $("#bankTradeNoId").val()) {
		Check.messageBox("提示","流水号与转账记录流水号不匹配充值失败!","warning");
		return;
	}
	**/
	
	if (money != rechargeAmount) {
		$.messager.confirm('提示', '输入金额与用户提交金额不相等，请确认充值金额是否正确?', function(r){
    		if (r){
    			$.post(Check.rootPath() + "/admin/recharge/updateRechargeState" 
    					,{"id":id, "stateValue":"21","tradeNo":$("#netBankTradeNoId").val(),"actualMoney":rechargeAmount} ,function(data){
    				if (data == "success") {
    					Check.messageBox("提示","更新成功");
    					$("#dg003").datagrid('reload');
    					netBankPayClose();
    				}
    				else {
    					Check.messageBox("提示",data,"error");
    				}
    			});
    		}
		});
	}
	else {
		$.post(Check.rootPath() + "/admin/recharge/updateRechargeState" 
				,{"id":id, "stateValue":"21","tradeNo":$("#netBankTradeNoId").val(),"actualMoney":rechargeAmount} ,function(data){
			if (data == "success") {
				Check.messageBox("提示","更新成功");
				$("#dg003").datagrid('reload');
				netBankPayClose();
			}
			else {
				Check.messageBox("提示",data,"error");
			}
		});
	}
	
};
function failNetBankPay() {
	var rows = $("#dg003").datagrid('getSelections');
	if (Check.validateSelectItems($("#dg003"),1)) {
		if (rows[0].statusValue - 0 == 1) {
			$.messager.show({
	            title:'提示',
	            msg:'已标记为"充值失败"不能重复提交!',
	            timeout:5000,
	            showType:'slide'
	        });
		}
		else if (rows[0].statusValue - 0 == 21) {
			$.messager.show({
	            title:'提示',
	            msg:'"充值成功"后不能修改为"充值失败"!',
	            timeout:5000,
	            showType:'slide'
	        });
		}
		else {
			var id = rows[0].id;
			$.messager.confirm('提示', '请确认是否需要标记本项为充值失败?', function(r){
	    		if (r){
	    			$.post(Check.rootPath() + "/admin/recharge/failUpdateRechargeState" 
	    					,{"id":id, "stateValue":1} ,function(data){
	    				if (data == "success") {
	    					Check.messageBox("提示","更新成功");
	    					$("#dg003").datagrid('reload');
	    				}
	    				else {
	    					Check.messageBox("提示",data,"error");
	    				}
	    			});
	    			
	    		}
			});
		}
	}
};

function netBankPayClose() {
	$("#netBankPay").window('close');
};
//function update
function alibaPayClose() {
	$("#alibaPay").window('close');
};

function failAlibaPay() {
	var rows = $("#dg001").datagrid('getSelections');
	if (Check.validateSelectItems($("#dg001"),1)) {
		if (rows[0].statusValue - 0 == 1) {
			$.messager.show({
	            title:'提示',
	            msg:'已标记为"充值失败"不能重复提交!',
	            timeout:5000,
	            showType:'slide'
	        });
		}
		else if (rows[0].statusValue - 0 == 21) {
			$.messager.show({
	            title:'提示',
	            msg:'"充值成功"后不能修改为"充值失败"!',
	            timeout:5000,
	            showType:'slide'
	        });
		}
		else {
			var id = rows[0].id;
			$.messager.confirm('提示', '请确认是否需要标记本项为充值失败?', function(r){
	    		if (r){
	    			$.post(Check.rootPath() + "/admin/recharge/failUpdateRechargeState" 
	    					,{"id":id, "stateValue":1} ,function(data){
	    				if (data == "success") {
	    					Check.messageBox("提示","更新成功");
	    					$("#dg001").datagrid('reload');
	    				}
	    				else {
	    					Check.messageBox("提示",data,"error");
	    				}
	    			});
	    			
	    		}
			});
			
		}
		
	}
};


/**
 * 提交
 */
function confirmAlibaPay() {
	var rows = $("#dg001").datagrid('getSelections');
	var id = rows[0].id;
	$.post(Check.rootPath() + "/admin/recharge/updateRechargeState" 
			,{"id":id, "stateValue":21,"tradeNo":null,"rechargeType":"aliba","actualMoney":null, 'mobile':$('#mobile').val()} ,function(data){
		if (data == "success") {
			Check.messageBox("提示","更新成功");
			$("#dg001").datagrid('reload');
			alibaPayClose();
		}
		else {
			Check.messageBox("提示",data,"error");
		}
	});
//	var money = rows[0].money - 0;
//	var rechargeAmount = $("#rechargeAmountId").val() - 0;
//	if (money != rechargeAmount) {
//		$.messager.confirm('提示', '输入金额与用户提交金额不相等，请确认充值金额是否正确?', function(r){
//    		if (r){
//    			$.post(Check.rootPath() + "/admin/recharge/updateRechargeState" 
//    					,{"id":id, "stateValue":21,"tradeNo":$("#tradeNoId").val(),"rechargeType":"aliba","actualMoney":$("#rechargeAmountId").val()} ,function(data){
//    				if (data == "success") {
//    					Check.messageBox("提示","更新成功");
//    					$("#dg001").datagrid('reload');
//    					alibaPayClose();
//    				}
//    				else {
//    					Check.messageBox("提示",data,"error");
//    				}
//    			});
//    			
//    		}
//		});
//	}
//	else {
//		$.post(Check.rootPath() + "/admin/recharge/updateRechargeState" 
//				,{"id":id, "stateValue":21,"tradeNo":$("#tradeNoId").val(),"rechargeType":"aliba","actualMoney":$("#rechargeAmountId").val()} ,function(data){
//			if (data == "success") {
//				Check.messageBox("提示","更新成功");
//				$("#dg001").datagrid('reload');
//				alibaPayClose();
//			}
//			else {
//				Check.messageBox("提示",data,"error");
//			}
//		});
//	}
	
};

/**
 * 打开支付宝充值确认框
 */
function alibaPayOpen() {
	var rows = $("#dg001").datagrid('getSelections');
	if (Check.validateSelectItems($("#dg001"),1)) {
		if (rows[0].statusValue - 0 == 21) {
			$.messager.show({
	            title:'提示',
	            msg:'已充值后不能在充值',
	            timeout:5000,
	            showType:'slide'
	        });
		}
		else if (rows[0].statusValue - 0 == 1) {
			$.messager.show({
	            title:'提示',
	            msg:'已标记为"充值失败"!',
	            timeout:5000,
	            showType:'slide'
	        });
		}
		else {
			$("#rechargeAmountId").numberbox('setValue',"");
			$("#tradeNoId").val("");
			$("#alibaPay").show();
			$("#alibaPay").window('open');
		}
		
	}
};

/**
 * 打开充值确认框
 */
function bankPayOpen() {
	var rows = $("#dg002").datagrid('getSelections');
	if (Check.validateSelectItems($("#dg002"),1)) {
		
		if (rows[0].statusValue - 0 == 21) {
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
	
	if (!$("#bankPayForm").form("validate")) {
		return false;
	}
	var rows = $("#dg002").datagrid('getSelections');
	var id = rows[0].id;
	var money = rows[0].money - 0;
	var rechargeAmount = $("#bankAmountId").val() - 0;
	/**
	if (rows[0].tradeNo != $("#bankTradeNoId").val()) {
		Check.messageBox("提示","流水号与转账记录流水号不匹配充值失败!","warning");
		return;
	}
	**/
	
	if (money != rechargeAmount) {
		$.messager.confirm('提示', '输入金额与用户提交金额不相等，请确认充值金额是否正确?', function(r){
    		if (r){
    			$.post(Check.rootPath() + "/admin/recharge/updateRechargeState" 
    					,{"id":id, "stateValue":"21","tradeNo":$("#bankTradeNoId").val(),"bankname":$("#bankId").combobox("getValue"),"actualMoney":rechargeAmount} ,function(data){
    				if (data == "success") {
    					Check.messageBox("提示","更新成功");
    					$("#dg002").datagrid('reload');
    					bankPayClose();
    				}
    				else {
    					Check.messageBox("提示",data,"error");
    				}
    			});
    			
    		}
		});
	}
	else {
		$.post(Check.rootPath() + "/admin/recharge/updateRechargeState" 
				,{"id":id, "stateValue":"21","tradeNo":$("#bankTradeNoId").val(),"actualMoney":rechargeAmount} ,function(data){
			if (data == "success") {
				Check.messageBox("提示","更新成功");
				$("#dg002").datagrid('reload');
				bankPayClose();
			}
			else {
				Check.messageBox("提示",data,"error");
			}
		});
	}
	
};

function failBankPay() {
	var rows = $("#dg002").datagrid('getSelections');
	if (Check.validateSelectItems($("#dg002"),1)) {
		if (rows[0].statusValue - 0 == 1) {
			$.messager.show({
	            title:'提示',
	            msg:'已标记为"充值失败"不能重复提交!',
	            timeout:5000,
	            showType:'slide'
	        });
		}
		else if (rows[0].statusValue - 0 == 21) {
			$.messager.show({
	            title:'提示',
	            msg:'"充值成功"后不能修改为"充值失败"!',
	            timeout:5000,
	            showType:'slide'
	        });
		}
		else {
			var id = rows[0].id;
			$.messager.confirm('提示', '请确认是否需要标记本项为充值失败?', function(r){
	    		if (r){
	    			$.post(Check.rootPath() + "/admin/recharge/failUpdateRechargeState" 
	    					,{"id":id, "stateValue":1} ,function(data){
	    				if (data == "success") {
	    					Check.messageBox("提示","更新成功");
	    					$("#dg002").datagrid('reload');
	    				}
	    				else {
	    					Check.messageBox("提示",data,"error");
	    				}
	    			});
	    			
	    		}
			});
			
		}
		
	}
};
function bankPayClose() {
	$("#bankPay").window('close');
};

/**
 * 微信充值打开充值确认框
 */
function wechatPayOpen() {
	var rows = $("#dg004").datagrid('getSelections');
	if (Check.validateSelectItems($("#dg004"),1)) {
		
		if (rows[0].statusValue - 0 == 21) {
			$.messager.show({
	            title:'提示',
	            msg:'已充值后不能在充值',
	            timeout:5000,
	            showType:'slide'
	        });
		}
		else {
			$("#wechatAmountId").numberbox('setValue',"");
			$("#wechatTradeNoId").val("");
			//$("#bankId").combobox('reload');
			$("#wechatPay").show();
			$("#wechatPay").window('open');
		}
		
	}
};
/**
 * 提交
 */
function confirmWechatPay() {
	
	if (!$("#wechatPayForm").form("validate")) {
		return false;
	}
	var rows = $("#dg004").datagrid('getSelections');
	var id = rows[0].id;
	var money = rows[0].money - 0;
	var rechargeAmount = $("#wechatAmountId").val() - 0;
	/**
	if (rows[0].tradeNo != $("#bankTradeNoId").val()) {
		Check.messageBox("提示","流水号与转账记录流水号不匹配充值失败!","warning");
		return;
	}
	**/
	
	if (money != rechargeAmount) {
		$.messager.confirm('提示', '输入金额与用户提交金额不相等，请确认充值金额是否正确?', function(r){
    		if (r){
    			$.post(Check.rootPath() + "/admin/recharge/updateRechargeState" 
    					,{"id":id, "stateValue":"21","tradeNo":$("#wechatTradeNoId").val(),"rechargeType":"wechat","actualMoney":rechargeAmount} ,function(data){
    				if (data == "success") {
    					Check.messageBox("提示","更新成功");
    					$("#dg004").datagrid('reload');
    					wechatPayClose();
    				}
    				else {
    					Check.messageBox("提示",data,"error");
    				}
    			});
    		}
		});
	}
	else {
		$.post(Check.rootPath() + "/admin/recharge/updateRechargeState" 
				,{"id":id, "stateValue":"21","tradeNo":$("#wechatTradeNoId").val(),"rechargeType":"wechat","actualMoney":rechargeAmount} ,function(data){
			if (data == "success") {
				Check.messageBox("提示","更新成功");
				$("#dg004").datagrid('reload');
				wechatPayClose();
			}
			else {
				Check.messageBox("提示",data,"error");
			}
		});
	}
	
};
function failWechatPay() {
	var rows = $("#dg004").datagrid('getSelections');
	if (Check.validateSelectItems($("#dg004"),1)) {
		if (rows[0].statusValue - 0 == 1) {
			$.messager.show({
	            title:'提示',
	            msg:'已标记为"充值失败"不能重复提交!',
	            timeout:5000,
	            showType:'slide'
	        });
		}
		else if (rows[0].statusValue - 0 == 21) {
			$.messager.show({
	            title:'提示',
	            msg:'"充值成功"后不能修改为"充值失败"!',
	            timeout:5000,
	            showType:'slide'
	        });
		}
		else {
			var id = rows[0].id;
			$.messager.confirm('提示', '请确认是否需要标记本项为充值失败?', function(r){
	    		if (r){
	    			$.post(Check.rootPath() + "/admin/recharge/failUpdateRechargeState" 
	    					,{"id":id, "stateValue":1} ,function(data){
	    				if (data == "success") {
	    					Check.messageBox("提示","更新成功");
	    					$("#dg004").datagrid('reload');
	    				}
	    				else {
	    					Check.messageBox("提示",data,"error");
	    				}
	    			});
	    			
	    		}
			});
		}
	}
};

function wechatPayClose() {
	$("#wechatPay").window('close');
};







