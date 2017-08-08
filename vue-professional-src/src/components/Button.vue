<template>
	<div id="payBtn" :class="className" @touchstart="lontap" @touchend='levtap'>
		<div>
			{{name}}
		</div>
	</div>
</template>

<script>
	export default {
		name: 'payBtn',
		props: ['name','color'],
		methods:{
			lontap:function(){	
				var clname=this.$el.className;
				$(this.$el).removeClass().addClass(clname+'cl');
			},
			levtap:function(){
				var e=this.$el.className.substr(-2,2);
				if(e=='cl'){
					var clname=this.$el.className.slice(0,-2);
					$(this.$el).removeClass().addClass(clname);
				}
			}
		},
		computed:{
			className:function(){
				if(this.color=='gray'){
					return 'graybtn';
				}else if(this.color=='yellow'){
					return 'yellowbtn';
				}
			}
		}
	}
</script>

<style scoped lang="less">
	@import url("../assets/css/main.less");
	#payBtn div{
		border: 1px solid rgba(0,0,0,1);
	}
	.graybtn{
		background-color: #CCD5FF;
	}
	.yellowbtn{
		background-color: #FFD400;
	}
	.yellowbtncl{
		background-color: #CCAA00;
	}
	.graybtncl{
		background-color: #a3aacc;
	}
	/*ip5*/
	
	@media(max-width:370px) {
		div {
			width: 164px*@ip5;
			height: 42px*@ip5;
			color: #1b1b26;
			border-radius: 22px*@ip5;
			text-align: center;
			line-height: 42px*@ip5;
			font-size: 16px*@ip5;
		}
	}
	/*ip6*/
	
	@media (min-width:371px) and (max-width:410px) {
		div {
			width: 164px*@ip6;
			height: 42px*@ip6;
			color: #1b1b26;
			border-radius: 22px*@ip6;
			text-align: center;
			line-height: 42px*@ip6;
			font-size: 16px*@ip6;
		}
	}
	/*ip6p及以上*/
	
	@media (min-width:411px) {
		div {
			width: 164px;
			height: 42px;
			color: #1b1b26;
			border-radius: 22px;
			text-align: center;
			line-height: 42px;
			font-size: 16px;
		}
	}
</style>
