<template>
	<div id="account">
		<tipsDialog :msg="msgTips"></tipsDialog>
		<topbar title="我的"></topbar>
		<cs title="客服"></cs>
		<div class="top_tips">
			<img src="../assets/img/head_img.png" class="fl" />
			<p class="fl ml10">欢迎你，<span id="userName">{{username}}</span></p>
		</div>
		<div class="money_option mt10">
			<div class="money_option_row border_bottom">
				<p>余额：{{balance}}元</p>
			</div>
			<div class="money_option_row">
				<button class="fl yellow" @tap='recharge'>我要充值</button>
				<button class="fr blue" @tap='withdrawal'>我要提现</button>
			</div>
		</div>
		<div class="list_option mt10">
			<ul>
				<li @tap='tomoneyLog'>
					<img src="../assets/img/money.png" />
					<span>资金明细</span>
					<img src="../assets/img/arrow.png" class="img_arrow"/>
					<span></span>
				</li>
				<li @tap='tradeRecord'>
					<img src="../assets/img/log.png" />
					<span>开户记录</span>
					<img src="../assets/img/arrow.png" class="img_arrow"/>
					<span></span>
				</li>
				<li @tap='toidentify'>
					<img src="../assets/img/relname.png" />
					<span>实名认证</span>
					<img src="../assets/img/arrow.png" class="img_arrow"/>
					<span>{{isCertification ? '已认证' : '未认证'}}</span>
				</li>
				<li @tap='editPhone'>
					<img src="../assets/img/phone_bind.png" />
					<span>手机绑定</span>
					<img src="../assets/img/arrow.png" class="img_arrow"/>
					<span>修改</span>
				</li>
				<li @tap='editPwd'>
					<img src="../assets/img/pwd.png" />
					<span>登录密码</span>
					<img src="../assets/img/arrow.png" class="img_arrow"/>
					<span>修改</span>
				</li>
			</ul>
		</div>
		<div class="list_option mt10">
			<ul>
				<li>
					<a href="tel:4008528008">
					<img src="../assets/img/phone.png" />
					<span>客服热线</span>
					<img src="../assets/img/arrow.png" class="img_arrow"/>
					<span>{{hotLine}}</span>
					</a>
				</li>
				<li>
					<button class="btn" @tap='exit'>退出登录</button>
				</li>
			</ul>
		</div>
		
		<!--退出弹出框 s-->
		<div class="exit_box" v-show="isexit">
			<div class="bg"></div>
			<ul>
				<li @tap="confirmExit">
					<span>确认退出</span>
				</li>
				<li @tap="cancel">
					<span>取消退出</span>
				</li>
			</ul>
		</div>
		<!--退出弹出框 e-->
		
		<!--<button class="btn mt10" @tap='totest'>点我看其他demo</button>-->
	</div>
</template>

<script>
	import topbar from '../components/Topbar.vue'
	import cs from '../components/customerService.vue'
	import tipsDialog from '../components/tipsDialog.vue'
	import pro from '../assets/common.js'
	export default{
		name: 'account',
		components: {topbar, cs, tipsDialog},
		data(){
			return {
				isShow: false,
				isexit: false,
				msg: '',
				balance: 0.00,
				username: '',
				isCertification: false,
				userInfo: ''
			}
		},
		computed: {
			msgTips: function(){
				return this.msg;
			},
			PATH: function(){
				return this.$store.getters.PATH;
			},
			hotLine: function(){
				return this.$store.state.account.hotLine;
			}
		},
		methods: {
			recharge: function(){
				this.$router.push({path:'/recharge',query: {balance: this.balance}});
			},
			withdrawal: function(){
				this.$router.push({path:'/withdrawal'});
			},
			tradeRecord: function(){
				this.$router.push({path: '/tradeapply', query: {type: 1}});
			},
			editPwd: function(){
				this.$router.push({path:'/editPwd'});
			},
			editPhone: function(){
				this.$router.push({path:'/editPhone'});
			},
			toidentify: function(){
				if(this.isCertification == true){
					return;
				}else{
					this.$router.push({path:'/nameCertification'});
				}
			},
//			dialPhone: function(){
//				pro.callService();
//			},
			totest: function(){
				this.$router.push({path:'/test'});
			},
			tomoneyLog: function(){
				this.$router.push({path:'/moneyLog'});
			},
			exit: function(){
				this.isexit = true;
			},
			confirmExit: function(){
				localStorage.removeItem("user");
				this.$router.push({path:'/login'});
				this.isexit = false;
			},
			cancel: function(){
				this.isexit = false;
			},
			getUserMsg: function(){
				this.$http.post(this.PATH + '/user/getbalancerate', {emulateJSON: true},{
					headers: {
						'token':  this.userInfo.token,
						'secret': this.userInfo.secret
					},
					params: {
						businessType: 4
					},
					timeout: 5000
				}).then(function(e){
					var data = e.body;
					if(data.success == true){
						if(data.code == 1){
							this.username = this.userInfo.username;
							this.isCertification = data.data.isCertification;
							if(this.isCertification == true) this.username = data.data.username;
							this.balance = pro.parseTwoFloat(data.data.balance);
						}
					}else{
						switch (data.code){
							case '3':
								this.$children[0].isShow = true;
								this.msg = '用户信息不存在';
								break;
							default:
								break;
						}
					}
				}.bind(this), function(){
					this.$children[0].isShow = true;
					this.msg = '网络不给力，请稍后再试！';
				});
			},
			getHotLine: function(){
				this.$http.post(this.PATH + '/hotline', {emulateJSON: true}, {
					params: {},
					timeout: 5000
				}).then(function(e) {
					var data = e.body;
					if(data.success == true && data.code == 1){
						this.$store.state.account.hotLine = data.data.hotline;
					}
				}.bind(this), function() {
					this.$refs.dialog.isShow = true;
					this.msg = '网络不给力，请稍后再试！'
				});
			}
		},
		mounted: function(){
			//页面高度计算
			$("#account").css("height",window.screen.height - 20 + "px");
			//获取客服热线
			this.getHotLine();
		},
		activated: function(){
			this.userInfo = localStorage.user ? JSON.parse(localStorage.user) : '';
			//获取用户账户信息
			this.getUserMsg();
			//判断是否登录
//			if(!localStorage.user) {
//				this.$router.replace({
//					'path': '/home'
//				});
//			}
		}
	}
