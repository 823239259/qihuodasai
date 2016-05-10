<%@page import="com.umpay.api.log.SysOutLogger"%>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@ page import="com.tzdr.common.utils.ConfUtil"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	String appPath = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+appPath;
	String stockForumBasePath = ConfUtil.getContext("discussStock.url");  
	Boolean isOpanStockForum = Boolean.valueOf(ConfUtil.getContext("lg.isOpen"));
	String p2pAccount = ConfUtil.getContext("p2p.user.account");  
	String p2pMyBorrowMoney = ConfUtil.getContext("p2p.my.borrow.money");  
	String p2pMyInvestment = ConfUtil.getContext("p2p.my.investment");  
	String futureUrl=ConfUtil.getContext("tzdr.future.url");
%>
<c:set var="ctx" value="<%=basePath%>"></c:set>
<c:set var="v" value="201511447"></c:set>
<c:set var="sfb" value="<%=stockForumBasePath%>"></c:set>
<c:set var="iosf" value="<%=isOpanStockForum%>"></c:set>
<c:set var="p2pAccount" value="<%=p2pAccount%>"></c:set>
<c:set var="p2pMyBorrowMoney" value="<%=p2pMyBorrowMoney%>"></c:set>
<c:set var="p2pMyInvestment" value="<%=p2pMyInvestment%>"></c:set>
<c:set var="futureUrl" value="<%=futureUrl %>"></c:set>
<script language="javascript" src="${ctx}/static/script/common/jquery-1.8.0.min.js"></script>
<script language="javascript" src="${ctx}/static/script/common/commonUtils.js"></script>
<!-- <script charset="utf-8" src="http://wpa.b.qq.com/cgi/wpa.php"></script> -->
<script language="javascript" src="${ctx}/static/script/common/discussStock.js?version=20150812"></script>
<%-- <script language="javascript" src="${ctx}/static/script/common/BizQQWPA.js?version=20150515"></script> --%>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/common.css?version=20150724">
<link rel="shortcut icon" href="${ctx}/static/ico/pgb.ico">
<script type="text/javascript">
	
	var basepath='${ctx}'+"/";
	var isLoginSSO=Boolean("${sessionScope.tzdrUser}");
	var sc_width = $(window).height() ;
	//if(isIE = navigator.userAgent.indexOf("MSIE")!=-1) { 
		/* if(sc_width >= 780){
		    loadjscssfile(basepath+'static/css/common.css','css');
		}else{
		    loadjscssfile(basepath+'static/css/common.mini.css','css');
		} */
	//} 
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
		window.open('http://wpa.b.qq.com/cgi/wpa.php?ln=1&uin=4000200158&curl=http://www.tzdr.com/','在线客服','height=405,width=500,top=200,left=200,toolbar=no,menubar=no,scrollbars=yes, resizable=no,location=no, status=no');
	}
</script>
<span style="display:none"><script src='http://w.cnzz.com/q_stat.php?id=1257376699&l=3' language='JavaScript'></script></span>
<script>
$(document).ready(function(e) {
	$.ajaxSetup({ 
    	contentType : "application/x-www-form-urlencoded;charset=utf-8", 
    	complete : function(XMLHttpRequest, textStatus) { 
    	var sessionstatus = XMLHttpRequest.getResponseHeader("sessionstatus"); // 通过XMLHttpRequest取得响应头，sessionstatus， 
    	if (sessionstatus == "timeout") { 
    	// 如果超时就处理 ，指定要跳转的页面 
    	 window.location.replace(basepath+"login"); 
    	} 
      } 
    }); 
	
	$('.notic a').click(function() {
        $('.notic').slideUp();
    });
});	
</script>