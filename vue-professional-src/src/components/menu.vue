<template>
	<div id="menus">
		<div>
			<div class="icon icon_menu" @tap='openmenu'></div>
			<div class="shade" v-show="shadeShow" @tap="openShade"></div>
			<!--<transition name="topdown" mode="out-in">-->
			<div class="menulist" v-show="open">
				<ul>
					<li class="fontgray" @tap="toConditions" v-show="hasLoginShow">条件单</li>
					<li class="fontgray" @tap="toStopMoney" v-show="hasLoginShow">止损止盈</li>
					<li class="fontgray" @tap="toMoneyDetails" v-show="hasLoginShow">资金详情</li>
					<li class="fontgray" @tap="toHistoryTrade" v-show="hasLoginShow">历史成交</li>
					<li class="fontgray" @tap.stop="toTradeLogin">{{userTitle}}</li>
				</ul>
			</div>
			<!--</transition>-->
		</div>
		<alert title="提示" :line1="promptMsg" status="1" ref="alert"></alert>
	</div>
</template>

<script>
	var url1 = require('../assets/img/menuo.png');
	var url2 = require('../assets/img/menu.png');
	import alert from './Tradealert.vue'
	export default {
		name: 'menus',
		components: {alert},
		data() {
			return {
				open: false,
				shadeShow: false,
				hasLoginShow: false,
				userTitle: '切换账号',
				promptMsg: '',
				status: 0
			}
		},
		computed: {
			positionListCont(){
				return this.$store.state.market.positionListCont;
			},
		},
		watch: {
			status: function(n, o){
				if(!n) return;
				localStorage.removeItem("tradeUser");
				this.hasLoginShow = false;
				this.userTitle = '登录账号';
				this.$router.push({path:'/tradeLogin'});
			}
		},
		methods: {
			toTradeLogin: function(e){
				if(this.hasLoginShow == true){
					this.$refs.alert.isshow = true;
					if(this.positionListCont.length > 0){
						this.promptMsg = '您当前账户中有持仓合约，确认切换？';
					}else{
						this.promptMsg = '确认切换账户？';
					}
				}else{
					this.$router.push({path:'/tradeLogin'});
				}
				this.open = false;
				this.shadeShow = false;
				$(".icon_menu").css('background-image', 'url(' + url2 + ')');
			},
			toHistoryTrade: function(){
				this.open = false;
				this.shadeShow = false;
				$(".icon_menu").css('background-image', 'url(' + url2 + ')');
				this.$router.push({path:'/historyTrade'});
			},
			toConditions: function(){
				this.open = false;
				this.shadeShow = false;
				$(".icon_menu").css('background-image', 'url(' + url2 + ')');
				this.$router.push({path:'/conditions'});
			},
			toStopMoney: function(){
				this.open = false;
				this.shadeShow = false;
				$(".icon_menu").css('background-image', 'url(' + url2 + ')');
				this.$router.push({path:'/stopMoney'});
			},
			toMoneyDetails: function(){
				this.open = false;
				this.shadeShow = false;
				$(".icon_menu").css('background-image', 'url(' + url2 + ')');
				this.$router.push({path:'/moneyDetails'});
			},
			openmenu: function(e) {
				if(this.open == false) {
					this.open = true;
					this.shadeShow = true;
					$(e.target).css('background-image', 'url(' + url1 + ')');
				} else {
					this.open = false;
					this.shadeShow = false;
					$(e.target).css('background-image', 'url(' + url2 + ')');
				}
			},
			openShade: function(e){
				if(this.open == false) {
					this.open = true;
					this.shadeShow = true;
					$(e.target).siblings('.icon').css('background-image', 'url(' + url1 + ')');
				} else {
					this.open = false;
					this.shadeShow = false;
					$(e.target).siblings('.icon').css('background-image', 'url(' + url2 + ')');
				}
			}
		},
		mounted: function() {
			//判断是否已登录交易账号
			if(localStorage.tradeUser == undefined){
				this.hasLoginShow = false;
				this.userTitle = '登录账号';
			}else{
				this.hasLoginShow = true;
				this.userTitle = '切换账号';
			}
		}
	}
</script>

<style scoped lang="less">
	@import url("../assets/css/main.less");
	@icon_width: 19px;
	@icon_height: 19px;
	@li_width: 88px;
	@li_height: 43px;
	li:last-child {
		border: none;
	}
	.shade{
		position: fixed;
		top: 50px;
		left: 0;
		z-index: 1110;
		width: 100%;
		height: 100%;
		overflow: hidden;
		background: #000;
		/*opacity: 0.3;*/
	}
	/*ip5*/
	@media(max-width:370px) {
		.icon {
			width: 50px*@ip5;
			height: 50px*@ip5;
			background: url('../assets/img/menu.png') no-repeat 15px*@ip5 15px*@ip5;
			background-size: 20px*@ip5 20px*@ip5;
		}
		ul {
			overflow: hidden;
			border-radius: 3px*@ip5;
			background-color: #242633;
			box-shadow: 1px 1px 1px rgba(0, 0, 0, 0.5), -1px -1px 1px rgba(0, 0, 0, 0.5);
			position: absolute;
			top: 51px*@ip5;
			right: 15px*@ip5;
			z-index: 1111;
		}
		li {
			width: @li_width*@ip5;
			height: @li_height*@ip5;
			border-bottom: 1px solid #1f202c;
			line-height: @li_height*@ip5;
			text-align: center;
			font-size: 14px*@ip5;
		}
		.shade{
			position: fixed;
			top: 50px*@ip5;
			left: 0;
			width: 100%;
			height: 100%;
			overflow: hidden;
			background: transparent;
		}
	}
	/*ip6*/
	@media (min-width:371px) and (max-width:410px) {
		.icon {
			width: 50px*@ip6;
			height: 50px*@ip6;
			background: url('../assets/img/menu.png') no-repeat 15px*@ip6 15px*@ip6;
			background-size: 20px*@ip6 20px*@ip6;
		}
		ul {
			border-radius: 3px*@ip6;
			background-color: #242633;
			box-shadow: 1px 1px 1px rgba(0, 0, 0, 0.5), -1px -1px 1px rgba(0, 0, 0, 0.5);
			position: absolute;
			top: 51px*@ip6;
			right: 15px*@ip6;
			z-index: 1111;
		}
		li {
			width: @li_width*@ip6;
			height: @li_height*@ip6;
			border-bottom: 1px solid #1f202c;
			line-height: @li_height*@ip6;
			text-align: center;
			font-size: 14px*@ip6;
		}
		.shade{
			position: fixed;
			top: 50px*@ip6;
			left: 0;
			width: 100%;
			height: 100%;
			overflow: hidden;
			background: transparent;
		}
	}
	/*ip6p及以上*/
	@media (min-width:411px) {
		.icon {
			width: 50px;
			height: 50px;
			background: url('../assets/img/menu.png') no-repeat 15px 15px;
			background-size: 20px 20px;
		}
		ul {
			border-radius: 3px;
			background-color: #242633;
			box-shadow: 1px 1px 1px rgba(0, 0, 0, 0.5), -1px -1px 1px rgba(0, 0, 0, 0.5);
			position: absolute;
			top: 51px;
			right: 15px;
			z-index: 1111;
		}
		li {
			width: @li_width;
			height: @li_height;
			border-bottom: 1px solid #1f202c;
			line-height: @li_height;
			text-align: center;
			font-size: 14px;
		}
		.shade{
			position: fixed;
			top: 50px*@ip6p;
			left: 0;
			width: 100%;
			height: 100%;
			overflow: hidden;
			background: transparent;
		}
	}
</style>