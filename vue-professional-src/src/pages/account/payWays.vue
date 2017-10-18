<template>
	<div id="payWays">
		<topbar title="支付"></topbar>
		<back></back>
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
			return {}
		},
		computed: {
			iframe(){
				return 'http://test.pay.duokongtai.cn/app/appPayinfo?mobile='+ this.$route.query.username +'&money='+ this.$route.query.money;
			}
		},
		methods: {},
		mounted: function(){}
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