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
				<p @>忘记密码？</p>
				<button class="btn yellow" @click="loginEvent">立即登录</button>
			</div>
		</div>
	</div>
</template>

<script>
	export default{
		name: 'trade_login',
		data(){
			return{
				show: false,
				username: '',
				pwd: ''
			}
		},
		computed: {
			tradeConfig(){
				return this.$store.state.market.tradeConfig;
			}
		},
		methods: {
			closeEvent: function(){
				this.show = false;
			},
			loginEvent: function(){
				if(this.username == ''){
					layer.msg('请输入您的账号名', {time: 1000});
				}else if(this.pwd == ''){
					layer.msg('请输入您的密码', {time: 1000});
				}else{
					var tradeSocket = new WebSocket(this.tradeConfig.url_real);
					tradeSocket.onopen = function(evt){
						//登录
						if(tradeSocket.readyState==1){ //连接已建立，可以进行通信。
							tradeSocket.send('{"Method":"Login","Parameters":{"ClientNo":"'+ this.username +'","PassWord":"'+ Base64.encode(this.pwd) +'","IsMock":'+this.tradeConfig.model+',"Version":"'+this.tradeConfig.version+'","Source":"'+this.tradeConfig.client_source+'"}}');
						}
					}.bind(this);
					tradeSocket.onmessage = function(evt) {
						var data = JSON.parse(evt.data);
						var parameters = data.Parameters;
						switch (data.Method){
							case 'OnRtnHeartBeat':
								break;
							case 'OnRspLogin'://登录回复
								if(parameters.Code==0){
									layer.msg('登录成功', {time: 1000});
									this.$store.state.account.username = this.username;
									this.$store.state.account.password = Base64.encode(this.pwd);
									var userData = {'username': this.username, 'password': Base64.encode(this.pwd)};  
									localStorage.setItem("tradeUser", JSON.stringify(userData));
									
									setTimeout(function(){
										this.show = false;
										this.$router.push({path: '/index'});
										this.$store.state.account.isRefresh = 1;
									}.bind(this),1000);
								}else{
									layer.msg(parameters.Message, {time: 1000});
								}
								break;
							default:
								break;
						}
					}.bind(this);
				}
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
		margin: -160px 0 0 -200px;
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
		}
	}
</style>