<template>
	<div id="trade_details">
		资金详情
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