</script>

<style scoped lang="less">
@import url("../assets/css/main.less");
@import url("../assets/css/base.less");
/*ip6p及以上*/
@media (min-width:411px) {
	#account{
		width: 100%;
		overflow: hidden;
		padding-top: 50px;
		background: @black;
		.top_tips{
			height: 80px;
			overflow: hidden;
			padding: 15px;
			background: @deepblue;
			img{
				width: 50px;
				height: 50px;
			}
			p{
				line-height: 50px;
				font-size: @fs16;
				color: #fff;
			}
		}
		.money_option{
			background: @deepblue;
			.money_option_row{
				height: 55px;
				padding: 0 15px;
				p{
					line-height: 55px;
					text-align: center;
					font-size: @fs18;
					color: @yellow;
				}
				button{
					width: 165px;
					height: 40px;
					border-radius: 20px;
					color: @black;
					font-size: @fs16;
					border: none;
					margin-top: 7px;
					&.yellow{
						background: @yellow;
					}
					&.blue{
						background: @blue;
					}
				}
			}
		}
		.list_option{
			background: @deepblue;
			ul{
				li{
					height: 54px;
					line-height: 54px;
					border-bottom: 1px solid @black;
					font-size: @fs16; 
					padding: 0 15px;
					&:last-child{
						border: none;
					}
					img{
						float: left;
						width: 22px;
						height: 22px;
						margin: 16px 10px 0 0;
						&.img_arrow{
							float: right;
							width: 9px;
							height: 13px;
							margin: 22px 0 0 0;
						}
					}
					span{
						float: left;
						font-size: @fs16;
						color: @lightblue;
						&:nth-child(4){
							float: right;
							color: @white;
							margin-right: 15px;
						}
					}
					
				}
			}
		}
		.btn{
			width: 100%;
			height: 50px;
			line-height: 50px;
			font-size: @fs16;
			background: @deepblue;
			border: none;
			color: @lightblue;
			padding: 0;
		}
	}
	.border_bottom{
		border-bottom: 1px solid @black;
		color: @lightblue;
	}
	.exit_box{
		.bg{
			position: absolute;
			top: 0;
			left: 0;
			right: 0;
			bottom: 0;
			z-index: 1110;
			background: #000;
			opacity: 0.7;
		}
		ul{
			position: absolute;
			left: 0;
			bottom: 0;
			z-index: 1111;
			width: 100%;
			background: @deepblue;
			li{
				height: 54px;
				line-height: 54px;
				border-bottom: 1px solid @black;
				text-align: center;
				span{
					font-size: @fs16; 
					color: @blue;
				}
				&:first-child span{
					color: @white;
				}
			}
		}
	}
}
/*ip6*/
@media (min-width:371px) and (max-width:410px) {
	#account{
		width: 100%;
		overflow: hidden;
		padding-top: 50px*@ip6;
		background: @black;
		.top_tips{
			height: 80px*@ip6;
			overflow: hidden;
			padding: 15px*@ip6;
			background: @deepblue;
			img{
				width: 50px*@ip6;
				height: 50px*@ip6;
			}
			p{
				line-height: 50px*@ip6;
				font-size: @fs16*@ip6;
				color: #fff;
			}
		}
		.money_option{
			background: @deepblue;
			.money_option_row{
				height: 55px*@ip6;
				padding: 0 15px*@ip6;
				p{
					line-height: 55px*@ip6;
					text-align: center;
					font-size: @fs18*@ip6;
					color: @yellow;
				}
				button{
					width: 165px*@ip6;
					height: 40px*@ip6;
					border-radius: 20px*@ip6;
					color: @black;
					font-size: @fs16*@ip6;
					border: none;
					margin-top: 7px*@ip6;
					&.yellow{
						background: @yellow;
					}
					&.blue{
						background: @blue;
					}
				}
			}
		}
		.list_option{
			background: @deepblue;
			ul{
				li{
					height: 54px*@ip6;
					line-height: 54px*@ip6;
					border-bottom: 1px solid @black;
					font-size: @fs16*@ip6; 
					padding: 0 15px*@ip6;
					&:last-child{
						border: none;
					}
					img{
						float: left;
						width: 22px*@ip6;
						height: 22px*@ip6;
						margin: 16px*@ip6 10px*@ip6 0 0;
						&.img_arrow{
							float: right;
							width: 9px*@ip6;
							height: 13px*@ip6;
							margin: 22px*@ip6 0 0 0;
						}
					}
					span{
						float: left;
						font-size: @fs16*@ip6;
						color: @lightblue;
						&:nth-child(4){
							float: right;
							color: @white;
							margin-right: 15px*@ip6;
						}
					}
					
				}
			}
		}
		.btn{
			width: 100%;
			height: 50px*@ip6;
			line-height: 50px*@ip6;
			font-size: @fs16*@ip6;
			background: @deepblue;
			border: none;
			color: @lightblue;
			padding: 0;
		}
	}
	.border_bottom{
		border-bottom: 1px solid @black;
		color: @lightblue;
	}
	.exit_box{
		.bg{
			position: absolute;
			top: 0;
			left: 0;
			right: 0;
			bottom: 0;
			z-index: 1110;
			background: #000;
			opacity: 0.7;
		}
		ul{
			position: absolute;
			left: 0;
			bottom: 0;
			z-index: 1111;
			width: 100%;
			background: @deepblue;
			li{
				height: 54px*@ip6;
				line-height: 54px*@ip6;
				border-bottom: 1px solid @black;
				text-align: center;
				span{
					font-size: @fs16*@ip6; 
					color: @blue;
				}
				&:first-child span{
					color: @white;
				}
			}
		}
	}
}
/*ip5*/
@media(max-width:370px) {
	#account{
		width: 100%;
		overflow: hidden;
		padding-top: 50px*@ip5;
		background: @black;
		.top_tips{
			height: 80px*@ip5;
			overflow: hidden;
			padding: 15px*@ip5;
			background: @deepblue;
			img{
				width: 50px*@ip5;
				height: 50px*@ip5;
			}
			p{
				line-height: 50px*@ip5;
				font-size: @fs16*@ip5;
				color: #fff;
			}
		}
		.money_option{
			background: @deepblue;
			.money_option_row{
				height: 55px*@ip5;
				padding: 0 15px*@ip5;
				p{
					line-height: 55px*@ip5;
					text-align: center;
					font-size: @fs18*@ip5;
					color: @yellow;
				}
				button{
					width: 165px*@ip5;
					height: 40px*@ip5;
					border-radius: 20px*@ip5;
					color: @black;
					font-size: @fs16*@ip5;
					border: none;
					margin-top: 7px*@ip5;
					&.yellow{
						background: @yellow;
					}
					&.blue{
						background: @blue;
					}
				}
			}
		}
		.list_option{
			background: @deepblue;
			ul{
				li{
					height: 54px*@ip5;
					line-height: 54px*@ip5;
					border-bottom: 1px solid @black;
					font-size: @fs16*@ip5; 
					padding: 0 15px*@ip5;
					&:last-child{
						border: none;
					}
					img{
						float: left;
						width: 22px*@ip5;
						height: 22px*@ip5;
						margin: 16px*@ip5 10px*@ip5 0 0;
						&.img_arrow{
							float: right;
							width: 9px*@ip5;
							height: 13px*@ip5;
							margin: 22px*@ip5 0 0 0;
						}
					}
					span{
						float: left;
						font-size: @fs16*@ip5;
						color: @lightblue;
						&:nth-child(4){
							float: right;
							color: @white;
							margin-right: 15px*@ip5;
						}
					}
					
				}
			}
		}
		.btn{
			width: 100%;
			height: 50px*@ip5;
			line-height: 50px*@ip5;
			font-size: @fs16*@ip5;
			background: @deepblue;
			border: none;
			color: @lightblue;
			padding: 0;
		}
	}
	.border_bottom{
		border-bottom: 1px solid @black;
		color: @lightblue;
	}
	.exit_box{
		.bg{
			position: absolute;
			top: 0;
			left: 0;
			right: 0;
			bottom: 0;
			z-index: 1110;
			background: #000;
			opacity: 0.7;
		}
		ul{
			position: absolute;
			left: 0;
			bottom: 0;
			z-index: 1111;
			width: 100%;
			background: @deepblue;
			li{
				height: 54px*@ip5;
				line-height: 54px*@ip5;
				border-bottom: 1px solid @black;
				text-align: center;
				span{
					font-size: @fs16*@ip5; 
					color: @blue;
				}
				&:first-child span{
					color: @white;
				}
			}
		}
	}
}
</style>
