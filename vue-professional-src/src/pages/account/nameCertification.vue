<template>
	<div id="nameCertification">
		<tipsDialog :msg="msgTips"></tipsDialog>
		<topbar title="实名认证"></topbar>
		<back></back>
		<cs title="客服"></cs>
		<div class="page_cont">
			<div class="ipt_row">
				<label for="name">真名姓名</label>
				<input type="text" id="name" placeholder="请输入真实姓名" v-model.trim="name" />
			</div>
			<div class="ipt_row">
				<label for="idcard">身份证号</label>
				<input type="text" id="idcard" placeholder="请输入身份证号" v-model.trim="idcard" />
			</div>
			<btn name="立即认证" @tap.native="nameCheck"></btn>
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
		name:'nameCertification',
		components: {topbar, back, cs, btn, tipsDialog},
		data(){
			return {
				msg: '',
				name: '',
				idcard: ''
			}
		},
		computed: {
			msgTips: function(){
				return this.msg;
			},
			PATH: function(){
				return this.$store.getters.PATH;
			},
			userInfo: function(){
				return this.$store.state.account;
			}
		},
		methods: {
			nameCheck: function(){
				var cardReg = /^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$/;
				if(this.name == ''){
					this.$children[0].isShow = true;
					this.msg = '请输入真实姓名';
				}else if(this.idcard == ''){
					this.$children[0].isShow = true;
					this.msg = '请输入身份证号';
				}else if(cardReg.test(this.idcard) == false){
					this.$children[0].isShow = true;
					this.msg = '身份证号格式错误';
				}else{
					//实名认证请求
					this.$http.post(this.PATH + '/user/security/validatecard', {emulateJSON: true}, {
						headers: {
							token:  this.userInfo.token,
							secret: this.userInfo.secret
						},
						params: {
							name: this.name,
							card: this.idcard
						},
						timeout: 5000
					}).then(function(e) {
						var data = e.body;
						if(data.success == true ){
							if(data.code == 1){
								this.$children[0].isShow = true;
								this.msg = '实名认证成功';
								this.$store.state.account.isCertification = true;
								this.$store.state.account.username = this.name;
								this.name = '';
								this.idcard = '';
								this.$router.replace({path: '/account'});
							}
						}else{
							switch (data.code){
								case '-1':
									this.$children[0].isShow = true;
									this.msg = '认证失败';
									break;
								case '2':
									this.$children[0].isShow = true;
									this.msg = '实名认证失败';
									break;
								case '3':
									this.$children[0].isShow = true;
									this.msg = '身份证号格式有误';
									break;
								case '4':
									this.$children[0].isShow = true;
									this.msg = '该身份证已经被认证过';
									break;
								case '8':
									this.$children[0].isShow = true;
									this.msg = '操作频率过高';
									break;
								default:
									break;
							}
						}
					}.bind(this), function() {
						this.$children[0].isShow = true;
						this.msg = '服务器连接失败'
					});
				}
			}
		},
		mounted: function(){
			$("#nameCertification").css("height", window.screen.height - 20 + 'px');
		},
	}
</script>

<style scoped lang="less">
@import url("../../assets/css/base.less");
/*ip6p及以上*/
@media (min-width:411px) {
    #nameCertification{
    	width: 100%;
    	overflow: hidden;
    	padding-top: 65px;
    	background: @deepblue;
    }
}
/*ip6*/
@media (min-width:371px) and (max-width:410px) {
	#nameCertification{
    	width: 100%;
    	overflow: hidden;
    	padding-top: 65px*@ip6;
    	background: @deepblue;
    }
}
/*ip5*/
@media(max-width:370px) {
	#nameCertification{
    	width: 100%;
    	overflow: hidden;
    	padding-top: 65px*@ip5;
    	background: @deepblue;
    }
}
</style>