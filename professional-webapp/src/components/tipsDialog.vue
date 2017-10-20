<template>
	<div id="tipsDialog" v-if="isShow">{{msg}}</div>
</template>

<script>
	export default{
		name:'tipsDialog',
		props: ['msg','time'],
		data(){
			return {
				isShow: false,
				times: 1000
			}
		},
		updated: function(){
			if(this.time) this.times = this.time;
			if(this.isShow == true){
				setTimeout(function(){
					this.isShow = false;
				}.bind(this), this.times);
			}
		}
	}
</script>

<style lang="scss" scoped type="text/css">
	@import "../assets/css/common.scss";
	#tipsDialog {
		width: 200px;
		height: 30px;
		position: absolute;
		top: 30%;
		left: 40%;
		z-index: 110;
		color: $white;
		background-color: $blue;
		border-radius: 10px;
		line-height: 30px;
		font-size: $fs14;
	}
</style>