<template>
	<div id="guide">
		<div class="mui-slider">
			<div class="mui-slider-group">
				<div class="mui-slider-item">
					<img src="../assets/img/guide01.png" />
				</div>
				<div class="mui-slider-item">
					<img src="../assets/img/guide02.png" />
				</div>
				<div class="mui-slider-item">
					<img src="../assets/img/guide03.png" @tap='toHome' @swipeleft='toHome'/>
				</div>
			</div>
		</div>
	</div>
</template>

<script>
	export default {
		name: 'guide',
		methods:{
			toHome:function(){
				localStorage.guideshow = '关闭';
				this.$store.state.isshow.guideshow=false;
			}
		}
	}
</script>

<style scoped lang="less">
	@import url("../assets/css/main.less");
	#guide{
		position: fixed;
		top: 0;
		z-index: 1000;
	}
</style>