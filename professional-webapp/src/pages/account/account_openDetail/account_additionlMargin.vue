<template>
	<div id="additionlMargin">
		<div class="bg"></div>
		<div class="additionlMargin">
			<div class="title">
				<span>交易账号查询</span>
				<i class="ifont" v-on:click="close">&#xe624;</i>
			</div>
			<div class="center">
				<p>交易账号：<span>1008611</span></p>
				<p>交易密码：<span>a123456</span>（请妥善保管您的密码）</p>
				<p>交易细则：<span>操盘细则</span></p>
				<button class="btn yellow" v-on:click="toTradeLogin">立即操盘</button>
				<button class="btn green" v-on:click="back">取消</button>
			</div>
		</div>
	</div>
</template>

<script>
	export default {
		name : "openDetail_additionlMargin",
		data(){
			return{
				
			}
		},
		methods:{
			close : function(){
				
			},
			back:function(){
				
			},
			toTradeLogin:function(){
				
			}
		},
		mounted:function(){
			
		}
	}
</script>

<style lang="scss" scoped type="text/css">
@import "../../../assets/css/common.scss";
	.additionlMargin {
		position: fixed;
		/*top: 200px;*/
		left: 50%;
		top: 50%;
		margin: -200px 0 0 -120px;
		z-index: 100;
		width: 400px;
		height: 240px;
		background-color: $blue;
		overflow:visible;
		border-radius: 10px;
	}
	.title {
		height: 40px;
		background-color: $bottom_color;
		line-height: 40px;
		color: $white;
		text-align: center;
		border-radius: 10px;
	}
	.ifont {
		font-size: $fs16;
		color: #7a8199;
		float: right;
		margin-right: 5px;
	}
	.center {
		p {
			margin-top: 20px;
			font-size: $fs14;
			margin-left: 30px;
			span {
				color: $white;
			}
		}
		.btn {
			margin-top: 20px;
			width: 90px;
			height: 30px;
			margin-left: 20%;
		}
	}
</style>