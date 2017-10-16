<template>
	<div id="index">
		<div class="order">
			<div class="title">
				<ul>
					<template></template>
					<li class="current">
						<span>国际期货</span>
					</li>
					<li>
						<span>国内期货</span>
					</li>
					<li>
						<span>添加外汇</span>
					</li>
				</ul>
			</div>
			<div class="cont">
				<table>
					<thead>
						<tr>
							<td></td>
							<td>名称</td>
							<td>合约代码</td>
							<td>最新</td>
							<td>现手</td>
							<td>买价</td>
							<td>卖价</td>
							<td>买量</td>
							<td>卖量</td>
							<td>成交量</td>
							<td>涨跌</td>
							<td>涨幅%</td>
							<td>持仓量</td>
							<td>开盘</td>
							<td>最高</td>
							<td>最低</td>
							<td>昨结算</td>
						</tr>
					</thead>
					<tbody>
						<template v-for="v in parameters">
							<tr>
								<td class="ifont_arrow" v-show="v.LastQuotation.LastPrice >= v.LastQuotation.PreSettlePrice"><i class="ifont" :class="{red: v.LastQuotation.LastPrice > v.LastQuotation.PreSettlePrice, green: v.LastQuotation.LastPrice < v.LastQuotation.PreSettlePrice}">&#xe761;</i></td>
								<td class="ifont_arrow" v-show="v.LastQuotation.LastPrice < v.LastQuotation.PreSettlePrice"><i class="ifont" :class="{red: v.LastQuotation.LastPrice > v.LastQuotation.PreSettlePrice, green: v.LastQuotation.LastPrice < v.LastQuotation.PreSettlePrice}">&#xe76a;</i></td>
								<td>{{v.CommodityName}}</td>
								<td>{{v.CommodityNo + v.MainContract}}</td>
								<td :class="{red: v.LastQuotation.LastPrice > v.LastQuotation.PreSettlePrice, green: v.LastQuotation.LastPrice < v.LastQuotation.PreSettlePrice}">{{v.LastQuotation.LastPrice}}</td>
								<td>{{v.LastQuotation.LastVolume}}</td>
								<td :class="{red: v.LastQuotation.BidPrice1 > v.LastQuotation.PreSettlePrice, green: v.LastQuotation.BidPrice1 < v.LastQuotation.PreSettlePrice}">{{v.LastQuotation.BidPrice1}}</td>
								<td :class="{red: v.LastQuotation.AskPrice1 > v.LastQuotation.PreSettlePrice, green: v.LastQuotation.AskPrice1 < v.LastQuotation.PreSettlePrice}">{{v.LastQuotation.AskPrice1}}</td>
								<td>{{v.LastQuotation.BidQty1}}</td>
								<td>{{v.LastQuotation.AskQty1}}</td>
								<td>{{v.LastQuotation.TotalVolume}}</td>
								<td :class="{green: v.LastQuotation.ChangeRate < 0, red: v.LastQuotation.ChangeRate > 0}">{{v.LastQuotation.ChangeValue | fixNum(v.DotSize)}}</td>
								<td :class="{green: v.LastQuotation.ChangeRate < 0, red: v.LastQuotation.ChangeRate > 0}">{{v.LastQuotation.ChangeRate | fixNumTwo}}%</td>
								<td>{{v.LastQuotation.Position}}</td>
								<td :class="{red: v.LastQuotation.OpenPrice > v.LastQuotation.PreSettlePrice, green: v.LastQuotation.OpenPrice < v.LastQuotation.PreSettlePrice}">{{v.LastQuotation.OpenPrice}}</td>
								<td :class="{red: v.LastQuotation.HighPrice > v.LastQuotation.PreSettlePrice, green: v.LastQuotation.HighPrice < v.LastQuotation.PreSettlePrice}">{{v.LastQuotation.HighPrice}}</td>
								<td :class="{red: v.LastQuotation.LowPrice > v.LastQuotation.PreSettlePrice, green: v.LastQuotation.LowPrice < v.LastQuotation.PreSettlePrice}">{{v.LastQuotation.LowPrice}}</td>
								<td>{{v.LastQuotation.PreSettlePrice}}</td>
							</tr>
						</template>
					</tbody>
				</table>
			</div>
		</div>
		<div class="echarts_box">
			<div class="title">
				<span class="fl">美黄金</span>
				<span class="fl">CG1708</span>
				<div class="add fr">
					<i class="ifont fl">&#xe600;</i>
					<span class="fl">添加自选</span>
				</div>
			</div>
			<div id="echarts_f">
				
			</div>
			<div id="echarts_k">
				
			</div>
		</div>
	</div>
</template>

<script>
	import { mapMutations,mapActions } from 'vuex'
	export default{
		name:'index',
		computed: {
//			quoteInitStatus(){
//				return this.$store.state.market.quoteInitStatus;
//			},
			parameters(){
				return this.$store.state.market.Parameters;
			},
			tradeLoginSuccessMsg(){
				return this.$store.state.market.tradeLoginSuccessMsg;
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
			
		}
	}
</script>

<style lang="scss" scoped>
	@import "../assets/css/common.scss";
	#index{
		position: relative;
	}
	.echarts_box{
		position: absolute;
		top: 0;
		right: 0;
		width: 400px;
		overflow: hidden;
		.title{
			height: 40px;
			line-height: 40px;
			padding: 0 10px;
			background: $bottom_color;
			span{
				font-size: $fs16;
				color: $white;
				&:nth-child(2){
					margin-left: 10px;
				}
			}
			.add{
				.ifont{
					color: $yellow;
					font-size: $fs16;
				}
				span{
					font-size: $fs12;
					color: $yellow;
					margin-left: 5px;
				}
			}
		}
		#echarts_f, #echarts_k{
			width: 400px;
		}
	}
	.order{
		width: 100%;
		overflow: hidden;
		padding-right: 410px;
		box-sizing: border-box;
		.title{
			height: 35px;
			ul{
				li{
					float: left;
					width: 120px;
					height: 35px;
					line-height: 35px;
					text-align: center;
					overflow: hidden;
					background: $deepblue;
					border-bottom: 5px solid $black;
					margin-right: 5px;
					cursor: pointer;
					&:hover, &.current{
						border-color: $deepblue;
						span{
							color: $yellow;
						}
					}
					
				}
			}
		}
		.cont{
			table{
				td.ifont_arrow{
					width: 32px;
					text-align: center;
					.ifont{
						font-size: $fs12;
					}
				}
				thead{
					tr{
						height: 30px;
						font-size: $fs12;
						background: $bottom_color;
					}
				}
				tbody{
					tr{
						height: 40px;
					}
				}
				
			}
		}
	}


</style>