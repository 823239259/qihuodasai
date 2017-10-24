<template>
	<div id="trade">
		<tradeLogin ref="tradeLogin"></tradeLogin>
		<tradeLoginSpe ref="tradeLoginSpe"></tradeLoginSpe>
		<div class="quote">
			<div class="title">
				<ul>
					<template v-for="(key, index) in quoteTab">
						<li :class="{current: quoteDefault == index}" @click="quoteTabEvent(index)"><span>{{key}}</span></li>
					</template>
				</ul>
			</div>
			<div class="cont" v-if="quoteShow">
				<table>
					<thead>
						<tr>
							<td>合约代码</td>
							<td>最新/涨幅%</td>
							<td>成交量</td>
						</tr>
					</thead>
					<tbody>
						<template v-for="(v, index) in selectedList">
							<tr :class="{current: currentQuote == index}" @click="listClickEvent(index, v.CommodityNo, v.MainContract, v.ExchangeNo)">
								<td>
									<b>{{v.CommodityName}}</b>
									<span>{{v.CommodityNo + v.MainContract}}</span>
								</td>
								<td>
									<div class="fl">
										<span :class="{red: v.LastQuotation.LastPrice > v.LastQuotation.PreSettlePrice, green: v.LastQuotation.LastPrice < v.LastQuotation.PreSettlePrice}">{{v.LastQuotation.LastPrice}}</span>
										<span :class="{green: v.LastQuotation.ChangeRate < 0, red: v.LastQuotation.ChangeRate > 0}">{{v.LastQuotation.ChangeRate | fixNumTwo}}%</span>
									</div>
									<i class="ifont" v-show="v.LastQuotation.LastPrice >= v.LastQuotation.PreSettlePrice" :class="{red: v.LastQuotation.LastPrice > v.LastQuotation.PreSettlePrice, green: v.LastQuotation.LastPrice < v.LastQuotation.PreSettlePrice}">&#xe761;</i>
									<i class="ifont" v-show="v.LastQuotation.LastPrice < v.LastQuotation.PreSettlePrice" :class="{red: v.LastQuotation.LastPrice > v.LastQuotation.PreSettlePrice, green: v.LastQuotation.LastPrice < v.LastQuotation.PreSettlePrice}">&#xe76a;</i>
								</td>
								<td>{{v.LastQuotation.TotalVolume}}</td>
							</tr>
						</template>
					</tbody>
				</table>
			</div>
			<div class="cont" v-if="!quoteShow">
				<table>
					<thead>
						<tr>
							<td>合约代码</td>
							<td>最新/涨幅%</td>
							<td>成交量</td>
						</tr>
					</thead>
					<tbody>
						<template v-for="(v, index) in Parameters">
							<tr :class="{current: currentQuoteAll == index}" @click="listClickEvent(index, v.CommodityNo, v.MainContract, v.ExchangeNo)">
								<td>
									<b>{{v.CommodityName}}</b>
									<span>{{v.CommodityNo + v.MainContract}}</span>
								</td>
								<td>
									<div class="fl">
										<span :class="{red: v.LastQuotation.LastPrice > v.LastQuotation.PreSettlePrice, green: v.LastQuotation.LastPrice < v.LastQuotation.PreSettlePrice}">{{v.LastQuotation.LastPrice}}</span>
										<span :class="{green: v.LastQuotation.ChangeRate < 0, red: v.LastQuotation.ChangeRate > 0}">{{v.LastQuotation.ChangeRate | fixNumTwo}}%</span>
									</div>
									<i class="ifont" v-show="v.LastQuotation.LastPrice >= v.LastQuotation.PreSettlePrice" :class="{red: v.LastQuotation.LastPrice > v.LastQuotation.PreSettlePrice, green: v.LastQuotation.LastPrice < v.LastQuotation.PreSettlePrice}">&#xe761;</i>
									<i class="ifont" v-show="v.LastQuotation.LastPrice < v.LastQuotation.PreSettlePrice" :class="{red: v.LastQuotation.LastPrice > v.LastQuotation.PreSettlePrice, green: v.LastQuotation.LastPrice < v.LastQuotation.PreSettlePrice}">&#xe76a;</i>
								</td>
								<td>{{v.LastQuotation.TotalVolume}}</td>
							</tr>
						</template>
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
					<div class="cont" v-if="chartShow">
						<component :is="selectView"></component>
					</div>
				</div>
				<div class="info">
					<div class="order_details">
						<div class="title">
							<span class="fl">{{currentdetail.CommodityName}}</span>
							<span class="fl">{{currentdetail.CommodityNo + currentdetail.MainContract}}</span>
							<div class="add fr" :class="{current: !addStar}" @click="addOptional">
								<i class="ifont fl" v-show="addStar">&#xe754;</i>
								<i class="ifont fl" v-show="!addStar">&#xe602;</i>
								<span class="fl">{{optional}}</span>
							</div>
						</div>
						<p>
							<span :class="{red: currentdetail.LastQuotation.LastPrice > currentdetail.LastQuotation.PreSettlePrice, green: currentdetail.LastQuotation.LastPrice < currentdetail.LastQuotation.PreSettlePrice}">{{currentdetail.LastQuotation.LastPrice}}</span>
							<span :class="{green: currentdetail.LastQuotation.ChangeRate < 0, red: currentdetail.LastQuotation.ChangeRate > 0}">{{currentdetail.LastQuotation.ChangeValue | fixNum(currentdetail.DotSize)}}/{{currentdetail.LastQuotation.ChangeRate | fixNumTwo}}%</span>
						</p>
						<ul>
							<li>
								<div class="col">
									<b>现手：</b>
									<span>{{currentdetail.LastQuotation.LastVolume}}</span>
								</div>
								<div class="col">
									<b>成交量：</b>
									<span>{{currentdetail.LastQuotation.TotalVolume}}</span>
								</div>
								<div class="col">
									<b>持仓量：</b>
									<span>{{currentdetail.LastQuotation.Position}}</span>
								</div>
							</li>
							<li>
								<div class="col">
									<b>开盘：</b>
									<span :class="{red: currentdetail.LastQuotation.OpenPrice > currentdetail.LastQuotation.PreSettlePrice, green: currentdetail.LastQuotation.OpenPrice < currentdetail.LastQuotation.PreSettlePrice}">{{currentdetail.LastQuotation.OpenPrice}}</span>
								</div>
								<div class="col">
									<b>最高：</b>
									<span :class="{red: currentdetail.LastQuotation.HighPrice > currentdetail.LastQuotation.PreSettlePrice, green: currentdetail.LastQuotation.HighPrice < currentdetail.LastQuotation.PreSettlePrice}">{{currentdetail.LastQuotation.HighPrice}}</span>
								</div>
								<div class="col">
									<b>最低：</b>
									<span :class="{red: currentdetail.LastQuotation.LowPrice > currentdetail.LastQuotation.PreSettlePrice, green: currentdetail.LastQuotation.LowPrice < currentdetail.LastQuotation.PreSettlePrice}">{{currentdetail.LastQuotation.LowPrice}}</span>
								</div>
							</li>
							<li>
								<div class="col">
									<b>昨结：</b>
									<span>{{currentdetail.LastQuotation.PreSettlePrice}}</span>
								</div>
								<div class="col">
									<b>涨停：</b>
									<span>{{currentdetail.LastQuotation.LimitUpPrice}}</span>
								</div>
								<div class="col">
									<b>跌停：</b>
									<span>{{currentdetail.LastQuotation.LimitDownPrice}}</span>
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
									<span :class="{red: currentdetail.LastQuotation.AskPrice5 > currentdetail.LastQuotation.PreSettlePrice, green: currentdetail.LastQuotation.AskPrice5 < currentdetail.LastQuotation.PreSettlePrice}">{{currentdetail.LastQuotation.AskPrice5 | fixNum(dotSize)}}</span>
									<span>{{currentdetail.LastQuotation.AskQty5}}</span>
								</li>
								<li>
									<span>卖四</span>
									<span :class="{red: currentdetail.LastQuotation.AskPrice4 > currentdetail.LastQuotation.PreSettlePrice, green: currentdetail.LastQuotation.AskPrice4 < currentdetail.LastQuotation.PreSettlePrice}">{{currentdetail.LastQuotation.AskPrice4 | fixNum(dotSize)}}</span>
									<span>{{currentdetail.LastQuotation.AskQty4}}</span>
								</li>
								<li>
									<span>卖三</span>
									<span :class="{red: currentdetail.LastQuotation.AskPrice3 > currentdetail.LastQuotation.PreSettlePrice, green: currentdetail.LastQuotation.AskPrice3 < currentdetail.LastQuotation.PreSettlePrice}">{{currentdetail.LastQuotation.AskPrice3 | fixNum(dotSize)}}</span>
									<span>{{currentdetail.LastQuotation.AskQty3}}</span>
								</li>
								<li>
									<span>卖二</span>
									<span :class="{red: currentdetail.LastQuotation.AskPrice2 > currentdetail.LastQuotation.PreSettlePrice, green: currentdetail.LastQuotation.AskPrice2 < currentdetail.LastQuotation.PreSettlePrice}">{{currentdetail.LastQuotation.AskPrice2 | fixNum(dotSize)}}</span>
									<span>{{currentdetail.LastQuotation.AskQty2}}</span>
								</li>
								<li>
									<span>卖一</span>
									<span :class="{red: currentdetail.LastQuotation.AskPrice1 > currentdetail.LastQuotation.PreSettlePrice, green: currentdetail.LastQuotation.AskPrice1 < currentdetail.LastQuotation.PreSettlePrice}">{{currentdetail.LastQuotation.AskPrice1 | fixNum(dotSize)}}</span>
									<span>{{currentdetail.LastQuotation.AskQty1}}</span>
								</li>
								<li>
									<span>买一</span>
									<span :class="{red: currentdetail.LastQuotation.BidPrice1 > currentdetail.LastQuotation.PreSettlePrice, green: currentdetail.LastQuotation.BidPrice1 < currentdetail.LastQuotation.PreSettlePrice}">{{currentdetail.LastQuotation.BidPrice1 | fixNum(dotSize)}}</span>
									<span>{{currentdetail.LastQuotation.BidQty1}}</span>
								</li>
								<li>
									<span>买二</span>
									<span :class="{red: currentdetail.LastQuotation.BidPrice2 > currentdetail.LastQuotation.PreSettlePrice, green: currentdetail.LastQuotation.BidPrice2 < currentdetail.LastQuotation.PreSettlePrice}">{{currentdetail.LastQuotation.BidPrice2 | fixNum(dotSize)}}</span>
									<span>{{currentdetail.LastQuotation.BidQty2}}</span>
								</li>
								<li>
									<span>买三</span>
									<span :class="{red: currentdetail.LastQuotation.BidPrice3 > currentdetail.LastQuotation.PreSettlePrice, green: currentdetail.LastQuotation.BidPrice3 < currentdetail.LastQuotation.PreSettlePrice}">{{currentdetail.LastQuotation.BidPrice3 | fixNum(dotSize)}}</span>
									<span>{{currentdetail.LastQuotation.BidQty3}}</span>
								</li>
								<li>
									<span>买四</span>
									<span :class="{red: currentdetail.LastQuotation.BidPrice4 > currentdetail.LastQuotation.PreSettlePrice, green: currentdetail.LastQuotation.BidPrice4 < currentdetail.LastQuotation.PreSettlePrice}">{{currentdetail.LastQuotation.BidPrice4 | fixNum(dotSize)}}</span>
									<span>{{currentdetail.LastQuotation.BidQty4}}</span>
								</li>
								<li>
									<span>买五</span>
									<span :class="{red: currentdetail.LastQuotation.BidPrice5 > currentdetail.LastQuotation.PreSettlePrice, green: currentdetail.LastQuotation.BidPrice5 < currentdetail.LastQuotation.PreSettlePrice}">{{currentdetail.LastQuotation.BidPrice5 | fixNum(dotSize)}}</span>
									<span>{{currentdetail.LastQuotation.BidQty5}}</span>
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
								<template v-for="v in currentTradeDetails.data">
									<li>
										<span>{{v.time}}</span>
										<span :class="{red: v.price > v._price, green: v.price < v._price}">{{v.price}}</span>
										<span>{{v.volume}}</span>
									</li>
								</template>
							</ul>
						</div>
					</div>
					<div class="trade_login" v-if="tradeLoginShow">
						<button class="btn yellow" @click="toOpenAccount">我要开户</button>
						<button class="btn blue" @click="toTradeLogin">交易登录</button>
					</div>
				</div>
			</div>
			<div class="trade_box" v-if="tradeDetailsShow">
				<div class="operate">
					<div class="head">
						<span class="fl">交易账号：{{tradeUser}}</span>
						<i class="sanjiao fl"></i>
						<a href="javascript:void(0);" class="fl" @click="exitEvent">退出</a>
						<button class="btn blue fl">终结方案</button>
						<button class="btn yellow fl">追加保证金</button>
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
			</div>
		</div>
	</div>
