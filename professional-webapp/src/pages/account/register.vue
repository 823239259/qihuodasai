<template>
	<div id="register">
		<div class="bg"></div>
		<div class="register">
			<p>注册<i class="ifont">&#xe624;</i></p>
			<input type="text"  class="input_1" placeholder="请输入手机号" />
			<input type="password" name="input_2" class="input_2 input_4" placeholder="验证码" /><i class="span_code">获取验证码</i>
			<input type="password" name="input_3" class="input_2 input_5" placeholder="请输入密码（6-16位密码）" /><i class="ifont ifont_eyes">&#xe61c;</i>
			<p class="color_light">注册即表示	同意并已阅读<span class="span_white">《用户注册协议》</span></p>
			<button class="btn yellow">注册</button>
			<p class="color_light">已有期货大赛账号？<span class="span_white">立即登录</span></p>
		</div>
		
	</div>
</template>
<script>
	export default {
		name : "register"
	}
</script>
<style lang="scss" scoped type="text/css">
	@import "../../assets/css/common.scss";
	.register {
		position: absolute;
		top: 30%;
		left: 40%;
		width: 400px;
		height: 390px;
		background-color: $blue;
		text-align : center;
		color :$lightblue ;
		z-index : 100;
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
		.input_4 {
			position: relative;
			left: 30px;
		}
		.span_code {
			position: relative;
			right: 40px;
			top: 10px;
			color: $white;
			font-size: $fs12;
			display: inline-block;
			width: 60px;
			height: 20px;
		}
		.btn {
			width : 320px;
			height :40px;
		}
		.ifont {
			float: right;
			margin-right: 10px;
			color: $lightblue;
		}
		.ifont_eyes {
			font-size: $fs20;
			position: relative;
			top: 30px;
			right: 40px;
			color: $lightblue;
		}
		.input_5 {
			margin-left: 26px;
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