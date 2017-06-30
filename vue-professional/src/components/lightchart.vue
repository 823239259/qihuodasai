<template>
		<div id="lightchart">
			<div id="lightcharts" style="margin: 0 auto;">
				
			</div>
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
		methods:{
			...mapMutations([
				'drawlight',
				'drawlightsec',
				'setlightDate'
			])
		},
		mounted:function(){
			this.setlightDate();
			this.drawlight(this.id);
		}
	}
</script>

<style scoped lang="less">
@import url("../assets/css/main.less");

/*ip5*/
@media(max-width:370px) {
	#lightchart {
			width: 100%;
			height: 390px*@ip5;
		}
		#lightcharts {
			width: 100%;
			height: 390px*@ip5;
		}
}
/*ip6*/
@media (min-width:371px) and (max-width:410px) {
    	#lightchart {
			width: 100%;
			height: 390px*@ip6;
		}
		#lightcharts {
			width: 100%;
			height: 390px*@ip6;
		}
}
/*ip6p及以上*/
@media (min-width:411px) {
    	#lightchart {
			width: 100%;
			height: 390px*@ip6p;
		}
		#lightcharts {
			width: 100%;
			height: 390px*@ip6p;
		}
}
</style>
