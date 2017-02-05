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

	//顶部菜单位置
	$('.navlist li a').removeClass('on');
	$("#hkstockli").addClass("on");
	
	
	//绑定不足支付页面的关闭事件
	$("#notPay_close").click(function(){
		$("#notPay").hide();
	});
	var needinfo="账户余额不足,我要<a href='"+basepath+"pay/payinfo'>立即充值.</a>";
	//是否需要开启余额不足提示
	if($('#need').val()=='off'){
		$('#needdiv').hide();
	}
	else
	{
		$("#notPay").show();
	}
	//是否开启次日不够扣提示
	if($('#needNext').val()=='off'){
		$('#need_next_pay').hide();	
	}	
	
	//确认配资单击事件
	$("#sub").on("click",function(){
		var borrowPeriod=$('#borrowPeriod').val();
		var lever=$('#lever').val();
		var bailMoney=$('#bailMoney').val();
		var totalMoney =$("#totalMoney").val();
		var tradeStart=$('#tradeStart').val();
		// 登录回调
		$.post(basepath + "hkstock/confirm.check", {borrowPeriod:borrowPeriod,lever:lever,bailMoney:bailMoney,tradeStart:tradeStart,totalMoney:totalMoney,ajax:1}, function(result) {
				if (result.success) {
					$('.cp_paymoney').show();	// 打开支付金额模块域
					if(result.data.need=='on'){
							$('#needdiv').show();
							$('#avl_bal').html($.formatMoney(result.data.avlBal));
							$('#pay_enough').html($.formatMoney(result.data.payEnough));
							$('#need').val(result.data.need);
						}else{
							$('#needdiv').hide();
						}
					if(result.data.needNext=='on'){
							$('#need_next_pay').show();
					}else{
							$('#need_next_pay').hide();
					}
					$('#need_pay').html($.formatMoney(result.data.needPay));
					$('#isVerified').val(result.data.isVerified);
					$('#needNext').val(result.data.needNext);
					
					if($('#need').val()=='on'){
						showMsgDialog("提示",needinfo);
						return false;
					}else
					{
						// 是否实名认证
						if(Number($('#isVerified').val())==0){
							$('#div_Mask').show();
							$('#idcardDiv').show();
							return false;
						}else{
								$("#sub").text('操盘配额进行中......');
								$('#sub').css("color","#CCC").unbind("click");
							    $("form").submit();
						}
					}
					
				}else {
						if (result.message=="非法请求，请先登录！"){
							window.location.href=basepath+"/toHkStockSSO"; 
							return false;
						}
						showMsgDialog("提示",result.message);
				}
			}, "json");
	});
});