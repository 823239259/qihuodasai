<template>
	<div id="index">
		<div class="order">
			<div class="title">
				<ul>
					<template></template>
					<li>
						<span>国际期货</span>
					</li>
					<li>
						<span>国际期货</span>
					</li>
					<li>
						<span>国际期货</span>
					</li>
				</ul>
			</div>
			<div class="cont">
				<table>
					<thead>
						<tr>
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
							<td>
								<i class="ifont red">&#xe761;</i>
								<span>富时A50</span>
							</td>
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
					</tbody>
				</table>
			</div>
		</div>
		<div class="echarts_box">
			<div class="title">
				<span>美黄金</span>
				<span>CG1708</span>
				<div>
					<i class="ifont">&#xe600;</i>
					<span>添加自选</span>
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

<style scoped lang="scss">
@import url("../assets/css/common.scss");
#index{
	
}


</style>