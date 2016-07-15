<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>
<%@include file="../common/import-fileupload-js.jspf"%>
<%@include file="../common/import-artDialog-js.jspf"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>公司简介 - 投资达人</title>
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
	         <a href="${ctx }/about"  class="on">公司简介</a>
	       <%--  <a href="${ctx }/company" >公司资质</a>
	        <a href="${ctx }/partner">合作伙伴</a> --%>
	        <a href="${ctx }/contact">联系我们</a>
	       <%--  <a href="${ctx }/companypic">公司展示</a> --%>
	    </div>
	    <div class="news_mainbar">
	        <div class="news_cp">
	            <h3><i>Who-我们是谁？</i></h3>
	            <p><i style="color: #fc3">维胜网</i>由<i style="color: #fc3">成都盈透科技有限公司</i>开发运营，成都盈透科技有限公司由国际专业投资机构与国内领先互联网运营商共同出资设立，致力于构建中国专业的创新商业应用平台，以互动新媒体、游戏、金融等领域为主线，为涉及行业领域提供领先的互联网产品、技术以及运营方案。</p>
	            <p>维胜网秉承专业、创新、全面、精细的核心理念，结合当前主要的互联网技术与运营手段，为合作伙伴与客户带来最大的价值</p>
	            <p>维胜网主要团队成员在政府、互联网、媒体与金融方面具备多年的从业经验；同时与国内相关的行业领跑者建立了紧密的战略合作关系。</p>
	            <h3 style="margin-top: 30px;"><i>What-我们是做什么的？</i></h3>
	        	<ul>
	        		<li style="color: #fc3; font-size: 16px; line-height: 28px;">我们的愿景：</li>
	        		<li style="color: #666; font-size: 14px; line-height: 28px;">成为中国专业的互联网金融及衍生品交易服务平台；</li>
	        		<li style="color: #666; font-size: 14px; line-height: 28px;">以客观化数据为基础，以量化策略为手段，为用户提供更好的金融盈利工具。</li>
	        	</ul>
	        	<ul style="margin-top: 30px;">
	        		<li style="color: #fc3; font-size: 16px; line-height: 28px;">我们提供的服务:</li>
	        		<li style="color: #666; font-size: 14px; line-height: 28px;">◆多终端即时交易系统</li>
	        		<li style="color: #666; font-size: 14px; line-height: 28px;">◆智能量化的行情服务</li>
	        		<li style="color: #666; font-size: 14px; line-height: 28px;">◆客观及时的数据聚合与数据分析</li>
	        	</ul>
	            <h3 style="margin-top: 30px;"><i>Why-为什么选择我们？</i></h3>
	            <div class="xuanzhgevs">
	            	<h4><i>专业</i></h4>
	            	<ul>
	            		<li>团队成员来自知名券商与海外投资银行</li>
	            		<li>多年的互联网金融开发运营经验</li>
	            	</ul>
	            </div>	
	            <div class="xuanzhgevs">
	            	<h4><i>创新</i></h4>
	            	<ul>
	            		<li>客观量化数据与交易的无缝整合</li>
	            		<li>多类型金融工具的复合创新产品</li>
	            	</ul>
	            </div>		
	            <div class="xuanzhgevs">
	            	<h4><i>全面</i></h4>
	            	<ul>
	            		<li>交易过程的细节全面关注</li>
	            		<li>全球主流市场的数据广泛整合</li>
	            	</ul>
	            </div>		
	            <div class="xuanzhgevs">	
	            	<h4><i>精细</i></h4>
	            	<ul>
	            		<li>交易决策信息的7*24小时实时推送；</li>
	            		<li>交易各节点的主动关注</li>
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


