<template>
	<div id="service">
		<topbar title="在线客服"></topbar>
		<back @tap.native="goBack"></back>
		<iframe :src="iframe"></iframe>
	</div>
</template>

<script>
	import topbar from '../components/Topbar.vue'
	import back from '../components/back.vue'
	export default{
		name:'service',
		//组件
		components:{
			topbar,
			back
		},
		computed: {
			userInfo: function(){
				return this.$store.state.account;
			},
			PATH: function(){
				return this.$store.getters.PATH;
			},
			iframe: function() {
				return  /*this.PATH + */'http://test.www.vs.com/topic/consistentBeauty/consistentbeauty.html?phone=' + this.$store.state.account.phone + '&userName=' + this.$store.state.account.username;
			}
		},
		methods: {
			goBack: function(){
				this.$router.go(-2);
			}
		}
	}
</script>
<style scoped lang="less">
	iframe {
		width: 100%; height: 100%; position: fixed; bottom: 0;
	}
</style>