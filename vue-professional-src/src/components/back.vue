<template>
	<div id="back" :class="current" @tap='goback'>
		<div class="icon"></div>
	</div>
</template>

<script>
	export default{
		name:'back',
		props: ['title'],
		computed: {
			current: function(){
				if(this.title == true){
					return 'current';
				}
			}
		},
		methods:{
			goback:function(e){
				if(!$(e.currentTarget).hasClass("current")){
					this.$router.go(-1);
				}else{
					return false;
				}
			}
		}
	}
</script>

<style scoped lang="less">
@import url("../assets/css/main.less");
@icon_width:12px;
@icon_height:20px;
.icon{
	background-image: url('../assets/img/back.png');
	background-size:100% 100% ;
}
#back{
	width: 50px;
	height: 50px;
	overflow: hidden;
	padding: 15px;
	position: fixed;
	top: 0;
	left: 0;
	z-index: 1100;
}
/*ip5*/
@media(max-width:370px) {
	#back{
		width: 50px*@ip5;
		height: 50px*@ip5;
		overflow: hidden;
		/*padding: 15px*@ip5;*/
		position: fixed;
		top: 0;
		left: 0;
		z-index: 1000;
	}
	.icon{
		width: @icon_width*@ip5;
		height: @icon_height*@ip5;
	}
}
/*ip6*/
@media (min-width:371px) and (max-width:410px) {
	#back{
		width: 50px*@ip6;
		height: 50px*@ip6;
		overflow: hidden;
		/*padding: 15px*@ip6;*/
		position: fixed;
		top: 0;
		left: 0;
		z-index: 1000;
	}
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
