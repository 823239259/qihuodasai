<template>
	<div id="button">
		<button :class="colornames">{{btnName}}</button>
	</div>
</template>

<script>
	export default{
		name:'button',
		props:['btnName','colorname'],
		computed:{
			colornames:function(){
				if(this.colorname=='black'){
					return 'blackbtn'
				}else if(this.colorname=='yellow'){
					return 'yellowbtn'
				}else if(this.colorname=='gray'){
					return 'graybtn'
				}
			}
		}
	}
</script>

<style scoped lang="less">
@import url("../assets/css/main.less");
button{
	width: 90%;
	height: 40px;
	border-radius: 25px;
	outline: none;
	font-size: 16px;
	margin:0 auto;
	display: block;
}
.blackbtn{
	background-color: #333333;
	color:#fecb45;
	border: 1px solid #333333;
}
.yellowbtn{
	background-color: #fecb45;
	color:#333333;
	border: 1px solid #333333;
}
.graybtn{
	background-color: #c1c1c1;
	color:#333333;
	border: 1px solid #cccccc;
}
</style>
