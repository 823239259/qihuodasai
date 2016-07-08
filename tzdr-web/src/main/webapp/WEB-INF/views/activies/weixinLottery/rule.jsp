<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="zh-cmn-Hans">
<head>
	<meta charset="utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
	<meta http-equiv="Cache-Control" content="no-siteapp" />
	<meta name="renderer" content="webkit" />
	<meta name="robots" content="all" />
	<meta name="keywords" content="股票、期货" />
	<meta name="description" content="维胜投身普惠金融互联网服务，以网络平台为A股、港股、美股、富时A50、恒指期货、国际原油等金融产品的操盘提供便利条件。" />
	<meta name="author" content="www.tzdr.com" />
	<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
	<meta name="HandheldFriendly" content="true" />
	<meta name="MobileOptimized" content="320" />
	<meta name="apple-mobile-web-app-title" content="html5" />
	<meta name="format-detection" content="telephone=no,email=no" />
	<meta name="apple-mobile-web-app-capable" content="yes" />
	<meta name="apple-mobile-web-app-status-bar-style" content="black" />
	<title>微信抽奖 - 维胜 </title>
	<%
		String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
	%>
	<c:set var="ctx" value="<%=basePath%>"></c:set>
	<c:set var="v" value="20160113"></c:set>

	<!-- common css -->
	<link rel="shortcut icon" href="${ctx}/static/ico/icon.png">
	<!-- custom css -->
	<link rel="stylesheet" href="${ctx}/static/css/weixin-lottery.css?v=${v}">

</head>
<body>
<div class="pz">
    <img src="${ctx}/static/images/weixinLottery/ru_01.jpg">
    <img src="${ctx}/static/images/weixinLottery/ru_02.jpg">
    <img src="${ctx}/static/images/weixinLottery/ru_03.jpg">
    <img src="${ctx}/static/images/weixinLottery/ru_04.jpg">
    <img src="${ctx}/static/images/weixinLottery/ru_05.jpg">
    <img src="${ctx}/static/images/weixinLottery/ru_06.jpg">
</div>
<footer>
    <a href="${ctx}/weixin/lottery/index"><img src="${ctx}/static/images/weixinLottery/nav_01.gif"></a>
    <a href="${ctx}/weixin/lottery/prize"><img src="${ctx}/static/images/weixinLottery/nav_02.gif"></a>
    <a href="${ctx}/weixin/lottery/rule"><img src="${ctx}/static/images/weixinLottery/navon_03.jpg"></a>
</footer>
</body>
</html>
