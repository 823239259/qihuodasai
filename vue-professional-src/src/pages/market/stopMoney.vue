<template>
	<div id="conditions">
		<div class="head">
			<topbar title="止损止赢"></topbar>
			<back></back>
			<refresh></refresh>
		</div>
		<div class="page_cont">
			<div class="tab_box" id="tabBox">
				<template v-for="key in tabList">
					<div class="tab_box_col" @tap="showCont">
						<span>{{key.nav}}</span>
					</div>
				</template>
			</div>
			<div id="noCont" class="list" v-if="isShow">
				<ul class="list_cont_box">
					<li class="list_head">
						<span>合约名称</span>
						<span>状态</span>
						<span>多空</span>
						<span>类别</span>
						<span>手数</span>
						<span>触发条件</span>
						<span>委托价</span>
						<span>有效期</span>
						<span>下单时间</span>
					</li>
					<template v-for="k in noListCont">
						<li @tap="listTap" id="123">
							<div class="list_cont">
								<span>{{k.name}}</span>
								<span :class="{red: k.type_color == 'red', green: k.type_color == 'green'}">{{k.status}}</span>
								<span>{{k.type}}</span>
								<span>{{k.types}}</span>
								<span>{{k.num}}</span>
								<span>{{k.conditions}}</span>
								<span>{{k.price}}</span>
								<span>{{k.term}}</span>
								<span>{{k.time}}</span>
							</div>
						</li>
					</template>
				</ul>
				<div class="list_tools">
					<cbtn name="暂停"></cbtn>
					<cbtn name="修改"></cbtn>
					<cbtn name="删除"></cbtn>
				</div>
			</div>
			<div id="yesCont" class="list" v-else="isShow">
				<ul class="list_cont_box">
					<li class="list_head">
						<span>合约名称</span>
						<span>状态</span>
						<span>多空</span>
						<span>类别</span>
						<span>手数</span>
						<span>触发条件</span>
						<span>委托价</span>
						<span>有效期</span>
						<span>下单时间</span>
					</li>
					<template v-for="k in noListCont">
						<li @tap="listTap" id="123">
							<div class="list_cont">
								<span>{{k.name}}</span>
								<span :class="{red: k.type_color == 'red', green: k.type_color == 'green'}">{{k.status}}</span>
								<span>{{k.type}}</span>
								<span>{{k.types}}</span>
								<span>{{k.num}}</span>
								<span>{{k.conditions}}</span>
								<span>{{k.price}}</span>
								<span>{{k.term}}</span>
								<span>{{k.time}}</span>
							</div>
						</li>
					</template>
				</ul>
			</div>
		</div>
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
						name: 'CL1710',
						status: '运行中',
						type: '多',
						types: '限价止损',
						num: 1,
						conditions: '触发价:47.50',
						price: '市价',
						term: '当日有效',
						time: '2017-08-28 10:01:04'
					}
				],
				yesListCont:[
					{
						name: 'CL1710',
						status: '运行中',
						type: '多',
						types: '限价止损',
						num: 1,
						conditions: '触发价:47.50',
						price: '市价',
						term: '当日有效',
						time: '2017-08-28 10:01:04'
					},
				],
			}
		},
		computed:{
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
			listTap: function(obj){
				if(!$(obj.currentTarget).hasClass("current")){
					$(obj.currentTarget).addClass("current");
					$(obj.currentTarget).siblings().removeClass("current");
					this.orderListId = $(obj.currentTarget).attr("id");
				}else{
					$(obj.currentTarget).removeClass("current");
					this.orderListId =null;
				}
			},
		},
		mounted: function(){
			//页面滚动高度计算
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
		#refresh{
			position: fixed;
			top: 0;
			right: 0;
			z-index: 1000;
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
		.list{
			ul{
				width: 100%;
				padding: 0;
				overflow-y: scroll;
			}
			li{
				width: 900px;
				background: @deepblue;
				padding-left: 15px;
				border-top: 1px solid @black;
				&.current{
					background: #2d3040;
				}
				&.list_head{
					height: 44px;
					background: #36394d;
				}
				.list_cont{
					height: 44px;
					&.current{
						background: #2d3040;
					}
				}
				span{
					display: inline-block;
					height: 44px;
					line-height: 44px;
					overflow: hidden;
					color: @lightblue;
					font-size: @fs14;
					margin: 0 0.4%;
					&:nth-child(1){
						width: 130px;
					}
					&:nth-child(2){
						width: 65px;
					}
					&:nth-child(3){
						width: 50px;
					}
					&:nth-child(4){
						width: 80px;
					}
					&:nth-child(5){
						width: 50px;
					}
					&:nth-child(6){
						width: 120px;
					}
					&:nth-child(7){
						width: 60px;
					}
					&:nth-child(8){
						width: 80px;
					}
					&:nth-child(9){
						width: 130px;
					}
					&.red{
						color: @red;
					}
					&.green{
						color: @green;
					}
				}
			}
			.list_tools{
				position: fixed;
				bottom: 0;
				left: 0;
				width: 100%;
				height: 44px;
				overflow: hidden;
				text-align: center;
				#conditionBtn{
					display: inline-block;
					margin: 6px 10px 0 10px;
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
		background: @black;
	}
	.head{
		#back{
			position: fixed;
			z-index: 1000;
			top: 0;
			left: 0;
		}
		#refresh{
			position: fixed;
			top: 0;
			right: 0;
			z-index: 1000;
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
		.list{
			ul{
				width: 100%;
				padding: 0;
				overflow-y: scroll;
			}
			li{
				width: 900px*@ip6;
				background: @deepblue;
				padding-left: 15px*@ip6;
				border-top: 1px solid @black;
				&.current{
					background: #2d3040;
				}
				&.list_head{
					height: 44px*@ip6;
					background: #36394d;
				}
				.list_cont{
					height: 44px*@ip6;
					&.current{
						background: #2d3040;
					}
				}
				span{
					display: inline-block;
					height: 44px*@ip6;
					line-height: 44px*@ip6;
					overflow: hidden;
					color: @lightblue;
					font-size: @fs14*@ip6;
					margin: 0 0.4%;
					&:nth-child(1){
						width: 130px*@ip6;
					}
					&:nth-child(2){
						width: 65px*@ip6;
					}
					&:nth-child(3){
						width: 50px*@ip6;
					}
					&:nth-child(4){
						width: 80px*@ip6;
					}
					&:nth-child(5){
						width: 50px*@ip6;
					}
					&:nth-child(6){
						width: 120px*@ip6;
					}
					&:nth-child(7){
						width: 60px*@ip6;
					}
					&:nth-child(8){
						width: 80px*@ip6;
					}
					&:nth-child(9){
						width: 130px*@ip6;
					}
					&.red{
						color: @red;
					}
					&.green{
						color: @green;
					}
				}
			}
			.list_tools{
				position: fixed;
				bottom: 0;
				left: 0;
				width: 100%;
				height: 44px*@ip6;
				overflow: hidden;
				text-align: center;
				#conditionBtn{
					display: inline-block;
					margin: 6px*@ip6 10px*@ip6 0 10px*@ip6;
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
		background: @black;
	}
	.head{
		#back{
			position: fixed;
			z-index: 1000;
			top: 0;
			left: 0;
		}
		#refresh{
			position: fixed;
			top: 0;
			right: 0;
			z-index: 1000;
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
		.list{
			ul{
				width: 100%;
				padding: 0;
				overflow-y: scroll;
			}
			li{
				width: 940px*@ip5;
				background: @deepblue;
				padding-left: 15px*@ip5;
				border-top: 1px solid @black;
				&.current{
					background: #2d3040;
				}
				&.list_head{
					height: 44px*@ip5;
					background: #36394d;
				}
				.list_cont{
					height: 44px*@ip5;
					&.current{
						background: #2d3040;
					}
				}
				span{
					display: inline-block;
					height: 44px*@ip5;
					line-height: 44px*@ip5;
					overflow: hidden;
					color: @lightblue;
					font-size: @fs14*@ip5;
					margin: 0 0.4%;
					&:nth-child(1){
						width: 130px*@ip5;
					}
					&:nth-child(2){
						width: 65px*@ip5;
					}
					&:nth-child(3){
						width: 50px*@ip5;
					}
					&:nth-child(4){
						width: 80px*@ip5;
					}
					&:nth-child(5){
						width: 50px*@ip5;
					}
					&:nth-child(6){
						width: 120px*@ip5;
					}
					&:nth-child(7){
						width: 60px*@ip5;
					}
					&:nth-child(8){
						width: 80px*@ip5;
					}
					&:nth-child(9){
						width: 160px*@ip5;
					}
					&.red{
						color: @red;
					}
					&.green{
						color: @green;
					}
				}
			}
			.list_tools{
				position: fixed;
				bottom: 0;
				left: 0;
				width: 100%;
				height: 44px*@ip5;
				overflow: hidden;
				text-align: center;
				#conditionBtn{
					display: inline-block;
					margin: 6px*@ip5 10px*@ip5 0 10px*@ip5;
				}
			}
		}
	}
}
	
</style>
