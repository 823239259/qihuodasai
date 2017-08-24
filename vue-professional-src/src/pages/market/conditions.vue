<template>
	<div id="conditions">
		<div class="head">
			<topbar title="条件单"></topbar>
			<back></back>
			<i class="add"></i>
		</div>
		<div class="page_cont">
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
		
	</div>
</template>

<script>
	import topbar from '../../components/Topbar.vue'
	import back from '../../components/back.vue'
	import cbtn from '../../components/conditionBtn.vue'
	import orderlist from '../../components/orderList.vue'
	export default{
		name:'conditions',
		components:{topbar, back, cbtn, orderlist},
		data(){
			return {
				isShow: true,
				tabList: [{nav:'未触发列表'},{nav:'已触发列表'}],
				noListCont:[
					{
						name: '美原油10',
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
			var screenHeight = window.screen.height;
			$("#conditions").css("height", screenHeight + "px");
			var h = $("#topbar").height() + $(".tab_box").height() + $(".list ul:first-child").height();
			$(".list_cont_box").css("height", screenHeight - h - 20 + 'px');
		},
		activated: function(){
			//不更新画图
			this.$store.state.isshow.isklineshow = false;
			this.$store.state.isshow.isfensshow = false;
			this.$store.state.isshow.islightshow =  false;
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
			overflow: hidden;
			padding-top: 50px;
			background: @black;
		}
		.head{
			#back{
				position: fixed;
				z-index: 1000;
				top: 0;
				left: 0;
			}
			.add{
				display: block;
				width: 50px;
				height: 50px;
				background: url(../../assets/img/add.png) no-repeat 14px 14px;
				background-size: 22px 22px;
				position: fixed;
				z-index: 1000;
				top: 0;
				right: 0;
			}
		}
		.page_cont{
			width: 100%;
			position: fixed;
			top: 50px;
			left: 0;
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
	}
	
	/*ip6*/
	@media (min-width:371px) and (max-width:410px) {
	    #conditions{
			width: 100%;
			overflow: hidden;
			padding-top: 50px*@ip6;
			background: @black;
		}
		.head{
			position: relative;
			#back{
				position: fixed;
				z-index: 1000;
				top: 0;
				left: 0;
			}
			.add{
				display: block;
				width: 50px*@ip6;
				height: 50px*@ip6;
				background: url(../../assets/img/add.png) no-repeat 14px*@ip6 14px*@ip6;
				background-size: 22px*@ip6 22px*@ip6;
				position: fixed;
				z-index: 1000;
				top: 0;
				right: 0;
			}
		}
		.page_cont{
			width: 100%;
			position: fixed;
			top: 50px*@ip6;
			left: 0;
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
	}
	
	/*ip5*/
	@media(max-width:370px) {
		#conditions{
			width: 100%;
			overflow: hidden;
			padding-top: 50px*@ip5;
			background: @black;
		}
		.head{
			position: relative;
			#back{
				position: fixed;
				z-index: 1000;
				top: 0;
				left: 0;
			}
			.add{
				display: block;
				width: 50px*@ip5;
				height: 50px*@ip5;
				background: url(../../assets/img/add.png) no-repeat 14px*@ip5 14px*@ip5;
				background-size: 22px*@ip5 22px*@ip5;
				position: fixed;
				z-index: 1000;
				top: 0;
				right: 0;
			}
		}
		.page_cont{
			width: 100%;
			position: fixed;
			top: 50px*@ip5;
			left: 0;
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
	}
	
</style>
