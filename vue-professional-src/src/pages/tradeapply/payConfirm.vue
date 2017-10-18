<template>
	<div id="payconfirm">
		<tipsDialog :msg="msgTips"></tipsDialog>
		<tp></tp>
		<cs title="操盘细则" type="1" @tap.native="toTradersRules"></cs>
		<ul class="tradeclass">
			<li class="fl">
				<template v-for="(v,k) in temp.contractList">
					<div v-if="k%2 == 0">可交易品种：</div>
				</template>
			</li>
			<li class="fl">
				<template v-for="(v,k) in temp.contractList">
					<div v-if="k%2 == 0" class="fontwhite">{{v.tradeType | cnname}}-{{v.shoushu | filtershoushu(chooseType)}}手</div>
				</template>
			</li>
			<li class="fl">
				<template v-for="(v,k) in temp.contractList">
					<div v-if="k%2 == 1" class="fontwhite">{{v.tradeType | cnname}}-{{v.shoushu | filtershoushu(chooseType)}}手</div>
				</template>
			</li>
		</ul>
		<div class="fontgray notice">
			<span class="fontyellow">注意：</span>以上手数为交易该品种时，初始最大可持仓手数
		</div>
		<!--总操盘资金-->
		<ul class="moneyinfo">
			<li class="fontgray">总操盘资金：<span class="fontwhite">{{traderTotal | moneytype}}美元</span></li>
			<li class="fontgray">亏损平仓线：<span class="fontwhite">{{lineLoss | moneytype}}美元</span></li>
		</ul>
		<!--账户余额-->
		<ul class="payinfo">
			<li>
				<div class="fontyellow fl">
					账户余额：{{balance}}元
				</div>
				<div class="fontgray fr">
					<span v-if="enough">(账户余额充足)</span><span class="fontred" v-else="">(账户余额不足请充值)</span>
				</div>
			</li>
			<li>
				<div class="fontyellow fl">
					支付金额：{{chooseType | moneytype}}元
				</div>
				<div class="fontgray fr">
					(操盘保证金)
				</div>
			</li>
		</ul>
		<a href="javascript:void(0);">
			<bbtn :name="btnName" @tap.native="tocom"></bbtn>
		</a>
	</div>
</template>

