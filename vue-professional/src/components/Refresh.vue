<template>
	<div id="refresh">
		<button @tap="refresh"></button>
	</div>
</template>

<script>
	export default{
		name:'refresh',
		methods:{
			refresh: function() {
				window.location.reload();
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
#refresh{
	position: fixed;
}
	/*ip5*/
@media(max-width:370px) {
#refresh button{
		width: 17px*@ip5;
		height: 17px*@ip5;
		background-color: transparent;
		border: none;
		outline: none;
		background-image: url('../assets/img/refresh.png');
		background-size: 100% 100%;
		z-index: 1000;
	}
}
/*ip6*/
@media (min-width:371px) and (max-width:410px) {
    #refresh button{
		width: 17px*@ip6;
		height: 17px*@ip6;
		background-color: transparent;
		border: none;
		outline: none;
		background-image: url('../assets/img/refresh.png');
		background-size: 100% 100%;
		z-index: 1000;
	}
}
/*ip6p及以上*/
@media (min-width:411px) {
    #refresh button{
		width: 17px;
		height: 17px;
		background-color: transparent;
		border: none;
		outline: none;
		background-image: url('../assets/img/refresh.png');
		background-size: 100% 100%;
		z-index: 1000;
	}
}
</style>
