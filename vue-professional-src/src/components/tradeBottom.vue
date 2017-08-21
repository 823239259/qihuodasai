<template>
	<div id="tradebottom">
		<div>
			<alert title="确认下单" line1="" :line2="insertOrder" :objstr='objst'></alert>
			<div class="toppart">
				<div class="fl">
					<ul>
						<li>
							<div class="fontgray fl">
								卖 <span class="fontred" :class="{red: Parameters.AskPrice1 - Parameters.PreSettlePrice > 0,green: Parameters.AskPrice1 - Parameters.PreSettlePrice < 0}">{{Parameters.AskPrice1 | fixNum4(detail.DotSize)}}</span>
							</div>
							<p class="fontwhite fr">
								{{Parameters.AskQty1}}
							</p>
						</li>
						<li>
							<div class="fontgray fl">
								买 <span class="fontred" :class="{red: Parameters.AskPrice1 - Parameters.PreSettlePrice > 0,green: Parameters.AskPrice1 - Parameters.PreSettlePrice < 0}">{{Parameters.BidPrice1 | fixNum4(detail.DotSize)}}</span>
							</div>
							<p class="fontwhite fr">
								{{Parameters.BidQty1}}
							</p>
						</li>
						<li>
							<div class="fontgray fl">
								成交量
							</div>
							<div class="fontwhite fr">
								{{Parameters.TotalVolume}}
							</div>
						</li>
					</ul>
				</div>
				<div class="fl">
					<ul>
						<li class="fontred" :class="{red: Parameters.AskPrice1 - Parameters.PreSettlePrice > 0,green: Parameters.AskPrice1 - Parameters.PreSettlePrice < 0}">
							{{Parameters.LastPrice | fixNum2(detail.DotSize)}}
						</li>
						<li class="fontred">
							<span :class="{red: Parameters.ChangeValue > 0,green: Parameters.ChangeValue < 0}">{{Parameters.ChangeValue | fixNum2(detail.DotSize)}}</span>
							<span :class="{red: Parameters.ChangeValue > 0,green: Parameters.ChangeValue < 0}">{{Parameters.ChangeRate | fixNum}}%</span>
						</li>
					</ul>
				</div>
				<div class="handle">
					<div>
						<button class="fontgray" @tap='add'>+</button>
						<div class="fl">
							<span class="fontgray">手数:</span>
							<input id="handlenum" type="text" class="fontwhite" v-model="lotnum" />
							<span class="fontwhite">(市价)</span>
						</div>
						<button class="fontgray" @tap='min'>-</button>
					</div>
				</div>
			</div>
			<div class="bottompart">
				<chartBtn type="buy" class="fl" @tap.native='buyOrder'></chartBtn>
				<chartBtn type="sell" class="fr" @tap.native='sellOrder'></chartBtn>
			</div>
			<alert title="提示" line1="你还未登录，请先登录" jump="true"></alert>
		</div>
	</div>
</template>

