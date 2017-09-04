<template>
	<div id="tradeapply">
		<tipsDialog :msg="msgTips"></tipsDialog>
		<topbar title="开户"></topbar>
		<cs title="客服"></cs>
		<!--轮播-->
		<div id="slider" class="mui-slider">
			<div class="mui-slider-group">
				<!--<div class="mui-slider-item mui-slider-item-duplicate">
					<a href="#"><img :src="bannerList[bannerList.length - 1].imgPath"></a>
				</div>-->
				<!--第一个内容区容器-->
				<template v-for="k in bannerList">
					<div class="mui-slider-item">
						<a href="#"><img :src="k.imgPath" /></a>
					</div>
				</template>
				<!--<div class="mui-slider-item mui-slider-item-duplicate">
					<a href="#"><img :src="bannerList[0].imgPath"></a>
				</div>-->
			</div>
		</div>
		<!--列表-->

		<ul class="datalist">
			<template>
				<li @tap='toDetail'>
					<ol>
						<li>
							<h5 class="fl">国际期货</h5>
							<p class="fr">一个账号可交易17个品种 <i>&nbsp;&nbsp;></i></p>
						</li>
						<li class="cl">
							<ul>
								<li class="lii">
									交易手续费：<span>19.5元/单边 起</span>
								</li>
								<li class="lii">
									交易时间：<span>09:05-23:55</span>
								</li>
							</ul>
						</li>
					</ol>
				</li>
			</template>
		</ul>

	</div>
</template>

