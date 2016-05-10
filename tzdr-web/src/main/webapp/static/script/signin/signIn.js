// 得到来源网页
var visitrecord_from = null;
var searchUrl = location.search;
var params = searchUrl.substr(searchUrl.indexOf("?") + 1);
if (params) {  //取参数信息
    //from参数必须在推广链接最后http://localhost:8080/peigubao/signin?p=100089&from=http://localhost:8080/peigubao/help?status=3
    var fromstr;
    if (params.indexOf("from=") != -1) {
    	 fromstr = params.substr(params.indexOf("from=") + 5, params.length);
         if (fromstr && fromstr.length > 0) {
             visitrecord_from = decodeURIComponent(fromstr);
         }
    }
}

$(document).ready(function(){
	
	// 进入登录页，则获取login ticket，该函数在下面定义。
	flushLoginTicket(); 
	
	//手机号码focus校验
	$('#mobile').focus(function(){
		var $this = $(this);
		$this.parent().find("p").css({display:"none"});
		$this.parent().find("span").css({display:"block"});
	});
	
	//手机号码blur校验
	$("#mobile").blur(function(){
		var $this = $(this);
		var mobile =  $.trim($this.val());
		verifyMobile($this);
	});

	//手机号码格式检验
	function verifyMobile(obj){
		var mobile =  $.trim(obj.val());
		if(mobile == null || mobile.length <= 0){  //判断手机号码是否为空  
			obj.parent().find("span").css({display:"none"});
			obj.parent().find("p").css({display:"block"}).text("请输入正确的手机号码");
			obj.addClass("rg_l_iperror");
			return false;
		}else if(!mobile.match(/^(((13[0-9])|(14[5|7])|(15[0-3|5-9])|(17[0|6-8])|(18[0-9]))+\d{8})$/)){  //判断手机号码是否正确
			obj.parent().find("span").css({display:"none"});
			obj.parent().find("p").css({display:"block"}).text("请输入正确的手机号码");
			obj.addClass("rg_l_iperror");
			return false;
		}
		obj.parent().find("span").css({display:"none"});
		obj.removeClass("rg_l_iperror");
		obj.parent().find("p").css({display:"none"});
		return true;
	}
	
	//打开短信验证码框
	$("#openYZMBox").live("click",function(){
		var $this = $(this);
		var status =  $(this).attr("status"); 
		if(status == "true"){
			if(verifyMobile($("#mobile"))){
				$(".fl_mask").show();
				$("#yzm_box").show();
				$("#refresh_code").attr("src",basepath+"validate.code?1=" + Math.random()*10000);
			}
		}
	});
	
	//关闭短信验证码框
	function colseYZMBox(){
		$(".fl_mask").hide();
		$("#yzm_box").hide();
		$("#yzm").val('');
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
	$("#getSMSCode").live("click",function(){
		var $this = $(this);
		var status =  $(this).attr("status");  
		if(status == "true"){
			$this.attr("status",false);
			$this.css({background:'#D3D3D3'});
			var yzm = $("#yzm").val();
			var mobile = $("#mobile").val();
			if(yzm == null || yzm.length <= 0){
				$this.attr("status",true);
				$this.css({background:'#ff9c00'});
				$.alertTip($(this),"验证码不能空 ！");
				return;
			}
			$.post(basepath+"send_mobile_code",{mobile:mobile,yzmCode:yzm,ajax:1},function(data){  //获取短信验证码信息
				$("#refresh_code").attr("src",basepath+"validate.code?1=" + Math.random()*10000);
				if(data.success){
					if(data.message!="" && data.message!=null){
						if(data.message == "mobileIsExist"){
							$this.attr("status",true);
							$this.css({background:'#ff9c00'});
							$.alertTip($("#getSMSCode"),"该手机号码已经存在 ！");
							return;
						}else if(data.message == "mobileIsNull" || data.message == "mobileFormatError"){
							$this.attr("status",true);
							$this.css({background:'#ff9c00'});
							$.alertTip($("#getSMSCode"),"手机号码错误 ！");
							return;
						}else if(data.message == "yzmStrError"){
							$this.attr("status",true);
							$this.css({background:'#ff9c00'});
							$.alertTip($("#getSMSCode"),"验证码错误！");
							return;
						}else if(data.message == "highOperation"){
							$this.attr("status",true);
							$this.css({background:'#ff9c00'});
							$.alertTip($("#getSMSCode"),"操作过于频繁，请稍后再重试！");
							return;
						}
						else if(data.message == "sendMobileMessage") {//符合发送短息条件
							colseYZMBox();
							$.post(basepath+"send_mobile_message",{mobile:mobile,yzmCode:yzm,ajax:1},function(resultData){  //获取短信验证码信息
								if (resultData.message=="sendMobileCodeFail"){
									$this.attr("status",true);
									$this.css({background:'#ff9c00'});
									$.alertTip($("#openYZMBox"),"每天只能获取5次验证码 ！");
									 return;
								}else
								{
									
									$this.attr("status",true);
									$this.css({background:'#ff9c00'});
									$("#openYZMBox").css({background:'#D3D3D3'});
									$("#openYZMBox").text(wait+"秒后重新获取");
									// $.alertTip($("#openYZMBox"),"发送成功");
									countdown($("#openYZMBox"));  //按钮设置倒计时
								}
							},"json");
						}
					}else{
						colseYZMBox();
						$this.attr("status",true);
						$this.css({background:'#ff9c00'});
						$("#openYZMBox").css({background:'#D3D3D3'});
						$("#openYZMBox").text(wait+"秒后重新获取");
						countdown($("#openYZMBox"));  //按钮设置倒计时
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
			obj.attr("status",true);
			obj.text("获取验证码");  
			obj.css({background:'#60bffb'});
			wait = 60;  
		} else {  
			obj.attr("status",false);
			obj.text(wait + "秒后重新获取");  
			wait--;  
			setTimeout(function() {  
				countdown(obj);  
			},  
			1000)  
		}  
	} 
	
	//手机验证码focus校验
	$('#code').focus(function(){
		var $this = $(this);
		$this.parent().find("p").css({display:"none"});
		$this.parent().find("span").css({display:"block"});
	});
	
	//手机验证码blur校验
	$("#code").blur(function(){
		var $this = $(this);
		verifyCode($this);  //校验手机验证码
		
	});
	
	//手机验证码格式校验
	function verifyCode(obj){
		var code =  $.trim(obj.val());
		if(code == null || code.length <= 0){
			obj.parent().find("span").css({display:"none"});
			obj.parent().find("p").css({display:"block"}).text("请输入手机验证码");
			obj.addClass("rg_l_iperror");
			return false;
		}else{
			obj.parent().find("span").css({display:"none"});
			obj.removeClass("rg_l_iperror");
			obj.parent().find("p").css({display:"none"});
			return true;
		}
	}
	
	//密码focus校验  
	$('#password').focus(function(){
		var $this = $(this);
		$this.parent().find("p").css({display:"none"});
		$this.parent().find("span").css({display:"block"});
	});
	
	//密码blur校验  
	$("#password").blur(function(){
		var $this = $(this);
		verifyPassword($this);  //校验密码格式
	});
	
	//密码格式校验
	function verifyPassword(obj){
		var password =  obj.val();
		if(password == null || password.length <= 0){
			obj.parent().find("span").css({display:"none"});
			obj.parent().find("p").css({display:"block"}).text("密码必须由6-16位数字和字母组成");
			obj.addClass("rg_l_iperror");
			return false;
		}else if(!/^[0-9a-zA-Z]{6,16}$/.test(password) || password.length != $.trim(password).length ){
			obj.parent().find("span").css({display:"none"});
			obj.parent().find("p").css({display:"block"}).text("密码必须由6-16位数字和字母组成");
			obj.addClass("rg_l_iperror");
			return false;
		}else if(!/[0-9]{1,}/.test(password) || !/[a-zA-Z]{1,}/.test(password)){
			obj.parent().find("span").css({display:"none"});
			obj.parent().find("p").css({display:"block"}).text("密码必须由6-16位数字和字母组成");
			obj.addClass("rg_l_iperror");
			return false;
		}else if(password.length < 6 || password.length > 20){
			obj.parent().find("span").css({display:"none"});
			obj.parent().find("p").css({display:"block"}).text("密码必须由6-16位数字和字母组成");
			obj.addClass("rg_l_iperror");
			return false;
		}
		obj.parent().find("span").css({display:"none"});
		obj.removeClass("rg_l_iperror");
		obj.parent().find("p").css({display:"none"});
		return true;
	}
	
	//确认密码focus校验 
	$('#affirmpassword').focus(function(){
		var $this = $(this);
		$this.parent().find("p").css({display:"none"});
		$this.parent().find("span").css({display:"block"});
	});
	
	//确认密码blur校验  
	$("#affirmpassword").blur(function(){
		var $this = $(this);
		verifyAffirmPassword($this,$('#password').val());   //校验确认密码 数据
	});
	
	//确认密码数据校验
	function verifyAffirmPassword(obj,password){
		var affirmPassword =  obj.val();
		if(affirmPassword == null || affirmPassword.length <= 0){
			obj.parent().find("span").css({display:"none"});
			obj.parent().find("p").css({display:"block"}).text("两次密码不一致");
			obj.addClass("rg_l_iperror");
			return false;
		}else if(affirmPassword != password){
			obj.parent().find("span").css({display:"none"});
			obj.parent().find("p").css({display:"block"}).text("两次密码不一致");
			obj.addClass("rg_l_iperror");
			return false;
		}
		obj.parent().find("span").css({display:"none"});
		obj.removeClass("rg_l_iperror");
		obj.parent().find("p").css({display:"none"});
		return true;
	}
	
	//确认推广码focus校验  
	$('#generalizeId').focus(function(){
		var $this = $(this);
		$this.parent().find("p").css({display:"none"});
		$this.parent().find("span").css({display:"block"});
	});
	
	//确认推广码blur校验  
	$("#generalizeId").blur(function(){
		var $this = $(this);
		$this.parent().find("span").css({display:"none"});
		$this.parent().find("p").css({display:"none"});
	});
	
	//限制推广码数据输入
	$("#generalizeId").keypress(T.numKeyPress).keyup(function() {
		
	});
	
	//协议选择操作
	$("#agreement").on("click",function(){
		var $this = $(this);
		var status = $this.attr("checked");
		if(status == null || status == "undefined"){
			$("#signin").css({background:"#D3D3D3",border:"1px solid #D3D3D3"});
			$this.removeAttr("checked");
			$("#signin").attr("status",false);
		}else{
			$("#signin").css({background:"#ff9c00",border:"1px solid #dd6f00"});
			$this.attr("checked","checked");
			$("#signin").attr("status",true);
		}
	});
	//注册
	$("#signin").on("click", function(){
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
		var mobile =  $.trim($("#mobile").val());
		var source = $.trim($("#source").val());
		var code =  $.trim($("#code").val());
		var password =  $("#password").val();
		var affirmPassword =  $.trim($("#affirmpassword").val());
		var parentGeneralizeId = $.trim($("#generalizeId").val());
		var status = $("#agreement").attr("checked");
		if($(this).attr("status") == "true"){
			$this.attr("status",false);
			$this.text("正在注册");
			//校验手机号码、验证码、密码、确认密码
			if(!verifyMobile($("#mobile")) || !verifyCode($("#code")) || !verifyPassword($("#password")) || !verifyAffirmPassword($("#affirmpassword"),password)){
				$this.attr("status",true);
				$this.text("注册");
				return;
			}
			$.post(basepath+"signin_operation",{source:source,mobile:mobile,code:code,password:password,parentGeneralizeId:parentGeneralizeId,ajax:1},function(data){ //注册
				if(data.success){
					if(data.message!="" && data.message!=null){
						if(data.message=="mobileIsExist"){
							$("#mobile").parent().find("span").css({display:"none"});
							$("#mobile").parent().find("p").css({display:"block"}).text("该手机号码已经存在");
							$("#mobile").addClass("rg_l_iperror");
						}else if(data.message=="codeError"){
							$("#code").parent().find("span").css({display:"none"});
							$("#code").parent().find("p").css({display:"block"}).text("验证码错误,请重新获取验证码");
							$("#code").addClass("rg_l_iperror");
						}else if(data.message=="codeTimeOut"){
							$("#code").parent().find("span").css({display:"none"});
							$("#code").parent().find("p").css({display:"block"}).text("验证码已失效,请重新获取验证码");
							$("#code").addClass("rg_l_iperror");
						}else{
							$("#generalizeId").parent().find("span").css({display:"none"});
							$("#generalizeId").parent().find("p").css({display:"block"}).text("推广码错误");
							$("#generalizeId").addClass("rg_l_iperror");
						}
						$this.attr("status",true);
						$this.text("立即注册");
					}else{
						var backData=$.trim($("#backData").val());
						var source = $.trim($("#source").val());
						
						alert("恭喜，注册成功！");	
						
						if(backData && source == 3){
							var form = $("<form action='"+backData+"' method='post'>");
							form.append("<input type=hidden name=key value='" + data.data.key + "'/>");
							form.append("<input type=hidden name=userName value='" + data.data.userName + "'/></form>");
							form.appendTo(document.body).submit();
						}else{
							$('#backData').val(data.data.from);
							//隐藏向CAS服务端提交登录的用户名和密码
							$("#loginUsername").val(mobile);
							$("#loginPassword").val(password);
							$("#loginForm").submit();
							/*
							//达人论股系统登录
							discussStockLogin(data.data.key,function(){
								var form = $("<form action='"+basepath+"signinsucess' method='post'><input type='hidden' name='volumeNum' value='" + data.data.volumeNum + "'/><input type='hidden' name='volumePrice' value='" + data.data.volumePrice + "'/></form>");
								form.appendTo(document.body).submit();
							});
							*/
						}
					}
				}else{
					$this.attr("status",true);
					$this.text("立即注册");
					showMsgDialog("提示","系统繁忙，请重试......");
				}
			},"json");
		}
	});
	
	var form = $(".form");
	
	// 回车事件
	form.on("keypress", "input", function(event){
		if(event.keyCode == 13){
			$("#signin").trigger("click");
			return false;
		}
	})
	
	//初始化定义光标位置
	$("#mobile").focus();
	
});

//显示协议
function showAgreement(){
	var htmlHeight = 685;  //网页高度
	var htmlWidth = 1221;  //网页宽度
	var iLeft = (window.screen.width-10-htmlWidth)/2;  //获得窗口的水平位置;  
	window.open(basepath+'websiteAgreement','投资达人网站服务协议','height='+htmlHeight+',innerHeight='+htmlHeight+',width='+htmlWidth+',innerWidth='+htmlWidth+',top=0,left='+iLeft+',toolbar=no,menubar=no,scrollbars=yes,resizeable=no,location=no,status=no');  
}

// 登录验证函数, 由 onsubmit 事件触发
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
	if(!backData || backData.length <= 0){
		backData = visitrecord_from;
	}
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

