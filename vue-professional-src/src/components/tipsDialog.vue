<template>
	<div id="tipsDialog" v-if="isShow">{{msg}}</div>
</template>

<script>
	export default{
		name:'tipsDialog',
		props: ['msg'],
		data(){
			return {
				isShow: false,
			}
		},
		updated: function(){
			if(this.isShow == true){
				setTimeout(function(){
					this.isShow = false;
				}.bind(this), 2000);
			}
		}
	}
</script>

<style scoped lang="less">
@import url("../assets/css/base.less");
/*ip6p及以上*/
@media (min-width:411px) {
    #tipsDialog{
    	position: fixed;
    	bottom: 125px;
    	left: 50%;
    	z-index: 1111;
    	width: 220px;
    	line-height: 24px;
    	padding: 5px 0;
    	text-align: center;
    	overflow: hidden;
    	margin-left: -110px;
    	background: #36394d;
    	color: @white;
    	font-size: @fs14;
    	border-radius: 5px;
    	box-shadow: 0 0 8px @black;
    }
}
/*ip6*/
@media (min-width:371px) and (max-width:410px) {
    #tipsDialog{
    	position: fixed;
    	bottom: 125px*@ip6;
    	left: 50%;
    	z-index: 1111;
    	width: 220px*@ip6;
    	line-height: 24px*@ip6;
    	padding: 5px*@ip6 0;
    	text-align: center;
    	overflow: hidden;
    	margin-left: -110px*@ip6;
    	background: #36394d;
    	color: @white;
    	font-size: @fs14*@ip6;
    	border-radius: 5px*@ip6;
    	box-shadow: 0 0 8px*@ip6 @black;
    }
}
/*ip5*/
@media(max-width:370px) {
	#tipsDialog{
    	position: fixed;
    	bottom: 125px*@ip5;
    	left: 50%;
    	z-index: 1111;
    	width: 220px*@ip5;
    	line-height: 24px*@ip5;
    	padding: 5px*@ip5 0;
    	text-align: center;
    	overflow: hidden;
    	margin-left: -110px*@ip5;
    	background: #36394d;
    	color: @white;
    	font-size: @fs14*@ip5;
    	border-radius: 5px*@ip5;
    	box-shadow: 0 0 8px*@ip5 @black;
    }
}
</style>