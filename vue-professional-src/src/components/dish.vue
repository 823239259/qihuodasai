<template>
	<div id="dish">
		<alert title="确认下单" :line1="insertOrder" :objstr='objst'></alert>
		<div class="title">
			<span>盘口</span>
			<span>五档</span>
		</div>
		<div class="list">
			<div class="list_col fl">
				<ul>
					<li>
						<span>涨跌</span>
						<span :class="[{'green':defaultParameters.ChangeValue<0},{'red':defaultParameters.ChangeValue>0},{'white':defaultParameters.ChangeValue==0}]">{{defaultParameters.ChangeValue | fixNum2(detail.DotSize)}}/{{defaultParameters.ChangeRate | fixNum}}%</span>
					</li>
					<li>
						<span>成交量</span>
						<span>{{defaultParameters.TotalVolume}}</span>
					</li>
					<li>
						<span>持仓量</span>
						<span>{{defaultParameters.Position}}</span>
					</li>
					<li>
						<span>昨结</span>
						<span>{{defaultParameters.PreSettlePrice | fixNum2(detail.DotSize)}}</span>
					</li>
					<li>
						<span>最新</span>
						<span :class="{red: defaultParameters.LastPrice - defaultParameters.PreSettlePrice > 0,green: defaultParameters.LastPrice - defaultParameters.PreSettlePrice < 0}">{{defaultParameters.LastPrice | fixNum2(detail.DotSize)}}</span>
					</li>
					<li>
						<span>开盘</span>
						<span :class="{red: defaultParameters.OpenPrice - defaultParameters.PreSettlePrice > 0,green: defaultParameters.OpenPrice - defaultParameters.PreSettlePrice < 0}">{{defaultParameters.OpenPrice | fixNum2(detail.DotSize)}}</span>
					</li>
					<li>
						<span>最高</span>
						<span :class="{red: defaultParameters.HighPrice - defaultParameters.PreSettlePrice > 0,green: defaultParameters.HighPrice - defaultParameters.PreSettlePrice < 0}">{{defaultParameters.HighPrice | fixNum2(detail.DotSize)}}</span>
					</li>
					<li>
						<span>最低</span>
						<span :class="{red: defaultParameters.LowPrice - defaultParameters.PreSettlePrice > 0,green: defaultParameters.LowPrice - defaultParameters.PreSettlePrice < 0}">{{defaultParameters.LowPrice | fixNum2(detail.DotSize)}}</span>
					</li>
					<li>
						<span></span>
						<span class="red"></span>
					</li>
					<li>
						<span></span>
						<span class="red"></span>
					</li>
				</ul>
			</div>
			<div class="list_col fl">
				<ul>
					<template>
						<li>
							<span>卖五</span>
							<span :class="{red: defaultParameters.AskPrice5 - defaultParameters.PreSettlePrice > 0, green: defaultParameters.AskPrice5 - defaultParameters.PreSettlePrice < 0}">{{defaultParameters.AskPrice5 | fixNum2(detail.DotSize)}}</span>
							<span>{{defaultParameters.AskQty5}}</span>
						</li>
						<li>
							<span>卖四</span>
							<span :class="{red: defaultParameters.AskPrice4 - defaultParameters.PreSettlePrice > 0, green: defaultParameters.AskPrice4 - defaultParameters.PreSettlePrice < 0}">{{defaultParameters.AskPrice4 | fixNum2(detail.DotSize)}}</span>
							<span>{{defaultParameters.AskQty4}}</span>
						</li>
						<li>
							<span>卖三</span>
							<span :class="{red: defaultParameters.AskPrice3 - defaultParameters.PreSettlePrice > 0, green: defaultParameters.AskPrice3 - defaultParameters.PreSettlePrice < 0}">{{defaultParameters.AskPrice3 | fixNum2(detail.DotSize)}}</span>
							<span>{{defaultParameters.AskQty3}}</span>
						</li>
						<li>
							<span>卖二</span>
							<span :class="{red: defaultParameters.AskPrice2 - defaultParameters.PreSettlePrice > 0, green: defaultParameters.AskPrice2 - defaultParameters.PreSettlePrice < 0}">{{defaultParameters.AskPrice2 | fixNum2(detail.DotSize)}}</span>
							<span>{{defaultParameters.AskQty2}}</span>
						</li>
						<li>
							<span>卖一</span>
							<span :class="{red: defaultParameters.AskPrice1 - defaultParameters.PreSettlePrice > 0, green: defaultParameters.AskPrice1 - defaultParameters.PreSettlePrice < 0}">{{defaultParameters.AskPrice1 | fixNum2(detail.DotSize)}}</span>
							<span>{{defaultParameters.AskQty1}}</span>
						</li>
						<li>
							<span>买一</span>
							<span :class="{red: defaultParameters.BidPrice1 - defaultParameters.PreSettlePrice > 0, green: defaultParameters.BidPrice1 - defaultParameters.PreSettlePrice < 0}">{{defaultParameters.BidPrice1 | fixNum2(detail.DotSize)}}</span>
							<span>{{defaultParameters.BidQty1}}</span>
						</li>
						<li>
							<span>买二</span>
							<span :class="{red: defaultParameters.BidPrice2 - defaultParameters.PreSettlePrice > 0, green: defaultParameters.BidPrice2 - defaultParameters.PreSettlePrice < 0}">{{defaultParameters.BidPrice2 | fixNum2(detail.DotSize)}}</span>
							<span>{{defaultParameters.BidQty2}}</span>
						</li>
						<li>
							<span>买三</span>
							<span :class="{red: defaultParameters.BidPrice3 - defaultParameters.PreSettlePrice > 0, green: defaultParameters.BidPrice3 - defaultParameters.PreSettlePrice < 0}">{{defaultParameters.BidPrice3 | fixNum2(detail.DotSize)}}</span>
							<span>{{defaultParameters.BidQty3}}</span>
						</li>
						<li>
							<span>买四</span>
							<span :class="{red: defaultParameters.BidPrice4 - defaultParameters.PreSettlePrice > 0, green: defaultParameters.BidPrice4 - defaultParameters.PreSettlePrice < 0}">{{defaultParameters.BidPrice4 | fixNum2(detail.DotSize)}}</span>
							<span>{{defaultParameters.BidQty4}}</span>
						</li>
						<li>
							<span>买五</span>
							<span :class="{red: defaultParameters.BidPrice5 - defaultParameters.PreSettlePrice > 0, green: defaultParameters.BidPrice5 - defaultParameters.PreSettlePrice < 0}">{{defaultParameters.BidPrice5 | fixNum2(detail.DotSize)}}</span>
							<span>{{defaultParameters.BidQty5}}</span>
						</li>
						<!--<li>
							<span></span>
							<span></span>
							<span></span>
						</li>-->
						<!--<li>
							<span></span>
							<span></span>
							<span></span>
						</li>
						<li>
							<span></span>
							<span></span>
							<span></span>
						</li>-->
					</template>
				</ul>
			</div>
		</div>
		<div class="tools">
			<div class="oprtate_num">
				<operatenum></operatenum>
			</div>
			<div class="btn_box">
				<chartBtn type="buy" class="fl" @tap.native='buy'></chartBtn>
				<chartBtn type="sell" class="fr" @tap.native='sell'></chartBtn>
			</div>
		</div>
		<alert title="提示" :line1="promptMsg" jump="false" ref="alert" :name="confirmName"></alert>
		<tipsDialog :msg="msgTips" ref="dialog"></tipsDialog>
	</div>
