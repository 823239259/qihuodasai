<template>
	<div id="detailTopbar" :class="colorClass">
		<tipsDialog :msg="sysMsg" ref="dialog"></tipsDialog>
		<back @tap.native='clearPositionListCont'></back>
		<div class="title">
			<h4 class="fontwhite">{{cname}}</h4>
			<h6 class="fontwhite"><span>{{cnum}}</span><span>{{mc}}</span></h6>
		</div>
		<!--<h3 v-show="!istitle">{{user}}</h3>-->
		<span class="rule" @tap="toRole">规则</span>
		<refresh @tap.native="tradeRefresh"></refresh>
		<menus></menus>
	</div>
</template>

<script>
	import back from '../components/back.vue'
	import refresh from '../components/Refresh.vue'
	import menus from '../components/menu.vue'
	import tipsDialog from '../components/tipsDialog.vue'
	export default{
		name:'detailTopbar',
		components:{back, refresh, menus, tipsDialog},
		props:['cname','cnum','mc','colorName'],
		data(){
			return{
				msg: '',
//				istitle: true,
//				user: '',
				pathName: ''
			}
		},
		computed: {
			colorClass: function(){
				if(this.colorName == 'red'){
					return 'red';
				}
			},
			sysMsg: function(){
				return this.msg;
			},
			detail() {
				return this.$store.state.market.currentdetail;
			},
			bottomshow(){
				return this.$store.state.isshow.bottomshow;
			}
		},
		methods:{
			toRole: function(){
				var name = this.detail.CommodityNo;
				switch (name){
					case 'CL':
						this.pathName = 'cl';
						break;
					case 'HSI':
						this.pathName = 'hsi';
						break;
					case 'GC':
						this.pathName = 'gc';
						break;
					case 'FDAX':
						this.pathName = 'fdax';
						break;
					case 'MHI':
						this.pathName = 'mhi';
						break;
					case 'CN':
						this.pathName = 'cn';
						break;
					case 'HG':
						this.pathName = 'hg';
						break;
					case 'SI':
						this.pathName = 'si';
						break;
					case 'YM':
						this.pathName = 'ym';
						break;
					case 'NQ':
						this.pathName = 'nq';
						break;
					case 'ES':
						this.pathName = 'es';
						break;
					case 'NK':
						this.pathName = 'nk';
						break;
					case 'HHI':
						this.pathName = 'hhi';
						break;
					case 'MCH':
						this.pathName = 'mch';
						break;
					case 'QM':
						this.pathName = 'qm';
						break;
					case 'FDXM':
						this.pathName = 'fdxm';
						break;
					case 'NG':
						this.pathName = 'ng';
						break;
					default:
						break;
				}
				this.$router.push({path: '/' + this.pathName});
			},
			clearPositionListCont:function(){
				this.$store.state.market.positionListCont=[];
				this.$router.push({path: '/index', query: {isBack: 1}});
			},
			tradeRefresh: function(){
				if(this.$parent.iconIsconnected == true){
					this.$refs.dialog.isShow = true;
					this.msg = '网络未连接，交易不能刷新！';
					return false;
				}else{
					this.$router.push({path: '/index', query: {isBack: 1}});
				}
				
			}
		},
		activated: function(){
			if(localStorage.tradeUser && this.bottomshow == true){
//				this.user = JSON.parse(localStorage.tradeUser).username;
				'cname','cnum','mc'
				this.cname = '期货模拟账号';
				this.cnum = JSON.parse(localStorage.tradeUser).username;
				this.mc = '';
//				this.istitle = false;
			}
		}
	}
</script>

