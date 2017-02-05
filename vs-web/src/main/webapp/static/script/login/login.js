$(document).ready(function(){
	//设置标题
	$(".headerTitle").text("用户登录");
	//在header.jsp的class为.tzdrlogo前面加一段html
	var headerInser = "<li class='logined'>还不是会员？<a  id='register' href='"+basepath+"signin' style='padding-left:5px;'>立即注册</a><i style='padding:0 5px; color:#ff8d1b;'>|</i><a  id='register' href='"+basepath+"' >返回首页</a></li>";
	$(".tzdrlogo").before(headerInser);
	
	//邮箱验证规则
	var emailForm  = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;	
	
	//手机号码规则
	var mobileForm = /^(((13[0-9])|(14[7])|(15[0-9])|(17[0-9])|(18[0-9]))+\d{8})$/;
	
	//初始化文本提示信息
	var initLoginNameText = "帐号";
	
	var initCodeText = "验证码";
	
	//刷新验证码
	$(".validateCode").on("click", function(){
		var $this = $(this);
		$this.attr("src",basepath+"/validate.code?1=" + Math.random()*10000);
	})
	
	//监听键盘事件
	 $('.userName').bind("keydown",function(e){
		 var $this = $(this);
		 var keyCode = e.keyCode || e.which;
		 if($this.val() && $this.val().length == 1 && keyCode == 8 || $.trim($this.val()) == initLoginNameText){
				$(".clean").css({display: "none"});
		}else{
			$(".clean").css({display: "block"});
		}
	});
	
	//点击事件
	$(document).click(function(){
		if($(".userName").val().length > 0 && $.trim($(".userName").val()) != initLoginNameText){
			$(".clean").css({display: "block"});
		}else{
			$(".clean").css({display: "none"});
		}
	});
	
	$('.userName').focus(function(){
		 var $this = $(this);
		if($.trim($this.val()) == initLoginNameText){
			$this.val("");
		}
	});
	
	$('.userName').blur(function(){
		 var $this = $(this);
		if(!$.trim($this.val())){
			$this.val(initLoginNameText);
		}
	});
	
	$('.password').focus(function(){
		 var $this = $(this);
		 if($.trim($(".password").val()).length <= 0 && $(".pwdText").css("display") == "block"){
			$(".pwdText").css({display: "none"});
		}
	});
	
	$('.password').blur(function(){
		 var $this = $(this);
		 if($.trim($(".password").val()).length <= 0 && $(".pwdText").css("display") == "none"){
			$(".pwdText").css({display: "block"});
		 }
	});
	
	$(".pwdText").live("click",function(){
		$('.password').focus();
	});
	
	$('.codeText').live("focus",function(){
		 var $this = $(this);
		 $(".codeText").val("");
		if($.trim($this.val()) == initCodeText){
			$this.val("");
		}
	});
	
	$('.codeText').live("blur",function(){
		 var $this = $(this);
		if(!$.trim($this.val())){
			$this.val(initCodeText);
		}
	});
	
	//清除帐号文本框
	$(".clean").live("click",function(e){
		var $this = $(this);
		$(".userName").val("");
		$(".clean").css({display: ""});
	});
	
	//登录操作
	$(".login").on("click",function(){
		var $this = $(this);
		$this.attr("disabled","disabled");
		$this.text("正在登录");
		var loginName = $.trim($(".userName").val());
		var password = $.trim($(".password").val());
		var codeLi = $(".codeLi");
		var code = codeLi == null || codeLi.length <= 0? null : $.trim($(".codeText").val());
		if(loginName == null || loginName.length <= 0 || loginName == initLoginNameText){
			$this.text("登录");
			$this.removeAttr("disabled");
			$(".loginError").text("请输入帐号");
			$(".userNameLi").css({"margin-top":"30px"});
			if(codeLi != null && codeLi.length > 0){
				$(".validateCode").attr("src",basepath+"/validate.code?1=" + Math.random()*10000);
			}
			$(".userName").focus();
			return;
		}else if(!((emailForm.test(loginName) && isNaN(loginName) && !loginName.match(mobileForm)) || (loginName.match(mobileForm) && loginName.length == 11 && !isNaN(loginName) && !emailForm.test(loginName)))){
			$this.text("登录");
			$this.removeAttr("disabled");
			$(".loginError").text("您输入的账号格式有误");
			$(".userNameLi").css({"margin-top":"30px"});
			if(codeLi != null && codeLi.length > 0){
				$(".validateCode").attr("src",basepath+"/validate.code?1=" + Math.random()*10000);
			}
			$(".userName").focus();
			return;
		}else if(password == null || password.length <= 0){
			$this.text("登录");
			$this.removeAttr("disabled");
			$(".loginError").text("请输入密码");
			$(".userNameLi").css({"margin-top":"30px"});
			if(codeLi != null && codeLi.length > 0){
				$(".validateCode").attr("src",basepath+"/validate.code?1=" + Math.random()*10000);
			}
			$(".password").focus();
			return;
		}else if((codeLi != null && codeLi.length > 0) && (code == null || code.length <= 0 || code == initCodeText)){
			$this.text("登录");
			$this.removeAttr("disabled");
			$(".loginError").text("请输入验证码");
			$(".userNameLi").css({"margin-top":"30px"});
			$this.attr("src",basepath+"/validate.code?1=" + Math.random()*10000);
			$(".codeText").focus();
			return;
		}
		
		$.post(basepath+"/login_operation",{loginName:loginName,password:password,code:code,ajax:1},function(data){
			if(data.success){
				if(data.message!="" && data.message!=null){
					var codeDivObject = $(".codeLi");
					if(data.data != null &&  data.data.isNeedCode && codeDivObject != null && codeDivObject.length <= 0){
						var codeDiv = "<div class='logon-ipt mgt20 codeLi' style='width:160px;'>";
						codeDiv = codeDiv + "<input id='yzm'  class='lg_codeip codeText' name='yzm' maxLength='5' value='验证码'/>";
						codeDiv = codeDiv + "<img class='validateCode' src='validate.code' style=' position:absolute; margin-left:5px;' width='115' height='44'>";
						codeDiv = codeDiv + "</div>";
						$(".passwordLi").after(codeDiv);
					}
					if(data.message=="codeError"){
						$(".loginError").text("验证码错误");
					}else if(data.message=="loginFail"){
						$(".loginError").text("帐号或密码错误");
					}
					$(".userNameLi").css({"margin-top":"30px"});
					$(".password").val("");
					$(".password").focus();
					$this.text("登录");
					$(".validateCode").attr("src",basepath+"validate.code?1=" + Math.random()*10000);
					$this.removeAttr("disabled");
				}else{
					var backData=$.trim($("#backData").val());
					var returnUrl = $.trim($("#returnUrl").val());
					if(backData){
						var form = $("<form action='"+backData+"' method='post'>");
						form.append("<input type=hidden name=key value='" + data.data.key + "'/>");
						form.append("<input type=hidden name=userName value='" + data.data.userName + "'/></form>");
						form.appendTo(document.body).submit();
					}else{
						//达人论股系统登录
						discussStockLogin(data.data.key,function(){
							if(!returnUrl){
								returnUrl =  basepath+"user/account";
								/*if(data.data != null &&  data.data.hasTrade){
									returnUrl =  basepath+"user/account";
								}else{
									returnUrl =  basepath;
								}*/
							}
							var form = $("<form action='"+returnUrl+"' method='post'></form>");
							form.appendTo(document.body).submit();
						});
					}
				}
			}else{
				$this.text("登录");
				$(".validateCode").attr("src",basepath+"validate.code?1=" + Math.random()*10000);
				$this.removeAttr("disabled");
				$(".loginError").text("系统错误......");
			}
		},"json");
	});
	
	var form = $(".form");
	
	// 回车事件
	form.on("keypress", "input", function(event){
		if(event.keyCode == 13){
			$(".login").trigger("click");
			return false;
		}
	})
	
	//初始化定义光标位置
	$(".password").focus();
});

