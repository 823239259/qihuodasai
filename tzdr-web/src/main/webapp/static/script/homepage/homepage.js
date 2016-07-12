$(function () {
	// 是否登录
	flushLoginTicket();
	islogin();
	
	// banner切换
	var num = $("#slide-box a").size();
    var i = 0;
    var theInt = null;
    $("#ad-slider a").eq(0).addClass("on");
    $("#slide-box a").eq(0).fadeIn(500);
    $("#ad-slider a").each(function (i) {
        $(this).click(function () {
            Change(i);
            HuanDeng(i);
        });
    });
    HuanDeng = function (i) {
        clearInterval(theInt);
        theInt = setInterval(function () {
            i++;
            if (i < num) {
                Change(i);
            } else {
                i = 0
                Change(i);
            }
        }, 5000);
    }
    HuanDeng(0);
    function Change(i) {
        $("#slide-box a").fadeOut(500);
        $("#slide-box a").eq(i).fadeIn(500);
        $("#ad-slider a").removeClass("on");
        $("#ad-slider a").eq(i).addClass("on");
        
    }
    // 股市tab切换
	var left_xiangmu   = $(".w_content .w_center_xiangqing .left_xiangmu");
    left_xiangmu.each(function(){
        left_xiangmu.click(function(){
            left_xiangmu.removeClass('on');
            $(this).addClass('on');
        });
    })
	
	// 加载最新公告
	$.ajax({
		url:basepath+"findnews",
		type:'POST',
		data:{},
		dataType:"json",
		success:function(news){
			var newsStr = "";
			$(news).each(function(i){
				newsStr = newsStr + "<li><i></i><a href=\""+basepath+"news/shownews/"+$(this).attr("id")+"\" target=\"_blank\">"+$(this).attr("name")+"</a><em><a href=\""+basepath+"news/shownews/"+$(this).attr("id")+"\" target=\"_blank\">"+$(this).attr("addtime")+"</a></em></li>";
			})
			$('.h_noticlist').html(newsStr);
			
			setTimeout(function(){
				// 最新公告滚动效果
				var box=document.getElementById("h_scroll"),can=true; 
				box.innerHTML+=box.innerHTML; 
				box.onmouseover=function(){can=false}; 
				box.onmouseout=function(){can=true}; 
				new function (){ 
				var stop=box.scrollTop%18==0&&!can; 
				if(!stop)box.scrollTop==parseInt(box.scrollHeight/2)?box.scrollTop=0:box.scrollTop++; 
				setTimeout(arguments.callee,box.scrollTop%17?30:3000); 
				};
			},2000);
			
		}
	});
    
    
});
	
//检测公告
function checkNotice() {
	var noticeid = getCookie("noticeid");
	var loaclNoticeid = $("#noticeid").val();
	if(noticeid === loaclNoticeid) {
		$(".site-notice").remove();
	} else {
		$(".notice-fixed").fadeIn("slow");
	}
} 


//邮箱验证规则
var emailForm  = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
//手机号码规则
var mobileForm = /^(((13[0-9])|(14[7])|(15[0-9])|(17[0-9])|(18[0-9]))+\d{8})$/;
var initLoginNameText = "请输入手机号码";
var initCodeText = "请输入登录密码";
var loginValid = false;

$('#code').live("focus",function(){
	var $this = $(this);
	$("#code").val("");
	if($.trim($this.val()) == initCodeText){
		$this.val("");
	}
});

$('#code').live("blur",function(){
	var $this = $(this);
	if(!$.trim($this.val())){
		$this.val(initCodeText);
	}
});

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
	var codeLi = $("#codeLi");
	var code = codeLi == null || codeLi.length <= 0? null : $.trim($("#code").val());
	if(loginName == null || loginName.length <= 0 || loginName == initLoginNameText){
		$this.text("立即登录");
		$this.removeAttr("disabled");
		$("#uname-txt span").html("请输入手机号码");
		$("#uname-txt").fadeIn("slow").delay(1000).fadeOut("slow");
		$("#validateCode").attr("src",basepath+"validate.code?1=" + Math.random()*10000);
		$("#username").focus();
		return;
	}else if(!((emailForm.test(loginName) && isNaN(loginName) && !loginName.match(mobileForm)) || (loginName.match(mobileForm) && loginName.length == 11 && !isNaN(loginName) && !emailForm.test(loginName)))){
		$this.text("立即登录");
		$this.removeAttr("disabled");
		$("#uname-txt span").html("您输入的账号格式有误");
		$("#uname-txt").fadeIn("slow").delay(1000).fadeOut("slow");
		$("#validateCode").attr("src",basepath+"validate.code?1=" + Math.random()*10000);
		$("#username").focus();
		return;
	}else if(password == null || password.length <= 0){
		$this.text("立即登录");
		$this.removeAttr("disabled");
		$("#pwd-txt span").html("请输入登录密码");
		$("#pwd-txt").fadeIn("slow").delay(1000).fadeOut("slow");
		$("#validateCode").attr("src",basepath+"validate.code?1=" + Math.random()*10000);
		$("#password").focus();
		return;
	}else if($("#codeLi").css("display") == "block" && (code == null || code.length <= 0 || code == initCodeText)){
		$this.text("立即登录");
		$this.removeAttr("disabled");
		$this.attr("src",basepath+"validate.code?1=" + Math.random()*10000);
		$("#code").focus();
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
