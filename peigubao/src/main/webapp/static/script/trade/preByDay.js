$(document).ready(function(e) {

	 // 最大融资倍数提示框js
    var b = $('.fl_mpromtfont').height();
    var h = b/3;
    $('.fl_mpromtfont').css('marginTop', -h);
    $('.fl_moneypromt').children('a').hover(function() {
        $('.fl_mpromtfont').slideDown();
    }, function() {        
        $('.fl_mpromtfont').slideUp();
    });
	
	$('#navlist a').removeClass('cur');
	$("#stockli").addClass("cur");

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
		if($.isNumeric($('#use_day').val())){
		var borrowPeriod = Number($('#use_day').val());
		endday(borrowPeriod,1);
		}
	});
	$('.tday').click(function() {
		if($.isNumeric($('#use_day').val())){
		var borrowPeriod = Number($('#use_day').val());
		endday(borrowPeriod,0);
		}
	});



	function risk(pzgg, pzje) {
		if (1 <= pzgg && pzgg <= 5) {
			$('#ksbcx').text($.formatMoney((pzje * 1.1).toFixed()));
			$('#kspcx').text($.formatMoney((pzje * 1.07).toFixed()));
		} else if (pzgg ==6 ) {
			$('#ksbcx').text($.formatMoney((pzje * 1.0867).toFixed()));
			$('#kspcx').text($.formatMoney((pzje * 1.06).toFixed()));
		} else if (pzgg ==7 ) {
			/*$('#ksbcx').text($.formatMoney((pzje * 1.0771).toFixed()));
			$('#kspcx').text($.formatMoney((pzje * 1.0529).toFixed()));*/
			$('#ksbcx').text($.formatMoney((pzje * 1.0929).toFixed()));
			$('#kspcx').text($.formatMoney((pzje * 1.0571).toFixed()));
		} else if (pzgg ==8 ) {
			/*$('#ksbcx').text($.formatMoney((pzje * 1.07).toFixed()));
			$('#kspcx').text($.formatMoney((pzje * 1.0475).toFixed()));*/
			$('#ksbcx').text($.formatMoney((pzje * 1.0875).toFixed()));
			$('#kspcx').text($.formatMoney((pzje * 1.0563).toFixed()));
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

	
	
	var tzMaxMoney=Number($('#max').val());
	var minMoney=300;
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
		var gg = getPzgg();
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
		moneyChange();
		var capitalMargin=getIntMoney();
		$.post(basepath + "maxLever.json", {		
			capitalMargin : capitalMargin
		}, function(result) {
			if (result.success) {				
				var pzbzjTemp = $.isNumeric(getIntMoney())?getIntMoney():0;	
				if(pzbzjTemp>=minMoney){
				var pzbzj = $.isNumeric(getIntMoney())?getIntMoney():0;
				$('#capitalMargin').val(pzbzj);
				
				var minpzgg = Math.ceil(minPzjeMoney / pzbzj);
				if(minpzgg<=1){
					minpzgg =1
				}
				var maxpzgg = Number(result.obj);
				if (maxpzgg >= 5) {
					maxpzgg = 5;
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
				if($.isNumeric(gg)){
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
				if($.isNumeric($('#use_day').val())){
					getData();
				}
				risk(pzgg, pzje);	
				}
				}
			} else {
				showMsgDialog("提示",result.message);
			}
		}, "json");
		
	}
	
	

	function getData() {
		var borrowPeriod = Number($('#use_day').val());
		var capitalMargin = $.isNumeric(getIntMoney())?getIntMoney():0;
		var lever = Number($('#pzgg').val());
		var tradeStart = typeof($("input[name='tradeStart']:checked").val()) == "undefined"?1:$("input[name='tradeStart']:checked").val();
		if(capitalMargin>=300&&borrowPeriod>=2&&lever>=1){
		if(capitalMargin*lever>=minPzjeMoney){
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
	
	$("#tz").keypress(T.numKeyPress).keyup(function() {
		getAllData($("#pzgg").val());
		onTz();
		onLerver();
		onDay();
		showLever();
		$(this).focus();
	}).focus(function() {
		if($(this).val()==''){
			$("#tz").removeClass("font_size_15").addClass("font_size_22");	
			$(this).val('');
			}
		$(this).css("ime-mode","disabled");
	}).blur(function(){   
		var moneyInt = getIntMoney();    
		if (moneyInt<minMoney){
			$("#tz").val($.formatMoney(minMoney));
			getAllData($("#pzgg").val());
			onTz();
			onLerver(); 
			onDay();
			showLever();
		}
	});;
	
	$("#pzgg").keypress(T.numKeyPress).keyup(function() {
		pzggChange();
		getAllData($("#pzgg").val());
		onTz();
		onLerver();
		onDay();
		$(this).focus();
	}).focus(function() {
		if($(this).val()==''){
			$("#pzgg").removeClass("font_size_15").addClass("font_size_22");	
			$(this).val('');
			}
		$(this).css("ime-mode","disabled");
	});

	$("#use_day").keypress(T.numKeyPress).keyup(function() {
		dayChange();
		getData();
		onTz();
		onLerver();
		onDay();
		$(this).focus();
	}).focus(function() {
		if($(this).val()==''){
			$("#use_day").removeClass("font_size_15").addClass("font_size_22");	
			$(this).val('');
			}
		$(this).css("ime-mode","disabled");
	});
	
	$('#margin ul li').click(function() {
		$("#tz").removeClass("font_size_15").addClass("font_size_22");	
		$('#margin ul li.on').removeClass('on');
		$(this).addClass('on');
		$("#tz").val($.formatMoney(Number($(this).attr('data'))));	
		getAllData($("#pzgg").val());
		showLever();
		
	});
	
/*	$('#capital_lever ul li').click(function() {
		$("#pzgg").removeClass("font_size_15").addClass("font_size_22");	
		$('#capital_lever ul li.on').removeClass('on');
		$(this).addClass('on');
		$("#pzgg").val(Number($(this).attr('data')));
		getAllData($("#pzgg").val());
	});*/
	
	$('#match_days ul li').click(function() {
		$("#use_day").removeClass("font_size_15").addClass("font_size_22");	
		$('#match_days ul li.on').removeClass('on');
		$(this).addClass('on');
		$("#use_day").val(Number($(this).attr('data')));
		dayChange();
		getData();
	});

	$("#submit").on("click",function(){
		var T_pzbzj = $.isNumeric(getIntMoney())?getIntMoney():0;
		var T_pzgg = Number($("#pzgg").val());
		var T_pzje=T_pzbzj*T_pzgg;
		
		$.post(basepath + "maxAmount.json", {		
			lever : T_pzgg
		}, function(result) {
			if (result.success) {
				var maxAmount=Number(result.obj);
				var maxLeverMoney=Number(result.data.maxLeverMoney);
				var isOpen=Number(result.data.isOpen);
				var loginStatus = Number(result.data.loginStatus);
				var userTodayTradeNum = Number(result.data.userTodayTradeNum);
				var limitTradeNum = Number(result.data.limitTradeNum);
				if(T_pzje>maxAmount){
					showMsgDialog("提示",T_pzgg+"倍最大操盘配额为"+maxAmount+"元");	
					return false;	
				}if(isOpen==1&& loginStatus==1 && (T_pzje>maxLeverMoney || userTodayTradeNum >= limitTradeNum)){
					$('#OK').parent().find('p i').text($.formatMoney(maxLeverMoney,2));
					$('#OK').parent().find('p span i').text(limitTradeNum);
					$('#OK').parent().css({display:'block'});
					$('.fl_mask').css({display:'block'});
//					showMsgDialog("提示","为保障更多用户获得股票操盘，本时段每用户最大操盘配额限"+maxLeverMoney+"元");
					return false;	
				}else{
					if($("#agree").attr("checked")){
						var pzbzjTemp = $.isNumeric(getIntMoney())?getIntMoney():0;
						if(pzbzjTemp>=300){ 
							var no=Number($("#use_day").val());
							if(no<=180&&no>=2){
								var minPzgg = Number($('#min_lever').html());
								var maxPzgg = $('#max_lever').html()==''?Number($('#min_lever').html()): Number($('#max_lever').html());
								var inputgg=$('#pzgg').val()
								if(inputgg>=minPzgg&&inputgg<=maxPzgg){
									if(typeof($("input[name='tradeStart']:checked").val())!='undefined'){
									if($("input[name='tradeStart']:checked").val()==1){
									showConfirmDialog("提示","请确认是否要在下个交易日开始操盘!",function(){
										if(!isLoginSSO){
							    			window.location.href=basepath+"/toDaySSO"; 
										}else{
											$("form").submit();		
										}		
									},"确定",function(){
										return true;		
									});
									}else{
										if(!isLoginSSO){
							    			window.location.href=basepath+"/toDaySSO"; 
										}else{
											$("form").submit();		
										}
									}
									}else{
										showMsgDialog("提示","请选择开始交易时间！");	
									}
									
								}else{
								showMsgDialog("提示","请选择操盘倍数！"); 
								}
							}else{
								showMsgDialog("提示","操盘天数最少2天，最多180天");	
							}
						}else{
							showMsgDialog("提示","操盘保证金最少300元，最多600万元。");
						}
					}else{
						showMsgDialog("提示","请阅读并同意《借款协议》。");
					}		
				}
			} else {
				showMsgDialog("提示",result.message);
			}
		}, "json");
	});

	if($('#capitalMargin').val()!=0){
		$("#tz").val($.formatMoney(Number($('#capitalMargin').val())));
		$("#pzgg").val($('#lever').val());
		$("#use_day").val($('#borrowPeriod').val());
		var start=$('#backTradeStart').val();
		if(start==0){
		$("input[name='tradeStart'][value=0]").attr("checked",true); 
		}else{
		$("input[name='tradeStart'][value=1]").attr("checked",true); 	
		}
		initTradeStart();
		getAllData(Number($('#lever').val()));
		onTz();
		onLerver();
		onDay();
	}else{	
		var tz=$.isNumeric($('#margin ul li.on').attr('data'))?$.formatMoney(Number($('#margin ul li.on').attr('data'))):'';
		if(tz==''){
			$("#tz").removeClass("font_size_22").addClass("font_size_15");	
		}
		$("#tz").val(tz);
		var gg=$.isNumeric($('#capital_lever ul li.on').attr('data'))?Number($('#capital_lever ul li.on').attr('data')):'';
		if(gg==''){
			$("#pzgg").removeClass("font_size_22").addClass("font_size_15");	
		}
		$("#pzgg").val(gg);
		var use_day=$.isNumeric($('#match_days ul li.on').attr('data'))?Number($('##match_days ul li.on').attr('data')):'';
		if(use_day==''){
			$("#use_day").removeClass("font_size_22").addClass("font_size_15");	
		}
		$("#use_day").val(use_day);
		var capitalMargin = $.isNumeric(getIntMoney())?getIntMoney():0;
		$('#capitalMargin').val(capitalMargin);
		if($.isNumeric(gg)){
			getAllData(gg);
		}
		initTradeStart();
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
	
    //关闭最大融资额限制弹框
    $('#OK').live('click',function(){
    	var $this = $(this);
    	$this.parent().css({display:'none'});
    	$('.fl_mask').css({display:'none'});
    });
    
    //保证金变更时，校验倍数是否允许选择
    var  showLever=function(){
    	var moneyInt = getIntMoney();
    	var maxSelLever = maxMoney/moneyInt;
    	var minSelLever = minPzjeMoney/moneyInt;
        $('.cp_m_mul li').each(function() {
           var lidata = $(this).attr("data");
           if (lidata >=minSelLever && lidata <= maxSelLever){
        	  if ($(this).hasClass("no")){
        		$(this).removeClass("no");
        	  }
        	  // 绑定点击事件
        	  $(this).click(function() {
        			$("#pzgg").removeClass("font_size_15").addClass("font_size_22");	
        			$('#capital_lever ul li.on').removeClass('on');
        			$(this).addClass('on');
        			$("#pzgg").val(Number($(this).attr('data')));
        			getAllData($("#pzgg").val());
        		});
           }else
        	{
        	   if (!$(this).hasClass("no")){
        		   $(this).addClass("no");
        	   }
        	   // 移除点击事件
        	   $(this).unbind("click");
        	}
        });
    }
});