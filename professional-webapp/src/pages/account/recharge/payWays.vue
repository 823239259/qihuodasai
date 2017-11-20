<template>
<div id="payWays">
	<div class="bg"></div>
	<div class="payWays">
		<div class="page_cont">
			<iframe :src="iframe"></iframe>
		</div>
	</div>
</div>
</template>
<script>
	export default{
		name:"payWays",
		data(){
			return{
				
			}
		},
		computed: {
			iframe(){
				return 'http://pay.duokongtai.cn/app/appPayinfo?mobile='+ this.$route.query.username +'&money='+ this.$route.query.money;
			}
		},
		mounted:function(){
			//初始化高度
			var h = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;
			var _h = h-47
			var contH = $(".payWays").height();
			if(contH > _h){
				$(".payWays").height(_h);
			}
			$(window).resize(function(){
				var h = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;
				var _h = h-47
				if(contH > _h){
					$(".payWays").height(_h);
				}
			});
		}
	}
</script>

<style lang="scss" scoped type="text/css">
	@import "../../../assets/css/common.scss";
	#payWays{
		width: 100%;
		height: 780px;
	}
	.bg{
		background: url(../../../assets/images/recharge_bak.png)  no-repeat;
		opacity:1;
		vertical-align:middle;
	}
	.payWays{
		overflow-y: scroll;
		position: absolute;
		z-index: 120;
		left: 40%;
		margin: auto;
		.page_cont{
			iframe{
				width: 100%;
				height: 686px;
				border: none;
			}
		}
	}
	
</style>