<script>
	import topbar from '../components/Topbar.vue'
	import cs from '../components/customerService.vue'
	import tipsDialog from '../components/tipsDialog.vue'
	export default {
		name: 'tradeapply',
		components: {
			topbar, cs, tipsDialog
		},
		data(){
			return {
				bannerList: []
			}
		},
		computed: {
			PATH() {
				return this.$store.getters.PATH
			},
			msgTips: function(){
				return this.msg;
			},
		},
		methods: {
			toDetail: function() {
				this.$router.push({
					path: '/applydetail'
				});
			},
			getBanner: function(){
				this.$http.post(this.PATH + '/banner/list', {emulateJSON: true},{
					params: {
						type: 9
					},
					timeout: 5000
				}).then(function(e){
					var data = e.body;
					if(data.success == true){
						if(data.code == 1){
							this.bannerList = data.data.bannerList;
							this.bannerList.forEach(function(o, i){
								o.imgPath = "http://manage.dktai.cn/" + o.imgPath;
							});
						}
					}else{
						switch (data.code){
							case '2':
								this.$children[0].isShow = true;
								this.msg = '处理失败';
								break;
							default:
								break;
						}
					}
				}.bind(this), function(){
					this.$children[0].isShow = true;
					this.msg = '网络不给力，请稍后再试！';
				});
			}
		},
		mounted: function() {
			//初始化banner
			this.getBanner();
			var gallery = mui('.mui-slider');
			gallery.slider({
			  interval: 0 //自动轮播周期，若为0则不自动播放，默认为0；
			});
//			$('#banner1').attr('src', require("../../src/assets/img/banner01.png"));
			//初始化各种合约数据
			this.$http.post(
				this.PATH + '/ftrade/params', {
					emulateJSON: true
				}, {
					params: {
						"businessType": 8
					},
					timeout: 5000
				}

			).then(function(e) {
				this.$store.state.tempTradeapply = e.body.data;
				this.$store.state.tempTradeapply.contractList.forEach(function(x) {
					switch(x.tradeType) {
						case 0:
							//						return '富时A50'
							var arr = [];
							this.$store.state.tempTradeapply.paramList.forEach(function(a) {
								arr.push(a.tranLever);
								x.shoushu = arr;
							}.bind(this));
							break;
						case 6:
							//						return '国际原油'
							var arr = [];
							this.$store.state.tempTradeapply.paramList.forEach(function(a) {
								arr.push(a.crudeTranLever);
								x.shoushu = arr;
							}.bind(this));
							break;
						case 7:
							//						return '恒指期货'
							var arr = [];
							this.$store.state.tempTradeapply.paramList.forEach(function(a) {
								arr.push(a.hsiTranLever);
								x.shoushu = arr;
							}.bind(this));
							break;
						case 9:
							//						return '迷你道指'
							var arr = [];
							this.$store.state.tempTradeapply.paramList.forEach(function(a) {
								arr.push(a.mdtranLever);
								x.shoushu = arr;
							}.bind(this));
							break;
						case 10:
							//						return '迷你纳指'
							var arr = [];
							this.$store.state.tempTradeapply.paramList.forEach(function(a) {
								arr.push(a.mntranLever);
								x.shoushu = arr;
							}.bind(this));
							break;
						case 11:
							//						return '迷你标普'
							var arr = [];
							this.$store.state.tempTradeapply.paramList.forEach(function(a) {
								arr.push(a.mbtranLever);
								x.shoushu = arr;
							}.bind(this));
							break;
						case 12:
							//						return '德国DAX'
							var arr = [];
							this.$store.state.tempTradeapply.paramList.forEach(function(a) {
								arr.push(a.daxtranLever);
								x.shoushu = arr;
							}.bind(this));
							break;
						case 13:
							//						return '日经225'
							var arr = [];
							this.$store.state.tempTradeapply.paramList.forEach(function(a) {
								arr.push(a.nikkeiTranLever);
								x.shoushu = arr;
							}.bind(this));
							break;
						case 14:
							//						return '小恒指'
							var arr = [];
							this.$store.state.tempTradeapply.paramList.forEach(function(a) {
								arr.push(a.lhsiTranActualLever);
								x.shoushu = arr;
							}.bind(this));
							break;
						case 15:
							//						return '美黄金'
							var arr = [];
							this.$store.state.tempTradeapply.paramList.forEach(function(a) {
								arr.push(a.agTranActualLever);
								x.shoushu = arr;
							}.bind(this));
							break;
						case 16:
							//						return 'H股指数'
							var arr = [];
							this.$store.state.tempTradeapply.paramList.forEach(function(a) {
								arr.push(a.hIndexActualLever);
								x.shoushu = arr;
							}.bind(this));
							break;
						case 17:
							//						return '小H股指数'
							var arr = [];
							this.$store.state.tempTradeapply.paramList.forEach(function(a) {
								arr.push(a.xhIndexActualLever);
								x.shoushu = arr;
							}.bind(this));
							break;
						case 18:
							//						return '美铜'
							var arr = [];
							this.$store.state.tempTradeapply.paramList.forEach(function(a) {
								arr.push(a.aCopperActualLever);
								x.shoushu = arr;
							}.bind(this));
							break;
						case 19:
							//						return '美白银'
							var arr = [];
							this.$store.state.tempTradeapply.paramList.forEach(function(a) {
								arr.push(a.aSilverActualLever);
								x.shoushu = arr;
							}.bind(this));
							break;
						case 20:
							//						return '小原油'
							var arr = [];
							this.$store.state.tempTradeapply.paramList.forEach(function(a) {
								arr.push(a.smaActualLever);
								x.shoushu = arr;
							}.bind(this));
							break;
						case 21:
							//						return '迷你德国DAX指数'  //迷你德国DAX指数
							var arr = [];
							this.$store.state.tempTradeapply.paramList.forEach(function(a) {
								arr.push(a.daxtranMinActualLever);
								x.shoushu = arr;
							}.bind(this));
							break;
						case 22:
							//						return '天然气'
							var arr = [];
							this.$store.state.tempTradeapply.paramList.forEach(function(a) {
								if(a.naturalGasActualLever==null){
									arr.push(0);
								}else{
									arr.push(a.naturalGasActualLever);
								}
								x.shoushu = arr;
							}.bind(this));
							break;
					}
				}.bind(this));
			}.bind(this), function(e) {
				this.$children[0].isShow = true;
				this.msg = '网络不给力，请稍后再试！';
			});
		}
	}
</script>

