<template>
<div id="payWays">
	<div class="bg"></div>
	<div class="payWays">
		<div class="page_cont">
			<div class="back">
				<h3 v-on:click="back">点击返回</h3>
			</div>
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
				accountMoney:''
			}
		},
		computed: {
			iframe(){
				return 'http://pay.duokongtai.cn/app/appPayinfo?mobile='+ this.$route.query.username +'&money='+ this.$route.query.money;
			}
		},
		methods:{
			back:function(){
				this.$router.push({path:'/recharge',query:{accountMoney:this.accountMoney}});
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
			this.accountMoney = this.$route.query.accountMoney;
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
		width: 600px;
		height: 800px;
		z-index: 120;
		position: absolute;
		top: 50%;
		left: 50%;
		margin: -400px 0 0 -300px;
		text-align: center;
		.page_cont{
			iframe{
				width: 400px;
				height: 560px;
				border: none;
				position: relative;
				top: 40px;
			}
			.back{
				text-align: left;
				height: 40px;
				width: 400px;
				background-color: black;
				line-height: 40px;
				color: $white;
				position: absolute;
				left: 100px;
				h3{
					text-indent: 10px;
					font-weight: 500;
					&:hover{
						color: $yellow;
						cursor:pointer;
					}
				}
			}
		}
	}
	
</style>