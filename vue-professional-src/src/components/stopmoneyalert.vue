<template>
	<div id="stopmoneyalert" v-if='isshow'>
		<div class="bg">
			<div>
				<div class="fl" :class="{current:isstopm}" @tap="sel">
					止损
				</div>
				<div class="fl" :class="{current:!isstopm}" @tap="sel">
					止盈
				</div>
			</div>
			<template v-if="isstopm">
				<ul class="cl">
					<li>
						<ol class="cl">
							<li class="fl fontgray">合约</li>
							<li class="fl fontwhite">{{commodityObj.CommodityNo+commodityObj.MainContract}}</li>
							<li class="fl fontgray">{{condition.Drection==0?'多':'空'}}</li>
							<li class="fl fontgray">
								<!--最新：<span class="fontwhite">{{templateListObj.LastPrice | toFixed(orderTemplistDotSize)}}</span>-->
								最新：<span class="fontwhite">{{lastPrice00 | toFixed(orderTemplistDotSize)}}</span>
							</li>
						</ol>
					</li>
					<li>
						<ol class="cl">
							<li class="fl fontgray">方式</li>
							<li class="fl">
								<select class="fontwhite sellong" v-model="selectStopLossType00">
									<option value="0">止损价</option>
									<option value="2">动态价</option>
								</select>
								<input type="text" v-model="inputPrice" class="inp" />
								<span class="fontgray">{{percentLoss}}%</span>
							</li>
						</ol>
					</li>
					<li>
						<ol class="cl">
							<li class="fl fontgray">手数</li>
							<li class="fl"><input class='inp' type="text" v-model="Num" /></li>
							<li class="fl  fontgray">
								止损委托价：
								<select name="" class='fontwhite selshort' v-model="orderType">
									<option value="1">市价</option>
									<option value="2">限价</option>
								</select>
							</li>
						</ol>
					</li>
				</ul>
			</template>
			<template v-else="isstopm">
				<ul class="cl">
					<li>
						<ol class="cl">
							<li class="fl fontgray">合约</li>
							<li class="fl fontwhite">{{commodityObj.CommodityNo+commodityObj.MainContract}}</li>
							<li class="fl fontgray">{{condition.Drection==0?'多':'空'}}</li>
							<li class="fl fontgray">
								最新：<span class="fontwhite">{{lastPrice00 | toFixed(orderTemplistDotSize)}}</span>
							</li>
						</ol>
					</li>
					<li>
						<ol class="cl">
							<li class="fl fontgray">止盈价</li>
							<li class="fl">
								<input type="text" class="inp" v-model="zhiYinInputPrice" />
								<span class="fontgray">{{percentWin}}%</span>
							</li>
						</ol>
					</li>
					<li>
						<ol class="cl">
							<li class="fl fontgray">手数</li>
							<li class="fl"><input class='inp' type="text" v-model="zhiYinNum" /></li>
							<li class="fl  fontgray">
								止损委托价：
								<select name="" class='fontwhite selshort' v-model="zhiYinorderType">
									<option value="1">市价</option>
									<option value="2">限价</option>
								</select>
							</li>
						</ol>
					</li>
				</ul>
			</template>
			<div class="cl">
				<div class="fl fontgray" @tap='close'>关闭</div>
				<div class="fl fontgray" @tap='confirm'>添加</div>
			</div>
		</div>
		<alert title="提示" :line1="tipsAlert" :objstr="sendMsg" ref="alert"></alert>
		<tipsDialog :msg="msgTips" ref="dialog"></tipsDialog>
	</div>
</template>

