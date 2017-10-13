<template>
	<div id="warning">
		<div class="bg"></div>
		<div class="warning">
			<div class="title">
				<h3>警告！</h3>
				<i class="ifont">&#xe624;</i>
			</div>
			<div class="cont">
				<p class="tips">网络名称：服务发生中断</p>
				<div class="details">
					<p><i class="ifont">&#xe624;</i><b>交易服务：</b><span class="red">已断开</span></p>
					<p><i class="ifont ifont_green">&#xe62a;</i><b>行情服务：</b><span>正常</span></p>
				</div>
				<p class="status"><b>已断开，<span class="red">5s</span>后自动重连</b><b class="red connect">立即重连</b></p>
			</div>
		</div>
	</div>
</template>

<script>
	export default{
		name: 'warning',
	}
</script>

<style lang="scss" scoped>
	@import "../../assets/css/common.scss";
	.warning{
		width: 400px;
		height: 240px;
		overflow: hidden;
		background: $white;
		border-radius: 10px;
		position: absolute;
		top: 50%;
		left: 50%;
		z-index: 100;
		margin: -120px 0 0 -200px;
	}
	.title{
		height: 40px;
		line-height: 40px;
		text-align: center;
		background: $red;
		position: relative;
		h3{
			font-size: $fs16;
			color: $white;
		}
		i{
			position: absolute;
			top: 2px;
			right: 12px;
			color: $white;
			cursor: pointer;
		}
	}
	.cont{
		.tips{
			height: 40px;
			line-height: 40px;
			text-align: center;
			color: #292a33;
		}
		.details{
			height: 110px;
			padding: 30px 0 0 110px;
			border-top: 1px solid #e6e6e6;
			border-bottom: 1px solid #e6e6e6;
			p{
				font-size: $fs16;
				margin-bottom: 20px;
				b{
					color: #525566;
				}
				.ifont{
					margin-right: 10px;
					color: $red;
				}
				.ifont_green{
					color: $green;
					font-weight: bold;
					font-size: $fs18;
					margin-right: 7px;
				}
			}
		}
		span{
			color: $green;
			&.red{
				color: $red;
			}
		}
		.status{
			height: 50px;
			line-height: 50px;
			b{
				display: inline-block;
				width: 50%;
				text-align: center;
				color: #525566;
				&.red{
					color: $red;
				}
			}
			.connect{
				cursor: pointer;
			}
		}
	}
</style>