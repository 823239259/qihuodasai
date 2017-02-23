
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

	
var bussType = "";
var endType = 0;
/**
 * 自动导入
 */
function autoinput(){
	var rows = $("#hasAuditData").datagrid('getSelections');
	if (Check.validateSelectItems($("#hasAuditData"),1)) {
		var bussinessType = rows[0].businessType;
		bussType = bussinessType;
		var id = rows[0].id;
		$("#input_file_tr").hide();//隐藏导入数据tr
		if(rows[0].stateType == "已结算"){
			Check.messageBox("提示","已结算的用户不能再次录入！");
			return;
		}
		if(rows[0].stateType == "待结算"){
			$.ajax({
				url:Check.rootPath() +"/admin/internation/future/getFtse",
				type:"get",
				data:{
					id:id
				},
				success:function(result){
					var data = result.data.fste;
					var tradeDetail = result.data.tradeDetail;
					var html = appendTradeDetailHtml(tradeDetail, 0);
					$("#tradeDetail").html(html);
					handleData(data,0);
					inputLeverShow(bussinessType);
					$("#tranProfitLoss").attr("disabled","disabled");
					$("#inputWin .easyui-validatebox").attr("disabled","disabled");
					$("#inputWin").show();
					$("#inputWin").window('open');
				}
			});
			
		}		
		if(rows[0].stateType == "申请结算" || rows[0].stateType == "操盘中"){
			$.ajax({
				url:Check.rootPath() +"/admin/internation/future/getFtse",
				type:"get",
				data:{
					id:id
				},
				success:function(result){
					var tranAccount = result.data.fste.tranAccount;
					var tranPassword = result.data.fste.tranPassword;
					evaluation(tranAccount,tranPassword);
				    initQuoteClient()//连接行情
				}
			});
		}
		
		$("#mobile").val(rows[0].mobile);
		$("#Account").val(rows[0].tranAccount);
		$("#traderBond").val(rows[0].traderBond);
		$("#tradeDetail").html("");
	}
}

function testcheck(tranAccount,todayMoeny){
	var rows = $("#hasAuditData").datagrid('getSelections');
	var id = rows[0].id;
	$.ajax({
		url:Check.rootPath() +"/admin/internation/future/getAllDetails",
		type:"post",
		data:{
			"tranAccount":tranAccount,
			"todayMoeny":todayMoeny,
			"id":id
		},
		success:function(result){
			var tradeDetails = result.data.tradeDetails;
			var leadLever = result.data.leadLever;
			var html = appendTradeDetailHtml(tradeDetails, 0);
			$("#tradeDetail").html(html);
			handleData(leadLever,1);
			inputLeverShow(bussType);
			endType = 1;
			Trade.doLoginOut(tranAccount,"");
			$("#tranProfitLoss").attr("disabled","disabled");
			$("#inputWin .easyui-validatebox").attr("disabled","disabled");
			$("#inputWin").show();
			$("#inputWin").window('open');
		}						
	});
}


/**
 * 手动导入
 */
function input(){
	localDataLever = null;
	var rows = $("#hasAuditData").datagrid('getSelections');
	if (Check.validateSelectItems($("#hasAuditData"),1)) {
		if(rows[0].stateType == "已结算"){
			Check.messageBox("提示","已结算的用户不能再次录入！");
			return;
		}
		var bussinessType = rows[0].businessType;
		bussType = bussinessType;
		inputLeverShow(bussinessType);
		var id = rows[0].id;
		$.ajax({
			url:Check.rootPath() +"/admin/internation/future/getFtse",
			type:"get",
			data:{
				id:id
			},
			success:function(result){
				var data = result.data.fste;
				var tradeDetail = result.data.tradeDetail;
				var html = appendTradeDetailHtml(tradeDetail, 0);
				$("#tradeDetail").html(html);
				handleData(data,0);
			}
		});
		$("#inputWin").show();
		$("#inputWin").window('open');
		$("#mobile").val(rows[0].mobile);
		$("#Account").val(rows[0].tranAccount);
		$("#traderBond").val(rows[0].traderBond);
		$("#tradeDetail").html("");
	}
}

