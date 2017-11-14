<template>
	<div id="trade_details">
		<table>
			<thead>
				<tr>
					<td>币种</td>
					<td>昨结存</td>
					<td>今权益</td>
					<td>今可用</td>
					<td>保证金</td>
					<td>冻结资金</td>
					<td>逐笔浮盈</td>
					<td>平仓盈亏</td>
					<td>入金</td>
					<td>出金</td>
				</tr>
			</thead>
			<tbody class="table_box">
				<template v-for="v in moneyDetail">
					<tr>
						<td>{{v.CurrencyName}}</td>
						<td>{{v.OldAmount | fixNumTwo}}</td>
						<td>{{v.TodayBalance | fixNumTwo}}</td>
						<td>{{v.TodayCanUse | fixNumTwo}}</td>
						<td>{{v.Deposit | fixNumTwo}}</td>
						<td>{{v.FrozenMoney | fixNumTwo}}</td>
						<td>{{v.FloatingProfit | fixNumTwo}}</td>
						<td>{{v.CloseProfit | fixNumTwo}}</td>
						<td>{{v.InMoney | fixNumTwo}}</td>
						<td>{{v.OutMoney | fixNumTwo}}</td>
					</tr>
				</template>
			</tbody>
		</table>
	</div>
</template>

<script>
	import { mapMutations,mapActions } from 'vuex'
	export default{
		name: 'trade_details',
		computed: {
			moneyDetail: function(){
				return this.$store.state.market.CacheAccount.moneyDetail;
			},
			jCacheTotalAccount: function(){
				return this.$store.state.market.CacheAccount;
			}
		},
		filters:{
			fixNumTwo: function(num){
				return num.toFixed(2);
			},
			fixNum: function(num, dotsize){
				return num.toFixed(dotsize);
			}
		},
		methods: {
			
		},
		mounted: function(){
			this.moneyDetail.forEach(function(o, i){
				console.log(o);
				o.TodayBalance = o.TodayAmount;
				o.TodayCanUse = o.TodayAmount - o.Deposit - o.FrozenMoney
			}.bind(this));
		}
	}
</script>

<style lang="scss" scoped>
	@import "../../assets/css/common.scss";
	/*.table_box_head{
		padding-right: 1.5%;
	}*/
	/*.table_box{
		height: 180px;
		overflow-y: auto;
	}*/
	#trade_details{
		height: 190px;
		overflow-y: auto;
	}
	table{
		td{
			width: 10%;
			padding: 0 10px;
		}
		thead{
			width: 75%;
			tr{
				height: 30px;
				background: $bottom_color;
				td{
					width: 1%
					&:nth-child(3){
						/*padding-left: 5px;*/
					}
				}
			}
		}
		tbody{
			tr{
				height: 40px;
				border-bottom: 1px solid $bottom_color;
			}
		}
		
	}
	.tools{
		margin: 15px 0 0 10px;
		.btn{
			width: 90px;
			height: 30px;
			line-height: 30px;
		}
	}
	@media only screen and (min-width: 1280px) and (max-width: 1366px) {
		#trade_details{
			width: 635px;
			td{
				font-size: $fs12;
			}
		}
	}
</style>