<template>
	<div id="confirmPayment">
		<div class="bg"></div>
		<div class="confirmPayment">
			<p>确认支付<i class="ifont">&#xe624;</i></p>
			<p>账户余额：<span>0.08</span>元</p>
			<p>支付金额：<span>300</span>元</p>
			<p>您的账户余额<i>0.88</i>元，本次支付还差<i>4000</i>元</p>
			<button class="btn yellow">去充值</button>
			<button class="btn green">取消</button>
		</div>
	</div>
</template>

<script>
	export default {
		name : "openAccount_confirmPayment"
	}
</script>

<style lang="scss" scoped type="text/css">
@import "../../assets/css/common.scss";
	.confirmPayment{
		position: absolute;
		top: 50%;
		left: 40%;
		z-index: 100;
		width: 400px;
		height: 240px;
		background-color: $blue;
		text-align: center;
		border-radius: 10px;
	}
	p {
		&:nth-child(1){
			background-color: $bottom_color;
			border-radius: 10px;
		}
		&:nth-child(4){
			background-color: $deepblue;
			font-size: $fs12;
		}
		height: 40px;
		line-height: 40px;
	}
	.btn {
		width: 120px;
		height: 30px;
		margin-top: 20px;
		margin-left: 10%;
	}
	span {
		color: $yellow;
	}
	i {
		color: $white;
	}
	.ifont {
		float: right;
		margin-right: 10px;
		color: #7a8199;
	}
</style>



