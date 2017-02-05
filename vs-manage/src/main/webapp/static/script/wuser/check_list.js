
/**
 * 生置状态
 */
function resetStateValue() {
	if (Check.validateSelectItems($("#dg001"),1)) {
    	$.messager.confirm('修改认证状态', '确定修改用户状态?', function(r){
    		if (r){
    			var rows = $("#dg001").datagrid("getSelections");
    			var id = rows[0].id;
    			$.post(Check.rootPath() + "/admin/wUser/updateWuserState" 
    					,{"userId":id, "stateValue":5} ,function(data){
    				if (data == "success") {
    					Check.messageBox("提示","更新成功");
    					$("#dg001").datagrid('reload');
    				}
    			});
    		}
    	});
	}
};

/**
 * 显示身份照片审核-Windows
 */
function sidApplyWinShow() {
	if (Check.validateSelectItems($("#dg002"),1)) {
		var rows = $("#dg002").datagrid('getSelections');
		var prefix = $("#pathPrefix").val();
		var row = rows[0];
		//var userVer = row.userVerified;
		$("#sidUserId").val(row.id);
		var v = Math.random();
		$("#imgId001").attr("src",(prefix + row.idcardFront) + "" + "?v=" + v);//正面
		$("#imgId002").attr("src",(prefix + row.idcardBack) + "" + "?v=" + v);//背面
		$("#imgId003").attr("src",(prefix + row.idcardPath) + "" + "?v=" + v);//手持
		$("#imgA001").attr("href", (prefix + row.idcardFront) + "" + "?v=" + v);
		$("#imgA002").attr("href", (prefix + row.idcardBack) + "" + "?v=" + v);
		$("#imgA003").attr("href", (prefix + row.idcardPath) + "" + "?v=" + v);
		$("#sidApplyWin").show();
		$("#sidApplyWin").window('open');
	}
};
function sidPass() {
	var rows = $("#dg002").datagrid('getSelections');
	$.post(Check.rootPath() + "/admin/wUser/updateWuserState" 
			,{"userId":rows[0].id, "stateValue":2} ,function(data){
		if (data == "success") {
			Check.messageBox("提示","更新成功");
			$("#sidApplyWin").window('close');
			$("#dg002").datagrid('reload');
		}
	});
};

/**
 * 没有通过
 */
function sidNotPass() {
	var rows = $("#dg002").datagrid('getSelections');
	$.post(Check.rootPath() + "/admin/wUser/updateWuserState" 
			,{"userId":rows[0].id, "stateValue":1,"notByReason":$("#applyNotByReasonId").val()} ,function(data){
		if (data == "success") {
			Check.messageBox("提示","更新成功");
			$("#applyReasonWin").window("close");
			$("#sidApplyWin").window('close');
			$("#dg002").datagrid('reload');
		}
	});
};

/**
 * 不通过原因弹出框
 */
function applayReasonShow() {
   $("#applyNotByReasonId").val("");
   $("#applyReasonWin").show();
   $("#applyReasonWin").window('open');	
};


/**
 * 显示身份照片审核-Windows
 */
function threeApplyWinShow() {
	if (Check.validateSelectItems($("#dg003"),1)) {
		var rows = $("#dg003").datagrid('getSelections');
		var prefix = $("#pathPrefix").val();
		var row = rows[0];
		//var userVer = row.userVerified;
		$("#sidUserId").val(row.id);
		$("#threeImgId001").attr("src",prefix + row.idcardFront);//正面
		$("#threeImgId002").attr("src",prefix + row.idcardBack);//背面
		$("#threeImgId003").attr("src",prefix + row.idcardPath);//手持
		$("#threeApplyWin").show();
		$("#threeApplyWin").window('open');
	}
};

function threePass() {
	var rows = $("#dg003").datagrid('getSelections');
	$.post(Check.rootPath() + "/admin/wUser/updateWuserState" 
			,{"userId":rows[0].id, "stateValue":2} ,function(data){
		if (data == "success") {
			Check.messageBox("提示","更新成功");
			$("#threeApplyWin").window('close');
			$("#dg003").datagrid('reload');
		}
	});
};

/**
 * 没有通过
 */
function threeNotPass() {
	var rows = $("#dg003").datagrid('getSelections');
	$.post(Check.rootPath() + "/admin/wUser/updateWuserState" 
			,{"userId":rows[0].id, "stateValue":1,"notByReason":$("#threeApplyNotByReasonId").val()} ,function(data){
		if (data == "success") {
			Check.messageBox("提示","更新成功");
			$("#threeApplyReasonWin").window("close");
			$("#threeApplyWin").window('close');
			$("#dg003").datagrid('reload');
		}
	});
};

/**
 * 不通过原因弹出框
 */
function threeApplayReasonShow() {
   $("#threeApplyNotByReasonId").val("");
   $("#threeApplyReasonWin").show();
   $("#threeApplyReasonWin").window('open');	
};



