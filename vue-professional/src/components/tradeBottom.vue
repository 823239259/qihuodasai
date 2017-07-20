<template>
	<div id="tradebottom">
		<div>
			<div class="toppart">
				<div class="fl">
					<ul>
						<li>
							<div class="fontgray fl">
								卖 <span class="fontred">{{LastQuotation.AskPrice1 | fixNum2(detail.DotSize)}}</span>
							</div>
							<p class="fontwhite fr">
								{{LastQuotation.AskQty1}}
							</p>
						</li>
						<li>
							<div class="fontgray fl">
								买 <span class="fontred">{{LastQuotation.BidPrice1 | fixNum2(detail.DotSize)}}</span>
							</div>
							<p class="fontwhite fr">
								{{LastQuotation.BidQty1}}
							</p>
						</li>
						<li>
							<div class="fontgray fl">
								成交量
							</div>
							<div class="fontwhite fr">
								{{LastQuotation.LastVolume}}
							</div>
						</li>
					</ul>
				</div>
				<div class="fl">
					<ul>
						<li class="fontred">
							{{LastQuotation.LastPrice}}
						</li>
						<li class="fontred">
							<span>{{LastQuotation.ChangeValue | fixNum2(detail.DotSize)}}</span>
							<span> {{LastQuotation.ChangeRate | fixNum}}%</span>
						</li>
					</ul>
				</div>
				<div class="handle">
					<div>
						<button class="fontgray" @tap='add'>+</button>
						<div class="fl">
							<span class="fontgray">手数:</span>
							<input id=handlenum type="text" class="fontwhite" v-model="lotnum" />
							<span class="fontwhite">(市价)</span>
						</div>
						<button class="fontgray" @tap='min'>-</button>
					</div>
				</div>
			</div>
			<div class="bottompart">
				<tradebtn marketprice="132133" transaction='sell' class='fl'></tradebtn>
				<tradebtn marketprice="13221321" transaction='buy' class='fl'></tradebtn>
			</div>
		</div>
	</div>
</template>

<script>
	import tradebtn from '../components/tradeButton.vue'
	export default {
		name: 'tradebottom',
		components: {
			tradebtn
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
				if(num>=0){
					return '+'+num.toFixed(dotsize);
				}else{
					return ' '+num.toFixed(dotsize);
				}
			}
		},
		data() {
			return {
				lotnum: 0,
			}
		},
		methods: {
			add() {
				this.lotnum++;
			},
			min() {
				this.lotnum--;
			}
		},
		computed:{
			//映射假数据
			Data(){
				return this.$store.state.market.jsonData.Parameters.Data;
			},
			detail(){
				return this.$store.state.currentdetail;
			},
			LastQuotation(){
				return this.$store.state.currentdetail.LastQuotation
			}
		},
		activated:function(){
			console.log(1);
			this.detail=this.$parent.detail;
		},
		watch:{
			lotnum:function(n,o){
				if(n<0){
					this.lotnum=0;
				}
			}
		}
	}
</script>

<style scoped lang="less">
	@import url("../assets/css/main.less");
	#tradebottom{
		position: fixed;
		bottom: 0;
		left: 0;
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
	
	.toppart>div:nth-child(2)>ul>li {
		height: 82px/2;
		line-height: 26px;
	}
	
	.toppart>div:nth-child(2)>ul>li:first-child {
		font-size: 26px;
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
	
	.handle span:last-child {
		font-size: 14px;
	}
	
	.handle span:first-child {
		font-size: 16px;
	}
	
	.bottompart {
		height: 66px;
	}
	
	.bottompart>div {
		margin-left: 17px;
	}
	
	.bottompart>div:last-child {
		margin-left: 51px;
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
		.toppart>div:nth-child(2)>ul>li {
			height: 82px*@ip5/2;
			line-height: 26px*@ip5;
		}
		.toppart>div:nth-child(2)>ul>li:first-child {
			font-size: 26px*@ip5;
		}
		.handle {
			height: 55px*@ip5;
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
		.handle span:last-child {
			font-size: 14px*@ip5;
		}
		.handle span:first-child {
			font-size: 16px*@ip5;
		}
		.bottompart {
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
		.toppart>div:nth-child(2)>ul>li {
			height: 82px*@ip6/2;
			line-height: 26px*@ip6;
		}
		.toppart>div:nth-child(2)>ul>li:first-child {
			font-size: 26px*@ip6;
		}
		.handle {
			height: 55px*@ip6;
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
		.handle span:last-child {
			font-size: 14px*@ip6;
		}
		.handle span:first-child {
			font-size: 16px*@ip6;
		}
		.bottompart {
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
		width: 100%;
	}
}
</style>