<template>
	<div id="forgetPassword">
		<p><i class="ifont ifont_left">&#xe625;</i>忘记密码<i class="ifont ifont_right">&#xe624;</i></p>
		<input type="text" class="input_1"  placeholder="请输入手机号"/>
		<input type="text" class="input_2 input_4"  placeholder="验证码"/>
		<i class="span_code">获取验证码</i>
		<label class="span_ms">60s</label>
		<button class="btn blue">下一步</button>
		<p class="color_light">还没有期货大赛账号？<span class="span_yellow">立即注册</span></p>
	</div>
</template>

<script>
	export default {
		name : "forgetPassword"
	}
</script>

<style lang="scss" scoped type="text/css">
	@import "../../assets/css/common.scss";
	#forgetPassword {
		width: 400px;
		height: 300px;
		text-align : center;
		color :$lightblue ;
		background-color: $blue;
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
		label {
			font-size: $fs16;
		}
		.btn {
			width: 320px;
			height: 40px;	
			margin-top: 20px;
			margin-right: 40px;
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
		.input_4 {
			position: relative;
			left: 30px;
		}
		.span_ms {
			position: relative;
			top: -50px;
			left: 360px;
			color: $white;
		}
		.color_light {
			color: $lightblue;
			margin-top: 10px;
		}
		.span_yellow {
			color: $yellow;
		}
	}
</style>