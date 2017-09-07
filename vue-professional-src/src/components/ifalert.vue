<template>
	<div id="ifalert" v-if="isshow">
		<div>
			<ul class="selectbar">
				<li class="fontgray fl" :class="{selected: ifshow}" @tap="selection">价格条件</li>
				<li class="fontgray fl" :class="{selected: !ifshow}"@tap="selection">时间条件</li>
			</ul>
			<ul class="content">
				<template v-if="ifshow">
					<li>
						<ol>
							<li class="fontgray">合约</li>
							<li>
								<select name="contract" class="selectlong fontwhite" v-model="selectId">
									<option v-for="v in parameters" :value="v.CommodityNo+v.MainContract">{{v.CommodityName}}</option>
								</select>
							</li>
						</ol>
					</li>
					<li>
						<ol>
							<li class="fontgray">
								价格
							</li>
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
								<input type="text" v-model="inputAdditionalPrice" />
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
									<option value="2">限价</option>
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
	</template>
	<template v-else="ifshow">
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
								<!--<input type="time" v-model="time" class='time'/>-->
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
				</template>
				
				<li class="do">
					<div class="fontgray" @tap="close">关闭</div>
					<div class="fontgray" @tap='confirm'>添加</div>
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
			}
		},
		watch:{
			selectId:function(n,o){
				if(n != undefined){
					this.commodityNo = n.substring(0,n.length-4);
					this.contractNo = n.substring(n.length-4,n.length);
					this.inputPrice =  parseFloat(this.templateList[this.commodityNo].LastPrice).toFixed(this.orderTemplist[this.commodityNo].DotSize);
			
				}
			},
			selectTimeId:function(n,o){
				if(n != undefined){
					this.commodityNo00 = n.substring(0,n.length-4);
					this.contractNo00 = n.substring(n.length-4,n.length);
					this.timeAddtionPrice =  parseFloat(this.templateList[this.commodityNo00].LastPrice).toFixed(this.orderTemplist[this.commodityNo00].DotSize);
					this.timeAddtionPrice00 =  parseFloat(this.templateList[this.commodityNo00].LastPrice).toFixed(this.orderTemplist[this.commodityNo00].DotSize);
			
				}	
			},
			selectAdditionalPrice:function(n,o){
				if(this.selectAdditionalPrice==5){
					 this.inputAdditionalPrice = '';
					 this.additionFlag = false;
				}else{
					this.inputAdditionalPrice =this.inputPrice;
					this.additionFlag = true;
				}
			},
			additionValue:function(n,o){
				if(this.additionValue==5){
					this.timeAddtionPrice='';
					this.timeAdditionFlag = false;
				}else{
					this.timeAddtionPrice = this.timeAddtionPrice00;
					this.timeAdditionFlag = true;
				}
				
			}
		},
		methods:{
			selection:function(e){
				console.log(this.objstrParms);
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
				console.log(this.time);
				
				this.isshow = false;
				console.log(this.templateList[this.commodityNo]);
				console.log('selectAdditionalPrice:'+this.selectAdditionalPrice);
				console.log('AdditionFlag:'+this.additionFlag);
				if(this.ifshow==true){
					let b={
							"Method":'InsertCondition',
							"Parameters":{
								'ExchangeNo':this.templateList[this.commodityNo].ExchangeNo,
								'CommodityNo':this.commodityNo,
								'ContractNo':this.contractNo,
								'Num':parseInt(this.holdNum),
								'ConditionType':0,
								'PriceTriggerPonit':parseFloat(this.inputPrice),
								'CompareType':parseInt(this.selectPrice),
								'TimeTriggerPoint':'',
								'AB_BuyPoint':0.0,
								'AB_SellPoint':0.0,
								'OrderType':parseInt(this.selectMarketOrLimited),
								'Direction':parseInt(this.selectBuyOrSell),
								'StopLossType':5,
								'StopLossDiff':0.0,
								'StopWinDiff':0.0,
								'AdditionFlag':this.additionFlag,
								'AdditionType':parseInt(this.selectAdditionalPrice),
								'AdditionPrice':(function(){
													if(this.inputAdditionalPrice==''){
														return  0;
													}else{
														return parseFloat(this.inputAdditionalPrice);
													}
												}.bind(this))()
							}
						};
					this.tradeSocket.send(JSON.stringify(b));	
				}else{
					let b={
							"Method":'InsertCondition',
							"Parameters":{
								'ExchangeNo':this.templateList[this.commodityNo00].ExchangeNo,
								'CommodityNo':this.commodityNo00,
								'ContractNo':this.contractNo00,
								'Num':parseInt(this.timeHoldNum),
								'ConditionType':1,
								'PriceTriggerPonit':0.0,
								'CompareType':5,
								'TimeTriggerPoint':this.time,
								'AB_BuyPoint':0.0,
								'AB_SellPoint':0.0,
								'OrderType':parseInt(this.timeOrderType),
								'Direction':parseInt(this.timeBuyOrSell),
								'StopLossType':5,
								'StopLossDiff':0.0,
								'StopWinDiff':0.0,
								'AdditionFlag':this.timeAdditionFlag,
								'AdditionType':parseInt(this.additionValue),
								'AdditionPrice':(function(){
													if(this.timeAddtionPrice==''){
														return  0;
													}else{
														return parseFloat(this.timeAddtionPrice);
													}
												}.bind(this))()
							}
						};
						
					console.log(JSON.stringify(b));	
					this.tradeSocket.send(JSON.stringify(b));	
					
				}
				
			}
		},
		mounted:function(){
			this.selectPrice = 0;
			let arr=[];
			arr = this.parameters;
			this.selectId=arr[0].CommodityNo+arr[0].MainContract;
			this.commodityNo = arr[0].CommodityNo;
			this.contractNo = arr[0].MainContract;
			this.inputPrice =  parseFloat(this.templateList[this.commodityNo].LastPrice).toFixed(this.orderTemplist[this.commodityNo].DotSize);
			this.selectAdditionalPrice = 5;
			this.inputAdditionalPrice = this.inputPrice;
			
			this.selectBuyOrSell = 0;
			this.selectMarketOrLimited=1;
			
			
			//-------------------时间条件------------------------
			this.additionValue = 5;
			
			let arr00=[];
			arr00 = this.parameters;
			this.selectTimeId=arr00[0].CommodityNo+arr00[0].MainContract;
			this.commodityNo00 = arr00[0].CommodityNo;
			this.contractNo00 = arr00[0].MainContract;
			this.addtionPrice = parseFloat(this.templateList[this.commodityNo00].LastPrice).toFixed(this.orderTemplist[this.commodityNo00].DotSize);
			
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