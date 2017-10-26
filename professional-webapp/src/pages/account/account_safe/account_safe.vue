<template>
	<div id="account_safe">
				<div class="account_safe_top">
					<div class="safe_left">
						<img src="../../../assets/images/icon_accountsafe.png" alt="safelogo" />
					</div>
					<div class="safe_right">
						<p>{{username}}&nbsp;<span>{{phone}}</span></p>
						<p>平台实时保护您的账户和资金安全</p>
					</div>
				</div>
				<div class="acount_safe_btm">
					<p>您设置了<span>3</span>个保护，还有<span>2</span>保护项可设置</p>
					<table>
						<tbody>
							<tr>
								<td><img src="../../../assets/images/icon_acc1.png" alt="" /></td>
								<td>实名认证</td>
								<td>未认证</td>
								<td>认证信息：*小四，51116*************2222</td>
								<td v-on:click="toCertification">去认证</td>
							</tr>
							<tr>
								<td><img src="../../../assets/images/icon_password1.png" alt="" /></td>
								<td>提现密码</td>
								<td>未设置	</td>
								<td>认证信息：*小四，51116*************2222</td>
								<td v-on:click="toaWithdrawalPassword">设置</td>
							</tr>
							<tr>
								<td><img src="../../../assets/images/icon_bindcard.png" alt="" /></td>
								<td>绑定银行卡</td>
								<td>未绑定</td>
								<td>认证信息：*小四，51116*************2222</td>
								<td v-on:click="toBindBankCard">绑定</td>
							</tr>
							<tr>
								<td><img src="../../../assets/images/icon_loginpassword.png" alt="" /></td>
								<td>登录密码</td>
								<td>已设置</td>
								<td>认证信息：*小四，51116*************2222</td>
								<td v-on:click="toResetLoginPassword">修改</td>
							</tr>
							<tr>
								<td><img src="../../../assets/images/icon_bindtel.png" alt="" /></td>
								<td>绑定手机</td>
								<td>已绑定</td>
								<td>认证信息：*小四，51116*************2222</td>
								<td v-on:click="toResetCellPassword">修改</td>
							</tr>
						</tbody>
					</table>
					<p class="p_center">投资有风险，入市需谨慎</p>
				</div>
			</div>
</template>

<script>
	import pro from "../../../assets/js/common.js"
	export default {
		name:"account_safe",
		data(){
			return{
				username : '',
				phone : ''
			}
		},
		methods:{
			toCertification : function(){
			},
			toaWithdrawalPassword:function(){
			},
			toBindBankCard:function(){
			},
			toResetLoginPassword:function(){
			},
			toResetCellPassword:function(){
			}
		},
		mounted:function(){
			var headers = {
				token : JSON.parse(localStorage.user).token,
				secret : JSON.parse(localStorage.user).secret
			}
			//判断安全信息
			pro.fetch("post","/user/security",{},headers).then((res)=>{
				if(res.success == true){
					if(res.code == 1){
						this.username = res.data.realName;
						this.phone = res.data.mobile;
						if(res.data.realName == ''){
							
						}else if(res.data.isWithdrawPwd == true){
							
						}else if(res.data.isBoundBankCard == true){
							
						}
					}
				}
			}).catch((err)=>{
				
			})
		}
	}
</script>

<style lang="scss" scoped type="text/css">
@import "../../../assets/css/common.scss";
#account_safe {
			display: none;
			width: 100%;
			height: 540px;
			background-color: #242633;
			float: left;
			.account_safe_top {
				height: 150px;
				width:100%;
				.safe_left {
					float: left;
					width: 150px;
					height:100%;
					background-color: $blue;
					img {
						padding-left: 50px;
						padding-top: 30px;
					}
				}
				.safe_right {
					float: left;
					width: 850px;
					height: 100%;
					background-color: $blue;
					p {
						width: 100%;
						font-size: $fs16;
						color: $white;
						padding-top: 60px;
						&:nth-child(2){
							color: $lightblue;
							padding-top: 5px;
							font-size: $fs14;
						}
					}
				}
				
			}
			.acount_safe_btm {
				width: 100%;
				p {
					font-size: $fs14;
					text-indent: 5px;
					width: 100%;
					height: 40px;
					background-color: $bottom_color;
					line-height: 40px;
					span {
						color: $white;
						margin: 0 3px;
					}
				}
				table {
					margin-top: 5px;
				}
				td {
					height: 60px;
					text-indent: 5px;
					background-color: $blue;
					border-bottom: 1px solid $bottom_color;
				}
				
			}
		}
</style>