<template>
	<div id="ifalert" v-if="isshow">
		<alert title="提示" :line1="tipsAlert" :objstr="sendMsg" ref="alert"></alert>
		<tipsDialog :msg="msgTips" ref="dialog"></tipsDialog>
		<div class="ifalert_box">
			<ul class="selectbar">
				<li class="fontgray fl" :class="{selected: ifshow}">价格条件</li>
				<!--<li class="fontgray fl" :class="{selected: !ifshow}"@tap="selection">时间条件</li>-->
			</ul>
			<ul class="content">
				<!--<template v-if="ifshow">-->
					<li>
						<ol>
							<li class="fontgray">合约</li>
							<li>
								<input type="text" v-model="selectId" class="selectlong fontwhite" disabled />
							</li>
							<li>
								<span class="fontgray">最新：</span>
								<span class="white">{{lastPrice}}</span>
							</li>
						</ol>
					</li>
					<li>
						<ol>
							<li class="fontgray">价格</li>
							<li>
								<select class="fontwhite selectshort" v-model="selectPrice">
									<option value="0">&nbsp;&nbsp;></option>
									<option value="2">&nbsp;&nbsp;>=</option>
									<option value="1">&nbsp;&nbsp;<</option>
									<option value="3">&nbsp;&nbsp;<=</option>
								</select>
								<input type="text" v-model="inputPrice" class="fontwhite" />
							</li>
							<li>
								<select class="fontwhite selectshort" v-model="selectAdditionalPrice">
									<option value="5">附加</option>
									<option value="0">></option>
									<option value="2">>=</option>
									<option value="1"><</option>
									<option value="3"><=</option>
								</select>
								<input type="text" disabled="disabled" class="additionalPrice" v-model="inputAdditionalPrice" />
							</li>
						</ol>
					</li>
					<li>
						<ol>
							<li class="fontgray">操作</li>
							<li>
								<select class="fontwhite  selectshort" v-model="selectBuyOrSell">
									<option value="0">&nbsp;&nbsp;买</option>
									<option value="1">&nbsp;&nbsp;卖</option>
								</select>
								<select class="fontwhite selectshort" v-model="selectMarketOrLimited ">
									<option value="1">市价</option>
									<option value="2">对手价</option>
								</select>
								<span class="fontgray lot">手数</span>
							</li>
							<li>
								<input type="number"  v-model="holdNum" class="fontwhite" />
							</li>
						</ol>
					</li>
					<li>
						<ol>
							<li class="fontgray">有效</li>
							<li class="fontwhite today">
								当日有效
							</li>
						</ol>
					</li>
				<li class="do">
					<div class="fontgray" @tap="close">关闭</div>
					<div class="fontgray" @tap='confirm'>修改</div>
				</li>
			</ul>
		</div>
	</div>
</template>

