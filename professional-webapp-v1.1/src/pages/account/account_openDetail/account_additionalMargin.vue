<template>
	<div id="openDetail_trade">
		<div class="bg"></div>
		<div class="openDetail_trade">
			<div class="title">
				<span>追加保证金</span>
				<i class="ifont" @click="close">&#xe624;</i>
			</div>
			<div class="center">
				<p>账户资金：<span>{{account_money}}</span>元</p>
				<p>追加保证金：<input type="text"  v-model="additionlMoney"/>元</p>
				<p>换算美元：&nbsp;<span>{{(additionlMoney/account_USdollar).toFixed(2)}}</span>美元</p>
				<ul>
					<li>注意：</li>
					<li>1.系统将在下个交易日前为您的期货账户追加保证金；</li>
					<li>2.添加成功将短信通知您，最低追加金额为500元</li>
					<li>3.保证金采用固定汇率：<span>{{account_USdollar}}</span>元=1美元</li>
				</ul>
				<button class="btn yellow" @click="sure_additionlMoney">确定</button>
				<button class="btn green" v-on:click="back">取消	</button>
			</div>
		</div>
	</div>
</template>
<script>
	import pro from "../../../assets/js/common.js"
	export default {
		name : "openDetail_additionalMargin",
		data(){
			return{
				account_money : '',
				account_USdollar : '',
				additionlMoney : '',
				changemoney : '',
				id:'',
				type: '',
			}
		},
		methods:{
			close: function(){
				this.$router.push({path:'/account_openDetail'})
			},
			back: function(){
				this.$router.push({path:'/account_openDetail'})
			},
			sure_additionlMoney : function(){
				var data = {
					id :this.id,
					addBond : this.additionlMoney
				}
				var headers = {
					token : JSON.parse(localStorage.user).token,
					secret : JSON.parse(localStorage.user).secret
				}
				if(this.additionlMoney == ''){
					layer.msg('请输入补充保证金的金额',{time:2000})
				}else if(this.additionlMoney < 500){
					layer.msg('补充保证金的金额需要大于500元',{time:2000})
				}else if(this.account_money < this.additionlMoney){
					layer.msg("余额不足，请先去充值",{time:2000})
				}else{
					pro.fetch("post",'/user/ftrade/addbond',data,headers).then((res)=>{
						if(res.success == true){
							if(res.code == 1){
								layer.msg("追加成功",{time:2000});
								if(this.type == 'trade'){
									this.$router.push({path:'trade'});
									this.$store.state.account.currentNav = 1;
									localStorage.currentNav = 1;
									return;
								}
								this.$router.push({path:'account_openDetail'});
								this.$store.state.account.currentNav = 6;
								localStorage.currentNav = 6;
							}
						}
					}).catch((err)=>{
						if(err.data.success == false){
							switch (err.data.code){
								case '-1':
									layer.msg("认证失败",{time:2000});
									break;
								case '2':
									layer.msg("余额不足",{time:2000});
									break;
								case '3':
									layer.msg("未找到该方案",{time:2000});
									break;
								case '4':
									layer.msg("该方案已完结",{time:2000});
									break;
								case '5':
									layer.msg("追加金额低于默认最小金额",{time:2000});
									break;
								case '6':
									layer.msg("方案编号不能为空",{time:2000});
									break;
								case '7':
									layer.msg("追加金额不能为空或者0",{time:2000});
									break;
								case '8':
									layer.msg("追加保证金失败",{time:2000});
									break;
								default:
									break;
							}
						}else{
							layer.msg('网络不给力，请稍后再试',{time:2000})
						}
					})
				}
			}
		},
		mounted:function(){
			this.id = this.$route.query.id;
			this.type = this.$route.query.type;
			var data = {
				businessType : 1,
			}
			var headers = {
				token : JSON.parse(localStorage.user).token,
				secret :JSON.parse(localStorage.user).secret
			}
			pro.fetch("post",'/user/getbalancerate',data,headers).then((res)=>{
				if(res.success == true){
					if(res.code == 1){
						this.account_money = res.data.balance,
						this.account_USdollar = res.data.rate
					}
				}
			}).catch((err)=>{
				layer.msg('网络不给力，请稍后再试',{time:1000})
			})
		},
		activated:function(){
			this.id = this.$route.query.id;
		}
	}
</script>

<style lang="scss" scoped type="text/css">
@import "../../../assets/css/common.scss";
	.openDetail_trade {
		position: fixed;
		left: 50%;
		top: 50%;
		z-index: 100;
		width: 400px;
		height: 330px;
		margin: -200px 0 0 -165px;
		background-color: $blue;
		overflow:visible;
		border-radius: 10px;
	}
	.title {
		height: 40px;
		background-color: $bottom_color;
		line-height: 40px;
		color: $white;
		text-align: center;
		border-radius: 10px;
	}
	.ifont {
		font-size: $fs16;
		color: #7a8199;
		float: right;
		margin-right: 5px;
	}
	.center {
		p {
			margin-top: 20px;
			font-size: $fs14;
			margin-left: 30px;
			span {
				color: $yellow;
			}
		}
		.btn {
			margin-top: 20px;
			width: 90px;
			height: 30px;
			margin-left: 20%;
		}
		li {
			margin-left:30px;
			font-size: $fs12;
			margin-top:10px;
			&:nth-child(1){
				color: $yellow;
				margin-top: 20px;
			}
		}
		input {
			width: 120px;
			height: 30px;
			border: 1px solid $lightblue;
			border-radius: 5px;
			margin: 0 5px;
			color: $white;
		}
	}
</style>