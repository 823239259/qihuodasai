<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>
<%@include file="../common/import-fileupload-js.jspf"%>
<%@include file="../common/import-artDialog-js.jspf"%>
<%
	String tab = request.getParameter("tab");
%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="zh">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>我的邀请码 - 维胜</title>
<link rel="stylesheet" href="${ctx}/static/css/uc.css?version=20150721" type="text/css">
<link href="${ctx}/static/css/public.css" rel="stylesheet"
	type="text/css">
<link href="${ctx}/static/css/pagination.css" rel="stylesheet"
	type="text/css" />
<script src="${ctx}/static/script/common/jquery.pagination.js"
	type="text/javascript"></script>
	<!-- 
<link href="common/tablesorter.css" rel="stylesheet" type="text/css" />
	 -->
<script src="${ctx}/static/script/My97DatePicker/WdatePicker.js"
	type="text/javascript"></script>

<script type='text/javascript'
	src="${ctx}/static/script/fund/fundDeatail.js?version=20150721"></script>
<script type='text/javascript'
	src="${ctx}/static/script/common/dateUtils.js"></script>
<script type='text/javascript'
	src="${ctx}/static/script/common/ZeroClipboard.min.js"></script>
<script type="text/javascript" src="${ctx}/static/script/tzdr.js"></script>
<title>维胜</title>
<script type="text/javascript">
	var tab =<%=tab%>;

	var isOut = false;

	$(document).ready(function() {
		$('#paytype').click(function(event) {
			event.stopPropagation();
			$("#paytypediv").show();
		});

		$(document).click(function() {
			if (isOut == false) {
				$("#alltypediv").hide();
				$("#paytypediv").hide();
				$("#fundLoantypediv").hide();
				$("#uniondiv").hide();
				$("#interestdiv").hide();
			}
		});

		$('#alltype').click(function(event) {
			event.stopPropagation();
			$("#alltypediv").show();
		});

		$('#fundLoantype').click(function(event) {
			event.stopPropagation();
			$("#fundLoantypediv").show();
		});

		$('#interesttype').click(function(event) {
			event.stopPropagation();
			$("#interestdiv").show();
		});

		$('#uniontype').click(function(event) {
			event.stopPropagation();
			$("#uniondiv").show();
		});

		$("#alltypediv a").each(function() {
			$(this).click(function() {
				$('#fundtype').attr("data-id", $(this).attr("data-id"));
				var value = $(this).html();
				$('#fundtype').val(value);
				$('#alltypediv').hide();
			});

		});

		$("#paytypediv a").each(function() {
			$(this).click(function() {
				var value = $(this).html();
				$('#changetype').attr("data-id", $(this).attr("data-id"));
				$('#changetype').val(value);
				$('#paytypediv').hide();
			});

		});

		$("#interestdiv a").each(function() {
			$(this).click(function() {
				var value = $(this).html();
				$('#interest').attr("data-id", $(this).attr("data-id"));

				$('#interest').val(value);
				$('#interestdiv').hide();
			});

		});

		$("#fundLoantypediv a").each(function() {
			$(this).click(function() {
				var value = $(this).html();
				$('#fundLoan').attr("data-id", $(this).attr("data-id"));

				$('#fundLoan').val(value);
				$('#fundLoantypediv').hide();
			});

		});

		$("#uniondiv a").each(function() {
			$(this).click(function() {
				var value = $(this).html();
				$('#union').attr("data-id", $(this).attr("data-id"));
				$('#union').val(value);
				$('#uniondiv').hide();
			});

		});

		var index="${index}";
		if(index){
			$("#fundtab ul.uc_paynav a").eq(index).trigger("click");
		}
		
		//初始化tab页面
		
	});
	$(function(){
		var tts=$("#invitationCode").find('ul.uc_paynav a');
		var tis=$("#invitationCode").find('div.tabcon div.subtab');
		tts.first().addClass('on');
		tis.first().show().siblings().hide();
		tts.on("click",function(){
			tts.removeClass('on');
			var i = tts.index(this);
			var currTab = tts.eq(i);
			var currTabCon=tis.eq(i);
			if(currTab.hasClass('on'))return;
			var text=currTab.text();
			tts.eq(i).addClass('on');
			currTabCon.show().siblings().hide();
		})
	})
