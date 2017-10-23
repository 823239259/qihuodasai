<template>
	<div id="forgetPassword">
		<div class="forgetPassword">
			<p><i class="ifont ifont_left">&#xe625;</i>忘记密码<i class="ifont ifont_right">&#xe624;</i></p>
			<input type="text" id="phone" class="input_1"  placeholder="请输入手机号" v-model="phone"/>
			<input type="text"id="pwd" class="input_2 input_4"  placeholder="验证码" v-model="code"/>
			<i class="span_code" v-on:click="getcode">{{volid ? info : (time+'秒')}}</i>
			<button class="btn blue" v-on:click="toResetPassword" >下一步</button>
			<p class="color_light">还没有期货大赛账号？<span class="span_yellow">立即注册</span></p>
		</div>
		<tipsDialog :msg="msgTips" ref="dialog"></tipsDialog>
		<codeDialog ref="codeDialog" type="findpwd"></codeDialog>
		<resetPassword v-if="isshow_resetPassword" class="show_reset"></resetPassword>
	</div>
</template>

<script>
	import resetPassword from "./resetPassword.vue"
	import tipsDialog from "../../components/tipsDialog.vue"
	import codeDialog from "../../components/codeDialog.vue"
	import axios from "axios";
	import qs from "qs";
	export default {
		name : "forgetPassword",
		components : {tipsDialog,codeDialog,resetPassword},
		data(){
			return{
				msg: '',
				phone: '',
				code: '',
				time: 0,
				info: '获取验证码',
				phoneReg: /^(((13[0-9])|(14[5-7])|(15[0-9])|(17[0-9])|(18[0-9]))+\d{8})$/,
				num: 0,
				isshow_resetPassword : false
			}
		},
		computed : {
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
				return '1.1';
			},
			environment(){
				return this.$store.state.environment;
			}
		},
		methods : {
			getcode : function(e){
				if($(e.target).hasClass('current')) return false;
				if(this.phone == ''){
					this.$refs.dialog.isShow = true;
					this.msg = '请输入手机号';
				}else if(this.phoneReg.test(this.phone) == false){
					this.$refs.dialog.isShow = true;
					this.msg = '手机号格式错误';
				}else{
					if(this.num && this.num > 2){
						this.$refs.codeDialog.isshow = true;
						this.$refs.codeDialog.path = this.PATH + "/sendImageCode?code=" + Math.random()*1000 + "&mobile=" + this.phone;
						this.$refs.codeDialog.phone = this.phone;
					}else{
						//请求发送验证码
						var data = {
							mobile:this.phone,
							type : 2
						}
						axios({
							method : "post",
							url : this.PATH+'/sms',
							timeout : 5000,
							data : qs.stringify(data)
						}).then((res)=>{
							var data = res.data;
							if(data.success == true){
								if(data.code == 1){
									this.$refs.dialog.isShow = true;
									this.msg = '发送成功';
								}
							}else{
								this.$refs.dialog.isShow = true;
								this.msg = data.message;
							}
						}).catch((err)=>{
							var data = err.data;
							this.$refs.dialog.isShow = true;
							this.msg = data.message;
						})
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
			}
		},
		toResetPassword : function(){
			if(this.phone!=''&&this.code !=''){
				this.isshow_resetPassword= true;
				$(".forgetPassword").css('display','none')
			}else{
				this.$refs.dialog.isShow = true;
				this.msg = "请填写完整信息"
			}
		}
	}
}
</script>

<style lang="scss" scoped type="text/css">
	@import "../../assets/css/common.scss";
	#forgetPassword {
		position : absolute;
		left : 40%;
		top:30%;
		width: 400px;
		height: 300px;
		text-align : center;
		color :$lightblue ;
		background-color: $blue;
		z-index:100;
		p {
			line-height : 40px;
			color:$white;
			&:nth-child(1) {
				height: 40px;
				background-color: $bottom_color;
			}
		}
		input {
			width: 320px;
			height: 40px;
			color: $white;
			text-align: center;
			border: 1px solid $bottom_color;
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
		label {
			font-size: $fs16;
		}
		.btn {
			width: 320px;
			height: 40px;	
			margin-top: 20px;
			margin-left: 0px;
		}
		.span_code {
			position: relative;
			right: 40px;
			top: 10px;
			color: $white;
			font-size: $fs12;
			display: inline-block;
			width: 60px;
			height: 20px;
		}
		.input_4 {
			position: relative;
			left: 34px;
			top: 10px;
		}
		.span_ms {
			position: relative;
			top: -50px;
			left: 360px;
			color: $white;
		}
		.color_light {
			color: $lightblue;
			margin-top: 10px;
		}
		.span_yellow {
			color: $yellow;
		}
	}
</style>