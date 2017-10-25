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
				<template v-for="v in orderList">
					<tr>
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
			<button class="btn blue">全撤</button>
			<button class="btn blue">撤单</button>
			<button class="btn blue">改单</button>
		</div>
	</div>
</template>

<script>
	export default{
		name: 'trade_details',
		data(){
			return{
				orderList: [],	
			}
		},
		computed: {
			orderTemplist(){
				return this.$store.state.market.orderTemplist;
			},
			OnRspOrderInsertOrderListCont(){
				return this.$store.state.market.OnRspOrderInsertOrderListCont;
			}
		},
		watch: {
			OnRspOrderInsertOrderListCont: function(n, o){
				if(n){
					//更新挂单列表数据
					this.operateData(n);
				}
			}
		},
		methods: {
			operateData: function(obj){
				this.orderList = [];
				if(obj){
					var data = {};
					obj.forEach(function(o, i){
						data.commodityName = this.orderTemplist[o.CommodityNo].CommodityName;
						data.buyOrSell = function(){
							if(o.Drection==0){
								return '买';
							}else{
								return '卖';
							}
						}();
						data.delegatePrice = function(){
							if(o.OrderPriceType==1){
								return '市价';
							}else{
								return o.OrderPrice;
							}
						}();
						data.delegateNum = o.OrderNum;
						data.ApplyOrderNum = o.OrderNum-e.TradeNum;
						data.InsertDateTime = o.InsertDateTime;
						data.ContractCode = o.ContractCode;
						data.OrderID = o.OrderID;
						this.orderList.unshift(data);
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
		height: 210px;
		position: relative;
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
		left: 0;
		margin: 15px 0 0 10px;
		.btn{
			width: 90px;
			height: 30px;
			line-height: 30px;
		}
	}
</style>