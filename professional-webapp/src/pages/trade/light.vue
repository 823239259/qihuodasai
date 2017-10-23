<template>
	<div id="echarts">
		<div id="light"></div>
	</div>
</template>

<script>
	import { mapMutations,mapActions } from 'vuex'
	export default{
		name: 'echarts',
		data(){
			return {
				id:'light'
			}
		},
		methods:{
			...mapMutations([
				'drawlight',
				'setlightDate'
			])
		},
		mounted:function(){
			var h = this.$parent.chartHeight;
			$("#light").height(h*0.98);
			this.$store.state.isshow.islightshow = false;
			this.$store.state.isshow.islight = true;
			this.$store.state.market.lightChartTime.time = [],
			this.$store.state.market.lightChartTime.price = []	
			this.setlightDate();
			this.drawlight(this.id);
		}
	}
</script>

<style lang="scss" scoped>
	@import "../../assets/css/common.scss";
	#light{
		width: 100%;
		margin: 0 auto;
	}
</style>