var user={
		isNeedCode:false,
		loginCodeDivStr:"<li class='codeDivLi'><input type='text' class='fl_lg_code divCode' value='验证码' ><img src='validate.code' class='fl_lg_codeimg divCode' onclick='user.refreshCode();'><em class='fl_lg_codefont'></em></li>",
		userCallback:null,
		account:{userName:null,generalizeUid:null},
		isLogin:function(callback){
			var result = false;
			//判断是否登录
			$.ajax({url : basepath+"/user_login_check",async : false, type : "POST", dataType : "json",  
		        success : function(data) {  
		        	if(data.success){
						if(data.message!="" && data.message!=null){
							result = false;
						}else{
							user.account.userName = data.data.userName;
							user.account.generalizeUid = data.data.generalizeUid;
							user.refreshPageHeader(callback);  //刷新
							result = true;
						}
						user.isNeedCode = data.data.isNeedCode;
					}
		        }}); 
			return result;
		},
		close:function(callback){
			//关闭登录弹出框
			$("#floatlayerDiv").remove();
		},
		checkAndShowLogin:function(callback){
			//检测是否有显示注册弹窗口
			if($("#floatlayerDiv") != null && $("#floatlayerDiv").length > 0){
				user.close(callback);
			}
			//检测是否显示弹窗口
			if(!user.isLogin(callback)){  //判断是否登录
				user.userCallback = callback;
				user.createLoginDiv(callback);
				//初始化定义光标位置
				$(".divUserName").focus();
				return false;
			}else{
				if(callback != null){
					callback();
				}
				return true;
			}
		},
		loginDiv:function(callback){  //登录
			var emailForm  = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;	 //邮箱验证规则
			var mobileForm = /^(((13[0-9])|(14[7])|(15[0-9])|(17[6-7])|(18[0-9]))+\d{8})$/; //手机号码规则
			var loginName = $.trim($(".divUserName").val());
			var password = $.trim($(".divPassword").val());
			var codeDivLi = $(".codeDivLi");
			var code = codeDivLi == null || codeDivLi.length <= 0? null : $.trim($(".divCode").val());
			var $this = $(".divLogin");
			var status = $this.attr("status");  
			if(status == "true"){
				$this.attr("status",false);
				$this.text("正在登录");
				if(loginName == null || loginName.length <= 0 || loginName == initLoginText.initLoginNameText){
					$.alertTip($(".divUserName"),"帐号不能空 !");
					$this.attr("status",true);
					$this.text("登录");
					user.refreshCode();//刷新验证码
					$(".divUserName").focus();
					return;
				}else if(!((emailForm.test(loginName) && isNaN(loginName) && !loginName.match(mobileForm)) || (loginName.match(mobileForm) && loginName.length == 11 && !isNaN(loginName) && !emailForm.test(loginName)))){
					$.alertTip($(".divUserName"),"账号格式有误 !");
					$this.attr("status",true);
					$this.text("登录");
					user.refreshCode();//刷新验证码
					return;
				}else if(password == null || password.length <= 0){
					$.alertTip($(".divPassword"),"密码不能空 !");
					$this.attr("status",true);
					$this.text("登录");
					user.refreshCode();//刷新验证码
					$(".divPassword").focus();
					return;
				}else if((codeDivLi != null && codeDivLi.length > 0) && (code == null || code.length <= 0 || code == initLoginText.initCodeText)){
					$.alertTip($(".divCode"),"验证码不能空 !");
					$this.attr("status",true);
					$this.text("登录");
					user.refreshCode();//刷新验证码
					$(".divCode").focus();
					return;
				}
				$.post(basepath+"/login_operation",{loginName:loginName,password:password,code:code},function(data){
					if(data.success){
						if(data.message!="" && data.message!=null){
							var codeDivObject = $(".codeDivLi");
							if(data.data != null &&  data.data.isNeedCode && codeDivObject != null && codeDivObject.length <= 0){
								$(".divPassword").parent().after(user.loginCodeDivStr);
							}
							if(data.message=="codeError"){
								$.alertTip($(".divCode"),"验证码错误 !");
							}else if(data.message=="loginFail"){
								$.alertTip($(".divLogin"),"帐号或密码错误 !");
								$(".divPassword").val("");
							}
							user.refreshCode();
						}else{
							user.isLogin(user.userCallback);  //刷新
							user.close(user.userCallback);
							//达人论股系统登录
							discussStockLogin(data.data.key,user.userCallback);
							if(user.userCallback != null){
								user.userCallback();
							}
						}
					}else{
						$.alertTip($(".divLogin"),"系统繁忙，请重试......");
					}
					$this.attr("status",true);
					$this.text("登录");
				},"json");
			}
		},
		createLoginDiv:function(callback){
			//创建登录DIV
			var loginDivStr = "<div class='floatlayer' id='floatlayerDiv'>";
			loginDivStr = loginDivStr +"<div class='fl_mask' style='display:block;'></div>";
			loginDivStr = loginDivStr +"<div class='fl_box fl_uc_bank' style='display:block;'>";
			loginDivStr = loginDivStr +"<form id='loginDivForm' class='loginDivForm' action='' method='post'>";
			loginDivStr = loginDivStr +"<div class='fl_navtitle'><h3 class='fl_logintitle'>用户登录</h3><a href='javascript:void(0);' class='close' onclick='user.close();' ></a></div>";
			loginDivStr = loginDivStr +"<ul class='fl_login'>";
			loginDivStr = loginDivStr +"<li><i class='fl_lg_user'></i><input type='text' class='fl_lg_ip divUserName' value='帐号' ><em></em></li>";
			loginDivStr = loginDivStr +"<li><i class='fl_lg_password'></i><input type='password' class='fl_lg_ip divPassword'><em class='divPwdText'>密码</em></li>";
			if(user.isNeedCode){
				loginDivStr = loginDivStr +"<li class='codeDivLi'><input type='text' class='fl_lg_code divCode' value='验证码' ><img src='validate.code' class='fl_lg_codeimg divCode' onclick='user.refreshCode();'><em class='fl_lg_codefont'></em></li>";
			}
			loginDivStr = loginDivStr +"</ul>";
			loginDivStr = loginDivStr +"<div class='fl_loginbtn'><a href='javascript:void(0);' status='true' class='fl_loginbtn divLogin' onclick='user.loginDiv(user.userCallback);'>登录</a></div>";
			loginDivStr = loginDivStr +"<div class='fl_loginfont'><a href='javascript:void(0);' onclick='user.checkAndShowSignin(user.userCallback);' class='fl'>免费注册</a><a href='"+basepath+"forgetpw' class='fr'>忘记密码</a></div>";
			loginDivStr = loginDivStr +"</form>";
			loginDivStr = loginDivStr +"</div>";
			loginDivStr = loginDivStr +"</div>";
			$("#floatlayerDiv").remove();
			$(document.body).append(loginDivStr);
		},refreshPageHeader:function(callback){
			
			$("#lg_username").text(user.account.userName);
			$("#lg_username_h2").text(user.account.userName);
			$("#loginhead").show();
			$("#tologin").hide();
			/*var stockli = $("#stockli");
			if(stockli == null || stockli.length <= 0){
				$("#homeli").parent().after("<li><a href='"+basepath+"day?enter=0'  id='stockli' >股票操盘</a></li>");
			}*/
		},refreshCode:function(callback){
			$(".divCode").attr("src",basepath+"/validate.code?1=" + Math.random()*10000);
		},
		checkAndShowSignin:function(callback){
			//检测是否有显示登录弹窗口
			if($("#floatlayerDiv") != null && $("#floatlayerDiv").length > 0){
				user.close(callback);
			}
			//检测是否显示弹窗口
			if(!user.isLogin(callback)){  //判断是否登录
				user.userCallback = callback;
				user.createSigninDiv(callback);
				//初始化定义光标位置
				$("#divSigMobile").focus();
				return false;
			}else{
				if(callback != null){
					callback();
				}
				return true;
			}
		},
		signinDiv:function(callback){  //注册
			var mobile =  $.trim($("#divSigMobile").val());
			var code =  $.trim($("#sigCode").val());
			var password =  $("#sigPassword").val();
			var affirmPassword =  $.trim($("#sigAffirmpassword").val());
			var parentGeneralizeId = $.trim($("#sigGeneralizeId").val());
			var divSignin = $("#divSignin");
			if(divSignin.attr("status") == "true"){
				divSignin.attr("status",false);
				divSignin.text("正在注册");
				//校验手机号码、验证码、密码、确认密码
				if(!verifyMobile($("#divSigMobile")) || !verifyCode($("#sigCode")) || !verifyPassword($("#sigPassword")) || !verifyAffirmPassword($("#sigAffirmpassword"),password)){
					divSignin.attr("status",true);
					divSignin.text("注册");
					return;
				}
				$.post(basepath+"signin_operation",{source:1,mobile:mobile,code:code,password:password,parentGeneralizeId:parentGeneralizeId,ajax:1},function(data){ //注册
					if(data.success){
						if(data.message!="" && data.message!=null){
							if(data.message=="mobileIsExist"){
								$.alertTip($("#divSigMobile"),"该手机号码已经存在 ！");
							}else if(data.message=="codeError"){
								$.alertTip($("#sigCode"),"验证码错误,请重新获取验证码！");
							}else if(data.message=="codeTimeOut"){
								$.alertTip($("#sigCode"),"验证码已失效,请重新获取验证码！");
							}else{
								$.alertTip($("#sigGeneralizeId"),"推广码错误  ！");
							}
							divSignin.attr("status",true);
							divSignin.text("立即注册");
						}else{
							user.isLogin(user.userCallback);  //刷新
							user.close(user.userCallback);
							if(user.userCallback != null){
								user.userCallback();   //执行回调函数
							}
						}
					}else{
						divSignin.attr("status",true);
						divSignin.text("立即注册");
						$.alertTip($("#divSignin"),"系统繁忙，请重试......");
					}
				},"json");
			}
		},
		createSigninDiv:function(callback){
			//创建注册DIV
			var signinDivStr = "<div class='floatlayer' id='floatlayerDiv'>";
			
			/*注册验证码弹窗口*/
			signinDivStr = signinDivStr +"<div class='fl_mask yzm_fl_mask' style='display: none; z-index:1010;'></div>";
			signinDivStr = signinDivStr +"<div class='fl_box fl_rg_yzm' id='yzm_box' name='yzm_box' style='display:none;z-index:1050;'>";
			signinDivStr = signinDivStr +"<div class='fl_navtitle'>";
			signinDivStr = signinDivStr +"<h3>请先输入图形验证码</h3>";
			signinDivStr = signinDivStr +"<a href='javascript:void(0);'  class='close yzm_box_close'></a>";
			signinDivStr = signinDivStr +"</div>";
			signinDivStr = signinDivStr +"<div class='fl_uc_main'>";
			signinDivStr = signinDivStr +"<ul class='fl_uc_list' style='padding:10px 0;'>";
			signinDivStr = signinDivStr +"<li>";
			signinDivStr = signinDivStr +"<input type='text' id='yzm' name='yzm' maxlength='5' style='width:120px;'>";
			signinDivStr = signinDivStr +"<img src='"+basepath+"validate.code' id='refresh_code' name='refresh_code' class='refresh_code' width='100px' height='34px'>";
			signinDivStr = signinDivStr +"<a href='javascript:void(0);' class='refresh_code'>点击换一张</a>";
			signinDivStr = signinDivStr +"</li>";
			signinDivStr = signinDivStr +"</ul>";
			signinDivStr = signinDivStr +"</div>";
			signinDivStr = signinDivStr +"<div class='fl_uc_btn'>";
			signinDivStr = signinDivStr +"<a href='javascript:void(0);' status='true' id='getSMSCode' name='getSMSCode' class='fl_uc_surebtn'>确定</a>";
			signinDivStr = signinDivStr +"</div>"
			signinDivStr = signinDivStr +"</div>";
			
			signinDivStr = signinDivStr +"<div class='fl_mask' style='display:block;'></div>";
			signinDivStr = signinDivStr +"<div class='fl_box fl_registbox' style='display:block;'>";
			signinDivStr = signinDivStr +"<form id='signinDivForm' class='signinDivForm' action='' method='post'>";
			signinDivStr = signinDivStr +"<div class='fl_navtitle'>";
			signinDivStr = signinDivStr +"<h3 class='fl_logintitle'>用户注册</h3><a href='javascript:void(0);' class='close' onclick='user.close();'></a>";
			signinDivStr = signinDivStr +"</div>";
			signinDivStr = signinDivStr +"<ul class='fl_login fl_regist'>";
			signinDivStr = signinDivStr +"<li><i class='fl_lg_user'></i><input type='text' class='fl_lg_ip' id='divSigMobile' maxlength='11'><em class='divPwdText'>手机号码</em></li>";
			signinDivStr = signinDivStr +"<li class='fl_lg_ip2'><i class='fl_lg_codeip'></i><input type='text' id='sigCode' maxlength='6' class='fl_lg_ip'><em class='divPwdText'>验证码</em><a href='javascript:void(0);' status='true' id='openYZMBox' name='openYZMBox'>获取验证码</a></li>";
			signinDivStr = signinDivStr +"<li><i class='fl_lg_password'></i><input type='password' id='sigPassword' maxlength='16' class='fl_lg_ip'><em class='divPwdText'>密码</em></li>";
			signinDivStr = signinDivStr +"<li><i class='fl_lg_password'></i><input type='password' id='sigAffirmpassword' maxlength='16' class='fl_lg_ip'><em class='divPwdText'>确认密码</em></li>";
			if(user.account.generalizeUid && user.account.generalizeUid != 'null' && user.account.generalizeUid != 'undefined' && user.account.generalizeUid != ''){
				signinDivStr = signinDivStr +"<li><i class='fl_rg_code'></i><input type='text' id='sigGeneralizeId' readonly='readonly' maxlength='6' value='"+user.account.generalizeUid+"' class='fl_lg_ip'><em class='divPwdText'>推广码，可不填</em></li>";
			}else{
				signinDivStr = signinDivStr +"<li><i class='fl_rg_code'></i><input type='text' id='sigGeneralizeId' maxlength='6' class='fl_lg_ip'><em class='divPwdText'>推广码，可不填</em></li>";
			}
			signinDivStr = signinDivStr +"</ul>";
			signinDivStr = signinDivStr +"<div class='fl_loginbtn'>";
			signinDivStr = signinDivStr +"<a href='javascript:void(0);' status='true' class='fl_loginbtn' id='divSignin' onclick='user.signinDiv(user.userCallback);'>注册</a>";
			signinDivStr = signinDivStr +"</div>";
			signinDivStr = signinDivStr +"<div class='fl_loginfont'>";
			signinDivStr = signinDivStr +"<a href='javascript:void(0);' onclick='user.checkAndShowLogin(user.userCallback);' class='fl'>登录</a><a href='"+basepath+"forgetpw' class='fr'>忘记密码</a>";
			signinDivStr = signinDivStr +"</div>";
			signinDivStr = signinDivStr +"</form>";
			signinDivStr = signinDivStr +"</div>";
			$("#floatlayerDiv").remove();
			$(document.body).append(signinDivStr);
		}
}

