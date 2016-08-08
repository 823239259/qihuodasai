<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String appPath = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+appPath;
	String forgetPw = "http"+"://test.www.vs.com/";//"http"+"://localhost:8088/tzdr-web/";////www.vs.com
%>
<c:set var="ctx" value="<%=basePath%>"></c:set>
<c:set var="forget" value="<%=forgetPw%>"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="zh">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登录 - 维胜</title>
<link rel="shortcut icon" href="${ctx}/images/login/weisheng-icon.png">
<link rel="stylesheet" href="${ctx}/css/login.css?ver=20150901">
<!-- <script language="javascript" src="http://www.tzdr.com/static/script/common/jquery-1.8.0.min.js"></script> -->
	<script type='text/javascript'>

		window._CWiQ = window._CWiQ || [];
		
		window.BX_CLIENT_ID = 38474; // 帐号ID
		
		(function() {
		
		var c = document.createElement('script')
		
		,p = 'https:'==document.location.protocol;
		
		c.type = 'text/javascript';
		
		c.async = true;
		
		c.src = (p?'https://':'http://')+'tp.ana.pjdsp.com/boot/0';
		
		var h = document.getElementsByTagName('script')[0];
		
		h.parentNode.insertBefore(c, h);
		
		})();
		
	</script>

	<script type='text/javascript' src="http://tp.ana.pjdsp.com/js/vc.min.js">
	
	</script>

	<script type='text/javascript'>
	
	window.BX_CLIENT_ID = 38474; // 帐号ID
	
	BDX_DMP.trackPdmp('38474'); //访客分类名称是给访客贴的标签，可以自定义，例如： 访问过官网、注册用户等。
	
	</script>
<script language="javascript" src="resources/jquery.min.js?ver=20150901"></script>
<script type="text/javascript">
	var basepath = '${ctx}' + "/";
</script>
<script language="javascript" src="tzdr/js/login.js?ver=20150901"></script>

</head>
<body>
	<div class="header">
		<ul>
			<li><a href="${forget }" id="a1"></a></li>
			<!-- <li><a href="javascript: void(0);" id="a2"></a></li> -->
			<!-- <li><p></p></li> -->
			<li><h3>用户登录中心</h3></li>
		</ul>
		<p id="rightP"><a href="${forget }">返回首页</a></p>
	</div>
	<div class="lg_promt"><!-- 提示：配股宝与投资达人已达成战略合作关系，共享了账号系统，投资达人用户可直接登录配股宝，不用重新注册！ --></div>
	<div class="center1000 clearfix">
		<div class="logon-wrap clearfix">
			<form:form method="post" id="fm1" commandName="${commandName}"
				htmlEscape="true" cssClass="form">
				<div class="ui-logon login" id="login">
					<h3>用户登录<p id="rightP" style="margin-top:-15px; *margin-top:-34px;">没有账号？<a href="${forget}signin">立即注册</a></p></h3>
					<input type="hidden" name="lt" value="${loginTicket}" /> <input
						type="hidden" name="execution" value="${flowExecutionKey}" /> <input
						type="hidden" name="_eventId" value="submit" />
					<div class="logon-ipt userNameLi" style="margin-top: 80px;">
						<label>手机号码</label> 
						<i class="iphone"></i>	
						<input id="username" name="username"  class="holder userName" tabindex="1" accesskey="n" type="text" maxlength="11" autocomplete="off"/>
						<div class="ui-err loginError usernameError" style="display: none; width:140px;">
						</div>
					</div>
					<div class="logon-ipt mgt20 passwordLi">
						<label>登录密码</label> 
						<i class="password-img"></i>	
						<input id="password" name="password" tabindex="2" class="password" type="password" value="" maxlength="16" autocomplete="off" /> 
						<div class="ui-err loginError passwordError" style="display: none; width:140px;">
							<form:errors path="*" id="msg" htmlEscape="false" cssStyle="display: none;" />
						</div>
					</div>
					<div class="login-btn">
						<button class="loginbtn" type="button" id="loginbtn">登录</button>
					</div>
					<div class="lastP">
						<a class="forget" href="${ctx}forgetpw">忘记密码？</a> <a class="registerlink" href="${ctx}signin">免费注册</a>
					</div>
				</div>
			</form:form>
		</div>
	</div>

	<!--底部 -->
	<!-- 公司简介 -->
	<div class="footer">
		<p>Copyright © 2016 成都盈透科技有限公司 版权所有 蜀ICP备16018768号-1</p>
	</div>
	<spring:theme code="cas.javascript.file" var="casJavascriptFile" text="" />
	<script type="text/javascript" src="<c:url value="${casJavascriptFile}" />"></script>
	<!-- <span style="display: none"><script src='http://w.cnzz.com/q_stat.php?id=1256807294&l=3' language='JavaScript'></script></span> -->
</body>
</html>