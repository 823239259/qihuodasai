<template>
	<div id="trade_details">
		<table>
			<thead>
				<tr>
					<td>合约名称</td>
					<td>买卖</td>
					<td>委托价</td>
					<td>委托量</td>
					<td>挂单量</td>
					<td>挂单时间</td>
				</tr>
			</thead>
			<tbody>
				<template v-for="(v, index) in orderListCont">
					<tr :class="{current: selectedNum == index}" @click="clickEvent(index, v.OrderID, v.delegatePrice, v.delegateNum)">
						<td>{{v.commodityName}}</td>
						<td>{{v.buyOrSell}}</td>
						<td>{{v.delegatePrice}}</td>
						<td>{{v.delegateNum}}</td>
						<td>{{v.ApplyOrderNum}}</td>
						<td>{{v.InsertDateTime}}</td>
					</tr>
				</template>
			</tbody>
		</table>
		<div class="tools">
			<button class="btn blue" @click="cancelOrderAll">全撤</button>
			<button class="btn blue" @click="cancelOrder">撤单</button>
			<button class="btn blue" @click="editOrder">改单</button>
		</div>
		<div class="dialog_edit_order" id="edit_order" v-show="showDialog">
			<div class="edit_order">
				<div class="row">
					<label>委托价格：</label>
					<input type="text" v-model="entrustPrice" />
				</div>
				<div class="row">
					<label>委托数量：</label>
					<input type="text" v-model="entrustNum" />
				</div>
			</div>
		</div>
	</div>
</template>

