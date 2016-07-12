<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>
<%@include file="../common/import-artDialog-js.jspf"%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="zh">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>维胜-中国领先的互联网金融交易平台</title>
<link href="${ctx}/static/css/uc.css?v=${v}" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/css/pagination.css?v=${v}" rel="stylesheet" type="text/css" />
<script type="text/javascript"src="${ctx}/static/script/common/jquery.pagination.js"></script>
<script type="text/javascript" src="${ctx}/static/script/coupon/coupon_common.js?v=${v}"></script>
<script type="text/javascript" src="${ctx}/static/script/coupon/coupon_list.js?v=${v}"></script>
<style>
	#nav_my {color: #ffcc33; border-bottom:2px solid #ffcc33; padding-bottom: 26px;}
</style>
<body>
	<!-- 弹窗口 -->
	<div class="uc_floatlayer" id="redDev" style="display: none">
		<div class="fl_mask"></div>
		<div class="tp_myprize" style="display: block;">
			<p id="redMoney">获得5000元现金红包</p>
			<a href="javascript:void(0);" onclick="closeUseRedPackageDev();">确定</a>
		</div>
	</div>
	<!--顶部 -->
	<%@include file="../common/header.jsp"%>
	<div class="uc">
		<!--个人中心导航 -->
		<%@ include file="../common/leftnav.jsp"%>
		<!--我的优惠劵-->
		<div class="uc_mianbar">
			<div class="uc_siflist" style="margin-bottom: 20px;">
				<div class="uc_sl_nav">
					<a data="couponAll" href="javascript:void(0);" id="fSimpleCoupon" class="on tzdr-tab">优惠列表</a>
				</div>
				<!-- 优惠列表 -->
				<div id="fSimpleCouponData" class="uc_cu_list tzdr-data01">
					<ul class="uc_cul_title">
						<li></li>
						<li class="uc_cul_type">类型</li>
						<li>获得时间</li>
						<li class="uc_cul_time">到期时间</li>
						<li class="come">来源</li>
						<li>状态</li>
					</ul>
					<div style="text-align:center;">
				            
				    </div>
				</div>
				<div id="fSimpleCouponPage" style="margin-top: 10px;"></div>
				<!-- 全部优惠劵 -->
				<div class="uc_trade">					
					<div id="content"></div>
				</div>
				<div id="Pagination" style="margin: 20px 0;"></div>
				<div class="uc_cu_promt">
					<h3>温馨提示：</h3>
					<p>1，国际期货、商品期货申请操盘方案时，可使用代金券支付操盘保证金。</p>
					<p>2，国际期货、商品期货申请终结方案时，可使用折扣券享受折扣优惠。</p>
				</div>
			</div>
		</div>
	</div>
	<%@include file="../common/footer.jsp"%>
	<%@ include file="../common/dsp.jsp"%>
</body>
</html>

