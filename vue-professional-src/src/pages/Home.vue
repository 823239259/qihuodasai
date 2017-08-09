<template>
	<div id="home">
		<template>
			<tipsDialog :msg="msgTips"></tipsDialog>
			<div id="disconnect" v-show='!isconnected'>
				<div><s></s>&nbsp;&nbsp;行情连接已断开，<span>{{time}}</span>秒后自动重连</div>
			</div>
			<div>
				<topbar title="行情" :connected='!isconnected'></topbar>
				<button id="refresh" @tap="refresh"></button>
				<!--选择条-->

				<div id="selectbar">
					<ul>
						<li @tap="selectClass" class="fl fontsm current">国际期货</li>
						<!--<li @tap="selectClass" class="fl fontsm">亚洲指数</li>
						<li @tap="selectClass" class="fl fontsm">欧美指数</li>-->
					</ul>
				</div>
				<div id="datalist">
					<ul>
						<li>
							<ol>
								<li class="fl fontgray">合约名称</li>
								<li class="fl fontgray">成交量</li>
								<li class="fl fontgray">最新价</li>
								<li class="fl fontgray">涨跌幅 <i></i></li>
							</ol>
						</li>
					</ul>
					<ul>
						<template v-for="(v,i) in Parameters">
							<li class="list cl" @tap='toDetail'>
								<ol>
									<li class="fl">
										<h5 class="fontwhite">{{v.CommodityName}}</h5>
										<h5 class="fontgray">{{v.CommodityNo}}{{v.MainContract}}</h5>
									</li>
									<li class="fl">{{v.LastQuotation.TotalVolume}}</li>
									<li :class="['fl',{'fontgreen':v.LastQuotation.ChangeRate<0},{'fontred':v.LastQuotation.ChangeRate>0},{'fontwhite':v.LastQuotation.ChangeRate==0}]">{{v.LastQuotation.LastPrice | fixNum2(v.DotSize)}}</li>
									<li :class="['fl',{'fontgreen':v.LastQuotation.ChangeRate<0},{'fontred':v.LastQuotation.ChangeRate>0},{'fontwhite':v.LastQuotation.ChangeRate==0}]">{{v.LastQuotation.ChangeRate | fixNum}}%</li>
								</ol>
							</li>
						</template>
					</ul>
				</div>
			</div>
		</template>
			<guide v-if='guideshow'></guide>
	</div>

</template>

<script>
	//	import store from '../store'
	import tipsDialog from '../components/tipsDialog.vue'
	import topbar from '../components/Topbar.vue'
	import guide from './Guide.vue'
	
	import Vue from 'vue'
	import Vuex from 'vuex'
	import VueNativeSock from 'vue-native-websocket'
