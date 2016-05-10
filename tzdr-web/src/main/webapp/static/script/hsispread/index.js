$(document).ready(function(){
	// 进入登录页，则获取login ticket，该函数在下面定义。
	flushLoginTicket(); 
	
	$("#mobile").val($("#mobile").attr("focucmsg"));
	$("#mobile").focus(function(){
	if($("#mobile").val() == $("#mobile").attr("focucmsg"))
		{
			$("#mobile").val('');
			$("#mobile").val('').css("color","#6b6969");
		}
	});
	$("#mobile").blur(function(){
		if(!$("#mobile").val()){
			$("#mobile").val($("#mobile").attr("focucmsg"));
			$("#mobile").val($("#mobile").attr("focucmsg")).css("color","#979393");
		}
	});
	
	$("#code").val($("#code").attr("focucmsg"));
	$("#code").focus(function(){
	if($("#code").val() == $("#code").attr("focucmsg"))
		{
			$("#code").val('');
			$("#code").val('').css("color","#6b6969");
		}
	});
	$("#code").blur(function(){
		if(!$("#code").val()){
			$("#code").val($("#code").attr("focucmsg"));
			$("#code").val($("#code").attr("focucmsg")).css("color","#979393");
		}
	});
	
	

	//手机号码blur校验
	$("#mobile").blur(function(){
		var $this = $(this);
		var mobile =  $.trim($this.val());
	});
});



//手机号码格式检验
function verifyMobile(){ 
	var obj = $("#mobile");
	var mobile =  $.trim(obj.val());
	if(!mobile.match(/^(((13[0-9])|(14[7])|(15[0-9])|(17[0-9])|(18[0-9]))+\d{8})$/)){  //判断手机号码是否正确
		obj.parent().find("p").css({display:"block"});
		return false;
	}
	obj.parent().find("p").css({display:"none"});
	var mobile = $("#mobile").val();
	$.post(basepath+"hsiSpread/send_mobile_code",{mobile:mobile},function(data){
		if(data.success){
			if(data.message!="" && data.message!=null){
				if(data.message == "mobileIsExist"){
					alert("该手机号码已经存在,请使用新号码 ！");
					return;
				}else if(data.message == "mobileIsNull" || data.message == "mobileFormatError"){
					alert("手机号码错误 ！");
					return;
				}else if(data.message == "sendMobileMessage") {//符合发送短息条件
					$.post(basepath+"hsiSpread/send_mobile_message",{mobile:mobile,ajax:1},function(data){  //获取短信验证码信息
						if(data.success){
							if(data.message=="sendMobileCodeFail"){
								alert("获取次数超过5次，请明天再来");
							}else {
								alert("验证码已发送，请注意查收");
							}
						}
						
					},"json");
				}
			}
		}
	},"json");
	return true;
}

//注册
 function sigin(){
	//var $this = $(this);
	 
	var mobile =  $.trim($("#mobile").val());
	var source = 1;
	var code =  $.trim($("#code").val());
	var password =  "a"+mobile;
	if(!mobile.match(/^(((13[0-9])|(14[7])|(15[0-9])|(17[0-9])|(18[0-9]))+\d{8})$/)){  //判断手机号码是否正确
		 $("#mobile").parent().find("p").css({display:"block"});
		return false;
	}
	$("#mobile").parent().find("p").css({display:"none"});
		$.post(basepath+"/hsiSpread/signin_operation",{source:source,mobile:mobile,code:code,password:password,pajax:1},function(data){ //注册
			if(data.success){
				if(data.message!="" && data.message!=null){
					if(data.message=="mobileIsExist"){
						$("#mobile").parent().find("p").css({display:"block"}).text("该手机号码已经存在");
					}else if(data.message=="codeError"){
						$("#mobile").parent().find("p").css({display:"block"}).text("验证码错误,请重新获取验证码");
					}else if(data.message=="codeTimeOut"){
						$("#mobile").parent().find("p").css({display:"block"}).text("验证码已失效,请重新获取验证码");
					}else if(data.message=="success"){
						//隐藏向CAS服务端提交登录的用户名和密码
						$("#loginUsername").val(mobile);
						$("#loginPassword").val(password);
						$("#loginForm").submit();
						//$this.attr("status",true);
					}
				}
			}else{
				alert("提示","系统繁忙，请重试......");
			}
		},"json");
	 

	
};

//登录验证函数, 由 onsubmit 事件触发
function hsiLogin(){
	deleteIFrame('#ssoLoginFrame');// 删除用完的iframe,但是一定不要在回调前删除，Firefox可能有问题的
	// 重新刷新 login ticket
	flushLoginTicket();
	// 验证成功后，动态创建用于提交登录的 iframe
	$('body').append($('<iframe/>').attr({
		style : "display:none;width:0;height:0",
		id : "ssoLoginFrame",
		name : "ssoLoginFrame",
		src : "javascript:false;"
	}));
	return true;
}


function deleteIFrame(iframeName) {
	var iframe = $(iframeName);
	if (iframe) { // 删除用完的iframe，避免页面刷新或前进、后退时，重复执行该iframe的请求
		iframe.remove()
	}
};

// 由于一个 login ticket 只允许使用一次, 当每次登录需要调用该函数刷新 lt
function flushLoginTicket() {
	var _services = 'service='
			+ encodeURIComponent(basepath+"hsiSpread/success");
	$.getScript(casServerLoginUrl+'?'
			+ _services + '&get-lt=true&n=' + new Date().getTime(), function() {
		// 将返回的 _loginTicket 变量设置到  input name="lt" 的value中。
		$('#J_LoginTicket').val(_loginTicket);
		$('#J_FlowExecutionKey').val(_flowExecutionKey);
	});
};