//弹窗口初始化常量文本提示
var initLoginText={
	initLoginNameText:"帐号",
	initPasswordText:"密码",
	initCodeText:"验证码"
}

$(".divUserName").live("focus",function(){
	var $this = $(this);
	if($.trim($this.val()) == initLoginText.initLoginNameText){
		$this.val("");
	}
});

$(".divUserName").live("blur",function(){
	var $this = $(this);
	if(!$.trim($this.val())){
		$this.val(initLoginText.initLoginNameText);
	}
});

$(".divPassword").live("focus",function(){
	 var $this = $(this);
	if($.trim($(".divPwdText").text()) == initLoginText.initPasswordText){
		$(".divPwdText").text("");
	}
});

$(".divPassword").live("blur",function(){
	 var $this = $(this);
	if(!$.trim($(".divPwdText").text()) && !$.trim($(".divPassword").val())){
		$(".divPwdText").text(initLoginText.initPasswordText);
	}
});

$(".divPwdText").live("click",function(){
	$(".divPassword").focus();
});

$(".divCode").live("focus",function(){
	if($.trim($(this).val()) == initLoginText.initCodeText){
		$(this).val("");
	}
});

$(".divCode").live("blur",function(){
	if(!$.trim($(this).val())){
		$(this).val(initLoginText.initCodeText);
	}
});

