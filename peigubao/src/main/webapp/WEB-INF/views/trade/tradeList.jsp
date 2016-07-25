<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>
<%@include file="../common/import-artDialog-js.jspf"%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="zh">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%-- <%@ include file="../common/commonkeyword.jsp"%> --%>
<title>A股方案列表 - 配股宝</title>
<link rel="stylesheet" href="${ctx}/static/css/uc.css?version=20150721" type="text/css">
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/css/public.css">
<link href="${ctx}/static/css/pagination.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript" src="${ctx}/static/script/tzdr.js"></script>
<script type="text/javascript"
	src="${ctx}/static/script/common/jquery.pagination.js"></script>
<script type="text/javascript"
	src="${ctx}/static/script/common/dateUtils.js"></script>
<script type="text/javascript"
	src="${ctx}/static/script/trade/tradeList.js?v=${v}"></script>
<script type="text/javascript">
	
</script>

</head>
<body>

	<!--顶部 -->
	<%@ include file="../common/personheader.jsp"%>
	<div class="uc">
		<!--个人中心导航 -->
		<%@ include file="../common/leftnav.jsp"%>

		<!--网银支付-->
		<div class="uc_mianbar">
			<div class="uc_pay">
				<ul class="uc_paynav">
					<li data="all"><a href="javascript:void(0);" class="on">方案列表</a></li>
	<!--  				<li data="need"><a href="javascript:void(0);">待添加保证金</a></li>
					<li data="opt"><a href="javascript:void(0);">操盘中</a></li>
					<li data="closed"><a href="javascript:void(0);">已完结</a></li> -->
				</ul>
				<!-- 全部申请 -->
				<div class="uc_trade">					
					<div id="content"></div>
				</div>
				<div id="Pagination" style="margin: 20px 0;"></div>
			</div>

		</div>
		<!-- 遮罩层 -->
		<div class="fl_mask" style="display:none;"></div>
		<!-- 追加保证金弹出框 -->
		<div class="fl_box fl_uc_trade" style="display: none;">
			<div class="fl_navtitle">
				<h3>追加保证金</h3>
				<a href="javascript:void(0);" class="close"></a>
			</div>
			<div class="fl_uc_main">
				<ul class="fl_uc_list">
					<!-- 
					<li>
						<label>账户余额：</label>
						<input type="hidden" id="balance" value="${balance}" />
						<input type="hidden" id="group_id" value="${gid}" />
						<span>
							<i><fmt:formatNumber value="${balance}"  type="number" minFractionDigits="2"  ></fmt:formatNumber></i>元
							<a href="${ctx}/pay/payinfo" target="_blank">&nbsp;&nbsp;去充值&gt;&gt;</a>
						</span>
					</li>
					<li>
						<label>总操盘资金：</label>
						<span><i><fmt:formatNumber value="${trade.totalOperateMoney}" type="number" minFractionDigits="2"></fmt:formatNumber></i>元</span>
					</li>
					<li>
						<label>追加金额：</label>
						<input id="additionalMargin" type="hidden" value="${trade.activityType}" />
						<input id="minAddMoney" type="hidden" value="${minAddMoney}" />
						<input id="maxAddMoney" type="hidden" value="${maxAddMoney}" />
						<input id="addMoney" type="text" maxlength="9" value="${minAddMoney}" />
						<span>元</span>
					</li>
					 -->
				</ul>
			</div>
			<div class="fl_uc_btn">
				<a href="javascript:void(0);" class="fl_uc_cancelbtn">取消</a>
				<a id="addBondNext" href="javascript:void(0);" class="fl_uc_surebtn">下一步</a>
			</div>
		</div>
		<!-- 追加保证金弹出确认框 -->
		<div class="fl_box fl_uc_trade2" style="display: none;">
			<div class="fl_navtitle">
				<h3>追加保证金</h3>
				<a href="javascript:void(0);" class="close"></a>
			</div>
			<div class="fl_uc_main">
				<p style="padding: 8px 16px;font-size:14px;text-align: left;font-family: '宋体'; line-height: 24px;">一，系统会先扣账户余额，再追加到A股交易账户中，追加成功后会以短信提示您；</p>
				<p style="padding: 8px 16px;font-size:14px;text-align: left;font-family: '宋体'; line-height: 24px;">二，交易时间，10分钟内完成追加；非交易时间，下个交易日开盘前完成追加。</p>
				<p style="padding: 16px;font-size:14px;text-align: center;font-family: '宋体'; line-height: 24px;">请确认是否追加？</p>
			</div>
			<div class="fl_uc_btn">
				<a href="javascript:void(0);" class="fl_uc_cancelbtn">取消</a>
				<a id="addBond" href="javascript:void(0);" class="fl_uc_surebtn">确认</a>
			</div>
		</div>
		<div class="clear"></div>
	</div>
	<%@ include file="../common/personfooter.jsp"%>
<%@ include file="../common/dsp.jsp"%>
</body>
</html>