</template>

<script>
	import chartBtn from '../components/chartBtn.vue'
	import operatenum from '../components/oprtateNum.vue'
	import alert from '../components/Tradealert.vue'
	import tipsDialog from '../components/tipsDialog.vue'
	export default{
		name: 'dish',
		components: {chartBtn, operatenum, alert, tipsDialog},
		data(){
			return{
				msg: '',
				promptMsg: '',
				buyText: {},
				confirmName: '',
				defaultParameters: {
					ChangeValue: 0,
					ChangeRate: 0,
					TotalVolume: 0,
					Position: 0,
					PreSettlePrice: 0,
					LastPrice: 0,
					OpenPrice: 0,
					HighPrice: 0,
					LowPrice: 0,
					AskPrice5: 0,
					AskPrice4: 0,
					AskPrice3: 0,
					AskPrice2: 0,
					AskPrice1: 0,
					BidPrice2: 0,
					BidPrice3: 0,
					BidPrice4: 0,
					BidPrice5: 0,
					
					
					AskQty5:0,
					AskQty4:0,
					AskQty3:0,
					AskQty2:0,
					AskQty1:0,
					
					BidQty5:0,
					BidQty4:0,
					BidQty3:0,
					BidQty2:0,
					BidQty1:0,
				}
			}
		},
		computed:{
			msgTips: function(){
				return this.msg;
			},
			layer(){
				return this.$store.state.market.layer;
			},
			operateOrderLength(){
				return this.$store.state.account.operateOrderLength;
			},
			detail(){
				return this.$parent.detail;
			},
			Parameters(){
				return this.$store.state.market.jsonTow.Parameters;
			},
			templateList(){
				return this.$store.state.market.templateList;
			},
			tradeSocket() {
				return this.$store.state.tradeSocket;
			},
			orderTemplist(){
				return	this.$store.state.market.orderTemplist;
			},
			objst: function(){
				if(this.buyText){
					return JSON.stringify(this.buyText);
				}
			},
			insertOrder: function(){
				var obj = this.buyText.Parameters;
				if(obj != undefined){
					var contract=obj.CommodityNo+obj.ContractNo;
					var LimitPrice;
					if(obj.PriceType == 1){
						LimitPrice='市价';
					}else{
						LimitPrice = this.tradePrices;
					}
					var orderNum = obj.OrderNum;
					var drection;
					if(obj.Drection==0){
						drection = '买';
					}else{
						drection = '卖';
					}
					var text = '确认提交订单:【'+contract+'】,价格【'+LimitPrice +'】,手数【'+orderNum+'】,方向【'+drection+'】？';
					return  text;
				}
			},
		},
		mounted: function(){
			$("#tradeCenter").css("height",window.screen.height + "px");
			//初始化盘口
			this.defaultParameters.ChangeValue = this.templateList[this.detail.CommodityNo].ChangeValue;
			this.defaultParameters.ChangeRate = this.templateList[this.detail.CommodityNo].ChangeRate;
			this.defaultParameters.TotalVolume = this.templateList[this.detail.CommodityNo].TotalVolume;
			this.defaultParameters.Position = this.templateList[this.detail.CommodityNo].Position;
			this.defaultParameters.PreSettlePrice = this.templateList[this.detail.CommodityNo].PreSettlePrice;
			this.defaultParameters.LastPrice = this.templateList[this.detail.CommodityNo].LastPrice;
			this.defaultParameters.OpenPrice = this.templateList[this.detail.CommodityNo].OpenPrice;
			this.defaultParameters.HighPrice = this.templateList[this.detail.CommodityNo].HighPrice;
			this.defaultParameters.LowPrice = this.templateList[this.detail.CommodityNo].LowPrice;
			this.defaultParameters.AskPrice5 = this.templateList[this.detail.CommodityNo].AskPrice5;
			this.defaultParameters.AskPrice4 = this.templateList[this.detail.CommodityNo].AskPrice4;
			this.defaultParameters.AskPrice3 = this.templateList[this.detail.CommodityNo].AskPrice3;
			this.defaultParameters.AskPrice2 = this.templateList[this.detail.CommodityNo].AskPrice2;
			this.defaultParameters.AskPrice1 = this.templateList[this.detail.CommodityNo].AskPrice1;
			this.defaultParameters.BidPrice1 = this.templateList[this.detail.CommodityNo].BidPrice1;
			this.defaultParameters.BidPrice2 = this.templateList[this.detail.CommodityNo].BidPrice2;
			this.defaultParameters.BidPrice3 = this.templateList[this.detail.CommodityNo].BidPrice3;
			this.defaultParameters.BidPrice4 = this.templateList[this.detail.CommodityNo].BidPrice4;
			this.defaultParameters.BidPrice5 = this.templateList[this.detail.CommodityNo].BidPrice5;
			
			this.defaultParameters.AskQty5 = this.templateList[this.detail.CommodityNo].AskQty5;
			this.defaultParameters.AskQty4 = this.templateList[this.detail.CommodityNo].AskQty4;
			this.defaultParameters.AskQty3 = this.templateList[this.detail.CommodityNo].AskQty3;
			this.defaultParameters.AskQty2 = this.templateList[this.detail.CommodityNo].AskQty2;
			this.defaultParameters.AskQty1 = this.templateList[this.detail.CommodityNo].AskQty1;
			
			this.defaultParameters.BidQty5 = this.templateList[this.detail.CommodityNo].BidQty5;
			this.defaultParameters.BidQty4 = this.templateList[this.detail.CommodityNo].BidQty4;
			this.defaultParameters.BidQty3 = this.templateList[this.detail.CommodityNo].BidQty3;
			this.defaultParameters.BidQty2 = this.templateList[this.detail.CommodityNo].BidQty2;
			this.defaultParameters.BidQty1 = this.templateList[this.detail.CommodityNo].BidQty1;
		},
		watch: {
			layer: function(n, o){
				setTimeout(function(){
					this.$children[5].isShow = true;
					this.msg = n.slice(0,-1);
				}.bind(this), 1000);
			},
			Parameters: function(n, o){
				if(n){
					this.defaultParameters = n;
				}
			}
		},
		filters:{
			fixNum:function(num){
				return num.toFixed(2);
			},
			fixNum2:function(num,dotsize){
				if(num){
					return parseFloat(num).toFixed(dotsize);
				}else{
					return 0;
				}
			}
		},
		methods:{
			buy:function(){
				if(JSON.parse(localStorage.getItem('tradeUser')) == null){
					if(this.operateOrderLength > 0){
						this.$router.push({path: '/tradeLogin'});
					}else{
						this.confirmName = '去申请开户';
						this.$refs.alert.isshow = true;
						this.promptMsg = '您目前没有交易账户，赶紧去申请吧~';
					}
				}else if(this.$children[1].defaultNum == 0){
					this.$refs.dialog.isShow = true;
					this.msg = '手数不能为0';
				}else{
					this.$children[0].isshow = true;
					var commodityNo = this.detail.CommodityNo;
					var buildIndex=0;
					if(buildIndex>100){
						buildIndex=0;
					}
					var b={
						"Method":'InsertOrder',
						"Parameters":{
							"ExchangeNo":this.orderTemplist[commodityNo].ExchangeNo,
							"CommodityNo":this.templateList[commodityNo].CommodityNo,
							"ContractNo":this.detail.LastQuotation.ContractNo,
							"OrderNum":this.$children[1].defaultNum,
							"Drection":0,
							"PriceType":1,
							"LimitPrice":0.00,
							"TriggerPrice":0,
							"OrderRef":this.$store.state.market.tradeConfig.client_source+ new Date().getTime()+(buildIndex++)
						}
					};
					this.buyText = b;
//					this.tradeSocket.send(JSON.stringify(b));	
				}
			},
			sell:function(){
				if(JSON.parse(localStorage.getItem('tradeUser')) == null){
					if(this.operateOrderLength > 0){
						this.$router.push({path: '/tradeLogin'});
					}else{
						this.confirmName = '去申请开户';
						this.$refs.alert.isshow = true;
						this.promptMsg = '您目前没有交易账户，赶紧去申请吧~';
					}
				}else if(this.$children[1].defaultNum == 0){
					this.$refs.dialog.isShow = true;
					this.msg = '手数不能为0';
				}else{
					this.$children[0].isshow = true;
					var commodityNo = this.detail.CommodityNo;
					var buildIndex=0;
					if(buildIndex>100){
						buildIndex=0;
					}
					var b={
						"Method":'InsertOrder',
						"Parameters":{
							"ExchangeNo":this.orderTemplist[commodityNo].ExchangeNo,
							"CommodityNo":this.templateList[commodityNo].CommodityNo,
							"ContractNo":this.detail.LastQuotation.ContractNo,
							"OrderNum":this.$children[1].defaultNum,
							"Drection":1,
							"PriceType":1,
							"LimitPrice":0.00,
							"TriggerPrice":0,
							"OrderRef":this.$store.state.market.tradeConfig.client_source+ new Date().getTime()+(buildIndex++)
						}
					};
					this.buyText = b;
//					this.tradeSocket.send(JSON.stringify(b));
				}
			}
		},
		activated: function(){

		}
	}
	