var loginDivForm = $(".loginDivForm");
//回车事件
loginDivForm.live("keypress", "input", function(event){
	if(event.keyCode == 13){
		$("#divSignin").trigger("click");
		user.loginDiv();
		return false;
	}
});

//注册弹窗口初始化常量文本提示
var initSignInText={
	initMobileText:"手机号码",
	initCodeText:"验证码",
	initPasswordText:"密码",
	initAffirmPasswordText:"确认密码",
	initGeneralizeCodeText:"推广码，可不填"
}

//手机号码focus校验
$('#divSigMobile').live('focus',function(){
	 var $this = $(this);
	if($.trim($this.parent().find("em").text()) == initSignInText.initMobileText){
		$this.parent().find("em").text("");
	}
});

//手机号码blur校验
$("#divSigMobile").live('blur',function(){
	var $this = $(this);
	var mobile =  $.trim($this.val());
	if(mobile == null || mobile.length <= 0){  //判断手机号码是否为空  
		$this.parent().find("em").text(initSignInText.initMobileText);
		return;
	}
	verifyMobile($this);
});

//手机号码格式检验
function verifyMobile(obj){
	var mobile =  $.trim(obj.val());
	if(mobile == null || mobile.length <= 0){  //判断手机号码是否为空  
		obj.parent().find("em").text(initSignInText.initMobileText);
		$.alertTip(obj,"请输入您常用的手机号码");
		return false;
	}else if(!mobile.match(/^(((13[0-9])|(14[7])|(15[0-9])|(17[0-9])|(18[0-9]))+\d{8})$/)){  //判断手机号码是否正确
		$.alertTip(obj,"请输入正确的手机号码");
		return false;
	}
	return true;
}

