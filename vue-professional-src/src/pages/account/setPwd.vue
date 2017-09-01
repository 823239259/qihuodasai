<template>
	<div id="setPwd">
		<tipsDialog :msg="msgTips"></tipsDialog>
		<topbar title="找回密码"></topbar>
		<back></back>
		<cs title="客服"></cs>
		<div class="page_cont">
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
	</div>
</template>

<script>
	import topbar from '../../components/Topbar.vue'
	import back from '../../components/back.vue'
	import cs from '../../components/customerService.vue'
	import btn from '../../components/bigBtn.vue'
	import tipsDialog from '../../components/tipsDialog.vue'
	export default{
		name:'setPwd',
		components: {topbar, back, cs, btn, tipsDialog},
		computed: {
			msgTips: function(){
				return this.msg;
			},
			PATH: function(){
				return this.$store.getters.PATH;
			}
		},
		data(){
			return {
				msg: '',
				pwd: '',
				newPwd: '',
				pwdReg: /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{6,18}$/
			}
		},
		methods: {
			toLogin: function(){
				var phone = this.$route.query.phone;
				if(this.pwd == ''){
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
					//请求设置新密码
					this.$http.post(this.PATH + '/reset_password',{emulateJSON: true},{
						params: {
							mobile: phone,
							password: this.pwd
						},
						timeout: 5000
					}).then(function(e){
						var data = e.body;
						if(data.success == true){
							if(data.code == 1){
								this.$children[0].isShow = true;
								this.msg = '密码重置成功';
								setTimeout(function(){
									this.$children[0].isShow = true;
									this.$router.push({path: '/login', query: {isJump: 1}});
								}.bind(this), 1000);
								this.pwd = '';
								this.newPwd = '';
							}
						}else{
							switch (data.code){
								case '-2':
									this.$children[0].isShow = true;
									this.msg = '请求参数未填写完整';
									break;
								case '3':
									this.$children[0].isShow = true;
									this.msg = '用户信息不存在';
									break;
								case '4':
									this.$children[0].isShow = true;
									this.msg = '验证码失效';
									break;
								case '5':
									this.$children[0].isShow = true;
									this.msg = '验证码错误';
									break;
								default:
									break;
							}
						}
					}.bind(this), function(){
						this.$children[0].isShow = true;
						this.msg = '网络不给力，请稍后再试！'
					});
				}
				
			}
		},
		mounted: function(){
			var h = window.screen.height - 20 - $("#topbar").height();
			$("#setPwd").height(h);
		},
	}
</script>

<style scoped lang="less">
@import url("../../assets/css/main.less");
@import url("../../assets/css/base.less");
/*ip6p及以上*/
@media (min-width:411px) {
    #setPwd{
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
	#setPwd{
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
	#setPwd{
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