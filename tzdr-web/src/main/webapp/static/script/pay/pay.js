
var items_per_page = 10;
var page_index = 0;
var isOut = false; 
$(document).ready(function(){
	$(".fr a").removeClass("dja");
	$(".account").addClass("dja");
	$("#oAccount").find("a").removeClass("on");
	$('.navlist li a').removeClass('on');
	$("#nav_my").addClass("on");
	var icbc_desc = $('#icbc_desc');
	if(icbc_desc){
		icbc_desc.show().siblings().hide();
	}
	// 网银充值
	$('.uc_olbankdown').click(function() {
		$('.uc_banklist').css('height', '488px');
		$('.uc_oldown1').hide();
		$('.uc_olup1').show();
	});
	$('.uc_olbankup').click(function() {
		$('.uc_banklist').css('height', '288px');
		$('.uc_oldown1').show();
		$('.uc_olup1').hide();	

	});

	$('.fl_navtitle a').click(function() {
		$('.fl_uc_banks').hide();
		$('.fl_mask').hide();
	});
	
	$('.fl_uc_cancelbtn').click(function() {
		$('.fl_uc_banks').hide();
		$('.fl_mask').hide();
	});
	
	$('.fl_uc_surebtn').click(function() {
		$('.fl_uc_banks').hide();
		$('.fl_mask').hide();
		window.location.href=basepath+"pay/payinfo?tab=3";
		
	});
	
	
	$('.uc_b_ipbank').click(function(event) {
		event.stopPropagation();
		$(".uc_b_bank").show();
	});

	$(document).click(function(){
		if(isOut ==  false) {
			$(".uc_b_bank").hide();
		}		
	});

	$(".uc_b_bank a").each(function(){
		$(this).click(function() {			
			var value = $(this).html();
			$('.uc_b_selbank').val(value);
			$('.uc_b_selbank').attr("data-id",$(this).attr("data-id"));
			$('.uc_b_bank').hide();
		});

	});
	
	$.post(basepath+"/pay/queryUserAliAccount.json",{},function(data){  
		if(data.success){
			if(data.data && data.data.aliAccount) {
				$('#payaliaccount').html(data.data.aliAccount);
				$('#alipay1').hide();
				$('#alipay2').show();
			}
		}
	},"json"); 
	
	$('#payaliaccount').parent().find('a').click(function() {
		$('#ali_account_text').hide();
		$('#ali_account_input').show();
	});
	$('#ali_account_input').find('.btn').click(function() {
		doAliCharge(true);
	});
	
	inittab();
	if(tab!="" && tab!=null){
		showtab("",tab);
	}
	//快捷支付按钮
	$('#quickPayForm').find('.uc_banklist').find(':radio').click(function(){
		$('#quickPayForm').find(".uc_banklist li i").each(function(){
			$(this).remove();
		 });
		$(this).parent().append("<i></i>");
		
	});
	
	
	
	//快捷支付按钮图片按钮
	var tbs=$('#quickPayForm').find('.uc_banklist li a');
	tbs.click(function(){
		 $(this).prevAll("input").eq(0).attr("checked",'checked');
		 $('#quickPayForm').find(".uc_banklist li i").each(function(){
				$(this).remove();
		 });
		 $(this).parent().append("<i></i>");
		 
	});
	//网银支付按钮
	$('#netbank').find('.uc_banklist').find(':radio').click(function(){
		$('#netbank').find(".uc_banklist li i").each(function(){
			$(this).remove();
		 });
		$(this).parent().append("<i></i>");
		var banktype=$("#netbank input[name='banktype']:checked").val();
		
		var bank_desc = $('#'+banktype+'_desc');
		if(bank_desc){
			bank_desc.show().siblings().hide();
		}
	});
	//网银图片按钮
	var nettbs=$('#netbank').find('.uc_banklist li a');
	nettbs.click(function(){
		
		 $(this).prevAll("input").eq(0).attr("checked",'checked');
		 $('#netbank').find(".uc_banklist li i").each(function(){
				$(this).remove();
		 });
		 $(this).parent().append("<i></i>");
		 
		 var banktype=$("#netbank input[name='banktype']:checked").val();
			
			var bank_desc = $('#'+banktype+'_desc');
			if(bank_desc){
				bank_desc.show().siblings().hide();
			}
	});
	
	
	var secwarn =getSecInfoWarn();
	if(secwarn!=""){
		$("#bankCard").click(function(){
			showMsgDialog("提示",secwarn);
			return;
			});
		$("#paymoney").click(function(){
			showMsgDialog("提示",secwarn);
			return;
		});
	}else{
		$("#paymoney").blur(function(){
			var money = $("#paymoney").val();
			var realmoney=Number(money) + Number(money)*Number("0.007");
			var poundagemoney=Number(money)*Number("0.007");
			$("#poundage").text(poundagemoney.toFixed(2));
			$("#realpaymoney").text(realmoney.toFixed(2));
		});
	}
	
	$('.uc_ppbtn').hover(function() {
		$('.uc_pay_promt').show();
	}, function() {
		$('.uc_pay_promt').hide();
	});
	getDataList(page_index);
	
	var clip = new ZeroClipboard($("#popBtn"), {
		moviePath: basepath+"/static/script/common/ZeroClipboard.swf"
	});
	clip.on("complete", function(client, args){
		alert("已复制到剪切板");
	});	
	$('.uc_sidebar').find("div.uc_nav ul a").each(function(){
		$(this).removeClass('on');
 	});
	$("#paynav").parent().addClass('on');
	var isFlag = $("#isFlag").val();
	if(isFlag == "1"){
		$("#payButton").text("充值并购买方案");
		$("#fatip").show();
	}else{
		$("#fatip").hide();
	}
});


