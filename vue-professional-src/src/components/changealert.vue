<template>
	<div id="changealert" v-if='isshow'>
		<tipsDialog :msg="msgTips"></tipsDialog>
		<div>
			<ul>
				<li class="fontwhite">改单 <i @tap='close'></i></li>
				<li>
					<div>
						<label for="price" class="fontgray">委托价格</label>
						<input type="number" id='price' class="fontwhite" :value="priceFun" v-model="p" />
					</div>
					<div>
						<label for="num" class="fontgray">委托数量</label>
						<input type="number" id="num" class="fontwhite" :value="numFun" v-model="entrustNum" />
					</div>
				</li>
				<li>
					<div class="fontgray fl" @tap='confirm'>确认改单</div>
					<div class="fontgray fl" @tap='close'>取消</div>
				</li>
			</ul>
		</div>
	</div>
</template>

<script>
	import tipsDialog from '../components/tipsDialog.vue'
	export default {
		name: 'changealert',
		components:{tipsDialog},
		data(){
			return{
				numReg: /^[0-9]*$/,
				isshow:false,
				p: 0.00,
				entrustNum: 0,
				msg: '',
				editNum: ''
			}
		},
		props: ['price', 'num'],
		computed: {
			msgTips: function(){
				return this.msg;
			},
			priceFun(){
				this.p = this.price;
				return this.price;
			},
			numFun(){
				this.entrustNum = this.num;
				return this.num;
			},
			tradeSocket() {
				return this.$store.state.tradeSocket;
			},
			templateList(){
				return this.$store.state.market.templateList;
			},
		},
		watch: {
			entrustNum: function(n, o){
				this.entrustNum = Number(n);
				if(n == '' || n < 1){
					this.entrustNum = 0;
				}
			}
		},
		methods:{
			close:function(){
				this.isshow=false
			},
			confirm: function() {
				/*
				 * 确认并提交数据到后台
				 * @param {String} a '提交到后台的地址';{String} b '提交到后台的对象字符串'
				 */
				var Contract=this.$store.state.market.openChangealertCurrentObj.ContractCode.substring(0,this.$store.state.market.openChangealertCurrentObj.ContractCode.length-4);
				console.log(this.templateList);
				console.log(Contract);
				var b={
					"Method":'ModifyOrder',
					"Parameters":{
						"OrderSysID":'',
						"OrderID":this.$store.state.market.openChangealertCurrentObj.OrderID,
						"ExchangeNo":this.templateList[Contract].ExchangeNo,
						"CommodityNo":this.templateList[Contract].CommodityNo,
						"ContractNo":this.templateList[Contract].ContractNo,
						"OrderNum":parseFloat(this.entrustNum),
						"Direction":function(){
										if(this.$store.state.market.openChangealertCurrentObj.buyOrSell=='买'){
											return 0;
										}else{
											return 1;
										}
									},
						"OrderPrice":parseFloat(this.p),
						"TriggerPrice":0
					}
				};
				this.tradeSocket.send(JSON.stringify(b));
				this.isshow = false;
				
			}
		}
	}
</script>

