R<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>
<%@include file="../common/import-artDialog-js.jspf"%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="zh">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="../common/commonkeyword.jsp"%>
<link href="${ctx}/static/css/public.css" rel="stylesheet"
	type="text/css">
<link href="${ctx}/static/css/trade.css?v=20150528" rel="stylesheet"
	type="text/css" />

<script type="text/javascript">
	
</script>

</head>
<body>

	<!--顶部 -->
	<%@ include file="../common/personheader.jsp"%>

	<div class="capital">
	<div class="cp_step">
		<div class="cp_sp_line"></div>
		<div class="cp_sp_linehover" style="width:350px;"></div>
		<div class="cp_sp_num">
			<a href="#" class="on">1</a>
			<a href="#" class="on">2</a>
			<a href="#" class="on">3</a>
			<a href="#">4</a>
			<a href="#">5</a>
		</div>
		<div class="cp_sp_font">			
			<span>申请操盘</span>
			<span>支付操盘费用</span>
			<span style="color:red;">申请失败</span>
			<span>投资股票</span>
			<span>盈利归您</span>
		</div>
	</div>
	<div class="cp_fail">
		<h3>很抱歉！您的申请失败了！</h3>
		<p>请联系客服400-633-9257，把您的问题反馈给客服，我们会第一时间解决您的问题。</p>
		<span>您也可以先<a href="${ctx}/help?tab=software&leftMenu=2" target="_blank">下载安装</a>交易软件</span>	
		<div class="cp_paybtn">
			<div class="cp_pb_font">您现在可以进入：</div>
			<div class="cp_btnback"><a href="${ctx}/">返回首页</a></div>		
			<div class="uc_paybtn cp_btnsuc"><a href="${ctx}/user/account">个人中心</a></div>
		</div>

	</div>
</div>
	<%@ include file="../common/personfooter.jsp"%>
<%@ include file="../common/dsp.jsp"%>
</body>
</html>

