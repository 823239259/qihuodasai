<template>
	<div id="ifalert" v-if="isshow">
		<div>
			<ul class="selectbar">
				<li class="fontgray fl selected" @tap="selection">时间条件</li>
			</ul>
			<ul class="content">
				<li>
					<ol>
						<li class="fontgray">合约</li>
						<li>
							<select name="contract" class="selectlong fontwhite" v-model="selectTimeId">
								<option v-for="v in parameters" :value="v.CommodityNo+v.MainContract">{{v.CommodityName}}</option>
							</select>
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
							<input type="text" v-model="timeAddtionPrice" />
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
								<option value="2">限价</option>
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
				
			}
		},
		props: ['objstr'],
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
				return this.objstr;
			},
		},
		watch:{
			
			objstrParms:function(n,o){
				console.log(n);
				
				let sb= JSON.parse(n);
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
		},
		methods:{
			selection:function(e){
				if(e.target.innerHTML == '价格条件'){
					this.ifshow=true;
					$('#ifalert>div').css('height',this.height1+'px');
				}else{
					this.ifshow=false;
					$('#ifalert>div').css('height',this.height2+'px');
				}
			},
			close: function() {
				this.isshow = false;
			},
			confirm: function() {
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
				this.isshow = false;
				let mmp = JSON.parse(this.objstrParms);
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
							'Direction':parseInt(this.timeBuyOrSell),
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
				this.tradeSocket.send(JSON.stringify(b));	
				
			}
		},
		mounted:function(){
			
		}
	}
</script>

