<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>
<%@include file="../common/import-fileupload-js.jspf"%>
<%@include file="../common/import-artDialog-js.jspf"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>公司展示 - 投资达人</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/common.css">
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/news.css">
<script type='text/javascript' src="${ctx}/static/script/tzdr.js"></script>
</head>

<body>
<!-- 顶部 -->
<%@ include file="../common/personheader.jsp"%>
<div class="news">
     <div class="news_siderbar">
        <h2>关于我们</h2>
        <a href="${ctx }/about">公司简介</a>
        <a href="${ctx }/company">公司资质</a>
        <a href="${ctx }/partner">合作伙伴</a>
        <a href="${ctx }/contact">联系我们</a>
        <a href="${ctx }/companypic" class="on">公司展示</a>
    </div>
    <div class="news_mainbar">
        <h2>公司展示</h2>
        <div class="news_cp">
            <img src="${ctx }/static/images/news/cppic_banner.jpg">
            <h3><i>办公环境</i></h3>
            <p>公司目前拥有数百平米的独立办公区域，为营造好的工作氛围，公司精心为员工打造一个舒适、优雅、愉快的办公环境。宽敞的办公区域、舒适的休息区、下午茶必到场所茶水间……每一个场所都体现出公司对员工的关怀。公司力求打造员工以企业为骄傲，企业以员工而自豪的企业氛围。</p>
            <div class="cp_piclist">
                <img src="${ctx }/static/images/news/cppic_01.jpg" width="703" height="233">
                <img src="${ctx }/static/images/news/cppic_02.jpg" width="703" height="234">
                <img src="${ctx }/static/images/news/cppic_03.jpg" widht="703" height="192">
            </div>
            <h3><i>公司活动</i></h3>
            <h5 class="cp_pictitle">踏青拓展活动：奔跑吧！投资达人！</h5>
            <p>阳春四月，万物复苏，这是出游玩耍、踏青拓展的美好时节。在这个充满了春天气息的大好日子，公司为增加员工的归属感，促进大家的沟通交流，发扬广大员工群策群力、同舟协作的奋进精神，特组织了四月郊游。</p>
            <div class="cp_piclist">
                <img src="${ctx }/static/images/news/cppic_04.jpg">
                <img src="${ctx }/static/images/news/cppic_05.jpg">
            </div>
        </div>
    </div>

</div>
<%@ include file="../common/personfooter.jsp"%>
<%@ include file="../common/dsp.jsp"%>
</body>
</html>
