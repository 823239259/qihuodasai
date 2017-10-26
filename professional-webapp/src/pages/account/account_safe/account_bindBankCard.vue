<template>
	<div id="account_bindBankCard">
		<div class="account_bindBankCard_top">
			<ul>
				<li>
					<img src="../../../assets/images/icon_accountsafe.png" alt="logo_safe" />
				</li>
				<li>
					<p>您已绑定银行卡，可提现到银行卡中。</p>
					<p>以下为您绑定的银行卡</p>
				</li>
			</ul>
		</div>
		<div class="account_bindBankCard_center">
			<ul>
				<li>
					<input type="radio" name="radiobutton" value="radiobutton" checked> 
				</li>
				<li>
					<input type="radio" name="radiobutton" value="radiobutton" checked>  
				</li>
				<li>
					<input type="radio" name="radiobutton" value="radiobutton" checked>  
				</li>
				<li>
					<button class="btn yellow">添加银行卡</button>
				</li>
			</ul>
			
		</div>
		<div class="account_bindBankCard_btm">
			<p>投资有分析，入市需谨慎</p>
		</div>
	</div>
</template>

<script>
	export default {
		name : "safe_bindBankCard"
	}
</script>

<style lang="scss" scoped type="text/css">
	@import "../../../assets/css/common.scss";
	#account_bindBankCard {
		width: 100%;
		.account_bindBankCard_top {
			height : 150px;
			background-color : $blue;
			border-bottom : 1px solid $bottom_color;
			li {
				float: left;
				&:nth-child(1){
					margin-left: 30px;
					margin-top: 30px;
					
				}
				&:nth-child(2) {
					margin-top: 60px;
					margin-left: 20px;
					p {
						&:nth-child(1){
							color : $white;
							margin-bottom: 5px;
						}
					}
				}
			}
		}
		.account_bindBankCard_center {
			width: 100%;
			height: 160px;
			background-color: $blue;
			text-align: center;
			input {
				width: 400px;
				height: 30px;
				border: 1px solid $bottom_color;
				border-radius: 5px;
			}
			.btn {
				width: 160px;
				height: 30px;
			}
		}
		.account_bindBankCard_btm {
			p {
				width: 100%;
				height: 40px;
				text-align: center;
				background-color: $blue;
				margin-top: 10px;
				line-height: 40px;
			}
		}
	}
</style>