<template>
	<div id="dish">
		<div class="title">
			<span>盘口</span>
			<span>五档</span>
		</div>
		<div class="list">
			<div class="list_col fl">
				<ul>
					<li>
						<span>涨跌</span>
						<span :class="[{'green':detail.LastQuotation.ChangeValue<0},{'red':detail.LastQuotation.ChangeValue>0},{'white':detail.LastQuotation.ChangeValue==0}]">{{detail.LastQuotation.ChangeValue | fixNum2(detail.DotSize)}}/{{detail.LastQuotation.ChangeRate | fixNum}}%</span>
					</li>
					<li>
						<span>成交量</span>
						<span>{{detail.LastQuotation.TotalVolume}}</span>
					</li>
					<li>
						<span>持仓量</span>
						<span>{{detail.LastQuotation.Position}}</span>
					</li>
					<li>
						<span>昨结</span>
						<span :class="[{'green':detail.LastQuotation.PreSettlePrice<0},{'red':detail.LastQuotation.PreSettlePrice>0},{'white':detail.LastQuotation.PreSettlePrice==0}]">{{detail.LastQuotation.PreSettlePrice | fixNum2(detail.DotSize)}}</span>
					</li>
					<li>
						<span>最新</span>
						<span class="red">{{detail.LastQuotation.LastPrice | fixNum2(detail.DotSize)}}</span>
					</li>
					<li>
						<span>开盘</span>
						<span class="green">{{detail.LastQuotation.OpenPrice | fixNum2(detail.DotSize)}}</span>
					</li>
					<li>
						<span>最高</span>
						<span class="red">{{detail.LastQuotation.HighPrice | fixNum2(detail.DotSize)}}</span>
					</li>
					<li>
						<span>最低</span>
						<span class="red">{{detail.LastQuotation.LowPrice | fixNum2(detail.DotSize)}}</span>
					</li>
					<li>
						<span>结算</span>
						<span class="red">{{detail.LastQuotation.SettlePrice | fixNum2(detail.DotSize)}}</span>
					</li>
					<li>
						<span>结算</span>
						<span class="red">{{detail.LastQuotation.SettlePrice | fixNum2(detail.DotSize)}}</span>
					</li>
					<li>
						<span>结算</span>
						<span class="red">{{detail.LastQuotation.SettlePrice | fixNum2(detail.DotSize)}}</span>
					</li>
				</ul>
			</div>
			<div class="list_col fl">
				<ul>
					<template>
						<li>
							<span class="green">卖五</span>
							<span class="green">{{detail.LastQuotation.AskPrice5 | fixNum2(detail.DotSize)}}</span>
							<span class="green">{{detail.LastQuotation.AskQty5}}</span>
						</li>
						<li>
							<span class="green">卖四</span>
							<span class="green">{{detail.LastQuotation.AskPrice4 | fixNum2(detail.DotSize)}}</span>
							<span class="green">{{detail.LastQuotation.AskQty4}}</span>
						</li>
						<li>
							<span class="green">卖三</span>
							<span class="green">{{detail.LastQuotation.AskPrice3 | fixNum2(detail.DotSize)}}</span>
							<span class="green">{{detail.LastQuotation.AskQty3}}</span>
						</li>
						<li>
							<span class="green">卖二</span>
							<span class="green">{{detail.LastQuotation.AskPrice2 | fixNum2(detail.DotSize)}}</span>
							<span class="green">{{detail.LastQuotation.AskQty2}}</span>
						</li>
						<li>
							<span class="green">卖一</span>
							<span class="green">{{detail.LastQuotation.AskPrice1 | fixNum2(detail.DotSize)}}</span>
							<span class="green">{{detail.LastQuotation.AskQty1}}</span>
						</li>
						<li>
							<span class="red">买一</span>
							<span class="red">{{detail.LastQuotation.BidPrice1 | fixNum2(detail.DotSize)}}</span>
							<span class="red">{{detail.LastQuotation.BidQty1}}</span>
						</li>
						<li>
							<span class="red">买二</span>
							<span class="red">{{detail.LastQuotation.BidPrice2 | fixNum2(detail.DotSize)}}</span>
							<span class="red">{{detail.LastQuotation.BidQty2}}</span>
						</li>
						<li>
							<span class="red">买三</span>
							<span class="red">{{detail.LastQuotation.BidPrice3 | fixNum2(detail.DotSize)}}</span>
							<span class="red">{{detail.LastQuotation.BidQty3}}</span>
						</li>
						<li>
							<span class="red">买四</span>
							<span class="red">{{detail.LastQuotation.BidPrice4 | fixNum2(detail.DotSize)}}</span>
							<span class="red">{{detail.LastQuotation.BidQty4}}</span>
						</li>
						<li>
							<span class="red">买五</span>
							<span class="red">{{detail.LastQuotation.BidPrice5 | fixNum2(detail.DotSize)}}</span>
							<span class="red">{{detail.LastQuotation.BidQty5}}</span>
						</li>
						<li>
							<span></span>
							<span></span>
							<span></span>
						</li>
						<li>
							<span></span>
							<span></span>
							<span></span>
						</li>
						<li>
							<span></span>
							<span></span>
							<span></span>
						</li>
					</template>
				</ul>
			</div>
		</div>
		<div class="oprtate_num">
			<operatenum></operatenum>
		</div>
		<div class="btn_box">
			<tradebtn :marketprice="detail.LastQuotation.AskPrice1 | fixNum2(detail.DotSize)" transaction='sell'></tradebtn>
			<tradebtn :marketprice="detail.LastQuotation.BidPrice1 | fixNum2(detail.DotSize)" transaction='buy'></tradebtn>
		</div>
		
	</div>
</template>

<script>
	import tradebtn from '../components/tradeButton.vue'
	import operatenum from '../components/oprtateNum.vue'
	export default{
		name: 'dish',
		components: {tradebtn, operatenum},
		mounted: function(){
			$("#tradeCenter").css("height",window.screen.height + "px");
		},
		computed:{
			detail(){
				return this.$parent.detail;
			}
		},
		filters:{
			fixNum:function(num){
				return num.toFixed(2);
			},
			fixNum2:function(num,dotsize){
				return num.toFixed(dotsize);
			}
		},
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
	    			height: 32px;
	    			line-height: 32px;
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
	    .operate_num_box{
	    	width: 100%;
	    	height: 55px;
	    	margin-top: 15px;
	    	padding: 5px 15px;
	    	background: @deepblue;
	    	.operate_num{
	    		height: 45px;
	    		border-radius: 5px;
	    		border: 1px solid #12121a;
	    		background: @black;
	    	}
	    }
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
	    #dish{
			width: 100%;
			background: @black;
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
	    			height: 32px;
	    			line-height: 32px;
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
	    .operate_num_box{
	    	width: 100%;
	    	height: 55px;
	    	margin-top: 15px;
	    	padding: 5px 15px;
	    	background: @deepblue;
	    	.operate_num{
	    		height: 45px;
	    		border-radius: 5px;
	    		border: 1px solid #12121a;
	    		background: @black;
	    	}
	    }
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
	    			height: 32px*@ip6;
	    			line-height: 32px*@ip6;
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
	    			height: 32px*@ip5;
	    			line-height: 32px*@ip5;
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
	
	
</style>