<template>
	<div id="trade_details">
		<table>
			<thead>
				<tr>
					<td>合约名称</td>
					<td>状态</td>
					<td>多空</td>
					<td>类型</td>
					<td>条件</td>
					<td>下单</td>
					<td>有效日期</td>
					<td>下单时间</td>
				</tr>
			</thead>
			<tbody>
				<template></template>
				<tr>
					<td>合约名称</td>
					<td>状态</td>
					<td>多空</td>
					<td>类型</td>
					<td>条件</td>
					<td>下单</td>
					<td>有效日期</td>
					<td>下单时间</td>
				</tr>
			</tbody>
		</table>
		<div class="tools">
			<button class="btn blue">暂停</button>
			<button class="btn blue">修改</button>
			<button class="btn blue">删除</button>
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
	#trade_details{
		height: 151px;
		overflow: auto;
	}
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
		position: absolute;
		bottom: 10px;
		left: 10px;
		z-index: 2;
		.btn{
			width: 90px;
			height: 30px;
			line-height: 30px;
		}
	}
	@media only screen and (min-width: 1280px) and (max-width: 1366px) {
		#trade_details{
			width: 635px;
			td{
				font-size: $fs12;
			}
		}
	}
</style>