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
					<p>您设置了<span>{{project}}</span>个保护，还有<span>{{4-project}}</span>保护项可设置</p>
					<table>
						<tbody>
							<!--<tr>
								<td><i class="icon icon_realname" :class="{current: realName != null}"></i></td>
								<td>实名认证</td>
								<td v-if="realName == null">未认证</td>
								<td v-else="realName != null" class="yellow_status">已认证</td>
								<td v-if="realName == null">您还没有实名认证完成更高级认证后将提升您的充值与提现权限。</td>
								<td v-else="realName!=null">认证信息：{{username}}</td>
								<td v-on:click="toCertification" v-if="realName == null" class="chang_color">去认证</td>
								<td v-else="realName != null"></td>
							</tr>-->
							<tr>
								<td><i class="icon icon_pwd" :class="{current: isWithdrawPwd == true}"></i></td>
								<td>提现密码</td>
								<td v-if="isWithdrawPwd==false">未设置</td>
								<td v-else="isWithdrawPwd==true" class="yellow_status">已设置</td>
								<td v-if="isWithdrawPwd==false">账户资金变动时，需先验证提现密码</td>
								<td v-else="isWithdrawPwd==true">您已设置提现密码，请保管好自己的密码</td>
								<td v-on:click="toaWithdrawalPassword" v-if="isWithdrawPwd==false" class="chang_color">去设置</td>
								<td v-on:click="toaWithdrawalPassword" v-else="isWithdrawPwd==true" class="yellow_status">去修改</td>
							</tr>
							<tr>
								<td><i class="icon icon_bank" :class="{current: isBoundBankCard == true}"></i></td>
								<td>绑定银行卡</td>
								<td v-if="isBoundBankCard==false">未绑定</td>
								<td v-else="isBoundBankCard==true" class="yellow_status">已绑定</td>
								<td v-if="isBoundBankCard==false">您还没有绑定银行卡，完成绑定后可提现到银行卡中</td>
								<td v-else="isBoundBankCard==true" >您已绑定银行卡，可提现到银行卡中</td>
								<td v-on:click="toAddBankCard" v-if="isBoundBankCard==false" class="chang_color">去绑定</td>
								<td v-on:click="toBindBankCard" v-else="isBoundBankCard==true" class="yellow_status">去修改</td>
							</tr>
							<tr>
								<td><i class="icon icon_loginpwd"></i></td>
								<td>登录密码</td>
								<td class="yellow_status">已设置</td>
								<td>登录网站时使用</td>
								<td v-on:click="toResetLoginPassword" class="yellow_status">去修改</td>
							</tr>
							<tr>
								<td><i class="icon icon_phone"></i></td>
								<td>绑定手机</td>
								<td class="yellow_status">已绑定</td>
								<td>您已绑定手机{{phone}}</td>
								<td v-on:click="toResetCellPassword" class="yellow_status">去修改</td>
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
				userInfo: '',
				username : '',
				phone : '',
				realName :'',
				isBoundBankCard:'',
				isWithdrawPwd:'',
				project: 2
			}
		},
		methods:{
			//实名认证
			toCertification : function(){
				this.$router.push({path:'/safe_certification'})
			},
			//设置提现密码
			toaWithdrawalPassword:function(){
				this.$router.push({path:'/safe_withdrawalPassword'})
			},
			//绑定银行卡
			toBindBankCard:function(){
				this.$router.push({path:'/safe_bindBankCard'})
			},
			//修改登录密码
			toResetLoginPassword:function(){
				this.$router.push({path:'/safe_resetLoginPassword'})
			},
			//修改绑定手机号
			toResetCellPassword:function(){
				this.$router.push({path:'/safe_resetCellPhone'})
			},
			//添加银行卡
			toAddBankCard :function(){
				this.$router.push({path:'/safe_addBankCard'})
			},
			getUserSafeMsg: function(){
				var headers = {
					token : this.userInfo.token,
					secret : this.userInfo.secret
				};
				//判断安全信息
				pro.fetch("post","/user/security",{},headers).then(function(res){
					if(res.success == true && res.code == '1'){
						var data = res.data;
						if(data.realName != null && data.isWithdrawPwd == true && data.isBoundBankCard == true){
							this.project = 5;
						}else if(data.realName != null && data.isWithdrawPwd == true){
							this.project = 4;
						}else if(data.isWithdrawPwd == true && data.isBoundBankCard == true){
							this.project = 4;
						}else if(data.realName != null && data.isBoundBankCard == true){
							this.project = 4;
						}else if(data.realName != null || data.isWithdrawPwd == true || data.isBoundBankCard == true){
							this.project = 3;
						}
						
					}
				}.bind(this)).catch(function(err){
					var data = err.data;
					if(data == undefined || data == "" || data == null){
						layer.msg('网络不给力，请稍后再试', {time: 1000});
					}else if(data.code == "-1"){
						layer.msg('您的用户名或密码错误，请联系客服', {time: 1000});
					}else if(data.code == "3"){
						layer.msg('用户信息不存在，请重新登录', {time: 1000});
					}
				}.bind(this));
			},
		},
		mounted:function(){
			//初始化高度
			var h = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;
			var _h = h - 80 - 47;
			var contH = $("#account_safe").height();
			if(contH > _h){
				$("#account_safe").height(_h);
			}
			$(window).resize(function(){
				var h = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;
				var _h = h - 80 - 47;
				if(contH > _h){
					$("#account_safe").height(_h);
				}
			});
			//获取用户平台登录信息
			this.userInfo = localStorage.user ? JSON.parse(localStorage.user) : '';
			//获取用户账户信息
			this.getUserSafeMsg();
		},
		activated:function(){
			//获取用户账户信息
			this.getUserSafeMsg();
		},
		beforeCreate(){
			var headers = {
				token : JSON.parse(localStorage.user).token,
				secret : JSON.parse(localStorage.user).secret
			}
			//判断安全信息
			pro.fetch("post","/user/security",{},headers).then((res)=>{
				if(res.success == true){
					if(res.code == 1){
						var phoneNumber= res.data.mobile;
						this.phone = phoneNumber.substr(0, 3) + '****' + phoneNumber.substr(7)
						this.realName = res.data.realName;
						this.isWithdrawPwd = res.data.isWithdrawPwd;
						this.isBoundBankCard = res.data.isBoundBankCard;
						if(res.data.realName == null){
							this.username = "你好";
						}else{
							this.username =  '*'+res.data.realName.substr(1,5);
						}
					}
				}
			}).catch((err)=>{
				layer.msg('网络不给力，请稍后再试',{time:1000});
			})
		}
	}
