<template>
	<div id="account_withDraw">
		<div class="show_title">
			<ul>
				<li><p>提取余额到银行卡   <span>余额：<i>10.00元</i>（累计免费提现金额：1000元）</span></p></li>
				<li>添加银行卡</li>
				<li>提现记录</li>
			</ul>
		</div>
	</div>
</template>

<script>
	export default {
		name : "account_withDraw"
	}
</script>

<style lang="scss" scoped type="text/css">
	@import "../../assets/css/common.scss";
	#account_withDraw {
		width : 80%;
		.show_title {
			position : fixed;
			width:1000px;
			li {
				&:nth-child(1){
					float: left;
				}
				&:nth-child(2){
					float: right;
				}
				&:nth-child(3){
					float: right;
				}
				
			}
		}
	}
</style>