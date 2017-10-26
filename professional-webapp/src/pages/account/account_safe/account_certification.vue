<template>
	<div id="account_certification">
		<div class="account_certification_top">
			<p><span>提示：</span>认证年龄需满<span>18</span>周岁，不建议<span>60</span>岁以上的用户交易</p>
		</div>
		<div class="account_certification_center">
			<p>姓名：<input type="text" /></p>
			<p>身份证号码：<input type="text" /></p>
			<button class="btn yellow">确认</button>
		</div>
		<div class="account_certification_btm">
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
		name : "safe_certification"
	}
</script>

<style lang="scss" scoped type="text/css">
	@import "../../../assets/css/common.scss";
	
	#account_certification {
		width: 1000px;
		.account_certification_top {
			p {
				width :100%;
				text-indent: 10px;
				height :40px;
				line-height : 40px;
				background-color:$bottom_color;
				span {
					color : $white;
					padding : 0 5px 0 5px;
				}
			}
		}
		.account_certification_center {
			height: 190px;
			text-align: center;
			background-color: $blue;
			input {
				width: 160px;
				height: 30px;
				border: 1px solid $bottom_color;
				border-radius: 5px;	
				margin-top: 10px;
			}
			.btn {
				width : 160px;
				height : 30px;
				margin-top: 10px;
			}
		}
		.account_certification_btm {
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