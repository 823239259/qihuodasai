<template>
	<div id="withDraw_sure">
		<div class="withDraw_sure_top">
			<ul>
				<li>银行卡信息：<span>舒</span><i>招商银行（**** **** **** 0803）</i></li>
				<li>提现金额：<span class="color_yellow">0</span>元</li>
				<li>提现手续费：<span>{{fee}}</span>元<i>（按揭提现金额的0.5%收取）</i></li>
				<li>实际到账金额：<span class="color_yellow">9.9</span>元<i>（提现金额-提现手续费）</i></li>
			</ul>
		</div>
		<div class="withDraw_sure_center">
			<ul>
				<li>提现密码：<input type="text" v-model="withdrawPwd"/><span>忘记密码</span></li>
				<li><button class="btn yellow" v-on:click="withDraw_money">确认提现</button><span>返回修改</span></li>
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
				withMoney:''
				
			}
		},
		methods:{
			//提现
			withDraw_money:function(){
				var token = JSON.parse(localStorage.user).token;
				var secret = JSON.parse(localStorage.user).secret;
				var data = {
					bank:bank,
				    card:card,
				    money:money,
				    withdrawPwd:withdrawPwd
				}
				var headers = {
					token : token,
					secret :secret
				}
				pro.fetch("post","/user/withdraw/handle",data,headers).then((res)=>{
					
				}).catch((err)=>{
					
				})
			},
			//查询银行卡信息
			queryBank:function(){
				pro.fetch("post","/user/withdraw/queryBank",data,headers).then((res)=>{
					
				}).catch((err)=>{
					
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