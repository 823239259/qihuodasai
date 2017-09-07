<template>
	<div id="conditions">
		<tipsDialog :msg="msgTips"></tipsDialog>
		<ifalert :objstr="sendObj"></ifalert>
		<div class="head">
			<topbar title="条件单"></topbar>
			<back></back>
			<i class="add" @tap="addConditions"></i>
		</div>
		<div class="page_cont">
			<div class="tab_box" id="tabBox">
				<template v-for="key in tabList">
					<div class="tab_box_col" @tap="showCont">
						<span>{{key.nav}}</span>
					</div>
				</template>
			</div>
			<div id="noCont" class="list" v-if="isShow">
				<ul class="list_cont_box">
					<li class="list_head">
						<span>合约名称</span>
						<span>状态</span>
						<span>类型</span>
						<span>条件</span>
						<span>下单</span>
						<span>有效日期</span>
						<span>下单时间</span>
					</li>
					<template v-for="k in noListCont">
						<li @tap="listTap" :id="k.ConditionNo" :status='k.Status' :compareType='k.CompareType'>
							<div class="list_cont">
								<span>{{k.name}}</span>
								<span>{{k.status00}}</span>
								<span>{{k.type}}</span>
								<span>{{k.conditions}}</span>
								<span>{{k.order}}</span>
								<span>{{k.term}}</span>
								<span>{{k.time}}</span>
							</div>
						</li>
					</template>
				</ul>
				<div class="list_tools">
					<cbtn :name="statusName" @tap.native="suspendEvent"></cbtn>
					<cbtn name="修改" @tap.native="modify"></cbtn>
					<cbtn name="删除" @tap.native="deleteEvent"></cbtn>
				</div>
			</div>
			<div id="yesCont" class="list" v-else="isShow">
				<ul class="list_cont_box">
					<li class="list_head">
						<span>合约名称</span>
						<span>状态</span>
						<span>类型</span>
						<span>条件</span>
						<span>下单</span>
						<span>有效日期</span>
						<span>触发时间</span>
					</li>
					<template v-for="k in yesListCont">
						<li @tap="listTap" id="123">
							<div class="list_cont">
								<span>{{k.name}}</span>
								<span>{{k.status00}}</span>
								<span>{{k.type}}</span>
								<span>{{k.conditions}}</span>
								<span>{{k.order}}</span>
								<span>{{k.term}}</span>
								<span>{{k.time}}</span>
							</div>
						</li>
					</template>
				</ul>
			</div>
		</div>
	</div>
</template>

