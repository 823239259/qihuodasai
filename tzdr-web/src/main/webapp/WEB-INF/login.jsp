<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="views/common/common.jsp"%>
<%@include file="views/common/import-artDialog-js.jspf"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
    String returnUrl =  request.getHeader("referer");
	if(returnUrl == null ){
		returnUrl = "";
	}else if(returnUrl.contains("/login")){
		Object obj = request.getSession().getAttribute("RETURNURL");
		returnUrl = obj == null ? "":obj.toString().replaceAll("＆", "&");
	}else if(returnUrl.contains("/signin") || returnUrl.contains("/forgetpw") || returnUrl.contains("/fastsignin") || returnUrl.contains("/newfastsignin")){
		returnUrl = basePath+"/user/account";
	}else if(returnUrl.contains("/totrade")){
		returnUrl = basePath+"/day?enter=0";
	}else if(returnUrl.contains("future/pay")){
		returnUrl = basePath+"/future/index";
	}else{
		returnUrl = returnUrl.replaceAll("＆", "&");
		request.getSession().setAttribute("RETURNURL", returnUrl);
	}
%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="zh">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta content="投资达人提供股票操盘、P2P贷款、投资理财、股指期货等交易服务，更高的收益，更加安全可靠；投资达人致力于打造中国领先的互联网金融交易平台." name="description">
<title>登录_投资达人-中国领先的互联网金融交易平台 </title>
<link rel="stylesheet" href="${ctx}/static/css/login.css">
<link rel="stylesheet" href="${ctx}/static/css/tzdr.css">
<script language="javascript" src="${ctx}/static/script/login/login.js?version=20150611"></script>
<script language="javascript" src="${ctx}/static/script/tzdr.js"></script>
</head>
<body>
	<%@ include file="views/common/header.jsp"%>
	<input type="hidden" id="backData" value="${backData}"/>
	<div class="dontdothat"></div>
	<div class="center1000 clearfix">
		<c:choose>
			<c:when test="${!empty returnUrl}">
				<input type="hidden" value="${returnUrl }" name="returnUrl" id="returnUrl"/>
			</c:when>
			<c:otherwise>
				<input type="hidden" value="<%=returnUrl%>" name="returnUrl" id="returnUrl"/>
			</c:otherwise>
		</c:choose>
		<div class="logon-wrap clearfix">
			<div class="logon-left">
				
				<img width="576" height="340" src="${ctx}/static/images/left_img.png" />
				
			</div>
			<div class="ui-logon" id="login">
				<h2>登录</h2>
				<!-- 错误提示 -->
				<div class="ui-err loginError"></div>
				<form id="fm1" class="form" action="" method="post">
				<div class="logon-ipt userNameLi">
					<em class="user"></em>
					<input id="username" name="username"  class="holder userName" tabindex="1" accesskey="n" type="text" value="帐号" autocomplete="off"/>
					<!-- 记住的账号 -->
					<ul class="user-layer" style="display: none;">
						<li>Blackjeffer</li>
						<li>Blackjeffer</li>
						<li>Blackjeffer</li>
						<li>Blackjeffer</li>
						<li>Blackjeffer</li>
					</ul>
					<a class="close-btn clean" style="display:none" href="javascript:void(0);"></a>
				</div>
				<div class="logon-ipt mgt20 passwordLi">
					<em class="key"></em>
					<input id="password"  name="password" tabindex="2" class="password" type="password" value="" maxlength="16" autocomplete="off"/>
					<i id='pwd-txt' class="pwd-txt pwdText" style="display: block;" ><!-- 密码 --></i>
				</div>
				<c:if test="${isNeedCode}">
					<div class="logon-ipt mgt20 codeLi" style="width:160px;">
						<input id="yzm"  class="lg_codeip codeText" name="yzm" maxLength="5" value="验证码"/>
						<img class="validateCode" src="validate.code" style=" position:absolute; margin-left:5px;" width="115" height="44">
					</div>
				</c:if>
				<div class="ui-pwd">
					<a href="${ctx}/forgetpw">忘记密码</a>
				</div>
				<div class="login-btn">
					<button class="login" type="button">登录</button>
				</div>
				<div class="login-regist">
					没有账号？ <a class="register" href="${ctx}/signin" style="color: #06c;">免费注册</a>
				</div>
				</form>
			</div>
		</div>
	</div>
	<%@ include file="views/common/personfooter.jsp"%>
	<%@ include file="views/common/dsp.jsp"%>
</body>
</html>