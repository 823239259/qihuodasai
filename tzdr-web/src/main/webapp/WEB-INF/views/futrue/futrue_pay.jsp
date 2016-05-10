<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>
<%@include file="../common/import-artDialog-js.jspf"%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="zh">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta content="投资达人提供股票操盘、P2P贷款、投资理财、股指期货等交易服务，更高的收益，更加安全可靠；投资达人致力于打造中国领先的互联网金融交易平台." name="description">
<style type="text/css">
 .font_size_15 {font-size: 15px; color: #f60; }
 .font_size_22 {font-size: 22px; color: #f60; }                       
</style>
<link href="${ctx}/static/css/trade.css?v=${v}" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/css/common.css?v=${v}" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/css/tzdr.css?v=${v}" rel="stylesheet" type="text/css" />	
<script language="javascript" src="${ctx}/static/script/tzdr.js?v=${v}"></script>
<link href="${ctx}/static/css/pagination.css?v=${v}" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/css/vtrade.css?v=${v}" rel="stylesheet" type="text/css">
<script src="${ctx}/static/script/common/jquery.pagination.js?v=${v}" type="text/javascript"></script>
<script language="javascript" src="${ctx}/static/script/trade/preByDay.js?v=${v}"></script>
<script language="javascript" src="${ctx}/static/script/common/tzdr.user.js?v=${v}"></script>
<script type="text/javascript" src="${ctx}/static/script/futrue/futrue.js?v=${v}"></script>

<title>支付确认 - 投资达人,投资达人-中国领先的互联网金融交易平台</title>
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
	init();
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
	<div class="floatlayer" id="notPay" style="display:none;">
		<div class="fl_mask"></div>
		<div class="fl_box fl_uc_bank">
            <div class="fl_navtitle">
                <h3 class="fl_logintitle"></h3><a href="javascript:void(0);" class="close" id="notPay_close" ></a>
            </div>
            <div class="fl_uc_main">
                <p class="fl_promtfont">您的账户余额只剩<i>${avlBal_user}</i>元<br>本次支付还差<i>${payable_avlBal_user}</i>元</p>
            </div>
            <div class="fl_loginbtn">
                <a href="${ctx}/pay/payinfo" class="fl_loginbtn">立即充值</a>
            </div>
        </div>	
	</div>
    <!-- 确认支付 -->
    <div class="floatlayer" id="confirmPayDiv" style="display:none;">
		<div class="fl_mask"></div>
		<div class="fl_box fl_uc_bank">
            <div class="fl_navtitle">
                <h3 class="fl_logintitle">确认支付</h3><a href="javascript:void(0);" class="close" onclick="colseConfirmPayDiv();"></a>
            </div>
            <div class="fl_uc_main">
                <p class="fl_promtfont">是否确定支付<i>${payable}</i>元</p>
            </div>
            <div class="fl_loginbtn">
                <a href="javascript:void(0);" onclick="colseConfirmPayDiv();" class="fl_loginbtn" style="width:120px; float:left; margin:0 10px;">取消</a>
                <a href="javascript:void(0);" status="true" id="confirmPay" class="fl_loginbtn" style="width:120px; float:left; margin:0 10px;">确定支付</a>
            </div>
        </div>	
    </div>
	<div class="capital">
		<form action="${ctx}/future/paySuccessful" id="payableForm" method="post">
			<input type="hidden" name="inputTraderBond" value="${inputTraderBond}" />
			<input type="hidden" name="inputTranLever" value="${inputTranLever}" />
			<input type="hidden" name="inputTranDay" value="${inputTranDay}" />
			<input type="hidden" name="tokenTzdr" value="${tokenTzdr}" />
			<div class="cp_ctn">
				<!-- 支付配资费用 -->	
				<div class="cp_payctn">	
					<div class="cp_paytitle">
						方案详情
					</div>
					<ul class="cp_paylist sif_paylist sif_paylist2">
						<li>
							<p>操盘保证金</p>
							<p>￥<i>${traderBond}</i>元</p>
						</li>
						<li>
							<p>总操盘金额</p>
							<p>￥<i>${traderTotal}</i>元</p>
						</li>
						<li>
							<p>可开仓手数</p>
							<p><i>${inputTranLever}</i>手</p>
						</li>
						<li>
							<p>帐户使用时间</p>
							<p><i>${inputTranDay}</i>个交易日</p>
						</li>
						<li>
							<p>管理费总计</p>
							<p>￥<i>${totalManageAmount}</i>元</p>
						</li>
					</ul>
					<p class="cp_paypromt" style="display:block;"></p>		
					<div class="cp_paytitle">风险规则</div>
					<table class="cp_paylist2" cellpadding="0" cellspacing="0">
						<tr>
							<td width="50%">操盘须知</td>
							<td width="50%">亏损平仓线</td>
						</tr>
						<tr>
							<td>投资沪深300当期主力合约，盈利全归您</td>
							<td>￥<i>${lossLine}</i>元</td>
						</tr>
					</table>
				</div>
				<div class="cp_paymoney">
					<!-- 账户余额充足的时候，span隐藏 -->
					<c:if test="${showAvl == 1}">
					<span>您的账户余额只剩<i>${avlBal_user}</i>元，本次支付还差<i>${payable_avlBal_user}</i>元。<a href="${ctx}/pay/payinfo">立即充值</a></span>
					</c:if>
					<p>应付款：<em>￥${payable}</em>元</p>
					<input type="hidden" name="showAvl" value="${showAvl}" />
					<div class="cp_paybtn">
					<input type="submit" style="display:none;" id="forPaySubmit" />
						<div class="uc_paybtn cp_btnsure"><a href="javascript:void(0);" id="pay_button" >下一步</a></div>
						<div class="cp_btnback"><a href="${ctx}/future/index">返回修改</a></div>
					</div>
				</div>
			</div>
		</form>
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

