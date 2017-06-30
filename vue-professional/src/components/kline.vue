<template>
	<div id="klines">
		<div id="kliness" style="margin: 0 auto;">
			
		</div>
		<div id="volume" style="margin: 0 auto;">

		</div>
	</div>
</template>

<script>
	import { mapMutations,mapActions } from 'vuex'
	export default {
		name: 'klines',
		methods: {
			...mapActions([
				'setklineoption',
				'drawkline',
				'drawklinesec'
			]),
			...mapMutations([
				'processingData'
			])
		},
		data() {
			return {
				obj: {
					id1: 'kliness',
					id2: 'volume'
				}
			}
		},
		computed: {
			Data() {
				return this.$store.state.market.jsonData.Parameters.Data;
			}
		},
		watch: {
			Data: function(n, o) {
				this.setklineoption(); //成交量设置
				this.processingData();
				this.drawklinesec(this.obj);
//				this.drawkline(this.obj);
			}
		},
		mounted: function() {
			this.setklineoption();
			this.processingData();
			this.drawkline(this.obj);
		}
	}
</script>

<style scoped lang="less">
	@import url("../assets/css/main.less");
	/*ip5*/
	
	@media(max-width:370px) {
		#klines {
			width: 100%;
			height: 390px*@ip5;
		}
		#kliness {
			width: 100%;
			height: 390px/5*3*@ip5;
		}
		#volume {
			width: 100%;
			height: 390px/5*2*@ip5;
		}
	}
	/*ip6*/
	
	@media (min-width:371px) and (max-width:410px) {
		#klines {
			width: 100%;
			height: 390px*@ip6;
		}
		#kliness {
			width: 100%;
			height: 390px/5*3*@ip6;
		}
		#volume {
			width: 100%;
			height: 390px/5*2*@ip6;
		}
	}
	/*ip6p及以上*/
	
	@media (min-width:411px) {
		#klines {
			width: 100%;
			height: 390px*@ip6p;
		}
		#kliness {
			width: 100%;
			height: 390px/5*3*@ip6p;
		}
		#volume {
			width: 100%;
			height: 390px/5*2*@ip6p;
		}
	}
</style>