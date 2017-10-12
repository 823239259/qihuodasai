<template>
	<div id="account">
			<ul>
				<li>
					<span>账户概括</span>
				</li>
				<li>
					<span>开户明细</span>
				</li>
				<li>
					<span>安全设置</span>
				</li>
			</ul>
	</div>
</template>

<script>
	export default{
		name:'account',
	}
</script>

<style lang="scss" scoped type="text/css">
@import "../assets/css/common.scss";
	#account {
		margin-left: 120px;
		li {
			text-align: center;
			float : left;	
			width : 120px;
			height : 30px;
			margin : 5px;
			line-height : 30px;
			background-color : $blue;
			&:nth-child(1){
				color: $yellow;
			}
		}
	}
</style>