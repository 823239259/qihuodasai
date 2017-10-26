<template>
	<div id="account_resetCellPassword">
		<div class="account_resetCellPassword_top">
			<p>您正在为账户<span>182****222</span>修改绑定手机</p>
		</div>
		<div class="account_resetCellPassword_center">
			<p>旧手机号码：<span>182****222</span></p>
			<p>短信验证码：<input type="text" /></p>
			<p>新手机号码：<input type="text" /></p>
			<p>短信验证码:<input type="text" /></p>
			<button class="btn yellow">确认</button>
		</div>
		<div class="account_resetCellPassword_btm">
			<p>绑定手机遇到问题</p>
			<ul>
				<li>更改手机绑定收不到验证码怎么办？</li>
				<li>答：建议把手机放置在信号好的地点或者检查手机号码输入是否有误，如有疑问请拨打客服热线。</li>
			</ul>
			<p>投资有风险，入市需谨慎</p>
		</div>
	</div>
</template>

<script>
	export default {
		name : "safe_resetCellPhone"
	}
</script>


<style lang="scss" scoped type="text/css">
	@import "../../../assets/css/common.scss";
	#account_resetCellPassword {
		width: 100%;
		.account_resetCellPassword_top{
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
		.account_resetCellPassword_center {
			width: 100%;
			height: 290px;
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
		.account_resetCellPassword_btm {
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