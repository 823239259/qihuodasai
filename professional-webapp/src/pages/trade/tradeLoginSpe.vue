<template>
	<div id="trade_login" v-show="show">
		<div class="bg"></div>
		<div class="trade_login">
			<div class="title">
				<h3>交易账户快速安全登录</h3>
				<i class="ifont" @click="closeEvent">&#xe624;</i>
			</div>
			<div class="cont">
				<p class="tips">请点击账号授权登录</p>
				<div class="account">
					<ul>
						<template v-for="(v, index) in accountList">
							<li @click="loginEvent(v.account, v.password, v.fid)">
								<p>账户：{{v.account}}</p>
								<p>密码：******</p>
								<p>初始资金：{{v.initcapital}}元</p>
							</li>
						</template>
					</ul>
				</div>
				<div class="btn_box">
					<span @click="goTradeLogin">账号密码登录</span>
					<span @click="goOpenAccount">申请开户</span>
				</div>
			</div>
		</div>
	</div>
</template>

<script>
	import pro from '../../assets/js/common.js'
	import { mapMutations,mapActions } from 'vuex'
	export default{
		name: 'trade_login',
		data(){
			return{
				show: false,
				accountList: [],
				user: '',
				pwd: '',
			}
		},
		computed: {
			tradeConfig(){
				return this.$store.state.market.tradeConfig;
			},
			loginStatus(){
				return this.$store.state.account.loginStatus;
			},
		},
		watch: {
			loginStatus: function(n, o){
				if(n == true){
					var userData = {'username': this.user, 'password': Base64.encode(this.pwd), 'fid': fid};  
					localStorage.tradeUser = JSON.stringify(userData);
				}
			}
		},
		methods: {
			...mapActions([
				'handleTradeMessage',
				'initTrade'
			]),
			closeEvent: function(){
				this.show = false;
			},
			goTradeLogin: function(){
				this.show = false;
				this.$parent.$refs.tradeLogin.show = true;
			},
			goOpenAccount: function(){
				this.$router.push({path: '/openAccount'});
				$("#nav li").eq(3).addClass("current").siblings().removeClass("current");
			},
			loginEvent: function(user, pwd, fid){
				if(this.tradeConfig.url_real == '' || this.tradeConfig.url_real == undefined) return;
				this.user = user;
				this.pwd = pwd;
				this.$store.state.market.tradeConfig.username = user;
				this.$store.state.market.tradeConfig.password = Base64.encode(pwd);
				this.initTrade();
				setTimeout(function(){
					this.show = false;
				}.bind(this));
			},
			getTradeAccount: function(){
				if(this.userInfo){
					var headers = {
						token: this.userInfo.token,
						secret: this.userInfo.secret,
						version: ''
					};
					pro.fetch('post', '/user/getTradeAccount', '', headers).then(function(res){
						if(res.success == true && res.code == 1){
							this.accountList = res.data;
							if(res.data.length <= 0){
								this.show = false;
								this.$parent.$refs.tradeLogin.show = true;
							}
						}
					}.bind(this)).catch(function(err){
						var data = err.data;
						if(data) layer.msg(data.message, {time: 1000});
					});
				}
			}
		},
		mounted: function(){
			//获取交易账户
			this.getTradeAccount();
		},
		activated: function(){
			//获取平台账户登录信息
			this.userInfo = localStorage.user ? JSON.parse(localStorage.user) : '';
			//获取交易账户
			this.getTradeAccount();
		},
	}
</script>

<style lang="scss" scoped>
	@import "../../assets/css/common.scss";
	.trade_login{
		width: 400px;
		height: 260px;
		overflow: hidden;
		background: $blue;
		border-radius: 10px;
		position: absolute;
		top: 50%;
		left: 50%;
		z-index: 100;
		margin: -130px 0 0 -200px;
	}
	.title{
		height: 40px;
		line-height: 40px;
		text-align: center;
		background: $bottom_color;
		position: relative;
		h3{
			font-size: $fs16;
			color: $white;
		}
		i{
			position: absolute;
			top: 2px;
			right: 12px;
			color: #7a8199;
			cursor: pointer;
		}
	}
	.cont{
		.tips{
			height: 30px;
			line-height: 30px;
			background: $deepblue;
			font-size: $fs12;
			text-align: center;
		}
		.account{
			height: 130px;
			overflow-y: auto;
			padding: 20px 0 0 0;
			border-top: 1px solid $bottom_color;
			border-bottom: 1px solid $bottom_color;
			li{
				float: left;
				width: 170px;
				height: 90px;
				overflow: hidden;
				border: 1px solid $bottom_color;
				border-radius: 5px;
				cursor: pointer;
				margin: 0 10px 20px 10px;
				padding: 6px 0 0 10px; 
				&:hover{
					border-color: $yellow;
					color: $yellow;
				}
				p{
					height: 24px;
					line-height: 24px;
				}
			}
		}
		.btn_box{
			height: 60px;
			line-height: 60px;
			span{
				float: left;
				display: inline-block;
				width: 50%;
				text-align: center;
				cursor: pointer;
				&:hover{
					color: $yellow;
				}
			}
		}
	}
</style>