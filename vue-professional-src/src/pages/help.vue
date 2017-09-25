<template>
	<div id="help">
		<topbar title="新手指南"></topbar>
		<back></back>
		<div class="page_cont">
			<img src="../assets/img/help_00.png"/>
		</div>
	</div>
</template>

<script>
	import topbar from '../components/Topbar.vue'
	import back from '../components/back.vue'
	export default{
		name: 'help',
		components: {topbar, back},
		mounted: function(){
			$(".page_cont img").width(window.screen.width);
		}
	}
</script>

<style scoped lang="less">
@import url("../assets/css/base.less");
/*ip6p及以上*/
@media (min-width:411px) {
    #help{
    	width: 100%;
    	overflow: hidden;
    	padding-top: 50px;
    }
}

/*ip6*/
@media (min-width:371px) and (max-width:410px) {
    #help{
    	width: 100%;
    	overflow: hidden;
    	padding-top: 50px*@ip6;
    }
}

/*ip5*/
@media(max-width:370px) {
	#help{
    	width: 100%;
    	overflow: hidden;
    	padding-top: 50px*@ip5;
    }
}

</style>