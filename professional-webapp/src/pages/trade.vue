<template>
	<div id="trade">
		<tradeLogin ref="tradeLogin" v-if="tradeLoginShow"></tradeLogin>
		<tradeLoginSpe ref="tradeLoginSpe" v-if="tradeLoginSpeShow"></tradeLoginSpe>
		<div class="quote">
			<div class="title">
				<ul>
					<li class="current">
						<span>自选</span>
					</li>
					<li>
						<span>行情</span>
					</li>
				</ul>
			</div>
			<div class="cont">
				<table>
					<thead>
						<tr>
							<td>合约代码</td>
							<td>最新/涨幅%</td>
							<td>成交量</td>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>
								<b>国际原油</b>
								<span>CL1705</span>
							</td>
							<td>
								<div class="fl">
									<span>46.94</span>
									<span>+0.24%</span>
								</div>
								<i class="ifont red fl">&#xe761;</i>
							</td>
							<td>10086</td>
						</tr>
						<tr>
							<td>
								<b>国际原油</b>
								<span>CL1705</span>
							</td>
							<td>
								<div class="fl">
									<span>46.94</span>
									<span>+0.24%</span>
								</div>
								<i class="ifont red fl">&#xe761;</i>
							</td>
							<td>10086</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
		<div class="trade_right">
			<div class="trade_right_top">
				<div class="echarts_box">
					<div class="title">
						<template v-for="(key, index) in echartList">
							<span :class="{current: selected == index}" @click="tabEvent(index)">{{key.name}}</span>
						</template>
					</div>
					<div class="cont">
						<component :is="selectView"></component>
					</div>
				</div>
				<div class="info">
					<div class="order_details">
						<div class="title">
							<span class="fl">美黄金</span>
							<span class="fl">CG1708</span>
							<div class="add fr">
								<i class="ifont fl">&#xe754;</i>
								<!--<i class="ifont fl" v-show="!addStar">&#xe602;</i>-->
								<span class="fl">添加自选</span>
							</div>
						</div>
						<p>
							<b>25288</b>
							<span>+152/+052%</span>
						</p>
						<ul>
							<li>
								<div class="col">
									<b>现手：</b>
									<span>10</span>
								</div>
								<div class="col">
									<b>成交量：</b>
									<span>37319</span>
								</div>
								<div class="col">
									<b>持仓量：</b>
									<span>10086</span>
								</div>
							</li>
							<li>
								<div class="col">
									<b>开盘：</b>
									<span class="green">24.90</span>
								</div>
								<div class="col">
									<b>最高：</b>
									<span class="red">25.06</span>
								</div>
								<div class="col">
									<b>最低：</b>
									<span class="green">24.04</span>
								</div>
							</li>
							<li>
								<div class="col">
									<b>昨结：</b>
									<span>25.11</span>
								</div>
								<div class="col">
									<b>涨停：</b>
									<span>- -</span>
								</div>
								<div class="col">
									<b>跌停：</b>
									<span>- -</span>
								</div>
							</li>
						</ul>
					</div>
					<div class="trade_details">
						<div class="quote_five fl">
							<h3>五档行情</h3>
							<ul>
								<li>
									<span>卖五</span>
									<span class="green">12112.12</span>
									<span>20</span>
								</li>
								<li>
									<span>卖四</span>
									<span class="red">12112.12</span>
									<span>20</span>
								</li>
								<li>
									<span>卖三</span>
									<span class="green">12112.12</span>
									<span>20</span>
								</li>
								<li>
									<span>卖二</span>
									<span class="red">12112.12</span>
									<span>20</span>
								</li>
								<li>
									<span>卖一</span>
									<span class="green">12112.12</span>
									<span>20</span>
								</li>
								<li>
									<span>买一</span>
									<span class="red">12112.12</span>
									<span>20</span>
								</li>
								<li>
									<span>买二</span>
									<span class="green">12112.12</span>
									<span>20</span>
								</li>
								<li>
									<span>买三</span>
									<span class="red">12112.12</span>
									<span>20</span>
								</li>
								<li>
									<span>买四</span>
									<span class="green">12112.12</span>
									<span>20</span>
								</li>
								<li>
									<span>买五</span>
									<span class="red">12112.12</span>
									<span>20</span>
								</li>
							</ul>
						</div>
						<div class="deal_details fr">
							<div class="title">
								<span>时间</span>
								<span>价格</span>
								<span>现手</span>
							</div>
							<ul>
								<li>
									<span>15:59:41</span>
									<span class="red">25289</span>
									<span>15</span>
								</li>
								<li>
									<span>15:59:41</span>
									<span class="green">25289</span>
									<span>15</span>
								</li>
								<li>
									<span>15:59:41</span>
									<span class="red">25289</span>
									<span>15</span>
								</li>
								<li>
									<span>15:59:41</span>
									<span class="green">25289</span>
									<span>15</span>
								</li>
								<li>
									<span>15:59:41</span>
									<span class="red">25289</span>
									<span>15</span>
								</li>
								<li>
									<span>15:59:41</span>
									<span class="green">25289</span>
									<span>15</span>
								</li>
								<li>
									<span>15:59:41</span>
									<span class="red">25289</span>
									<span>15</span>
								</li>
								<li>
									<span>15:59:41</span>
									<span class="green">25289</span>
									<span>15</span>
								</li>
								<li>
									<span>15:59:41</span>
									<span class="red">25289</span>
									<span>15</span>
								</li>
								<li>
									<span>15:59:41</span>
									<span class="green">25289</span>
									<span>15</span>
								</li>
							</ul>
						</div>
					</div>
					<div class="trade_login">
						<button class="btn yellow">我要开户</button>
						<button class="btn blue" @click="toTradeLogin">交易登录</button>
					</div>
				</div>
			</div>
			<!--<div class="trade_box">
				<div class="operate">
					<div class="head">
						<span class="fl">交易账号：158******36</span>
						<i class="sanjiao fl"></i>
						<a href="javascript:void(0);" class="fl">退出</a>
						<button class="btn yellow fl">追加保证金</button>
						<button class="btn blue fl">终结方案</button>
					</div>
					<div class="down_order">
						<div class="title">
							<ul>
								<li class="current">
									<span>普通单</span>
								</li>
								<li>
									<span>条件单</span>
								</li>
							</ul>
						</div>
						<div class="cont">
							<ul>
								<li>
									<label>合约代码</label>
									<div class="slt-box">
										<input type="text" class="slt" disabled="disabled" selectVal="0" value="富时A50"/>
										<span class="tal-box"><span class="tal"></span></span>
										<div class="slt-list">
											<ul>
												<li selectVal="1">富时A50</li>
												<li selectVal="1">富时A50</li>
												<li selectVal="1">富时A50</li>
												<li selectVal="1">富时A50</li>
												<li selectVal="1">富时A50</li>
											</ul>
										</div>
									</div>
								</li>
								<li>
									<label>订单类型</label>
									<div class="col">
										<span class="current">市价</span>
										<span>限价</span>
									</div>
								</li>
								<li>
									<p>市价</p>
								</li>
								<li>
									<label>委托数量</label>
									<div class="col">
										<i class="ifont fl">&#xe6f2;</i>
										<input type="text" class="fl" value="10" />
										<i class="ifont fr">&#xe601;</i>
									</div>
								</li>
							</ul>
							<div class="btn_box">
								<button class="red">买入/市价</button>
								<button class="green">卖出/市价</button>
							</div>
						</div>
					</div>
				</div>
				<div class="trade_list">
					<div class="head">
						<p><span>总资产：</span><em>0.00</em></p>
						<p><span>余额：</span><em>0.00</em></p>
						<p><span>持仓盈亏：</span><em>0.00</em></p>
						<p><span>交易盈亏：</span><em>0.00</em></p>
						<p><span>平仓线：</span><em>0.00</em></p>
						<p><span>风险度%：</span><em>0.00%</em></p>
					</div>
					<div class="list">
						<div class="title">
							<ul>
								<template></template>
								<li class="current"><span>持仓</span></li>
								<li><span>挂单</span></li>
								<li><span>委托</span></li>
								<li><span>止损单</span></li>
								<li><span>条件单</span></li>
								<li><span>当日成交</span></li>
								<li><span>历史成交</span></li>
								<li><span>资金明细</span></li>
							</ul>
						</div>
						<div class="cont">
							<table>
								<thead>
									<tr>
										<td>合约名称</td>
										<td>多空</td>
										<td>手数</td>
										<td>持仓均价</td>
										<td>浮动盈利</td>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td>富时A50</td>
										<td class="red">多</td>
										<td>1</td>
										<td>25289</td>
										<td class="red">+100.00：USD</td>
									</tr>
									<tr>
										<td>富时A50</td>
										<td class="green">空</td>
										<td>1</td>
										<td>25289</td>
										<td class="green">-100.00：USD</td>
									</tr>
									<tr>
										<td>富时A50</td>
										<td class="red">多</td>
										<td>1</td>
										<td>25289</td>
										<td class="red">+100.00：USD</td>
									</tr>
								</tbody>
							</table>
							<div class="tools">
								<button class="btn blue">全部平仓</button>
								<button class="btn blue">平仓</button>
								<button class="btn blue">反手</button>
								<button class="btn blue">止损止盈</button>
							</div>
						</div>
					</div>
				</div>
			</div>-->
		</div>
	</div>
