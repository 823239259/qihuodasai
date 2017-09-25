<template>
	<div id="novice">
		<div class="shade"></div>
		<div class="img img_nine current" @tap="tapEvent"><img src="../assets/img/help_09.png"/></div>
	</div>
</template>

<script>
	export default {
		name: 'novice',
		computed: {
			Parameters(){
				return this.$store.state.market.Parameters;
			}
		},
		methods:{
			tapEvent: function(e){
				var index = $(e.currentTarget).index();
				$(e.currentTarget).removeClass('current').next().addClass('current');
				if(index == 1){
					localStorage.helpshow = '关闭';
					this.$store.state.isshow.helpshow = false;
				}
			}
		}
	}
</script>

<style scoped lang="less">
@import url("../assets/css/base.less");
.shade{
	position: absolute;
	top: 0;
	left: 0;
	right: 0;
	bottom: 0;
	z-index: 1112;
	background: #000;
	opacity: 0.7;			
}
.img{
	position: absolute;
	z-index: 1115;
	display: none;
	&.current{
		display: inline;
	}
}
/*ip6p及以上*/
@media (min-width:411px) {
	.img_nine{
		top: -31px;
	}
}
/*ip6*/
@media (min-width:371px) and (max-width:410px) {
    .img_nine{
		top: -31px*@ip6;
	}
}
/*ip5*/
@media(max-width:370px) {
	.img_nine{
		top: -31px*@ip5;
	}
}
</style>