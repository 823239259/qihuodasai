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
<input type="hidden" class="token"  value=""> 
<div class="center1000 clearfix">
	<div class="pw_lg"><a href="${ctx}/signin" class="fontblue">注册</a><a>|</a><a href="${ctx}/user/account" class="fontblue">登录</a></div>
	<div class="pw_main">
		<form id="fm1" class="checkoutMobile" action="" method="post">
			<div class="pw_list">
				<p class="pw_step1"></p>
				<div class="pw_stepfont">
					<a class="zmmmart125">验证账号</a>
					<a class="zmmmart125">设置新密码</a>
					<a>重置成功</a>
				</div>
				<div class="pw_ip">
					<font>手机号码：</font>
					<input class="srk mobile" value="手机号码" maxlength="11" type="text"/>
				</div>
				<div class="pw_ip">
					<font>验证码：</font>
					<input class="srk code" type="text" value="短信验证码" maxlength="6" style="width:125px; float:left;"/>
					<div class="getCode" status="true"><a class="pw_ipbtn">获取短信验证码</a></div>
				</div>
				<div class="pw_btn">
					<a class="verifyMobileCode" status="true" href="javascript:void(0);">下一步</a>
				</div>
			</div>
		</form>
	</div>
</div>
<%@ include file="../common/footer.jsp"%>
<%@ include file="../common/dsp.jsp"%>
</body>
</html>