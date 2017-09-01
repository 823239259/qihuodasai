<template>
	<div id="stopmoneyalert" v-if='isshow'>
		<div class="bg">
			<div>
				<div class="fl" :class="{current:isstopm}" @tap="sel">止损</div>
			</div>
			<template>
				<ul class="cl">
					<li>
						<ol class="cl">
							<li class="fl fontgray">合约</li>
							<li class="fl fontwhite">{{stopLossListSelectOneObj.CommodityNo+stopLossListSelectOneObj.ContractNo}}</li>
							<li class="fl fontgray">{{stopLossListSelectOneObj.HoldDrection}}</li>
							<li class="fl fontgray">
								最新：<span class="fontwhite">{{lastPrice}}</span>
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
								<span class="fontgray">0.00%</span>
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
			<div class="cl">
				<div class="fl fontgray" @tap='close'>关闭</div>
				<div class="fl fontgray" @tap='confirm'>修改</div>
			</div>

		</div>
	</div>
</template>

<script>
	export default {
		name: 'stopmoneyalert',
		data() {
			return {
				isstopm: true,
				isshow:false,
				zhiYinInputPrice:0.00,
				zhiYinNum:1,
				zhiYinorderType:1,
			}
		},
		props: ['val'],
		computed: {
			parameters(){
				return this.$store.state.market.Parameters;
			},
			orderTemplist(){
				return	this.$store.state.market.orderTemplist;
			},
			tradeSocket() {
				return this.$store.state.tradeSocket;
			},
			stopLossListSelectOneObj(){
				return this.$store.state.market.stopLossListSelectOneObj;
			},
			templateList(){
				return	this.$store.state.market.templateList;
			},
			lastPrice(){
				let commodityNo = this.stopLossListSelectOneObj.CommodityNo;
				return this.$store.state.market.templateList[commodityNo].LastPrice;
			},
			selectStopLossType00(){
				return this.stopLossListSelectOneObj.StopLossType00;
			},
			inputPrice(){
				
				if(this.selectStopLossType00==0){
					return this.stopLossListSelectOneObj.StopLossPrice;
				}else if(this.selectStopLossType00==2){
					return this.stopLossListSelectOneObj.StopLossDiff;
				}
			},
			Num(){
				return this.stopLossListSelectOneObj.Num;
			},
			orderType(){
				return this.stopLossListSelectOneObj.OrderType00;
			}
			
		},
		filters:{
			toFixed:function(value,dotSize){
				if (!value) return '';
				return parseFloat(value).toFixed(dotSize);
			},
			filterSelectStopLossType:function(value){
				if(value==0)
					return '止损价';
				if(value==2)
					return '动态价';
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
				this.isshow = false;
			}
		},
		mounted: function(){
		},
		activated:function(){
		}
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