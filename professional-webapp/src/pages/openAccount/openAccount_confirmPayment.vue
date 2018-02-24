<template>
	<div id="confirmPayment">
		<div class="bg"></div>
		<div class="confirmPayment">
			<p v-if="accountMoney-payMoney>0 || accountMoney-payMoney==0">确认支付<i class="ifont" v-on:click="close">&#xe624;</i></p>
			<p v-else="accountMoney-payMoney!>0">去充值<i class="ifont" v-on:click="close">&#xe624;</i></p>
			<p>账户余额：<span>{{accountMoney}}</span>元</p>
			<p>支付金额：<span>{{payMoney}}</span>元</p>
			<p v-if="accountMoney-payMoney>0">您的账户余额<i>{{accountMoney}}</i>元，本次支付完毕剩余<i>{{accountMoney-payMoney | fixNumTwo}}</i>元</p>
			<p v-else="accountMoney-payMoney!>0">您的账户余额<i>{{accountMoney}}</i>元，本次支付还差<i>{{surplus}}</i>元</p>
			<button class="btn yellow" id="btn_yellow" v-on:click="to_payMoney" v-if="accountMoney-payMoney>0 || accountMoney-payMoney==0">确认支付</button>
			<button class="btn yellow" v-on:click="to_Recharge" v-else="accountMoney!>0">去充值</button>
			<button class="btn green" v-on:click="cancel">取消</button>
		</div>
	</div>
</template>

<script>
	import pro from "../../assets/js/common.js"
	export default {
		name : "confirmPayment",
		data(){
			return{
				accountMoney:'',
				payMoney:'',
				refresh:true,
				surplus:''
			}
		},
		filters:{
			fixNumTwo: function(num){
				return num.toFixed(2);
			}
		},
		mounted:function(){
			if(this.$route.query.payMoney!=null){
				this.payMoney = this.$route.query.payMoney;
			}else {
				this.surplus = this.$route.query.payMoney1;
				this.payMoney = parseFloat(this.$route.query.payMoney1)+parseFloat(this.accountMoney);
			}
		},
		methods : {
			//充值
			to_Recharge:function(){
				if(this.accountMoney < this.payMoney){
					this.$router.push({path:'/recharge',query:{"accountMoney":this.accountMoney,"rechargeMoney":this.surplus}});
				}
			},
			//支付
			to_payMoney :function(e){
				$("#btn_yellow").attr("disabled","disabled")
				var data = {
					vid:'',
					"traderBond":this.payMoney,
					"tranLever":0,
					"businessType":8
				}
				var headers = {
					token:JSON.parse(localStorage.user).token,
					secret:JSON.parse(localStorage.user).secret
				}
				pro.fetch("post","/user/ftrade/handle",data,headers).then((res)=>{
					var data = res.data;
					if(res.success == true){
						if(res.code == 1){
							this.$router.push({path:'/openAccount_success'});
							$("#btn_yellow").removeAttr("disabled");
						}
					}
				}).catch((err)=>{
					if(err.data.success == false){
						switch (err.data.code){
							case "-1":
							layer.msg("认证失败",{time:2000});
							$("#btn_yellow").removeAttr("disabled");
								break;
							case "0":
							layer.msg("系统异常，请稍后重试",{time:2000});
							$("#btn_yellow").removeAttr("disabled");
								break;
							case "2":
							layer.msg("传的参数错误，没有获取到配置方案",{time:2000});
							$("#btn_yellow").removeAttr("disabled");
								break;
							case "3":
							layer.msg("用户余额不足",{time:2000});
							$("#btn_yellow").removeAttr("disabled");
								break;
							default:
								break;
						}
					}else{
						layer.msg("网络不给力，请稍后再试",{time:2000})
					}
				})
			},
			//关闭
			close:function(){
				this.$router.push({path:"/openAccount"})
			},
			//取消
			cancel:function(){
				this.$router.push({path:"/openAccount"})
			},
			getUserMsg:function(){
				var data = {
					businessType:4
				}
				var headers = {
					token:JSON.parse(localStorage.user).token,
					secret:JSON.parse(localStorage.user).secret
				}
				pro.fetch("post","/user/getbalancerate",data,headers).then((res)=>{
					var data =res.data;
					if(res.success == true){
						if(res.code == 1){
							this.accountMoney = data.balance;
							this.surplus =Math.abs(this.accountMoney-this.payMoney).toFixed(2);
						}
					}
				}).catch((err)=>{
					if(err.data.success == false){
						switch (err.data.code){
							case "3":
							layer.msg("用户信息不存在",{time:2000});
								break;
							case "-1":
							layer.msg("认证失败",{time:2000});
							default:
								break;
						}
					}else{
						layer.msg("网络不给力，请稍后再试",{time:2000})
					}
				})
			}
		},
		activated: function(){
			this.getUserMsg();
			if(this.$route.query.payMoney!=null){
				this.payMoney = this.$route.query.payMoney;
			}else {
				this.surplus = this.$route.query.payMoney1;
				this.payMoney = parseFloat(this.$route.query.payMoney1)+parseFloat(this.accountMoney);
			}
		},
	}
</script>

<style lang="scss" scoped type="text/css">
@import "../../assets/css/common.scss";
	.confirmPayment{
		position: fixed;
		top: 50%;
		left: 50%;
		z-index: 100;
		width: 400px;
		height: 240px;
		margin:-120px 0 0 -200px;
		background-color: $blue;
		text-align: center;
		border-radius: 10px;
	}
	p {
		&:nth-child(1){
			background-color: $bottom_color;
			border-radius: 10px;
		}
		&:nth-child(4){
			background-color: $deepblue;
			font-size: $fs12;
		}
		height: 40px;
		line-height: 40px;
	}
	.btn {
		width: 120px;
		height: 30px;
		margin-top: 20px;
		margin-left: 10%;
	}
	span {
		color: $yellow;
	}
	i {
		color: $white;
	}
	.ifont {
		float: right;
		margin-right: 10px;
		color: #7a8199;
	}
</style>