<script>
	import alert from './Tradealert.vue'
	import tipsDialog from './tipsDialog.vue'
	export default {
		name: 'stopmoneyalert',
		components: {alert, tipsDialog},
		data() {
			return {
				isstopm: true,
				isshow: false,
				Num:1,
				selectStopLossType00:0,
				inputPrice:0.00,
				orderType:1,
				zhiYinInputPrice:0.00,
				zhiYinNum:1,
				zhiYinorderType:1,
				tipsMsg: '',
				msg: '',
				str: '',
				lastPrice00:'',
				percent: 0.10,
			}
		},
		props: ['val'],
		computed: {
			sendMsg: function(){
				if(this.str) return JSON.stringify(this.str);
			},
			tipsAlert: function(){
				return this.tipsMsg;
			},
			msgTips: function(){
				return this.msg;
			},
			parameters(){
				return this.$store.state.market.Parameters;
			},
			orderTemplist(){
				return	this.$store.state.market.orderTemplist;
			},
			condition(){
				if(this.val==undefined) return;
				return JSON.parse(this.val);
			},
			commodityObj(){
				return this.orderTemplist[this.condition.CommodityNo];
			},
			templateListObj(){
				if(this.$store.state.market.templateList[this.condition.CommodityNo]==undefined) return;
				return this.$store.state.market.templateList[this.condition.CommodityNo];
			},
			orderTemplistDotSize(){
				if(this.$store.state.market.orderTemplist[this.condition.CommodityNo]==undefined) return;
				return	this.$store.state.market.orderTemplist[this.condition.CommodityNo].DotSize;
			},
			tradeSocket() {
				return this.$store.state.tradeSocket;
			},
		},
		watch:{
			parameters:function(n,o){
				if(this.condition.CommodityNo!=undefined){
					n.forEach(function(e,i){
						if(this.condition.CommodityNo==e.CommodityNo){
							this.lastPrice00 = this.orderTemplist[this.condition.CommodityNo].LastQuotation.LastPrice;
						}
						
					}.bind(this));
				}
			},
			inputPrice: function(n, o){
				if(n != undefined){
					var openAvgPrice = JSON.parse(this.val).OpenAvgPrice;
					this.percentLoss = parseFloat((n - openAvgPrice)/openAvgPrice*100).toFixed(2);
				}
			},
			zhiYinInputPrice: function(n, o){
				if(n != undefined){
					var openAvgPrice = JSON.parse(this.val).OpenAvgPrice;
					this.percentWin = parseFloat((n - openAvgPrice)/openAvgPrice*100).toFixed(2);
				}
			}
		},
		filters:{
			toFixed:function(value,dotSize){
				if (!value) return '';
				return parseFloat(value).toFixed(dotSize);
			}
		},
		methods: {
			sel: function(e) {
				var txt = e.target.innerText;
				switch(txt) {
					case '止损':
						this.isstopm = true;
						break;
					case '止盈':
						this.isstopm = false;
						break;
				}
			},
			close: function() {
				this.isshow = false;
			},
			confirm: function() {
				if(this.isstopm == true){
					if(this.inputPrice == '' || this.inputPrice == 0 || this.inputPrice == undefined){
						this.$refs.dialog.isShow = true;
						this.msg = '请输入止损价';
					}else if(this.inputPrice >= this.templateListObj.LastPrice){
						this.$refs.dialog.isShow = true;
						this.msg = '输入价格应该小于最新价';
					}else if(this.Num == '' || this.Num == 0 || this.Num == undefined){
						this.$refs.dialog.isShow = true;
						this.msg = '请输入止损手数';
					}else{
						this.$refs.alert.isshow = true;
						this.tipsMsg = '是否添加限价止损？';
						let b={
							"Method":'InsertStopLoss',
							"Parameters":{
								"ExchangeNo":this.orderTemplist[this.condition.CommodityNo].ExchangeNo,
								"CommodityNo":this.orderTemplist[this.condition.CommodityNo].CommodityNo,
								"ContractNo":this.orderTemplist[this.condition.CommodityNo].MainContract,
								"Num":parseInt(this.Num),
								"StopLossType":parseInt(this.selectStopLossType00),
								"StopLossPrice":(function(){
													if(parseInt(this.selectStopLossType00)==0){
														return parseFloat(this.inputPrice);
													}else{
														return 0.00;
													}
												}).bind(this)(),
								"StopLossDiff":(function(){
													if(parseInt(this.selectStopLossType00)==2){
														return parseFloat(this.inputPrice);
													}else{
														return 0.00;
													}
												}).bind(this)(),
								"HoldAvgPrice":this.condition.HoldAvgPrice,
								"HoldDirection":this.condition.Drection,
								"OrderType":parseInt(this.orderType),
							}
						};
						this.str = b;
					}
				}else{
					if(this.zhiYinInputPrice == '' || this.zhiYinInputPrice == 0 || this.zhiYinInputPrice == undefined){
						this.$refs.dialog.isShow = true;
						this.msg = '请输入止盈价';
					}else if(this.zhiYinInputPrice <= this.templateListObj.LastPrice){
						this.$refs.dialog.isShow = true;
						this.msg = '输入价格应该高于最新价';
					}else if(this.zhiYinNum == '' || this.zhiYinNum == 0 || this.zhiYinNum == undefined){
						this.$refs.dialog.isShow = true;
						this.msg = '请输入止赢手数';
					}else{
						this.$refs.alert.isshow = true;
						this.tipsMsg = '是否添加限价止赢？';
						let b={
							"Method":'InsertStopLoss',
							"Parameters":{
								"ExchangeNo":this.orderTemplist[this.condition.CommodityNo].ExchangeNo,
								"CommodityNo":this.orderTemplist[this.condition.CommodityNo].CommodityNo,
								"ContractNo":this.orderTemplist[this.condition.CommodityNo].MainContract,
								"Num":parseInt(this.zhiYinNum),
								"StopLossType":1,
								"StopLossPrice":parseFloat(this.zhiYinInputPrice),
								"StopLossDiff":0.00,
								"HoldAvgPrice":this.condition.HoldAvgPrice,
								"HoldDirection":this.condition.Drection,
								"OrderType":parseInt(this.orderType)
							}
						};
						this.str = b;
					}
				}
			}
		},
		mounted: function(){

		},
	}
