<template>
	<div id="openDetail_particulars">
		<div class="bg"></div>
		<div class="openDetail_particulars">
			<div class="title">
				<span>结算明细</span>
				<i class="ifont">&#xe624;</i>
			</div>
			<div class="top">
				<p>结算金额明细</p>
				<ul>
					<li><span>6415154.10元</span>（结算金额）= <span>3000元</span>（操盘保证金）+<span>0.00元</span>（追加保证金）+<span>261351.00元</span>（交易盈亏）-<span>1315.00元</span>（手续费）</li>
					<li><span>注意：</span>交易书续费=合约手续费x交易手数</li>
				</ul>
				<p>交易手数明细</p>
				<table>
					<thead>
						<tr>
							<td>合约名称</td>
							<td>富时A50</td>
							<td>恒指期货</td>
							<td>国际原油</td>
							<td>迷你指数</td>
							<td></td>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>交易手数</td>
							<td>10</td>
							<td>10</td>
							<td>10</td>
							<td>10</td>
							<td></td>
						</tr>
					</tbody>
				</table>
			</div>
			<div class="center">
				<p>成交记录</p>
				<table>
					<thead>
						<tr>
							<td>序号</td>
							<td>成交时间</td>
							<td>交易账号</td>
							<td>币种</td>
							<td>交易所</td>
							<td>品种</td>
							<td>买</td>
							<td>卖</td>
							<td>成交价</td>
							<td>手续费</td>
							<td>平仓盈亏</td>
							<td>平仓手数</td>
							<td>开仓手数</td>
							<td>类型</td>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>1</td>
							<td>2017-07-13 16:52:12</td>
							<td>da23513</td>
							<td>usd</td>
							<td>hkex</td>
							<td>hsi1707</td>
							<td>-</td>
							<td>1</td>
							<td>21515.121</td>
							<td>22.22</td>
							<td>55.00</td>
							<td>0</td>
							<td>10</td>
							<td>强制开仓</td>
						</tr>
						<tr>
							<td>1</td>
							<td>2017-07-13 16:52:12</td>
							<td>da23513</td>
							<td>usd</td>
							<td>hkex</td>
							<td>hsi1707</td>
							<td>1</td>
							<td>-</td>
							<td>21515.121</td>
							<td>22.22</td>
							<td>55.00</td>
							<td>0</td>
							<td>10</td>
							<td>强制开仓</td>
						</tr>
						<tr>
							<td>1</td>
							<td>2017-07-13 16:52:12</td>
							<td>da23513</td>
							<td>usd</td>
							<td>hkex</td>
							<td>hsi1707</td>
							<td>-</td>
							<td>1</td>
							<td>21515.121</td>
							<td>22.22</td>
							<td>55.00</td>
							<td>10</td>
							<td>0</td>
							<td>强制开仓</td>
						</tr>
						<tr>
							<td>1</td>
							<td>2017-07-13 16:52:12</td>
							<td>da23513</td>
							<td>usd</td>
							<td>hkex</td>
							<td>hsi1707</td>
							<td>-</td>
							<td>1</td>
							<td>21515.121</td>
							<td>22.22</td>
							<td>55.00</td>
							<td>10</td>
							<td>0</td>
							<td>强制开仓</td>
						</tr>
						<tr>
							<td>1</td>
							<td>2017-07-13 16:52:12</td>
							<td>da23513</td>
							<td>usd</td>
							<td>hkex</td>
							<td>hsi1707</td>
							<td>1</td>
							<td>-</td>
							<td>21515.121</td>
							<td>22.22</td>
							<td>55.00</td>
							<td>0</td>
							<td>10</td>
							<td>强制开仓</td>
						</tr>
					</tbody>
				</table>
			</div>
			<div class="btm">
					<span>上一页</span>
					<span>1</span>
					<span>2</span>
					<span>3</span>
					<span>4</span>
					<span>5</span>
					<span>下一页</span>
				</div>
		</div>
	</div>
</template>

<script>
	export default {
		name : "account_openDetail_particulars"
	}
</script>

<style lang="scss" scoped type="text/css">
@import "../../assets/css/common.scss";
	.openDetail_particulars {
		position: relative;
		/*top: 200px;*/
		left: 25%;
		z-index: 100;
		width: 1000px;
		background-color: $blue;
		overflow:visible;
		border-radius: 10px;
	}
	.title {
		height: 40px;
		background-color: $bottom_color;
		line-height: 40px;
		color: $white;
		text-align: center;
		border-radius: 10px;
	}
	.ifont {
		font-size: $fs16;
		color: #7a8199;
		float: right;
		margin-right: 5px;
	}
	.top {
		p {
			width: 100%;
			height: 40px;
			background-color: $deepblue;
			line-height: 40px;
			text-indent: 10px;
		}
		ul {
			border-top: 1px solid $bottom_color;
			height: 70px;
			text-indent: 10px;
			li {
				&:nth-child(1){
					margin-top: 15px;
					span {
						color:$white;
						font-weight: 600;
						margin: 0 5px;
					}
				}
				&:nth-child(2){
					margin-top: 15px;
					font-size: $fs12;
					span {
						color: $yellow;
					}
				}
			}
		}

	}
	.center {
		p {
			width: 100%;
			height: 40px;
			line-height: 40px;
			background-color: $deepblue;
			text-indent: 10px;
		}
	}
	table {
		border-top: 1px solid $bottom_color;
		text-align: center;
		tr {
			height: 40px;
			border: 1px solid $bottom_color;
			width: 100px;
			td {
				border: 1px solid $bottom_color;
				width: 100px;
			}
		}
	}
	.btm {
		height: 60px;
		text-align: right;
		line-height: 60px;
		span {
			margin-right: 20px;
			&:hover {
				color: $yellow;
			}
		}
	}
</style>