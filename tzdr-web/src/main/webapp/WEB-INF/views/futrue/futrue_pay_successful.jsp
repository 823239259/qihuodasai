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


<title>期指操盘,维胜-中国领先的互联网金融交易平台</title>
<script type="text/javascript">
function tradeContract(){
	var htmlHeight = 800;  //网页高度
	var htmlWidth = 1221;  //网页宽度
	var iTop = (window.screen.height-30-htmlHeight)/2; //获得窗口的垂直位置;  
	var iLeft = (window.screen.width-10-htmlWidth)/2;  //获得窗口的水平位置;  
	window.open(basepath+'tradeContract','随心配合作操盘协议','height='+htmlHeight+',innerHeight='+htmlHeight+',width='+htmlWidth+',innerWidth='+htmlWidth+',top='+iTop+',left='+iLeft+',toolbar=no,menubar=no,scrollbars=yes,resizeable=no,location=no,status=no');  
}
$(document).ready(function(){
	$('.navlist li a').removeClass('on');
	$("#future-index").addClass("on");
	 $('.cp_sdblink').click(function() {
		 $.ajax({   
		        type: "POST",   
		        url: basepath+"limitstock/stocks",  
		        dataType: 'json', 
		        contentType: "application/x-www-form-urlencoded", 
		        success: function(msg){  
		            var html = '';   
		            $.each(msg, function(i) {
		            	html+="<li>"+msg[i].code+"&nbsp;&nbsp;&nbsp;&nbsp;"+msg[i].name+"</li>";
	              });
		           
		          $('.fl_cpdblist').html(html); 
		        }   
		    }); 
	        $('.fl_cpdb').show();
	        $('.fl_mask').show();
	    });
	 $('.fl_cpdbtn a').click(function() {
	        $('.fl_cpdb').hide();
	        $('.fl_mask').hide();
	    });
});
</script>
</head>
<body>
	<!--顶部 -->
	<%@ include file="../common/personheader.jsp"%>
	<div class="floatlayer">
		 <div class="fl_mask" style="display:none;"></div>
	    <!-- 限制股弹出框 -->
	    <div class="fl_cpdb" style="display:none;">
	        <h2>今日限制股</h2>
	        <ul class="fl_cpdblist">
	       
	        </ul>
	        <div id="Pagination"></div> 
	        <div class="fl_cpdbtn"><a href="javascript:void(0);">我知道了</a></div>
	    </div>
	</div>
	<div class="capital" style="background-color: white;">
		<div class="cp_suc" style="margin:auto;border: 1px solid #F1F1F1">
			<h3>申请成功！账户将以短信形式通知</h3>
			<p>交易时间：系统将在30分钟内下发操盘账户；<br>非交易时间：系统将在次日开盘前下发操盘账户。</p>		
			<div class="cp_paybtn">
				<div class="cp_pb_font"></div>
				<div class="uc_paybtn cp_btnsuc" style="float:left;"><a href="${ctx}/topic/byds/byds.exe">下载交易软件</a></div>
				<div class="uc_paybtn cp_btnsuc" style="float:left;"><a href="${ctx}/help?tab=software&leftMenu=7">查看交易说明</a></div>	
			</div>		
		</div>
	</div>
	<%@ include file="../common/personfooter.jsp"%>
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

