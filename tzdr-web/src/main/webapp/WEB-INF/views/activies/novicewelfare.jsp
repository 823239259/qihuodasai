<%@page
	import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@page import="com.tzdr.domain.entity.DataMap"%>
<%@page import="com.tzdr.business.service.datamap.DataMapService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>
<%@include file="../common/import-artDialog-js.jspf"%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="zh">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="${ctx}/static/css/new_index.css?v=20151127">
<link rel="stylesheet" href="${ctx}/static/css/skLuckyDraw.css?v=20151127">
<title>新手福利- 维胜</title>
</head>
<body>
	<!--顶部 -->
	<%@include file="../common/header.jsp"%>
	<div class="xsfl-title"></div>
	<div class="xsfl-bottom">免费金牌-第一手交易，双边手续费全免</div>
	<div class="xsfl-content">
		<div class="xsfl-xx">
			<p class="xsfl-xx-title">活动期间，首次进行交易，并终结方案，可免第一手双边交易费，免首笔合约为：富时A50（CN）、恒指期货（HSI）、国际原油（CL）主力合约。</p>
			<p class="xsfl-xx-js" style="margin-top: 40px;">注：<p>
			<p class="xsfl-xx-js">1、首次终结方案时，自动免除一手交易费，每位实名操盘用户仅限1手；</p>
			<p class="xsfl-xx-js">2、有任何交易行为的用户，则不算新用户，请知悉；</p>
			<p class="xsfl-xx-js">3、不限期货品种的减免规则，将在2016年12月26日起生效，请注意；</p>
			<p class="xsfl-xx-js">4、第一手交易出现持仓过夜情况的用户，不享受该活动优惠，且终结方案的时间须在活动期间内；</p>
			<p class="xsfl-xx-js">5、若同时持有多种国际综合合约，减免则按照最低手续费的进行；</p>
		</div>
		<img class="xsfl-fs" alt="" src="${ctx}/static/images/login/noviceWelfare-fs.png">
	</div>
	<p class="xsfl-footer">以上所有活动最终解释权归成都盈透科技有限公司所有，客服咨询：400-852-8008</p>
</body>
</html>