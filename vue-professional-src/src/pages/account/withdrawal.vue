<template>
	<div id="withdrawal">
		<tipsDialog :msg="msgTips"></tipsDialog>
		<topbar title="我要提现"></topbar>
		<back :title="isJumpEvent"></back>
		<cs title="客服"></cs>
		<div class="page_cont">
			<div class="bind_bank" @tap="toBindCard">
				<i class="icon_bank"></i>
				<span>我的银行卡</span>
				<i class="icon_arrow"></i>
				<!--<span v-if="!bankShow">绑定银行卡</span>-->
				<span>{{defaultBank}}</span>
			</div>
			<div class="money mt10">
				<p>余额：{{balance}}元</p>
				<p>累计免提现手续费金额为：<span>{{operateMoney}}元</span></p>
			</div>
			<div class="fm_box mt10">
				<div class="ipt_row">
					<label for="money">提现金额</label>
					<input type="number" id="money" placeholder="请输入提现金额" v-model.trim="money" />
					<span class="unit">元</span>
				</div>
				<div class="ipt_row">
					<label for="poundage">提现手续费</label>
					<input type="number" id="poundage" placeholder="" disabled v-model="poundage" />
					<span class="unit">元</span>
				</div>
				<div class="ipt_row">
					<label for="relmoney">实际到账</label>
					<input type="number" id="relmoney" placeholder="" disabled v-model="relmoney" />
					<span class="unit">元</span>
				</div>
				<div class="ipt_row">
					<label for="pwd">提现密码</label>
					<input type="password" id="pwd" placeholder="请输入您的提现密码" v-model="pwd" />
					<i class="eye" @tap="eyeEvent"></i>
				</div>
				<btn name="确认提现" @tap.native="confirmOperate"></btn>
				<p class="tools white" @tap="toMoneyPwd">设置/修改<span class="yellow">提现密码</span></p>
			</div>
			<div class="tips mt10">
				<h2 class="yellow">温馨提示</h2>
				<p>1.到账时间：工作日09:00-16:30申请，24小时内到账，最快5分钟到账，其余时间申请将在下个工作日到账。</p>
				<p>2.提现手续费：（a）1%（适用于充值后，未实际操盘金额）；（b）0元（适用于操盘用户提现）。</p>
				<p>3.提现处理时间：每个工作日固定时间进行提现处理。具体为10:00，14:00，16:00，17:30。</p>
			</div>
		</div>
	</div>
</template>

