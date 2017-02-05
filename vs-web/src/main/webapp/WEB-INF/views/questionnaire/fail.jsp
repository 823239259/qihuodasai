<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>
<%@include file="../common/import-fileupload-js.jspf"%>
<%@include file="../common/import-artDialog-js.jspf"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="../common/commonkeyword.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/common.css">
<link rel="stylesheet" href="${ctx}/static/activies/css/sp.css"/>
<link rel="stylesheet" href="${ctx}/static/script/valForm/css/style.css">

<link rel="stylesheet" href="${ctx}/static/css/tzdr.css">
 <script src="${ctx}/static/script/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
 <script src="${ctx}/static/script/questionnaire/questionnaire.js" type="text/javascript"></script>
 <script src="${ctx}/static/script/tzdr.js" type="text/javascript"></script>
<script src="${ctx}/static/script/common/tzdr.user.js?version=20150720" type="text/javascript"></script>		
<script src="${ctx}/static/script/jquery.validate.min.js" type="text/javascript"></script>		

	
</head>
<body>
<div class="question">
    <div class="qbanner1"></div>
    <div class="qbanner2"></div>
    <div class="qstep">
        <img src="${ctx}/static/activies/images/qtitle_03.PNG">
    </div>
    
     <div class="qs_bg">
	    <div class="tp_main">
	        <h3>已申请失败:</h3>
	        <div class="tp_font">来晚了，账号抢光了。<br>感谢您的参与，我们即将推出更多优惠活动，敬请关注。<br>您可以<a href="${ctx }/signin">立即注册</a>，完成您的投资</div>
	    </div>
    </div>
</div>


<!-- 公司简介 -->
<!-- DINGBU -->
<%@ include file="../common/dsp.jsp"%>
</body>
</html>
