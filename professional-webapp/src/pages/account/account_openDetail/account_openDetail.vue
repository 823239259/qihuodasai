<template>
	<div id="account_openDetail">
				<div class="account_openDetail_top">
					<ul>
						<li>
							<span>今天</span>
							<span>一个月</span>
							<span>三个月</span>
							<span>一年</span>
							<span>起始时间</span>
							<span>2017-07-15</span>
							<span>></span>
							<span>2017-07-16</span>
						</li>
						<li>
							<span>全部</span>
							<span>开户中</span>
							<span>操盘中</span>
							<span>已结算</span>
						</li>
					</ul>
				</div>
				<div class="account_openDetail_center">
					<table>
						<thead>
							<tr>
								<td>交易品种</td>
								<td>状态</td>
								<td>初始保证金</td>
								<td>追加保证金</td>
								<td>总操盘资金</td>
								<td>亏损平仓线</td>
								<td>申请时间</td>
								<td>结算时间</td>
								<td>结算金额</td>
								<td>操作</td>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>国际综合</td>
								<td>开户中</td>
								<td>3000元</td>
								<td>-</td>
								<td>3000元</td>
								<td>5270美元</td>
								<td>2017-07-06</br>16:29:55</td>
								<td>-</td>
								<td>-</td>
								<td v-on:click="toOpenDetailTrade">追加保证金</td>
							</tr>
							<tr>
								<td>国际综合</td>
								<td>开户中</td>
								<td>3000元</td>
								<td>-</td>
								<td>3000元</td>
								<td>5270美元</td>
								<td>2017-07-06</br>16:29:55</td>
								<td>-</td>
								<td>-</td>
								<td v-on:click="toAdditionlMargin">查看账号</td>
							</tr>
							<tr>
								<td>国际综合</td>
								<td>开户中</td>
								<td>3000元</td>
								<td>-</td>
								<td>3000元</td>
								<td>5270美元</td>
								<td>2017-07-06</br>16:29:55</td>
								<td>-</td>
								<td>-</td>
								<td v-on:click="toParticulars">结算明细</td>
							</tr>
						</tbody>
					</table>
				</div>
				<div class="account_openDetail_notice">
					<p>注意：</p>
					<ul>
						<li>1.交易手续费=合约手续费x手数</li>
						<li>2.买卖各算一手</li>
						<li>3.结算金额=操盘保证金+补充保证金+交易盈亏-交易手续费</li>
					</ul>
				</div>
				<div class="account_openDetail_btm">
					<p>投资有风险，入市需谨慎</p>
				</div>
			</div>
</template>

<script>
	export default {
		name : "account_openDetail",
		data(){
			return{
				
			}
		},
		methods:{
			toOpenDetailTrade:function(){
				
			},
			toAdditionlMargin:function(){
				
			},
			toParticulars:function(){
				
			}
		}
	}
</script>

<style lang="scss" scoped type="text/css">
@import "../../../assets/css/common.scss";
	#account_openDetail{
			width: 100%;
			margin-top: 5px;
			.account_openDetail_top {
				height: 110px;
				background-color: $blue;
				li {
					&:nth-child(1){
						height: 70px;
						border-bottom: 1px solid $bottom_color;
						line-height: 70px;
						span {
							&:nth-child(1) {
								color: $yellow;
							}
						}
					}
					&:nth-child(2){
						height: 40px;
						line-height: 40px;
						span {
							&:nth-child(1) {
								color: $yellow;
							}
						}
					}
				}
			}
			.account_openDetail_center{
				background-color: $blue;
			}
			.account_openDetail_notice {
				height: 120px;
				background-color: $blue;
				p {
					color: $yellow;
					font-size: $fs14;
					padding-top: 20px;
				}
				li {
					margin-top: 10px;
					font-size: $fs12;
				}
			}
			.account_openDetail_btm{
				p {
					width: 100%;
					height: 40px;
					line-height: 40px;
					margin-top: 10px;
					background-color: $blue;
					text-align: center;
					font-size: $fs12;
				}
			}
		}
</style>