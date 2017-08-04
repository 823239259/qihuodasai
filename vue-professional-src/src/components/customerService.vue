<template>
	<div id="customerService">
		<button @tap='customerService' id='btn'>{{title}}</button>
	</div>
</template>

<script>
	export default{
		name:'customerService',
		props: ['title', 'type'],
		methods: {
			customerService: function() {
				if(this.type != 1){
					this.$router.push({path: '/service'});
				}else{
					return false;
				}
			}
		}
	}
</script>

<style scoped lang="less">
@import url("../assets/css/main.less");
@import url("../assets/css/base.less");
/*ip6p及以上*/
@media (min-width:411px) {
    button{
    	height: 50px;
		padding: 0 15px;
		overflow: hidden;
		border: none;
		background: none;
		color: #fff;
		font-size: @fs14;
		position: fixed;
		top: 0;
		right: 0;
		z-index: 1000;
    }
}
/*ip6*/
@media (min-width:371px) and (max-width:410px) {
    button{
    	height: 50px*@ip6;
		padding: 0 15px*@ip6;
		overflow: hidden;
		border: none;
		background: none;
		color: #fff;
		font-size: @fs14*@ip6;
		position: fixed;
		top: 0;
		right: 0;
		z-index: 1000;
    }
}
/*ip5*/
@media(max-width:370px) {
	button{
		height: 50px*@ip5;
		padding: 0 15px*@ip5;
		overflow: hidden;
		border: none;
		background: none;
		color: #fff;
		font-size: @fs14*@ip5;
		position: fixed;
		top: 0;
		right: 0;
		z-index: 1000;
	}
}	
</style>