//	import QuoteMethod from '../assets/n_quote_vo'
//	import Quote from '../assets/QuoteUtils'
	import { mapMutations,mapActions } from 'vuex'
	
	
	export default {
		name: 'home',
		//		store,
		data() {
			return {
				time: 3,
				msg: '',
				isBack: ''
			}
		},
		filters:{
			fixNum:function(num){
				return num.toFixed(2);
			},
			fixNum2:function(num,dotsize){
				dotsize=dotsize;
				return num.toFixed(dotsize);
			}
		},
		components: {
			topbar,
			guide,
			tipsDialog
		},
		computed: {
			msgTips: function(){
				return this.msg;
			},
			tipMsg: function(){
				return this.$store.state.wsmsg;
			},
			Parameters(){
				return this.$store.state.market.Parameters
			},
			isconnected() {
				return this.$store.state.isshow.isconnected
			},
			guideshow(){
				return this.$store.state.isshow.guideshow
			}
		},
		watch: {
			isconnected: function(n, o) {
				var sw = window.screen.width;
				if(n == true) {
					if(sw <= 370) {
						$('#home').css('padding-top', 50 * 0.7729 + 'px');
						$('#selectbar').css('top', 50 * 0.7729 + 'px');
					} else if(sw <= 410) {
						$('#home').css('padding-top', 50 * 0.9058 + 'px');
						$('#selectbar').css('top', 50 * 0.9058 + 'px');
					} else {
						$('#home').css('padding-top', '50px');
						$('#selectbar').css('top', '50px');
					}
				} else {
					if(sw <= 370) {
						$('#home').css('padding-top', 50 * 0.7729 + 30 + 'px');
						$('#selectbar').css('top', 50 * 0.7729 + 30 + 'px');
					} else if(sw <= 410) {
						$('#home').css('padding-top', 50 * 0.9058 + 30 + 'px');
						$('#selectbar').css('top', 50 * 0.9058 + 30 + 'px');
					} else {
						$('#home').css('padding-top', '80px');
						$('#selectbar').css('top', '80px');
					}
				}
			}
		},
		methods: {
			...mapActions([
				'initQuoteClient'
			]),
			refresh: function(e) {
				this.$router.push({
					path: '/space'
				});
			},
			selectClass: function(e) {
				$(e.target).addClass('current').siblings('li').removeClass('current');
			},
			toDetail: function(a) {
				this.$children[0].isShow = true;
				this.msg = '此功能暂未上线';
//				this.Parameters.forEach(function(e){
//					if(e.CommodityName == $(a.currentTarget).children().find('h5:first-child').text()){
//						this.$store.state.market.currentdetail=e;
//					}
//				}.bind(this));
//				this.$router.push({path: '/orderdetail'});
			}
		},
		mounted: function() {
			this.initQuoteClient();
			var sw = window.screen.width;
			if(this.isconnected) {
				if(sw <= 370) {
					$('#home').css('padding-top', 50 * 0.7729 + 'px');
					$('#selectbar').css('top', 50 * 0.7729 + 'px');
				} else if(sw <= 410) {
					$('#home').css('padding-top', 50 * 0.9058 + 'px');
					$('#selectbar').css('top', 50 * 0.9058 + 'px');
				} else {
					$('#home').css('padding-top', '50px');
					$('#selectbar').css('top', '50px');
				}
			} else {
				if(sw <= 370) {
					$('#home').css('padding-top', 50 * 0.7729 + 30 + 'px');
					$('#selectbar').css('top', 50 * 0.7729 + 30 + 'px');
				} else if(sw <= 410) {
					$('#home').css('padding-top', 50 * 0.9058 + 30 + 'px');
					$('#selectbar').css('top', 50 * 0.9058 + 30 + 'px');
				} else {
					$('#home').css('padding-top', '80px');
					$('#selectbar').css('top', '80px');
				}

			}
		},
		activated:function(){
			this.$store.state.market.currentNo='';
			this.$store.state.isshow.isklineshow = false;
			//提示框
			this.$children[0].isShow = true;
			this.msg = this.tipMsg;
			
			this.isBack = this.$route.query.isBack;
			if(this.isBack && this.isBack == 1){
				console.log(123);
				window.location.reload();
//				this.$router.go(-1);
			}
		}
	}
</script>

