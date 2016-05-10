
var items_per_page = 10;
var page_index = 0;
var isOut = false; 
$(document).ready(function(){
	
	$(".fr a").removeClass("dja");
	$(".account").addClass("dja");
	$("#oAccount").find("a").removeClass("on");
	$('.navlist li a').removeClass('on');
	$("#nav_my").addClass("on");
	inittab();
	if(tab!="" && tab!=null){
		showtab("",tab);
	}
	
	 $(".uc_wdbank").click(function(){
		 	//event.stopPropagation();
			$(".uc_wd_bank").show();
		 });
		 $(".uc_wd_bank a").each(function(){
			$(this).click(function(){
				var value = $(this).html();
				$(".uc_wdbanksel").val(value);
				$(".uc_wd_bank").hide();
			});
		 });
		
	
	$('.uc_wdbank').click(function(event) {
		event.stopPropagation();
		$("#uc_wd_bank").show();
	});
	
	$('.uc_bcbdown').click(function(event) {
		event.stopPropagation();
		$("#uc_wd_banks").show();
	});

	$(document).click(function(){
		if(isOut ==  false) {
			$(".uc_wd_bank").hide();
			$('#uc_wd_banks').hide();
		}		
	});
	
	
	$(".uc_wd_bank a").each(function(){
		$(this).click(function() {			
			var value = $(this).html();
			var card=$(this).attr("data-id");
			
			if(card=="addcard"){
				showtab("",1);
			}else{
				$('.uc_wdbanksel').val(value);
				$('.uc_wdbanksel').attr("data-id",$(this).attr("data-id"));
				$('.uc_wdbanksel').attr("data-name",$(this).attr("data-name"));
				$('.uc_wdbanksel').css('color', '#666');
				$('.uc_b_bank').hide();
			}
		});

	});
	
	$("#uc_wd_banks a").each(function(){
		$(this).click(function() {			
			var value = $(this).html();
			var bankname=$(this).attr("data-id");
			$('#bankname').val(value);
			$('#bankname').attr("data-id",$(this).attr("data-id"));
			$('#uc_wd_banks').hide();
			
		});

	});
	
	
	var secwarn =getSecInfoWarn();
	var warncard=getwarncard();
	$("#money").click(function(){
		if(secwarn!="" && secwarn!=undefined){
			showMsgDialog("提示",secwarn);
			return;
		}else if(warncard!="" && warncard!=undefined){
			showMsgDialog("提示","未绑定银行卡，请先绑定银行卡");
			return;
		}
		
	});
	
	$("#drawpwd").click(function(){
		if(secwarn!="" && secwarn!=undefined){
			showMsgDialog("提示",secwarn);
			return;
		}else if(warncard!="" && warncard!=undefined){
			showMsgDialog("提示","未绑定银行卡，请先绑定银行卡");
			return;
		}
		
	});
	
	
	$("#savecard").click(function(){
		var bankname=$("#bankname").attr("data-id");
		var card=$("#card").val();
		var agincard=$("#agincard").val();
		if(bankname==undefined||bankname==""){
			showMsgDialog("提示","请选择银行");
			return;
		}
		if(card==""){
			showMsgDialog("提示","请输入卡号");
			return;
		}

		if(!isBankCard(card)){
			showMsgDialog("提示","卡号填写错误");
			$("#brankCard").focus();
			return ;
		}
		if(agincard==""){
			showMsgDialog("提示","请输入确认卡号");
			return;
		}
		if(card!=agincard){
			showMsgDialog("提示","两次卡号输入不一致");
			return;
		}
		
		 $.post(basepath+"draw/savecard",{"bankname":bankname,"card":card,"agincard":agincard},function(data){  
				if(data.success){
					window.location.href=basepath+"draw/drawmoney?tab=1";
					
				}else{
					showMsgDialog("提示",data.message);
				}
			},"json"); 
		
	});
	

	getDataList(page_index);
	
	//点击左边菜单
	$('.uc_sidebar').find("div.uc_nav ul a").each(function(){
		$(this).removeClass('on');
 	});
	$("#drawnav").parent().addClass('on');
	
	//取消提现
	$("#cancelDraw").live('click',function(){
		var $this = $(this);
		var dataId = $this.attr('data_id');
		if($this.attr('status') == 'true'){
			$this.attr('status',false);
			$this.text('正在取消中');
			$.post(basepath+"draw/cancelDraw",{id:dataId,ajax:1},function(data){  //检验手机号码是否已注册
				if(data.success){
					var $parent = $this.parent().prev();
					if(data.message!="" && data.message!=null){
						if(data.message=="idIsNull" || data.message=="idIsError"){
							showMsgDialog("提示","提交数据错误");
							$this.attr('status',true);
							$this.text('取消提现');
						}if(data.message=="dataAlreadyCancel"){
							showMsgDialog("提示","该数据已取消,不能重复操作");
							$this.remove();
							$parent.html(data.data.statusValue);
						}if(data.message=="dataAlreadyDispose"){
							showMsgDialog("提示","该数据已审核过,无法取消");
							$this.remove();
							$parent.html(data.data.statusValue);
						}
					}else{
						$this.remove();
						$parent.html('已取消');
					}
				}else{
					$this.attr('status',true);
					$this.text('取消提现');
					showMsgDialog('提示','系统繁忙，请重试......');
				}
			},'json');
		}
	});
	
	$('#money').bind({
		keyup: function() {
			var _money = $(this).val();
			
			if(isMoney(_money, true)) {
				if(_money < 5000) {
					$('#handle-fee').text($("#handleFee").val());
					$('#actual-money').text((_money - $("#handleFee").val()).toFixed(2));
				} else {
					$('#handle-fee').text('0.00');
					$('#actual-money').text((_money - 0).toFixed(2));
				}
				return;
			}
			$('#handle-fee').text('0.00');
			$('#actual-money').text('0.00');
		}
	});
});

