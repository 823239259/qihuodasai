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
					eyWindow.wprogress("系统提示","系统处理中,请稍候...");
					$.post(
						Check.rootPath() + "/admin/internation/future/notPass",
						{"id":rows[0].id},
						function(data){
							eyWindow.closeProgress();
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
			$("#type_win").val("1");
		} else if(type==2){
			if(rows[0].stateType != "操盘中"){Check.messageBox("提示","不能修改账户！账户状态必须为：操盘中。");return;}
			$("#tranAccount").val(rows[0].tranAccount);
			$("#tranPassword").val(rows[0].tranPassword);
			$("#type_win").val("2");
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
		var type=$("#type_win").val();
		eyWindow.wprogress("系统提示","系统处理中,请稍候...");
		$.post(Check.rootPath() + "/admin/internation/future/auditPass" 
				,{"id":rows[0].id,"tranAccount":account,"tranPassword":password,"type":type} ,
				function(data){
					eyWindow.closeProgress();
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

function input(){
	var rows = $("#hasAuditData").datagrid('getSelections');
	if (Check.validateSelectItems($("#hasAuditData"),1)) {
		$("#inputFlag").show();
		$("#inputFlag").window('open');
		$("#input_mobile").val(rows[0].mobile);
		$("#input_account").val(rows[0].tranAccount);
		$("#input_traderBond").val(rows[0].traderBond);
	}
}
function importExcl(){
	$.ajaxFileUpload({  
        url : '/admin/internation/future/importExclDetail',  
        secureuri : false,//安全协议  
        fileElementId:'input_file',//id  
        type : 'POST',  
        dataType : 'json',  
        async : false,  
        error : function(data,status,e) {  
            alert('Operate Failed!');  
        },  
        success : function(json) {  
            alert("上传成功");
        }  
    });  
}

/*function input() {
	var rows = $("#hasAuditData").datagrid('getSelections');
	if (Check.validateSelectItems($("#hasAuditData"),1)) {
		
		if (rows[0].businessType == "国际综合"){
			
			$("#a50td").html("A50交易手数:");
			$("#hsiTradeNumTR").show();
			$("#crudeTradeNumTR").show();
			
			$("#mdTradeNumTR").show();
			$("#mnTradeNumTR").show();
			$("#mbTradeNumTR").show();
			$("#daxTradeNumTR").show();
			$("#nikkeiTradeNumTR").show();
			$("#lhsiTradeNumTR").show();
			$("#agTradeNumTR").show();
			$("#hsTradeNumTR").show();
			$("#xHsTradeNumTR").show();
			$("#acTradeNumTR").show();
			$("#asTradeNumTR").show();
			$("#scTradeNumTR").show();
			$("#daxMinTradeNumTR").show();
			$("#inputWin").css("height","457px");
		
		}else
		{
			$("#a50td").html("交易手数:");
			$("#hsiTradeNumTR").hide();
			$("#crudeTradeNumTR").hide();
			
			$("#mdTradeNumTR").hide();
			$("#mnTradeNumTR").hide();
			$("#mbTradeNumTR").hide();
			$("#daxTradeNumTR").hide();
			$("#nikkeiTradeNumTR").hide();
			$("#lhsiTradeNumTR").hide();
			$("#agTradeNumTR").hide();
			$("#hsTradeNumTR").hide();
			$("#xHsTradeNumTR").hide();
			$("#acTradeNumTR").hide();
			$("#asTradeNumTR").hide();
			$("#scTradeNumTR").hide();
			$("#daxMinTradeNumTR").hide();
			$("#inputWin").css("height","250px");

		}
		if(rows[0].stateType == "已结算"){
			Check.messageBox("提示","已结算的用户不能再次录入！");
		}else{
			$("#mobile").val(rows[0].mobile);
			$("#Account").val(rows[0].tranAccount);
			$("#traderBond").val(rows[0].traderBond);
			$("#tranProfitLoss").val("");
			$("#tranActualLever").val("");
			$("#hsiTranActualLever").val("");
			$("#crudeTranActualLever").val("");
			$("#mdtranActualLever").val("");
			$("#mntranActualLever").val("");
			$("#mbtranActualLever").val("");
			$("#daxtranActualLever").val("");
			$("#nikkeiTranActualLever").val("");
			$("#lhsiTranActualLever").val("");
			$("#agTranActualLever").val("");
			$("#heStockMarketLever").val("");
			$("#xhStockMarketLever").val("");
			$("#AmeCopperMarketLever").val("");
			$("#AmeSilverMarketLever").val("");
			$("#smallCrudeOilMarketLever").val("");
			$("#daxtranMinActualLever").val("");
			$("#inputWin").show();
			$("#inputWin").window('open');
		}
	}
};
*/
/**
 * 录入提交
 */
function inputSave() {
	var rows = $("#hasAuditData").datagrid('getSelections');
	if ($("#inputWin").form("validate")) {
		var profit = $("#tranProfitLoss").val();
		var commission = $("#tranActualLever").val();

		var crudeTranActualLever = $("#crudeTranActualLever").val();
		var hsiTranActualLever = $("#hsiTranActualLever").val();
		var mdtranActualLever = $("#mdtranActualLever").val();
		var mbtranActualLever = $("#mbtranActualLever").val();
		var daxtranActualLever = $("#daxtranActualLever").val();
		var nikkeiTranActualLever = $("#nikkeiTranActualLever").val();
		var mntranActualLever = $("#mntranActualLever").val();
		// 2016-05-17 国际综合中追加小恒指和美黄金 By Ryan.he
		var lhsiTranActualLever = $("#lhsiTranActualLever").val();
		var agTranActualLever = $("#agTranActualLever").val();
		var heStockMarketLever=$("#heStockMarketLever").val();
		var xhStockMarketLever=$("#xhStockMarketLever").val();
		var AmeCopperMarketLever=$("#AmeCopperMarketLever").val();
		var AmeSilverMarketLever=$("#AmeSilverMarketLever").val();
		var smallCrudeOilMarketLever=$("#smallCrudeOilMarketLever").val();
		var daxtranMinActualLever = $("#daxtranMinActualLever").val();
		if (!profit) {
			Check.messageBox("提示","请输入对应交易盈亏！");
			return ;
		}

		// 国际综合
		if (rows[0].businessType == "国际综合"){
			if (!commission || !crudeTranActualLever || !hsiTranActualLever
					|| !mdtranActualLever || !mbtranActualLever || !daxtranActualLever
					|| !nikkeiTranActualLever || !mntranActualLever
					|| !lhsiTranActualLever || !agTranActualLever||!heStockMarketLever||!xhStockMarketLever
					||!AmeCopperMarketLever||!AmeSilverMarketLever||!smallCrudeOilMarketLever || !daxtranMinActualLever) {

				Check.messageBox("提示","请输入对应交易手数！");
				return ;
			}
			if(commission < 0 || crudeTranActualLever < 0 || hsiTranActualLever < 0 
					|| mdtranActualLever<0 || mbtranActualLever < 0 || daxtranActualLever < 0
					|| nikkeiTranActualLever < 0 || mntranActualLever < 0
					|| lhsiTranActualLever < 0 || agTranActualLever < 0||heStockMarketLever<0||xhStockMarketLever<0
					||AmeCopperMarketLever<0||AmeSilverMarketLever<0||smallCrudeOilMarketLever<0 || daxtranMinActualLever < 0) {

				Check.messageBox("提示","输入的数据有误,交易手数不能输入负数！");
				return;
			}
		// 单独产品
		} else {
			if(commission < 0){
				Check.messageBox("提示","输入的数据有误，交易手数不能输入负数！");
				return;
			}
		}

		eyWindow.wprogress("系统提示","系统处理中,请稍候...");
		$.post(Check.rootPath() + "/admin/internation/future/input" 
				,{"id":rows[0].id,"tranProfitLoss":profit,"tranActualLever":commission,
				"crudeTranActualLever":crudeTranActualLever,"hsiTranActualLever":hsiTranActualLever,
				"mdtranActualLever":mdtranActualLever,"mbtranActualLever":mbtranActualLever,
				"daxtranActualLever":daxtranActualLever,"nikkeiTranActualLever":nikkeiTranActualLever,
				"mntranActualLever":mntranActualLever,"lhsiTranActualLever":lhsiTranActualLever,
				"agTranActualLever":agTranActualLever,"heStockMarketLever":heStockMarketLever,"xhStockMarketLever":xhStockMarketLever,
				"AmeCopperMarketLever":AmeCopperMarketLever,"AmeSilverMarketLever":AmeSilverMarketLever,"smallCrudeOilMarketLever":smallCrudeOilMarketLever,"daxtranMinActualLever":daxtranMinActualLever} ,
				function(data){
					eyWindow.closeProgress();
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
	$("#tranProfitLoss").val("");
	$("#tranActualLever").val("");
	$("#hsiTranActualLever").val("");
	$("#crudeTranActualLever").val("");
	$("#mdtranActualLever").val("");
	$("#mntranActualLever").val("");
	$("#mbtranActualLever").val("");
	$("#daxtranActualLever").val("");
	$("#nikkeiTranActualLever").val("");
	$("#lhsiTranActualLever").val("");
	$("#agTranActualLever").val("");
	$("#heStockMarketLever").val("");
	$("#xhStockMarketLever").val("");
	$("#AmeCopperMarketLever").val("");
	$("#AmeSilverMarketLever").val("");
	$("#smallCrudeOilMarketLever").val("");
	$("#daxtranMinActualLever").val("");
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
						eyWindow.wprogress("系统提示","系统处理中,请稍候...");
						$.post(Check.rootPath() + "/admin/internation/future/end" 
						,{"id":rows[0].id} ,
						function(data){
							eyWindow.closeProgress();
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

/**
 * 更新补充保证金记录状态
 */
function  updateAppendMoneyStatus(){
	var rowData=$('#appendMoneydatagrid').datagrid('getSelected');
	if (rowData==null){
		eyWindow.walert("提示",'请选择要处理的记录', 'info');
		return;
	}else if(rowData.status!=0){
		eyWindow.walert("提示",'请选择状态为\"未处理\"的记录', 'info');
		return;
	}
	$.messager.confirm("确认提示","确认是否已处理？", function(result){
		if(result){
			ajaxPost({
				url : basepath + 'admin/internation/future/changeStatus',
				cache : false,
				async : false,
				data : {id : rowData.id},
				success : function(data) {
					if (data.success) {
						$("#appendMoneydatagrid").datagrid('reload');
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

/**
 * 交易手数
 */
function tradeCount() {
	var rows = $("#hasAuditData").datagrid('getSelections');
	if (Check.validateSelectItems($("#hasAuditData"),1)) {
		$('#a50Count').html(filterNull(rows[0].tranActualLever));
		$('#hsiCount').html(filterNull(rows[0].hsiTranActualLever));
		$('#crudeCount').html(filterNull(rows[0].crudeTranActualLever));
		$('#mdCount').html(filterNull(rows[0].mdtranActualLever));
		$('#mnCount').html(filterNull(rows[0].mntranActualLever));
		$('#mbCount').html(filterNull(rows[0].mbtranActualLever));
		$('#daxCount').html(filterNull(rows[0].daxtranActualLever));
		$('#nikkeiCount').html(filterNull(rows[0].nikkeiTranActualLever));
		$('#lhsiCount').html(filterNull(rows[0].lhsiTranActualLever));
		$('#agCount').html(filterNull(rows[0].agTranActualLever));
		$('#hsCount').html(filterNull(rows[0].heStockMarketLever));
		$('#xhsCount').html(filterNull(rows[0].xhStockMarketLever));
		$('#acCount').html(filterNull(rows[0].ameCopperMarketLever));
		$('#asCount').html(filterNull(rows[0].ameSilverMarketLever));
		$('#scCount').html(filterNull(rows[0].smallCrudeOilMarketLever));
		$("#daxMinCount").html(filterNull(rows[0].daxtranMinActualLever));
		/*$('#a50Count').html(rows[0].tranActualLever);
		$('#hsiCount').html(rows[0].hsiTranActualLever);
		$('#crudeCount').html(rows[0].crudeTranActualLever);
		$('#mdCount').html(rows[0].mdtranActualLever);
		$('#mnCount').html(rows[0].mntranActualLever);
		$('#mbCount').html(rows[0].mbtranActualLever);
		$('#daxCount').html(rows[0].daxtranActualLever);
		$('#nikkeiCount').html(rows[0].nikkeiTranActualLever);*/
		$("#tradeCountWin").show();
		$('#tradeCountWin').window('open');
	}
}
/**
 * 转换为0处理
 * @param val
 * @returns {Number}
 */
function filterNull(val) {
	if(val==null || val==""){
		return 0;
	}else{
		return val;
	}
}

$(document).ready(function() {
	$('#hasAuditData').datagrid({
		onLoadSuccess:function(data) {
			var rows = data.rows;
			for (var i = 0; i < rows.length; i++) {
				if (rows[i].businessType != "国际综合") {
					var tranActLever = "";
					if ("待结算" == rows[i].stateType || "已结算" == rows[i].stateType) {
						tranActLever = 0;
					}
					// 富时A50
					if (filterNull(rows[i].tranActualLever) != 0) {
						tranActLever = rows[i].tranActualLever;
					}
					// 恒指期货
					if (filterNull(rows[i].hsiTranActualLever) != 0) {
						tranActLever = rows[i].hsiTranActualLever;
					}
					// 国际原油
					if (filterNull(rows[i].crudeTranActualLever) != 0) {
						tranActLever = rows[i].crudeTranActualLever;
					}
					// 小恒指
					if (filterNull(rows[i].lhsiTranActualLever) != 0) {
						tranActLever = rows[i].lhsiTranActualLever;
					}  
					// 交易手数
					$("#hasAuditData").datagrid('updateRow',{
						index:i,
						row:{
							tranActLever:tranActLever
						}
					});
				}
			}
		}
	});
});