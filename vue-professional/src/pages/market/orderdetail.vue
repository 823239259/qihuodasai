<template>
	<div id="orderdetail">
		<topbar cname='国际原油' cnum='CL' mc="1708"></topbar>
		<selectbar></selectbar>
		<dish v-if="pshow"></dish>
		<tradebottom v-if='s'></tradebottom>
		<tradecenter v-if='jshow'></tradecenter>
		<chartfens v-if='fshow'></chartfens>
		<kline v-if='kshow'></kline>
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
	export default {
		name: 'orderdetail',
		components: {
			topbar,
			selectbar,
			tradebottom,
			tradecenter,
			dish,
			chartfens,
			kline
		},
		computed:{
			kshow(){
				return this.$store.state.isshow.kshow;
			},
			jshow(){
				return this.$store.state.isshow.bottomshow;
			},
			pshow(){
				return this.$store.state.isshow.pshow;
			},
			fshow(){
				return this.$store.state.isshow.fshow;
			},
			s(){      //判断底部操作栏是否显示
				if(this.jshow || this.pshow){
					return false;
				}else{
					return true;
				}
			},
			//映射假数据
			Data(){
				return this.$store.state.market.jsonData.Parameters.Data;
			}
		},
		mounted:function(){
			//下面的循环用于假数据更新
			setInterval(function() {
				var ran = Math.floor(Math.random() * (4350 - 4348 + 1) + 4348)/100;
				this.$store.state.market.jsonData.Parameters.Data.pop();
				this.$store.state.market.jsonData.Parameters.Data.push(["2017-06-26 11:16:00", ran, 43.46, 43.46, 43.46, 548303, 2]);
			}.bind(this), 100);
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