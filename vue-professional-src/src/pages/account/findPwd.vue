<template>
	<div id="findPwd">
		<tipsDialog :msg="msgTips" ref="dialog"></tipsDialog>
		<topbar title="找回密码"></topbar>
		<back></back>
		<cs title="客服"></cs>
		<div class="page_cont">
			<div class="ipt_row">
				<label for="phone">手机号</label>
				<input type="number" id="phone" placeholder="请输入您的手机号" v-model.trim="phone" />
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
			<btn name="登　录" @tap.native="toLogin"></btn>
		</div>
		<p class="bottom_tips">如遇问题请拨打：400-852-8008</p>
		<codeDialog ref="codeDialog" type="findpwd"></codeDialog>
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
		name:'findPwd',
		components: {topbar, back, cs, btn, tipsDialog, codeDialog},
		data(){
			return {
				msg: '',
				phone: '',
				code: '',
				time: 0,
				pwd: '',
				newPwd: '',
				pwdReg: /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{6,18}$/,
				info: '获取验证码',
				phoneReg: /^(((13[0-9])|(14[5-7])|(15[0-9])|(17[0-9])|(18[0-9]))+\d{8})$/,
				num: 0
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
			version: function(){
				return JSON.parse(localStorage.version).ios;
			},
			environment(){
				return this.$store.state.environment;
			}
		},
		methods: {
			getCode: function(e){
				if($(e.target).hasClass('current')) return false;
				if(this.phone == ''){
					this.$children[0].isShow = true;
					this.msg = '请输入手机号';
				}else if(this.phoneReg.test(this.phone) == false){
					this.$children[0].isShow = true;
					this.msg = '手机号格式错误';
				}else{
					console.log(this.num);
					if(this.num && this.num > 2){
						this.$refs.codeDialog.isshow = true;
						if(this.environment == 'test'){
							this.$refs.codeDialog.path = "http://test.api.duokongtai.cn/sendImageCode?code=" + Math.random()*1000 + "&mobile=" + this.phone;
						}else{
							this.$refs.codeDialog.path = "http://api.duokongtai.cn/sendImageCode?code=" + Math.random()*1000 + "&mobile=" + this.phone;
						}
						this.$refs.codeDialog.phone = this.phone;
					}else{
						//请求发送验证码
						this.$http.post(this.PATH + '/sms',{emulateJSON: true},{
							params: {
								mobile: this.phone,
								type: 2,
							},
							timeout: 5000
						}).then(function(e){
							var data = e.body;
							console.log(data);
							if(data.success == true){
								if(data.code == 1){
									this.$refs.dialog.isShow = true;
									this.msg = '发送成功';
								}
							}else{
								this.$refs.dialog.isShow = true;
								this.msg = data.message;
							}
						}.bind(this), function(){
							this.$refs.dialog.isShow = true;
							this.msg = '网络不给力，请稍后再试！'
						});
					}
					
					
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
			toLogin: function(){
				if(this.phone == ''){
					this.$refs.dialog.isShow = true;
					this.msg = '请输入手机号';
				}else if(this.phoneReg.test(this.phone) == false){
					this.$refs.dialog.isShow = true;
					this.msg = '手机号格式错误';
				}else if(this.code == ''){
					this.$refs.dialog.isShow = true;
					this.msg = '请输入手机验证码';
				}else if(this.pwd == ''){
					this.$refs.dialog.isShow = true;
					this.msg = '请输入新密码';
				}else if(this.newPwd == ''){
					this.$refs.dialog.isShow = true;
					this.msg = '请确认新密码';
				}else if(this.pwdReg.test(this.pwd) == false || this.pwdReg.test(this.newPwd) == false){
					this.$refs.dialog.isShow = true;
					this.msg = '密码格式错误';
				}else if(this.pwd != this.newPwd){
					this.$refs.dialog.isShow = true;
					this.msg = '两次密码输入不一致';
				}else{
					//请求设置新密码
					this.$http.post(this.PATH + '/reset_password',{emulateJSON: true},{
						headers: {'version': this.version},
						params: {
							mobile: this.phone,
							password: this.pwd,
							code: this.code
						},
						timeout: 5000
					}).then(function(e){
						var data = e.body;
						this.$refs.dialog.isShow = true;
						if(data.success == true){
							if(data.code == 1){
								this.msg = '密码重置成功';
								setTimeout(function(){
									this.$router.push({path: '/login', query: {isJump: 1}});
								}.bind(this), 1000);
								this.phone = '';
								this.code = '';
								this.pwd = '';
								this.newPwd = '';
							}
						}else{
							this.num = data.data;
							console.log(this.num);
							this.msg = data.message;
						}
					}.bind(this), function(){
						this.$refs.dialog.isShow = true;
						this.msg = '网络不给力，请稍后再试！'
					});
				}
			}
		},
		mounted: function(){
			var h = window.screen.height - 20 - $("#topbar").height();
			$("#findPwd").height(h);
		},
	}
</script>

<style scoped lang="less">
@import url("../../assets/css/main.less");
@import url("../../assets/css/base.less");
/*ip6p及以上*/
@media (min-width:411px) {
    #findPwd{
    	width: 100%;
    	overflow: hidden;
    	padding-top: 65px;
    	background: @deepblue;
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
	#findPwd{
    	width: 100%;
    	overflow: hidden;
    	padding-top: 65px*@ip6;
    	background: @deepblue;
    	.bottom_tips{
    		position: fixed;
    		bottom: 20px;
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
	#findPwd{
    	width: 100%;
    	overflow: hidden;
    	padding-top: 65px*@ip5;
    	background: @deepblue;
    	.bottom_tips{
    		position: fixed;
    		bottom: 20px;
    		left: 0;
    		width: 100%;
    		text-align: center;
    		font-size: @fs14*@ip5;
    		color: #7a7f99;
    	}
    }
}
</style>