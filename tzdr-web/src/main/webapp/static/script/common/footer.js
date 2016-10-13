$(document).ready(function() {
    $('.top_mynav').hover(function() {
	}, function() {
	    $(this).hide();
	});
    $('.fl_sv_code a').hover(function() {
        $('.fl_sv_codetk ').show();
    }, function() {
        $('.fl_sv_codetk ').hide();
    });    
    /*二维码*/
    $('.follow .erweima').hover(function() {
        $('.erweima-wxtk').show();
    }, function() {
        $('.erweima-wxtk').hide();
    });
    // 加载最新公告
    var showNotice = false;
    var content="";
    $.ajax({
    	url:basepath+"findnewData",
    	data:{},
    	type:'POST',
    	success:function(nitives){
    		var reg1=new RegExp("&lt;","g"); 
    		var reg2=new RegExp("&gt;","g"); 
    		$(nitives).each(function(){
    			content = $(this).attr("content");
    			content=content.replace(reg1,"<");
    			content=content.replace(reg2,">");
    			$('.notice-content').html(content);
    			$('#noticeid').val($(this).attr("version"));
    		    // 检查公告
    		    checkNotice();
    		    showNotice = true;
    		})
    	},dataType:'json'
    })
    
    $(window).scroll(function () {
    	if(showNotice) {
		    var scrollTop = $(this).scrollTop();//滚动条位置
		    var scrollHeight = $(document).height();//高度
		    var windowHeight = $(this).height();//整体高度
		    if (scrollTop + windowHeight >= scrollHeight-80) {
		    	$(".notice-fixed").fadeOut(1000);
		    	$(".notice-relative").fadeIn(1000);
			} else {
				$(".notice-fixed").fadeIn(1000);
				$(".notice-relative").fadeOut(1000);
			}
    	}
	});
    
});
//检测公告
function checkNotice() {
	var noticeid = getCookie("noticeid");
	var loaclNoticeid = $("#noticeid").val();
	$(".notice-fixed").hide();
	if(noticeid == loaclNoticeid) {
		$(".site-notice").remove();
	} else {
		$(".notice-fixed").fadeIn("slow");
	}
}
 // 关闭公告
function closeNotice() {
	// cookie记录公告已删除
	setCookie("noticeid", $("#noticeid").val());
	$(".site-notice").remove();
}
function setCookie(name, value) {
	var Days = 30;
	var exp = new Date();
	exp.setTime(exp.getTime() + Days*24*60*60*1000);
	document.cookie = name + "=" + escape(value) + ";path=/;expires="
			+ exp.toGMTString();
}
function addCookie(objName, objValue){
	if(objValue==""){
		var Num="";
		for(var i=0;i<6;i++){ 
			Num+=Math.floor(Math.random()*10); 
		} 
		objValue=Num;
	}
    document.cookie = objName+"="+ escape (objValue)+";path=/;expires=0"; 
}
//获取指定名称的cookie的值 
function getCookie(c_name) {
	if (document.cookie.length > 0) {
		var c_start = document.cookie.indexOf(c_name + "=");
		if(c_start != -1) {
			c_start = c_start + c_name.length + 1; 
			c_end = document.cookie.indexOf(";", c_start)
			if(c_end == -1) {
				c_end = document.cookie.length;
			}
		    return unescape(document.cookie.substring(c_start, c_end))
		}
	}
	return ""
}
	// 平滑滚动到顶部
function scrollTop() {
	$('html, body').animate({scrollTop: '0px'}, 800);
}
/* function scrollBottom() {
	$('html,body').animate({scrollTop: $(document).height()}, 800);
} */
/*弹出层登录*/
$(function() {
	$("#signin").click(function() {
		$("#signin_box").css("display","block");
		$("#div_Mask").show();
		var popupHeight = $(".tck01").height();   
		var popupWidth = $(".tck01").width();    
		$(".tck01").css({     
		 "margin-left": -popupHeight/2,   
		 "margin-top": -popupWidth/2   
		});
	});
});
function closeDiv() {
	$("#signin_box").css("display","none");
	$("#div_Mask").css("display","none");
}
/*登录*/
//手机号码规则
var mobileForm = /^(((13[0-9])|(14[7])|(15[0-9])|(17[0-9])|(18[0-9]))+\d{8})$/;
var initLoginNameText = "请输入手机号码";
var loginValid = false;

//登录操作
$("#login_box").click(function(){
	var $this = $(this);
	$this.text("正在登录");
	$this.attr("disabled","disabled");
	var loginName = $.trim($("#signin_username").val());
	var password = $.trim($("#signin_password").val());
	if(loginName == null || loginName.length <= 0 || loginName == initLoginNameText){
		$this.text("立即登录");
		$this.removeAttr("disabled");
		$("#signin_box .warning").html("请输入手机号码");
		$("#signin_username").focus();
		return;
	}else if(!loginName.match(mobileForm)){
		$this.text("立即登录");
		$this.removeAttr("disabled");
		$("#signin_box .warning").html("您输入的手机号有误");
		$("#signin_username").focus();
		return;
	}else if(password == null || password.length <= 0){
		$this.text("立即登录");
		$this.removeAttr("disabled");
		$("#signin_box .warning").html("请输入密码");
		$("#signin_password").focus();
		return;
	}
	else if(password.length < 6 || password.length > 20){		
		$this.text("立即登录");
		$this.removeAttr("disabled");
		$("#signin_box .warning").html("请输入6~16位密码");
		$("#signin_password").focus();
		return;
	}
	
	loginValid = true;
	$("#signin_box .warning").html("");
	$this.text("立即登录");
	$this.removeAttr("disabled");
	//basepath+"login"
	$.post("http://localhost:8088/cas/login",{username:loginName,password:password,ajax:1},function(data){ //登录
		alert(data);
		if(data.success){
			alert("登录成功");
			$("#signin_box").css("display","none");
			$("#div_Mask").css("display","none");
		}else{
			$("#signin_box .warning").html("账号和密码错误");
			$this.text("立即登录");
			$("#signin_password").focus();
		}
		/*if(data.success){
			if(data.message!="" && data.message!=null){
				if(data.message=="密码是否正确"){
					alert("登录成功");
				}else{
					$("#signin_box .warning").html("密码错误");
					$this.text("立即登录");
					$("#signin_password").focus();
				}
			}
		}else{
			$this.attr("status",true);
			$this.text("立即登录");
		}*/
	},"json");
});

