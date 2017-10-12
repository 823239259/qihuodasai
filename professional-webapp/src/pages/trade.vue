<template>
	<div id="trade">
		<div class="quote">
			<div class="title">
				<ul>
					<li class="current">
						<span>自选</span>
					</li>
					<li>
						<span>行情</span>
					</li>
				</ul>
			</div>
			<div class="cont">
				<table>
					<thead>
						<tr>
							<td>合约代码</td>
							<td>最新/涨幅%</td>
							<td>成交量</td>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>
								<b>国际原油</b>
								<span>CL1705</span>
							</td>
							<td>
								<div class="fl">
									<span>46.94</span>
									<span>+0.24%</span>
								</div>
								<i class="ifont red fl">&#xe761;</i>
							</td>
							<td>10086</td>
						</tr>
						<tr>
							<td>
								<b>国际原油</b>
								<span>CL1705</span>
							</td>
							<td>
								<div class="fl">
									<span>46.94</span>
									<span>+0.24%</span>
								</div>
								<i class="ifont red fl">&#xe761;</i>
							</td>
							<td>10086</td>
						</tr>
						<tr>
							<td>
								<b>国际原油</b>
								<span>CL1705</span>
							</td>
							<td>
								<div class="fl">
									<span>46.94</span>
									<span>+0.24%</span>
								</div>
								<i class="ifont red fl">&#xe761;</i>
							</td>
							<td>10086</td>
						</tr>
						<tr>
							<td>
								<b>国际原油</b>
								<span>CL1705</span>
							</td>
							<td>
								<div class="fl">
									<span>46.94</span>
									<span>+0.24%</span>
								</div>
								<i class="ifont red fl">&#xe761;</i>
							</td>
							<td>10086</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
		<div class="echarts_box">
			
		</div>
		<div class="details">
			
		</div>
	</div>
</template>

<script>
	export default{
		name:'trade',
	}
</script>

<style scoped lang="scss">
	@import "../assets/css/common.scss";
	.quote{
		float: left;
		width: 250px;
		overflow: hidden;
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
					cursor: pointer;
					&:hover, &.current{
						border-color: $deepblue;
						span{
							color: $yellow;
						}
					}
					&:first-child{
						margin-right: 10px;
					}
				}
			}
		}
		.cont{
			width: 100%;
			table{
				td:first-child{
					padding-left: 10px;
				}
				td:last-child{
					padding-right: 10px;
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
						height: 50px;
						td{
							&:first-child{
								span{
									font-size: $fs12;
								}
							}
						}
						b, span{
							display: block;
							margin-top: 5px;
						}
						b{
							color: $white;
						}
						.ifont{
							margin: 12px 0 0 5px;
						}
					}
				}
			}
		}
	}
</style>