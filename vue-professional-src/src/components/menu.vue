<template>
	<div id="menus">
		<div>
			<div class="icon icon_menu" @tap='openmenu'>

			</div>
			<div class="shade" v-show="shadeShow" @tap="openShade"></div>
			<!--<transition name="topdown" mode="out-in">-->
			<div class="menulist" v-show="open">
				<ul>
					<!--<li class="fontgray">条件单</li>
					<li class="fontgray">止损止盈</li>
					<li class="fontgray">资金详情</li>-->
					<li class="fontgray" @tap="toHistoryTrade">历史成交</li>
					<li class="fontgray" @tap="toTradeLogin">切换账号</li>
				</ul>
			</div>
			<!--</transition>-->
		</div>
	</div>
</template>

<script>
	var url1 = require('../assets/img/menuo.png');
	var url2 = require('../assets/img/menu.png');
	export default {
		name: 'menus',
		data() {
			return {
				open: false,
				shadeShow: false
			}
		},
		methods: {
			toTradeLogin: function(){
				this.open = false;
				this.shadeShow = false;
				$(".icon_menu").css('background-image', 'url(' + url2 + ')');
				this.$router.push({path:'/tradeLogin'});
			},
			toHistoryTrade: function(){
				this.open = false;
				this.shadeShow = false;
				$(".icon_menu").css('background-image', 'url(' + url2 + ')');
				this.$router.push({path:'/historyTrade'});
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
			//			console.log($('*').not('#menus'));
			//			$('*').not('#menus').on('click',function(){
			//				this.open=false;
			//				$('#menus').css('background-image','url('+url2+')');
			//			});
		}
	}
</script>

<style scoped lang="less">
	@import url("../assets/css/main.less");
	@icon_width: 19px;
	@icon_height: 19px;
	@li_width: 88px;
	@li_height: 43px;
	#menus>div {
		position: relative;
	}
	
	li:last-child {
		border: none;
	}
	.shade{
		position: fixed;
		top: 50px;
		left: 0;
		width: 100%;
		height: 100%;
		overflow: hidden;
		background: transparent;
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
			border-radius: 3px*@ip5;
			background-color: #242633;
			box-shadow: 1px 1px 1px rgba(0, 0, 0, 0.5), -1px -1px 1px rgba(0, 0, 0, 0.5);
			position: absolute;
			/*bottom: (-@li_height*5-15px)*@ip5;*/
			top: 51px*@ip5;
			right: 15px*@ip5;
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
			/*bottom: (-@li_height*5-15px)*@ip6;*/
			top: 51px*@ip6;
			right: 15px*@ip6;
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
			/*bottom: -@li_height*5-15px;*/
			top: 51px;
			right: 15px;
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