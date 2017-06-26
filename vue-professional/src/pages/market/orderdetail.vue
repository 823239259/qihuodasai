<template>
	<div id="orderdetail">
		<topbar cname='国际原油' cnum='CL' mc="1708"></topbar>
		<selectbar></selectbar>
		<dish v-if="pshow"></dish>
		<tradebottom v-if='s'></tradebottom>
		<tradecenter v-if='jshow'></tradecenter>
	</div>
</template>

<script>
	import topbar from '../../components/detailTopbar.vue'
	import selectbar from '../../components/detailSelectbar.vue'
	import tradebottom from '../../components/tradeBottom.vue'
	import tradecenter from '../../components/tradeCenter.vue'
	import dish from '../../components/dish.vue'
	export default {
		name: 'orderdetail',
		components: {
			topbar,
			selectbar,
			tradebottom,
			tradecenter,
			dish
		},
		computed:{
			jshow(){
				return this.$store.state.isshow.bottomshow;
			},
			pshow(){
				return this.$store.state.isshow.pshow;
			},
			s(){      //判断底部操作栏是否显示
				if(this.jshow || this.pshow){
					return false;
				}else{
					return true;
				}
			}
		}
	}
</script>

<style scoped lang="less">
	@import url("../../assets/css/main.less");
	#orderdetail {
		padding-top: 90px;
		height: 736px;
		overflow: hidden;
	}
	/*ip5*/
	
	@media(max-width:370px) {
		#orderdetail {
			padding-top: 90px*@ip5;
			height: 736px*@ip5;
			overflow: hidden;
		}
		
	}
	/*ip6*/
	
	@media (min-width:371px) and (max-width:410px) {
		#orderdetail {
			padding-top: 90px*@ip6;
			height: 736px*@ip6;
			overflow: hidden;
		}
	}
</style>