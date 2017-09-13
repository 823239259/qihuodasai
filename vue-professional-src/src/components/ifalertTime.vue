<template>
	<div id="ifalert" v-if="isshow">
		<alert title="提示" :line1="tipsAlert" :objstr="sendMsg" ref="alert"></alert>
		<tipsDialog :msg="msgTips" ref="dialog"></tipsDialog>
		<div class="ifalert_box">
			<ul class="selectbar">
				<li class="fontgray fl selected">时间条件</li>
			</ul>
			<ul class="content">
				<li>
					<ol>
						<li class="fontgray">合约</li>
						<li>
							<input type="text" v-model="selectTimeId" class="selectlong fontwhite" disabled />
							<!--<select name="contract" class="selectlong fontwhite" v-model="selectTimeId">
								<option v-for="v in parameters" :value="v.CommodityNo+v.MainContract">{{v.CommodityName}}</option>
							</select>-->
						</li>
					</ol>
				</li>
				<li>
					<ol>
						<li class="fontgray">
							时间
						</li>
						<li>
							<input type="time" class="time" v-model="time" />
						</li>
					</ol>
				</li>
				<li>
					<ol>
						<li class="fontgray">
							价格
						</li>
						<li>
							<select class="fontwhite selectshort" v-model="additionValue">
								<option value="5">附加</option>
								<option value="0">></option>
								<option value="2">>=</option>
								<option value="1"><</option>
								<option value="3"><=</option>
							</select>
							<input type="text" disabled="disabled" class="additionalTime" v-model="timeAddtionPrice" />
						</li>
					</ol>
				</li>
				<li>
					<ol>
						<li class="fontgray">操作</li>
						<li>
							<select class="fontwhite  selectshort" v-model="timeBuyOrSell">
								<option value="0">&nbsp;&nbsp;买</option>
								<option value="1">&nbsp;&nbsp;卖</option>
							</select>
							<select class="fontwhite selectshort" v-model="timeOrderType">
								<option value="1">市价</option>
								<option value="2">对手价</option>
							</select>
							<span class="fontgray lot">手数</span>
						</li>
						<li>
							<input type="text" v-model="timeHoldNum" class="fontwhite" />
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
			height1(){
				return $('#ifalert>div').css('height').slice(0,-2);
			},
			height2(){
				return this.height1*1.1585;
			},
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
			objstrParms:function(n,o){
				let sb= n;
				this.selectTimeId = sb.CommodityNo+sb.ContractNo;
				let time00 = sb.TimeTriggerPoint.split(' ')[1];
				this.time = time00.split(':')[0]+':'+time00.split(':')[1];
				
				if(sb.AdditionFlag==0){ //没有附加价格
					this.additionValue = 5;
					this.timeAddtionPrice = '';
				}else{//有附加价格
					this.additionValue = sb.AdditionType;
					this.timeAddtionPrice = sb.AdditionPrice;
				}
				
				this.timeBuyOrSell = sb.Drection;
				this.timeOrderType = sb.OrderType;
				this.timeHoldNum = sb.Num;
			},
			additionValue: function(n,o){
				if(this.additionValue == 5){
					$(".additionalTime").attr("disabled","disabled");
					this.timeAddtionPrice = '';
				}else{
					$(".additionalTime").removeAttr("disabled");
					let size = this.orderTemplist[this.objstrParms.CommodityNo].DotSize;
					this.timeAddtionPrice = parseFloat(this.templateList[this.objstrParms.CommodityNo].LastPrice).toFixed(size);
				}
			},
			timeAddtionPrice: function(n, o){
				if(n != undefined && this.moneyReg.test(n) == false){
					this.timeAddtionPrice = '';
				}
			},
		},
		methods:{
			close: function() {
				this.isshow = false;
			},
			confirm: function() {
				if(this.timeAddtionPrice){
					var d2 = this.timeAddtionPrice % this.miniTikeSize;
					if(d2 >= 0.000000001 && parseFloat(this.miniTikeSize-d2) >= 0.0000000001){
						this.$refs.dialog.isShow = true;
						this.msg = '输入附加价格不符合最小变动价，最小变动价为：' + this.miniTikeSize;
						return;
					}
				}
				if(this.timeHoldNum == '' || this.timeHoldNum == 0 || this.timeHoldNum == undefined){
					this.$refs.dialog.isShow = true;
					this.msg = '请输入手数';
					return;
				}
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
//				this.isshow = false;
				let mmp = this.objstrParms;
				let b={
						"Method":'ModifyCondition',
						"Parameters":{
							'ConditionNo':mmp.ConditionNo,
							'ModifyFlag':0,
							'Num':parseInt(this.timeHoldNum),
							'ConditionType':1,
							'PriceTriggerPonit':0.00,
							'CompareType':5,
							'TimeTriggerPoint':dateTime,
							'AB_BuyPoint':0.0,
							'AB_SellPoint':0.0,
							'OrderType':parseInt(this.timeOrderType),
							'StopLossType':5,
							'Drection':parseInt(this.timeBuyOrSell),
							'StopLossDiff':0.0,
							'StopWinDiff':0.0,
							'AdditionFlag':(function(){
												if(this.additionValue==5){
													return false;
												}else{
													return true;
												}
										}.bind(this))(),
							'AdditionType':parseInt(this.additionValue),
							'AdditionPrice':parseFloat(this.timeAddtionPrice)
							
						}
					};
//				this.tradeSocket.send(JSON.stringify(b));	
				this.str = b;
			}
		},
		mounted:function(){
			
		}
	}
</script>

<style scoped lang="less">
@import url("../assets/css/main.less");
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
	ul>li:nth-child(2)>ol>li:nth-child(2),
	ul>li:nth-child(3)>ol>li:nth-child(2) {
		padding-right: 8px;
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
	ul>li:nth-child(2)>ol>li:nth-child(2),
	ul>li:nth-child(3)>ol>li:nth-child(2) {
		padding-right: 8px*@ip6;
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
	ul>li:nth-child(2)>ol>li:nth-child(2),
	ul>li:nth-child(3)>ol>li:nth-child(2) {
		padding-right: 8px*@ip5;
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