<script>
	import tp from '../../components/payTopbar.vue'
	import bbtn from '../../components/bigBtn.vue'
	import tipsDialog from '../../components/tipsDialog.vue'
	import cs from '../../components/customerService.vue'
	import pro from '../../assets/common.js'
	export default {
		name: 'payconfirm',
		components: {tp, bbtn, tipsDialog, cs},
		filters: {
			filtershoushu: function(arr, chooseType) {
				switch(chooseType) {
					case 3000:
						return arr[0];
						break;
					case 6000:
						return arr[1];
						break;
					case 10000:
						return arr[2];
						break;
					case 12000:
						return arr[3];
						break;
					case 15000:
						return arr[4];
						break;
					case 50000:
						return arr[5];
						break;
					case 100000:
						return arr[6];
						break;
					case 200000:
						return arr[7];
						break;
				}
			},
			moneytype: function(num) {
				return num.toLocaleString();
			},
			cnname: function(a) {
				switch(a) {
					case 0:
						return '富时A50'
						break;
					case 6:
						return '国际原油'
						break;
					case 7:
						return '恒指期货'
						break;
					case 9:
						return '迷你道指'
						break;
					case 10:
						return '迷你纳指'
						break;
					case 11:
						return '迷你标普'
						break;
					case 12:
						return '德国DAX'
						break;
					case 13:
						return '日经225'
						break;
					case 14:
						return '小恒指'
						break;
					case 15:
						return '美黄金'
						break;
					case 16:
						return 'H股指数'
						break;
					case 17:
						return '小H股指数'
						break;
					case 18:
						return '美铜'
						break;
					case 19:
						return '美白银'
						break;
					case 20:
						return '小原油'
						break;
					case 21:
						return '迷你德国DAX指数' //迷你德国DAX指数
						break;
					case 22:
						return '天然气'
						break;
				}

			}
		},
		data() {
			return {
				msg: '',
				chooseType: 3000,
				traderTotal: 0,
				lineLoss: 0,
				balance: 0.00,
				userInfo: '',
				btnName: '确认支付'
			}
		},
		computed: {
			msgTips: function(){
				return this.msg;
			},
			PATH() {
				return this.$store.getters.PATH
			},
			temp() {
				return this.$store.state.tempTradeapply;
			},
			enough: function() {
				if((this.balance - this.chooseType) < 0) {
					this.btnName = '立即充值';
					return false;
				} else {
					this.btnName = '确认支付';
					return true;
				}
			}
		},
		methods: {
			toTradersRules: function(){
				this.$router.push({path: '/tradersRules'});
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
					this.msg = '服务器连接失败';
				});
			},
			tocom: function(e) {
				//资金足够的时候
				if(this.enough) {
					this.$http.post(
						this.PATH + '/user/ftrade/handle', {emulateJSON: true}, {
							headers:{
								'token':  this.userInfo.token,
								'secret': this.userInfo.secret
							},
							params: {
								"vid":-1,
								"traderBond":this.chooseType,
								"tranLever":0,
								"businessType":8
							},
							timeout: 5000
						}
					).then(function(e) {
						var data = e.body;
						if(data.success == true){
							if(data.code == 1){
								this.$store.state.account.programList.push(e.body.data);
								this.$router.replace({
									path: '/applycomplate',
									query:{
										'vid': data.data.id,
										'businessType': 8,
										'isPay': data.data.stateType
									}
								});
							}
						}else{
							switch (data.code){
								case '-1':
									this.$children[0].isShow = true;
									this.msg = '认证失败';
									break;
								case '2':
									this.$children[0].isShow = true;
									this.msg = '参数传入错误';
									break;
								case '3':
									this.$children[0].isShow = true;
									this.msg = '账户余额不足';
									break;
								default:
									break;
							}
						}
					}.bind(this), function(e) {
						this.$children[0].isShow = true;
						this.msg = '服务器连接失败';
					});
				}else{    //资金不足的时候
//					$(e.currentTarget).parent().attr("href","tel:4008528008");
					this.$router.replace({
						path: '/recharge'
					});
//					pro.callService();
				}
			}
		},
		mounted: function() {
			this.chooseType = this.$route.query.chooseType;
			this.traderTotal = this.$route.query.traderTotal;
			this.lineLoss = this.$route.query.lineLoss;
		},
		activated: function() {
			this.userInfo = JSON.parse(localStorage.user);
			//获取用户账户信息
			this.getUserMsg();
			this.chooseType = this.$route.query.chooseType;
			this.traderTotal = this.$route.query.traderTotal;
			this.lineLoss = this.$route.query.lineLoss;
		}
	}
</script>

