<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String appPath = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+appPath;
	String forgetPw = "http"+"://www.vs.com";//"http"+"://localhost:8088/tzdr-web/";////www.dktai.com
%>
<c:set var="ctx" value="<%=basePath%>"></c:set>
<c:set var="forget" value="<%=forgetPw%>"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="zh">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登录 - 中国领先的国际期货及衍生品互联网交易平台</title>
<meta name="keywords" content="维胜，国际期货，期货，投资达人，金勺子，高盛，都城，南华期货，配资，期货配资，期货开户，外盘，富时A50，国际原油，恒指期货，期货公司，期货平台，炒期货，模拟盘，赚钱，头寸，持仓，成都盈透科技有限公司"/>
	<meta name="description" content="维胜（www.vs.com）-致力于成为中国领先的国际期货及衍生品互联网交易平台，提供恒指期货、国际原油、富时A50等主流国际期货产品，开户操盘快捷方便，交易费用全网最低。"/>
<link rel="shortcut icon" href="${ctx}/images/login/weisheng-icon.png">
<link rel="stylesheet" href="${ctx}/css/login.css?ver=20150901">
<!-- <script language="javascript" src="http://www.vs.com/static/script/common/jquery-1.8.0.min.js"></script> -->
<!-- start 吉鹏代码 -->
<script type="text/javascript">
var _zzsiteid="g2CiQ0pbhOF";
var _zzid = "g2CiQ0pbhOE";
(function() {
  var zz = document.createElement('script');
  zz.type = 'text/javascript';
  zz.async = true;
  zz.src = 'https:' == document.location.protocol ? 'https://daima.adpengshun.com/api/trace.js' : 'http://daima.adpengshun.com/trace/api/trace.js';
  var s = document.getElementsByTagName('script')[0];
  s.parentNode.insertBefore(zz, s);
})();
</script>
<!-- end 吉鹏代码 -->
<!-- <!— Start Alexa Certify Javascript —> -->
<script type="text/javascript">
_atrk_opts = { atrk_acct:"lhQPn1QolK10WR", domain:"vs.com",dynamic: true};
(function() { var as = document.createElement('script'); as.type = 'text/javascript'; as.async = true; as.src = "https://d31qbv1cthcecs.cloudfront.net/atrk.js"; var s = document.getElementsByTagName('script')[0];s.parentNode.insertBefore(as, s); })();
</script>
<noscript><img src="https://d5nxst8fruw4z.cloudfront.net/atrk.gif?account=lhQPn1QolK10WR" style="display:none" height="1" width="1" alt="" /></noscript>
<!-- <!— End Alexa Certify Javascript —> -->
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
			<li><h3>用户登录中心</h3></li>
		</ul>
		<p id="rightP"><a href="${forget }">返回首页</a></p>
	</div>
	<div class="lg_promt"></div>
	<div class="center1000 clearfix">
		<div class="logon-wrap clearfix">
			<form:form method="post" id="fm1" commandName="${commandName}"
				htmlEscape="true" cssClass="form">
				<div class="ui-logon login" id="login">
					<h3>用户登录<p id="rightP" style="margin-top:-15px; *margin-top:-34px;">没有账号？<a href="${forget}/signin">立即注册</a></p></h3>
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
						<a class="forget" href="${forget }/forgetpw">忘记密码？</a> <a class="registerlink" href="${forget }/signin">免费注册</a>
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
	<span style="display: none"><script src='http://w.cnzz.com/q_stat.php?id=1256807294&l=3' language='JavaScript'></script></span>
</body>
</html>