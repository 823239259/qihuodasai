<template>
	<div id="index">
		<div class="order">
			<div class="title">
				<ul>
					<li class="current">
						<span>国际期货</span>
					</li>
					<!--<li>
						<span @click="click">国内期货</span>
					</li>
					<li>
						<span>添加外汇</span>
					</li>-->
				</ul>
			</div>
			<div class="cont">
				<ul class="head">
					<li>
						<span class="ifont_arrow"><i class="ifont">&#xe761;</i></span>
						<span class="name">名称</span>
						<span class="code">合约代码</span>
						<span class="price">最新</span>
						<span class="num_sm">现手</span>
						<span class="price">买价</span>
						<span class="price">卖价</span>
						<span class="num_sm">买量</span>
						<span class="num_sm">卖量</span>
						<span class="num_bg">成交量</span>
						<span class="num_bg">涨跌额</span>
						<span class="num_bg">涨跌幅%</span>
						<span class="num_bg">持仓量</span>
						<span class="num_bg">开盘</span>
						<span class="num_bg">最高</span>
						<span class="num_bg">最低</span>
						<span class="num_bg">昨结算</span>
					</li>
				</ul>
				<ul class="body">
					<template v-for="(v,index) in Parameters">
						<li :class="{current: current == index}" @click="toggle(index, v.CommodityName, v.CommodityNo, v.MainContract, v.ExchangeNo)" @dblclick="dblclickEvent(v.CommodityNo)">
							<span class="ifont_arrow" v-show="v.LastQuotation.LastPrice >= v.LastQuotation.PreSettlePrice"><i class="ifont" :class="{red: v.LastQuotation.LastPrice > v.LastQuotation.PreSettlePrice, green: v.LastQuotation.LastPrice < v.LastQuotation.PreSettlePrice}">&#xe761;</i></span>
							<span class="ifont_arrow" v-show="v.LastQuotation.LastPrice < v.LastQuotation.PreSettlePrice"><i class="ifont" :class="{red: v.LastQuotation.LastPrice > v.LastQuotation.PreSettlePrice, green: v.LastQuotation.LastPrice < v.LastQuotation.PreSettlePrice}">&#xe76a;</i></span>
							<span class="name">{{v.CommodityName}}</span>
							<span class="code">{{v.CommodityNo + v.MainContract}}</span>
							<span class="price" :class="{red: v.LastQuotation.LastPrice > v.LastQuotation.PreSettlePrice, green: v.LastQuotation.LastPrice < v.LastQuotation.PreSettlePrice}">{{v.LastQuotation.LastPrice | fixNum(v.DotSize)}}</span>
							<span class="num_sm">{{v.LastQuotation.LastVolume}}</span>
							<span class="price" :class="{red: v.LastQuotation.BidPrice1 > v.LastQuotation.PreSettlePrice, green: v.LastQuotation.BidPrice1 < v.LastQuotation.PreSettlePrice}">{{v.LastQuotation.BidPrice1 | fixNum(v.DotSize)}}</span>
							<span class="price" :class="{red: v.LastQuotation.AskPrice1 > v.LastQuotation.PreSettlePrice, green: v.LastQuotation.AskPrice1 < v.LastQuotation.PreSettlePrice}">{{v.LastQuotation.AskPrice1 | fixNum(v.DotSize)}}</span>
							<span class="num_sm">{{v.LastQuotation.BidQty1}}</span>
							<span class="num_sm">{{v.LastQuotation.AskQty1}}</span>
							<span class="num_bg">{{v.LastQuotation.TotalVolume}}</span>
							<span class="num_bg" :class="{green: v.LastQuotation.ChangeRate < 0, red: v.LastQuotation.ChangeRate > 0}">{{v.LastQuotation.ChangeValue | fixNum(v.DotSize)}}</span>
							<span class="num_bg" :class="{green: v.LastQuotation.ChangeRate < 0, red: v.LastQuotation.ChangeRate > 0}">{{v.LastQuotation.ChangeRate | fixNumTwo}}%</span>
							<span class="num_bg">{{v.LastQuotation.Position}}</span>
							<span class="num_bg" :class="{red: v.LastQuotation.OpenPrice > v.LastQuotation.PreSettlePrice, green: v.LastQuotation.OpenPrice < v.LastQuotation.PreSettlePrice}">{{v.LastQuotation.OpenPrice | fixNum(v.DotSize)}}</span>
							<span class="num_bg" :class="{red: v.LastQuotation.HighPrice > v.LastQuotation.PreSettlePrice, green: v.LastQuotation.HighPrice < v.LastQuotation.PreSettlePrice}">{{v.LastQuotation.HighPrice | fixNum(v.DotSize)}}</span>
							<span class="num_bg" :class="{red: v.LastQuotation.LowPrice > v.LastQuotation.PreSettlePrice, green: v.LastQuotation.LowPrice < v.LastQuotation.PreSettlePrice}">{{v.LastQuotation.LowPrice | fixNum(v.DotSize)}}</span>
							<span class="num_bg">{{v.LastQuotation.PreSettlePrice}}</span>
						</li>
					</template>
				</ul>
			</div>
		</div>
		<div class="echarts_box">
			<div class="title">
				<span class="fl">{{orderName}}</span>
				<span class="fl">{{orderNum}}</span>
				<div class="add fr" :class="{current: !addStar}" @click="addOptional" @mouseover="overOptional" @mouseleave="leaveOptional">
					<i class="ifont fl" v-show="addStar">&#xe754;</i>
					<i class="ifont fl" v-show="!addStar">&#xe602;</i>
					<span class="fl">{{optional}}</span>
				</div>
			</div>
			<div id="echarts_f" v-show="showFens">
				<div id="fens" class="chart"></div>
				<div id="volume" class="chart"></div>
			</div>
			<h1>日K</h1>
			<div id="echarts_k" v-show="showKline">
				<div id="kliness" class="char"></div>
				<div id="kliness_volume" class="chart"></div>
			</div>
		</div>
		<warning ref="warning"></warning>
	</div>