<script>
	import chartBtn from '../components/chartBtn.vue'
	import alert from '../components/Tradealert.vue'
	export default {
		name: 'tradebottom',
		components: {
			chartBtn, alert
		},
		filters:{
			fixNum:function(num){
				if(num>=0){
					return '+'+num.toFixed(2);
				}else{
					return ' '+num.toFixed(2);
				}
			},
			fixNum2:function(num,dotsize){
//				if(num>=0){
					return num.toFixed(dotsize);
//				}else{
//					return ' '+num.toFixed(dotsize);
//				}
			},
			fixNum3:function(num,dotsize){
				if(num>=0){
					return num.toFixed(dotsize);
				}else{
					return num.toFixed(dotsize);
				}
			},
			fixNum4:function(num,dotsize){
				if(num>=0){
					return num.toFixed(dotsize);
				}else{
					return num.toFixed(dotsize);
				}
			}
			
		},
		data() {
			return {
				lotnum: 1,
				numReg: /^[0-9]*$/,
				buyText: {}
			}
		},
		methods: {
			add() {
				this.lotnum++;
			},
			min() {
				this.lotnum--;
			},
			buyOrder:function(){
				if(JSON.parse(localStorage.getItem('tradeUser')) == null){
					this.$children[3].isshow = true;
				}else{
					this.$children[0].isshow = true;
					var buildIndex = 0;
					if(buildIndex > 100){
						buildIndex = 0;
					}
					var b ={
						"Method":'InsertOrder',
						"Parameters": {
							"ExchangeNo": this.detail.LastQuotation.ExchangeNo,
							"CommodityNo": this.detail.LastQuotation.CommodityNo,
							"ContractNo": this.detail.LastQuotation.ContractNo,
							"OrderNum": this.lotnum,
							"Drection": 0,
							"PriceType": 1,
							"LimitPrice": 0.00,
							"TriggerPrice": 0,
							"OrderRef": this.$store.state.market.tradeConfig.client_source+ new Date().getTime()+(buildIndex++)
						}
					};
					this.buyText = b;
//					this.tradeSocket.send(JSON.stringify(b));
				}
			},
			sellOrder:function(){
				if(JSON.parse(localStorage.getItem('tradeUser')) == null){
					this.$children[3].isshow = true;
				}else{
					this.$children[0].isshow = true;
					var buildIndex=0;
					if(buildIndex>100){
						buildIndex=0;
					}
					var b={
						"Method":'InsertOrder',
						"Parameters":{
							"ExchangeNo":this.detail.LastQuotation.ExchangeNo,
							"CommodityNo":this.detail.LastQuotation.CommodityNo,
							"ContractNo":this.detail.LastQuotation.ContractNo,
							"OrderNum": this.lotnum,
							"Drection":1,
							"PriceType":1,
							"LimitPrice":0.00,
							"TriggerPrice":0,
							"OrderRef":this.$store.state.market.tradeConfig.client_source+ new Date().getTime()+(buildIndex++)
						}
					};
					this.buyText = b;
//					this.tradeSocket.send(JSON.stringify(b));
				}
			}
		},
		computed:{
			//映射假数据
			Data(){
				return this.$store.state.market.jsonData.Parameters.Data;
			},
			detail(){
				return this.$store.state.market.	currentdetail
			},
			Parameters(){
				return this.$store.state.market.jsonTow.Parameters;
			},
			templateList(){
				return this.$store.state.market.templateList;
			},
			detail(){
				return this.$store.state.market.currentdetail
			},
			tradeSocket() {
				return this.$store.state.tradeSocket;
			},
			objst: function(){
				if(this.buyText){
					return JSON.stringify(this.buyText);
				}
			},
			insertOrder: function(){
				var obj = this.buyText.Parameters;
				if(obj != undefined){
					var contract=obj.CommodityNo+obj.ContractNo;
					var LimitPrice;
					if(obj.PriceType == 1){
						LimitPrice='市价';
					}else{
						LimitPrice = this.tradePrices;
					}
					var orderNum = obj.OrderNum;
					var drection;
					if(obj.Drection==0){
						drection = '买';
					}else{
						drection = '卖';
					}
					var text = '确认提交订单:【'+contract+'】,价格【'+LimitPrice +'】,手数【'+orderNum+'】,方向【'+drection+'】？';
					return  text;
				}
			},
		},
		watch:{
			lotnum:function(n,o){
				this.lotnum = parseFloat(n);
				if(this.numReg.test(n) == false || n < 0){
					this.lotnum = 0;
				};
			}
		}
	}
</script>