<style scoped lang="less">
	@import url("../../assets/css/main.less");
	/*ip5*/
	@media(max-width:370px) {
		#payconfirm {
			padding-top: 50px*@ip5;
			height: 414px*@ip5 - 20px;
			background-color: #242633;
			height: 736px*@ip5 - 20px;
		}
		.tradeclass {
			&:after {
				content: '';
				display: block;
				clear: both;
			}
			padding: 0 15px*@ip5;
			background: #242633;
			li {
				/*height: 40px;*/
				/*line-height: 40px;*/
				font-size: 14px*@ip5;
			}
			li:first-child {
				width: 95px*@ip5;
				div {
					height: 40px*@ip5;
					line-height: 40px*@ip5;
					border-bottom: 1px solid #1B1B26;
					color: transparent;
				}
				div:first-child {
					color: #949bbb;
				}
			}
			li:nth-child(2) {
				div {
					height: 40px*@ip5;
					line-height: 40px*@ip5;
					border-bottom: 1px solid #1B1B26;
				}
				width: 120px*@ip5;
			}
			li:nth-child(3) {
				div {
					height: 40px*@ip5;
					line-height: 40px*@ip5;
					border-bottom: 1px solid #1B1B26;
				}
				text-align: right;
			}
		}
		.notice {
			padding: 0 15px*@ip5;
			font-size: 14px*@ip5;
			height: 40px*@ip5;
			line-height: 40px*@ip5;
			background-color: #242633;
		}
		.moneyinfo {
			height: 90px*@ip5;
			border-bottom: 5px solid #1B1B26;
			border-top: 5px solid #1B1B26;
			background: #242633;
			>li {
				border-top: 1px solid #1B1B26;
				height: 40px*@ip5;
				line-height: 40px*@ip5;
				padding: 0 15px*@ip5;
				font-size: 14px*@ip5;
			}
		}
		.payinfo {
			height: 80px*@ip5;
			background: #242633;
			margin-bottom: 5px*@ip5;
			>li {
				height: 40px*@ip5;
				line-height: 40px*@ip5;
				border-bottom: 1px solid #1B1B26;
				padding: 0 15px*@ip5;
				div:first-child {
					font-size: 16px*@ip5;
				}
				div:last-child {
					font-size: 12px;
				}
			}
		}
	}
	/*ip6*/
	
	@media (min-width:371px) and (max-width:410px) {
		#payconfirm {
			padding-top: 50px*@ip6;
			height: 414px*@ip6 - 20px;
			background-color: #242633;
			height: 736px*@ip6 - 20px;
		}
		.tradeclass {
			&:after {
				content: '';
				display: block;
				clear: both;
			}
			padding: 0 15px*@ip6;
			background: #242633;
			li {
				/*height: 40px;*/
				/*line-height: 40px;*/
				font-size: 14px*@ip6;
			}
			li:first-child {
				width: 90px*@ip6;
				div {
					height: 40px*@ip6;
					line-height: 40px*@ip6;
					border-bottom: 1px solid #1B1B26;
					color: transparent;
				}
				div:first-child {
					color: #949bbb;
				}
			}
			li:nth-child(2) {
				div {
					height: 40px*@ip6;
					line-height: 40px*@ip6;
					border-bottom: 1px solid #1B1B26;
				}
				width: 120px*@ip6;
			}
			li:nth-child(3) {
				div {
					height: 40px*@ip6;
					line-height: 40px*@ip6;
					border-bottom: 1px solid #1B1B26;
				}
				text-align: right;
			}
		}
		.notice {
			padding: 0 15px*@ip6;
			font-size: 14px*@ip6;
			height: 40px*@ip6;
			line-height: 40px*@ip6;
			background-color: #242633;
		}
		.moneyinfo {
			height: 90px*@ip6;
			border-bottom: 5px solid #1B1B26;
			border-top: 5px solid #1B1B26;
			background: #242633;
			>li {
				border-top: 1px solid #1B1B26;
				height: 40px*@ip6;
				line-height: 40px*@ip6;
				padding: 0 15px*@ip6;
				font-size: 14px*@ip6;
			}
		}
		.payinfo {
			height: 80px*@ip6;
			background: #242633;
			margin-bottom: 5px*@ip6;
			>li {
				height: 40px*@ip6;
				line-height: 40px*@ip6;
				border-bottom: 1px solid #1B1B26;
				padding: 0 15px*@ip6;
				div:first-child {
					font-size: 16px*@ip6;
				}
				div:last-child {
					font-size: 12px;
				}
			}
		}
	}
	/*ip6p及以上*/
	
	@media (min-width:411px) {
		#payconfirm {
			padding-top: 50px;
			height: 414px - 20px;
			background-color: #242633;
			height: 736px*@ip6p - 20px;
		}
		.tradeclass {
			&:after {
				content: '';
				display: block;
				clear: both;
			}
			padding: 0 15px;
			background: #242633;
			li {
				/*height: 40px;*/
				/*line-height: 40px;*/
				font-size: 14px;
			}
			li:first-child {
				width: 90px;
				div {
					height: 40px;
					line-height: 40px;
					border-bottom: 1px solid #1B1B26;
					color: transparent;
				}
				div:first-child {
					color: #949bbb;
				}
			}
			li:nth-child(2) {
				div {
					height: 40px;
					line-height: 40px;
					border-bottom: 1px solid #1B1B26;
				}
				width: 120px;
			}
			li:nth-child(3) {
				div {
					height: 40px;
					line-height: 40px;
					border-bottom: 1px solid #1B1B26;
				}
				text-align: right;
			}
		}
		.notice {
			padding: 0 15px;
			font-size: 14px;
			height: 40px;
			line-height: 40px;
			background-color: #242633;
		}
		.moneyinfo {
			height: 90px;
			border-bottom: 5px solid #1B1B26;
			border-top: 5px solid #1B1B26;
			background: #242633;
			>li {
				border-top: 1px solid #1B1B26;
				height: 40px;
				line-height: 40px;
				padding: 0 15px;
				font-size: 14px;
			}
		}
		.payinfo {
			height: 80px;
			background: #242633;
			margin-bottom: 5px;
			>li {
				height: 40px;
				line-height: 40px;
				border-bottom: 1px solid #1B1B26;
				padding: 0 15px;
				div:first-child {
					font-size: 16px;
				}
				div:last-child {
					font-size: 12px;
				}
			}
		}
	}
</style>