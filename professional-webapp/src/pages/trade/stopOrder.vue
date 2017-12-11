<template>
	<div id="trade_details">
		<div class="title">
			<template v-for="(v, index) in tabList">
				<span :class="{current: currentNum == index}" @click="tabEvent(index)">{{v}}</span>
			</template>
		</div>
		<div class="cont_not" v-if="tabShow">
			<table>
				<thead>
					<tr>
						<td>合约名称</td>
						<td>状态</td>
						<td>多空</td>
						<td>类别</td>
						<td>手数</td>
						<td>触发条件</td>
						<td>委托价</td>
						<td>有效期</td>
						<td>下单时间</td>
					</tr>
				</thead>
				<tbody>
					<template v-for="(v, index) in notStopLossList">
						<tr :class="{current: selectedNum == index}" @click="clickEvent(index, v.StopLossNo, v.Status, v.StopLossType00)">
							<td>{{v.CommodityNo + v.ContractNo}}</td>
							<td>{{v.StatusMsg00}}</td>
							<td>{{v.HoldDrection}}</td>
							<td>{{v.StopLossType}}</td>
							<td>{{v.Num}}</td>
							<td>{{v.triggerCondition}}</td>
							<td>{{v.entrustPrice}}</td>
							<td>{{v.validity}}</td>
							<td>{{v.InsertDateTime}}</td>
						</tr>
					</template>
				</tbody>
			</table>
			<div class="tools">
				<button class="btn blue" @click="suspendEvent">{{statusName}}</button>
				<button class="btn blue" @click="editStopOrder">修改</button>
				<button class="btn blue" @click="deleteStopOrder">删除</button>
			</div>
		</div>
		<div class="cont_already" v-if="!tabShow">
			<table>
				<thead>
					<tr>
						<td>合约名称</td>
						<td>状态</td>
						<td>多空</td>
						<td>类别</td>
						<td>手数</td>
						<td>触发条件</td>
						<td>委托价</td>
						<td>有效期</td>
						<td>下单时间</td>
					</tr>
				</thead>
				<tbody>
					<template v-for="(v, index) in alreadyStopLossList">
						<tr>
							<td>{{v.CommodityNo + v.ContractNo}}</td>
							<td>{{v.StatusMsg00}}</td>
							<td>{{v.HoldDrection}}</td>
							<td>{{v.StopLossType}}</td>
							<td>{{v.Num}}</td>
							<td>{{v.triggerCondition}}</td>
							<td>{{v.entrustPrice}}</td>
							<td>{{v.validity}}</td>
							<td>{{v.InsertDateTime}}</td>
						</tr>
					</template>
				</tbody>
			</table>
		</div>
		
		<div id="edit_loss_order" v-show="showLossDialog">
			<div class="edit_order cont">
				<div class="row">
					<div class="fl">
						<label>合约名称:</label>
						<span>{{currentOrderList.CommodityNo + currentOrderList.ContractNo}},{{orderTemplist[currentOrderList.CommodityNo] ? orderTemplist[currentOrderList.CommodityNo].CommodityName : ''}}&nbsp;{{currentOrderList.HoldDrection}}</span>
					</div>
					<div class="fl">
						<label>最新:</label>
						<span>{{lastPrice}}</span>
					</div>
				</div>
				<div class="row">
					<div class="fl">
						<label>止损价:</label>
						<input type="text" class="ipt" v-model="stopPrice" />
					</div>
					<div class="fl">
						<input type="text" class="ipt" v-model="stopNum" />
						<label>手数</label>
					</div>
				</div>
				<div class="row">
					<div class="fl">
						<!--<label><i class="ifont checkbox">&#xe634;</i></label>-->
						<!--<label><i class="ifont checkboxs">&#xe600;</i></label>-->
						<span class="dynamic">动态追踪，价格回撤幅度</span>
					</div>
					<div class="fl">
						<input type="text" class="ipt" disabled="disabled" v-model="percentLoss" />
						<label>%</label>
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
			</div>
		</div>
		<div id="edit_profit_order" v-show="showProfitDialog">
			<div class="edit_order cont">
				<div class="row">
					<div class="fl">
						<label>合约名称:</label>
						<span>{{currentOrderList.CommodityNo + currentOrderList.ContractNo}},{{orderTemplist[currentOrderList.CommodityNo] ? orderTemplist[currentOrderList.CommodityNo].CommodityName : ''}}&nbsp;{{currentOrderList.HoldDrection}}</span>
					</div>
					<div class="fl">
						<label>最新:</label>
						<span>{{lastPrice}}</span>
					</div>
				</div>
				<div class="row">
					<div class="fl">
						<label>止损价:</label>
						<input type="text" class="ipt" v-model="stopPrice" />
					</div>
					<div class="fl">
						<input type="text" class="ipt" v-model="stopNum" />
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
			</div>
		</div>
	</div>
