<template>
	<div id="stopMoney" v-show="show">
		<div class="bg"></div>
		<div class="stopMoney">
			<div class="title">
				<ul>
					<template v-for="(v, index) in tabList">
						<li :class="{current: currentNum == index}"  @click="clickEvent(index)"><span>{{v}}</span></li>
					</template>
				</ul>
				<i class="ifont icon_close" @click="closeEvent">&#xe624;</i>
			</div>
			<div class="cont">
				<div class="row">
					<div class="fl">
						<label>合约名称:</label>
						<span>{{selectedMsg.ContractCode}},{{selectedMsg.CommodityName}}&nbsp;{{selectedMsg.Drection}}</span>
					</div>
					<div class="fl">
						<label>最新:</label>
						<span>{{lastPrice}}</span>
					</div>
				</div>
				<div class="row" v-show="tabShow">
					<div class="fl">
						<label class="fl">止损方式:</label>
						<div class="slt-box row_money_box" id="stop_type">
							<input type="text" class="slt" disabled="disabled" :selectVal="stopType" :value="stopTypeName"/>
							<span class="tal-box"><span class="tal"></span></span>
							<div class="slt-list">
								<ul>
									<li selectVal="0">止损价</li>
									<li selectVal="2">动态价</li>
								</ul>
							</div>
						</div>
					</div>
				</div>
				<div class="row" v-show="tabShow">
					<div class="fl">
						<label>{{stopTypeName}}:</label>
						<input type="text" class="ipt" v-model="stopPrice" />
						<span v-show="rangeShow">{{range}}%</span>
					</div>
					<div class="fl">
						<input type="text" class="ipt" v-model="stopNum" />
						<label>手数</label>
					</div>
				</div>
				<div class="row" v-show="!tabShow">
					<div class="fl">
						<label>止赢价:</label>
						<input type="text" class="ipt" v-model="stopProfitPrice" />
						<span>{{profitRange}}%</span>
					</div>
					<div class="fl">
						<input type="text" class="ipt" v-model="stopProfitNum" />
						<label>手数</label>
					</div>
				</div>
				<div class="row">
					<div class="fl">
						<label>止损委托价:</label>
						<span>市价</span>
					</div>
				</div>
				<p>1.止损单在云端执行，软件关闭后扔然有效，云端自动确认结算单。</p>
				<p>2.止损单在行情不活跃或快速发送变化下，不保证成交价为指定价。</p>
				<p>3.止损单存在风险，云端系统、网络故障情况下失效等。</p>
				<div class="btn_box">
					<div class="fl">
						<button class="btn yellow" @click="confirmEvent">确定</button>
					</div>
					<div class="fl">
						<button class="btn blue" @click="cancelEvent">取消</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</template>

