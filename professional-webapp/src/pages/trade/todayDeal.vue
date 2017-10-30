<template>
	<div id="trade_details">
		<table>
			<thead>
				<tr>
					<td>合约名称</td>
					<td>买卖</td>
					<td>成交价</td>
					<td>成交量</td>
					<td>成交时间</td>
				</tr>
			</thead>
			<tbody>
				<template v-for="v in dealList">
					<tr>
						<td>{{v.commodityName}}</td>
						<td>{{v.buyOrSell}}</td>
						<td>{{v.tradePrice}}</td>
						<td>{{v.tradeNum}}</td>
						<td>{{v.tradeDateTime}}</td>
					</tr>
				</template>
			</tbody>
		</table>
	</div>
</template>

<script>
	export default{
		name: 'trade_details',
		data(){
			return{
				dealList: [],
			}
		},
		computed: {
			orderTemplist(){
				return this.$store.state.market.orderTemplist;
			},
			OnRspQryTradeDealListCont(){
				return this.$store.state.market.OnRspQryTradeDealListCont;
			}
		},
		watch: {
			OnRspQryTradeDealListCont: function(n, o){
				if(n){
					//获取成交列表数据
					this.operateData(this.OnRspQryTradeDealListCont);
				}
			}
		},
		methods: {
			operateData: function(obj){
				this.dealList = [];
				if(obj){
					this.OnRspQryTradeDealListCont.forEach(function(o, i){
						var data = {};
						data.commodityName = this.orderTemplist[o.CommodityNo].CommodityName;
						data.buyOrSell = function(){
							if(o.Drection == 0){
								return '买';
							}else{
								return '卖';
							}
						}();
						data.tradePrice = o.TradePrice;
						data.tradeNum = o.TradeNum;
						data.tradeDateTime = o.TradeDateTime;
						data.ContractCode = o.ContractCode;
						data.OrderID = o.OrderID;
						this.dealList.unshift(data);
					}.bind(this));
				}
			}
		},
		mounted: function(){
			//获取成交列表数据
			this.operateData(this.OnRspQryTradeDealListCont);
		}
	}
</script>

<style lang="scss" scoped>
	@import "../../assets/css/common.scss";
	#trade_details{
		height: 190px;
		overflow-y: auto;
	}
	table{
		thead tr{
			height: 30px;
			background: $bottom_color;
		}
		td{
			padding: 0 10px;
		}
		tbody tr{
			height: 40px;
			border-bottom: 1px solid $bottom_color;
		}
	}
	@media only screen and (min-width: 1280px) and (max-width: 1366px) {
		#trade_details{
			width: 635px;
		}
	}
</style>