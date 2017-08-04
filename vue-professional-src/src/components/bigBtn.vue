<template>
	<button id="bigBtn" class="btn" @touchstart="lontap" @touchend='levtap'>{{name}}</button>
</template>

<script>
	export default{
		name:'bigBtn',
		props: ['name'],
		methods: {
			lontap:function(){
				$(this.$el).addClass("current");
			},
			levtap:function(){
				$(this.$el).removeClass("current");
			}
		}
	}
</script>

<style scoped lang="less">
@import url("../assets/css/main.less");
@import url("../assets/css/base.less");
/*ip6p及以上*/
@media (min-width:411px) {
    #bigBtn{
    	width: 92.75%;
    	height: 54px;
    	line-height: 54px;
    	overflow: hidden;
    	background: @yellow;
    	border: 1px solid @black;
    	border-radius: 27px;
    	color: @black;
    	font-size: @fs18;
    	text-align: center;
    	margin: 0 3.625%;
    	&.current{
			background: #ccaa00;
    	}
    }
}
/*ip6*/
@media (min-width:371px) and (max-width:410px) {
    #bigBtn{
    	width: 92.75%;
    	height: 54px*@ip6;
    	line-height: 54px*@ip6;
    	overflow: hidden;
    	background: @yellow;
    	border: 1px solid @black;
    	border-radius: 27px*@ip6;
    	color: @black;
    	font-size: @fs18*@ip6;
    	text-align: center;
    	margin: 0 3.625%;
    	&.current{
			background: #ccaa00;
    	}
    }
}
/*ip5*/
@media(max-width:370px) {
	#bigBtn{
    	width: 92.75%;
    	height: 54px*@ip5;
    	line-height: 54px*@ip5;
    	overflow: hidden;
    	background: @yellow;
    	border: 1px solid @black;
    	border-radius: 27px*@ip5;
    	color: @black;
    	font-size: @fs18*@ip5;
    	text-align: center;
    	margin: 0 3.625%;
    	&.current{
			background: #ccaa00;
    	}
    }
}
</style>