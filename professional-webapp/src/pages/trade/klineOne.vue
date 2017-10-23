<template>
	<div id="echarts">
		<div id="kliness"></div>
		<div id="kliness_volume"></div>
	</div>
</template>

<script>
	export default{
		name: 'echarts',
		computed: {
			currentdetail(){
				return this.$store.state.market.currentdetail;
			},
			quoteSocket(){
				return this.$store.state.quoteSocket;
			},
		},
		mounted: function(){
			var h = this.$parent.chartHeight;
			$("#kliness").height(h/10*6.8);
			$("#kliness_volume").height(h/10*3);
			this.$store.state.isshow.iskline = true;
			this.$store.state.market.selectTime = 1;
			var data = {
				Method: "QryHistory",
				Parameters:{
					ExchangeNo: this.currentdetail.ExchangeNo,
					CommodityNo: this.currentdetail.CommodityNo,
					ContractNo: this.currentdetail.MainContract,
					HisQuoteType: 1,
					BeginTime: "",
					EndTime: "",
					Count: 0
				}
			};
			this.quoteSocket.send(JSON.stringify(data));
		}
	}
</script>

<style lang="scss" scoped>
	@import "../../assets/css/common.scss";
	#kliness, #kliness_volume{
		width: 100%;
		margin: 0 auto;
	}
</style>