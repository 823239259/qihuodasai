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
			chartHeight(){
				return this.$store.state.market.chartHeight;
			}
		},
		watch: {
			quoteInitStep: function(n, o){
				if(n && n == true){
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
			chartHeight: function(n, o){
				if(n && n != ''){
					this.$store.state.isshow.isfens = true;
					$("#fens").height(n/10*6.8);
					$("#volume").height(n/10*3);
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
			var h;
			if(this.$parent.loginChartHeight){
				h = this.$parent.loginChartHeight;
			}else{
				h = this.$store.state.market.chartHeight;
			}
			$("#fens").height(h/10*6.9);
			$("#volume").height(h/10*3);
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
			this.$store.state.isshow.isfens = true;
			if(this.quoteInitStep != ''){
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