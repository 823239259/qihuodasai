<template>
	<div id="refresh">
		<button @tap="refresh"></button>
	</div>
</template>

<script>
	export default {
		name: 'refresh',
		methods: {
			refresh: function() {
				this.$router.push({
					path:'/space'
				});
			},
		},
		mounted: function() {
			$('#refresh').css({
				'transition': '1s linear',
				'transform': 'rotate(360deg)'
			});
		}
	}
</script>

<style scoped lang="less">
	@import url("../assets/css/main.less");
	#refresh {
		position: fixed;
	}
	/*ip5*/
	
	@media(max-width:370px) {
		#refresh button {
			width: 50px*@ip5;
			height: 50px*@ip5;
			background-color: transparent;
			border: none;
			outline: none;
			background: url('../assets/img/refresh.png') no-repeat 16px*@ip5 16px*@ip5;
			background-size: 18px*@ip5 18px*@ip5;
			z-index: 1000;
		}
	}
	/*ip6*/
	
	@media (min-width:371px) and (max-width:410px) {
		#refresh button {
			width: 50px*@ip6;
			height: 50px*@ip6;
			background-color: transparent;
			border: none;
			outline: none;
			background: url('../assets/img/refresh.png') no-repeat 16px*@ip6 16px*@ip6;
			background-size: 18px*@ip6 18px*@ip6;
			z-index: 1000;
		}
	}
	/*ip6p及以上*/
	@media (min-width:411px) {
		#refresh button {
			width: 50px;
			height: 50px;
			background-color: transparent;
			border: none;
			outline: none;
			background: url('../assets/img/refresh.png') no-repeat 16px 16px;
			background-size: 18px 18px;
			z-index: 1000;
		}
	}
</style>