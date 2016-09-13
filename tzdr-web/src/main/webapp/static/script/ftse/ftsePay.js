$(document).ready(function(){
	$('.navlist li a').removeClass('on');
	$("#internationalFutures").addClass("on");
	
	var isShow = $("input[name='showAvl']").val();
	if(isShow == 1){
		$("#notPay").show();
	}
	
	$("#pay_button").on("click",function(){
//		var showAvl = $("input[name='showAvl']").val();
//		if(showAvl=='1'){
//			$("#notPay").show();
//			return false;
//		}else{
//			$("#confirmPayDiv").show();
//			return false;
//		}
		$("#confirmPayDiv").show();
		vocherSeleted();
		return false;
	});
	
	$("#voucher").change(vocherSeleted);
});
/*
 * 检测余额是否充足
 */
function checkBanlanceEnough() {
	var balance =parseFloat($("#banlance").text()); // 账户余额
	var payable = parseFloat($("#payable").text()); // 实际支付金额
	var content = "";
	if(balance < payable) {
		$("#notEnoughPay").show();
		$("#confirmPay").attr("status",true);
		//$("#confirmPay").attr("class","fl_pb_nosure");
		$("#confirmPay").text("去充值");
		bindPay((payable - balance));
		content = "你的账户余额只剩"+banlace+"元,本次充值支付还差"+banlace+"元";
	} else {
		bindConfirmPay();
		$("#notEnoughPay").hide();
		$("#confirmPay").attr("status",true);
		$("#confirmPay").text("确认支付");
		content = "你的账户余额只剩"+banlace+"元,本次充值支付完毕剩余"+banlace+"元";
	}
	$("#notEnoughPay").text(content);
}
/*
 * 代金券优惠
 */
function vocherSeleted(obj) {
	var payable = $("#payableFiexd").val(); // 应付金额
	var vocherText = $("#voucher").children("option:selected").text(); // 代金券金额
	var vocher = vocherText.substring(0, vocherText.indexOf("元代金券"));
	if("" == vocher) {
		$("#payable").text(parseFloat(payable).toFixed(2));
	} else {
		var payact = parseFloat(payable) - parseFloat(vocher);
		$("#payable").text((payact<0)?0.00:payact.toFixed(2));
	}
	// 检测余额是否充足
	checkBanlanceEnough();
}

function colseConfirmPayDiv() {
	$("#confirmPayDiv").hide();
};
/**
 * 绑定去充值事件
 */
function bindPay(banlace){
	$("#confirmPay").attr("href",basepath + "pay/payinfo?balance="+banlace);
}
/**
 * 绑定确认支付事件
 */
function bindConfirmPay(){
	$('#confirmPay').bind('click',function(){
		var status =  $(this).attr("status");
		$(this).attr("status",false);
		if(status == "true"){
			$("#confirmPayDiv").hide();
			$(this).attr("status",true);
			$("#voucherId").val($("#voucher").val());
			$('#forPaySubmit').click();
		}
	});
}
