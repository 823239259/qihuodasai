/**
 * 拒绝
 */
function refuse() {
	var rows = $("#edatagrid").datagrid('getSelections');
	if (Check.validateSelectItems($("#edatagrid"),1)) {
		if(rows[0].stateTypeStrOne == "申请已拒绝"){
			Check.messageBox("提示","该用户已经被拒绝！");
		}else{
			$.messager.confirm("提示", "是否确认拒绝该用户的配资申请?", function (r) {
				if (r) {
					$.post(Check.rootPath() + "/admin/spif/refuse" 
							,{"id":rows[0].id,"mobileNo":rows[0].mobile} ,
							function(data){
						if (data == "success") {
							Check.messageBox("提示","拒绝成功!");
							$("#edatagrid").datagrid('reload');
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
 * 分配账户
 */
function pass() {
	var rows = $("#edatagrid").datagrid('getSelections');
	if (Check.validateSelectItems($("#edatagrid"),1)) {
		
		if(rows[0].stateTypeStrOne == "申请已拒绝"){
			Check.messageBox("提示","被拒绝的申请不能分配账户！");
		}else{
			$("#account").val("");
			$("#password").val("");
			$("#passWin").show();
			$("#passWin").window('open');
		}
	}
};

/**
 * 分配账户提交
 */
function passSave() {
	var rows = $("#edatagrid").datagrid('getSelections');
	if ($("#passWin").form("validate")) {
		var account = $("#account").val();
		var password = $("#password").val();
		$.post(Check.rootPath() + "/admin/spif/pass" 
				,{"id":rows[0].id,"account":account,"password":password} ,
				function(data){
			if (data == "success") {
				Check.messageBox("提示","分配成功!");
				$("#edatagrid").datagrid('reload');
				$("#hasAuditData").datagrid('reload');
				 passClose() ;
			}
			else {
				Check.messageBox("提示",data,"error");
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
		if(rows[0].stateTypeStrTwo == "已结算"){
			Check.messageBox("提示","已结算的用户不能再次录入！");
		}else{
			$("#mobile").val(rows[0].mobile);
			$("#inputAccount").val(rows[0].tranAccount);
			$("#bond").val(rows[0].traderBond);
			$("#appendTraderBond").val(rows[0].appendTraderBond);
			$("#profit").val("");
			$("#commission").val("");
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
		var profit = $("#profit").val();
		var commission = $("#commission").val();
		$.post(Check.rootPath() + "/admin/spif/input" 
				,{"id":rows[0].id,"profit":profit,"commission":commission} ,
				function(data){
			if (data == "success") {
				Check.messageBox("提示","录入成功!");
				$("#hasAuditData").datagrid('reload');
				 inputClose() ;
			}
			else {
				Check.messageBox("提示",data,"error");
			}
		});
	
	}
	
};

function inputClose() {
	$("#inputWin").show();
	$("#inputWin").window('close');
		
};

function end() {
	var rows = $("#hasAuditData").datagrid('getSelections');
	if (Check.validateSelectItems($("#hasAuditData"),1)) {
		
		if(rows[0].stateTypeStrTwo == "已结算"){
			Check.messageBox("提示","该用户已结算！");
		}else{
			if(rows[0].endAmount==null){
				Check.messageBox("提示","请录入结果后结算！");
			}else{
				
				$.post(Check.rootPath() + "/admin/spif/end" 
						,{"id":rows[0].id} ,
						function(data){
							if (data == "success") {
								Check.messageBox("提示","结算成功!");
								$("#hasAuditData").datagrid('reload');
								inputClose() ;
							}
							else {
								Check.messageBox("提示",data,"error");
							}
						});
			}
		}
	}
		
};



function format(value){
	if(value=="0天"){
		return '<a style="color:red;">'+value+'</a>';
	}else{
		return '<a >'+value+'</a>';
	}
}

/**
 * 处理追加保证金列表数据
 */
function handleAppendMoney(){
	
	var rows = $("#applendMoneyData").datagrid('getChecked');
	if (!rows || rows.length==0){
		eyWindow.walert("提示","请选择数据", 'info');
		return;
	}
	var ids = [];
	for (var i=0;i<rows.length;i++)
	{
		ids.push(rows[i].id);
	}
	
	// 删除数据
	$.messager.confirm("确认提示","请确认您选择的记录是否已处理？", function(result){
		if (result){
			ajaxPost({
				url : basepath + "admin/spif/handleAppendMoney",
				cache : false,
				async : false,
				data : {
					"ids" :ids.join(",")
				},
				success : function(data) {
					if (data.success) {
						$("#applendMoneyData").datagrid('reload');
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
