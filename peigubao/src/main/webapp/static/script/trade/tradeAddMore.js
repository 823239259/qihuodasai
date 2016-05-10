$(document).ready(function(e) {
	
	$('#navlist a').removeClass('cur');
	$("#stockli").addClass("cur");

	var initTradeStart= function(){
		$.post(basepath + "isTradingMore.json", {}, function(result) {
		if (result.success) {
			var tday = $(".tday");
			var nday = $(".nday");
			if (result.data.isTradeDay) {
				tday.show();			
				nday.hide();
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
			$('#ksbcx').text($.formatMoney((pzje * 1.0867).toFixed()));
			$('#kspcx').text($.formatMoney((pzje * 1.06).toFixed()));
		} else if (pzgg ==7 ) {
			$('#ksbcx').text($.formatMoney((pzje * 1.0771).toFixed()));
			$('#kspcx').text($.formatMoney((pzje * 1.0529).toFixed()));
		} else if (pzgg ==8 ) {
			$('#ksbcx').text($.formatMoney((pzje * 1.07).toFixed()));
			$('#kspcx').text($.formatMoney((pzje * 1.0475).toFixed()));
		} else if (pzgg ==9 ) {
			$('#ksbcx').text($.formatMoney((pzje * 1.0644).toFixed()));
			$('#kspcx').text($.formatMoney((pzje * 1.0433).toFixed()));
		} else if (pzgg ==10 ) {
			$('#ksbcx').text($.formatMoney((pzje * 1.06).toFixed()));
			$('#kspcx').text($.formatMoney((pzje * 1.04).toFixed()));
		} else if (pzgg ==11 ) {
			$('#ksbcx').text($.formatMoney((pzje * 1.0682).toFixed()));
			$('#kspcx').text($.formatMoney((pzje * 1.0409).toFixed()));
		} else if (pzgg ==12 ) {
			$('#ksbcx').text($.formatMoney((pzje * 1.0625).toFixed()));
			$('#kspcx').text($.formatMoney((pzje * 1.0375).toFixed()));
		} else if (pzgg ==13 ) {
			$('#ksbcx').text($.formatMoney((pzje * 1.0577).toFixed()));
			$('#kspcx').text($.formatMoney((pzje * 1.0346).toFixed()));
		} else if (pzgg ==14 ) {
			$('#ksbcx').text($.formatMoney((pzje * 1.0536).toFixed()));
			$('#kspcx').text($.formatMoney((pzje * 1.0321).toFixed()));
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
	
	var borrow=function(endDayString,tradeStart){
		$.post(basepath + "getMoreBorrowPeriod.json", {endDayString:endDayString,tradeStart:tradeStart}, function(result) {
			if (result.success) {
				$("#use_day").attr("readonly",false);
				var borrowPeriod=result.data.borrowPeriod;
				$("#use_day").val(borrowPeriod);
				$("#use_day").attr("readonly",true);
				$("#use_day_lable").html(result.data.borrowPeriod);
				getData(borrowPeriod);
			} else {
				showMsgDialog("提示",result.message);
			}
		}, "json");
	}
	
	$('.nday').click(function() {
		var endDayString = $('#end_day').html();
		borrow(endDayString,1);	
	});
	$('.tday').click(function() {
		var endDayString = $('#end_day').html();
		borrow(endDayString,0);
	});
	
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

	
	var tzMaxMoney=Number($("#moreMaxMoney").val());
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
	
	var getAllData=function(){
		var pzbzjTemp = $.isNumeric(getIntMoney())?getIntMoney():0;
		if(pzbzjTemp>=minMoney){
		moneyChange();
		var pzbzj = $.isNumeric(getIntMoney())?getIntMoney():0;
		$('#capitalMargin').val(pzbzj);
		
		var pzgg = Number($("#pzgg").val());
		var pzje = pzbzj * pzgg;
		var zcpzj = pzje + pzbzj;
		$('#pzje').text($.formatMoney(Number(pzje)));
		$('#zcpzj').text($.formatMoney(Number(zcpzj)))
		operatorsInfo(pzje);
		getData();
		risk(pzgg, pzje);	
		}
	}
	$("#tz").keypress(T.numKeyPress).keyup(function() {
		getAllData($("#pzgg").val());
		onTz();
		onLerver();
		onDay();
		$(this).focus();
	});
	
	$('#margin ul li').click(function() {
		$('#margin ul li.on').removeClass('on');
		$(this).addClass('on');
		$("#tz").val($.formatMoney(Number($(this).attr('data'))));	
		getAllData($("#pzgg").val());
		
	});

	function getData(day) {
		var borrowPeriod = Number($('#use_day').val());
		if(day){
			borrowPeriod = day;
		}
		var capitalMargin = $.isNumeric(getIntMoney())?getIntMoney():0;
		var lever = Number($('#pzgg').val());	
		var tradeStart = typeof($("input[name='tradeStart']:checked").val()) == "undefined"?1:$("input[name='tradeStart']:checked").val();
		if(capitalMargin>=100){
		$.post(basepath + "data.json", {
			borrowPeriod : borrowPeriod,
			lever : lever,
			capitalMargin : capitalMargin,
			tradeStart:tradeStart
		}, function(result) {
			if (result.success) {
				$("#lx").html(($.formatMoney(Number(result.data.interestFee*borrowPeriod).toFixed(2))));
				$("#pzglf").html($.formatMoney(Number(result.data.manageFee)));
			} else {
				showMsgDialog("提示",result.message);
			}
		}, "json");
		}
	}

	$("#submit").on("click",function(){
		// showMsgDialog("提示","追加操盘方案暂时关闭。");
		// return false;
		var groupId=$('#groupId').val();
		var xcapitalMargin=$.isNumeric(getIntMoney())?getIntMoney():0;
	    if (xcapitalMargin<100){
		   showMsgDialog("提示","请输入您的操盘保证金，最少100元");
		   return false;
	    }
	    
	    if(!$("#agree").attr("checked")){ 
	    	showMsgDialog("提示","请阅读并同意《借款协议》。");
	    	return false;
	    }
		var pzgg = Number($("#pzgg").val());
		var pzje = xcapitalMargin * pzgg;
		var totalTrader = pzje + xcapitalMargin;
		
		$.post(basepath + "trade/addProgramCheck.json", {capitalMargin:xcapitalMargin,groupId:groupId,totalTrader:totalTrader,ajax:1}, function(result) {
			if (result.success) {
				$("#sub").submit();
			}else {
				showMsgDialog("提示",result.message);
			}
		}, "json");		
	});
	
	

	if($('#capitalMargin').val()!=0){
		$("#tz").val($.formatMoney(Number($('#capitalMargin').val())));	
		var start=$('#backTradeStart').val();
		if(start==0){
		$("input[name='tradeStart'][value=0]").attr("checked",true);
		$(".tday").show();
		$(".nday").hide();	
		}else{
		$(".tday").hide();
		$(".nday").show();	
		$("input[name='tradeStart'][value=1]").attr("checked",true); 	
		}	
		getAllData();
		onTz();
		onLerver();
		onDay();
	}else{
		$("#tz").val($.formatMoney(Number($('#margin ul li.on').attr('data'))));
		var capitalMargin = $.isNumeric(getIntMoney())?getIntMoney():0;
		$('#capitalMargin').val(capitalMargin);
		initTradeStart();
		getAllData();
		onTz();
		onLerver();
		onDay();
	}
	
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
	
});