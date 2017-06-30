<template>
	<div id="tradeCenter">
		<!--topbar-->
		<!--tab切换-->
		<div class="money_total border_bottom">
			<span>总资产</span>
			<span class="white">418875</span>
			<i>|</i>
			<span>余额</span>
			<span class="white">575875</span>
			<i>|</i>
			<span>平仓线</span>
			<span class="white">223325</span>
			<div class="icon_arrow fr">
				<a href="#" class="icon"></a>
			</div>
		</div>
		<div class="order_type border_bottom">
			<div class="order_type_left fl">
				<div class="cont">
					<span class="white">国际原油</span>
					<span>CL1609</span>
					<i class="icon_search"></i>
				</div>
			</div>
			<div class="order_type_right fl">
				<ul>
					<template v-for="k in list">
						<li>
							<span>{{k.type}}</span>
							<span :class="{red: k.price_color == 'red', green: k.price_color == 'green'}">{{k.price}}</span>
							<span>{{k.num}}</span>
						</li>
					</template>
				</ul>
			</div>
		</div>
		<div class="order_num border_bottom">
			<div class="operate_num fl" v-if="isShow">
				<operatenum w="sm"></operatenum>
			</div>
			<div class="limit_cont" v-else>
				<div class="limit_cont_col">
					<span>手数：</span>
					<input type="number" value="10" class="ipt fl" />
				</div>
				<div class="limit_cont_col">
					<span>价格：</span>
					<input type="number" class="ipt fl" />
					<span class="white">(元)</span>
				</div>
			</div>
			<div class="order_price fr" @tap="showPrice">
				<span>市价</span>
				<i class="icon_triangle"></i>
			</div>
		</div>
		<div class="trade_btn mt10">
			<tradebtn marketprice="132133" transaction='sell'></tradebtn>
			<tradebtn marketprice="13221321" transaction='buy'></tradebtn>
		</div>
		<div class="tab_box mt10" id="tabBox">
			<template v-for="key in tabList">
				<div class="tab_box_col" @tap="showCont">
					<span>{{key.nav}}</span>
				</div>
			</template>
		</div>
		<orderlist :val="positionContEvent" id="positionCont" v-if="positionShow"></orderlist>
		<orderlist :val="orderContEvent" id="orderCont" v-else-if="orderShow"></orderlist>
		<orderlist :val="entrustContEvent" id="entrustCont" v-else-if="entrustShow"></orderlist>
		<orderlist :val="dealContEvent" id="dealCont" v-else></orderlist>
	</div>
</template>

