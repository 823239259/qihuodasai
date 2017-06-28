<template>
	<div id="chartfens">
		<div id="fens" style="margin: 0 auto;">

		</div>
		<div id="volume" style="margin: 0 auto;">

		</div>
	</div>
</template>

<script>
	import { mapMutations } from 'vuex'
	export default {
		name: 'chartfens',
		methods: {
			...mapMutations([
				'drawfens',
				'setfensoption',
				'drawfenssecond'
			]),
		},
		data() {
			return {
				obj: {
					id1: 'fens',
					id2: 'volume'
				}
			}
		},
		computed:{
			Data(){
				return this.$store.state.market.jsonData.Parameters.Data;
			}
		},
		watch: {
			Data: function(n, o) {
				this.setfensoption();
				//用下面注释的方法也可调用store中的函数
//				this.$store.commit('setfensoption');   
//				this.$store.commit('drawfenssecond',this.obj);
				this.drawfenssecond(this.obj);
			}
		},
		mounted: function() {
			this.setfensoption();
			this.drawfens(this.obj);
		}
	}
</script>

<style scoped lang="less">
	@import url("../assets/css/main.less");
	/*ip5*/
	
	@media(max-width:370px) {
		#chartfens {
			width: 100%;
			height: 390px*@ip5;
		}
		#fens {
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
		#chartfens {
			width: 100%;
			height: 390px*@ip6;
		}
		#fens {
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
		#chartfens {
			width: 100%;
			height: 390px;
		}
		#fens {
			width: 100%;
			height: 390px/4*3*@ip6p;
		}
		#volume {
			width: 100%;
			height: 390px/4*@ip6p;
		}
	}
</style>