<script>
	import topbar from '../../components/Topbar.vue'
	import back from '../../components/back.vue'
	import cs from '../../components/customerService.vue'
	import btn from '../../components/bigBtn.vue'
	import tipsDialog from '../../components/tipsDialog.vue'
	import pro from '../../assets/common.js'
	export default{
		name:'withdrawal',
		components: {topbar, back, cs, btn, tipsDialog},
		data(){
			return {
				msg: '',
				eyeShow: false,
				balance: 0.00,
				operateMoney: 0.00,
				money: '',
				poundage: 0,
				relmoney: '',
				pwd: '',
				defaultBank: '',
				abbreviation: '',
				defaultBankCard: '',
				pwdReg: /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{6,18}$/,
				userInfo: ''
			}
		},
		computed: {
			isJumpEvent: function(){
				this.isJump = this.$route.query.isJump;
				if(this.isJump == 1)  return true;
			},
			msgTips: function(){
				return this.msg;
			},
			PATH: function(){
				return this.$store.getters.PATH;
			}
		},
		watch: {
			money: function(){
				if(this.money == ''){
					this.poundage = 0;
					this.relmoney = '';
					return false;
				}else{
					if(this.money < 0){
						this.$children[0].isShow = true;
						this.msg = '提现金额不能是负数';
						this.money = '';
					}else{
						var str = this.money.toString().split('.');
						if(str[1] && str[1].length > 1){
							this.money = parseFloat(str[0]+ '.'+str[1].substring(0,2));
						}
//						this.relmoney = this.money;
						this.$http.post(this.PATH + '/user/withdraw/drawFee', {emulateJSON: true},{
							headers: {
								'token':  this.userInfo.token,
								'secret': this.userInfo.secret
							},
							params: {money: this.money},
							timeout: 5000
						}).then(function(e){
							var data = e.body;
							if(data.success == true){
								if(data.code == 1){
									this.poundage = data.data.fee;
									this.relmoney = parseFloat(this.money - this.poundage).toFixed(2);
								}
							}else{
								switch (data.code){
									case '-1':
										this.$children[0].isShow = true;
										this.msg = '认证失败';
										break;
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
					}
				}
			}
		},
		methods: {
			eyeEvent: function(e){
				if(this.eyeShow == false){
					this.eyeShow = true;
					$(e.target).addClass("current").siblings("#pwd").attr("type",'text');
				}else{
					this.eyeShow = false;
					$(e.target).removeClass("current").siblings("#pwd").attr("type",'password');
				}
			},
			toBindCard: function(){
				this.$router.push({path: '/bindBankCard'});
			},
			toMoneyPwd: function(){
				this.$router.push({path: '/moneyPwd'});
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
							this.operateMoney = pro.parseTwoFloat(data.data.operateMoney);
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
			getBindBankList: function(){
				this.$http.post(this.PATH + '/user/withdraw/bank_list', {emulateJSON: true},{
					headers: {
						'token':  this.userInfo.token,
						'secret': this.userInfo.secret
					},
					params: {},
					timeout: 5000
				}).then(function(e){
					var data = e.body;
					if(data.success == true){
						if(data.code == 1){
							if(data.data.length > 0){
								console.log(data.data);
								data.data.forEach(function(o, i){
									if(o.default == true){
										this.defaultBank = o.card.substr(0,4) + '***********' + o.card.substr(-4,4);
										this.defaultBankCard = o.card;
										this.abbreviation = o.abbreviation;
									}
								}.bind(this));
							}else{
								this.defaultBank = '去绑定';
							}
							
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
			confirmOperate: function(){
				if(this.money == ''){
					this.$children[0].isShow = true;
					this.msg = '请输入提现金额';
				}else if(this.pwd == ''){
					this.$children[0].isShow = true;
					this.msg = '请输入提现密码';
				}else{
					this.$http.post(this.PATH + '/user/withdraw/handle', {emulateJSON: true},{
						headers: {
							'token':  this.userInfo.token,
							'secret': this.userInfo.secret
						},
						params: {
							bank: this.abbreviation,
							card: this.defaultBankCard,
							money: this.money,
							withdrawPwd: this.pwd
						},
						timeout: 5000
					}).then(function(e){
						var data = e.body;
						if(data.success == true){
							if(data.code == 1){
								this.$children[0].isShow = true;
								this.msg = '提现成功';
								this.money = '';
								this.poundage = '';
								this.relmoney = '';
								this.pwd = '';
								setTimeout(function(){
									this.$router.replace({path: '/account', query: {isJump: 1}});
								}.bind(this),1000);
							}
						}else{
							switch (data.code){
								case '-1':
									this.$children[0].isShow = true;
									this.msg = '认证失败';
									break;
								case '0':
									this.$children[0].isShow = true;
									this.msg = 'token失效';
									break;
								case '2':
									this.$children[0].isShow = true;
									this.msg = '此账户暂未绑定银行卡';
									break;
								case '3':
									this.$children[0].isShow = true;
									this.msg = '用户信息不存在';
									break;
								case '4':
									this.$children[0].isShow = true;
									this.msg = '银行卡卡号不存在';
									break;
								case '5':
									this.$children[0].isShow = true;
									this.msg = '存在欠费无法提现';
									break;
								case '6':
									this.$children[0].isShow = true;
									this.msg = '系统升级期间无法提现';
									break;
								case '7':
									this.$children[0].isShow = true;
									this.msg = '余额不足不能提现';
									break;
								case '8':
									this.$children[0].isShow = true;
									this.msg = '当天取款次数不能超过5次';
									break;
								case '9':
									this.$children[0].isShow = true;
									this.msg = '每次提现金额不能小于10元';
									break;
								case '10':
									this.$children[0].isShow = true;
									this.msg = '提现密码错误';
									break;
								case '11':
									this.$children[0].isShow = true;
									this.msg = '暂不支持此银行提现';
									break;
								case '12':
									this.$children[0].isShow = true;
									this.msg = '单笔提现金额不能超过5万元';
									break;
								case '15':
									this.$children[0].isShow = true;
									this.msg = '提现渠道设置参数错误';
									break;
								default:
									break;
							}
						}
					}.bind(this), function(){
						this.$children[0].isShow = true;
						this.msg = '网络不给力，请稍后再试！';
					});
				}
			},
		},
		mounted: function(){
			//页面高度计算
//			$("#withdrawal").css("height", window.screen.height - 20 + 'px');
		},
		activated: function(){
			this.userInfo = JSON.parse(localStorage.user);
			//获取用户账户信息
			this.getUserMsg();
			//获取默认银行卡
			this.getBindBankList();
		}
	}
</script>

<style scoped lang="less">
@import url("../../assets/css/base.less");
/*ip6p及以上*/
@media (min-width:411px) {
    #withdrawal{
    	width: 100%;
    	overflow-y: scroll;
    	padding-top: 50px;
    	background: @black;
    	.page_cont{
    		.bind_bank{
    			width: 100%;
    			height: 42px;
    			background: @deepblue;
    			padding: 0 15px;
    			span{
    				float: left;
    				line-height: 42px;
    				font-size: @fs16;
    				color: @blue;
    				&:last-child{
    					float: right;
    					margin-right: 15px;
    					font-size: @fs14;
    				}
    			}
    			i{
    				float: left;
    				display: inline-block;
    				overflow: hidden;
    				&.icon_bank{
    					width: 22px;
    					height: 22px;
    					background: url(../../assets/img/bank.png) no-repeat center center;
    					background-size: 22px 22px;
    					margin: 10px 10px 0 0;
    				}
    				&.icon_arrow{
    					float: right;
    					width: 10px;
    					height: 15px;
    					background: url(../../assets/img/arrow.png) no-repeat center center;
    					background-size: 10px 15px;
    					margin: 13px 0 0 0;
    				}
    			}
    			
    		}
    	}
    	.money{
    		overflow: hidden;
    		background: @deepblue;
    		p{
    			height: 44px;
    			line-height: 44px;
    			text-align: center;
    			color: @blue;
    			font-size: @fs14;
    			span{
    				color: @white;
    			}
    			&:first-child{
    				color: @yellow;
    				font-size: @fs16;
    				border-bottom: 1px solid @black;
    			}
    		}
    	}
    	.fm_box{
    		overflow: hidden;
    		background: @deepblue;
    		padding-top: 15px;
    		label{
    			width: 110px;
    		}
    		input{
    			padding-left: 120px;
    		}
    		.unit{
    			position: absolute;
    			top: 0;
    			right: 0;
    			z-index: 5;
    			display: inline-block;
    			height: 54px;
    			line-height: 54px;
    			margin-right: 15px;
    			color: @blue;
    		}
    		.tools{
	    		margin: 35px 0 25px 0;
	    		text-align: center;
	    	}
    	}
    	.tips{
    		overflow: hidden;
    		background: @deepblue;
    		padding: 15px;
    		h2{
    			font-size: @fs16;
    			margin: 0 0 10px 0;
    		}
    		p{
    			line-height: 28px;
    			margin-bottom: 26px;
    			color: #7a7f99; 
    			font-size: @fs16;
    			&:last-child{
    				margin: 0;
    			}
    		}
    	}
    }
}
/*ip6*/
@media (min-width:371px) and (max-width:410px) {
	#withdrawal{
    	width: 100%;
    	overflow-y: scroll;
    	padding-top: 50px*@ip6;
    	background: @black;
    	.page_cont{
    		.bind_bank{
    			width: 100%;
    			height: 42px*@ip6;
    			background: @deepblue;
    			padding: 0 15px*@ip6;
    			span{
    				float: left;
    				line-height: 42px*@ip6;
    				font-size: @fs16*@ip6;
    				color: @blue;
    				&:last-child{
    					float: right;
    					margin-right: 15px*@ip6;
    					font-size: @fs14*@ip6;
    				}
    			}
    			i{
    				float: left;
    				display: inline-block;
    				overflow: hidden;
    				&.icon_bank{
    					width: 22px*@ip6;
    					height: 22px*@ip6;
    					background: url(../../assets/img/bank.png) no-repeat center center;
    					background-size: 22px*@ip6 22px*@ip6;
    					margin: 10px*@ip6 10px*@ip6 0 0;
    				}
    				&.icon_arrow{
    					float: right;
    					width: 10px*@ip6;
    					height: 15px*@ip6;
    					background: url(../../assets/img/arrow.png) no-repeat center center;
    					background-size: 10px*@ip6 15px*@ip6;
    					margin: 13px*@ip6 0 0 0;
    				}
    			}
    			
    		}
    	}
    	.money{
    		overflow: hidden;
    		background: @deepblue;
    		p{
    			height: 44px*@ip6;
    			line-height: 44px*@ip6;
    			text-align: center;
    			color: @blue;
    			font-size: @fs14*@ip6;
    			span{
    				color: @white;
    			}
    			&:first-child{
    				color: @yellow;
    				font-size: @fs16*@ip6;
    				border-bottom: 1px solid @black;
    			}
    		}
    	}
    	.fm_box{
    		overflow: hidden;
    		background: @deepblue;
    		padding-top: 15px*@ip6;
    		label{
    			width: 110px*@ip6;
    		}
    		input{
    			padding-left: 120px*@ip6;
    		}
    		.unit{
    			position: absolute;
    			top: 0;
    			right: 0;
    			z-index: 5;
    			display: inline-block;
    			height: 54px*@ip6;
    			line-height: 54px*@ip6;
    			margin-right: 15px*@ip6;
    			color: @blue;
    		}
    		.tools{
	    		margin: 35px*@ip6 0 25px*@ip6 0;
	    		text-align: center;
	    	}
    	}
    	.tips{
    		overflow: hidden;
    		background: @deepblue;
    		padding: 15px*@ip6;
    		h2{
    			font-size: @fs16*@ip6;
    			margin: 0 0 10px 0;
    		}
    		p{
    			line-height: 28px*@ip6;
    			margin-bottom: 26px*@ip6;
    			color: #7a7f99; 
    			font-size: @fs16*@ip6;
    			&:last-child{
    				margin: 0;
    			}
    		}
    	}
    }
}
/*ip5*/
@media(max-width:370px) {
	#withdrawal{
    	width: 100%;
    	overflow-y: scroll;
    	padding-top: 50px*@ip5;
    	background: @black;
    	.page_cont{
    		.bind_bank{
    			width: 100%;
    			height: 42px*@ip5;
    			background: @deepblue;
    			padding: 0 15px*@ip5;
    			span{
    				float: left;
    				line-height: 42px*@ip5;
    				font-size: @fs16*@ip5;
    				color: @blue;
    				&:last-child{
    					float: right;
    					margin-right: 15px*@ip5;
    					font-size: @fs14*@ip5;
    				}
    			}
    			i{
    				float: left;
    				display: inline-block;
    				overflow: hidden;
    				&.icon_bank{
    					width: 22px*@ip5;
    					height: 22px*@ip5;
    					background: url(../../assets/img/bank.png) no-repeat center center;
    					background-size: 22px*@ip5 22px*@ip5;
    					margin: 10px*@ip5 10px*@ip5 0 0;
    				}
    				&.icon_arrow{
    					float: right;
    					width: 10px*@ip5;
    					height: 15px*@ip5;
    					background: url(../../assets/img/arrow.png) no-repeat center center;
    					background-size: 10px*@ip5 15px*@ip5;
    					margin: 13px*@ip5 0 0 0;
    				}
    			}
    			
    		}
    	}
    	.money{
    		overflow: hidden;
    		background: @deepblue;
    		p{
    			height: 44px*@ip5;
    			line-height: 44px*@ip5;
    			text-align: center;
    			color: @blue;
    			font-size: @fs14*@ip5;
    			span{
    				color: @white;
    			}
    			&:first-child{
    				color: @yellow;
    				font-size: @fs16*@ip5;
    				border-bottom: 1px solid @black;
    			}
    		}
    	}
    	.fm_box{
    		overflow: hidden;
    		background: @deepblue;
    		padding-top: 15px*@ip5;
    		label{
    			width: 110px*@ip5;
    		}
    		input{
    			padding-left: 120px*@ip5;
    		}
    		.unit{
    			position: absolute;
    			top: 0;
    			right: 0;
    			z-index: 5;
    			display: inline-block;
    			height: 54px*@ip5;
    			line-height: 54px*@ip5;
    			margin-right: 15px*@ip5;
    			color: @blue;
    		}
    		.tools{
	    		margin: 35px*@ip5 0 25px*@ip5 0;
	    		text-align: center;
	    	}
    	}
    	.tips{
    		overflow: hidden;
    		background: @deepblue;
    		padding: 15px*@ip5;
    		h2{
    			font-size: @fs16*@ip5;
    			margin: 0 0 10px 0;
    		}
    		p{
    			line-height: 28px*@ip5;
    			margin-bottom: 26px*@ip5;
    			color: #7a7f99; 
    			font-size: @fs16*@ip5;
    			&:last-child{
    				margin: 0;
    			}
    		}
    	}
    }
}
</style>