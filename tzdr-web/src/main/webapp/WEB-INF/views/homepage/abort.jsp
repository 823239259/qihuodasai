<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>
<%@include file="../common/import-fileupload-js.jspf"%>
<%@include file="../common/import-artDialog-js.jspf"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>公司简介 - 维胜</title>
<link rel="stylesheet" href="${ctx}/static/css/login.css">
<link rel="stylesheet" href="${ctx}/static/css/news.css">
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/tzdr.css">
<script type='text/javascript' src="${ctx}/static/script/tzdr.js"></script>



</head>
<body>
<!-- 顶部 -->
<%@include file="../common/header.jsp"%>
<div class="newsbox">
	<div class="news">
	     <div class="news_siderbar">
	        <!-- <a href="#">关于我们</a> -->
	        <a href="${ctx }/about"  class="on">公司简介<span>&gt;</span></a>
	        <%-- <a href="${ctx }/company" >公司资质<span>&gt;</span></a>
	        <a href="${ctx }/partner">合作伙伴<span>&gt;</span></a> --%>
	        <a href="${ctx }/contact">联系我们<span>&gt;</span></a>
	       <%--  <a href="${ctx }/companypic">公司展示<span>&gt;</span></a> --%>
	    </div>
	    <div class="news_mainbar">
	        <h2>公司简介</h2>
	        <div class="news_cp">
	            <img src="${ctx }/static/images/news/cp_banner.jpg">
	            <h3><i>关于维胜网</i></h3>
	            <p>维胜网(www.tzdr.com)系上海信闳投资管理有限公司倾力打造的、专注于股票操盘的互联网金融服务平台。通过建构便捷、安全、良性的互联网金融平台，维胜致力于加速投资者的财富之路，保障投、融双方进行健康、可持续的投资行为。上海信闳投资管理有限公司是首批响应国家金融创新号召，注册在上海自贸区的金融创新企业，注册资本500万元人民币。公司前身在是Pinnacle Enterprise Pte Ltd为总部的多元化产业集团，涉足的行业有：房地产，银行业，电子商务，在线游戏，风险投资等。</p>
	            <h3><i>我们的优势</i></h3>
	            <p><b>1）资金管理优势：</b>维胜对客户交易资金的管理完全按照"专户专款专用"的标准模式进行运作，并且采用三方监管——资金由银行监管，交易由券商监管，风控由维胜监管。维胜与招商银行达成合作关系，招商银行作为维胜网的第三方账户存管银行，有权对存管账户资金进出进行管理、对账户资金进行确认，确保客户的资金安全。客户在维胜的交易资金是可以完全放心的。<br><span><b>2） 交易管理优势：</b>所有股票交易委托均通过交易系统实时进入合作券商在市场上成交，确保交易安全、快捷，客户挂单真实可见，每笔交易都可在开通level2功能的股票行情软件中查询。<br></span><span><b>3） 服务管理优势：</b>维胜透过自主企业出资，引入多级风控管理机制，降低投资风险和门槛。维胜提供的股票操盘服务效率高、收费透明、流程简单，用心助每一位客户实现财富梦想、走上人生巅峰。</span></p>
	            <h3><i>文化理念</i></h3>
	            <p>公司本着“人才、创新、诚信、共赢”的创业理念，汇集了数十位海内外行业专家、互联网专家。维胜作为本土领先的互联网金融交易平台之一，依托团队优势和行业资源，秉承“让人人都有资格享受金融服务”的服务理念，始终走在行业发展的前列。</p>
	            <h3><i>我们的愿景</i></h3>
	            <p>专注于股票与期货等金融投资领域，消除来自资金和账户开设方面的交易门槛，助力投资者的财富梦想。<br><span>凭借多年行业经验，以投资者为中心，通过便捷、安全的服务，保障投、融双方进行健康、可持续的投资行为，建构可信任的互联网金融服务环境。<br></span><span>秉承客户至上的理念，打造功能多样化、产品多元化的互联网金融综合平台，不断加强创新能力，提高综合实力，领导行业发展，促进全民普惠金融。</span></p>
	        </div>
	    </div>
	
	</div>
</div>

<%@include file="../common/footer.jsp"%>
<%@ include file="../common/dsp.jsp"%>
</body>


</html>


