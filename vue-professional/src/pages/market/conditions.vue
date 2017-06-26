<template>
	<div id="conditions">
		<div class="head">
			<topbar title="条件单"></topbar>
			<back></back>
			<i class="add"></i>
		</div>
		<div class="tab_box" id="tabBox">
			<template v-for="key in tabList">
				<div class="tab_box_col" @tap="showCont">
					<span>{{key.nav}}</span>
				</div>
			</template>
		</div>
		<div class="list" id="noCont" v-if="isShow">
			<ul>
				<li>
					<span>合约名称</span>
					<span>多空</span>
					<span>手数</span>
					<span>平仓均价</span>
					<span>浮动盈利</span>
				</li>
				<template v-for='key in noListCont'>
					<li>
						<div :class="[list_cont,{current:key.showbar}]" @tap="listTap(noListCont)">
							<span>{{key.name}}</span>
							<span :class="{red: key.type_color == 'red', green: key.type_color == 'green'}">{{key.type}}</span>
							<span>{{key.num}}</span>
							<span>{{key.price}}</span>
							<span :class="{red: key.type_color == 'red', green: key.type_color == 'green'}">{{key.total}}</span>
						</div>
						<transition name="fade" mode="out-in">
							<div class="list_tools " v-show="key.showbar">
								<cbtn name="暂停"></cbtn>
								<cbtn name="修改"></cbtn>
								<cbtn name="删除"></cbtn>
							</div>
						</transition>
					</li>
				</template>
			</ul>
		</div>
		<div class="list" id="yesCont" v-else>
			<ul>
				<li>
					<span>合约名称</span>
					<span>多空</span>
					<span>手数</span>
					<span>平仓均价</span>
					<span>浮动盈利</span>
				</li>
				<template v-for='key in yesListCont'>
					<li>
						<div :class="[list_cont,{current:key.showbar}]" @tap="listTap(yesListCont)">
							<span>{{key.name}}</span>
							<span :class="{red: key.type_color == 'red', green: key.type_color == 'green'}">{{key.type}}</span>
							<span>{{key.num}}</span>
							<span>{{key.price}}</span>
							<span :class="{red: key.type_color == 'red', green: key.type_color == 'green'}">{{key.total}}</span>
						</div>
						<transition name="fade" mode="out-in">
							<div class="list_tools " v-show="key.showbar">
								<cbtn name="暂停"></cbtn>
								<cbtn name="修改"></cbtn>
								<cbtn name="删除"></cbtn>
							</div>
						</transition>
					</li>
				</template>
			</ul>
		</div>
	</div>
</template>

<script>
	import topbar from '../../components/Topbar.vue'
	import back from '../../components/back.vue'
	import cbtn from '../../components/conditionBtn.vue'
	export default{
		name:'conditions',
		components:{topbar, back, cbtn},
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
			list_cont: function(){
				return 'list_cont'
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
			listTap: function(obj){
				var index  = $(event.currentTarget).parents("li").index();
				if(obj[index - 1].showbar == false){
					obj[index - 1].showbar = true;
				}else{
					obj[index - 1].showbar = false;
				}
			}
		},
		mounted: function(){
			$("#tabBox .tab_box_col:first-child span").addClass("current");
			$("#conditions").css("height",window.screen.height + "px");
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
		.list{
			li{
				width: 100%;
				background: @deepblue;
				border-top: 1px solid @black;
				&:first-child{
					padding: 0 3.62%;
					background: #36394d;
				}
				.list_cont{
					height: 44px;
					padding: 0 3.62%;
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
						width: 22.61%;
					}
					&:nth-child(2){
						width: 14.5%;
					}
					&:nth-child(3){
						width: 14.5%;
					}
					&:nth-child(4){
						width: 21.5%;
					}
					&:nth-child(5){
						width: 16.5%;
					}
					&.red{
						color: @red;
					}
					&.green{
						color: @green;
					}
				}
				.list_tools{
					height: 44px;
					overflow: hidden;
					border-top: 1px solid @black;
					background: #2d3040;
					padding: 0 3.62%;
					#conditionBtn{
						float: right;
						margin: 6px 0 0 5px;
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
		.list{
			li{
				width: 100%;
				background: @deepblue;
				border-top: 1px solid @black;
				&:first-child{
					padding: 0 3.62%;
					background: #36394d;
				}
				.list_cont{
					height: 44px*@ip6;
					padding: 0 3.62%;
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
						width: 21.11%;
					}
					&:nth-child(2){
						width: 14.5%;
					}
					&:nth-child(3){
						width: 14.5%;
					}
					&:nth-child(4){
						width: 21.5%;
					}
					&:nth-child(5){
						width: 18.5%;
					}
					&.red{
						color: @red;
					}
					&.green{
						color: @green;
					}
				}
				.list_tools{
					height: 44px*@ip6;
					overflow: hidden;
					border-top: 1px solid @black;
					background: #2d3040;
					padding: 0 3.62%;
					#conditionBtn{
						float: right;
						margin: 6px*@ip6 0 0 5px*@ip6;
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
		.list{
			li{
				width: 100%;
				background: @deepblue;
				border-top: 1px solid @black;
				&:first-child{
					padding: 0 3.62%;
					background: #36394d;
				}
				.list_cont{
					height: 44px*@ip5;
					padding: 0 3.62%;
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
						width: 21.11%;
					}
					&:nth-child(2){
						width: 14%;
					}
					&:nth-child(3){
						width: 14%;
					}
					&:nth-child(4){
						width: 21%;
					}
					&:nth-child(5){
						width: 19%;
					}
					&.red{
						color: @red;
					}
					&.green{
						color: @green;
					}
				}
				.list_tools{
					height: 44px*@ip5;
					overflow: hidden;
					border-top: 1px solid @black;
					background: #2d3040;
					padding: 0 3.62%;
					#conditionBtn{
						float: right;
						margin: 6px*@ip5 0 0 5px*@ip5;
					}
				}
			}
		}
	}
	
</style>
