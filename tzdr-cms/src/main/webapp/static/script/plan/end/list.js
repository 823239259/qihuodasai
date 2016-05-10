
function endOfPlanOpen() {
	if (Check.validateSelectItems($("#dg004"),1)) {
		$("#endOfPlan").show();
		$("#endOfPlan").window('open');
	}
};


function endOfPlanOpen01() {
	if (Check.validateSelectItems($("#dg003"),1)) {
		$("#endOfPlan01").show();
		$("#endOfPlan01").window('open');
	}
};


function updateEndOfPlanPass01() {
	if (!$("#endOfForm01").form("validate")) {
		return false;
	}
	var rows = $("#dg003").datagrid('getSelections');
	$.post(Check.rootPath() + "/admin/plan/end/updateEndPlanSubmit" 
			,{"groupId":rows[0].groupId,"amount":$("#endOfPlanAmount01").numberbox("getValue")} ,function(data){
		if (data == "success") {
			Check.messageBox("提示","更新成功");
			$("#dg003").datagrid('reload');
			endOfPlanClose01();
		}
		else {
			Check.messageBox("提示",data,"error");
		}
	},"text");
};


function updateEndOfPlanPass() {
	if (!$("#endOfForm").form("validate")) {
		return false;
	}
	var rows = $("#dg004").datagrid('getSelections');
	$.post(Check.rootPath() + "/admin/plan/end/endPlanPassSuccessful" 
			,{"groupId":rows[0].groupId,"amount":$("#endOfPlanAmount").numberbox("getValue")} ,function(data){
		if (data == "success") {
			Check.messageBox("提示","更新成功");
			$("#dg004").datagrid('reload');
			endOfPlanClose();
		}
		else {
			Check.messageBox("提示",data,"error");
		}
	},"text");
};

function updateEndOfPlanNoPass() {
	if (!$("#noPassForm").form("validate")) {
		return false;
	}
	$.messager.progress({
        title:'提示',
        msg:'执行中'
    });
	var rows = $("#dg004").datagrid('getSelections');
	$.post(Check.rootPath() + "/admin/plan/end/endPlanFail" 
			,{"groupId":rows[0].groupId,"failCause":$("#failCause_id").val(),"activityType":rows[0].activityType} ,function(data){
		if (data == "success") {
			Check.messageBox("提示","更新成功");
			$("#dg004").datagrid('reload');
			endOfPlanNoPassClose();
		}
		else {
			Check.messageBox("提示",data,"error");
		}
		$.messager.progress('close');
	},"text");
};

function updateEndOfPlanNoPass01() {
	if (!$("#noPassForm01").form("validate")) {
		return false;
	}
	$.messager.progress({
        title:'提示',
        msg:'执行中'
    });
	var rows = $("#dg003").datagrid('getSelections');
	$.post(Check.rootPath() + "/admin/plan/end/endPlanFail" 
			,{"groupId":rows[0].groupId,"failCause":$("#failCause_id01").val(),"activityType":rows[0].activityType} ,function(data){
		if (data == "success") {
			Check.messageBox("提示","更新成功");
			$("#dg003").datagrid('reload');
			endOfPlanNoPassClose01();
		}
		else {
			Check.messageBox("提示",data,"error");
		}
		$.messager.progress('close');
	},"text");
};




function endOfPlanClose() {
	$("#endOfPlan").show();
	$("#endOfPlan").window('close');
		
};

function endOfPlanClose01() {
	$("#endOfPlan01").show();
	$("#endOfPlan01").window('close');
		
};

function endOfPlanNoPassOpen01() {
	if (Check.validateSelectItems($("#dg003"),1)) {
		$("#endOfPlanNoPass01").show();
		$("#failCause_id01").val("");
		$("#endOfPlanNoPass01").window('open');
	}
		
};

function endOfPlanNoPassOpen() {
	if (Check.validateSelectItems($("#dg004"),1)) {
		$("#endOfPlanNoPass").show();
		$("#failCause_id").val("");
		$("#endOfPlanNoPass").window('open');
	}
		
};

function endOfPlanNoPassClose() {
	$("#endOfPlanNoPass").show();
	$("#endOfPlanNoPass").window('close');
		
};

function endOfPlanNoPassOpen01() {
	if (Check.validateSelectItems($("#dg003"),1)) {
		$("#endOfPlanNoPass01").show();
		$("#failCause_id01").val("");
		$("#endOfPlanNoPass01").window('open');
	}
		
};

function endOfPlanNoPassClose01() {
	$("#endOfPlanNoPass01").show();
	$("#endOfPlanNoPass01").window('close');
		
};






