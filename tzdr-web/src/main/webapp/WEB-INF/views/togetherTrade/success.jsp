<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>
<%@include file="../common/import-artDialog-js.jspf"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>股票合买发起成功 - 维胜</title>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/buy.css">
<script language="javascript" src="${ctx}/static/script/tzdr.js"></script>
<script language="javascript" >
$(document).ready(function(e) {
	//顶部菜单位置
	$('.navlist li a').removeClass('on');
	$("#togetherli").addClass("on");

});
</script>
</head>

<body>
<!--顶部 -->
	<%@ include file="../common/personheader.jsp"%>
<div class="buy_success">
	<div class="bs_ctn">
		<h3><img src="${ctx}/static/images/together/icon_04.jpg"/>恭喜您发起成功！请等待合买满标......</h3>
		<p>1，目前受邀的合买者比较活跃，一般在1个工作日内会满标，若3个交易日未满标，该方案将流标；</p>
		<p>2，合买满标后，工作时间内30分钟开通A股交易账户，非工作时间下个交易日09:15前开通；</p>
		<p>3，交易账户开通成功后，我们将短信通知您。</p>
		<P class="pA">您现在可以进入：<a href="${ctx}/help?tab=software&leftMenu=1">交易软件下载</a><a href="${ctx}/together/detail/${groupId}">合买详情页</a><a href="${ctx}/usertogether/detail/${groupId}">方案详情页</a></P>
	</div>
</div>
<%@ include file="../common/personfooter.jsp"%>
</body>
</html>
