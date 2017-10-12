<template>
	<div id="forgetPassword">
		<p><i class="ifont ifont_left">&#xe625;</i>忘记密码<i class="ifont ifont_right">&#xe624;</i></p>
		<input type="text" class="input_1"  placeholder="请输入手机号"/>
		<input type="text" class="input_2"  placeholder="验证码"/>
		<label >60s</label>
		<button>下一步</button>
		<p>没有期货大赛账号？<span>立即注册</span></p>
		
	</div>
</template>

<script>
	export default {
		name : "FORGETPASSWORD"
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
	}
</style>