<template>
	<div id="forgetPassword" >
		<p><i class="ifont ifont_left">&#xe625;</i>设置密码<i class="ifont ifont_right">&#xe624;</i></p>
		<input type="password"  class="input_1" placeholder="请输入新密码" />
		<input type="password" class="input_2"  placeholder="确认新密码" />
		<button class="btn yellow">确认</button>
		<p class="color_light">还没有期货大赛账号？<span class="span_white">立即注册</span></p>
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
			line-height : 40px;
			&:nth-child(1) {
				height: 40px;
				background-color: $bottom_color;
			}
		}
		input {
			width: 320px;
			height: 40px;
		}
		.btn {
			width: 320px;
			height: 40px;
			margin-top: 20px;
		}
		.color_light {
			color: $lightblue;
			margin-top: 10px;
		}
		.span_white {
			color: $white;
		}
		.ifont {
			color: $lightblue;
		}
		.ifont_left {
			float: left;
			margin-left: 10px;
		}
		.ifont_right {
			float: right;
			margin-right: 10px;
		}
	}
</style>