//分页查询充值记录
function getDataList(index,type){ 
	    var pageIndex = index; 
	    $.ajaxSetup({ 
	    	contentType : "application/x-www-form-urlencoded;charset=utf-8", 
	    	complete : function(XMLHttpRequest, textStatus) { 
	    	var sessionstatus = XMLHttpRequest.getResponseHeader("sessionstatus"); // 通过XMLHttpRequest取得响应头，sessionstatus， 
	    	if (sessionstatus == "timeout") { 
	    	// 如果超时就处理 ，指定要跳转的页面 
	    	 window.location.replace(basepath+"pay/payinfo?tab=3"); 
	    	} 
	      } 
	    }); 

	    //$.ajax(); 
	    $.ajax({   
	        type: "POST",   
	        url: basepath+"pay/payHistory",   
	        data: {"pageIndex":pageIndex,'perPage':items_per_page,"isAjax":"1"},   
	        dataType: 'json',   
	        contentType: "application/x-www-form-urlencoded",
	        success: function(msg){  
	            var total =msg.totalCount;   
	            var html = '';   
	            $.each(msg.pageResults,function(i,n){
	            	var statusvalue=n.statusvalue;
	            	var actualMoney=n.actualMoney;
	            	var no=n.no;
	            	if(actualMoney==null){
	            		actualMoney="0";
	            	}
	            	if(statusvalue==null)
	            		statusvalue="";
	            	if(no==null)
	            		no="";
	            	html +="<ol>";
//	            	if(n.oktime!=null &&n.oktime!=""){
//	            		html+="<li class='uc_re_time'>"+getFormatDateByLong(n.oktime,'yyyy-MM-dd hh:mm:ss')+"</li>";
//		            	
//	            	}else{
	            		html+="<li class='uc_re_time'>"+getFormatDateByLong(n.addtime,'yyyy-MM-dd hh:mm:ss')+"</li>";
		            	n.no
	            	//}
	            	html+="<li class='uc_re_num120'>"+no+"</li>";
	            	html+="<li class='uc_re_way80'>"+n.paytypevalue+"</li>";
	            	html+="<li class='uc_re_money100'>"+n.money+"元</li>";
	            	html+="<li class='uc_re_money100'>"+actualMoney+"元</li>";
	            	html+="<li class='uc_re_ope100'>"+statusvalue+"</li>";
	            	html +="</ol>";
	            }); 
	            $('#Searchresult').html(html); 
	           //分页-只初始化一次   
	            if($("#Pagination").html()== ''){
	            	
	                $("#Pagination").pagination(total, {   
	                    'items_per_page'      : items_per_page,
	                    'num_edge_entries'    : 2,   
	                    'prev_text'           : "上一页",   
	                    'next_text'           : "下一页",   
	                    'callback'            : pageselectCallback  
	                });   
	            }   
	        }   
	    });   
	}   
	  



