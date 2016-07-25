<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <script type="text/javascript">
	
 $(document).ready(function(){
	var nav=$('.uc_sidebar').find('div.uc_nav ul li');
	nav.click(function(){
		$('.uc_sidebar').find("div.uc_nav ul li").each(function(){
			$(this).removeClass('on');
	 	});
		$(this).addClass('on');
	});	
	$('#oAccount').live("click",function(){
		window.location.href=basepath+"user/account";
	});
	
 });
</script>
	<!--个人中心导航 -->
	<div class="uc_sidebar">
		<h2 id="oAccount" style="cursor: pointer;"><a href="#">账户首页<i>></i></a></h2>
		<div class="uc_nav">
			<ul>
				<li><a id="stock" href="${ctx}/trade/list" class="icon1">A股方案</a><i>></i></li>
				<li><a id="hkstock" href="${ctx}/uhkstock/list" class="icon2">港股方案 </a><i>></i></li>
			</ul>
		</div>
		<div class="uc_nav">
			<ul>				
				<li><a id="paynav" href="${ctx}/pay/payinfo" class="icon3">我要充值</a><i>></i></li>
				<li><a id="drawnav" href="${ctx}/draw/drawmoney" class="icon4">我要提现</a><i>></i></li>
				<li><a id="fund" href="${ctx}/fund/fundDetail" class="icon5">资金明细</a><i>></i></li>
				<li><a id="coupon" href="${ctx}/user/coupon/list" class="icon8">我的优惠</a><i>></i></li>			
			</ul>
		</div>
		<div class="uc_nav">
			<ul>
				<li><a id="toUserInfo" href="${ctx}/userinfo/info" class="icon6">个人信息</a><i>></i></li>
				<li><a id="security" href="${ctx}/securityInfo/secInfo" class="icon7">安全信息</a><i>></i></li>				
			</ul>
		</div>
		 <div class="uc_nav" style="margin-bottom:100px;">
			<ul>
				<li><a id="toGeneralize" href="${ctx}/generalize/details" class="icon010">股票代理</a><i>></i></li>
				<%--<li><a id="userVolume" href="${ctx}/uservolume/volumelist">抵扣券</a><i>></i></li> --%>
			</ul>
		</div>
	</div>