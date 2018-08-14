<template>
	<div id="trade_details">
		<table>
			<thead>
				<tr>
					<td>合约名称</td>
					<td>状态</td>
					<td>买卖</td>
					<td>委托价</td>
					<td>委托量</td>
					<td>已成交</td>
					<td>已撤单</td>
					<td>下单时间</td>
					<td>状态说明</td>
				</tr>
			</thead>
			<tbody>
				<template v-for="v in entrustList">
					<tr>
						<td>{{v.commodityName}}</td>
						<td>{{v.commodityStatus}}</td>
						<td>{{v.buyOrSell}}</td>
						<td>{{v.delegatePrice}}</td>
						<td>{{v.delegateNum}}</td>
						<td>{{v.TradeNum}}</td>
						<td>{{v.RevokeNum}}</td>
						<td>{{v.InsertDateTime}}</td>
						<td>{{v.StatusMsg}}</td>
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
				entrustList: [],    //渲染委托列表数据
			}
		},
		computed: {
			orderTemplist(){
				return this.$store.state.market.orderTemplist;
			},
			OrderType(){
				return this.$store.state.market.OrderType;
			},
			OnRspOrderInsertEntrustCont(){
				return this.$store.state.market.OnRspOrderInsertEntrustCont;
			},
		},
		watch: {
			OnRspOrderInsertEntrustCont: function(n, o){
				if(n){
					//更新委托列表数据
					this.operateData(n);
				}
			}
		},
		methods: {
			operateData: function(obj){
				this.entrustList = [];
				if(obj){
					obj.forEach(function(o, i){
						var data = {};
						if(o.CommodityNo != ''){
							data.commodityName = this.orderTemplist[o.CommodityNo].CommodityName;
							data.commodityStatus = this.OrderType[o.OrderStatus];
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
									return parseFloat(o.OrderPrice).toFixed(this.orderTemplist[o.CommodityNo].DotSize);
								}
							}.bind(this)();
							data.delegateNum = o.OrderNum;
							data.TradeNum = o.TradeNum;
							data.RevokeNum = function(){
								if(o.OrderStatus == 4){
									return o.OrderNum - o.TradeNum;
								}else{
									return 0;
								}
							}();
							data.InsertDateTime = o.InsertDateTime;
							data.ContractCode = o.ContractCode;
							data.OrderID = o.OrderID;
							data.StatusMsg = o.StatusMsg;
							this.entrustList.unshift(data);
						}
					}.bind(this));
				}
			}
		},
		mounted: function(){
			//获取委托列表数据
			this.operateData(this.OnRspOrderInsertEntrustCont);
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
		table tr td{
			font-size: $fs12;
			padding: 0 4px;
		}
	}
</style>