function pageselectCallback(page_index, jq){
	getDataList(page_index);
}


//网银支付
/*function doNetPayment1(){
	var banktype=$("#netbank input[name='banktype']:checked").val();
	var money = $("#money").val();
	if(money==""){
		showMsgDialog("提示","请填写金额");
		$("#money").focus();
		return ;
	}
	
	if(!isMoney(money)){
		showMsgDialog("提示","充值金额填写错误");
		$("#money").focus();
		return ;
	}
	
	if(parseFloat(money)<1){
		showMsgDialog("提示","充值金额必须大于等于1元");
		$("#money").focus();
		return ;
	}
	$('.fl_mask').show();
	$('#issucessdiv').show();
	document.forms["netbank"].action=basepath+"pay/netbankPayment";//
	document.forms["netbank"].submit();*/
	//$("#netbank").submit();
	
	 /*$.post(basepath+"/pay/netPayment",{"banktype":banktype,"money":money},function(data){  
			if(data.success){
				if(data.message!="" && data.message!=null){
					 if(data.message=="paymoneyless"){
						showMsgDialog("提示","充值金额最少为1元");
					}else if(data.message=="banktypeNotExist"){
						showMsgDialog("提示","请选择银行");
					}else{
						//window.open(data.message);
						var tempwindow=window.open('_blank');
						tempwindow.location=data.message;
					}
				}
				
			}
		},"json"); */
//}
/**
 * 国付宝充值
 */
function doGopay(){
	var money = $("#gopaymoney").val();
	if(money==""){
		showMsgDialog("提示","请填写金额");
		$("#gopaymoney").focus();
		return ;
	}
	
	if(!isMoney(money)){
		showMsgDialog("提示","充值金额填写错误");
		$("#gopaymoney").focus();
		return ;
	}
	
	if(parseFloat(money)<1){
		showMsgDialog("提示","充值金额必须大于等于1元");
		$("#gopaymoney").focus();
		return ;
	}
	if($("#isFlag").val() == "1"){
		var payMoney = parseFloat($("#isFlagMoney").val());
		if(parseFloat(money) < payMoney){
			showMsgDialog("提示","充值金额不能少于"+payMoney+"元");
			$("#gopaymoney").focus();
			return;
		}
	}
	document.forms["goNetbank"].action=basepath+"pay/goPayView";
	document.forms["goNetbank"].submit();
}

/*微信支付*/
$(function(){
	var userAccount =  $("#userAccount").text();
	if(userAccount != undefined && userAccount != "" && userAccount.length > 0){
		$("#weixin_bind").hide();
		$("#weixin_update").show();
	}else{
		$("#weixin_bind").show();
		$("#weixin_update").hide();
	}
	/*绑定微信号*/
	$(".weixin_bind").click(function(){
		var weixin = $("#weixin").val();
		if(weixin==""){
			showMsgDialog("提示","请填写微信账号");
			$("#weiixn").focus();
			return ;
		}
		/*var reg=/^[a-zA-Z\d_]{5,}$/;    
		if(reg.test(weiixn)){
			showMsgDialog("提示","微信号格式有误");
			$("#weiixn").focus();
			return ;
		}*/
		$.post(basepath+"/pay/wx/bind/account",{"account":weixin},function(data){  
			if(data.success){
				showMsgDialog("提示","绑定成功！");
				$("#weixin_bind").hide();
				$("#weixin_update").show();
				$("#userAccount").text(weixin);
			}else{
				showMsgDialog("提示",data.message);
			}
		},"json"); 
	});
	/*修改微信号*/
	$("#weixinbank .weixin_update").click(function(){
		$("#weixin_bind").show();
		$("#weixin_update").hide();
		$("#weixin").val($("#userAccount").text());
	});
});

