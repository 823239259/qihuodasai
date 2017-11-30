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
						<label>合约名称：</label>
						<span>HSI,恒指期货</span>
					</div>
					<div class="fl">
						<label>最新：</label>
						<span>56.12</span>
					</div>
				</div>
				<div class="row">
					<div class="fl">
						<label>止损价：</label>
						<input type="text" />
					</div>
					<div class="fl">
						<input type="text" />
						<label>手数</label>
					</div>
				</div>
				<div class="row">
					<div class="fl">
						<label>止损价：</label>
						<input type="text" />
					</div>
					<div class="fl">
						<input type="text" />
						<label>手数</label>
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
		computed: {
			
		},
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
</style>