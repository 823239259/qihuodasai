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
						<td>类型</td>
						<td>条件</td>
						<td>下单</td>
						<td>有效日期</td>
						<td>下单时间</td>
					</tr>
				</thead>
				<tbody>
					<template v-for="(v, index) in conditionListCont">
						<tr :class="{current: selectedNum == index}" @click="clickEvent(index, v.ConditionNo, v.Status, v.ConditionType)">
							<td>{{v.name}}</td>
							<td>{{v.status00}}</td>
							<td>{{v.type}}</td>
							<td>{{v.conditions}}</td>
							<td>{{v.order}}</td>
							<td>{{v.term}}</td>
							<td>{{v.time}}</td>
						</tr>
					</template>
				</tbody>
			</table>
			<div class="tools">
				<button class="btn blue" @click="suspendConditionOrder">{{statusName}}</button>
				<button class="btn blue" @click="editConditionOrder">修改</button>
				<button class="btn blue" @click="deleteConditionOrder">删除</button>
			</div>
		</div>
		<div class="cont_already" v-if="!tabShow">
			<table>
				<thead>
					<tr>
						<td>合约名称</td>
						<td>状态</td>
						<td>类型</td>
						<td>条件</td>
						<td>下单</td>
						<td>有效日期</td>
						<td>下单时间</td>
					</tr>
				</thead>
				<tbody>
					<template v-for="(v, index) in triggerConditionListCont">
						<tr>
							<td>{{v.name}}</td>
							<td>{{v.status00}}</td>
							<td>{{v.type}}</td>
							<td>{{v.conditions}}</td>
							<td>{{v.order}}</td>
							<td>{{v.term}}</td>
							<td>{{v.time}}</td>
						</tr>
					</template>
				</tbody>
			</table>
		</div>
		<div id="edit_price_order" v-show="showPriceDialog">
			<div class="edit_order cont">
				<div class="row">
					<div class="fl">
						<label>合约:</label>
						<span>{{currentConditionOrder.CommodityNo + currentConditionOrder.ContractNo}},{{orderTemplist[currentConditionOrder.CommodityNo] ? orderTemplist[currentConditionOrder.CommodityNo].CommodityName : ''}}</span>
					</div>
					<div class="fl">
						<label>最新:</label>
						<span>{{lastPrice}}</span>
					</div>
				</div>
				<div class="row">
						<label>条件:</label>
						<span>价格</span>
						<div class="slt-box row_price_box" id="price_type">
							<input type="text" class="slt" disabled="disabled" :selectVal="priceType" :value="priceTypeName"/>
							<span class="tal-box"><span class="tal"></span></span>
							<div class="slt-list">
								<ul>
									<li selectVal="0">></li>
									<li selectVal="2">>=</li>
									<li selectVal="1"><</li>
									<li selectVal="3"><=</li>
								</ul>
							</div>
						</div>
						<input type="text" class="ipt" v-model="conditionPrice" />
						<label>附加:</label>
						<div class="slt-box row_price_box" id="additional_price_type">
							<input type="text" class="slt" disabled="disabled" :selectVal="additionalPriceType" :value="additionalPriceTypeName"/>
							<span class="tal-box"><span class="tal"></span></span>
							<div class="slt-list">
								<ul>
									<li selectVal="0">></li>
									<li selectVal="2">>=</li>
									<li selectVal="1"><</li>
									<li selectVal="3"><=</li>
								</ul>
							</div>
						</div>
						<input type="text" class="ipt" v-model="additionPrice" />
				</div>
				<div class="row">
					<div class="fl">
						<label>操作:</label>
						<div class="slt-box row_money_box" id="direction_type">
							<input type="text" class="slt" disabled="disabled" :selectVal="directionType" :value="directionTypeName"/>
							<span class="tal-box"><span class="tal"></span></span>
							<div class="slt-list">
								<ul>
									<li selectVal="0">买入</li>
									<li selectVal="1">卖出</li>
								</ul>
							</div>
						</div>
						<div class="slt-box row_money_box" id="condition_price_type">
							<input type="text" class="slt" disabled="disabled" :selectVal="orderType" :value="orderTypeName"/>
							<span class="tal-box"><span class="tal"></span></span>
							<div class="slt-list">
								<ul>
									<li selectVal="1">市价</li>
									<li selectVal="2">对手价</li>
								</ul>
							</div>
						</div>
					</div>
					<div class="fl">
						<label>手数:</label>
						<input type="text" class="ipt" v-model="conditionNum" />
					</div>
				</div>
			</div>
			<h6>当日有效</h6>
			<p>1.止损单在云端执行，软件关闭后扔然有效，云端自动确认结算单。</p>
			<p>2.止损单在行情不活跃或快速发送变化下，不保证成交价为指定价。</p>
			<p>3.止损单存在风险，云端系统、网络故障情况下失效等。</p>
		</div>
		<div id="edit_time_order" v-show="showTimeDialog">
			<div class="edit_order cont">
				<div class="row">
					<div class="fl">
						<label>合约:</label>
						<span>{{currentConditionOrder.CommodityNo + currentConditionOrder.ContractNo}},{{orderTemplist[currentConditionOrder.CommodityNo] ? orderTemplist[currentConditionOrder.CommodityNo].CommodityName : ''}}</span>
					</div>
					<div class="fl">
						<label>最新:</label>
						<span>{{lastPrice}}</span>
					</div>
				</div>
				<div class="row">
						<label>条件:</label>
						<span>时间</span>
						<input type="text" class="ipt time" readonly="readonly" />
						<label>附加:</label>
						<div class="slt-box row_price_box">
							<input type="text" class="slt" disabled="disabled" selectVal="0" value=">"/>
							<span class="tal-box"><span class="tal"></span></span>
							<div class="slt-list">
								<ul>
									<li selectVal="0">></li>
									<li selectVal="2">>=</li>
									<li selectVal="1"><</li>
									<li selectVal="3"><=</li>
								</ul>
							</div>
						</div>
						<input type="text" class="ipt" v-model="additionPrice" />
				</div>
				<div class="row">
					<div class="fl">
						<label>操作:</label>
						<div class="slt-box row_money_box">
							<input type="text" class="slt" disabled="disabled" selectVal="0" value="买入"/>
							<span class="tal-box"><span class="tal"></span></span>
							<div class="slt-list">
								<ul>
									<li selectVal="0">买入</li>
									<li selectVal="1">卖出</li>
								</ul>
							</div>
						</div>
						<div class="slt-box row_money_box">
							<input type="text" class="slt" disabled="disabled" selectVal="0" value="市价"/>
							<span class="tal-box"><span class="tal"></span></span>
							<div class="slt-list">
								<ul>
									<li selectVal="0">市价</li>
									<li selectVal="1">限价</li>
								</ul>
							</div>
						</div>
					</div>
					<div class="fl">
						<label>手数:</label>
						<input type="text" class="ipt" v-model="conditionNum" />
					</div>
				</div>
			</div>
			<h6>当日有效</h6>
			<p>1.止损单在云端执行，软件关闭后扔然有效，云端自动确认结算单。</p>
			<p>2.止损单在行情不活跃或快速发送变化下，不保证成交价为指定价。</p>
			<p>3.止损单存在风险，云端系统、网络故障情况下失效等。</p>
		</div>
	</div>