//打开短信验证码框
$("#openYZMBox").live("click",function(){
	var $this = $(this);
	var status =  $(this).attr("status"); 
	if(status == "true"){
		if(verifyMobile($("#divSigMobile"))){
			$(".yzm_fl_mask").show();
			$("#yzm_box").show();
		}
	}
});

//关闭短信验证码框
function colseYZMBox(){
	$(".yzm_fl_mask").hide();
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

//获取验证码
$("#getSMSCode").live("click", function(){
	var $this = $(this);
	var status =  $(this).attr("status");  
	if(status == "true"){
		$this.attr("status",false);
		$this.css({background:'#D3D3D3'});
		var yzm = $("#yzm").val();
		var mobile = $("#divSigMobile").val();
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
					}else{
						colseYZMBox();
						$this.attr("status",true);
						$this.css({background:'#ff9c00'});
						$("#openYZMBox").css({background:'#D3D3D3'});
						$("#openYZMBox").text(wait+"秒后重新获取");
						$.alertTip($("#openYZMBox"),data.message == "highOperation" ? "操作过于频繁，请稍后再重试！" : "每天只能获取5次验证码 ！");
						keepTimeFunction($("#openYZMBox"));  //按钮设置倒计时
					}
				}else{
					colseYZMBox();
					$this.attr("status",true);
					$this.css({background:'#ff9c00'});
					$("#openYZMBox").css({background:'#D3D3D3'});
					$("#openYZMBox").text(wait+"秒后重新获取");
					keepTimeFunction($("#openYZMBox"));  //按钮设置倒计时
				}
			}else{
				$this.attr("status",true);
				$this.css({background:'#ff9c00'});
				$.alertTip($this,"系统繁忙，请重试......");
			}
		},"json");
	}
});

