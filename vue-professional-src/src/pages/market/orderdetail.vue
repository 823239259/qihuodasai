<template>
	<div id="orderdetail">
		<tipsDialog :msg="sysMsg" ref="dialog"></tipsDialog>
		<div class="disconnect" v-show="isconnected">
			<i class="icon"></i>
			<span>交易、行情未连接，网络恢复时会自动连接！</span>
		</div>
		<topbar :cname="cname" :cnum="cnum" :colorName="bg" ref="topbar"></topbar>
		<i class="icon_connected" v-show="iconIsconnected"></i>
		<selectbar ref="selectBar"></selectbar>
		<dish v-if="pshow"></dish>
		<tradebottom v-if='s'></tradebottom>
		<tradecenter v-if='jshow'></tradecenter>
		<chartfens :detail='detail' v-if='fshow'></chartfens>
		<kline v-if='kshow'></kline>
		<lightchart v-if='sshow'></lightchart>
		<novice v-if="helpshow"></novice>
		<orderTypeList ref="orderTypeList"></orderTypeList>
	</div>
</template>

<script>
	import topbar from '../../components/detailTopbar.vue'
	import selectbar from '../../components/detailSelectbar.vue'
	import tradebottom from '../../components/tradeBottom.vue'
	import tradecenter from '../../components/tradeCenter.vue'
	import dish from '../../components/dish.vue'
	import chartfens from '../../components/chartfens.vue'
	import kline from '../../components/kline.vue'
	import lightchart from '../../components/lightchart.vue'
	import tipsDialog from '../../components/tipsDialog.vue'
	import pro from '../../assets/common.js'
	import novice from '../noviceTrade.vue'
	import orderTypeList from '../../components/orderTypeList.vue'
	export default {
		name: 'orderdetail',
		components: {topbar, selectbar, tradebottom, tradecenter, dish, chartfens, kline, lightchart, tipsDialog, novice, orderTypeList},
		data() {
			return {
				commodity_name: '',
				EngName: '',
				msg: '',
				iconIsconnected: false,
				isconnected: false,
				colors: '',
				userInfo: '',
				cname: '',
				cnum: '',
				touchFlag: true,
				timer: null
			}
		},
		computed: {
			bg: function(){
				if(this.colors) return this.colors;
			},
			PATH() {
				return this.$store.getters.PATH;
			},
			tradeConnectedMsg: function(){
				return this.$store.state.market.tradeConnectedMsg;
			},
			sysMsg: function(){
				return this.msg;
			},
			helpshow(){
				return this.$store.state.isshow.helpshow;
			},
			detail() {
				this.cname = this.$store.state.market.currentdetail.commodity_name;
				this.cnum = this.$store.state.market.currentdetail.commodity_no + this.$store.state.market.currentdetail.mainContract;
				return this.$store.state.market.currentdetail;
			},
			Parameters() {
				return this.$store.state.market.Parameters;
			},
			s() { //判断底部操作栏是否显示
				if(this.jshow || this.pshow) {
					return false;
				} else {
					return true;
				}
			},
			//映射初始值
			Data() {
				return this.$store.state.market.jsonData.Parameters.Data;
			},
			quoteSocket() {
				return this.$store.state.quoteSocket
			},
			layerOnRtnOrder(){
				return this.$store.state.market.layerOnRtnOrder;
			},
			tradeLoginSuccessMsg(){
				return this.$store.state.market.tradeLoginSuccessMsg;
			},
			sshow(){
				return this.$store.state.isshow.sshow;
			},
			fshow(){
				return this.$store.state.isshow.fshow;
			},
			kshow(){
				return this.$store.state.isshow.kshow;
			},
			pshow(){
				return this.$store.state.isshow.pshow;
			},
			jshow(){
				return this.$store.state.isshow.bottomshow;
			},
			errorMsg(){
				return this.$store.state.market.errorMsg;
			}
		},
		watch: {
			errorMsg: function(n, o){
				this.$router.push({path: '/index', query: {isBack: 1}});
			},
			layerOnRtnOrder: function(n, o){
				this.$children[0].isShow = true;
				this.msg = n.slice(0, -1);
			},
			tradeConnectedMsg: function(n, o){
				this.$children[0].isShow = true;
				this.msg = n;
				this.$router.push({path: '/index', query: {isBack: 1}});
			},
			tradeLoginSuccessMsg: function(n, o){
				this.$children[0].isShow = true;
				this.msg = n;
			}
		},
		methods: {
			getOperateDetails: function(){
				if(this.userInfo){
					this.$http.post(this.PATH + '/user/ftrade/list', {emulateJSON: true}, {
						headers: {
							'token':  this.userInfo.token,
							'secret': this.userInfo.secret
						},
						params: {},
						timeout: 5000
					}).then(function(e) {
						var data = e.body;
						if(data.success == true ){
							if(data.code == 1){
								var tradeList = data.data.tradeList;
								if(tradeList){
									tradeList.forEach(function(o, i){
										if(o.stateType == 4) this.$store.state.account.operateOrderLength += 1;
									}.bind(this));
								}
							}
						}else{
							
						}
					}.bind(this), function() {
						this.$refs.dialog.isShow = true;
						this.msg = '网络不给力，请稍后再试！'
					});
				}
			}
		},
		activated: function() {
			this.commodity_name = this.$route.query.CommodityName;
			this.EngName = this.$route.query.EngName;
			// 再次进入其他合约的时候修正到分时图
			this.$store.state.isshow.currentIndex = 1;
//			this.$store.state.isshow.sshow = false;
//			this.$store.state.isshow.fshow = true;
//			this.$store.state.isshow.kshow = false;
//			this.$store.state.isshow.pshow = false;
//			this.$store.state.isshow.bottomshow = false;
//			this.$store.state.isshow.islightshow = false;
		},
		deactivated () {
			this.$store.state.isshow.sshow = false;
			this.$store.state.isshow.fshow = false;
			this.$store.state.isshow.kshow = false;
			this.$store.state.isshow.pshow = false;
			this.$store.state.isshow.bottomshow = false;
			this.$store.state.isshow.islightshow = false;
			//this.$store.state.isshow.islightshow = false;
		},
		mounted: function() {
//			this.$store.state.isshow.fshow = true;
//			this.$store.state.isshow.pshow = false;
//			this.$store.state.isshow.sshow = false;
//			this.$store.state.isshow.kshow = false;
//			this.$store.state.isshow.bottomshow = false;
			//判断交易账号是否已登录
			if(localStorage.tradeUser != null || localStorage.tradeUser != undefined){
				this.$children[0].isShow = true;
				this.msg = this.tradeLoginSuccessMsg;
			}else{
				this.$children[0].isShow = false;
			}
			//获取本地用户信息
			if(localStorage.user) this.userInfo = JSON.parse(localStorage.user);
			//获取当前平台账户是否有操盘记录
			this.getOperateDetails();
			//滑动事件
			var obj = document.getElementById("orderdetail");
			var startx, starty, overx, overy;
			//touchstart事件,当鼠标点击屏幕时触发
			// obj.addEventListener('touchstart', function(event) { 
			// 	startx = event.touches[0].clientX;
			// 	starty = event.touches[0].clientY;
			// }, false);
			// //touchend事件,当鼠标离开屏幕时触发
			// obj.addEventListener('touchend', () => {
			// 	setTimeout(()=> this.touchFlag = true ,100)
			// },false);
			// //touchmove事件,当鼠标在屏幕移动时触发
			// obj.addEventListener('touchmove', (event) =>{
			// 	console.log(this.touchFlag)
			// 	if(!this.touchFlag) return;
			// 	clearTimeout(this.timer)
			// 	this.timer = setTimeout(()=>{
			// 		overx = event.touches[0].clientX;
			// 		overy = event.touches[0].clientY; 
			// 		let isshow = this.$store.state.isshow
			// 		if(startx-overx > 50){    //左滑动判断
			// 				this.touchFlag = false
			// 				if (isshow.currentIndex<4){
			// 					isshow.currentIndex++;
			// 				}
			// 		}else if ( startx - overx < -50) { //右滑动判断
			// 			this.touchFlag = false
			// 			if(isshow.currentIndex>0) {
			// 				isshow.currentIndex--;
			// 			}
			// 		}
			// 	},100)
			// }, false);
			
		},
		updated: function(){
			//判断网络
			pro.netIsconnected(function(){
				this.isconnected = true;
				setTimeout(function(){
					this.isconnected = false;
					this.iconIsconnected = true;
					this.colors = 'red';
				}.bind(this), 2000);
			}.bind(this), function(){
				this.iconIsconnected = false;
				this.colors = '';
			}.bind(this));
		}
	}