</template>

<script>
	export default{
		name: 'trade_details',
		data(){
			return{
				showLossDialog: false,
				showProfitDialog: false,
				tabList: ['未触发列表','已触发列表'],
				currentNum: 0,
				tabShow: true,
				notStopLossList: [],
				alreadyStopLossList: [],
				currentId: '',
				currentOrderList: '',
				status: '',
				statusName: '暂停',
				selectedNum: -1,
				stopLossType: '',
				lastPrice: '',
				stopPrice: '',
				stopNum: '',
				percentLoss: '0.00',
			}
		},
		computed: {
			tradeSocket() {
				return this.$store.state.tradeSocket;
			},
			orderTemplist(){
				return	this.$store.state.market.orderTemplist;
			},
			parameters(){
				return this.$store.state.market.Parameters;
			},
			stopLossList(){
				return this.$store.state.market.stopLossList;
			},
			stopLossTriggeredList(){
				return this.$store.state.market.stopLossTriggeredList;
			},
		},
		watch: {
			stopLossList: function(n, o){
				this.notStopLossListEvent();
			},
			parameters: function(n,o){
				if(this.currentOrderList.CommodityNo != undefined){
					n.forEach(function(o, i){
						if(this.currentOrderList.CommodityNo == o.CommodityNo){
							this.lastPrice = this.orderTemplist[this.currentOrderList.CommodityNo].LastQuotation.LastPrice;
							this.lastPrice = parseFloat(this.lastPrice).toFixed(this.orderTemplist[this.currentOrderList.CommodityNo].DotSize);
						}
					}.bind(this));
				}
			},
			stopPrice: function(n, o){
				if(n != undefined){
					let openAvgPrice = this.currentOrderList.HoldAvgPrice;
					this.percentLoss = parseFloat((n - openAvgPrice)/openAvgPrice*100).toFixed(2);
				}
			}
		},
		methods: {
			tabEvent: function(index){
				this.currentNum = index;
				if(index == 0){
					this.tabShow = true;
				}else{
					this.tabShow = false;
					this.alreadyStopLossListEvent();
				}
			},
			clickEvent: function(index, id, status, type){
				if(this.selectedNum == index){
					this.selectedNum = -1;
					this.currentId = '';
					this.currentOrderList = '';
					return;
				}
				this.selectedNum = index;
				this.currentId = id;
				this.status = status;
				this.stopLossType = type;
				if(this.status == 0){
					this.statusName = '暂停';
				}else{
					this.statusName = '启动';
				}
				this.notStopLossList.forEach(function(o, i){
					if(o.StopLossNo == this.currentId){
						this.currentOrderList = o;
						this.stopNum = o.Num;
						this.stopPrice = o.StopLossPrice;
						var openAvgPrice = o.HoldAvgPrice;
						this.percentLoss = parseFloat((this.stopPrice - openAvgPrice)/openAvgPrice*100).toFixed(2);
					}
				}.bind(this));
			},
			suspendEvent: function(){
				if(this.currentId == '' || this.currentId == undefined){
					layer.msg('请选择一条数据', {time: 1000});
					return;
				}
				var msg, b;
				this.notStopLossList.forEach(function(o, i){
					if(o.StopLossNo == this.currentId){
						if(o.Status == 0){ //运行中
							msg = '是否暂停止损单？';
							b = {
								"Method": 'ModifyStopLoss',
								"Parameters":{
									'StopLossNo': o.StopLossNo,
									'ModifyFlag': 2,
									'Num': parseInt(o.Num),
									'StopLossType': parseInt(o.StopLossType00),
									'OrderType': parseInt(o.OrderType00),
									'StopLossPrice': parseFloat(o.StopLossPrice),
									'StopLossDiff': parseFloat(o.StopLossDiff)
								}
							};
						}else if (o.Status == 1){ //暂停
							msg = '是否启动止损单？';
							b = {
								"Method": 'ModifyStopLoss',
								"Parameters":{
									'StopLossNo': o.StopLossNo,
									'ModifyFlag': 3,
									'Num': parseInt(o.Num),
									'StopLossType': parseInt(o.StopLossType00),
									'OrderType': parseInt(o.OrderType00),
									'StopLossPrice': parseFloat(o.StopLossPrice),
									'StopLossDiff': parseFloat(o.StopLossDiff)
								}
							};
						}
						layer.confirm(msg, {
							btn: ['确定','取消']
						}, function(index){
							this.tradeSocket.send(JSON.stringify(b));
							this.currentId = '';
							this.selectedNum = -1;
							layer.close(index);
						}.bind(this));
					}
				}.bind(this));		
			},
			editConfirm: function(){
				let miniTikeSize = this.orderTemplist[this.currentOrderList.CommodityNo].MiniTikeSize;
				let d0 = this.stopPrice % miniTikeSize;
				if(this.stopLossType == 0){
					if(this.stopPrice == '' || this.stopPrice == 0 || this.stopPrice == undefined){
						layer.msg('请输入止损价', {time: 1000});
					}else if(d0 >= 0.000000001 && parseFloat(miniTikeSize-d0) >= 0.0000000001){
						layer.msg('输入价格不符合最小变动价，最小变动价为：' + miniTikeSize, {time: 1000});
					}else if(this.stopNum == '' || this.stopNum <= 0 || this.stopNum == undefined){
						layer.msg('请输入止损手数', {time: 1000});
					}else{
						if(this.currentOrderList.HoldDrection == '多'){
							if(this.stopPrice > this.lastPrice){
								layer.msg('输入价格应该低于最新价', {time: 1000});
								return;
							}
						}
						if(this.currentOrderList.HoldDrection == '空'){
							if(this.stopPrice < this.lastPrice){
								layer.msg('输入价格应该高于最新价', {time: 1000});
								return;
							}
						}
						let b = {
							"Method":'ModifyStopLoss',
							"Parameters":{
								'StopLossNo': this.currentOrderList.StopLossNo,
								'ModifyFlag': 0,
								'Num': parseInt(this.stopNum),
								'StopLossType': parseInt(this.currentOrderList.StopLossType00),
								'OrderType': parseInt(this.currentOrderList.OrderType00),
								'StopLossPrice': parseFloat(this.stopPrice),
								'StopLossDiff': (function(){
													if(parseInt(this.stopLossType)==0)
														return 0;
													if(parseInt(this.stopLossType)==2)
														return parseFloat(this.stopPrice);
												}.bind(this))()
							}
						};
						layer.confirm('是否添加限价止损？', {
							btn: ['确定','取消']
						}, function(index){
							this.tradeSocket.send(JSON.stringify(b));
							this.currentId = '';
							this.selectedNum = -1;
							layer.closeAll();
							this.showLossDialog = false;
							this.showProfitDialog = false;
						}.bind(this));
					}
				}else{
					if(this.stopPrice == '' || this.stopPrice == 0 || this.stopPrice == undefined){
						layer.msg('请输入止盈价', {time: 1000});
					}else if(d0 >= 0.000000001 && parseFloat(miniTikeSize-d0) >= 0.0000000001){
						layer.msg('输入价格不符合最小变动价，最小变动价为：' + miniTikeSize, {time: 1000});
					}else if(this.stopNum == '' || this.stopNum <= 0 || this.stopNum == undefined){
						layer.msg('请输入止盈手数', {time: 1000});
					}else{
						if(this.currentOrderList.HoldDrection == '多'){
							if(this.stopPrice < this.lastPrice){
								layer.msg('输入价格应该高于最新价', {time: 1000});
								return;
							}
						}
						if(this.currentOrderList.HoldDrection == '空'){
							if(this.stopPrice > this.lastPrice){
								layer.msg('输入价格应该低于最新价', {time: 1000});
								return;
							}
						}
						let b = {
							"Method":'ModifyStopLoss',
							"Parameters":{
								'StopLossNo': this.currentOrderList.StopLossNo,
								'ModifyFlag': 0,
								'Num': parseInt(this.stopNum),
								'StopLossType': parseInt(this.currentOrderList.StopLossType00),
								'OrderType': parseInt(this.currentOrderList.OrderType00),
								'StopLossPrice': parseFloat(this.stopPrice),
								'StopLossDiff': 0
							}
						};
						layer.confirm('是否添加限价止盈？', {
							btn: ['确定','取消']
						}, function(index){
							this.tradeSocket.send(JSON.stringify(b));
							this.currentId = '';
							this.selectedNum = -1;
							layer.closeAll();
							this.showLossDialog = false;
							this.showProfitDialog = false;
						}.bind(this));
					}
				}
			},
			editStopOrder: function(){
				if(this.currentId == '' || this.currentId == undefined){
					layer.msg('请选择一条数据', {time: 1000});
				}else if(this.status == 0){
					layer.msg('运行中的止损单不能修改', {time: 1000});
				}else{
					var dialogObj, title;
					if(this.stopLossType == 0){
						dialogObj = $("#edit_loss_order");
						this.showLossDialog = true;
						title = '修改止损单';
					}else{
						dialogObj = $("#edit_profit_order");
						this.showProfitDialog = true;
						title = '修改止盈单';
					}
					this.showDialog = true;
					layer.open({
						type: 1,
						title: title,
						area: ['400px', 'auto'],
						content: dialogObj,
						btn: ['确定','取消'],
						btn1: function(index){
							this.editConfirm();
						}.bind(this),
						btn2: function(){
							this.showLossDialog = false;
							this.showProfitDialog = false;
						}.bind(this),
						cancel: function(){
							this.showLossDialog = false;
							this.showProfitDialog = false;
						}.bind(this)
					});
				}
			},
			deleteStopOrder: function(){
				if(this.currentId != ''){
					this.notStopLossList.forEach(function(o, i){
						if(o.StopLossNo == this.currentId){
							let b = {
								"Method": 'ModifyStopLoss',
								"Parameters": {
									'StopLossNo': o.StopLossNo,
									'ModifyFlag': 1,
									'Num': parseInt(o.Num),
									'StopLossType': parseInt(o.StopLossType00),
									'OrderType': parseInt(o.OrderType00),
									'StopLossPrice': parseFloat(o.StopLossPrice),
									'StopLossDiff': parseFloat(o.StopLossDiff)
								}
							};
							layer.confirm('是否删除止损单？', {
								btn: ['确定','取消']
							}, function(index){
								this.tradeSocket.send(JSON.stringify(b));
								this.currentId = '';
								this.selectedNum = -1;
								layer.close(index);
							}.bind(this));
						}
					}.bind(this));
				}else{
					layer.msg('请选择一条数据', {time: 1000});
				}
			},
			notStopLossListEvent: function(){
				this.notStopLossList = [];
				this.stopLossList.forEach(function(o, i){
					let obj = {};
					obj.ClientNo = o.ClientNo;
					obj.CommodityNo = o.CommodityNo;
					obj.ContractNo = o.ContractNo;
					obj.ExchangeNo = o.ExchangeNo;
					obj.HoldAvgPrice = o.HoldAvgPrice;
					obj.HoldDrection = (function(){
							if(o.HoldDrection == 0){
								return '多';
							}else{
								return '空';
							}
						})();
					obj.InsertDateTime = o.InsertDateTime;
					obj.Num = o.Num;
					obj.OrderType = (function(){
						if(o.OrderType == 1){
							return '市价';
						}else{
							return '限价';
						}
					})();
					obj.OrderType00 = o.OrderType;
					obj.Status = o.Status;
					obj.StatusMsg = o.StatusMsg;
					obj.StatusMsg00 = (function(){
						if(o.Status == 0)
							return '运行中';
						if(o.Status == 1)
							return '暂停';
						if(o.Status == 2)
							return '已触发';
						if(o.Status == 3)
							return '已取消';
						if(o.Status == 4)
							return '插入失败';
						if(o.Status == 5)
							return '触发失败';	
					})();
					obj.StopLossDiff = o.StopLossDiff;
					obj.StopLossNo = o.StopLossNo;
					obj.StopLossPrice = parseFloat(o.StopLossPrice).toFixed(this.orderTemplist[o.CommodityNo].DotSize);
					obj.StopLossType = (function(){
						if(o.StopLossType == 0)
							return '限价止损';
						if(o.StopLossType == 1)	
							return '限价止盈';
						if(o.StopLossType == 2)
							return '动态止损';
					})();
					obj.StopLossType00 = o.StopLossType;
					obj.triggerCondition=(function(){
						if(o.StopLossType == 0 || o.StopLossType == 1)
							return '触发价:'+parseFloat(o.StopLossPrice).toFixed(this.orderTemplist[o.CommodityNo].DotSize);
						if(o.StopLossType == 2)
							return '动态价:'+parseFloat(o.StopLossDiff).toFixed(this.orderTemplist[o.CommodityNo].DotSize);
					}.bind(this))();
					obj.entrustPrice = (function(){
						if(o.OrderType == 1){
							return '市价';
						}else{
							return '对手价';
						}
					})();
					obj.validity = '当日有效';
					obj.InsertDateTime = o.InsertDateTime;
					this.notStopLossList.push(obj);
				}.bind(this));
			},
			alreadyStopLossListEvent: function(){
				this.alreadyStopLossList = [];
				this.stopLossTriggeredList.forEach(function(o, i){
					let obj = {};
					obj.ClientNo = o.ClientNo;
					obj.CommodityNo = o.CommodityNo;
					obj.ContractNo = o.ContractNo;
					obj.ExchangeNo = o.ExchangeNo;
					obj.HoldAvgPrice = o.HoldAvgPrice;
					obj.HoldDrection = (function(){
							if(o.HoldDrection == 0){
								return '多';
							}else{
								return '空';
							}
						})();
					obj.InsertDateTime = o.InsertDateTime;
					obj.Num = o.Num;
					obj.OrderType = (function(){
						if(o.OrderType == 1){
							return '市价';
						}else{
							return '限价';
						}
					})();
					obj.Status = o.Status;
					obj.StatusMsg = o.StatusMsg;
					obj.StatusMsg00 = (function(){
						if(o.Status == 0)
							return '运行中';
						if(o.Status == 1)
							return '暂停';
						if(o.Status == 2)
							return '已触发';
						if(o.Status == 3)
							return '已取消';
						if(o.Status == 4)
							return '插入失败';
						if(o.Status == 5)
							return '触发失败';	
					})();
					obj.StopLossDiff = o.StopLossDiff;
					obj.StopLossNo = o.StopLossNo;
					obj.StopLossPrice = parseFloat(o.StopLossPrice).toFixed(this.orderTemplist[o.CommodityNo].DotSize);
					obj.StopLossType = (function(){
						if(o.StopLossType == 0)
							return '限价止损';
						if(o.StopLossType == 1)	
							return '限价止盈';
						if(o.StopLossType == 2)
							return '动态止损';
					})();
					obj.triggerCondition = (function(){
						if(o.StopLossType == 0 || o.StopLossType == 1)
							return '触发价:'+parseFloat(o.StopLossPrice).toFixed(this.orderTemplist[o.CommodityNo].DotSize);
						if(o.StopLossType == 2)
							return '动态价:'+parseFloat(o.StopLossDiff).toFixed(this.orderTemplist[o.CommodityNo].DotSize);	
					}.bind(this))();
					obj.entrustPrice = (function(){
						if(o.OrderType == 1){
							return '市价';
						}else{
							return '对手价';
						}
					})();
					obj.validity = '当日有效';
					obj.InsertDateTime = o.InsertDateTime;
					this.alreadyStopLossList.push(obj);
				}.bind(this));
			}
			
			
		},
		mounted: function(){
			//获取渲染页面所需数据
			this.notStopLossListEvent();
		},
		activated: function(){}
	}
</script>

<style lang="scss" scoped>
	@import "../../assets/css/common.scss";
	.cont_not{
		height: 111px;
		overflow: auto;
	}
	.cont_already{
		height: 170px;
		overflow: auto;
	}
	.title{
		width: 100%;
		height: 40px;
		line-height: 40px;
		span{
			margin: 0 10px;
			cursor: pointer;
			&.current, &:hover{
				color: $yellow;
			}
		}
	}
	table{
		thead tr{
			height: 30px;
			background: $bottom_color;
		}
		td{
			padding: 0 10px;
		}
		tbody tr{
			height: 40px;
			border-bottom: 1px solid $bottom_color;
			
		}
	}
	.tools{
		position: absolute;
		bottom: 10px;
		left: 10px;
		z-index: 2;
		.btn{
			width: 90px;
			height: 30px;
			line-height: 30px;
		}
	}
	.edit_order{
		width: 400px;
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
		}
		p{
			font-size: $fs12;
			line-height: 22px;
		}
	}
	@media only screen and (min-width: 1280px) and (max-width: 1366px) {
		#trade_details{
			width: 635px;
			td{
				font-size: $fs12;
			}
		}
	}
</style>