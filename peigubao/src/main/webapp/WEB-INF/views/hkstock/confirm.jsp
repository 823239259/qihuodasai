<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>
<%@include file="../common/import-artDialog-js.jspf"%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="zh">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%-- <%@ include file="../common/commonkeyword.jsp"%> --%>
<title>港股操盘支付确认 - 配股宝</title>
<%-- <link href="${ctx}/static/css/public.css?version=20150602" rel="stylesheet"
	type="text/css"> --%>
<link href="${ctx}/static/css/trade.css?v=${v}" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/css/tzdr.css?v=${v}" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/css/vtrade.css?version=20150602" rel="stylesheet" type="text/css">	
<script language="javascript" src="${ctx}/static/script/hkstock/confirm.js?v=${v}"></script> 
<script language="javascript" src="${ctx}/static/script/common/tzdr.user.js"></script>
<script language="javascript" src="${ctx}/static/script/tzdr.js"></script>
<script type="text/javascript">
$(document).ready(function() {
	 $('.cp_sdblink').click(function() {
        $('.fl_cpdb').show();
        $('.fl_mask').show();
    });
    $('.fl_cpdbtn a').click(function() {
        $('.fl_cpdb').hide();
        $('.fl_mask').hide();
    });
    
    var len = $('.fl_cpdblist li').length;
    if (len <= 14) {
        $('.fl_cpdblist').css('overflow-y', 'hidden');
    };

});

</script>
</head>

<body>
<!-- 余额不足弹框 -->
<div class="floatlayer" id="notPay" style="display:none;">
		<div class="fl_mask"></div>
		<div class="fl_box fl_uc_bank">
            <div class="fl_navtitle">
                <h3 class="fl_logintitle"></h3><a href="javascript:void(0);" class="close" id="notPay_close" ></a>
            </div>
            <div class="fl_uc_main">
                <p class="fl_promtfont">您的账户余额只剩<i><fmt:formatNumber value="${avlBal}" type="number" minFractionDigits="2" ></fmt:formatNumber></i>元<br>
                	本次支付还差<i><fmt:formatNumber value="${payEnough}" type="number" minFractionDigits="2" ></fmt:formatNumber></i>元</p>
            </div>
            <div class="fl_loginbtn">
                <a href="${ctx}/pay/payinfo" class="fl_loginbtn">立即充值</a>
            </div>
        </div>	
</div>
<!-- 身份认证 -->
<div id="div_Mask"  style="display:none;"></div>
<div class="tck01" id="idcardDiv" style="display: none;">
	<div class="navtitle">
		<a class="nava" style="width: 90px;">实名认证</a><a class="close"
			onclick="javascript:closeDiv('idcardDiv')"></a>
	</div>
	<div class=" wzms">实名信息提交后不可修改，请务必认真填写真实资料 一个身份证只能绑定一个帐号。

		如遇到问题，请联系客服 400-633-9257</div>
	<div class="smain">
		<div class="srk">
			<span class="label">真实姓名：</span><input class="au-ipt"
				name="cardname" id="cardname" type="text">
		</div>
		<div class="srk">
			<span class="label">身份证号：</span> <input class="au-ipt" name="input"
				name="idcard" id="idcard" type="text">
		</div>
	</div>
	<!--001-1-->
	<div class="anniu">
		<a class="btn-h01" id="validating"
			onclick="javascript:validateCard();">提&nbsp;交</a><a class="btn-h02"
			onclick="javascript:closeDiv('idcardDiv')">取&nbsp;消</a>
	</div>
</div>
	<%@ include file="../common/personheader.jsp"%>
<div class="capital">

<!--隐藏域-->
<form method="post" action="${ctx}/userhkstock/handle" >
	<input type="hidden" name="lever" id="lever" value="${lever}"/>
	<input type="hidden" name="tradeStart" id="tradeStart" value="${tradeStart}"/>
	<input type="hidden" name="bailMoney" id="bailMoney" value="${bailMoney}"/>
	<input type="hidden" name="borrowPeriod" id="borrowPeriod" value="${borrowPeriod}"/>
	<input type="hidden" name="capitalAmount" id="capitalAmount" value="${capitalAmount}"/>
	<input type="hidden" name="need" id="need" value="${need}"/>
	<input type="hidden" name="isVerified" id="isVerified" value="${isVerified}"/>
	<input type="hidden" name="totalMoney" id="totalMoney" value="${totalMoney}"/>
	<input type="hidden" name="needNext" id="needNext" value="${needNext}"/>
