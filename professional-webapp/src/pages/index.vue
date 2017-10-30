<template>
	<div id="index">
		<div class="order">
			<div class="title">
				<ul>
					<template></template>
					<li class="current">
						<span>国际期货</span>
					</li>
					<li>
						<span>国内期货</span>
					</li>
					<li>
						<span>添加外汇</span>
					</li>
				</ul>
			</div>
			<div class="cont">
				<table>
					<thead>
						<tr>
							<td></td>
							<td>名称</td>
							<td>合约代码</td>
							<td>最新</td>
							<td>现手</td>
							<td>买价</td>
							<td>卖价</td>
							<td>买量</td>
							<td>卖量</td>
							<td>成交量</td>
							<td>涨跌</td>
							<td>涨幅%</td>
							<td>持仓量</td>
							<td>开盘</td>
							<td>最高</td>
							<td>最低</td>
							<td>昨结算</td>
						</tr>
					</thead>
					<tbody>
						<template v-for="(v,index) in Parameters">
							<tr :class="{current: current == index}" @click="toggle(index, v.CommodityName, v.CommodityNo, v.MainContract, v.ExchangeNo)" @dblclick="dblclickEvent(v.CommodityNo)">
								<td class="ifont_arrow" v-show="v.LastQuotation.LastPrice >= v.LastQuotation.PreSettlePrice"><i class="ifont" :class="{red: v.LastQuotation.LastPrice > v.LastQuotation.PreSettlePrice, green: v.LastQuotation.LastPrice < v.LastQuotation.PreSettlePrice}">&#xe761;</i></td>
								<td class="ifont_arrow" v-show="v.LastQuotation.LastPrice < v.LastQuotation.PreSettlePrice"><i class="ifont" :class="{red: v.LastQuotation.LastPrice > v.LastQuotation.PreSettlePrice, green: v.LastQuotation.LastPrice < v.LastQuotation.PreSettlePrice}">&#xe76a;</i></td>
								<td>{{v.CommodityName}}</td>
								<td>{{v.CommodityNo + v.MainContract}}</td>
								<td class="price" :class="{red: v.LastQuotation.LastPrice > v.LastQuotation.PreSettlePrice, green: v.LastQuotation.LastPrice < v.LastQuotation.PreSettlePrice}">{{v.LastQuotation.LastPrice}}</td>
								<td>{{v.LastQuotation.LastVolume}}</td>
								<td class="price" :class="{red: v.LastQuotation.BidPrice1 > v.LastQuotation.PreSettlePrice, green: v.LastQuotation.BidPrice1 < v.LastQuotation.PreSettlePrice}">{{v.LastQuotation.BidPrice1}}</td>
								<td class="price" :class="{red: v.LastQuotation.AskPrice1 > v.LastQuotation.PreSettlePrice, green: v.LastQuotation.AskPrice1 < v.LastQuotation.PreSettlePrice}">{{v.LastQuotation.AskPrice1}}</td>
								<td>{{v.LastQuotation.BidQty1}}</td>
								<td>{{v.LastQuotation.AskQty1}}</td>
								<td>{{v.LastQuotation.TotalVolume}}</td>
								<td :class="{green: v.LastQuotation.ChangeRate < 0, red: v.LastQuotation.ChangeRate > 0}">{{v.LastQuotation.ChangeValue | fixNum(v.DotSize)}}</td>
								<td :class="{green: v.LastQuotation.ChangeRate < 0, red: v.LastQuotation.ChangeRate > 0}">{{v.LastQuotation.ChangeRate | fixNumTwo}}%</td>
								<td>{{v.LastQuotation.Position}}</td>
								<td :class="{red: v.LastQuotation.OpenPrice > v.LastQuotation.PreSettlePrice, green: v.LastQuotation.OpenPrice < v.LastQuotation.PreSettlePrice}">{{v.LastQuotation.OpenPrice}}</td>
								<td :class="{red: v.LastQuotation.HighPrice > v.LastQuotation.PreSettlePrice, green: v.LastQuotation.HighPrice < v.LastQuotation.PreSettlePrice}">{{v.LastQuotation.HighPrice}}</td>
								<td :class="{red: v.LastQuotation.LowPrice > v.LastQuotation.PreSettlePrice, green: v.LastQuotation.LowPrice < v.LastQuotation.PreSettlePrice}">{{v.LastQuotation.LowPrice}}</td>
								<td>{{v.LastQuotation.PreSettlePrice}}</td>
							</tr>
						</template>
					</tbody>
				</table>
			</div>
		</div>
		<div class="echarts_box">
			<div class="title">
				<span class="fl">{{orderName}}</span>
				<span class="fl">{{orderNum}}</span>
				<div class="add fr" :class="{current: !addStar}" @click="addOptional">
					<i class="ifont fl" v-show="addStar">&#xe754;</i>
					<i class="ifont fl" v-show="!addStar">&#xe602;</i>
					<span class="fl">{{optional}}</span>
				</div>
			</div>
			<div id="echarts_f" v-show="showFens">
				<div id="fens" class="chart"></div>
				<div id="volume" class="chart"></div>
			</div>
			<div id="echarts_k" v-show="showKline">
				<div id="kliness" class="char"></div>
				<div id="kliness_volume" class="chart"></div>
			</div>
		</div>
	</div>
