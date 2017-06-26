<template>
	<div id="back" @tap='goback'>
		<div class="icon">
		</div>
	</div>
</template>

<script>
	export default{
		name:'back',
		methods:{
			goback:function(){
				this.$router.go(-1);
			}
		}
	}
</script>

<style scoped lang="less">
@import url("../assets/css/main.less");
@icon_width:12px;
@icon_height:20px;
.icon{
	background-image: url('../assets/img/back.png') ;
	background-size:100% 100% ;
}
#back{
	width: 40px;
	height: 20px;
}
/*ip5*/
@media(max-width:370px) {
	.icon{
		width: @icon_width*@ip5;
		height: @icon_height*@ip5;
	}
}
/*ip6*/
@media (min-width:371px) and (max-width:410px) {
    .icon{
		width: @icon_width*@ip6;
		height: @icon_height*@ip6;
	}
}
/*ip6p及以上*/
@media (min-width:411px) {
    .icon{
		width: @icon_width;
		height: @icon_height;
	}
}
</style>
