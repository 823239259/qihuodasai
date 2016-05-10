$(document).ready(function(){
	$('.navlist li a').removeClass('on');
	$("#productli").addClass("on");
	
	var isShow = $("input[name='showAvl']").val();
	if(isShow == 1){
		$("#notPay").show();
	}
	
	$("#pay_button").on("click",function(){
		var showAvl = $("input[name='showAvl']").val();
		if(showAvl=='1'){
			$("#notPay").show();
			return false;
		}else{
			$("#confirmPayDiv").show();
			return false;
		}
	});
});

function colseConfirmPayDiv() {
	$("#confirmPayDiv").hide();
};

$('#confirmPay').live('click',function(){
	var status =  $(this).attr("status");
	$(this).attr("status",false);
	if(status == "true"){
		$("#confirmPayDiv").hide();
		$(this).attr("status",true);
		$('#forPaySubmit').click();
	}
});