<style scoped lang="less">
	@import url("../assets/css/main.less");
	@import url("../assets/css/base.less");
	#tradebottom{
		position: fixed;
		bottom: 0;
		left: 0;
		z-index: 1015;
		width: 100%;
	}
	.haddle {
		clear: both;
	}
	
	.toppart {
		height: 150px;
		width: 100%;
		background: #242633;
		border-top: 6px solid #1b1b26;
		border-bottom: 6px solid #1b1b26;
	}
	
	.toppart>div:first-child,
	.toppart>div:nth-child(2) {
		width: 50%;
		border-bottom: 1px solid #1b1b26;
		height: 82px;
	}
	
	.toppart>div:nth-child(2) {
		border-left: 1px solid #1b1b26;
	}
	
	.toppart>div>ul {
		height: 82px;
		width: 100%;
	}
	
	.toppart>div>ul>li {
		width: 100%;
		height: 82px*0.2733;
		line-height: 82px*0.2733;
		font-size: 14px;
		padding: 8px 17px;
	}
	.toppart>div:nth-child(1)>ul>li span{
		margin-left: 25px;
	}
	.toppart>div:nth-child(2)>ul>li {
		height: 30px;
		line-height: 30px;
		
	}
	.toppart>div:nth-child(2){
		height: 82px;
		padding-top: 8px;
	}
	.toppart>div:nth-child(2)>ul>li:first-child {
		font-size: 30px;
		font-weight: bold;
	}
	
	.handle {
		height: 55px;
		width: 100%;
		clear: both;
	}
	
	.handle:before {
		content: '';
		display: table;
	}
	
	.handle>div {
		clear: both;
		width: 382px;
		height: 44px;
		border: 1px solid #1d1e29;
		border-radius: 4px;
		background-color: #1b1b26;
		margin-left: 16px;
		margin-top: 5px;
		text-align: center;
	}
	
	.handle>div>button:first-child {
		width: 50px;
		height: 44px;
		border: none;
		outline: none;
		background: transparent;
		float: left;
		font-size: 20px;
	}
	
	.handle>div>button:last-child {
		width: 50px;
		height: 44px;
		border: none;
		outline: none;
		background: transparent;
		float: right;
		font-size: 20px;
	}
	
	#handlenum {
		width: 160px;
		height: 44px;
		background: transparent;
		text-align: center;
		font-size: 18px;
	}
	.handle span{
		display: inline-block;
		height: 44px;
		line-height: 44px;
	}
	.handle span:last-child {
		float: right;
		font-size: 14px;
	}
	
	.handle span:first-child {
		float: left;
		font-size: 16px;
	}
	
	.bottompart {
		padding: 0 15px;
		height: 66px;
	}
	
	.bottompart>div {
		margin-left: 17px;
	}
	
	.bottompart>div:last-child {
		margin-left: 51px;
	}
	span.red, li.red{
		color: @red;
	}
	span.green, li.green{
		color: @green;
	}
	
	@media(max-width:370px) {
		.toppart {
			height: 150px*@ip5;
			width: 100%;
			background: #242633;
			border-top: 6px solid #1b1b26;
			border-bottom: 6px solid #1b1b26;
		}
		.toppart>div:first-child,
		.toppart>div:nth-child(2) {
			width: 50%;
			border-bottom: 1px solid #1b1b26;
			height: 82px*@ip5;
		}
		.toppart>div:nth-child(2) {
			border-left: 1px solid #1b1b26;
		}
		.toppart>div>ul {
			height: 82px*@ip5;
			width: 100%;
		}
		.toppart>div>ul>li {
			width: 100%;
			height: 82px*0.2733*@ip5;
			line-height: 82px*0.2733*@ip5;
			font-size: 14px*@ip5;
			padding: 8px*@ip5 17px*@ip5;
		}
		.toppart>div:nth-child(1)>ul>li span{
			margin-left: 25px*@ip5;
		}
		.toppart>div:nth-child(2)>ul>li {
			height: 30px*@ip5;
			line-height: 30px*@ip5;
		}
		.toppart>div:nth-child(2){
			height: 82px*@ip5;
			padding-top: 8px*@ip5;
		}
		.toppart>div:nth-child(2)>ul>li:first-child {
			font-size: 30px*@ip5;
			font-weight: bold;
		}
		.handle {
			height: 44px*@ip5;
			width: 100%;
			clear: both;
		}
		.handle:before {
			content: '';
			display: table;
		}
		.handle>div {
			clear: both;
			width: 382px*@ip5;
			height: 44px*@ip5;
			border: 1px solid #1d1e29;
			border-radius: 4px;
			background-color: #1b1b26;
			margin-left: 16px*@ip5;
			margin-top: 5px*@ip5;
			text-align: center;
		}
		.handle>div>button:first-child {
			width: 50px*@ip5;
			height: 44px*@ip5;
			border: none;
			outline: none;
			background: transparent;
			float: left;
			font-size: 20px*@ip5;
			transform: translateY(-2px);
		}
		.handle>div>button:last-child {
			width: 50px*@ip5;
			height: 44px*@ip5;
			border: none;
			outline: none;
			background: transparent;
			float: right;
			font-size: 20px*@ip5;
			transform: translateY(-2px);
		}
		#handlenum {
			width: 160px*@ip5;
			height: 44px*@ip5;
			background: transparent;
			text-align: center;
			font-size: 18px*@ip5;
		}
		.handle span{
			display: inline-block;
			height: 44px*@ip5;
			line-height: 44px*@ip5;
		}
		.handle span:last-child {
			font-size: 14px*@ip5;
		}
		.handle span:first-child {
			font-size: 16px*@ip5;
		}
		.bottompart {
			padding: 0 15px*@ip5;
			height: 66px*@ip5;
		}
		.bottompart>div {
			margin-left: 17px*@ip5;
		}
		.bottompart>div:last-child {
			margin-left: 51px*@ip5;
		}
	}
	/*ip6*/
	@media (min-width:371px) and (max-width:410px) {
		.toppart {
			height: 150px*@ip6;
			width: 100%;
			background: #242633;
			border-top: 6px solid #1b1b26;
			border-bottom: 6px solid #1b1b26;
		}
		.toppart>div:first-child,
		.toppart>div:nth-child(2) {
			width: 50%;
			border-bottom: 1px solid #1b1b26;
			height: 82px*@ip6;
		}
		.toppart>div:nth-child(2) {
			border-left: 1px solid #1b1b26;
		}
		.toppart>div>ul {
			height: 82px*@ip6;
			width: 100%;
		}
		.toppart>div>ul>li {
			width: 100%;
			height: 82px*0.2733*@ip6;
			line-height: 82px*0.2733*@ip6;
			font-size: 14px*@ip6;
			padding: 8px*@ip6 17px*@ip6;
		}
		.toppart>div:nth-child(1)>ul>li span{
			margin-left: 25px*@ip6;
		}
		.toppart>div:nth-child(2)>ul>li {
			height: 30px*@ip6;
			line-height: 30px*@ip6;
		}
		.toppart>div:nth-child(2){
			height: 82px*@ip6;
			padding-top: 8px*@ip6;
		}
		.toppart>div:nth-child(2)>ul>li:first-child {
			font-size: 30px*@ip6;
			font-weight: bold;
		}
		.handle {
			height: 44px*@ip6;
			width: 100%;
			clear: both;
		}
		.handle:before {
			content: '';
			display: table;
		}
		.handle>div {
			clear: both;
			width: 382px*@ip6;
			height: 44px*@ip6;
			border: 1px solid #1d1e29;
			border-radius: 4px;
			background-color: #1b1b26;
			margin-left: 16px*@ip6;
			margin-top: 5px*@ip6;
			text-align: center;
		}
		.handle>div>button:first-child {
			width: 50px*@ip6;
			height: 44px*@ip6;
			border: none;
			outline: none;
			background: transparent;
			float: left;
			font-size: 20px*@ip6;
			transform: translateY(-2px);
		}
		.handle>div>button:last-child {
			width: 50px*@ip6;
			height: 44px*@ip6;
			border: none;
			outline: none;
			background: transparent;
			float: right;
			font-size: 20px*@ip6;
			transform: translateY(-2px);
		}
		#handlenum {
			width: 160px*@ip6;
			height: 44px*@ip6;
			background: transparent;
			text-align: center;
			font-size: 18px*@ip6;
		}
		.handle span{
			display: inline-block;
			height: 44px*@ip6;
			line-height: 44px*@ip6;
		}
		.handle span:last-child {
			font-size: 14px*@ip6;
		}
		.handle span:first-child {
			font-size: 16px*@ip6;
		}
		.bottompart {
			padding: 0 15px*@ip6;
			height: 66px*@ip6;
		}
		.bottompart>div {
			margin-left: 17px*@ip6;
		}
		.bottompart>div:last-child {
			margin-left: 51px*@ip6;
		}
	}
	
	
	/*ip6p及以上*/
@media (min-width:411px) {
    #tradebottom{
		position: fixed;
		bottom: 0;
		left: 0;
		z-index: 1015;
		width: 100%;
	}
}
</style>