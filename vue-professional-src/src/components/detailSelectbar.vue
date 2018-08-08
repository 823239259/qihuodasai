<template>
	<div id="detailselectbar">
		<alert title="提示" line1="你还未登录，请先登录" jump="true" ref="alert"></alert>
		<ul>
			<!-- <li :class="[fontgray,fl,{current:sshow}]" @tap='switchTab'>闪电图</li>
			<li :class="[fontgray,fl,{current:fshow}]" @tap='switchTab'>分时</li>
			<li :class="[fontgray,fl,{current:kshow}]" @tap='switchTab' id="kline">
				<span>K线</span>
				<transition name="topdown" mode="out-in">
					<ol v-show='olshow'>
						<li class="fontwhite  fl" @tap.stop="olclick">1分</li>
						<li class="fontwhite  fl" @tap.stop="olclick">5分</li>
						<li class="fontwhite  fl" @tap.stop="olclick">15分</li>
						<li class="fontwhite  fl" @tap.stop="olclick">30分</li>
						<li class="fontwhite  fl" @tap.stop="olclick">日K</li>
					</ol>
				</transition>
			</li>
			<li :class="[fontgray,fl,{current:pshow}]" @tap='switchTab'>盘口</li>
			<li :class="[fontgray,fl,{current:jshow}]" @tap.stop='switchTab'>交易中心</li> -->

			<li :class="[fontgray,fl,{current:currentIndex==index}]" @tap='switchTab(item,index)' v-for="(item, index) in tabList" :key="index">
				<template v-if="item==='K线'">
					<span>K线</span>
					<transition name="topdown" mode="out-in">
						<ol v-show='olshow'>
							<li class="fontwhite  fl" v-for="(kType, index) in kTypeList" :key="index" @tap.stop="olclick">{{kType}}</li>
						</ol>
					</transition>
				</template>
				<template v-else>
					{{item}}
				</template>
			</li>
		</ul>
	</div>
</template>

