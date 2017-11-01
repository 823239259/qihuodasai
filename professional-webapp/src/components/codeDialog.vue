<template>
	<div id="codeDialog" v-show="isshow">
		<div class="bg"></div>
		<div class="codeDialog">
			<div class="page_cont">
				<i @tap="close"></i>
				<h3 class="title">请先输入图形 验证码</h3>
				<div class="code_box">
					<input type="text" class="fl" placeholder="图形验证码" v-model="code" />
					<a href="javascript:void(0);" class="fr"><img :src="imgPath" v-on:click="refreshCode" /></a>
				</div>
				<div class="btn_box">
					<button class="btn yellow" v-on:click="confirm" >确认</button>
					<button class="btn green" v-on:click="close">取消</button>
				</div>
			</div>
		</div>
	</div>
</template>

<script>
	import pro from '../assets/js/common.js'
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
				pwd: '',
				path: ''
			}
		},
		props: ['objstr','type'],
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
			version: function(){
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
				if(this.code == ''){
					layer.msg('请输入验证码', {time: 1000});
				}else{
					if(this.type == 'login'){
//						请求认证
						var data = {
							loginName: this.info.loginName,
							password: this.info.password,
							code: this.code
						};
						var headers = {version:this.version}
						pro.fetch("post",'/login',data,headers).then(function(){
							if(res.success = true){
								if(res.code = 1){
									layer.msg('登录成功', {time: 1000});
									this.token = res.data.token;
									this.secret = res.data.secret;
									var userData = {'username': this.info.loginName, 'password': this.info.password, 'token': res.data.token, 'secret': res.data.secret};  
									localStorage.setItem("user", JSON.stringify(userData));
									this.code = '';
									setTimeout(function(){
										this.isshow = false;
									},1000)
								}
							}
						}.bind(this)).catch(function(err){
							var data = err.data;
							if(data.success == false){
								this.code = '';
								this.path = this.path + '&' + Math.random()*10;
								layer.msg(data.message,{time:1000})
								if(data.code == 4){
									layer.msg(data.message, {time: 1000});
								}else{
									layer.msg(res.message, {time: 1000});
									setTimeout(function(){
										this.isshow = false;
									}.bind(this),1000);
								}
							}else{
									layer.msg('网络不给力，请稍后重试', {time: 5000});
							}
						}.bind(this))
					}else if(this.type == 'register'){
						//请求发送验证码
						var data ={
						 	mobile: this.phone,
							type: 1,
							yzm: this.code
						};
						var headers = {
							version: this.version
						};
						pro.fetch('post', '/sms', data, headers).then(function(res){
							if(res.success == true){
								if(res.code == 1){
									layer.msg('发送成功', {time: 1000});
									console.log(this.isshow);
									this.isshow = false;
								}
							}
							else{
								layer.msg(res.message, {time: 1000});
							}
						}.bind(this)).catch(function(err){
							var data = err.data;
							if(data.success == false){
								this.code = '',
								this.path = this.path + '&' + Math.random()*10;
								layer.msg(data.message,{time:1000})
							}else{
								layer.msg('网络不给力，请稍后再试',{time:5000})
							}
						}.bind(this));
					}else if(this.type == 'findpwd'){
						//请求发送验证码
						var data={
								mobile: this.phone,
								type: 2,
								yzm: this.code
						};
						var headers = {version:this.version}
						pro.fetch("post",'/sms',data,headers).then(function(res){
							if(res.success == true){
								if(res.code == 1){
									layer.msg('发送成功', {time: 1000});
									setTimeout(function(){
										this.isshow = false;
									}.bind(this),1000);
								}
							}
						}.bind(this)).catch(function(err){
							var data = err.data;
							if(data.success == false){
								this.code = '',
								layer.msg(data.message,{time:1000})
							}else{
								layer.msg('网络不给力，请稍后重试', {time: 5000});
							}
						}.bind(this))
					}else if(this.type == "resetMobile"){
						var data={
								mobile: this.phone,
								type: 3
						};
						var headers = {
							token : JSON.parse(localStorage.user).token,
							secret : JSON.parse(localStorage.user).secret
						}
						pro.fetch("post",'/user/security/send_sms',headers).then((res)=>{
							if(res.success == true){
								if(res.code == 1){
									layer.msg('设置成功',{time:1000});
									setTimeout(function(){
										this.isshow = false;
									}.bind(this),1000);
								}else {
									layer.msg(res.code,{time:1000});
								}
							}
						}).catch((err)=>{
							layer.msg('网络不给力，请稍后重试', {time: 1000});
						})
					}else if(this.type == 'resetWithDrawPWD'){
						var data={
								mobile: this.phone,
								type: 2
						}
						var headers = {
							token : JSON.parse(localStorage.user).token,
							secret : JSON.parse(localStorage.user).secret
						}
						pro.fetch("post","/user/security/send_sms",data,headers).then((res)=>{
							if(res.success == true){
								if(res.code == 1){
									layer.msg('设置成功',{time:1000});
									setTimeout(function(){
										this.isshow = false;
									}.bind(this),1000);
								}else {
									layer.msg(res.code,{time:1000});
								}
							}
						}).catch((err)=>{
							layer.msg('网络不给力，请稍后重试', {time: 1000});
						})
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
		top: 50%;
		left: 50%;
		width: 400px;
		height: 200px;
		position: fixed;
		margin: -100px 0 0 -200px;
		border-radius: 10px;
		text-align: center;
		background-color: $blue;
		color: $lightblue;
		z-index: 105;
	}
	.title {
		height: 40px;
		background-color: $bottom_color;
		color: $white;
		border-radius: 10px;
		line-height: 40px;
	}
	.fl {
		width: 320px;
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