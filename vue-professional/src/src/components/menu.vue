<template>
	<div id="menus">
		<div>
			<div class="icon" @tap='openmenu'>

			</div>
			<transition name="topdown" mode="out-in">
			<div class="menulist" v-show="open">
				<ul>
					<li class="fontgray">条件单</li>
					<li class="fontgray">止损止盈</li>
					<li class="fontgray">资金详情</li>
					<li class="fontgray">历史成交</li>
					<li class="fontgray">切换账号</li>
				</ul>
			</div>
			</transition>
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
				open: false
			}
		},
		methods: {
			openmenu: function(e) {
				if(this.open == false) {
					this.open = true;
					$(e.target).css('background-image', 'url(' + url1 + ')');
				} else {
					this.open = false;
					$(e.target).css('background-image', 'url(' + url2 + ')');
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
	/*ip5*/
	
	@media(max-width:370px) {
		.icon {
			width: @icon_width*@ip5;
			height: @icon_height*@ip5;
			background-image: url('../assets/img/menu.png');
			background-size: 100% 100%;
		}
		ul {
			border-radius: 3px*@ip5;
			background-color: #242633;
			box-shadow: 1px 1px 1px rgba(0, 0, 0, 0.5), -1px -1px 1px rgba(0, 0, 0, 0.5);
			position: absolute;
			bottom: (-@li_height*5-15px)*@ip5;
			right: 0;
		}
		li {
			width: @li_width*@ip5;
			height: @li_height*@ip5;
			border-bottom: 1px solid #1f202c;
			line-height: @li_height*@ip5;
			text-align: center;
			font-size: 14px*@ip5;
		}
	}
	/*ip6*/
	
	@media (min-width:371px) and (max-width:410px) {
		.icon {
			width: @icon_width*@ip6;
			height: @icon_height*@ip6;
			background-image: url('../assets/img/menu.png');
			background-size: 100% 100%;
		}
		ul {
			border-radius: 3px*@ip6;
			background-color: #242633;
			box-shadow: 1px 1px 1px rgba(0, 0, 0, 0.5), -1px -1px 1px rgba(0, 0, 0, 0.5);
			position: absolute;
			bottom: (-@li_height*5-15px)*@ip6;
			right: 0;
		}
		li {
			width: @li_width*@ip6;
			height: @li_height*@ip6;
			border-bottom: 1px solid #1f202c;
			line-height: @li_height*@ip6;
			text-align: center;
			font-size: 14px*@ip6;
		}
	}
	/*ip6p及以上*/
	
	@media (min-width:411px) {
		.icon {
			width: @icon_width;
			height: @icon_height;
			background-image: url('../assets/img/menu.png');
			background-size: 100% 100%;
		}
		ul {
			border-radius: 3px;
			background-color: #242633;
			box-shadow: 1px 1px 1px rgba(0, 0, 0, 0.5), -1px -1px 1px rgba(0, 0, 0, 0.5);
			position: absolute;
			bottom: -@li_height*5-15px;
			right: 0;
		}
		li {
			width: @li_width;
			height: @li_height;
			border-bottom: 1px solid #1f202c;
			line-height: @li_height;
			text-align: center;
			font-size: 14px;
		}
	}
</style>