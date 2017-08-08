<template>
	<div id="space">

	</div>
</template>

<script>
	export default {
		name: 'space',
		activated:function(){
			this.$router.go(-1);
		}
	}
</script>

<style scoped lang="less">
	@import url("../assets/css/main.less");
	/*ip5*/
	#space{
		background: #242633;
	}
	@media(max-width:370px) {
		#space {
			width: 100%;
			height: 414px*@ip5;
		}
	}
	/*ip6*/
	
	@media (min-width:371px) and (max-width:410px) {
		#space {
			width: 100%;
			height: 414px*@ip6;
		}
	}
	/*ip6p及以上*/
	
	@media (min-width:411px) {
		#space {
			width: 100%;
			height: 414px;
		}
	}
</style>