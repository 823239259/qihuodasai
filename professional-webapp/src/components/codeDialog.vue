<template>
	<div id="codeDialog" v-if="isshow">
		<div class="bg"></div>
		<div class="codeDialog">
			<div class="page_cont">
				<i @tap="close"></i>
				<h3 class="title">请先输入图形 验证码</h3>
				<div class="code_box">
					<input type="number" class="fl" placeholder="图形验证码" v-model="code" />
					<a href="javascript:void(0);" class="fr"><img :src="imgPath" v-on:click="refreshCode" /></a>
				</div>
				<div class="btn_box">
					<button class="btn yellow" v-on:click="confirm" >确认</button>
					<button class="btn green" v-on:click="close">取消</button>
				</div>
			</div>
		</div>
		<tipsDialog :msg="msgTips" ref="dialog"></tipsDialog>
	</div>
</template>

<script>
	import axios from "axios";
	import qs from "qs";
	import tipsDialog from './tipsDialog.vue'
	export default{
		name: 'codeDialog',
		components : {tipsDialog},
		data(){
			return{
				isshow: false,
				code: '',
				msg: '',
				token: '',
				secret: '',
				phone: '',
				pwd: '',
				path: ''
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
//				return JSON.parse(localStorage.version).ios;
				return '1.1';
			},
		},
		methods: {
			refreshCode: function(){
				this.path = this.path + '&' + Math.random();
			},
			close: function(){
				this.isshow = false;
			},
			confirm: function(){
				console.log(this.code);
				console.log(this.phone);
				if(this.code == ''){
					this.$refs.dialog.isShow = true;
					this.msg = '请输入验证码';
				}else{
					if(this.type == 'login'){
						axios.post(this.PATH + '/login', {emulateJSON: true}, {
								promise: {
									loginName: this.info.loginName,
									password: this.info.password,
									code: this.code
								},
								timeout: 5000,
								emulateJSON: true
						}).then(function(e) {
							var data = e.body;
							if(data.success == true ){
								if(data.code == 1){
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
								this.path = this.path + '&' + Math.random()*10;
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
							console.log(this.PATH);
							axios({
								    method:"POST",
								    url:'http://test.api.duokongtai.cn/sms',
//								    emulateJSON: true,
								    headers: {'version': this.version},
//								    timeout: 5000,
								    data:{
								        mobile: this.phone,
										type: 1,
										yzm: this.code
								    }
								}).then((res)=>{
									var data = res.data;
									if(data.success == true){
										if(data.code == 1){
											this.$refs.dialog.isShow = true;
											this.msg = '发送成功';
											setTimeout(function(){
													this.isshow = false;
												}.bind(this),1000);
											}
										}else{
	//										this.code = '';
	//										this.path = this.path + '&' + Math.random()*10;
	//										this.$refs.dialog.isShow = true;
											console.log(data)
	//										this.msg = data.message;
										}
									}).catch((err)=>{
										this.$refs.dialog.isShow = true;
										this.msg = '网络不给力，请稍后再试！'
										console.log(err.data)
									})
							}
					}else if(this.type = 'findpwd'){
						//请求发送验证码
						this.$http.post(this.PATH + '/sms',{emulateJSON: true},{
							headers: {'version': this.version},
							promise: {
								mobile: this.phone,
								type: 2,
								yzm: this.code
							},
							timeout: 5000,
							emulateJSON: true
						}).then(function(e){
							var data = e.body;
							cosnole.log(data);
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

<style lang="scss" scoped type="text/css">
	@import "../assets/css/common.scss";
	.bg {
		z-index: 101;
	}
	.codeDialog {
		width: 400px;
		height: 200px;
		position: absolute;
		top: 32%;
		left: 40%;
		border-radius: 10px;
		text-align: center;
		background-color: $blue;
		color: $lightblue;
		z-index: 102;
	}
	.title {
		height: 40px;
		background-color: $bottom_color;
		color: $white;
		border-radius: 10px;
		line-height: 40px;
	}
	.fl {
		width: 330px;
		height: 40px;
		border: 1px solid $bottom_color;
		border-radius: 5px;
		float: left;
		margin-top: 20px;
		text-align: center;
		color: $white;
	}
	.btn {
		width: 120px;
		height: 30px;
		margin-top: 40px;
	}
	.fr {
		float: right;
		img {
			display: block;
			width: 68px;
			height: 40px;
			margin-top: 20px;
		}
	}
</style>