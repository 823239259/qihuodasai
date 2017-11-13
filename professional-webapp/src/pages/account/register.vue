<template>
	<div id="register">
		<div class="bg"></div>
		<div class="register">
			<p>注册<i class="ifont" v-on:click="close">&#xe624;</i></p>
			<input type="text" id="phone"  class="input_1" placeholder="请输入手机号" maxlength="11" v-model.trim="phone" />
			<input type="text" id="code" class="input_1 input_4" placeholder="验证码" v-model.trim="code" />
			<i class="span_code" v-on:click="getCode">{{volid ? info : (time + '秒')}}</i>
			<input type="password"  class="input_1 input_5" placeholder="请输入密码（6-16位密码）" id="pwd" v-model.trim="pwd"/>
			<i class="ifont ifont_eyes" v-on:click="eyeEvent">&#xe61c;</i>
			<p class="color_light">注册即表示	同意并已阅读<a href="registrationProtocol" class="span_white" target="_blank">《用户注册协议》</a></p>
			<button class="btn yellow" v-on:click="register">注册</button>
			<p class="color_light">已有期货大赛账号？<span class="span_white" v-on:click="toLogin">立即登录</span></p>
		</div>
		<codeDialog ref="codeDialog" type="register"></codeDialog>
	</div>
</template>
<script>
	import codeDialog from "../../components/codeDialog.vue"
	import pro from '../../assets/js/common.js'
	export default {
		name : "register",
		components : {codeDialog},
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
				defaultOrder: [
					{commodityCode: 'CL', commodityName: '国际原油'},
					{commodityCode: 'HSI', commodityName: '恒指期货'},
					{commodityCode: 'GC', commodityName: '美黄金'},
					{commodityCode: 'FDAX', commodityName: '德园DAX指数'},
					{commodityCode: 'SI', commodityName: '美白银'},
				],
//				path: ''
				path: ''
			}
		},
		computed: {
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
					layer.msg('请输入手机号', {time: 1000});
				}else if(this.phoneReg.test(this.phone) == false){
					layer.msg('手机格式错误', {time: 1000});
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
					$(e.target).html("&#xe64f;");
					$(e.target).addClass("current").siblings("#pwd").attr("type",'text');
				}else{
					this.eyeShow = false;
					$(e.target).html("&#xe61c;");
					$(e.target).removeClass("current").siblings("#pwd").attr("type",'password');
				}
			},
			register : function(){
				if(this.phone == ''){
					layer.msg('请输入手机号', {time: 1000});
				}else if(this.phoneReg.test(this.phone) == false){
					layer.msg('手机格式错误', {time: 1000});
				}else if(this.code == ''){
					layer.msg('请输入手机验证码', {time: 1000});
				}else if(this.pwd == ''){
					layer.msg('请输入密码', {time: 1000});
				}else if(this.pwdReg.test(this.pwd) == false){
					layer.msg('密码由6到18位字母和数字组成', {time: 1000});
				}else{
					//注册请求
					var data = {
						mobile: this.phone,
						password: this.pwd,
						code: this.code
					};
					var headers = {
						version: this.version
					};
					pro.fetch("post",'/regist',data,headers).then(function(res){
						if(res.success == true){
							if(res.code == 1){
								layer.msg('注册成功', {time: 1000});
								this.phone = '';
								this.pws = '';
								this.code = '';
								this.time = 0;
								setTimeout(function(){
									this.$router.push({path:'/login'})
								}.bind(this),1000)
								//设置默认自选列表
								var headers = {
									token:  res.data.token,
									secret: res.data.secret
								};
								this.defaultOrder.forEach(function(o, i){
									var data = {
										commodityCode: o.commodityCode,
										commodityName: o.commodityName,
									};
									pro.fetch('post', '/contract/saveOptional', data, headers).then(function(res){
										if(res.success == true){
											if(res.code == 1){
												console.log('自选合约添加成功');
											}
										}
									}.bind(this)).catch(function(err){
										var data = err.data;
										layer.msg(data.message, {time: 1000});
									});
								}.bind(this));
							}
						}
					}.bind(this)).catch(function(err){
						layer.msg('网络不给力，请稍后再试', {time: 5000});
					})
				}
			},
			close : function(){
				this.$router.push({path:'/index'})
			},
			toLogin : function(){
				this.$router.push({path:'/login'})
			}
		}
		
	}
</script>
<style lang="scss" scoped type="text/css">
	@import "../../assets/css/common.scss";
	.register {
		position: fixed;
		top: 50%;
		left: 50%;
		z-index : 100;
		width: 400px;
		height: 420px;
		margin: -210px 0 0 -200px;
		border-radius : 10px;
		background-color: $blue;
		text-align : center;
		color :$lightblue ;
	}
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
</style>