<style scoped lang="less">
@import url("../assets/css/main.less");
@height:50px;
/*ip6p及以上*/
@media (min-width:411px) {
    #detailTopbar{
		width: 100%;
		height: @height;
		background-color: #242633;
		position: fixed;
		top: 0;
		z-index: 1100;
		&.red{
			background: #a73d42;
		}
	}
	#detailTopbar .title{
		width: 100%;
		height: 50px;
		margin-top: 8px;
		padding-left: 45px;	
	}
	#detailTopbar .title h4{
		font-weight: normal;
		font-size: 16px;
		text-align: left;
	}
	#detailTopbar .title h6{
		font-weight: 200;
		font-size: 12px;
		text-align: left;
	}
	#back{
		position: absolute;
		left: 0;
		top: 0;
	}
	h3{
		line-height: 50px;
		font-size: 16px;
		font-weight: normal;
		color: #fff;
		position: fixed;
		top: 0;
		left: 50px;		
	}
	.rule{
		display: inline-block;
		width: 50px;
		height: 50px;
		line-height: 50px;
		text-align: center;
		color: #fff;
		font-size: 16px;
		position: fixed;
		top: 0;
		right: 100px;
	}
	#refresh{
		position: absolute;
		top: 0;
		right: 50px;
	}
	#menus{
		position: absolute;
		top: 0;
		right: 0;
		z-index: 1100;
	}
	#menus ol{
		z-index: 1101;
	}
	#menus ol li{
		z-index: 1102;
	}
	h6{
		margin-top: 6px;
	}
}

/*ip6*/
@media (min-width:371px) and (max-width:410px) {
	#detailTopbar{
		width: 100%;
		height: @height*@ip6;
		background-color: #242633;
		position: fixed;
		top: 0;
		z-index: 1100;
		&.red{
			background: #a73d42;
		}
	}
	#detailTopbar .title{
		width: 100%;
		height: 50px*@ip6;
		margin-top: 8px*@ip6;
		padding-left: 45px*@ip6;	
	}
	#detailTopbar .title h4{
		font-weight: normal;
		font-size: 16px*@ip6;
		text-align: left;
	}
	#detailTopbar .title h6{
		font-weight: 200;
		font-size: 12px*@ip6;
		text-align: left;
	}
	#back{
		position: absolute;
		left: 0;
		top: 0;
	}
	h3{
		line-height: 50px*@ip6;
		font-size: 16px*@ip6;
		font-weight: normal;
		color: #fff;
		position: fixed;
		top: 0;
		left: 50px*@ip6;		
	}
	.rule{
		display: inline-block;
		width: 50px*@ip6;
		height: 50px*@ip6;
		line-height: 50px*@ip6;
		text-align: center;
		color: #fff;
		font-size: 16px*@ip6;
		position: fixed;
		top: 0;
		right: 100px*@ip6;
	}
	#refresh{
		position: absolute;
		top: 0;
		right: 50px*@ip6;
	}
	#menus{
		position: absolute;
		top: 0;
		right: 0;
	}
	h6{
		margin-top: 6px*@ip6;
	}
}

/*ip5*/
@media(max-width:370px) {
	#detailTopbar{
		width: 100%;
		height: @height*@ip5;
		background-color: #242633;
		position: fixed;
		top: 0;
		z-index: 1100;
		&.red{
			background: #a73d42;
		}
	}
	#detailTopbar .title{
		width: 100%;
		height: 50px*@ip5;
		margin-top: 8px*@ip5;
		padding-left: 45px*@ip5;	
	}
	#detailTopbar .title h4{
		font-weight: normal;
		font-size: 16px*@ip5;
		text-align: left;
	}
	#detailTopbar .title h6{
		font-weight: 200;
		font-size: 12px*@ip5;
		text-align: left;
	}
	#back{
		position: absolute;
		left: 0;
		top: 0;
	}
	h3{
		line-height: 50px*@ip5;
		font-size: 16px*@ip5;
		font-weight: normal;
		color: #fff;
		position: fixed;
		top: 0;
		left: 50px*@ip5;		
	}
	.rule{
		display: inline-block;
		width: 50px*@ip5;
		height: 50px*@ip5;
		line-height: 50px*@ip5;
		text-align: center;
		color: #fff;
		font-size: 16px*@ip5;
		position: fixed;
		top: 0;
		right: 100px*@ip5;
	}
	#refresh{
		position: absolute;
		top: 0;
		right: 50px*@ip5;
	}
	#menus{
		position: absolute;
		top: 0;
		right: 0;
	}
	h6{
		margin-top: 6px*@ip5;
	}
}
</style>
