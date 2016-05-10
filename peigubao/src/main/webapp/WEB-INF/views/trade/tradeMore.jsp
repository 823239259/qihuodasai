<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>
<%@include file="../common/import-artDialog-js.jspf"%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="zh">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="../common/commonkeyword.jsp"%>
<link href="${ctx}/static/css/public.css" rel="stylesheet"
	type="text/css">
<link href="${ctx}/static/css/trade.css" rel="stylesheet"
	type="text/css" />
<link href="${ctx}/static/css/tzdr.css" rel="stylesheet"
	type="text/css" />
<script language="javascript" src="${ctx}/static/script/trade/tradeMore.js?version=20150710"></script>
<script language="javascript" src="${ctx}/static/script/common/tzdr.user.js?version=20150720"></script>
<script language="javascript" src="${ctx}/static/script/tzdr.js?version=20150710"></script>

<script type="text/javascript">
$(document).ready(function(){
    $("#ticketid").on("click",function(){
    	setNeedMoney();
    }); 
    
    $('#volumeId').change(function(){
    	setNeedMoney();
    });

});


function setNeedMoney(){
	var ischecked=$("#ticketid").is(":checked");
	var volumeId=$("#volumeId").val();
	var vmoney=$("#volumeId option:selected").attr("datamoney");
	var totalInterestFee=parseFloat('${totalInterestFee}');
	var needPay=parseFloat('${needPay}');
	var avlBal=parseFloat('${avlBal}');
	if(ischecked==true){
		$("#volumeDetailId").val(volumeId);
		var needmoney=totalInterestFee-vmoney;
		if(needmoney>=0){
			needPay=needPay-vmoney;
		}else{
			needPay=needPay-totalInterestFee;
		}
	}else{
		$("#volumeDetailId").val("");
	}
	var moneyval=needPay-avlBal;
	if(moneyval>0){
		$("#needdiv").show();
		$("#pay_enough").html(formatMoney(moneyval,2));
		$("#need").val("on");
	}else{
		$("#needdiv").hide();
		$("#need").val("off");
	}
	
	var needPays=formatMoney(needPay,2);
	$("#need_pay").html(needPays);
}
</script>
</head>
<body>

	<!--顶部 -->
	<%@ include file="../common/personheader.jsp"%>

	<div class="capital">
	
	<form method="POST" action="${ctx}/trade/moresuccess">
		<input type="hidden" name="lever" id="lever" value="${lever}"/>
		<input type="hidden" name="type"  value="${type}"/>
		<input type="hidden" name="tradeStart" id="tradeStart" value="${tradeStart}"/>
		<input type="hidden" name="capitalMargin" id="capitalMargin" value="${capitalMargin}"/>
		<input type="hidden" name="borrowPeriod" id="borrowPeriod" value="${borrowPeriod}"/>
		<input type="hidden" name="user" id="user" value="${user}"/>
		<input type="hidden" name="need" id="need" value="${need}"/>
		<input type="hidden" name="groupId" id="groupId" value="${groupId}"/>
		<input type="hidden" name="needNext" id="needNext" value="${needNext}"/>

	<div class="cp_ctn">
		<!-- 支付操盘费用 -->	
		<div class="cp_payctn">	
			<div class="cp_paytitle">操盘费用</div>
			<ul class="cp_paylist">
				<li>
					<p>操盘保证金</p>
					<p>￥<i><fmt:formatNumber value="${capitalMargin} " pattern="###,###"  ></fmt:formatNumber></i>元</p>
				</li>
				<li>
					<p>操盘金额</p>
					<p>￥<i><fmt:formatNumber value="${capitalAmount} " pattern="###,###"  ></fmt:formatNumber></i>元</p>
				</li>
				<li>
					<p>总操盘金额</p>
					<input type="hidden" name="totalTrader" id="totalTrader" value="${totalTrader}"/>
					<p>￥<i><fmt:formatNumber value="${totalTrader} " pattern="###,###"  ></fmt:formatNumber></i>元</p>
				</li>
				<li>
					<p>借款期限</p>
					<p>${borrowPeriod}天</p>
				</li>
				<li>
					<p>总操盘利息</p>
					<p>￥<i><fmt:formatNumber value="${totalInterestFee} " type="number" minFractionDigits="2" ></fmt:formatNumber></i>元</p>
				</li>
				<li>
					<p>每天账户管理费</p>
					<p>￥<i><fmt:formatNumber value="${manageFee} " type="number" minFractionDigits="2" ></fmt:formatNumber></i>元</p>
				</li>
			</ul>
			<p class="cp_paypromt">先支付使用账户期限操盘利息，预存的账户管理费先存入到您的账户，<i>使用一天扣一天</i>。借款账户，如账余额充足，<i>默认自动延期</i>，扣除当天账户管理费，否则自动终止。<em id="need_next_pay" style="color:#ff3000;">您的余额不足以扣取下一个交易日的管理费，请及时充值，以免方案被自动平仓、终止。</em></p>		
			<div class="cp_paytitle">风险规则</div>
			<table class="cp_paylist2" cellpadding="0" cellspacing="0">
				<tr>
					<td width="40%">操盘须知</td>
					<td width="30%">亏损补仓钱</td>
					<td width="30%">亏损平仓钱</td>
				</tr>
				<tr>
					<td>${operatorsInfo}</td>
					<td>￥<i><fmt:formatNumber value="${shortLine} " pattern="###,###"></fmt:formatNumber></i>元</td>
					<td>￥<i><fmt:formatNumber value="${openLine} " pattern="###,###"></fmt:formatNumber></i>元</td>
				</tr>
			</table>
		</div>
		<input type="hidden" name="volumeDetailId" id="volumeDetailId">
		</form>
	
		<div class="cp_paymoney">
			<!-- 账户余额充足的时候，span隐藏 -->

			<span id="needdiv">您的账户余额只剩<i id="avl_bal"><fmt:formatNumber value="${avlBal}" type="number" minFractionDigits="2" ></fmt:formatNumber></i>元，本次支付还差<i id="pay_enough"><fmt:formatNumber value="${payEnough}" type="number" minFractionDigits="2" ></fmt:formatNumber></i>元。<a href="${ctx}/pay/payinfo">立即充值</a></span>
		
			<p>应付款：<em >￥<em id="need_pay"><fmt:formatNumber value="${needPay}" type="number" minFractionDigits="2" ></fmt:formatNumber></em>元</em></p>
				<c:if test="${userVolumeDetailVoList!= null && fn:length(userVolumeDetailVoList) > 0}">
				<div class="cp_tkchoose">
					
					<input type="checkbox" name="ticketid" id="ticketid"/><i>是否使用抵扣券</i>
					<select id="volumeId" name="volumeId" >
						<c:forEach items="${userVolumeDetailVoList}" var="volume">
						 <option value="${volume.id}" datamoney="${volume.money}">${volume.name}</option>
						
						</c:forEach>
					</select>
				</div>
			</c:if>
		</div>

		<div class="cp_paybtn">
			<div class="uc_paybtn cp_btnsure"><a href="javascript:void(0);" id="sub">确定申请</a></div>

			<div class="cp_btnback"><a href="${ctx}/trade/more/${groupId}?capitalMargin=${capitalMargin}&tradeStart=${tradeStart}&borrowPeriod=${borrowPeriod}">返回修改</a></div>

			
		</div>
	</div>

<script type="text/javascript">
	
</script>
	<%@ include file="../common/personfooter.jsp"%>
<%@ include file="../common/dsp.jsp"%>
</body>
</html>

