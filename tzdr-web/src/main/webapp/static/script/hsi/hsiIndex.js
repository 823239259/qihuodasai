$(document).ready(function(){
	$('.navlist li a').removeClass('on');
	$("#internationalFutures").addClass("on");
	//保证金【人民币】
	var traderBond;
	//操盘金额【美元】
	var traderMoney;
	//操盘亏损平仓线【美元】
	var traderLineLoss;
	//操盘亏损平仓线【美元】
	var feeManage;
	//操盘亏损平仓线【美元】
	var tranFees;
	
	//可开仓手数
	var tranLever;

	$.easyui.rechangeSetValue(".zk_ul_futrue_tranLever li","#input_tranLever",function(e){

		var $input = $(e).find("input");
		
		traderBond = $input.attr("data_traderBond") - 0;
		traderMoney = $input.attr("data_traderMoney") - 0;
		traderLineLoss = $input.attr("data_lineLoss") - 0;
		feeManage = $input.attr("data_feeManage") - 0;
		tranFees = $input.attr("data_tranFees") - 0;
		tranLever = $input.val() - 0;

		$("#input_traderBond").val(traderBond);
		$(".zk_ul_futrue_traderBond").find("input").val(traderBond/tranLever);
		$(".zk_ul_futrue_traderBond").find("p").find("i").html($.formatMoney(Number(traderBond/tranLever)));
		$("#totalTraderBond_id").html($.formatMoney(Number(traderBond))+" ");
		$("#totalTradeMoney_id").html($.formatMoney(Number(traderMoney))+" ");
		if(feeManage > 0){
			$("#feeManage_id").parent().html("<i id='feeManage_id'>"+$.formatMoney(Number(feeManage))+"</i>元/交易日，14:00前即日计费，14:00后次日计费(以操盘账户短信发送时间为准)");
		}else{
			$("#feeManage_id").parent().html("<i id='feeManage_id'>免费</i>");
		}
		$("#lineLoss_id").html($.formatMoney(Number(traderLineLoss))+" ");
		$("#tranFees_id").html($.formatMoney(Number(tranFees))+" ");
	});
	
	$(".zk_ul_futrue_tranLever li")[0].click();
});

function submitSetting() {
	if ($("#checkbox_agree").attr("checked")) {
		if(!isLoginSSO){
			window.location.href=basepath+"/toHSIIndexSSO"; 
		}else{ 
    		$.easyui.submitForm("settingForm");
		} 
	}
	else {
		showMsgDialog("提示","请勾选\"我已阅读并同意《恒生指数操盘合作协议》\"");
		return false;
	}
};

function tradeContract(){
	var htmlHeight = 800;  //网页高度
	var htmlWidth = 1221;  //网页宽度
	var iTop = (window.screen.height-30-htmlHeight)/2; //获得窗口的垂直位置;  
	var iLeft = (window.screen.width-10-htmlWidth)/2;  //获得窗口的水平位置;  
	window.open(basepath+'tradeContract','恒生指数操盘合作协议','height='+htmlHeight+',innerHeight='+htmlHeight+',width='+htmlWidth+',innerWidth='+htmlWidth+',top='+iTop+',left='+iLeft+',toolbar=no,menubar=no,scrollbars=yes,resizeable=no,location=no,status=no');  
};