var localDataLever = null;
function importExcl(){
	$(function(){
		$.ajaxFileUpload({  
	        url : Check.rootPath() + '/admin/internation/future/importExclDetail',  
	        secureuri : false,//安全协议  
	        fileElementId:'input_file',//id  
	        type : 'POST',  
	        dataType : 'json',  
	        async : false,  
	        error : function(data,status,e) {  
	            alert('导入失败!');  
	        },  
	        success : function(result) {  
	            if(result.success){
	            	var data = result.data.data;
	            	var html = appendTradeDetailHtml(data, 1);
	            	$("#tradeDetail").html(html);
	            	handleData(result.data.dataLever,1);
	            	localDataLever = JSON.stringify(data);
	            }else{
	            	Check.messageBox("提示",data.message);
	            }
	        }  
	    });  
	})
}
function appendTradeDetailHtml(tradeDetail,index){
			var html = "<tr>"+
			"<td>序号</td>"+
			"<td>成交日期</td>"+
			"<td>客户名称</td>"+
			"<td>客户号</td>"+
			"<td>币种</td>"+
			"<td>交易所</td>"+
			"<td>品种</td>"+
			"<td>买</td>"+
			"<td>卖</td>"+
			"<td>成交价</td>"+
			"<td>手续费</td>"+
			"<td>下单类型</td>"+
			"<td>下单人编号</td>" +
			"<td>下单人姓名</td>" +
			"<td>成交类型</td>" +
			"</tr>";
		var data = tradeDetail;
		var length = data.length;
		for(var i = index ;i  < length ; i++){
			var _data = data[i];
			if(_data.tradeDate == null || _data.tradeDate == "null" || _data.tradeDate.length == 0){
				continue;
			}
			var marketTime = "";
			if(_data.marketTime==undefined||_data.marketTime==null||_data.marketTime=="null"){
				marketTime = "";
			}else{
				marketTime = _data.marketTime;
			}
			var  number = parseInt(_data.buyNum)+parseInt(_data.sellNum);
			html += "<tr>" +
				"<td>"+i+"</td>" +
				"<td>"+_data.tradeDate+"</td>" +
				"<td>"+_data.username+"</td>" +
				"<td>"+_data.userNo+"</td>" +
				"<td>"+_data.currencyNo+"</td>" +
				"<td>"+_data.exchangeNo+"</td>" +
				"<td>"+_data.commodityNo+"</td>" +
				"<td>"+parseFloat(_data.buyNum).toFixed(0)+"</td>" +
				"<td>"+parseFloat(_data.sellNum).toFixed(0)+"</td>" +
				"<td>"+_data.tradePrice+"</td>" +
				"<td>"+_data.free+"</td>" +
				"<td>"+_data.orderType+"</td>" +
				"<td>"+_data.orderUserno+"</td>" +
				"<td>"+_data.orderUsername+"</td>" +
				"<td>"+_data.tradeType+"</td>" +
				"</tr>";
			
		}
		return html;
}
function handleData(fast,index){
		var dataLever = fast;
		if(bussType == "国际综合"){
			$("#tranProfitLoss").val(dataLever.tranProfitLoss==undefined?"":dataLever.tranProfitLoss);
			$("#tranActualLever").val(dataLever.tranActualLever==undefined?0:dataLever.tranActualLever);
			$("#hsiTranActualLever").val(dataLever.hsiTranActualLever==undefined?0:dataLever.hsiTranActualLever);
			$("#mdtranActualLever").val(dataLever.mdtranActualLever==undefined?0:dataLever.mdtranActualLever);
			$("#mntranActualLever").val(dataLever.mntranActualLever==undefined?0:dataLever.mntranActualLever);
			$("#crudeTranActualLever").val(dataLever.crudeTranActualLever==undefined?0:dataLever.crudeTranActualLever);
			$("#mbtranActualLever").val(dataLever.mbtranActualLever==undefined?0:dataLever.mbtranActualLever);
			$("#daxtranActualLever").val(dataLever.daxtranActualLever==undefined?0:dataLever.daxtranActualLever);
			$("#nikkeiTranActualLever").val(dataLever.nikkeiTranActualLever==undefined?0:dataLever.nikkeiTranActualLever);
			$("#lhsiTranActualLever").val(dataLever.lhsiTranActualLever==undefined?0:dataLever.lhsiTranActualLever);
			$("#agTranActualLever").val(dataLever.agTranActualLever==undefined?0:dataLever.agTranActualLever);
			$("#heStockMarketLever").val(dataLever.heStockMarketLever==undefined?0:dataLever.heStockMarketLever);
			$("#xhStockMarketLever").val(dataLever.xhStockMarketLever==undefined?0:dataLever.xhStockMarketLever);
			$("#AmeCopperMarketLever").val(dataLever.AmeCopperMarketLever==undefined?0:dataLever.AmeCopperMarketLever);
			$("#AmeSilverMarketLever").val(dataLever.AmeSilverMarketLever==undefined?0:dataLever.AmeSilverMarketLever);
			$("#smallCrudeOilMarketLever").val(dataLever.smallCrudeOilMarketLever==undefined?0:dataLever.smallCrudeOilMarketLever);
			$("#daxtranMinActualLever").val(dataLever.daxtranMinActualLever==undefined?0:dataLever.daxtranMinActualLever);
		}else if(bussType == "富时A50"){
			$("#tranActualLever").val(dataLever.tranActualLever==undefined?0:dataLever.tranActualLever);
		}else if(bussType == "国际原油"){
			if(index == 1){
				$("#tranActualLever").val(dataLever.crudeTranActualLever==undefined?0:dataLever.crudeTranActualLever);
			}else if(index == 0){
				$("#tranActualLever").val(dataLever.tranActualLever==undefined?0:dataLever.tranActualLever);
			}
		}else if(bussType == "恒生指数"){
			if(index == 1){
				$("#tranActualLever").val(dataLever.hsiTranActualLever==undefined?0:dataLever.hsiTranActualLever);
			}else if(index == 0){
				$("#tranActualLever").val(dataLever.tranActualLever==undefined?0:dataLever.tranActualLever);
			}
		}								   
		$("#tranProfitLoss").val(dataLever.tranProfitLoss);
}

