$(document).ready(function(){
	$("#addVolume").find(".hasDateClass").click(function(){
		var val = $(this).val();
		if (val == 0) {
			$("#in_datetime").show();
			$("#in_day").hide();
		}
		else {
			$("#in_datetime").hide();
			$("#in_day").show();
		}
	});
	
	$("#updateVolume").find(".hasDateClass").click(function(){
		var val = $(this).val();
		if (val == 0) {
			$("#update_in_datetime").show();
			$("#update_in_day").hide();
		}
		else {
			$("#update_in_datetime").hide();
			$("#update_in_day").show();
		}
	});
	
});

/**
 * 新增状态
 */
function addVolumeOpen() {
	
	$("#addName").val("");
	$("#addMoney").numberbox("setValue",0);
	$("#addReleaseNum").numberbox("setValue",1);
	$('#addStartupDateValue').datetimebox('setValue', "");
	$('#addEndDateValue').datetimebox('setValue', "");
	$('#addDealStartDateValueStr').datetimebox('setValue', "");
	$('#addDealEndDateValueStr').datetimebox('setValue', "");
	
	$("#addEndDayValue").numberbox("setValue",0);
	$("#addVolume").find(".hasDateClass").attr("disabled",false);
	$("#addVolume").find(".hasDateClass").each(function(){
		if ($(this).val() == 0) {
			$(this).click();
		}
	});
	
	$("#addVolume").show();
	$("#addVolume").window('open');
		
};

function addVolumeClose() {
	$("#addVolume").show();
	$("#addVolume").window('close');
		
};

/**
 * 更新
 */
function updateVolumeOpen() {
	//validationCode();
    var rows = $("#dg003").datagrid('getSelections');
	
	if (Check.validateSelectItems($("#dg003"),1)) {
		var obj = rows[0];
		$("#updateId").val(obj.id);
		$("#updateName").val(obj.name);
		$("#updateType").combobox("select",obj.type);
		$("#updateUseType").combobox("select",obj.useType);
		$("#updateReleaseNum").numberbox({"min":obj.releaseNum,"value":obj.releaseNum});
		$("#updateMoney").numberbox('setValue',obj.money);
		$('#updateStartupDateValue').datetimebox('setValue', obj.startupDateValueTimeStr);
		$('#updateEndDateValue').datetimebox('setValue', obj.endDateValueTimeStr);
		$('#updateDealStartDateValueStr').datetimebox('setValue', obj.dealStartDateValueStr);
		$('#updateDealEndDateValueStr').datetimebox('setValue', obj.dealEndDateValueStr);
		
		$("#updateEndDayValue").val(obj.endDayValue);
		$("#updateVolume").find(".hasDateClass").attr("disabled",false);
		$("#updateVolume").find(".hasDateClass").each(function(){
			if ($(this).val() == obj.deadlineType) {
				$(this).click();
			}
		});
		
		if ((obj.stateValue - 0) == -1) {
			$("#updateReleaseNum").numberbox({disabled:true});
		}
		else {
			$("#updateReleaseNum").numberbox({disabled:false});
		}
		
		$("#updateVolume").find(".hasDateClass").attr("disabled",true);
		
		
		$("#updateVolume").show();
		$("#updateVolume").window('open');
	}
	
		
};

function updateVolumeClose() {
	$("#updateVolume").show();
	$("#updateVolume").window('close');
		
};

/**
 * 删除
 */
function deleteVolume() {
	var rows = $("#dg003").datagrid('getSelections');
	
	if (Check.validateSelectItems($("#dg003"),1)) {
	
		$.messager.confirm("提示", "是否需要删除?", function (r) {
			if (r) {
				$.messager.progress({title:"提示",msg:"删除中...."});
				$.post(Check.rootPath() + "/admin/volume/deleteVolume" 
						,{"id":rows[0].id} ,
						function(data){
				    $.messager.progress('close');
					if (data == "success") {
						Check.messageBox("提示","删除成功!");
						$("#dg003").datagrid('reload');
					}
					else {
						Check.messageBox("提示",data,"error");
					}
				});
			}
		});
		
		
	}
};

/**
 * 停用
 */
function stopVolume() {
	var rows = $("#dg003").datagrid('getSelections');
	if (Check.validateSelectItems($("#dg003"),1)) {
	
		$.messager.confirm("提示", "是否确认要停用?", function (r) {
			if (r) {
				$.post(Check.rootPath() + "/admin/volume/stopVolume" 
						,{"id":rows[0].id} ,
						function(data){
					if (data == "success") {
						Check.messageBox("提示","停用成功!");
						$("#dg003").datagrid('reload');
					}
					else {
						Check.messageBox("提示",data,"error");
					}
				});
			}
		});
	}
};

