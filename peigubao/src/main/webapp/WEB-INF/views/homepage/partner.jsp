<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>
<%@include file="../common/import-fileupload-js.jspf"%>
<%@include file="../common/import-artDialog-js.jspf"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta content="配股宝提供股票操盘、P2P贷款、投资理财、股指期货等交易服务，更高的收益，更加安全可靠；投资达人致力于打造中国领先的互联网金融交易平台." name="description">
<title>合作伙伴_配股宝-中国领先的互联网金融交易平台</title>
<link rel="stylesheet" href="${ctx}/static/css/login.css">
<link rel="stylesheet" href="${ctx}/static/css/news.css">
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/tzdr.css">
<script type='text/javascript' src="${ctx}/static/script/tzdr.js"></script>



</head>
<body>
<!-- 顶部 -->
<%@ include file="../common/personheader.jsp"%>
<div class="news">
    <div class="news_siderbar">
        <h2>关于我们</h2>
        <a href="${ctx }/abort" >公司简介</a>
        <a href="${ctx }/company">公司资质</a>
        <a href="${ctx }/partner" class="on">合作伙伴</a>
        <a href="${ctx }/contact">联系我们</a>
        <a href="${ctx }/companypic">公司展示</a>
    </div>
    <div class="news_mainbar">
        <h2>合作伙伴</h2>
        <div class="news_pt">
            <ul class="news_ptlist">
                <c:forEach items="${partners}" var="partner">
                 	<li><a href="${partner.linkUrl}" target="_blank"><img src="${ctx}/getRemoteContent?contentUrl=${partner.imgPath}"></a></li>
                </c:forEach>
                <li><a href="#"><img src="${ctx}/static/images/news/pt_cmbc.png"></a></li>	
                <li><a href="#"><img src="${ctx}/static/images/news/pt_safe.png"></a></li>		
            </ul>
        </div>
    </div>

</div>

<%@ include file="../common/personfooter.jsp"%>
<%@ include file="../common/dsp.jsp"%>
</body>


</html>


