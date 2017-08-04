<template>
	<div id="addMoney">
		<tipsDialog :msg="msgTips"></tipsDialog>
		<topbar title="追加保证金"></topbar>
		<back :title="isJumpEvent"></back>
		<cs title="客服"></cs>
		<div class="page_cont">
			<div class="money_box">
				<span class="blue">账户余额：</span>
				<span class="yellow">{{userInfo.balance}}元</span>
				<i class="icon_arrow"></i>
				<span class="white fr" @tap="toRecharge">去充值</span>
			</div>
			<div class="ipt_box">
				<div class="ipt_row">
					<label for="money">追加金额</label>
					<input type="number" id="money" placeholder="输入金额" v-model.trim="money" @keyup="moneyEvent" />
					<span>元</span>
				</div>
			</div>
			<ul>
				<li><span class="blue">换算汇率：</span><span class="white">{{rate}}人民币=1美元</span></li>
				<li><span class="blue">换算美元：</span><span class="white">{{moneyRate}}美元</span></li>
			</ul>
			<div class="btn_box">
				<btn name="立即追加" @tap.native="confirmAdd"></btn>
			</div>
			<div class="tips">
				<h4 class="yellow">温馨提示：</h4>
				<p>1.最低追加保证金额为500元。</p>
				<p>2.系统将在下个交易日前为您的操盘账户追加保证金。</p>
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
	export default{
		name:'addMoney',
		components: {topbar, back, cs, btn, tipsDialog},
		data(){
			return {
				msg: '',
				money: '',
				rate: '',
				moneyRate: '0.00'
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
			},
			userInfo: function(){
				return this.$store.state.account;
			},
		},
		methods: {
			toRecharge: function(){
				this.$router.push({path: '/recharge'});
			},
			confirmAdd: function(){
				if(this.money == ''){
					this.$children[0].isShow = true;
					this.msg = '请输入追加金额';
				}else if(this.money < 500) {
					this.$children[0].isShow = true;
					this.msg = '追加金额最低500元！！';
    			}else if(Number(this.userInfo.balance) < Number(this.money)) {
    				this.$children[0].isShow = true;
					this.msg = '余额不足，请立即充值!';
    			}else{
					this.$http.post(this.PATH + '/user/ftrade/addbond', {emulateJSON: true},{
						headers: {
							'token':  this.userInfo.token,
							'secret': this.userInfo.secret
						},
						params: {
							'id': this.$route.query.id,
							'addBond': this.money
						},
						timeout: 5000
					}).then(function(e){
						var data = e.body;
						if(data.success == true){
							if(data.code == 1){
								this.$children[0].isShow = true;
								this.msg = '追加成功';
								this.$store.state.account.balance = this.userInfo.balance - this.money;
								this.money = '';
								this.moneyRate = '0.00';
								this.$router.push({
									path: '/tradeDetails',
									query: {
										id: this.$route.query.id,
										type: this.$route.query.type
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
									this.msg = '余额不足';
									break;
								case '3':
									this.$children[0].isShow = true;
									this.msg = '未找到该方案';
									break;
								case '4':
									this.$children[0].isShow = true;
									this.msg = '该方案已完结';
									break;
								case '5':
									this.$children[0].isShow = true;
									this.msg = '追加金额低于默认最小金额';
									break;
								case '6':
									this.$children[0].isShow = true;
									this.msg = '方案编号不能为空';
									break;
								case '7':
									this.$children[0].isShow = true;
									this.msg = '追加金额不能为空或者0';
									break;
								case '8':
									this.$children[0].isShow = true;
									this.msg = '追加保证金失败！';
									break;
								default:
									break;
							}
						}
					}.bind(this), function(){
						this.$children[0].isShow = true;
						this.msg = '服务器连接失败';
					});
				}
			},
			getUserMsg: function(){
				this.$http.post(this.PATH + '/user/getbalancerate', {emulateJSON: true},{
					headers: {
						'token':  this.userInfo.token,
						'secret': this.userInfo.secret
					},
					params: {
						businessType: 1
					},
					timeout: 5000
				}).then(function(e){
					var data = e.body;
					if(data.success == true){
						if(data.code == 1){
							var info = data.data;
							this.rate = info.rate;
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
			moneyEvent: function() {
				if(this.money){
					this.moneyRate = (this.money/this.rate).toFixed(2);
					this.money = parseInt(this.money);
					if(Number(this.money) > Number(this.userInfo.balance)){
						this.$children[0].isShow = true;
						this.msg = '余额不足！';
//						this.money = this.userInfo.balance;
					}
				}else{
					this.moneyRate = '0.00';
				}
			}
		},
		mounted: function(){
			//页面高度计算
			$("#addMoney").css("height", window.screen.height - 20 + 'px');
		},
		activated: function(){
			//获取余额、汇率等
			this.getUserMsg();
		}
	}
</script>

<style scoped lang="less">
@import url("../../assets/css/base.less");
/*ip6p及以上*/
@media (min-width:411px) {
    #addMoney{
    	width: 100%;
    	padding-top: 50px;
    	background: @deepblue;
    	.page_cont{
    		span{
				&.blue{
					color: @blue;
				}
				&.yellow{
					color: @yellow;
				}
				&.white{
					color: @white;
				}
			}
    		.money_box{
    			width: 100%;
    			height: 52px;
    			border-bottom: 1px solid @black;
    			padding: 0 15px;
    			span{
    				line-height: 52px;
    				font-size: @fs16;
    				&:last-child{
    					margin-right: 15px;
    				}
    			}
    			.icon_arrow{
					float: right;
					width: 10px;
					height: 15px;
					background: url(../../assets/img/arrow.png) no-repeat center center;
					background-size: 10px 15px;
					margin: 18px 0 0 0;
				}
    		}
    		.ipt_box{
    			border-bottom: 10px solid @black;
    			.ipt_row{
    				margin: 8px 3.625%;
    				label{
    					width: 150px;
    				}
    				input{
    					padding-left: 150px;
    					padding-right: 50px;
    				}
    				span{
    					position: absolute;
    					top: 18px;
    					right: 15px;
    					z-index: 1000;
    					color: @white;
    					font-size: @fs16;
    				}
    			}
    		}
			li{
				height: 42px;
				line-height: 42px;
				border-bottom: 1px solid @black;
				padding: 0 15px;
				span{
					font-size: @fs14;
					margin-right: 10px;
				}
			}
			.btn_box{
				padding: 15px 0; 
				border-bottom: 10px solid @black;
			}
			.tips{
				padding: 15px 15px 0 15px;
				h4{
					font-size: @fs16;
					margin-bottom: 10px;
				}
				p{
					font-size: @fs14;
					color: @blue;
					margin-bottom: 10px;
				}
			}
    	}
    }
}
/*ip6*/
@media (min-width:371px) and (max-width:410px) {
	#addMoney{
    	width: 100%;
    	padding-top: 50px*@ip6;
    	background: @deepblue;
    	.page_cont{
    		span{
				&.blue{
					color: @blue;
				}
				&.yellow{
					color: @yellow;
				}
				&.white{
					color: @white;
				}
			}
    		.money_box{
    			width: 100%;
    			height: 52px*@ip6;
    			border-bottom: 1px solid @black;
    			padding: 0 15px*@ip6;
    			span{
    				line-height: 52px*@ip6;
    				font-size: @fs16*@ip6;
    				&:last-child{
    					margin-right: 15px*@ip6;
    				}
    			}
    			.icon_arrow{
					float: right;
					width: 10px*@ip6;
					height: 15px*@ip6;
					background: url(../../assets/img/arrow.png) no-repeat center center;
					background-size: 10px*@ip6 15px*@ip6;
					margin: 18px*@ip6 0 0 0;
				}
    		}
    		.ipt_box{
    			border-bottom: 10px*@ip6 solid @black;
    			.ipt_row{
    				margin: 8px*@ip6 3.625%;
    				label{
    					width: 150px*@ip6;
    				}
    				input{
    					padding-left: 150px*@ip6;
    					padding-right: 50px*@ip6;
    				}
    				span{
    					position: absolute;
    					top: 18px*@ip6;
    					right: 15px*@ip6;
    					z-index: 1000;
    					color: @white;
    					font-size: @fs16*@ip6;
    				}
    			}
    		}
			li{
				height: 42px*@ip6;
				line-height: 42px*@ip6;
				border-bottom: 1px solid @black;
				padding: 0 15px*@ip6;
				span{
					font-size: @fs14*@ip6;
					margin-right: 10px*@ip6;
				}
			}
			.btn_box{
				padding: 15px*@ip6 0; 
				border-bottom: 10px*@ip6 solid @black;
			}
			.tips{
				padding: 15px*@ip6 15px*@ip6 0 15px*@ip6;
				h4{
					font-size: @fs16*@ip6;
					margin-bottom: 10px*@ip6;
				}
				p{
					font-size: @fs14*@ip6;
					color: @blue;
					margin-bottom: 10px*@ip6;
				}
			}
    	}
    }
}
/*ip5*/
@media(max-width:370px) {
	#addMoney{
    	width: 100%;
    	padding-top: 50px*@ip5;
    	background: @deepblue;
    	.page_cont{
    		span{
				&.blue{
					color: @blue;
				}
				&.yellow{
					color: @yellow;
				}
				&.white{
					color: @white;
				}
			}
    		.money_box{
    			width: 100%;
    			height: 52px*@ip5;
    			border-bottom: 1px solid @black;
    			padding: 0 15px*@ip5;
    			span{
    				line-height: 52px*@ip5;
    				font-size: @fs16*@ip5;
    				&:last-child{
    					margin-right: 15px*@ip5;
    				}
    			}
    			.icon_arrow{
					float: right;
					width: 10px*@ip5;
					height: 15px*@ip5;
					background: url(../../assets/img/arrow.png) no-repeat center center;
					background-size: 10px*@ip5 15px*@ip5;
					margin: 18px*@ip5 0 0 0;
				}
    		}
    		.ipt_box{
    			border-bottom: 10px*@ip5 solid @black;
    			.ipt_row{
    				margin: 8px*@ip5 3.625%;
    				label{
    					width: 150px*@ip5;
    				}
    				input{
    					padding-left: 150px*@ip5;
    					padding-right: 50px*@ip5;
    				}
    				span{
    					position: absolute;
    					top: 18px*@ip5;
    					right: 15px*@ip5;
    					z-index: 1000;
    					color: @white;
    					font-size: @fs16*@ip5;
    				}
    			}
    		}
			li{
				height: 42px*@ip5;
				line-height: 42px*@ip5;
				border-bottom: 1px solid @black;
				padding: 0 15px*@ip5;
				span{
					font-size: @fs14*@ip5;
					margin-right: 10px*@ip5;
				}
			}
			.btn_box{
				padding: 15px*@ip5 0; 
				border-bottom: 10px*@ip5 solid @black;
			}
			.tips{
				padding: 15px*@ip5 15px*@ip5 0 15px*@ip5;
				h4{
					font-size: @fs16*@ip5;
					margin-bottom: 10px*@ip5;
				}
				p{
					font-size: @fs14*@ip5;
					color: @blue;
					margin-bottom: 10px*@ip5;
				}
			}
    	}
    }
}
</style>