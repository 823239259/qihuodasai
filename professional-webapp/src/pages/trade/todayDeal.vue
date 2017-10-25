<template>
	<div id="trade_details">
		<table>
			<thead>
				<tr>
					<td>合约名称</td>
					<td>买卖</td>
					<td>成交价</td>
					<td>成交量</td>
					<td>成交时间</td>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>当日成交</td>
					<td class="red">多</td>
					<td>1</td>
					<td>25289</td>
					<td class="red">+100.00：USD</td>
				</tr>
			</tbody>
		</table>
		<div class="tools">
			<button class="btn blue">全部平仓</button>
			<button class="btn blue">平仓</button>
			<button class="btn blue">反手</button>
			<button class="btn blue">止损止盈</button>
		</div>
	</div>
</template>

<script>
	import { mapMutations,mapActions } from 'vuex'
	export default{
		name: 'trade_details',
	}
</script>

<style lang="scss" scoped>
	@import "../../assets/css/common.scss";
	table{
		thead tr{
			height: 30px;
			background: $bottom_color;
		}
		td{
			padding: 0 10px;
		}
		tbody tr{
			height: 40px;
			border-bottom: 1px solid $bottom_color;
		}
	}
	.tools{
		margin: 15px 0 0 10px;
		.btn{
			width: 90px;
			height: 30px;
			line-height: 30px;
		}
	}
</style>