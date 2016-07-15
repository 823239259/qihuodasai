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
	            <%-- <img src="${ctx }/static/images/news/cp_banner.jpg"> --%>
	            <h3><i>Who-我们是谁？</i></h3>
	            <p>维胜网由成都盈透科技有限公司开发运营，成都盈透科技有限公司由国际专业投资机构与国内领先互联网运营商共同出资设立，致力于构建中国专业的创新商业应用平台，以互动新媒体、游戏、金融等领域为主线，为涉及行业领域提供领先的互联网产品、技术以及运营方案。</p>
	            <p>维胜网秉承专业、创新、全面、精细的核心理念，结合当前主要的互联网技术与运营手段，为合作伙伴与客户带来最大的价值。</p>
	            <p>维胜网主要团队成员在政府、互联网、媒体与金融方面具备多年的从业经验；同时与国内相关的行业领跑者建立了紧密的战略合作关系。</p>
	            <h3><i>What-我们是做什么的？</i></h3>
	            <p><b>1）我们的愿景</b></p>
	            <p>成为中国专业的互联网金融及衍生品交易服务平台；</p>
	            <p>以客观化数据为基础，以量化策略为手段，为用户提供更好的金融盈利工具。</p>
	            <p><b>2） 我们提供的服务</b></p>
	            <p>多终端即时交易系统</p>
	            <p>智能量化的行情服务</p>
	            <p>客观及时的数据聚合与数据分析</p>
	            <h3><i>Why-为什么选择我们？</i></h3>
	            <div class="xuanzhgevs">
	            	<h4>专业</h4>
	            	<ul>
	            		<li>•团队成员来自知名券商与海外投资银行</li>
	            		<li>•多年的互联网金融开发运营经验</li>
	            	</ul>
	            </div>	
	            <div class="xuanzhgevs">
	            	<h4>创新</h4>
	            	<ul>
	            		<li>•客观量化数据与交易的无缝整合</li>
	            		<li>•多类型金融工具的复合创新产品</li>
	            	</ul>
	            </div>		
	            <div class="xuanzhgevs">
	            	<h4>全面</h4>
	            	<ul>
	            		<li>•交易过程的细节全面关注</li>
	            		<li>•全球主流市场的数据广泛整合</li>
	            	</ul>
	            </div>		
	            <div class="xuanzhgevs">	
	            	<h4>精细</h4>
	            	<ul>
	            		<li>•交易决策信息的7*24小时实时推送；</li>
	            		<li>•交易各节点的主动关注</li>
	            	</ul>
	            </div>	
	        </div>
	    </div>
	
	</div>
</div>

<%@include file="../common/footer.jsp"%>
<%@ include file="../common/dsp.jsp"%>
</body>


</html>


