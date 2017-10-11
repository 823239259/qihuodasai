<template>
	<div id="test">
		<h1>{{ msg }}</h1>
	</div>
</template>

<script>
	export default {
		name: 'test',
		data() {
			return {
				msg: 'Welcome to Your Vue.js App'
			}
		},
		
	}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="scss">
	$red: green;
	h1{
		color: $red;
	}
</style>