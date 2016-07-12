<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>
<%@include file="../common/import-fileupload-js.jspf"%>
<%@include file="../common/import-artDialog-js.jspf"%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="zh">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新闻中心 - 维胜</title>
 <link rel="stylesheet" href="${ctx}/static/css/news.css"  type="text/css">
 	
 <link href="${ctx}/static/css/pagination.css" rel="stylesheet" type="text/css" /> 
 <script src="${ctx}/static/script/common/jquery.pagination.js" type="text/javascript"></script> <link href="common/tablesorter.css" rel="stylesheet" type="text/css" />
 <script src="${ctx}/static/script/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
	
 <script type='text/javascript' src="${ctx}/static/script/news/news.js"></script>
 <script type='text/javascript' src="${ctx}/static/script/common/dateUtils.js"></script>

</head>
<body>
<!-- 顶部 -->
<%@include file="../common/header.jsp"%>
<input type="hidden" name="pageno" id="pageno">
<div class="news">
    <div class="news_siderbar">
    	<h2><a href="${ctx}/news/newsdata" iddata="" title="新闻中心" >新闻中心</a></h2>
       
    	<c:forEach  items="${newscols}" var="newscol" varStatus="status">
    		<c:choose>
    			<c:when test="${col!=null}">
    			   <c:choose>
	    				<c:when test="${col.name==newscol.name}">
	    					<a href="javascript:void(0)" class="on" id="colnameon" title="${newscol.name}" iddata="${newscol.id}">${newscol.name}</a>
	    				</c:when>
	    				<c:otherwise>
	    					<a href="javascript:void(0)"  title="${newscol.name}"  iddata="${newscol.id}">${newscol.name}</a>
	    				</c:otherwise>
    				</c:choose>
    			</c:when>
    			<c:otherwise>
    				<a href="javascript:void(0)"  title="${newscol.name}"  iddata="${newscol.id}">${newscol.name}</a>
    			</c:otherwise>
    		</c:choose>
		</c:forEach>
    </div>
    <div class="news_mainbar">
        <h2><span id="newstitle">
        <c:choose>
    			<c:when test="${col!=null}">
    				${col.name}
    			</c:when>
    			<c:otherwise>
    				新闻中心
    			</c:otherwise>
    	</c:choose>
        	
        </span>
        </h2>
        <ul class="news_newslist">
        </ul>
        <div class="uc_tpage" id="pagebtndiv">
     		<div id="Pagination"></div> 
     		<c:forEach  items="${newscols}" var="newscol" varStatus="status">
    			<div id="Pagination${newscol.id}"></div> 
			</c:forEach>
        </div>
    </div>

</div>
<!-- 公司简介 -->
<%@include file="../common/footer.jsp"%>
<%@ include file="../common/dsp.jsp"%>
</body>
</html>

