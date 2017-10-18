<template>
	<div id="novice">
		<div class="shade"></div>
		<div class="img img_four current" @tap="tapEvent"><img src="../assets/img/help_04.png"/></div>
		<div class="img img_five" @tap="tapEvent"><img src="../assets/img/help_05.png"/></div>
		<div class="img img_six" @tap="tapEvent"><img src="../assets/img/help_06.png"/></div>
		<div class="img img_seven" @tap="tapEvent"><img src="../assets/img/help_07.png"/></div>
		<div class="img img_eight" @tap="tapEvent"><img src="../assets/img/help_08.png"/></div>
	</div>
</template>

<script>
	export default {
		name: 'novice',
		computed: {},
		methods:{
			tapEvent: function(e){
				var index = $(e.currentTarget).index();
				$(e.currentTarget).removeClass('current').next().addClass('current');
				if(index == 5){
					this.$router.push({path: 'tradeLogin'});
				}
			}
		},
		mounted: function(){
			document.getElementById("novice").addEventListener('touchmove', function(e){
				e.preventDefault();
			});
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
	.img_four{
		top: 0;
	}
	.img_five{
		top: -28px;
	}
	.img_six{
		top: -20px;
	}
	.img_seven{
		top: -20px;
	}
	.img_eight{
		top: 0;
	}
}
/*ip6*/
@media (min-width:371px) and (max-width:410px) {
	.img_four{
		top: 0;
	}
	.img_five{
		top: -28px*@ip6;
	}
	.img_six{
		top: -20px*@ip6;
	}
	.img_seven{
		top: -20px*@ip6;
	}
	.img_eight{
		top: 0;
	}
}
/*ip5*/
@media(max-width:370px) {
	.img_four{
		top: 0;
	}
	.img_five{
		top: -28px*@ip5;
	}
	.img_six{
		top: -20px*@ip5;
	}
	.img_seven{
		top: -20px*@ip5;
	}
	.img_eight{
		top: 0;
	}
}
</style>