//验证码倒计时
function keepTimeFunction(obj) {  
	if (wait <= 0) {  
		obj.attr("status",true);
		obj.text("获取验证码");  
		obj.css({background:'#34B3E0'});
		wait = 60;  
	} else {  
		obj.attr("status",false);
		obj.text("重新发送(" + wait + ")");  
		wait--;  
		setTimeout(function() {  
			keepTimeFunction(obj)  
		},  
		1000)  
	}  
};

//手机验证码focus校验
$('#sigCode').live('focus',function(){
	var $this = $(this);
	if($.trim($this.parent().find("em").text()) == initSignInText.initCodeText){
		$this.parent().find("em").text('');
	}
});

//手机验证码blur校验
$("#sigCode").live('blur',function(){
	var $this = $(this);
	if($.trim($this.val()) == null || $.trim($this.val()) <= 0){  //判断手机号码是否为空  
		$this.parent().find("em").text(initSignInText.initCodeText);
		return;
	} 
	verifyCode($this);  //校验手机验证码
	
});

//手机验证码格式校验
function verifyCode(obj){
	var code =  $.trim(obj.val());
	if(code == null || code.length <= 0){
		obj.parent().find("em").text(initSignInText.initCodeText);
		$.alertTip(obj,"请输入手机号码验证码");
		return false;
	}
	return true;
};

