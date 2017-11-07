<template>
	<div id="loading" v-if="isshow">
		<div class="bg"></div>
		<div class="img">
			<img src="../assets/images/loading.gif" />
		</div>
	</div>
</template>

<script>
	export default{
		name: 'loading',
		data(){
			return{
				isshow: false,
			}
		}
		
	}
</script>

<style lang="scss" scoped type="text/css">
	@import "../assets/css/common.scss";
	.bg{
		width: 100%;
		height: 100%;
		overflow: hidden;
		background: #000;
		opacity: 0.5;
		position: absolute;
		top: 0;
		left: 0;
		right: 0;
		bottom: 0;
		z-index: 2;		
	}
	.img{
		width: 130px;
		height: 30px;
		overflow: hidden;
		position: absolute;
		top: 50%;
		left: 50%;
		z-index: 3;
		margin: -15px 0 0 -65px;
	}
</style>