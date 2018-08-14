<template>
	<div id="openAccount_success">
		<div class="openAccount_top">
			<img src="../../assets/images/icon_openaccount_03.png"/>
		</div>
		<div class="openAccount_step3">
			<div class="openAccount_step3_top">
					<img src="../../assets/images/icon_openAccount_3.png" alt="" />
					<p class="classweight">开户成功！账户将以短信形式通知您</p>
					<p>系统将在30分钟内下发操盘账户，超时请联系客服处理。</p>
					<button class="btn blue" v-on:click="to_trade">立即操盘</button>
					<button class="btn blue" v-on:click="to_hq">查看行情</button>
			</div>
			<div class="openAccount_step3_btm">
				<p>投资有风险，入市需谨慎</p>				
			</div>
		</div>
	</div>
</template>
<script>
	import pro from "../../assets/js/common.js"
	export default{
		name:"openAccount_success",
		data(){
			return{
				
			}
		},
		methods:{
			to_hq:function(){
				this.$router.push({path:"/"});
				this.$store.state.account.currentNav = 0;
				localStorage.currentNav = 0;
			},
			to_trade:function(){
				this.$router.push({path:"/trade"});
				this.$store.state.account.currentNav = 1;
				localStorage.currentNav = 1;
			}
		}
	}
</script>
<style lang="scss" scoped type="text/css">
	@import "../../assets/css/common.scss";
	#openAccount_success{
		width: 80%;
		margin: auto;
	}
	.openAccount_top {
		text-align: center;
		height: 140px;
		background-color: $blue;
		img {
			width: 720px;
			height: 60px;
			margin-top: 40px;
		}
	}
	.openAccount_step3_top {
		margin-top: 10px;
	 	width: 100%;
	 	height: 400px;
	 	background-color: $blue;
	 	text-align: center;
	 	.btn {
	 		width: 120px;
	 		height: 30px;
	 		margin-top: 30px;
	 		margin-right: 30px;
	 	}
	 	img{
	 		margin-top: 80px;
	 	}
	 }
	 .openAccount_step3_btm {
	 	p{
	 		height: 40px;
	 		background-color: $bottom_color;
	 		line-height: 40px;
	 		margin-bottom: 50px;
	 		text-align: center;
	 		font-size: $fs12;
	 	}
	 }
	 .classweight{
	 	font-size: $fs20;
	 	font-weight: 500;
	 	color: $white;
	 	margin-top: 30px;
	 	margin-bottom: 20px;
	 }
</style>