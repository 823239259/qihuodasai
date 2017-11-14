<template>
	<div id="trade_details">
		<div class="condition">
			<template v-for="(v, index) in conditionList">
				<span :class="{current: selectedNum == index}" @click="conditionSearch(index)">{{v}}</span>
			</template>
			<em>起始时间</em>
			<div class="time">
				<input type="text" readonly="readonly" class="fl startTime" :value="startTime" />
				<i class="ifont fl">&#xe690;</i>
				<input type="text" readonly="readonly" class="fr endTime" :value="endTime" />
			</div>
			<button class="btn blue" @click="serchEvent">搜索</button>
		</div>
		<table>
			<thead>
				<tr>
					<td>序号</td>
					<td>合约代码</td>
					<td>交易所</td>
					<td>币种</td>
					<td>买卖</td>
					<td>成交价</td>
					<td>成交量</td>
					<td>手续费</td>
					<td>成交时间</td>
				</tr>
			</thead>
		</table>
		<div class="table_box">
			<table>
				<tbody>
					<template v-for="v in histroyDealList">
						<tr>
							<td>{{v.index}}</td>
							<td>{{v.CommodityNoContractNo}}</td>
							<td>{{v.ExchangeNo}}</td>
							<td>{{v.CurrencyNo}}</td>
							<td>{{v.Drection}}</td>
							<td>{{v.TradePrice}}</td>
							<td>{{v.TradeNum}}</td>
							<td>{{v.TradeFee}}</td>
							<td>{{v.TradeDateTime}}</td>
						</tr>
					</template>
				</tbody>
			</table>
		</div>
	</div>
</template>

