<template>
	<div id="editPhone">
		<tipsDialog :msg="msgTips"></tipsDialog>
		<topbar title="修改手机号"></topbar>
		<back></back>
		<cs title="客服"></cs>
		<div class="page_cont">
			<div class="ipt_row">
				<label for="phone">原号码</label>
				<input type="number" id="phone" placeholder="" disabled v-model="userInfo.phone" />
			</div>
			<div class="ipt_row">
				<label for="code">验证码</label>
				<input type="number" id="code" placeholder="" v-model.trim="oldCode" />
				<span class="code" @tap.self="getOldCode">{{oldVolid ? info : (oldTime + '秒')}}</span>
			</div>
			<div class="ipt_row">
				<label for="phone">新号码</label>
				<input type="number" id="phone" placeholder="" v-model.trim="newPhone" />
			</div>
			<div class="ipt_row">
				<label for="code">验证码</label>
				<input type="number" id="code" placeholder="" v-model.trim="newCode" />
				<span class="code" @tap.self="getNewCode">{{newVolid ? info : (newTime + '秒')}}</span>
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
		name:'editPhone',
		components: {topbar, back, cs, btn, tipsDialog},
		data(){
			return {
				msg: '',
				newPhone: '',
				oldCode: '',
				newCode: '',
				oldTime: 0,
				newTime: 0,
				info:'获取验证码',
				phoneReg: /^(((13[0-9])|(14[5-7])|(15[0-9])|(17[0-9])|(18[0-9]))+\d{8})$/
			}
		},
		computed: {
			msgTips: function(){
				return this.msg;
			},
			PATH: function(){
				return this.$store.getters.PATH;
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
			},
			userInfo: function(){
				return this.$store.state.account;
			}
		},
		methods: {
			getOldCode: function(e){
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
				//请求发送验证码
				this.$http.post(this.PATH + '/user/security/send_sms',{emulateJSON: true},{
					headers: {
						'token':  this.userInfo.token,
						'secret': this.userInfo.secret
					},
					params: {
						mobile: this.userInfo.phone,
						type: 1
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
					this.msg = '服务器连接失败'
				});
			},
			getNewCode: function(e){
				if(this.newPhone == ''){
					this.$children[0].isShow = true;
					this.msg = '请输入手机号';
				}else if(this.phoneReg.test(this.newPhone) == false){
					this.$children[0].isShow = true;
					this.msg = '手机号格式错误';
				}else{
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
					//请求发送验证码
					this.$http.post(this.PATH + '/user/security/send_sms',{emulateJSON: true},{
						headers: {
							'token':  this.userInfo.token,
							'secret': this.userInfo.secret
						},
						params: {
							mobile: this.newPhone,
							type: 3
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
								case '3':
									this.$children[0].isShow = true;
									this.msg = '手机号码已经存在';
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
						this.msg = '服务器连接失败'
					});
				}
			},
			confirmEdit: function(){
				if(this.oldCode == '' || this.newCode == ''){
					this.isShow == true;
					this.msg == '请输入验证码';
				}else if(this.newPhone == ''){
					this.isShow == true;
					this.msg == '请输入新手机号';
				}else if(this.phoneReg.test(this.newPhone) == false){
					this.isShow == true;
					this.msg == '手机号格式错误';
				}else{
					this.$http.post(this.PATH + '/user/security/upphone', {emulateJSON: true},{
						headers: {
							'token':  this.userInfo.token,
							'secret': this.userInfo.secret
						},
						params: {
							oldCode: this.oldCode,
							newMobile: this.newPhone,
							newCode: this.newCode,
						},
						timeout: 5000
					}).then(function(e){
						var data = e.body;
						if(data.success == true){
							if(data.code == 1){
								this.$children[0].isShow = true;
								this.msg = '手机号修改成功';
								this.newPhone = '';
								this.oldCode = '';
								this.newCode = '';
								var usermsg = JSON.parse(localStorage.getItem("user"))
								usermsg.username = this.newPhone;
								localStorage.setItem(JSON.stringify(usermsg));
								this.$store.state.account.phone = this.newPhone;
								setTimeout(function(){
									this.$router.replace({path: '/account'});
								}.bind(this), 1000);
							}
						}else{
							switch (data.code){
								case '-1':
									this.$children[0].isShow = true;
									this.msg = '修改失败';
									break;
								case '2':
									this.$children[0].isShow = true;
									this.msg = '手机号已存在';
									break;
								case '5':
									this.$children[0].isShow = true;
									this.msg = '新手机验证码超时';
									break;
								case '6':
									this.$children[0].isShow = true;
									this.msg = '新手机验证码错误';
									break;
								default:
									break;
							}
						}
					}.bind(this), function(){
						this.$children[0].isShow = true;
						this.msg = '服务器连接失败';
					});
				}
			}
		},
		mounted: function(){
			//高度计算
			$("#editPhone").css("height", window.screen.height - 20 + 'px');
		},
		activated: function(){}
	}
</script>

<style scoped lang="less">
@import url("../../assets/css/base.less");
/*ip6p及以上*/
@media (min-width:411px) {
    #editPhone{
    	width: 100%;
    	overflow: hidden;
    	padding-top: 65px;
    	background: @deepblue;
    }
}
/*ip6*/
@media (min-width:371px) and (max-width:410px) {
	#editPhone{
    	width: 100%;
    	overflow: hidden;
    	padding-top: 65px*@ip6;
    	background: @deepblue;
    }
}
/*ip5*/
@media(max-width:370px) {
	#editPhone{
    	width: 100%;
    	overflow: hidden;
    	padding-top: 65px*@ip5;
    	background: @deepblue;
    }
}
</style>