</script>

<style scoped lang="less">
	@import url("../assets/css/main.less");
	@width: 330px;
	@height: 226px;
	#stopmoneyalert {
		position: fixed;
		top: 0;
		left: 0;
		right: 0;
		bottom: 0;
		background-color: rgba(0, 0, 0, .8);
		font-size: 15px;
		z-index: 1100;
	}
	
	.bg {
		width: @width;
		height: @height;
		background-color: #1b1b26;
		position: fixed;
		top: 212px;
		left: 40px;
		position: relative;
		box-shadow: 1px 1px 3px rgba(0, 0, 0, 0.3), -1px -1px 3px rgba(0, 0, 0, 0.3);
		border-top-left-radius: 5px;
		border-top-right-radius: 5px;
		border-bottom-left-radius: 5px;
		border-bottom-right-radius: 5px;
	}
	
	.bg>div {
		width: 100%;
		height: 44px;
		background-color: #242633;
		border-top-left-radius: 5px;
		border-top-right-radius: 5px;
	}
	
	.bg>ul {
		background-color: #242633;
		width: 100%;
		height: 132px;
	}
	
	.bg>div:after {
		content: '';
		display: div;
		clear: both;
	}
	
	.bg>div:first-child>div {
		color: #949bbb;
		width: 50%;
		height: 44px;
		text-align: center;
		line-height: 44px;
	}
	
	.bg>div:last-child>div {
		color: #949bbb;
		width: 50%;
		height: 44px;
		text-align: center;
		line-height: 44px;
	}
	
	.bg>div:last-child {
		border-bottom-left-radius: 5px;
		border-bottom-right-radius: 5px;
	}
	
	ol {
		height: 44px;
		width: 100%;
	}
	
	ol>li {
		text-align: center;
	}
	
	ol>li:first-child {
		width: 56px;
		border-right: 1px solid #1c1c27;
	}
	
	ul>li:first-child>ol>li:nth-child(2) {
		width: 111px;
		border-right: 1px solid #1c1c27;
	}
	
	ul>li:first-child>ol>li:nth-child(3) {
		width: 56px;
		border-right: 1px solid #1c1c27;
	}
	
	ul>li:first-child>ol>li:nth-child(4) {
		width: 100px;
	}
	
	ul>li:nth-child(2)>ol>li:nth-child(2) {
		padding-left: 5px;
	}
	
	ul>li:nth-child(3)>ol>li:nth-child(2) {
		width: 110px;
		border-right: 1px solid #1c1c27;
	}
	
	ul>li:nth-child(3)>ol>li:nth-child(3) {
		padding-left: 5px;
	}
	
	.inp {
		width: 99px;
		height: 33px;
		border-radius: 3px;
		border: 1px solid #14151d;
		color: white;
		outline: none;
		background-color: #1b1b26;
		text-align: center;
		margin: 0;
	}
	
	.sellong {
		padding-left: 2em;
		width: 99px;
		height: 33px;
		border-radius: 3px;
		border: 1px solid #14151d;
		color: white;
		outline: none;
		background-color: #1b1b26;
	}
	
	.selshort {
		padding-left: 1em;
		width: 55px;
		height: 33px;
		border-radius: 3px;
		border: 1px solid #14151d;
		color: white;
		outline: none;
		background-color: #1b1b26;
	}
	
	.bg>ul>li {
		width: 100%;
		height: 44px;
		border-top: 1px solid #1c1c27;
	}
	
	.bg>ul>li li {
		line-height: 44px;
	}
	
	.bg>div:last-child {
		position: absolute;
		bottom: 0;
	}
	
	.bg>div>div.current {
		color: #fcc900;
	}
</style>