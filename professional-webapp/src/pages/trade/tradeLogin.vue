<template>
	<div id="trade_login" v-show="show">
		<div class="bg"></div>
		<div class="trade_login">
			<div class="title">
				<h3>账户密码登录</h3>
				<i class="ifont" @click="closeEvent">&#xe624;</i>
			</div>
			<div class="cont">
				<input type="text" class="ipt" value="" placeholder="请输入账户名" v-model="username" />
				<input type="password" class="ipt" value="" placeholder="请输入账户密码" v-model="pwd" @keyup.enter="loginEvent" />
				<p @click="forgetPwd">忘记密码？</p>
				<button class="btn yellow" @click="loginEvent">立即登录</button>
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
				username: '',
				pwd: '',
				url_real: '',
			}
		},
		computed: {
			tradeConfig(){
				return this.$store.state.market.tradeConfig;
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
			loginEvent: function(){
				if(this.username == ''){
					layer.msg('请输入您的账号名', {time: 1000});
				}else if(this.pwd == ''){
					layer.msg('请输入您的密码', {time: 1000});
				}else{
					if(this.tradeConfig.url_real == '' || this.tradeConfig.url_real == undefined) return;		
					this.$store.state.market.tradeConfig.username = this.username;
					this.$store.state.market.tradeConfig.password = Base64.encode(this.pwd);
					var userData = {'username': this.username, 'password': Base64.encode(this.pwd)};  
					localStorage.tradeUser = JSON.stringify(userData);
					this.initTrade();
					setTimeout(function(){
						this.show = false;
					}.bind(this));
				}
			},
			forgetPwd: function(){
				layer.confirm('如果您忘记密码请及时与我们取得联系；联系电话：400-852-8008',{
					btn: ['关闭']
				}, function(index){
					layer.close(index);
				});
			}
		}
	}
</script>

<style lang="scss" scoped>
	@import "../../assets/css/common.scss";
	.trade_login{
		width: 400px;
		height: 320px;
		overflow: hidden;
		background: $blue;
		border-radius: 10px;
		position: absolute;
		top: 50%;
		left: 50%;
		z-index: 100;
		margin: -160px 0 0 -235px;
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
		padding: 40px;
		.ipt{
			width: 320px;
			height: 40px;
			line-height: 40px;
			color: $white;
			border: 1px solid #474c66;
			border-radius: 5px;
			text-align: center;
			margin-bottom: 20px;
			&:focus{
				border-color: $yellow;
			}
		}
		.btn{
			width: 320px;
			height: 40px;
			line-height: 40px;
		}
		p{
			text-align: right;
			margin-bottom: 20px;
			cursor: pointer;
			&:hover{
				color: $yellow;
			}
		}
	}
</style>