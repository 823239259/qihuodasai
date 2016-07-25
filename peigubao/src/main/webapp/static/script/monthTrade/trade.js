//关闭div
function closeDiv(id){
		$("#div_Mask").hide();
		$("#"+id).css("display","none"); 
}

//验证身份证
function validateCard(){
	
	var name=$("#cardname").val();
	var card=$("#idcard").val();
	if(name==""){
		$("#cardname").focus(); 
		 $.alertTip($("#cardname"),"姓名不能为空");
		
		return;
	}
	if(card==""){
		$("#idcard").focus(); 
		 $.alertTip($("#idcard"),"身份证号码不能为空");
			
		return;
	}
	if(checkCard(card)==false){
		$("#idcard").focus(); 
		 $.alertTip($("#idcard"),"身份证号码数据不对");
		
		return;
	}
	 $("#validating").text("正在验证");
	 $.post(basepath+"securityInfo/validateCard",{"name":name,"card":card},function(data){  
			if(data.success){
				$("#validating").text("提交");
				if(data.message!="" && data.message!=null){
					 if(data.message=="false"){
						 $.alertTip($("#idcard"),"身份证信息错误。");
					
					}else if(data.message=="maxnum"){
						$.alertTip($("#idcard"),"身份证验证失败超过3次,请与客服联系。");
					}else if(data.message=="exsit"){
						 $.alertTip($("#idcard"),"身份证已经存在。");
						
					}else{
						closeDiv('idcardDiv');
						$('#isVerified').val("1");
						showMsgDialog('提示','身份证验证成功。');										
					}
				}
				
			}
		},"json"); 
}

//验证身份证
checkCard = function(card) {
    //是否为空
    if(card === '') {
        return false;
    }
    //校验长度，类型
    if(isCardNo(card) === false) {
        return false;
    }

    //校验生日
    if(checkBirthday(card) === false) {
        return false;
    }
    //检验位的检测
    if(checkParity(card) === false)
    {
        return false;
    }
    return true;
};

//检查号码是否符合规范，包括长度，类型
isCardNo = function(card) {
    //身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X
    var reg = /(^\d{15}$)|(^\d{17}(\d|X)$)/;
    if(reg.test(card) === false) {
        return false;
    }
    return true;
};



//检查生日是否正确
checkBirthday = function(card) {
    var len = card.length;
    //身份证15位时，次序为省（3位）市（3位）年（2位）月（2位）日（2位）校验位（3位），皆为数字
    if(len == '15') {
        var re_fifteen = /^(\d{6})(\d{2})(\d{2})(\d{2})(\d{3})$/; 
        var arr_data = card.match(re_fifteen);
        var year = arr_data[2];
        var month = arr_data[3];
        var day = arr_data[4];
        var birthday = new Date('19'+year+'/'+month+'/'+day);
        return verifyBirthday('19'+year,month,day,birthday);
    }
    //身份证18位时，次序为省（3位）市（3位）年（4位）月（2位）日（2位）校验位（4位），校验位末尾可能为X
    if(len == '18') {
        var re_eighteen = /^(\d{6})(\d{4})(\d{2})(\d{2})(\d{3})([0-9]|X)$/;
        var arr_data = card.match(re_eighteen);
        var year = arr_data[2];
        var month = arr_data[3];
        var day = arr_data[4];
        var birthday = new Date(year+'/'+month+'/'+day);
        return verifyBirthday(year,month,day,birthday);
    }
    return false;
};

//校验日期
verifyBirthday = function(year,month,day,birthday) {
    var now = new Date();
    var now_year = now.getFullYear();
    //年月日是否合理
    if(birthday.getFullYear() == year && (birthday.getMonth() + 1) == month && birthday.getDate() == day) {
        //判断年份的范围（3岁到100岁之间)
        var time = now_year - year;
        if(time >= 3 && time <= 100) {
            return true;
        }
        return false;
    }
    return false;
};

