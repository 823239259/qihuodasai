<template>
	<div id="stopMoney" v-show="show">
		<div class="bg"></div>
		<div class="stopMoney">
			
		</div>
	</div>
</template>

<script>
	export default{
		name: 'warning',
		data(){
			return{
				show: false,
				
			}
		},
		computed: {
			
		},
	}
</script>

<style lang="scss" scoped>
	@import "../../assets/css/common.scss";
	
</style>