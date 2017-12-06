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
			<div class="tools">
				<button class="btn blue">暂停</button>
				<button class="btn blue" @click="editConditionOrder">修改</button>
				<button class="btn blue">删除</button>
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
		<div id="edit_price_order" v-show="showDialog">
			<div class="edit_order cont">
				<div class="row">
					<div class="fl">
						<label>合约:</label>
						<span>HSI,恒指期货</span>
					</div>
					<div class="fl">
						<label>最新:</label>
						<span>56.12</span>
					</div>
				</div>
				<div class="row">
						<label>条件:</label>
						<span>价格</span>
						<div class="slt-box row_price_box">
							<input type="text" class="slt" disabled="disabled" selectVal="0" value=">="/>
							<span class="tal-box"><span class="tal"></span></span>
							<div class="slt-list">
								<ul>
									<li selectVal="0">></li>
									<li selectVal="1">>=</li>
									<li selectVal="2"><</li>
									<li selectVal="3"><=</li>
								</ul>
							</div>
						</div>
						<input type="text" class="ipt" />
						<label>附加:</label>
						<div class="slt-box row_price_box">
							<input type="text" class="slt" disabled="disabled" selectVal="0" value=">="/>
							<span class="tal-box"><span class="tal"></span></span>
							<div class="slt-list">
								<ul>
									<li selectVal="0">></li>
									<li selectVal="1">>=</li>
									<li selectVal="2"><</li>
									<li selectVal="3"><=</li>
								</ul>
							</div>
						</div>
						<input type="text" class="ipt" />
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
						<input type="text" class="ipt" />
					</div>
				</div>
			</div>
			<h6>当日有效</h6>
			<p>1.止损单在云端执行，软件关闭后扔然有效，云端自动确认结算单。</p>
			<p>2.止损单在行情不活跃或快速发送变化下，不保证成交价为指定价。</p>
			<p>3.止损单存在风险，云端系统、网络故障情况下失效等。</p>
		</div>
		<div id="edit_time_order" v-show="showDialog">
			<div class="edit_order cont">
				<div class="row">
					<div class="fl">
						<label>合约:</label>
						<span>HSI,恒指期货</span>
					</div>
					<div class="fl">
						<label>最新:</label>
						<span>56.12</span>
					</div>
				</div>
				<div class="row">
						<label>条件:</label>
						<span>时间</span>
						<input type="text" class="ipt time" />
						<label>附加:</label>
						<div class="slt-box row_price_box">
							<input type="text" class="slt" disabled="disabled" selectVal="0" value=">="/>
							<span class="tal-box"><span class="tal"></span></span>
							<div class="slt-list">
								<ul>
									<li selectVal="0">></li>
									<li selectVal="1">>=</li>
									<li selectVal="2"><</li>
									<li selectVal="3"><=</li>
								</ul>
							</div>
						</div>
						<input type="text" class="ipt" />
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
						<input type="text" class="ipt" />
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
		},
		watch: {
			conditionList: function(n, o){
				this.regroupConditionList();
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
			editConditionOrder: function(){
				this.showDialog = true;
				layer.open({
					type: 1,
					title: '修改价格条件单',
					area: ['400px', 'auto'],
					content: $("#edit_time_order"),
					btn: ['确定','取消'],
					btn1: function(index){
						
						layer.close(index);
						this.showDialog = false;
					}.bind(this),
					btn2: function(){
						this.showDialog = false;
					}.bind(this),
					cancel: function(){
						this.showDialog = false;
					}.bind(this)
				});
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
					obj.term = '当日有效';
					obj.time = o.InsertDateTime;
					this.conditionListCont.push(obj);
					console.log(this.conditionListCont);
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
					obj.term = '当日有效';
					obj.time = o.InsertDateTime;
					this.triggerConditionListCont.push(obj);
					console.log(this.triggerConditionListCont);
				}.bind(this));
			},
		},
		mounted: function(){
			//重组数据
			this.regroupConditionList();
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
		overflow: hidden;
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