//校验位的检测
checkParity = function(card) {
    //15位转18位
    card = changeFivteenToEighteen(card);
    var len = card.length;
    if(len == '18') {
        var arrInt = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2); 
        var arrCh = new Array('1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'); 
        var cardTemp = 0, i, valnum; 
        for(i = 0; i < 17; i ++) { 
            cardTemp += card.substr(i, 1) * arrInt[i]; 
        } 
        valnum = arrCh[cardTemp % 11]; 
        if (valnum == card.substr(17, 1)) {
            return true;
        }
        return false;
    }
    return false;
};

//15位转18位身份证号
changeFivteenToEighteen = function(card) {
    if(card.length == '15') {
        var arrInt = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2); 
        var arrCh = new Array('1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'); 
        var cardTemp = 0, i;   
        card = card.substr(0, 6) + '19' + card.substr(6, card.length - 6);
        for(i = 0; i < 17; i ++) { 
            cardTemp += card.substr(i, 1) * arrInt[i]; 
        } 
        card += arrCh[cardTemp % 11]; 
        return card;
    }
    return card;
};

$(document).ready(function(e) {

	$("#interesfee1").val($("#interesfee i").text());
	$("#managefee1").val($("#managefee i").text());
	$("#needpaynum1").val($("#needpaynum i").text());

	$("#voucher").bind("change",function(){
		$("#interesfee i").text($("#interesfee1").val());
		$("#managefee i").text($("#managefee1").val());
		$("#needpaynum i").text($("#needpaynum1").val());
		var voucher =$(this).children('option:selected').text();
		$("#voucher_id").val($(this).children('option:selected').val());
		var interesfee =$("#interesfee i").text();
		var managefee =$("#managefee i").text();
		var needpaynum =$("#needpaynum i").text();
		var lengs;
		if(voucher.indexOf("代金券")>0){
			lengs=voucher.indexOf("元代金券");
			voucher=voucher.substring(0,lengs);
			var prices =Number(interesfee.replace(/[^0-9-.]/g, ''));
			if(prices<=Number(voucher)){
				$("#interesfee i").text("0");
				var needpayVal = Number(needpaynum.replace(/[^0-9-.]/g, ''))-prices;
				$("#needpaynum i").text($.formatMoney(needpayVal));
			}else{
				var interesfeeVal = Number(interesfee.replace(/[^0-9-.]/g, ''))-Number(voucher);
				$("#interesfee i").text($.formatMoney(interesfeeVal));
				var needpayVal = Number(needpaynum.replace(/[^0-9-.]/g, ''))-Number(voucher);
				$("#needpaynum i").text($.formatMoney(needpayVal))
			}
		}else if(voucher.indexOf("折扣券")>0){
			lengs=voucher.indexOf("折折扣券");
			voucher=voucher.substring(0,lengs);
			var price =managefee.replace(/[^0-9-.]/g, '');
			var managefeeVal =price*(Number(voucher)/10);
			$("#managefee i").text($.formatMoney(managefeeVal))
			var needpayVal = Number(needpaynum.replace(/[^0-9-.]/g, ''))-(price-price*(Number(voucher)/10));
			$("#needpaynum i").text($.formatMoney(needpayVal))
		}else if(voucher.indexOf("抵扣券")>0){
			lengs=voucher.indexOf("元抵扣券");
			voucher=voucher.substring(0,lengs);
			var price =managefee.replace(/[^0-9-.]/g, '');
			if(price <= Number(voucher)){
				$("#managefee i").text(0);
				var needpayVal = Number(needpaynum.replace(/[^0-9-.]/g, ''))-Number(managefee);
				$("#needpaynum i").text($.formatMoney(needpayVal))
			}else{
				var managefeeVal =price-Number(voucher);
				$("#managefee i").text($.formatMoney(managefeeVal));
				var needpayVal = Number(needpaynum.replace(/[^0-9-.]/g, ''))-Number(voucher);
				$("#needpaynum i").text($.formatMoney(needpayVal))

			}
		}
	});


	$('#navlist a').removeClass('cur');
	$("#monthli ").addClass("cur");

	var needinfo="账户余额不足,我要<a href='"+basepath+"pay/payinfo'>立即充值.</a>";
	var needVerified="没有进行实名验证,我要<a href='"+basepath+"securityInfo/secInfo'>立即验证.</a>";

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
		var capitalAmount = Number(capitalMargin)*Number(lever);
		var tradeStart=$('#tradeStart').val();
		var maxLeverMoney= $('#maxLeverMoney').val();
		var isOpen= $('#isOpen').val();
		var userTodayTradeNum = Number($('#userTodayTradeNum').val());
		var limitTradeNum = Number($('#limitTradeNum').val());
		var tradetype=$("#tradetype").val(); 
		var voucherId =$("#voucher_id").val();
		$.post(basepath + "/monthTrade/check.json", {borrowPeriod:borrowPeriod,lever:lever,capitalMargin:capitalMargin,tradeStart:tradeStart,tradetype:tradetype,ajax:1}, function(result) {
				if (result.success) {
					$('#userTodayTradeNum').val(result.data.userTodayTradeNum);
					$('#limitTradeNum').val(result.data.limitTradeNum);
					
					$('#holdMaxNum').val(result.data.holdMaxNum);
					userTodayTradeNum = Number(result.data.userTodayTradeNum);
					limitTradeNum = Number(result.data.limitTradeNum);
					$('#isVerified').val(result.data.isVerified);
					$('#proCount').val(result.data.proCount);
					$('#need').val(result.data.need);

					$('#submitMsgConfirm_balance').html(result.data.avlBal);
				    $('#submitMsgConfirm_pay').html(result.data.needPay);
				    $('#submitConfirm_balance').html(result.data.avlBal);
				    $('#submitConfirm_pay').html(result.data.needPay);
					/**
					 * 余额是否充足
					 */
					if(result.data.need=='on'){
						    $("#notEnoughDiv").show();
						    $("#nextNotEnoughDiv").hide();
					    	showSubmitConfirm('submitMsgConfirm');
					    	return false;
					}
					
					if(Number(isOpen) == 1 && (capitalAmount > Number(maxLeverMoney) || userTodayTradeNum >= Number(limitTradeNum))){
						$('#OK').parent().find('p i').text($.formatMoney(maxLeverMoney,2));
						$('#OK').parent().find('p span i').text(limitTradeNum);
						$('#OK').parent().css({display:'block'});
						$('.fl_mask').css({display:'block'});
						return false;
					}
					
					if(Number($('#proCount').val())>=Number($('#holdMaxNum').val())&&Number($('#holdMaxNum').val())>0 ){
							showMsgDialog("提示","一个用户最多同时配"+$('#holdMaxNum').val()+"个方案。");	
							return false;
					}
					
					if(Number($('#isVerified').val())==0){
						$('#div_Mask').show();
						$('#idcardDiv').show();
						return false;
					}
					showSubmitConfirm('submitConfirm');
			    	return;
					
				}else {
						showMsgDialog("提示",result.message);				
				}
			}, "json");	
	});

	$('#OK').live('click',function(){
		var $this = $(this);
    	$this.parent().css({display:'none'});
    	$('.fl_mask').css({display:'none'});
	});
});


function showSubmitConfirm(showid){
	$("#"+showid).css({display:'block'});
	$("#"+showid).parent().css({display:'block'});
}


function closeSubmitConfirm(closeid){
	$("#"+closeid).css({display:'none'});
	$("#"+closeid).parent().css({display:'none'});
}

function submitTrade(){
	$("#sub").text('操盘配额进行中......');
	$('#sub').css("color","#CCC").unbind("click");
	$("form").submit();
	closeSubmitConfirm('submitConfirm');
}

