<template>
	<div id="editPwd">
		<tipsDialog :msg="msgTips"></tipsDialog>
		<topbar title="修改提现密码"></topbar>
		<back></back>
		<cs title="客服"></cs>
		<div class="page_cont">
			<div class="ipt_row">
				<label for="phone">手机号</label>
				<input type="number" id="phone" placeholder="请输入您的手机号" disabled v-model="userInfo.username" />
			</div>
			<div class="ipt_row">
				<label for="code">验证码</label>
				<input type="number" id="code" placeholder="请输入验证码" v-model.trim="code" />
				<span class="code" @tap="getCode">{{volid ? info : (time + '秒')}}</span>
			</div>
			<div class="ipt_row">
				<label for="pwd">新密码</label>
				<input type="password" id="pwd" placeholder="请输入您的新密码" v-model.trim="pwd" />
			</div>
			<div class="ipt_row">
				<label for="newPwd">确认密码</label>
				<input type="password" id="newPwd" placeholder="请确认您的新密码" v-model.trim="newPwd" />
			</div>
			<btn name="确认修改" @tap.native="confirmEdit"></btn>
		</div>
	</div>
</template>

<script>
	import topbar from '../../components/Topbar.vue'
	import back from '../../components/back.vue'
	import cs from '../../components/customerService.vue'
	import btn from '../../components/bigBtn.vue'
	import tipsDialog from '../../components/tipsDialog.vue'
	export default{
		name:'editPwdFirst',
		components: {topbar, back, cs, btn, tipsDialog},
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
			}
		},
		data(){
			return {
				msg: '',
				code: '',
				time: 0,
				info:'获取验证码',
				pwd: '',
				newPwd: '',
				pwdReg: /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{6,18}$/,
				userInfo: ''
			}
		},
		methods: {
			getCode: function(e){
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
				//请求发送验证码
				this.$http.post(this.PATH + '/user/security/send_sms',{emulateJSON: true},{
					headers: {
						'token':  this.userInfo.token,
						'secret': this.userInfo.secret
					},
					params: {
						mobile: this.userInfo.username,
						type: 2
					},
					timeout: 5000
				}).then(function(e){
					var data = e.body;
					if(data.success == true){
						if(data.code == 1){
							this.$children[0].isShow = true;
							this.msg = '发送成功';
						}
					}else{
						switch (data.code){
							case '2':
								this.$children[0].isShow = true;
								this.msg = '短信验证码发送失败';
								break;
							case '4':
								this.$children[0].isShow = true;
								this.msg = '手机号码不存在';
								break;
							case '5':
								this.$children[0].isShow = true;
								this.msg = '操作过于频繁，请稍候再试';
								break;
							case '6':
								this.$children[0].isShow = true;
								this.msg = '电话号码格式错误';
								break;
							default:
								break;
						}
					}
				}.bind(this), function(){
					this.$children[0].isShow = true;
					this.msg = '网络不给力，请稍后再试！'
				});
			},
			confirmEdit: function(){
				if(this.code == ''){
					this.$children[0].isShow = true;
					this.msg = '请输入验证码';
				}else if(this.pwd == ''){
					this.$children[0].isShow = true;
					this.msg = '请输入新密码';
				}else if(this.newPwd == ''){
					this.$children[0].isShow = true;
					this.msg = '请确认新密码';
				}else if(this.pwdReg.test(this.pwd) == false || this.pwdReg.test(this.newPwd) == false){
					this.$children[0].isShow = true;
					this.msg = '密码格式错误';
				}else if(this.pwd != this.newPwd){
					this.$children[0].isShow = true;
					this.msg = '两次密码输入不一致';
				}else{
					//请求修改提现密码
					this.$http.post(this.PATH + '/user/security/set_withdraw_pwd', {emulateJSON: true},{
						headers: {
							'token':  this.userInfo.token,
							'secret': this.userInfo.secret
						},
						params: {
							code: this.code,
							password: this.pwd,
						},
						timeout: 5000
					}).then(function(e){
						var data = e.body;
						if(data.success == true){
							if(data.code == 1){
								this.$children[0].isShow = true;
								this.msg = '提现密码修改成功';
								this.time = 0;
								this.code = '';
								this.pwd = '';
								this.newPwd = '';
								setTimeout(function(){
									this.$router.replace({path: '/withdrawal', query: {isJump: 1}});
								}.bind(this), 1000);
							}
						}else{
							switch (data.code){
								case '-1':
									this.$children[0].isShow = true;
									this.msg = '认证失败';
									break;
								case '2':
									this.$children[0].isShow = true;
									this.msg = '参数没有传递';
									break;
								case '3':
									this.$children[0].isShow = true;
									this.msg = '用户信息不存在';
									break;
								case '4':
									this.$children[0].isShow = true;
									this.msg = '提现密码不能和登录密码相同';
									break;
								case '6':
									this.$children[0].isShow = true;
									this.msg = '验证码错误';
									break;
								default:
									break;
							}
						}
					}.bind(this), function(){
						this.$children[0].isShow = true;
						this.msg = '网络不给力，请稍后再试！';
					});
				}
				
			}
		},
		mounted: function(){
			$("#editPwd").css("height", window.screen.height - 20 + 'px');
		},
		activated: function(){
			this.userInfo = JSON.parse(localStorage.user);
		}
	}
</script>

<style scoped lang="less">
@import url("../../assets/css/base.less");
/*ip6p及以上*/
@media (min-width:411px) {
    #editPwd{
    	width: 100%;
    	overflow: hidden;
    	padding-top: 65px;
    	background: @deepblue;
    }
}
/*ip6*/
@media (min-width:371px) and (max-width:410px) {
	#editPwd{
    	width: 100%;
    	overflow: hidden;
    	padding-top: 65px*@ip6;
    	background: @deepblue;
    }
}
/*ip5*/
@media(max-width:370px) {
	#editPwd{
    	width: 100%;
    	overflow: hidden;
    	padding-top: 65px*@ip5;
    	background: @deepblue;
    }
}
</style>