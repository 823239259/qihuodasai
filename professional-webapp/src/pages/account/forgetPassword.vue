<template>
	<div id="forgetPassword">
		<div class="bg"></div>
		<div class="forgetPassword" >
			<p><i class="ifont ifont_left" v-on:click="back">&#xe625;</i>忘记密码<i class="ifont ifont_right" v-on:click="close">&#xe624;</i></p>
			<input type="text" id="phone" class="input_1"  placeholder="请输入手机号" v-model="phone"/>
			<input type="text"id="pwd" class="input_2 input_4"  placeholder="验证码" v-model="code"/>
			<i class="span_code" v-on:click="getcode">{{volid ? info : (time+'秒')}}</i>
			<button class="btn blue" v-on:click="toResetPassword" >下一步</button>
			<p class="color_light">还没有期货大赛账号？<span class="span_yellow" v-on:click="toRegister">立即注册</span></p>
		</div>
		<div class="resetPassword">
			<p><i class="ifont ifont_left" v-on:click="back_forget">&#xe625;</i>设置密码<i class="ifont ifont_right" v-on:click="close">&#xe624;</i></p>
			<input type="password" id="pwd" class="input_1" placeholder="请输入新密码"v-model="pwd"  />
			<input type="password" id="newPwd" class="input_2"  placeholder="确认新密码"v-model="newPwd"  />
			<button class="btn yellow" v-on:click="toLogin">确认</button>
			<p class="color_light">还没有期货大赛账号？<span class="span_white" v-on:click="toRegister">立即注册</span></p>
		</div>
		<codeDialog ref="codeDialog" type="findpwd"></codeDialog>
	</div>
</template>

<script>
	import codeDialog from "../../components/codeDialog.vue"
	import pro from '../../assets/js/common.js'
	export default {
		name : "forgetPassword",
		components : {codeDialog},
		data(){
			return{
				msg: '',
				phone: '',
				code: '',
				time: 0,
				info: '获取验证码',
				phoneReg: /^(((13[0-9])|(14[5-7])|(15[0-9])|(17[0-9])|(18[0-9]))+\d{8})$/,
				num: 0,
				phoneReg: /^(((13[0-9])|(14[5-7])|(15[0-9])|(17[0-9])|(18[0-9]))+\d{8})$/,
				pwd : '',
				newPwd:'',
			}
		},
		computed : {
			PATH: function(){
				return this.$store.getters.PATH;
			},
			volid: function(){
				if(this.time <= 0){
					return true
				}else{
					return false
				}
			},
			version: function(){
				return '1.1';
			},
			environment(){
				return this.$store.state.environment;
			}
		},
		methods : {
			getcode : function(e){
				if($(e.target).hasClass('current')) return false;
				if(this.phone == ''){
					layer.msg('请输入手机号', {time: 1000});
				}else if(this.phoneReg.test(this.phone) == false){
					layer.msg('手机格式错误', {time: 1000});
				}else{
					if(this.num && this.num > 2){
						this.$refs.codeDialog.isshow = true;
						this.$refs.codeDialog.path = this.PATH + "/sendImageCode?code=" + Math.random()*1000 + "&mobile=" + this.phone;
						this.$refs.codeDialog.phone = this.phone;
					}else{
						//请求发送验证码
						var data = {
							mobile:this.phone,
							type : 2
						};
						var headers = {
							version: this.version
						};
						pro.fetch("post",'/sms',data,headers).then(function(res){
							console.log(res)
							if(res.success == true){
								if(res.code == 1){
									layer.msg('发送成功', {time: 1000});
								}
							}else if(res.success == false){
								layer.msg(res.message, {time: 1000});
							}
						}.bind(this)).catch(function(err){
							var data = err.data;
							layer.msg('网络不给力，请稍后再试', {time: 1000});
						})
					//页面效果
					$(e.target).addClass('current');
					this.time = 60;
					var timing = setInterval(function(){
						this.time--;
						if(this.time <= 0){
							clearInterval(timing);
							$(e.target).removeClass('current');
						}
					}.bind(this), 1000);
				}
			}
		},
			toResetPassword : function(){
				if(this.phone!=''&&this.code !=''){
					$(".forgetPassword").css('display','none');
					$(".resetPassword").css('display','block')
				}else{
					layer.msg('请填写完整信息', {time: 1000});
				}
			},
			toLogin : function(){
				if(this.pwd == ''){
					layer.msg('请输入新密码', {time: 1000});
				}else if(this.newPwd == ''){
					layer.msg('请确认新密码', {time: 1000});
				}else if(this.pwdReg.test(this.pwd) == false || this.pwdReg.test(this.newPwd) == false){
					layer.msg('密码格式错误', {time: 1000});
				}else if(this.pwd != this.newPwd){
					layer.msg('两次密码输入不一致', {time: 1000});
				}else{
					//请求设置新密码
					var data = {
						mobile: this.phone,
						password: this.pwd,
						code: this.code
					};
					var headers = {version:this.version}
					pro.fetch("post",'reset_password',data,headers).then(function(res){
						if(res.success == true){
							if(res.code == 1){
								layer.msg('密码重置成功', {time: 1000});
								setTimeout(function(){
									this.$router.push({path: '/login'})
								}.bind(this), 1000);
								this.pwd = '';
								this.newPwd = '';
							}
						}
					}.bind(this)).catch(function(err){
						var data = err.data;
						if(data.success == false){
							this.code = '';
							this.num = res.num;
							layer.msg(data.message, {time: 1000});
						}else {
							layer.msg('网络不给力，请稍后重试',{time:5000})
						}
					}.bind(this))
				}
			},
			back : function(){
				this.$router.push({path:'/login'});
			},
			back_forget : function(){
				$(".forgetPassword").css("display","block");
				$(".resetPassword").css("display","none");
				this.phone = '',
				this.code = ''
			},
			close : function(){
				this.$router.push({path: '/index'})
			},
			toRegister: function(){
			this.$router.push({path:'/register'})
		}
	}
}
</script>

<style lang="scss" scoped type="text/css">
	@import "../../assets/css/common.scss";
	#forgetPassword {
		height: 800px;
	}
	.forgetPassword {
		position : relative;
		left : 40%;
		top:30%;
		width: 400px;
		height: 300px;
		text-align : center;
		color :$lightblue ;
		background-color: $blue;
		z-index:100;
		border-radius: 10px;
	}
	p {
			line-height : 40px;
			color:$white;
			&:nth-child(1) {
				height: 40px;
				background-color: $bottom_color;
				border-radius: 10px;
			}
		}
		input {
			width: 320px;
			height: 40px;
			color: $white;
			text-align: center;
			border: 1px solid $bottom_color;
			&:hover{
				border: 1px solid $yellow;
			}
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
			margin-left: 0px;
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
			left: 34px;
			top: 10px;
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
		.span_white {
			color: $white;
		}
	.resetPassword {
		display: none;
		position : relative;
		left : 40%;
		top:30%;
		width: 400px;
		height: 300px;
		text-align : center;
		color :$lightblue ;
		background-color: $blue;
		z-index:100;
		border-radius: 10px;
	}
</style>