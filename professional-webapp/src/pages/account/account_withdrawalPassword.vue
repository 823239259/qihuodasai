<template>
	<div id="account_withdrawlPassword">
		<div class="account_withdrawlPassword_top">
			<p>您正在为账户<span>182****111</span>设置提现密码</p>
		</div>
		<div class="account_withdrawlPassword_center">
			<p>手机号码：182****222</p>
			<p>短信验证码：<input type="text" /></p>
			<p>提现密码：<input type="text" /></p>
			<p>确认密码：<input type="text" /></p>
			<button class="btn yellow">确认</button>
		</div>
		<div class="account_withdrawlPassword_btm">
			<p>实名认证相关</p>
			<ul>
				<li>实名认证遇到问题怎么办？</li>
				<li>答：实名认证因涉及到账户提现安全，最好认证本人身份信息，认证信息错误三次以上需要后台人工审核才可继续认证。</li>
			</ul>
			<p>投资有风险，入市需谨慎</p>
		</div>
	</div>
</template>

<script>
	export default {
		name : "account_withdrawalPassword"
	}
</script>


<style lang="scss" scoped type="text/css">
	@import "../../assets/css/common.scss";
	#account_withdrawlPassword {
		width: 100%;
		.account_withdrawlPassword_top {
			 p {
			 	width: 100%;
			 	height: 40px;
			 	line-height: 40px;
			 	background-color: $bottom_color;
			 	span {
			 		color:$white;
			 		margin : 0 5px 0 5px;
			 	}
			 }
		}
		.account_withdrawlPassword_center{
			width: 100%;
			height: 330px;
			text-align: center;
			background-color: $blue;	
			p {
				padding-top: 20px;
				&:nth-child(3) {
					border-top: 1px solid $bottom_color;
					margin-top: 20px;
				}
			}
			input {
				width : 160px;
				height: 30px;
				border: 1px solid $bottom_color;
				border-radius: 5px;
			}
			.btn {
				width: 160px;
				height: 30px;
				margin-top: 30px;
			}
		}
		.account_withdrawlPassword_btm {
			width: 100%;
			height: 170px;
			p {
				&:nth-child(1){
					width: 100%;
					height: 40px;
					background-color: $bottom_color;
					line-height: 40px;
					margin-top: 10px;
				}
				&:nth-child(3){
					width: 100%;
					height: 40px;
					line-height: 40px;
					margin-top: 10px;
					background-color: $blue;
					text-align: center;
					font-size: $fs12;
					background-color: $bottom_color;
				}
			}
			ul {
				height: 80px;
				width: 100%;
				background-color: $blue;
			}
			li {
				padding-top:20px; 
				&:nth-child(2) {
					color: $white;
					font-size: $fs12;
				}
			}
		}
	}
	
</style>