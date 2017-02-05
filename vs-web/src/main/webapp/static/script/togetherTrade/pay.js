$(document).ready(function(e) {
	//顶部菜单位置
	$('.navlist li a').removeClass('on');
	$("#togetherli").addClass("on");
	if($('#need').val()=='on'){
		var needinfo="余额不足,<a target='_blank' href='"+basepath+"pay/payinfo'>请立即充值.</a>";
		$('#noNeedPay').html(needinfo);
		$('#noNeedPay').show();
	}
	if($('#need').val()=='off'&&$('#needNext').val()=='on'){
		var needinfo="您的余额不足以扣除下个交易日的账户管理费，<a target='_blank' href='"+basepath+"pay/payinfo'>请立即充值.</a>";
		$('#noNeedPay').html(needinfo);
		$('#noNeedPay').show();
	}
	if(Number($('#isVerified').val())==0){
		var needVerified="没有进行实名验证,我要<a href='"+basepath+"securityInfo/secInfo'>立即验证.</a>";
		$('#noNeedPay').html(needVerified);
		$('#noNeedPay').show();
	}
	$("#sub").on("click",function(){
		if($('#needNext').val()=='on'||$('#need').val()=='on'||Number($('#isVerified').val())==0){
		}else{
			$('#sure').removeAttr("style");
		}
		$('#paydiv').show();
		$('#fl_mask').show();
		
	});
	
	$("#cancel").on("click",function(){
		$('#paydiv').hide();
		$('#fl_mask').hide();
	});
	$("#cancelx").on("click",function(){
		$('#paydiv').hide();
		$('#fl_mask').hide();
	});
	$("#sure").on("click",function(){
		if($('#needNext').val()=='on'||$('#need').val()=='on'||Number($('#isVerified').val())==0){
			return false;
		}else{
			$("form").submit();
		}
	});
	
	
	
});






