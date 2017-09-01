<template>
	<div id="conditions">
		<tipsDialog :msg="msgTips" ref="dialog"></tipsDialog>
		<stopLossAlert ref="stoplossalert"></stopLossAlert>
		<stopWinAlert ref="stopwinalert"></stopWinAlert>
		<div class="head">
			<topbar title="止损止赢"></topbar>
			<back></back>
			<refresh></refresh>
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
						<span>多空</span>
						<span>类别</span>
						<span>手数</span>
						<span>触发条件</span>
						<span>委托价</span>
						<span>有效期</span>
						<span>下单时间</span>
					</li>
					<template v-for="k in hasNostopLossList">
						<li @tap="listTap" :id="k.StopLossNo" :status="k.Status" :StopLossType00="k.StopLossType00" >
							<div class="list_cont">
								<span>{{k.CommodityNo + k.ContractNo}}</span>
								<span>{{k.StatusMsg00}}</span>
								<span>{{k.HoldDrection}}</span>
								<span>{{k.StopLossType}}</span>
								<span>{{k.Num}}</span>
								<span>{{k.triggerCondition}}</span>
								<span>{{k.entrustPrice}}</span>
								<span>{{k.validity}}</span>
								<span>{{k.InsertDateTime}}</span>
							</div>
						</li>
					</template>
				</ul>
				<div class="list_tools">
					<cbtn :name="statusNameEvent" @tap.native="suspendEvent"></cbtn>
					<cbtn name="修改" @tap.native="updateEvent"></cbtn>
					<cbtn name="删除" @tap.native="deleteEvent"></cbtn>
				</div>
			</div>
			<div id="yesCont" class="list" v-else="isShow">
				<ul class="list_cont_box">
					<li class="list_head">
						<span>合约名称</span>
						<span>状态</span>
						<span>多空</span>
						<span>类别</span>
						<span>手数</span>
						<span>触发条件</span>
						<span>委托价</span>
						<span>有效期</span>
						<span>触发时间</span>
					</li>
					<template v-for="k in hasYesstopLossList">
						<li @tap="listTap" :id="k.StopLossNo">
							<div class="list_cont">
								<span>{{k.CommodityNo + k.ContractNo}}</span>
								<span>{{k.StatusMsg00}}</span>
								<span>{{k.HoldDrection}}</span>
								<span>{{k.StopLossType}}</span>
								<span>{{k.Num}}</span>
								<span>{{k.triggerCondition}}</span>
								<span>{{k.entrustPrice}}</span>
								<span>{{k.validity}}</span>
								<span>{{k.InsertDateTime}}</span>
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
	import refresh from '../../components/Refresh.vue'
	import tipsDialog from '../../components/tipsDialog.vue'
	import stopLossAlert from '../../components/stopLossAlert.vue'
	import stopWinAlert from '../../components/stopWinAlert.vue'
	export default{
		name:'conditions',
		components:{topbar, back, cbtn, refresh, tipsDialog, stopLossAlert, stopWinAlert},
		data(){
			return {
				msg: '',
				isShow: true,
				tabList: [{nav:'未触发列表'},{nav:'已触发列表'}],
				orderListId: '',
				orderStatus: '',
				statusName: '暂停',
				StopLossType00:'',
			}
		},
		computed:{
			statusNameEvent: function(){
				return this.statusName;
			},
			msgTips: function(){
				return this.msg;
			},
			stopLossTriggeredList(){
				return this.$store.state.market.stopLossTriggeredList;
			},
			hasYesstopLossList(){
				return this.$store.state.market.hasYesstopLossList;
			},
			stopLossList(){
				return this.$store.state.market.stopLossList;
			},
			hasNostopLossList(){
				return this.$store.state.market.hasNostopLossList;
			},
			orderTemplist(){
				return	this.$store.state.market.orderTemplist;
			},
			tradeSocket() {
				return this.$store.state.tradeSocket;
			}
		},
		watch: {
			stopLossList: function(n, o){
				this.hasNostopLossList00();
			}
			
		},
		methods: {
			updateEvent: function(){
				if(this.orderListId == '' || this.orderListId == null){
					this.$refs.dialog.isShow = true;
					this.msg = '请选择一条数据';
				}else{
					if(this.StopLossType00==0 || this.StopLossType00==2){
						this.$refs.stoplossalert.isshow = true;
					}else{
						this.$refs.stopwinalert.isshow = true;
					}
				}
			},
			suspendEvent:function(){
				if(this.orderListId == '' || this.orderListId == null){
					this.$refs.dialog.isShow = true;
					this.msg = '请选择一条数据';
				}else{
					this.hasNostopLossList.forEach(function(e,i){
						if(e.StopLossNo==this.orderListId){
							if(e.Status==0){ //运行中
								let b={
									"Method":'ModifyStopLoss',
									"Parameters":{
										'StopLossNo':e.StopLossNo,
										'ModifyFlag':2,
										'Num':parseInt(e.Num),
										'StopLossType':parseInt(e.StopLossType00),
										'OrderType':parseInt(e.OrderType00),
										'StopLossPrice':parseFloat(e.StopLossPrice),
										'StopLossDiff':parseFloat(e.StopLossDiff)
									}
								};
								this.tradeSocket.send(JSON.stringify(b));
							} else if (e.Status==1){ //暂停
								let b={
									"Method":'ModifyStopLoss',
									"Parameters":{
										'StopLossNo':e.StopLossNo,
										'ModifyFlag':3,
										'Num':parseInt(e.Num),
										'StopLossType':parseInt(e.StopLossType00),
										'OrderType':parseInt(e.OrderType00),
										'StopLossPrice':parseFloat(e.StopLossPrice),
										'StopLossDiff':parseFloat(e.StopLossDiff)
									}
								};
								this.tradeSocket.send(JSON.stringify(b));
							}
							
						}
					}.bind(this));
				}
			},
			deleteEvent: function(){
				if(this.orderListId == '' || this.orderListId == null){
					this.$refs.dialog.isShow = true;
					this.msg = '请选择一条数据';
				}else{
					this.hasNostopLossList.forEach(function(e,i){
						if(e.StopLossNo==this.orderListId){
							this.hasNostopLossList.splice(i,1);
							this.stopLossList.splice(i,1);
							let b={
								"Method":'ModifyStopLoss',
								"Parameters":{
									'StopLossNo':e.StopLossNo,
									'ModifyFlag':1,
									'Num':parseInt(e.Num),
									'StopLossType':parseInt(e.StopLossType00),
									'OrderType':parseInt(e.OrderType00),
									'StopLossPrice':parseFloat(e.StopLossPrice),
									'StopLossDiff':parseFloat(e.StopLossDiff)
								}
							};
							
							this.tradeSocket.send(JSON.stringify(b));
							
						}
					}.bind(this));
				}
				
			},
			showCont: function(e){
				$(e.currentTarget).find("span").addClass('current');
				$(e.currentTarget).siblings().find("span").removeClass('current')
				if($(e.currentTarget).index() == 0){
					this.isShow = true;
				}else{
					this.isShow = false;
					this.hasNostopLossList00();
					this.hasYesstopLossList00();
				}
			},
			listTap: function(obj){
				if(!$(obj.currentTarget).hasClass("current")){
					$(obj.currentTarget).addClass("current");
					$(obj.currentTarget).siblings().removeClass("current");
					this.orderListId = $(obj.currentTarget).attr("id");
					this.orderStatus = $(obj.currentTarget).attr("status");
					this.StopLossType00 = $(obj.currentTarget).attr("StopLossType00");
					if(this.orderStatus == 0){
						this.statusName = '暂停';
					}else{
						this.statusName = '启动';
					}
					this.hasNostopLossList.forEach(function(e,i){
						if(e.StopLossNo == this.orderListId){
							this.$store.state.market.stopLossListSelectOneObj=e;
							console.log(e);
						}
					}.bind(this));
					
				}else{
					$(obj.currentTarget).removeClass("current");
					this.orderListId = null;
				}
			},
			hasYesstopLossList00: function(){
				this.$store.state.market.hasYesstopLossList = [];
				let orderTemplist = this.orderTemplist;
				this.stopLossTriggeredList.forEach(function(e,i){
					let s={};
					s.ClientNo = e.ClientNo;
					s.CommodityNo=e.CommodityNo;
					s.ContractNo = e.ContractNo;
					s.ExchangeNo = e.ExchangeNo;
					s.HoldAvgPrice=e.HoldAvgPrice;
					s.HoldDrection = (function(){
							if(e.HoldDrection==0){
								return '多';
							}else{
								return '空';
							}
							
						})();
					s.InsertDateTime = e.InsertDateTime;
					s.Num = e.Num;
					s.OrderType=(function(){
						if(e.OrderType==1){
							return '市价';
						}else{
							return '限价';
						}
					})();
					s.Status = e.Status;
					s.StatusMsg = e.StatusMsg;
					s.StatusMsg00=(function(){
						if(e.Status==0)
							return '运行中';
						if(e.Status==1)
							return '暂停';
						if(e.Status==2)
							return '已触发';
						if(e.Status==3)
							return '已取消';
						if(e.Status==4)
							return '插入失败';
						if(e.Status==5)
							return '触发失败';	
					})();
					s.StopLossDiff = e.StopLossDiff;
					s.StopLossNo = e.StopLossNo;
					s.StopLossPrice = parseFloat(e.StopLossPrice).toFixed(orderTemplist[e.CommodityNo].DotSize);
					s.StopLossType = (function(){
						if(e.StopLossType==0)
							return '限价止损';
						if(e.StopLossType==1)	
							return '限价止盈';
						if(e.StopLossType==2)
							return '动态止损';
					})();
					s.triggerCondition=(function(){
						if(e.StopLossType==0 || e.StopLossType==1)
							return '触发价:'+parseFloat(e.StopLossPrice).toFixed(orderTemplist[e.CommodityNo].DotSize);
						if(e.StopLossType==2)
							return '动态价:'+parseFloat(e.StopLossDiff).toFixed(orderTemplist[e.CommodityNo].DotSize);	
					})();
					
					s.entrustPrice=(function(){
						if(e.OrderType==1){
							return '市价';
						}else{
							return '对手价';
						}
					})();
					s.validity = '当日有效';
					s.InsertDateTime = e.InsertDateTime;
					this.hasYesstopLossList.push(s);
				}.bind(this));
			},
			hasNostopLossList00: function(){
				let orderTemplist = this.orderTemplist;
				this.$store.state.market.hasNostopLossList = [];
				this.stopLossList.forEach(function(e,i){
					let s={};
					s.ClientNo = e.ClientNo;
					s.CommodityNo=e.CommodityNo;
					s.ContractNo = e.ContractNo;
					s.ExchangeNo = e.ExchangeNo;
					s.HoldAvgPrice=e.HoldAvgPrice;
					s.HoldDrection = (function(){
							if(e.HoldDrection==0){
								return '多';
							}else{
								return '空';
							}
							
						})();
					s.InsertDateTime = e.InsertDateTime;
					s.Num = e.Num;
					s.OrderType=(function(){
						if(e.OrderType==1){
							return '市价';
						}else{
							return '限价';
						}
					})();
					s.OrderType00 = e.OrderType;
					s.Status = e.Status;
					s.StatusMsg = e.StatusMsg;
					s.StatusMsg00=(function(){
						if(e.Status==0)
							return '运行中';
						if(e.Status==1)
							return '暂停';
						if(e.Status==2)
							return '已触发';
						if(e.Status==3)
							return '已取消';
						if(e.Status==4)
							return '插入失败';
						if(e.Status==5)
							return '触发失败';	
					})();
					s.StopLossDiff = e.StopLossDiff;
					s.StopLossNo = e.StopLossNo;
					s.StopLossPrice = parseFloat(e.StopLossPrice).toFixed(orderTemplist[e.CommodityNo].DotSize);
					s.StopLossType = (function(){
						if(e.StopLossType==0)
							return '限价止损';
						if(e.StopLossType==1)	
							return '限价止盈';
						if(e.StopLossType==2)
							return '动态止损';
					})();
					s.StopLossType00=e.StopLossType;
					s.triggerCondition=(function(){
						if(e.StopLossType==0 || e.StopLossType==1)
							return '触发价:'+parseFloat(e.StopLossPrice).toFixed(orderTemplist[e.CommodityNo].DotSize);
						if(e.StopLossType==2)
							return '动态价:'+parseFloat(e.StopLossDiff).toFixed(orderTemplist[e.CommodityNo].DotSize);
					})();
					
					s.entrustPrice=(function(){
						if(e.OrderType==1){
							return '市价';
						}else{
							return '对手价';
						}
					})();
					s.validity = '当日有效';
					s.InsertDateTime = e.InsertDateTime;
					this.hasNostopLossList.push(s);
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
		},
		activated: function(){
			this.stopLossList;
			this.stopLossTriggeredList;
			this.hasNostopLossList00();
			this.hasYesstopLossList00();
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
		#refresh{
			position: fixed;
			top: 0;
			right: 0;
			z-index: 1000;
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
				width: 920px;
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
						width: 130px;
					}
					&:nth-child(2){
						width: 65px;
					}
					&:nth-child(3){
						width: 50px;
					}
					&:nth-child(4){
						width: 80px;
					}
					&:nth-child(5){
						width: 50px;
					}
					&:nth-child(6){
						width: 120px;
					}
					&:nth-child(7){
						width: 60px;
					}
					&:nth-child(8){
						width: 80px;
					}
					&:nth-child(9){
						width: 150px;
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
		padding-top: 50px*@ip6;
		background: @black;
	}
	.head{
		#back{
			position: fixed;
			z-index: 1000;
			top: 0;
			left: 0;
		}
		#refresh{
			position: fixed;
			top: 0;
			right: 0;
			z-index: 1000;
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
				width: 900px*@ip6;
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
						width: 130px*@ip6;
					}
					&:nth-child(2){
						width: 65px*@ip6;
					}
					&:nth-child(3){
						width: 50px*@ip6;
					}
					&:nth-child(4){
						width: 80px*@ip6;
					}
					&:nth-child(5){
						width: 50px*@ip6;
					}
					&:nth-child(6){
						width: 120px*@ip6;
					}
					&:nth-child(7){
						width: 60px*@ip6;
					}
					&:nth-child(8){
						width: 80px*@ip6;
					}
					&:nth-child(9){
						width: 140px*@ip6;
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
		padding-top: 50px*@ip5;
		background: @black;
	}
	.head{
		#back{
			position: fixed;
			z-index: 1000;
			top: 0;
			left: 0;
		}
		#refresh{
			position: fixed;
			top: 0;
			right: 0;
			z-index: 1000;
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
				width: 940px*@ip5;
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
						width: 130px*@ip5;
					}
					&:nth-child(2){
						width: 65px*@ip5;
					}
					&:nth-child(3){
						width: 50px*@ip5;
					}
					&:nth-child(4){
						width: 80px*@ip5;
					}
					&:nth-child(5){
						width: 50px*@ip5;
					}
					&:nth-child(6){
						width: 120px*@ip5;
					}
					&:nth-child(7){
						width: 60px*@ip5;
					}
					&:nth-child(8){
						width: 80px*@ip5;
					}
					&:nth-child(9){
						width: 160px*@ip5;
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