</template>

<script>
	import warning from "./trade/warning.vue"
	import pro from '../assets/js/common.js'
	export default{
		name:'index',
		components : {warning},
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
			},
			warningShow(){
				return this.$store.state.isshow.warningShow;
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
			warningShow: function(n, o){
				if(n && n == true){
					this.$refs.warning.show = true;
				}else{
					this.$refs.warning.show = false;
				}
			},
			quoteInitStep: function(n, o){
				if(n && n == true){
					this.$store.state.market.currentdetail = this.Parameters[0];
					this.$store.state.market.currentTradeDetails = this.tradeParameters[0];
					sessionStorage.currentDetails = JSON.stringify(this.Parameters[0]);
					this.showFens = true;
					this.showKline = true;
					//分时、k线容器赋高度
					var h = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;
					h = h - 50 - 30 - 40 - 25;
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
					$(".body li").eq(n).addClass("red_bg");
					setTimeout(function(){
						$(".body li").eq(n).removeClass("red_bg");
					}, 500);
				}else{
					$(".body li").eq(n).addClass("green_bg");
					setTimeout(function(){
						$(".body li").eq(n).removeClass("green_bg");
					}, 500);
				}
			},
			isRefresh: function(n, o){
				if(n == 1){
					window.location.reload();
				}else if(n == ''){
					this.$store.state.account.isRefresh = '';
				}
			},
		},
		methods: {
			click: function(){
//				console.log(window.location.origin);
				window.open(window.location.origin + '/aaa.html');
			},
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
				this.$store.state.isshow.isfens = true;
				this.$store.state.isshow.iskline = true;
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
			overOptional: function(){
				if(this.addStar == true){
					this.addStar = false;
				}else{
					this.addStar = true;
				}
			},
			leaveOptional: function(){
				if(this.addStar == true){
					this.addStar = false;
				}else{
					this.addStar = true;
				}
			},
			addOptional: function(e){
				//获取平台账户登录信息
				this.userInfo = localStorage.user ? JSON.parse(localStorage.user) : '';
				if(this.userInfo != '' && this.userInfo != undefined){
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
						layer.confirm('您确定删除自选吗?', {
							btn: ['确定','取消']
						}, function(index){
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
							layer.close(index);
						}.bind(this));
					}
				}else{
					layer.msg('请先登录平台账号', {time: 1000});
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
			},
			getTradeWsUrl: function(){
				var data = {
					appVersions: this.$store.state.market.tradeConfig.version
				};
				pro.fetch('post', '/socket/config/getVersions', data, '').then(function(res){
					if(res.success == true && res.code == 1){
						this.$store.state.market.tradeConfig.url_real = res.data.socketUrl;
					}
				}.bind(this)).catch(function(err){
					var data = err.data;
					if(data) layer.msg(data.message, {time: 1000});
				});
			}
		},
		mounted: function(){
			//初始化页面高度
			var w = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth;
			var h = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;
			$(".order").height(h - 50 - 30);
			var body_height = h - 125;
			var body_width = w - 480;
			if(body_height < 748){
				$(".cont").css({
					'height': body_height,
					'overflow-y': 'scroll'
				});
			}
			if(body_width < 1290){
				$(".cont").css({
					'width': body_width,
					'overflow-x': 'scroll'
				});
			}
		},
		activated: function(){
			//获取平台账户登录信息
			this.userInfo = localStorage.user ? JSON.parse(localStorage.user) : '';
			//是否是自选合约
			this.isSelectedOrder();
			//获取交易ws地址
			this.getTradeWsUrl();
			$(window).resize(function(){
				var w = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth;
				var h = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;
				$(".order").height(h - 50 - 30);
				var body_height = h - 125;
				var body_width = w - 480;
				if(body_height < 748){
					$(".cont").css({
						'height': body_height,
						'overflow-y': 'scroll'
					});
				}
				if(body_width < 1290){
					$(".cont").css({
						'width': body_width,
						'overflow-x': 'scroll'
					});
				}
			});
			//默认画图
			this.$store.state.market.currentdetail = this.Parameters[0];
			this.$store.state.market.currentTradeDetails = this.tradeParameters[0];
			sessionStorage.currentDetails = JSON.stringify(this.Parameters[0]);
			this.showFens = true;
			this.showKline = true;
			//分时、k线容器赋高度
			var h = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;
			h = h - 50 - 30 - 40 - 25;
			$("#echarts_f, #echarts_k").height(h/2);
			$("#volume, #kliness_volume").height(h/2/10*3.7);
			$("#fens, #kliness").height(h/2/10*6);
			//开始画图
			this.$store.state.isshow.isfens = true;
			this.$store.state.isshow.iskline = true;
			if(this.Parameters[0] != undefined){
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
			}
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
		h1{
			font-size: $fs18;
			font-weight: bold;
			background: $blue;
			padding: 0 0 0 10px;
			color: $white;
		}
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
		#echarts_f, #echarts_k{
			background: $blue;
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
			ul{
				li{
					min-width: 1290px;
					overflow: hidden;
					background: $blue;
			        cursor: pointer;
			        &:nth-child(even){
			            background: $deepblue;
			        }
			        &:hover, &.current{
			            background: $highLight;
			        }
			        .red, .ifont.red{
				        color: $red !important;
				    }
				    .green, .ifont.green{
				        color: $green !important;
				    }
				    span{
				    	display: inline-block;
				    	float: left;
				    	&.ifont_arrow{
							width: 30px;
							text-align: center;
							.ifont{
								font-size: $fs12;
								color: $lightblue;
							}
						}
						&.name{
							width: 130px;
						}
						&.code, &.price, &.num_bg{
							width: 80px;
						}
						&.num_sm{
							width: 60px;
						}
				    }
				}
				&.head li{
					height: 30px;
					line-height: 30px;
					font-size: $fs12;
					background: $bottom_color;
					.ifont_arrow{
						opacity: 0;
					}
				}
				&.body{
					display: block;
					li{
						height: 44px;
						line-height: 44px;
						overflow: hidden;
						&.red_bg{
							background: #54323e;
						}
						&.green_bg{
							background: #2c4c4a;
						}
					}
				}
			}
		}
	}
	.chart{
		width: 100%;
		margin: 0 auto;
	}

</style>