<script>
	import tipsDialog from './tipsDialog.vue'
	import alert from './Tradealert.vue'
	export default {
		name: 'ifalert',
		data(){
			return {
				ifshow:true,
				isshow:false,
				selectId:'',
				selectPrice:'',
				commodityNo:'',
				contractNo:'',
				selectPrice:'',
				selectAdditionalPrice:'',
				inputPrice:'',
				inputAdditionalPrice:'',
				selectBuyOrSell:'',
				selectMarketOrLimited:'',
				holdNum:1,
				additionFlag:false,
				addtionPrice:'',
				lastPrice: '0.00',
				
				timeAddtionPrice:'',
				timeAddtionPrice00:'',
				time:'',
				timeAdditionFlag:false,
				timeHoldNum:1,
				commodityNo00:'',
				contractNo00:'',
				selectTimeId:'',
				timeOrderType:1,
				timeBuyOrSell:0,
				additionValue:'',
				tipsMsg: '',
				str: '',
				msg: '',
				moneyReg: /^(([1-9]\d*)|0)(\.\d*)?$/
			}
		},
		props: ['objstr'],
		components: {alert, tipsDialog},
		computed:{
//			height1(){
//				return $('#ifalert>div').css('height').slice(0,-2);
//			},
//			height2(){
//				return this.height1*1.1585;
//			},
			parameters(){
				return this.$store.state.market.Parameters;
			},
			orderTemplist(){
				return	this.$store.state.market.orderTemplist;
			},
			templateList(){
				return this.$store.state.market.templateList;
			},
			tradeSocket() {
				return this.$store.state.tradeSocket;
			},
			objstrParms: function(){
				if(this.objstr) return JSON.parse(this.objstr);
			},
			tipsAlert: function(){
				return this.tipsMsg;
			},
			msgTips: function(){
				return this.msg;
			},
			sendMsg: function(){
				if(this.str) return JSON.stringify(this.str);
			},
			miniTikeSize(){
				return this.orderTemplist[this.objstrParms.CommodityNo].MiniTikeSize;
			}
		},
		watch:{
			parameters:function(n,o){
				if(this.objstrParms != undefined){
					n.forEach(function(e,i){
						if(this.objstrParms.CommodityNo == e.CommodityNo){
							this.lastPrice = this.orderTemplist[this.objstrParms.CommodityNo].LastQuotation.LastPrice;
						}
					}.bind(this));
				}
			},
			objstrParms:function(n,o){
				let sb = n;
				//价格条件
				this.selectId = sb.CommodityNo+sb.ContractNo;
				this.selectPrice = sb.CompareType;
				this.inputPrice = sb.PriceTriggerPonit;
				if(sb.AdditionFlag==0){ //没有附件条件
					this.selectAdditionalPrice = 5
					this.inputAdditionalPrice = '';
				}else{//有附加条件
					this.selectAdditionalPrice = sb.AdditionType;
					this.inputAdditionalPrice = sb.AdditionPrice;
				}
					
				this.selectBuyOrSell = sb.Drection;
				this.selectMarketOrLimited = sb.OrderType;
				this.holdNum = sb.Num;
			},
			selectAdditionalPrice: function(n,o){
				if(this.selectAdditionalPrice == 5){
					 $(".additionalPrice").attr("disabled","disabled");
					 this.inputAdditionalPrice = '';
				}else{
					$(".additionalPrice").removeAttr("disabled");
					this.inputAdditionalPrice = this.inputPrice;
				}
			},
			inputPrice: function(n, o){
				if(n != undefined && this.moneyReg.test(n) == false){
					this.inputPrice = parseFloat(this.templateList[this.objstrParms.CommodityNo].LastPrice).toFixed(this.orderTemplist[this.objstrParms.CommodityNo].DotSize);
				}
			},
			inputAdditionalPrice: function(n, o){
				if(n != undefined && this.moneyReg.test(n) == false){
					this.inputAdditionalPrice = '';
				}
			},
		},
		methods:{
			close: function() {
				this.isshow = false;
			},
			confirm: function() {
				if(this.inputAdditionalPrice){
					var d1 = this.inputAdditionalPrice % this.miniTikeSize;
					if(d1 >= 0.000000001 && parseFloat(this.miniTikeSize-d1) >= 0.0000000001){
						this.$refs.dialog.isShow = true;
						this.msg = '输入附加价格不符合最小变动价，最小变动价为：' + this.miniTikeSize;
						return;
					}
				}
				//判断价格与附加价格是否形成区间
				switch (this.selectPrice){
					case 0:
						if(this.selectAdditionalPrice == 0 || this.selectAdditionalPrice == 2 || this.inputAdditionalPrice &&  this.inputAdditionalPrice <= this.inputPrice){
							this.$refs.dialog.isShow = true;
							this.msg = '附加条件添加错误';
							return;
						}
						break;
					case 2:
						if(this.selectAdditionalPrice == 0 || this.selectAdditionalPrice == 2 || this.inputAdditionalPrice &&  this.inputAdditionalPrice <= this.inputPrice){
							this.$refs.dialog.isShow = true;
							this.msg = '附加条件添加错误';
							return;
						}
						break;
					case 1:
						if(this.selectAdditionalPrice == 1 || this.selectAdditionalPrice == 3 || this.inputAdditionalPrice &&  this.inputAdditionalPrice >= this.inputPrice){
							this.$refs.dialog.isShow = true;
							this.msg = '附加条件添加错误';
							return;
						}
						break;
					case 3:
						if(this.selectAdditionalPrice == 1 || this.selectAdditionalPrice == 3 || this.inputAdditionalPrice &&  this.inputAdditionalPrice >= this.inputPrice){
							this.$refs.dialog.isShow = true;
							this.msg = '附加条件添加错误';
							return;
						}
						break;
					default:
						break;
				}
				var d0 = this.inputPrice % this.miniTikeSize;
				if(this.inputPrice == '' || this.inputPrice == 0 || this.inputPrice == undefined){
					this.$refs.dialog.isShow = true;
					this.msg = '请输入价格';
				}else if(d0 >= 0.000000001 && parseFloat(this.miniTikeSize-d0) >= 0.0000000001){
					this.$refs.dialog.isShow = true;
					this.msg = '输入价格不符合最小变动价，最小变动价为：' + this.miniTikeSize;
				}else if(this.holdNum == '' || this.holdNum == 0 || this.holdNum == undefined){
					this.$refs.dialog.isShow = true;
					this.msg = '请输入手数';
				}else{
					this.$refs.alert.isshow = true;
					this.tipsMsg = '是否修改价格条件单？';
					function getNowFormatDate() {
					    let date = new Date();
					    let seperator1 = "-";
					    let month = date.getMonth() + 1;
					    let strDate = date.getDate();
					    if (month >= 1 && month <= 9) {
					        month = "0" + month;
					    }
					    if (strDate >= 0 && strDate <= 9) {
					        strDate = "0" + strDate;
					    }
					    let currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate;
					    return currentdate;
					}
					let dateTime= getNowFormatDate()+' '+this.time+':'+new Date().getSeconds();
					let mmp = this.objstrParms;
					let b = {
						"Method":'ModifyCondition',
						"Parameters":{
							'ConditionNo':mmp.ConditionNo,
							'ModifyFlag':0,
							'Num':parseInt(this.holdNum),
							'ConditionType':0,
							'PriceTriggerPonit':parseFloat(this.inputPrice),
							'CompareType':parseInt(this.selectPrice),
							'TimeTriggerPoint':'',
							'AB_BuyPoint':0.0,
							'AB_SellPoint':0.0,
							'OrderType':parseInt(this.selectMarketOrLimited),
							'StopLossType':5,
							'Drection':parseInt(this.selectBuyOrSell),
							'StopLossDiff':0.0,
							'StopWinDiff':0.0,
							'AdditionFlag':(function(){
												if(this.selectAdditionalPrice==5){
													return false;
												}else{
													return true;
												}
										}.bind(this))(),
							'AdditionType':parseInt(this.selectAdditionalPrice),
							'AdditionPrice':parseFloat(this.inputAdditionalPrice)
							
						}
					};
	//				this.tradeSocket.send(JSON.stringify(b));		
					this.str = b;
				}
			}
		},
		mounted:function(){
		}
	}
