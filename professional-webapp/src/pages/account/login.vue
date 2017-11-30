<template>
	<div id="login">
		<div class="bg"></div>
		<div class="login">
			<p>登录<i class="ifont ifont_x" v-on:click="close">&#xe624;</i></p>
			<!--<input type="text" id="phone" class="input_1" style="display: none;" />-->
			<input type="text" id="phone" class="input_1" placeholder="请输入手机号码" v-model.trim="phone" autocomplete="off" />
			<!--<input type="text" id="pwd" class="input_1" style="display: none;" />-->
			<input type="text" onfocus="this.type='password'" id="pwd" class="input_1 ml30" placeholder="请输入密码" v-model.trim="pwd" autocomplete="off" @focus="focusEvent" />
			<i class=" ifont ifont_eye" v-on:click="eyeEvent" v-show="eyeShow">&#xe64f;</i>
			<i class=" ifont ifont_eye" v-on:click="eyeEvent" v-show="eyeShowNo">&#xe61c;</i>
			<p class="span_right" v-on:click="toForgetPassword">忘记密码?</p>
			<button class="btn yellow" v-on:click="login">登录</button>
			<p class="color_light">还没有期货大赛账号？<span class="span_white" v-on:click="tORegister">立即注册</span></p>
		</div>
		<codeDialog ref="codeDialog" :objstr="sendMsg" type="login"></codeDialog>
	</div>
</template>
<script>
	import codeDialog from "../../components/codeDialog.vue"
	import pro from '../../assets/js/common.js'
	export default {
		name : "login",
		components : {codeDialog},
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
				eyeShowNo:true
			}
		},
		computed : {
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
			focusEvent: function(e){
				if(this.eyeShow == true){
					$(e.currentTarget).attr("type", 'text');
					$(e.currentTarget).css('background','none');
				}
			},
			toForgetPassword : function(){
				this.$router.push({path: '/forgetPassword'})
			},
			login : function (){
				var phoneReg = /^(((13[0-9])|(14[5-7])|(15[0-9])|(17[0-9])|(18[0-9]))+\d{8})$/;
				var pwdReg = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{6,18}$/;
				if(this.phone == ''){
					layer.msg('请输入手机号', {time: 1000});
				}else if(phoneReg.test(this.phone) == false){
					layer.msg('手机格式错误', {time: 1000});
				}else if(this.pwd == ''){
					layer.msg('请输入密码', {time: 1000});
				}else if(pwdReg.test(this.pwd) == false){
					layer.msg('密码由6到18位字母和数字组成', {time: 1000});
				}else{
//					this.$refs.codeDialog.path = this.PATH + '&' + Math.random();
					//登录请求
					var info = {
						loginName: this.phone,
						password : this.pwd
					};
					var headers = {
						version: 1.1
					};
					pro.fetch('post', '/login', info, headers).then(function(res){
						if(res.success == true){
							if(res.code == 1){
								layer.msg('登录成功', {time: 1000});
								this.token = res.data.token;
								this.secret = res.data.secret;
								var userData = {'username':this.phone,'password':Base64.encode(this.pwd),'token':res.data.token,'secret':res.data.secret};
								localStorage.setItem("user", JSON.stringify(userData));
								this.$store.state.account.userName = this.phone;
								this.$router.push({path: '/account'});
								this.$store.state.account.currentNav = 3;
								localStorage.currentNav = 3;
							}
						}
					}.bind(this)).catch(function(err){
						var data = err.data;
						this.num = data.data.num;
						if(data.success == false){
							if(this.num > 2){
								this.$refs.codeDialog.isshow = true;
								this.$refs.codeDialog.path = this.PATH + "/sendImageCode?code=" + Math.random()*1000 + "&mobile=" + this.phone;
								this.str = {
									loginName : this.phone,
									password :this.pwd
								}
							}
							if(data.data.date != undefined){
								var h = (data.data.date/3600).toString();
								var hour = h.split('.')[0];
								var minute = parseInt((h - hour) * 60);
								layer.msg(data.message + '，距解冻时间还有' + hour + '小时' + minute + '分', {time: 3000});
							}else{
								layer.msg(data.message, {time: 1000});
							}
						}else {
							layer.msg('网路不给力',{time: 2000})
						}
					}.bind(this));
				}
			},
			tORegister : function(){
				this.$router.push({path:'/register'})
			},
			eyeEvent : function(e){
				if(this.eyeShow == false){
					this.eyeShow = true;
					this.eyeShowNo = false;
					$(e.target).removeClass("current").siblings("#pwd").attr("type",'text');
				}else{
					this.eyeShow = false;
					this.eyeShowNo = true;
					$(e.target).removeClass("current").siblings("#pwd").attr("type",'password');
				}
				
			},
			close :function(){
				this.$router.push({path:'/index'});
				this.$store.state.account.currentNav = 0;
				localStorage.currentNav = 0;
			}
		}
	}
</script>
<style lang="scss" scoped type="text/css">
	@import "../../assets/css/common.scss";
	#login{
		height: 800px;
	}
	.login {
		position: fixed;
		top: 50%;	
		left: 50%;
		width: 400px;
		height: 330px;
		margin: -165px 0 0 -200px;
		background-color: $blue;
		text-align : center;
		z-index : 100;
		border-radius:10px;
	}
	p {
			line-height : 40px;
			color:$white;
			&:nth-child(1) {
				height: 40px;
				background-color: $bottom_color;
				border-radius: 10px;
			}
			&:nth-child(3) {
				color: $lightblue;
			}
		}
		input:-webkit-autofill , textarea:-webkit-autofill, select:-webkit-autofill {  
		    -webkit-text-fill-color: #ededed !important;  
		    -webkit-box-shadow: 0 0 0px 1000px transparent  inset !important;  
		    background-color:transparent;  
		    background-image: none;  
		     transition: background-color 50000s ease-in-out 0s; //背景色透明  生效时长  过渡效果  启用时延迟的时间  
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
				/*margin-left: 25px;*/
			}
		}
		.ml30{
			margin-left: 30px;
		}
		.ifont_x {
			float: right;
			margin-right: 10px;
			color: $lightblue;
			cursor: pointer;
		}
		.ifont_eye {
			z-index: 2;
			color: $lightblue;
			font-size: 22px;
			position: relative;
			top: 22px;
			right: 35px;
			cursor: pointer;
		}
		.span_right {
			text-align : right;
			margin-right : 40px;
			&:hover{
				color: $yellow;
				cursor:pointer;
			}
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
			cursor: pointer; 
			&:hover{
				color: $yellow;
			}
		}
</style>