<template>
	<div id="payWays">
		<topbar title="选择支付方式"></topbar>
		<back></back>
		<cs title="客服"></cs>
		<div class="page_cont">
			<ul>
				<li class="current" @tap="chooseWays">
					<i class="icon_zfb"></i>
					<span>支付宝</span>
					<i class="icon_choose"><em></em></i>
				</li>
				<li @tap="chooseWays">
					<i class="icon_wx"></i>
					<span>微信支付</span>
					<i class="icon_choose"><em></em></i>
				</li>
			</ul>
		</div>
		<div class="page_foot">
			<p class="pay_money yellow">支付：{{payMoney}}元</p>
			<btn name="立即付款" @tap="toPays"></btn>
		</div>
	</div>
</template>

<script>
	import topbar from '../../components/Topbar.vue'
	import back from '../../components/back.vue'
	import cs from '../../components/customerService.vue'
	import btn from '../../components/bigBtn.vue'
	export default{
		name:'payWays',
		components: {topbar, back, cs, btn},
		data(){
			return {
				payMoney: ''
			}
		},
		methods: {
			chooseWays: function(e){
				if($(e.currentTarget).hasClass("current")) return false;
				$(e.currentTarget).addClass("current").siblings().removeClass("current");
			},
			toPays: function(){
				
			}
		},
		mounted: function(){
			//充值金额
			this.payMoney = this.$route.query.money;
		}
	}
</script>

