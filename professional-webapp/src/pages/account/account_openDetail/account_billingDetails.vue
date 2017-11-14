<template>
	<div id="billingDetails">
		<div class="bg"></div>
		<div class="billingDetails">
			
		</div>
	</div>
</template>

<script>
	import pro from "../../../assets/js/common.js"
	export default{
		name: "openDetail_billingDetails"
	}
</script>

<style lang="scss" scoped type="text/css">
@import "../../../assets/css/common.scss";
	#billingDetails{
		height: 800px;
	}
	.billingDetails{
		position: absolute;
		top: 50%;
		left: 50%;
		margin:-300px 0 0 -500px;
		width: 1000px;
		height: 600px;
		z-index: 120;
		border-radius: 10px;
		background-color: $deepblue;
	}
</style>