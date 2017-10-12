<template>
	<div id="login">
		<p>登录<i class="ifont"></i></p>
		<input type="text"  class="input_1" placeholder="请输入手机号码" />
		<input type="password"  class="input_2" placeholder="请输入密码" />
		<p>忘记密码</p>
		<button class="btn">登录</button>
		<p>还没有期货大赛账号？<span>立即注册</span></p>
	</div>
</template>
<script>
	export default {
		name : "LOGIN"
	}
</script>
<style lang="scss" scoped type="text/css">
	@import "../../assets/css/common.scss";
	#login {
		width: 400px;
		height: 330px;
		background-color: $blue;
		text-align:center;
		p {
			line-height : 40px;
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
	}
</style>