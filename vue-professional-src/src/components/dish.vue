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
						<span :class="[{'green':Parameters.ChangeValue<0},{'red':Parameters.ChangeValue>0},{'white':Parameters.ChangeValue==0}]">{{Parameters.ChangeValue | fixNum2(detail.DotSize)}}/{{Parameters.ChangeRate | fixNum}}%</span>
					</li>
					<li>
						<span>成交量</span>
						<span>{{Parameters.TotalVolume}}</span>
					</li>
					<li>
						<span>持仓量</span>
						<span>{{Parameters.Position}}</span>
					</li>
					<li>
						<span>昨结</span>
						<span :class="[{'green':Parameters.PreSettlePrice<0},{'red':Parameters.PreSettlePrice>0},{'white':Parameters.PreSettlePrice==0}]">{{Parameters.PreSettlePrice | fixNum2(detail.DotSize)}}</span>
					</li>
					<li>
						<span>最新</span>
						<span class="red">{{Parameters.LastPrice | fixNum2(detail.DotSize)}}</span>
					</li>
					<li>
						<span>开盘</span>
						<span class="green">{{Parameters.OpenPrice | fixNum2(detail.DotSize)}}</span>
					</li>
					<li>
						<span>最高</span>
						<span class="red">{{Parameters.HighPrice | fixNum2(detail.DotSize)}}</span>
					</li>
					<li>
						<span>最低</span>
						<span class="red">{{Parameters.LowPrice | fixNum2(detail.DotSize)}}</span>
					</li>
					<!--<li>
						<span>结算</span>
						<span class="red">{{Parameters.SettlePrice | fixNum2(detail.DotSize)}}</span>
					</li>-->
					<li>
						<span></span>
						<span class="red"></span>
					</li>
					<li>
						<span></span>
						<span class="red"></span>
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
							<span class="red">卖五</span>
							<span class="red">{{Parameters.AskPrice5 | fixNum2(detail.DotSize)}}</span>
							<span class="red">{{Parameters.AskQty5}}</span>
						</li>
						<li>
							<span class="red">卖四</span>
							<span class="red">{{Parameters.AskPrice4 | fixNum2(detail.DotSize)}}</span>
							<span class="red">{{Parameters.AskQty4}}</span>
						</li>
						<li>
							<span class="red">卖三</span>
							<span class="red">{{Parameters.AskPrice3 | fixNum2(detail.DotSize)}}</span>
							<span class="red">{{Parameters.AskQty3}}</span>
						</li>
						<li>
							<span class="red">卖二</span>
							<span class="red">{{Parameters.AskPrice2 | fixNum2(detail.DotSize)}}</span>
							<span class="red">{{Parameters.AskQty2}}</span>
						</li>
						<li>
							<span class="red">卖一</span>
							<span class="red">{{Parameters.AskPrice1 | fixNum2(detail.DotSize)}}</span>
							<span class="red">{{Parameters.AskQty1}}</span>
						</li>
						<li>
							<span class="green">买一</span>
							<span class="green">{{Parameters.BidPrice1 | fixNum2(detail.DotSize)}}</span>
							<span class="green">{{Parameters.BidQty1}}</span>
						</li>
						<li>
							<span class="green">买二</span>
							<span class="green">{{Parameters.BidPrice2 | fixNum2(detail.DotSize)}}</span>
							<span class="green">{{Parameters.BidQty2}}</span>
						</li>
						<li>
							<span class="green">买三</span>
							<span class="green">{{Parameters.BidPrice3 | fixNum2(detail.DotSize)}}</span>
							<span class="green">{{Parameters.BidQty3}}</span>
						</li>
						<li>
							<span class="green">买四</span>
							<span class="green">{{Parameters.BidPrice4 | fixNum2(detail.DotSize)}}</span>
							<span class="green">{{Parameters.BidQty4}}</span>
						</li>
						<li>
							<span class="green">买五</span>
							<span class="green">{{Parameters.BidPrice5 | fixNum2(detail.DotSize)}}</span>
							<span class="green">{{Parameters.BidQty5}}</span>
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
			<chartBtn type="buy" class="fl"></chartBtn>
			<chartBtn type="sell" class="fr"></chartBtn>
		</div>
		
	</div>
</template>

<script>
	import chartBtn from '../components/chartBtn.vue'
	import operatenum from '../components/oprtateNum.vue'
	export default{
		name: 'dish',
		components: {chartBtn, operatenum},
		mounted: function(){
			$("#tradeCenter").css("height",window.screen.height + "px");
		},
		computed:{
			detail(){
				return this.$parent.detail;
			},
			Parameters(){
				return this.$store.state.market.jsonTow.Parameters;
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