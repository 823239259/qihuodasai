<template>
	<div id="forgetPassword" >
		<div class="bg"></div>
		<div class="forgetPassword">
			<p><i class="ifont ifont_left">&#xe625;</i>设置密码<i class="ifont ifont_right">&#xe624;</i></p>
			<input type="password" id="pwd" class="input_1" placeholder="请输入新密码"v-model.trim="pwd"  />
			<input type="password" id="newPwd" class="input_2"  placeholder="确认新密码"v-model.trim="newPwd"  />
			<button class="btn yellow" v-on:click="toLogin">确认</button>
			<p class="color_light">还没有期货大赛账号？<span class="span_white">立即注册</span></p>
		</div>
		<tipsDialog :msg="msgTips" time="2000" ref="dialog"></tipsDialog>
	</div>
</template>

<script>
	import tipsDialog from "../../components/tipsDialog.vue"
	import axios from "axios";
	import qs from "qs";
	export default {
		name : "resetPassword",
		components : {tipsDialog},
		data(){
			return {
				pwdReg: /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{6,18}$/,
				pwd : '',
				newPwd : ''
			}
		},
		computed : {
			msgTips: function(){
				return this.msg;
			},
			PATH: function(){
				return this.$store.getters.PATH;
			},
			version: function(){
				return '1.1';
			}
		},
		methods : {
			toLogin : function(){
//				if(this.pwd == ''){
//					this.$refs.dialog.isShow = true;
//					this.msg = '请输入新密码';
//					console.log(4444444)
//				}else if(this.newPwd == ''){
//					this.$refs.dialog.isShow = true;
//					this.msg = '请确认新密码';
//					console.log(55555555)
//				}else if(this.pwdReg.test(this.pwd) == false || this.pwdReg.test(this.newPwd) == false){
//					this.$refs.dialog.isShow = true;
//					this.msg = '密码格式错误';
//					console.log(111111111)
//				}else if(this.pwd != this.newPwd){
//					this.$refs.dialog.isShow = true;
//					this.msg = '两次密码输入不一致';
//					console.log(3333333333)
//				}else{
//					//请求设置新密码
//					var data = {
//						mobile: this.phone,
//						password: this.pwd,
//						code: this.code
//					}
//					axios({
//						method : 'post',
//						url : this.PATH+'/reset_password',
//						timeout : 5000,
//						headers:{'version':this.version},
//						data : qs.stringify(data)
//					}).then((res)=>{
//						var data =res.data;
//						console.log(data);
//						this.$refs.dialog.isShow = true;
//						if(data.success == true){
//							if(data.code == 1){
//								this.msg = '密码重置成功';
//								setTimeout(function(){
//									this.$router.push({path: '/login', query: {isJump: 1}});
//									console.log("whisha")
//								}.bind(this), 1000);
//								this.pwd = '';
//								this.newPwd = '';
//							}
//						}else{
//							cosnole.log(6666666666)
//							this.code = '';
//							this.num = data.data;
//							this.msg = data.message;
//						}
//					}).catch((err)=>{
//						console.log(2222222222222);
//						var data =err.data;
//						this.$refs.dialog.isShow = true;
//						this.msg = '网络不给力，请稍后再试！'
//					})
//				}
			}
		}
	}
</script>
<style lang="scss" scoped type="text/css">
	@import "../../assets/css/common.scss";
	.forgetPassword {
		position : absolute;
		z-index:100;
		width: 400px;
		background-color: $blue;
		p {
			color:$white;
			line-height : 40px;
			&:nth-child(1) {
				height: 40px;
				background-color: $bottom_color;
			}
		}
		input {
			width: 320px;
			height: 40px;
			text-align: center;
			color: $white;
			margin-bottom: 10px;
			border-radius: 5px;
			border: 1px solid $bottom_color;
			&:hover{
				border: 1px solid $yellow;
			}
		}
		.btn {
			width: 320px;
			height: 40px;
			margin-top: 20px;
		}
		.color_light {
			color: $lightblue;
			margin-top: 10px;
		}
		.span_white {
			color: $white;
		}
		.ifont {
			color: $lightblue;
		}
		.ifont_left {
			float: left;
			margin-left: 10px;
		}
		.ifont_right {
			float: right;
			margin-right: 10px;
		}
	}
</style>