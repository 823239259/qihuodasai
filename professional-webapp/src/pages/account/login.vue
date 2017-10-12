<template>
	<div id="login">
		<p>登录<i class="ifont">&#xe624;</i></p>
		<input type="text"  class="input_1" placeholder="请输入手机号码" />
		<input type="password"  class="input_2" placeholder="请输入密码" />
		<p class="span_right" v-on:click="toForgetPassword">忘记密码?</p>
		<button class="btn yellow">登录</button>
		<p class="color_light">还没有期货大赛账号？<span class="span_white">立即注册</span></p>
		
	</div>
</template>
<script>
	import FORGETPASSWORD from "./pages/account/forgetPassword.vue"
	export default {
		name : "LOGIN",
		data(){
			return {
				isshow_forgetPassword : false
			}
		},
		methods : {
			toForgetPassword : function(){
				
			}
		}
	}
</script>
<style lang="scss" scoped type="text/css">
	@import "../../assets/css/common.scss";
	#login {
		width: 400px;
		height: 330px;
		background-color: $blue;
		text-align : center;
		p {
			line-height : 40px;
			color:$white;
			&:nth-child(1) {
				height: 40px;
				background-color: $bottom_color;
			}
			&:nth-child(3) {
				color: $lightblue;
			}
		}
		input {
			width: 320px;
			height: 40px;
		}
		.ifont {
			float: right;
			margin-right: 10px;
			color: $lightblue;
		}
		.span_right {
			text-align : right;
			margin-right : 34px;
		}
		.btn {
			width : 320px;
			height :40px;
		}
		.color_light {
			color: $lightblue;
			margin-top: 10px;
		}
		.span_white {
			color: $white;
		}
	}
</style>