<template>
	<div id="stopmoneyalert" v-if='isshow'>
		<tipsDialog :msg="msgTips" ref="dialog"></tipsDialog>
		<alert title="提示" :line1="tipsAlert" :objstr="sendMsg" ref="alert"></alert>
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
			<div class="cl">
				<div class="fl fontgray" @tap='close'>关闭</div>
				<div class="fl fontgray" @tap='confirm'>修改</div>
			</div>

		</div>
	</div>
</template>

<script>
	import tipsDialog from './tipsDialog.vue'
	import alert from './Tradealert.vue'
	export default {
		name: 'stopmoneyalert',
		components: {tipsDialog, alert},
		data() {
			return {
				isstopm: true,
				isshow:false,
				zhiYinInputPrice:0.00,
				zhiYinNum:1,
				selectStopLossType00:'',
				inputPrice:'',
				percentLoss: '0.00',
				Num:'',
				orderType:'',
				tipsMsg: '',
				msg: '',
				str: ''
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
			miniTikeSize(){
				return this.orderTemplist[this.stopLossListSelectOneObj.CommodityNo].MiniTikeSize;
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
		watch:{
			stopLossListSelectOneObj: function(n,o){
				this.selectStopLossType00 = n.StopLossType00;
				this.Num = n.Num;
				this.orderType = n.OrderType00;
			},
			selectStopLossType00: function(n, o){
				if(n == 2){
					this.inputPrice = this.miniTikeSize;
				}else if(n == 0){
					this.inputPrice = this.stopLossListSelectOneObj.StopLossPrice;
				}
			},
			inputPrice: function(n, o){
				if(n != undefined){
					if(this.selectStopLossType00 == 2){
						this.percentLoss = '0.00';
						return;
					}
					var openAvgPrice = this.stopLossListSelectOneObj.HoldAvgPrice;
					this.percentLoss = parseFloat((n - openAvgPrice)/openAvgPrice*100).toFixed(2);
				}
			},
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
				var d0 = this.inputPrice%this.miniTikeSize;
				if(this.inputPrice == '' || this.inputPrice == 0 || this.inputPrice == undefined){
					this.$refs.dialog.isShow = true;
					this.msg = '请输入止损价';
				}else if(this.inputPrice >= this.lastPrice){
					this.$refs.dialog.isShow = true;
					this.msg = '输入价格应该小于最新价';
				}else if(d0 >= 0.000000001 && parseFloat(this.miniTikeSize-d0) >= 0.0000000001){
					this.$refs.dialog.isShow = true;
					this.msg = '输入价格不符合最小变动价，最小变动价为：' + this.miniTikeSize;
				}else if(this.Num == '' || this.Num == 0 || this.Num == undefined){
					this.$refs.dialog.isShow = true;
					this.msg = '请输入止损手数';
				}else{
					this.$refs.alert.isshow = true;
					this.tipsMsg = '是否添加限价止损？';
					let b={
						"Method":'ModifyStopLoss',
						"Parameters":{
							'StopLossNo':this.stopLossListSelectOneObj.StopLossNo,
							'ModifyFlag':0,
							'Num':parseInt(this.Num),
							'StopLossType':parseInt(this.selectStopLossType00),
							'OrderType':parseInt(this.orderType),
							'StopLossPrice':(function(){
												if(parseInt(this.selectStopLossType00)==0)
													return parseFloat(this.inputPrice);
												if(parseInt(this.selectStopLossType00)==2)
													return 0.0;
											}.bind(this))(),
							'StopLossDiff':(function(){
												if(parseInt(this.selectStopLossType00)==0)
													return 0;
												if(parseInt(this.selectStopLossType00)==2)
													return parseFloat(this.inputPrice);
											}.bind(this))()
						}
					};
					this.str = b;
				}
//				this.isshow = false;
//				this.tradeSocket.send(JSON.stringify(b));
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
/*ip6p及以上*/
@media (min-width:411px) {
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
		width: 95px;
		height: 33px;
		line-height: 32px;
		padding: 0;
		border-radius: 3px;
		border: 1px solid #14151d;
		color: white;
		outline: none;
		background-color: #1b1b26;
		text-align: center;
		margin: 0;
	}
	.sellong {
		padding: 0 10px;
		width: 95px;
		height: 33px;
		border-radius: 3px;
		border: 1px solid #14151d;
		color: white;
		outline: none;
		background-color: #1b1b26;
	}
	.selshort {
		padding: 0 10px;
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
}
/*ip6*/
@media (min-width:371px) and (max-width:410px) {
    @width: 330px*@ip6;
	@height: 226px*@ip6;
	#stopmoneyalert {
		position: fixed;
		top: 0;
		left: 0;
		right: 0;
		bottom: 0;
		background-color: rgba(0, 0, 0, .8);
		font-size: 16px*@ip6;
		z-index: 1100;
	}
	.bg {
		width: @width;
		height: @height;
		background-color: #1b1b26;
		position: fixed;
		top: 212px*@ip6;
		left: 40px*@ip6;
		position: relative;
		box-shadow: 1px 1px 3px rgba(0, 0, 0, 0.3), -1px -1px 3px rgba(0, 0, 0, 0.3);
		border-radius: 5px*@ip6;
	}
	.bg>div {
		width: 100%;
		height: 44px*@ip6;
		background-color: #242633;
		border-top-left-radius: 5px*@ip6;
		border-top-right-radius: 5px*@ip6;
	}
	.bg>ul {
		background-color: #242633;
		width: 100%;
		height: 132px*@ip6;
	}
	.bg>div:after {
		content: '';
		display: div;
		clear: both;
	}
	.bg>div:first-child>div {
		color: #949bbb;
		width: 50%;
		height: 44px*@ip6;
		text-align: center;
		line-height: 44px*@ip6;
	}
	.bg>div:last-child>div {
		color: #949bbb;
		width: 50%;
		height: 44px*@ip6;
		text-align: center;
		line-height: 44px*@ip6;
	}
	.bg>div:last-child {
		border-bottom-left-radius: 5px*@ip6;
		border-bottom-right-radius: 5px*@ip6;
	}
	ol {
		height: 44px*@ip6;
		width: 100%;
	}
	ol>li {
		text-align: center;
	}
	ol>li:first-child {
		width: 56px*@ip6;
		border-right: 1px solid #1c1c27;
	}
	ul>li:first-child>ol>li:nth-child(2) {
		width: 111px*@ip6;
		border-right: 1px solid #1c1c27;
	}
	ul>li:first-child>ol>li:nth-child(3) {
		width: 56px*@ip6;
		border-right: 1px solid #1c1c27;
	}
	ul>li:first-child>ol>li:nth-child(4) {
		width: 100px*@ip6;
	}
	ul>li:nth-child(2)>ol>li:nth-child(2) {
		padding-left: 5px*@ip6;
	}
	ul>li:nth-child(3)>ol>li:nth-child(2) {
		width: 110px*@ip6;
		border-right: 1px solid #1c1c27;
	}
	ul>li:nth-child(3)>ol>li:nth-child(3) {
		padding-left: 5px*@ip6;
	}
	.inp {
		width: 95px*@ip6;
		height: 33px*@ip6;
		line-height: 32px*@ip6;
		padding: 0;
		border-radius: 3px*@ip6;
		border: 1px solid #14151d;
		color: white;
		outline: none;
		background-color: #1b1b26;
		text-align: center;
		margin: 0;
	}
	.sellong {
		padding: 0 10px*@ip6;
		width: 95px*@ip6;
		height: 33px*@ip6;
		border-radius: 3px*@ip6;
		border: 1px solid #14151d;
		color: white;
		outline: none;
		background-color: #1b1b26;
	}
	.selshort {
		padding: 0 10px*@ip6;
		width: 55px*@ip6;
		height: 33px*@ip6;
		border-radius: 3px*@ip6;
		border: 1px solid #14151d;
		color: white;
		outline: none;
		background-color: #1b1b26;
	}
	.bg>ul>li {
		width: 100%;
		height: 44px*@ip6;
		border-top: 1px solid #1c1c27;
	}
	.bg>ul>li li {
		line-height: 44px*@ip6;
	}
	.bg>div:last-child {
		position: absolute;
		bottom: 0;
	}
	.bg>div>div.current {
		color: #fcc900;
	}
}
/*ip5*/
@media(max-width:370px) {
	@width: 330px*@ip5;
	@height: 226px*@ip5;
	#stopmoneyalert {
		position: fixed;
		top: 0;
		left: 0;
		right: 0;
		bottom: 0;
		background-color: rgba(0, 0, 0, .8);
		font-size: 16px*@ip5;
		z-index: 1100;
	}
	.bg {
		width: @width;
		height: @height;
		background-color: #1b1b26;
		position: fixed;
		top: 212px*@ip5;
		left: 40px*@ip5;
		position: relative;
		box-shadow: 1px 1px 3px rgba(0, 0, 0, 0.3), -1px -1px 3px rgba(0, 0, 0, 0.3);
		border-radius: 5px*@ip5;
	}
	.bg>div {
		width: 100%;
		height: 44px*@ip5;
		background-color: #242633;
		border-top-left-radius: 5px*@ip5;
		border-top-right-radius: 5px*@ip5;
	}
	.bg>ul {
		background-color: #242633;
		width: 100%;
		height: 132px*@ip5;
	}
	.bg>div:after {
		content: '';
		display: div;
		clear: both;
	}
	.bg>div:first-child>div {
		color: #949bbb;
		width: 50%;
		height: 44px*@ip5;
		text-align: center;
		line-height: 44px*@ip5;
	}
	.bg>div:last-child>div {
		color: #949bbb;
		width: 50%;
		height: 44px*@ip5;
		text-align: center;
		line-height: 44px*@ip5;
	}
	.bg>div:last-child {
		border-bottom-left-radius: 5px*@ip5;
		border-bottom-right-radius: 5px*@ip5;
	}
	ol {
		height: 44px*@ip5;
		width: 100%;
	}
	ol>li {
		text-align: center;
	}
	ol>li:first-child {
		width: 56px*@ip5;
		border-right: 1px solid #1c1c27;
	}
	ul>li:first-child>ol>li:nth-child(2) {
		width: 111px*@ip5;
		border-right: 1px solid #1c1c27;
	}
	ul>li:first-child>ol>li:nth-child(3) {
		width: 56px*@ip5;
		border-right: 1px solid #1c1c27;
	}
	ul>li:first-child>ol>li:nth-child(4) {
		width: 100px*@ip5;
	}
	ul>li:nth-child(2)>ol>li:nth-child(2) {
		padding-left: 5px*@ip5;
	}
	ul>li:nth-child(3)>ol>li:nth-child(2) {
		width: 110px*@ip5;
		border-right: 1px solid #1c1c27;
	}
	ul>li:nth-child(3)>ol>li:nth-child(3) {
		padding-left: 5px*@ip5;
	}
	.inp {
		width: 95px*@ip5;
		height: 33px*@ip5;
		line-height: 32px*@ip5;
		padding: 0;
		border-radius: 3px*@ip5;
		border: 1px solid #14151d;
		color: white;
		outline: none;
		background-color: #1b1b26;
		text-align: center;
		margin: 0;
	}
	.sellong {
		padding: 0 10px*@ip5;
		width: 95px*@ip5;
		height: 33px*@ip5;
		border-radius: 3px*@ip5;
		border: 1px solid #14151d;
		color: white;
		outline: none;
		background-color: #1b1b26;
	}
	.selshort {
		padding: 0 10px*@ip5;
		width: 55px*@ip5;
		height: 33px*@ip5;
		border-radius: 3px*@ip5;
		border: 1px solid #14151d;
		color: white;
		outline: none;
		background-color: #1b1b26;
	}
	.bg>ul>li {
		width: 100%;
		height: 44px*@ip5;
		border-top: 1px solid #1c1c27;
	}
	.bg>ul>li li {
		line-height: 44px*@ip5;
	}
	.bg>div:last-child {
		position: absolute;
		bottom: 0;
	}
	.bg>div>div.current {
		color: #fcc900;
	}
}
</style>