<template>
	<div id="account" class="bg">
		<topbar  title="我的"></topbar>
		<button id="refresh">客服</button>
		<div class="top_tips">
			<img src="../assets/img/head_img.png" class="fl" />
			<p class="fl ml10">欢迎你，<span id="userName">15182736808</span></p>
		</div>
		<div class="money_option mt10">
			<div class="money_option_row border_bottom">
				<p>余额：0.00元</p>
			</div>
			<div class="money_option_row">
				<button class="fl yellow">我要充值</button>
				<button class="fr blue">我要提现</button>
			</div>
		</div>
		<div class="list_option mt10">
			<ul>
				<li>
					<img src="../assets/img/money.png" />
					<span>资金明细</span>
					<img src="../assets/img/arrow.png" class="img_arrow"/>
					<span></span>
				</li>
				<li>
					<img src="../assets/img/relname.png" />
					<span>实名认证</span>
					<img src="../assets/img/arrow.png" class="img_arrow"/>
					<span>未认证</span>
				</li>
				<li>
					<img src="../assets/img/phone_bind.png" />
					<span>手机绑定</span>
					<img src="../assets/img/arrow.png" class="img_arrow"/>
					<span>修改</span>
				</li>
				<li>
					<img src="../assets/img/pwd.png" />
					<span>登录密码</span>
					<img src="../assets/img/arrow.png" class="img_arrow"/>
					<span>修改</span>
				</li>
			</ul>
		</div>
		<button class="btn mt10">退出登录</button>
	</div>
</template>

<script>
	import topbar from '../components/Topbar.vue'
	export default{
		name:'account',
		components:{topbar},
		mounted: function(){
			$("#account").css("height",window.screen.height + "px");
//			console.log(window.screen.height);
		}
	}
</script>

<style scoped lang="less">
	@import url("../assets/css/main.less");
	@import url("../assets/css/base.less");
	#account{
		width: 100%;
		padding-top: 50px;
		padding-bottom: 50px;
	}
	.bg{
		overflow: hidden;
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
				font-size: @fs14;
				color: #fff;
			}
		}
		.money_option{
			height: 101px;
			background: @deepblue;
			.money_option_row{
				height: 50px;
				padding: 0 15px;
				p{
					line-height: 50px;
					text-align: center;
					font-size: @fs16;
					color: @yellow;
				}
				button{
					width: 45%;
					height: 40px;
					border-radius: 20px;
					color: @black;
					font-size: @fs16;
					border: none;
					margin-top: 5px;
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
				margin: 0;
				padding: 0;
				li{
					height: 50px;
					line-height: 50px;
					border-bottom: 1px solid @black;
					font-size: @fs14; 
					padding: 0 15px;
					&:last-child{
						border: none;
					}
					img{
						float: left;
						width: 17px;
						height: 17px;
						margin: 15px 10px 0 0;
						&.img_arrow{
							float: right;
							width: 7px;
							height: 12px;
							margin: 20px 0 0 0;
						}
					}
					span{
						float: left;
						font-size: @fs14;
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
	#refresh {
		color:#fff;
		background-color: transparent;
		border: none;
		outline: none;
		position: fixed;
		z-index: 1000;
		right: 10%;
		top: 2%;
	}



</style>
