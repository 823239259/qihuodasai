<template>
	<div id="app">
		<div id="nav" class="container_left">
			<ul>
				<li @click="toIndex">
					<img src="./assets/images/logo.png" alt="logo" />
				</li>
				<template v-for="(v, index) in navList">
					<li :class="{current: currentNav == index}" @click="clickEvent(index)">
						<i class="icon" :class="v.cs"></i>
						<p>{{v.name}}</p>
					</li>
				</template>
			</ul>
		</div>
		<div class="container_top">
			<div class="fl box"></div>
			<div class="fl">
				<i class="ifont zoom" @click="fullScreen">&#xe62e;</i>
				<!--<i class="ifont zoom" @click="fullScreen" v-show="fullScreenCurrent">&#xe612;</i>-->
				<div class="customer_service fl" :class="{current: csAddressCurrent}" @click="customerService">
					<i class="ifont">&#xe68f;</i>
					<span>在线客服</span>
				</div>
				<iframe :src="csAddress" v-if="csAddressCurrent"></iframe>
				<div class="tel fl">
					<i class="ifont">&#xe611;</i>
					<span>客服热线：</span>
					<em>400-852-8008</em>
				</div>
			</div>
			<div class="fr" v-show="!show_login">
				<a href="javascript: void(0);" v-on:click="toLogin">登录</a>
				<a href="javascript: void(0);" v-on:click="toRegister">注册</a>
			</div>
			<!--登陆后显示样式-->
			<div class="fr_login" v-show="show_login">
				<i>欢迎，</i><span class="userPhone">{{userName}}</span>
				<i v-on:click="exit">退出</i>
			</div>
		</div>
		<div class="container_bottom">
			<p v-if="parameters[0]">
				<span>国际原油：</span>
				<span :class="{red: parameters[0].LastQuotation.LastPrice > parameters[0].LastQuotation.PreSettlePrice, green: parameters[0].LastQuotation.LastPrice < parameters[0].LastQuotation.PreSettlePrice}">{{parameters[0].LastQuotation.LastPrice | fixNumTwo}}</span>
				<span :class="{green: parameters[0].LastQuotation.ChangeRate < 0, red: parameters[0].LastQuotation.ChangeRate > 0}">{{parameters[0].LastQuotation.ChangeValue | fixNum(parameters[0].DotSize)}}</span>
				<span :class="{green: parameters[0].LastQuotation.ChangeRate < 0, red: parameters[0].LastQuotation.ChangeRate > 0}">{{parameters[0].LastQuotation.ChangeRate | fixNumTwo}}%</span>
				<i class="ifont" v-show="parameters[0].LastQuotation.LastPrice < parameters[0].LastQuotation.PreSettlePrice" :class="{red: parameters[0].LastQuotation.LastPrice > parameters[0].LastQuotation.PreSettlePrice, green: parameters[0].LastQuotation.LastPrice < parameters[0].LastQuotation.PreSettlePrice}">&#xe76a;</i>
				<i class="ifont" v-show="parameters[0].LastQuotation.LastPrice >= parameters[0].LastQuotation.PreSettlePrice" :class="{red: parameters[0].LastQuotation.LastPrice > parameters[0].LastQuotation.PreSettlePrice, green: parameters[0].LastQuotation.LastPrice < parameters[0].LastQuotation.PreSettlePrice}">&#xe761;</i>
			</p>
			<p v-if="parameters[2]">
				<span>美黄金：</span>
				<span :class="{red: parameters[2].LastQuotation.LastPrice > parameters[2].LastQuotation.PreSettlePrice, green: parameters[2].LastQuotation.LastPrice < parameters[2].LastQuotation.PreSettlePrice}">{{parameters[2].LastQuotation.LastPrice | fixNumTwo}}</span>
				<span :class="{green: parameters[2].LastQuotation.ChangeRate < 0, red: parameters[2].LastQuotation.ChangeRate > 0}">{{parameters[2].LastQuotation.ChangeValue | fixNum(parameters[2].DotSize)}}</span>
				<span :class="{green: parameters[2].LastQuotation.ChangeRate < 0, red: parameters[2].LastQuotation.ChangeRate > 0}">{{parameters[2].LastQuotation.ChangeRate | fixNumTwo}}%</span>
				<i class="ifont" v-show="parameters[2].LastQuotation.LastPrice < parameters[2].LastQuotation.PreSettlePrice" :class="{red: parameters[2].LastQuotation.LastPrice > parameters[2].LastQuotation.PreSettlePrice, green: parameters[2].LastQuotation.LastPrice < parameters[2].LastQuotation.PreSettlePrice}">&#xe76a;</i>
				<i class="ifont" v-show="parameters[2].LastQuotation.LastPrice >= parameters[2].LastQuotation.PreSettlePrice" :class="{red: parameters[2].LastQuotation.LastPrice > parameters[2].LastQuotation.PreSettlePrice, green: parameters[2].LastQuotation.LastPrice < parameters[2].LastQuotation.PreSettlePrice}">&#xe761;</i>
			</p>
			<p v-if="parameters[1]">
				<span>恒指期货：</span>
				<span :class="{red: parameters[1].LastQuotation.LastPrice > parameters[1].LastQuotation.PreSettlePrice, green: parameters[1].LastQuotation.LastPrice < parameters[1].LastQuotation.PreSettlePrice}">{{parameters[1].LastQuotation.LastPrice | fixNumTwo}}</span>
				<span :class="{green: parameters[1].LastQuotation.ChangeRate < 0, red: parameters[1].LastQuotation.ChangeRate > 0}">{{parameters[1].LastQuotation.ChangeValue | fixNum(parameters[1].DotSize)}}</span>
				<span :class="{green: parameters[1].LastQuotation.ChangeRate < 0, red: parameters[1].LastQuotation.ChangeRate > 0}">{{parameters[1].LastQuotation.ChangeRate | fixNumTwo}}%</span>
				<i class="ifont" v-show="parameters[1].LastQuotation.LastPrice < parameters[1].LastQuotation.PreSettlePrice" :class="{red: parameters[1].LastQuotation.LastPrice > parameters[1].LastQuotation.PreSettlePrice, green: parameters[1].LastQuotation.LastPrice < parameters[1].LastQuotation.PreSettlePrice}">&#xe76a;</i>
				<i class="ifont" v-show="parameters[1].LastQuotation.LastPrice >= parameters[1].LastQuotation.PreSettlePrice" :class="{red: parameters[1].LastQuotation.LastPrice > parameters[1].LastQuotation.PreSettlePrice, green: parameters[1].LastQuotation.LastPrice < parameters[1].LastQuotation.PreSettlePrice}">&#xe761;</i>
			</p>
			<p class="net net_yes" v-show="iconShow">
				<i class="icon icon_yes"></i>
				<span>已连接</span>
			</p>
			<p class="net net_no" v-show="!iconShow">
				<i class="icon icon_no"></i>
				<span>已断开</span>
			</p>
		</div>
		<div class="container">
			<keep-alive>
				<router-view v-if="!$route.meta.notKeepAlive"></router-view>
			</keep-alive>
			<router-view v-if="$route.meta.notKeepAlive"></router-view>
		</div>
		<!--退出弹窗-->
		<div id="isExit" v-show="isShow_exit">
			<div class="bg"></div>
			<div class="isExit">
				<p>退回登录</p>
				<p>是否退出登录状态？</p>
				<button class="yellow btn" v-on:click="confirm">确认</button>
				<button class="green btn" v-on:click="canal">取消</button>
			</div>
		</div>
	</div>
