$(document).ready(function(e) {
	
	var maxActivityDay=Number($("#maxActivityDay").val());
	
	    $('.fl_cpstep2 a').click(function() {  
	    	if($("#activity_margin").val()==""||Number($("#activity_margin").val())!=600){
				$.alertTip($("#activity_margin"),"只可以输入600元操盘保证金 !");
				return;
			}else{
				$('.fl_cpstep3').show();
		        $('.fl_cpstep2 p').hide();	
			}	        
	    });
	    $('.fl_cpstep3 a').click(function() {  
	    	if($("#activity_lever").val()==""||Number($("#activity_lever").val())!=10){
				$.alertTip($("#activity_lever"),"只可以输入10倍放大倍数 !");
				return;
			}else{
				 $('.fl_cpstep4').show();
			     $('.fl_cpstep3 p').hide();
			}	   	      
	    });
	    $('.fl_cpstep4 a').click(function() {
	    	if($("#activity_day").val()==""||Number($("#activity_day").val())>maxActivityDay){
				$.alertTip($("#activity_day"),"只可以输入小于"+maxActivityDay+"天的操盘天数 !");
				return;
			}else{
				$('.fl_cpstep5').show();
			    $('.fl_cpstep4 p').hide();
			}	   	       
	    });
	    $('.fl_cpstep5 a').click(function() {        
	        $('.fl_cpstep6').show();
	        $('.fl_cpstep5 p').hide();
	    });
	    $('.fl_cpstep6 a').click(function() {        
	    	if($("#activity_margin").val()==""||Number($("#activity_margin").val())!=600){
				$.alertTip($("#sub_margin"),"只可以输入600元操盘保证金 !");
				return;
			}
	    	if($("#activity_lever").val()==""||Number($("#activity_lever").val())!=10){
				$.alertTip($("#sub_margin"),"只可以输入10倍放大倍数 !");
				return;
			}
	    	if($("#activity_day").val()==""||Number($("#activity_day").val())>maxActivityDay){
				$.alertTip($("#sub_margin"),"只可以输入小于"+maxActivityDay+"天的操盘天数 !");
				return;
			}
	    	$("#submit").click();
	    });
	
	$('.navlist li a').removeClass('on');
	$("#stockli").addClass("on");
	
	

	var initTradeStart= function(){
		$.post(basepath + "isTrading.json", {}, function(result) {
		if (result.success) {
			var tday = $(".tday");
			var nday = $(".nday");
			if (result.data.isTradeDay) {
				tday.show();			
				nday.show();
				tday.click();
			} else {
				tday.hide();
				nday.show();
				nday.click();			
			}
		} else {
			showMsgDialog("提示",result.message);
		}
	}, "json");
	}
	
	
	
	var endday=function(borrowPeriod,tradeStart){
		$.post(basepath + "endDay.json", {borrowPeriod:borrowPeriod,tradeStart:tradeStart}, function(result) {
			if (result.success) {
				$("#end_day").html(result.data.endDay);
			} else {
				showMsgDialog("提示",result.message);
			}
		}, "json");
	}
	
	$('.nday').click(function() {
		var borrowPeriod = Number($('#use_day').val());
		endday(borrowPeriod,1);
	});
	$('.tday').click(function() {
		var borrowPeriod = Number($('#use_day').val());
		endday(borrowPeriod,0);
	});
	
	
	var initStart= function(){
		$.post(basepath + "isTrading.json", {}, function(result) {
		if (result.success) {
			var ttday = $(".ttday");
			var nnday = $(".nnday");
			if (result.data.isTradeDay) {
				ttday.show();			
				nnday.show();
				nnday.click();
			} else {
				ttday.hide();
				nnday.show();
				nnday.click();			
			}
		} else {
			showMsgDialog("提示",result.message);
		}
	}, "json");
	}
	
	$('.nnday').click(function() {
		$('.nday').click();
	});
	$('.ttday').click(function() {
		$('.tday').click();
	});


	$('.uc_ppbtn').hover(function() {
		$('.uc_pay_promt1').show();
	}, function() {
		$('.uc_pay_promt1').hide();
	});
	$('.uc_ppbtn2').hover(function() {
		$('.uc_pay_promt2').show();
	}, function() {
		$('.uc_pay_promt2').hide();
	});
	$('.uc_ppbtn3').hover(function() {
		$('.uc_pay_promt3').show();
	}, function() {
		$('.uc_pay_promt3').hide();
	});

	function risk(pzgg, pzje) {
		if (1 <= pzgg && pzgg <= 5) {
			$('#ksbcx').text($.formatMoney((pzje * 1.1).toFixed()));
			$('#kspcx').text($.formatMoney((pzje * 1.07).toFixed()));
		} else if (pzgg ==6 ) {
			$('#ksbcx').text($.formatMoney((pzje * 1.09).toFixed()));
			$('#kspcx').text($.formatMoney((pzje * 1.06).toFixed()));
		} else if (pzgg ==7 ) {
			$('#ksbcx').text($.formatMoney((pzje * 1.08).toFixed()));
			$('#kspcx').text($.formatMoney((pzje * 1.05).toFixed()));
		} else if (pzgg ==8 ) {
			$('#ksbcx').text($.formatMoney((pzje * 1.07).toFixed()));
			$('#kspcx').text($.formatMoney((pzje * 1.05).toFixed()));
		} else if (pzgg ==9 ) {
			$('#ksbcx').text($.formatMoney((pzje * 1.06).toFixed()));
			$('#kspcx').text($.formatMoney((pzje * 1.04).toFixed()));
		} else if (pzgg ==10 ) {
			$('#ksbcx').text($.formatMoney((pzje * 1.06).toFixed()));
			$('#kspcx').text($.formatMoney((pzje * 1.04).toFixed()));
		} else if (pzgg ==11 ) {
			$('#ksbcx').text($.formatMoney((pzje * 1.07).toFixed()));
			$('#kspcx').text($.formatMoney((pzje * 1.04).toFixed()));
		} else if (pzgg ==12 ) {
			$('#ksbcx').text($.formatMoney((pzje * 1.06).toFixed()));
			$('#kspcx').text($.formatMoney((pzje * 1.04).toFixed()));
		} else if (pzgg ==13 ) {
			$('#ksbcx').text($.formatMoney((pzje * 1.06).toFixed()));
			$('#kspcx').text($.formatMoney((pzje * 1.03).toFixed()));
		} else if (pzgg ==14 ) {
			$('#ksbcx').text($.formatMoney((pzje * 1.05).toFixed()));
			$('#kspcx').text($.formatMoney((pzje * 1.03).toFixed()));
		} else if (pzgg ==15 ) {
			$('#ksbcx').text($.formatMoney((pzje * 1.05).toFixed()));
			$('#kspcx').text($.formatMoney((pzje * 1.03).toFixed()));
		}
	}

	function operatorsInfo(zcpzj) {
		if (zcpzj <= 100000) {
			$('#ksbcx').removeClass('cp_ru_ctn2');
			$('#notify').html('投资沪深A股，仓位不限制。');
		} else if (100000 < zcpzj && zcpzj <= 500000) {
			$('#notify').addClass('cp_ru_ctn2');
			$('#notify').html('投资沪深A股，仓位有限制，单股不超总操盘资金的70%。');
		}else if (500000 < zcpzj && zcpzj <= 1000000) {
			$('#notify').addClass('cp_ru_ctn2');
			$('#notify').html('投资沪深A股，仓位有限制，单股不超总操盘资金的50%。');
		} else if (1000000 < zcpzj) {
			$('#notify').addClass('cp_ru_ctn2');
			$('#notify').html('投资沪深A股，仓位有限制，单股不超总操盘资金的50%(创业板33%)。');
		}
	}

	
	
	var tzMaxMoney=Number($('#max').val());
	var minMoney=100;
	function getMoney(){
		return $.trim($("#tz").val()).replace(/\,/g,"");
	}
	function getIntMoney(){
		var money = getMoney();
		return /^\d+$/.test($.trim(money))?parseInt(money,10):0;
	}

	function moneyChange(){
		var money = getMoney();
		if(/^\d+$/.test(money)){
			var moneyInt = getIntMoney();
			if(moneyInt>tzMaxMoney){
				$("#tz").val($.formatMoney(tzMaxMoney));
			}else{
				$("#tz").val($.formatMoney(moneyInt));
			}
		}else{
			$("#tz").val("");
		}
		return true;
	}

	var maxday = 180;
	var minday = 1;
	function getDay() {
		return $.trim($("#use_day").val()).replace(/\,/g, "");
	}
	function getIntDay() {
		var day = getDay();
		return /^\d+$/.test($.trim(day)) ? parseInt(day, 10) : 0;
	}

	function dayChange() {
		var day = getDay();
		if (/^\d+$/.test(day)) {
			var day = getIntDay();
			if (day > maxday) {
				$("#use_day").val(maxday);
			} else if (day < minday) {
				$("#use_day").val(minday);
			}
		} else {
			$("#use_day").val("");
		}
	}
	
	function getPzgg() {
		return $.trim($("#pzgg").val()).replace(/\,/g, "");
	}
	function getIntPzgg() {
		var pzggInt = getPzgg();
		return /^\d+$/.test($.trim(pzggInt)) ? parseInt(pzggInt, 10) : 0;
	}

	function pzggChange() {
		var minPzgg =  1;
		var maxPzgg = $('#max_lever').html()==''?Number($('#min_lever').html()): Number($('#max_lever').html());	
		var gg = getIntPzgg();
		if (/^\d+$/.test(gg)) {
			var gzgg = getIntPzgg();
			if (gzgg > maxPzgg) {
				$("#pzgg").val(maxPzgg);
			} else if (gzgg < minPzgg) {
				$("#pzgg").val(minPzgg);
			}
		} else {
			$("#pzgg").val("");
		}
	}


	var maxMoney = Number($('#max').val());
	var minPzjeMoney = Number(1500);
	var getAllData=function(gg){
		var pzbzjTemp = $.isNumeric(getIntMoney())?getIntMoney():0;
		
		if(pzbzjTemp>=minMoney){
		moneyChange();
		var pzbzj = $.isNumeric(getIntMoney())?getIntMoney():0;
		$('#capitalMargin').val(pzbzj);
		
		var minpzgg = Math.ceil(minPzjeMoney / pzbzj);
		if(minpzgg<=1){
			minpzgg =1
		}
		var maxpzgg = parseInt(maxMoney / pzbzj, 10);
		if (maxpzgg >= 15) {
			maxpzgg = 15;
		}
		if(minpzgg==maxpzgg){
			$('#min_lever').html(minpzgg);
			$('#link').html('');	
			$('#max_lever').html('');	
		}else{
			$('#min_lever').html(minpzgg);
			$('#link').html('-');	
			$('#max_lever').html(maxpzgg);	
		}
		var pzgg = Number($("#pzgg").val());
		if(gg<=maxpzgg){
			pzgg=gg;
			$("#pzgg").val(gg);
		}else{
			pzgg=maxpzgg;
			$("#pzgg").val(maxpzgg);
		}
		
		var pzje = pzbzj * pzgg;
		var zcpzj = pzje + pzbzj;
		$('#pzje').text($.formatMoney(Number(pzje)));
		$('#zcpzj').text($.formatMoney(Number(zcpzj)))	
		operatorsInfo(pzje);
		getData();
		risk(pzgg, pzje);	
		}
	}
	
	

	function getData() {
		var borrowPeriod = Number($('#use_day').val());
		var capitalMargin = $.isNumeric(getIntMoney())?getIntMoney():0;
		var lever = Number($('#pzgg').val());
		var tradeStart = typeof($("input[name='tradeStart']:checked").val()) == "undefined"?1:$("input[name='tradeStart']:checked").val();
		if(capitalMargin>=100&&borrowPeriod>=2&&lever>=1){
		$.post(basepath + "data.json", {
			borrowPeriod : borrowPeriod,
			lever : lever,
			capitalMargin : capitalMargin,
			tradeStart:tradeStart
		}, function(result) {
			if (result.success) {
				$("#end_day").html(result.data.endDay);
				$("#lx").html(($.formatMoney(Number(result.data.interestFee*borrowPeriod).toFixed(2))));
				$("#pzglf").html($.formatMoney(Number(result.data.manageFee)));
			} else {
				showMsgDialog("提示",result.message);
			}
		}, "json");
		}
	}
	var onTz = function (){
		var capitalMoney=getMoney();
		$('#margin ul li').each(function(i){
			if($(this).attr('data')==capitalMoney){
				$(this).addClass('on');
			 }else{
				$(this).removeClass('on'); 
			 }
		});
	}
	
	var onLerver = function (){
		var lever=Number($("#pzgg").val());
		$('#capital_lever ul li').each(function(i){
			if($(this).attr('data')==lever){
				$(this).addClass('on');
			 }else{
				$(this).removeClass('on'); 
			 }
		});
	}
	
	var onDay = function (){
		var onDay=Number($("#use_day").val());
		$('#match_days ul li').each(function(i){
			if($(this).attr('data')==onDay){
				$(this).addClass('on');
			 }else{
				$(this).removeClass('on'); 
			 }
		});
	}
	
	var onChangeFun = function (){
		onTz();
		onLerver();
		onDay();
	}
	
	$("#tz").keypress(T.numKeyPress).keyup(function() {	
		getAllData($("#pzgg").val());
		onChangeFun();
		$(this).focus();
	});
	
	$("#pzgg").keypress(T.numKeyPress).keyup(function() {
		pzggChange();
		getAllData($("#pzgg").val());
		onChangeFun();
		$(this).focus();
	});

	$("#use_day").keypress(T.numKeyPress).keyup(function() {
		dayChange();
		getData();
		onChangeFun();
		$(this).focus();
	});
	
	$("#activity_margin").keypress(T.numKeyPress).keyup(function() {
		$("#tz").val($("#activity_margin").val());
		getAllData($("#pzgg").val());
		onChangeFun();
		$(this).focus();
	});
	
	$("#activity_lever").keypress(T.numKeyPress).keyup(function() {
		$("#pzgg").val($("#activity_lever").val());
		pzggChange();
		getAllData($("#pzgg").val());
		onChangeFun();
		$(this).focus();
	});

	$("#activity_day").keypress(T.numKeyPress).keyup(function() {
		$("#use_day").val($("#activity_day").val());
		dayChange();
		getData();
		onChangeFun();
		$(this).focus();
	});
	
	$('#margin ul li').click(function() {
		$('#margin ul li.on').removeClass('on');
		$(this).addClass('on');
		$("#tz").val($.formatMoney(Number($(this).attr('data'))));	
		getAllData($("#pzgg").val());
		
	});
	
	$('#capital_lever ul li').click(function() {
		$('#capital_lever ul li.on').removeClass('on');
		$(this).addClass('on');
		$("#pzgg").val(Number($(this).attr('data')));
		getAllData($("#pzgg").val());
	});
	
	$('#match_days ul li').click(function() {
		$('#match_days ul li.on').removeClass('on');
		$(this).addClass('on');
		$("#use_day").val(Number($(this).attr('data')));
		dayChange();
		getData();
	});

	$("#submit").on("click",function(){
		if($("#agree").attr("checked")){
			var pzbzjTemp = $.isNumeric(getIntMoney())?getIntMoney():0;
			if(pzbzjTemp>=100){ 
				var no=Number($("#use_day").val());
				if(no<=maxActivityDay&&no>=2){
					var minPzgg = Number($('#min_lever').html());
					var maxPzgg = $('#max_lever').html()==''?Number($('#min_lever').html()): Number($('#max_lever').html());
					var inputgg=$('#pzgg').val()
					if(inputgg>=minPzgg&&inputgg<=maxPzgg){
						$("form").submit();	
					}else{
					showMsgDialog("提示",$("#lever_notify").html());
					}
				}else{
					showMsgDialog("提示","资金使用期限为2-"+maxActivityDay+"天");
				}
			}else{
				showMsgDialog("提示","请输入您的操盘保证金，最少100，最多600万。");
			}
		}else{
			showMsgDialog("提示","请阅读并同意《借款协议》。");
		}		
	});

	
		

	
	  // 提示框
    $('.cp_sdfont').each(function() {
        $('.uc_pay_promt').hide();
        var promtbtn = $(this);
        promtbtn.children('label').children('a').hover(function() {
            promtbtn.find('.uc_pay_promt ').show();
        }, function() {
            
            promtbtn.find('.uc_pay_promt ').hide();
        });
    });
    
	getAllData($("#pzgg").val());
	initTradeStart();
	initStart();
	onChangeFun();
});