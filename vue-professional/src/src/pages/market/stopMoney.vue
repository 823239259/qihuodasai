<template>
	<div id="conditions">
		<div class="head">
			<topbar title="止损止赢"></topbar>
			<back></back>
			<refresh></refresh>
		</div>
		<div class="tab_box" id="tabBox">
			<template v-for="key in tabList">
				<div class="tab_box_col" @tap="showCont">
					<span>{{key.nav}}</span>
				</div>
			</template>
		</div>
		<orderlist :val="noContEvent" id="noCont" v-if="isShow"></orderlist>
		<orderlist :val="yesContEvent"  id="yesCont" v-else="isShow"></orderlist>
	</div>
</template>

<script>
	import topbar from '../../components/Topbar.vue'
	import back from '../../components/back.vue'
	import cbtn from '../../components/conditionBtn.vue'
	import refresh from '../../components/Refresh.vue'
	import orderlist from '../../components/orderList.vue'
	export default{
		name:'conditions',
		components:{topbar, back, cbtn, refresh, orderlist},
		data(){
			return {
				isShow: true,
				tabList: [{nav:'未触发列表'},{nav:'已触发列表'}],
				noListCont:[
					{
						name: '美原油09',
						type: '多',
						num: 12,
						price: '123.98',
						total: '4800.00',
						showbar: false,
						type_color: 'red',
						total_color: 'green'
					},
					{
						name: '美原油09',
						type: '空',
						num: 12,
						price: '123.98',
						total: '4800.00',
						showbar: false,
						type_color: 'green',
						total_color: 'red'
					},
					{
						name: '美原油09',
						type: '多',
						num: 12,
						price: '123.98',
						total: '4800.00',
						showbar: false,
						type_color: 'red',
						total_color: 'green'
					},
				],
				yesListCont:[
					{
						name: '美原油09',
						type: '空',
						num: 12,
						price: '123.98',
						total: '4800.00',
						showbar: false,
						type_color: 'red',
						total_color: 'green'
					},
				],
			}
		},
		computed:{
			noContEvent: function(){
				return JSON.stringify(this.noListCont);
			},
			yesContEvent: function(){
				return JSON.stringify(this.yesListCont);
			}
		},
		methods: {
			showCont: function(e){
				$(e.currentTarget).find("span").addClass('current');
				$(e.currentTarget).siblings().find("span").removeClass('current')
				if($(e.currentTarget).index() == 0){
					this.isShow = true;
				}else{
					this.isShow = false;
				}
			},
		},
		mounted: function(){
			$("#tabBox .tab_box_col:first-child span").addClass("current");
			$("#conditions").css("height",window.screen.height + "px");
			$(".list_cont_box").css("height", window.screen.height - 136 + 'px');
		}
	}
</script>

<style scoped lang="less">
	@import url("../../assets/css/main.less");
	@import url("../../assets/css/base.less");
	/*ip6p及以上*/
	@media (min-width:411px) {
	    #conditions{
			width: 100%;
			padding-top: 50px;
			padding-bottom: 50px;
			background: @black;
		}
		.head{
			position: relative;
			#back{
				position: absolute;
				z-index: 1000;
				top: -35px;
				left: 15px;
			}
			.add{
				display: block;
				width: 22px;
				height: 22px;
				background: url(../../assets/img/add.png) no-repeat center center;
				background-size: 100% 100%;
				position: absolute;
				z-index: 1000;
				top: -37px;
				right: 15px;
			}
			#refresh{
				position: absolute;
				top: -35px;
				right: 15px;
				z-index: 1000;
			}
		}
		.tab_box{
			height: 44px;
			background: @deepblue;
			.tab_box_col{
				float: left;
				width: 50%;
				text-align: center;
				span{
					display: inline-block;
					height: 44px;
					line-height: 44px;
					color: @lightblue;
					font-size: @fs14;
					&.current{
						color: @yellow;
						border-bottom: 4px solid @yellow;
					}
				}	
			}
		}
	}
	
	/*ip6*/
	@media (min-width:371px) and (max-width:410px) {
	    #conditions{
			width: 100%;
			padding-top: 50px*@ip6;
			padding-bottom: 50px*@ip6;
			background: @black;
		}
		.head{
			position: relative;
			#back{
				position: absolute;
				z-index: 1000;
				top: -35px*@ip6;
				left: 15px*@ip6;
			}
			.add{
				display: block;
				width: 22px*@ip6;
				height: 22px*@ip6;
				background: url(../../assets/img/add.png) no-repeat center center;
				background-size: 100% 100%;
				position: absolute;
				z-index: 1000;
				top: -37px*@ip6;
				right: 15px*@ip6;
			}
			#refresh{
				position: absolute;
				top: -35px*@ip6;
				right: 15px*@ip6;
				z-index: 1000;
			}
		}
		.tab_box{
			height: 44px*@ip6;
			background: @deepblue;
			.tab_box_col{
				float: left;
				width: 50%;
				text-align: center;
				span{
					display: inline-block;
					height: 44px*@ip6;
					line-height: 44px*@ip6;
					color: @lightblue;
					font-size: @fs14*@ip6;
					&.current{
						color: @yellow;
						border-bottom: 4px*@ip6 solid @yellow;
					}
				}	
			}
		}
	}
	
	/*ip5*/
	@media(max-width:370px) {
		#conditions{
			width: 100%;
			padding-top: 50px*@ip5;
			padding-bottom: 50px*@ip5;
			background: @black;
		}
		.head{
			position: relative;
			#back{
				position: absolute;
				z-index: 1000;
				top: -35px*@ip5;
				left: 15px*@ip5;
			}
			.add{
				display: block;
				width: 22px*@ip5;
				height: 22px*@ip5;
				background: url(../../assets/img/add.png) no-repeat center center;
				background-size: 100% 100%;
				position: absolute;
				z-index: 1000;
				top: -37px*@ip5;
				right: 15px*@ip5;
			}
			#refresh{
				position: absolute;
				top: -35px*@ip5;
				right: 15px*@ip5;
				z-index: 1000;
			}
		}
		.tab_box{
			height: 44px*@ip5;
			background: @deepblue;
			.tab_box_col{
				float: left;
				width: 50%;
				text-align: center;
				span{
					display: inline-block;
					height: 44px*@ip5;
					line-height: 44px*@ip5;
					color: @lightblue;
					font-size: @fs14*@ip5;
					&.current{
						color: @yellow;
						border-bottom: 4px*@ip5 solid @yellow;
					}
				}	
			}
		}
	}
	
</style>