</template>

<script>
	import pro from '../../assets/js/common.js'
	export default{
		name: 'trade_details',
		data(){
			return{
				showDialog: false,
				tabList: ['未触发列表','已触发列表'],
				currentNum: 0,
				tabShow: true,
				conditionListCont: [],
				triggerConditionListCont: [],
				selectedNum: -1,
				currentId: '',
				status: '',
				statusName: '暂停',
				currentConditionOrder: '',
				conditionType: '',
				showPriceDialog: false,
				showTimeDialog: false,
				lastPrice: '',
				conditionNum: '',
				conditionPrice: '',
				conditionTime: '',
				additionPrice: '',
				priceType: '0',
				priceTypeName: '>',
				additionalPriceType: '0',
				additionalPriceTypeName: '>',
				directionType: '0',
				directionTypeName: '买入',
				orderType: '1',
				orderTypeName: '市价',
			}
		},
		computed: {
			conditionList(){
				return this.$store.state.market.conditionList;
			},
			triggerConditionList(){
				return this.$store.state.market.triggerConditionList;
			},
			tradeSocket(){
				return this.$store.state.tradeSocket;
			},
			orderTemplist(){
				return	this.$store.state.market.orderTemplist;
			},
			parameters(){
				return this.$store.state.market.Parameters;
			},
		},
		watch: {
			parameters: function(n,o){
				if(this.currentConditionOrder.CommodityNo != undefined){
					n.forEach(function(o, i){
						if(this.currentConditionOrder.CommodityNo == o.CommodityNo){
							this.lastPrice = this.orderTemplist[this.currentConditionOrder.CommodityNo].LastQuotation.LastPrice;
							this.lastPrice = parseFloat(this.lastPrice).toFixed(this.orderTemplist[this.currentConditionOrder.CommodityNo].DotSize);
						}
					}.bind(this));
				}
			},
			conditionList: function(n, o){
				this.regroupConditionList();
			},
			triggerConditionList: function(n, o){
				this.regroupTriggerConditionList();
			},
			additionPrice: function(n, o){
				if(n == 0){
					this.additionPrice = '';
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
					this.regroupTriggerConditionList();
				}
			},
			clickEvent: function(index, id, status, type){
				if(this.selectedNum == index){
					this.selectedNum = -1;
					this.currentId = '';
					this.currentConditionOrder = '';
					return;
				}
				this.selectedNum = index;
				this.currentId = id;
				this.status = status;
				this.conditionType = type;
				if(this.status == 0){
					this.statusName = '暂停';
				}else{
					this.statusName = '启动';
				}
				this.conditionList.forEach(function(o, i){
					if(this.currentId == o.ConditionNo){
						this.currentConditionOrder = o;
						this.conditionNum = o.Num;
						this.additionPrice = o.AdditionPrice;
					}
				}.bind(this));
			},
			suspendConditionOrder: function(){
				if(this.currentId == '' || this.currentId == undefined){
					layer.msg('请选择一条数据', {time: 1000});
					return;
				}
				var b, msg;
				this.conditionListCont.forEach(function(o,i){
					if(this.currentId == o.ConditionNo){
						if(o.Status == 0){    //如果处于运行中，则暂停
							msg = '是否暂停条件单？';
							b = {
								"Method": 'ModifyCondition',
								"Parameters": {
									"ConditionNo": o.ConditionNo,
									"ModifyFlag": 2, //暂停
									"Num": o.Num,
									"ConditionType": o.ConditionType,
									"PriceTriggerPonit": o.PriceTriggerPonit,
									"CompareType": o.CompareType,
									"TimeTriggerPoint": o.TimeTriggerPoint,
									"AB_BuyPoint": o.AB_BuyPoint,
									"AB_SellPoint": o.AB_SellPoint,
									"OrderType": o.OrderType,
									"StopLossType": o.StopLossType,
									"Direction": o.Drection,
									"StopLossDiff": 0.0,
									"StopWinDiff": 0.0,
									"AdditionFlag": o.AdditionFlag,
									"AdditionType": o.AdditionType,
									"AdditionPrice": o.AdditionPrice
								}
							};
						}else if(o.Status == 1){
							msg = '是否启动条件单？';
							b = {
								"Method": 'ModifyCondition',
								"Parameters": {
									"ConditionNo": o.ConditionNo,
									"ModifyFlag": 3, //启动
									"Num": o.Num,
									"ConditionType": o.ConditionType,
									"PriceTriggerPonit": o.PriceTriggerPonit,
									"CompareType": o.CompareType,
									"TimeTriggerPoint": o.TimeTriggerPoint,
									"AB_BuyPoint": o.AB_BuyPoint,
									"AB_SellPoint": o.AB_SellPoint,
									"OrderType": o.OrderType,
									"StopLossType": o.StopLossType,
									"Direction": o.Drection,
									"StopLossDiff": 0.0,
									"StopWinDiff": 0.0,
									"AdditionFlag": o.AdditionFlag,
									"AdditionType": o.AdditionType,
									"AdditionPrice": o.AdditionPrice
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
				var miniTikeSize = this.orderTemplist[this.currentConditionOrder.CommodityNo].MiniTikeSize;
				var msg, b;
				if(this.conditionType == 0){
					if(this.additionPrice){
						var d1 = this.additionPrice % miniTikeSize;
						if(d1 >= 0.000000001 && parseFloat(miniTikeSize - d1) >= 0.0000000001){
							layer.msg('输入附加价格不符合最小变动价，最小变动价为：' + miniTikeSize, {time: 1000});
							return;
						}
						//判断价格与附加价格是否形成区间
						switch (this.priceType){
							case '0':
								if(this.additionalPriceType == '0' || this.additionalPriceType == '2' || this.additionPrice <= this.conditionPrice){
									layer.msg('输入的条件不能形成区间', {time: 1000});
									return;
								}
								break;
							case '1':
								if(this.additionalPriceType == '0' || this.additionalPriceType == '2' || this.additionPrice <= this.conditionPrice){
									layer.msg('输入的条件不能形成区间', {time: 1000});
									return;
								}
								break;
							case '1':
								if(this.additionalPriceType == '1' || this.additionalPriceType == '3' || this.additionPrice >= this.conditionPrice){
									layer.msg('输入的条件不能形成区间', {time: 1000});
									return;
								}
								break;
							case '1':
								if(this.additionalPriceType == '1' || this.additionalPriceType == '3' || this.additionPrice >= this.conditionPrice){
									layer.msg('输入的条件不能形成区间', {time: 1000});
									return;
								}
								break;
							default:
								break;
						}
					}
					var d0 = this.conditionPrice % miniTikeSize;
					if(this.conditionPrice == '' || this.conditionPrice == 0 || this.conditionPrice == undefined){
						layer.msg('请输入价格', {time: 1000});
					}else if(d0 >= 0.000000001 && parseFloat(miniTikeSize - d0) >= 0.0000000001){
						layer.msg('输入价格不符合最小变动价，最小变动价为：' + miniTikeSize, {time: 1000});
					}else if(this.conditionNum == '' || this.conditionNum <= 0 || this.conditionNum == undefined){
						layer.msg('请输入手数', {time: 1000});
					}else{
						msg = '是否修改价格条件单？';
						b = {
							"Method": 'ModifyCondition',
							"Parameters":{
								'ConditionNo': this.currentConditionOrder.ConditionNo,
								'ModifyFlag': 0,
								'Num': parseInt(this.conditionNum),
								'ConditionType': 0,
								'PriceTriggerPonit': parseFloat(this.conditionPrice),
								'CompareType': parseInt(this.priceType),
								'TimeTriggerPoint': '',
								'AB_BuyPoint': 0.0,
								'AB_SellPoint': 0.0,
								'OrderType': parseInt(this.orderType),
								'StopLossType': 5,
								'Drection': parseInt(this.directionType),
								'StopLossDiff': 0.0,
								'StopWinDiff': 0.0,
								'AdditionFlag':(function(){
													if(this.additionPrice == '' || this.additionPrice == 0 || this.additionPrice == undefined){
														return false;
													}else{
														return true;
													}
											}.bind(this))(),
								'AdditionType': parseInt(this.additionalPriceType),
								'AdditionPrice': parseFloat(this.additionPrice)
							}
						};
						
						layer.confirm(msg, {
							btn: ['确定','取消']
						}, function(index){
							this.tradeSocket.send(JSON.stringify(b));
							layer.closeAll();
							this.showPriceDialog = false;
							this.showTimeDialog = false;
						}.bind(this));
					}
				}else{
					this.conditionTime = this.getNowFormatDate() + ' ' + $(".time").val();
					if(this.additionPrice){
						var d2 = this.additionPrice % miniTikeSize;
						if(d2 >= 0.000000001 && parseFloat(miniTikeSize-d2) >= 0.0000000001){
							layer.msg('输入附加价格不符合最小变动价，最小变动价为：' + miniTikeSize, {time: 1000});
						}
					}
					if(this.conditionNum == '' || this.conditionNum <= 0 || this.conditionNum == undefined){
						layer.msg('请输入手数', {time: 1000});
					}else{
						msg = '是否修改时间条件单？';
						b = {
							"Method": 'ModifyCondition',
							"Parameters":{
								'ConditionNo': this.currentConditionOrder.ConditionNo,
								'ModifyFlag': 0,
								'Num': parseInt(this.conditionNum),
								'ConditionType': 1,
								'PriceTriggerPonit': 0.00,
								'CompareType': 5,
								'TimeTriggerPoint': this.conditionTime,
								'AB_BuyPoint': 0.0,
								'AB_SellPoint': 0.0,
								'OrderType': parseInt(this.orderType),
								'StopLossType': 5,
								'Drection': parseInt(this.directionType),
								'StopLossDiff': 0.0,
								'StopWinDiff': 0.0,
								'AdditionFlag':(function(){
													if(this.additionPrice == '' || this.additionPrice == 0 || this.additionPrice == undefined){
														return false;
													}else{
														return true;
													}
											}.bind(this))(),
								'AdditionType': parseInt(this.additionalPriceType),
								'AdditionPrice': parseFloat(this.additionPrice)
							}
						};
						layer.confirm(msg, {
							btn: ['确定','取消']
						}, function(index){
							this.tradeSocket.send(JSON.stringify(b));
							layer.closeAll();
							this.showPriceDialog = false;
							this.showTimeDialog = false;
						}.bind(this));
					}
				}
			},
			editConditionOrder: function(){
				if(this.currentId == '' || this.currentId == undefined){
					layer.msg('请选择一条数据', {time: 1000});
				}else if(this.status == 0){
					layer.msg('运行中的条件单不能修改', {time: 1000});
				}else{
					var dialogObj, title;
					if(this.conditionType == 0){    //价格
						dialogObj = $("#edit_price_order");
						this.showPriceDialog = true;
						title = '修改价格条件单';
						this.currentConditionOrder.PriceTriggerPonit == 0 ? this.conditionPrice = '' : this.conditionPrice = this.currentConditionOrder.PriceTriggerPonit;
						if(this.currentConditionOrder.CompareType == 0){
							this.priceType = '0';
							this.priceTypeName = '>';
						}else if(this.currentConditionOrder.CompareType == 1){
							this.priceType = '1';
							this.priceTypeName = '<';
						}else if(this.currentConditionOrder.CompareType == 2){
							this.priceType = '2';
							this.priceTypeName = '>=';
						}else if(this.currentConditionOrder.CompareType == 3){
							this.priceType = '3';
							this.priceTypeName = '<=';
						}
					}else{   //时间
						dialogObj = $("#edit_time_order");
						this.showTimeDialog = true;
						title = '修改时间条件单';
						this.currentConditionOrder.TimeTriggerPoint.split(' ')[1] == '00:00:00' ? this.conditionTime = '': this.conditionTime = this.currentConditionOrder.TimeTriggerPoint.split(' ')[1];
						$(".time").val(this.conditionTime);
						
					}
					if(this.additionPrice != 0){
						if(this.currentConditionOrder.AdditionType == 0){
							this.additionalPriceType = '0';
							this.additionalPriceTypeName = '>';
						}else if(this.currentConditionOrder.AdditionType == 1){
							this.additionalPriceType = '1';
							this.additionalPriceTypeName = '<';
						}else if(this.currentConditionOrder.AdditionType == 2){
							this.additionalPriceType = '2';
							this.additionalPriceTypeName = '>=';
						}else if(this.currentConditionOrder.AdditionType == 3){
							this.additionalPriceType = '3';
							this.additionalPriceTypeName = '<=';
						}
					}
					if(this.currentConditionOrder.OrderType == 1){
						this.orderType = '1';
						this.orderTypeName = '市价';
					}else if(this.currentConditionOrder.OrderType == 2){
						this.orderType = '2';
						this.orderTypeName = '对手价';
					}
					if(this.currentConditionOrder.Drection == 0){
						this.directionType = '0';
						this.directionTypeName = '买入';
					}else if(this.currentConditionOrder.Drection == 1){
						this.directionType = '1';
						this.directionTypeName = '卖出';
					}
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
							this.showPriceDialog = false;
							this.showTimeDialog = false;
						}.bind(this),
						cancel: function(){
							this.showPriceDialog = false;
							this.showTimeDialog = false;
						}.bind(this)
					});
				}
			},
			deleteConditionOrder: function(){
				if(this.currentId == '' || this.currentId == undefined){
					layer.msg('请选择一条数据', {time: 1000});
					return;
				}
				this.conditionListCont.forEach(function(o, i){
					if(this.currentId == o.ConditionNo){
						let b = {
							"Method": 'ModifyCondition',
							"Parameters": {
								"ConditionNo": o.ConditionNo,
								"ModifyFlag": 1, //删除
								"Num": o.Num,
								"ConditionType": o.ConditionType,
								"PriceTriggerPonit": o.PriceTriggerPonit,
								"CompareType": o.CompareType,
								"TimeTriggerPoint": o.TimeTriggerPoint,
								"AB_BuyPoint": o.AB_BuyPoint,
								"AB_SellPoint": o.AB_SellPoint,
								"OrderType": o.OrderType,
								"StopLossType": o.StopLossType,
								"Direction": o.Drection,
								"StopLossDiff": 0.0,
								"StopWinDiff": 0.0,
								"AdditionFlag": o.AdditionFlag,
								"AdditionType": o.AdditionType,
								"AdditionPrice": o.AdditionPrice
							}
						};
						layer.confirm('是否删除条件单？', {
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
			regroupConditionList:function(){
				this.conditionListCont = [];
				this.conditionList.forEach(function(o, i){
					let obj = {};
					obj.AB_BuyPoint = o.AB_BuyPoint;
					obj.AB_SellPoint = o.AB_SellPoint;
					obj.AdditionFlag=o.AdditionFlag;
					obj.AdditionPrice = o.AdditionPrice;
					obj.AdditionType = o.AdditionType;
					obj.CommodityNo = o.CommodityNo;
					obj.CompareType = o.CompareType;
					obj.ConditionNo = o.ConditionNo;
					obj.ConditionType = o.ConditionType;
					obj.ContractNo = o.ContractNo;
					obj.Drection = o.Drection;
					obj.ExchangeNo = o.ExchangeNo;
					obj.InsertDateTime = o.InsertDateTime;
					obj.Num = o.Num;
					obj.OrderType = o.OrderType;
					obj.PriceTriggerPonit = o.PriceTriggerPonit;
					obj.Status = o.Status;
					obj.StatusMsg = o.StatusMsg;
					obj.StopLossDiff = o.StopLossDiff;
					obj.StopLossType = o.StopLossType;
					obj.StopLossWin = o.StopLossWin;
					obj.TimeTriggerPoint = o.TimeTriggerPoint;
					obj.TriggedTime = o.TriggedTime;
					obj.name=o.CommodityNo+o.ContractNo;
					obj.status00 = (function(){
									if(o.Status==0){
										return '运行中';
									}else if(o.Status==1){
										return '暂停';
									}else if(o.Status==2){
										return '已触发';
									}else if(o.Status==3){
										return '已取消';
									}else if(o.Status==4){
										return '插入失败';
									}else if(o.Status==5){
										return '触发失败';
									}
								})();
					obj.type = (function(){
									if(o.ConditionType==0){
										return '价格条件';
									}else if(o.ConditionType==1){
										return '时间条件';
									}else if(o.ConditionType==2){
										return 'AB单';
									}
								})();
					obj.conditions = (function(){
									if(o.AdditionFlag==0){ //没有附件条件
										if(o.CompareType==0){
											return '>'+o.PriceTriggerPonit;
										}else if(o.CompareType==1){
											return '<'+o.PriceTriggerPonit;
										}else if(o.CompareType==2){
											return '>='+o.PriceTriggerPonit;
										}else if(o.CompareType==3){
											return '<='+o.PriceTriggerPonit;
										}else{
											let s = o.TimeTriggerPoint.split(' ');
											if(o.AdditionType==0){
												return s[1]+' >'+o.AdditionPrice;
											}else if(o.AdditionType==1){
												return s[1]+' <'+o.AdditionPrice;
											}else if(o.AdditionType==2){
												return s[1]+' >='+o.AdditionPrice;
											}else if(o.AdditionType==3){
												return s[1]+' <='+o.AdditionPrice;
											}else{
												return s[1];
											}
										}
									}else{ //有附加条件
										if(o.CompareType==0){
											if(o.AdditionType==0){
												return '>'+o.PriceTriggerPonit+' >'+o.AdditionPrice;
											}else if(o.AdditionType==1){
												return '>'+o.PriceTriggerPonit+' <'+o.AdditionPrice;
											}else if(o.AdditionType==2){
												return '>'+o.PriceTriggerPonit+' >='+o.AdditionPrice;
											}else if(o.AdditionType==3){
												return '>'+o.PriceTriggerPonit+' <='+o.AdditionPrice;
											}
										}else if(o.CompareType==1){
											if(o.AdditionType==0){
												return '<'+o.PriceTriggerPonit+' >'+o.AdditionPrice;
											}else if(o.AdditionType==1){
												return '<'+o.PriceTriggerPonit+' <'+o.AdditionPrice;
											}else if(o.AdditionType==2){
												return '<'+o.PriceTriggerPonit+' >='+o.AdditionPrice;
											}else if(o.AdditionType==3){
												return '<'+o.PriceTriggerPonit+' <='+o.AdditionPrice;
											}
										}else if(o.CompareType==2){
											if(o.AdditionType==0){
												return '>='+o.PriceTriggerPonit+' >'+o.AdditionPrice;
											}else if(o.AdditionType==1){
												return '>='+o.PriceTriggerPonit+' <'+o.AdditionPrice;
											}else if(o.AdditionType==2){
												return '>='+o.PriceTriggerPonit+' >='+o.AdditionPrice;
											}else if(o.AdditionType==3){
												return '>='+o.PriceTriggerPonit+' <='+o.AdditionPrice;
											}
										}else if(o.CompareType==3){
											if(o.AdditionType==0){
												return '<='+o.PriceTriggerPonit+' >'+o.AdditionPrice;
											}else if(o.AdditionType==1){
												return '<='+o.PriceTriggerPonit+' <'+o.AdditionPrice;
											}else if(o.AdditionType==2){
												return '<='+o.PriceTriggerPonit+' >='+o.AdditionPrice;
											}else if(o.AdditionType==3){
												return '<='+o.PriceTriggerPonit+' <='+o.AdditionPrice;
											}
										}else{
											let s = o.TimeTriggerPoint.split(' ');
											if(o.AdditionType==0){
												return s[1]+' >'+o.AdditionPrice;
											}else if(o.AdditionType==1){
												return s[1]+' <'+o.AdditionPrice;
											}else if(o.AdditionType==2){
												return s[1]+' >='+o.AdditionPrice;
											}else if(o.AdditionType==3){
												return s[1]+' <='+o.AdditionPrice;
											}else{
												return s[1];
											}
										}
									}
								})();
					obj.order = (function(){
								if(o.Drection == 0){ //买
									if(o.OrderType==1){
										return '买,市价,'+o.Num+'手'
									}else{
										return '买,对手价,'+o.Num+'手'
									}
								} else if(o.Drection == 1){//卖
									if(o.OrderType==1){
										return '卖,市价,'+o.Num+'手'
									}else{
										return '卖,对手价,'+o.Num+'手'
									}
								}
							})();
					obj.term = '永久有效';
					obj.time = o.InsertDateTime;
					this.conditionListCont.push(obj);
				}.bind(this));
			},
			regroupTriggerConditionList: function(){
				this.triggerConditionListCont = [];
				this.triggerConditionList.forEach(function(o, i){
					let obj = {};
					obj.AB_BuyPoint = o.AB_BuyPoint;
					obj.AB_SellPoint = o.AB_SellPoint;
					obj.AdditionFlag=o.AdditionFlag;
					obj.AdditionPrice = o.AdditionPrice;
					obj.AdditionType = o.AdditionType;
					obj.CommodityNo = o.CommodityNo;
					obj.CompareType = o.CompareType;
					obj.ConditionNo = o.ConditionNo;
					obj.ConditionType = o.ConditionType;
					obj.ContractNo = o.ContractNo;
					obj.Drection = o.Drection;
					obj.ExchangeNo = o.ExchangeNo;
					obj.InsertDateTime = o.InsertDateTime;
					obj.Num = o.Num;
					obj.OrderType = o.OrderType;
					obj.PriceTriggerPonit = o.PriceTriggerPonit;
					obj.Status = o.Status;
					obj.StatusMsg = o.StatusMsg;
					obj.StopLossDiff = o.StopLossDiff;
					obj.StopLossType = o.StopLossType;
					obj.StopLossWin = o.StopLossWin;
					obj.TimeTriggerPoint = o.TimeTriggerPoint;
					obj.TriggedTime = o.TriggedTime;
					obj.name=o.CommodityNo+o.ContractNo;
					obj.status00 = (function(){
									if(o.Status==0){
										return '运行中';
									}else if(o.Status==1){
										return '暂停';
									}else if(o.Status==2){
										return '已触发';
									}else if(o.Status==3){
										return '已取消';
									}else if(o.Status==4){
										return '插入失败';
									}else if(o.Status==5){
										return '触发失败';
									}
								})();
					obj.type = (function(){
									if(o.ConditionType==0){
										return '价格条件';
									}else if(o.ConditionType==1){
										return '时间条件';
									}else if(o.ConditionType==2){
										return 'AB单';
									}
								})();
					obj.conditions = (function(){
									if(o.AdditionFlag==0){ //没有附件条件
										if(o.CompareType==0){
											return '>'+o.PriceTriggerPonit;
										}else if(o.CompareType==1){
											return '<'+o.PriceTriggerPonit;
										}else if(o.CompareType==2){
											return '>='+o.PriceTriggerPonit;
										}else if(o.CompareType==3){
											return '<='+o.PriceTriggerPonit;
										}else{
											let s = o.TimeTriggerPoint.split(' ');
											if(o.AdditionType==0){
												return s[1]+' >'+o.AdditionPrice;
											}else if(o.AdditionType==1){
												return s[1]+' <'+o.AdditionPrice;
											}else if(o.AdditionType==2){
												return s[1]+' >='+o.AdditionPrice;
											}else if(o.AdditionType==3){
												return s[1]+' <='+o.AdditionPrice;
											}else {
												return s[1];
											}
										}
									}else{ //有附加条件
										if(o.CompareType==0){
											if(o.AdditionType==0){
												return '>'+o.PriceTriggerPonit+' >'+o.AdditionPrice;
											}else if(o.AdditionType==1){
												return '>'+o.PriceTriggerPonit+' <'+o.AdditionPrice;
											}else if(o.AdditionType==2){
												return '>'+o.PriceTriggerPonit+' >='+o.AdditionPrice;
											}else if(o.AdditionType==3){
												return '>'+o.PriceTriggerPonit+' <='+o.AdditionPrice;
											}
										}else if(o.CompareType==1){
											if(o.AdditionType==0){
												return '<'+o.PriceTriggerPonit+' >'+o.AdditionPrice;
											}else if(o.AdditionType==1){
												return '<'+o.PriceTriggerPonit+' <'+o.AdditionPrice;
											}else if(o.AdditionType==2){
												return '<'+o.PriceTriggerPonit+' >='+o.AdditionPrice;
											}else if(o.AdditionType==3){
												return '<'+o.PriceTriggerPonit+' <='+o.AdditionPrice;
											}
										}else if(o.CompareType==2){
											if(o.AdditionType==0){
												return '>='+o.PriceTriggerPonit+' >'+o.AdditionPrice;
											}else if(o.AdditionType==1){
												return '>='+o.PriceTriggerPonit+' <'+o.AdditionPrice;
											}else if(o.AdditionType==2){
												return '>='+o.PriceTriggerPonit+' >='+o.AdditionPrice;
											}else if(o.AdditionType==3){
												return '>='+o.PriceTriggerPonit+' <='+o.AdditionPrice;
											}
										}else if(o.CompareType==3){
											if(o.AdditionType==0){
												return '<='+o.PriceTriggerPonit+' >'+o.AdditionPrice;
											}else if(o.AdditionType==1){
												return '<='+o.PriceTriggerPonit+' <'+o.AdditionPrice;
											}else if(o.AdditionType==2){
												return '<='+o.PriceTriggerPonit+' >='+o.AdditionPrice;
											}else if(o.AdditionType==3){
												return '<='+o.PriceTriggerPonit+' <='+o.AdditionPrice;
											}
										}else{
											let s = o.TimeTriggerPoint.split(' ');
											if(o.AdditionType==0){
												return s[1]+' >'+o.AdditionPrice;
											}else if(o.AdditionType==1){
												return s[1]+' <'+o.AdditionPrice;
											}else if(o.AdditionType==2){
												return s[1]+' >='+o.AdditionPrice;
											}else if(o.AdditionType==3){
												return s[1]+' <='+o.AdditionPrice;
											}else {
												return s[1];
											}
										}
									}
								})();
					obj.order = (function(){
								if(o.Drection == 0){ //买
									if(o.OrderType==1){
										return '买,市价,'+o.Num+'手'
									}else{
										return '买,对手价,'+o.Num+'手'
									}
								} else if(o.Drection == 1){//卖
									if(o.OrderType==1){
										return '卖,市价,'+o.Num+'手'
									}else{
										return '卖,对手价,'+o.Num+'手'
									}
								}
							})();
					obj.term = '永久有效';
					obj.time = o.InsertDateTime;
					this.triggerConditionListCont.push(obj);
					console.log(this.triggerConditionListCont);
				}.bind(this));
			},
			getNowFormatDate: function(){
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
			},
			getType: function(num){
				if(num == '0'){
					return '>';
				}else if(num == '1'){
					return '<';
				}else if(num == '2'){
					return '>=';
				}else if(num == '3'){
					return '<=';
				}
			}
		},
		mounted: function(){
			//重组数据
			this.regroupConditionList();
			//调用时间插件
			dateEvent('.time', 'time');
			//调用下拉框
			pro.selectEvent('#price_type', function(data){
				this.priceType = data;
				this.priceTypeName = this.getType(data);
			}.bind(this));
			pro.selectEvent('#additional_price_type', function(data){
				this.additionalPriceType = data;
				this.additionalPriceTypeName = this.getType(data);
			}.bind(this));
			pro.selectEvent('#direction_type', function(data){
				this.directionType = data;
				data == '0' ? this.directionTypeName = '买入' : this.directionTypeName = '卖出';
			}.bind(this));
			pro.selectEvent('#condition_price_type', function(data){
				this.orderType = data;
				data == '1' ? this.orderTypeName = '市价' : this.orderTypeName = '对手价';
			}.bind(this));
		}
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
				width: 268px;
			}
			label{
				float: left;
				display: inline-block;
				width: 40px;
				line-height: 30px;
			}
			span{
				float: left;
				color: $white;
				line-height: 30px;
			}
			.ipt{
				float: left;
				width: 58px;
				height: 26px;
				line-height: 26px;
				border: 1px solid #474c66;
				border-radius: 4px;
				color: $white;
				text-align: center;
				&.time{
					width: 80px;
					margin: 0 20px 0 5px;
				}
			}
			.row_money_box, .row_price_box{
				width: 80px;
				margin-left: 5px;
				.slt{
					width: 78px;
					padding-left: 0;
				}
				.slt-list{
					width: 80px;
					height: 48px;
					overflow: hidden;
					li{
						width: 80px;
						height: 24px;
						line-height: 24px;
					}
				}
				span{
					line-height: 54px;
				}
			}
			.row_price_box{
				width: 60px;
				margin-right: 5px;
				.slt{
					width: 58px;
					padding-left: 0;
				}
				.slt-list{
					width: 60px;
					height: 96px;
					li{
						width: 60px;
					}
				}
			}
		}
	}
	h6{
		height: 30px;
		line-height: 30px;
		overflow: hidden;
		text-align: center;
		background: $deepblue;
		margin-bottom: 15px;
	}
	p{
		font-size: $fs12;
		line-height: 22px;
		padding: 0 10px;
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