</script>

<style scoped lang="less">
@import url("../assets/css/main.less");
.white{
	color: white;
}
/*ip6p及以上*/
@media (min-width:411px) {
	@width: 330px;
	@height: 265px;
	#ifalert {
		position: fixed;
		top: 0;
		left: 0;
		right: 0;
		bottom: 0;
		z-index: 1010;
		background-color: rgba(0, 0, 0, .5);
		font-size: 14px;
	}
	#ifalert .ifalert_box{
		width: @width;
		background-color: #1b1b26;
		position: fixed;
		top: 212px;
		left: 40px;
		position: relative;
		box-shadow: 1px 1px 3px rgba(0, 0, 0, 0.3), -1px -1px 3px rgba(0, 0, 0, 0.3);
		border-radius: 5px;
	}
	.selectbar{
		width: 100%;
		height: 42px;
		background-color: #242633;
		border-top-left-radius: 5px;
		border-top-right-radius: 5px;
	}
	.selectbar li {
		width: 50%;
		text-align: center;
		line-height: 42px;
		border-top-left-radius: 5px;
		border-top-right-radius: 5px;
	}
	.selected {
		color: #ffe100;
	}
	.content {
		background-color: #1b1b26;
		height: 220px;
		border-top: 1px solid #20212d;
		border-bottom-left-radius: 5px;
		border-bottom-right-radius: 5px;
	}
	.content>li{
		height: 43px;
		line-height: 42px;
		overflow: hidden;
		border-bottom: 1px solid #20212d;
		border-bottom-left-radius: 5px;
		border-bottom-right-radius: 5px;
	}
	.content>li:last-child {
		width: 100%;
		position: absolute;
		bottom: 0;
	}
	.content>li:last-child>div {
		float: left;
		width: 50%;
		height: 42px;
		line-height: 42px;
		border-bottom-left-radius: 5px;
		border-bottom-right-radius: 5px;
	}
	ol>li {
		float: left;
		border-right: 1px solid #1b1b26;
	}
	ol>li:last-child{
		border: none;
	}
	ol>li:first-child {
		width: 53px;
	}
	ol{
		height: 42px;
	}
	li{
		text-align: center;
		background-color: #242633;
	}
	.selectshort {
		width: 55px;
		height: 32px;
		background-color: #1b1b26;
		transform: translateY(-1.5px);
		padding: 0 8px;
		margin-left: 5px;
		background-image: url('../assets/img/sanjiao.png');
		background-repeat: no-repeat;
		background-size: 5px;
		background-position: 45px 21px;
		border-radius: 3px;
	}
	.selectlong {
		width: 135px;
		height: 32px;
		padding: 0 10px;
		text-align: center;
		background-color: #1b1b26;
		margin-left: 5px;
		border-radius: 3px;
	}
	input {
		width: 55px;
		height: 32px;
		background-color: #1b1b26;
		color: white;
		font-size: 16px;
		text-align: center;
		margin-left: 5px;
		margin-bottom: 0;
		padding: 0;
		border-radius: 3px;
	}
	.today {
		width: 275px;
		text-align: center;
	}
	ul>li:nth-child(1)>ol>li:nth-child(2),
	ul>li:nth-child(2)>ol>li:nth-child(2),
	ul>li:nth-child(3)>ol>li:nth-child(2) {
		padding-right: 8px;
	}
	ul>li:nth-child(1)>ol>li:nth-child(3){
		padding-left: 8px;
	}
	.lot {
		margin-left: 20px;
	}
	.do {
		border-bottom-left-radius: 5px;
		border-bottom-right-radius: 5px;
	}
	.do>div {
		border-bottom-left-radius: 5px;
		border-bottom-right-radius: 5px;
	}
	.time{
		display: inline-block;
		vertical-align: middle;
		width: 95px;
		height: 32px;
		color: white;
		padding: 0 5px;
	}
}
/*ip6*/
@media (min-width:371px) and (max-width:410px) {
	@width: 330px*@ip6;
	@height: 265px*@ip6;
	#ifalert {
		position: fixed;
		top: 0;
		left: 0;
		right: 0;
		bottom: 0;
		z-index: 1010;
		background-color: rgba(0, 0, 0, .5);
		font-size: 14px*@ip6;
	}
	#ifalert .ifalert_box{
		width: @width;
		background-color: #1b1b26;
		position: fixed;
		top: 212px*@ip6;
		left: 40px*@ip6;
		position: relative;
		box-shadow: 1px 1px 3px rgba(0, 0, 0, 0.3), -1px -1px 3px rgba(0, 0, 0, 0.3);
		border-radius: 5px*@ip6;
	}
	.selectbar{
		width: 100%;
		height: 42px*@ip6;
		background-color: #242633;
		border-top-right-radius: 5px*@ip6;
		border-top-left-radius: 5px*@ip6;
	}
	.selectbar li {
		width: 50%;
		text-align: center;
		line-height: 42px*@ip6;
		border-top-left-radius: 5px*@ip6;
		border-top-right-radius: 5px*@ip6;
	}
	.selected {
		color: #ffe100;
	}
	.content {
		background-color: #1b1b26;
		height: 220px*@ip6;
		border-top: 1px solid #20212d;
		border-bottom-left-radius: 5px*@ip6;
		border-bottom-right-radius: 5px*@ip6;
	}
	.content>li{
		height: 43px*@ip6;
		line-height: 42px*@ip6;
		overflow: hidden;
		border-bottom: 1px solid #20212d;
		border-bottom-left-radius: 5px*@ip6;
		border-bottom-right-radius: 5px*@ip6;
	}
	.content>li:last-child {
		width: 100%;
		position: absolute;
		bottom: 0;
	}
	.content>li:last-child>div {
		float: left;
		width: 50%;
		height: 42px*@ip6;
		line-height: 42px*@ip6;
		border-bottom-left-radius: 5px*@ip6;
		border-bottom-right-radius: 5px*@ip6;
	}
	ol>li {
		float: left;
		border-right: 1px solid #1b1b26;
	}
	ol>li:last-child{
		border: none;
	}
	ol>li:first-child {
		width: 53px*@ip6;
	}
	ol{
		height: 42px*@ip6;
	}
	li{
		text-align: center;
		background-color: #242633;
	}
	.selectshort {
		width: 55px*@ip6;
		height: 32px*@ip6;
		background-color: #1b1b26;
		transform: translateY(-1.5px);
		padding: 0 8px*@ip6;
		margin-left: 5px*@ip6;
		background-image: url('../assets/img/sanjiao.png');
		background-repeat: no-repeat;
		background-size: 5px*@ip6;
		background-position: 45px*@ip6 21px*@ip6;
		border-radius: 3px*@ip6;
	}
	.selectlong {
		width: 135px*@ip6;
		height: 32px*@ip6;
		padding: 0 10px*@ip6;
		text-align: center;
		background-color: #1b1b26;
		margin-left: 5px*@ip6;
		border-radius: 3px*@ip6;
	}
	input {
		width: 55px*@ip6;
		height: 32px*@ip6;
		background-color: #1b1b26;
		color: white;
		font-size: 16px*@ip6;
		text-align: center;
		margin-left: 5px*@ip6;
		margin-bottom: 0;
		padding: 0;
		border-radius: 3px*@ip6;
	}
	.today {
		width: 275px*@ip6;
		text-align: center;
	}
	ul>li:nth-child(1)>ol>li:nth-child(2),
	ul>li:nth-child(2)>ol>li:nth-child(2),
	ul>li:nth-child(3)>ol>li:nth-child(2) {
		padding-right: 8px*@ip6;
	}
	ul>li:nth-child(1)>ol>li:nth-child(3){
		padding-left: 8px*@ip5;
	}
	.lot {
		margin-left: 20px*@ip6;
	}
	.do {
		border-bottom-left-radius: 5px*@ip6;
		border-bottom-right-radius: 5px*@ip6;
	}
	.do>div {
		border-bottom-left-radius: 5px*@ip6;
		border-bottom-right-radius: 5px*@ip6;
	}
	.time{
		display: inline-block;
		vertical-align: middle;
		width: 95px*@ip6;
		height: 32px*@ip6;
		color: white;
		padding: 0 5px*@ip6;
	}
}
/*ip5*/
@media(max-width:370px) {
	@width: 330px*@ip5;
	@height: 265px*@ip5;
	#ifalert {
		position: fixed;
		top: 0;
		left: 0;
		right: 0;
		bottom: 0;
		z-index: 1010;
		background-color: rgba(0, 0, 0, .5);
		font-size: 14px*@ip5;
	}
	#ifalert .ifalert_box{
		width: @width;
		background-color: #1b1b26;
		position: fixed;
		top: 212px*@ip5;
		left: 40px*@ip5;
		position: relative;
		box-shadow: 1px 1px 3px rgba(0, 0, 0, 0.3), -1px -1px 3px rgba(0, 0, 0, 0.3);
		border-radius: 5px*@ip5;
	}
	.selectbar{
		width: 100%;
		height: 42px*@ip5;
		background-color: #242633;
		border-top-right-radius: 5px*@ip5;
		border-top-left-radius: 5px*@ip5;
	}
	.selectbar li {
		width: 50%;
		text-align: center;
		line-height: 42px*@ip5;
		border-top-left-radius: 5px*@ip5;
		border-top-right-radius: 5px*@ip5;
	}
	.selected {
		color: #ffe100;
	}
	.content {
		background-color: #1b1b26;
		height: 220px*@ip5;
		border-top: 1px solid #20212d;
		border-bottom-left-radius: 5px*@ip5;
		border-bottom-right-radius: 5px*@ip5;
	}
	.content>li{
		height: 43px*@ip5;
		line-height: 42px*@ip5;
		overflow: hidden;
		border-bottom: 1px solid #20212d;
		border-bottom-left-radius: 5px*@ip5;
		border-bottom-right-radius: 5px*@ip5;
	}
	.content>li:last-child {
		width: 100%;
		position: absolute;
		bottom: 0;
	}
	.content>li:last-child>div {
		float: left;
		width: 50%;
		height: 42px*@ip5;
		line-height: 42px*@ip5;
		border-bottom-left-radius: 5px*@ip5;
		border-bottom-right-radius: 5px*@ip5;
	}
	ol>li {
		float: left;
		border-right: 1px solid #1b1b26;
	}
	ol>li:last-child{
		border: none;
	}
	ol>li:first-child {
		width: 53px*@ip5;
	}
	ol{
		height: 42px*@ip5;
	}
	li{
		text-align: center;
		background-color: #242633;
	}
	.selectshort {
		width: 55px*@ip5;
		height: 32px*@ip5;
		background-color: #1b1b26;
		transform: translateY(-1.5px);
		padding: 0 8px*@ip5;
		margin-left: 5px*@ip5;
		background-image: url('../assets/img/sanjiao.png');
		background-repeat: no-repeat;
		background-size: 5px*@ip5;
		background-position: 45px*@ip5 21px*@ip5;
		border-radius: 3px*@ip5;
	}
	.selectlong {
		width: 135px*@ip5;
		height: 32px*@ip5;
		padding: 0 10px*@ip5;
		text-align: center;
		background-color: #1b1b26;
		margin-left: 5px*@ip5;
		border-radius: 3px*@ip5;
	}
	input {
		width: 55px*@ip5;
		height: 32px*@ip5;
		background-color: #1b1b26;
		color: white;
		font-size: 16px*@ip5;
		text-align: center;
		margin-left: 5px*@ip5;
		margin-bottom: 0;
		padding: 0;
		border-radius: 3px*@ip5;
	}
	.today {
		width: 275px*@ip5;
		text-align: center;
	}
	ul>li:nth-child(1)>ol>li:nth-child(2),
	ul>li:nth-child(2)>ol>li:nth-child(2),
	ul>li:nth-child(3)>ol>li:nth-child(2) {
		padding-right: 8px*@ip5;
	}
	ul>li:nth-child(1)>ol>li:nth-child(3){
		padding-left: 8px*@ip5;
	}
	.lot {
		margin-left: 20px*@ip5;
	}
	.do {
		border-bottom-left-radius: 5px*@ip5;
		border-bottom-right-radius: 5px*@ip5;
	}
	.do>div {
		border-bottom-left-radius: 5px*@ip5;
		border-bottom-right-radius: 5px*@ip5;
	}
	.time{
		display: inline-block;
		vertical-align: middle;
		width: 95px*@ip5;
		height: 32px*@ip5;
		color: white;
		padding: 0 5px*@ip5;
	}
}
</style>