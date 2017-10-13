<template>
	<div id="trade_login">
		<div class="bg"></div>
		<div class="trade_login">
			<div class="title">
				<h3>账户密码登录</h3>
				<i class="ifont">&#xe624;</i>
			</div>
			<div class="cont">
				<input type="text" class="ipt" value="DQ000007" placeholder="请输入账户名" />
				<input type="text" class="ipt" value="" placeholder="请输入账户密码" />
				<p>忘记密码？</p>
				<button class="btn yellow">立即登录</button>
			</div>
		</div>
	</div>
</template>

<script>
	export default{
		name: 'trade_login',
	}
</script>

<style lang="scss" scoped>
	@import "../../assets/css/common.scss";
	.trade_login{
		width: 400px;
		height: 320px;
		overflow: hidden;
		background: $blue;
		border-radius: 10px;
		position: absolute;
		top: 50%;
		left: 50%;
		z-index: 100;
		margin: -160px 0 0 -200px;
	}
	.title{
		height: 40px;
		line-height: 40px;
		text-align: center;
		background: $bottom_color;
		position: relative;
		h3{
			font-size: $fs16;
			color: $white;
		}
		i{
			position: absolute;
			top: 2px;
			right: 12px;
			color: #7a8199;
			cursor: pointer;
		}
	}
	.cont{
		padding: 40px;
		.ipt{
			width: 320px;
			height: 40px;
			line-height: 40px;
			color: $white;
			border: 1px solid #474c66;
			border-radius: 5px;
			text-align: center;
			margin-bottom: 20px;
			&:focus{
				border-color: $yellow;
			}
		}
		.btn{
			width: 320px;
			height: 40px;
			line-height: 40px;
		}
		p{
			text-align: right;
			margin-bottom: 20px;
		}
	}
</style>