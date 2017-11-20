<template>
	<div id="additionlMargin">
		<div class="bg"></div>
		<div class="additionlMargin">
			<div class="title">
				<span>交易账号查询</span>
				<i class="ifont" v-on:click="close">&#xe624;</i>
			</div>
			<div class="center">
				<p>交易账号：<span>{{tranAccount}}</span></p>
				<p>交易密码：<span>{{tranPassword}}</span>（请妥善保管您的密码）</p>
				<p>交易细则：<span v-on:click="toTradersRules">操盘细则</span></p>
				<button class="btn yellow" v-on:click="toTradeLogin">立即操盘</button>
				<button class="btn green" v-on:click="back">取消</button>
			</div>
		</div>
	</div>
</template>

<script>
	import pro from "../../../assets/js/common.js"
	export default {
		name : "openDetail_viewAccount",
		data(){
			return{
				item : '',
				id:'',
				tranAccount:'',
				tranPassword:''
			}
		},
		methods:{
			toTradersRules:function(){
				this.$router.push({path:"/tradersRules"});
			},
			close : function(){
				this.$router.push({path:'/account_openDetail'});
			},
			back:function(){
				this.$router.push({path:'/account_openDetail'});
			},
			//去操盘
			toTradeLogin:function(){
				this.$router.push({path:'/trade'});
			},
			//获取交易账户
			getTrade:function(a){
				var headers = {
					token : JSON.parse(localStorage.user).token,
					secret : JSON.parse(localStorage.user).secret
				}
				var data = {
					id:a
				}
				pro.fetch('post','/ user/ftrade/details',data,headers).then((res)=>{
					if(res.success == true){
						if(res.code == 1){
							this.tranAccount = res.data.details.tranAccount;
							this.tranPassword = res.data.details.tranPassword
						}
					}
				}).catch((err)=>{
					layer.msg('网络不给力，请稍后再试',{time:1000})
				})
			}
		},
		mounted:function(){
			this.id = this.$route.query.id;
			this.getTrade(this.id = this.$route.query.id);
		},
		actived:function(){
			this.id = this.$route.query.id;
		}
	}
</script>

<style lang="scss" scoped type="text/css">
@import "../../../assets/css/common.scss";
	.additionlMargin {
		position: fixed;
		/*top: 200px;*/
		left: 50%;
		top: 50%;
		margin: -200px 0 0 -120px;
		z-index: 100;
		width: 400px;
		height: 240px;
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
				color: $white;
			}
		}
		.btn {
			margin-top: 20px;
			width: 90px;
			height: 30px;
			margin-left: 20%;
		}
	}
</style>