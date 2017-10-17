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
						<template v-for="(v,index) in Parameters">
							<tr :class="{current: current == index}" @click="toggle(index, v.CommodityName, v.CommodityNo, v.MainContract, v.ExchangeNo)">
								<td class="ifont_arrow" v-show="v.LastQuotation.LastPrice >= v.LastQuotation.PreSettlePrice"><i class="ifont" :class="{red: v.LastQuotation.LastPrice > v.LastQuotation.PreSettlePrice, green: v.LastQuotation.LastPrice < v.LastQuotation.PreSettlePrice}">&#xe761;</i></td>
								<td class="ifont_arrow" v-show="v.LastQuotation.LastPrice < v.LastQuotation.PreSettlePrice"><i class="ifont" :class="{red: v.LastQuotation.LastPrice > v.LastQuotation.PreSettlePrice, green: v.LastQuotation.LastPrice < v.LastQuotation.PreSettlePrice}">&#xe76a;</i></td>
								<td>{{v.CommodityName}}</td>
								<td>{{v.CommodityNo + v.MainContract}}</td>
								<td class="price" :class="{red: v.LastQuotation.LastPrice > v.LastQuotation.PreSettlePrice, green: v.LastQuotation.LastPrice < v.LastQuotation.PreSettlePrice}">{{v.LastQuotation.LastPrice}}</td>
								<td>{{v.LastQuotation.LastVolume}}</td>
								<td class="price" :class="{red: v.LastQuotation.BidPrice1 > v.LastQuotation.PreSettlePrice, green: v.LastQuotation.BidPrice1 < v.LastQuotation.PreSettlePrice}">{{v.LastQuotation.BidPrice1}}</td>
								<td class="price" :class="{red: v.LastQuotation.AskPrice1 > v.LastQuotation.PreSettlePrice, green: v.LastQuotation.AskPrice1 < v.LastQuotation.PreSettlePrice}">{{v.LastQuotation.AskPrice1}}</td>
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
				<span class="fl">{{orderName}}</span>
				<span class="fl">{{orderNum}}</span>
				<div class="add fr">
					<i class="ifont fl">&#xe600;</i>
					<span class="fl">添加自选</span>
				</div>
			</div>
			<div id="chart_fens" v-if="showFens">
				<div id="fens" style="width: 100%; height: 300px; margin: 0 auto;">
					
				</div>
				<div id="volume" style="width: 100%; height: 200px; margin: 0 auto;">
					
				</div>
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
		data(){
			return{
				current: 0,
				showFens: false,
				obj: {
					id1: 'fens',
					id2: 'volume',
					
				},
				orderName: '',
				orderNum: ''
			}
		},
		computed: {
			quoteInitStatus(){
				return this.$store.state.market.quoteInitStatus;
			},
			Parameters(){
				return this.$store.state.market.Parameters;
			},
			quoteInitStep(){
				return this.$store.state.market.quoteInitStep;
			},
			tradeLoginSuccessMsg(){
				return this.$store.state.market.tradeLoginSuccessMsg;
			},
			quoteSocket(){
				return this.$store.state.quoteSocket;
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
		watch: {
			quoteInitStep: function(n, o){
				if(n && n == true){
					this.$store.state.market.currentdetail = this.Parameters[0];
					this.showFens = true;
					this.orderName = this.Parameters[0].CommodityName;
					this.orderNum = this.Parameters[0].CommodityNo + this.Parameters[0].MainContract;
					var b = {
						Method: "QryHistory",
						Parameters:{
							ExchangeNo: this.Parameters[0].ExchangeNo,
							CommodityNo: this.Parameters[0].CommodityNo,
							ContractNo: this.Parameters[0].MainContract,
							HisQuoteType: 0,
							BeginTime: "",
							EndTime: "",
							Count: 0
						}
					};
					this.quoteSocket.send(JSON.stringify(b));
					this.$store.state.market.currentNo = this.Parameters[0].CommodityNo;
				}
			}
		},
		methods: {
			...mapActions([
				'initQuoteClient'
			]),
			toggle: function(i, name, commodityNo, mainContract, exchangeNo){
				this.current = i;
				this.orderName = name;
				this.orderNum = commodityNo + mainContract;
				var b = {
					Method: "QryHistory",
					Parameters:{
						ExchangeNo: exchangeNo,
						CommodityNo: commodityNo,
						ContractNo: mainContract,
						HisQuoteType: 0,
						BeginTime: "",
						EndTime: "",
						Count: 0
					}
				};
				this.quoteSocket.send(JSON.stringify(b));
			}
		},
		mounted: function(){
			//初始化行情
			if(this.quoteInitStatus == false){
				this.initQuoteClient();
				this.$store.state.market.quoteInitStatus = true;
			}
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
						color: $lightblue;
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
				.price{
					min-width: 50px;
				}
			}
		}
	}


</style>