<script>
	import pro from '../../assets/js/common.js'
	export default{
		name: 'stopMoney',
		data(){
			return{
				show: false,
				tabList: ['新增止损单','新增止盈单'],
				currentNum: 0,
				tabShow: true,
				stopName: '止损价',
				stopPrice: '',
				stopNum: '',
				range: '0.00',
				stopProfitPrice: '',
				stopProfitNum: '',
				profitRange: '0.00',
				lastPrice: '',
				str: '',
				stopType: '0',
				stopTypeName: '止损价',
				rangeShow: true,
			}
		},
		props: ['value'],
		computed: {
			orderTemplist(){
				return this.$store.state.market.orderTemplist;
			},
			parameters(){
				return this.$store.state.market.Parameters;
			},
			tradeSocket() {
				return this.$store.state.tradeSocket;
			},
			selectedMsg: function(){
				if(this.value != undefined && this.value != ''){
					var data = JSON.parse(this.value);
					this.stopPrice = data.stopPrice;
					this.stopNum = data.HoldNum;
					this.stopProfitPrice = data.stopPrice;
					this.stopProfitNum = data.HoldNum;
					return data;
				}
			},
			miniTikeSize(){
				return this.orderTemplist[this.selectedMsg.CommodityNo].MiniTikeSize;
			}
		},
		watch: {
			parameters: function(n,o){
				if(this.selectedMsg.CommodityNo != undefined){
					n.forEach(function(o, i){
						if(this.selectedMsg.CommodityNo == o.CommodityNo){
							this.lastPrice = this.orderTemplist[this.selectedMsg.CommodityNo].LastQuotation.LastPrice;
							this.lastPrice = parseFloat(this.lastPrice).toFixed(this.orderTemplist[this.selectedMsg.CommodityNo].DotSize);
						}
					}.bind(this));
				}
			},
			stopPrice: function(n,o){
				if(n && n != undefined){
					var openAvgPrice = this.selectedMsg.HoldAvgPrice;
					this.range = parseFloat((this.stopPrice - openAvgPrice)/openAvgPrice*100).toFixed(2);
				}
			},
			stopProfitPrice: function(n,o){
				if(n && n != undefined){
					var openAvgPrice = this.selectedMsg.HoldAvgPrice;
					this.profitRange = parseFloat((this.stopProfitPrice - openAvgPrice)/openAvgPrice*100).toFixed(2);
				}
			},
		},
		methods: {
			clickEvent: function(index){
				this.currentNum = index;
				if(index == 1){
					this.tabShow = false;
					this.stopName = '止盈价';
				}else{
					this.tabShow = true;
					this.stopName = '止损价';
				}
			},
			confirmEvent: function(){
				//公式（判断输入价格是否符合最小变动价）
//				var a = 46.41;
//				var b = 0.01;
//				var c = a/b;
//				var d = a%b;
//				if (d < 0.000000001 || b-d < 0.0000000001){
//					alert("yes");
//				}
				let a0, b0, d0, msg, drection;
				if(!(this.stopPrice == '' || this.stopPrice == 0 || this.stopPrice == undefined)){
					a0 = this.stopPrice;
					b0 = this.miniTikeSize;
					d0 = a0%b0;
				}
				if(this.selectedMsg.Drection == '多'){
					drection = 0;
				}else{
					drection = 1;
				}
				if(this.currentNum == 0){
					msg = '是否添加限价止损？';
					if(this.stopPrice == '' || this.stopPrice == 0 || this.stopPrice == undefined){
						layer.msg('请输入止损价', {time: 1000});return;
					}else if(!(d0 < 0.000000001 || parseFloat(b0-d0) < 0.0000000001)){
						//d0 >= 0.000000001 && parseFloat(b0-d0) >= 0.0000000001
						layer.msg('输入价格不符合最小变动价，最小变动价为：' + b0, {time: 1000});return;
					}else if(this.stopNum == '' || this.stopNum <= 0 || this.stopNum == undefined){
						layer.msg('请输入止损手数', {time: 1000});return;
					}else{
						if(drection == 0){
							//if(this.inputPrice > this.templateListObj.LastPrice){
							if(parseFloat(this.stopPrice) >= parseFloat(this.lastPrice)){	
								layer.msg('输入价格应该低于最新价', {time: 1000});
								return;
							}
						}
						if(drection == 1){
							//if(this.inputPrice < this.templateListObj.LastPrice){
							if(parseFloat(this.stopPrice) <= parseFloat(this.lastPrice)){
								layer.msg('输入价格应该高于最新价', {time: 1000});
								return;
							}
						}
						let b = {
							"Method": 'InsertStopLoss',
							"Parameters": {
								"ExchangeNo": this.orderTemplist[this.selectedMsg.CommodityNo].ExchangeNo,
								"CommodityNo": this.orderTemplist[this.selectedMsg.CommodityNo].CommodityNo,
								"ContractNo": this.orderTemplist[this.selectedMsg.CommodityNo].MainContract,
								"Num": parseInt(this.stopNum),
								"StopLossType": parseInt(this.stopType),
								"StopLossPrice": this.stopType == '0' ? parseFloat(this.stopPrice) : 0.00,
								"StopLossDiff": this.stopType == '2' ? parseFloat(this.stopPrice) : 0.00,
								"HoldAvgPrice": parseFloat(this.selectedMsg.HoldAvgPrice),
								"HoldDrection": drection,
								"OrderType": 1,
							}
						};
						this.str = b;
					}
				}else if(this.currentNum == 1){
					msg = '是否添加限价止盈？';
					if(this.stopProfitPrice == '' || this.stopProfitPrice == 0 || this.stopProfitPrice == undefined){
						layer.msg('请输入止盈价', {time: 1000});return;
					}else if(this.stopProfitNum == '' || this.stopProfitNum <= 0 || this.stopProfitNum == undefined){
						layer.msg('请输入止盈手数', {time: 1000});return;
					}else if(d0 >= 0.000000001 && parseFloat(b0-d0) >= 0.0000000001){
						layer.msg('输入价格不符合最小变动价，最小变动价为：' + b0, {time: 1000});return;
					}else{
						if(drection == 0){
							if(parseFloat(this.stopProfitPrice) <= parseFloat(this.lastPrice)){	
								layer.msg('输入价格应该高于最新价', {time: 1000});
								return;
							}
						}
						if(drection == 1){
							if(parseFloat(this.stopProfitPrice) >= parseFloat(this.lastPrice)){	
								layer.msg('输入价格应该低于最新价', {time: 1000});
								return;
							}
						}
						let b = {
							"Method":'InsertStopLoss',
							"Parameters":{
								"ExchangeNo": this.orderTemplist[this.selectedMsg.CommodityNo].ExchangeNo,
								"CommodityNo": this.orderTemplist[this.selectedMsg.CommodityNo].CommodityNo,
								"ContractNo": this.orderTemplist[this.selectedMsg.CommodityNo].MainContract,
								"Num": parseInt(this.stopNum),
								"StopLossType": 1,
								"StopLossPrice": parseFloat(this.stopProfitPrice),
								"StopLossDiff": 0.00,
								"HoldAvgPrice": parseFloat(this.selectedMsg.HoldAvgPrice),
								"HoldDrection": drection,
								"OrderType": 1
							}
						};
						this.str = b;
					}
				}
				layer.confirm(msg, {
					btn: ['确定','取消']
				}, function(index){
					this.tradeSocket.send(JSON.stringify(this.str));
					this.$parent.currentOrderID = '';
					this.$parent.selectedNum = -1;
					this.show = false;
					layer.close(index);
				}.bind(this));
			},
			cancelEvent: function(){
				this.show = false;
			},
			closeEvent: function(){
				this.show = false;
			}
		},
		mounted: function(){
			//调用下拉框
			pro.selectEvent('#stop_type', function(data){
				this.stopType = data;
				if(data == '0'){
					this.stopTypeName = '止损价';
					this.rangeShow = true;
				}else{
					this.stopTypeName = '动态价';
					this.rangeShow = false;
				}
			}.bind(this));
		}
	}
