<template>
	<div id="account_withDraw">
		<div class="account_withDraw_top">
			<ul>
				<li><p>提取余额到银行卡   <span>余额：<i>10.00元</i>（累计免费提现金额：1000元）</span></p></li>
				<li>添加银行卡</li>
				<li>提现记录</li>
			</ul>
		</div>
		<div class="account_withDraw_btm">
			<ul>
				<li>相关提示</li>
				<li>大额充值资金无法一次性提现出去怎么办？<p>答：建议使用充值退回功能。</p></li>
				<li>最快：<span>10</span>分钟到账
					<p>1、到账时间：工作日09:00--16:30申请，24小时到账，最快5分钟到账。其余时间申请，将在下一个工作日到账。</p>
					<p>2、提现手续费： 1%（适用于充值后，未实际操盘用户） 0元（适用于操盘提现用户提现）。</p>
					<p>3、提现处理时间：每个工作日固定时间进行提现处理。具体时间为：10:00，14:00，17:30</p>
				</li>
				<li>支持银行多达 <span>15</span>家
				<p>推荐您使用工商银行、建设银行、农业银行提现，到账最快</p>
				</li>
				<li><span>温馨提示：</span>禁止洗钱、信用卡套现、虚假交易等行为，一经发现并确认，将终止该用户使用</li>
			</ul>
		</div>
		<div class="btm">
			<p>投资有风险，入市需谨慎</p>
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
		width: 1000px;
		background-color: $black;
		.account_withDraw_top {
			li {
				width : 100%;
			}
		}
		.account_withDraw_btm {
			width: 100%;
			background-color: $blue;
			li {
				font-size: $fs14;
				color: $lightblue;
				text-indent:10px; 
				&:nth-child(1) {
					color: $white;
					height: 40px;
					line-height: 40px;
					
				}
				&:nth-child(2) {
					padding-top: 20px;
					height: 80px;
					color: $lightblue;
					p {
						color: $white;
						font-size: $fs12;
						padding-top: 10px;
					}
				}
				&:nth-child(3) {
					padding-top: 20px;
					height: 140px;
					border-bottom: 1px solid $bottom_color;
					border-top: 1px solid $bottom_color;
					span {
						font-size: $fs18;
						color: $white;
						font-weight: 800;
						margin: 0 6px;
					}
					p {
						padding-top: 10px;
					}
				}
				&:nth-child(4) {
					height: 80px;
					padding-top: 20px;
					border-bottom: 1px solid $bottom_color;
					span {
						font-size: $fs18;
						color: $white;
						font-weight: 800;
						margin: 0 5px;
					}
					p {
						font-size: $fs12;
						padding-top: 10px;
					}
				}
				&:nth-child(5) {
					height: 60px;
					line-height: 60px;
					span {
						color: $yellow;	
					}
				}
			}
		}
		.btm {
			line-height: 40px;
			text-align: center;
			height: 40px;
			margin-top: 10px;
			width: 100%;
			background-color: $blue;
			font-size: $fs12;
		}
	}
</style>