<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>
<%@include file="../common/import-fileupload-js.jspf"%>
<%@include file="../common/import-artDialog-js.jspf"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>公司资质 - 维胜</title>
<link rel="stylesheet" href="${ctx}/static/css/login.css">
<link rel="stylesheet" href="${ctx}/static/css/news.css?20120520">
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/tzdr.css">
<script type='text/javascript' src="${ctx}/static/script/tzdr.js"></script>
<script type="text/javascript">
$(document).ready(function() {
    $('.news_cpimglist li a').each(function() {
        $(this).click(function() {
            $('.fl_conimg').hide();
            $('.fl_mask').show();
            var value = $(this).attr('data');
            $('.fl_box'+value).show();
        });
    });
    $('.fl_conimg .fl_navtitle a').each(function() {
        $(this).click(function() {
            $('.fl_conimg').hide();
            $('.fl_mask').hide();
        });
    });

});
</script>



</head>
<body>
<!-- 顶部 -->
<%@ include file="../common/personheader.jsp"%>
<div class="floatlayer">
    <div class="fl_mask" style="display:none;"></div>
    <div class="fl_conimg fl_box1" style="display:none;">
        <div class="fl_navtitle">
            <h3 class="fl_logintitle">税务登记证</h3>
            <a href="javascript:void(0)" class="close"></a>
        </div>
        <div class="fl_conimgctn">
            <img src="${ctx}/static/images/news/cp_01.jpg">

        </div>

    </div>
    <div class="fl_conimg fl_box2" style="display:none;">
        <div class="fl_navtitle">
            <h3 class="fl_logintitle">营业执照副本</h3>
            <a href="javascript:void(0)" class="close"></a>
        </div>
        <div class="fl_conimgctn">
            <img src="${ctx}/static/images/news/cp_02.jpg">

        </div>

    </div>
    <div class="fl_conimg fl_box3" style="display:None;">
        <div class="fl_navtitle">
            <h3 class="fl_logintitle">组织机构代码证</h3>
            <a href="javascript:void(0)" class="close"></a>
        </div>
        <div class="fl_conimgctn">
            <img src="${ctx}/static/images/news/cp_03.jpg">

        </div>

    </div>
    <div class="fl_conimg fl_box4" style="display:None;">
        <div class="fl_navtitle">
            <h3 class="fl_logintitle">最具信赖的互联网金融机构</h3>
            <a href="javascript:void(0)" class="close"></a>
        </div>
        <div class="fl_conimgctn">
            <img src="${ctx}/static/images/news/cp_04.jpg">

        </div>

    </div>
    <div class="fl_conimg fl_box5" style="display:None;">
        <div class="fl_navtitle">
            <h3 class="fl_logintitle">互联网金融行业协会会员单位</h3>
            <a href="javascript:void(0)" class="close"></a>
        </div>
        <div class="fl_conimgctn">
            <img src="${ctx}/static/images/news/cp_05.jpg">
        </div>
    </div>
    <div class="fl_conimg fl_box6" style="display:None;">
        <div class="fl_navtitle">
            <h3 class="fl_logintitle">互联网金融行业十大影响力品牌</h3>
            <a href="javascript:void(0)" class="close"></a>
        </div>
        <div class="fl_conimgctn">
            <img src="${ctx}/static/images/news/cp_06.jpg">
        </div>
    </div>
    <div class="fl_conimg fl_box7" style="display:None;">
        <div class="fl_navtitle">
            <h3 class="fl_logintitle">投资理财服务最具公信力诚信品牌</h3>
            <a href="javascript:void(0)" class="close"></a>
        </div>
        <div class="fl_conimgctn">
            <img src="${ctx}/static/images/news/cp_07.jpg">
        </div>
    </div>

</div>
<div class="news">
     <div class="news_siderbar">
        <h2>关于我们</h2>
          <a href="${ctx }/about" >公司简介</a>
        <a href="${ctx }/company"  class="on">公司资质</a>
        <a href="${ctx }/partner">合作伙伴</a>
        <a href="${ctx }/contact">联系我们</a>
        <a href="${ctx }/companypic">公司展示</a>
    </div>
    <div class="news_mainbar">
        <h2>公司资质</h2>
        <div class="news_cp2">
            <img src="${ctx}/static/images/news/cp2_banner.jpg">
            <ul class="news_cpimglist">
                <li>
                    <a href="javascript:void(0);" data="1">
                        <img src="${ctx}/static/images/news/cp_01.jpg" width="208" height="153">
                        <p>税务登记证</p>
                    </a>
                </li>
                <li>
                    <a href="javascript:void(0);" data="2">
                        <img src="${ctx}/static/images/news/cp_02.jpg" width="103" height="153">
                        <p>营业执照副本</p>
                    </a>
                </li>
                <li>
                    <a href="javascript:void(0);" data="3">
                        <img src="${ctx}/static/images/news/cp_03.jpg" width="208" height="153">
                        <p>组织机构代码证</p>
                    </a>
                </li>
                <li>
                    <a href="javascript:void(0);" data="4">
                        <img src="${ctx}/static/images/news/cp_04.jpg" width="208" height="153">
                        <p>最具信赖的互联网金融机构</p>
                    </a>
                </li>
                <li>
                    <a href="javascript:void(0);" data="5">
                        <img src="${ctx}/static/images/news/cp_05.jpg" width="208" height="153">
                        <p>互联网金融行业协会会员单位</p>
                    </a>
                </li>
				<li>
                    <a href="javascript:void(0);" data="6">
                        <img src="${ctx}/static/images/news/cp_06.jpg" width="208" height="153">
                        <p>互联网金融行业十大影响力品牌</p>
                    </a>
                </li><li>
                    <a href="javascript:void(0);" data="7">
                        <img src="${ctx}/static/images/news/cp_07.jpg" width="208" height="153">
                        <p>投资理财服务最具公信力诚信品牌</p>
                    </a>
                </li>
                
            </ul>
        </div>
    </div>

</div>

<%@ include file="../common/personfooter.jsp"%>
<%@ include file="../common/dsp.jsp"%>
</body>


</html>


