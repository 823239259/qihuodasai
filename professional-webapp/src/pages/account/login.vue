<template>
	<div id="login">
		<p>登录<i class="ifont ifont_x">&#xe624;</i></p>
		<input type="text"  class="input_1" placeholder="请输入手机号码" />
		<input type="password"  class="input_2" placeholder="请输入密码" /><i class=" ifont ifont_eye">&#xe64f;</i>
		<p class="span_right" v-on:click="toForgetPassword">忘记密码?</p>
		<button class="btn yellow">登录</button>
		<p class="color_light">还没有期货大赛账号？<span class="span_white">立即注册</span></p>
		<FORGETPASSWORD class="forgetPassword_show" v-if="isshow_forgetpassword" />
	</div>
</template>
<script>
	import FORGETPASSWORD from "./forgetPassword.vue"
	export default {
		name : "LOGIN",
		components : {FORGETPASSWORD},
		data(){
			return {
				isshow_forgetpassword : false
			}
		},
		methods : {
			toForgetPassword : function(){
				this.isshow_forgetpassword=!this.isshow_forgetpassword;
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
		.input_2 {
			margin-left: 25px;
		}
		.ifont_x {
			float: right;
			margin-right: 10px;
			color: $lightblue;
		}
		.ifont_eye {
			z-index: 2;
			color: $lightblue;
			font-size: 30px;
			position: relative;
			top: 20px;
			right: 35px;
			
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
	.forgetPassword_show {
		position: fixed;
		top: 300px;
		left: 200px;
	}
</style>