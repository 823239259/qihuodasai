<template>
		<div id="account_resetCellPassword">
			<div class="account_resetCellPassword_top">
				<p>您正在为账户<span>{{phone}}</span>修改绑定手机</p>
			</div>
			<div class="account_resetCellPassword_center">
				<p>旧手机号码：<span>{{username}}</span></p>
				<p>短信验证码：<input type="text" v-model="oldCode"/><i class="getcode" v-on:click="getOldCode">{{oldVolid ? info : (oldTime + '秒')}}</i></p>
				<p>新手机号码：<input type="text" v-model="newMobile" maxlength="11"/></p>
				<p>短信验证码:<input type="text" v-model="newCode"/><i class="getcode" v-on:click="getNewCode">{{newVolid ? info : (newTime + '秒')}}</i></p>
				<button class="btn yellow" v-on:click="toResetMobile">确认</button>
			</div>
			<div class="account_resetCellPassword_btm">
				<p>绑定手机遇到问题</p>
				<ul>
					<li>更改手机绑定收不到验证码怎么办？</li>
					<li>答：建议把手机放置在信号好的地点或者检查手机号码输入是否有误，如有疑问请拨打客服热线。</li>
				</ul>
				<p>投资有风险，入市需谨慎</p>
			</div>
		</div>
</template>

<script>
	import pro from "../../../assets/js/common.js"
	import codeDialog from "../../../components/codeDialog.vue"
	export default {
		name : "safe_resetCellPhone",
		data(){
			return{
				oldTime:'',
				newTime:'',
				oldCode:'',
				newMobile:'',
				newCode:'',
				info:'点击获取',
				infos:'点击获取',
				path:'',
				phone:'',
				username:'',
				phoneReg: /^(((13[0-9])|(14[5-7])|(15[0-9])|(17[0-9])|(18[0-9]))+\d{8})$/,
			}
		},
		computed : {
			PATH: function(){
				return this.$store.getters.PATH;
			},
			environment(){
				return this.$store.state.environment;
			},
			oldVolid: function(){
				if(this.oldTime <= 0){
					return true
				}else{
					return false
				}
			},
			newVolid: function(){
				if(this.newTime <= 0){
					return true
				}else{
					return false
				}
			}
		},
		methods:{
			toResetMobile:function(){
				if(this.oldCode == ''){
					layer.msg('请输入旧手机验证码',{time:1000});
				}else if(this.newMobile == ''){
					layer.msg('请输入新手机号码',{time:1000});
				}else if(this.newCode == ''){
					layer.msg('请输入新手机验证码',{time:1000});
				}else if(phoneReg.test(this.newMobile) == false){
					layer.msg('手机格式错误',{time:1000});
				}else {
					var headers = {
						token:JSON.parse(localStorage.user).token,
						secret : JSON.parse(localStorage.user).secret
					}
					var data = {
						newMobile:this.newMobile,
						oldCode:this.oldCode,
						newCode:this.newCode
					}
					pro.fetch("post",'/user/security/upphone',data,headers).then((res)=>{
						if(res.success == true){
							if(res.code == 1){
								layer.msg('设置成功',{time:1000})
								this.$router.path({path:'/account_safe'})
							}
						}
					}).catch((err)=>{
						if(err.data.success == false){
							switch (err.data.code){
								case '-1':
									layer.msg('修改失败',{time:1000});
									break;
								case '2':
									layer.msg('手机号已存在',{time:1000});
									break;
								case '5':
									layer.msg('新手机验证码超时',{time:1000});
									break;
								case '6':
									layer.msg('新手机验证码错误',{time:1000});
									break;
								default:
									break;
							}
						}else{
							layer.msg('网络不给力，请稍后再试',{time:1000})
						}
					})
				}
			},
			getOldCode :function(e){
				//获取验证码
				var data = {
					mobile: this.phone,
					type: 3
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
								this.oldTime = 60;
								var timing = setInterval(function(){
									this.oldTime--;
									if(this.oldTime <= 0){
										clearInterval(timing);
										$(e.target).removeClass('current');
									}
								}.bind(this), 1000);
							layer.msg("发送成功",{time:2000})
						}
					}
				}).catch((err)=>{
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
			getNewCode:function(e){
				if(this.newMobile == this.username){
					layer.msg("该手机号已经被注册，请更换后重试",{time:2000})
				}else if(this.newMobile == ''){
					layer.msg("请输入新手机号码",)
				}else if(this.phoneReg.test(this.newMobile) == false){
					layer.msg("手机格式不正确",{time:2000})
				}else{
					//获取验证码
					var data = {
						mobile: this.newMobile,
						type: 3
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
									this.newTime = 60;
									var timing = setInterval(function(){
										this.newTime--;
										if(this.newTime <= 0){
											clearInterval(timing);
											$(e.target).removeClass('current');
										}
									}.bind(this), 1000);
								layer.msg("发送成功",{time:2000})
							}
						}
					}).catch((err)=>{
						if(err.data.success == false){
							switch (err.data.code){
								case "2":
									layer.msg("短信验证码发送失败",{time:1000})
									break;
								case "3":
									layer.msg("手机号码已经存在",{time:1000})
									break;
								case "4":
									layer.msg("手机号码不存在",{time:1000})
									break;
								case "5":
									layer.msg("操作过于频繁，请稍候再试",{time:1000})
									break;
								case "6":
									layer.msg("电话号码格式错误",{time:1000})
									break;
								default:
									break;
							}
						}else {
							layer.msg("网络不给力，请稍后再试",{time:1000})
						}
					})
				}
			}
		},
		created(){
			this.phone = JSON.parse(localStorage.user).username;
			this.username =JSON.parse(localStorage.user).username;
		}
	}
</script>


<style lang="scss" scoped type="text/css">
	@import "../../../assets/css/common.scss";
	#account_resetCellPassword {
		width: 100%;
		.account_resetCellPassword_top{
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
		.account_resetCellPassword_center {
			width: 100%;
			height: 290px;
			background-color: $blue;
			text-align : center;
			p {
				&:nth-child(1){
					margin-right: 156px;
				}
				padding-top : 20px;
				&:nth-child(3){
					margin-right: 70px;
				}
			}
			input {
				width: 160px;
				color: $white;
				height: 30px;
				border: 1px solid $bottom_color;
				border-radius: 5px;
			}
			.btn {
				width: 160px;
				height: 30px;
				margin-top: 10px;
			}
		}
		.account_resetCellPassword_btm {
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