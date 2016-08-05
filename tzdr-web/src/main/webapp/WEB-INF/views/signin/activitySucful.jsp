<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>
<%@ include file="../common/import-artDialog-js.jspf"%>
<%
	String casServerLoginUrl = ConfUtil.getContext("SSO.casServer.loginUrl");

%>
<c:set var="casServerLoginUrl" value="<%=casServerLoginUrl%>"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="zh">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta content="维胜提供股票操盘、P2P贷款、投资理财、股指期货等交易服务，更高的收益，更加安全可靠；维胜致力于打造中国领先的互联网金融交易平台." name="description">
<meta name="viewport" content="width=1000">
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/newregist.css?v=${v}">
<title>注册成功 - 维胜 </title>
<script type="text/javascript">
	var casServerLoginUrl="${casServerLoginUrl}";
</script>
<script type="text/javascript">
window.onload = function(){
	/* 倒计时  */
	$(function(){
		var m = $("#username").val();
		var p = $("#password").val();
		 var djs = $(".cg_djs").html();
	    var cgdjs = setInterval(function(){
	        djs--;
	        $(".cg_djs").html(djs);
	        if(djs==0){
	        	clearInterval(cgdjs); 
				$("#loginForm").submit();
	        }
	    },1000);
	});
}
	
</script>
<style>
	.ks_footer {position: absolute; bottom: 0; width: 100%;}
</style>
</head>
<body>
<input type="hidden" id="backData" value="${backData}"/>
<input type="hidden" id="source" value="${source}"/>
<div class="ks_header">
	<div class="ks_content">
		<a class="logo" href="http://www.vs.com">
			<img src="${ctx}/static/images/common-new/new_logo.png" title="维胜" alt="维胜">
		</a>
		<p style="float: right;">全国客服热线<span>400</span>-<span>852</span>-<span>8008</span></p>
	</div>
</div>
<div class="ks-cg">
	<div class="ks-cg_centent">
		<p class="ks-cg_ts1"><img src="${ctx}/static/images/login/ks-zhucecg.png">恭喜您注册成功~</p>
		 <form id="loginForm" name="loginForm" action="${ctx }/" method="post">
			<input type="hidden" value="${m }" name = "m" id = "username"/>
			<input type="hidden" value="${p }" name = "p" id = "password"/>
			<input type = "hidden" value="1" name = "islogin" id= "islogin">
        </form>
		<p class="ks-cg_ts2">您已成功注册维胜网站！</p>
		<p class="ks-cg_ts3"><a class="logo" href="${ctx}">立即操盘</a></p>
		<p class="ks-cg_ts4"><span class="cg_djs">3</span>S后自动跳转网站首页</p>
	</div>
</div>
<div class="ks_footer">
	<p>
		<span>Copyright © 2016 成都盈透科技有限公司 版权所有 蜀ICP备16018768号-1</span>
		<img src="${ctx}/static/images/image/chengxing.png" style="margin-right: 0;">
    	<img src="${ctx}/static/images/image/anquan.png">
    	<img src="${ctx}/static/images/image/shiming.png">
    </p>
</div>
<%@ include file="../common/dsp.jsp"%>
</body>
</html>