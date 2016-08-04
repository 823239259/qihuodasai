<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>
<%@ include file="../common/import-artDialog-js.jspf"%>
<%
	String casServerLoginUrl="http"+"://localhost:8080/cas/login";//ConfUtil.getContext("SSO.casServer.loginUrl");

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
var loginValid = true;
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
	var _services = 'service=' + encodeURIComponent(basepath+"indexSSO");
	$.getScript(casServerLoginUrl + '?' + _services + '&get-lt=true&n=' + new Date().getTime(), function() {
		// 将返回的 _loginTicket 变量设置到 input name="lt" 的value中。
		$('#J_LoginTicket').val(_loginTicket);
		$('#J_FlowExecutionKey').val(_flowExecutionKey);
	});
};

	/* 倒计时  */
	$(function(){
		 $("#loginForm").submit();
		var djs = $(".cg_djs").html();
	    var cgdjs = setInterval(function(){
	        djs--;
	        $(".cg_djs").html(djs);
           
	        if(djs==0){
	        	clearInterval(cgdjs);
	            window.location.href = "${ctx}/user/account";
	        }
	    },1000); 
	});
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
		<a class="logo" href="http://www.dktai.com">
			<img src="${ctx}/static/images/common-new/new_logo.png" title="维胜" alt="维胜">
		</a>
		<p style="float: right;">全国客服热线<span>400</span>-<span>852</span>-<span>8008</span></p>
	</div>
</div>
<div class="ks-cg">
	<div class="ks-cg_centent">
		<p class="ks-cg_ts1"><img src="${ctx}/static/images/login/ks-zhucecg.png">恭喜您注册成功~</p>
		 <form id="loginForm" name="loginForm" action="<%=casServerLoginUrl%>" onsubmit="return loginValidate();" method="post" target="ssoLoginFrame">
			<input type="hidden" value="${islogin}"  id = "login"/>
			<input type="hidden" value="13550078956" name = "username" id = "username"/>
			<input type="hidden" value="a123456" name = "password" id = "password"/>
			<input type="hidden" name="isajax" value="true">
	        <input type="hidden" name="isframe" value="true">
	        <input type="hidden" name="lt" value="" id="LoginTicket">
	        <input type="hidden" name="execution" value="e3s1" id="J_FlowExecutionKey">
	        <input type="hidden" name="_eventId" value="submit">
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