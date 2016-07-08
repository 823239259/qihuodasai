$(document).ready(function(){

	// 进入登录页，则获取login ticket，该函数在下面定义。
	flushLoginTicket(); 
	
	//校验手机号码
	$(".mobile").blur(function(){
		var $this = $(this);
		var mobileValue =  $.trim($this.val());
		verifyMobile($this) //检验手机号码;
	});

	//检验手机号码格式
	function verifyMobile(obj){
		var mobileValue =  $.trim(obj.val());
		if(mobileValue == null || mobileValue.length <= 0){  //判断手机号码是否为空  
			obj.parent().find("p").addClass("error");
			obj.parent().find("p").html("<i>*</i>请填写手机号码");
			return false;
		}else if(!mobileValue.match(/^(((13[0-9])|(14[7])|(15[0-9])|(17[0-9])|(18[0-9]))+\d{8})$/)){  //判断手机号码是否正确
			obj.parent().find("p").addClass("error");
			obj.parent().find("p").html("<i>*</i>手机号码输入有误");
			return false;
		}
		obj.parent().find("p").removeClass("error");
		obj.parent().find("p").html("<i>*</i>您将用该手机号登录");
		return true;
	}
	
	//打开短信验证码框
	$(".openYZMBox").live("click",function(){
		var $this = $(this);
		var mobileObject = $this.parent().parent().find(".mobile");
		if(verifyMobile(mobileObject)){
			$(".getSMSCode").attr("data_type",$this.attr("data_type"));
			$(".yzm_fl_mask").show();
			$(".yzm_box").show();
		}
	});
	
	//关闭短信验证码框
	function colseYZMBox(){
		$(".yzm_fl_mask").hide();
		$(".yzm_box").hide();
		$(".yzm").val('');
	}
	
	//关闭短信验证码框
	$(".yzm_box_close").live("click",function(){
		colseYZMBox();
	});
	
	//刷新注册码
	$(".refresh_code").live("click",function(){
		var $this = $(this);
		$("#refresh_code").attr("src",basepath+"validate.code?1=" + Math.random()*10000);
	});

	//验证码倒计时
	var wait=60;
	
	//获取短信验证码
	$(".getSMSCode").live("click",function(){
		var $this = $(this);
		var status =  $(this).attr("status");  
		if(status == "true"){
			$this.attr("status",false);
			$this.css({background:'#D3D3D3'});
			var yzm = $(".yzm").val();
			var mobile = $this.attr("data_type") == 0 ? $($(".mobile")[0]).val() : $($(".mobile")[1]).val();
			if(yzm == null || yzm.length <= 0){
				$this.attr("status",true);
				$this.css({background:'#ff9c00'});
				$.alertTip($(this),"验证码不能空 ！");
				return;
			}
			$.post(basepath+"send_mobile_code",{mobile:mobile,yzmCode:yzm,ajax:1},function(data){  //获取短信验证码信息
				$(".refresh_code").attr("src",basepath+"validate.code?1=" + Math.random()*10000);
				if(data.success){
					if(data.message!="" && data.message!=null){
						if(data.message == "mobileIsExist"){
							$this.attr("status",true);
							$this.css({background:'#ff9c00'});
							$.alertTip($(".getSMSCode"),"该手机号码已经存在 ！");
							return;
						}else if(data.message == "mobileIsNull" || data.message == "mobileFormatError"){
							$this.attr("status",true);
							$this.css({background:'#ff9c00'});
							$.alertTip($(".getSMSCode"),"手机号码错误 ！");
							return;
						}else if(data.message == "yzmStrError"){
							$this.attr("status",true);
							$this.css({background:'#ff9c00'});
							$.alertTip($(".getSMSCode"),"验证码错误！");
							return;
						}else{
							var openYZMBoxOject = $(".openYZMBox");
							colseYZMBox();
							$this.attr("status",true);
							$this.css({background:'#ff9c00'});
							openYZMBoxOject.attr("disabled",true);
							openYZMBoxOject.text("重新发送(" + wait + ")");
							$.alertTip($this.attr("data_type") == 0 ? $($(".openYZMBox")[0]) : $($(".openYZMBox")[1]),data.message == "highOperation" ? "操作过于频繁，请稍后再重试！" : "每天只能获取5次验证码 ！");
							countdown(openYZMBoxOject);  //按钮设置倒计时
						}
					}else{
						var openYZMBoxOject = $(".openYZMBox");
						colseYZMBox();
						$this.attr("status",true);
						$this.css({background:'#ff9c00'});
						openYZMBoxOject.attr("disabled",true);
						var mobileObj = $this.attr("data_type") == 0 ? $($(".mobile")[0]) : $($(".mobile")[1]);
						mobileObj.parent().find("p").removeClass("error");
						mobileObj.parent().find("p").html("<i>*</i>√");
						openYZMBoxOject.text("重新发送(" + wait + ")");
						countdown(openYZMBoxOject);  //按钮设置倒计时
					}
				}else{
					$this.attr("status",true);
					$this.css({background:'#ff9c00'});
					$.alertTip($(this),"系统繁忙，请重试......");
				}
			},"json");
		}
	});
	
	//验证码倒计时
	function countdown(obj) {  
		if (wait <= 0) {  
			obj.attr("disabled",false);
			obj.text("获取验证码");  
			wait = 60;  
		} else {  
			obj.attr("disabled",true);
			obj.text("重新发送(" + wait + ")");  
			wait--;  
			setTimeout(function() {  
				countdown(obj);  
			},  
			1000)  
		}  
	} 
	
	//校验密码  
	$(".password").blur(function(){
		var $this = $(this);
		verifyPassword($this);  //校验密码格式
	});
	
	//校验密码格式
	function verifyPassword(obj){
		var passwordValue =  $.trim(obj.val());
		if(passwordValue == null || passwordValue.length <= 0 || !/^[0-9a-zA-Z]{6,16}$/.test(passwordValue) || passwordValue.length != $.trim(passwordValue).length ){
			obj.parent().find("p").addClass("error");
			return false;
		}else if(!/[0-9]{1,}/.test(passwordValue) || !/[a-zA-Z]{1,}/.test(passwordValue)){
			obj.parent().find("p").addClass("error");
			return false;
		}else if(passwordValue.length < 6 || passwordValue.length > 20){
			obj.parent().find("p").addClass("error");
			return false;
		}
		obj.parent().find("p").removeClass("error");
		return true;
	}
	
	//校验手机验证码格式
	function verifyCode(obj){
		var codeValue =  $.trim(obj.val());
		obj.parent().find("p").html("<i>*</i>请填写验证码");
		if(codeValue == null || codeValue.length <= 0){
			obj.parent().find("p").addClass("error");
			return false;
		}else{
			obj.parent().find("p").removeClass("error");
			return true;
		}
	}
	
	
	//协议选择操作
	$(".agreement").on("click",function(){
		var $this = $(this);
		var status = $this.attr("checked");
		if(status == null || status == "undefined"){
			$this.removeAttr("checked");
			$this.parent().parent().parent().find(".fastsignin").attr("status",false);
			$this.parent().parent().parent().find(".fastsignin").css({background:'#D3D3D3'});
		}else{
			$this.attr("checked","checked");
			$this.parent().parent().parent().find(".fastsignin").attr("status",true);
			$this.parent().parent().parent().find(".fastsignin").css({background:'url('+basepath+'static/images/btnbg_02.gif) repeat-x'});
		}
	});
	
	//注册
	$(".fastsignin").on("click", function(){
		
		/*art.dialog({
		    title: '提示',
		    content:'为响应政府整顿配资市场，本站已经暂时关闭注册功能，如有疑问请联系客服热线400-020-0158',
		    width:400,
		    cancelVal: '关闭',
		    background: '#BBBBBB', // 背景色 
		    opacity:0.87, 
		    zIndex:9900,
		    lock:true,
		    cancel: true //为true等价于function(){}
		});
		return;*/
		
		var $this = $(this);
		if($(this).attr("status") == "true"){
			$this.attr("status",false);
			$this.text("正在注册");
			var mobileObject =  $this.parent().find(".mobile");
			var mobileValue =  $.trim(mobileObject.val());
			var passwordObject =  $this.parent().find(".password");
			var passwordValue =  passwordObject.val();
			var codeObject =  $this.parent().find(".code");
			var codeValue =  $.trim(codeObject.val());
			//校验手机号码、验证码、密码
			if(!verifyMobile(mobileObject) || !verifyCode(codeObject) || !verifyPassword(passwordObject)){
				$this.attr("status",true);
				$this.text("免费注册");
				return;
			}
			
			$.post(basepath+"signin_operation",{ 'source':1,'mobile':mobileValue,'code':codeValue,'password':passwordValue,ajax:1},function(data){ //注册
				if(data.success){
					if(data.message!="" && data.message!=null){
						if(data.message=="mobileIsExist"){
							mobileObject.parent().find("p").addClass("error");
							mobileObject.parent().find("p").html("<i>*</i>该手机号码已经存在");
						}else if(data.message=="codeError"){
							codeObject.parent().find("p").addClass("error");
							codeObject.parent().find("p").html("<i>*</i>验证码错误,请重新获取");
						}else if(data.message=="codeTimeOut"){
							codeObject.parent().find("p").addClass("error");
							codeObject.parent().find("p").html("<i>*</i>验证码已失效,请重新获取");
						}
						$this.attr("status",true);
						$this.text("免费注册");
					}else{
						/*//达人论股系统登录
						discussStockLogin(data.data.key,function(){
							var form = $("<form action='"+basepath+"signinsucess"+"' method='post'><input type='hidden' name='volumeNum' value='" + data.data.volumeNum + "'/><input type='hidden' name='volumePrice' value='" + data.data.volumePrice + "'/><input type='hidden' name='type' value='1'/></form>");
							form.appendTo(document.body).submit();
						});*/
						alert("恭喜，注册成功！");	
						//隐藏向CAS服务端提交登录的用户名和密码
						$("#loginUsername").val(mobileValue);
						$("#loginPassword").val(passwordValue);
						$("#loginForm").submit();
					}
				}else{
					$this.attr("status",true);
					$this.text("免费注册");
					showMsgDialog("提示","系统繁忙，请重试......");
				}
			},"json");
		}
	});
	
	var form = $(".form");
	
	// 回车事件
	form.on("keypress", "input", function(event){
		if(event.keyCode == 13){
			$(".fastsignin").trigger("click");
			return false;
		}
	});
	
	var formBox = $(".formBox");
	
	// 回车事件
	formBox.on("keypress", "input", function(event){
		if(event.keyCode == 13){
			$(".fastsignin").trigger("click");
			return false;
		}
	})
	
	//初始化定义光标位置
	$(".mobile")[0].focus();
	
	//关闭弹出框
	$(".bazarSigninClose").on("click", function(){
		var $this = $(this);
		
		$this.parent().parent().css({display: "none"});
		
		var mobileObject = $this.parent().parent().find(".mobile");
		mobileObject.val("");
		mobileObject.parent().find("p").removeClass("error");
		mobileObject.parent().find("p").html("<i>*</i>请填写手机号码");
		
		var passwordObject = $this.parent().parent().find(".password");
		passwordObject.val("");
		passwordObject.parent().find("p").removeClass("error");
		passwordObject.parent().find("p").html("<i>*</i>6-16位数字和字母组成");
		
		var codeObject = $this.parent().parent().find(".code");
		codeObject.val("");
		codeObject.parent().find("p").removeClass("error");
		codeObject.parent().find("p").html("<i>*</i>请填写验证码");
		
		var agreementObject = $this.parent().parent().find(".agreement");
		agreementObject.attr("checked","checked");
		$this.parent().find(".fastsignin").attr("status",true);
		$this.parent().find(".fastsignin").css({background:'url('+basepath+'static/images/btnbg_02.gif) repeat-x'});
		$this.parent().find(".fastsignin").text("免费注册");
	});
	
	//弹出注册框
	$(".showSigninBox").on("click", function(){

		$(".bazarSigninClose").parent().parent().css({display: "block"});
		
		var $this = $(".form");
		
		var mobileObject = $this.parent().parent().find(".mobile");
		mobileObject.val("");
		mobileObject.parent().find("p").removeClass("error");
		mobileObject.parent().find("p").html("<i>*</i>请填写手机号码");
		
		var passwordObject = $this.parent().parent().find(".password");
		passwordObject.val("");
		passwordObject.parent().find("p").removeClass("error");
		passwordObject.parent().find("p").html("<i>*</i>6-16位数字和字母组成");
		
		var codeObject = $this.parent().parent().find(".code");
		codeObject.val("");
		codeObject.parent().find("p").removeClass("error");
		codeObject.parent().find("p").html("<i>*</i>请填写验证码");
		
		var agreementObject = $this.parent().parent().find(".agreement");
		agreementObject.attr("checked","checked");
		$this.parent().find(".fastsignin").attr("status",true);
		$this.parent().find(".fastsignin").css({background:'url('+basepath+'static/images/btnbg_02.gif) repeat-x'});
		$this.parent().find(".fastsignin").text("免费注册");
	});
});