</template>

<script>
	import { mapMutations,mapActions } from 'vuex'
	export default {
		name: 'app',
		data(){
			return {
				fullScreenCurrent: false,
				csAddress: '',
				csAddressCurrent: false,
				parametersRecommend: [],
				userInfo : '',
				isShow_exit : false,
				show_login : false,
				iconShow: true,
				zoomShow: true,
				navList: [{
					name: '行情',
					cs: 'icon_quote'
				},{
					name: '交易',
					cs: 'icon_trade'
				},{
					name: '开户',
					cs: 'icon_open'
//				},{
//					name: '直播',
//					cs: 'icon_live'
//				},{
//					name: '日历',
//					cs: 'icon_calendar'
//				},{
//					name: '下载',
//					cs: 'icon_download'
				},{
					name: '我的',
					cs: 'icon_account'
				}],
			}
		},
		computed: {
			parameters(){
				return this.$store.state.market.Parameters;
			},
			quoteInitStatus(){
				return this.$store.state.market.quoteInitStatus;
			},
			currentNav(){
				return this.$store.state.account.currentNav;
			},
			isRefresh(){
				return this.$store.state.account.isRefresh;
			},
			userName(){
				if(this.$store.state.account.userName){
					var user = this.$store.state.account.userName;
					return user.substr(0, 3) + '****' + user.substr(7); 
				}
			}
		},
		filters:{
			fixNumTwo: function(num){
				return num.toFixed(2);
			},
			fixNum: function(num, dotsize){
				return num.toFixed(dotsize);
			},
		},
		watch: {
			showWarning: function(n, o){
				if(n && n == true){
					this.iconShow = false;
				}else{
					this.iconShow = true;
				}
			},
			userName: function(n, o){
				if(n != '' && n != undefined){
					this.show_login = true;
				}else{
					this.show_login = false;
				}
			},
			isRefresh: function(n, o){
				console.log(n);
				if(n == 1){
					window.location.reload();
				}else if(n == ''){
					this.$store.state.account.isRefresh = '';
				}
			}
		},
		methods: {
			...mapActions([
				'initQuoteClient'
			]),
			customerService: function(){
				if(this.csAddressCurrent == false){
					this.csAddressCurrent = true;
					this.csAddress = 'http://test.www.vs.com/topic/consistentBeauty/consistentbeauty.html?phone='+ this.userInfo.username +'&userName=aaa';
				}else{
					this.csAddressCurrent = false;
					this.csAddress = '';
				}
			},
			fullScreen: function(){
				if(this.fullScreenCurrent == false){
					this.fullScreenCurrent = true;
					var docElm = document.documentElement;
					if (docElm.requestFullscreen) {  //W3C  
					    docElm.requestFullscreen();  
					}else if (docElm.mozRequestFullScreen) {  //FireFox 
					    docElm.mozRequestFullScreen();  
					}else if (docElm.webkitRequestFullScreen) {  //Chrome等  
					    docElm.webkitRequestFullScreen();  
					}else if (elem.msRequestFullscreen) {  //IE11
					  elem.msRequestFullscreen();
					}
				}else{
					this.fullScreenCurrent = false;
					if (document.exitFullscreen) {  
						document.exitFullscreen();  
					}else if (document.mozCancelFullScreen) {  
					    document.mozCancelFullScreen();  
					}else if (document.webkitCancelFullScreen) {  
					    document.webkitCancelFullScreen();  
					}else if (document.msExitFullscreen) {
					    document.msExitFullscreen();
					}
				}
				document.addEventListener("fullscreenchange", function () {  
			        fullscreenState.innerHTML = (document.fullscreen) ? "" : "not ";  
			    }, false);  
			    document.addEventListener("mozfullscreenchange", function () {  
			        fullscreenState.innerHTML = (document.mozFullScreen) ? "" : "not ";  
			    }, false);  
			    document.addEventListener("webkitfullscreenchange", function () {  
			        fullscreenState.innerHTML = (document.webkitIsFullScreen) ? "" : "not ";  
			    }, false);  
			    document.addEventListener("msfullscreenchange", function () {  
			        fullscreenState.innerHTML = (document.msFullscreenElement) ? "" : "not ";  
			    }, false);  
			},
			toIndex: function(){
				this.$router.push({path: '/index'});
				this.$store.state.account.currentNav = 0;
			},
			clickEvent: function(index){
				this.$store.state.isshow.isfens = false;
				this.$store.state.isshow.iskline = false;
				this.$store.state.isshow.isfensshow = false;
				this.$store.state.isshow.isklineshow = false;
				switch (index){
					case 0:
						this.$router.push({path: '/index'});
						this.$store.state.account.currentNav = 0;
						break;
					case 1:
						this.$router.push({path: '/trade'});
						this.$store.state.account.currentNav = 1;
						break;
					case 2:
						this.userInfo = localStorage.user ? JSON.parse(localStorage.user) : '';
						if(this.userInfo == ''){
							this.$router.push({path: '/login'});
							this.$store.state.account.currentNav = 3;
						}else{
							this.$router.push({path: '/openAccount'});
							this.$store.state.account.currentNav = 2;
						}
						break;
//					case 3:
//						this.$router.push({path: '/liveStream'});
//						this.$store.state.account.currentNav = 3;
//						break;
//					case 4:
//						this.$router.push({path: '/calendar'});
//						this.$store.state.account.currentNav = 4;
//						break;
//					case 5:
//						this.$router.push({path: '/download'});
//						this.$store.state.account.currentNav = 5;
//						break;
					case 3:
						this.userInfo = localStorage.user ? JSON.parse(localStorage.user) : '';
						if(this.userInfo == ''){
							this.$router.push({path: '/login'});
						}else{
							this.$router.push({path: '/account'});
						}
						this.$store.state.account.currentNav = 3;
						break;
					default:
						break;
				}
			},
			toRegister : function(){
				this.$router.push({path: '/register'});
				this.$store.state.isshow.isfensshow = false;
				this.$store.state.isshow.isklineshow = false;
			},
			toLogin : function(){
				this.$router.push({path:'/login'});
				this.$store.state.isshow.isfensshow = false;
				this.$store.state.isshow.isklineshow = false;
			},
			//退出登录,清空localstorge
			exit: function(){
				this.isShow_exit = true;
			},
			confirm: function(){
				localStorage.removeItem('user');
				localStorage.removeItem('tradeUser');
				this.$store.state.account.userName = '';
				this.$store.state.account.isRefresh = 1;
				this.$router.push({path: '/index'});
				this.$store.state.account.currentNav = 0;
				this.isShow_exit = false;
				this.show_login = false;
			},
			canal: function(){
				this.isShow_exit = false;
			}
		},
		mounted: function(){
			//初始化行情
			if(this.quoteInitStatus == false){
				this.initQuoteClient();
				this.$store.state.market.quoteInitStatus == true;
			};
			//判断是否登录
			if(localStorage.user){
				this.show_login = true;
				this.$store.state.account.userName = JSON.parse(localStorage.user).username;
			}else{
				this.show_login = false;
			}
		},
		activated: function(){
			//获取平台账户登录信息
			this.userInfo = localStorage.user ? JSON.parse(localStorage.user) : '';
			//监听退出全屏
			document.onkeydown = function (e) {
	        	e = e || event;
	            if (e.keyCode == 27) {  //判断是否单击的esc按键
	                this.fullScreenCurrent = false;
	            }
		 	}.bind(this);
		}
	}
