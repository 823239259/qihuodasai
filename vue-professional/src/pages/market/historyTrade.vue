<template>
	<div id="historyTrade">
		<div class="head">
			<topbar title="历史成交"></topbar>
			<back></back>
		</div>
		<div class="search">
			<div class="ipt_box fl">
				<img src="../../assets/img/date.png"/>
				<input type="text" />
			</div>
			<img src="../../assets/img/date_arrow.png" class="date_arrow fl" />
			<div class="ipt_box fl">
				<img src="../../assets/img/date.png"/>
				<input type="text" />
			</div>
			<cbtn name="搜索"></cbtn>
		</div>
		<div class="tab_box mt10" id="tab_box">
			<ul>
				<template v-for="key in tablist">
					<li @tap="showCont">{{key.nav}}</li>
				</template>
			</ul>
		</div>
		<div id="dayCont" class="list_cont" v-if="dayShow">
			<ul>
				<li>
					<span>序号</span>
					<span>合约名称</span>
					<span>交易盈亏</span>
					<span>交易手数</span>
					<span>交易手续费</span>
				</li>
				<template v-for="key in dataList">
					<li>
						<span>{{key.index}}</span>
						<span>{{key.name}}</span>
						<span :class="{red: key.type_color == 'red', green: key.type_color == 'green'}">{{key.type}}</span>
						<span>{{key.num}}</span>
						<span :class="{red: key.type_color == 'red', green: key.type_color == 'green'}">{{key.money}}</span>
					</li>
				</template>
			</ul>
		</div>
		<div id="weekCont" class="list_cont" v-else-if="weekShow">
			<ul>
				<li>
					<span>序号</span>
					<span>合约名称</span>
					<span>交易盈亏</span>
					<span>交易手数</span>
					<span>交易手续费</span>
				</li>
				<template>
					<li>
						<span>2</span>
						<span>美原油09</span>
						<span>多</span>
						<span>12</span>
						<span>4800</span>
					</li>
				</template>
			</ul>
		</div>
		<div id="monthCont" class="list_cont" v-else>
			<ul>
				<li>
					<span>序号</span>
					<span>合约名称</span>
					<span>交易盈亏</span>
					<span>交易手数</span>
					<span>交易手续费</span>
				</li>
				<template>
					<li>
						<span>3</span>
						<span>美原油09</span>
						<span>多</span>
						<span>12</span>
						<span>4800</span>
					</li>
				</template>
			</ul>
		</div>
	</div>
</template>

<script>
	import topbar from '../../components/Topbar.vue'
	import back from '../../components/back.vue'
	import cbtn from '../../components/conditionBtn.vue'
	export default{
		name:'historyTrade',
		components:{topbar, back, cbtn},
		data() {
			return {
				dayShow: true,
				weekShow: false,
				tablist: [{
					nav: '一天内'},{
					nav: '一周内'},{
					nav: '一月内',
				}],
				dataList: [
					{
						index: 1,
						name: '美原油09',
						type: '多',
						num: 12,
						money: 4800,
						type_color: 'green',
						money_color: 'green'
					},
					{
						index: 2,
						name: '美原油09',
						type: '空',
						num: 12,
						money: 4800,
						type_color: 'red',
						money_color: 'red'
					},
				]
			}
		},
		methods: {
			showCont: function(e){
				$(e.currentTarget).addClass('current').siblings().removeClass('current');
				if($(e.currentTarget).index() == 0){
					this.dayShow = true;
					this.weekShow = false;
				}else if($(e.currentTarget).index() == 1){
					this.dayShow = false;
					this.weekShow = true;
				}else{
					this.dayShow = false;
					this.weekShow = false;
				}
				
			}
		},
		mounted: function(){
			$("#tab_box li:first-child").addClass("current");
			$("#historyTrade").css("height",window.screen.height + "px");
		}
	}
</script>

