<template>
	<div id="app">
		<div id="nav" class="container_left">
			<ul>
				<li @click="clickEvent">
					<img src="./assets/images/logo.png" alt="logo" />
				</li>
				<li class="current" @click="clickEvent">
					<i class="icon icon_quote"></i>
					<p>行情</p>
				</li>
				<li @click="clickEvent">
					<i class="icon icon_trade"></i>
					<p>交易</p>
				</li>
				<li @click="clickEvent">
					<i class="icon icon_open"></i>
					<p>开户</p>
				</li>
				<li @click="clickEvent">
					<i class="icon icon_live"></i>
					<p>直播</p>
				</li>
				<li @click="clickEvent">
					<i class="icon icon_calendar"></i>
					<p>日历</p>
				</li>
				<li @click="clickEvent">
					<i class="icon icon_download"></i>
					<p>下载</p>
				</li>
				<li @click="clickEvent">
					<i class="icon icon_account"></i>
					<p>我的</p>
				</li>
			</ul>
		</div>
		<div class="container_top">
			<div class="fl box"></div>
			<div class="fl">
				<i class="ifont zoom" @click="fullScreen">&#xe62e;</i>
				<div class="customer_service fl" :class="{current: csAddressCurrent}" @click="customerService">
					<i class="ifont">&#xe68f;</i>
					<span>在线客服</span>
				</div>
				<iframe :src="csAddress"></iframe>
				<div class="tel fl">
					<i class="ifont">&#xe611;</i>
					<span>客服热线：</span>
					<em>400-852-8008</em>
				</div>
			</div>
			<div class="fr">
				<a href="javascript: void(0);" v-on:click="toLogin">登录</a>
				<a href="javascript: void(0);" v-on:click="toRegister">注册</a>
			</div>
		</div>
		<div class="container_bottom">
			<p v-if="parameters[0]">
				<span>国际原油：</span>
				<span :class="{red: parameters[0].LastQuotation.LastPrice > parameters[0].LastQuotation.PreSettlePrice, green: parameters[0].LastQuotation.LastPrice < parameters[0].LastQuotation.PreSettlePrice}">{{parameters[0].LastQuotation.LastPrice}}</span>
				<span :class="{green: parameters[0].LastQuotation.ChangeRate < 0, red: parameters[0].LastQuotation.ChangeRate > 0}">{{parameters[0].LastQuotation.ChangeValue | fixNum(parameters[0].DotSize)}}</span>
				<span :class="{green: parameters[0].LastQuotation.ChangeRate < 0, red: parameters[0].LastQuotation.ChangeRate > 0}">{{parameters[0].LastQuotation.ChangeRate | fixNumTwo}}%</span>
				<i class="ifont" v-show="parameters[0].LastQuotation.LastPrice < parameters[0].LastQuotation.PreSettlePrice" :class="{red: parameters[0].LastQuotation.LastPrice > parameters[0].LastQuotation.PreSettlePrice, green: parameters[0].LastQuotation.LastPrice < parameters[0].LastQuotation.PreSettlePrice}">&#xe76a;</i>
				<i class="ifont" v-show="parameters[0].LastQuotation.LastPrice >= parameters[0].LastQuotation.PreSettlePrice" :class="{red: parameters[0].LastQuotation.LastPrice > parameters[0].LastQuotation.PreSettlePrice, green: parameters[0].LastQuotation.LastPrice < parameters[0].LastQuotation.PreSettlePrice}">&#xe761;</i>
			</p>
			<p v-if="parameters[2]">
				<span>美黄金：</span>
				<span :class="{red: parameters[2].LastQuotation.LastPrice > parameters[2].LastQuotation.PreSettlePrice, green: parameters[2].LastQuotation.LastPrice < parameters[2].LastQuotation.PreSettlePrice}">{{parameters[2].LastQuotation.LastPrice}}</span>
				<span :class="{green: parameters[2].LastQuotation.ChangeRate < 0, red: parameters[2].LastQuotation.ChangeRate > 0}">{{parameters[2].LastQuotation.ChangeValue | fixNum(parameters[2].DotSize)}}</span>
				<span :class="{green: parameters[2].LastQuotation.ChangeRate < 0, red: parameters[2].LastQuotation.ChangeRate > 0}">{{parameters[2].LastQuotation.ChangeRate | fixNumTwo}}%</span>
				<i class="ifont" v-show="parameters[2].LastQuotation.LastPrice < parameters[2].LastQuotation.PreSettlePrice" :class="{red: parameters[2].LastQuotation.LastPrice > parameters[2].LastQuotation.PreSettlePrice, green: parameters[2].LastQuotation.LastPrice < parameters[2].LastQuotation.PreSettlePrice}">&#xe76a;</i>
				<i class="ifont" v-show="parameters[2].LastQuotation.LastPrice >= parameters[2].LastQuotation.PreSettlePrice" :class="{red: parameters[2].LastQuotation.LastPrice > parameters[2].LastQuotation.PreSettlePrice, green: parameters[2].LastQuotation.LastPrice < parameters[2].LastQuotation.PreSettlePrice}">&#xe761;</i>
			</p>
			<p v-if="parameters[1]">
				<span>恒指期货：</span>
				<span :class="{red: parameters[1].LastQuotation.LastPrice > parameters[1].LastQuotation.PreSettlePrice, green: parameters[1].LastQuotation.LastPrice < parameters[1].LastQuotation.PreSettlePrice}">{{parameters[1].LastQuotation.LastPrice}}</span>
				<span :class="{green: parameters[1].LastQuotation.ChangeRate < 0, red: parameters[1].LastQuotation.ChangeRate > 0}">{{parameters[1].LastQuotation.ChangeValue | fixNum(parameters[1].DotSize)}}</span>
				<span :class="{green: parameters[1].LastQuotation.ChangeRate < 0, red: parameters[1].LastQuotation.ChangeRate > 0}">{{parameters[1].LastQuotation.ChangeRate | fixNumTwo}}%</span>
				<i class="ifont" v-show="parameters[1].LastQuotation.LastPrice < parameters[1].LastQuotation.PreSettlePrice" :class="{red: parameters[1].LastQuotation.LastPrice > parameters[1].LastQuotation.PreSettlePrice, green: parameters[1].LastQuotation.LastPrice < parameters[1].LastQuotation.PreSettlePrice}">&#xe76a;</i>
				<i class="ifont" v-show="parameters[1].LastQuotation.LastPrice >= parameters[1].LastQuotation.PreSettlePrice" :class="{red: parameters[1].LastQuotation.LastPrice > parameters[1].LastQuotation.PreSettlePrice, green: parameters[1].LastQuotation.LastPrice < parameters[1].LastQuotation.PreSettlePrice}">&#xe761;</i>
			</p>
			<p class="net net_yes">
				<i class="icon icon_yes"></i>
				<span>已连接</span>
			</p>
			<!--<p class="net net_no">
				<i class="icon icon_no"></i>
				<span>已断开</span>
			</p>-->
		</div>
		<div class="container">
			<keep-alive>
				<router-view v-if="!$route.meta.notKeepAlive"></router-view>
			</keep-alive>
			<router-view v-if="$route.meta.notKeepAlive"></router-view>
		</div>
		<login class="loginShow" v-if="isshow_login"/>
		<register class="resgisterShow" v-if="isshow_register"/>
		<warning v-if="warningShow"></warning>
	</div>
