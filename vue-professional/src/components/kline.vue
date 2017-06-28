<template>
	<div id="klines">
		<div id="kline" style="margin: 0 auto;">

		</div>
		<div id="volume" style="margin: 0 auto;">

		</div>
	</div>
</template>

<script>
	import { mapMutations } from 'vuex'
	export default {
		name: 'klines',
		methods: {
			...mapMutations([
				'setklineoption',
				'drawkline'
			]),
		},
		data() {
			return {
				obj: {
					id1: 'kline',
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
//				this.setfensoption();
				//用下面注释的方法也可调用store中的函数
				//				this.$store.commit('setfensoption');   
				//				this.$store.commit('drawfenssecond',this.obj);
//				this.drawfenssecond(this.obj);
			}
		},
		mounted: function() {
			this.setklineoption();
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
		#kline {
			width: 100%;
			height: 390px/4*3*@ip5;
		}
		#volume {
			width: 100%;
			height: 390px/4*@ip5;
		}
	}
	/*ip6*/
	
	@media (min-width:371px) and (max-width:410px) {
		#klines {
			width: 100%;
			height: 390px*@ip6;
		}
		#kline {
			width: 100%;
			height: 390px/4*3*@ip6;
		}
		#volume {
			width: 100%;
			height: 390px/4*@ip6;
		}
	}
	/*ip6p及以上*/
	
	@media (min-width:411px) {
		#klines {
			width: 100%;
			height: 390px;
		}
		#kline {
			width: 100%;
			height: 390px/4*3*@ip6p;
		}
		#volume {
			width: 100%;
			height: 390px/4*@ip6p;
		}
	}
</style>