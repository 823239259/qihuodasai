<template>
	<div id="login">
		<tipsDialog :msg="msgTips"></tipsDialog>
		<topbar title="交易登录"></topbar>
		<back></back>
		<cs title="客服"></cs>
		<div class="page_cont">
			<div class="ipt_row mt15">
				<label for="username">交易账号</label>
				<input type="text" id="username" placeholder="请输入您的交易账号" v-model.trim="username" />
				<!--<span class="code">切换账号</span>
				<select class="switchUser" v-model="tradeUser">
					<option value="CP1008611">CP1008611</option>
					<option value="CP1008612">CP1008612</option>
					<option value="CP1008613">CP1008613</option>
					<option value="CP1008614">CP1008614</option>
					<option value="CP1008615">CP1008615</option>
					<option value="CP1008616">CP1008616</option>
				</select>-->
			</div>
			<div class="ipt_row">
				<label for="pwd">密码</label>
				<input type="password" id="pwd" placeholder="请输入您的交易密码" v-model.trim="pwd" @keyup.enter="login"/>
				<i class="eye" @tap="eyeEvent"></i>
			</div>
			<btn name="立即登录" color="blue" @tap.native='login'></btn>
			<btn name="开户申请" color="yellow" @tap.native='openApply'></btn>
			<p class="tips">点击登录，表示同意<span class="yellow" @tap="toAgreement">《期货大赛用户协议》</span></p>
			<div class="mark">
				<h3>操盘交易账户≠注册登录账户</h3>
				<p><span>交易账户：</span>申请方案后系统自动发放，用于实盘或模拟盘交易的账号。可查询交易明细，结算后将会更换。</p>
				<p><span>登录账户：</span>使用手机注册后的平台账号，用于登录APP和网站，进行充值、提现、查看资金明细等。</p>
			</div>
		</div>
		<div class="shade" v-show="shadeShow"></div>
	</div>
</template>

<script>
	import {mapMutations, mapActions} from 'vuex'
	import topbar from '../../components/Topbar.vue'
	import back from '../../components/back.vue'
	import cs from '../../components/customerService.vue'
	import btn from '../../components/bigBtn.vue'
	import tipsDialog from '../../components/tipsDialog.vue'
	import pro from '../../assets/common.js'
	export default{
		name:'login',
		components: {topbar, back, cs, btn, tipsDialog},
		computed: {
			msgTips: function(){
				return this.msg;
			},
			PATH: function(){
				return this.$store.getters.PATH;
			},
			tradeSocket() {
				return this.$store.state.tradeSocket;
			},
			tradeConfig(){
				return this.$store.state.market.tradeConfig;
			}
		},
		data(){
			return {
				eyeShow: false,
				msg: '',
				username: '',
				pwd: '',
				token: '',
				secret: '',
				tradeUser: '',
				shadeShow: false
			}
		},
		methods:{
			...mapActions([
				'handleTradeMessage',
				'initTrade'
			]),
			eyeEvent: function(e){
				if(this.eyeShow == false){
					this.eyeShow = true;
					$(e.target).addClass("current").siblings("#pwd").attr("type",'text');
				}else{
					this.eyeShow = false;
					$(e.target).removeClass("current").siblings("#pwd").attr("type",'password');
				}
			},
			toAgreement: function(){
				this.$router.push({path: '/agreement'})
			},
			login: function(){
				if(this.username == ''){
					this.$children[0].isShow = true;
					this.msg = '请输入您的交易账号';
				}else if(this.pwd == ''){
					this.$children[0].isShow = true;
					this.msg = '请输入您的交易密码';
				}else{
					var  tradeSocket = new WebSocket(this.tradeConfig.url_real);
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
								console.log('last:'+parameters.Ref);
								break;
							case 'OnRspLogin'://登录回复
								if(parameters.Code==0){
									this.shadeShow = true;
									this.$children[0].isShow = true;
									this.msg = '登录成功';
									this.$store.state.market.tradeConfig.username = this.username;
									this.$store.state.market.tradeConfig.password = Base64.encode(this.pwd);
									var userData = {'username': this.username, 'password': Base64.encode(this.pwd)};  
									localStorage.setItem("tradeUser", JSON.stringify(userData));
									setTimeout(function(){
										this.$router.push({path: '/index', query: {isBack: 1}});
									}.bind(this),100);
								}else{
									this.$children[0].isShow = true;
									this.msg = parameters.Message;
								}
								break;
							default:
								break;
						}
					}.bind(this);
				}
			},
			openApply: function(){
				this.$router.push({path: '/tradeapply'});
			}
		},
		watch: {
			tradeUser: function(n, o){
				this.username = n;
			}
		},
		mounted: function(){
			//页面高度计算
			var h = window.screen.height - 20 - $("#topbar").height();
			$("#login").height(h);
		},
		activated: function(){
			this.username = this.$route.query.user;
			this.pwd = this.$route.query.pwd;
			
			//不更新画图
			this.$store.state.isshow.isklineshow = false;
			this.$store.state.isshow.isfensshow = false;
			this.$store.state.isshow.islightshow =  false;
			
		}
	}
