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
#hengzhiqidai {color: #ffcc33; border-bottom:2px solid #ffcc33; padding-bottom: 26px;}                     
</style>
<%-- <link href="${ctx}/static/css/common.css?v=${v}" rel="stylesheet" type="text/css" /> --%>
<link href="${ctx}/static/css/trade.css?v=${v}" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/css/tzdr.css?v=${v}" rel="stylesheet" type="text/css" />	
<script language="javascript" src="${ctx}/static/script/tzdr.js?v=${v}"></script>
<link href="${ctx}/static/css/pagination.css?v=${v}" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/css/vtrade.css?v=${v}" rel="stylesheet" type="text/css">
<script src="${ctx}/static/script/common/jquery.pagination.js?v=${v}" type="text/javascript"></script>
<script language="javascript" src="${ctx}/static/script/common/tzdr.user.js?v=${v}"></script>
<script type="text/javascript" src="${ctx}/static/script/hsi/hsiCommon.js?v=${v}"></script>
<script type="text/javascript" src="${ctx}/static/script/hsi/hsiPay.js?v=${v}"></script>
<title>支付确认- 恒指期货  - 维胜金融-中国领先的国际期货及衍生品互联网交易平台</title>
</head>
<body>
	<!--顶部 -->
	<%@include file="../common/header.jsp"%>
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
		<div class="fl_box fl_pay">
            <div class="fl_navtitle">
                <h3 class="fl_logintitle">确认支付</h3><a href="javascript:void(0);" class="close" onclick="colseConfirmPayDiv();"></a>
            </div>
            <div class="fl_uc_main">            	
            	<ul class="fl_paylist">
            		<li><label>账户余额：</label><span><i id="banlance"><fmt:formatNumber value="${avlBal}" pattern="###.##" /></i>元</span></li>
            		<li><label>支付金额：</label><span><i id="payable"><fmt:formatNumber value="${payable}" pattern="###.##" /></i>元</span></li>
            		<input type="hidden" id="payableFiexd" value="${payable}" />
            		<li>
            			<label>代金券：</label>
	            		<select id="voucher">
	                		<c:choose>
	                			<c:when test="${fn:length(voucher) == 0 }">
	                				<option value="">无代金券</option>
	                			</c:when>
	                			<c:otherwise>
	                				<c:forEach var="vo" items="${voucher }">
			                			<option value="${vo.id }">${vo.money }元代金券</option>
			                		</c:forEach>
		                			<option value="">不使用代金券</option>
	                			</c:otherwise>
	                		</c:choose>
	                	</select>
                	</li>
            	</ul>
            	<p id="notEnoughPay" class="fl_cgpromt" style="display: none;">余额不足，<a href="${ctx}/pay/payinfo" class="fl_loginbtn">请立即充值！</a></p>				
            </div>
            <div class="fl_loginbtn">
                <a href="javascript:void(0);" onclick="colseConfirmPayDiv();" class="fl_pb_cancle">取消</a>
                <a href="javascript:void(0);" status="true" id="confirmPay" class="fl_pb_sure">确定支付</a>
            </div>            
            <p class="fl_promtfont">注：代金券一般由维胜平台活动发放，请大家多多关注。</p>
        </div>	
    </div>
	<div class="capital">
		<form action="${ctx}/userhsi/paySuccessful" id="payableForm" method="post">
			<input type="hidden" name="inputTraderBond" value="${inputTraderBond}" />
			<input type="hidden" name="inputTranLever" value="${inputTranLever}" />
			<input type="hidden" name="tokenTzdr" value="${tokenTzdr}" />
			<input type="hidden" id="voucherId" name="voucherId" value="" />
			<div class="cp_ctn">
				<!-- 支付配资费用 -->	
				<div class="cp_payctn">	
					<div class="cp_paytitle">
						方案详情
					</div>
					<ul class="cp_paylist sif_paylist">
						<li>
							<h3>总操盘金额（$）</h3>
							<p><i>${traderTotal}</i>美元</p>
						</li>
						<li>
							<h3>交易手续费(￥)</h3>
							<p><i><fmt:formatNumber value="${inputTranFees}" pattern="#,###"></fmt:formatNumber></i>元/手</p>
						</li>
						<li>
							<h3>账户管理费(￥)</h3>
							<p><i>免费</i></p>
						</li>
						<li>
							<h3>可开仓手数</h3>
							<p><i>${inputTranLever}</i>手</p>
						</li>
					</ul>
					<p class="cp_paypromt" style="display:none;"></p>		
					<div class="cp_paytitle">风险规则</div>
					<ul class="cp_paylist cp_payrule">
						<li>							
							<h3>操盘须知</h3>
							<p>投资香港恒生指数<i>(${contract})</i>，盈利全归您</p>
						</li>
						<li>							
							<h3>亏损平仓线($)</h3>
							<p><i>${lossLine}</i>美元</p>
						</li>
					</ul>
					<div class="cp_paytitle">支付清单</div>
					<div class="cp_paymoneybox">
						<ul class="cp_paylist cp_paymoneylist">
							<li>
								<h3>操盘保证金(￥)</h3>
								<p><i>${traderBond}</i>元</p>
							</li>
							<li>
								<h3>账户管理费(￥)</h3>
								<p><i>免费</i></p>
							</li>
						</ul>
						<div class="cp_paymoneyic">=</div>
						<ul class="cp_paylist cp_paymoneyinfo">
							<li>
								<h3>应付金额(￥)</h3>
								<p><i>￥${payable}</i>元</p>
							</li>
						</ul>
					</div>
					<div class="cp_paybtn">
						<input type="submit" style="display:none;" id="forPaySubmit" />
						<div class="uc_paybtn cp_btnsure"><a href="javascript:void(0);" id="pay_button" >下一步</a></div>
						<div class="cp_btnback"><a href="${ctx}/hsi/index">返回修改</a></div>
					</div>
				</div>
			</div>
		</form>
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

