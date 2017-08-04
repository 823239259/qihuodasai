<template>
	<div id="orderdetail">
		<topbar :cname='CommodityName' :cnum='EngName'></topbar>
		<selectbar></selectbar>
		<dish v-if="pshow"></dish>
		<tradebottom v-if='s'></tradebottom>
		<tradecenter v-if='jshow'></tradecenter>
		<chartfens v-if='fshow'></chartfens>
		<kline v-if='kshow'></kline>
		<lightchart v-if='sshow'></lightchart>
	</div>
</template>

<script>
	import topbar from '../../components/detailTopbar.vue'
	import selectbar from '../../components/detailSelectbar.vue'
	import tradebottom from '../../components/tradeBottom.vue'
	import tradecenter from '../../components/tradeCenter.vue'
	import dish from '../../components/dish.vue'
	import chartfens from '../../components/chartfens.vue'
	import kline from '../../components/kline.vue'
	import lightchart from '../../components/lightchart.vue'
	export default {
		name: 'orderdetail',
		components: {
			topbar,
			selectbar,
			tradebottom,
			tradecenter,
			dish,
			chartfens,
			kline,
			lightchart
		},
		data() {
			return {
				CommodityName: this.$route.query.CommodityName,
				EngName: this.$route.query.EngName,
			}
		},
		computed: {
			detail() {
				return this.$store.state.market.currentdetail
			},
			Parameters() {
				return this.$store.state.market.Parameters;
			},
			sshow() {
				return this.$store.state.isshow.sshow;
			},
			kshow() {
				return this.$store.state.isshow.kshow;
			},
			jshow() {
				return this.$store.state.isshow.bottomshow;
			},
			pshow() {
				return this.$store.state.isshow.pshow;
			},
			fshow() {
				return this.$store.state.isshow.fshow;
			},
			s() { //判断底部操作栏是否显示
				if(this.jshow || this.pshow) {
					return false;
				} else {
					return true;
				}
			},
			//映射初始值
			Data() {
				return this.$store.state.market.jsonData.Parameters.Data;
			},
			quoteSocket() {
				return this.$store.state.quoteSocket
			}
		},
		activated: function() {
			this.CommodityName = this.$route.query.CommodityName;
			this.EngName = this.$route.query.EngName;
			//			var b = '{"Method":"QryHistory","Parameters":{"ExchangeNo":"'+this.detail.LastQuotation.ExchangeNo+'","CommodityNo":"'+this.detail.CommodityNo+'","ContractNo":"'+this.detail.LastQuotation.ContractNo+'","HisQuoteType":'+0+',"BeginTime":"","EndTime":"","Count":'+0+'}}'
			//			this.quoteSocket.send(b);
			this.$store.state.isshow.sshow = false;
			this.$store.state.isshow.fshow = true;
			this.$store.state.isshow.kshow = false;
			this.$store.state.isshow.pshow = false;
			this.$store.state.isshow.bottomshow = false;
			this.$store.state.isshow.islightshow = false;
		},
		mounted: function() {
			this.$store.state.isshow.fshow = true;
			this.$store.state.isshow.pshow = false;
			this.$store.state.isshow.sshow = false;
			this.$store.state.isshow.kshow = false;
			this.$store.state.isshow.bottomshow = false;
			//			var vol=5;
			//			下面的循环用于假数据更新
			//			setInterval(function() {
			//				var ran = Math.floor(Math.random() * (4350 - 4348 + 1) + 4348)/100;
			//				var ran2 = Math.floor(Math.random() * (4350 - 4348 + 1) + 4348)/100;
			//				vol+=5;
			//				this.$store.state.market.jsonData.Parameters.Data.pop();
			//				this.$store.state.market.jsonData.Parameters.Data.push(["2017-06-26 11:16:00", ran, ran2, 43.46, 43.46, 548303,vol ]);
			//			}.bind(this), 100);
		}
	}
</script>

<style scoped lang="less">
	@import url("../../assets/css/main.less");
	#orderdetail {
		padding-top: 90px;
		overflow: hidden;
	}
	/*ip5*/
	
	@media(max-width:370px) {
		#orderdetail {
			padding-top: 90px*@ip5;
			overflow: hidden;
		}
	}
	/*ip6*/
	
	@media (min-width:371px) and (max-width:410px) {
		#orderdetail {
			padding-top: 90px*@ip6;
			overflow: hidden;
		}
	}
</style>