<style scoped lang="less">
	@import url("../../assets/css/main.less");
	@import url("../../assets/css/base.less");
	/*ip5*/
	@media(max-width:370px) {
		#historyTrade{
			width: 100%;
			padding-top: 50px*@ip5;
			padding-bottom: 50px*@ip5;
			background: @black;
		}
		.head{
			position: relative;
			#back{
				position: absolute;
				z-index: 1000;
				top: -35px*@ip5;
				left: 15px*@ip5;
			}
		}
		.search{
			height: 44px*@ip5;
			padding: 0 15px*@ip5;
			background: @deepblue;
			.ipt_box{
				width: 110px*@ip5;
				height: 34px*@ip5;
				overflow: hidden;
				background: @black;
				margin: 5px*@ip5 0;
				border-radius: 4px*@ip5;
				img{
					float: left;
					width: 20px*@ip5;
					height: 21px*@ip5;
					margin: 7px*@ip5;
				}
				input{
					float: left;
					width: 75px*@ip5;
					height: 34px*@ip5;
					line-height: 34px*@ip5;
					background: none;
					color: @white;
					padding: 0;
				}
			}
			.date_arrow{
				width: 18px*@ip5;
				height: 8px*@ip5;
				margin: 18px*@ip5 5px*@ip5;
			}
			#conditionBtn{
				float: right;
				margin-top: 5px*@ip5;	
			}
		}
		.tab_box{
			height: 42px*@ip5;
			background: @deepblue;
			li{
				float: left;
				width: 33.33%;
				height: 42px*@ip5;
				line-height: 42px*@ip5;
				color: @lightblue;
				text-align: center;
				font-size: @fs14*@ip5;
				&.current{
					color: @yellow;
				}
			}
		}
		.list_cont{
			width: 100%;
			height: auto;
			background: @deepblue;
			overflow-x: scroll;
			ul{
				width: 120%;
				box-sizing: content-box;
				li{
					width: 100%;
					height: 44px*@ip5;
					line-height: 44px*@ip5;
					border-top: 1px solid @black;
					padding: 0 3.62%;
					&:nth-child(1){
						background: #36394d;
					}
					span{
						display: inline-block;
						float: left;
						width: 20%;
						font-size: @fs14*@ip5;
						color: @blue;
						&:nth-child(1){
							width: 14%;
						}
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
	}
	/*ip6*/
	@media (min-width:371px) and (max-width:410px) {
	    #historyTrade{
			width: 100%;
			padding-top: 50px*@ip6;
			padding-bottom: 50px*@ip6;
			background: @black;
		}
		.head{
			position: relative;
			#back{
				position: absolute;
				z-index: 1000;
				top: -35px*@ip6;
				left: 15px*@ip6;
			}
		}
		.search{
			height: 44px*@ip6;
			padding: 0 15px*@ip6;
			background: @deepblue;
			.ipt_box{
				width: 110px*@ip6;
				height: 34px*@ip6;
				overflow: hidden;
				background: @black;
				margin: 5px*@ip6 0;
				border-radius: 4px*@ip6;
				img{
					float: left;
					width: 20px*@ip6;
					height: 21px*@ip6;
					margin: 7px*@ip6;
				}
				input{
					float: left;
					width: 75px*@ip6;
					height: 34px*@ip6;
					line-height: 34px*@ip6;
					background: none;
					color: @white;
					padding: 0;
				}
			}
			.date_arrow{
				width: 18px*@ip6;
				height: 8px*@ip6;
				margin: 18px*@ip6 5px*@ip6;
			}
			#conditionBtn{
				float: right;
				margin-top: 5px*@ip6;	
			}
		}
		.tab_box{
			height: 42px*@ip6;
			background: @deepblue;
			li{
				float: left;
				width: 33.33%;
				height: 42px*@ip6;
				line-height: 42px*@ip6;
				color: @lightblue;
				text-align: center;
				font-size: @fs14*@ip6;
				&.current{
					color: @yellow;
				}
			}
		}
		.list_cont{
			width: 100%;
			height: auto;
			background: @deepblue;
			overflow-x: scroll;
			ul{
				width: 120%;
				box-sizing: content-box;
				li{
					width: 100%;
					height: 44px*@ip6;
					line-height: 44px*@ip6;
					border-top: 1px solid @black;
					padding: 0 3.62%;
					&:nth-child(1){
						background: #36394d;
					}
					span{
						display: inline-block;
						float: left;
						width: 20%;
						font-size: @fs14*@ip6;
						color: @blue;
						&:nth-child(1){
							width: 14%;
						}
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
	}
	/*ip6p及以上*/
	@media (min-width:411px) {
	    #historyTrade{
			width: 100%;
			padding-top: 50px;
			padding-bottom: 50px;
			background: @black;
		}
		.head{
			position: relative;
			#back{
				position: absolute;
				z-index: 1000;
				top: -35px;
				left: 15px;
			}
		}
		.search{
			height: 44px;
			padding: 0 15px;
			background: @deepblue;
			.ipt_box{
				width: 110px;
				height: 34px;
				overflow: hidden;
				background: @black;
				margin: 5px 0;
				border-radius: 4px;
				img{
					float: left;
					width: 20px;
					height: 21px;
					margin: 7px;
				}
				input{
					float: left;
					width: 75px;
					height: 34px;
					line-height: 34px;
					background: none;
					color: @white;
					padding: 0;
				}
			}
			.date_arrow{
				width: 18px;
				height: 8px;
				margin: 18px 5px;
			}
			#conditionBtn{
				float: right;
				margin-top: 5px;	
			}
		}
		.tab_box{
			height: 42px;
			background: @deepblue;
			li{
				float: left;
				width: 33.33%;
				height: 42px;
				line-height: 42px;
				color: @lightblue;
				text-align: center;
				font-size: @fs14;
				&.current{
					color: @yellow;
				}
			}
		}
		.list_cont{
			width: 100%;
			height: auto;
			background: @deepblue;
			overflow-x: scroll;
			ul{
				width: 120%;
				box-sizing: content-box;
				li{
					width: 100%;
					height: 44px;
					line-height: 44px;
					border-top: 1px solid @black;
					padding: 0 3.62%;
					&:nth-child(1){
						background: #36394d;
					}
					span{
						display: inline-block;
						float: left;
						width: 20%;
						font-size: @fs14;
						color: @blue;
						&:nth-child(1){
							width: 14%;
						}
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
	}
	
	
	
</style>
