<template>
	<div id="trade_login">
		<div class="bg"></div>
		<div class="trade_login">
			<div class="title">
				<h3>交易账户快速安全登录</h3>
				<i class="ifont">&#xe624;</i>
			</div>
			<div class="cont">
				<p class="tips">请点击账号授权登录</p>
				<div class="account">
					<ul>
						<li>
							<p>账户：DQ000001</p>
							<p>密码：******</p>
							<p>初始资金：20000元</p>
						</li>
						<li>
							<p>账户：DQ000001</p>
							<p>密码：******</p>
							<p>初始资金：20000元</p>
						</li>
						<li>
							<p>账户：DQ000001</p>
							<p>密码：******</p>
							<p>初始资金：20000元</p>
						</li>
					</ul>
				</div>
				<div class="btn_box">
					<span>账号密码登录</span>
					<span>申请开户</span>
				</div>
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
		height: 260px;
		overflow: hidden;
		background: $blue;
		border-radius: 10px;
		position: absolute;
		top: 50%;
		left: 50%;
		z-index: 100;
		margin: -130px 0 0 -200px;
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
		.tips{
			height: 30px;
			line-height: 30px;
			background: $deepblue;
			font-size: $fs12;
			text-align: center;
		}
		.account{
			height: 130px;
			overflow-y: auto;
			padding: 20px 0 0 0;
			border-top: 1px solid $bottom_color;
			border-bottom: 1px solid $bottom_color;
			li{
				float: left;
				width: 170px;
				height: 90px;
				overflow: hidden;
				border: 1px solid $bottom_color;
				border-radius: 5px;
				cursor: pointer;
				margin: 0 10px 20px 10px;
				padding: 6px 0 0 10px; 
				&:hover{
					border-color: $yellow;
					color: $yellow;
				}
				p{
					height: 24px;
					line-height: 24px;
				}
			}
		}
		.btn_box{
			height: 60px;
			line-height: 60px;
			span{
				float: left;
				display: inline-block;
				width: 50%;
				text-align: center;
				cursor: pointer;
			}
		}
	}
</style>