<template>
	<div id="paytopbar">
		<div>
			<ul>
				<li class="fontwhite">国际期货</li>
				<li class="fontwhite">支付确认</li>
			</ul>
		</div>
		<back></back>
	</div>
</template>

<script>
	import back from './back.vue'
	export default {
		name: 'paytopbar',
		components: {
			back,
		}
	}
</script>

<style scoped lang="less">
	@import url("../assets/css/main.less");
	#paytopbar{
		width: 100%;
		ul{
			width:100%;
			padding-top: 1%;
			li{
				width:100%;
				text-align: center;
			}
		}
		
	}
	/*ip5*/
	
	@media(max-width:370px) {
		#paytopbar {
			position: fixed;
			top: 0;
			height: 50px*@ip5;
			background: #242633;
			border-bottom: 1px solid #1B1B26;
			ul {
				width: 100%;
				height: 50px*@ip5;
				li:first-child {
					font-size: 16px*@ip5;
				}
				li:last-child {
					font-size: 12px;
				}
			}
		}
	}
	/*ip6*/
	
	@media (min-width:371px) and (max-width:410px) {
		#paytopbar {
			position: fixed;
			top: 0;
			height: 50px*@ip6;
			background: #242633;
			border-bottom: 1px solid #1B1B26;
			ul {
				width: 100%;
				height: 50px*@ip6;
				li:first-child {
					font-size: 16px*@ip6;
				}
				li:last-child {
					font-size: 12px;
				}
			}
		}
	}
	/*ip6p及以上*/
	
	@media (min-width:411px) {
		#paytopbar {
			position: fixed;
			top: 0;
			height: 50px;
			background: #242633;
			border-bottom: 1px solid #1B1B26;
			ul {
				width: 100%;
				height: 50px;
				li:first-child {
					font-size: 16px;
				}
				li:last-child {
					font-size: 12px;
				}
			}
		}
	}
</style>