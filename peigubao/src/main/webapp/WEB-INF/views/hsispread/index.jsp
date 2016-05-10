<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>
<%@include file="../common/import-artDialog-js.jspf"%>
<%
	String casServerLoginUrl=ConfUtil.getContext("SSO.casServer.loginUrl");
%>
<c:set var="casServerLoginUrl" value="<%=casServerLoginUrl%>"></c:set>
<html xmlns="http://www.w3.org/1999/xhtml" lang="zh">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>恒生指数期货急速交易 - 投资达人</title>
<meta content="24倍杠杆助力恒指,低门槛,高收益" name="description">
<script language="javascript" src="${ctx}/static/script/tzdr.js?v=${v}"></script>
<script type="text/javascript" src="${ctx}/static/script/hsispread/index.js?v=${v}"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/hsispreadindex.css?v=${v}">
<script type="text/javascript">
	var casServerLoginUrl="${casServerLoginUrl}";
</script>

<link rel="stylesheet" type="text/css" href="${ctx}/static/css/hsispreadindex.css?v=${v}">
</head>
 
<body>
<div class="hsi_top">
	<p class="hsi_top_logo fl"><a href="http://www.tzdr.com/" target="_blank"><img src="${ctx}/static/images/hsispread/logo.png"></a></p>
	<p class="hsi_top_tel fr">客服热线：400-020-158</p>
</div>

<div class="banner">
	<div class="banner_pic"><span><img src="${ctx}/static/images/hsispread/banner.jpg"></span></div>
	<!-- 未注册 -->
	<div class="banner_login" style="display:block;">
		<ul class="bannber_l_list">
			<li><input name="mobile" id="mobile" type="text" focucmsg="请输入手机号"  />
				<p style="display: none; font-size: 12px; color: rgb(255, 156, 0);">请输入正确的手机号码</p>
			</li>
			<li><input type="text"  id="code" name = "code" class="code" focucmsg="请输入验证码"><a href="javascript:void(0);"  onclick = "verifyMobile()" class="codebtn">获取验证码</a></li>
			<li><a href="javascript:void(0);" onclick="sigin()" class="regitsbtn">获取行情软件</a></li>
            <li><a href="javascript:void(0);" onclick="sigin()" class="regitsbtn">模拟交易软件</a></li>
		</ul>
	</div>
</div>
<div class="main">
    <img src="${ctx}/static/images/hsispread/pic_01.gif">
    <a href="http://www.tzdr.com/ftse/index" target="_blank" class="tp_l_btn">查看详情</a>
    <a href="http://www.tzdr.com/crudeoil/index" target="_blank" class="tp_l_btn2">查看详情</a>
    <a href="http://www.tzdr.com/product/gold_index" target="_blank" class="tp_l_btn3">查看详情</a>
</div>
<div class="main2">
    <img src="${ctx}/static/images/hsispread/pic_02.gif">
    <img src="${ctx}/static/images/hsispread/pic_03.gif">
    <img src="${ctx}/static/images/hsispread/pic_04.gif">
</div>
<div class="main3">
    <img src="${ctx}/static/images/hsispread/pic_05.jpg">
</div>
<div class="foot">Copyright © 2015 上海信闳投资管理有限公司 版权所有 沪ICP备14048395号-1</div>


<span style="display:none">

</span>

<%-- cas ajax登录 --%>
    <div >
    	<form id="loginForm" action="${casServerLoginUrl}" onsubmit="return hsiLogin();" method="post" target="ssoLoginFrame">
	    	<input type="hidden" name="isajax" value="true" />
			<input type="hidden" name="isframe" value="true" />
			<input type="hidden" name="lt" value="" id="J_LoginTicket">
			<input type="hidden" name="execution" value="" id="J_FlowExecutionKey">
			<input type="hidden" name="_eventId" value="submit" />
			<input type="hidden" name="username" id="loginUsername">
			<input type="hidden" name="password" id="loginPassword">
    	</form>
    </div>
<span style="display:none"><script src='http://w.cnzz.com/q_stat.php?id=1256807294&l=3' language='JavaScript'></script></span>
</body> 
</html>