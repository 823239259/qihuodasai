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
	var balance = $("#banlance").text(); // 账户余额
	var payable = $("#payable").text(); // 实际支付金额
	if(parseFloat(balance) < parseFloat(payable)) {
		$("#notEnoughPay").show();
		$("#confirmPay").attr("status",false);
		$("#confirmPay").attr("class","fl_pb_nosure");		
	} else {
		$("#notEnoughPay").hide();
		$("#confirmPay").attr("status",true);
	}
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

$('#confirmPay').live('click',function(){
	var status =  $(this).attr("status");
	$(this).attr("status",false);
	if(status == "true"){
		$("#confirmPayDiv").hide();
		$(this).attr("status",true);
		$("#voucherId").val($("#voucher").val());
		$('#forPaySubmit').click();
	}
});