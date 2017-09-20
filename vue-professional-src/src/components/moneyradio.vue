<template>
	<div id="moneyradio">
		<div class="">
			{{num}}<span>元</span>
		</div>
	</div>
</template>

<script>
	export default {
		name: 'moneyradio',
		props: ['num'],
		computed:{
			indent:function(){
				if(this.num.length<5){
					return '10px'
				}else if(this.num.length<6){
					return '3px'
				}else{
					return '0'
				}
			}
		},
		mounted:function(){
			$(this.$el).css('text-indent',this.indent);
		}
	}
</script>

<style scoped lang="less">
	@import url("../assets/css/main.less");
	@import url("../assets/css/base.less");
	#moneyradio {
		width: 75px;
		height: 40px;
	}
	#moneyradio div {
		/*text-align: center;*/
		padding-left: 10%;
		width: 75px;
		height: 40px;
		color: #949bbb;
		background-image: url(../assets/img/goug.png);
		background-size: 100% 100%;
		font-size: @fs16;
		line-height: 40px;
		position: relative;
	}
	#moneyradio div span {
		position: absolute;
		top: 3%;
		right: 8%;
		display: block;
		color: inherit;
		font-size: @fs16;
		transform: scale(0.625) translateX(60%);
	}
	#moneyradio div.current {
		color: #ffd400;
		background-image: url(../assets/img/gouy.png);
		background-size: 100% 100%;
	}
	
	/*ip5*/
	@media(max-width:370px) {
		#moneyradio {
			width: 75px*@ip5;
			height: 40px*@ip5;
		}
		#moneyradio div {
			/*text-align: center;*/
			width: 75px*@ip5;
			height: 40px*@ip5;
			color: #949bbb;
			background-image: url(../assets/img/goug.png);
			background-size: 100% 100%;
			font-size: @fs16*@ip5;
			line-height: 40px*@ip5;
		}
		#moneyradio div span {
			color: inherit;
			font-size: @fs16*@ip5;
			transform: scale(0.625) translateX(60%);
		}
		#moneyradio div.current {
			color: #ffd400;
			background-image: url(../assets/img/gouy.png);
			background-size: 100% 100%;
		}
	}
	
	/*ip6*/
	@media (min-width:371px) and (max-width:410px) {
		#moneyradio {
			width: 75px*@ip6;
			height: 40px*@ip6;
		}
		#moneyradio div {
			/*text-align: center;*/
			width: 75px*@ip6;
			height: 40px*@ip6;
			color: #949bbb;
			background-image: url(../assets/img/goug.png);
			background-size: 100% 100%;
			font-size: @fs16*@ip6;
			border-radius: 3px;
			border: 1px solid #12121a;
			line-height: 40px*@ip6;
		}
		#moneyradio div span {
			color: inherit;
			font-size: @fs16*@ip6;
			transform: scale(0.625) translateX(60%);
		}
		#moneyradio div.current {
			color: #ffd400;
			background-image: url(../assets/img/gouy.png);
			background-size: 100% 100%;
		}
	}
</style>