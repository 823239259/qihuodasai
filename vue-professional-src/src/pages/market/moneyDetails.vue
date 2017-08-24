<template>
	<div id="moneyDetails">
		<div class="head">
			<topbar title="资金详情"></topbar>
			<back></back>
		</div>
		<div class="list">
			<div class="list_left fl">
				<ul>
					<li><span>币种</span></li>
					<li><span>昨结存</span></li>
					<li><span>今收益</span></li>
					<li><span>今可用</span></li>
					<li><span>保证金</span></li>
					<li><span>冻结资金</span></li>
					<li><span>逐笔浮盈</span></li>
					<li><span>平仓盈亏</span></li>
					<li><span>入金</span></li>
					<li><span>出金</span></li>
					<li><span>平仓线</span></li>
					<li><span>风险度</span></li>
				</ul>
			</div>
			<div class="list_right fl">
				<ul>
					<li>
						<span>美元</span>
						<span>人民币</span>
						<span>欧元</span>
						<span>港币</span>
						<span>日元</span>
					</li>
					<li>
						<span>10000000</span>
						<span>10000000</span>
						<span>10000000</span>
						<span>10000000</span>
						<span>10000000</span>
					</li>
					<li>
						<span>10000000</span>
						<span>10000000</span>
						<span>10000000</span>
						<span>10000000</span>
						<span>10000000</span>
					</li>
					<li>
						<span></span>
						<span></span>
						<span></span>
						<span></span>
						<span></span>
					</li>
					<li>
						<span></span>
						<span></span>
						<span></span>
						<span></span>
						<span></span>
					</li>
					<li>
						<span></span>
						<span></span>
						<span></span>
						<span></span>
						<span></span>
					</li>
					<li>
						<span></span>
						<span></span>
						<span></span>
						<span></span>
						<span></span>
					</li>
					<li>
						<span></span>
						<span></span>
						<span></span>
						<span></span>
						<span></span>
					</li>
					<li>
						<span></span>
						<span></span>
						<span></span>
						<span></span>
						<span></span>
					</li>
					<li>
						<span></span>
						<span></span>
						<span></span>
						<span></span>
						<span></span>
					</li>
					<li>
						<span></span>
						<span></span>
						<span></span>
						<span></span>
						<span></span>
					</li>
					<li>
						<span></span>
						<span></span>
						<span></span>
						<span></span>
						<span></span>
					</li>
				</ul>
			</div>
		</div>
	</div>
</template>

<script>
	import topbar from '../../components/Topbar.vue'
	import back from '../../components/back.vue'
	export default{
		name:'moneyDetails',
		components:{topbar, back},
		mounted: function(){
			$("#moneyDetails").css("height",window.screen.height + "px");
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
	    #moneyDetails{
			width: 100%;
			padding-top: 50px;
			background: @black;
		}
		.head{
			#back{
				position: fixed;
				top: 0;
				left: 0;
				z-index: 1000;
			}
		}
		.list{
			width: 100%;
			overflow: hidden;
			position: fixed;
			top: 50px;
			left: 0;
			.list_left{
				width: 88px;
				li{
					width: 88px;
					background: #36394d;
					span{
						width: 88px;
						padding-left: 15px;
						color: @blue;
					}
				}
			}
			.list_right{
				width: 326px;
				overflow-x: scroll;
				ul{
					width: 136%;
					li{
						background: @deepblue;
						span{
							width: 88px;
							text-align: center;
							color: @white;
							border-right: 1px solid @black;
							&:last-child{
								border: none;
							}
						}
					}
				}
			}
			li{
				height: 44px;
				border-bottom: 1px solid @black;
				border-right: 1px solid @black;
				&:nth-child(7), &:nth-child(9), &:nth-child(11){
					margin-top: 10px;
				}
				span{
					display: inline-block;
					float: left;
					height: 44px;
					line-height: 44px;
					font-size: @fs14;
				}
			}
		}
	}
	
	/*ip6*/
	@media (min-width:371px) and (max-width:410px) {
	    #moneyDetails{
			width: 100%;
			padding-top: 50px*@ip6;
			background: @black;
		}
		.head{
			#back{
				position: fixed;
				top: 0;
				left: 0;
				z-index: 1000;
			}
		}
		.list{
			width: 100%;
			overflow: hidden;
			position: fixed;
			top: 50px*@ip6;
			left: 0;
			.list_left{
				width: 88px*@ip6;
				li{
					width: 88px*@ip6;
					background: #36394d;
					span{
						width: 88px*@ip6;
						padding-left: 15px*@ip6;
						color: @blue;
					}
				}
			}
			.list_right{
				width: 326px*@ip6;
				overflow-x: scroll;
				ul{
					width: 136%;
					li{
						background: @deepblue;
						span{
							width: 88px*@ip6;
							text-align: center;
							color: @white;
							border-right: 1px solid @black;
							&:last-child{
								border: none;
							}
						}
					}
				}
			}
			li{
				height: 44px*@ip6;
				border-bottom: 1px solid @black;
				border-right: 1px solid @black;
				&:nth-child(7), &:nth-child(9), &:nth-child(11){
					margin-top: 10px*@ip6;
				}
				span{
					display: inline-block;
					float: left;
					height: 44px*@ip6;
					line-height: 44px*@ip6;
					font-size: @fs14*@ip6;
				}
			}
		}
	}
	
	/*ip5*/
	@media(max-width:370px) {
		#moneyDetails{
			width: 100%;
			padding-top: 50px*@ip5;
			background: @black;
		}
		.head{
			#back{
				position: fixed;
				top: 0;
				left: 0;
				z-index: 1000;
			}
		}
		.list{
			width: 100%;
			overflow: hidden;
			position: fixed;
			top: 50px*@ip6;
			left: 0;
			.list_left{
				width: 88px*@ip5;
				li{
					width: 88px*@ip5;
					background: #36394d;
					span{
						width: 88px*@ip5;
						padding-left: 15px*@ip5;
						color: @blue;
					}
				}
			}
			.list_right{
				width: 326px*@ip5;
				overflow-x: scroll;
				ul{
					width: 136%;
					li{
						background: @deepblue;
						span{
							width: 88px*@ip5;
							text-align: center;
							color: @white;
							border-right: 1px solid @black;
							&:last-child{
								border: none;
							}
						}
					}
				}
			}
			li{
				height: 44px*@ip5;
				border-bottom: 1px solid @black;
				border-right: 1px solid @black;
				&:nth-child(7), &:nth-child(9), &:nth-child(11){
					margin-top: 10px*@ip5;
				}
				span{
					display: inline-block;
					float: left;
					height: 44px*@ip5;
					line-height: 44px*@ip5;
					font-size: @fs14*@ip5;
				}
			}
		}
	}
</style>
