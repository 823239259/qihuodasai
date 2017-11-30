<template>
	<div id="stopMoney" v-show="show">
		<div class="bg"></div>
		<div class="stopMoney">
			<div class="title">
				<ul>
					<li class="current"><span>新增止损单</span></li>
					<li><span>新增止盈单</span></li>
				</ul>
				<i class="ifont icon_close">&#xe624;</i>
			</div>
			<div class="cont">
				<div class="row">
					<div class="fl">
						<label>合约名称:</label>
						<span>HSI,恒指期货</span>
					</div>
					<div class="fl">
						<label>最新:</label>
						<span>56.12</span>
					</div>
				</div>
				<div class="row">
					<div class="fl">
						<label>止损价:</label>
						<input type="text" class="ipt" />
					</div>
					<div class="fl">
						<input type="text" class="ipt" />
						<label>手数</label>
					</div>
				</div>
				<div class="row">
					<div class="fl">
						<label><i class="ifont">&#xe634;</i></label>
						<span>动态追踪，价格回撤幅度</span>
					</div>
					<div class="fl">
						<input type="text" class="ipt" />
						<label>HKD</label>
					</div>
				</div>
				<div class="row">
					<div class="fl">
						<label class="fl">止损委托价:</label>
						<div class="slt-box row_money_box fl">
							<input type="text" class="slt" disabled="disabled" selectVal="0" value="市价"/>
							<span class="tal-box"><span class="tal"></span></span>
							<div class="slt-list">
								<ul>
									<li selectVal="0">市价</li>
									<li selectVal="1">限价</li>
								</ul>
							</div>
						</div>
					</div>
				</div>
				<p>1.止损单在云端执行，软件关闭后扔然有效，云端自动确认结算单。</p>
				<p>2.止损单在行情不活跃或快速发送变化下，不保证成交价为指定价。</p>
				<p>3.止损单存在风险，云端系统、网络故障情况下失效等。</p>
				<div class="btn_box">
					<div class="fl">
						<button class="btn yellow">确定</button>
					</div>
					<div class="fl">
						<button class="btn blue">取消</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</template>

<script>
	export default{
		name: 'warning',
		data(){
			return{
				show: false,
				
			}
		},
		computed: {},
	}
</script>

<style lang="scss" scoped>
	@import "../../assets/css/common.scss";
	.stopMoney{
		width: 400px;
		height: 380px;
		overflow: hidden;
		background: $blue;
		border-radius: 5px;
		position: absolute;
		top: 50%;
		left: 50%;
		z-index: 100;
		margin: -190px 0 0 -200px;
	}
	.title{
		height: 40px;
		background: $bottom_color;
		position: relative;
		li{
			float: left;
			width: 200px;
			height: 40px;
			line-height: 40px;
			text-align: center;
			span{
				cursor: pointer;
				font-size: $fs16;
			}
			&:hover, &.current{
				span{
					color: $yellow;
				}
			}
			&:first-child{
				border-right: 1px solid $black;
			}
		}
		.icon_close{
			position: absolute;
			top: 14px;
			right: 10px;
			color: #7a8199;
			cursor: pointer;
		}
	}
	.cont{
		padding: 20px 10px 0 10px;
		.row{
			height: 30px;
			margin-bottom: 18px;
			div:first-child{
				width: 260px;
				label{
					width: 85px;
					text-align: right;
				}
			}
			label{
				display: inline-block;
				line-height: 30px;
			}
			span{
				color: $white;
				line-height: 30px;
			}
			.ipt{
				width: 78px;
				height: 26px;
				line-height: 26px;
				border: 1px solid #474c66;
				border-radius: 4px;
				color: $white;
				text-align: center;
			}
			.row_money_box{
				width: 80px;
				margin-left: 5px;
				.slt{
					width: 78px;
					padding-left: 0;
				}
				span{
					line-height: 54px;
				}
			}
		}
		p{
			font-size: $fs12;
			line-height: 22px;
		}
	}
	.btn_box{
		height: 60px;
		div{
			width: 50%;
			text-align: center;
		}
		button{
			width: 90px;
			height: 30px;
			line-height: 30px;
			margin-top: 15px;
		}
	}
</style>