<script>
	import tradebtn from '../components/tradeButton.vue'
	import cbtn from '../components/conditionBtn.vue'
	import operatenum from '../components/oprtateNum.vue'
	import orderlist from '../components/orderList.vue'
	export default{
		name: 'tradeCenter',
		components: {tradebtn, cbtn, operatenum, orderlist},
		data(){
			return {
				isShow: true,
				positionShow: true,
				orderShow: false,
				entrustShow: false,
				list: [
					{
						type: '新',
						price: '4556',
						price_color: 'red',
						num: 56
					},
					{
						type: '买',
						price: '4556',
						price_color: 'red',
						num: 56
					},
					{
						type: '卖',
						price: '4556',
						price_color: 'green',
						num: 56
					},
				],
				tabList: [{nav:'持仓'},{nav:'挂单'},{nav:'委托'},{nav:'成交'}],
				positionListCont:[
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
				orderListCont:[
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
				entrustListCont:[
					{
						name: '美原油10',
						type: '空',
						num: 12,
						price: '123.98',
						total: '4800.00',
						showbar: false,
						type_color: 'red',
						total_color: 'green'
					},
				],
				dealListCont:[
					{
						name: '美原油11',
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
			positionContEvent: function(){
				return JSON.stringify(this.positionListCont);
			},
			orderContEvent: function(){
				return JSON.stringify(this.orderListCont);
			},
			entrustContEvent: function(){
				return JSON.stringify(this.entrustListCont);
			},
			dealContEvent: function(){
				return JSON.stringify(this.dealListCont);
			}
		},
		methods: {
			showPrice: function(e){
				if(this.isShow == true){
					this.isShow = false;
					$(e.currentTarget).find('span').text('限价');
				}else{
					this.isShow = true;
					$(e.currentTarget).find('span').text('市价');
				}
			},
			showCont: function(e){
				$(e.currentTarget).find("span").addClass('current');
				$(e.currentTarget).siblings().find("span").removeClass('current')
				if($(e.currentTarget).index() == 0){
					this.positionShow = true;
					this.orderShow = false;
					this.entrustShow = false;
				}else if($(e.currentTarget).index() == 1){
					this.positionShow = false;
					this.orderShow = true;
					this.entrustShow = false;
				}else if($(e.currentTarget).index() == 2){
					this.positionShow = false;
					this.orderShow = false;
					this.entrustShow = true;
				}else{
					this.positionShow = false;
					this.orderShow = false;
					this.entrustShow = false;
				}
			},
		},
		mounted: function(){
			$("#tabBox .tab_box_col:first-child span").addClass("current");
			$("#tradeCenter").css("height",window.screen.height + "px");
			var h = $("#detailTopbar").height() + $("#detailselectbar").height() + $(".money_total").height() + 
					$(".order_type").height() + $(".order_num").height() + $(".trade_btn").height() +
					$(".tab_box").height() + $(".list ul:first-child").height();
			$(".list_cont_box").css("height", window.screen.height - h + 'px');
		}
	}
</script>

<style scoped lang="less">
	@import url("../assets/css/main.less");
	@import url("../assets/css/base.less");
	/*ip6p及以上*/
	@media (min-width:411px) {
	    #tradeCenter{
			width: 100%;
			background: @black;
		}
		.border_bottom{
			border-bottom: 1px solid @black;
		}
		.money_total{
			height: 42px;
			padding: 0 15px;
			background: @deepblue;
			span, i{
				display: inline-block;
				float: left;
				height: 22px;
				line-height: 22px;
				font-size: @fs14;
				margin: 10px 0;
			}
			span{
				color: #9da3c4;
				&.white{
					margin-left: 5px;
					color: @white;
				}
			}
			i{
				color: @black;
				margin: 10px 5px;
				font-size: 22px;
			}
			.icon_arrow{
				width: 20px;
				height: 42px;
				.icon{
					display: inline-block;
					width: 9px;
					height: 15px;
					background: url('../assets/img/arrow.png') no-repeat center center;
					background-size: 100% 100%;
					margin: 13px 0 0 11px;
				}
			}
		}
		.order_type{
			width: 100%;
			height: 80px;
			padding: 0 15px;
			background: @deepblue;
			.order_type_left{
				width: 50%;
				border-right: 1px solid @black;
				.cont{
					width: 178px;
					height: 50px;
					overflow: hidden;
					background: @black;
					padding: 0 15px;
					margin: 15px 0;
					border-radius: 5px;;
					border: 1px solid #12121a;
					position: relative;
					span{
						display: block;
						line-height: 20px;
						&.white{
							font-size: @fs16;
							color: @white;
						}
						&:first-child{
							margin-top: 6px;
						}
					}
					.icon_search{
						position: absolute;
						top: 15px;
						right: 16px;
						width: 20px;
						height: 20px;
						overflow: hidden;
						background: url('../assets/img/search.png') no-repeat center center;
						background-size: 100% 100%;
					}
				}
			}
			.order_type_right{
				width: 50%;
				padding: 4px 0 4px 15px;
				li{
					width: 100%;
					height: 24px;
					line-height: 24px;
					span{
						display: block;
						float: left; 
						margin-left: 15px;
						&:last-child{
							float: right;
						}
						&.red{
							color: @red;
						}
						&.green{
							color: @green;
						}
					}
				}
			}
			span{
				color: @blue;
				font-size: @fs14;
			}
		}
		.order_num{
			height: 55px;
			background: @deepblue;
			padding: 5px 15px;
			.market_cont{
				width: 310px;
				overflow: hidden;
			}
			.limit_cont{
				.limit_cont_col{
					float: left;
					width: 150px;
					height: 45px;
					line-height: 45px;
					padding: 0 10px;
					background: @black;
					border: 1px solid #12121a;
					border-radius: 5px;
					span{
						float: left;
					}
					.ipt{
						float: left;
						width: 85px;
						height: 45px;
						line-height: 45px;
						text-align: left;
						padding: 0 5px;
						background: none;
						border: none;
						text-align: left;
						color: @white;
					}
					&:last-child{
						margin-left: 13px;
						.ipt{
							width: 65px;
						}
						span:last-child{
							float: right;
							font-size: @fs12;
						}
					}
				}
			}
			.order_price{
				width: 56px;
				height: 45px;
				border-radius: 5px;
				text-align: center;
				background: @black;
				border: 1px solid #12121a;
				position: relative;
				span{
					display: inline-block;
					line-height: 45px;
					color: @yellow;
				}
				.icon_triangle{
					display: block;
					width: 6px;
					height: 6px;
					overflow: hidden;
					background: url('../assets/img/sanjiao_yellow.png') no-repeat center center;
					background-size: 100% 100%;
					position: absolute;
					bottom: 5px;
					right: 6px;
				}
			}
			span{
				font-size: @fs14;
				color: @blue;
			}
		}
		.trade_btn{
			height: 65px;
			padding: 5px 15px;
			background: @deepblue;
		}
		.trade_btn>div{
			float:left
		}
		.trade_btn>div:last-child{
			float:right
		}
		.tab_box{
				height: 44px;
				background: @deepblue;
				.tab_box_col{
					float: left;
					width: 25%;
					text-align: center;
					span{
						display: inline-block;
						height: 44px;
						line-height: 44px;
						color: @lightblue;
						font-size: @fs14;
						&.current{
							color: @yellow;
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
		#tradeCenter{
			width: 100%;
			background: @black;
		}
		.border_bottom{
			border-bottom: 1px solid @black;
		}
		.money_total{
			height: 42px*@ip6;
			padding: 0 15px*@ip6;
			background: @deepblue;
			span, i{
				display: inline-block;
				float: left;
				height: 22px*@ip6;
				line-height: 22px*@ip6;
				font-size: @fs14*@ip6;
				margin: 10px*@ip6 0;
			}
			span{
				color: #9da3c4;
				&.white{
					margin-left: 5px*@ip6;
					color: @white;
				}
			}
			i{
				color: @black;
				margin: 10px*@ip6 5px*@ip6;
				font-size: 22px*@ip6;
			}
			.icon_arrow{
				width: 20px*@ip6;
				height: 42px*@ip6;
				.icon{
					display: inline-block;
					width: 9px*@ip6;
					height: 15px*@ip6;
					background: url('../assets/img/arrow.png') no-repeat center center;
					background-size: 100% 100%;
					margin: 13px*@ip6 0 0 11px*@ip6;
				}
			}
		}
		.order_type{
			width: 100%;
			height: 80px*@ip6;
			padding: 0 15px*@ip6;
			background: @deepblue;
			.order_type_left{
				width: 50%;
				border-right: 1px solid @black;
				.cont{
					width: 178px*@ip6;
					height: 50px*@ip6;
					overflow: hidden;
					background: @black;
					padding: 0 15px*@ip6;
					margin: 15px*@ip6 0;
					border-radius: 5px*@ip6;;
					border: 1px solid #12121a;
					position: relative;
					span{
						display: block;
						line-height: 20px*@ip6;
						&.white{
							font-size: @fs16*@ip6;
							color: @white;
						}
						&:first-child{
							margin-top: 6px*@ip6;
						}
					}
					.icon_search{
						position: absolute;
						top: 15px*@ip6;
						right: 16px*@ip6;
						width: 20px*@ip6;
						height: 20px*@ip6;
						overflow: hidden;
						background: url('../assets/img/search.png') no-repeat center center;
						background-size: 100% 100%;
					}
				}
			}
			.order_type_right{
				width: 50%;
				padding: 4px*@ip6 0 4px*@ip6 15px*@ip6;
				li{
					width: 100%;
					height: 24px*@ip6;
					line-height: 24px*@ip6;
					span{
						display: block;
						float: left; 
						margin-left: 15px*@ip6;
						&:last-child{
							float: right;
						}
						&.red{
							color: @red;
						}
						&.green{
							color: @green;
						}
					}
				}
			}
			span{
				color: @blue;
				font-size: @fs14*@ip6;
			}
		}
		.order_num{
			height: 55px*@ip6;
			background: @deepblue;
			padding: 5px*@ip6 15px*@ip6;
			.market_cont{
				width: 310px*@ip6;
				overflow: hidden;
			}
			.limit_cont{
				.limit_cont_col{
					float: left;
					width: 150px*@ip6;
					height: 45px*@ip6;
					line-height: 45px*@ip6;
					padding: 0 10px*@ip6;
					background: @black;
					border: 1px solid #12121a;
					border-radius: 5px*@ip6;
					span{
						float: left;
					}
					.ipt{
						float: left;
						width: 85px*@ip6;
						height: 45px*@ip6;
						line-height: 45px*@ip6;
						text-align: left;
						padding: 0 5px*@ip6;
						font-size: @fs14*@ip6;
						background: none;
						border: none;
						text-align: left;
						color: @white;
					}
					&:last-child{
						margin-left: 13px*@ip6;
						.ipt{
							width: 60px*@ip6;
						}
						span:last-child{
							float: right;
							font-size: @fs12;
						}
					}
				}
			}
			.order_price{
				width: 56px*@ip6;
				height: 45px*@ip6;
				border-radius: 5px*@ip6;
				text-align: center;
				background: @black;
				border: 1px solid #12121a;
				position: relative;
				span{
					display: inline-block;
					line-height: 45px*@ip6;
					color: @yellow;
				}
				.icon_triangle{
					display: block;
					width: 6px*@ip6;
					height: 6px*@ip6;
					overflow: hidden;
					background: url('../assets/img/sanjiao_yellow.png') no-repeat center center;
					background-size: 100% 100%;
					position: absolute;
					bottom: 5px*@ip6;
					right: 6px*@ip6;
				}
			}
			span{
				font-size: @fs14*@ip6;
				color: @blue;
			}
		}
		.trade_btn{
			height: 65px*@ip6;
			padding: 5px*@ip6 15px*@ip6;
			background: @deepblue;
		}
		.trade_btn>div{
			float:left
		}
		.trade_btn>div:last-child{
			float:right
		}
		.tab_box{
				height: 44px*@ip6;
				background: @deepblue;
				.tab_box_col{
					float: left;
					width: 25%;
					text-align: center;
					span{
						display: inline-block;
						height: 44px*@ip6;
						line-height: 44px*@ip6;
						color: @lightblue;
						font-size: @fs14*@ip6;
						&.current{
							color: @yellow;
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
					height: 44px*@ip6;
					line-height: 44px*@ip6;
					overflow: hidden;
					color: @lightblue;
					font-size: @fs14*@ip6;
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
		#tradeCenter{
			width: 100%;
			background: @black;
		}
		.border_bottom{
			border-bottom: 1px solid @black;
		}
		.money_total{
			height: 42px*@ip5;
			padding: 0 15px*@ip5;
			background: @deepblue;
			span, i{
				display: inline-block;
				float: left;
				height: 22px*@ip5;
				line-height: 22px*@ip5;
				font-size: @fs14*@ip5;
				margin: 10px*@ip5 0;
			}
			span{
				color: #9da3c4;
				&.white{
					margin-left: 5px*@ip5;
					color: @white;
				}
			}
			i{
				color: @black;
				margin: 10px*@ip5 5px*@ip5;
				font-size: 22px*@ip5;
			}
			.icon_arrow{
				width: 20px*@ip5;
				height: 42px*@ip5;
				.icon{
					display: inline-block;
					width: 9px*@ip5;
					height: 15px*@ip5;
					background: url('../assets/img/arrow.png') no-repeat center center;
					background-size: 100% 100%;
					margin: 13px*@ip5 0 0 11px*@ip5;
				}
			}
		}
		.order_type{
			width: 100%;
			height: 80px*@ip5;
			padding: 0 15px*@ip5;
			background: @deepblue;
			.order_type_left{
				width: 50%;
				border-right: 1px solid @black;
				.cont{
					width: 178px*@ip5;
					height: 50px*@ip5;
					overflow: hidden;
					background: @black;
					padding: 0 15px*@ip5;
					margin: 15px*@ip5 0;
					border-radius: 5px*@ip5;;
					border: 1px solid #12121a;
					position: relative;
					span{
						display: block;
						line-height: 20px*@ip5;
						&.white{
							font-size: @fs16*@ip5;
							color: @white;
						}
						&:first-child{
							margin-top: 6px*@ip5;
						}
					}
					.icon_search{
						position: absolute;
						top: 15px*@ip5;
						right: 16px*@ip5;
						width: 20px*@ip5;
						height: 20px*@ip5;
						overflow: hidden;
						background: url('../assets/img/search.png') no-repeat center center;
						background-size: 100% 100%;
					}
				}
			}
			.order_type_right{
				width: 50%;
				padding: 4px*@ip5 0 4px*@ip5 15px*@ip5;
				li{
					width: 100%;
					height: 24px*@ip5;
					line-height: 24px*@ip5;
					span{
						display: block;
						float: left; 
						margin-left: 15px*@ip5;
						&:last-child{
							float: right;
						}
						&.red{
							color: @red;
						}
						&.green{
							color: @green;
						}
					}
				}
			}
			span{
				color: @blue;
				font-size: @fs14*@ip5;
			}
		}
		.order_num{
			height: 55px*@ip5;
			background: @deepblue;
			padding: 5px*@ip5 15px*@ip5;
			.market_cont{
				width: 310px*@ip5;
				overflow: hidden;
			}
			.limit_cont{
				.limit_cont_col{
					float: left;
					width: 150px*@ip5;
					height: 45px*@ip5;
					line-height: 45px*@ip5;
					padding: 0 10px*@ip5;
					background: @black;
					border: 1px solid #12121a;
					border-radius: 5px*@ip5;
					span{
						float: left;
					}
					.ipt{
						float: left;
						width: 80px*@ip5;
						height: 45px*@ip5;
						line-height: 45px*@ip5;
						text-align: left;
						padding: 0 5px*@ip5;
						font-size: @fs14*@ip5;
						background: none;
						border: none;
						text-align: left;
						color: @white;
					}
					&:last-child{
						margin-left: 13px*@ip5;
						.ipt{
							width: 50px*@ip5;
						}
						span:last-child{
							float: right;
							font-size: @fs12;
						}
					}
				}
			}
			.order_price{
				width: 56px*@ip5;
				height: 45px*@ip5;
				border-radius: 5px*@ip5;
				text-align: center;
				background: @black;
				border: 1px solid #12121a;
				position: relative;
				span{
					display: inline-block;
					line-height: 45px*@ip5;
					color: @yellow;
				}
				.icon_triangle{
					display: block;
					width: 6px*@ip5;
					height: 6px*@ip5;
					overflow: hidden;
					background: url('../assets/img/sanjiao_yellow.png') no-repeat center center;
					background-size: 100% 100%;
					position: absolute;
					bottom: 5px*@ip5;
					right: 6px*@ip5;
				}
			}
			span{
				font-size: @fs14*@ip5;
				color: @blue;
			}
		}
		.trade_btn{
			height: 65px*@ip5;
			padding: 5px*@ip5 15px*@ip5;
			background: @deepblue;
		}
		.trade_btn>div{
			float:left
		}
		.trade_btn>div:last-child{
			float:right
		}
		.tab_box{
				height: 44px*@ip5;
				background: @deepblue;
				.tab_box_col{
					float: left;
					width: 25%;
					text-align: center;
					span{
						display: inline-block;
						height: 44px*@ip5;
						line-height: 44px*@ip5;
						color: @lightblue;
						font-size: @fs14*@ip5;
						&.current{
							color: @yellow;
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
					height: 44px*@ip5;
					line-height: 44px*@ip5;
					overflow: hidden;
					color: @lightblue;
					font-size: @fs14*@ip5;
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