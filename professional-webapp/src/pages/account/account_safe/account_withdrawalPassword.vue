<template>
	<div id="account_withdrawlPassword">
		<div class="account_withdrawlPassword_top">
			<p>您正在为账户<span>{{phone}}</span>设置提现密码</p>
		</div>
		<div class="account_withdrawlPassword_center">
			<div class="center_left">
				<p>手机号码：</p>
				<p>短信验证码：</p>
				<p>提现密码：</p>
				<p>确认密码：</p>
			</div>
			<div class="center_right">
				<p>{{username}}</p>
				<p><input type="text" v-model="code" /><i class="getcode" v-on:click="getCode">{{volid ? info : (time + '秒')}}</i></p>
				<p><input type="password" v-model="withDrawPassword" /></p>
				<p><input type="password" v-model="sure_withDrawPassword" /></p>
				<button class="btn yellow" v-on:click="comfire">确认</button>
			</div>
		</div>
		<div class="account_withdrawlPassword_btm">
			<p>实名认证相关</p>
			<ul>
				<li>实名认证遇到问题怎么办？</li>
				<li>答：实名认证因涉及到账户提现安全，最好认证本人身份信息，认证信息错误三次以上需要后台人工审核才可继续认证。</li>
			</ul>
			<p>投资有风险，入市需谨慎</p>
		</div>
	</div>
</template>

<script>
	import pro from "../../../assets/js/common.js"
	import codeDialog from "../../../components/codeDialog.vue"
	export default {
		name : "safe_withdrawalPassword",
		data(){
			return {
				time:0,
				info : "点击获取",
				code : '',
				withDrawPassword : '',
				sure_withDrawPassword: '',
				phone:'',
				username:'',
				pwdReg: /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{6,18}$/
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
		methods :{
			getCode :function(e){
				//获取验证码
				var data = {
					mobile: this.username,
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
							layer.msg("发送成功",{time:1000})
						}
					}
				}).catch((err)=>{
					if(err.data.success == false){
						switch (err.data.code){
							case 2:
								layer.msg("短信验证码发送失败",{time:1000})
								break;
							case 4:
								layer.msg("手机号码不存在",{time:1000})
								break;
							case 5:
								layer.msg("操作过于频繁，请稍候再试",{time:1000})
								break;
							case 6:
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
			comfire:function(){
				if(this.code == ''){
					layer.msg('请输入验证码',{time:1000});
				}else if(this.withDrawPassword == ''){
					layer.msg('请输入提现密码',{time:1000});
				}else if(this.sure_withDrawPassword == ''){
					layer.msg('请输入确认密码',{time:1000});
				}else if (this.withDrawPassword!=this.sure_withDrawPassword){
					this.withDrawPassword = '';
					this.sure_withDrawPassword = '';
					layer.msg('两次密码输入不一致，请重新输入',{time:1000});
				}else if(this.pwdReg.test(this.withDrawPassword) == false || this.pwdReg.test(this.sure_withDrawPassword)==false){
					layer.msg("密码格式错误，请重新设置",{time:2000});
				}else {
					var data = {
						password : this.withDrawPassword,
						code : this.code
					}
					var headers = {
						token : JSON.parse(localStorage.user).token,
						secret:JSON.parse(localStorage.user).secret
					}
					pro.fetch("post","/user/security/set_withdraw_pwd",data,headers).then((res)=>{
						if(res.success == true){
							if(res.code == 1){
								layer.msg("设置成功",{time:2000});
								this.$router.push({path:'/account_safe'})
							}
						}
					}).catch((err)=>{
						console.log(err);
						if(err.data.success == false){
							switch (err.data.code){
								case '-1':
									layer.msg("认证失败",{time:2000});
									break;
								case '2':
									layer.msg("参数没有传递",{time:2000});
									break;
								case '3':
									layer.msg("用户信息不存在",{time:2000});
									break;
								case '4':
									layer.msg("提现密码不能和登录密码相同",{time:2000});
									break;
								case '6':
									layer.msg("验证码错误",{time:2000});
									break;
								default:
									break;
							}
						}else{
							layer.msg("网络不给力，请稍后再试",{time:1000})
						}
					})
				}
			}
		},
		created(){
			this.phone = JSON.parse(localStorage.user).username;
			this.username = JSON.parse(localStorage.user).username;
		}
		
	}
</script>


<style lang="scss" scoped type="text/css">
	@import "../../../assets/css/common.scss";
	#account_withdrawlPassword {
		width: 100%;
		.account_withdrawlPassword_top {
			 p {
			 	text-indent:10px;
			 	width: 100%;
			 	height: 40px;
			 	line-height: 40px;
			 	background-color: $bottom_color;
			 	span {
			 		color:$white;
			 		margin : 0 5px 0 5px;
			 	}
			 }
		}
		.account_withdrawlPassword_center{
			width: 100%;
			height: 330px;
			text-align: center;
			background-color: $blue;	
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
		.account_withdrawlPassword_btm {
			width: 100%;
			height: 170px;
			p {
				text-indent: 10px;
				&:nth-child(1){
					width: 100%;
					height: 40px;
					background-color: $bottom_color;
					line-height: 40px;
					margin-top: 10px;
				}
				&:nth-child(3){
					width: 100%;
					height: 40px;
					line-height: 40px;
					margin-top: 10px;
					background-color: $blue;
					text-align: center;
					font-size: $fs12;
					background-color: $bottom_color;
				}
			}
			ul {
				height: 80px;
				width: 100%;
				background-color: $blue;
			}
			li {
				padding-left: 10px;
				padding-top:20px; 
				&:nth-child(2) {
					color: $white;
					font-size: $fs12;
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