/**
 * 支付宝充值
 */
function doNetPayment(){
	var money = $("#money").val();
	if(money==""){
		showMsgDialog("提示","请填写金额");
		$("#money").focus();
		return ;
	}
	
	if(!isMoney(money)){
		showMsgDialog("提示","充值金额填写错误");
		$("#money").focus();
		return ;
	}
	
	if(parseFloat(money)<1){
		showMsgDialog("提示","充值金额必须大于等于1元");
		$("#money").focus();
		return ;
	}
	document.forms["netbank"].action=basepath+"pay/pingplusplus";
	document.forms["netbank"].submit();
}
//快捷支付
function doPayment(){
	var banktype=$("#quickPayForm input[name='banktype']:checked").val();
	var warn=getSecInfoWarn();
	if(warn!=""){
		showMsgDialog("提示",warn);
		return;
	}
	var money = $("#paymoney").val();
	var bankCard = $("#bankCard").val();
	if(bankCard==""){
		showMsgDialog("提示","请填写储蓄卡卡号");
		$("#brankCard").focus();
		return ;
	}
	if(money==""){
		showMsgDialog("提示","请填写金额");
		$("#paymoney").focus();
		return ;
	}
	
	if(!isBankCard(bankCard)){
		showMsgDialog("提示","储蓄卡卡号填写错误");
		$("#brankCard").focus();
		return ;
	}
	if(!isMoney(money)){
		showMsgDialog("提示","充值金额填写错误");
		$("#paymoney").focus();
		return ;
	}
	if(parseFloat(money)<1){
		showMsgDialog("提示","充值金额必须大于等于1元");
		$("#paymoney").focus();
		return ;
	}
	if(parseFloat(money)>50000){
		showMsgDialog("提示","快捷支付单笔最大金额为5万");
		return ;
	}
	$('.fl_mask').show();
	$('#issucessdiv').show();
	 $.post(basepath+"/pay/quickPayment",{"bankCard":bankCard,"paymoney":money,"banktype":banktype},function(data){  
			if(data.success){
				if(data.message!="" && data.message!=null){
					if(data.message=="idCardNotExist"){
						showMsgDialog("提示","身份证号码错误");
					}else if(data.message=="paymoneyless"){
						showMsgDialog("提示","充值金额最少为1元");
					}else{
						//window.open(data.message);
						var tempwindow=window.open('_blank');
						tempwindow.location=data.message;
					}
				}
				
			}
		},"json"); 
	
}

//银行转账
$(function(){
	var bankname=$('#uc_bank_radio ol');
	bankname.click(function(){
		var this_ = $(this).index();
		$('#uc_bank_radio input:radio[name="back_icon"]').eq(this_).prop("checked",true);
	});
});
function doTransmany(){
	var bankname=$('#uc_bank_radio input:radio[name="back_icon"]:checked').val();
	var money=$("#transmoney").val();
	var serialnum=$("#serialnum").val();
	if(bankname==""||bankname==undefined){
		showMsgDialog("提示","请选择银行");
		return ;
	}
	if(!isMoney(money)){
		showMsgDialog("提示","充值金额填写错误");
		return ;
	}
	if(parseFloat(money)<1){
		showMsgDialog("提示","充值金额必须大于等于1元");
		return ;
	}
	if(parseFloat(money)>6000000){
		showMsgDialog("提示","充值金额不能大于600万");
		return ;
	}
	if(serialnum==""){
		showMsgDialog("提示","请输入流水号");
		return ;
	}
	 $.post(basepath+"/pay/doAlipayOrTansaccount",{"bankname":bankname,"alimoney":money,"serialnum":serialnum,"type":"trans"},function(data){  
			if(data.success){
			    if(data.message!=""){
					showMsgDialog("提示",data.message);
				}else{
					showMsgDialog("提示","提交成功");
					$("#transmoney").val("");
					$("#serialnum").val("");
				}
				
			}
		},"json"); 
	
}


