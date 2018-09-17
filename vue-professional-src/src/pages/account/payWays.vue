<template>
	<div id="payWays">
		<topbar title="支付"></topbar>
		<back @tap.native="backEvent"></back>
		<cs title="客服"></cs>
		<div class="page_cont">
			<iframe :src="iframe"></iframe>
		</div>
	</div>
</template>

<script>
	import topbar from '../../components/Topbar.vue'
	import back from '../../components/back.vue'
	import cs from '../../components/customerService.vue'
	export default{
		name:'payWays',
		components: {topbar, back, cs},
		data(){
			return {
				username: '',
				money: '',
				iframe: ''
			}
		},
		computed: {
			isTest () {
				return this.$store.state.environment
			},
		},
		methods: {
			backEvent: function(){
				this.$router.replace({path: '/recharge', query: {isRefresh: 1}});
			},
			setIframeUrl () {
				if (this.isTest === 'test') {
					this.iframe = 'http://test.pay.duokongtai.cn/app/appPayinfo?mobile='+ this.username +'&money='+ this.money;
					// this.iframe = 'http://117.139.13.188:8180/app/appPayinfo?mobile='+ this.username +'&money='+ this.money;
				}else{
					// this.iframe ='http://pay.duokongtai.cn/app/appPayinfo?mobile='+ this.username +'&money='+ this.money;
					this.iframe = 'http://117.139.13.188:8180/vs-pay/app/appPayinfo?mobile='+ this.username +'&money='+ this.money;
				}
			}
		},
		// mounted: function(){
		// 	this.username = this.$route.query.username
		// 	this.money = this.$route.query.money
		// 	this.setIframeUrl()
		// },
		activated() {
			this.username = this.$route.query.username
			this.money = this.$route.query.money
			this.setIframeUrl()
		},
		deactivated() {
			this.iframe = ''
		},
	}
</script>

<style scoped lang="less">
@import url("../../assets/css/main.less");
@import url("../../assets/css/base.less");
/*ip6p及以上*/
@media (min-width:411px) {
    #payWays{
    	width: 100%;
    	overflow: hidden;
    	padding-top: 50px;
    	.page_cont{
    		iframe{
    			width: 100%;
    			height: 686px;
    			border: none;
    		}
    	}
    }
}
/*ip6*/
@media (min-width:371px) and (max-width:410px) {
	#payWays{
    	width: 100%;
    	overflow: hidden;
    	padding-top: 50px*@ip6;
    	.page_cont{
    		iframe{
    			width: 100%;
    			height: 686px*@ip6;
    			border: none;
    		}
    	}
    }
}
/*ip5*/
@media(max-width:370px) {
	#payWays{
    	width: 100%;
    	overflow: hidden;
    	padding-top: 50px*@ip5;
    	.page_cont{
    		iframe{
    			width: 100%;
    			height: 686px*@ip5;
    			border: none;
    		}
    	}
    }
}
</style>