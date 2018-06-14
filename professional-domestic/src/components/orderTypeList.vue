<template>
	<div id="orderTypeList" v-if="isshow">
		<div class="bg" @tap="clickEvent"></div>
		<div class="list">
			<ul>
				<template v-for="key in parameters">
					<li :value="key.CommodityName + '&' + key.CommodityNo + '&' + key.MainContract" @tap="selectEvent">{{key.CommodityName}} {{key.CommodityNo}} {{key.MainContract}}</li>
				</template>
			</ul>
		</div>
	</div>
</template>

<script>
	export default {
		name: 'orderTypeList',
		props: [],
		data() {
			return {
				isshow: false,
			}
		},
		computed:{
			parameters(){
				return this.$store.state.market.Parameters;
			},
		},
		methods: {
			selectEvent: function(e){
				this.$store.state.market.selectId = $(e.currentTarget).attr('value');
				this.isshow = false;
			},
			clickEvent: function(){
				this.isshow = false;
			}
		}
	}
</script>

<style scoped lang="less">
@import url("../assets/css/base.less");
.bg{
	position: fixed;
	top: 0;
	left: 0;
	right: 0;
	bottom: 0;
	z-index: 1110;
	background: #000;
	opacity: 0.7;
}
/*ip6p及以上*/
@media (min-width:411px) {
	.list{
		position: fixed;
		bottom: 0;
		left: 0;
		z-index: 1111;
		width: 100%;
		height: 225px;
		overflow-y: scroll;
		background: @deepblue;
		li{
			height: 45px;
			line-height: 45px;
			text-align: center;
			border-bottom: 1px solid @black;
			color: @blue;
			font-size: @fs16;
		}
	}
}
/*ip6*/
@media (min-width:371px) and (max-width:410px) {
	.list{
		position: fixed;
		bottom: 0;
		left: 0;
		z-index: 1111;
		width: 100%;
		height: 225px*@ip6;
		overflow-y: scroll;
		background: @deepblue;
		li{
			height: 45px*@ip6;
			line-height: 45px*@ip6;
			text-align: center;
			border-bottom: 1px solid @black;
			color: @blue;
			font-size: @fs16*@ip6;
		}
	}
}
/*ip5*/
@media(max-width:370px) {
	.list{
		position: fixed;
		bottom: 0;
		left: 0;
		z-index: 1111;
		width: 100%;
		height: 225px*@ip5;
		overflow-y: scroll;
		background: @deepblue;
		li{
			height: 45px*@ip5;
			line-height: 45px*@ip5;
			text-align: center;
			border-bottom: 1px solid @black;
			color: @blue;
			font-size: @fs16*@ip5;
		}
	}
}
</style>