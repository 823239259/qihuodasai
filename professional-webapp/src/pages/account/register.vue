<template>
	<div id="register">
		<p>注册<span>x</span></p>
		<input type="text"  class="input_1" placeholder="请输入手机号" />
		<input type="password"  class="input_2" placeholder="验证码" />
		<input type="password"  class="input_2" placeholder"请输入密码（6-16位密码）" />
		<p>注册即表示	同意并已阅读<span>《用户注册协议》</span></p>
		<button>注册</button>
		<p>已有期货大赛账号？<span>立即登录</span></p>
	</div>
</template>

<script>
	export default {
		name : "REGISTER"
	}
</script>

<style lang="scss" scoped type="text/css">
	@import "../../assets/css/common.scss";
	#register {
		width: 400px;
		height: 390px;
		background-color: $blue;
		p {
			color:$white;
			&:nth-child(1) {
				height: 40px;
				background-color: $bottom_color;
			}
		}
		input {
			width: 320px;
			height: 40px;
		}
		button {
			width: 400px;
			height: 40px;
			color: $black;
			background-color: $yellow;
			outline: none;
			border-radius: 5px;
		}
	}
</style>