</template>

<script>
	import login from "./pages/account/login.vue"
	import register from "./pages/account/register.vue"
	import warning from "./pages/trade/warning.vue"
	import { mapMutations,mapActions } from 'vuex'
	export default {
		name: 'app',
		components : {login,register, warning},
		data(){
			return {
				isshow_login : false,
				isshow_register : false,
				warningShow: false,
				fullScreenCurrent: false,
				csAddress: '',
				csAddressCurrent: false,
				parametersRecommend: [],
			}
		},
		computed: {
			parameters(){
				return this.$store.state.market.Parameters;
			},
		},
		filters:{
			fixNumTwo: function(num){
				return num.toFixed(2);
			},
			fixNum: function(num, dotsize){
				return num.toFixed(dotsize);
			}
		},
		methods: {
			customerService: function(){
				if(this.csAddressCurrent == false){
					this.csAddressCurrent = true;
					this.csAddress = 'http://test.www.vs.com/topic/consistentBeauty/consistentbeauty.html?phone=15928423292&userName=wxf';
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
					}  
					else if (document.mozCancelFullScreen) {  
					    document.mozCancelFullScreen();  
					}  
					else if (document.webkitCancelFullScreen) {  
					    document.webkitCancelFullScreen();  
					}
					else if (document.msExitFullscreen) {
					      document.msExitFullscreen();
					}
				}
			},
			clickEvent: function(e){
				var index = $(e.currentTarget).index();
				if(index == 0){
					this.$router.push({path: '/index'});
					$("#nav li").eq(1).addClass("current").siblings().removeClass("current");
				}else{
					$(e.currentTarget).addClass("current").siblings().removeClass("current");
					switch (index){
						case 1:
							this.$router.push({path: '/index'});
							break;
						case 2:
							this.$router.push({path: '/trade'});
							break;
						case 3:
							this.$router.push({path: '/openAccount'});
							break;
						case 4:
							this.$router.push({path: '/liveStream'});
							break;
						case 5:
							this.$router.push({path: '/calendar'});
							break;
						case 6:
							this.$router.push({path: '/download'});
							break;
						case 7:
							this.$router.push({path: '/account'});
							break;
						default:
							break;
					}
				}
			},
			toRegister : function(){
				 this.isshow_register=!this.isshow_register;
			},
			toLogin : function(){
                this.isshow_login=!this.isshow_login;
			},
		},
		mounted: function(){
			
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
	            		background-image: url(assets/images/icon_quote1.png);
	            	}
	            	.icon_trade{
	            		background-image: url(assets/images/icon_trade1.png);
	            	}
	            	.icon_open{
	            		background-image: url(assets/images/icon_open1.png);
	            	}
	            	.icon_live{
	            		background-image: url(assets/images/icon_live1.png);
	            	}
	            	.icon_calendar{
	            		background-image: url(assets/images/icon_calendar1.png);
	            	}
	            	.icon_download{
	            		background-image: url(assets/images/icon_download1.png);
	            	}
	            	.icon_account{
	            		background-image: url(assets/images/icon_account1.png);
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
	/*注册登录弹窗*/
	.loginShow {
		position: fixed;
		top: 40%;
		left: 40%;
	}
	.resgisterShow {
		position: fixed;
		top: 40%;
		left: 40%;
	}
	iframe {
		width: auto; 
		height: auto; 
		position: fixed; 
		bottom: 0;
		right: 0;
		z-index: -1;
		border: none;
	}
</style>