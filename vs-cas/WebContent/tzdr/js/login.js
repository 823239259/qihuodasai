$(document).ready(function(){
	
	var erroMsg = $("#msg").html();
	if(typeof(erroMsg) != "undefined" && erroMsg.length > 0) {
		$(".password").addClass("error");
		$(".passwordError").html("账号或密码错误");
		$(".passwordError").css({display: "block"});
	}
	
	$('.userName').focus(function(){
		 var $this = $(this);
		$(".userName").removeClass("error");
		$(".usernameError").html("");
		$(".usernameError").css({display: "none"});
	});
	
	$('.userName').blur(function(){
		 var $this = $(this);
		if(!$.trim($this.val())){
			$(".userName").addClass("error");
			$(".usernameError").html("请输入正确的手机号码");
			$(".usernameError").css({display: "block"});
		}
	});
	
	$('.password').focus(function(){
		 var $this = $(this);
		 $(".password").removeClass("error");
		 $(".passwordError").html("");
		 $(".passwordError").css({display: "none"});
	});
	
	$('.password').blur(function(){
		 var $this = $(this);
		 if(!$.trim($this.val())){
			$(".password").addClass("error");
			$(".passwordError").html("请输入密码");
			$(".passwordError").css({display: "block"});
		 }
	});
	
	//登录操作
	$(".loginbtn").on("click",function(){
		var $this = $(this);
		$this.attr("disabled","disabled");
		var loginName = $.trim($(".userName").val());
		var password = $.trim($(".password").val());
		if(loginName == null || loginName.length <= 0){
			$this.removeAttr("disabled");
			$(".userName").addClass("error");
			$(".usernameError").html("请输入正确的手机号码");
			$(".usernameError").css({display: "block"});
			return;
		}else if(password == null || password.length <= 0){
			$this.removeAttr("disabled");
			$(".password").addClass("error");
			$(".passwordError").html("请输入密码");
			$(".passwordError").css({display: "block"});
			return;
		}
		$("#fm1").submit();
	});
	
	var form = $(".form");
	
	// 回车事件
	form.on("keypress", "input", function(event){
		if(event.keyCode == 13){
			$(".loginbtn").trigger("click");
			return false;
		}
	})
});

