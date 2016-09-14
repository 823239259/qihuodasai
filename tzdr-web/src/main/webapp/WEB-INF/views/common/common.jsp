<%@page import="com.tzdr.web.constants.Constants"%>
<%@page import="com.tzdr.web.utils.UserSessionBean"%>
<%@page import="com.umpay.api.log.SysOutLogger"%>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@ page import="com.tzdr.common.utils.ConfUtil"%> 
<%
	String appPath = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+appPath;
	String stockForumBasePath = ConfUtil.getContext("discussStock.url");  
	Boolean isOpanStockForum = Boolean.valueOf(ConfUtil.getContext("lg.isOpen"));
	String p2pAccount = ConfUtil.getContext("p2p.user.account");  
	String p2pMyBorrowMoney = ConfUtil.getContext("p2p.my.borrow.money");  
	String p2pMyInvestment = ConfUtil.getContext("p2p.my.investment");  
	String futureUrl=ConfUtil.getContext("tzdr.future.url");
	UserSessionBean userSessionBean = (UserSessionBean) request.getSession().getAttribute(Constants.TZDR_USER_SESSION);
	String userId = null;
	if(userSessionBean != null){
		userId = userSessionBean.getId();
	}
%>
<c:set var="ctx" value="<%=basePath%>"></c:set>
<c:set var="v" value="20160914"></c:set>
<c:set var="sfb" value="<%=stockForumBasePath%>"></c:set>
<c:set var="iosf" value="<%=isOpanStockForum%>"></c:set>
<c:set var="p2pAccount" value="<%=p2pAccount%>"></c:set>
<c:set var="p2pMyBorrowMoney" value="<%=p2pMyBorrowMoney%>"></c:set>
<c:set var="p2pMyInvestment" value="<%=p2pMyInvestment%>"></c:set>
<c:set var="futureUrl" value="<%=futureUrl %>"></c:set>
<script language="javascript" src="${ctx}/static/script/common/jquery-1.8.0.min.js?v=${v}"></script>
<script language="javascript" src="${ctx}/static/script/common/commonUtils.js?v=${v}"></script>
<script language="javascript" src="${ctx}/static/script/common/discussStock.js?v=${v}"></script>
<link rel="stylesheet" href="${ctx }/static/css/new_index.css?v=${v}">
<link rel="shortcut icon" href="${ctx}/static/ico/icon.png">
<%@ include file="../common/dsp.jsp"%>
<!-- <!— Start Alexa Certify Javascript —> -->
<script type="text/javascript">
_atrk_opts = { atrk_acct:"lhQPn1QolK10WR", domain:"vs.com",dynamic: true};
(function() { var as = document.createElement('script'); as.type = 'text/javascript'; as.async = true; as.src = "https://d31qbv1cthcecs.cloudfront.net/atrk.js"; var s = document.getElementsByTagName('script')[0];s.parentNode.insertBefore(as, s); })();
</script>
<noscript><img src="https://d5nxst8fruw4z.cloudfront.net/atrk.gif?account=lhQPn1QolK10WR" style="display:none" height="1" width="1" alt="" /></noscript>
<!-- <!— End Alexa Certify Javascript —> -->
<script type="text/javascript">
	var basepath='${ctx}'+"/";
	var isLoginSSO=Boolean("${sessionScope.tzdrUser}");
	var sc_width = $(window).height() ;
	function loadjscssfile(filename,filetype){
	    if(filetype == "js"){
	        var fileref = document.createElement('script');
	        fileref.setAttribute("type","text/javascript");
	        fileref.setAttribute("src",filename);
	    }else if(filetype == "css"){
	    
	        var fileref = document.createElement('link');
	        fileref.setAttribute("rel","stylesheet");
	        fileref.setAttribute("type","text/css");
	        fileref.setAttribute("href",filename);
	    }
	   if(typeof fileref != "undefined"){
	        document.getElementsByTagName("head")[0].appendChild(fileref);
	    }
	}
	var stockForumBasePath = '${sfb}'+"/";
	var isOpanStockForum = '${iosf}';
	function showQQDialog(){
		window.open('http://wpa.b.qq.com/cgi/wpa.php?ln=1&uin=4000200158&curl=http://www.vs.com/','在线客服','height=405,width=500,top=200,left=200,toolbar=no,menubar=no,scrollbars=yes, resizable=no,location=no, status=no');
	}
</script>
<span style="display:none"><script src="https://s95.cnzz.com/z_stat.php?id=1259839078&web_id=1259839078" language="JavaScript"></script></span>