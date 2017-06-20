<template>
	<div id="account">
		<topbar title="我的"></topbar>
	</div>
</template>

<script>
	import topbar from '../components/Topbar.vue'
	export default{
		name:'account',
		components:{topbar}
	}
</script>

<style scoped lang="less">
@import url("../assets/css/main.less");
#account{
	padding-top: 50px;
}
</style>