function inputLeverShow(businessType) {
	if (businessType == "国际综合"){
		$("#a50td").html("A50交易手数:");
		$(".hsiTradeNumTR").show();
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
	}else{
		$("#a50td").html("交易手数:");
		$(".hsiTradeNumTR").hide();
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
		$("#inputWin").css("height","457px");
	}
};


/**
 * 录入提交
 */
function handInputSave() {
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
				"AmeCopperMarketLever":AmeCopperMarketLever,"AmeSilverMarketLever":AmeSilverMarketLever,
				"smallCrudeOilMarketLever":smallCrudeOilMarketLever,"daxtranMinActualLever":daxtranMinActualLever,
				"endType":endType,"tradeDetail":localDataLever
				} ,
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


function closeTradeCount(){
	$("#tradeCountWin").show();
	$("#tradeCountWin").window('close');
}

function tradeOpenEnd(){
	var rows = $("#hasAuditData").datagrid('getSelections');
	if (Check.validateSelectItems($("#hasAuditData"),1)) {
		if(rows[0].stateType == "已结算"){
			Check.messageBox("提示","该用户已结算！");
		}else{
			if(rows[0].endAmount==null){
				Check.messageBox("提示","请录入结果后结算！");
			}else{
				tradeCount();
				var id = rows[0].id;
				$.ajax({
					url:Check.rootPath() +"/admin/internation/future/getFtse",
					type:"get",
					data:{
						id:id
					},
					success:function(result){
						var data = result.data.fste;
						var tradeDetail = result.data.tradeDetail;
						var html = appendTradeDetailHtml(tradeDetail, 0);
						$("#end_tradeDetail").html(html);
					}
				});
				$("#btn_end").show();
			}
		}
	}
	
}
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
								closeTradeCount();
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
		$.ajax({
			url:Check.rootPath() +"/admin/internation/future/getFtse",
			type:"get",
			data:{
				id:rows[0].id
			},
			success:function(result){
				var data = result.data.fste;
				var tradeDetail = result.data.tradeDetail;
				if(data.endtype == 1){
					$("#end_type_show").html("自动结算");
					$("#end_type_show").css({color:'#FFDEAD',width:'16px'});
				}else{
					$("#end_type_show").html("手动结算");
					$("#end_type_show").css({color:'#C0C0C0',width:'16px'});
				}
				var html = appendTradeDetailHtml(tradeDetail, 0);
				$("#end_tradeDetail").html(html);
			}
		});
		$("#tradeCountWin").show();
		$('#tradeCountWin').window('open');
		$("#btn_end").hide();
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
/**
 * 拒绝结算
 */
function refuseInput(){
	var rows = $("#hasAuditData").datagrid('getSelections');
	if (Check.validateSelectItems($("#hasAuditData"),1)) {
		var stateType = rows[0].stateType;
		if(stateType == "已结算"){
			Check.messageBox("提示","该用户已结算！");
			return;
		}
		if(stateType == "操盘中"){
			Check.messageBox("提示","操盘中的方案不能操作！");
			return;
		}
			$.messager.confirm("确认提示","是否拒绝结算？", function(result){
				if(result){
					var id = rows[0].id;
					$.ajax({
						url:Check.rootPath() +"/admin/internation/future/refuseInput",
						type:"post",
						data:{
							id:id,
							stateType:4
						},success:function(result){
							if(result.success){
								eyWindow.walert("成功提示", result.message, 'info');
								$("#edatagrid").datagrid('reload');
								$("#hasAuditData").datagrid('reload');
							}
						}
					});
				}
		});
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