<script>
	import pro from '../../assets/js/common.js'
	export default{
		name: 'trade_details',
		data(){
			return{
				startTime: '',
				endTime: '',
				conditionList: ['今天','7天','30天'],
				selectedNum: 0,
				histroyDealList: [],
			}
		},
		computed: {
			queryHisList(){
				return this.$store.state.market.queryHisList;
			},
			orderTemplist(){
				return	this.$store.state.market.orderTemplist;
			},
			tradeSocket(){
				return this.$store.state.tradeSocket;
			},
		},
		methods: {
			operateData: function(){
				this.queryHisList.forEach(function(o, i){
					var data = {};
					data.index = i;
					data.CommodityNoContractNo = o.ContractCode;
					data.ExchangeNo = o.ExchangeNo;
					data.CurrencyNo = o.CurrencyNo;
					data.Drection =(function(){
						if(o.Drection==0){
							return '买';
						}else{
							return '卖';
						}
					})();
					data.TradePrice = parseFloat(o.TradePrice).toFixed(this.orderTemplist[o.CommodityNo].DotSize);
					data.TradeNum = o.TradeNum;
					data.TradeFee = o.TradeFee;
					data.TradeDateTime = o.TradeDateTime;
					this.histroyDealList.push(data);
				}.bind(this));
			},
			serchEvent: function(){
				this.selectedNum = -1;
				this.startTime = $(".startTime").val();
				this.endTime = $(".endTime").val();
				var beginTime = $(".startTime").val() + ' 00:00:00';
				var t =  Date.parse(new Date(this.endTime)) + 86400000;
				var endTime = pro.getDate("y-m-d", t) + ' 00:00:00';
				this.histroyDealList = [];
				this.$store.state.market.queryHisList = [];
				this.tradeSocket.send('{"Method":"QryHisTrade","Parameters":{"ClientNo":"'+JSON.parse(localStorage.tradeUser).username	+'","BeginTime":"'+beginTime+'","EndTime":"'+endTime+'"}}');
				setTimeout(function(){
					this.operateData();
				}.bind(this),500);
			},
			conditionSearch: function(index){
				var date = new Date();
				var time = pro.getDate("y-m-d", date.getTime()).split(' ')[0];
				this.startTime = time;
				this.endTime = time;
				this.selectedNum = index;
				if(index == 0){ //今天
					this.histroyDealList = [];
					var date = new Date();
					date.setDate(date.getDate() - 1);
		    		var year = date.getFullYear();
		    		var day = date.getDate() > 9 ? date.getDate() : "0" + date.getDate();
		    		var month = (date.getMonth() + 1) > 9 ? (date.getMonth() + 1) : "0"+ (date.getMonth() + 1);
		    		var beginTime = year + '/' + month + '/' + day+' 00:00:00';
		    		
		    		var date00 = new Date(); 
		    		date00.setDate(date00.getDate() + 1);
		    		var year00 = date00.getFullYear();
		    		var day00 = date00.getDate() > 9 ? date00.getDate() : "0" + date00.getDate();
		    		var month00 = (date00.getMonth() + 1) > 9 ? (date00.getMonth() + 1) : "0"+ (date00.getMonth() + 1);
		    		var endTime = year00 + '/' + month00 + '/' + day00+' 00:00:00';
		    		
		    		this.$store.state.market.queryHisList = [];
					this.tradeSocket.send('{"Method":"QryHisTrade","Parameters":{"ClientNo":"'+JSON.parse(localStorage.tradeUser).username	+'","BeginTime":"'+beginTime+'","EndTime":"'+endTime+'"}}');
					setTimeout(function(){
						this.operateData();
					}.bind(this),500);
				}else if(index == 1){ //7天
					this.histroyDealList = [];
					var date = new Date(); 
		    		date.setDate(date.getDate()-7);
		    		var year = date.getFullYear();
		    		var day = date.getDate() > 9 ? date.getDate() : "0" + date.getDate();
		    		var month = (date.getMonth() + 1) > 9 ? (date.getMonth() + 1) : "0"+ (date.getMonth() + 1);
		    		var beginTime = year + '/' + month + '/' + day+' 00:00:00';
		    		
		    		var date00 = new Date(); 
		    		date00.setDate(date00.getDate() + 1);
		    		var year00 = date00.getFullYear();
		    		var day00 = date00.getDate() > 9 ? date00.getDate() : "0" + date00.getDate();
		    		var month00 = (date00.getMonth() + 1) > 9 ? (date00.getMonth() + 1) : "0"+ (date00.getMonth() + 1);
		    		
		    		var endTime = year00 + '/' + month00 + '/' + day00+' 00:00:00';
		    		this.$store.state.market.queryHisList = [];
					this.tradeSocket.send('{"Method":"QryHisTrade","Parameters":{"ClientNo":"'+JSON.parse(localStorage.tradeUser).username+'","BeginTime":"'+beginTime+'","EndTime":"'+endTime+'"}}');
					setTimeout(function(){
						this.operateData();
					}.bind(this),500);
				}else{ //30天
					this.histroyDealList = [];
					var date = new Date(); 
		    		date.setDate(date.getDate()-30);
		    		var year = date.getFullYear();
		    		var day = date.getDate() > 9 ? date.getDate() : "0" + date.getDate();
		    		var month = (date.getMonth() + 1) > 9 ? (date.getMonth() + 1) : "0"+ (date.getMonth() + 1);
		    		var beginTime = year + '/' + month + '/' + day+' 00:00:00';
		    		
		    		var date00 = new Date(); 
		    		date00.setDate(date00.getDate() + 1);
		    		var year00 = date00.getFullYear();
		    		var day00 = date00.getDate() > 9 ? date00.getDate() : "0" + date00.getDate();
		    		var month00 = (date00.getMonth() + 1) > 9 ? (date00.getMonth() + 1) : "0"+ (date00.getMonth() + 1);
		    		
		    		var endTime = year00 + '/' + month00 + '/' + day00+' 00:00:00';
		    		this.$store.state.market.queryHisList = [];
					this.tradeSocket.send('{"Method":"QryHisTrade","Parameters":{"ClientNo":"'+JSON.parse(localStorage.tradeUser).username+'","BeginTime":"'+beginTime+'","EndTime":"'+endTime+'"}}');
					setTimeout(function(){
						this.operateData();
					}.bind(this),500);
						
				}
			}
		},
		mounted: function(){
			//调用日历插件
			dateEvent('.startTime');
			dateEvent('.endTime');
			var date = new Date();
			var time = pro.getDate("y-m-d", date.getTime()).split(' ')[0];
			this.startTime = time;
			this.endTime = time;
			//默认查询当天数据
			this.conditionSearch(0);
		}
	}
</script>

<style lang="scss" scoped>
	@import "../../assets/css/common.scss";
	.condition{
		height: 60px;
		span, em{
			float: left;
			display: inline-block;
			height: 60px;
			line-height: 60px;
			margin: 0 10px;
		}
		span{
			cursor: pointer;
			&:hover, &.current{
				color: $yellow;
			}
		}
		.time{
			float: left;
			width: 270px;
			height: 30px;
			overflow: hidden;
			border: 1px solid $lightblue;
			margin: 15px 0;
			border-radius: 4px;
			input{
				width: 109px;
				height: 28px;
				line-height: 28px;
				padding: 0 5px;
				color: $white;
				cursor: pointer;
			}
			.ifont{
				width: 30px;
				height: 28px;
				line-height: 28px;
				text-align: center;
				background: $lightblue;
				font-size: $fs18;
				color: $blue;
			}
		}
		.btn{
			height: 30px;
			line-height: 30px;
			padding: 0 20px;
			margin: 15px;
		}
	}
	.table_box{
		height: 100px;
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
			td{
				font-size: $fs12;
			}
		}
	}
</style>