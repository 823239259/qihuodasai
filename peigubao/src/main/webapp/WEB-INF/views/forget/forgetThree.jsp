<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>
<%@include file="../common/import-artDialog-js.jspf"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="zh">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
 <%@ include file="../common/commonkeyword.jsp"%>
    <link rel="stylesheet" href="${ctx}/static/css/login.css">
    <script type='text/javascript' src="${ctx}/static/script/tzdr.js"></script>
    <script language="javascript" src="${ctx}/static/script/forget/forget.js"></script>
</head>
<body>
<%@ include file="../common/header.jsp"%>
<div class="dontdothat"></div>
<div class="center1000 clearfix">
	<div class="pw_lg"><a href="${ctx}/signin" class="fontblue">注册</a><a>|</a><a href="${ctx}/user/account" class="fontblue">登录</a></div>
	<div class="pw_main">
		<div class="pw_list">
			<p class="pw_step3"></p>
			<div class="pw_stepfont">
				<a class="zmmmart125">验证账号</a>
				<a class="zmmmart125">设置新密码</a>
				<a>重置成功</a>
			</div>
			<div class="pw_font">
				<span><img src="${ctx}/static/images/ok.png"></span>
				<p class="pw_fontmain">密码设置成功</p>
				<p class="pw_fontpromt">您现在可以使用新密码登录了</p>
			</div>
			<div class="pw_btn">
				<a href="${ctx}/login">马上登录</a>
			</div>
		</div>
	</div>
</div>
<%@ include file="../common/footer.jsp"%>
<%@ include file="../common/dsp.jsp"%>
</body>
</html>