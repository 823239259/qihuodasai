<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>
<%@include file="../common/import-fileupload-js.jspf"%>
<%@include file="../common/import-artDialog-js.jspf"%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="zh">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta content="${news.keyWord}" name="keywords">
<meta content="${news.summary}" name="description">
 <link rel="stylesheet" href="${ctx}/static/css/news.css"  type="text/css">	
 <link href="${ctx}/static/css/pagination.css" rel="stylesheet" type="text/css" /> 
 <script src="${ctx}/static/script/common/jquery.pagination.js" type="text/javascript"></script> <link href="common/tablesorter.css" rel="stylesheet" type="text/css" />
 <script src="${ctx}/static/script/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
	
 <script type='text/javascript' src="${ctx}/static/script/common/dateUtils.js"></script>
 <title>${news.name} - 维胜</title>

</head>
<body>
<!-- 顶部 -->
<%@ include file="../common/personheader.jsp"%>
<div class="news">
	<input type="hidden" name="newscolname" id="newscolname" value="${news.parentConfig.id}">
    <div class="news_siderbar">
        <h2><a href="${ctx}/news/newsdata" iddata="" >新闻中心</a></h2>
        <c:forEach  items="${newscols}" var="newscol" varStatus="status">
        	<c:choose>
        		<c:when test="${newscol.id==news.parentConfig.id}">
        		 <a  href="${ctx}/news/newsdata?colname=${newscol.id}" class="on" id="${newscol.id}">${newscol.name}</a>
        		</c:when>
        		<c:otherwise>
        		 <a href="${ctx}/news/newsdata?colname=${newscol.id}" id="${newscol.id}">${newscol.name}</a>
        		</c:otherwise>
        	</c:choose>
		</c:forEach>

    </div>
    <div class="news_mainbar">
        <div class="news_title">
            <h3>${news.parentConfig.name }</h3>
            <c:choose>
            	<c:when test="${type=='all'}">
            	 <a href="${ctx}/news/newsdata" ><< 返回列表</a>
            	</c:when>
            	<c:otherwise>
            		 <a href="${ctx}/news/newsdata?colname=${news.parentConfig.id}" ><< 返回列表</a>
            	</c:otherwise>
            </c:choose>
           
        </div>
        <div class="news_ctn">
            <h4>${news.name}</h4>
            <span class="news_ctntime">发布时间：${news.defineReleaseTime}</span>
            <div class="news_ctninfo">${news.content}</div>
        </div>
    </div>

</div>

<!-- 公司简介 -->
<%@ include file="../common/personfooter.jsp"%>
<%@ include file="../common/dsp.jsp"%>
</body>
</html>


