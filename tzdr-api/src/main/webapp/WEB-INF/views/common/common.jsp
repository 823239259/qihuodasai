<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<%
	String appPath = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+appPath+"/";
// 	System.out.println(request.getHeader("user-agent"));
%>
<script language="javascript" src="${ctx}/static/script/common/jquery-1.8.0.min.js"></script>
<script language="javascript" src="${ctx}/static/script/common/commonUtils.js"></script>
<link rel="stylesheet" type="text/css"  href="${ctx}/static/css/common.css"/>
<link rel="shortcut icon" href="${ctx}/static/ico/tzdr.ico">

<script>
	var basepath='${ctx}'+"/";
</script>