<script>
	import topbar from '../../components/Topbar.vue'
	import back from '../../components/back.vue'
	import cbtn from '../../components/conditionBtn.vue'
	import ifalert from '../../components/ifalert.vue'
	import tipsDialog from '../../components/tipsDialog.vue'
	export default{
		name:'conditions',
		components:{topbar, back, cbtn, ifalert, tipsDialog},
		data(){
			return {
				msg: '',
				isShow: true,
				tabList: [{nav:'未触发列表'},{nav:'已触发列表'}],
				orderListId:'',
//				noListCont:[],
//				yesListCont:[],
				orderStatus: '',
				statusName: '暂停',
				orderType: '',
				sendMsg: ''
			}
		},
		computed:{
			msgTips: function(){
				return this.msg;
			},
			conditionList(){
				return this.$store.state.market.conditionList;
			},
			triggerConditionList(){
				return this.$store.state.market.triggerConditionList;
			},
			noListCont(){
				return this.$store.state.market.noListCont;
			},
			yesListCont(){
				return this.$store.state.market.yesListCont;
			},
			tradeSocket(){
				return this.$store.state.tradeSocket;
			},
			sendObj: function(){
				if(this.sendMsg) return JSON.stringify(this.sendMsg);
			}
		},
		methods: {
			modify:function(){
				this.$children[1].isshow = true;
//				if(this.orderType == 5){
//					this.$children[1].ifshow = false;
					this.noListCont.forEach(function(e,i){
						if(this.orderListId == e.ConditionNo){
							this.sendMsg = e;
						}
					}.bind(this));
//					let o = this.$store.state.market.noObj;
//					console.log(JSON.stringify(o));
//				}else{
//					this.$children[1].ifshow = true;
//				}
				
			},
			deleteEvent:function(){
				if(this.orderListId == '' || this.orderListId == null){
					this.$refs.dialog.isShow = true;
					this.msg = '请选择一条数据';
				}else{
					this.noListCont.forEach(function(e,i){
						if(this.orderListId==e.ConditionNo){
							this.$store.state.market.noObj = e;
						}
					}.bind(this));
					let o = this.$store.state.market.noObj;
					let b={
							"Method":'ModifyCondition',
							"Parameters":{
								"ConditionNo":o.ConditionNo,
								"ModifyFlag":1, //删除
								"Num":o.Num,
								"ConditionType":o.ConditionType,
								"PriceTriggerPonit":o.PriceTriggerPonit,
								"CompareType":o.CompareType,
								"TimeTriggerPoint":o.TimeTriggerPoint,
								"AB_BuyPoint":o.AB_BuyPoint,
								"AB_SellPoint":o.AB_SellPoint,
								"OrderType":o.OrderType,
								"StopLossType":o.StopLossType,
								"Direction":o.Drection,
								"StopLossDiff":0.0,
								"StopWinDiff":0.0,
								"AdditionFlag":o.AdditionFlag,
								"AdditionType":o.AdditionType,
								"AdditionPrice":o.AdditionPrice
							}
						};
						this.tradeSocket.send(JSON.stringify(b));	
					
				}
			},
			suspendEvent:function(){
				if(this.orderListId == '' || this.orderListId == null){
					this.$refs.dialog.isShow = true;
					this.msg = '请选择一条数据';
				}else{
					this.noListCont.forEach(function(e,i){
						if(this.orderListId==e.ConditionNo){
							this.$store.state.market.noObj = e;
						}
					}.bind(this));
					let o = this.$store.state.market.noObj;
					if(o.Status==0){//如果处于运行中，则暂停
							let b={
							"Method":'ModifyCondition',
							"Parameters":{
								"ConditionNo":o.ConditionNo,
								"ModifyFlag":2, //暂停
								"Num":o.Num,
								"ConditionType":o.ConditionType,
								"PriceTriggerPonit":o.PriceTriggerPonit,
								"CompareType":o.CompareType,
								"TimeTriggerPoint":o.TimeTriggerPoint,
								"AB_BuyPoint":o.AB_BuyPoint,
								"AB_SellPoint":o.AB_SellPoint,
								"OrderType":o.OrderType,
								"StopLossType":o.StopLossType,
								"Direction":o.Drection,
								"StopLossDiff":0.0,
								"StopWinDiff":0.0,
								"AdditionFlag":o.AdditionFlag,
								"AdditionType":o.AdditionType,
								"AdditionPrice":o.AdditionPrice
							}
						};
						this.tradeSocket.send(JSON.stringify(b));	
					}else if(o.Status==1){
						let b={
							"Method":'ModifyCondition',
							"Parameters":{
								"ConditionNo":o.ConditionNo,
								"ModifyFlag":3, //暂停
								"Num":o.Num,
								"ConditionType":o.ConditionType,
								"PriceTriggerPonit":o.PriceTriggerPonit,
								"CompareType":o.CompareType,
								"TimeTriggerPoint":o.TimeTriggerPoint,
								"AB_BuyPoint":o.AB_BuyPoint,
								"AB_SellPoint":o.AB_SellPoint,
								"OrderType":o.OrderType,
								"StopLossType":o.StopLossType,
								"Direction":o.Drection,
								"StopLossDiff":0.0,
								"StopWinDiff":0.0,
								"AdditionFlag":o.AdditionFlag,
								"AdditionType":o.AdditionType,
								"AdditionPrice":o.AdditionPrice
							}
						};
						this.tradeSocket.send(JSON.stringify(b));	
					}
				}
				$(".list_cont_box li").removeClass("current");
			},
			showCont: function(e){
				$(e.currentTarget).find("span").addClass('current');
				$(e.currentTarget).siblings().find("span").removeClass('current')
				if($(e.currentTarget).index() == 0){
					this.isShow = true;
				}else{
					this.isShow = false;
				}
			},
			listTap: function(obj){
				if(!$(obj.currentTarget).hasClass("current")){
					$(obj.currentTarget).addClass("current");
					$(obj.currentTarget).siblings().removeClass("current");
					this.orderListId = $(obj.currentTarget).attr("id");
					this.orderStatus = $(obj.currentTarget).attr("status");
					this.orderType = $(obj.currentTarget).attr("compareType");
					if(this.orderStatus == 0){
						this.statusName = '暂停';
					}else{
						this.statusName = '启动';
					}
				}else{
					$(obj.currentTarget).removeClass("current");
					this.orderListId =null;
				}
			},
			addConditions: function(){
				this.$children[1].isshow = true;
			},
			regroupConditionList:function(){
				this.conditionList.forEach(function(e,i){
					let b={};
					b.AB_BuyPoint = e.AB_BuyPoint;
					b.AB_SellPoint = e.AB_SellPoint;
					b.AdditionFlag=e.AdditionFlag;
					b.AdditionPrice = e.AdditionPrice;
					b.AdditionType = e.AdditionType;
					b.CommodityNo = e.CommodityNo;
					b.CompareType = e.CompareType;
					b.ConditionNo = e.ConditionNo;
					b.ConditionType = e.ConditionType;
					b.ContractNo = e.ContractNo;
					b.Drection = e.Drection;
					b.ExchangeNo = e.ExchangeNo;
					b.InsertDateTime = e.InsertDateTime;
					b.Num = e.Num;
					b.OrderType = e.OrderType;
					b.PriceTriggerPonit = e.PriceTriggerPonit;
					b.Status = e.Status;
					b.StatusMsg = e.StatusMsg;
					b.StopLossDiff = e.StopLossDiff;
					b.StopLossType = e.StopLossType;
					b.StopLossWin = e.StopLossWin;
					b.TimeTriggerPoint = e.TimeTriggerPoint;
					b.TriggedTime = e.TriggedTime;
					
					b.name=e.CommodityNo+e.ContractNo;
					b.status00 = (function(){
									if(e.Status==0){
										return '运行中';
									}else if(e.Status==1){
										return '暂停';
									}else if(e.Status==2){
										return '已触发';
									}else if(e.Status==3){
										return '已取消';
									}else if(e.Status==4){
										return '插入失败';
									}else if(e.Status==5){
										return '触发失败';
									}
								})();
					b.type = (function(){
									if(e.ConditionType==0){
										return '价格条件';
									}else if(e.ConditionType==1){
										return '时间条件';
									}else if(e.ConditionType==2){
										return 'AB单';
									}
								})();
					
					b.conditions = (function(){
									
									if(e.AdditionFlag==0){ //没有附件条件
										if(e.CompareType==0){
											return '>'+e.PriceTriggerPonit;
										}else if(e.CompareType==1){
											return '<'+e.PriceTriggerPonit;
										}else if(e.CompareType==2){
											return '>='+e.PriceTriggerPonit;
										}else if(e.CompareType==3){
											return '<='+e.PriceTriggerPonit;
										}else{
											
											let s = e.TimeTriggerPoint.split(' ');
											if(e.AdditionType==0){
												return s[1]+' >'+e.AdditionPrice;
											}else if(e.AdditionType==1){
												return s[1]+' <'+e.AdditionPrice;
											}else if(e.AdditionType==2){
												return s[1]+' >='+e.AdditionPrice;
											}else if(e.AdditionType==3){
												return s[1]+' <='+e.AdditionPrice;
											}else{
												return s[1];
											}
											
										}
									}else{ //有附加条件
										if(e.CompareType==0){
											if(e.AdditionType==0){
												return '>'+e.PriceTriggerPonit+' >'+e.AdditionPrice;
											}else if(e.AdditionType==1){
												return '>'+e.PriceTriggerPonit+' <'+e.AdditionPrice;
											}else if(e.AdditionType==2){
												return '>'+e.PriceTriggerPonit+' >='+e.AdditionPrice;
											}else if(e.AdditionType==3){
												return '>'+e.PriceTriggerPonit+' <='+e.AdditionPrice;
											}
										}else if(e.CompareType==1){
											if(e.AdditionType==0){
												return '<'+e.PriceTriggerPonit+' >'+e.AdditionPrice;
											}else if(e.AdditionType==1){
												return '<'+e.PriceTriggerPonit+' <'+e.AdditionPrice;
											}else if(e.AdditionType==2){
												return '<'+e.PriceTriggerPonit+' >='+e.AdditionPrice;
											}else if(e.AdditionType==3){
												return '<'+e.PriceTriggerPonit+' <='+e.AdditionPrice;
											}
										}else if(e.CompareType==2){
											if(e.AdditionType==0){
												return '>='+e.PriceTriggerPonit+' >'+e.AdditionPrice;
											}else if(e.AdditionType==1){
												return '>='+e.PriceTriggerPonit+' <'+e.AdditionPrice;
											}else if(e.AdditionType==2){
												return '>='+e.PriceTriggerPonit+' >='+e.AdditionPrice;
											}else if(e.AdditionType==3){
												return '>='+e.PriceTriggerPonit+' <='+e.AdditionPrice;
											}
										}else if(e.CompareType==3){
											if(e.AdditionType==0){
												return '<='+e.PriceTriggerPonit+' >'+e.AdditionPrice;
											}else if(e.AdditionType==1){
												return '<='+e.PriceTriggerPonit+' <'+e.AdditionPrice;
											}else if(e.AdditionType==2){
												return '<='+e.PriceTriggerPonit+' >='+e.AdditionPrice;
											}else if(e.AdditionType==3){
												return '<='+e.PriceTriggerPonit+' <='+e.AdditionPrice;
											}
										}else{
											
											let s = e.TimeTriggerPoint.split(' ');
											if(e.AdditionType==0){
												return s[1]+' >'+e.AdditionPrice;
											}else if(e.AdditionType==1){
												return s[1]+' <'+e.AdditionPrice;
											}else if(e.AdditionType==2){
												return s[1]+' >='+e.AdditionPrice;
											}else if(e.AdditionType==3){
												return s[1]+' <='+e.AdditionPrice;
											}else{
												return s[1];
											}
											
										}
									}
									
								})();
					b.order = (function(){
								if(e.Drection == 0){ //买
									if(e.OrderType==1){
										return '买,市价,'+e.Num+'手'
									}else{
										return '买,限价,'+e.Num+'手'
									}
								} else if(e.Drection == 1){//卖
									if(e.OrderType==1){
										return '卖,市价,'+e.Num+'手'
									}else{
										return '卖,限价,'+e.Num+'手'
									}
								}
								
								
							})();
					b.term = '当日有效';
					b.time = e.InsertDateTime;
					
					this.noListCont.push(b);
					
				}.bind(this));
			},
			regroupTriggerConditionList:function(){
				this.triggerConditionList.forEach(function(e,i){
					let b={};
					b.AB_BuyPoint = e.AB_BuyPoint;
					b.AB_SellPoint = e.AB_SellPoint;
					b.AdditionFlag=e.AdditionFlag;
					b.AdditionPrice = e.AdditionPrice;
					b.AdditionType = e.AdditionType;
					b.CommodityNo = e.CommodityNo;
					b.CompareType = e.CompareType;
					b.ConditionNo = e.ConditionNo;
					b.ConditionType = e.ConditionType;
					b.ContractNo = e.ContractNo;
					b.Drection = e.Drection;
					b.ExchangeNo = e.ExchangeNo;
					b.InsertDateTime = e.InsertDateTime;
					b.Num = e.Num;
					b.OrderType = e.OrderType;
					b.PriceTriggerPonit = e.PriceTriggerPonit;
					b.Status = e.Status;
					b.StatusMsg = e.StatusMsg;
					b.StopLossDiff = e.StopLossDiff;
					b.StopLossType = e.StopLossType;
					b.StopLossWin = e.StopLossWin;
					b.TimeTriggerPoint = e.TimeTriggerPoint;
					b.TriggedTime = e.TriggedTime;
					
					b.name=e.CommodityNo+e.ContractNo;
					b.status00 = (function(){
									if(e.Status==0){
										return '运行中';
									}else if(e.Status==1){
										return '暂停';
									}else if(e.Status==2){
										return '已触发';
									}else if(e.Status==3){
										return '已取消';
									}else if(e.Status==4){
										return '插入失败';
									}else if(e.Status==5){
										return '触发失败';
									}
								})();
					b.type = (function(){
									if(e.ConditionType==0){
										return '价格条件';
									}else if(e.ConditionType==1){
										return '时间条件';
									}else if(e.ConditionType==2){
										return 'AB单';
									}
								})();
					
					b.conditions = (function(){
									
									if(e.AdditionFlag==0){ //没有附件条件
										if(e.CompareType==0){
											return '>'+e.PriceTriggerPonit;
										}else if(e.CompareType==1){
											return '<'+e.PriceTriggerPonit;
										}else if(e.CompareType==2){
											return '>='+e.PriceTriggerPonit;
										}else if(e.CompareType==3){
											return '<='+e.PriceTriggerPonit;
										}else{
											let s = e.TimeTriggerPoint.split(' ');
											if(e.AdditionType==0){
												return s[1]+' >'+e.AdditionPrice;
											}else if(e.AdditionType==1){
												return s[1]+' <'+e.AdditionPrice;
											}else if(e.AdditionType==2){
												return s[1]+' >='+e.AdditionPrice;
											}else if(e.AdditionType==3){
												return s[1]+' <='+e.AdditionPrice;
											}else {
												return s[1];
											}
										}
									}else{ //有附加条件
										if(e.CompareType==0){
											if(e.AdditionType==0){
												return '>'+e.PriceTriggerPonit+' >'+e.AdditionPrice;
											}else if(e.AdditionType==1){
												return '>'+e.PriceTriggerPonit+' <'+e.AdditionPrice;
											}else if(e.AdditionType==2){
												return '>'+e.PriceTriggerPonit+' >='+e.AdditionPrice;
											}else if(e.AdditionType==3){
												return '>'+e.PriceTriggerPonit+' <='+e.AdditionPrice;
											}
										}else if(e.CompareType==1){
											if(e.AdditionType==0){
												return '<'+e.PriceTriggerPonit+' >'+e.AdditionPrice;
											}else if(e.AdditionType==1){
												return '<'+e.PriceTriggerPonit+' <'+e.AdditionPrice;
											}else if(e.AdditionType==2){
												return '<'+e.PriceTriggerPonit+' >='+e.AdditionPrice;
											}else if(e.AdditionType==3){
												return '<'+e.PriceTriggerPonit+' <='+e.AdditionPrice;
											}
										}else if(e.CompareType==2){
											if(e.AdditionType==0){
												return '>='+e.PriceTriggerPonit+' >'+e.AdditionPrice;
											}else if(e.AdditionType==1){
												return '>='+e.PriceTriggerPonit+' <'+e.AdditionPrice;
											}else if(e.AdditionType==2){
												return '>='+e.PriceTriggerPonit+' >='+e.AdditionPrice;
											}else if(e.AdditionType==3){
												return '>='+e.PriceTriggerPonit+' <='+e.AdditionPrice;
											}
										}else if(e.CompareType==3){
											if(e.AdditionType==0){
												return '<='+e.PriceTriggerPonit+' >'+e.AdditionPrice;
											}else if(e.AdditionType==1){
												return '<='+e.PriceTriggerPonit+' <'+e.AdditionPrice;
											}else if(e.AdditionType==2){
												return '<='+e.PriceTriggerPonit+' >='+e.AdditionPrice;
											}else if(e.AdditionType==3){
												return '<='+e.PriceTriggerPonit+' <='+e.AdditionPrice;
											}
										}else{
											
											let s = e.TimeTriggerPoint.split(' ');
											if(e.AdditionType==0){
												return s[1]+' >'+e.AdditionPrice;
											}else if(e.AdditionType==1){
												return s[1]+' <'+e.AdditionPrice;
											}else if(e.AdditionType==2){
												return s[1]+' >='+e.AdditionPrice;
											}else if(e.AdditionType==3){
												return s[1]+' <='+e.AdditionPrice;
											}else {
												return s[1];
											}
											
										}
									}
									
								})();
					b.order = (function(){
								if(e.Drection == 0){ //买
									if(e.OrderType==1){
										return '买,市价,'+e.Num+'手'
									}else{
										return '买,限价,'+e.Num+'手'
									}
								} else if(e.Drection == 1){//卖
									if(e.OrderType==1){
										return '卖,市价,'+e.Num+'手'
									}else{
										return '卖,限价,'+e.Num+'手'
									}
								}
								
								
							})();
					b.term = '当日有效';
					b.time = e.InsertDateTime;
					
					this.yesListCont.push(b);
					
				}.bind(this));
			}
		},
		mounted: function(){
			//页面滚动高度计算
			$("#tabBox .tab_box_col:first-child span").addClass("current");
			var screenHeight = window.screen.height;
			$("#conditions").css("height", screenHeight + "px");
			var h = $("#topbar").height() + $(".tab_box").height() + $(".list ul:first-child").height();
			$(".list_cont_box").css("height", screenHeight - h - 20 + 'px');
			this.regroupConditionList();
			this.regroupTriggerConditionList();
		},
		activated: function(){
			//不更新画图
			this.$store.state.isshow.isklineshow = false;
			this.$store.state.isshow.isfensshow = false;
			this.$store.state.isshow.islightshow =  false;
		}
	}