</script>
<style>
	#nav_my {color: #ffcc33; border-bottom:2px solid #ffcc33; padding-bottom: 26px;}
</style>
</head>
<body>
	<!--顶部 -->
	<!--个人中心导航 -->
	<%@include file="../common/header.jsp"%>
	<div class="uc">
		<!--个人中心导航 -->
		<%@ include file="../common/leftnav.jsp"%>
		<!--网银支付-->
		<div class="uc_mianbar">
			<div class="uc_pay" id="invitationCode">
				<ul class="uc_paynav">
					<li><a href="javascript:void(0)" class="on">我的邀请码</a></li>
					<li><a href="javascript:void(0)">佣金明细</a></li>
				</ul>

				<div class="tabcon">
					<div class="subtab">
						<p>以下网址是您对外界进行推广的地址，您可以通过朋友、QQ、微信、博客进行推广，所有通过改地址访问过来的人，注册就都属于您的用户，而当这些用户在本站股票操盘时，您就可以赚取佣金了。</p>
						
					</div>

					<div class="subtab">
						<!-- 充值取款明细 -->
						<div class="uc_trade">
							<div class="uc_floatlayer">
								<div class="uc_bc_option uc_fs_type" id="paytypediv"
									style="display: none;">
									<a href="javascript:void(0)" data-id="1,2,3,4">全部明细</a> <a
										href="javascript:void(0)" data-id="1">充值存入</a> <a
										href="javascript:void(0)" data-id="2">提现取出</a>
										<a href="javascript:void(0)" data-id="3">系统调账</a>
										<a href="javascript:void(0)" data-id="4">系统冲账</a>
								</div>
							</div>
							<div class="uc_fsdetails">
								<div class="uc_fsmoney">
									<p>
										收入<i class="color1" id="inpaycount"><c:choose><c:when test="${!empty inpayfund.count}">${inpayfund.count}</c:when><c:otherwise>0</c:otherwise></c:choose></i>笔，共<i class="color1" id="inpaysum"><c:choose><c:when test="${!empty inpayfund.summoney}"><fmt:formatNumber value="${inpayfund.summoney}"
														pattern="##.##" minFractionDigits="2"></fmt:formatNumber></c:when><c:otherwise>0.00</c:otherwise></c:choose></i>元</p>
									<p>
										支出<i class="color5" id="outpaycount"><c:choose><c:when test="${!empty outpayfund.count}">${outpayfund.count}</c:when><c:otherwise>0</c:otherwise></c:choose></i>笔，共<i class="color5" id="outpaysum"><c:choose><c:when test="${!empty outpayfund.summoney}"><fmt:formatNumber value="${outpayfund.summoney}" pattern="##.##" minFractionDigits="2"></fmt:formatNumber></c:when><c:otherwise>0.00</c:otherwise></c:choose></i>元</p>
								</div>
								<div class="uc_fssearch">
									<em>收支类型：</em> <input type="text" class="uc_fs_ip"
										id="changetype" data-id="1,2,3,4" name="changetype" value="全部明细">
									<a href="javascript:void(0)" id="paytype" name="paytype"
										class="uc_fssdown uc_fsiptype"></a> <em>时间：</em> <input
										type="text" class="uc_fsiptime" id="paystarttime"
										onclick="WdatePicker();" readonly="readonly"
										name="paystarttime"> <i>—</i> <input type="text"
										class="uc_fsiptime" id="payendtime" onclick="WdatePicker();"
										readonly="readonly" name="payendtime"> <span><a
										href="javascript:void(0)"
										onclick="findpayfund('payfundSearchresult','payfundPagination');">查询</a></span>
								</div>
							</div>
							<ul class="uc_fslist">
								<ol class="uc_fsl_title">
									<li class="uc_fsl165">时间</li>
									<li class="uc_fsl200">类型</li>
									<li class="uc_fsl100">收入</li>
									<li class="uc_fsl100">支出</li>
									<!--  
									<li class="uc_fsl100">余额</li>
									-->
								</ol>
								<div>
									<div id="payfundSearchresult"></div>
								</div>
							</ul>
							<div class="uc_tpage uc_fspage">
								<div id="payfundPagination"></div>
							</div>
						</div>
					</div>
					
				</div>
			</div>

		</div>
	</div>
	<%@include file="../common/footer.jsp"%>
	<%@ include file="../common/dsp.jsp"%>
</body>
</html>