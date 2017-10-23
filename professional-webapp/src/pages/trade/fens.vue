<template>
	<div id="echarts">
		<div id="fens"></div>
		<div id="volume"></div>
	</div>
</template>

<script>
	import { mapMutations,mapActions } from 'vuex'
	export default{
		name: 'echarts',
		computed: {
			quoteInitStatus(){
				return this.$store.state.market.quoteInitStatus;
			},
			currentdetail(){
				return this.$store.state.market.currentdetail;
			},
			quoteInitStep(){
				return this.$store.state.market.quoteInitStep;
			},
			quoteSocket(){
				return this.$store.state.quoteSocket;
			},
		},
		watch: {
			quoteInitStep: function(n, o){
				this.$store.state.isshow.isfens = true;
				var data = {
					Method: "QryHistory",
					Parameters:{
						ExchangeNo: this.currentdetail.ExchangeNo,
						CommodityNo: this.currentdetail.CommodityNo,
						ContractNo: this.currentdetail.MainContract,
						HisQuoteType: 0,
						BeginTime: "",
						EndTime: "",
						Count: 0
					}
				};
				this.quoteSocket.send(JSON.stringify(data));
			}
		},	
		methods: {
			...mapActions([
				'initQuoteClient'
			]),
		},
		mounted: function(){
			if(this.quoteInitStep != ''){
				this.$store.state.isshow.isfens = true;
				var data = {
					Method: "QryHistory",
					Parameters:{
						ExchangeNo: this.currentdetail.ExchangeNo,
						CommodityNo: this.currentdetail.CommodityNo,
						ContractNo: this.currentdetail.MainContract,
						HisQuoteType: 0,
						BeginTime: "",
						EndTime: "",
						Count: 0
					}
				};
				this.quoteSocket.send(JSON.stringify(data));
			}
		},
		activated: function(){
//			if(this.quoteInitStep != ''){
//				this.$store.state.isshow.isfens = true;
//				var data = {
//					Method: "QryHistory",
//					Parameters:{
//						ExchangeNo: this.currentdetail.ExchangeNo,
//						CommodityNo: this.currentdetail.CommodityNo,
//						ContractNo: this.currentdetail.MainContract,
//						HisQuoteType: 0,
//						BeginTime: "",
//						EndTime: "",
//						Count: 0
//					}
//				};
//				this.quoteSocket.send(JSON.stringify(data));
//			}
		}
	}
</script>

<style lang="scss" scoped>
	@import "../../assets/css/common.scss";
	#fens{
		width: 100%;
		height: 324px;
		margin: 0 auto;
	}
	#volume{
		width: 100%;
		height: 216px;
		margin: 0 auto;
	}
</style>