<style scoped lang="less">
@import url("../../assets/css/main.less");
@import url("../../assets/css/base.less");
/*ip6p及以上*/
@media (min-width:411px) {
    #payWays{
    	width: 100%;
    	overflow: hidden;
    	padding-top: 50px;
    	.page_cont{
    		ul{
    			li{
    				height: 54px;
    				padding: 0 15px;
    				border-bottom: 1px solid @black;
    				background: @deepblue;
    				&.current{
    					span{ color: @yellow;}
    					.icon_choose{
    						border-color: @yellow;
    						em{ background: @yellow;}
    					}
    				}
    				&:last-child{
    					border: none;
    				}
    				i{
    					float: left;
    					display: inline-block;
    					overflow: hidden;
    					&:first-child{
    						width: 22px;
    						height: 22px;
    						margin: 16px 10px 0 0;
    					}
    					&.icon_zfb{
    						background: url(../../assets/img/ways_zfb.png) no-repeat center center;
    						background-size: 22px 22px;
    					}
    					&.icon_wx{
    						background: url(../../assets/img/ways_wx.png) no-repeat center center;
    						background-size: 22px 22px;
    					}
    					&.icon_choose{
    						position: relative;
    						float: right;
    						width: 22px;
    						height: 22px;
    						border: 2px solid @blue;
    						border-radius: 100%;
    						margin: 16px 0 0 0;
    						em{
    							position: absolute;
    							top: 3px;
    							left: 3px;
    							width: 12px;
    							height: 12px;
    							background: @blue;
    							border-radius: 100%;
    						}
    					}
    				}
    				span{
    					float: left;
    					line-height: 54px;
    					color: @blue;
    					font-size: @fs16;
    				}
    			}
    		}
    	}
    	.page_foot{
    		width: 100%;
    		overflow: hidden;
    		background: @deepblue;
    		position: fixed;
    		bottom: 0;
    		left: 0;
    		.pay_money{
    			height: 44px;
    			line-height: 44px;
    			text-align: center;
    			border-bottom: 1px solid @black;
    			font-size: @fs18;
    		}
    		#bigBtn{
    			margin: 5px 3.625%;
    		}
    	}
    }
}
/*ip6*/
@media (min-width:371px) and (max-width:410px) {
	#payWays{
    	width: 100%;
    	overflow: hidden;
    	padding-top: 50px*@ip6;
    	.page_cont{
    		ul{
    			li{
    				height: 54px*@ip6;
    				padding: 0 15px*@ip6;
    				border-bottom: 1px solid @black;
    				background: @deepblue;
    				&.current{
    					span{ color: @yellow;}
    					.icon_choose{
    						border-color: @yellow;
    						em{ background: @yellow;}
    					}
    				}
    				&:last-child{
    					border: none;
    				}
    				i{
    					float: left;
    					display: inline-block;
    					overflow: hidden;
    					&:first-child{
    						width: 22px*@ip6;
    						height: 22px*@ip6;
    						margin: 16px*@ip6 10px*@ip6 0 0;
    					}
    					&.icon_zfb{
    						background: url(../../assets/img/ways_zfb.png) no-repeat center center;
    						background-size: 22px 22px;
    					}
    					&.icon_wx{
    						background: url(../../assets/img/ways_wx.png) no-repeat center center;
    						background-size: 22px*@ip6 22px*@ip6;
    					}
    					&.icon_choose{
    						position: relative;
    						float: right;
    						width: 22px*@ip6;
    						height: 22px*@ip6;
    						border: 2px*@ip6 solid @blue;
    						border-radius: 100%;
    						margin: 16px*@ip6 0 0 0;
    						em{
    							position: absolute;
    							top: 3px*@ip6;
    							left: 3px*@ip6;
    							width: 12px*@ip6;
    							height: 12px*@ip6;
    							background: @blue;
    							border-radius: 100%;
    						}
    					}
    				}
    				span{
    					float: left;
    					line-height: 54px*@ip6;
    					color: @blue;
    					font-size: @fs16*@ip6;
    				}
    			}
    		}
    	}
    	.page_foot{
    		width: 100%;
    		overflow: hidden;
    		background: @deepblue;
    		position: fixed;
    		bottom: 0;
    		left: 0;
    		.pay_money{
    			height: 44px*@ip6;
    			line-height: 44px*@ip6;
    			text-align: center;
    			border-bottom: 1px solid @black;
    			font-size: @fs18*@ip6;
    		}
    		#bigBtn{
    			margin: 5px*@ip6 3.625%;
    		}
    	}
    }
}
/*ip5*/
@media(max-width:370px) {
	#payWays{
    	width: 100%;
    	overflow: hidden;
    	padding-top: 50px*@ip5;
    	.page_cont{
    		ul{
    			li{
    				height: 54px*@ip5;
    				padding: 0 15px*@ip5;
    				border-bottom: 1px solid @black;
    				background: @deepblue;
    				&.current{
    					span{ color: @yellow;}
    					.icon_choose{
    						border-color: @yellow;
    						em{ background: @yellow;}
    					}
    				}
    				&:last-child{
    					border: none;
    				}
    				i{
    					float: left;
    					display: inline-block;
    					overflow: hidden;
    					&:first-child{
    						width: 22px*@ip5;
    						height: 22px*@ip5;
    						margin: 16px*@ip5 10px*@ip5 0 0;
    					}
    					&.icon_zfb{
    						background: url(../../assets/img/ways_zfb.png) no-repeat center center;
    						background-size: 22px 22px;
    					}
    					&.icon_wx{
    						background: url(../../assets/img/ways_wx.png) no-repeat center center;
    						background-size: 22px*@ip5 22px*@ip5;
    					}
    					&.icon_choose{
    						position: relative;
    						float: right;
    						width: 22px*@ip5;
    						height: 22px*@ip5;
    						border: 2px*@ip5 solid @blue;
    						border-radius: 100%;
    						margin: 16px*@ip5 0 0 0;
    						em{
    							position: absolute;
    							top: 3px*@ip5;
    							left: 3px*@ip5;
    							width: 12px*@ip5;
    							height: 12px*@ip5;
    							background: @blue;
    							border-radius: 100%;
    						}
    					}
    				}
    				span{
    					float: left;
    					line-height: 54px*@ip5;
    					color: @blue;
    					font-size: @fs16*@ip5;
    				}
    			}
    		}
    	}
    	.page_foot{
    		width: 100%;
    		overflow: hidden;
    		background: @deepblue;
    		position: fixed;
    		bottom: 0;
    		left: 0;
    		.pay_money{
    			height: 44px*@ip5;
    			line-height: 44px*@ip5;
    			text-align: center;
    			border-bottom: 1px solid @black;
    			font-size: @fs18*@ip5;
    		}
    		#bigBtn{
    			margin: 5px*@ip5 3.625%;
    		}
    	}
    }
}
</style>