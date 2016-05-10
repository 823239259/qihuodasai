$(document).ready(function(){
	$('.navlist li a').removeClass('on');
	$("#productli").addClass("on");
	
	
	
	//单手保证金【人民币】
	var traderBaseBond;
	//单手操盘金额【人民币】
	var traderBaseMoney;
	//单手操盘亏损保留金额【人民币】
	var traderBaseLineLoss;
	//总保证金
	var totalTraderBond;
	//总操盘金
	var totalTradeMoney;
	//总平仓线
	var totalLineLoss;
	
	//可开仓手数
	var tranLever;
	
	
	
	$.easyui.rechangeSetValue(".zk_ul_futrue_tranLever li","#input_tranLever",function(){
		
		traderBaseBond = $("#inputTraderBaseBond").val() - 0;
		traderBaseMoney = $("#inputTraderBaseMoney").val() - 0;
		traderBaseLineLoss = $("#inputTraderBaseLineLoss").val() - 0;
		tranLever = $("#input_tranLever").val() - 0;
		tranFees = $("#tranFees_id").val() - 0;
		
		 $.each($("input[name=tranLever]"),function(n,value) {  
    			if(tranLever == $(this).val()){
    				tranFees = $("input[name=tranFees]").eq(n).val();
    			}
	     }); 
		totalTraderBond = traderBaseBond*tranLever;
		totalTradeMoney = traderBaseMoney*tranLever;
		totalLineLoss = totalTradeMoney-totalTraderBond+traderBaseLineLoss*tranLever;
		
		$("#totalTraderBond_id").html($.formatMoney(Number(totalTraderBond))+" ");
		$("#totalTradeMoney_id").html($.formatMoney(Number(totalTradeMoney))+" ");
		$("#lineLoss_id").html($.formatMoney(Number(totalLineLoss))+" ");
		$("#tranFees_id").html($.formatMoney(Number(tranFees))+" ");
		$("#input_tranFees").val($.formatMoney(Number(tranFees))+" ");
	});
	$(".zk_ul_futrue_tranLever li")[0].click();
	
	
});




function submitSetting(type) {
	if ($("#checkbox_agree").attr("checked")) {
		if(!isLoginSSO){
			if(type == 1){
				window.location.href=basepath+"/toProductGoldIndexSSO"; 
			}else if(type == 2){
				window.location.href=basepath+"/toProductSliverIndexSSO"; 
			}else if(type == 3){
				window.location.href=basepath+"/toProductCopperIndexSSO"; 
			}else if(type == 4){
				window.location.href=basepath+"/toProductRubberIndexSSO"; 
			}
		}else{ 
    		$.easyui.submitForm("settingForm");
		} 
	}
	else {
		if(type == 1){
			showMsgDialog("提示","请勾选\"我已阅读并同意《沪金期货操盘合作协议》\""); 
		}else if(type == 2){
			showMsgDialog("提示","请勾选\"我已阅读并同意《沪银期货操盘合作协议》\""); 
		}else if(type == 3){
			showMsgDialog("提示","请勾选\"我已阅读并同意《沪铜期货操盘合作协议》\"");
		}else if(type == 4){
			showMsgDialog("提示","请勾选\"我已阅读并同意《橡胶期货操盘合作协议》\"");
		}
		
		return false;
	}
};