//密码focus校验  
$('#sigPassword').live('focus',function(){
	var $this = $(this);
	if($.trim($this.parent().find("em").text()) == initSignInText.initPasswordText){
		$this.parent().find("em").text("");
	}
});

//密码blur校验  
$("#sigPassword").live('blur',function(){
	var $this = $(this);
	if($.trim($this.val()) == null || $.trim($this.val()) <= 0){  //判断手机号码是否为空  
		$this.parent().find("em").text(initSignInText.initPasswordText);
		return;
	} 
	verifyPassword($this);  //校验密码格式
});

//密码格式校验
function verifyPassword(obj){
	var password =  obj.val();
	if(password == null || password.length <= 0){
		obj.parent().find("em").text(initSignInText.initPasswordText);
		$.alertTip(obj,"6-16位数字和字母组成");
		return false;
	}else if(!/^[0-9a-zA-Z]{6,16}$/.test(password) || password.length != $.trim(password).length ){
		$.alertTip(obj,"6-16位数字和字母组成");
		return false;
	}else if(!/[0-9]{1,}/.test(password) || !/[a-zA-Z]{1,}/.test(password)){
		$.alertTip(obj,"6-16位数字和字母组成");
		return false;
	}else if(password.length < 6 || password.length > 20){
		$.alertTip(obj,"6-16位数字和字母组成");
		return false;
	}
	return true;
}

//确认密码focus校验 
$('#sigAffirmpassword').live('focus',function(){
	var $this = $(this);
	if($.trim($this.parent().find("em").text()) == initSignInText.initAffirmPasswordText){
		$this.parent().find("em").text("");
	}
});

//确认密码blur校验  
$("#sigAffirmpassword").live('blur',function(){
	var $this = $(this);
	if($.trim($this.val()) == null || $.trim($this.val()) <= 0){  //判断手机号码是否为空  
		$this.parent().find("em").text(initSignInText.initAffirmPasswordText);
		return;
	} 
	verifyAffirmPassword($this,$('#sigPassword').val());   //校验确认密码 数据
});

//确认密码数据校验
function verifyAffirmPassword(obj,password){
	var affirmPassword =  obj.val();
	if(affirmPassword == null || affirmPassword.length <= 0){
		$this.parent().find("em").text(initSignInText.initAffirmPasswordText);
		$.alertTip(obj,"请输入二次密码");
		return false;
	}else if(affirmPassword != password){
		$.alertTip(obj,"两次密码不一致");
		return false;
	}
	return true;
}

//确认推广码focus校验  
$('#sigGeneralizeId').live('focus',function(){
	var $this = $(this);
	if($.trim($this.parent().find("em").text()) == initSignInText.initGeneralizeCodeText){
		$this.parent().find("em").text("");
	}
});

//确认推广码blur校验  
$("#sigGeneralizeId").live('blur',function(){
	var $this = $(this);
	var generalizeId =  $.trim($this.val());
	if(generalizeId == null || generalizeId.length <= 0){
		$this.parent().find("em").text(initSignInText.initGeneralizeCodeText);
	}
});

//限制推广码数据输入
$("#sigGeneralizeId").live('keypress',function(e){
	var k = e.keyCode || e.which;
	if(k>=48&&k<=57||k==8){
		return true;
	}
	return false;
});

$("#sigGeneralizeId").live('keyup',function(e){
	
});

var signinDivForm = $(".signinDivForm");
//回车事件
signinDivForm.live("keypress", "input", function(event){
	if(event.keyCode == 13){
		$("#divSignin").trigger("click");
		return false;
	}
});



