<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="../common/common.jsp"%>
<%@include file="../common/import-artDialog-js.jspf"%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="zh">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="${ctx}/static/css/public.css?v=${v}" rel="stylesheet" type="text/css">
<link href="${ctx}/static/css/trade.css?v=${v}" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/capital.css?v=${v}">
<script language="javascript" src="${ctx}/static/script/tzdr.js?v=${v}"></script>
<script language="javascript" src="${ctx}/static/script/product/common.js?v=${v}"></script>

<script type="text/javascript" src="${ctx}/static/script/product/commodity.js?v=${v}"></script>
<title>商品综合 - 维胜</title>

<script type="text/javascript">
$(document).ready(function() {
    $('.cpp_r_nav').children('a').hover(function() {
        $('.cpp_r_nav').children('a').removeClass('on');
        $(this).addClass('on');
    });
    $('.cpp_r_nav').hover(function() {
    }, function() {
        $('.cpp_r_nav').children('a').removeClass('on');
    });
    $('.cpp_ru_list').each(function() {
        var pplist = $(this);
        pplist.find('a').hover(function() {
            pplist.find('.cpp_ru_promt').show();
        }, function() {            
            pplist.find('.cpp_ru_promt').hide();
        });
    });
    $('.cpp_r_nav a').click(function() {
        $('.cpp_r_nav').children('a').removeClass('point');
        $(this).addClass('point');
        var index = $(this).index();
        $('.cpp_r_list').hide();
        $('.cpp_r_list').eq(index).show();
        return false;
    });
});
</script>
</head>

<body>
<!--顶部 -->
	<!-- DINGBU -->
	<form action="${ctx}/userCommodity/pay" id="settingForm" method="post">
	<input type="hidden" id="traderBondAttr" name="traderBondAttr" value="${comprehensiveCommodityParameter[0].traderBond}" >
    
    

