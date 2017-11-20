<template>
	<div id="recharge">
		<div class="bg"></div>
		<div class="reaharge_title">
			<img src="../../../assets/images/recharge_icon4.png" alt="" />
			<i>盈透支付</i>
		</div>
		<div class="recharge">
			<h2>充值到平台</h2>
			<div class="recharge_top">
				<ul>
					<li>我的余额：</li>
					<li><span>0.00</span>元</li>
					<li>（充值后余额：<span>0.00</span>）元</li>
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
							<li><input type="text"  v-model="recharge_money"/></li>
							<li><span>注意：</span>单次充值金额最低10元</li>
							<li><button class="btn" >下一步</button></li>
						</ul>
					</div>
				</div>
				<div class="recharge_center_2">
					<p>支付方式有哪些？</p>
					<p>目前平台支付方式有支付宝扫码、微信扫码支付、快捷支付、支付宝转账支付、支付宝转银行卡支付、网银转账支付六种，后期可能会增加其他方式，请您注意留意。</p>
				</div>
			</div>
			<div class="recharge_btm">
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
			<h3>投资有风险，入市需谨慎</h3>
		</div>
	</div>
</template>

<script>
	export default{
		name : "recharge",
		data(){
			return{
				recharge_money:''
			}
		},
		mounted: function(){
			//初始化高度
			var h = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;
			var _h = h - 80 - 47;
			var contH = $(".recharge").height();
			if(contH > _h){
				$(".recharge").height(_h);
			}
			$(window).resize(function(){
				var h = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;
				var _h = h - 80 - 47;
				if(contH > _h){
					$(".recharge").height(_h);
				}
			});
		}
	}
</script>

<style lang="scss" scoped type="text/css">
	@import "../../../assets/css/common.scss";
	#recharge{
		width: 100%;
		height: 780px;
	}
	.bg{
		background: url(../../../assets/images/recharge_bak.png)  no-repeat;
		opacity:1;
		vertical-align:middle;
	}
	.reaharge_title{
		position: fixed;
		top:0;
		width: 100%;
		height: 60px;
		background-color:#284e95;
		z-index: 120;
		i{
			color: $white;
			font-size: $fs20;
		}
		img{
			margin-left: 400px;
			margin-top: 10px;
			width: 40px;
			height: 40px;
			line-height: 60px;
			display: inline-block;
		}
	}
	.recharge{
		overflow-y: scroll;
		float: left;
		border-radius: 10px;
		background-color: #FFFFFF;
		position: absolute;
		left: 50%;
		top: 50%;
		width: 1000px;
		height: 700px;
		margin: auto;
		margin: -350px 0 0 -500px;
		z-index: 120;
		h2{		
			border-radius: 10px 10px 0 0 ;
			height: 50px;
			color: #006699;
			width: 100%;
			text-align: center;
			background-color: #e5e9f2;
			line-height: 50px;
			font-weight: 800;
			font-size: $fs20;
		}
		.recharge_top{
			line-height: 60px;
			margin: auto;
			width: 95%;
			height: 60px;
			border-bottom: 1px solid  #e5e9f2;
			li{
				float: left;
				&:nth-child(1){
					color: #ff4c4c;
					font-style: $fs16;
				}
				&:nth-child(2){
					color: #ff4c4c;
					span{
						font-size: $fs20;
						font-weight: 800;
					}
				}
				&:nth-child(3){
					font-size: $fs14;
					color: #999999;
					span{
						color: #ff4c4c;
					}
				}
			}
		}
		.recharge_center{
			margin: auto;
			width: 95%;
			height: 360px;
			border-bottom: 1px solid  #e5e9f2;
			.recharge_center_1{
				float: left;
				width: 100%;
				height: 230px;
				border-bottom: 1px solid  #e5e9f2;
				.recharge_center_left{
					width: 20%;
					float: left;
					text-align: right;
					height: 230px;
					li{
						height: 50px;
						line-height: 50px;
						color: black;
						font-size: $fs16;
						margin-top: 10px;
					}
					
				}
				.recharge_center_right{
					width: 80%;
					height: 230px;
					float: left;
					li{
						height: 50px;
						line-height: 50px;
						margin-top: 10px;
						&:nth-child(2){
							span{
								color: #ff4c4c;
							}
						}
					}
					input{
						width: 300px;
						height: 50px;
						border: 1px solid #e5e9f2;
						border-radius: 5px;
						color: #ff4c4c;
						font-size: $fs20;
					}
					.btn{
						width: 150px;
						height: 50px;
						border-radius: 5px;
						color: $white;
						background-color: #ff9500;
					}
				}
			}
			.recharge_center_2{
				float: left;
				width: 100%;
				height: 130px;
				p{
					width:100%; 
					&:nth-child(1){
						font-size: $fs16;
						height: 50px;
						line-height: 50px;
					}
					&:nth-child(2){
						font-size: $fs14;
						height: 80px;
					}
				}
			}
		}
		.recharge_btm{
			height: 190px;
			width: 95%;
			margin: auto;
			p{
				height: 70px;
				line-height: 70px;
				color: black;
			}
			ul{
				display: flex;
				justify-content: space-around;
				li{
					float: left;
					text-align: center;
					p{
						color: #666666;
					}
				}
			}
		}
		h3{
			border-radius: 0 0 10px 10px ;
			height: 40px;
			width: 100%;
			text-align: center;
			background-color: #e5e9f2;
			line-height: 40px;
			font-size: $fs16;
		}
	}
</style>