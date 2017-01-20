<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>
<%@include file="../common/import-artDialog-js.jspf"%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="zh">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="keywords" content="维胜，国际期货，期货，投资达人，金勺子，高盛，都城，南华期货，配资，期货配资，期货开户，外盘，富时A50，国际原油，恒指期货，期货公司，期货平台，炒期货，模拟盘，赚钱，头寸，持仓，成都盈透科技有限公司"/>
<meta name="description" content="维胜（www.vs.com）-致力于成为中国领先的国际期货及衍生品互联网交易平台，提供恒指期货、国际原油、富时A50等主流国际期货产品，开户操盘快捷方便，交易费用全网最低。"/>
<title>我的优惠-维胜金融-中国领先的国际期货及衍生品互联网交易平台</title>
<link href="${ctx}/static/css/uc.css?v=${v}" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/css/pagination.css?v=${v}" rel="stylesheet" type="text/css" />
<script type="text/javascript"src="${ctx}/static/script/common/jquery.pagination.js?v=${v}"></script>
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
				<ul class="uc_paynav">
					<li><a data="couponAll" href="javascript:void(0);" class="on tzdr-tab"  id="fSimpleCoupon">优惠列表</a></li>
				</ul>
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
					<p>1，恒指期货、国际原油、富时A50、国际综合申请操盘方案时，可使用代金券支付操盘保证金。</p>
					<p>2，恒指期货、国际原油、富时A50、国际综合申请终结方案时，可使用折扣券享受折扣优惠。</p>
				</div>
			</div>
		</div>
	</div>
	<%@include file="../common/footer.jsp"%>
	<%@ include file="../common/dsp.jsp"%>
</body>
</html>