</script>

<style lang="scss" scoped type="text/css">
@import "../../../assets/css/common.scss";
#account_safe {
			width: 100%;
			height: 540px;
			background-color: #242633;
			float: left;
			overflow-y: auto;
			.account_safe_top {
				height: 150px;
				width:100%;
				.safe_left {
					float: left;
					width: 15%;
					height:100%;
					background-color: $blue;
					img {
						padding-left: 50px;
						padding-top: 30px;
					}
				}
				.safe_right {
					float: left;
					width: 85%;
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
				.icon{
					width: 20px;
					height: 20px;
					background-size: 100% 100%;
				}
				.icon_realname{
					background: url(../../../assets/images/acc_01.png) no-repeat center center;
				}
				.icon_realname.current{
					background: url(../../../assets/images/acc_02.png) no-repeat center center;
				}
				.icon_pwd{
					background: url(../../../assets/images/acc_03.png) no-repeat center center;
				}
				.icon_pwd.current{
					background: url(../../../assets/images/acc_04.png) no-repeat center center;
				}
				.icon_bank{
					background: url(../../../assets/images/acc_05.png) no-repeat center center;
				}
				.icon_bank.current{
					background: url(../../../assets/images/acc_06.png) no-repeat center center;
				}
				.icon_loginpwd{
					background: url(../../../assets/images/acc_08.png) no-repeat center center;
				}
				.icon_phone{
					background: url(../../../assets/images/acc_10.png) no-repeat center center;
				}
			}
			.p_center{
				height: 40px;
				line-height: 40px;
				text-align: center;
			}
			.yellow_status {
				color: $yellow;
				&:hover{
					color: $lightblue;
				}
			}
			.chang_color{
				&:hover{
					color: $yellow;
				}
			}
		}
</style>