<template>
	<div id="account_resetLoginPassword">
		<div class="account_resetLoginPassword_top">
			<p>您正在为账户<span>182****222</span>重置手机密码</p>
		</div>
		<div class="account_resetLoginPassword_center">
			<p>旧手机密码：<input type="text" /></p>
			<p>新手机密码：<input type="text" /></p>
			<p>确认信密码：<input type="text" /></p>
			<button class="btn yellow">确认</button>
		</div>
		<div class="account_resetLoginPassword_btm">
			<p>设置资金密码遇到问题</p>
			<ul>
				<li>重置新密码规则？</li>
				<li>新密码与旧密码不能相同，如果有疑问请拨打客服热线</li>
			</ul>
			<p>投资有风险，入市需谨慎</p>
		</div>
	</div>
</template>

<script>
	export default {
		name : "account_resetLoginPassword"
	}
</script>


<style lang="scss" scoped type="text/css">
	@import "../../assets/css/common.scss";
	#account_resetLoginPassword {
		width: 100%;
		.account_resetLoginPassword_top{
			width:100%;
			p {
				width: 100%;
				height: 40px;
				line-height: 40px;
				background-color: $bottom_color;
				span {
					color:$white;
				}
			}
		}
		.account_resetLoginPassword_center {
			width: 100%;
			height: 240px;
			background-color: $blue;
			text-align : center;
			p {
				padding-top : 20px;
			}
			input {
				width: 160px;
				height: 30px;
				border: 1px solid $bottom_color;
				border-radius: 5px;
			}
			.btn {
				width: 160px;
				height: 30px;
				margin-top: 10px;
			}
		}
		.account_resetLoginPassword_btm {
			width: 100%;
			height: 170px;
			p {
				width: 100%;
				height: 40px;
				background-color: $bottom_color;
				color: $white;
				line-height: 40px;
				margin-top: 5px;
				&:nth-child(3){
					text-align: center;
					font-size: $fs12;
					color: #7a7f99;
				}
			}
			ul {
				height: 80px;
				background-color: $blue;
			}
			li {
				&:nth-child(1){
					padding-top: 20px;
					font-size: $fs12;
				}
				&:nth-child(2){
					color: $white;
					font-size: $fs12;
					padding-top: 10px;
				}
			}
		}
		
		
	}

</style>
