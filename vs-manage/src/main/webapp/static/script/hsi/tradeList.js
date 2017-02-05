/**
 * 拒绝
 */
function refuse() {
	var rows = $("#edatagrid").datagrid('getSelections');
	if (Check.validateSelectItems($("#edatagrid"),1)) {
		if(rows[0].stateType != "开户中"){
			Check.messageBox("提示","不能拒绝申请。申请状态必须为：开户中。");
		}else{
			$.messager.confirm("提示", "是否确认拒绝该用户的配资申请?", function (r) {
				if (r) {
					$.post(
						Check.rootPath() + "/admin/hsi/notPass",
						{"id":rows[0].id},
						function(data){
							if (data.success) {
								Check.messageBox("提示",data.message);
								$("#edatagrid").datagrid('reload');
							} else {
								Check.messageBox("提示",data.message,"error");
							}
					});
				}
			});
		}
	}
};


/**
 * 分配账户
 */
function pass(type) {
	var rows = $("#edatagrid").datagrid('getSelections');
	if (Check.validateSelectItems($("#edatagrid"),1)) {
		if(type==1){
			if(rows[0].stateType != "开户中"){Check.messageBox("提示","不能分配账户！申请状态必须为：开户中。");return;}
			$("#tranAccount").val("");
			$("#tranPassword").val("");
		} else if(type==2){
			if(rows[0].stateType != "操盘中"){Check.messageBox("提示","不能修改账户！账户状态必须为：操盘中。");return;}
			$("#tranAccount").val(rows[0].tranAccount);
			$("#tranPassword").val(rows[0].tranPassword);
		}
		$("#passWin").show();
		$("#passWin").window('open');
	}
};

/**
 * 分配账户提交
 */
function passSave() {
	var rows = $("#edatagrid").datagrid('getSelections');
	if ($("#passWin").form("validate")) {
		var account = $("#tranAccount").val();
		var password = $("#tranPassword").val();
		$.post(Check.rootPath() + "/admin/hsi/auditPass" 
				,{"id":rows[0].id,"tranAccount":account,"tranPassword":password} ,
				function(data){
					if (data.success) {
						Check.messageBox("提示",data.message);
						$("#edatagrid").datagrid('reload');
						$("#hasAuditData").datagrid('reload');
						passClose() ;
					} else {
						Check.messageBox("提示",data.message,"error");
					}
		});
	}
};

function passClose() {
	$("#passWin").show();
	$("#passWin").window('close');
};


/**
 * 录入
 */
function input() {
	var rows = $("#hasAuditData").datagrid('getSelections');
	if (Check.validateSelectItems($("#hasAuditData"),1)) {
		if(rows[0].stateType == "已结算"){
			Check.messageBox("提示","已结算的用户不能再次录入！");
		}else{
			$("#mobile").val(rows[0].mobile);
			$("#Account").val(rows[0].tranAccount);
			$("#traderBond").val(rows[0].traderBond);
			$("#tranProfitLoss").val("");
			$("#tranActualLever").val("");
			$("#inputWin").show();
			$("#inputWin").window('open');
		}
	}
};

/**
 * 录入提交
 */
function inputSave() {
	var rows = $("#hasAuditData").datagrid('getSelections');
	if ($("#inputWin").form("validate")) {
		var profit = $("#tranProfitLoss").val();
		var commission = $("#tranActualLever").val();
		$.post(Check.rootPath() + "/admin/hsi/input" 
				,{"id":rows[0].id,"tranProfitLoss":profit,"tranActualLever":commission} ,
				function(data){
					if (data.success) {
						Check.messageBox("提示",data.message);
						$("#edatagrid").datagrid('reload');
						$("#hasAuditData").datagrid('reload');
						inputClose();
					} else {
						Check.messageBox("提示",data.message,"error");
					}
		});
	
	}
	
};
function inputClose() {
	$("#inputWin").show();
	$("#inputWin").window('close');
};

/**
 * 结算处理
 */
function end() {
	var rows = $("#hasAuditData").datagrid('getSelections');
	if (Check.validateSelectItems($("#hasAuditData"),1)) {
		if(rows[0].stateType == "已结算"){
			Check.messageBox("提示","该用户已结算！");
		}else{
			if(rows[0].endAmount==null){
				Check.messageBox("提示","请录入结果后结算！");
			}else{
				$.messager.confirm("提示", "是否确认结算该账户?", function (r) {
					if (r) {
						$.post(Check.rootPath() + "/admin/hsi/end" 
						,{"id":rows[0].id} ,
						function(data){
							if (data.success) {
								Check.messageBox("提示",data.message);
								$("#edatagrid").datagrid('reload');
								$("#hasAuditData").datagrid('reload');
								inputClose();
							} else {
								Check.messageBox("提示",data.message,"error");
							}
						});
					}
				});
			}
		}
	}
};
