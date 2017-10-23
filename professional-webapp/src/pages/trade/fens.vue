<template>
	<div id="echarts">
		<div id="fens"></div>
		<div id="volume"></div>
	</div>
</template>

<script>
	export default{
		name: 'echarts',
		computed: {
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
				if(n && n != ''){
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
			}
		},	
		mounted: function(){
			console.log(1111);
			var h = this.$parent.chartHeight;
			$("#fens").height(h/10*6.8);
			$("#volume").height(h/10*3);
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
		}
	}
</script>

<style lang="scss" scoped>
	@import "../../assets/css/common.scss";
	#fens, #volume{
		width: 100%;
		margin: 0 auto;
	}
</style>