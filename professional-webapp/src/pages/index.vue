<template>
	<div id="index">
		<div class="order">
			<div class="title">
				<ul>
					<template></template>
					<li class="current">
						<span>国际期货</span>
					</li>
					<li>
						<span>国内期货</span>
					</li>
					<li>
						<span>添加外汇</span>
					</li>
				</ul>
			</div>
			<div class="cont">
				<table>
					<thead>
						<tr>
							<td></td>
							<td>名称</td>
							<td>合约代码</td>
							<td>最新</td>
							<td>现手</td>
							<td>买价</td>
							<td>卖价</td>
							<td>买量</td>
							<td>卖量</td>
							<td>成交量</td>
							<td>涨跌</td>
							<td>涨幅%</td>
							<td>持仓量</td>
							<td>开盘</td>
							<td>最高</td>
							<td>最低</td>
							<td>昨结算</td>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td><i class="ifont red">&#xe761;</i></td>
							<td>富时A50</td>
							<td>CL1705</td>
							<td class="red">46.81</td>
							<td>5</td>
							<td class="red">46.71</td>
							<td class="red">47.36</td>
							<td>15</td>
							<td>20</td>
							<td>65211</td>
							<td class="red">+0.24</td>
							<td class="red">+0.51%</td>
							<td class="red">430110</td>
							<td class="red">49.02</td>
							<td class="red">49.25</td>
							<td class="red">48.56</td>
							<td>46.90</td>
						</tr>
						<tr>
							<td><i class="ifont green">&#xe76a;</i></td>
							<td>富时A50</td>
							<td>CL1705</td>
							<td class="green">46.81</td>
							<td>5</td>
							<td class="green">46.71</td>
							<td class="green">47.36</td>
							<td>15</td>
							<td>20</td>
							<td>65211</td>
							<td class="green">+0.24</td>
							<td class="green">+0.51%</td>
							<td class="green">430110</td>
							<td class="green">49.02</td>
							<td class="green">49.25</td>
							<td class="green">48.56</td>
							<td>46.90</td>
						</tr>
						<tr>
							<td><i class="ifont red">&#xe761;</i></td>
							<td>富时A50</td>
							<td>CL1705</td>
							<td class="red">46.81</td>
							<td>5</td>
							<td class="red">46.71</td>
							<td class="red">47.36</td>
							<td>15</td>
							<td>20</td>
							<td>65211</td>
							<td class="red">+0.24</td>
							<td class="red">+0.51%</td>
							<td class="red">430110</td>
							<td class="red">49.02</td>
							<td class="red">49.25</td>
							<td class="red">48.56</td>
							<td>46.90</td>
						</tr>
						<tr>
							<td><i class="ifont green">&#xe76a;</i></td>
							<td>富时A50</td>
							<td>CL1705</td>
							<td class="green">46.81</td>
							<td>5</td>
							<td class="green">46.71</td>
							<td class="green">47.36</td>
							<td>15</td>
							<td>20</td>
							<td>65211</td>
							<td class="green">+0.24</td>
							<td class="green">+0.51%</td>
							<td class="green">430110</td>
							<td class="green">49.02</td>
							<td class="green">49.25</td>
							<td class="green">48.56</td>
							<td>46.90</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
		<div class="echarts_box">
			<div class="title">
				<span class="fl">美黄金</span>
				<span class="fl">CG1708</span>
				<div class="add fr">
					<i class="ifont fl">&#xe600;</i>
					<span class="fl">添加自选</span>
				</div>
			</div>
			<div id="echarts_f">
				
			</div>
			<div id="echarts_k">
				
			</div>
		</div>
	</div>
</template>

<script>
	export default{
		name:'index',
	}
</script>

<style lang="scss" scoped>
	@import "../assets/css/common.scss";
	.echarts_box{
		position: absolute;
		top: 50px;
		right: 0;
		width: 400px;
		overflow: hidden;
		.title{
			height: 40px;
			line-height: 40px;
			padding: 0 10px;
			background: $bottom_color;
			span{
				font-size: $fs16;
				color: $white;
				&:nth-child(2){
					margin-left: 10px;
				}
			}
			.add{
				.ifont{
					color: $yellow;
					font-size: $fs16;
				}
				span{
					font-size: $fs12;
					color: $yellow;
					margin-left: 5px;
				}
			}
		}
		#echarts_f, #echarts_k{
			width: 400px;
		}
	}
	.order{
		width: 100%;
		overflow: hidden;
		padding-right: 410px;
		box-sizing: border-box;
		.title{
			height: 35px;
			ul{
				li{
					float: left;
					width: 120px;
					height: 35px;
					line-height: 35px;
					text-align: center;
					overflow: hidden;
					background: $deepblue;
					border-bottom: 5px solid $black;
					margin-right: 5px;
					cursor: pointer;
					&:hover, &.current{
						border-color: $deepblue;
						span{
							color: $yellow;
						}
					}
					
				}
			}
		}
		.cont{
			table{
				td:first-child{
					width: 32px;
					text-align: center;
					.ifont{
						font-size: $fs12;
					}
				}
				thead{
					tr{
						height: 30px;
						font-size: $fs12;
						background: $bottom_color;
					}
				}
				tbody{
					tr{
						height: 40px;
					}
				}
				
			}
		}
	}


</style>