<style scoped lang="less">
	@import url("../assets/css/main.less");
	@width: 100%;
	/*ip5*/
	@media(max-width:370px) {
		#tradeapply {
			padding-bottom: 50px*@ip5;
			background-color: #1b1b26;
			padding-top: 50px*@ip5;
			height: 736px*@ip5 - 20px;
		}
		.mui-slider {
			width: @width;
			height: 150px*@ip5;
			background-color: #242633;
		}
		.mui-slider-group {
			height: 150px*@ip5;
		}
		.mui-slider-item{
			width: @width;
			height: 150px*@ip5;
			overflow: hidden;
			padding: 15px*@ip5;
		}
		img {
			width: 100%;
			height: 120px*@ip5;
			vertical-align: bottom;
		}
		.datalist {
			overflow-y: scroll;
		}
		h5 {
			margin-top: 14px*@ip5;
			color: #ffd400;
			font-size: 16px*@ip5;
		}
		li,
		p,
		span {
			font-size: 14px*@ip5;
		}
		.datalist>li {
			width: @width;
			height: 100px*@ip5;
			background-color: #242633;
			padding: 0 15px*@ip5;
			border-top: 5px solid #1b1b26;
		}
		p {
			color: #9ba1c2;
		}
		.cl {
			padding: 5px*@ip5 0;
			border-top: 1px solid #1b1b26;
		}
		.cl li {
			color: #9ba1c2;
		}
		.cl span {
			color: #fff;
		}
		ol {
			height: 100%;
		}
		ol>li:first-child {
			overflow: hidden;
			height: 40px*@ip5;
			line-height: 40px*@ip5;
		}
		ol>li:nth-child(2) {
			height: 55%;
		}
		#refresh {
			color: #fff;
			background-color: transparent;
			border: none;
			outline: none;
			position: fixed;
			z-index: 1000;
			right: 10%;
			top: 2%;
		}
		ol>li:nth-child(2)>ul>li {
			
			height: 28px*@ip5;
			line-height: 28px*@ip5;
			transform: scale(0.9);
			margin-left: -15px;
		}
		ol>li:nth-child(2)>ul>li:nth-child(2) {
			margin-top: -8px;
		}
	}
	/*ip6*/
	@media (min-width:371px) and (max-width:410px) {
		#tradeapply {
			padding-bottom: 50px*@ip6;
			background-color: #1b1b26;
			padding-top: 50px*@ip6;
			height: 736px*@ip6 - 20px;
		}
		.mui-slider {
			width: @width;
			height: 150px*@ip6;
			background-color: #242633;
		}
		.mui-slider-group {
			height: 150px*@ip6;
		}
		.mui-slider-item{
			width: @width;
			height: 150px*@ip6;
			overflow: hidden;
			padding: 15px*@ip6;
		}
		img {
			width: 100%;
			height: 120px*@ip6;
			vertical-align: bottom;
		}
		.datalist {
			overflow-y: scroll;
		}
		h5 {
			color: #ffd400;
			font-size: 16px*@ip6;
			margin-top: 14px*@ip6;
		}
		li,
		p,
		span {
			font-size: 14px*@ip6;
		}
		.datalist>li {
			width: @width;
			height: 100px*@ip6;
			background-color: #242633;
			padding: 0 15px*@ip6;
			border-top: 5px solid #1b1b26;
		}
		p {
			color: #9ba1c2;
		}
		.cl {
			padding: 5px*@ip6 0;
			border-top: 1px solid #1b1b26;
		}
		.cl li {
			color: #9ba1c2;
		}
		.cl span {
			color: #fff;
		}
		ol {
			height: 100%;
		}
		ol>li:first-child {
			overflow: hidden;
			height: 40px*@ip6;
			line-height: 40px*@ip6;
		}
		ol>li:nth-child(2) {
			height: 55%;
		}
		#refresh {
			color: #fff;
			background-color: transparent;
			border: none;
			outline: none;
			position: fixed;
			z-index: 1000;
			right: 10%;
			top: 2%;
		}
	}
	/*ip6p及以上*/
	@media (min-width:411px) {
		#tradeapply {
			padding-bottom: 50px;
			background-color: #1b1b26;
			padding-top: 50px;
			height: 736px-20px;
		}
		.mui-slider {
			width: @width;
			height: 150px;
			background-color: #242633;
		}
		.mui-slider-group {
			height: 150px;
		}
		.mui-slider-item{
			width: @width;
			height: 150px;
			overflow: hidden;
			padding: 15px;
		}
		img {
			width: 100%;
			height: 120px;
			vertical-align: bottom;
		}
		.datalist {
			overflow-y: scroll;
		}
		h5 {
			color: #ffd400;
			font-size: 16px;
			margin-top: 14px
		}
		li,
		p,
		span {
			font-size: 14px;
		}
		.datalist>li {
			width: @width;
			height: 100px;
			background-color: #242633;
			padding: 0 15px;
			border-top: 5px solid #1b1b26;
		}
		p {
			color: #9ba1c2;
		}
		.cl {
			padding: 5px 0;
			border-top: 1px solid #1b1b26;
		}
		.cl li {
			color: #9ba1c2;
		}
		.cl span {
			color: #fff;
		}
		ol {
			height: 100%;
		}
		ol>li:first-child {
			overflow: hidden;
			height: 40px;
			line-height: 40px;
		}
		ol>li:nth-child(2) {
			height: 55%;
		}
		#refresh {
			color: #fff;
			background-color: transparent;
			border: none;
			outline: none;
			position: fixed;
			z-index: 1000;
			right: 10%;
			top: 2%;
		}
	}
</style>