//使用
//$('#id').validatebox('remove'); //删除
//$('#id').validatebox('reduce'); //恢复

/**
 * 提交
 */
function addVolumeSave() {
	
	if ($("#voForm").form("validate")) {
		var validateVal = true;
		$("#allChooseDeadlineType").find("input[name=deadlineType]").each(function(){
			if ($(this).attr("checked") && $(this).attr("checked") == "checked" ) {
				var value = $(this).val() - 0;
				if (value == 0) {
					var val = $("#addEndDateValue").datetimebox("getValue");
					if (val === undefined || val == null || val == "") {
						validateVal = false;
						$.easyui.messageBox("提示","截止日期不能为空!","warning");
					}
				}
				else if (value == 1) {
					var val = $("#addEndDayValue").numberbox("getValue");
					if (val === undefined || val == null || val == "") {
						validateVal = false;
						$.easyui.messageBox("提示","使用天数不能为空!","warning");
					}
				}
			}
		});
		if (!validateVal) {
			return false;
		}
	}
	else {
		return false;
	}
	
	$.messager.progress({title:"提示",msg:"抵扣卷生成中...."});
	$.post(Check.rootPath() + "/admin/volume/saveVolume" 
			,$("#voForm").serialize() ,
			function(data){
		        $.messager.progress('close');
				if (data == "success") {
					Check.messageBox("提示","更新成功");
					$("#dg003").datagrid('reload');
					addVolumeClose();
				}
				else {
					Check.messageBox("提示",data,"error");
				}
	});
	
	
};


/**
 * 提交
 */
function updateVolumeSave() {
	if (!$("#voUpdateForm").form("validate")) {
		return false;
	}
	$.messager.progress({title:"提示",msg:"抵扣卷生成中...."});
	$.post(Check.rootPath() + "/admin/volume/updateVolume" 
			,$("#voUpdateForm").serialize() ,
			function(data){
		        $.messager.progress('close');
				if (data == "success") {
					Check.messageBox("提示","更新成功");
					$("#dg003").datagrid('reload');
					updateVolumeClose();
				}
				else {
					Check.messageBox("提示",data,"error");
				}
	});
};

//抵扣券发放相关js

/**
 * 发放
 */
function grantVolumeOpen() {
	if (Check.validateSelectItems($("#dg003"),1)) {
		var row = $("#dg003").datagrid('getSelected');
		if (row){
			if(row.useStateStr == '已过期'){
				Check.messageBox("发放提示","此抵扣券已过期，请重新选择");
			}else if(row.useStateStr == '已使用'){
				Check.messageBox("发放提示","此抵扣券已被使用，请重新选择");
			}else if(row.useStateStr == '未使用'){
				Check.messageBox("发放提示","此抵扣券已被发放，请重新选择");
			}else{
				$("#grantVolume").show();
				$("#grantVolume").window('open');
				$("#phone").val("");
				$("#name").val("");
			}
		}
	}
	
};
var id;
/**
 * 获取电话号码对应的姓名
 */
function getName(){
	
	var phoneNum= $("#phone").val();
	if(phoneNum.length != 11){
		Check.messageBox("提示","请输入11位电话号码");
	}else{
		$.post(Check.rootPath() + "/admin/volume/getWUser" 
				,{"mobile":phoneNum} ,
				function(data){
					if (data.success) {
						$.messager.progress('close');
						var trueName = data.data.trueName;
						if(trueName == null){
							$("#name").val('');
							Check.messageBox("提示","该用户未进行实名认证");
						}else{
							$("#name").val(trueName);
							id = data.data.uid;
						}
					}
					else {
						$("#name").val('');
						Check.messageBox("提示",data.message,"error");
					}
		},"json");
	}
	
	
	
	
	
}
/**
 * 确认
 */
function grantVolumeSave() {
	var name= $("#name").val();
	if(name==""){
		Check.messageBox("提示","请选择正确的用户再发放");
	}else{
		var row = $("#dg003").datagrid('getSelected');
		var volumeid=row.id;
		
		
		$.post(Check.rootPath() + "/admin/volume/grantVolume" 
				,{"uid":id,"volumeid":volumeid} ,
				function(data){
					$.messager.progress('close');
					if (data == "success") {
						Check.messageBox("提示","发放成功");
						$("#dg003").datagrid('reload');
						grantVolumeClose();
					}
					else {
						Check.messageBox("提示",data,"error");
					}
				});
	}
};





function grantVolumeClose() {
	$("#grantVolume").show();
	$("#grantVolume").window('close');
		
};



	




