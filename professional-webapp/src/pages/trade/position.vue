<template>
	<div id="trade_details">
		<div class="trade_details_box">
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
					<template v-for="(v, index) in positionListCont">
						<tr :class="{current: selectedNum == index}" @click="clickEvent(index, v.commodityNocontractNo)">
							<td>{{v.CommodityName}}</td>
							<td :class="v.type_color">{{v.type}}</td>
							<td>{{v.HoldNum}}</td>
							<td>{{v.price}}</td>
							<td :class="v.total_color">{{v.total}}</td>
						</tr>
					</template>
				</tbody>
			</table>
		</div>
		<div class="tools">
			<button class="btn blue" @click="closePositionAll">全部平仓</button>
			<button class="btn blue" @click="closePosition">平仓</button>
			<!--<button class="btn blue" @click="backTrade">反手</button>
			<button class="btn blue">止损止盈</button>-->
		</div>
	</div>
</template>

<script>
	import { mapMutations,mapActions } from 'vuex'
	export default{
		name: 'trade_details',
		data(){
			return{
				selectedNum: -1,
				currentOrderID: '',
//				positionList: [],
			}
		},
		computed: {
			orderTemplist(){
				return this.$store.state.market.orderTemplist;
			},
			templateList(){
				return this.$store.state.market.templateList;
			},
			qryHoldTotalArr(){
				return this.$store.state.market.qryHoldTotalArr;
			},
			positionListCont(){
				return this.$store.state.market.positionListCont;
			},
			tradeSocket(){
				return this.$store.state.tradeSocket;
			},
			buyStatus(){
				return this.$store.state.market.buyStatus;
			}
		},
		methods: {
			clickEvent: function(i, id){
				this.selectedNum = i;
				this.currentOrderID = id;
			},
			closePositionAll: function(){
				if(this.buyStatus == true) return;
				if(this.positionListCont.length > 0){
					layer.confirm('此次操作会平掉您持仓列表中所有的合约，请您慎重选择。确认平仓全部合约？', {
						btn: ['确定','取消']
					}, function(index){
						this.positionListCont.forEach(function(o,i){
							var buildIndex = 0;
							if(buildIndex > 100) buildIndex = 0;
							var Contract = o.commodityNocontractNo.substring(0, o.commodityNocontractNo.length-4);
							var drection;
							o.type == '多' ? drection = 1 : drection = 0;
							var b = {
								"Method": 'InsertOrder',
								"Parameters":{
									"ExchangeNo": this.orderTemplist[Contract].ExchangeNo,
									"CommodityNo": this.templateList[Contract].CommodityNo,
									"ContractNo": this.templateList[Contract].ContractNo,
									"OrderNum": o.HoldNum,
									"Drection": drection,
									"PriceType": 1,
									"LimitPrice": 0.00,
									"TriggerPrice": 0,
									"OrderRef": this.$store.state.market.tradeConfig.client_source+ new Date().getTime()+(buildIndex++)
								}
							};
							this.tradeSocket.send(JSON.stringify(b));
							this.$store.state.market.buyStatus = true;
						}.bind(this));
						layer.close(index);
					}.bind(this));
				}else{
					layer.msg('暂无合约需要平仓', {time: 1000});
				}
			},
			closePosition: function(){
				if(this.buyStatus == true) return;
				var confirmText;
				if(this.currentOrderID != ''){
					this.positionListCont.forEach(function(o,i){
						if(this.currentOrderID == o.commodityNocontractNo){
							var buildIndex = 0;
							if(buildIndex > 100) buildIndex = 0;
							console.log(o.commodityNocontractNo);
							var Contract = o.commodityNocontractNo.substring(0, o.commodityNocontractNo.length-4);
							var drection;
							o.type == '多' ? drection = 1 : drection = 0;
							var b = {
								"Method": 'InsertOrder',
								"Parameters":{
									"ExchangeNo": this.orderTemplist[Contract].ExchangeNo,
									"CommodityNo": this.templateList[Contract].CommodityNo,
									"ContractNo": this.templateList[Contract].ContractNo,
									"OrderNum": o.HoldNum,
									"Drection": drection,
									"PriceType": 1,
									"LimitPrice": 0.00,
									"TriggerPrice": 0,
									"OrderRef": this.$store.state.market.tradeConfig.client_source+ new Date().getTime()+(buildIndex++)
								}
							};
							confirmText = '提交订单:【'+ o.commodityNocontractNo +'】,价格【市价】,手数【'+ o.HoldNum +'】？';
							layer.confirm(confirmText, {
								btn: ['确定','取消']
							}, function(index){
								this.tradeSocket.send(JSON.stringify(b));
								this.$store.state.market.buyStatus = true;
								layer.close(index);
							}.bind(this));
						}
					}.bind(this));
				}else{
					layer.msg('请选择一条数据', {time: 1000});
				}
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
		overflow: auto;
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
		position: absolute;
		bottom: 10px;
		left: 10px;
		z-index: 2;
		.btn{
			width: 90px;
			height: 30px;
			line-height: 30px;
		}
	}
</style>