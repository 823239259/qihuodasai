var PGB={
	closeWindow:function (jqueryFind,callback) {
		$(".fl_mask").hide();
		$(jqueryFind).hide();
		if(callback){
			try {
				callback(this);
			}catch (e){}
		}
	},openWindow:function (jqueryFind,callback) {
		$(".fl_mask").show();
		$(jqueryFind).show();
		if(callback){
 			try {
				callback(this);
			}catch (e){}
		}
	}
};


function getMoney(){
	var month =$("#monthNum").val();
	var groupId=$("#group_id").val();
	var balance =$("#balance").val();
	if(month != "请选择"){
	$.post(basepath + "user/monthTrade/countFee.json", {month:month,groupId:groupId,ajax:1}, 
		function(result){
		if(result.success == true){
			var money=result.obj;
			$("#totalFee").text(formatMoney(result.obj)+"元");
			if(Number(balance) <Number(money)){
				$("#notEnoughDiv").css("display","block");
				$("#sure1").css("background","#b7b7b7").css("border","none");
			}else{
				$("#notEnoughDiv").css("display","none");
				$("#sure1").css("background","#ff524f").css("border","1px solid #ff524f");
					}
				}
			}
			
		)
	}else{
		$("#notEnoughDiv").css("display","none");
		$("#totalFee").text("");
	}
}


$(document).ready(function() {
	
	$("#oAccount").find("a").removeClass("cur");
	$('#navlist a').removeClass('cur');
	$("#nav_my").addClass("cur");
	$('.uc_sidebar').find("div.uc_nav ul a").each(function(){
		$(this).removeClass('on');
 	});
	$("#stock").parent().addClass('on');
	
	/* 账户管理费*/
	var fee= function(type,groupId){
		$.post(basepath + "user/monthTrade/detail.json", {type:type,groupId:groupId,ajax:1}, function(result) {
			if (result.success) {	
				var data=result.obj;
				var head='<tr>';
				head+='<td id="td1">支付时间</td>';
				head+='<td id="td2">总操盘费用</td>';
				head+='</tr>';
				var tr='<tr>';
				tr+='<td id="td5">{0}</td>';
				tr+='<td id="td6">{1}</td>';
				tr+='</tr>';
				var table="";
				
				$.each(data, function(){
					var time= this.uptimestr;
					var money = Number(this.money).toFixed(2);
					table+=$.format(tr,time,money);				
				});
				$('#fee').empty();
				$('#fee').append(head+table);
			}
		}, "json");	
	}
	
	//获取管理费信息
	fee(3,$('#group_id').val());  
	
	//显示追加保证金弹出框
	$('.addTradeMoney').live('click',function() {
		var type = $(this).attr("data-feeType");
		if(type == 0 || type == 1){
			showMsgDialog("提示","系统升级中，该功能暂停使用。");
			return;
		}
		PGB.openWindow("#addTradeMoneyBox",null);
	});
	
	$('.addTradeMonth').live('click',function() {
		var type = $(this).attr("data-feeType");
		if(type == 0 || type == 1){
			showMsgDialog("提示","系统升级中，该功能暂停使用。");
			return;
		}
		PGB.openWindow("#addTradeMonthBox",null);
	});
	
	//验证追加保证金金额
	$('#addTradeMoneyBox #sure').live('click',function() {
		addBondNext();
	});
	
	//追加保证金
	$('#addTradeMoneyAlert #sure').live('click',function() {
		var addMoney=Number($('#addMoney').val());
		var groupId=$('#group_id').val();
		if($(this).attr("status") == "true"){
			$(this).attr("status",false);
			addBond($(this),addMoney,groupId);
			
		}
	});
	function toNumber(str){
		var strs =str.split(",");
		var num="";
		for(var i=0;i<strs.length;i++){
			num+=strs[i];
		}
		return Number(num);
	}
	$('#addTradeMonthBox #sure1').live('click',function(){
		var month=$("#monthNum").val();
		var groupId=$('#group_id').val();
		var balance =$('#balance').val();
		var totalFee = $('#totalFee').text();
		totalFee=totalFee.substring(0,totalFee.length-1);
		if(toNumber(balance)<toNumber(totalFee)){
			showMsgDialog("提示","余额不足请充值");
			return ;
		}
		addMonth($(this),month,groupId);
	})
	$('#addTradeMoneyAlert #sure').live('click',function() {
		var addMoney=Number($('#addMoney').val());
		var groupId=$('#group_id').val();
		if($(this).attr("status") == "true"){
			$(this).attr("status",false);
			addBond($(this),addMoney,groupId);
		}
	});
	
	//提交终结方案申请
	$("#endOfProgramAlert #sure").live('click',function(){
		if($(this).attr("status") == "true"){
			$(this).attr("status",false);
			var groupId = $('#group_id').val();
			$.post(basepath + "trade/endOfProgram.json", {groupId:groupId,ajax:1}, function(result) {
				if (result.success) {
					$(this).attr("status",true);
					$(this).html('确定');
					PGB.closeWindow("#endOfProgramAlert",null);  //隐藏终结方案确认框
					clickCloseRefresh("提示","申请成功，我们会尽快处理。");
				} else {
					$(this).attr("status",true);
					$(this).html('确定');
					showMsgDialog("提示",result.message);
				}
			}, "json");	
		}
	});
	
	// 获取初始追加一个月的费用
	getMoney();
});

