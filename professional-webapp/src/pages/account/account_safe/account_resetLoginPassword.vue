<template>
	<div id="account_resetLoginPassword">
		<div class="account_resetLoginPassword_top">
			<p>您正在为账户<span>{{phone}}</span>重置手机密码</p>
		</div>
		<div class="account_resetLoginPassword_center">
			<div class="center_left">
				<ul>
					<p>手机号码:</p>
					<p>获取验证码：</p>
					<p>新登录密码：</p>
					<p>确认新密码：</p>
				</ul>
			</div>
			<div class="center_right">
				<ul>
					<p>{{username}}</p>
					<p><input type="text" v-model="code"/><i class="getcode" v-on:click="getCode">{{volid ? info : (time + '秒')}}</i></p>
					<p><input type="password" v-model="newLoginPassword" /></p>
					<p><input type="password" v-model="sureLoginPassword"/></p>
					<p><button class="btn yellow" v-on:click="toResetLoginPassword">确认</button></p>
				</ul>
			</div>
		</div>
		<div class="account_resetLoginPassword_btm">
			<p>设置资金密码遇到问题</p>
			<ul>	
				<li>重置新密码规则？</li>
				<li>新密码与旧密码不能相同，如果有疑问请拨打客服热线</li>
			</ul>
			<p>投资有风险，入市需谨慎</p>
		</div>
		<codeDialog ref="codeDialog" type="resetMobile"></codeDialog>
	</div>
</template>
<script>
	import pro from "../../../assets/js/common.js"
	import codeDialog from "../../../components/codeDialog.vue"
	export default {
		name : "safe_resetLoginPassword",
		components: {codeDialog},
		data(){
			return{
				phone:'',
				newLoginPassword:'',
				sureLoginPassword:'',
				username : '',
				info:'点击获取',
				time : 0,
				path:'',
				code :''
			}
		},
		computed : {
			PATH: function(){
				return this.$store.getters.PATH;
			},
			environment(){
				return this.$store.state.environment;
			},
			volid: function(){
				if(this.time <= 0){
					return true
				}else{
					return false
				}
			}
		},
		methods:{
			toResetLoginPassword : function(){
				var data = {
					password : this.newLoginPassword,
					code : this.code
				}
				var headers = {
						token:JSON.parse(localStorage.user).token,
						secret : JSON.parse(localStorage.user).secret
					}
				var pwdReg = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{6,18}$/;
				if(this.code == ''){
					layer.msg('请输入验证码',{time:1000})
				}else if(this.newLoginPassword == ''){
					layer.msg('请输入新登录密码',{time:1000})
				}else if(pwdReg.test(this.newLoginPassword ==false)){
					layer.msg('请输入正确的新登录密码',{time:1000})
				}else if(this.sureLoginPassword == ''){
					layer.msg('请输入确认新密码',{time:1000})
				}else if(pwdReg.test(this.sureLoginPassword)==false){
					layer.msg('请输入正确新登录密码',{time:1000})
				}else if(this.newLoginPassword!=this.sureLoginPassword){
					layer.msg('新密码和确认密码不一致 ',{time:1000})
				}else if(this.oldLoginPassword == this.newLoginPassword){
					layer.msg('新密码和旧密码一致 ',{time:1000})
				}else {
					pro.fetch("post",'/user/security/update_loginPwd',data,headers).then((res)=>{
						if(res.success == true){
							if(res.code == 1){
								layer.msg('设置成功',{time:1000});
								this.$router.push({path:'/account_safe'})
							}
						}
					}).catch((err)=>{
						if(err.data.success == false){
							switch (err.data.code){
								case '-2':
									layer.msg('参数错误',{time:1000});
									break;
								case '-1':
									layer.msg('认证失败',{time:1000});
									break;
								case '3':
									layer.msg('用户信息不存在',{time:1000});
									break;
								case '4':
									layer.msg('密码格式错误',{time:1000});
									break;
								case '5':
									layer.msg('验证码失效',{time:1000});
									break;
								case '6':
									layer.msg('验证码错误',{time:1000});
									break;
								case '7':
									layer.msg('新密码与旧密码相同',{time:1000});
									break;
								default:
									break;
							}
						}else{
								layer.msg('网络不给力，请稍后重试',{time:1000});
						}
					})
				}
			},
			getCode :function(e){
				//获取验证码
				var data = {
					mobile: this.phone,
					type: 1
				}
				var headers = {
					token : JSON.parse(localStorage.user).token,
					secret :JSON.parse(localStorage.user).secret
				}
				pro.fetch("post","/user/security/send_sms",data,headers).then((res)=>{
					if(res.success == true){
						if(res.code == 1){
							if($(e.target).hasClass('current')) return false;
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
								layer.msg("发送成功",{time:2000})
						}
					}
				}).catch((err)=>{
					console.log(err)
					if(err.data.success == false){
						switch (err.data.code){
							case '2':
								layer.msg("短信验证码发送失败",{time:1000})
								break;
							case '4':
								layer.msg("手机号码不存在",{time:1000})
								break;
							case '5':
								layer.msg("操作过于频繁，请稍候再试",{time:1000})
								break;
							case '6':
								layer.msg("电话号码格式错误",{time:1000})
								break;
							default:
								break;
						}
					}else {
						layer.msg("网络不给力，请稍后再试",{time:1000})
					}
				})
			},
		},
		created(){
			this.phone =JSON.parse(localStorage.user).username;
			this.username = JSON.parse(localStorage.user).username;
		}
	}
</script>


<style lang="scss" scoped type="text/css">
	@import "../../../assets/css/common.scss";
	#account_resetLoginPassword {
		width: 100%;
		.account_resetLoginPassword_top{
			width:100%;
			p {
				width: 100%;
				height: 40px;
				line-height: 40px;
				background-color: $bottom_color;
				span {
					color:$white;
				}
			}
		}
		.account_resetLoginPassword_center {
			width: 100%;
			height: 330px;
			background-color: $blue;
			text-align : center;
			.center_left{
				width: 50%;
				float: left;
				text-align: right;
				p{
					margin-top: 10px;
					height: 50px;
					line-height: 50px;
					&:nth-child(3){
						border-bottom: 1px solid $bottom_color;
					}
				}
			}
			.center_right{
				width: 50%;
				float: left;
				text-align: left;
				p{
					margin-top: 10px;
					height: 50px;
					line-height: 50px;
					&:nth-child(3){
						border-bottom: 1px solid $bottom_color;
					}
					&:nth-child(1){
						color: white;
					}
				}
				input {
					width : 160px;
					height: 30px;
					border: 1px solid $bottom_color;
					border-radius: 5px;
					color: $white;
				}
				.btn {
					width: 160px;
					height: 30px;
					margin-top: 30px;
				}
			}	
		}
		.account_resetLoginPassword_btm {
			width: 100%;
			height: 170px;
			p {
				width: 100%;
				height: 40px;
				background-color: $bottom_color;
				color: $white;
				line-height: 40px;
				margin-top: 5px;
				&:nth-child(3){
					text-align: center;
					font-size: $fs12;
					color: #7a7f99;
				}
			}
			ul {
				height: 80px;
				background-color: $blue;
			}
			li {
				&:nth-child(1){
					padding-top: 20px;
					font-size: $fs12;
				}
				&:nth-child(2){
					color: $white;
					font-size: $fs12;
					padding-top: 10px;
				}
			}
		}
	}
	.getcode {
		position: relative;
		left: -70px;
		background-color: $highLight;
		color: $white;
		padding: 9px 8px;
		top: 2px;
	}
</style>