</template>

<script>
	import pro from '../assets/js/common.js'
	export default{
		name:'index',
		data(){
			return{
				current: 0,
				showFens: false,
				showKline: false,
				orderName: '',
				orderNum: '',
				orderNo: '',
				orderId: '',
				optional: '添加自选',
				addStar: true,
				isJump: '',
				userInfo: '',
			}
		},
		computed: {
			quoteInitStatus(){
				return this.$store.state.market.quoteInitStatus;
			},
			Parameters(){
				return this.$store.state.market.Parameters;
			},
			tradeParameters(){
				return this.$store.state.market.tradeParameters;
			},
			quoteInitStep(){
				return this.$store.state.market.quoteInitStep;
			},
			quoteSocket(){
				return this.$store.state.quoteSocket;
			},
			quoteIndex(){
				return this.$store.state.market.quoteIndex;
			},
			quoteColor(){
				return this.$store.state.market.quoteColor;
			},
			isRefresh(){
				return this.$store.state.account.isRefresh;
			}
		},
		filters:{
			fixNumTwo: function(num){
				return num.toFixed(2);
			},
			fixNum: function(num, dotsize){
				return num.toFixed(dotsize);
			}
		},
		watch: {
			quoteInitStep: function(n, o){
				if(n && n == true){
					this.$store.state.market.currentdetail = this.Parameters[0];
					this.$store.state.market.currentTradeDetails = this.tradeParameters[0];
					this.showFens = true;
					this.showKline = true;
					//分时、k线容器赋高度
					var h = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;
					h = h - 50 - 30 - 40;
					$("#volume, #kliness_volume").height(h/2/10*3.7);
					$("#fens, #kliness").height(h/2/10*6);
					//开始画图
					this.$store.state.isshow.isfens = true;
					this.$store.state.isshow.iskline = true;
					this.orderName = this.Parameters[0].CommodityName;
					this.orderNum = this.Parameters[0].CommodityNo + this.Parameters[0].MainContract;
					this.orderNo = this.Parameters[0].CommodityNo;
					var data = {
						Method: "QryHistory",
						Parameters:{
							ExchangeNo: this.Parameters[0].ExchangeNo,
							CommodityNo: this.Parameters[0].CommodityNo,
							ContractNo: this.Parameters[0].MainContract,
							HisQuoteType: 0,
							BeginTime: "",
							EndTime: "",
							Count: 0
						}
					};
					this.quoteSocket.send(JSON.stringify(data));
					this.$store.state.market.selectTime = 1440;
					var datas = {
						Method: "QryHistory",
						Parameters:{
							ExchangeNo: this.Parameters[0].ExchangeNo,
							CommodityNo: this.Parameters[0].CommodityNo,
							ContractNo: this.Parameters[0].MainContract,
							HisQuoteType: 1440,
							BeginTime: "",
							EndTime: "",
							Count: 0
						}
					};
					this.quoteSocket.send(JSON.stringify(datas));
					this.$store.state.market.currentNo = this.Parameters[0].CommodityNo;
					//是否是自选合约
					this.isSelectedOrder();
				}
			},
			quoteIndex: function(n, o){
				if(this.quoteColor == 'red'){
					$("tbody tr").eq(n).addClass("red_bg");
					setTimeout(function(){
						$("tbody tr").eq(n).removeClass("red_bg");
					}, 500);
				}else{
					$("tbody tr").eq(n).addClass("green_bg");
					setTimeout(function(){
						$("tbody tr").eq(n).removeClass("green_bg");
					}, 500);
				}
			},
			isRefresh: function(n, o){
				if(n == 1){
					window.location.reload();
				}else if(n == ''){
					this.$store.state.account.isRefresh = '';
				}
			}
		},
		methods: {
			toggle: function(i, name, commodityNo, mainContract, exchangeNo){
				this.Parameters.forEach(function(o, i){
					if(commodityNo == o.CommodityNo){
						this.$store.state.market.currentdetail = o;
					}
				}.bind(this));
				this.tradeParameters.forEach(function(o, i){
					if(commodityNo == o.CommodityNo){
						this.$store.state.market.currentTradeDetails = o;
					}
				}.bind(this));
				this.$store.state.market.currentNo = commodityNo;
				this.current = i;
				this.orderName = name;
				this.orderNum = commodityNo + mainContract;
				this.orderNo = commodityNo;
				var data = {
					Method: "QryHistory",
					Parameters:{
						ExchangeNo: exchangeNo,
						CommodityNo: commodityNo,
						ContractNo: mainContract,
						HisQuoteType: 0,
						BeginTime: "",
						EndTime: "",
						Count: 0
					}
				};
				this.quoteSocket.send(JSON.stringify(data));
				this.$store.state.market.selectTime = 1440;
				var datas = {
					Method: "QryHistory",
					Parameters:{
						ExchangeNo: exchangeNo,
						CommodityNo: commodityNo,
						ContractNo: mainContract,
						HisQuoteType: 1440,
						BeginTime: "",
						EndTime: "",
						Count: 0
					}
				};
				this.quoteSocket.send(JSON.stringify(datas));
				//是否自选
				this.isSelectedOrder();
			},
			dblclickEvent: function(commodityNo){
				this.$router.push({path: '/trade'});
				$("#nav li").eq(2).addClass("current").siblings().removeClass("current");
				this.Parameters.forEach(function(o, i){
					if(commodityNo == o.CommodityNo){
						this.$store.state.market.currentdetail = o;
					}
				}.bind(this));
				this.$store.state.isshow.isfensshow = false;
				this.$store.state.isshow.iskline = false;
				localStorage.currentOrder = commodityNo;
			},
			addOptional: function(e){
				if(this.addStar == true){
					var data = {
						commodityCode: this.orderNo,
						commodityName: this.orderName,
					};
					var headers = {
						token:  this.userInfo.token,
						secret: this.userInfo.secret
					};
					pro.fetch('post', '/contract/saveOptional', data, headers).then(function(res){
						if(res.success == true){
							if(res.code == 1){
								layer.msg('添加成功', {time: 1000});
								this.addStar = false;
								this.optional = '取消自选';
							}
						}
					}.bind(this)).catch(function(error){
						var data = err.data;
						layer.msg(data.message, {time: 1000});
					});
				}else{
					var data = {
						commodityCode: this.orderId
					};
					var headers = {
						token:  this.userInfo.token,
						secret: this.userInfo.secret
					};
					pro.fetch('post', 'contract/delOptional', data, headers).then(function(res){
						if(res.success == true){
							if(res.code == 1){
								layer.msg('删除成功', {time: 1000});
								this.addStar = true;
								this.optional = '添加自选';
							}else{
								layer.msg(res.message, {time: 1000});
							}
						}
					}.bind(this)).catch(function(err){
						var data = err.data;
						layer.msg(data.message, {time: 1000});
					});
				}
			},
			isSelectedOrder: function(){
				if(!this.userInfo) return false;
				this.addStar = true;
				this.optional = '添加自选';
				var headers = {
					token:  this.userInfo.token,
					secret: this.userInfo.secret,
					version: ''
				};
				pro.fetch('post', 'contract/optional/list', '', headers).then(function(res){
					if(res.success == true){
						if(res.code == 1){
							res.data.forEach(function(o, i){
								if(o.commodityCode === this.orderNo){
									this.addStar = false;
									this.optional = '取消自选';
									this.orderId = o.commodityCode;
								}
							}.bind(this));
						}
					}
				}.bind(this)).catch(function(err){
					var data = err.data;
					if(data){
						layer.msg(data.message, {time: 1000});
					}
				});
			}
		},
		mounted: function(){
			//初始化页面高度
			var h = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;
			$(".order").height(h - 50 - 30);
			//获取平台账户登录信息
			this.userInfo = localStorage.user ? JSON.parse(localStorage.user) : '';
		}
	}