</template>

<script>
	import pro from '../assets/js/common.js'
	import tradeLogin from './trade/tradeLogin.vue'
	import tradeLoginSpe from './trade/tradeLoginSpe.vue'
	import fens from './trade/fens.vue'
	import light from './trade/light.vue'
	import klineOne from './trade/klineOne.vue'
	import klineFive from './trade/klineFive.vue'
	import klineFifteen from './trade/klineFifteen.vue'
	import klineThirty from './trade/klineThirty.vue'
	import klineDay from './trade/klineDay.vue'
	export default{
		name:'trade',
		components: {tradeLogin, tradeLoginSpe, fens, light, klineOne, klineFive, klineFifteen, klineThirty, klineDay},
		data(){
			return{
				tradeLoginShow: false,
				tradeLoginSpeShow: false,
				selected: 1,
				selectView: 'fens',
				echartList: [
					{name: '闪电'},
					{name: '分时'},
					{name: '1分'},
					{name: '5分'},
					{name: '15分'},
					{name: '30分'},
					{name: '日线'}
				],
			}
		},
		methods: {
			tabEvent: function(index){
				this.selected = index;
				if(index == 0){
					this.selectView = 'light';
				}else if(index == 1){
					this.selectView = 'fens';
				}else if(index == 2){
					this.selectView = 'klineOne';
				}else if(index == 3){
					this.selectView = 'klineFive';
				}else if(index == 4){
					this.selectView = 'klineFifteen';
				}else if(index == 5){
					this.selectView = 'klineThirty';
				}else if(index == 6){
					this.selectView = 'klineDay';
				}
				this.$store.state.isshow.isfensshow = false;
				this.$store.state.isshow.isklineshow = false;
				this.$store.state.isshow.islightshow = false;
			},
			toTradeLogin: function(){
//				this.tradeLoginShow = true;
				this.tradeLoginSpeShow = true;
			}
		},
		mounted: function(){
			//调用下拉框
			$(".slt-box").each(function(i, o){
				pro.selectEvent(o);
			});
			//初始化高度
			var h = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;
			$(".trade_right_top").height(h - 50 - 30);
		},
	}