<div class="capital">    
    <div class="capital_ctn cpx_ctn">
        <div class="cpx_main">
            <h2 class="cpx_title cpx_t_icon1"><b>操盘保证金：</b><i>(可操盘32种产品，操盘保证金越多，可持仓手数越多)</i></h2>
            <ul class="cplx_mianlist">
                <c:forEach items="${comprehensiveCommodityParameter}" var="comprehensiveCommodityParameter" varStatus="status">
              		<c:choose>
              			<c:when test="${status.first==true}">
              				<li class="on" data='${comprehensiveCommodityParameter.traderBond}'><fmt:formatNumber value="${comprehensiveCommodityParameter.traderBond}"  type="number" maxFractionDigits="2"  ></fmt:formatNumber>元</li>
                		</c:when>
                		<c:otherwise>
                			<li class data='${comprehensiveCommodityParameter.traderBond}'><fmt:formatNumber value="${comprehensiveCommodityParameter.traderBond}"  type="number" maxFractionDigits="2"  ></fmt:formatNumber>元</li>
               			</c:otherwise>
               		</c:choose>
            	</c:forEach>
            </ul>
            
            <h2 class="cpx_title cpx_t_icon2"><b>账户规则：</b></h2>
            <ul class="cpx_m_rule">
                <li>
                    <h3>操盘保证金(￥)</h3>
                    <span><i id='traderBond'><fmt:formatNumber value="${comprehensiveCommodityParameter[0].traderBond}"  type="number" maxFractionDigits="2"  ></fmt:formatNumber></i>元</span>
                </li>
                <li>
                    <h3>总操盘资金(￥)</h3>
                    <span><i id='traderTotal'><fmt:formatNumber value="${comprehensiveCommodityParameter[0].traderTotal}"  type="number" maxFractionDigits="2"  ></fmt:formatNumber></i>元</span>
                </li>
                <li>
                    <h3>亏损平仓线(￥)</h3>
                    <span><i id='lineLoss'><fmt:formatNumber value="${comprehensiveCommodityParameter[0].lineLoss}"  type="number" maxFractionDigits="2"  ></fmt:formatNumber></i>元</span>
                </li>
                <li>
                    <h3>账户管理费(￥)</h3>
                    <span><i>免费</i></span>
                </li>
            </ul>
            <a name="font"></a>
            <h2 class="cpx_title cpx_t_icon3"><b>交易规则：</b><i>(一个账号可同时交易32种期货产品)</i></h2>
            <div class="cpp_rule">
                <div class="cpp_r_nav">
                    <a href="" class="on point">上海期货交易所<i></i></a>
                    <a href="">大连期货交易所<i></i></a>
                    <a href="">郑州期货交易所<i></i></a>
                    <a href="">中金所<i></i></a>
                </div>
                <div class="cpp_r_list" style="display:block;">
                    <div class="cpp_ru_list left">
                         <p class="cpp_ru_promt"  >
                            <i>只交易该品种时，初始最大可持仓手数</i>
                            <img src="../static/images/cx/icon_08.gif">
                        </p>
                        <table width="100%" border="0" cellspacing="0" cellpadding="0" >
                            <thead>
                                <td width="20%">期货产品</td>
                                <td width="20%">主力合约</td>
                                <td width="30%">初始可持仓手数<a href="javascript:void(0);"></a></td>
                                <td width="30%">交易手续费</td>      
                            </thead>
                            <tr>
                                <td>白银</td>
                                <c:forEach items="${comprehensiveCommodityPrice}" var="comprehensiveCommodityPrice" varStatus="status">
				              		<c:choose>
				              			<c:when test="${comprehensiveCommodityPrice.tradeType==0}">
				              				<td>${comprehensiveCommodityPrice.mainContract}</td>
				                		</c:when>
				               		</c:choose>
				            	</c:forEach>
                                <td><i id="silverLever">${comprehensiveCommodityParameter[0].silverLever}</i>手</td>
                                <c:forEach items="${comprehensiveCommodityPrice}" var="comprehensiveCommodityPrice" varStatus="status">
				              		<c:choose>
				              			<c:when test="${comprehensiveCommodityPrice.tradeType==0}">
				              				<td><i>${comprehensiveCommodityPrice.price}</i>元/手</td>
				                		</c:when>
				               		</c:choose>
				            	</c:forEach>
                            </tr>
                            <tr>
                                <td>铝</td>
                                <c:forEach items="${comprehensiveCommodityPrice}" var="comprehensiveCommodityPrice" varStatus="status">
				              		<c:choose>
				              			<c:when test="${comprehensiveCommodityPrice.tradeType==1}">
				              				<td>${comprehensiveCommodityPrice.mainContract}</td>
				                		</c:when>
				               		</c:choose>
				            	</c:forEach>
                                <td><i id="aluminumLever">${comprehensiveCommodityParameter[0].aluminumLever}</i>手</td>
                                <c:forEach items="${comprehensiveCommodityPrice}" var="comprehensiveCommodityPrice" varStatus="status">
				              		<c:choose>
				              			<c:when test="${comprehensiveCommodityPrice.tradeType==1}">
				              				<td><i>${comprehensiveCommodityPrice.price}</i>元/手</td>
				                		</c:when>
				               		</c:choose>
				            	</c:forEach>
                            </tr>
                            <tr>
                                <td>黄金</td>
                                <c:forEach items="${comprehensiveCommodityPrice}" var="comprehensiveCommodityPrice" varStatus="status">
				              		<c:choose>
				              			<c:when test="${comprehensiveCommodityPrice.tradeType==2}">
				              				<td>${comprehensiveCommodityPrice.mainContract}</td>
				                		</c:when>
				               		</c:choose>
				            	</c:forEach>
                                <td><i id="goldLever">${comprehensiveCommodityParameter[0].goldLever}</i>手</td>
                                <c:forEach items="${comprehensiveCommodityPrice}" var="comprehensiveCommodityPrice" varStatus="status">
				              		<c:choose>
				              			<c:when test="${comprehensiveCommodityPrice.tradeType==2}">
				              				<td><i>${comprehensiveCommodityPrice.price}</i>元/手</td>
				                		</c:when>
				               		</c:choose>
				            	</c:forEach>
                            </tr>
                            <tr>
                                <td>沥青</td>
                                <c:forEach items="${comprehensiveCommodityPrice}" var="comprehensiveCommodityPrice" varStatus="status">
				              		<c:choose>
				              			<c:when test="${comprehensiveCommodityPrice.tradeType==3}">
				              				<td>${comprehensiveCommodityPrice.mainContract}</td>
				                		</c:when>
				               		</c:choose>
				            	</c:forEach>
                                <td><i id="asphaltLever">${comprehensiveCommodityParameter[0].asphaltLever}</i>手</td>
                                <c:forEach items="${comprehensiveCommodityPrice}" var="comprehensiveCommodityPrice" varStatus="status">
				              		<c:choose>
				              			<c:when test="${comprehensiveCommodityPrice.tradeType==3}">
				              				<td><i>${comprehensiveCommodityPrice.price}</i>元/手</td>
				                		</c:when>
				               		</c:choose>
				            	</c:forEach>
                            </tr>
                            <tr>
                                <td>铜</td>
                                <c:forEach items="${comprehensiveCommodityPrice}" var="comprehensiveCommodityPrice" varStatus="status">
				              		<c:choose>
				              			<c:when test="${comprehensiveCommodityPrice.tradeType==4}">
				              				<td>${comprehensiveCommodityPrice.mainContract}</td>
				                		</c:when>
				               		</c:choose>
				            	</c:forEach>
                                <td><i id="copperLever">${comprehensiveCommodityParameter[0].copperLever}</i>手</td>
                                <c:forEach items="${comprehensiveCommodityPrice}" var="comprehensiveCommodityPrice" varStatus="status">
				              		<c:choose>
				              			<c:when test="${comprehensiveCommodityPrice.tradeType==4}">
				              				<td><i>${comprehensiveCommodityPrice.price}</i>元/手</td>
				                		</c:when>
				               		</c:choose>
				            	</c:forEach>
                            </tr>
                            
                            
                        </table>
                    </div>
                    <div class="cpp_ru_list right">
                        <p class="cpp_ru_promt">
                            <i>只交易该品种时，初始最大可持仓手数</i>
                            <img src="../static/images/cx/icon_08.gif">
                        </p>
                        <table width="100%" border="0" cellspacing="0" cellpadding="0">
                             <thead>
                                <td width="20%">期货产品</td>
                                <td width="20%">主力合约</td>
                                <td width="30%">初始可持仓手数<a href="javascript:void(0);"></a></td>
                                <td width="30%">交易手续费</td>      
                            </thead>
                            <tr>
                                <td>热卷</td>
                                <c:forEach items="${comprehensiveCommodityPrice}" var="comprehensiveCommodityPrice" varStatus="status">
				              		<c:choose>
				              			<c:when test="${comprehensiveCommodityPrice.tradeType==5}">
				              				<td>${comprehensiveCommodityPrice.mainContract}</td>
				                		</c:when>
				               		</c:choose>
				            	</c:forEach>
                                <td><i id="coilLever">${comprehensiveCommodityParameter[0].coilLever}</i>手</td>
                                <c:forEach items="${comprehensiveCommodityPrice}" var="comprehensiveCommodityPrice" varStatus="status">
				              		<c:choose>
				              			<c:when test="${comprehensiveCommodityPrice.tradeType==5}">
				              				<td><i id="silverLever">${comprehensiveCommodityPrice.price}</i>元/手</td>
				                		</c:when>
				               		</c:choose>
				            	</c:forEach>
                            </tr>
                            <tr>
                                <td>镍</td>
                                 <c:forEach items="${comprehensiveCommodityPrice}" var="comprehensiveCommodityPrice" varStatus="status">
				              		<c:choose>
				              			<c:when test="${comprehensiveCommodityPrice.tradeType==6}">
				              				<td>${comprehensiveCommodityPrice.mainContract}</td>
				                		</c:when>
				               		</c:choose>
				            	</c:forEach>
                                <td><i id="nickelLever">${comprehensiveCommodityParameter[0].nickelLever}</i>手</td>
                                <c:forEach items="${comprehensiveCommodityPrice}" var="comprehensiveCommodityPrice" varStatus="status">
				              		<c:choose>
				              			<c:when test="${comprehensiveCommodityPrice.tradeType==6}">
				              				<td><i>${comprehensiveCommodityPrice.price}</i>元/手</td>
				                		</c:when>
				               		</c:choose>
				            	</c:forEach>
                            </tr>
                            <tr>
                                <td>螺纹钢</td>
                                <c:forEach items="${comprehensiveCommodityPrice}" var="comprehensiveCommodityPrice" varStatus="status">
				              		<c:choose>
				              			<c:when test="${comprehensiveCommodityPrice.tradeType==7}">
				              				<td>${comprehensiveCommodityPrice.mainContract}</td>
				                		</c:when>
				               		</c:choose>
				            	</c:forEach>
                                <td><i id="rebarLever">${comprehensiveCommodityParameter[0].rebarLever}</i>手</td>
                                <c:forEach items="${comprehensiveCommodityPrice}" var="comprehensiveCommodityPrice" varStatus="status">
				              		<c:choose>
				              			<c:when test="${comprehensiveCommodityPrice.tradeType==7}">
				              				<td><i id="silverLever">${comprehensiveCommodityPrice.price}</i>元/手</td>
				                		</c:when>
				               		</c:choose>
				            	</c:forEach>
                            </tr>
                            <tr>
                                <td>锌</td>
                                <c:forEach items="${comprehensiveCommodityPrice}" var="comprehensiveCommodityPrice" varStatus="status">
				              		<c:choose>
				              			<c:when test="${comprehensiveCommodityPrice.tradeType==8}">
				              				<td>${comprehensiveCommodityPrice.mainContract}</td>
				                		</c:when>
				               		</c:choose>
				            	</c:forEach>
                                <td><i id="zincLever">${comprehensiveCommodityParameter[0].zincLever}</i>手</td>
                                <c:forEach items="${comprehensiveCommodityPrice}" var="comprehensiveCommodityPrice" varStatus="status">
				              		<c:choose>
				              			<c:when test="${comprehensiveCommodityPrice.tradeType==8}">
				              				<td><i>${comprehensiveCommodityPrice.price}</i>元/手</td>
				                		</c:when>
				               		</c:choose>
				            	</c:forEach>
                            </tr>
                            <tr>
                                <td>橡胶</td>
                                <c:forEach items="${comprehensiveCommodityPrice}" var="comprehensiveCommodityPrice" varStatus="status">
				              		<c:choose>
				              			<c:when test="${comprehensiveCommodityPrice.tradeType==9}">
				              				<td>${comprehensiveCommodityPrice.mainContract}</td>
				                		</c:when>
				               		</c:choose>
				            	</c:forEach>
                                <td><i id="rubberLever">${comprehensiveCommodityParameter[0].rubberLever}</i>手</td>
                                <c:forEach items="${comprehensiveCommodityPrice}" var="comprehensiveCommodityPrice" varStatus="status">
				              		<c:choose>
				              			<c:when test="${comprehensiveCommodityPrice.tradeType==9}">
				              				<td><i>${comprehensiveCommodityPrice.price}</i>元/手</td>
				                		</c:when>
				               		</c:choose>
				            	</c:forEach>
                            </tr>
                            
                        </table>
                    </div>                    
                    <div class="clear"></div>
                </div>
                <div class="cpp_r_list">
                    <div class="cpp_ru_list left">
                         <p class="cpp_ru_promt" style="display:none;">
                            <i>只交易该品种时，初始最大可持仓手数</i>
                            <img src="../static/images/cx/icon_08.gif">
                        </p>
                        <table width="100%" border="0" cellspacing="0" cellpadding="0" >
                            <thead>
                                <td width="20%">期货产品</td>
                                <td width="20%">主力合约</td>
                                <td width="30%">初始可持仓手数<a href="javascript:void(0);"></a></td>
                                <td width="30%">交易手续费</td>      
                            </thead>
                            <tr>
                                <td>豆一</td>
                                <c:forEach items="${comprehensiveCommodityPrice}" var="comprehensiveCommodityPrice" varStatus="status">
				              		<c:choose>
				              			<c:when test="${comprehensiveCommodityPrice.tradeType==10}">
				              				<td>${comprehensiveCommodityPrice.mainContract}</td>
				                		</c:when>
				               		</c:choose>
				            	</c:forEach>
                                <td><i id="beanLever">${comprehensiveCommodityParameter[0].beanLever}</i>手</td>
                                <c:forEach items="${comprehensiveCommodityPrice}" var="comprehensiveCommodityPrice" varStatus="status">
				              		<c:choose>
				              			<c:when test="${comprehensiveCommodityPrice.tradeType==10}">
				              				<td><i>${comprehensiveCommodityPrice.price}</i>元/手</td>
				                		</c:when>
				               		</c:choose>
				            	</c:forEach>
                            </tr>
                            <tr>
                                <td>玉米</td>
                                <c:forEach items="${comprehensiveCommodityPrice}" var="comprehensiveCommodityPrice" varStatus="status">
				              		<c:choose>
				              			<c:when test="${comprehensiveCommodityPrice.tradeType==11}">
				              				<td>${comprehensiveCommodityPrice.mainContract}</td>
				                		</c:when>
				               		</c:choose>
				            	</c:forEach>
                                <td><i id="cornLever">${comprehensiveCommodityParameter[0].cornLever}</i>手</td>
                                <c:forEach items="${comprehensiveCommodityPrice}" var="comprehensiveCommodityPrice" varStatus="status">
				              		<c:choose>
				              			<c:when test="${comprehensiveCommodityPrice.tradeType==11}">
				              				<td><i>${comprehensiveCommodityPrice.price}</i>元/手</td>
				                		</c:when>
				               		</c:choose>
				            	</c:forEach>
                            </tr>
                            <tr>
                                <td>玉米淀粉</td>
                                <c:forEach items="${comprehensiveCommodityPrice}" var="comprehensiveCommodityPrice" varStatus="status">
				              		<c:choose>
				              			<c:when test="${comprehensiveCommodityPrice.tradeType==12}">
				              				<td>${comprehensiveCommodityPrice.mainContract}</td>
				                		</c:when>
				               		</c:choose>
				            	</c:forEach>
                                <td><i id="cornStarchLever">${comprehensiveCommodityParameter[0].cornStarchLever}</i>手</td>
                                <c:forEach items="${comprehensiveCommodityPrice}" var="comprehensiveCommodityPrice" varStatus="status">
				              		<c:choose>
				              			<c:when test="${comprehensiveCommodityPrice.tradeType==12}">
				              				<td><i>${comprehensiveCommodityPrice.price}</i>元/手</td>
				                		</c:when>
				               		</c:choose>
				            	</c:forEach>
                            </tr>
                            <tr>
                                <td>铁矿石</td>
                                <c:forEach items="${comprehensiveCommodityPrice}" var="comprehensiveCommodityPrice" varStatus="status">
				              		<c:choose>
				              			<c:when test="${comprehensiveCommodityPrice.tradeType==13}">
				              				<td>${comprehensiveCommodityPrice.mainContract}</td>
				                		</c:when>
				               		</c:choose>
				            	</c:forEach>
                                <td><i id="ironOreLever">${comprehensiveCommodityParameter[0].ironOreLever}</i>手</td>
                                <c:forEach items="${comprehensiveCommodityPrice}" var="comprehensiveCommodityPrice" varStatus="status">
				              		<c:choose>
				              			<c:when test="${comprehensiveCommodityPrice.tradeType==13}">
				              				<td><i>${comprehensiveCommodityPrice.price}</i>元/手</td>
				                		</c:when>
				               		</c:choose>
				            	</c:forEach>
                            </tr>
                            <tr>
                                <td>焦炭</td>
                                <c:forEach items="${comprehensiveCommodityPrice}" var="comprehensiveCommodityPrice" varStatus="status">
				              		<c:choose>
				              			<c:when test="${comprehensiveCommodityPrice.tradeType==14}">
				              				<td>${comprehensiveCommodityPrice.mainContract}</td>
				                		</c:when>
				               		</c:choose>
				            	</c:forEach>
                                <td><i id="cokeLever">${comprehensiveCommodityParameter[0].cokeLever}</i>手</td>
                                <c:forEach items="${comprehensiveCommodityPrice}" var="comprehensiveCommodityPrice" varStatus="status">
				              		<c:choose>
				              			<c:when test="${comprehensiveCommodityPrice.tradeType==14}">
				              				<td><i>${comprehensiveCommodityPrice.price}</i>元/手</td>
				                		</c:when>
				               		</c:choose>
				            	</c:forEach>
                            </tr>
                            <tr>
                                <td>鸡蛋</td>
                                <c:forEach items="${comprehensiveCommodityPrice}" var="comprehensiveCommodityPrice" varStatus="status">
				              		<c:choose>
				              			<c:when test="${comprehensiveCommodityPrice.tradeType==15}">
				              				<td>${comprehensiveCommodityPrice.mainContract}</td>
				                		</c:when>
				               		</c:choose>
				            	</c:forEach>
                                <td><i id="eggLever">${comprehensiveCommodityParameter[0].eggLever}</i>手</td>
                                <c:forEach items="${comprehensiveCommodityPrice}" var="comprehensiveCommodityPrice" varStatus="status">
				              		<c:choose>
				              			<c:when test="${comprehensiveCommodityPrice.tradeType==15}">
				              				<td><i>${comprehensiveCommodityPrice.price}</i>元/手</td>
				                		</c:when>
				               		</c:choose>
				            	</c:forEach>
                            </tr>
                        </table>
                    </div>
                    <div class="cpp_ru_list right">
                        <p class="cpp_ru_promt" style="display:none;">
                            <i>只交易该品种时，初始最大可持仓手数</i>
                            <img src="../static/images/cx/icon_08.gif">
                        </p>
                        <table width="100%" border="0" cellspacing="0" cellpadding="0">
                             <thead>
                                <td width="20%">期货产品</td>
                                <td width="20%">主力合约</td>
                                <td width="30%">初始可持仓手数<a href="javascript:void(0);"></a></td>
                                <td width="30%">交易手续费</td>      
                            </thead>
                            <tr>
                                <td>焦煤</td>
                                <c:forEach items="${comprehensiveCommodityPrice}" var="comprehensiveCommodityPrice" varStatus="status">
				              		<c:choose>
				              			<c:when test="${comprehensiveCommodityPrice.tradeType==16}">
				              				<td>${comprehensiveCommodityPrice.mainContract}</td>
				                		</c:when>
				               		</c:choose>
				            	</c:forEach>
                                <td><i id="cokingCoalLever">${comprehensiveCommodityParameter[0].cokingCoalLever}</i>手</td>
                                <c:forEach items="${comprehensiveCommodityPrice}" var="comprehensiveCommodityPrice" varStatus="status">
				              		<c:choose>
				              			<c:when test="${comprehensiveCommodityPrice.tradeType==16}">
				              				<td><i>${comprehensiveCommodityPrice.price}</i>元/手</td>
				                		</c:when>
				               		</c:choose>
				            	</c:forEach>
                            </tr>
                            <tr>
                                <td>塑料</td>
                                <c:forEach items="${comprehensiveCommodityPrice}" var="comprehensiveCommodityPrice" varStatus="status">
				              		<c:choose>
				              			<c:when test="${comprehensiveCommodityPrice.tradeType==17}">
				              				<td>${comprehensiveCommodityPrice.mainContract}</td>
				                		</c:when>
				               		</c:choose>
				            	</c:forEach>
                                <td><i id="plasticLever">${comprehensiveCommodityParameter[0].plasticLever}</i>手</td>
                                <c:forEach items="${comprehensiveCommodityPrice}" var="comprehensiveCommodityPrice" varStatus="status">
				              		<c:choose>
				              			<c:when test="${comprehensiveCommodityPrice.tradeType==17}">
				              				<td><i>${comprehensiveCommodityPrice.price}</i>元/手</td>
				                		</c:when>
				               		</c:choose>
				            	</c:forEach>
                            </tr>
                            <tr>
                                <td>豆粕</td>
                                <c:forEach items="${comprehensiveCommodityPrice}" var="comprehensiveCommodityPrice" varStatus="status">
				              		<c:choose>
				              			<c:when test="${comprehensiveCommodityPrice.tradeType==18}">
				              				<td>${comprehensiveCommodityPrice.mainContract}</td>
				                		</c:when>
				               		</c:choose>
				            	</c:forEach>
                                <td><i id="soybeanMealLever">${comprehensiveCommodityParameter[0].soybeanMealLever}</i>手</td>
                                <c:forEach items="${comprehensiveCommodityPrice}" var="comprehensiveCommodityPrice" varStatus="status">
				              		<c:choose>
				              			<c:when test="${comprehensiveCommodityPrice.tradeType==18}">
				              				<td><i>${comprehensiveCommodityPrice.price}</i>元/手</td>
				                		</c:when>
				               		</c:choose>
				            	</c:forEach>
                            </tr>
                            <tr>
                                <td>棕榈油</td>
                                <c:forEach items="${comprehensiveCommodityPrice}" var="comprehensiveCommodityPrice" varStatus="status">
				              		<c:choose>
				              			<c:when test="${comprehensiveCommodityPrice.tradeType==19}">
				              				<td>${comprehensiveCommodityPrice.mainContract}</td>
				                		</c:when>
				               		</c:choose>
				            	</c:forEach>
                                <td><i id="palmOilLever">${comprehensiveCommodityParameter[0].palmOilLever}</i>手</td>
                                <c:forEach items="${comprehensiveCommodityPrice}" var="comprehensiveCommodityPrice" varStatus="status">
				              		<c:choose>
				              			<c:when test="${comprehensiveCommodityPrice.tradeType==19}">
				              				<td><i>${comprehensiveCommodityPrice.price}</i>元/手</td>
				                		</c:when>
				               		</c:choose>
				            	</c:forEach>
                            </tr>
                            <tr>
                                <td>聚丙烯</td>
                                <c:forEach items="${comprehensiveCommodityPrice}" var="comprehensiveCommodityPrice" varStatus="status">
				              		<c:choose>
				              			<c:when test="${comprehensiveCommodityPrice.tradeType==20}">
				              				<td>${comprehensiveCommodityPrice.mainContract}</td>
				                		</c:when>
				               		</c:choose>
				            	</c:forEach>
                                <td><i id="polypropyleneLever">${comprehensiveCommodityParameter[0].polypropyleneLever}</i>手</td>
                                <c:forEach items="${comprehensiveCommodityPrice}" var="comprehensiveCommodityPrice" varStatus="status">
				              		<c:choose>
				              			<c:when test="${comprehensiveCommodityPrice.tradeType==20}">
				              				<td><i>${comprehensiveCommodityPrice.price}</i>元/手</td>
				                		</c:when>
				               		</c:choose>
				            	</c:forEach>
                            </tr>
                            <tr>
                                <td>豆油</td>
                                <c:forEach items="${comprehensiveCommodityPrice}" var="comprehensiveCommodityPrice" varStatus="status">
				              		<c:choose>
				              			<c:when test="${comprehensiveCommodityPrice.tradeType==21}">
				              				<td>${comprehensiveCommodityPrice.mainContract}</td>
				                		</c:when>
				               		</c:choose>
				            	</c:forEach>
                                <td><i id="soybeanOilLever">${comprehensiveCommodityParameter[0].soybeanOilLever}</i>手</td>
                                <c:forEach items="${comprehensiveCommodityPrice}" var="comprehensiveCommodityPrice" varStatus="status">
				              		<c:choose>
				              			<c:when test="${comprehensiveCommodityPrice.tradeType==21}">
				              				<td><i>${comprehensiveCommodityPrice.price}</i>元/手</td>
				                		</c:when>
				               		</c:choose>
				            	</c:forEach>
                            </tr>
                        </table>
                    </div>                    
                    <div class="clear"></div>
                </div>
                <div class="cpp_r_list">
                    <div class="cpp_ru_list left">
                         <p class="cpp_ru_promt" style="display:none;">
                            <i>只交易该品种时，初始最大可持仓手数</i>
                            <img src="../static/images/cx/icon_08.gif">
                        </p>
                        <table width="100%" border="0" cellspacing="0" cellpadding="0" >
                            <thead>
                                <td width="20%">期货产品</td>
                                <td width="20%">主力合约</td>
                                <td width="30%">初始可持仓手数<a href="javascript:void(0);"></a></td>
                                <td width="30%">交易手续费</td>      
                            </thead>
                            <tr>
                                <td>棉花</td>
                                <c:forEach items="${comprehensiveCommodityPrice}" var="comprehensiveCommodityPrice" varStatus="status">
				              		<c:choose>
				              			<c:when test="${comprehensiveCommodityPrice.tradeType==22}">
				              				<td>${comprehensiveCommodityPrice.mainContract}</td>
				                		</c:when>
				               		</c:choose>
				            	</c:forEach>
                                <td><i id="cottonLever">${comprehensiveCommodityParameter[0].cottonLever}</i>手</td>
                                <c:forEach items="${comprehensiveCommodityPrice}" var="comprehensiveCommodityPrice" varStatus="status">
				              		<c:choose>
				              			<c:when test="${comprehensiveCommodityPrice.tradeType==22}">
				              				<td><i>${comprehensiveCommodityPrice.price}</i>元/手</td>
				                		</c:when>
				               		</c:choose>
				            	</c:forEach>
                            </tr>
                            <tr>
                                <td>玻璃</td>
                                <c:forEach items="${comprehensiveCommodityPrice}" var="comprehensiveCommodityPrice" varStatus="status">
				              		<c:choose>
				              			<c:when test="${comprehensiveCommodityPrice.tradeType==23}">
				              				<td>${comprehensiveCommodityPrice.mainContract}</td>
				                		</c:when>
				               		</c:choose>
				            	</c:forEach>
                                <td><i id="glassLever">${comprehensiveCommodityParameter[0].glassLever}</i>手</td>
                                <c:forEach items="${comprehensiveCommodityPrice}" var="comprehensiveCommodityPrice" varStatus="status">
				              		<c:choose>
				              			<c:when test="${comprehensiveCommodityPrice.tradeType==23}">
				              				<td><i>${comprehensiveCommodityPrice.price}</i>元/手</td>
				                		</c:when>
				               		</c:choose>
				            	</c:forEach>
                            </tr>
                            <tr>
                                <td>甲醇</td>
                                <c:forEach items="${comprehensiveCommodityPrice}" var="comprehensiveCommodityPrice" varStatus="status">
				              		<c:choose>
				              			<c:when test="${comprehensiveCommodityPrice.tradeType==24}">
				              				<td>${comprehensiveCommodityPrice.mainContract}</td>
				                		</c:when>
				               		</c:choose>
				            	</c:forEach>
                                <td><i id="methanolLever">${comprehensiveCommodityParameter[0].methanolLever}</i>手</td>
                                <c:forEach items="${comprehensiveCommodityPrice}" var="comprehensiveCommodityPrice" varStatus="status">
				              		<c:choose>
				              			<c:when test="${comprehensiveCommodityPrice.tradeType==24}">
				              				<td><i>${comprehensiveCommodityPrice.price}</i>元/手</td>
				                		</c:when>
				               		</c:choose>
				            	</c:forEach>
                            </tr>
                            <tr>
                                <td>菜油</td>
                                <c:forEach items="${comprehensiveCommodityPrice}" var="comprehensiveCommodityPrice" varStatus="status">
				              		<c:choose>
				              			<c:when test="${comprehensiveCommodityPrice.tradeType==25}">
				              				<td>${comprehensiveCommodityPrice.mainContract}</td>
				                		</c:when>
				               		</c:choose>
				            	</c:forEach>
                                <td><i id="rapeOilLever">${comprehensiveCommodityParameter[0].rapeOilLever}</i>手</td>
                                <c:forEach items="${comprehensiveCommodityPrice}" var="comprehensiveCommodityPrice" varStatus="status">
				              		<c:choose>
				              			<c:when test="${comprehensiveCommodityPrice.tradeType==25}">
				              				<td><i>${comprehensiveCommodityPrice.price}</i>元/手</td>
				                		</c:when>
				               		</c:choose>
				            	</c:forEach>
                            </tr>
                            
                        </table>
                    </div>
                    <div class="cpp_ru_list right">
                        <p class="cpp_ru_promt" style="display:none;">
                            <i>只交易该品种时，初始最大可持仓手数</i>
                            <img src="../static/images/cx/icon_08.gif">
                        </p>
                        <table width="100%" border="0" cellspacing="0" cellpadding="0">
                             <thead>
                                <td width="20%">期货产品</td>
                                <td width="20%">主力合约</td>
                                <td width="30%">初始可持仓手数<a href="javascript:void(0);"></a></td>
                                <td width="30%">交易手续费</td>      
                            </thead>
                            <tr>
                                <td>菜粕</td>
                                <c:forEach items="${comprehensiveCommodityPrice}" var="comprehensiveCommodityPrice" varStatus="status">
				              		<c:choose>
				              			<c:when test="${comprehensiveCommodityPrice.tradeType==26}">
				              				<td>${comprehensiveCommodityPrice.mainContract}</td>
				                		</c:when>
				               		</c:choose>
				            	</c:forEach>
                                <td><i id="rapeseedMealLever">${comprehensiveCommodityParameter[0].rapeseedMealLever}</i>手</td>
                                <c:forEach items="${comprehensiveCommodityPrice}" var="comprehensiveCommodityPrice" varStatus="status">
				              		<c:choose>
				              			<c:when test="${comprehensiveCommodityPrice.tradeType==26}">
				              				<td><i>${comprehensiveCommodityPrice.price}</i>元/手</td>
				                		</c:when>
				               		</c:choose>
				            	</c:forEach>
                            </tr>
                            <tr>
                                <td>白糖</td>
                                <c:forEach items="${comprehensiveCommodityPrice}" var="comprehensiveCommodityPrice" varStatus="status">
				              		<c:choose>
				              			<c:when test="${comprehensiveCommodityPrice.tradeType==27}">
				              				<td>${comprehensiveCommodityPrice.mainContract}</td>
				                		</c:when>
				               		</c:choose>
				            	</c:forEach>
                                <td><i id="sugarLever">${comprehensiveCommodityParameter[0].sugarLever}</i>手</td>
                                <c:forEach items="${comprehensiveCommodityPrice}" var="comprehensiveCommodityPrice" varStatus="status">
				              		<c:choose>
				              			<c:when test="${comprehensiveCommodityPrice.tradeType==27}">
				              				<td><i>${comprehensiveCommodityPrice.price}</i>元/手</td>
				                		</c:when>
				               		</c:choose>
				            	</c:forEach>
                            </tr>
                            <tr>
                                <td>PTA</td>
                                <c:forEach items="${comprehensiveCommodityPrice}" var="comprehensiveCommodityPrice" varStatus="status">
				              		<c:choose>
				              			<c:when test="${comprehensiveCommodityPrice.tradeType==28}">
				              				<td>${comprehensiveCommodityPrice.mainContract}</td>
				                		</c:when>
				               		</c:choose>
				            	</c:forEach>
                                <td><i id="pTALever">${comprehensiveCommodityParameter[0].pTALever}</i>手</td>
                                <c:forEach items="${comprehensiveCommodityPrice}" var="comprehensiveCommodityPrice" varStatus="status">
				              		<c:choose>
				              			<c:when test="${comprehensiveCommodityPrice.tradeType==28}">
				              				<td><i>${comprehensiveCommodityPrice.price}</i>元/手</td>
				                		</c:when>
				               		</c:choose>
				            	</c:forEach>
                            </tr>
                            <tr>
                                <td>动力煤</td>
                                <c:forEach items="${comprehensiveCommodityPrice}" var="comprehensiveCommodityPrice" varStatus="status">
				              		<c:choose>
				              			<c:when test="${comprehensiveCommodityPrice.tradeType==29}">
				              				<td>${comprehensiveCommodityPrice.mainContract}</td>
				                		</c:when>
				               		</c:choose>
				            	</c:forEach>
                                <td><i id="powerCoalLever">${comprehensiveCommodityParameter[0].powerCoalLever}</i>手</td>
                                <c:forEach items="${comprehensiveCommodityPrice}" var="comprehensiveCommodityPrice" varStatus="status">
				              		<c:choose>
				              			<c:when test="${comprehensiveCommodityPrice.tradeType==29}">
				              				<td><i>${comprehensiveCommodityPrice.price}</i>元/手</td>
				                		</c:when>
				               		</c:choose>
				            	</c:forEach>
                            </tr>
                            
                        </table>
                    </div>                    
                    <div class="clear"></div>
                </div>
                <div class="cpp_r_list">
                    <div class="cpp_ru_list left">
                         <p class="cpp_ru_promt" style="display:none;">
                            <i>只交易该品种时，初始最大可持仓手数</i>
                            <img src="../static/images/cx/icon_08.gif">
                        </p>
                        <table width="100%" border="0" cellspacing="0" cellpadding="0" >
                            <thead>
                                <td width="20%">期货产品</td>
                                <td width="20%">主力合约</td>
                                <td width="30%">初始可持仓手数<a href="javascript:void(0);"></a></td>
                                <td width="30%">交易手续费</td>      
                            </thead>
                            <tr>
                                <td>五年期国债</td>
                                <c:forEach items="${comprehensiveCommodityPrice}" var="comprehensiveCommodityPrice" varStatus="status">
				              		<c:choose>
				              			<c:when test="${comprehensiveCommodityPrice.tradeType==30}">
				              				<td>${comprehensiveCommodityPrice.mainContract}</td>
				                		</c:when>
				               		</c:choose>
				            	</c:forEach>
                                <td><i id="fiveNationalDebtLever">${comprehensiveCommodityParameter[0].fiveNationalDebtLever}</i>手</td>
                                <c:forEach items="${comprehensiveCommodityPrice}" var="comprehensiveCommodityPrice" varStatus="status">
				              		<c:choose>
				              			<c:when test="${comprehensiveCommodityPrice.tradeType==30}">
				              				<td><i>${comprehensiveCommodityPrice.price}</i>元/手</td>
				                		</c:when>
				               		</c:choose>
				            	</c:forEach>
                            </tr>
                            
                        </table>
                    </div>
                    <div class="cpp_ru_list right">
                        <p class="cpp_ru_promt" style="display:none;">
                            <i>只交易该品种时，初始最大可持仓手数</i>
                            <img src="../static/images/cx/icon_08.gif">
                        </p>
                        <table width="100%" border="0" cellspacing="0" cellpadding="0">
                             <thead>
                                <td width="20%">期货产品</td>
                                <td width="20%">主力合约</td>
                                <td width="30%">初始可持仓手数<a href="javascript:void(0);"></a></td>
                                <td width="30%">交易手续费</td>      
                            </thead>
                            <tr>
                                <td>十年期国债</td>
                                <c:forEach items="${comprehensiveCommodityPrice}" var="comprehensiveCommodityPrice" varStatus="status">
				              		<c:choose>
				              			<c:when test="${comprehensiveCommodityPrice.tradeType==31}">
				              				<td>${comprehensiveCommodityPrice.mainContract}</td>
				                		</c:when>
				               		</c:choose>
				            	</c:forEach>
                                <td><i id="tenNationalDebtLever">${comprehensiveCommodityParameter[0].tenNationalDebtLever}</i>手</td>
                                <c:forEach items="${comprehensiveCommodityPrice}" var="comprehensiveCommodityPrice" varStatus="status">
				              		<c:choose>
				              			<c:when test="${comprehensiveCommodityPrice.tradeType==31}">
				              				<td><i>${comprehensiveCommodityPrice.price}</i>元/手</td>
				                		</c:when>
				               		</c:choose>
				            	</c:forEach>
                            </tr>
                            
                        </table>
                    </div>                    
                    <div class="clear"></div>
                </div>
            </div>
        </div>
        
          
        <div class="cp_bom cpp_bom">
            <p>如您不清楚规则，或有其他疑问，请联系客服：400-180-1860</p>
            <div class="cp_b_link">
                <input type="checkbox" checked="checked" id="checkbox_agree"ame="agree">
                <span>我已阅读并同意<a href="javascript:lookContract()">《商品期货综合操盘合作协议》</a></span>
            </div>
            <div class="cp_b_btn"><a  href="javascript:submitSetting()">提交操盘申请</a></div>
        </div>
    </div>
    </form>