<script>
	var yellow = require('../assets/img/sanjiao01.png');
	var blue = require('../assets/img/sanjiao02.png');
	import alert from './Tradealert.vue'
	export default {
		name: 'detailselectbar',
		components: {alert},
		data() {
			return {
				olshow: false,
				tabList: ['闪电图', '分时', 'K线', '盘口', '交易中心'],
				kTypeList: ['1分', '5分', '15分', '30分', '日K'],
				currentIndex: 1,
			}
		},
		computed: {
//			tradeConfig(){
//				return this.$store.state.market.tradeConfig;
//			},
			detail() {
				return this.$store.state.market.currentdetail;
			},
		},
		watch: {
			kshow: function(n, o) {
				if(n == true) {
					$('#kline').css('background-image', 'url(' + yellow + ')');
				} else {
					$('#kline').css('background-image', 'url(' + blue + ')');
				}
			}
		},
		methods: {
			olclick: function(e) {
				var oltex = e.target.innerText;
				$(e.target).parent().parent('li').children('span').text(oltex);
				if(oltex == '1分') {
					this.$store.state.market.selectTime=1;
					var b = '{"Method":"QryHistory","Parameters":{"ExchangeNo":"' + this.$parent.detail.LastQuotation.ExchangeNo + '","commodity_no":"' + this.$parent.detail.commodity_no + '","ContractNo":"' + this.$parent.detail.LastQuotation.ContractNo + '","HisQuoteType":' + 1 + ',"BeginTime":"","EndTime":"","Count":' + 0 + '}}'
					this.quoteSocket.send(b);
				} else if(oltex == '5分') {
					this.$store.state.market.selectTime=5;
					var b = '{"Method":"QryHistory","Parameters":{"ExchangeNo":"' + this.$parent.detail.LastQuotation.ExchangeNo + '","commodity_no":"' + this.$parent.detail.commodity_no + '","ContractNo":"' + this.$parent.detail.LastQuotation.ContractNo + '","HisQuoteType":' + 5 + ',"BeginTime":"","EndTime":"","Count":' + 0 + '}}'
					this.quoteSocket.send(b);
				} else if(oltex == '15分') {
					this.$store.state.market.selectTime=15;
					var b = '{"Method":"QryHistory","Parameters":{"ExchangeNo":"' + this.$parent.detail.LastQuotation.ExchangeNo + '","commodity_no":"' + this.$parent.detail.commodity_no + '","ContractNo":"' + this.$parent.detail.LastQuotation.ContractNo + '","HisQuoteType":' + 15 + ',"BeginTime":"","EndTime":"","Count":' + 0 + '}}'
					this.quoteSocket.send(b);
				} else if(oltex == '30分') {
					this.$store.state.market.selectTime=30;
					var b = '{"Method":"QryHistory","Parameters":{"ExchangeNo":"' + this.$parent.detail.LastQuotation.ExchangeNo + '","commodity_no":"' + this.$parent.detail.commodity_no + '","ContractNo":"' + this.$parent.detail.LastQuotation.ContractNo + '","HisQuoteType":' + 30 + ',"BeginTime":"","EndTime":"","Count":' + 0 + '}}'
					this.quoteSocket.send(b);
				} else if(oltex == '日K') {
					this.$store.state.market.selectTime=1440;
					var b = '{"Method":"QryHistory","Parameters":{"ExchangeNo":"' + this.$parent.detail.LastQuotation.ExchangeNo + '","commodity_no":"' + this.$parent.detail.commodity_no + '","ContractNo":"' + this.$parent.detail.LastQuotation.ContractNo + '","HisQuoteType":' + 1440 + ',"BeginTime":"","EndTime":"","Count":' + 0 + '}}'
					this.quoteSocket.send(b);
				}
				this.olshow = false;
//				isshow.isklineshow = true;
			},
			switchTab (item,index) {

				let isshow = this.$store.state.isshow

				this.currentIndex = index

				switch(item) {
					case '闪电图':
						this.$parent.cname = this.$parent.detail.commodity_name;
						this.$parent.cnum = this.$parent.detail.commodity_no + this.$parent.detail.mainContract;
						isshow.sshow = true;
						isshow.fshow = false;
						isshow.kshow = false;
						isshow.pshow = false;
						isshow.bottomshow = false;
						this.olshow = false;
						isshow.isfensshow = false;
						isshow.isklineshow = false;
						break;
					case '分时':
						this.$parent.cname = this.$parent.detail.commodity_name;
						this.$parent.cnum = this.$parent.detail.commodity_no + this.$parent.detail.mainContract;
						isshow.sshow = false;
						isshow.fshow = true;
						isshow.kshow = false;
						isshow.pshow = false;
						isshow.bottomshow = false;
						this.olshow = false;
						isshow.islightshow = false;
						isshow.isklineshow = false;
						//isshow.isfensshow = true;
						break;
					case '盘口':
						this.$parent.cname = this.$parent.detail.commodity_name;
						this.$parent.cnum = this.$parent.detail.commodity_no + this.$parent.detail.mainContract;
						isshow.sshow = false;
						isshow.fshow = false;
						isshow.kshow = false;
						isshow.pshow = true;
						isshow.bottomshow = false;
						this.olshow = false;
						isshow.isfensshow = false;
						isshow.islightshow = false;
						isshow.isklineshow = false;
						break;
					case '交易中心':
//						var tradeConfig =this.$store.state.market.tradeConfig;
						if(JSON.parse(localStorage.getItem('tradeUser')) == null){
							this.$children[0].isshow = true;
						}else{
							this.$parent.cname = '期货模拟账号';
							this.$parent.cnum = JSON.parse(localStorage.tradeUser).username;
							isshow.sshow = false;
							isshow.fshow = false;
							isshow.kshow = false;
							isshow.pshow = false;
							isshow.bottomshow = true;
							this.olshow = false;
							isshow.isfensshow = false;
							isshow.islightshow = false;
							isshow.isklineshow = false;
							
						}
						break;
					case 'K线':
						this.$parent.cname = this.$parent.detail.commodity_name;
						this.$parent.cnum = this.$parent.detail.commodity_no + this.$parent.detail.mainContract;
						isshow.sshow = false;
						isshow.fshow = false;
						isshow.kshow = true;
						isshow.pshow = false;
						isshow.bottomshow = false;
						isshow.isfensshow = false;
						isshow.islightshow = false;
						if(this.olshow == false) {
							this.olshow = true;
						} else {
							this.olshow = false;
						}
						//默认一分钟K线
						this.$store.state.market.selectTime=1;
						var subscribeMessage = {
							method: 'req_history_data',
							data: {
								contract_info: {
									security_type: this.$parent.detail.security_type,
									exchange_no: this.$parent.detail.exchange_no,
									commodity_no: this.$parent.detail.commodity_no,
									contract_no: this.$parent.detail.mainContract,
								},
								period: 'KLINE_1MIN',
								count:40

							}
						}
						//var b = '{"":""req_history_data"","Parameters":{"ExchangeNo":"' + this.$parent.detail.LastQuotation.ExchangeNo + '","commodity_no":"' + this.$parent.detail.commodity_no + '","ContractNo":"' + this.$parent.detail.LastQuotation.ContractNo + '","HisQuoteType":' + 1 + ',"BeginTime":"","EndTime":"","Count":' + 0 + '}}'
						this.quoteSocket.send(JSON.stringify(subscribeMessage));
						break;
					default:
						isshow.sshow = false;
						isshow.fshow = false;
						isshow.kshow = true;
						isshow.pshow = false;
						isshow.bottomshow = false;
						isshow.isfensshow = false;
						isshow.islightshow = false;
						if(this.olshow == false) {
							this.olshow = true;
						} else {
							this.olshow = false;
						}
						break;
				}
			}
		},
		computed: {
			quoteSocket() {
				return this.$store.state.quoteSocket
			},
			fontwhite() {
				return 'fontwhite';
			},
			fl() {
				return 'fl';
			},
			fontgray() {
				return 'fontgray';
			},
			jshow() {
				return this.$store.state.isshow.bottomshow
			},
			pshow() {
				return this.$store.state.isshow.pshow
			},
			sshow() {
				return this.$store.state.isshow.sshow
			},
			fshow() {
				return this.$store.state.isshow.fshow
			},
			kshow() {
				return this.$store.state.isshow.kshow
			}
		},
		mounted: function() {
			this.$store.state.isshow.bottomshow = false
		}
	}
