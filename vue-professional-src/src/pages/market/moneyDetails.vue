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
					<!--<template v-for="key in moneyDetailList">
						<li><span>{{key.title}}</span></li>
					</template>-->
					<li><span>昨结存</span></li>
					<li><span>今权益</span></li>
					<li><span>今可用</span></li>
					<li><span>保证金</span></li>
					<li><span>冻结资金</span></li>
					<li><span>逐笔浮盈</span></li>
					<li><span>平仓盈亏</span></li>
					<li><span>入金</span></li>
					<li><span>出金</span></li>
					<!--<li><span>平仓线</span></li>
					<li><span>风险度</span></li>-->
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
					<template v-for="key in moneyDetailList">
						<li>
							<span>{{key.val[0]}}</span>
							<span>{{key.val[4]}}</span>
							<span>{{key.val[2]}}</span>
							<span>{{key.val[1]}}</span>
							<span>{{key.val[3]}}</span>
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
	export default{
		name:'moneyDetails',
		components:{topbar, back},
		data(){
			return{
				status: 0,
				moneyDetailList: []
			}
		},
		computed: {
			moneyDetail: function(){
				return this.$store.state.market.CacheAccount.moneyDetail;
			}
		},
		watch: {
			moneyDetail: function(n, o){
				console.log(1);
				this.status = 1;
				this.moneyDetailList = [];
				var OldAmount = {title: '昨结存', val: []}, 
					TodayBalance = {title: '今权益', val: []}, 
					TodayCanUse = {title: '今可用', val: []},  
					Deposit = {title: '保证金', val: []},  
					FrozenMoney = {title: '冻结资金', val: []}, 
					FloatingProfit = {title: '逐笔浮盈', val: []},
					CloseProfit = {title: '平仓盈亏', val: []},
					InMoney = {title: '入金', val: []},
					OutMoney = {title: '出金', val: []};
				n.forEach(function(o, i){
					OldAmount.val.push(parseFloat(o.OldAmount).toFixed(2)); // 昨结存
					TodayBalance.val.push(parseFloat(o.TodayBalance).toFixed(2));//今权益
					TodayCanUse.val.push(parseFloat(o.TodayCanUse).toFixed(2));//今可用
					Deposit.val.push(parseFloat(o.Deposit).toFixed(2));//保证金
					FrozenMoney.val.push(parseFloat(o.FrozenMoney).toFixed(2));//冻结资金
					FloatingProfit.val.push(parseFloat(o.FloatingProfit).toFixed(2));//逐笔浮盈
					CloseProfit.val.push(parseFloat(o.CloseProfit).toFixed(2));//平仓盈亏
					InMoney.val.push(parseFloat(o.InMoney).toFixed(2)); //入金
					OutMoney.val.push(parseFloat(o.OutMoney).toFixed(2));//出金
				}.bind(this));
				this.moneyDetailList.push(OldAmount);
				this.moneyDetailList.push(TodayBalance);
				this.moneyDetailList.push(TodayCanUse);
				this.moneyDetailList.push(Deposit);
				this.moneyDetailList.push(FrozenMoney);
				this.moneyDetailList.push(FloatingProfit);
				this.moneyDetailList.push(CloseProfit);
				this.moneyDetailList.push(InMoney);
				this.moneyDetailList.push(OutMoney);
			}
		},
		mounted: function(){
			$("#moneyDetails").css("height",window.screen.height + "px");
			//初始化页面数据
			console.log(this.moneyDetail);
			if(this.status == 0){
				this.moneyDetailList.push({title: '昨结存', val: ['0.00','0.00','0.00','0.00','0.00']});
				this.moneyDetailList.push({title: '今权益', val: ['0.00','0.00','0.00','0.00','0.00']});
				this.moneyDetailList.push({title: '今可用', val: ['0.00','0.00','0.00','0.00','0.00']});
				this.moneyDetailList.push({title: '保证金', val: ['0.00','0.00','0.00','0.00','0.00']});
				this.moneyDetailList.push({title: '冻结资金', val: ['0.00','0.00','0.00','0.00','0.00']});
				this.moneyDetailList.push({title: '逐笔浮盈', val: ['0.00','0.00','0.00','0.00','0.00']});
				this.moneyDetailList.push({title: '平仓盈亏', val: ['0.00','0.00','0.00','0.00','0.00']});
				this.moneyDetailList.push({title: '入金', val: ['0.00','0.00','0.00','0.00','0.00']});
				this.moneyDetailList.push({title: '出金', val: ['0.00','0.00','0.00','0.00','0.00']});
			}
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