//验证追加保证金
var addBondNext = function() {
	var minAddMoney=Number($('#minAddMoney').val());
	var maxAddMoney=Number($('#maxAddMoney').val());
	var addMoney=Number($('#addMoney').val());
	var acountbalance=Number($('#balance').val());
	if(!addMoney){
		showMsgDialog("提示","请输入追加保证金金额。");
		return false;
	}
	if(!$.isNumeric(addMoney)){
		showMsgDialog("提示","请输入正确的金额。");
		return false;
	}
	var moneyreg=/^[0-9]+([.]{1}[0-9]{1,2})?$/;
	if(!moneyreg.test(addMoney)){
		showMsgDialog("提示","请输入正确的金额,最小金额为分。"); 
		return false; 
	}
	if(addMoney<minAddMoney){
		showMsgDialog("提示","追加金额最低"+minAddMoney+"元");
		$('#addMoney').val(minAddMoney);
		return false;
	}
	if(addMoney>maxAddMoney){
		showMsgDialog("提示","追加金额最高100万元。");
		$('#addMoney').val("");
		return false;
	}
	if(addMoney>acountbalance){
		showMsgDialog("提示","余额不足，请立即充值。");
		$('#addMoney').val("");
		return false;
	}
	PGB.closeWindow("#addTradeMoneyBox",null);  //隐藏追加保证金显示框
	PGB.openWindow("#addTradeMoneyAlert",null); //显示追加保证金确认弹出框
}
var addMonth=function(obj,addMonth,groupId){
	obj.html('添加中......');
	$.post(basepath + "/user/monthTrade/addMonth.json", {addMonth:addMonth,groupId:groupId,ajax:1}, function(result) {
		if (result.success) {
			PGB.closeWindow("#addTradeMonthAlert",null);
			obj.attr("status",true);
			obj.html('确定');
			clickCloseRefresh("提示",result.message);
		} else {
			showMsgDialog("提示",result.message);
			obj.attr("status",true);
			obj.html('确定');
		}
	}, "json");	
}
//追加保证金
var addBond= function(obj,addMoney,groupId){
	$.post(basepath + "trade/addBond.json", {addMoney:addMoney,groupId:groupId,ajax:1}, function(result) {
		if (result.success) {
			PGB.closeWindow("#addTradeMonthBox",null);
			obj.attr("status",true);
			obj.html('确定');
			clickCloseRefresh("提示","追加保证金申请成功，系统会尽快处理！");
		} else {
			showMsgDialog("提示",result.message);
			obj.attr("status",true);
			obj.html('确定');
		}
	}, "json");	
}

//终结方案
var stopBtnFun= function(feeType,groupId) {
	if(feeType == 0 || feeType == 1){
		showMsgDialog("提示","系统升级中，该功能暂停使用。");
		return;
	}
	//检测是否已经提交过终结方案申请
	$.ajax({url : basepath+"trade/checkEndOfProgram.json",async : false,data:{groupId:groupId,type:feeType,ajax:1}, type : "POST", dataType : "json",  
        success : function(result) {  
        	if(result.success) {
        		var tds = result.data.tradeDays;
        		if(2 == feeType) {
	        		if(result.data.tradeStatus == 'finalized') { //已终结
	        			clickCloseRefresh("提示","该方案已终结成功，不能重复终结！");
	        		} else if(result.data.tradeStatus == 'auditing') {  //终结中
	        			showMsgDialog("提示",'该方案正在“终止操盘”的审核状态，请耐心等待！');
	        		} else {  //操盘中
	        			endOfProgramAlert(feeType, groupId, tds);
	        		}
	        	} else {
	        		endOfProgramAlert(feeType, groupId, tds);
	        	}
			}else{
				showMsgDialog("提示",result.message);
			}
        }
	});
};

//显示终结方案申请确认框
var endOfProgramAlert = function(feeType, groupId, tds){
	var alertContent = "若您的股票账户仍有股票未卖出，您的终结申请将不能通过审核。确认终止操盘？";
	if(tds > 0){
		alertContent = "您还有"+tds+"天利息，中途终止操盘，剩余利息将不予退还。确认终止操盘？";
	}
	$("#endOfProgramAlert").find("#alertContent").html(alertContent); //设置提示信息
	
	PGB.openWindow("#endOfProgramAlert",null);
};

