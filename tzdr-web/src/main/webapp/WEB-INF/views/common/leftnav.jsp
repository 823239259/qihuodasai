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
		<h2 id="oAccount" style="cursor: pointer;"><a href="#">账户中心<i>></i></a></h2>
		<div class="uc_nav">
			<ul>
				<li><a id="ftse" href="${ctx}/userftse/trade_list">操盘记录</a><i>></i></li>
			</ul>
		</div>
		<div class="uc_nav">
			<ul>
				<li><a id="paynav" href="${ctx}/pay/payinfo">账户充值</a><i>></i></li>
				<li><a id="drawnav" href="${ctx}/draw/drawmoney" >我要提现</a><i>></i></li>
				<li><a id="fund" href="${ctx}/fund/fundDetail">资金明细</a><i>></i></li>
				<li><a id="coupon" href="${ctx}/user/coupon/list">我的优惠</a><i>></i></li>
			</ul>
		</div>
		<div class="uc_nav" style="border-bottom:none;">
			<ul>
				<li><a id="security" href="${ctx}/securityInfo/secInfo" >安全信息</a><i>></i></li>
				<li><a id="toMessage" href="${ctx}/message/usermessage">在线留言</a><i>></i></li>
				<li><a id="invitationCode" href="${ctx}/extendsion/sign/invitation/code">邀请送礼</a><i>></i></li>
			</ul>
		</div>
	</div>