<style scoped lang="less">
	@import url("../assets/css/main.less");
	@width: 100%;
	#home {
		background: #1b1b26;
	}	
	#disconnect {
		width: 100%;
		height: 30px;
		background-color: #fff7cc;
		line-height: 30px;
		font-size: 12px;
		color: #2a2a31;
		position: fixed;
		/*top: 50px;*/
	}
	
	#selectbar {
		width: @width;
		overflow-x: scroll;
		padding: 0 2%;
		line-height: 45px;
		overflow: hidden;
		background-color: #242633;
		border-bottom: 1px solid #1b1b26;
		height: 45px;
		position: fixed;
	}
	
	#selectbar ul {
		box-sizing: content-box;
		width: 150%;
	}
	
	#selectbar li {
		height: 45px;
		margin: 0 2%;
		color: #ccd5ff;
		text-align: center;
		border-bottom: 3px solid transparent;
		/*border-bottom: 3px solid #ffd400;*/
	}
	
	#selectbar>ul>li.current {
		color: #ffd400;
		border-bottom: 3px solid #ffd400;
	}
	
	#datalist>ul>li>ol>li {
		text-align: center;
		width: 21%;
		font-size: 14px;
	}
	
	#datalist {
		overflow: hidden;
		margin-top: 45px;
	}
	
	#datalist>ul>li>ol>li:first-child {
		width: 35%;
	}
	
	#datalist>ul:first-child {
		width: 100%;
		position: fixed;
	}
	
	#datalist>ul:nth-child(2) {
		margin-top: 40px;
		box-sizing: content-box;
		padding-bottom: 55px;
	}
	
	#datalist>ul:first-child>li:first-child {
		width: @width;
		line-height: 40px;
		background: #36394d;
	}
	
	#datalist>ul>li>ol {
		width: 100%;
		height: 40px;
	}
	
	i {
		display: inline-block;
		width: 9px;
		height: 12px;
		background-size: 100% 100%;
		background-image: url(../assets/img/updown.png);
	}
	
	s {
		width: 15px;
		height: 15px;
		background-image: url(../assets/img/tanhao.png);
		background-size: 100% 100%;
		background-repeat: no-repeat;
		display: inline-block;
		transform: translateY(3px);
		margin-left: (15/414)*414px;
	}
	
	.list {
		height: 55px;
		width: 100%;
		line-height: 55px;
		background-color: #242633;
		border-bottom: 1px solid #1b1b26;
	}
	
	.list>ol>li:nth-child(2) {
		color: #fff;
	}
	
	.list h5 {
		margin-top: 8%;
	}
	
	#refresh {
		width: 17px;
		height: 17px;
		background-color: transparent;
		border: none;
		outline: none;
		background-image: url('../assets/img/refresh.png');
		background-size: 100% 100%;
		position: fixed;
		z-index: 1000;
		right: 7%;
		top: 2%;
	}
	/*ip5*/
	
	@media(max-width:370px) {
		#home {
			background: #1b1b26;
			height: 736px*@ip5 - 20px;
		}
		#disconnect {
			top: 50px*@ip5
		}
		#refresh {
			width: 17px*@ip5;
			height: 17px*@ip5;
			background-color: transparent;
			border: none;
			outline: none;
			background-image: url('../assets/img/refresh.png');
			background-size: 100% 100%;
			position: fixed;
			z-index: 1000;
			right: 16px*@ip5;
			top: 16px*@ip5;
		}
		#datalist>ul>li:first-child {
			/*top: 80px*@ip5+45px;*/
		}
		#datalist>ul:nth-child(2) {
			margin-top: 50px*@ip5;
			box-sizing: content-box;
			padding-bottom: 58px*@ip5;
		}
		#home {
			padding-top: 50px*@ip5+30px;
			padding-bottom: 50px*@ip5;
		}
		#datalist>ul>li:nth-child(2) {
			/*margin-top: 85px;*/
		}
		#selectbar {
			top: 50px*@ip5+30px;
		}
	}
	/*ip6*/
	
	@media (min-width:371px) and (max-width:410px) {
		#home {
			background: #1b1b26;
			height: 736px*@ip6 - 20px;
		}
		#disconnect {
			top: 50px*@ip6
		}
		#refresh {
			width: 17px*@ip6;
			height: 17px*@ip6;
			background-color: transparent;
			border: none;
			outline: none;
			background-image: url('../assets/img/refresh.png');
			background-size: 100% 100%;
			position: fixed;
			z-index: 1000;
			right: 16px*@ip6;
			top: 16px*@ip6;
		}
		#datalist>ul>li:first-child {
			/*top: 80px*@ip6+45px;*/
		}
		#datalist>ul:nth-child(2) {
			margin-top: 42px*@ip6;
			box-sizing: content-box;
			padding-bottom: 55px*@ip6;
		}
		#home {
			padding-top: 50px*@ip6+30px;
			padding-bottom: 50px*@ip6;
		}
		#datalist>ul>li:nth-child(2) {
			/*margin-top: 85px;*/
		}
		#selectbar {
			top: 50px*@ip6+30px;
		}
	}
	/*ip6p及以上*/
	
	@media (min-width:411px) {
		#home {
			background: #1b1b26;
			height: 736px*@ip6p - 20px;
		}
		#disconnect {
			top: 50px*@ip6p
		}
		#refresh {
			width: 17px*@ip6p;
			height: 17px*@ip6p;
			background-color: transparent;
			border: none;
			outline: none;
			background-image: url('../assets/img/refresh.png');
			background-size: 100% 100%;
			position: fixed;
			z-index: 1000;
			right: 16px;
			top: 16px;
		}
		#datalist>ul>li:first-child {
			/*top: 80px*@ip6p+45px;*/
		}
		#home {
			padding-top: 50px*@ip6p+30px;
			padding-bottom: 50px*@ip6p;
		}
		#datalist>ul>li:nth-child(2) {
			/*margin-top: 85px;*/
		}
		#selectbar {
			top: 50px*@ip6p+30px;
		}
	}
</style>