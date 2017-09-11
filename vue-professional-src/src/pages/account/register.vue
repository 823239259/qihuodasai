<template>
	<div id="register">
		<tipsDialog :msg="msgTips" ref="dialog"></tipsDialog>
		<topbar title="注册"></topbar>
		<back></back>
		<cs title="客服"></cs>
		<div class="page_cont">
			<div class="logo">
				<img src="../../assets/img/logo.png"/>
			</div>
			<div class="ipt_row">
				<label for="phone">手机号</label>
				<input type="number" id="phone" placeholder="请输入您的手机号" v-model.trim="phone" />
			</div>
			<!--<div class="ipt_row">
				<label for="imgCode">图形验证码</label>
				<input type="number" id="imgCode" placeholder="请输入验证码" v-model.trim="imgCode" />
				<a href="javascript:void(0);" class="code imgCode"><img :src="imgPath"/></a>
			</div>-->
			<div class="ipt_row">
				<label for="code">手机验证码</label>
				<input type="number" id="code" placeholder="请输入验证码" v-model.trim="code" />
				<span class="code" @tap="getCode">{{volid ? info : (time + '秒')}}</span>
			</div>
			<div class="ipt_row">
				<label for="pwd">密码</label>
				<input type="password" id="pwd" placeholder="请输入您的密码" v-model.trim="pwd" />
				<i class="eye" @tap="eyeEvent"></i>
			</div>
			<btn name="注　册" @tap.native="register"></btn>
			<p class="jump_operate"><span>已有账户？</span><span class="yellow" @tap="toLogin">立即登录</span></p>
		</div>
		<p class="bottom_tips">如遇问题请拨打：400-852-8008</p>
		<codeDialog ref="codeDialog" type="register"></codeDialog>
	</div>
</template>

<script>
	import topbar from '../../components/Topbar.vue'
	import back from '../../components/back.vue'
	import cs from '../../components/customerService.vue'
	import btn from '../../components/bigBtn.vue'
	import tipsDialog from '../../components/tipsDialog.vue'
	import codeDialog from '../../components/codeDialog.vue'
	export default{
		name:'register',
		components: {topbar, back, cs, btn, tipsDialog, codeDialog},
		data(){
			return {
				eyeShow: false,
				msg: '',
				phone: '',
				pwd: '',
				code: '',
				imgCode: '',
				time: 0,
				info:'获取验证码',
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
//				return JSON.parse(localStorage.version).ios;
				return '1.1';
			}
		},
		methods: {
			eyeEvent: function(e){
				if(this.eyeShow == false){
					this.eyeShow = true;
					$(e.target).addClass("current").siblings("#pwd").attr("type",'text');
				}else{
					this.eyeShow = false;
					$(e.target).removeClass("current").siblings("#pwd").attr("type",'password');
				}
			},
			getCode: function(e){
				if($(e.target).hasClass('current')) return false;
				if(this.phone == ''){
					this.$children[0].isShow = true;
					this.msg = '请输入手机号';
				}else if(this.phoneReg.test(this.phone) == false){
					this.$children[0].isShow = true;
					this.msg = '手机号格式错误';
				}else{
					this.$refs.codeDialog.isshow = true;
					this.path = this.PATH + "/sendImageCode?code=" + Math.random()*1000 + "&mobile=" + this.phone;
//					if(this.environment == 'test'){
//						this.$refs.codeDialog.path = "http://test.api.duokongtai.cn/sendImageCode?code=" + Math.random()*1000 + "&mobile=" + this.phone;
//					}else{
//						this.$refs.codeDialog.path = "http://api.duokongtai.cn/sendImageCode?code=" + Math.random()*1000 + "&mobile=" + this.phone;
//					}
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
			register: function(){
				if(this.phone == ''){
					this.$children[0].isShow = true;
					this.msg = '请输入手机号';
				}else if(this.phoneReg.test(this.phone) == false){
					this.$children[0].isShow = true;
					this.msg = '手机号格式错误';
				}else if(this.code == ''){
					this.$children[0].isShow = true;
					this.msg = '请输入手机验证码';
				}else if(this.pwd == ''){
					this.$children[0].isShow = true;
					this.msg = '请输入密码';
				}else if(this.pwdReg.test(this.pwd) == false){
					this.$children[0].isShow = true;
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
								this.$children[0].isShow = true;
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
							this.$children[0].isShow = true;
							this.msg = data.message;
						}
					}.bind(this), function() {
						this.$children[0].isShow = true;
						this.msg = '网络不给力，请稍后再试！'
					});
				}
			},
			toLogin: function(){
				this.$router.push({path:'/login'});
			}
		},
		mounted: function(){
			//页面高度计算
			var h = window.screen.height - 20 - $("#topbar").height();
			$("#register").height(h);
		},
	}
</script>

<style scoped lang="less">
@import url("../../assets/css/base.less");
/*ip6p及以上*/
@media (min-width:411px) {
    #register{
    	width: 100%;
    	overflow: hidden;
    	padding-top: 50px;
    	background: @deepblue;
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
    			color: @white;
    			margin-top: 38px; 
    			span.yellow{
    				color: @yellow;
    			}
    		}
    		#imgCode{
    			padding: 10px 120px 10px 110px;
    		}
    		.imgCode, .imgCode img{
    			width: 110px;
    			height: 52px;
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
	#register{
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
    		#imgCode{
    			padding: 10px*@ip6 120px*@ip6 10px*@ip6 110px*@ip6;
    		}
    		.imgCode, .imgCode img{
    			width: 110px*@ip6;
    			height: 52px*@ip6;
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
	#register{
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
    		#imgCode{
    			padding: 10px*@ip5 120px*@ip5 10px*@ip5 110px*@ip5;
    		}
    		.imgCode, .imgCode img{
    			width: 110px*@ip5;
    			height: 52px*@ip5;
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