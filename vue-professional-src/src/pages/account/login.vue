<template>
	<div id="login">
		<tipsDialog :msg="msgTips" ref="dialog"></tipsDialog>
		<topbar title="登录"></topbar>
		<back :title="isJumpEvent"></back>
		<cs title="客服"></cs>
		<div class="page_cont">
			<div class="logo">
				<img src="../../assets/img/logo.png"/>
			</div>
			<div class="ipt_row">
				<label for="phone">手机号</label>
				<input type="number" id="phone" placeholder="请输入您的手机号" v-model.trim="phone" />
			</div>
			<div class="ipt_row">
				<label for="pwd">密码</label>
				<input type="password" id="pwd" placeholder="请输入您的密码" v-model.trim="pwd" @keyup.enter="login"/>
				<i class="eye" @tap="eyeEvent"></i>
			</div>
			<btn name="登　录" @tap.native='login'></btn>
			<p class="jump_operate"><span @tap="toFindPwd">忘记密码</span>  /  <span class="yellow" @tap="toRegister">用户注册</span></p>
		</div>
		<p class="bottom_tips">如遇问题请拨打：400-852-8008</p>
		<codeDialog ref="codeDialog" :objstr="sendMsg" type="login"></codeDialog>
	</div>
</template>

<script>
	import codeDialog from '../../components/codeDialog.vue'
	import topbar from '../../components/Topbar.vue'
	import back from '../../components/back.vue'
	import cs from '../../components/customerService.vue'
	import btn from '../../components/bigBtn.vue'
	import tipsDialog from '../../components/tipsDialog.vue'
	import pro from '../../assets/common.js'
	export default{
		name:'login',
		components: {topbar, back, cs, btn, tipsDialog, codeDialog},
		computed: {
			isJumpEvent: function(){
				this.isJump = this.$route.query.isJump;
				if(this.isJump == 1)  return true;
			},
			msgTips: function(){
				return this.msg;
			},
			PATH: function(){
				return this.$store.getters.PATH;
			},
			environment(){
				return this.$store.state.environment;
			},
			sendMsg(){
				if(this.str) return JSON.stringify(this.str);
			}
		},
		data(){
			return {
				isJump: '',
				eyeShow: false,
				msg: '',
				phone: '',
				pwd: '',
				token: '',
				secret: '',
				path: '',
				str: '',
				num: 0
			}
		},
		methods:{
			eyeEvent: function(e){
				if(this.eyeShow == false){
					this.eyeShow = true;
					$(e.target).addClass("current").siblings("#pwd").attr("type",'text');
				}else{
					this.eyeShow = false;
					$(e.target).removeClass("current").siblings("#pwd").attr("type",'password');
				}
			},
			login: function(){
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
					console.log(this.num);
					this.$refs.codeDialog.path = this.path + '&' + Math.random();
					if(this.num >= 2 || this.num == 0){
						this.$refs.codeDialog.isshow = true;
						if(this.environment == 'test'){
							this.path = "http://test.api.duokongtai.cn/sendImageCode?code=" + Math.random()*1000 + "&mobile=" + this.phone;
						}else{
							this.path = "http://api.duokongtai.cn/sendImageCode?code=" + Math.random()*1000 + "&mobile=" + this.phone;
						}
						this.$refs.codeDialog.path = this.path + '&' + Math.random();
						this.str = {
							loginName: this.phone,
							password: this.pwd
						}
					}else{
						//登录请求
						this.$http.post(this.PATH + '/login', {emulateJSON: true}, {
							params: {
								loginName: this.phone,
								password: this.pwd
							},
							timeout: 5000
						}).then(function(e) {
							var data = e.body;
							if(data.success == true ){
								if(data.code == 1){
									this.$refs.dialog.isShow = true;
									this.msg = '登录成功';
									this.token = data.data.token;
									this.secret = data.data.secret;
									var userData = {'username': this.phone, 'password': this.pwd, 'token': data.data.token, 'secret': data.data.secret};  
									localStorage.setItem("user", JSON.stringify(userData));
									this.$router.push({path: '/account'});
								}
							}else{
								this.num = data.data.num;
								this.$refs.dialog.isShow = true;
								this.msg = data.message;
							}
						}.bind(this), function() {
							this.$refs.dialog.isShow = true;
							this.msg = '网络不给力，请稍后再试！';
						});
					}
				}
			},
			toRegister: function(){
				this.$router.push({path: '/register'});
			},
			toFindPwd: function(){
				this.$router.push({path: '/findPwd'});
			},
		},
		mounted: function(){
			//页面高度计算
			var h = window.screen.height - 20 - $("#topbar").height();
			$("#login").height(h);
			if(localStorage.user){
				var obj = JSON.parse(localStorage.user);
				this.phone = obj.username;
				this.pwd = obj.password;
			}
		},
	}
</script>

<style scoped lang="less">
@import url("../../assets/css/base.less");
/*ip6p及以上*/
@media (min-width:411px) {
    #login{
    	width: 100%;
    	overflow: hidden;
    	background: @deepblue;
    	padding-top: 50px;
    	.page_cont{
    		.logo{
    			width: 100%;
    			margin: 30px 0;
    			text-align: center;
    			img{
    				display: inline-block;
    				width: 68px;
    				height: 68px;
    			}
    		}
    		.jump_operate{
    			width: 100%;
    			text-align: center;
    			color: #7a7f99;
    			margin-top: 38px; 
    			span:hover{
    				color: @yellow;
    			}
    		}
    	}
    	.bottom_tips{
    		position: fixed;
    		bottom: 20px;
    		left: 0;
    		width: 100%;
    		text-align: center;
    		font-size: @fs14;
    		color: #7a7f99;
    	}
    }
}
/*ip6*/
@media (min-width:371px) and (max-width:410px) {
	#login{
    	width: 100%;
    	overflow: hidden;
    	padding-top: 50px*@ip6;
    	background: @deepblue;
    	.page_cont{
    		.logo{
    			width: 100%;
    			margin: 30px*@ip6 0;
    			text-align: center;
    			img{
    				display: inline-block;
    				width: 68px*@ip6;
    				height: 68px*@ip6;
    			}
    		}
    		.jump_operate{
    			width: 100%;
    			text-align: center;
    			color: #7a7f99;
    			margin-top: 38px*@ip6; 
    			span:hover{
    				color: @yellow;
    			}
    		}
    	}
    	.bottom_tips{
    		position: fixed;
    		bottom: 20px*@ip6;
    		left: 0;
    		width: 100%;
    		text-align: center;
    		font-size: @fs14*@ip6;
    		color: #7a7f99;
    	}
    }
}
/*ip5*/
@media(max-width:370px) {
	#login{
    	width: 100%;
    	overflow: hidden;
    	padding-top: 50px*@ip5;
    	background: @deepblue;
    	.page_cont{
    		.logo{
    			width: 100%;
    			margin: 30px*@ip5 0;
    			text-align: center;
    			img{
    				display: inline-block;
    				width: 68px*@ip5;
    				height: 68px*@ip5;
    			}
    		}
    		.jump_operate{
    			width: 100%;
    			text-align: center;
    			color: #7a7f99;
    			margin-top: 38px*@ip5; 
    			span:hover{
    				color: @yellow;
    			}
    		}
    	}
    	.bottom_tips{
    		position: fixed;
    		bottom: 20px*@ip5;
    		left: 0;
    		width: 100%;
    		text-align: center;
    		font-size: @fs14*@ip5;
    		color: #7a7f99;
    	}
    }
}
</style>