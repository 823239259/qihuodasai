<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>
<%@ include file="../common/import-artDialog-js.jspf"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>参与股票合买  - 投资达人</title>
	<link href="${ctx}/static/css/pagination.css" rel="stylesheet" type="text/css" /> 
	<link rel="stylesheet" type="text/css" href="${ctx}/static/css/buy.css?v=${v}">
	<script language="javascript" src="${ctx}/static/script/tzdr.js"></script>
	<script type="text/javascript"src="${ctx}/static/script/common/jquery.pagination.js"></script>
	<script language="javascript" src="${ctx}/static/script/togetherTrade/publiclist.js?v=${v}"></script>
</head>
<body>
	<!--顶部 -->
	<%@ include file="../common/personheader.jsp"%>

	<!-- 浮动层 -->
	<div class="floatlayer" style="display: none;"></div>
	<!-- 我来操盘 -->
	<div class="bl_box">
		<h3 class="bl_title">
			<i class="border"></i><em>我来操盘</em><b>（一人发起操盘，多人参与合买，操盘者放大收益，合买者只赚不亏！）</b>
			<a href="${ctx}/together/index">发起合买&gt;&gt;</a>
		</h3>
		<ul class="bl_top">
			<li class="li1">合买模式</li>
			<li class="li2">合买角色</li>
			<li class="li3">投资资源</li>
			<li class="li4">操盘成本</li>
			<li class="li5">方案盈利时</li>
			<li class="li6">方案亏损时</li>
			<li class="li7">投资特点</li>
		</ul>
		<ul class="bl_t_font bl_t_font1">
			<li class="li1">少分利<img src="${ctx}/static/images/together/icon_05.jpg" /></li>
			<li class="li2">
				<p class="p1">操盘者</p>
				<p>合买者</p>
			</li>
			<li class="li3">
				<p class="p1">出资金+操盘技术</p>
				<p>出资金</p>
			</li>
			<li class="li4">
				<p class="p1">合买者收益+账户管理费</p>
				<p>无</p>
			</li>
			<li class="li5">
				<p class="p1">获得：纯利润*98.5%</p>
				<p>获得：保底收益+纯利润*1.50%</p>
			</li>
			<li class="li6">
				<p class="p1">承担：亏损</p>
				<p>获得：保底收益</p>
			</li>
			<li class="li7">
				<p class="p1">进取型：较少本金，放大收益</p>
				<p>投资型：投资较多，收益越大</p>
			</li>
		</ul>
		<ul class="bl_t_font bl_t_font2">
			<li class="li1">多分利</li>
			<li class="li2">
				<p class="p1">操盘者</p>
				<p>合买者</p>
			</li>
			<li class="li3">
				<p class="p1">出资金+操盘技术</p>
				<p>出资金</p>
			</li>
			<li class="li4">
				<p class="p1">账户管理费</p>
				<p>无</p>
			</li>
			<li class="li5">
				<p class="p1">获得：纯利润*20%+纯利润*80%*资金占比</p>
				<p>获得：保底收益+纯利润*80%*资金占比</p>
			</li>
			<li class="li6">
				<p class="p1">承担：亏损</p>
				<p>不亏不赚</p>
			</li>
			<li class="li7">
				<p class="p1">稳健型：收益有限，回报较低</p>
				<p>保本型：盈利越多，分利越大</p>
			</li>
		</ul>
	</div>
	<div class="bl_box">
		<h3 class="bl_title">
			<i class="border"></i> <em>我来合买</em> <%-- <span>已有<i> ${togetherTradeNo } </i>位操盘手&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<i>${togetherTradeUserNo } </i>位合买者共赚取&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <i> ${togetherTradeMoney }  </i>元利润
			</span> --%>
		</h3>
		
		<div id="content" class="bl_list" style="display: none;"></div>
		<div id="Pagination" style="margin: 20px 0;"></div>
	</div>
	<!-- 底部 -->
	<%@ include file="../common/personfooter.jsp"%>
</body>
</html>
