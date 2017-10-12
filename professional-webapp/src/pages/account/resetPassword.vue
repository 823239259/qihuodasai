<template>
	<div id="forgetPassword" >
		<p><span><</span>设置密码<span>x</span></p>
		<input type="password"  class="input_1" placeholder="请输入新密码" />
		<input type="password" class="input_2"  placeholder="确认新密码" />
		<button>确认</button>
		<p>没有期货大赛账号？<span>立即注册</span></p>
	</div>
</template>

<script>
	export default {
		name : "resetPassword"
	}
</script>
<style lang="scss" scoped type="text/css">
	@import "../../assets/css/common.scss";
	#forgetPassword {
		width: 400px;
		height: 300px;
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
			color: $lightblue;
			background-color: $yellow;
			outline: none;
			border-radius: 5px;
		}
	}
</style>