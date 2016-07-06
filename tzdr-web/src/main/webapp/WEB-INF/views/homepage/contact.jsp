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
<%@ include file="../common/personheader.jsp"%>
<div class="news">
     <div class="news_siderbar">
        <h2>关于我们</h2>
            <a href="${ctx }/about"  >公司简介</a>
        <a href="${ctx }/company" >公司资质</a>
        <a href="${ctx }/partner">合作伙伴</a>
        <a href="${ctx }/contact" class="on">联系我们</a>
        <a href="${ctx }/companypic">公司展示</a>
    </div>
    <div class="news_mainbar">
        <h2>公司地址</h2>
        <div class="news_con">
             <div class="news_map"><img src="${ctx }/static/images/news/map.jpg"></div>   
             <h3>公司总部：中国（上海）自由贸易试验区加太路29号1幢东部404-A21室</h3>
             <h4>客户服务</h4>
             <p><span>如果您在投资达人的体验过程中有任何疑问，请与客服人员联系。<br></span><span>客服电话：400-020-0158<br></span><span>客服QQ：4000200158<br></span><span>客服QQ群： 196113230</span></p>
             <h4>商业合作</h4>
             <p><span>投资达人诚邀有一定客户基础和有股票操盘经验的机构或个人进行商业合作。<br></span><span>合作请联系：marketing@tzdr.com <br></span><span>联系电话：13527539642<br></span><span>联系人：曹经理</span></p>
             <h4>媒体采访</h4>
               <p><span>投资达人欢迎媒体朋友进一步了解并为我们提供宝贵的建议，共同探讨、推进行业发展。<br></span><span>采访请联系：media@tzdr.com <br></span><span>联系电话：13527539642<br></span><span>联系人：曹经理</span></p>
             <h4>加入我们</h4>
             <p><span>投资达人需要金融证券等行业精英的加入，不断创新、共同成长！<br></span><span>应聘请联系：hr@tzdr.com <br></span><span>联系电话：18512393959<br></span><span>联系人：刘经理</span></p>
             <h4>关注我们</h4>
             <ul>
                <li class="news_con_wb">
                    <h5>投资达人官网微信</h5>
                    <img src="${ctx }/static/images/news/wb.jpg">
                </li>
                <li class="news_con_wx">
                    <h5>投资达人官方微博</h5>
                    <img src="${ctx }/static/images/news/wx.jpg">
                </li>
             </ul>
        </div>
    </div>

</div>



<%@ include file="../common/personfooter.jsp"%>
<%@ include file="../common/dsp.jsp"%>
</body>


</html>


