$(function () {
	// 是否登录
	flushLoginTicket();
	islogin();
	
	// banner切换
	num = $(".bannerbox a").size();
	i = 0;
	theInt = null;
	$(".bannerbox a").eq(0).fadeIn(500);
	$(".bannernumber a").eq(0).addClass("cur");
	$(".bannernumber a").each(function (i) {
		$(this).click(function () {
			HuanDeng(i);
			Change(i);
		});
	});
	HuanDeng = function (p) {
	clearInterval(theInt);
	theInt = setInterval(function () {
		p++;
		if (p < num) {
			Change(p);
		} else {
			p = 0
			Change(p);
		}
		}, 5000);
	}
	HuanDeng(0);
	function Change(num) {
		$(".bannerbox a").fadeOut(500);
		$(".bannerbox a").eq(num).fadeIn(500);
		$(".bannernumber a").removeClass("cur");
		$(".bannernumber a").eq(num).addClass("cur");
	}
});

var loginValid = false;

//登录表单
var form = $("#loginForm");

// 回车事件
form.on("keypress", "input", function(event){
	if(event.keyCode == 13){
		$("#login").trigger("click");
		return false;
	}
})

//登录操作
$("#login").click(function(){
	var $this = $(this);
	$this.text("正在登录");
	$this.attr("disabled","disabled");
	var loginName = $.trim($("#username").val());
	var password = $.trim($("#password").val());
	if(loginName == null || loginName.length <= 0){
		$this.text("立即登录");
		$this.removeAttr("disabled");
		$("#username").focus();
		return;
	}else if(password == null || password.length <= 0){
		$this.text("立即登录");
		$this.removeAttr("disabled");
		$("#password").focus();
		return;
	}
	loginValid = true;
	$("#loginForm").submit();
	$this.text("立即登录");
	$this.removeAttr("disabled");
});

// 登录验证函数, 由 onsubmit 事件触发
function loginValidate() {
	if(loginValid) {
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
	return false;
}

function deleteIFrame(iframeName) {
	var iframe = $(iframeName);
	if (iframe) { // 删除用完的iframe，避免页面刷新或前进、后退时，重复执行该iframe的请求
		iframe.remove();
	}
};

// 由于一个 login ticket 只允许使用一次, 当每次登录需要调用该函数刷新 lt
function flushLoginTicket() {
	var _services = 'service=' + encodeURIComponent(basepath+"indexSSO");
	$.getScript(casServerLoginUrl + '?' + _services + '&get-lt=true&n=' + new Date().getTime(), function() {
		// 将返回的 _loginTicket 变量设置到 input name="lt" 的value中。
		$('#J_LoginTicket').val(_loginTicket);
		$('#J_FlowExecutionKey').val(_flowExecutionKey);
	});
};

//判断用户是否登陆
function islogin(){
	$.post(basepath+"homeIslogin",function(data){
		if(data.success){
			$("#logindiv").hide();
			$("#logondiv").show();
			// 账户信息
	    }else{
	    	$("#logindiv").show();
			$("#logondiv").hide();
		}
	},"json");
}