</div>
<!-- DINGBU -->
<%@ include file="../common/dsp.jsp"%>
<script>
!function(w,d,e){
var _orderno='${tzdrUser.id}';
var b=location.href,c=d.referrer,f,s,g=d.cookie,h=g.match(/(^|;)\s*ipycookie=([^;]*)/),i=g.match(/(^|;)\s*ipysession=([^;]*)/);if (w.parent!=w){f=b;b=c;c=f;};u='//stats.ipinyou.com/cvt?a='+e('BJ.ga.SUo2xRgMiNvhrAY2-R67L_')+'&c='+e(h?h[2]:'')+'&s='+e(i?i[2].match(/jump\%3D(\d+)/)[1]:'')+'&u='+e(b)+'&r='+e(c)+'&rd='+(new Date()).getTime()+'&OrderNo='+e(_orderno)+'&e=';
function _(){if(!d.body){setTimeout(_(),100);}else{s= d.createElement('script');s.src = u;d.body.insertBefore(s,d.body.firstChild);}}_();
}(window,document,encodeURIComponent);
</script>
	<script>
!function(w,d,e){
var _orderno='${tzdrUser.id}';
var b=location.href,c=d.referrer,f,s,g=d.cookie,h=g.match(/(^|;)\s*ipycookie=([^;]*)/),i=g.match(/(^|;)\s*ipysession=([^;]*)/);if (w.parent!=w){f=b;b=c;c=f;};u='//stats.ipinyou.com/cvt?a='+e('BJ._F.3ag4T-uuz-3qugl7_8Ab6_')+'&c='+e(h?h[2]:'')+'&s='+e(i?i[2].match(/jump\%3D(\d+)/)[1]:'')+'&u='+e(b)+'&r='+e(c)+'&rd='+(new Date()).getTime()+'&OrderNo='+e(_orderno)+'&e=';
function _(){if(!d.body){setTimeout(_(),100);}else{s= d.createElement('script');s.src = u;d.body.insertBefore(s,d.body.firstChild);}}_();
}(window,document,encodeURIComponent);
</script>
</body>
</html>
