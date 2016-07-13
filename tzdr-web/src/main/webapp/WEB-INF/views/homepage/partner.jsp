<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>
<%@include file="../common/import-fileupload-js.jspf"%>
<%@include file="../common/import-artDialog-js.jspf"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>合作伙伴 - 维胜</title>
<link rel="stylesheet" href="${ctx}/static/css/login.css">
<link rel="stylesheet" href="${ctx}/static/css/news.css">
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/tzdr.css">
<script type='text/javascript' src="${ctx}/static/script/tzdr.js"></script>



</head>
<body>
<!-- 顶部 -->
<%@include file="../common/header.jsp"%>
<div class="news">
    <div class="news_siderbar">
       <!--  <h2>关于我们</h2> -->
        <a href="${ctx }/about" >公司简介<span>&gt;</span></a>
        <%-- <a href="${ctx }/company">公司资质<span>&gt;</span></a>
        <a href="${ctx }/partner" class="on">合作伙伴<span>&gt;</span></a> --%>
        <a href="${ctx }/contact">联系我们<span>&gt;</span></a>
        <%-- <a href="${ctx }/companypic">公司展示<span>&gt;</span></a> --%>
    </div>
    <div class="news_mainbar">
        <h2>合作伙伴</h2>
        <div class="news_pt">
            <ul class="news_ptlist">
                <c:forEach items="${partners}" var="partner">
                 	<li><a href="${partner.linkUrl}" target="_blank"><img src="${ctx}/getRemoteContent?contentUrl=${partner.imgPath}"></a></li>
                </c:forEach>
                <li><a href="http://www.cmbchina.com/" target="_blank"><img src="${ctx}/static/images/news/pt_cmbc.png"></a></li>	
                <li><a href="https://www.ebaoquan.org/" target="_blank"><img src="${ctx}/static/images/news/pt_safe.png"></a></li>		
            </ul>
        </div>
    </div>

</div>

<%@include file="../common/footer.jsp"%>
<%@ include file="../common/dsp.jsp"%>
</body>


</html>