function createcode(){
	$("#validateCode").attr("src",basepath+"/validate.code?1=" + Math.random()*10000);
}
//分页查询充值记录
function getDataList(index){   
	    var pageIndex = index; 
	    
	    $.ajaxSetup({ 
	    	contentType : "application/x-www-form-urlencoded;charset=utf-8", 
	    	complete : function(XMLHttpRequest, textStatus) { 
	    	var sessionstatus = XMLHttpRequest.getResponseHeader("sessionstatus"); // 通过XMLHttpRequest取得响应头，sessionstatus， 
	    	if (sessionstatus == "timeout") { 
	    	// 如果超时就处理 ，指定要跳转的页面 
	    	 window.location.replace(basepath+"draw/drawmoney"); 
	    	} 
	      } 
	    }); 
	    
	    $.ajax({   
	        type: "POST",   
	        url: basepath+"draw/drawHistory",   
	        data: "pageIndex="+pageIndex+'&perPage='+items_per_page,   
	        dataType: 'json',   
	        contentType: "application/x-www-form-urlencoded",   
	        success: function(msg){  
	            var total =msg.totalCount;   
	            var html = '';   
	            $.each(msg.pageResults,function(i,n){
	            	var statusvalue=n.statusValue;
	            	if(statusvalue==null)
	            		statusvalue="";
	            	html +="<ol>";
	            	html+="<li class='uc_re_num'>"+getFormatDateByLong(n.addtime,'yyyy-MM-dd hh:mm:ss')+"</li>";
	            	html+="<li class='uc_re_num'>"+n.bank+"</li>";
	            	html+="<li class='uc_re_num2'>"+n.money+"元</li>";
	            	if(n.status != 3 && (n.isAudit == 0 || n.isAudit == -1)){
	            		html+="<li class='uc_re_num2'>提现审核中 </li>";
	            		html+="<li class='uc_re_num2'><a id='cancelDraw' data_id="+n.id+" status='true' name='cancelDraw' href='javascript:void(0)'>取消提现<a/></li>";
	            	}else{
	            		html+="<li class='uc_re_num2'>"+statusvalue+"</li>";
	            	}
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
	
//提现
function drawmoney(){
	var money = $("#money").val();
	var bankcard = $("#bankcard").attr("data-id");
	var bankname = $("#bankcard").attr("data-name");
	var drawpwd = $("#drawpwd").val();
	var balance=$("#balance").html();
	var codeLi = $("#randcode");
	var code = codeLi == null || codeLi.length <= 0? null : $.trim($("#code").val());
	if(money==""){
		showMsgDialog("提示","请填写提现金额");
		return;
	}
	
	if(bankcard=="" || bankcard=="添加银行卡"){
		showMsgDialog("提示","请选择银行卡");
		return;
	}
	if(drawpwd==""){
		showMsgDialog("提示","请填写提现密码");
		return;
	}
	if($("#randcode").css("display") == "block" && (code == null || code.length <= 0)){
		showMsgDialog("提示","请输入验证码");
		$("#validateCode").attr("src",basepath+"/validate.code?1=" + Math.random()*10000);
		$("#code").focus();
		return;
	}
	
	 var reg = new RegExp("^\\d+(\\.\\d+)?$");  
	  if(!reg.test(money)){
		  showMsgDialog("提示","提现金额请输入数字");
	      return;
	  }
	if(parseFloat(money)>balance){
		showMsgDialog("提示","提现金额不能大于账户余额");
		return;
	}
	if(parseFloat(money)<10){
		showMsgDialog("提示","提现金额不能小于10元");
		return;
	}
	
	//如果提现渠道是币币支付，金额不能大于50000
	if ($("#withdrawSetting").val()==2){
		if (parseFloat(money)>=50000){
			showMsgDialog("提示","因网银限制，单笔提现金额要小于50000元，每天可提现5笔。");
			return;
		}
	}
	 $.post(basepath+"draw/doDrawmoney",{"bankcard":bankcard,"money":money,"drawpwd":drawpwd,"bankname":bankname,"code":code},function(data){  
			if(data.success){
				if(data.message!="" && data.message!=null){
					if(data.data != null &&  data.data.isNeedCode && $("#randcode").css("display") == "none"){
						$("#randcode").css({display:"block"});
					}
					if(data.message=="codeError"){
						showMsgDialog("提示","验证码错误");
						$("#validateCode").attr("src",basepath+"/validate.code?1=" + Math.random()*10000);
					}else if(data.message=='existArrearage'){
						//showMsgDialog("提示","您有未结算的账目，您的账号已被限制提现，请联系客服解除限制！");
						showMsgDialog("提示",'您的账户已被限制提现，具体原因为“'+data.obj+'”，请联系客服解除限制！');
						$("#validateCode").attr("src",basepath+"/validate.code?1=" + Math.random()*10000);
					}else{
						if(data.message=="success"){
							winSucessRefresh("提示","成功申请提现",basepath+"draw/drawmoney?tab="+2);
						}else{
							showMsgDialog("提示",data.message);
							$("#validateCode").attr("src",basepath+"/validate.code?1=" + Math.random()*10000);
							
						}
					}
				}else{
					//alert("成功申请提现");
					winSucessRefresh("提示","成功申请提现",basepath+"draw/drawmoney?tab="+2);
					//window.location.href=basepath+"draw/drawmoney?tab="+2;
				}
			}
		},"json"); 
}


function pageselectCallback(page_index, jq){
	getDataList(page_index);
}

//设置默认银行卡
function setdefaulcard(id){
	 $.post(basepath+"draw/setDefaulcard",{"id":id},function(data){  
			if(data.success){
				if(data.message!="" && data.message!=null){
					window.location.href=basepath+"draw/drawmoney?tab=1";
					
				}
			}
		},"json"); 
}


//删除银行卡
function delcard(id){
	 if(!confirm("确定要删除此银行卡吗？")){
	   return;
	 }
	 $.post(basepath+"draw/delCard",{"id":id},function(data){  
			if(data.success){
				if(data.message!="" && data.message!=null){
					alert(data.message);
					window.location.href=basepath+"/draw/drawmoney?tab=1";
					
				}
			}
		},"json"); 
	 
}


//获取安全信息提示
function getSecInfoWarn(){
	var html="";
	if($("#warnidcard")){
		html=$("#warnidcard").html();
	}
	return html;
}

function getwarncard(){
	var html="";
	if($("#warncard")){
		html=$("#warncard").html();
	}
	return html;
}

function bankmanage(){
	showtab("",1);
	
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
				if(text=="提现记录"){//充值记录查询数据库
					
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
	if(text=="提现记录"){//充值记录查询数据库
		
		getDataList(page_index);
	}
	 tts.eq(tabnum).addClass('on');
	 currTabCon.show().siblings().hide();
}
	 
	