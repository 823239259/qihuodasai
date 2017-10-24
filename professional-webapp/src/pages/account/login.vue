<template>
	<div id="login">
		<div class="bg"></div>
		<div class="login">
			<p>登录<i class="ifont ifont_x" v-on:click="close">&#xe624;</i></p>
			<input type="text" id="phone" class="input_1" placeholder="请输入手机号码" v-model.trim="phone" />
			<input type="password" id="pwd" class="input_1" placeholder="请输入密码" v-model.trim="pwd"/><i class=" ifont ifont_eye" v-on:click="eyeEvent">&#xe64f;</i>
			<p class="span_right" v-on:click="toForgetPassword">忘记密码?</p>
			<button class="btn yellow" v-on:click="login">登录</button>
			<p class="color_light">还没有期货大赛账号？<span class="span_white" v-on:click="tORegister">立即注册</span></p>
		</div>
		<tipsDialog :msg="msgTips" time="2000" ref="dialog"></tipsDialog>
		<codeDialog ref="codeDialog" :objstr="sendMsg" type="login"></codeDialog>
	</div>
</template>
<script>
	import tipsDialog from "../../components/tipsDialog.vue"
	import codeDialog from "../../components/codeDialog.vue"
	import pro from '../../assets/js/common.js'
	export default {
		name : "login",
		components : {tipsDialog,codeDialog},
		data(){
			return {
				isJump: '',
				eyeShow: false,
				msg: '',
				phone: '',
				pwd: '',
				token: '',
				secret: '',
//				path: '',
				str: '',
				num: '',
			}
		},
		computed : {
			msgTips: function(){
				return this.msg;
			},
			sendMsg(){
				if(this.str) return JSON.stringify(this.str);
			},
			PATH: function(){
				return this.$store.getters.PATH;
			},
			environment(){
				return this.$store.state.environment;
			},
		},
		methods : {
			toForgetPassword : function(){
				this.$router.push({path: '/forgetPassword'})
			},
			login : function (){
				var phoneReg = /^(((13[0-9])|(14[5-7])|(15[0-9])|(17[0-9])|(18[0-9]))+\d{8})$/;
				var pwdReg = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{6,18}$/;
				if(this.phone == ''){
					this.$refs.dialog.isShow = true;
					this.msg = '请输入手机号';
				}else if(phoneReg.test(this.phone) == false){
					this.$refs.dialog.isShow = true;
					this.msg = '手机号格式错误';
				}else if(this.pwd == ''){
					this.$refs.dialog.isShow = true;
					this.msg = '请输入密码';
				}else if(pwdReg.test(this.pwd) == false){
					this.$refs.dialog.isShow = true;
					this.msg = '密码由6到18位字母和数字组成';
				}else{
//					this.$refs.codeDialog.path = this.PATH + '&' + Math.random();
					//登录请求
					var data = {
						loginName: this.phone,
						password : this.pwd
					};
					var headers = {
						version: 1.1
					}
					pro.fetch("post", '/login', data, headers).then((res)=>{
						var data = res.data;
						if(data.success == true){
							if(data.code ==1 ){
//								this.$refs.dialog.isShow = true;
								this.msg = "登录成功";
								this.token = data.data.token;
								this.secret = data.data.secret;
								var userData = {'username':this.phone,'password':this.pwd,'token':data.data.token,'secret':data.data.secret};
								localStorage.setItem("user", JSON.stringify(userData));
								this.$router.push({path: '/index'});
							}
						}else {
							this.num = data.data.num;
							if(this.num>2){
								this.$refs.codeDialog.isshow = true;
								this.$refs.codeDialog.path = this.PATH + "/sendImageCode?code=" + Math.random()*1000 + "&mobile=" + this.phone;
								this.str = {
									loginName : this.phone,
									password :this.pwd
								}
							}
							else {
//								this.$refs.dialog.isShow = true;
								if(data.data.date != undefined){
									var h = (data.data.date/3600).toString();
									var hour = h.split('.')[0];
									var minute = parseInt((h - hour) * 60);
									this.msg = data.message + '，距解冻时间还有' + hour + '小时' + minute + '分';
								}else{
									this.msg = data.message;
								}
							}
						}
					}).catch((err)=>{
						console.log(111111111111111111)
						var data =err.data;
						this.$refs.dialog.isShow = true;
						this.msg = '网络不给力，请稍后再试！';
					})
				}
			},
			tORegister : function(){
				this.$router.push({path:'register'})
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
			close :function(){
				this.$router.push({path:'index'})
			}
		}
	}
</script>
<style lang="scss" scoped type="text/css">
	@import "../../assets/css/common.scss";
	.login {
		position: relative;
		/*top: 30%;*/
		left: 40%;
		width: 400px;
		height: 330px;
		background-color: $blue;
		text-align : center;
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
			color: $white;
			width: 320px;
			height: 40px;
			border: 1px solid $bottom_color;
			&:hover {
				border: 1px solid $yellow;
			}
		}
		.input_1 {
			&:nth-child(3){
				margin-left: 25px;
			}
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
</style>