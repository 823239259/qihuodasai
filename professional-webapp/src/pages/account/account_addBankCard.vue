<template>
	<div id="account_addBankCard">
		<div class="account_addBankCard_top">
			<p>姓名：<span>威力</span></p>
			<p>开户银行：<input type="text" /></p>
			<p>开户省份：<input type="text" /></p>
			<p>开户城市：<input type="text" /></p>
			<p>开户支行：<input type="text" /></p>
			<p>银行卡卡号：<input type="text" /></p>
			<p>确认卡号：<input type="text" /><span>(不支持存折)</span></p>
			<button class="btn yellow">确认</button>
			
		</div>
		<div class="account_addBankCard_btm">
			<p>新增/修改银行卡遇到问题</p>
			<ul>
				<li>绑定的银行卡不是自己的怎么办？</li>
				<li>答：建议绑定自己的银行卡，如有疑问请拨打客服热线。</li>
			</ul>
		</div>
	</div>
</template>

<script>
	export default {
		name : "account_addBankCard"
	}
</script>

<style lang="scss" scoped type="text/css">
	@import "../../assets/css/common.scss";
	#account_addBankCard {
		width: 100%;
		.account_addBankCard_top {
			width : 100%;
			height : 420px;
			background-color : $blue;
			p {
				margin-left: 40%;
				padding-top: 20px;
				span {
					font-size : $fs12;
				}
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
				margin-left: 50%;
				margin-top: 10px;
			}
		}
		.account_addBankCard_btm {
			width: 100%;
			height: 120px;
			p {
				width: 100%;
				height: 40px;
				background-color: $bottom_color;
				line-height: 40px;
				margin-top: 10px;
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
				}
			}
		}
	}
</style>