</script>

<style scoped lang="less">
	@import url("../assets/css/main.less");
	@import url("../assets/css/base.less");
	/*ip6p及以上*/
	@media (min-width:411px) {
		#dish{
			width: 100%;
			background: @black;
			position: fixed;
			top: 90px;
			left: 0;
		}
	    .title{
	    	height: 44px;
	    	line-height: 44px;
	    	background: #36394d;
	    	span{
	    		display: inline-block;
	    		float: left;
	    		width: 50%;
	    		text-align: center;
	    		font-size: @fs14;
	    		color: @blue;
	    	}
	    }
	    .list{
	    	overflow: hidden;
	    	background: @deepblue;
	    	.list_col{
	    		width: 50%;
	    		&:first-child{
	    			border-right: 1px solid @black;
	    		}
	    		li{
	    			height: 36px;
	    			line-height: 36px;
	    			overflow: hidden;
	    			border-top: 1px solid @black;
	    			padding-left: 22px; 
	    			span{
	    				color: @white;
	    				font-size: @fs14;
	    				margin-right: 15px;
	    				&.red{
	    					color: @red;
	    				}
	    				&.green{
	    					color: @green;
	    				}
	    			} 
	    		}
	    	}
	    }
	    .tools{
	    	width: 100%;
	    	position: fixed;
	    	bottom: 0;
	    	left: 0;
	    	.oprtate_num{
		    	width: 100%;
		    	height: 55px;
		    	background: @deepblue;
		    	padding: 5px 15px;
		    	margin-top: 10px;
		    }
		    .btn_box{
		    	width: 100%;
		    	height: 65px;
		    	padding: 5px 15px;
		    	background: @deepblue;
		    	margin-top: 10px;
		    	div:first-child{
		    		float: left;
		    	}
		    	div:last-child{
		    		float: right;
		    	}
		    }
	    }
	}
	/*ip6*/
	@media (min-width:371px) and (max-width:410px) {
	    #dish{
			width: 100%;
			background: @black;
			position: fixed;
			top: 90px*@ip6;
			left: 0;
		}
	    .title{
	    	height: 44px*@ip6;
	    	line-height: 44px*@ip6;
	    	background: #36394d;
	    	span{
	    		display: inline-block;
	    		float: left;
	    		width: 50%;
	    		text-align: center;
	    		font-size: @fs14*@ip6;
	    		color: @blue;
	    	}
	    }
	    .list{
	    	overflow: hidden;
	    	background: @deepblue;
	    	.list_col{
	    		width: 50%;
	    		&:first-child{
	    			border-right: 1px solid @black;
	    		}
	    		li{
	    			height: 36px*@ip6;
	    			line-height: 36px*@ip6;
	    			overflow: hidden;
	    			border-top: 1px solid @black;
	    			padding-left: 22px*@ip6; 
	    			span{
	    				color: @white;
	    				font-size: @fs14*@ip6;
	    				margin-right: 15px*@ip6;
	    				&.red{
	    					color: @red;
	    				}
	    				&.green{
	    					color: @green;
	    				}
	    			} 
	    		}
	    	}
	    }
	    .tools{
	    	width: 100%;
	    	position: fixed;
	    	bottom: 0;
	    	left: 0;
	    	.oprtate_num{
		    	width: 100%;
		    	height: 55px*@ip6;
		    	background: @deepblue;
		    	padding: 5px*@ip6 15px*@ip6;
		    	margin-top: 10px*@ip6;
		    }
		    .btn_box{
		    	width: 100%;
		    	height: 65px*@ip6;
		    	padding: 5px*@ip6 15px*@ip6;
		    	background: @deepblue;
		    	margin-top: 10px*@ip6;
		    	div:first-child{
		    		float: left;
		    	}
		    	div:last-child{
		    		float: right;
		    	}
		    }
	    }
	}
	/*ip5*/
	@media(max-width:370px) {
		#dish{
			width: 100%;
			background: @black;
			position: fixed;
			top: 90px*@ip5;
			left: 0;
		}
	    .title{
	    	height: 44px*@ip5;
	    	line-height: 44px*@ip5;
	    	background: #36394d;
	    	span{
	    		display: inline-block;
	    		float: left;
	    		width: 50%;
	    		text-align: center;
	    		font-size: @fs14*@ip5;
	    		color: @blue;
	    	}
	    }
	    .list{
	    	overflow: hidden;
	    	background: @deepblue;
	    	.list_col{
	    		width: 50%;
	    		&:first-child{
	    			border-right: 1px solid @black;
	    		}
	    		li{
	    			height: 36px*@ip5;
	    			line-height: 36px*@ip5;
	    			overflow: hidden;
	    			border-top: 1px solid @black;
	    			padding-left: 22px*@ip5; 
	    			span{
	    				color: @white;
	    				font-size: @fs14*@ip5;
	    				margin-right: 15px*@ip5;
	    				&.red{
	    					color: @red;
	    				}
	    				&.green{
	    					color: @green;
	    				}
	    			} 
	    		}
	    	}
	    }
	    .tools{
	    	width: 100%;
	    	position: fixed;
	    	bottom: 0;
	    	left: 0;
	    	.oprtate_num{
		    	width: 100%;
		    	height: 55px*@ip5;
		    	background: @deepblue;
		    	padding: 5px*@ip5 15px*@ip5;
		    	margin-top: 10px*@ip5;
		    }
		    .btn_box{
		    	width: 100%;
		    	height: 65px*@ip5;
		    	padding: 5px*@ip5 15px*@ip5;
		    	background: @deepblue;
		    	margin-top: 10px*@ip5;
		    	div:first-child{
		    		float: left;
		    	}
		    	div:last-child{
		    		float: right;
		    	}
		    }
	    }
	}
</style>