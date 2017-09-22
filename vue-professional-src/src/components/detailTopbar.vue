<template>
	<div id="detailTopbar" :class="colorClass">
		<tipsDialog :msg="sysMsg" ref="dialog"></tipsDialog>
		<back @tap.native='clearPositionListCont'></back>
		<div v-show="istitle">
			<h4 class="fontwhite">{{cname}}</h4>
			<h6 class="fontwhite"><span>{{cnum}}</span><span>{{mc}}</span></h6>
		</div>
		<h3 v-show="!istitle">{{user}}</h3>
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
				istitle: true,
				user: ''
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
			}
		},
		methods:{
			toRole: function(){
				this.$router.push({path: '/cl'});
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
			if(localStorage.tradeUser){
				this.user = JSON.parse(localStorage.tradeUser).username;
				this.istitle = false;
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
	#detailTopbar>div:nth-child(2){
		width: 100%;
		height: @height;
		margin-top: 8px;	
	}
	#detailTopbar>div:nth-child(2)>h4{
		font-weight: normal;
		font-size: 16px;
		text-align: center;
	}
	#detailTopbar>div:nth-child(2)>h6{
		font-weight: 200;
		font-size: 13px;
		text-align: center;
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
		&.red{
			background: #a73d42;
		}
	}
	#detailTopbar>div:nth-child(2){
		width: 100%;
		height: @height*@ip6;
		margin-top: 8px*@ip6;	
	}
	#detailTopbar>div:nth-child(2)>h4{
		font-weight: normal;
		font-size: 16px*@ip6;
		text-align: center;
	}
	#detailTopbar>div:nth-child(2)>h6{
		font-weight: 200;
		font-size: 13px*@ip6;
		text-align: center;
	}
	#back{
		position: absolute;
		left: 0;
		top: 0;
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
		&.red{
			background: #a73d42;
		}
	}
	#detailTopbar>div:nth-child(2){
		width: 100%;
		height: @height*@ip5;
		margin-top: 8px*@ip5;	
	}
	#detailTopbar>div:nth-child(2)>h4{
		font-weight: normal;
		font-size: 16px*@ip5;
		text-align: center;
	}
	#detailTopbar>div:nth-child(2)>h6{
		font-weight: 200;
		font-size: 13px*@ip6;
		text-align: center;
		transform: scale(0.9);
	}
	#back{
		position: absolute;
		left: 0;
		top: 0;
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
