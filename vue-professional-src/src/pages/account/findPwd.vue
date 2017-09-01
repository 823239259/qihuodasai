<template>
	<div id="findPwd">
		<tipsDialog :msg="msgTips"></tipsDialog>
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
			<btn name="下一步" @tap.native="toSetPwd"></btn>
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
		name:'findPwd',
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
				phone: '',
				code: '',
				time: 0,
				info:'获取验证码',
				phoneReg: /^(((13[0-9])|(14[5-7])|(15[0-9])|(17[0-9])|(18[0-9]))+\d{8})$/
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
					this.$http.post(this.PATH + '/sms',{emulateJSON: true},{
						params: {
							mobile: this.phone,
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
								case '7':
									this.$children[0].isShow = true;
									this.msg = '短信次数限制';
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
			},
			toSetPwd: function(){
				if(this.phone == ''){
					this.$children[0].isShow = true;
					this.msg = '请输入手机号';
				}else if(this.phoneReg.test(this.phone) == false){
					this.$children[0].isShow = true;
					this.msg = '手机号格式错误';
				}else if(this.code == ''){
					this.$children[0].isShow = true;
					this.msg = '请输入验证码';
				}else{
					//请求检验验证码
					this.$http.post(this.PATH + '/reset_validation/code',{emulateJSON: true},{
						params: {
							mobile: this.phone,
							code: this.code
						},
						timeout: 5000
					}).then(function(e){
						var data = e.body;
						if(data.success == true){
							if(data.code == 1){
								this.time = 0;
								this.$router.push({path: '/setPwd', query: {phone: this.phone}});
							}
						}else{
							switch (data.code){
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
									this.msg = '验证码错误或未填写';
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