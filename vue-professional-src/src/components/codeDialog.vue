<template>
	<div id="codeDialog" v-if="isshow">
		<tipsDialog :msg="msgTips" ref="dialog"></tipsDialog>
		<div class="bg"></div>
		<div class="page_cont">
			<i @tap="close"></i>
			<h3 class="title">请先输入图形 验证码</h3>
			<div class="code_box">
				<input type="number" class="fl" placeholder="图形验证码" v-model="code" />
				<a href="javascript:void(0);" class="fr" @tap="refreshCode"><img :src="imgPath"/></a>
			</div>
			<div class="btn_box">
				<a href="javascript:void(0);" class="fl" @tap="confirm">确定</a>
				<a href="javascript:void(0);" class="fl" @tap="close">取消</a>
			</div>
		</div>
	</div>
</template>

<script>
	import tipsDialog from './tipsDialog.vue'
	export default{
		name: 'codeDialog',
		data(){
			return{
				isshow: false,
				code: '',
				msg: '',
				token: '',
				secret: '',
				phone: '',
				pwd: ''
			}
		},
		props: ['objstr','type'],
		components: {tipsDialog},
		computed: {
			info: function(){
				if(this.objstr){
					return JSON.parse(this.objstr);
				}
			},
			imgPath: function(){
				if(this.path) return this.path;
			},
			PATH: function(){
				return this.$store.getters.PATH;
			},
			msgTips: function(){
				return this.msg;
			},
			version: function(){
				return JSON.parse(localStorage.version).ios;
			},
		},
		methods: {
			refreshCode: function(){
//				console.log(imgPath);
				
			},
			close: function(){
				this.isshow = false;
			},
			confirm: function(){
				if(this.code == ''){
					this.$refs.dialog.isShow = true;
					this.msg = '请输入验证码';
				}else{
					if(this.type == 'login'){
						this.$http.post(this.PATH + '/login', {emulateJSON: true}, {
								params: {
									loginName: this.info.loginName,
									password: this.info.password,
									code: this.code
								},
								timeout: 5000
						}).then(function(e) {
							var data = e.body;
							if(data.success == true ){
								if(data.code == 1){
									console.log(159);
									this.$refs.dialog.isShow = true;
									this.msg = '登录成功';
									this.token = data.data.token;
									this.secret = data.data.secret;
									var userData = {'username': this.info.loginName, 'password': this.info.password, 'token': data.data.token, 'secret': data.data.secret};  
									localStorage.setItem("user", JSON.stringify(userData));
									this.code = '';
									setTimeout(function(){
										this.isshow = false;
										this.$router.push({path: '/account'});
									}.bind(this),1000);
								}
							}else{
								this.code = '';
								this.path = this.path + '&' + Math.random()*1000;
								if(data.code == 4){
									this.$refs.dialog.isShow = true;
									this.msg = data.message;
								}else{
									this.$refs.dialog.isShow = true;
									this.msg = data.message;
									setTimeout(function(){
										this.isshow = false;
									}.bind(this),1000);
								}
							}
						}.bind(this), function() {
							this.$refs.dialog.isShow = true;
							this.msg = '网络不给力，请稍后再试！'
						});
					}else if(this.type == 'register'){
						if(this.code == ''){
							this.$refs.dialog.isShow = true;
							this.msg = '请输入验证码';
						}else{
							//请求发送验证码
							this.$http.post(this.PATH + '/sms',{emulateJSON: true},{
								headers: {'version': this.version},
								params: {
									mobile: this.phone,
									type: 1,
									yzm: this.code
								},
								timeout: 5000
							}).then(function(e){
								var data = e.body;
								if(data.success == true){
									if(data.code == 1){
										this.$refs.dialog.isShow = true;
										this.msg = '发送成功';
										setTimeout(function(){
											this.isshow = false;
										}.bind(this),1000);
									}
								}else{
									this.$refs.dialog.isShow = true;
									this.msg = data.message;
								}
							}.bind(this), function(){
								this.$children[0].isShow = true;
								this.msg = '网络不给力，请稍后再试！'
							});
						}
					}else if(this.type = 'findpwd'){
						//请求发送验证码
						this.$http.post(this.PATH + '/sms',{emulateJSON: true},{
							headers: {'version': this.version},
							params: {
								mobile: this.phone,
								type: 2,
								yzm: this.code
							},
							timeout: 5000
						}).then(function(e){
							var data = e.body;
							this.$refs.dialog.isShow = true;
							if(data.success == true){
								if(data.code == 1){
									this.msg = '发送成功';
									setTimeout(function(){
										this.isshow = false;
									}.bind(this),1000);
								}
							}else{
								this.msg = data.message;
							}
						}.bind(this), function(){
							this.$refs.dialog.isShow = true;
							this.msg = '网络不给力，请稍后再试！'
						});
					}
				}
			}
		},
		mounted: function(){
		}
	}
</script>

