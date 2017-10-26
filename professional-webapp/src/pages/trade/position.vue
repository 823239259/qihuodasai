<template>
	<div id="trade_details">
		<table>
			<thead>
				<tr>
					<td>合约名称</td>
					<td>多空</td>
					<td>手数</td>
					<td>持仓均价</td>
					<td>浮动盈利</td>
				</tr>
			</thead>
			<tbody>
				<template v-for="v in positionListCont">
					<tr>
						<td>{{v.CommodityName}}</td>
						<td :class="v.type_color">{{v.type}}</td>
						<td>{{v.HoldNum}}</td>
						<td>{{v.price}}</td>
						<td :class="v.total_color">{{v.total}}</td>
					</tr>
				</template>
			</tbody>
		</table>
		<div class="tools">
			<button class="btn blue" @click="closePositionAll">全部平仓</button>
			<button class="btn blue" @click="closePosition">平仓</button>
			<button class="btn blue" @click="backTrade">反手</button>
			<button class="btn blue">止损止盈</button>
		</div>
	</div>
</template>

<script>
	import { mapMutations,mapActions } from 'vuex'
	export default{
		name: 'trade_details',
		data(){
			return{
//				positionList: [],
			}
		},
		computed: {
			orderTemplist(){
				return this.$store.state.market.orderTemplist;
			},
			qryHoldTotalArr(){
				return this.$store.state.market.qryHoldTotalArr;
			},
			positionListCont(){
				return this.$store.state.market.positionListCont;
			}
		},
		methods: {
			closePositionAll: function(){
				if(this.positionListCont.length > 0){
					layer.confirm('此次操作会平掉您持仓列表中所有的合约，请您慎重选择。确认平仓全部合约？', {
						btn: ['确定','取消']
					}, function(index){
						this.positionListCont.forEach(function(o,i){
							var Contract = o.ContractCode.substring(0, o.ContractCode.length-4);
							var b = {
								"Method": 'InsertOrder',
								"Parameters":{
									"ExchangeNo": this.qryHoldTotalArr[i].ExchangeNo,
									"CommodityNo": this.qryHoldTotalArr[i].CommodityNo,
									"ContractNo": this.qryHoldTotalArr[i].ContractNo,
									"OrderNum": this.qryHoldTotalArr[i].HoldNum,
									"Drection": drection,
									"PriceType": 1,
									"LimitPrice": 0.00,
									"TriggerPrice": 0,
									"OrderRef": this.$store.state.market.tradeConfig.client_source+ new Date().getTime()+(buildIndex++)
								}
							};
							this.tradeSocket.send(JSON.stringify(b));
						}.bind(this));
						layer.close(index);
					}.bind(this));
				}else{
					layer.msg('暂无合约需要平仓', {time: 1000});
				}
			},
			closePosition: function(){
				
			},
			backTrade: function(){
				
			},
			operateData: function(obj){
				this.$store.state.market.positionListCont = [];
				if(obj){
					obj.forEach(function(o, i){
						var data = {};
						data.CommodityName = this.orderTemplist[o.CommodityNo].CommodityName;
						data.type = function(){
							if(o.Drection == 0){
								return '多'
							}else{
								return '空'
							}
						}();
						data.HoldNum = o.HoldNum;
						data.price = o.HoldAvgPrice.toFixed(this.orderTemplist[o.CommodityNo].DotSize);
						data.total = 0;
						data.type_color = function(){
							if(o.Drection==0){
								return 'red'
							}else{
								return 'green'
							}
						}();
						data.total_color = 'green';
						data.commodityNocontractNo = this.orderTemplist[o.CommodityNo].LastQuotation.CommodityNo + this.orderTemplist[o.CommodityNo].LastQuotation.ContractNo;
						this.$store.state.market.positionListCont.unshift(data);
					}.bind(this));
				}
			}
		},
		mounted: function(){
			//获取持仓列表数据
			this.operateData(this.qryHoldTotalArr);
		}
	}
</script>

<style lang="scss" scoped>
	@import "../../assets/css/common.scss";
	#trade_details{
		height: 151px;
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
	.tools{
		position: fixed;
		bottom: 55px;
		left: 730px;
		margin: 15px 0 0 10px;
		.btn{
			width: 90px;
			height: 30px;
			line-height: 30px;
		}
	}
</style>