<template>
	<div id="account_resetLoginPassword">
		<div class="account_resetLoginPassword_top">
			<p>您正在为账户<span>{{phone}}</span>重置手机密码</p>
		</div>
		<div class="account_resetLoginPassword_center">
			<p>旧登录密码：<input type="text" v-model="oldLoginPassword"/></p>
			<p>新登录密码：<input type="text" v-model="newLoginPassword" /></p>
			<p>确认新密码：<input type="text" v-model="sureLoginPassword"/></p>
			<button class="btn yellow" v-on:click="toResetLoginPassword">确认</button>
		</div>
		<div class="account_resetLoginPassword_btm">
			<p>设置资金密码遇到问题</p>
			<ul>
				<li>重置新密码规则？</li>
				<li>新密码与旧密码不能相同，如果有疑问请拨打客服热线</li>
			</ul>
			<p>投资有风险，入市需谨慎</p>
		</div>
	</div>
</template>

<script>
	import pro from "../../../assets/js/common.js"
	export default {
		name : "safe_resetLoginPassword",
		data(){
			return{
				phone:'',
				oldLoginPassword:'',
				newLoginPassword:'',
				sureLoginPassword:''
			}
		},
		methods:{
			toResetLoginPassword : function(){
				var pwdReg = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{6,18}$/;
				if(this.oldLoginPassword == ''){
					layer.msg('请输入旧登录密码',{time:1000})
				}else if(pwdReg.test(this.oldLoginPassword)==false){
					layer.msg('请输入正确的旧登录密码',{time:1000})
				}else if(this.newLoginPassword == ''){
					layer.msg('请输入新登录密码',{time:1000})
				}else if(pwdReg.test(this.newLoginPassword ==false)){
					layer.msg('请输入正确的新登录密码',{time:1000})
				}else if(this.sureLoginPassword == ''){
					layer.msg('请输入确认新密码',{time:1000})
				}else if(pwdReg.test(this.sureLoginPassword)==false){
					layer.msg('请输入正确新登录密码',{time:1000})
				}else if(this.newLoginPassword!=this.sureLoginPassword){
					layer.msg('新密码和确认密码不一致 ',{time:1000})
				}else if(this.oldLoginPassword == this.newLoginPassword){
					layer.msg('新密码和旧密码一致 ',{time:1000})
				}else {
					
				}
				
			}
		},
		beforeCreate(){
			this.phone =JSON.parse(localStorage.user).username
		}
	}
</script>


<style lang="scss" scoped type="text/css">
	@import "../../../assets/css/common.scss";
	#account_resetLoginPassword {
		width: 100%;
		.account_resetLoginPassword_top{
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
		.account_resetLoginPassword_center {
			width: 100%;
			height: 240px;
			background-color: $blue;
			text-align : center;
			p {
				padding-top : 20px;
			}
			input {
				color:$white;
				width: 160px;
				height: 30px;
				border: 1px solid $bottom_color;
				border-radius: 5px;
				&:hover{
					border: 1px solid $yellow;
				}
			}
			.btn {
				width: 160px;
				height: 30px;
				margin-top: 10px;
				margin-left: 80px;
			}
		}
		.account_resetLoginPassword_btm {
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

</style>