</script>

<style scoped lang="less">
	@import url("../../assets/css/main.less");
	@import url("../../assets/css/base.less");
	/*ip6p及以上*/
	@media (min-width:411px) {
	    #conditions{
			width: 100%;
			overflow: hidden;
			padding-top: 50px;
			background: @black;
		}
		.head{
			#back{
				position: fixed;
				z-index: 1000;
				top: 0;
				left: 0;
			}
			.add{
				display: block;
				width: 50px;
				height: 50px;
				background: url(../../assets/img/add.png) no-repeat 14px 14px;
				background-size: 22px 22px;
				position: fixed;
				z-index: 1000;
				top: 0;
				right: 0;
			}
		}
		.page_cont{
			width: 100%;
			position: fixed;
			top: 50px;
			left: 0;
			.tab_box{
				height: 44px;
				background: @deepblue;
				.tab_box_col{
					float: left;
					width: 50%;
					text-align: center;
					span{
						display: inline-block;
						height: 44px;
						line-height: 44px;
						color: @lightblue;
						font-size: @fs14;
						&.current{
							color: @yellow;
							border-bottom: 4px solid @yellow;
						}
					}	
				}
			}
			.list{
				ul{
					width: 100%;
					padding: 0;
					overflow-y: scroll;
				}
				li{
					width: 800px;
					background: @deepblue;
					padding-left: 15px;
					border-top: 1px solid @black;
					&.current{
						background: #2d3040;
					}
					&.list_head{
						height: 44px;
						background: #36394d;
					}
					.list_cont{
						height: 44px;
						&.current{
							background: #2d3040;
						}
					}
					span{
						display: inline-block;
						height: 44px;
						line-height: 44px;
						overflow: hidden;
						color: @lightblue;
						font-size: @fs14;
						margin: 0 0.4%;
						&:nth-child(1){
							width: 100px;
						}
						&:nth-child(2){
							width: 65px;
						}
						&:nth-child(3){
							width: 80px;
						}
						&:nth-child(4){
							width: 140px;
						}
						&:nth-child(5){
							width: 90px;
						}
						&:nth-child(6){
							width: 80px;
						}
						&:nth-child(7){
							width: 130px;
						}
						&.red{
							color: @red;
						}
						&.green{
							color: @green;
						}
					}
				}
				.list_tools{
					position: fixed;
					bottom: 0;
					left: 0;
					width: 100%;
					height: 44px;
					overflow: hidden;
					text-align: center;
					#conditionBtn{
						display: inline-block;
						margin: 6px 10px 0 10px;
					}
				}
			}
		}
	}
	
	/*ip6*/
	@media (min-width:371px) and (max-width:410px) {
	    #conditions{
			width: 100%;
			overflow: hidden;
			padding-top: 50px*@ip6;
			background: @black;
		}
		.head{
			position: relative;
			#back{
				position: fixed;
				z-index: 1000;
				top: 0;
				left: 0;
			}
			.add{
				display: block;
				width: 50px*@ip6;
				height: 50px*@ip6;
				background: url(../../assets/img/add.png) no-repeat 14px*@ip6 14px*@ip6;
				background-size: 22px*@ip6 22px*@ip6;
				position: fixed;
				z-index: 1000;
				top: 0;
				right: 0;
			}
		}
		.page_cont{
			width: 100%;
			position: fixed;
			top: 50px*@ip6;
			left: 0;
			.tab_box{
				height: 44px*@ip6;
				background: @deepblue;
				.tab_box_col{
					float: left;
					width: 50%;
					text-align: center;
					span{
						display: inline-block;
						height: 44px*@ip6;
						line-height: 44px*@ip6;
						color: @lightblue;
						font-size: @fs14*@ip6;
						&.current{
							color: @yellow;
							border-bottom: 4px*@ip6 solid @yellow;
						}
					}	
				}
			}
			.list{
				ul{
					width: 100%;
					padding: 0;
					overflow-y: scroll;
				}
				li{
					width: 800px*@ip6;
					background: @deepblue;
					padding-left: 15px*@ip6;
					border-top: 1px solid @black;
					&.current{
						background: #2d3040;
					}
					&.list_head{
						height: 44px*@ip6;
						background: #36394d;
					}
					.list_cont{
						height: 44px*@ip6;
						&.current{
							background: #2d3040;
						}
					}
					span{
						display: inline-block;
						height: 44px*@ip6;
						line-height: 44px*@ip6;
						overflow: hidden;
						color: @lightblue;
						font-size: @fs14*@ip6;
						margin: 0 0.4%;
						&:nth-child(1){
							width: 100px*@ip6;
						}
						&:nth-child(2){
							width: 65px*@ip6;
						}
						&:nth-child(3){
							width: 80px*@ip6;
						}
						&:nth-child(4){
							width: 140px*@ip6;
						}
						&:nth-child(5){
							width: 90px*@ip6;
						}
						&:nth-child(6){
							width: 80px*@ip6;
						}
						&:nth-child(7){
							width: 130px*@ip6;
						}
						&.red{
							color: @red;
						}
						&.green{
							color: @green;
						}
					}
				}
				.list_tools{
					position: fixed;
					bottom: 0;
					left: 0;
					width: 100%;
					height: 44px*@ip6;
					overflow: hidden;
					text-align: center;
					#conditionBtn{
						display: inline-block;
						margin: 6px*@ip6 10px*@ip6 0 10px*@ip6;
					}
				}
			}
		}
	}
	
	/*ip5*/
	@media(max-width:370px) {
		#conditions{
			width: 100%;
			overflow: hidden;
			padding-top: 50px*@ip5;
			background: @black;
		}
		.head{
			position: relative;
			#back{
				position: fixed;
				z-index: 1000;
				top: 0;
				left: 0;
			}
			.add{
				display: block;
				width: 50px*@ip5;
				height: 50px*@ip5;
				background: url(../../assets/img/add.png) no-repeat 14px*@ip5 14px*@ip5;
				background-size: 22px*@ip5 22px*@ip5;
				position: fixed;
				z-index: 1000;
				top: 0;
				right: 0;
			}
		}
		.page_cont{
			width: 100%;
			position: fixed;
			top: 50px*@ip5;
			left: 0;
			.tab_box{
				height: 44px*@ip5;
				background: @deepblue;
				.tab_box_col{
					float: left;
					width: 50%;
					text-align: center;
					span{
						display: inline-block;
						height: 44px*@ip5;
						line-height: 44px*@ip5;
						color: @lightblue;
						font-size: @fs14*@ip5;
						&.current{
							color: @yellow;
							border-bottom: 4px*@ip5 solid @yellow;
						}
					}	
				}
			}
			.list{
				ul{
					width: 100%;
					padding: 0;
					overflow-y: scroll;
				}
				li{
					width: 820px*@ip5;
					background: @deepblue;
					padding-left: 15px*@ip5;
					border-top: 1px solid @black;
					&.current{
						background: #2d3040;
					}
					&.list_head{
						height: 44px*@ip5;
						background: #36394d;
					}
					.list_cont{
						height: 44px*@ip5;
						&.current{
							background: #2d3040;
						}
					}
					span{
						display: inline-block;
						height: 44px*@ip5;
						line-height: 44px*@ip5;
						overflow: hidden;
						color: @lightblue;
						font-size: @fs14*@ip5;
						margin: 0 0.4%;
						&:nth-child(1){
							width: 100px*@ip5;
						}
						&:nth-child(2){
							width: 65px*@ip5;
						}
						&:nth-child(3){
							width: 80px*@ip5;
						}
						&:nth-child(4){
							width: 140px*@ip5;
						}
						&:nth-child(5){
							width: 90px*@ip5;
						}
						&:nth-child(6){
							width: 80px*@ip5;
						}
						&:nth-child(7){
							width: 150px*@ip5;
						}
						&.red{
							color: @red;
						}
						&.green{
							color: @green;
						}
					}
				}
				.list_tools{
					position: fixed;
					bottom: 0;
					left: 0;
					width: 100%;
					height: 44px*@ip5;
					overflow: hidden;
					text-align: center;
					#conditionBtn{
						display: inline-block;
						margin: 6px*@ip5 10px*@ip5 0 10px*@ip5;
					}
				}
			}
		}
	}
	
</style>
