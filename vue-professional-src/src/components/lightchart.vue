<template>
		<div id="lightchart">
			<div id="lightcharts" style="margin: 0 auto;">
				
			</div>
			<!-- <div v-if="!timeLength">未到交易时间暂无闪电图</div> -->
		</div>
	
</template>

<script>
	import { mapMutations,mapActions } from 'vuex'
	export default{
		name:'lightchart',
		data(){
			return {
				id:'lightcharts'
			}
		},
		computed: {
			timeLength () {
				return this.$store.state.market.lightChartTime.time.length
			},
			currentdetail () {
				return this.$store.state.market.currentdetail
			}
		},
		methods:{
			...mapMutations([
				'drawlight',
				'setlightDate'
			])
		},
		mounted:function(){
			this.$store.state.market.lightChartTime.time=[];
			this.$store.state.market.lightChartTime.price=[];
			this.setlightDate();
			this.drawlight(this.id);
		}
	}
</script>

<style scoped lang="less">
@import url("../assets/css/main.less");
/*ip5*/
@media(max-width:370px) {
	#lightchart, #lightcharts{
		width: 100%;
		height: 390px*@ip5;
	}
}
/*ip6*/
@media (min-width:371px) and (max-width:410px) {
	#lightchart, #lightcharts{
		width: 100%;
		height: 390px*@ip6;
	}
}
/*ip6p及以上*/
@media (min-width:411px) {
	#lightchart, #lightcharts{
		width: 100%;
		height: 390px*@ip6p;
	}
}
</style>
