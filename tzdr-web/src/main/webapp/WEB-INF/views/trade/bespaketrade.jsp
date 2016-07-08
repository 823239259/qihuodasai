<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>
<%@include file="../common/import-artDialog-js.jspf"%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="zh">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="../common/commonkeyword.jsp"%>
<link href="${ctx}/static/css/public.css?version=20150602" rel="stylesheet"
	type="text/css">
<link href="${ctx}/static/css/trade.css?version=20150602" rel="stylesheet"
	type="text/css" />
<link href="${ctx}/static/css/tzdr.css?version=20150602" rel="stylesheet"
	type="text/css" />
<link href="${ctx}/static/css/vtrade.css?version=20150602" rel="stylesheet" type="text/css">	
<script language="javascript" src="${ctx}/static/script/trade/trade.js?version=20150622"></script>
<script language="javascript" src="${ctx}/static/script/common/tzdr.user.js?version=20150720"></script>
<script language="javascript" src="${ctx}/static/script/tzdr.js"></script>
<script type="text/javascript">
$(document).ready(function(){
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

	    var ischecked=$("#ticketid").is(":checked");
    	var volumeId=$("#volumeId").val();
    	
    	if(ischecked==true){
    		$("#volumeDetailId").val(volumeId);
    	}else{
    		$("#volumeDetailId").val("");
    	}
    
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
<div class="floatlayer">	
    <div class="fl_mask" style="display:none;"></div>
    <!-- 限制股弹出框 -->
    <div class="fl_cpdb" style="display:none;">
        <h2>今日限制股</h2>
        <ul class="fl_cpdblist">
            
	           
        </ul>
        <div class="fl_cpdbtn"><a href="javascript:void(0)">我知道了</a></div>
    </div>
</div>


<div id="div_Mask"  style="display:none;"></div>
<div class="tck01" id="idcardDiv" style="display:none;" >
<div class="navtitle"><a class="nava" style="width:90px;" >实名认证</a><a class="close" onclick="javascript:closeDiv('idcardDiv')"></a></div>
<div class=" wzms"> 实名信息提交后不可修改，请务必认真填写真实资料

 一个身份证只能绑定一个帐号。

如遇到问题，请联系客服 400-852-8008
</div>
<div class="smain" >
<div class="srk">
<span class="label">真实姓名：</span><input class="au-ipt" name="cardname" id="cardname" type="text">
</div>
<div class="srk"> <span class="label">身份证号：</span>
    <input class="au-ipt" name="input" name="idcard" id="idcard" type="text">
  </div>
 </div>
<!--001-1-->  
 <div class="anniu">
 <a class="btn-h01" id="validating" onclick="javascript:validateCard();">提&nbsp;交</a><a class="btn-h02" onclick="javascript:closeDiv('idcardDiv')">取&nbsp;消</a>
</div>
</div>

	<!--顶部 -->
	<%@ include file="../common/personheader.jsp"%>
	<!-- 最大操盘配额限制弹框 -->
    <div class="fl_mnpromt" style="display: none;">
	        <p><span>为保障更多用户获得操盘，每位用户每天只能新增<i>${limitTradeNum}</i>个操盘方案</span><br>本时段最大操盘配额限<i>500,000</i>元</p>
	        <a href="javascript:void(0);" id="OK">确定</a>
	</div>
	<div class="fl_mask" style="display:none;"></div>
		<c:if test="${!empty isFree}">
			<div class="fl_tdmask"></div>	
		</c:if>	
	<div class="capital">
			<c:choose>
				<c:when test="${!empty isFree}">
					<form method="POST" action="${ctx}/trade/success?type=8800">
				</c:when>
				<c:otherwise><form method="POST" action="${ctx}/trade/success"></c:otherwise>
			</c:choose>
	
		<input type="hidden" name="tradeConfigId" id="tradeConfigId" value="${tradeConfigId}"/>
		<input type="hidden" name="tradetype" id="tradetype" value="${tradetype}"/>
		<input type="hidden" name="lever" id="lever" value="${lever}"/>
		<input type="hidden" name="type"  value="0"/>
		<input type="hidden" name="tradeStart" id="tradeStart" value="${tradeStart}"/>
		<input type="hidden" name="capitalMargin" id="capitalMargin" value="${capitalMargin}"/>
		<input type="hidden" name="borrowPeriod" id="borrowPeriod" value="${borrowPeriod}"/>
		<input type="hidden" name="user" id="user" value="${user}"/>
		<input type="hidden" name="need" id="need" value="${need}"/>
		<input type="hidden" name="isVerified" id="isVerified" value="${isVerified}"/>
		<input type="hidden" name="proCount" id="proCount" value="${proCount}"/>
		<input type="hidden" name="needNext" id="needNext" value="${needNext}"/>
		<input type="hidden" name="userTodayTradeNum" id="userTodayTradeNum" value="${userTodayTradeNum}"/>
		<input type="hidden" name="isOpen" id="isOpen" value="${isOpen}"/>
		<input type="hidden" name="maxLeverMoney" id="maxLeverMoney" value="${maxLeverMoney}"/>
		<input type="hidden" name="maxLeverMoney" id="limitTradeNum" value="${limitTradeNum}"/>
        <input type="hidden" name="interest" id="interest" value="${interest}"/>
        <input type="hidden" name="expenese" id="expenese" value="${expenese}"/>
        
        <input type="hidden" name="holdMaxNum" id="holdMaxNum" value="${holdMaxNum}"/>
        
	<div class="cp_ctn">
		<!-- 支付操盘费用 -->	
		<div class="cp_payctn">	
			<div class="cp_paytitle">操盘费用</div>
			<ul class="cp_paylist">
				<li>
					<p>操盘保证金</p>
					<p>￥<i><fmt:formatNumber value="${capitalMargin}" pattern="###,###" ></fmt:formatNumber></i>元</p>
				</li>
				<li>
					<p>操盘配额</p>
					<p>￥<i><fmt:formatNumber value="${capitalAmount}" pattern="###,###" ></fmt:formatNumber></i>元</p>
				</li>
				<li>
					<p>总操盘金额</p>
					<p>￥<i><fmt:formatNumber value="${totalTrader}" pattern="###,###" ></fmt:formatNumber></i>元</p>
				</li>
				<li>
					<p>借款期限</p>
					<p>${borrowPeriod}天</p>
				</li>
				<li>
					<p>总操盘利息</p>
					<p><c:if test="${!empty isFree}"><img src="${ctx}/static/images/free.gif" class="freeicon" title="达人红包8800活动省"></c:if><span>￥<i><fmt:formatNumber value="${totalInterestFee}" type="number" minFractionDigits="2" ></fmt:formatNumber></i>元</span></p>
				</li>
				<li>
					<p>每天账户管理费</p>
					<p><c:if test="${!empty isFree}"><img src="${ctx}/static/images/free.gif" class="freeicon"></c:if><span>￥<i><fmt:formatNumber value="${manageFee}" type="number" minFractionDigits="2" ></fmt:formatNumber></i>元</span></p>
				</li>
			</ul>
			<p class="cp_paypromt">先支付使用账户期限操盘利息，预存的账户管理费先存入到您的账户，<i>使用一天扣一天</i>。借款账户，如账余额充足，<i>默认自动延期</i>，扣除当天账户管理费，否则自动终止。<em id="need_next_pay" style="color:#ff3000;">您的余额不足以扣取下一个交易日的管理费，请及时充值，以免方案被自动平仓、终止。</em></p>		
			<div class="cp_paytitle">风险规则
			<span class="cp_dblink"><a href="javascript:void(0);" class="cp_sdblink">今日限制股</a></span>
			</div>
			<table class="cp_paylist2" cellpadding="0" cellspacing="0">
				<tr>
					<td width="40%">操盘须知</td>
					<td width="30%">亏损补仓钱</td>
					<td width="30%">亏损平仓钱</td>
				</tr>
				<tr>
					<td>${operatorsInfo}</td>
					<td>￥<i><fmt:formatNumber value="${shortLine}" type="number" pattern="###,###"></fmt:formatNumber></i>元</td>
					<td>￥<i><fmt:formatNumber value="${openLine}" pattern="###,###"></fmt:formatNumber></i>元</td>
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
			<div class="cp_dzpaypromt" style="display:none;"><a href="${ctx}/topic/traderule/" target="_blank">规则介绍</a></div>
		</div>
	</div>
</div>
<script type="text/javascript">
	
</script>
	<%@ include file="../common/personfooter.jsp"%>
<%@ include file="../common/dsp.jsp"%>
</body>
</html>