//支付宝转账
function doAliCharge(flag) {
	var alipayaccount = flag ? $('#ali_account_input :input').val() : $("#alipayaccount").val();
//	var alipayaccount = $("#alipayaccount").val();
//	var alimoney = $("#alimoney").val();
	var aliSubmitBtn=$("#aliSubmitBtn");
	if(alipayaccount==""){
		showMsgDialog("提示","请填写支付宝账号");
		return ;
	}
//	if(!isMoney(alimoney)){
//		showMsgDialog("提示","充值金额填写错误");
//		return ;
//	}
//	if(parseFloat(alimoney)<1){
//		showMsgDialog("提示","充值金额必须大于等于1元");
//		return ;
//	}
//	if(parseFloat(alimoney)>1000000){
//		showMsgDialog("提示","充值金额不能大于100万");
//		return ;
//	}
	
	$.post(basepath+"/pay/doAlipayOrTansaccount",{"alipayaccount":alipayaccount,"alimoney":0},function(data){  
			if(data.success){
			    if(data.message!=""){
					showMsgDialog("提示",data.message);
				}else{
					$('#payaliaccount').html(alipayaccount);
//					$('#payalimoney').html(alimoney);
//					$('#alipay1').hide();
//					$('#alipay2').show();
//					aliSubmitBtn.attr({ "disabled": "disabled" });
					if(flag) {
						$('#ali_account_text').show();
						$('#ali_account_input').hide();
					} else {
						$('#alipay1').hide();
						$('#alipay2').show();
						aliSubmitBtn.attr({ "disabled": "disabled" });
					}
				}
				
			}
		},"json"); 
}

//到支付宝首页
function toAliCharge(){
	$("#aliSubmitBtn").removeAttr("disabled");
	$('#payaliaccount').html("");
	$('#payalimoney').html("");
	$('#alipay2').hide();
	$('#alipay1').show();
}

//获取安全信息提示
function getSecInfoWarn(){
	var html="";
	if($("#warnidcard")){
		html=$("#warnidcard").html();
	}
	return html;
}
//初始化tab页面
inittab=function(id){
			id=id||'banktab';
			var tts=$("#"+id).find('ul.uc_paynav a');
			var tis=$("#"+id).find('div.tabcon div.subtab');
			tts.first().addClass('on');
			tis.first().show().siblings().hide();
			tts.click(function(){
				tts.removeClass('on');
				var i = tts.index(this);
				var currTab = tts.eq(i);
				var currTabCon=tis.eq(i);
				if(currTab.hasClass('on'))return;
				var text=currTab.text();
				if(text=="充值记录"){//充值记录查询数据库
					getDataList(page_index);
				}
				 tts.eq(i).addClass('on');
				 currTabCon.show().siblings().hide();
			});
}
	
showtab=function(id,tabnum){
	id=id||'banktab';
	var tts=$("#"+id).find('ul.uc_paynav a');
	var tis=$("#"+id).find('div.tabcon div.subtab');
	tts.removeClass('on');
	var currTab = tts.eq(tabnum);
	var currTabCon=tis.eq(tabnum);
	if(currTab.hasClass('on'))return;
	var text=currTab.text();
	if(text=="充值记录"){//充值记录查询数据库
		getDataList(page_index);
	}
	
	 tts.eq(tabnum).addClass('on');
	
	 currTabCon.show().siblings().hide();
}
	 
	