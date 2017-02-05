

/**
 * 新增方案
 */
function addBespokeTrade() {
	
	$("#addName").val("");
	$("#addMinBond").numberspinner('setValue', "");
	$("#addMaxBond").numberspinner('setValue', "");
	$("#addMinMultiple").numberspinner('setValue', "");
	$("#addMaxMultiple").numberspinner('setValue', "");
	$("#addMinDuration").numberspinner('setValue', "");
	$("#addMaxDuration").numberspinner('setValue', "");
	$("#addShortestDuration").numberspinner('setValue', "");
	$("#addInterest").numberspinner('setValue', "");
	$("#addExpenese").numberspinner('setValue', "");
	$("#addStartTime").datetimebox('setValue', "");
	
	
	$("#addWin").show();
	$("#addWin").window('open');
		
};

function addBespokeTradeClose() {
	$("#addWin").show();
	$("#addWin").window('close');
		
};


/**
 * 删除
 */
function deleteBespokeTrade() {
	
		var row = $("#edatagrid").datagrid('getSelected');
		if(row.stateValue == "启用"){
			Check.messageBox("提示","删除方案前，请先停用该方案！");
		}else{
		    baseUtils.deleteData("bespokeTrade");
	}
};

/**
 * 停用
 */
function stopBespokeTrade() {
	var rows = $("#edatagrid").datagrid('getSelections');
	if (Check.validateSelectItems($("#edatagrid"),1)) {
	
		$.messager.confirm("提示", "是否确认要停用?", function (r) {
			if (r) {
				$.post(Check.rootPath() + "/admin/bespokeTrade/stop" 
						,{"id":rows[0].id} ,
						function(data){
					if (data == "success") {
						Check.messageBox("提示","停用成功!");
						$("#edatagrid").datagrid('reload');
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
 * 提交
 */
function addBespokeTradeSave() {
	
	if ($("#addWin").form("validate")) {
		var minMultiple = $("#addMinMultiple").val();
		var maxMultiple = $("#addMaxMultiple").val();
		var minDuration = $("#addMinDuration").val();
		var maxDuration = $("#addMaxDuration").val();
		
		if(Number(maxMultiple) < Number(minMultiple)){
			Check.messageBox("提示","请输入正确的倍数区间");
			return false;
		}else if(Number(maxDuration) < Number(minDuration)){
			Check.messageBox("提示","请输入正确的时长区间");
			return false;
		}else{
			baseUtils.saveOrUpdate("admin/bespokeTrade/create");
			
		}
		
		
	}
	
};