<script>
	export default{
		name: 'trade_details',
		data(){
			return{
				selectedNum: -1,
				currentOrderID: '',
				showDialog: false,
				entrustPrice: '',
				entrustNum: '',
//				orderList: [],	
			}
		},
		computed: {
			orderTemplist(){
				return this.$store.state.market.orderTemplist;
			},
			templateList(){
				return this.$store.state.market.templateList;
			},
			OnRspOrderInsertOrderListCont(){
				return this.$store.state.market.OnRspOrderInsertOrderListCont;
			},
			orderListCont(){
				return this.$store.state.market.orderListCont;
			},
			tradeSocket(){
				return this.$store.state.tradeSocket;
			},
			cancelStatus(){
				return this.$store.state.market.cancelStatus;
			}
		},
		watch: {
			OnRspOrderInsertOrderListCont: function(n, o){
				if(n){
					//更新挂单列表数据
					this.operateData(n);
				}
			},
			orderListCont: function(n, o){
				layer.closeAll();
				this.showDialog = false;
				this.currentOrderID = '';
				this.selectedNum = -1;
			}
		},
		methods: {
			clickEvent: function(i, id, price, num){
				this.selectedNum = i;
				this.currentOrderID = id;
				this.entrustPrice = price;
				this.entrustNum = num;
			},
			cancelOrderAll: function(){
				if(this.orderListCont.length > 0){
					layer.confirm('此次操作会撤掉您委托列表中所有的合约，请您慎重选择。确认撤单全部合约？', {
						btn: ['确定','取消']
					}, function(index){
						if(this.cancelStatus == true) return;
						this.$store.state.market.cancelStatus = true;
						this.orderListCont.forEach(function(o,i){
							var Contract = o.ContractCode.substring(0, o.ContractCode.length-4);
							var b = {
								"Method": 'CancelOrder',
								"Parameters":{
									"OrderSysID": '',
									"OrderID": o.OrderID,
									"ExchangeNo": this.orderTemplist[Contract].ExchangeNo,
									"CommodityNo": this.templateList[Contract].CommodityNo,
									"ContractNo": this.templateList[Contract].ContractNo,
									"OrderNum": parseFloat(o.delegateNum),
									"Direction": function(){
													if(o.buyOrSell=='买'){
														return 0;
													}else{
														return 1;
													}
												},
									"OrderPrice": parseFloat(o.delegatePrice)
								}
							};
							this.tradeSocket.send(JSON.stringify(b));
						}.bind(this));
						layer.close(index);
					}.bind(this));
				}else{
					layer.msg('暂无合约需要撤单', {time: 1000});
				}
			},
			cancelOrder: function(){
				var confirmText;
				if(this.currentOrderID != ''){
					this.orderListCont.forEach(function(o,i){
						if(this.currentOrderID == o.OrderID){
							var Contract = o.ContractCode.substring(0, o.ContractCode.length-4);
							var b = {
								"Method": 'CancelOrder',
								"Parameters":{
									"OrderSysID": '',
									"OrderID": o.OrderID,
									"ExchangeNo": this.orderTemplist[Contract].ExchangeNo,
									"CommodityNo": this.templateList[Contract].CommodityNo,
									"ContractNo": this.templateList[Contract].ContractNo,
									"OrderNum": parseFloat(o.delegateNum),
									"Direction": function(){
													if(o.buyOrSell=='买'){
														return 0;
													}else{
														return 1;
													}
												},
									"OrderPrice": parseFloat(o.delegatePrice)
								}
							};
							confirmText = '提交撤单:【'+ o.ContractCode +'】,价格【'+ o.delegatePrice +'】,手数【'+ o.delegateNum +'】,方向【'+ o.buyOrSell +'】？';
							layer.confirm(confirmText, {
								btn: ['确定','取消']
							}, function(index){
								if(this.cancelStatus == true) return;
								this.$store.state.market.cancelStatus = true;
								this.tradeSocket.send(JSON.stringify(b));
								layer.close(index);
							}.bind(this));
						}
					}.bind(this));
				}else{
					layer.msg('请选择一条数据', {time: 1000});
				}
			},
			editOrder: function(){
				if(this.currentOrderID != ''){
					this.showDialog = true;
					layer.open({
						type: 1,
						title: '改单',
						content: $("#edit_order"),
						btn: ['确定','取消'],
						btn1: function(index){
							if(this.entrustPrice == '' || this.entrustPrice == 0){
								layer.msg('委托价格不能为空或者0',{time: 1000});
							}else if(this.entrustPrice < 0){
								layer.msg('委托价格不能为负',{time: 1000});
							}else if(this.entrustNum == '' || this.entrustNum == 0){
								layer.msg('委托数量不能为空或者0',{time: 1000});
							}else if(this.entrustNum < 0){
								layer.msg('委托数量不能为负',{time: 1000});
							}else{
								if(this.cancelStatus == true) return;
								this.$store.state.market.cancelStatus = true;
								this.orderListCont.forEach(function(o,i){
									if(this.currentOrderID == o.OrderID){
										var Contract = o.ContractCode.substring(0, o.ContractCode.length - 4);
										var b = {
											"Method": 'ModifyOrder',
											"Parameters":{
												"OrderSysID": '',
												"OrderID": o.OrderID,
												"ExchangeNo": this.orderTemplist[Contract].ExchangeNo,
												"CommodityNo": this.templateList[Contract].CommodityNo,
												"ContractNo": this.templateList[Contract].ContractNo,
												"OrderNum": parseFloat(this.entrustNum),
												"Direction": function(){
																if(o.buyOrSell=='买'){
																	return 0;
																}else{
																	return 1;
																}
															},
												"OrderPrice": parseFloat(this.entrustPrice),
												"TriggerPrice":0
											}
										};
										this.tradeSocket.send(JSON.stringify(b));
									}
								}.bind(this));
							}
							layer.close(index);
							this.showDialog = false;
						}.bind(this),
						cancel: function(){
							this.showDialog = false;
						}.bind(this)
					});
				}else{
					layer.msg('请选择一条数据', {time: 1000});
				}
			},
			operateData: function(obj){
				this.$store.state.market.orderListCont = [];
				if(obj){
					obj.forEach(function(o, i){
						var data = {};
						data.commodityName = this.orderTemplist[o.CommodityNo].CommodityName;
						data.buyOrSell = function(){
							if(o.Drection == 0){
								return '买';
							}else{
								return '卖';
							}
						}();
						data.delegatePrice = function(){
							if(o.OrderPriceType == 1){
								return '市价';
							}else{
								return o.OrderPrice;
							}
						}();
						data.delegateNum = o.OrderNum;
						data.ApplyOrderNum = o.OrderNum-o.TradeNum;
						data.InsertDateTime = o.InsertDateTime;
						data.ContractCode = o.ContractCode;
						data.OrderID = o.OrderID;
						this.$store.state.market.orderListCont.unshift(data);
					}.bind(this));
				}
			}
		},
		mounted: function(){
			//获取挂单列表数据
			this.operateData(this.OnRspOrderInsertOrderListCont);
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
	.edit_order{
		overflow: hidden;
		margin-bottom: 5px;
		.row{
			width: 100%;
			padding: 0 80px;
			margin-top: 20px;
			label{
				height: 30px;
				line-height: 30px;
			}
			input{
				width: 90px;
				height: 28px;
				line-height: 28px;
				border: 1px solid $highLight;
				border-radius: 4px;
				color: $white;
				padding: 0 5px;
			}
		}
	}
	@media only screen and (min-width: 1280px) and (max-width: 1366px) {
		#trade_details{
			width: 635px;
		}
	}
</style>