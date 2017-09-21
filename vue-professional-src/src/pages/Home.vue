<template>
	<div id="home">
		<template>
			<tipsDialog :msg="msgTips"></tipsDialog>
			<div id="disconnect" v-show='!isconnected'>
				<div><s></s>&nbsp;&nbsp;行情连接已断开，<span>{{time}}</span>秒后自动重连</div>
			</div>
			<div>
				<topbar title="行情" :connected='!isconnected'></topbar>
				<button class="help" @tap="toHelp"></button>
				<button id="refresh" :class="{dynamic: isdynamic == true, static: isdynamic == false}" @tap="refresh"></button>
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
								<li class="fl fontgray" @tap="switchEvent">{{title}}<i class="icon_sj"></i></li>
							</ol>
						</li>
					</ul>
					<ul class="cont">
						<template v-for="(v,i) in Parameters">
							<li class="list cl" @tap='toDetail'>
								<ol>
									<li class="fl">
										<h5 class="fontwhite">{{v.CommodityName}}</h5>
										<h5 class="fontgray">{{v.CommodityNo}}{{v.MainContract}}</h5>
									</li>
									<li class="fl">{{v.LastQuotation.TotalVolume}}</li>
									<li class="fl" :class="{fontgreen: v.LastQuotation.ChangeRate < 0, fontred: v.LastQuotation.ChangeRate > 0, fontwhite: v.LastQuotation.ChangeRate == 0}">{{v.LastQuotation.LastPrice | fixNum2(v.DotSize)}}</li>
									<li class="fl" :class="{fontgreen: v.LastQuotation.ChangeRate < 0, fontred: v.LastQuotation.ChangeRate > 0, fontwhite: v.LastQuotation.ChangeRate == 0}" v-show="isswitch">{{v.LastQuotation.ChangeRate | fixNum}}%</li>
									<li class="fl" :class="{fontgreen: v.LastQuotation.ChangeRate < 0, fontred: v.LastQuotation.ChangeRate > 0, fontwhite: v.LastQuotation.ChangeRate == 0}" v-show="!isswitch">{{v.LastQuotation.ChangeValue | fixNum2(v.DotSize)}}</li>
								</ol>
							</li>
						</template>
						<p class="tips">没有更多的合约可加载啦~</p>
					</ul>
					
				</div>
			</div>
		</template>
		<guide v-if='guideshow'></guide>
	</div>

</template>

<script>
	import tipsDialog from '../components/tipsDialog.vue'
	import topbar from '../components/Topbar.vue'
	import guide from './Guide.vue'
	import VueNativeSock from 'vue-native-websocket'
	import { mapMutations,mapActions } from 'vuex'
	export default {
		name: 'home',
		data() {
			return {
				time: 3,
				msg: '',
				title: '涨跌幅',
				isdynamic: true,
				isswitch: true,
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
		components: {topbar, guide, tipsDialog},
		computed: {
			msgTips: function(){
				return this.msg;
			},
			Parameters(){
				return this.$store.state.market.Parameters;
			},
			quoteIndex(){
				return this.$store.state.market.quoteIndex;
			},
			isconnected() {
				return this.$store.state.isshow.isconnected;
			},
			guideshow(){
				return this.$store.state.isshow.guideshow;
			},
			quoteConnectedMsg(){
				return this.$store.state.market.quoteConnectedMsg;
			},
			isBack: function(){
				return this.$route.query.isBack;
			},
			PATH: function(){
				return this.$store.getters.PATH;
			},
		},
		watch: {
			quoteIndex: function(n, o){
				if(this.Parameters[n].LastQuotation.ChangeRate < 0){
					$("#datalist>.cont>li").eq(n).addClass("green");
					setTimeout(function(){
						$("#datalist>.cont>li").eq(n).removeClass("green");
					}, 500);
				}else if(this.Parameters[n].LastQuotation.ChangeRate == 0){
					return true;
				}else{
					$("#datalist>.cont>li").eq(n).addClass("red");
					setTimeout(function(){
						$("#datalist>.cont>li").eq(n).removeClass("red");
					}, 500);
				}
			},
			Parameters: function(n, o){
				this.arr = o;
//				setTimeout(function(){
//					n.forEach(function(v, k){
//						if(v.LastQuotation.ChangeRate < 0){
//							
//							$("#datalist>.cont>li").eq(k).addClass("green");
//							setTimeout(function(){
//								$("#datalist>.cont>li").eq(k).removeClass("green");
//							}, 500);
//						}else{
//							$("#datalist>.cont>li").eq(k).addClass("red");
//							setTimeout(function(){
//								$("#datalist>.cont>li").eq(k).removeClass("red");
//							}, 500);
//						}
//					});
//				}, 2000);
			},
			quoteConnectedMsg: function(n, o){
				if(n && this.guideshow == false){
					this.$children[0].isShow = true;
					this.msg = n.slice(0,-1);
					setTimeout(function(){
						this.isdynamic = false;
					}.bind(this),1000);
				}
			},
			guideshow: function(n, o){
				if(n == false){
					this.$children[0].isShow = true;
					this.msg = '行情服务器连接成功';
				}
			},
			isBack: function(n, o){
				if(n == 1){
					window.location.reload();
				}
			},
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
				window.location.reload();
			},
			toHelp: function(){
				this.$router.push({path: '/help'});
			},
			selectClass: function(e) {
				$(e.target).addClass('current').siblings('li').removeClass('current');
			},
			switchEvent: function(e){
				if($(e.currentTarget).text() == '涨跌幅'){
					this.title = '涨跌额';
					this.isswitch = false;
				}else{
					this.title = '涨跌幅';
					this.isswitch = true;
				}
			},
			toDetail: function(a) {
				this.Parameters.forEach(function(e){
					if(e.CommodityName == $(a.currentTarget).children().find('h5:first-child').text()){
						this.$store.state.market.currentdetail=e;
					}
				}.bind(this));
				this.$router.push({path: '/orderdetail'});
			},
			getVersion: function(){
				this.$http.post(this.PATH + '/getVersion', {emulateJSON: true}, {
						params: {},
						timeout: 5000
				}).then(function(e) {
					var data = e.body;
					if(data.success == true){
						if(data.code == 1){
							this.$store.state.version.ios = JSON.parse(data.data.version).iOS.version;
							this.$store.state.version.android = JSON.parse(data.data.version).Android.version;
							var version ={
								ios: JSON.parse(data.data.version).iOS.version,
								android: JSON.parse(data.data.version).Android.version
							}
							localStorage.version = JSON.stringify(version);
						}
					}
				}.bind(this), function() {
					this.$children[0].isShow = true;
					this.msg = '网络不给力，请稍后再试！'
				});
			}
		},
		mounted: function() {
			//初始化行情
			this.initQuoteClient();
			//取当前版本号
			this.getVersion();
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
			};
		},
		activated:function(){
			this.$store.state.market.currentNo='';
			this.$store.state.isshow.isklineshow = false;
		}
	}