</script>

<style lang="scss" scoped>
	@import "../../assets/css/common.scss";
	.stopMoney{
		width: 400px;
		overflow: hidden;
		background: $blue;
		border-radius: 5px;
		position: fixed;
		top: 50%;
		left: 50%;
		z-index: 100;
		margin: -190px 0 0 -200px;
	}
	.title{
		height: 40px;
		background: $bottom_color;
		position: relative;
		li{
			float: left;
			width: 200px;
			height: 40px;
			line-height: 40px;
			text-align: center;
			span{
				cursor: pointer;
				font-size: $fs16;
			}
			&:hover, &.current{
				span{
					color: $yellow;
				}
			}
			&:first-child{
				border-right: 1px solid $black;
			}
		}
		.icon_close{
			position: absolute;
			top: 14px;
			right: 10px;
			color: #7a8199;
			cursor: pointer;
		}
	}
	.cont{
		padding: 20px 10px 0 10px;
		.row{
			height: 30px;
			margin-bottom: 18px;
			div:first-child{
				width: 270px;
				label{
					width: 78px;
					text-align: right;
				}
			}
			label{
				display: inline-block;
				line-height: 30px;
			}
			span{
				color: $white;
				line-height: 30px;
			}
			.ipt{
				width: 68px;
				height: 26px;
				line-height: 26px;
				border: 1px solid #474c66;
				border-radius: 4px;
				color: $white;
				text-align: center;
			}
			.dynamic{
				opacity: 0;
			}
			.row_money_box{
				width: 100px;
				margin-left: 5px;
				.slt{
					width: 98px;
					padding-left: 0;
				}
				span{
					line-height: 54px;
				}
				.slt-list{
					width: 100px;
					height: auto;
					li{
						width: 100px;
					}
				}
				&.current{
					span{
						line-height: 0.5;
					}
				}
			}
		}
		p{
			font-size: $fs12;
			line-height: 22px;
		}
	}
	.btn_box{
		height: 60px;
		div{
			width: 50%;
			text-align: center;
		}
		button{
			width: 90px;
			height: 30px;
			line-height: 30px;
			margin-top: 15px;
		}
	}
</style>