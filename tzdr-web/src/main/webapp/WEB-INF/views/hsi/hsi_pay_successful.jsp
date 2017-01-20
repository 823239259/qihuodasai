<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>
<%@include file="../common/import-artDialog-js.jspf"%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="zh">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="keywords" content="维胜，国际期货，期货，投资达人，金勺子，高盛，都城，南华期货，配资，期货配资，期货开户，外盘，富时A50，国际原油，恒指期货，期货公司，期货平台，炒期货，模拟盘，赚钱，头寸，持仓，成都盈透科技有限公司"/>
<meta name="description" content="维胜（www.vs.com）-致力于成为中国领先的国际期货及衍生品互联网交易平台，提供恒指期货、国际原油、富时A50等主流国际期货产品，开户操盘快捷方便，交易费用全网最低。"/>
<style type="text/css">
 .font_size_15 {font-size: 15px; color: #f60; }
 .font_size_22 {font-size: 22px; color: #f60; } 
 #customerTel ul li a#guojiqihuo {color: #ffb319;}                      
</style>
<link href="${ctx}/static/css/public.css?v=${v}" rel="stylesheet"
	type="text/css">
<link href="${ctx}/static/css/trade.css?v=${v}" rel="stylesheet"
	type="text/css" />
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/capital.css?v=${v}">
<script language="javascript" src="${ctx}/static/script/tzdr.js?v=${v}"></script>
<link href="${ctx}/static/css/pagination.css?v=${v}" rel="stylesheet" type="text/css" />
<script src="${ctx}/static/script/common/jquery.pagination.js?v=${v}" type="text/javascript"></script>
<script language="javascript" src="${ctx}/static/script/trade/preByDay.js?v=${v}"></script>


<title>恒指期货方案申请成功 - 维胜</title>
<script type="text/javascript">
$(document).ready(function(){
	$('.navlist li a').removeClass('on');
	$("#internationalFutures").addClass("on");
});
</script>
</head>
<body>
	<!--顶部 -->
	<%@include file="../common/header.jsp"%>
	<div class="capital" style="background-color: white;">
		<input type="hidden" value = "${stateType}" id = "stateType"/>
		<c:choose>
			<c:when test="${stateType==1}">
				<div class="cp_suc" style="margin:auto;border: 1px solid #F1F1F1">
					<h3>申请成功！账户将以短信形式通知</h3>
					<p>交易时间：系统将在30分钟内下发操盘账户；<br>非交易时间：系统将在次日开盘前下发操盘账户。</p>		
					<div class="cp_paybtn">
						<div class="cp_pb_font"></div>
						<div class="uc_paybtn cp_btnsuc" style="float:left;"><a href="${ctx}/help?tab=software&leftMenu=1">下载交易软件</a></div>
						<div class="uc_paybtn cp_btnsuc" style="float:left;"><a href="${ctx}/help?tab=rule&leftMenu=9">查看交易说明</a></div>	
					</div>		
				</div>
			</c:when>
			<c:otherwise>
				<div class="cp_suc" style="margin:auto;border: 1px solid #F1F1F1">
					<h3>恭喜您，开户成功！</h3>
					<p>恭喜您完成开户申请，系统将在<label class="cp_djs">3</label>秒后后跳转到<a href="${ctx}/userftse/trade_list" target="_blank">操盘明细</a>页面。<br>您也可以点击<a href="${ctx}/html/qutrade/quoteTrade.html" target="_blank">行情交易</a>查看当前行情。</p>		
				</div> 	
				<!-- <p class="cp_jja">点击【操盘明细】页面跳转到个人中心->操盘明细；</p>
				<p class="cp_jja">点击【行情交易】页面跳转到行情交易页；</p>
				<p class="cp_jja">不点击自动跳转到操盘明细页面；</p> -->
			</c:otherwise>
		</c:choose>
	</div>
	<%@include file="../common/footer.jsp"%>
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