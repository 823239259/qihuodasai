<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>
<%@include file="../common/import-artDialog-js.jspf"%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="zh">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>港股方案申请成功 - 维胜</title>
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
					<a href="" class="on">1</a>
					<a href="" class="on">2</a>
					<a href="">3</a>
					<a href="">4</a>
					<a href="">5</a>
				</div>
				<div class="cp_sp_font">
					<span>申请配资</span>
					<span>支付配资费用</span>
					<span>配资成功</span>
					<span>投资股票</span>
					<span>盈利归你</span>
				</div>
			</div>
			<div class="cp_suc">
				<h3>恭喜您！申请成功!您的方案正在开户中......</h3>
				<p>工作时30分钟内开通股票交易账户，其他时间下个交易日09:15前开通，交易账户开通后我们将短信通知您。</p>
				<div class="cp_paybtn">
					<div class="cp_pb_font">您现在可以进入：</div>			
					<div class="uc_paybtn cp_btnsuc" style="float:left;"><a href="${ctx}/uhkstock/detail/${groupId}">方案详情</a></div>	
					<div class="uc_paybtn cp_btnsuc" style="float:left;"><a href="${ctx}/help?tab=software&leftMenu=1">交易软件下载</a></div>
				</div>
			</div>
		</div>
	<%@ include file="../common/personfooter.jsp"%>
<%@ include file="../common/dsp.jsp"%>
</body>
<script type="text/javascript">
$(document).ready(function(e) {
	//顶部菜单位置
	$('.navlist li a').removeClass('on');
	$("#hkstockli").addClass("on");
});
</script>
</html>