<style scoped lang="less">
@import url("../assets/css/main.less");
@width: 330px;
@height: 265px;
/*ip6p及以上*/
@media (min-width:411px) {
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
	#ifalert>div {
		width: @width;
		background-color: #1b1b26;
		position: fixed;
		top: 212px;
		left: 40px;
		position: relative;
		box-shadow: 1px 1px 3px rgba(0, 0, 0, 0.3), -1px -1px 3px rgba(0, 0, 0, 0.3);
		border-radius: 5px;
	}
	.selectbar {
		width: 100%;
		height: @selectbar_height;
		background-color: #242633;
		border-top-left-radius: 5px;
		border-top-right-radius: 5px;
	}
	@selectbar_height: 43px;
	.selectbar li {
		width: 50%;
		text-align: center;
		line-height: @selectbar_height;
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
	
	.content>li {
		height: @selectbar_height;
		line-height: @selectbar_height;
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
		height: @selectbar_height;
		line-height: @selectbar_height;
		border-bottom-left-radius: 5px;
		border-bottom-right-radius: 5px;
	}
	
	ol>li {
		float: left;
	}
	
	ol>li:first-child {
		width: 53px;
		border-right: 1px solid #20212d;
	}
	
	ol {
		height: @selectbar_height;
	}
	
	li {
		text-align: center;
		background-color: #242633;
	}
	
	.selectshort {
		width: 55px;
		height: 32px;
		background-color: #1b1b26;
		text-indent: 0.8em;
		transform: translateY(-1.5px);
		margin-left: 5px;
		background-image: url('../assets/img/sanjiao.png');
		background-repeat: no-repeat;
		background-size: 5px;
		background-position: 45px 21px;
		border-radius: 3px;
	}
	
	.selectlong {
		width: 99px;
		height: 32px;
		background-color: #1b1b26;
		text-indent: 1.5em;
		transform: translateY(-1px);
		margin-left: 5px;
		border-radius: 3px;
	}
	
	input {
		width: 55px;
		height: 32px;
		background-color: #1b1b26;
		color: white;
		font-size: 15px;
		text-align: center;
		margin-left: 5px;
		margin-bottom: 0;
		border-radius: 3px;
	}
	
	.today {
		width: 275px;
		text-align: center;
	}
	
	ul>li:nth-child(2)>ol>li:nth-child(2),
	ul>li:nth-child(3)>ol>li:nth-child(2) {
		border-right: 1px solid #20212d;
		padding-right: 2%;
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
		width: 99px;
		height: 32px;
		color: white;
	}
}
/*ip6*/
@media (min-width:371px) and (max-width:410px) {
	#ifalert {
		position: fixed;
		top: 0;
		left: 0;
		right: 0;
		bottom: 0;
		z-index: 1010;
		background-color: rgba(0, 0, 0, .8);
		font-size: 15px*@ip6;
	}
	#ifalert>div {
		width: @width*@ip6;
		height: @height*@ip6;
		background-color: #1b1b26;
		position: fixed;
		top: 212px*@ip6;
		left: 40px*@ip6;
		position: relative;
		box-shadow: 1px 1px 3px rgba(0, 0, 0, 0.3), -1px -1px 3px rgba(0, 0, 0, 0.3);
		border-top-left-radius: 5px;
		border-top-right-radius: 5px;
		border-bottom-left-radius: 5px;
		border-bottom-right-radius: 5px;
	}
	.selectbar {
		width: 100%;
		height: @selectbar_height*@ip6;
		background-color: #242633;
		border-top-left-radius: 5px;
		border-top-right-radius: 5px;
	}
	@selectbar_height: 43px;
	.selectbar li {
		width: 50%;
		text-align: center;
		line-height: @selectbar_height*@ip6;
		border-top-left-radius: 5px;
		border-top-right-radius: 5px;
	}
	.selected {
		color: #ffe100;
	}
	.content {
		background-color: #1b1b26;
		height: 220px*@ip6;
		border-top: 1px solid #20212d;
		border-bottom-left-radius: 5px;
		border-bottom-right-radius: 5px;
	}
	.content>li {
		height: @selectbar_height*@ip6;
		line-height: @selectbar_height*@ip6;
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
		height: @selectbar_height*@ip6;
		line-height: @selectbar_height*@ip6;
		border-bottom-left-radius: 5px;
		border-bottom-right-radius: 5px;
	}
	ol>li {
		float: left;
	}
	ol>li:first-child {
		width: 53px*@ip6;
		border-right: 1px solid #20212d;
	}
	ol {
		height: @selectbar_height*@ip6;
	}
	li {
		text-align: center;
		background-color: #242633;
	}
	.selectshort {
		width: 55px*@ip6;
		height: 32px*@ip6;
		background-color: #1b1b26;
		text-indent: 0.8em*@ip6;
		transform: translateY(-1.5px*@ip6);
		margin-left: 5px*@ip6;
		background-image: url('../assets/img/sanjiao.png');
		background-repeat: no-repeat;
		background-size: 5px*@ip6;
		background-position: 45px*@ip6 21px*@ip6;
		border-radius: 3px;
	}
	.selectlong {
		width: 99px*@ip6;
		height: 32px*@ip6;
		background-color: #1b1b26;
		text-indent: 1.5em*@ip6;
		transform: translateY(-1px*@ip6);
		margin-left: 5px*@ip6;
		border-radius: 3px;
	}
	input {
		width: 55px*@ip6;
		height: 32px*@ip6;
		background-color: #1b1b26;
		color: white;
		font-size: 15px*@ip6;
		text-align: center;
		margin-left: 5px*@ip6;
		margin-bottom: 0;
		border-radius: 3px;
	}
	.today {
		width: 275px*@ip6;
		text-align: center;
	}
	ul>li:nth-child(2)>ol>li:nth-child(2),
	ul>li:nth-child(3)>ol>li:nth-child(2) {
		border-right: 1px solid #20212d;
		padding-right: 2%;
	}
	.lot {
		margin-left: 20px*@ip6;
	}
	.time{
		width: 99px*@ip6;
		height: 32px*@ip6;
		color: white;
	}
}
/*ip5*/
@media(max-width:370px) {
	#ifalert {
		position: fixed;
		top: 0;
		left: 0;
		right: 0;
		bottom: 0;
		z-index: 1010;
		background-color: rgba(0, 0, 0, .8);
		font-size: 15px*@ip5;
	}
	#ifalert>div {
		width: @width*@ip5;
		height: @height*@ip5;
		background-color: #1b1b26;
		position: fixed;
		top: 212px*@ip5;
		left: 40px*@ip5;
		position: relative;
		box-shadow: 1px 1px 3px rgba(0, 0, 0, 0.3), -1px -1px 3px rgba(0, 0, 0, 0.3);
		border-top-left-radius: 5px;
		border-top-right-radius: 5px;
		border-bottom-left-radius: 5px;
		border-bottom-right-radius: 5px;
	}
	.selectbar {
		width: 100%;
		height: @selectbar_height*@ip5;
		background-color: #242633;
		border-top-left-radius: 5px;
		border-top-right-radius: 5px;
	}
	@selectbar_height: 43px;
	.selectbar li {
		width: 50%;
		text-align: center;
		line-height: @selectbar_height*@ip5;
		border-top-left-radius: 5px;
		border-top-right-radius: 5px;
	}
	.selected {
		color: #ffe100;
	}
	.content {
		background-color: #1b1b26;
		height: 220px*@ip5;
		border-top: 1px solid #20212d;
		border-bottom-left-radius: 5px;
		border-bottom-right-radius: 5px;
	}
	.content>li {
		height: @selectbar_height*@ip5;
		line-height: @selectbar_height*@ip5;
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
		height: @selectbar_height*@ip5;
		line-height: @selectbar_height*@ip5;
		border-bottom-left-radius: 5px;
		border-bottom-right-radius: 5px;
	}
	ol>li {
		float: left;
	}
	ol>li:first-child {
		width: 53px*@ip5;
		border-right: 1px solid #20212d;
	}
	ol {
		height: @selectbar_height*@ip5;
	}
	li {
		text-align: center;
		background-color: #242633;
	}
	.selectshort {
		width: 55px*@ip5;
		height: 32px*@ip5;
		background-color: #1b1b26;
		text-indent: 0.8em*@ip5;
		transform: translateY(-1.5px*@ip5);
		margin-left: 5px*@ip5;
		background-image: url('../assets/img/sanjiao.png');
		background-repeat: no-repeat;
		background-size: 5px*@ip5;
		background-position: 45px*@ip5 21px*@ip5;
		border-radius: 3px;
		font-size: 12px;
	}
	.selectlong {
		width: 99px*@ip5;
		height: 32px*@ip5;
		background-color: #1b1b26;
		text-indent: 1.5em*@ip5;
		transform: translateY(-1px*@ip5);
		margin-left: 5px*@ip5;
		border-radius: 3px;
		font-size: 12px;
	}
	input {
		width: 55px*@ip5;
		height: 32px*@ip5;
		background-color: #1b1b26;
		color: white;
		font-size: 12px;
		;
		text-align: center;
		margin-left: 5px*@ip5;
		margin-bottom: 0;
		border-radius: 3px;
	}
	.today {
		width: 275px*@ip5;
		text-align: center;
	}
	ul>li:nth-child(2)>ol>li:nth-child(2),
	ul>li:nth-child(3)>ol>li:nth-child(2) {
		border-right: 1px solid #20212d;
		padding-right: 2%;
	}
	.lot {
		margin-left: 20px*@ip5;
	}
	.time{
		width: 99px*@ip5;
		height: 32px*@ip5;
		color: white;
	}
}
</style>