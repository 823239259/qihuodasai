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
#customerTel ul li a#guojiqihuo {
    color: #ffb319;
}: 26px;}                     
</style>
<%-- <link href="${ctx}/static/css/common.css?v=${v}" rel="stylesheet" type="text/css" /> --%>
<link href="${ctx}/static/css/trade.css?v=${v}" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/css/tzdr.css?v=${v}" rel="stylesheet" type="text/css" />	
<script language="javascript" src="${ctx}/static/script/tzdr.js?v=${v}"></script>
<script language="javascript" src="${ctx}/static/script/common/tzdr.user.js?v=${v}"></script>
<script type="text/javascript" src="${ctx}/static/script/outDisk/pay.js?v=${v}"></script>
<script type="text/javascript" src="${ctx}/static/script/ftse/ftseCommon.js?v=${v}"></script>

<title>支付确认- 国际综合- 维胜金融-中国领先的国际期货及衍生品互联网交易平台</title>
<meta name="keywords" content="维胜，国际期货，期货，投资达人，金勺子，高盛，都城，南华期货，配资，期货配资，期货开户，外盘，富时A50，国际原油，恒指期货，期货公司，期货平台，炒期货，模拟盘，赚钱，头寸，持仓，成都盈透科技有限公司"/>
<meta name="description" content="维胜（www.vs.com）-致力于成为中国领先的国际期货及衍生品互联网交易平台，提供恒指期货、国际原油、富时A50等主流国际期货产品，开户操盘快捷方便，交易费用全网最低。"/>
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
     <form style = "display: none;" action="${ctx}/pay/payinfo" id = "toPayInfoForm" method = "get">
    	<input type="hidden" value = "0" name = "balance"/>
    	<input type="hidden" value = "1" name = "isFlag"/>
    	<input type="hidden" name="inputTraderBond" value="${traderBond}" />
		<input type="hidden" name="inputTranLever" value="0" />
		<input type="hidden" id = "inputVocherId" name ="inputVocherId" value = "">
		<input type="hidden" name="payUrl" value = "userOutDisk/paySuccessful"/>
		<input type="submit" id = "toPayInfoSubmit"/>
    </form>
	<div class="capital">
		<form action="${ctx}/userOutDisk/paySuccessful" id="payableForm" method="post">
			<input type="hidden" name=inputTraderBond value="${traderBond}" />
			<input type="hidden" name="tokenTzdr" value="${tokenTzdr}" />
			<input type="hidden" id="voucherId" name="voucherId" value="" />
			<div class="cp_ctn">
				<!-- 支付配资费用 -->	
				<div class="cp_payctn">	
					<div class="cp_paytitle">
						方案详情
					</div>
					<ul class="cp_paylist cp_ovlist">
						<li>
							<h3>总操盘资金（$）</h3>
							<p><i>${traderTotal}</i>美元</p>
						</li>
						<li class="big">
							<h3>交易手续费(￥)</h3>
							<p>
								富时A50<i><fmt:formatNumber type="number" maxFractionDigits="2" value="${outDiskPrice[0].price}" pattern="#,###"></fmt:formatNumber></i>元/手、
								恒指期货<i><fmt:formatNumber type="number" maxFractionDigits="2" value="${outDiskPrice[2].price}" pattern="#,###"></fmt:formatNumber></i></i>元/手、
								国际原油<i><fmt:formatNumber type="number" maxFractionDigits="2" value="${outDiskPrice[1].price}" pattern="#,###"></fmt:formatNumber></i></i>元/手、
								迷你道琼<i><fmt:formatNumber type="number" maxFractionDigits="2" value="${outDiskPrice[3].price}" pattern="#,###"></fmt:formatNumber></i></i>元/手、
								迷你纳斯达克<i><fmt:formatNumber type="number" maxFractionDigits="2" value="${outDiskPrice[4].price}" pattern="#,###"></fmt:formatNumber></i></i>元/手、
								迷你普尔<i><fmt:formatNumber type="number" maxFractionDigits="2" value="${outDiskPrice[5].price}" pattern="#,###"></fmt:formatNumber></i></i>元/手、
								德国DAX<i><fmt:formatNumber type="number" maxFractionDigits="2" value="${outDiskPrice[6].price}" pattern="#,###"></fmt:formatNumber></i></i>元/手、
								日经225<i><fmt:formatNumber type="number" maxFractionDigits="2" value="${outDiskPrice[7].price}" pattern="#,###"></fmt:formatNumber></i></i>元/手、
								小恒指<i><fmt:formatNumber type="number" maxFractionDigits="2" value="${outDiskPrice[8].price}" pattern="#,###"></fmt:formatNumber></i></i>元/手、
								美黄金<i><fmt:formatNumber type="number" maxFractionDigits="2" value="${outDiskPrice[9].price}" pattern="#,###"></fmt:formatNumber></i></i>元/手、
								H股指数<i><fmt:formatNumber type="number" maxFractionDigits="2" value="${outDiskPrice[10].price}" pattern="#,###"></fmt:formatNumber></i></i>元/手、
								小H股指数<i><fmt:formatNumber type="number" maxFractionDigits="2" value="${outDiskPrice[11].price}" pattern="#,###"></fmt:formatNumber></i></i>元/手、
								美铜<i><fmt:formatNumber type="number" maxFractionDigits="2" value="${outDiskPrice[12].price}" pattern="#,###"></fmt:formatNumber></i></i>元/手、
								美白银<i><fmt:formatNumber type="number" maxFractionDigits="2" value="${outDiskPrice[13].price}" pattern="#,###"></fmt:formatNumber></i></i>元/手、
								小原油<i><fmt:formatNumber type="number" maxFractionDigits="2" value="${outDiskPrice[14].price}" pattern="#,###"></fmt:formatNumber></i></i>元/手、
								迷你德国DAX指数<i><fmt:formatNumber type="number" maxFractionDigits="2" value="${outDiskPrice[15].price}" pattern="#,###"></fmt:formatNumber></i></i>元/手
							</p>
						</li>
						<li>
							<h3>账户管理费(￥)</h3>
							<p><i>免费</i></p>
						</li>
					</ul>
					<p class="cp_paypromt" style="display:none;"></p>		
					<div class="cp_paytitle">风险规则</div>
					<ul class="cp_paylist cp_ovpayrule">
						<li>							
							<h3>操盘主力合约</h3>
							<p class="big">
							投资国际期货富时A50<i>(${outDiskPrice[0].mainContract})</i>、恒指期货
							<i>(${outDiskPrice[2].mainContract})</i>、国际原油<i>(${outDiskPrice[1].mainContract})</i>、迷你道琼
							<i>(${outDiskPrice[3].mainContract})</i>、迷你纳斯达克<i>(${outDiskPrice[4].mainContract})</i>、迷你标普
							<i>(${outDiskPrice[5].mainContract})</i>、德国DAX<i>(${outDiskPrice[6].mainContract})</i>、日经225
							<i>(${outDiskPrice[7].mainContract})</i>、小恒指<i>(${outDiskPrice[8].mainContract})</i>、美黄金
							<i>(${outDiskPrice[9].mainContract})</i>、H股指数<i>(${outDiskPrice[10].mainContract})</i>
							、小H股指数<i>(${outDiskPrice[11].mainContract})</i>、美铜<i>(${outDiskPrice[12].mainContract})</i>
							、美白银<i>(${outDiskPrice[13].mainContract})</i>、小原油<i>(${outDiskPrice[14].mainContract})</i>、迷你德国DAX指数<i>(${outDiskPrice[15].mainContract})</i>
							</p>
						</li>
						<li>							
							<h3>亏损平仓线($)</h3>
							<p><i>${lineLoss}</i>美元</p>
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
						<div class="cp_btnback"><a href="${ctx}/outDisk/index">返回修改</a></div>
					</div>
				</div>
				<%--<div class="cp_paymoney" style="display:none;">--%>
					<%--<!-- 账户余额充足的时候，span隐藏 -->--%>
					<%--<c:if test="${showAvl == 1}">--%>
					<%--<span>您的账户余额只剩<i>${avlBal_user}</i>元，本次支付还差<i>${payable_avlBal_user}</i>元。<a href="${ctx}/pay/payinfo">立即充值</a></span>--%>
					<%--</c:if>--%>
					<%--<p>应付款：<em>￥${payable}</em>元</p>--%>
					<%--<input type="hidden" name="showAvl" value="${showAvl}" />					--%>
				<%--</div>--%>
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

