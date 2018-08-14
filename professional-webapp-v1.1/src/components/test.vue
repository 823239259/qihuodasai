<template>
	<div id="test">
		<h1>{{ msg }}</h1>
		<button @click="clickEvent">我是按钮</button>
		<i class="ifont">&#xe611;</i>
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
		methods: {
			clickEvent: function(){
				layer.msg('hello');
			}
		},
		mounted: function(){
			
		}
		
	}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="scss">
	$red: green;
	h1{
		color: $red;
	}
</style>