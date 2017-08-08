<template>
	<div id="topbar">
		<h3>{{title}}<span v-show="connected">(未连接)</span></h3>
	</div>
</template>

<script>
	export default {
		name: 'topbar',
		props: ['title', 'connected'],
		mounted: function() {
			var width = parseInt($('#topbar').css('width'));
			var height = width * 0.128;

			//			$('#topbar').css({'height':height+'px','lineHeight':height+'px'});
			//			$('h3').css({'lineHeight':height+'px'})
		}
	}
</script>

<style scoped lang="less">
	@import url("../assets/css/main.less");
	@import url("../assets/css/base.less");
	/*使用该组件的时候，请在挂载的页面ID样式加上padding-top:50px*/
	/*ip5*/
	
	@media(max-width:370px) {
		#topbar {
			width: 100%;
			height: 50px*@ip5;
			border-bottom: 1px solid @black;
			background-color: #242633;
			text-align: center;
			position: fixed;
			top: 0;
			z-index: 999;
		}
		#topbar h3 {
			color: #fff;
			font-size: 17px*@ip5;
			font-weight: normal;
			height: 50px*@ip5;
			line-height: 50px*@ip5;
		}
	}
	/*ip6*/
	
	@media (min-width:371px) and (max-width:410px) {
		#topbar {
			width: 100%;
			height: 50px*@ip6;
			border-bottom: 1px solid @black;
			background-color: #242633;
			text-align: center;
			position: fixed;
			top: 0;
			z-index: 999;
		}
		#topbar h3 {
			color: #fff;
			font-size: 17px*@ip6;
			font-weight: normal;
			height: 50px*@ip6;
			line-height: 50px*@ip6;
		}
	}
	/*ip6p及以上*/
	
	@media (min-width:411px) {
		#topbar {
			width: 100%;
			height: 50px;
			border-bottom: 1px solid @black;
			background-color: #242633;
			text-align: center;
			position: fixed;
			top: 0;
			z-index: 999;
		}
		#topbar h3 {
			color: #fff;
			font-size: 17px;
			font-weight: normal;
			height: 50px;
			line-height: 50px;
		}
	}
</style>