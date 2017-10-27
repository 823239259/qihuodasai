<template>
	<div id="trade_details">
		<div class="condition">
			<span class="current">今天</span>
			<span>7天</span>
			<span>30天</span>
			<em>起始时间</em>
			<div class="time">
				<input type="text" class="fl date" />
				<i class="ifont fl">&#xe690;</i>
				<input type="text" class="fr date" />
			</div>
		</div>
		<table>
			<thead>
				<tr>
					<td>序号</td>
					<td>合约代码</td>
					<td>交易所</td>
					<td>币种</td>
					<td>买卖</td>
					<td>成交价</td>
					<td>成交量</td>
					<td>手续费</td>
					<td>成交时间</td>
				</tr>
			</thead>
			<tbody>
				<template ></template>
				<tr>
					<td>历史成交</td>
					<td class="red">多</td>
					<td>1</td>
					<td>25289</td>
					<td class="red">+100.00：USD</td>
				</tr>
			</tbody>
		</table>
	</div>
</template>

<script>
	import { mapMutations,mapActions } from 'vuex'
	export default{
		name: 'trade_details',
		mounted: function(){
			//调用日历插件
			$(".date").click(function(){
				console.log(1111);
				WdatePicker();
			});
		}
	}
</script>

<style lang="scss" scoped>
	@import "../../assets/css/common.scss";
	.condition{
		height: 60px;
		span, em{
			float: left;
			display: inline-block;
			height: 60px;
			line-height: 60px;
			margin: 0 10px;
		}
		span{
			cursor: pointer;
			&:hover, &.current{
				color: $yellow;
			}
		}
		.time{
			float: left;
			width: 270px;
			height: 30px;
			overflow: hidden;
			border: 1px solid $lightblue;
			margin: 15px 0;
			border-radius: 4px;
			input{
				width: 109px;
				height: 26px;
				line-height: 26px;
				padding: 0 5px;
				color: $white;
			}
			.ifont{
				width: 30px;
				height: 28px;
				line-height: 28px;
				text-align: center;
				background: $lightblue;
				font-size: $fs18;
				color: $blue;
			}
		}
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
</style>