</script>

<style scoped lang="less">
@import url("../../assets/css/base.less");
.shade{
	width: 100%;
	height: 100%;
	overflow: hidden;
	background: #000;
	opacity: 0.3;
	position: absolute;
	top: 0;
	left: 0;
	right: 0;
	bottom: 0;
	z-index: 1010;
}
/*ip6p及以上*/
@media (min-width:411px) {
    #login{
    	width: 100%;
    	overflow: hidden;
    	background: @deepblue;
    	padding-top: 50px;
    	.page_cont{
    		.mt15{
    			margin-top: 15px;
    		}
    		.switchUser{
    			position: absolute;
	            top: 1px;
	            right: 1px;
	            z-index: 5;
	            width: 85px;
	            height: 52px;
	            line-height: 52px;
	            opacity: 0;
    		}
    		.btn{
    			margin-bottom: 15px;
    		}
    		.tips{
    			text-align: center;
    			margin-top: 15px;
    		}
    		.mark{
    			width: 100%;
    			padding: 0 15px;
    			margin-top: 10px;
    			h3{
    				font-size: @fs18;
    				color: @white;
    				padding: 20px 0;
    			}
    			p{
    				line-height: 20px;
    				font-size: @fs14;
    				margin-bottom: 15px;
    				span{
    					color: @white;
    				}
    			}
    		}
    	}
    }
}
/*ip6*/
@media (min-width:371px) and (max-width:410px) {
	#login{
    	width: 100%;
    	overflow: hidden;
    	background: @deepblue;
    	padding-top: 50px*@ip6;
    	.page_cont{
    		.mt15{
    			margin-top: 15px*@ip6;
    		}
    		.switchUser{
    			position: absolute;
	            top: 1px;
	            right: 1px;
	            z-index: 5;
	            width: 85px*@ip6;
	            height: 52px*@ip6;
	            line-height: 52px*@ip6;
	            opacity: 0;
    		}
    		.btn{
    			margin-bottom: 15px*@ip6;
    		}
    		.tips{
    			text-align: center;
    			margin-top: 15px*@ip6;
    		}
    		.mark{
    			width: 100%;
    			padding: 0 15px*@ip6;
    			margin-top: 10px*@ip6;
    			h3{
    				font-size: @fs18*@ip6;
    				color: @white;
    				padding: 20px*@ip6 0;
    			}
    			p{
    				line-height: 20px*@ip6;
    				font-size: @fs14*@ip6;
    				margin-bottom: 15px*@ip6;
    				span{
    					color: @white;
    				}
    			}
    		}
    	}
    }
}
/*ip5*/
@media(max-width:370px) {
	#login{
    	width: 100%;
    	overflow: hidden;
    	background: @deepblue;
    	padding-top: 50px*@ip5;
    	.page_cont{
    		.mt15{
    			margin-top: 15px*@ip5;
    		}
    		.switchUser{
    			position: absolute;
	            top: 1px;
	            right: 1px;
	            z-index: 5;
	            width: 85px*@ip5;
	            height: 52px*@ip5;
	            line-height: 52px*@ip5;
	            opacity: 0;
    		}
    		.btn{
    			margin-bottom: 15px*@ip5;
    		}
    		.tips{
    			text-align: center;
    			margin-top: 15px*@ip5;
    		}
    		.mark{
    			width: 100%;
    			padding: 0 15px*@ip5;
    			margin-top: 10px*@ip5;
    			h3{
    				font-size: @fs18*@ip5;
    				color: @white;
    				padding: 20px*@ip5 0;
    			}
    			p{
    				line-height: 20px*@ip5;
    				font-size: @fs14*@ip5;
    				margin-bottom: 15px*@ip5;
    				span{
    					color: @white;
    				}
    			}
    		}
    	}
    }
}
</style>