</script>

<style lang="scss" scoped>
	@import "../assets/css/common.scss";
	#index{
		position: relative;
	}
	.echarts_box{
		position: absolute;
		top: 0;
		right: 0;
		width: 400px;
		overflow: hidden;
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
				cursor: pointer;
				.ifont{
					color: $yellow;
					font-size: $fs16;
					margin-top: -2px;
				}
				span{
					font-size: $fs12;
					color: $yellow;
					margin-left: 5px;
				}
				&.current{
					span{
						color: #7a8199;
					}
					.ifont{
						font-size: 14px;
						margin-top: 0;
					}
				}
			}
		}
		#echarts_k{
			margin-top: 10px;
		}
		#fens, #volume, #kliness, #kliness_volume{
			width: 100%;
			margin: 0 auto;
		}
	}
	.order{
		width: 100%;
		overflow: hidden;
		padding-right: 410px;
		box-sizing: border-box;
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
			table{
				td.ifont_arrow{
					width: 32px;
					text-align: center;
					.ifont{
						font-size: $fs12;
						color: $lightblue;
					}
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
						height: 48px;
						&.red_bg{
							background: #54323e;
						}
						&.green_bg{
							background: #2c4c4a;
						}
					}
				}
				.price{
					min-width: 50px;
				}
			}
		}
	}
	.chart{
		width: 100%;
		margin: 0 auto;
	}
	@media only screen and (min-width: 1400px) and (max-width: 1600px) {
		.order .cont table tbody tr{
			height: 37px;
		}
	}
	@media only screen and (min-width: 1280px) and (max-width: 1366px) {
		.order .cont table tbody tr{
			height: 30px;
			td{
				font-size: $fs12;
			}
		}
	}

</style>