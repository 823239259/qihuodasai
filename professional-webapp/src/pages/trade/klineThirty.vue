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
			this.$store.state.isshow.iskline = true;
			this.$store.state.market.selectTime = 30;
			var data = {
				Method: "QryHistory",
				Parameters:{
					ExchangeNo: this.currentdetail.ExchangeNo,
					CommodityNo: this.currentdetail.CommodityNo,
					ContractNo: this.currentdetail.MainContract,
					HisQuoteType: 30,
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
	#kliness{
		width: 100%;
		height: 324px;
		margin: 0 auto;
	}
	#kliness_volume{
		width: 100%;
		height: 216px;
		margin: 0 auto;
	}
</style>