<style lang="less" scoped>
@import url("../assets/css/base.less");
/*ip6p及以上*/
@media (min-width:411px) {
    .bg{
    	position: absolute;
    	top: 0;
    	left: 0;
    	right: 0;
    	bottom: 0;
    	z-index: 1010;
    	background: #000;
    	opacity: 0.5;
    }
    .page_cont{
    	width: 330px;
    	height: 200px;
    	overflow: hidden;
    	border-radius: 5px;
    	box-shadow: 5px 5px 10px #000;
    	background: @black;
    	position: absolute;
    	top: 50%;
    	left: 50%;
    	z-index: 1011;
    	margin: -100px 0 0 -165px;
    	i{
			position: absolute;
			right: 0;
			top: 0;
			z-index: 1011;
			width: 32px;
			height: 32px;
			background: url('../assets/img/x.png') no-repeat center center;
			background-size: 100% 100%;
		}
    	.title{
    		height: 44px;
    		line-height: 44px;
    		text-align: center;
    		font-size: @fs18;
    		color: @white;
    		background: @deepblue;
    		border-bottom: 1px solid @black;
    	}
    	.code_box{
    		height: 100px;
    		background: @deepblue;
    		padding: 23px 15px;
    		input, a{
    			width: 143px;
    			height: 54px;
    			overflow: hidden;
    			border: 1px solid #171721;
    			border-radius: 5px;
    			background: @black;
    			color: @white;
    			img{
    				width: 143px;
    				height: 54px;
    			}
    		}
    	}
    	.btn_box{
    		height: 45px;
    		line-height: 45px;
    		margin-top: 10px;
    		background: @deepblue;
    		a{
    			width: 50%;
    			text-align: center;
    			font-size: @fs16;
    		}
    	}
    }
}
/*ip6*/
@media (min-width:371px) and (max-width:410px) {
    .bg{
    	position: absolute;
    	top: 0;
    	left: 0;
    	right: 0;
    	bottom: 0;
    	z-index: 1010;
    	background: #000;
    	opacity: 0.5;
    }
    .page_cont{
    	width: 330px*@ip6;
    	height: 200px*@ip6;
    	overflow: hidden;
    	border-radius: 5px*@ip6;
    	box-shadow: 5px*@ip6 5px*@ip6 10px*@ip6 #000;
    	background: @black;
    	position: absolute;
    	top: 50%;
    	left: 50%;
    	z-index: 1011;
    	margin: -100px*@ip6 0 0 -165px*@ip6;
    	i{
			position: absolute;
			right: 0;
			top: 0;
			z-index: 1011;
			width: 32px*@ip6;
			height: 32px*@ip6;
			background: url('../assets/img/x.png') no-repeat center center;
			background-size: 100% 100%;
		}
    	.title{
    		height: 44px*@ip6;
    		line-height: 44px*@ip6;
    		text-align: center;
    		font-size: @fs18*@ip6;
    		color: @white;
    		background: @deepblue;
    		border-bottom: 1px solid @black;
    	}
    	.code_box{
    		height: 100px*@ip6;
    		background: @deepblue;
    		padding: 23px*@ip6 15px*@ip6;
    		input, a{
    			width: 143px*@ip6;
    			height: 54px*@ip6;
    			overflow: hidden;
    			border: 1px solid #171721;
    			border-radius: 5px*@ip6;
    			background: @black;
    			color: @white;
    			img{
    				width: 143px*@ip6;
    				height: 54px*@ip6;
    			}
    		}
    	}
    	.btn_box{
    		height: 45px*@ip6;
    		line-height: 45px*@ip6;
    		margin-top: 10px*@ip6;
    		background: @deepblue;
    		a{
    			width: 50%;
    			text-align: center;
    			font-size: @fs16*@ip6;
    		}
    	}
    }
}
/*ip5*/
@media(max-width:370px) {
	.bg{
    	position: absolute;
    	top: 0;
    	left: 0;
    	right: 0;
    	bottom: 0;
    	z-index: 1010;
    	background: #000;
    	opacity: 0.5;
    }
    .page_cont{
    	width: 330px*@ip5;
    	height: 200px*@ip5;
    	overflow: hidden;
    	border-radius: 5px*@ip5;
    	box-shadow: 5px*@ip5 5px*@ip5 10px*@ip5 #000;
    	background: @black;
    	position: absolute;
    	top: 50%;
    	left: 50%;
    	z-index: 1011;
    	margin: -100px*@ip5 0 0 -165px*@ip5;
    	i{
			position: absolute;
			right: 0;
			top: 0;
			z-index: 1011;
			width: 32px*@ip5;
			height: 32px*@ip5;
			background: url('../assets/img/x.png') no-repeat center center;
			background-size: 100% 100%;
		}
    	.title{
    		height: 44px*@ip5;
    		line-height: 44px*@ip5;
    		text-align: center;
    		font-size: @fs18*@ip5;
    		color: @white;
    		background: @deepblue;
    		border-bottom: 1px solid @black;
    	}
    	.code_box{
    		height: 100px*@ip5;
    		background: @deepblue;
    		padding: 23px*@ip5 15px*@ip5;
    		input, a{
    			width: 143px*@ip5;
    			height: 54px*@ip5;
    			overflow: hidden;
    			border: 1px solid #171721;
    			border-radius: 5px*@ip5;
    			background: @black;
    			color: @white;
    			img{
    				width: 143px*@ip5;
    				height: 54px*@ip5;
    			}
    		}
    	}
    	.btn_box{
    		height: 45px*@ip5;
    		line-height: 45px*@ip5;
    		margin-top: 10px*@ip5;
    		background: @deepblue;
    		a{
    			width: 50%;
    			text-align: center;
    			font-size: @fs16*@ip5;
    		}
    	}
    }
}
</style>