</script>

<style scoped lang="less">
	@import url("../assets/css/main.less");
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
	}
	#selectbar {
		width: 100%;
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
	}
	#selectbar>ul>li.current {
		color: #ffd400;
		border-bottom: 3px solid #ffd400;
	}
	#datalist ul li{
		&.green{
			background: #294743;
		}
		&.red{
			background: #502e38;
		}
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
		width: 36%;
		/*text-align: left;
		padding-left: 15px;*/
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
		width: 100%;
		line-height: 40px;
		background: #36394d;
	}
	#datalist>ul>li>ol {
		width: 100%;
		height: 40px;
	}
	.icon_sj{
		display: inline-block;
		width: 8px;
		height: 8px;
		overflow: hidden;
		background: url(../assets/img/sanjiao.png) no-repeat center center;
		background-size: 100% 100%;
		margin: 18px 0 0 0;
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
		background-size: 100% 100%;
		position: fixed;
		z-index: 1000;
		right: 7%;
		top: 2%;
		&.dynamic{
			background-image: url('../assets/img/refresh.gif');
		}
		&.static{
			background-image: url('../assets/img/refresh.png');
		}
	}
	.tips{
		width: 100%;
		text-align: center;
		padding: 10px 15px;
		color: #fff;
		font-size: 14px;
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
		#datalist>ul:nth-child(2) {
			margin-top: 50px*@ip5;
			box-sizing: content-box;
			padding-bottom: 58px*@ip5;
		}
		#datalist>ul>li>ol>li {
			text-align: center;
			width: 21%;
			font-size: 12px;
		}
		#home {
			padding-top: 50px*@ip5+30px;
			padding-bottom: 50px*@ip5;
		}
		#selectbar {
			top: 50px*@ip5+30px;
		}
		.help{
			width: 50px*@ip5;
			height: 50px*@ip5;
			overflow: hidden;
			background: none;
			background: url(../assets/img/help.png) no-repeat 15px*@ip5 15px*@ip5;
			background-size: 20px*@ip5 20px*@ip5;
			border: none;
			outline: none;
			position: fixed;
			top: 0;
			left: 0;
			z-index: 1000;
		}
		.icon_sj{
			display: inline-block;
			width: 6px;
			height: 6px;
			overflow: hidden;
			background: url(../assets/img/sanjiao.png) no-repeat center center;
			background-size: 100% 100%;
			margin: 18px 0 0 0;
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
		#datalist>ul:nth-child(2) {
			margin-top: 42px*@ip6;
			box-sizing: content-box;
			padding-bottom: 55px*@ip6;
		}
		#home {
			padding-top: 50px*@ip6+30px;
			padding-bottom: 50px*@ip6;
		}
		#selectbar {
			top: 50px*@ip6+30px;
		}
		.help{
			width: 50px*@ip6;
			height: 50px*@ip6;
			overflow: hidden;
			background: url(../assets/img/help.png) no-repeat 15px*@ip6 15px*@ip6;
			background-size: 20px*@ip6 20px*@ip6;
			border: none;
			outline: none;
			position: fixed;
			top: 0;
			left: 0;
			z-index: 1000;
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
		#home {
			padding-top: 50px*@ip6p+30px;
			padding-bottom: 50px*@ip6p;
		}
		#selectbar {
			top: 50px*@ip6p+30px;
		}
		.help{
			width: 50px;
			height: 50px;
			overflow: hidden;
			background: url(../assets/img/help.png) no-repeat 15px 15px;
			background-size: 20px 20px;
			border: none;
			outline: none;
			position: fixed;
			top: 0;
			left: 0;
			z-index: 1000;
		}
	}
</style>