</script>

<style lang="scss" scoped type="text/css">
	@import "./assets/css/common.scss";
	/*容器框架*/
	#app {
	    width: 100%;
	    min-width: 1280px;
	}
	.icon{
		width: 20px;
		height: 20px;
		margin: 10px 0 4px 0;
		background-size: 100% 100%;
	}
	/*左部导航*/
	.container_left {
		position: fixed;
		top: 0;
		left: 0;
		z-index: 2;
	    width: 60px;
	    height: 100%;
	    background: $blue;
	    ul{
	        li{
	        	width: 60px;
	            height: 60px;
	            overflow: hidden;
	            text-align: center;
	            cursor: pointer;
	            &:hover, &.current{
	            	background: $deepblue;
	            	.icon_quote{
	            		background: url(assets/images/icon_quote1.png) no-repeat center center;
	            	}
	            	.icon_trade{
	            		background: url(assets/images/icon_trade1.png) no-repeat center center;
	            	}
	            	.icon_open{
	            		background: url(assets/images/icon_open1.png) no-repeat center center;
	            	}
	            	.icon_live{
	            		background: url(assets/images/icon_live1.png) no-repeat center center;
	            	}
	            	.icon_calendar{
	            		background: url(assets/images/icon_calendar1.png) no-repeat center center;
	            	}
	            	.icon_download{
	            		background: url(assets/images/icon_download1.png) no-repeat center center;
	            	}
	            	.icon_account{
	            		background: url(assets/images/icon_account1.png) no-repeat center center;
	            	}
	            	p{
	            		color: $yellow;
	            	}
	            }
	            .icon_quote{
	            	background: url(assets/images/icon_quote.png) no-repeat center center;
	            }
	            .icon_trade{
	            	background: url(assets/images/icon_trade.png) no-repeat center center;
	            }
	            .icon_open{
	            	background: url(assets/images/icon_open.png) no-repeat center center;
	            }
	            .icon_live{
	            	background: url(assets/images/icon_live.png) no-repeat center center;
	            }
	            .icon_calendar{
	            	background: url(assets/images/icon_calendar.png) no-repeat center center;
	            }
	            .icon_download{
	            	background: url(assets/images/icon_download.png) no-repeat center center;
	            }
	            .icon_account{
	            	background: url(assets/images/icon_account.png) no-repeat center center;
	            }
	            p{
	            	font-size: $fs12;
	            	color: $lightblue;
	            }
	        }
	    }
	}
	/*顶部栏*/
	.container_top {
		position: fixed;
		top: 0;
		left: 0;
		z-index: 1;
	    width: 100%;
	    height: 40px;
	    line-height: 40px;
	    background: $blue;
	    margin: 5px 0;
	    font-size: $fs12;
	    .box{
	    	width: 70px;
	    	height: 40px;
	    	overflow: hidden;
	    	background: $black;
	    }
		.ifont{
			float: left;
			color: $lightblue;
			&.zoom{
				font-size: $fs20;
				margin: 0 40px 0 20px;
				cursor: pointer;
			}
		}
		span, em{
			float: left;
		}
		.customer_service, .tel{
			margin-right: 48px;
			.ifont{
				font-size: $fs16;
				margin-right: 10px;
			}
		}
		.customer_service{
			cursor: pointer;
			&:hover, &.current{
				color: $yellow;
				.ifont{
					color: $yellow;
				}
			}
		}
		em{
			color: $white;
		}
		a{
			float: left;
			display: inline-block;
			height: 30px;
			line-height: 30px;
			padding: 0 20px;
			margin: 5px 0;
			font-size: $fs12;
			color: $lightblue;
			&:last-child{
				border: 1px solid $lightblue;
				border-radius: 15px;
				margin-right: 10px;
			}
			&:hover{
				color: $yellow;
				border-color: $yellow;
			}
		}
	}
	.fr_login {
		float: right;
		margin-right: 10px;
		i {
			color: #ccd5ff;
			font-size: $fs12;
			float: left;
			cursor: pointer;
		}
		.userPhone{
			color: $yellow;
			margin-right: 10px;
		}
	}
	/*底部栏*/
	.container_bottom{
	    position: fixed;
	    bottom: 0;
	    left: 0;
	    z-index: 3;
	    width: 100%;
	    height: 30px;
	    line-height: 30px;
	    background: $bottom_color;
	    p{
	    	float: left;
	    	font-size: $fs12;
	    	margin-left: 20px;
	    	.ifont{
	    		font-size: 10px;
	    		color: $lightblue;
	    	}
	    	.green{
	    		color: $green;
	    	}
	    	.red{
	    		color: $red;
	    	}
	    	span{
	    		float: left;
	    		margin-right: 10px;
	    		&:first-child{
	    			color: $white;
	    			margin: 0;
	    		}
	    	}
	    	&.net{
	    		float: right;
	    		margin-right: 20px;
	    		.icon{
	    			margin: 0;
	    			float: left;
	    			width: 16px;
	    			height: 16px;
	    			margin: 7px 5px 0 0;
	    		}
	    		.icon_yes{
	    			background: url(assets/images/net_yes.png) no-repeat center center;
	    		}
	    		.icon_no{
	    			background: url(assets/images/net_no.png) no-repeat center center;
	    		}
	    		span{
	    			color: $white;
	    		}
	    	}
	    }
	}
	/*容器*/
	.container{
		position: absolute;
		top: 0;
		left: 0;
		width: 100%;
		min-width: 1210px;
		overflow: hidden;
		overflow-x: auto;
		padding: 50px 0 0 70px;
	}
	iframe {
		width: 480px; 
		height: 480px; 
		overflow: hidden;
		position: fixed; 
		bottom: 0px;
		right: 0;
		z-index: 1;
		border: none;
	}
	/*退出框*/
	.isExit {
		position: fixed;
		top: 50%;
		left: 50%;
		width: 300px;
		height: 120px;
		margin: -60px 0 0 -150px;
		text-align: center;
		background-color: $blue;
		z-index: 100;
		border-radius: 10px;
		.btn {
			width: 120px;
			height: 30px;
		}
		p {
			&:nth-child(1){
				border-radius: 10px;
				height: 40px;
				background: $bottom_color;
				color: $white;
				font-size: $fs16;
				line-height: 40px;
			}
			&:nth-child(2){
				height: 40px;
				color: $white;
				font-size: $fs16;
				line-height: 40px;
			}
		}
	}
</style>