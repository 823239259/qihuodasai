<template>
	<div id="withDraw_sure">
		<div class="withDraw_sure_top">
			<ul>
				<li>银行卡信息：<span>{{username}}</span><i>{{bankName}}（{{bankCrad}}）</i></li>
				<li>提现金额：<span class="color_yellow">{{withMoney}}</span>元</li>
				<li>提现手续费：<span>{{withFee}}</span>元<i>（按揭提现金额的0.5%收取）</i></li>
				<li>实际到账金额：<span class="color_yellow">{{withMoney-withFee}}</span>元<i>（提现金额-提现手续费）</i></li>
			</ul>
		</div>
		<div class="withDraw_sure_center">
			<ul>
				<li>提现密码：<input type="password" v-model="withdrawPwd"/><span v-on:click="toResetPassword">忘记密码</span></li>
				<li><button class="btn yellow" v-on:click="withDraw_money">确认提现</button><span v-on:click="back">返回修改</span></li>
			</ul>
		</div>
		<div class="withDraw_sure_btm">
			<p>提现相关</p>
			<ul>
				<li>忘记密码怎么办？</li>
				<li>答：可点击【忘记密码】重新设置提款密码</li>
			</ul>
			<p>投资有风险，入市需谨慎</p>
		</div>
	</div>
</template>

<script>
	import pro from "../../../assets/js/common.js"
	export default {
		name:"sure_withDraw",
		data(){
			return{
				withdrawPwd:'',
				withFee:'',
				bankid:'',
				withMoney:'',
				bankName:'',
				username:'',
				bankCrad:'',
				bankCardShow:''
			}
		},
		methods:{
			//忘记密码
			toResetPassword:function(){
				this.$router.push({path:'/safe_withdrawalPassword'})
			},
			back:function(){
				this.$router.push({path:'/withDraw_bankcard'})
			},
			//提现
			withDraw_money:function(){
				var pwdReg = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{6,18}$/;
				var token = JSON.parse(localStorage.user).token;
				var secret = JSON.parse(localStorage.user).secret;
				var headers = {
					token : token,
					secret :secret
				}
				var data = {
					bank:this.bankName,
				    card:this.bankCardShow,
				    money:this.withMoney,
				    withdrawPwd:this.withdrawPwd
				}
				console.log(data)
				if(this.withdrawPwd==''){
					layer.msg("请输入提现密码");
				}else if(pwdReg.test(this.withdrawPwd)==false){
					this.withdrawPwd = '';
					layer.msg("密码格式输入有误，请重试",{time:2000});
				}else {
					pro.fetch("post","/user/withdraw/handle",data,headers).then((res)=>{
						if(res.success == true){
							if(res.code == 1){
								layer.msg("提现申请已提交，等待要银行处理。若24小时未到账请拨打：400-852-8008",{time:2000});
								this.$router.push({pat:"/account_survey"});
							}
						}
					}).catch((err)=>{
						if(err.data.success == false){
							switch (err.data.code){
								case '-1':
									layer.msg("认证失败",{time:2000});
									break;
								case '0':
									layer.msg("token失效",{time:2000});
									break;
								case '2':
									layer.msg("此账户暂未绑定银行卡",{time:2000});
									break;
								case '3':
									layer.msg("用户信息不存在",{time:2000});
									break;
								case '4':
									layer.msg("银行卡卡号不存在",{time:2000});
									break;
								case '5':
									layer.msg("存在欠费无法提现",{time:2000});
									break;
								case '6':
									layer.msg("系统升级期间无法提现",{time:2000});
									break;
								case '7':
									layer.msg("余额不足不能提现",{time:2000});
									break;
								case '8':
									layer.msg("当天取款次数不能超过5次",{time:2000});
									break;
								case '9':
									layer.msg("每次提现金额不能小于10元",{time:2000});
									break;
								case '10':
									layer.msg("提现密码错误",{time:2000});
									break;
								case '11':
									layer.msg("暂不支持此银行提现",{time:2000});
									break;
								case '12':
									layer.msg("单笔提现金额不能超过5万元",{time:2000});
									break;
								case '15':
									layer.msg("提现渠道设置参数错误",{time:2000});
									break;
								default:
									break;
							}
						}else{
							layer.msg("网络不给力，请稍后再试",{time:2000})
						}
					})
				}
			},
			//查询银行卡信息
			queryBank:function(){
				var data = {
					bankId:this.bankid
				}
				var token = JSON.parse(localStorage.user).token;
				var secret = JSON.parse(localStorage.user).secret;
				var headers = {
					token : token,
					secret :secret
				}
				pro.fetch("post","/user/withdraw/queryBank",data,headers).then((res)=>{
					if(res.success == true){
						if(res.code == 1){
							var phoneNumber = res.data.tname;
							this.username = '*' + phoneNumber.substr(1,5);
							this.bankName = res.data.abbreviation;
							var card = res.data.card;
							this.bankCrad = "**** **** **** "+card.substr(15,20);
							this.bankCardShow = res.data.card;
						}
					}
				}).catch((err)=>{
					if(err.data.success == false){
						
					}
					layer.msg("网络不给力，请稍后再试",{time:2000})
				})
			}
		},
		mounted:function(){
			this.bankid=this.$route.query.bankid;
			this.withFee=this.$route.query.withFee;
			this.withMoney=this.$route.query.withMoney;
			console.log(this.bankid);
			console.log(this.withFee);
			console.log(this.withMoney)
			this.queryBank();
		},
		activated:function(){
			this.bankid=this.$route.query.bankid;
			this.withFee=this.$route.query.withFee;
			this.withMoney=this.$route.query.withMoney;
		}
		
	}
</script>

<style lang="scss" scoped type="text/css">
@import "../../../assets/css/common.scss";
	#withDraw_sure {
		width: 100%;
		li {
				margin-top:25px;
			}
		.withDraw_sure_top {
			background-color: $blue;
			padding-top: 20px;
			height: 210px;
			width: 100%;
			border-bottom: 1px solid $blue;
			text-align: center;
			i {
				font-size: $fs12;
				margin-left: 5px;
			}
			span {
				color: $white;
			}
			.color_yellow {
				color: $yellow;
				font-weight: 800;
			}
		}
		.withDraw_sure_center {
			background-color: $blue;
			height: 150px;
			width: 100%;
			text-align: center;
			input {
				width: 120px;
				height: 30px;
				border: 1px solid $bottom_color;
				border-radius: 5px;
				color: $white;
			}
			.btn {
				width: 120px;
				height: 30px;
				margin-left: 70px;
			}
			 span {
			 	font-size: $fs12;
			 	margin-left: 10px;
			 }
		}
		.withDraw_sure_btm {
			background-color: $blue;
			height: 180px;
			text-align: center;
			p {
				&:nth-child(1) {
					text-align: left;
					background-color: $bottom_color;
					height: 40px;
					color: $white;
					line-height: 40px;
				}
				&:nth-child(2) {
					color: $white;
					height: 40px;
					line-height: 40px;
					margin-top: 10px;
					font-size: $fs12;
				}
			}
		}
	}
</style>