<style scoped lang="less">
	@import url("../assets/css/main.less");
	/*ip5*/
	
	@media(max-width:370px) {
		#changealert {
			position: fixed;
			top: 0;
			left: 0;
			right: 0;
			bottom: 0;
			z-index: 1010;
			background-color: rgba(0, 0, 0, .8);
		}
		#changealert>div {
			width: 332px*@ip5;
			height: 194px*@ip5;
			background-color: #1b1b26;
			margin: 0 auto;
			margin-top: 271px*@ip5;
			border-radius: 6px;
			position: relative;
		}
		#changealert>div>ul>li:first-child {
			font-size: 16px*@ip5;
			border-bottom: 1px solid #1b1b26;
			height: 44px*@ip5;
			line-height: 44px*@ip5;
			text-align: center;
		}
		#changealert>div>ul>li:first-child>i {
			display: block;
			position: absolute;
			top: 0;
			right: 0;
			width: 34px*@ip5;
			height: 34px*@ip5;
			background-image: url('../assets/img/x.png');
			background-repeat: no-repeat;
			background-size: 100%;
		}
		#changealert>div>ul>li:nth-child(2) {
			font-size: 15px*@ip5;
			height: 99px*@ip5;
		}
		#changealert>div>ul>li:nth-child(3) {
			height: 45px*@ip5;
			font-size: 15px*@ip5;
			position: absolute;
			bottom: 0;
		}
		input {
			width: 192px*@ip5;
			border: none;
			outline: none;
			background: none;
			padding-left: 65px*@ip5;
			display: block;
			position: relative;
			top: -33px;
			left: 50px*@ip5;
			font-size: 15px*@ip5;
		}
		label {
			width: 100px*@ip5;
		}
		#changealert>div>ul>li:nth-child(2):before {
			content: '';
			display: table;
		}
		#changealert>div>ul>li:nth-child(2)>div {
			width: 290px*@ip5;
			height: 33px*@ip5;
			background: #1b1b26;
			border-radius: 6px;
			border: 1px solid #12121b;
			margin: 0 auto;
			margin-top: 10px*@ip5;
			padding-top: 5px*@ip5;
			padding-left: 16px*@ip5;
		}
		#changealert>div>ul>li {
			background: #242633;
			width: 100%;
		}
		#changealert>div>ul>li:nth-child(3)>div {
			width: 50%;
			text-align: center;
			height: 45px*@ip5;
			line-height: 45px*@ip5;
			position: relative;
		}
	}
	/*ip6*/
	
	@media (min-width:371px) and (max-width:410px) {
		#changealert {
			position: fixed;
			top: 0;
			left: 0;
			right: 0;
			bottom: 0;
			z-index: 1010;
			background-color: rgba(0, 0, 0, .8);
		}
		#changealert>div {
			width: 332px*@ip6;
			height: 194px*@ip6;
			background-color: #1b1b26;
			margin: 0 auto;
			margin-top: 271px*@ip6;
			border-radius: 6px;
			position: relative;
		}
		#changealert>div>ul>li:first-child {
			font-size: 16px*@ip6;
			border-bottom: 1px solid #1b1b26;
			height: 44px*@ip6;
			line-height: 44px*@ip6;
			text-align: center;
		}
		#changealert>div>ul>li:first-child>i {
			display: block;
			position: absolute;
			top: 0;
			right: 0;
			width: 34px*@ip6;
			height: 34px*@ip6;
			background-image: url('../assets/img/x.png');
			background-repeat: no-repeat;
			background-size: 100%;
		}
		#changealert>div>ul>li:nth-child(2) {
			font-size: 15px*@ip6;
			height: 99px*@ip6;
		}
		#changealert>div>ul>li:nth-child(3) {
			height: 45px*@ip6;
			font-size: 15px*@ip6;
			position: absolute;
			bottom: 0;
		}
		input {
			width: 192px*@ip6;
			border: none;
			outline: none;
			background: none;
			padding-left: 65px*@ip6;
			display: block;
			position: relative;
			top: -33px;
			left: 50px*@ip6;
			font-size: 15px*@ip6;
		}
		label {
			width: 100px*@ip6;
		}
		#changealert>div>ul>li:nth-child(2):before {
			content: '';
			display: table;
		}
		#changealert>div>ul>li:nth-child(2)>div {
			width: 290px*@ip6;
			height: 33px*@ip6;
			background: #1b1b26;
			border-radius: 6px;
			border: 1px solid #12121b;
			margin: 0 auto;
			margin-top: 10px*@ip6;
			padding-top: 5px*@ip6;
			padding-left: 16px*@ip6;
		}
		#changealert>div>ul>li {
			background: #242633;
			width: 100%;
		}
		#changealert>div>ul>li:nth-child(3)>div {
			width: 50%;
			text-align: center;
			height: 45px*@ip6;
			line-height: 45px*@ip6;
			position: relative;
		}
	}
	/*ip6p及以上*/
	
	@media (min-width:411px) {
		#changealert {
			position: fixed;
			top: 0;
			left: 0;
			right: 0;
			bottom: 0;
			z-index: 1010;
			background-color: rgba(0, 0, 0, .8);
		}
		#changealert>div {
			width: 332px*@ip6p;
			height: 194px*@ip6p;
			background-color: #1b1b26;
			margin: 0 auto;
			margin-top: 271px*@ip6p;
			border-radius: 6px;
			position: relative;
		}
		#changealert>div>ul>li:first-child {
			font-size: 16px;
			border-bottom: 1px solid #1b1b26;
			height: 44px;
			line-height: 44px;
			text-align: center;
		}
		#changealert>div>ul>li:first-child>i {
			display: block;
			position: absolute;
			top: 0;
			right: 0;
			width: 34px;
			height: 34px;
			background-image: url('../assets/img/x.png');
			background-repeat: no-repeat;
			background-size: 100%;
		}
		#changealert>div>ul>li:nth-child(2) {
			font-size: 15px;
			height: 99px;
		}
		#changealert>div>ul>li:nth-child(3) {
			height: 45px;
			font-size: 15px;
			position: absolute;
			bottom: 0;
		}
		input {
			width: 192px;
			border: none;
			outline: none;
			background: none;
			padding-left: 65px;
			display: block;
			position: relative;
			top: -32px;
			left: 50px;
			font-size: 15px;
		}
		label {
			width: 100px;
		}
		#changealert>div>ul>li:nth-child(2):before {
			content: '';
			display: table;
		}
		#changealert>div>ul>li:nth-child(2)>div {
			width: 290px;
			height: 33px;
			background: #1b1b26;
			border-radius: 6px;
			border: 1px solid #12121b;
			margin: 0 auto;
			margin-top: 10px;
			padding-top: 5px;
			padding-left: 16px;
		}
		#changealert>div>ul>li {
			background: #242633;
			width: 100%;
		}
		#changealert>div>ul>li:nth-child(3)>div {
			width: 50%;
			text-align: center;
			height: 45px;
			line-height: 45px;
			position: relative;
		}
	}
</style>