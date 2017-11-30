<template>
		<div class="recharge">
			<h2>充值到平台</h2>
			<div class="recharge_top">
				<ul>
					<li>我的余额：</li>
					<li><span>{{accountMoney}}</span>元</li>
					<li>（充值后余额：<span>{{totalMoney}}</span>）元</li>
				</ul>
			</div>
			<div class="recharge_center">
				<div class="recharge_center_1">
					<div class="recharge_center_left">
						<ul>
							<li>充值金额：</li>
						</ul>
					</div>
					<div class="recharge_center_right">
						<ul>
							<li><input type="text"  v-model="recharge_money"/>&nbsp;元</li>
							<li><span>注意：</span>单次充值金额最低10元</li>
							<li><button class="btn" v-on:click="nextStep">下一步</button><i v-on:click="back">返回修改</i></li>
						</ul>
					</div>
				</div>
			</div>
			<div class="recharge_btm">
				<ul class="recharge_answer">
					<li>充值相关</li>
					<li>支付方式有哪些？</li>
					<li>目前平台支付方式有支付宝扫码、微信扫码支付、快捷支付、支付宝转账支付、支付宝转银行卡支付、网银转账支付六种，后期可能会增加其他方式，请您注意留意。</li>
				</ul>
				<div class="recharge_info">
					<p>充值提醒：</p>
					<ul>
						<li>
							<img src="../../../assets/images/recharge_icon1.png" alt="" />
							<p>填写并生成，您的充值订单</p>
						</li>
						<li>
							<img src="../../../assets/images/recharge_icon2.png" alt="" />
							<p>选择支付方式，按照订单进行转账</p>
						</li>
						<li>
							<img src="../../../assets/images/recharge_icon3.png" alt="" />
							<p>完成转账后，最快5分钟内到账</p>
						</li>
					</ul>
				</div>
			</div>
			<p class="p_center">投资有风险，入市需谨慎</p>
		</div>
</template>

<script>
	import pro from "../../../assets/js/common.js"
	export default{
		name : "recharge",
		data(){
			return{
				recharge_money:'',
				accountMoney:'',
				totalMoney:0.00,
				backto:"",
				rechargeMoney:''
			}
		},
		watch: {
			recharge_money: function(){
				if(this.recharge_money == '' || this.recharge_money == NaN){
					this.totalMoney = this.accountMoney;
				}else{
					var str = this.recharge_money.toString().split('.');
					if(str[1] && str[1].length > 1){
						this.money = parseFloat(str[0]+ '.'+str[1].substring(0,2));
					}
					this.totalMoney = (Number(this.accountMoney) + Number(this.recharge_money)).toFixed(2);
				}
			}
		},
		methods:{
			back:function(){
				if(this.backto != undefined){
					this.$router.push({path:'/account_survey'});
				}else {
					this.$router.push({path:'/confirmPayment',query:{"payMoney1":this.rechargeMoney}});
				}
			},
			nextStep:function(){
				if(this.recharge_money == ''){
					layer.msg("请输入充值金额",{time:2000});
				}else if(this.recharge_money < 10 ){
					layer.msg("充值金额需要大于10元哦~",{time:2000});
				}else {
					this.$router.push({path:'/payWays',query:{username:JSON.parse(localStorage.user).username,money:this.recharge_money,accountMoney:this.accountMoney}});
				}
			}
		},
		mounted: function(){
			//初始化高度
			var h = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;
			var _h = h
			var contH = $(".recharge").height();
			if(contH > _h){
				$(".recharge").height(_h);
			}
			$(window).resize(function(){
				var h = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;
				var _h = h
				if(contH > _h){
					$(".recharge").height(_h);
				}
			});
			//获取用户资金
			this.accountMoney = this.$route.query.accountMoney;
			this.totalMoney = this.accountMoney;
			this.backto = this.$route.query.backhere;
			this.rechargeMoney = this.$route.query.rechargeMoney;
			this.recharge_money = this.rechargeMoney;
		},
		activated:function(){
			this.accountMoney = this.$route.query.accountMoney;
			this.backto = this.$route.query.backhere;
			this.rechargeMoney = this.$route.query.rechargeMoney;
			this.recharge_money = this.rechargeMoney;
		},
	}
</script>

<style lang="scss" scoped type="text/css">
	@import "../../../assets/css/common.scss";
	.recharge{
		position: relative;
		/*left: 170px;*/
		width: 1000px;
		margin: auto;
		h2{		
			height: 40px;
			color: $white;
			width: 100%;
			text-align: left;
			background-color: $bottom_color;
			line-height: 40px;
			font-size: $fs14;
			text-indent: 10px;
		}
		.recharge_top{
			background-color: $blue;
			padding-left: 10px;
			line-height: 60px;
			margin: auto;
			width: 100%;
			height: 60px;
			border-bottom: 1px solid $bottom_color;
			li{
				float: left;
				&:nth-child(1){
					color: $lightblue;
					font-style: $fs16;
				}
				&:nth-child(2){
					color: $yellow;
					span{
						font-size: $fs20;
						font-weight: 800;
					}
				}
				&:nth-child(3){
					font-size: $fs14;
					color:$lightblue;
					span{
						color: $yellow;
					}
				}
			}
		}
		.recharge_center{
			background-color: $blue;
			margin: auto;
			width: 100%;
			height: 200px;
			.recharge_center_1{
				float: left;
				width: 100%;
				height: 200px;
				.recharge_center_left{
					width: 70px;
					float: left;
					text-align: right;
					height: 200px;
					li{
						text-indent: 10px;
						width: 80px;
						height: 60px;
						line-height: 60px;
						color: $white;
						font-size: $fs14;
					}
					
				}
				.recharge_center_right{
					padding-left: 10px;
					width: 920px;
					height: 200px;
					float: left;
					li{
						height: 60px;
						line-height: 60px;
						&:nth-child(2){
							font-size: $fs12;
							span{
								color: $yellow;
							}
						}
						input{
							width: 200px;
							height: 40px;
							border: 1px solid $bottom_color;
							border-radius: 5px;
							color: $white;
							font-size: $fs20;
							&:hover{
								border-color: $yellow;
							}
						}
						.btn{
							width: 120px;
							height: 40px;
							border-radius: 5px;
							color: black;
							background-color: $yellow;
						}
						i{
							font-size: $fs14;
							color: $lightblue;
							padding-left: 10px;
							&:hover{
								color: $yellow;
							}
						}
					}
				}
			}
		}
		.recharge_btm{
			margin-top: 5px;
			background-color: $blue;
			height: 320px;
			width: 100%;
			.recharge_answer{
				border-bottom: 1px solid $bottom_color;
				height: 140px;
				li{
					font-size: $fs14;
					width: 100%;
					padding-left: 10px;
					&:nth-child(1){
						height: 30px;
						line-height: 30px;
						color: $white;
						background-color: $bottom_color;
					}
					&:nth-child(2){
						height: 30px;
						line-height: 30px;
					}
					&:nth-child(3){
						height: 80px;
						color: $white;
					}
				}
			}
			.recharge_info{
				height: 180px;
				p{
					height: 40px;
					line-height: 40px;
					text-indent: 10px;
				}
				ul{
					padding-top: 20px;
					height: 140px;
					display: flex;
					justify-content: space-around;
					li{
						float: left;
						text-align: center;
						p{
							color: $white;
						}
					}
				}
			}
		}
		.p_center{
			height: 40px;
			text-align: center;
			line-height: 40px;
			color: $lightblue;
			margin-top: 5px;
			background-color: $bottom_color;
		}
	}
</style>