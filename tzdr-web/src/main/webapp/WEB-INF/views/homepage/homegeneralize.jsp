<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>
<%@include file="../common/import-fileupload-js.jspf"%>
<%@include file="../common/import-artDialog-js.jspf"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta content="投资达人提供股票操盘、P2P贷款、投资理财、股指期货等交易服务，更高的收益，更加安全可靠；投资达人致力于打造中国领先的互联网金融交易平台." name="description">
<title>推广赚钱_维胜-中国领先的互联网金融交易平台</title>
<link href="${ctx}/static/css/tzdr.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="${ctx}/static/generalize/css/sp.css">
<script type='text/javascript' src="${ctx}/static/script/homepage/generalize.js"></script>
<script language="javascript" src="${ctx}/static/script/common/tzdr.user.js?version=20150720"></script>
<script type='text/javascript' src="${ctx}/static/script/tzdr.js"></script>
<script type="text/javascript">
$(".navlist").removeClass("dja");
$("#generalizeli").addClass("on");
$(document).ready(function(){
	$('.navlist li a').removeClass('on');
	$("#generalizeli").addClass("on");
 });
</script>
</head>
<body>
<!-- 顶部 -->
<%@ include file="../common/personheader.jsp"%>
<div class="main">
    <div class="uc_sp_info">
        <div class="uc_sp_list">
            <h3>代理佣金榜</h3>
            <ul>
            
                <ol class="uc_spl_title">
                    <li class="uc_spl_num"></li>
                    <li class="uc_spl_name">代理用户</li>
                    <li class="uc_spl_money">佣金收入</li>
                </ol>   
              <c:forEach items="${generalizeVisitUserVoList}" var="generalizeVisitUserVo" varStatus="status">
					<ol>
						<li class="uc_spl_num">${status.index+1}</li>
						<li class="uc_spl_name">${generalizeVisitUserVo.mobile}</li>
						<li class="uc_spl_money">
						<c:choose>
							<c:when test="${!empty generalizeVisitUserVo.totalCommission}"><fmt:formatNumber type="number" value="${generalizeVisitUserVo.totalCommission}" pattern="#,###.##" minFractionDigits="2" /></c:when>
							<c:otherwise>0.00</c:otherwise>
						</c:choose>
						元</li>
					</ol>
				</c:forEach>
               
            </ul>
        </div>
        <div class="uc_sp_font">
            <h3>2015做什么最赚钱？</h3>            
            <p><span>投资达人独创股票操盘行业无限级代理制度，成功邀请下级，即可获得超高收益分成。下级用户再邀请下级用户，所有上级用户均可获得收益分成，轻松赚大钱！<br></span><span>您可以通过朋友、QQ、微信、微博等各种方式进行推广，所有通过您的推广链接访问而来的用户，注册后都将成为您的下线用户，而当这些用户在投资达人网站时，您便可以赚取所有下线用户一定比例的返点。</span> </p>
            <h3>做投资达人的代理能赚多少钱？</h3>
            <p><span>成为投资达人代理后，您发展的下级可再发展无限个下级，您可以获得所有下级用户的返点，收益比普通单级返点方式高出数十倍。到目前为止，投资达人代理体系已向所有代理用户发放了几百万元的佣金，有几位做得比较好的代理用户，他们经过几个月的努力，现在每月佣金收入就有十几万元。</span> </p>
            <h3>佣金如何结算？</h3>
            <p><span>您的下级用户每发生一笔，在完成后您就获得一笔返点，当天的返点将在第二天凌晨0点统一为您充值到投资达人网站的账户中。当您的佣金到达10元时，您就可以随时兑换并提现，当然也可以在本站进行。</span></p>
        </div>
    </div>
     <div class="uc_sp_way">
        <h3><i>如何赚钱</i><img src="${ctx }/static/images/uc/sp_03.gif"></h3>
        <div class="uc_sp_wayctn"><img src="${ctx }/static/images/uc/rhzq.jpg"></div>
    </div>
      <div class="uc_sp_way">
        <h3><i>如何推广</i><img src="${ctx }/static/images/uc/sp_03.gif"></h3>
        <div class="uc_sp_wayctn"><img src="${ctx }/static/images/uc/rhtg.jpg"></div>
    </div>
   
  
    <div class="sp_btn">
        <a href="${ctx}/generalize/details?tab=generalize">立即推广</a>

    </div>
</div>


<%@ include file="../common/personfooter.jsp"%>
<%@ include file="../common/dsp.jsp"%>
</body>


</html>


