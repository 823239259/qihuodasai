<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>
<%@include file="../common/import-fileupload-js.jspf"%>
<%@include file="../common/import-artDialog-js.jspf"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>联系我们 - 维胜</title>
<link rel="stylesheet" href="${ctx}/static/css/login.css">
<link rel="stylesheet" href="${ctx}/static/css/news.css">
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/tzdr.css">
<script type='text/javascript' src="${ctx}/static/script/tzdr.js"></script>



</head>
<body>
<!-- 顶部 -->
<%@include file="../common/header.jsp"%>
<div class="news">
     <div class="news_siderbar">
        <!-- <h2>关于我们</h2> -->
        <a href="${ctx }/about"  >公司简介<span>&gt;</span></a>
        <%-- <a href="${ctx }/company" >公司资质<span>&gt;</span></a>
        <a href="${ctx }/partner">合作伙伴<span>&gt;</span></a> --%>
        <a href="${ctx }/contact" class="on">联系我们<span>&gt;</span></a>
        <%-- <a href="${ctx }/companypic">公司展示<span>&gt;</span></a> --%>
    </div>
    <div class="news_mainbar">
        <h2>公司地址</h2>
        <div class="news_con">
             <div class="news_map"><img src="${ctx }/static/images/help/vs-help-26.png" style="width: 703px;height: 274px;"></div> 
             <h3>维胜网</h3>  
             <h3>成都盈透科技有限公司</h3>
             <h3>四川省成都市武侯区世纪城南路599号软件园D区</h3>
             <h4>客户服务</h4>
             <p><span>如果您在使用维胜网(www.vs.com)的过程中有任何疑问请您与维胜网客服人员联系。<br></span><span>客服热线：400-852-8008<br></span><span>工作时间：工作日9:00-12:00 13:30-18:00<br></span><span>客服邮箱：support@vs.com</span></p>
             <h4>商务合作</h4>
             <p><span>如果贵公司希望与我们建立合作关系，形成优势互补，请根据合作类型按照下面的联系方式联系我们。<br></span><span>营销/推广合作：marketing@vs.com<br></span><span>媒体合作：media@vs.com<br></span><span>其他合作：business@vs.com</span></p>
             <%-- <h4>媒体采访</h4>
               <p><span>维胜欢迎媒体朋友进一步了解并为我们提供宝贵的建议，共同探讨、推进行业发展。<br></span><span>采访请联系：media@tzdr.com <br></span><span>联系电话：13527539642<br></span><span>联系人：曹经理</span></p>
             <h4>加入我们</h4>
             <p><span>维胜需要金融证券等行业精英的加入，不断创新、共同成长！<br></span><span>应聘请联系：hr@tzdr.com <br></span><span>联系电话：18512393959<br></span><span>联系人：刘经理</span></p>
             <h4>关注我们</h4>
             <ul>
                <li class="news_con_wb">
                    <h5>维胜官网微信</h5>
                    <img src="${ctx }/static/images/news/wb.jpg">
                </li>
                <li class="news_con_wx">
                    <h5>维胜官方微博</h5>
                    <img src="${ctx }/static/images/news/wx.jpg">
                </li>
             </ul> --%>
        </div>
    </div>

</div>



<%@include file="../common/footer.jsp"%>
<%@ include file="../common/dsp.jsp"%>
</body>


</html>