</template>

<script>
	import { mapMutations,mapActions } from 'vuex'
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
				quoteTab: ['自选','行情'],
				quoteDefault: 0,
				quoteShow: true,
				currentQuote: 0,
				currentQuoteAll: '',
				optional: '添加自选',
				addStar: true,
				tradeLoginShow: true,
				tradeDetailsShow: false,
				chartShow: false,
				chartHeight: '',
				tradeUser: '',
				selectedList: [],
				selectedData: '',
			}
		},
		computed: {
			quoteInitStep(){
				return this.$store.state.market.quoteInitStep;
			},
			Parameters(){
				return this.$store.state.market.Parameters;
			},
			tradeParameters(){
				return this.$store.state.market.tradeParameters;
			},
			currentdetail(){
				return this.$store.state.market.currentdetail;
			},
			currentTradeDetails(){
				return this.$store.state.market.currentTradeDetails;
			},
			quoteSocket(){
				return this.$store.state.quoteSocket;
			},
			dotSize(){
				return this.$store.state.market.currentdetail.DotSize;
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
					this.$store.state.market.currentNo = this.Parameters[0].CommodityNo;
				}
			}
		},
		methods: {
			...mapActions([
				'initQuoteClient'
			]),
			quoteTabEvent: function(index){
				this.quoteDefault = index;
				if(index == 1){
					this.quoteShow = false;
				}else{
					this.quoteShow = true;
				}
			},
			addOptional: function(e){
				if(this.addStar == true){
					this.addStar = false;
					this.optional = '取消自选';
				}else{
					this.addStar = true;
					this.optional = '添加自选';
				}
			},
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
			listClickEvent: function(i, commodityNo, mainContract, exchangeNo){
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
				if(this.quoteShow == true){
					this.currentQuote = i;
					this.currentQuoteAll = -1;
				}else{
					this.currentQuoteAll = i;
					this.currentQuote = -1;
				}
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
							this.selectedData = res.data;
							this.selectedList = [];
							res.data.forEach(function(o, i){
								if(o.commodityCode === this.currentdetail.CommodityNo){
									this.addStar = false;
									this.optional = '取消自选';
									this.orderId = o.commodityCode;
									this.currentQuote = i;
								}
								this.Parameters.forEach(function(v, k){
									if(o.commodityCode == v.CommodityNo){
										this.selectedList.push(v);
									}
								}.bind(this));
							}.bind(this));
						}
					}
				}.bind(this)).catch(function(err){
					var data = err.data;
					layer.msg(data.message, {time: 1000});
				});
			},
			toOpenAccount: function(){
				this.$router.push({path: '/openAccount'});
				this.$store.state.isshow.isfensshow = false;
				this.$store.state.isshow.isklineshow = false;
			},
			toTradeLogin: function(){
				this.$refs.tradeLogin.show = true;
//				this.tradeLoginSpeShow = true;
			},
			exitEvent: function(){
				this.$router.push({path: '/index'});
				this.$store.state.account.isRefresh = 1;
				this.tradeDetailsShow = false;
				this.tradeLoginShow = true;
				localStorage.tradeUser =  '';
				this.$store.state.account.username = '';
				this.$store.state.account.password = '';
				var h = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;
				this.chartHeight = h - 50 -30 - 40;
			}
		},
		mounted: function(){
			//初始化高度
			var h = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;
			$(".quote .cont").height(h - 50 - 30 - 45);
			this.chartHeight = h - 50 -30 - 40;
			//开始画图
			this.chartShow = true;
			//调用下拉框
			$(".slt-box").each(function(i, o){
				pro.selectEvent(o);
			});
			//判断是否登录账户
			var user = localStorage.tradeUser ? JSON.parse(localStorage.tradeUser) : '';
			if(user){
				this.tradeLoginShow = false;
				this.tradeDetailsShow = true;
				this.chartHeight = h - 50 -30 - 40 - 280;
				this.tradeUser = user.username;
			}
			//获取平台账户登录信息
			this.userInfo = localStorage.user ? JSON.parse(localStorage.user) : '';
			//获取自选合约列表
			this.isSelectedOrder();
		},
		activated: function(){
			//获取自选合约列表
			this.isSelectedOrder();
		}
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
			overflow-y: auto;
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
						height: 48px;
						td{
							&:first-child{
								span{
									font-size: $fs12;
								}
							}
						}
						b, span{
							display: block;
							margin-top: 4px;
						}
						b{
							font-size: $fs12;
							color: $white;
						}
						.ifont{
							float: left;
							margin: 11px 0 0 5px;
							color: $lightblue;
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
				p{
					height: 40px;
					line-height: 40px;
					background: $deepblue;
					padding: 0 10px;
					color: $lightblue;
					span{
						&:first-child{
							font-size: $fs20;
							margin-right: 30px;
						}
						&.red{
							color: $red;
						}
						&.green{
							color: $green;
						}
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
							color: $white;
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
						height: 40px;
						line-height: 40px;
						font-size: $fs12;
					}
					ul{
						height: 352px;
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
						height: 35px;
						line-height: 35px;
						padding: 0 10px;
						span{
							display: inline-block;
							margin-right: 20px;
							&:nth-child(2){
								width: 45px;
							}
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
							height: 35px;
							line-height: 35px;
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
					float: right;
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