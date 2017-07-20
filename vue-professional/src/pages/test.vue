<template>
	<div id="test">
		<tradebtn marketprice="132133" transaction='sell'></tradebtn>
		<tradebtn marketprice="13221321" transaction='buy'></tradebtn>
		<cbtn name="弹窗3" @tap.native='alert3'></cbtn>
		<cbtn name="点我弹窗" @tap.native='alert'></cbtn>
		<pbtn name="我要充值" color="yellow"></pbtn>
		<pbtn name="点我弹窗2" color="gray" @tap.native='alert2'></pbtn>
		<menus style="visibility: hidden;"></menus>
		<back></back>
		<alert title="全部平仓" line1="确认下单吗？" line2="您是否卖掉全部的合约？" addr="127.0.0.1" :objstr='objst'></alert>
		<refresh></refresh>
		<ifalert></ifalert>
		<changealert></changealert>
		<cbtn name="条件页" @tap.native='goCon'></cbtn>
		<cbtn name="止损" @tap.native='goStop'></cbtn>
		<cbtn name="资金" @tap.native='goMon'></cbtn>
		<cbtn name="历史" @tap.native='goHis'></cbtn>
		<topbar cname='国际原油' cnum='CL' mc="1708"></topbar>
		<selectbar></selectbar>
		<tradebottom></tradebottom>
		<stopmoneyalert></stopmoneyalert>
		<pbtn name="止盈弹窗" color="gray" @tap.native='alert4'></pbtn>
		<charts></charts>
	</div>
</template>

<script>
	import tradebtn from '../components/tradeButton.vue'
	import cbtn from '../components/conditionBtn.vue'
	import pbtn from '../components/Button.vue'
	import menus from '../components/menu.vue'
	import back from '../components/back.vue'
	import alert from '../components/Tradealert.vue'
	import refresh from '../components/Refresh.vue'
	import ifalert from '../components/ifalert.vue'
	import changealert from '../components/changealert.vue'
	import topbar from '../components/detailTopbar.vue'
	import selectbar from '../components/detailSelectbar.vue'
	import tradebottom from '../components/tradeBottom.vue'
	import stopmoneyalert from '../components/stopmoneyalert.vue'
	import charts from '../components/chartfens.vue'
	import { mapMutations,mapActions } from 'vuex'
	export default {
		name: 'test',
		components: {
			tradebtn,
			cbtn,
			pbtn,
			menus,
			back,
			alert,
			refresh,
			ifalert,
			changealert,
			topbar,
			selectbar,
			tradebottom,
			stopmoneyalert,
			charts
		},
		methods: {
			 ...mapActions([
      			'add' // 映射 this.increment() 为 this.$store.commit('increment')
    			]),
			alert: function() {
				this.$children[8].isshow = true;
			},
			alert2: function() {
				//				console.log(this.$children);
				this.$children[10].isshow = true;
			},
			alert3: function() {
				//				console.log(this.$children);
				this.$children[11].isshow = true;
			},
			alert4:function(){
//				console.log(this.$children)
				this.$children[19].isshow = true;	
			},
			goCon() {
				this.$router.push({
					path: '/conditions'
				});
			},
			goStop() {
				this.$router.push({
					path: '/stopmoney'
				});
			},
			goMon() {
				this.$router.push({
					path: '/moneydetails'
				});
			},
			goHis() {
				this.$router.push({
					path: '/historytrade'
				});
			}
		},
		data() {
			return {
				obj: {
					name: 'cl123',
					id: 1,
					dsadadas: 213131,
					mele: 'adbd',
					sex: 'male'
				}
			}
		},
		computed: {
			objst: function() {
				return JSON.stringify(this.obj);
			}
		},
		activated:function(){
			console.log(1);
			this.add(this.$stoe);
		}
	}
</script>

<style scoped lang="less">
	@import url("../assets/css/main.less");
	
	#test {
		background:#242633;
		padding-top: 100px;
		overflow-y: scroll;
		padding-bottom: 300px;
	}
	
	#menus {
		position: absolute;
		top: 15px;
		right: 15px;
		z-index: 1100;
	}
	#menus ol>{
		z-index: 1999;
	}
	#refresh {
		top: 17px;
		right: 100px;
	}
	
	#detailselectbar {
		top: 50px;
		position: fixed;
	}
	
	/*ip5*/
	
	@media(max-width:370px) {
		#test {
			background: #242633;
			padding-top: 100px*@ip5;
		}
		#menus {
			position: absolute;
			top: 15px*@ip5;
			right: 15px*@ip5;
		}
		#refresh {
			top: 17px*@ip5;
			right: 100px*@ip5;
		}
		#detailselectbar {
			top: 50px*@ip5;
			position: fixed;
		}
	}
	/*ip6*/
	
	@media (min-width:371px) and (max-width:410px) {
		#test {
			background: #242633;
			padding-top: 100px*@ip6;
		}
		#menus {
			position: absolute;
			top: 15px*@ip6;
			right: 15px*@ip6;
		}
		#refresh {
			top: 17px*@ip6;
			right: 100px*@ip6;
		}
		#detailselectbar {
			top: 50px*@ip6;
			position: fixed;
		}
	}
</style>