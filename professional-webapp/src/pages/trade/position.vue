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
			<button class="btn blue" @click="backTrade">反手</button>
			<button class="btn blue" @click="stopMoney">止损止盈</button>
		</div>
		<stopMoney ref="stopMoney" :value="selectedMsgs"></stopMoney>
	</div>
</template>

<script>
	import stopMoney from './stopMoney.vue'
	export default{
		name: 'trade_details',
		components: {stopMoney},
		data(){
			return{
				selectedNum: -1,
				currentOrderID: '',
				selectedMsg: {},
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
			},
			stopStatus(){
				return this.$store.state.market.stopStatus;
			}
			jCacheTotalAccount(){
				return this.$store.state.market.CacheAccount.jCacheTotalAccount;
			},
			selectedMsgs(){
				return JSON.stringify(this.selectedMsg);
			}
		},
		watch: {
			qryHoldTotalArr: function(n, o){
				//获取持仓列表数据
				this.operateData(n);
			},
			positionListCont: function(n, o){
				if(n.length == 0){
					this.currentOrderID = '';
					this.selectedNum = -1;
				}
			}
		},
		methods: {
			clickEvent: function(i, id){
				this.selectedNum = i;
				this.currentOrderID = id;
			},
			closePositionAll: function(){
				if(this.positionListCont.length > 0){
					layer.confirm('此次操作会平掉您持仓列表中所有的合约，请您慎重选择。确认平仓全部合约？', {
						btn: ['确定','取消']
					}, function(index){
						if(this.buyStatus == true) return;
						this.$store.state.market.buyStatus = true;
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
						}.bind(this));
						layer.close(index);
					}.bind(this));
				}else{
					layer.msg('暂无合约需要平仓', {time: 1000});
				}
			},
			closePosition: function(){
				var confirmText;
				if(this.currentOrderID != ''){
					this.positionListCont.forEach(function(o,i){
						if(this.currentOrderID == o.commodityNocontractNo){
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
							confirmText = '提交订单:【'+ o.commodityNocontractNo +'】,价格【市价】,手数【'+ o.HoldNum +'】？';
							layer.confirm(confirmText, {
								btn: ['确定','取消']
							}, function(index){
								if(this.buyStatus == true) return;
								this.$store.state.market.buyStatus = true;
								this.tradeSocket.send(JSON.stringify(b));
								this.currentOrderID = '';
								this.selectedNum = -1;
								layer.close(index);
							}.bind(this));
						}
					}.bind(this));
				}else{
					layer.msg('请选择一条数据', {time: 1000});
				}
			},
			backTrade: function(){
				var confirmText;
				if(this.currentOrderID != ''){
					this.positionListCont.forEach(function(o,i){
						if(this.currentOrderID == o.commodityNocontractNo){
							if(o.price > this.jCacheTotalAccount.TodayCanUse){
								layer.msg('当前余额不足，反手操作失败', {time: 1000});
								return;
							}
							var buildIndex = 0;
							if(buildIndex > 100) buildIndex = 0;
							var Contract = o.commodityNocontractNo.substring(0, o.commodityNocontractNo.length-4);
							var drection, _drection;
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
							confirmText = '确定反手:【'+ o.commodityNocontractNo +'】,价格【市价】,手数【'+ o.HoldNum +'】？';
							layer.confirm(confirmText, {
								btn: ['确定','取消']
							}, function(index){
								if(this.buyStatus == true) return;
								this.$store.state.market.buyStatus = true;
								this.tradeSocket.send(JSON.stringify(b));
								setTimeout(function(){
									this.tradeSocket.send(JSON.stringify(b));
									this.currentOrderID = '';
									this.selectedNum = -1;
								}.bind(this), 500);
								layer.close(index);
							}.bind(this));
						}
					}.bind(this));
				}else{
					layer.msg('请选择一条数据', {time: 1000});
				}
			},
			stopMoney: function(){
				if(this.currentOrderID != ''){
					this.$refs.stopMoney.show = true;
					var dotSize;
					this.qryHoldTotalArr.forEach(function(o, i){
						if(this.currentOrderID == o.ContractCode){
							dotSize = this.orderTemplist[o.CommodityNo].DotSize;
							this.selectedMsg = {
								CommodityNo: o.CommodityNo,
								ContractCode: o.ContractCode,
								CommodityName: this.orderTemplist[o.CommodityNo].CommodityName,
								HoldNum: o.HoldNum,
								stopPrice: parseFloat(o.OpenAvgPrice).toFixed(dotSize),
								HoldAvgPrice: parseFloat(o.HoldAvgPrice).toFixed(dotSize)
							}
							o.Drection == 0 ? this.selectedMsg.Drection = '多' : this.selectedMsg.Drection = '空';
						}
					}.bind(this));
				}else{
					layer.msg('请选择一条数据', {time: 1000});
				}
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
	@media only screen and (min-width: 1280px) and (max-width: 1366px) {
		#trade_details{
			width: 635px;
			td{
				font-size: $fs12;
			}
		}
	}
</style>