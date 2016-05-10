<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>
<%@include file="../common/import-artDialog-js.jspf"%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="zh">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%-- <%@ include file="../common/commonkeyword.jsp"%> --%>
<title>A股操盘支付确认 - 配股宝</title>
<%-- <link href="${ctx}/static/css/public.css?version=20150602" rel="stylesheet"
	type="text/css"> --%>
<link href="${ctx}/static/css/trade.css?v=${v}" rel="stylesheet"
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
        <h2>今日限购</h2>
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

如遇到问题，请联系客服 400-633-9257
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
	<div class="floatlayer" style="display: none;" id="submitlayer">
		<div class="fl_mask"></div>
		<!-- 确认提示 -->
		<div class="fl_layer" style="display: none;" id="submitConfirm">
			<div class="fl_navtitle">
				<h3>确认提示</h3>
				<a href="javascript:void(0);" onclick="closeSubmitConfirm('submitConfirm')" class="close"></a>
			</div>
			<div class="fl_l_main">
				<ul class="fl_l_list">
					<li><label>账户余额：</label><span><i id="submitConfirm_balance"></i>元</span></li>
					<li><label>支付金额：</label><span><i id="submitConfirm_pay"></i>元</span></li>
				</ul>
			</div>
			<div class="fl_l_btn"><a href="javascript:void(0);" onclick="closeSubmitConfirm('submitConfirm')" class="cancel">取消</a><a href="javascript:void(0);" onclick="submitTrade()" class="sure">确认支付</a></div>
		</div>		
		<!-- 确认提示,余额不足 -->
		<div class="fl_layer"  style="display: none;" id="submitMsgConfirm">
			<div class="fl_navtitle">
				<h3>确认提示</h3>
				<a href="javascript:void(0);" onclick="closeSubmitConfirm('submitMsgConfirm')" class="close"></a>
			</div>
			<div class="fl_l_main">
				<ul class="fl_l_list">
					<li><label>账户余额：</label><span><i id="submitMsgConfirm_balance"></i>元</span></li>
					<li><label>支付金额：</label><span><i id="submitMsgConfirm_pay"></i>元</span></li>
				</ul>				
				<div class="fl_l_nomoney2" id="nextNotEnoughDiv">
					<span>您的余额不足以扣除下个交易日的账户管理费，</span><a href="${ctx}/pay/payinfo">请立即充值！</a>
				</div>
				<div class="fl_l_nomoney" id="notEnoughDiv">
					<span>余额不足，</span><a href="${ctx}/pay/payinfo">请立即充值！</a>
				</div>
			</div>
			<div class="fl_l_btn"><a href="javascript:void(0);" onclick="closeSubmitConfirm('submitMsgConfirm')"  class="cancel">取消</a><a href="javascript:void(0);" class="sure nosure">确认支付</a></div>
		</div>	
	</div>
	<!-- 最大操盘额限制弹框 -->
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
					<form method="POST" action="${ctx}/trade/success?type=6600">
				</c:when>
				<c:otherwise><form method="POST" action="${ctx}/trade/success"></c:otherwise>
			</c:choose>
	
	
		<input type="hidden" name="lever" id="lever" value="${lever}"/>
		<input type="hidden" name="type"  value="${type}"/>
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
		<input type="hidden" name="holdMaxNum" id="holdMaxNum" value="${holdMaxNum}"/>
	<div class="cp_ctn">
		<div class="cp_title">A股操盘</div>
		<!-- 支付操盘费用 -->	
		<div class="cp_payctn">	
			<div class="cp_paytitle">操盘规则</div>
			<ul class="cp_paylist">
				<li>
					<p style="background:#f9f9f9;">总操盘金额</p>
					<span><i><fmt:formatNumber value="${totalTrader}" pattern="###,###" ></fmt:formatNumber></i>元</span>
				</li>
				<li>
					<p style="background:#f9f9f9;">亏损警戒线</p>
					<span><i><fmt:formatNumber value="${shortLine}" pattern="###,###" ></fmt:formatNumber></i>元</span>
				</li>
				<li>
					<p style="background:#f9f9f9;">亏损平仓线</p>
					<span><i><fmt:formatNumber value="${openLine}" pattern="###,###" ></fmt:formatNumber></i>元</span>
				</li>
				<li>
					<p style="background:#f9f9f9;">借款期限</p>
					<span><i>${borrowPeriod}</i>天</span>
				</li>
				<li style="width:200px;">
					<p style="background:#f9f9f9;">开始操盘时间</p>
					<span><i>${tradeTime}</i></span>
				</li>
				
			</ul>	
 			<div class="cp_paytitle">注意事项<a href="${ctx}/help?status=1#list" target="_blank">今日限制股</a></div>
			<table class="cp_paylist2" cellpadding="0" cellspacing="0">
				<tr>
					<td width="50%" style="background:#f9f9f9;">操盘总利息</td>
					<td width="50%" style="background:#f9f9f9;">账户管理费</td>
				</tr>
				<tr>
					<td><p><span>申请时按操作天数一次性收取，延后再按天收取，<i><fmt:formatNumber value="${interestFee}" type="number" minFractionDigits="2" ></fmt:formatNumber></i>元/天</span></p>
					</td>
					<td><p><span><i><fmt:formatNumber value="${manageFee}" type="number" minFractionDigits="2" ></fmt:formatNumber></i>元/交易日，使用一个交易日扣一次</span></p>
					</td>
				</tr>
				<tr>
					<td colspan="2">
					请保持账户余额充足，当余额不足以扣除利息或管理费时，系统将主动为您终结操盘方案
					</td>
				</tr>
			</table>
			
			<div class="cp_paytitle">支付费用
			</div>
			<table class="cp_paylist2" cellpadding="0" cellspacing="0">
				<tr>
					<td width="20%" style="background:#f9f9f9;">操盘保证金</td>
					<td width="20%" style="background:#f9f9f9;">操盘总利息</td>
					<td width="20%" style="background:#f9f9f9;">账户管理费</td>
					<td width="10%" rowspan="2">=</td>
					<td width="30%" style="background:#f9f9f9;">本次应付总金额</td>
				</tr>
				<tr>
					<td>
						<p><i><fmt:formatNumber value="${capitalMargin}" pattern="###,###" ></fmt:formatNumber></i>元</p>
					</td>
					<td><p><span><i><fmt:formatNumber value="${totalInterestFee}" type="number" minFractionDigits="2" ></fmt:formatNumber></i>元</span></p>
					</td>
					<td><p><span><i><fmt:formatNumber value="${payManageFee==null?0:payManageFee}" type="number" minFractionDigits="2" ></fmt:formatNumber></i>元</span></p>
					</td>
					<td><p><span><i><fmt:formatNumber value="${needPay}" type="number" minFractionDigits="2" ></fmt:formatNumber></i>元</span></p>
					</td>
				</tr>
			</table>
		</div>
		<input type="hidden" name="volumeDetailId" id="volumeDetailId">
		</form>
		
<%-- 	
		<div class="cp_paymoney" style="display:none;">
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
		</div> --%>

		<div class="cp_paybtn">
			<div class="uc_paybtn cp_btnsure"><a href="javascript:void(0);" id="sub">下一步</a></div>
			<c:if test="${empty isFree}">
			<div class="cp_btnback"><a href="${ctx}/day?lever=${lever}&capitalMargin=${capitalMargin}&borrowPeriod=${borrowPeriod}&tradeStart=${tradeStart}&enter=${enter}">返回修改</a></div>	
			</c:if>		
		</div>
	</div>
</div>
<script type="text/javascript">
	
</script>
	<%@ include file="../common/personfooter.jsp"%>
<%@ include file="../common/dsp.jsp"%>
</body>
</html>

