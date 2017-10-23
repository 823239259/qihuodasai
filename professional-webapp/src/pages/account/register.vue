<template>
	<div id="register">
		<div class="bg"></div>
		<div class="register">
			<p>注册<i class="ifont">&#xe624;</i></p>
			<input type="text" id="phone"  class="input_1" placeholder="请输入手机号" maxlength="11" v-model.trim="phone" />
			<input type="number" id="code" class="input_1 input_4" placeholder="验证码" v-model.trim="code" />
			<i class="span_code" v-on:click="getCode">{{volid ? info : (time + '秒')}}</i>
			<input type="password"  class="input_1 input_5" placeholder="请输入密码（6-16位密码）" id="pwd" v-model.trim="pwd"/>
			<i class="ifont ifont_eyes" v-on:click="eyeEvent">&#xe61c;</i>
			<p class="color_light">注册即表示	同意并已阅读<span class="span_white">《用户注册协议》</span></p>
			<button class="btn yellow" v-on:click.native="register">注册</button>
			<p class="color_light">已有期货大赛账号？<span class="span_white" v-on:click="toLogin">立即登录</span></p>
		</div>
		<codeDialog ref="codeDialog" type="register"></codeDialog>
		<tipsDialog :msg="msgTips" ref="dialog"></tipsDialog>
	</div>
</template>
<script>
	import tipsDialog from "../../components/tipsDialog.vue"
	import codeDialog from "../../components/codeDialog.vue"
	export default {
		name : "register",
		components : {tipsDialog,codeDialog},
		data(){
			return {
				eyeShow: false,
				msg: '',
				phone: '',
				pwd: '',
				code: '',
				imgCode: '',
				time: 0,
				info:'获取短信验证码',
				phoneReg: /^(((13[0-9])|(14[5-7])|(15[0-9])|(17[0-9])|(18[0-9]))+\d{8})$/,
				pwdReg: /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{6,18}$/,
				path: '',
			}
		},
		computed: {
			msgTips: function(){
				return this.msg;
			},
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
			environment(){
				return this.$store.state.environment;
			},
			version: function(){
				return '1.1';
			}
		},
		methods : {
			getCode :function(e){
				if($(e.target).hasClass('current')) return false;
				if(this.phone == ''){
					this.$refs.dialog.isShow = true;
					this.msg = '请输入手机号';
				}else if(this.phoneReg.test(this.phone) == false){
					this.$refs.dialog.isShow = true;
					this.msg = '手机号格式错误';
				}else{
					this.$refs.codeDialog.isshow = true;
					this.$refs.codeDialog.path =  "http://test.api.duokongtai.cn/sendImageCode?code=" + Math.random()*1000 + "&mobile=" + this.phone;
					this.$refs.codeDialog.phone = this.phone;
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
			},
			eyeEvent : function(e){
				if(this.eyeShow == false){
					this.eyeShow = true;
					$(e.target).addClass("current").siblings("#pwd").attr("type",'text');
				}else{
					this.eyeShow = false;
					$(e.target).removeClass("current").siblings("#pwd").attr("type",'password');
				}
			},
			register : function(){
				if(this.phone == ''){
					this.$refs.dialog.isShow = true;
					this.msg = '请输入手机号';
				}else if(this.phoneReg.test(this.phone) == false){
					this.$refs.dialog.isShow = true;
					this.msg = '手机号格式错误';
				}else if(this.code == ''){
					this.$refs.dialog.isShow = true;
					this.msg = '请输入手机验证码';
				}else if(this.pwd == ''){
					this.$refs.dialog.isShow = true;
					this.msg = '请输入密码';
				}else if(this.pwdReg.test(this.pwd) == false){
					this.$refs.dialog.isShow = true;
					this.msg = '密码由6到18位字母和数字组成';
				}else{
					//注册请求
					this.$http.post(this.PATH + '/regist', {emulateJSON: true}, {
						headers: {'version': this.version},
						params: {
							mobile: this.phone,
							password: this.pwd,
							code: this.code
						},
						timeout: 5000,
						emulateJSON: true
					}).then(function(e) {
						var data = e.body;
						if(data.success == true ){
							if(data.code == 1){
								this.$refs.dialog.isShow = true;
								this.msg = '注册成功';
								this.phone = '';
								this.pwd = '';
								this.code = '';
								this.time = 0;
								setTimeout(function(){
									this.isShow = false;
									this.$router.replace({path: '/login'});
								}.bind(this), 1000);
							}
						}else{
							this.$refs.dialog.isShow = true;
							this.msg = data.message;
						}
					}.bind(this), function() {
						this.$refs.dialog.isShow = true;
						this.msg = '网络不给力，请稍后再试！'
					});
				}
			},
			toLogin : function(){
				
			}
		}
		
	}
</script>
<style lang="scss" scoped type="text/css">
	@import "../../assets/css/common.scss";
	.register {
		position: absolute;
		top: 30%;
		left: 40%;
		width: 400px;
		border-radius : 10px;
		background-color: $blue;
		text-align : center;
		color :$lightblue ;
		z-index : 100;
		p {
			border-radius : 10px;
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
			color: $white;
			width: 320px;
			height: 40px;
			border: 1px solid $bottom_color;
			&:hover {
				border: 1px solid $yellow;
			}
		}
		.input_4 {
			position: relative;
			left: 30px;
			/*top: 10px;*/
		}
		.span_code {
			position: relative;
			right: 40px;
			top: 16px;
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
			top: 40px;
			right: 40px;
			color: $lightblue;
		}
		.input_5 {
			margin-left: 26px;
		}
		.color_light {
			color: $lightblue;
			margin-top: 10px;
			margin-bottom: 10px;
		}
		.span_white {
			color: $white;
		}
	}
</style>