</script>

<style scoped lang="scss">
	@import "../assets/css/common.scss";
	#trade{
		position: relative;
	}
	.quote{
		float: left;
		width: 250px;
		overflow: hidden;
		.title{
			height: 35px;
			ul{
				li{
					float: left;
					width: 120px;
					height: 35px;
					line-height: 35px;
					text-align: center;
					overflow: hidden;
					background: $deepblue;
					border-bottom: 5px solid $black;
					cursor: pointer;
					&:hover, &.current{
						border-color: $deepblue;
						span{
							color: $yellow;
						}
					}
					&:first-child{
						margin-right: 10px;
					}
				}
			}
		}
		.cont{
			width: 100%;
			table{
				td:first-child{
					padding-left: 10px;
				}
				td:last-child{
					padding-right: 10px;
				}
				thead{
					tr{
						height: 30px;
						font-size: $fs12;
						background: $bottom_color;
					}
				}
				tbody{
					tr{
						height: 50px;
						td{
							&:first-child{
								span{
									font-size: $fs12;
								}
							}
						}
						b, span{
							display: block;
							margin-top: 5px;
						}
						b{
							color: $white;
						}
						.ifont{
							margin: 12px 0 0 5px;
						}
					}
				}
			}
		}
	}
	.trade_right{
		width: 100%;
		padding-left: 260px;
	}
	.trade_right_top{
		/*height: 536px;*/
		overflow: hidden;
		background: $black;
		position: relative;
		.echarts_box{
			float: left;
			width: 100%;
			padding-right: 410px;
			overflow: hidden;
			.title{
				height: 30px;
				line-height: 30px;
				background: $blue;
				text-align: center;
				span{
					display: inline-block;
					margin: 0 5px;
					font-size: $fs12;
					cursor: pointer;
					&:hover, &.current{
						color: $yellow;
					}
				}
			}
		}
		.info{
			position: absolute;
			top: 0;
			right: 0;
			width: 400px;
			overflow: hidden;
			.order_details{
				.title{
					height: 40px;
					line-height: 40px;
					padding: 0 10px;
					background: $bottom_color;
					span{
						font-size: $fs16;
						color: $white;
						&:nth-child(2){
							margin-left: 10px;
						}
					}
					.add{
						.ifont{
							color: $yellow;
							font-size: $fs16;
						}
						span{
							font-size: $fs12;
							color: $yellow;
							margin-left: 5px;
						}
					}
				}
				p{
					height: 40px;
					line-height: 40px;
					background: $deepblue;
					padding: 0 10px;
					color: $red;
					b{
						font-size: $fs20;
						margin-right: 30px;
					}
				}
				ul li{
					width: 400px;
					height: 40px;
					line-height: 40px;
					border-top: 1px solid $bottom_color;
					.col{
						width: 30%;
						float: left;
						margin-left: 10px;
						b, span{
							float: left;
						}
						span{
							&.green{
								color: $green;
							}
							&.red{
								color: $red;
							}
						}
					}
				}
			}
			.trade_details{
				width: 100%;
				overflow: hidden;
				background: $bottom_color;
				.quote_five, .deal_details{
					width: 190px;
					overflow: hidden;
					h3, .title{
						height: 30px;
						line-height: 30px;
						font-size: $fs12;
					}
					ul{
						height: 301px;
						background: $deepblue;
						li span{
							&.green{
								color: $green;
							}
							&.red{
								color: $red;
							}
						}
					}
				}
				.quote_five{
					h3{
						text-align: center;
					}
					li{
						height: 28px;
						line-height: 28px;
						padding: 0 10px;
						span{
							display: inline-block;
							margin-right: 20px;
							&:last-child{
								margin: 0;
							}
						}
					}
				}
				.deal_details{
					.title, li{
						padding: 0 20px 0 10px;
						span{
							display: inline-block; 
							&:nth-child(1){
								width: 70px;
							}
							&:nth-child(2){
								width: 50px;
							}
							&:nth-child(3){
								float: right;
							}
						}
					}
					ul{
						overflow-y: auto;
						li{
							height: 30px;
							line-height: 30px;
							border-top: 1px solid $bottom_color;
							&:first-child{
								border: none;
							}
						}
					}
				}
			}
			.trade_login{
				width: 100%;
				height: 160px;
				overflow: hidden;
				background: $blue;
				margin-top: 5px;
				padding-top: 30px;
				button{
					display: block;
					margin: 0 auto;
					width: 120px;
					height: 40px;
					line-height: 40px;
					&:last-child{
						margin-top: 20px;
					}
				}
			}
		}
	}
	.trade_box{
		width: 100%;
		height: 280px;
		overflow: hidden;
		margin-top: 10px;
		.operate{
			float: left;
			width: 390px;
			height: 280px;
			overflow: hidden;
			margin-right: 10px;
			.head{
				height: 30px;
				line-height: 30px;
				overflow: hidden;
				background: $bottom_color;
				padding: 0 0 0 10px;
				font-size: $fs12;
				.sanjiao{
					display: inline-block;
				    width: 0;
					height: 0;
				    overflow: hidden;
				    margin: 10px 0 0 7px;
				    font-size: 0;
				    line-height: 0;
				    border-color: #7a7f99 transparent transparent transparent;
				    border-style: solid dashed dashed dashed;
				    border-width: 7px;
				    cursor: pointer;
				}
				a{
					color: $lightblue;
					margin: 0 20px;
				}
				.btn{
					height: 20px;
					line-height: 20px;
					font-size: $fs12;
					padding: 0 10px;
					margin: 5px 10px 5px 0;
				}
			}
			.down_order{
				margin-top: 5px;
				.title{
					height: 35px;
					li{
						float: left;
						width: 190px;
						height: 35px;
						line-height: 35px;
						text-align: center;
						overflow: hidden;
						background: $blue;
						border-bottom: 5px solid $black;
						margin-right: 10px;
						cursor: pointer;
						&:hover, &.current{
							border-color: $blue;
							span{
								color: $yellow;
							}
						}
						&:last-child{
							margin: 0;
						}
					}
				}
				.cont{
					overflow: hidden;
					background: $blue;
					li{
						width: 100%;
						height: 30px;
						padding: 0 10px;
						margin-top: 5px;
						label, span, p{
							display: inline-block;
							float: left;
							height: 30px;
							line-height: 30px;
							color: #7a8199;
						}
						label{
							width: 70px;
						}
						p{
							float: right;
							width: 300px;
							height: 30px;
							line-height: 30px;
							text-align: center;
							overflow: hidden;
							border: 1px solid #474c66;
							border-radius: 5px;
							color: $white;
						}
						.col{
							float: left;
							width: 300px;
							height: 30px;
							border: 1px solid #474c66;
							border-radius: 5px;
							span{
								width: 148px;
								text-align: center;
								border-radius: 5px;
								cursor: pointer;
								&.current{
									background: #474c66;
								}
							}
							.ifont{
								width: 30px;
								height: 30px;
								line-height: 30px;
								text-align: center;
								color: $black;
								background: #474c66;
								cursor: pointer;
							}
							input{
								width: 238px;
								height: 30px;
								line-height: 30px;
								color: $white;
								padding: 0 10px;
								box-sizing: border-box;
								text-align: center;
								font-size: $fs14;
							}
						}
					}
					.btn_box{
						width: 100%;
						height: 30px;
						margin-top: 40px;
						button{
							float: left;
							width: 50%;
							height: 30px;
							line-height: 30px;
							color: $white;
							border: none;
							background: none;
							&.red{
								background: $red;
							}
							&.green{
								background: $green;
							}
						}
					}
				}
			}	
		}
		.trade_list{
			float: left;
			width: 1188px;
			height: 280px;
			.head{
				height: 30px;
				line-height: 30px;
				background: $bottom_color;
				padding: 0 10px;
				font-size: $fs12;
				p{
					float: left;
					margin-right: 30px;
				}
			}
			.list{
				.title{
					height: 35px;
					margin-top: 5px;
					ul{
						li{
							float: left;
							width: 80px;
							height: 35px;
							line-height: 35px;
							text-align: center;
							overflow: hidden;
							background: $deepblue;
							border-bottom: 5px solid $black;
							margin-right: 5px;
							cursor: pointer;
							&:hover, &.current{
								border-color: $deepblue;
								span{
									color: $yellow;
								}
							}
							
						}
					}
				}
				.cont{
					height: 210px;
					overflow: hidden;
					background: $blue;
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
						margin: 15px 0 0 10px;
						.btn{
							width: 90px;
							height: 30px;
							line-height: 30px;
						}
					}
				}
			}
		}
	}
</style>