</form>
<!--隐藏域--> 

	<div class="cp_ctn" style="border-top:none;">
		<!-- 支付配资费用 -->	
		<div class="cp_payctn">	
			<div class="cp_paytitle">操盘费用</div>
			<ul class="cp_paylist">
				<li style="width:198px;">
					<p>总操盘金额(HK$)</p>
					<p><i><fmt:formatNumber value="${totalMoney}" pattern="###,###" ></fmt:formatNumber></i>港元</p>
				</li>
				<li style="width:198px;">
					<p>操盘保证金(￥)</p>
					<p><i><fmt:formatNumber value="${bailMoney}" pattern="###,###" ></fmt:formatNumber></i>元</p>
				</li>
				<li style="width:198px;">
					<p>操盘天数</p>
					<p><i>${borrowPeriod}</i>天</p>
				</li>
				<li style="width:200px;">
					<p>通道使用费(￥)</p>
					<p>
						<c:choose>
							<c:when test="${!empty totalInterestFee && totalInterestFee !=0 }">
								<span><i>${totalInterestFee}</i></span>元
							</c:when>
							<c:otherwise>
								<span><i>免费</i></span>
							</c:otherwise>
						</c:choose>
					</p>
				</li>
				<li style="width:200px;">
					<p>账户管理费(￥)</p>
					<p><span><i>${manageFee}</i>元/交易日</span></p>
				</li>
			</ul>
			<p class="cp_paypromt">账户管理费是<i>使用一个交易日扣一次</i>，所以请保持账户余额充足；当账户余额不足时，我们将主动为您终结操盘方案。<em id="need_next_pay" style="color:#ff3000;">您的余额不足以扣取下一个交易日的管理费，请及时充值，以免方案被自动平仓、终止。</em></p>		
			<div class="cp_paytitle">风险规则</div>
			<table class="cp_paylist2" cellpadding="0" cellspacing="0">
				<tr>
					<td width="40%">操盘须知</td>
					<td width="30%">亏损警戒线(HK$)</td>
					<td width="30%">亏损平仓线(HK$)</td>
				</tr>
				<tr>
					<td>${operatorsInfo}</td>
					<td><i>${waringMoney}</i>港元</td>
					<td><i>${openMoney}</i>港元</td>
				</tr>
			</table>
		</div>
		
		
		<div class="cp_paymoney">
			<!-- 账户余额充足的时候，span隐藏 -->
			<span id="needdiv">您的账户余额只剩<i id="avl_bal"><fmt:formatNumber value="${avlBal}" type="number" minFractionDigits="2" ></fmt:formatNumber></i>元，本次支付还差<i id="pay_enough"><fmt:formatNumber value="${payEnough}" type="number" minFractionDigits="2" ></fmt:formatNumber></i>元。<a href="${ctx}/pay/payinfo">立即充值</a></span>
			<p>应付款：<em >￥<em id="need_pay"><fmt:formatNumber value="${needPay}" type="number" minFractionDigits="2" ></fmt:formatNumber></em>元</em></p>
		</div>
		<div class="cp_paybtn">
			<div class="uc_paybtn cp_btnsure"  style="margin:0 40px 0 42px;"><a href="javascript:void(0);" id="sub">下一步</a></div>
			<div class="cp_btnback"><a href="${ctx}/hkstock/index?lever=${lever}&totalMoney=${totalMoney}&borrowPeriod=${borrowPeriod}&tradeStart=${tradeStart}&bailMoney=${bailMoney}">返回修改</a></div>			
		</div>
	</div>
</div>

<%@ include file="../common/personfooter.jsp"%>
</body>
</html>

