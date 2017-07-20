<template>
	<div id="applycomplate">
		<tp title="申请成功"></tp>
		<back></back>
		<kf title="客服"></kf>
		<div>
			<ul>
				<li><img src="../../assets/img/complate.png" /></li>
				<li class="fontyellow">操盘方案申请成功</li>
				<li class="fontgray">交易账户正在排队开户中</li>
			</ul>
			<bbtn name="进入方案详情" @tap.native='toList'></bbtn>
			<p class="fontgray">
				交易时间： 系统将在30分钟内处理 <br /> 非交易时间： 系统将在下个交易日开盘前处理 <br /> 开户成功系统将短信通知你
			</p>
		</div>
	</div>
</template>

<script>
	import tp from '../../components/Topbar.vue'
	import bbtn from '../../components/bigBtn.vue'
	import back from '../../components/back.vue'
	import kf from '../../components/customerService.vue'
	export default {
		name: 'applycomplate',
		components: {
			tp,
			bbtn,
			back,
			kf
		},
		methods: {
			toList: function() {
				this.$router.replace({
					path: '/tradeDetails',
					query: {
						'toDetailsID': this.$route.query.vid,
						'type': 8,
						'isPay': this.$route.query.isPay
					}
				});
			}
		}
	}
</script>

<style scoped lang="less">
	@import url("../../assets/css/main.less");
	/*ip5*/
	
	@media(max-width:370px) {
		#applycomplate {
			background: #1b1b26;
			height: 736px*@ip5 - 20px;
			padding-top: 50px*@ip5;
		}
		img {
			width: 75px*@ip5;
			height: 50px*@ip5;
		}
		li {
			text-align: center;
		}
		p {
			text-align: center;
		}
		ul>li:first-child {
			margin-top: 65px*@ip5;
		}
		ul>li:nth-child(2) {
			font-size: 16px*@ip5;
			margin-top: 12px*@ip5;
		}
		ul>li:nth-child(3) {
			margin-top: 12px*@ip5;
			font-size: 14px*@ip5;
		}
		#bigBtn {
			margin-top: 63px*@ip5;
		}
		p {
			margin-top: 10px*@ip5;
			line-height: 25px*@ip5;
			font-size: 14px*@ip5;
		}
	}
	/*ip6*/
	
	@media (min-width:371px) and (max-width:410px) {
		#applycomplate {
			background: #1b1b26;
			height: 736px*@ip6 - 20px;
			padding-top: 50px*@ip6;
		}
		img {
			width: 75px*@ip6;
			height: 50px*@ip6;
		}
		li {
			text-align: center;
		}
		p {
			text-align: center;
		}
		ul>li:first-child {
			margin-top: 65px*@ip6;
		}
		ul>li:nth-child(2) {
			font-size: 16px*@ip6;
			margin-top: 12px*@ip6;
		}
		ul>li:nth-child(3) {
			margin-top: 12px*@ip6;
			font-size: 14px*@ip6;
		}
		#bigBtn {
			margin-top: 63px*@ip6;
		}
		p {
			margin-top: 10px*@ip6;
			line-height: 25px*@ip6;
			font-size: 14px*@ip6;
		}
	}
	/*ip6p及以上*/
	
	@media (min-width:411px) {
		#applycomplate {
			background: #1b1b26;
			height: 736px - 20px;
			padding-top: 50px;
		}
		img {
			width: 75px;
			height: 50px;
		}
		li {
			text-align: center;
		}
		p {
			text-align: center;
		}
		ul>li:first-child {
			margin-top: 65px;
		}
		ul>li:nth-child(2) {
			font-size: 16px;
			margin-top: 12px;
		}
		ul>li:nth-child(3) {
			margin-top: 12px;
			font-size: 14px;
		}
		#bigBtn {
			margin-top: 63px;
		}
		p {
			margin-top: 10px;
			line-height: 25px;
			font-size: 14px;
		}
	}
</style>