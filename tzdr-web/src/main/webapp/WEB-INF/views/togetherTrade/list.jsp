<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>
<%@include file="../common/import-artDialog-js.jspf"%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="zh">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>股票合买方案列表 - 维胜</title>
<link rel="stylesheet" href="${ctx}/static/css/uc.css?v=${v}" type="text/css">
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/public.css">
<link href="${ctx}/static/css/pagination.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}/static/script/tzdr.js"></script>
<script type="text/javascript" src="${ctx}/static/script/common/jquery.pagination.js"></script>
<script type="text/javascript" src="${ctx}/static/script/common/dateUtils.js"></script>
<script type="text/javascript" src="${ctx}/static/script/togetherTrade/list.js?v=${v}"></script>

</head>
<body>

	<!--顶部 -->
	<%@ include file="../common/personheader.jsp"%>
	<div class="uc">
		<!--个人中心导航 -->
		<%@ include file="../common/leftnav.jsp"%>

		<!--网银支付-->
		<div class="uc_mianbar">
			<div class="uc_pay">
				<ul class="uc_paynav">
					<li data="all"><a href="javascript:void(0);" class="on">发起的合买方案</a></li>
					<li data="other"><a href="javascript:void(0);" >参与的合买方案</a></li>
				</ul>
				<!-- 全部申请 -->
				<div class="uc_trade">					
					<div id="content"></div>
				</div>
				<div id="Pagination" style="margin: 20px 0;"></div>
			</div>

		</div>
		<!-- 遮罩层 -->
		<div class="fl_mask" style="display:none;"></div>
		
		<div class="clear"></div>
	</div>
	<%@ include file="../common/personfooter.jsp"%>
<%@ include file="../common/dsp.jsp"%>
</body>
</html>
