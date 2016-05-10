<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>
<%@include file="../common/import-artDialog-js.jspf"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="zh">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
 <%@ include file="../common/commonkeyword.jsp"%>
    <link rel="stylesheet" href="${ctx}/static/css/login.css">
    <link rel="stylesheet" href="${ctx}/static/css/tzdr.css">
    <script type='text/javascript' src="${ctx}/static/script/tzdr.js"></script>
    <script language="javascript" src="${ctx}/static/script/forget/forget.js"></script>
</head>
<body>
<%@ include file="../common/header.jsp"%>
<div class="dontdothat"></div>
<input type="hidden" class="token"  value="${token}"> 
<input type="hidden"  class="mobilePhone" value="${mobile}">
<div class="center1000 clearfix">
	<div class="pw_lg"><a href="${ctx}/signin" class="fontblue">注册</a><a>|</a><a href="${ctx}/user/account" class="fontblue">登录</a></div>
	<div class="pw_main">
		<form id="fm1" class="setPasswordForm" action="" method="post">
			<div class="pw_list">
				<p class="pw_step2"></p>
				<div class="pw_stepfont">
					<a class="zmmmart125">验证账号</a>
					<a class="zmmmart125">设置新密码</a>
					<a>重置成功</a>
				</div>
				<div class="pw_ip">
					<font>密码：</font>
					<input class="srk password" type="password"/>
					<i id='pwd-mm' class="pwd-mm newPwdText">密码为6-16位数字和字母组成</i>
				</div>
				<div class="pw_ip">
					<font>确认密码：</font>
					<input class="srk affirmPassword" type="password"/>
					<i id='pwd-mm' class="pwd-mm affirmPwdText" >确认密码必须与新密码一致</i>
				</div>
				<div class="pw_btn">
					<a class="updatePassword" status="true" href="javascript:void(0);">确定</a>
				</div>
			</div>
		</form>
	</div>
</div>
<%@ include file="../common/footer.jsp"%>
<%@ include file="../common/dsp.jsp"%>
</body>
</html>