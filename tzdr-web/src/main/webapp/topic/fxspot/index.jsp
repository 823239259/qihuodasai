

<%@page
	import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@page import="com.tzdr.domain.entity.DataMap"%>
<%@page import="com.tzdr.business.service.datamap.DataMapService"%>
<%@page import="com.umpay.api.log.SysOutLogger"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@ page import="com.tzdr.common.utils.ConfUtil"%> 

<%@ include file="../../WEB-INF/views/common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"  />
<title>外汇现货 - 投资达人</title>
<meta content="外汇现货,投资达人" name="description">
<link rel="stylesheet" type="text/css" href="css/index.css">
<script type="text/javascript">
$(document).ready(function(){
	$('.navlist li a').removeClass('on');
	$("#fxspot").addClass("on");
});
</script>
</head>
<body>
<!--顶部 -->
	<%@ include file="../../WEB-INF/views/common/header.jsp"%>
<div class="tp_main1" style="height:164px"><img src="image/img_01.png" style="height:164px" /></div>
<div class="tp_main1"><img src="image/img_02.png" ></div>
<div class="tp_main1"><img src="image/img_03.png" ></div>
<div class="xu"></div>
	<div class="tp_main2">
		<h3>五种投资方案</h3>
		<div class="way" >
			<img src="image/way_01.png" />
			<img src="image/way_02.png" />
			<img src="image/way_03.png" />
			<img src="image/way_04.png" />
			<img src="image/way_05.png" />
		</div>
		<h4>抢投热线：400-020-0158</h4>
	</div>
<div class="big_main3">
	<div class="tp_main3">
		<div class="special">
			<img src="image/s1.png" alt="无须繁琐的外汇开户！"/>
			<img src="image/s2.png" alt="无须复杂的美元入金！"/>
			<img src="image/s3.png" alt="立即申请立即操盘！"/>
		</div>
	</div>
</div>
<div class="tp_main4">
	<h3>外汇投资流程</h3>
	<div class="step">
		<img src="image/step1.png" /><span>选好投资方案给客服打电话</span>
		<img src="image/step2.png" /><span>账户余额扣款分发操盘账号</span>
		<img src="image/step3.png" /><span>操盘外汇现货轻松赚取美元</span>
		<img src="image/step4.png" /><span style="margin-right:0px">终结投资方案达人结算到账</span>
	</div>
	<div class="content">
		<div class="content1">
			<h3>交易安全</h3>
			<p><em>· </em>投资达人在和美国福汇集团战略合作，福汇集团（FXCM）是全球知名的外汇证券交易商，是全球性金融机构。</p>
			<p><em>· </em>福汇同时受4家司法机构监管：美国NFA，英国FCA，香港SFC，澳洲ASIC。</p>
			<p><em>· </em>通过投资达人分发的福汇操盘账号，交易绝对公开、公平、公正、安全！</p>
		</div>
		<div class="content2">
			<h3>资金安全</h3>
			<p><em>· </em>投资达人平台无论是在A股操盘、港股操盘、国际期货和商品期货，一直没有让众多投资者失望。努力建设的品牌口碑，为外汇现货投资做担保。</p>
			<p><em>· </em>无论是初始投资、中途追加资金还是终结方案，投资达人采用实时美元对人民币汇率，不吃利差。</p>
			<p><em>· </em> 零手续费，零管理费，零中介费，你盈亏多少就是多少。</p>
		</div>
	</div>	
</div>
<div class="xu"></div>
<div class="tp_main5">
	<h3>外汇操盘软件</h3>
	<div class="download">
		<div class="erweima">
			<p>iPhone手机安装：</p>
			<img src="image/iphone.png" />
		</div>
		<div class="erweima">
			<p>iPad平板安装：</p>
			<img src="image/ipad.png" />
		</div>
		<div class="erweima">
			<p>安卓手机安装：</p>
			<img src="image/andphone.png" />
		</div>
		<div class="erweima">
			<p>安卓平板安装：</p>
			<img src="image/andpad.png" />
		</div>
		<div class="erweima">
			<p>Windows电脑安装</p>
			<a href="https://download.fxcorporate.com/FXCM-MT4Install.exe"><img src="image/pc.png" /></a>
		</div>
	</div>
	<p>您看！为了您操盘外汇现货的便捷性，码农和设计师真是操碎了心，手机、平板、电脑软件一起拼！</p>
	
</div>
<div class="tp_foot">Copyright © 2016 上海信闳投资管理有限公司 版权所有 沪ICP备14048395号-1</div>
<!-- <span style="display:none"><script src='http://w.cnzz.com/q_stat.php?id=1256807294&l=3' language='JavaScript'></script></span> -->
<span style="display:none"><script src="https://s95.cnzz.com/z_stat.php?id=1259839078&web_id=1259839078" language="JavaScript"></script></span>
<%@ include file="/WEB-INF/views/common/count.jsp"%>
</body>
</html>