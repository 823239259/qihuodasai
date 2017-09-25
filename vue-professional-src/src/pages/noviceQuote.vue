<template>
	<div id="novice">
		<div class="shade"></div>
		<div class="img img_one current" @tap="tapEvent"><img src="../assets/img/help_01.png"/></div>
		<div class="img img_two" @tap="tapEvent"><img src="../assets/img/help_02.png"/></div>
		<div class="img img_three" @tap="tapEvent"><img src="../assets/img/help_03.png"/></div>
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
				if(index == 3){
					this.Parameters.forEach(function(e){
						if(e.CommodityName == '小恒指'){
							this.$store.state.market.currentdetail = e;
						}
					}.bind(this));
					this.$router.push({path: '/orderdetail'});
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
	.img_one, .img_two{
		top: -18px;
	}
	.img_three{
		top: 250px;
	}
}
/*ip6*/
@media (min-width:371px) and (max-width:410px) {
    .img_one, .img_two{
		top: -18px*@ip6;
	}
	.img_three{
		top: 250px*@ip6;
	}
}
/*ip5*/
@media(max-width:370px) {
	.img_one, .img_two{
		top: -18px*@ip5;
	}
	.img_three{
		top: 250px*@ip5;
	}
}
</style>