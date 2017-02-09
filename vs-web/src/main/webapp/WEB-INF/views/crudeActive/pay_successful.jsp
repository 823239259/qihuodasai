<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>
<%@include file="../common/import-artDialog-js.jspf"%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="zh">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta content="维胜提供股票操盘、P2P贷款、投资理财、股指期货等交易服务，更高的收益，更加安全可靠；维胜致力于打造中国领先的互联网金融交易平台." name="description">
<style type="text/css">
 .font_size_15 {font-size: 15px; color: #f60; }
 .font_size_22 {font-size: 22px; color: #f60; }    
.step { width: 1000px; height: 75px; margin: 20px auto -10px; position: relative; }
.step_line { width: 1000px; height:2px; background:#eee; position:absolute; top:11px; left:0; }
.step ul { display: block; width: 100%; height: 75px; overflow: hidden;}
.step ul li { display: block; width:333px; height: 75px; position: relative; float: left; }
.step ul li span { display: block; width:24px; height: 24px; background: url(${ctx}/static/images/capital/step.png) no-repeat; text-align: center; line-height: 24px; color: #fff; margin:0 auto; position:relative; }
.step ul li p { width: 100%; height:50px; line-height: 50px; font-size: 16px; color: #999; text-align: center;  }
.step ul li.on span { background: url(${ctx}/static/images/capital/stepon.png) no-repeat;}
.step ul li.on p { color:#ff6500;font-weight: bold; }
.step ul li.on i { display: block; width: 100%; height: 2px; background: #ff6500; position: absolute; top: 11px; left: 0; }                    
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


<title>恒生指数,维胜-中国领先的互联网金融交易平台</title>
<script type="text/javascript">
$(document).ready(function(){
	$('.navlist li a').removeClass('on');
	$("#crudeoilli").addClass("on");
});
</script>
</head>
<body>
	<!--顶部 -->
	<div class="step">
	  <div class="step_line"></div>
	  <ul>
	      <li>
	        <span>1</span>
	        <p>参与免费体验</p>
	      </li>
	      <li>
	        <span>2</span>
	        <p>支付1元体验金</p>
	      </li>
	      <li class="on">
	        <i></i>
	        <span>3</span>
	        <p>开始操盘</p>
	      </li>
	  </ul>  	
	</div>
	<div class="capital" style="background-color: white;">
		<div class="cp_suc" style="margin:auto;border: 1px solid #F1F1F1">
			<h3>申请成功！账户将以短信形式通知</h3>
			<p>交易时间：系统将在30分钟内下发操盘账户；<br>非交易时间：系统将在次日开盘前下发操盘账户。</p>		
			<div class="cp_paybtn">
				<div class="cp_pb_font"></div>
				<div class="uc_paybtn cp_btnsuc" style="float:left;"><a href="${ctx}/help?tab=software&leftMenu=8">下载交易软件</a></div>
				<div class="uc_paybtn cp_btnsuc" style="float:left;"><a href="${ctx}/help?tab=rule&leftMenu=8">查看交易说明</a></div>	
			</div>		
		</div>
	</div>
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
