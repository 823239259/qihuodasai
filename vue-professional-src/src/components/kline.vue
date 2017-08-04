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
			...mapMutations([
				'setklineoption',
				'drawkline',
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
			},
			quoteSocket() {
				return this.$store.state.quoteSocket
			},
		},
		mounted: function() {
//			var b = '{"Method":"QryHistory","Parameters":{"ExchangeNo":"' + this.$parent.detail.LastQuotation.ExchangeNo + '","CommodityNo":"' + this.$parent.detail.CommodityNo + '","ContractNo":"' + this.$parent.detail.LastQuotation.ContractNo + '","HisQuoteType":' + 1 + ',"BeginTime":"","EndTime":"","Count":' + 0 + '}}'
//			this.quoteSocket.send(b);
//			this.setklineoption();
//			this.drawkline(this.obj);
		},
		
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