<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>CAS &#8211; Central Authentication Service</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<base href="<c:url value="/" />"/>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<link href="resources/Flat-UI-master/dist/css/vendor/bootstrap.min.css" rel="stylesheet">
<link href="resources/Flat-UI-master/dist/css/flat-ui.css" rel="stylesheet">
<script type="text/javascript" charset="UTF-8" src="resources/jquery.min.js"></script>
<style type="text/css">
.container {
	width: 970px !important;
}
</style>
<script type="text/javascript">
function login() {
	var name = $('#login-name').val();
	var password = $('#login-pass').val();
	if (name && password) {
		$("#login_form").submit();
	} else {
		$("#login_error_info").html("<spring:message code="screen.welcome.label.error" />");
	}
}

/**
 * 清除错误提示信息
 */
function clearErrorInfo() {
	$('#login_error_info').html('');
}

/**
 * 绑定回车提交事件
 */
document.onkeydown = function(e) {
	var code = (e ? e : event).keyCode;
	if (code == 13) {
		login();
	}
};
</script>
</head>
<body id="cas">
	<center>
		<c:if test="${not pageContext.request.secure}">
			<div id="msg" class="errors">
				<h2>非安全连接</h2>
				<p>您正在通过非安全连接访问单点登录系统。单点登录将无法正常工作。为了单点登录正常工作，你必须通过HTTPS登录。</p>
			</div>
		</c:if>
	</center>
	<div class="container">
	<div class="login">
		<div class="login-screen">
			<div class="login-icon">
				<img src="resources/Flat-UI-master/dist/img/login/login.png" alt="Welcome to Mail App" />
				<h4>Welcome to <small>System</small></h4>
			</div>
			<div class="login-form">
				<form id="login_form" action="login" method="post">
					<div class="form-group">
						<input type="text" class="form-control login-field"
							placeholder="<spring:message code="screen.welcome.label.netid" />"
							id="login-name" name="username" onkeydown="clearErrorInfo()" />
						<label class="login-field-icon fui-user" for="login-name"></label>
					</div>

					<div class="form-group">
						<input type="password" class="form-control login-field"
							placeholder="<spring:message code="screen.welcome.label.password" />"
							id="login-pass"  name="password" onkeydown="clearErrorInfo()" />
						<label class="login-field-icon fui-lock" for="login-pass"></label>
					</div>

					<div>
				        <input type="hidden" name="lt" value="${loginTicket}"/>
				        <input type="hidden" name="execution" value="${flowExecutionKey}"/>
				        <input type="hidden" name="_eventId" value="submit"/>
				    </div>

					<a href="javascript:void(0);" class="btn btn-primary btn-lg btn-block" onclick="login()">
						<spring:message code="screen.welcome.button.login" />
					</a>
			        <a href="javascript:void(0);" class="login-link">忘记密码?</a>

					<div class="demo-type-example" style="color: #48C9B0; text-align: center;">
						<h5 id="login_error_info"></h5>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>
</body>
</html>