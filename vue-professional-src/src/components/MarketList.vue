<template>
	<div id="marketlist">
		<ul>
			<li>
				<ol>
					<li class="fl">合约名称</li>
					<li class="fl">最新价</li>
					<li class="fl">成交量</li>
					<li class="fl">涨跌幅</li>
				</ol>
			</li>
			<template v-for="key in marketlist">
				<li>
					<ol>
						<li class="fl">合约名称</li>
						<li class="fl">最新价</li>
						<li class="fl">成交量</li>
						<li class="fl">涨跌幅</li>
					</ol>
				</li>
			</template>
		</ul>
	</div>
</template>

<script>
	export default{
		name:'marketlist'
	}
</script>

<style scoped lang="less">
@import url("../assets/css/main.less");

</style>