</script>

<style scoped lang="less">
@import url("../../assets/css/base.less");
/*ip6p及以上*/
@media (min-width:411px) {
    #orderdetail {
		padding-top: 90px;
		overflow: hidden;
	}
	.disconnect {
		width: 100%;
		height: 50px;
		overflow: hidden;
		background-color: #fff7cc;
		position: fixed;
		top: 0;
		left: 0;
		z-index: 1111;
		.icon{
			float: left;
			display: inline-block;
			width: 18px;
			height: 18px;
			overflow: hidden;
			background: url(../../assets/img/tanhao.png) no-repeat center center;
			background-size: 100% 100%;
			margin: 15px 15px 0 15px;
		}
		span{
			display: inline-block;
			line-height: 52px;
			font-size: @fs16;
			color: @black;
		}
	}
	.icon_connected{
		display: inline-block;
		width: 20px;
		height: 20px;
		background: url(../../assets/img/tanhao_03.png) no-repeat center center;
		background-size: 100% 100%;
	    margin: 6px 6px 0 0;
		position: fixed;
		top: 0;
		right: 0;
		z-index: 1111;
	}
}
/*ip6*/
@media (min-width:371px) and (max-width:410px) {
	#orderdetail {
		padding-top: 90px*@ip6;
		overflow: hidden;
	}
	.disconnect {
		width: 100%;
		height: 50px*@ip6;
		overflow: hidden;
		background-color: #fff7cc;
		position: fixed;
		top: 0;
		left: 0;
		z-index: 1111;
		.icon{
			float: left;
			display: inline-block;
			width: 18px*@ip6;
			height: 18px*@ip6;
			overflow: hidden;
			background: url(../../assets/img/tanhao.png) no-repeat center center;
			background-size: 100% 100%;
			margin: 15px*@ip6 15px*@ip6 0 15px*@ip6;
		}
		span{
			display: inline-block;
			line-height: 52px*@ip6;
			font-size: @fs16*@ip6;
			color: @black;
		}
	}
	.icon_connected{
		display: inline-block;
		width: 20px*@ip6;
		height: 20px*@ip6;
		background: url(../../assets/img/tanhao_03.png) no-repeat center center;
		background-size: 100% 100%;
	    margin: 6px*@ip6 6px*@ip6 0 0;
		position: fixed;
		top: 0;
		right: 0;
		z-index: 1111;
	}
}

/*ip5*/
@media(max-width:370px) {
	#orderdetail {
		padding-top: 90px*@ip5;
		overflow: hidden;
	}
	.disconnect {
		width: 100%;
		height: 50px*@ip5;
		overflow: hidden;
		background-color: #fff7cc;
		position: fixed;
		top: 0;
		left: 0;
		z-index: 1111;
		.icon{
			float: left;
			display: inline-block;
			width: 18px*@ip5;
			height: 18px*@ip5;
			overflow: hidden;
			background: url(../../assets/img/tanhao.png) no-repeat center center;
			background-size: 100% 100%;
			margin: 15px*@ip5 15px*@ip5 0 15px*@ip5;
		}
		span{
			display: inline-block;
			line-height: 52px*@ip5;
			font-size: @fs16*@ip5;
			color: @black;
		}
	}
	.icon_connected{
		display: inline-block;
		width: 20px*@ip5;
		height: 20px*@ip5;
		background: url(../../assets/img/tanhao_03.png) no-repeat center center;
		background-size: 100% 100%;
	    margin: 6px*@ip5 6px*@ip5 0 0;
		position: fixed;
		top: 0;
		right: 0;
		z-index: 1111;
	}
}

</style>