</script>

<style scoped lang="less">
	@import url("../assets/css/main.less");
	@height: 40px;
	#detailselectbar {
		position: fixed;
		top: 50px;
		left: 0;
		z-index: 99;
	}
	
	#detailselectbar>ul>li.current {
		color: #ffd400;
		border-bottom: 3px solid #FFD400;
	}
	
	li {
		font-size: 16px;
	}
	
	#detailselectbar {
		width: 100%;
		height: @height;
	}
	
	#detailselectbar>ul {
		width: 100%;
		height: 43px;
		border-top: 1px solid #1b1b26;
		border-bottom: 1px solid #1b1b26;
		position: relative;
	}
	
	#detailselectbar>ul>li {
		width: 20%;
		height: @height;
		line-height: @height;
		text-align: center;
		background-color: #242633;
		border-bottom: 3px solid transparent;
		z-index: 6;
	}
	
	#detailselectbar>ul>li>ol {
		width: 414px;
		height: @height;
		position: absolute;
		z-index: 5;
		left: 0;
		top: @height;
		background: #2d3040;
		border-bottom: 1px solid #1b1b26;
	}
	
	#detailselectbar>ul>li:nth-child(3) {
		background-image: url('../assets/img/sanjiao02.png');
		background-repeat: no-repeat;
		background-size: 5px;
		background-position: 60px 23px;
	}
	
	#detailselectbar>ul>li>ol>li {
		width: 20%;
		text-align: center;
	}
	
	@media(max-width:370px) {
		#detailselectbar {
			position: fixed;
			top: 50px*@ip5;
			left: 0;
			z-index: 99;
		}
		#detailselectbar>ul>li.current {
			color: #ffd400;
			border-bottom: 3px solid #FFD400;
		}
		li {
			font-size: 16px*@ip5;
		}
		#detailselectbar {
			width: 100%;
			height: @height*@ip5;
		}
		#detailselectbar>ul {
			width: 100%;
			height: 43px*@ip5;
			border-top: 1px solid #1b1b26;
			border-bottom: 1px solid #1b1b26;
			position: relative;
		}
		#detailselectbar>ul>li {
			width: 20%;
			height: @height*@ip5;
			line-height: @height*@ip5;
			text-align: center;
			background-color: #242633;
			border-bottom: 3px solid transparent;
			z-index: 6;
		}
		#detailselectbar>ul>li>ol {
			width: 414px*@ip5;
			height: @height*@ip5;
			position: absolute;
			z-index: 5;
			left: 0;
			top: @height*@ip5;
			background: #2d3040;
			border-bottom: 1px solid #1b1b26;
		}
		#detailselectbar>ul>li:nth-child(3) {
			background-image: url('../assets/img/sanjiao02.png');
			background-repeat: no-repeat;
			background-size: 5px*@ip5;
			background-position: 60px*@ip5 23px*@ip5;
		}
		#detailselectbar>ul>li>ol>li {
			width: 20%;
			text-align: center;
		}
	}
	/*ip6*/
	
	@media (min-width:371px) and (max-width:410px) {
		#detailselectbar {
			position: fixed;
			top: 50px*@ip6;
			left: 0;
			z-index: 99;
		}
		#detailselectbar>ul>li.current {
			color: #ffd400;
			border-bottom: 3px solid #FFD400;
		}
		li {
			font-size: 16px*@ip6;
		}
		#detailselectbar {
			width: 100%;
			height: @height*@ip6;
		}
		#detailselectbar>ul {
			width: 100%;
			height: 43px*@ip6;
			border-top: 1px solid #1b1b26;
			border-bottom: 1px solid #1b1b26;
			position: relative;
		}
		#detailselectbar>ul>li {
			width: 20%;
			height: @height*@ip6;
			line-height: @height*@ip6;
			text-align: center;
			background-color: #242633;
			border-bottom: 3px solid transparent;
			z-index: 6;
		}
		#detailselectbar>ul>li>ol {
			width: 414px*@ip6;
			height: @height*@ip6;
			position: absolute;
			z-index: 5;
			left: 0;
			top: @height*@ip6;
			background: #2d3040;
			border-bottom: 1px solid #1b1b26;
		}
		#detailselectbar>ul>li:nth-child(3) {
			background-image: url('../assets/img/sanjiao02.png');
			background-repeat: no-repeat;
			background-size: 5px*@ip6;
			background-position: 60px*@ip6 23px*@ip6;
		}
		#detailselectbar>ul>li>ol>li {
			width: 20%;
			text-align: center;
		}
	}
</style>