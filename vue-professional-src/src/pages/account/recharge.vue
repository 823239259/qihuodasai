<template>
	<div id="recharge">
		<tipsDialog :msg="msgTips"></tipsDialog>
		<topbar title="我要充值"></topbar>
		<back></back>
		<cs title="客服"></cs>
		<div class="page_cont">
			<p class="money_before">余额：<span>{{balance}}</span>元</p>
			<div class="ipt_row">
				<label for="money">充值金额</label>
				<input type="number" id="money" placeholder="请输入充值金额" v-model.trim="money" @keyup="testMoney" />
			</div>
			<btn name="立即充值" @tap.native="toChoosePay"></btn>
			<p class="money_after"><span>充值后余额：</span><span class="white">{{totalMoney}}元</span></p>
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
		name:'recharge',
		components: {topbar, back, cs, btn, tipsDialog},
		data(){
			return {
				isShow: false,
				msg: '',
				balance: 0.00,
				money: '',
				totalMoney: 0.00,
				floatReg: /^[0-9]+([.][0-9]+)?$/,
				userInfo: ''
			}
		},
		computed: {
			msgTips: function(){
				return this.msg;
			},
			PATH: function(){
				return this.$store.getters.PATH;
			}
		},
		methods: {
			testMoney: function(){
				if(this.money != '' && this.floatReg.test(this.money) ==  false){
					this.$children[0].isShow = true;
					this.msg = '充值金额格式错误';
				}
			},
			toChoosePay: function(){
				if(this.money == ''){
					this.$children[0].isShow = true;
					this.msg = '请输入充值金额';
				}else{
					window.location.href = 'http://www.dktai.cn/app/appPayinfo?mobile='+ this.userInfo.username +'&money='+ this.money;
				}
				
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
							this.totalMoney = this.balance;
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
			}
		},
		watch: {
			money: function(){
				if(this.money == '' || this.money == NaN){
					this.totalMoney = this.balance;
				}else{
					var str = this.money.toString().split('.');
					if(str[1] && str[1].length > 1){
						this.money = parseFloat(str[0]+ '.'+str[1].substring(0,2));
					}
					this.totalMoney = (Number(this.balance) + Number(this.money)).toFixed(2);
					
				}
			}
		},
		mounted: function(){
			//页面高度计算
			$("#recharge").css("height", window.screen.height - 20 + 'px');
		},
		activated: function(){
			this.userInfo = JSON.parse(localStorage.user);
			//获取用户账户信息
			this.getUserMsg();
		}
	}
</script>

<style scoped lang="less">
@import url("../../assets/css/base.less");
/*ip6p及以上*/
@media (min-width:411px) {
    #recharge{
    	width: 100%;
    	overflow: hidden;
    	padding-top: 65px;
    	background: @deepblue;
    	.ipt_row{
    		label{
    			width: 140px;
    		}
    		input{
    			padding: 10px 0 10px 145px;
    		}
    	}
		.money_before{
			width: 100%;
			height: 47px;
			line-height: 42px;
			text-align: center;
			margin-bottom: 15px;
			color: @yellow;
			font-size: @fs18;
			border-bottom: 5px solid @black;
		}
		.money_after{
			width: 100%;
			text-align: center;
			margin-top: 36px;
			font-size: @fs14;
			color: @blue;
			span.white{
				color: @white;
			}
		}
    }
}
/*ip6*/
@media (min-width:371px) and (max-width:410px) {
	#recharge{
    	width: 100%;
    	overflow: hidden;
    	padding-top: 65px*@ip6;
    	background: @deepblue;
    	.ipt_row{
    		label{
    			width: 140px*@ip6;
    		}
    		input{
    			padding: 10px*@ip6 0 10px*@ip6 145px*@ip6;
    		}
    	}
		.money_before{
			width: 100%;
			height: 47px*@ip6;
			line-height: 42px*@ip6;
			text-align: center;
			margin-bottom: 15px*@ip6;
			color: @yellow;
			font-size: @fs18*@ip6;
			border-bottom: 5px*@ip6 solid @black;
		}
		.money_after{
			width: 100%;
			text-align: center;
			margin-top: 36px*@ip6;
			font-size: @fs14*@ip6;
			color: @blue;
			span.white{
				color: @white;
			}
		}
    }
}
/*ip5*/
@media(max-width:370px) {
	#recharge{
    	width: 100%;
    	overflow: hidden;
    	padding-top: 65px*@ip5;
    	background: @deepblue;
    	.ipt_row{
    		label{
    			width: 140px*@ip5;
    		}
    		input{
    			padding: 10px*@ip5 0 10px*@ip5 145px*@ip5;
    		}
    	}
		.money_before{
			width: 100%;
			height: 47px*@ip5;
			line-height: 42px*@ip5;
			text-align: center;
			margin-bottom: 15px*@ip5;
			color: @yellow;
			font-size: @fs18*@ip5;
			border-bottom: 5px*@ip5 solid @black;
		}
		.money_after{
			width: 100%;
			text-align: center;
			margin-top: 36px*@ip5;
			font-size: @fs14*@ip5;
			color: @blue;
			span.white{
				color: @white;
			}
		}
    }
}
</style>