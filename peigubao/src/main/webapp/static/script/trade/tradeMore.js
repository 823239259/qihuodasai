
$(document).ready(function(e) {
	$('#navlist a').removeClass('cur');
	$("#stockli").addClass("cur");
	
	var needinfo="账户余额不足,我要<a href='"+basepath+"pay/payinfo'>立即充值.</a>";

	if($('#user').val()=='off'){
		$('.cp_paymoney').hide();
		$('#need_next_pay').hide();	
	}
	if($('#need').val()=='off'){
		$('#needdiv').hide();	
	}
	if($('#needNext').val()=='off'){
		$('#need_next_pay').hide();	
	}	
	
	$("#sub").on("click",function(){
		var borrowPeriod=$('#borrowPeriod').val();
		var lever=$('#lever').val();
		var capitalMargin=$('#capitalMargin').val();
		var tradeStart=$('#tradeStart').val();
		var callback=function(){
			$.post(basepath + "check.json", {borrowPeriod:borrowPeriod,lever:lever,capitalMargin:capitalMargin,tradeStart:tradeStart,ajax:1}, function(result) {
				if (result.success) {
					if(result.data.need=='on'){
							$('.cp_paymoney').show();	
							$('#needdiv').show();
							$('#avl_bal').html(result.data.avlBal);
							$('#pay_enough').html(result.data.payEnough);
							$('#need_pay').html(result.data.needPay);
							$('#need').val(result.data.need);
						}else{
							$('.cp_paymoney').show();
							$('#needdiv').hide();
							$('#need_pay').html(result.data.needPay);
						}
					if(result.data.needNext=='on'){
						$('#need_next_pay').show();
						$('#needNext').val(result.data.needNext);
					}else{
						$('#need_next_pay').hide();
						$('#needNext').val(result.data.needNext);
					}
					}else {
						showMsgDialog("提示",result.message);
				}
			}, "json");
			
		}

		if(user.checkAndShowLogin(callback)){
			if($('#need').val()=='on'){
				showMsgDialog("提示",needinfo);
				return false;
			}else{
				var groupId=$('#groupId').val();
				var totalTrader=$('#totalTrader').val();
				var xcapitalMargin=$('#capitalMargin').val();

				$.post(basepath + "trade/addProgramCheck.json", {capitalMargin:xcapitalMargin,groupId:groupId,totalTrader:totalTrader,ajax:1}, function(result) {
					if (result.success) {
						$("#sub").text('操盘进行中......');
						$("form").submit();
					}else {
						showMsgDialog("提示",result.message);
					}
				}, "json");			
			}
		}	
	});
});