//显示协议
function showAgreement(){
	var htmlHeight = 685;  //网页高度
	var htmlWidth = 1221;  //网页宽度
	var iLeft = (window.screen.width-10-htmlWidth)/2;  //获得窗口的水平位置;  
	window.open(basepath+'websiteAgreement','维胜网站服务协议','height='+htmlHeight+',innerHeight='+htmlHeight+',width='+htmlWidth+',innerWidth='+htmlWidth+',top=0,left='+iLeft+',toolbar=no,menubar=no,scrollbars=yes,resizeable=no,location=no,status=no');  
}

//登录验证函数, 由 onsubmit 事件触发
function loginValidate(){
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
	var backData=$.trim($("#backData").val());
	var source = $.trim($("#source").val());
	var service = basepath+"user/account";
	if(backData && source == '1'){  //系统内部跳转：backData（url）非空，source（来源）为空
		service = backData;
	}
	var _services = 'service='
			+ encodeURIComponent(service);
	$.getScript(casServerLoginUrl+'?'
			+ _services + '&get-lt=true&n=' + new Date().getTime(), function() {
		// 将返回的 _loginTicket 变量设置到  input name="lt" 的value中。
		$('#J_LoginTicket').val(_loginTicket);
		$('#J_FlowExecutionKey').val(_flowExecutionKey);
	});
};