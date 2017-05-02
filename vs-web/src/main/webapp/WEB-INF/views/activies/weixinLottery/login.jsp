<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.tzdr.common.utils.ConfUtil"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="zh-cmn-Hans">
<head>
	<meta charset="utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
	<meta http-equiv="Cache-Control" content="no-siteapp" />
	<meta name="renderer" content="webkit" />
	<meta name="robots" content="all" />
	<meta name="keywords" content="股票、期货" />
	<meta name="description" content="维胜投身普惠金融互联网服务，以网络平台为A股、港股、美股、富时A50、恒指期货、国际原油等金融产品的操盘提供便利条件。" />
	<meta name="author" content="www.qdjinsida.com" />
	<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
	<meta name="HandheldFriendly" content="true" />
	<meta name="MobileOptimized" content="320" />
	<meta name="apple-mobile-web-app-title" content="html5" />
	<meta name="format-detection" content="telephone=no,email=no" />
	<meta name="apple-mobile-web-app-capable" content="yes" />
	<meta name="apple-mobile-web-app-status-bar-style" content="black" />
	<title>微信抽奖 - 维胜 </title>
	<%
		String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
		String casServerLoginUrl = ConfUtil.getContext("SSO.casServer.loginUrl");
	%>
	<c:set var="ctx" value="<%=basePath%>"></c:set>
	<c:set var="v" value="20160113"></c:set>

	<!-- common css -->
	<link rel="shortcut icon" href="${ctx}/static/ico/icon.png">
	<!-- custom css -->
	<link rel="stylesheet" href="${ctx}/static/css/weixin-lottery.css?v=${v}">

	<!-- common js -->
	<script src="${ctx}/static/script/common/jquery-1.8.0.min.js"></script>
	<script type="text/javascript">
		var basepath = "<%=basePath%>" + "/";
		var casServerLoginUrl = "<%=casServerLoginUrl%>";
	</script>

</head>
<body>
<!-- 登录 -->
<form id="loginForm" name="loginForm" class="login-form" action="<%=casServerLoginUrl%>" onsubmit="return loginValidate();" method="post" target="ssoLoginFrame">
	<input type="hidden" name="isajax" value="true" />
	<input type="hidden" name="isframe" value="true" />
	<input type="hidden" name="lt" value="" id="J_LoginTicket">
	<input type="hidden" name="execution" value="" id="J_FlowExecutionKey">
	<input type="hidden" name="_eventId" value="submit" />
	
	<article class="lg_main">
	    <ul class="lg_iplist">
	        <li><i class="lg_ipuser"></i><input type="tel" placeholder="手机号" name="username" id="username"></li>
	        <li><i class="lg_ippass"></i><input type="password" placeholder="登录密码" name="password" id="password"></li>
	    </ul>
	    <button type="button" class="lg_btn" id="login">登录</button>
	    <div class="lg_link">
	        <a href="http://m.peigubao.com/Home/Public/register.html" class="right">没有账号？立即注册</a>
	    </div>
	</article>
</form>

<!-- custom-login js -->
<script>
	//邮箱验证规则
	var emailForm  = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
	//手机号码规则
	var mobileForm = /^(((13[0-9])|(14[7])|(15[0-9])|(17[0-9])|(18[0-9]))+\d{8})$/;
	var initLoginNameText = "请输入手机号码";
	var initCodeText = "请输入登录密码";
	var loginValid = false;
	
	//登录操作
	$("#login").click(function(){
		var $this = $(this);
		$this.text("正在登录");
		$this.attr("disabled","disabled");
		var loginName = $.trim($("#username").val());
		var password = $.trim($("#password").val());
		
		if(loginName == null || loginName.length <= 0 || loginName == initLoginNameText){
			$this.text("立即登录");
			$this.removeAttr("disabled");
			alert("请输入手机号码");
			$("#username").focus();
			return;
		}else if(!((emailForm.test(loginName) && isNaN(loginName) && !loginName.match(mobileForm)) || (loginName.match(mobileForm) && loginName.length == 11 && !isNaN(loginName) && !emailForm.test(loginName)))){
			$this.text("立即登录");
			$this.removeAttr("disabled");
			alert("您输入的账号格式有误");
			$("#username").focus();
			return;
		}else if(password == null || password.length <= 0){
			$this.text("立即登录");
			$this.removeAttr("disabled");
			alert("请输入登录密码");
			$("#password").focus();
			return;
		}
		loginValid = true;
		$("#loginForm").submit();
		$this.text("立即登录");
		$this.removeAttr("disabled");
	});
	
	//登录验证函数, 由 onsubmit 事件触发
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
		var _services = 'service=' + encodeURIComponent(basepath+"weixin/lottery/indexSSO");
		$.getScript(casServerLoginUrl + '?' + _services + '&get-lt=true&n=' + new Date().getTime(), function() {
			// 将返回的 _loginTicket 变量设置到 input name="lt" 的value中。
			$('#J_LoginTicket').val(_loginTicket);
			$('#J_FlowExecutionKey').val(_flowExecutionKey);
		});
	};
	
	flushLoginTicket();
</script>
</body>
</html>
