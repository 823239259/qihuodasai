<template>
	<div id="trade_details">
		<table>
			<thead>
				<tr>
					<td>币种</td>
					<td>昨结存</td>
					<td>今权益</td>
					<td>今可用</td>
					<td>保证金</td>
					<td>冻结资金</td>
					<td>逐笔浮盈</td>
					<td>平仓盈亏</td>
					<td>入金</td>
					<td>出金</td>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>美元</td>
					<td>昨结存</td>
					<td>今权益</td>
					<td>今可用</td>
					<td>保证金</td>
					<td>冻结资金</td>
					<td>逐笔浮盈</td>
					<td>平仓盈亏</td>
					<td>入金</td>
					<td>出金</td>
				</tr>
			</tbody>
		</table>
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