<template>
	<div id="historyTrade">
		<div class="head">
			<topbar title="历史成交"></topbar>
			<back></back>
		</div>
		<div class="page_cont">
			<div class="search">
				<div class="ipt_box fl">
					<img src="../../assets/img/date.png"/>
					<input type="date" class="ipt_date" v-model="start_date" />
					<input type="text" v-model="startDate" />
				</div>
				<img src="../../assets/img/date_arrow.png" class="date_arrow fl" />
				<div class="ipt_box fl">
					<img src="../../assets/img/date.png"/>
					<input type="date" class="ipt_date" v-model="end_date" />
					<input type="text" v-model="endDate" />
				</div>
				<cbtn name="搜索" @tap.native="search00"></cbtn>
			</div>
			<div class="tab_box mt10" id="tab_box">
				<ul>
					<template v-for="key in tablist">
						<li @tap="showCont">{{key.nav}}</li>
					</template>
				</ul>
			</div>
			<div id="dayCont" class="list_cont">
				<ul>
					<li>
						<span>序号</span>
						<span>合约代码</span>
						<span>交易所</span>
						<span>币种</span>
						<span>买卖</span>
						<span>成交价</span>
						<span>成交量</span>
						<span>手续费</span>
						<span>成交时间</span>
					</li>
					<template v-for="key in dataList">
						<li>
							<span>{{key.index}}</span>
							<span>{{key.CommodityNoContractNo}}</span>
							<span>{{key.ExchangeNo}}</span>
							<span>{{key.CurrencyNo}}</span>
							<span :class="{red: key.Drection == '买', green: key.Drection == '卖'}">{{key.Drection}}</span>
							<span>{{key.TradePrice}}</span>
							<span>{{key.TradeNum}}</span>
							<span>{{key.TradeFee}}</span>
							<span>{{key.TradeDateTime}}</span>
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
	export default{
		name:'historyTrade',
		components:{topbar, back, cbtn},
		data() {
			return {
				start_date: '',
				end_date: '',
				startDate: '',
				endDate: '',
				dayShow: true,
				weekShow: false,
				tablist: [{
					nav: '一天内'},{
					nav: '一周内'},{
					nav: '一月内',
				}],
				
				dataList: []
			}
		},
		computed:{
			queryHisList(){
				return this.$store.state.market.queryHisList;
			},
			orderTemplist(){
				return	this.$store.state.market.orderTemplist;
			},
			tradeSocket() {
				return this.$store.state.tradeSocket;
			}
		},
		watch: {
			start_date: function(n, o){
				this.startDate = n;
			},
			end_date: function(n, o){
				this.endDate = n;
			}
		},
		methods: {
			search00:function(){
				var year = this.startDate.substring(0,4);
				var month = this.startDate.substring(5,7);
				var day =  this.startDate.substring(8,10);
				var beginTime = year+'/'+month+'/'+day+' 00:00:00';
				
				var year0 = this.endDate.substring(0,4);
				var month0 = this.endDate.substring(5,7);
				var day0 =  this.endDate.substring(8,10);
				var endTime = year0+'/'+month0+'/'+day0+' 00:00:00';
				
				this.dataList = [];
				this.$store.state.market.queryHisList = [];
				this.tradeSocket.send('{"Method":"QryHisTrade","Parameters":{"ClientNo":"'+JSON.parse(localStorage.tradeUser).username	+'","BeginTime":"'+beginTime+'","EndTime":"'+endTime+'"}}');
				setTimeout(function(){
						this.eachData();
				}.bind(this),1000);
			},
			eachData: function(){
//				this.$store.state.market.queryHisList=[];
				this.queryHisList.forEach(function(e,i){
					var b ={};
					b.index = i;
					b.CommodityNoContractNo = e.ContractCode;
					b.ExchangeNo = e.ExchangeNo;
					b.CurrencyNo = e.CurrencyNo;
					b.Drection =(function(){
						if(e.Drection==0){
							return '买';
						}else{
							return '卖';
						}
					})();
					b.TradePrice = parseFloat(e.TradePrice).toFixed(this.orderTemplist[e.CommodityNo].DotSize);
					b.TradeNum = e.TradeNum;
					b.TradeFee = e.TradeFee;
					b.TradeDateTime = e.TradeDateTime;
					this.dataList.push(b);
				}.bind(this));
				
			},
			showCont: function(e){
				$(e.currentTarget).addClass('current').siblings().removeClass('current');
				if($(e.currentTarget).index() == 0){ //一天
					this.dataList = [];
					var date = new Date(); 
		    		date.setDate(date.getDate()-1);
		    		var year = date.getFullYear();
		    		var day = date.getDate() > 9 ? date.getDate() : "0" + date.getDate();
		    		var month = (date.getMonth() + 1) > 9 ? (date.getMonth() + 1) : "0"+ (date.getMonth() + 1);
		    		var beginTime = year + '/' + month + '/' + day+' 00:00:00';
		    		
		    		var date00 = new Date(); 
		    		date00.setDate(date00.getDate());
		    		var year00 = date00.getFullYear();
		    		var day00 = date00.getDate() > 9 ? date00.getDate() : "0" + date00.getDate();
		    		var month00 = (date00.getMonth() + 1) > 9 ? (date00.getMonth() + 1) : "0"+ (date00.getMonth() + 1);
		    		
		    		var endTime= year00 + '/' + month00 + '/' + day00+' 00:00:00';
		    		this.$store.state.market.queryHisList = [];
					this.tradeSocket.send('{"Method":"QryHisTrade","Parameters":{"ClientNo":"'+JSON.parse(localStorage.tradeUser).username	+'","BeginTime":"'+beginTime+'","EndTime":"'+endTime+'"}}');
					setTimeout(function(){
						this.eachData();
					}.bind(this),1000);
				}else if($(e.currentTarget).index() == 1){ //一周
					this.dataList = [];
					var date = new Date(); 
		    		date.setDate(date.getDate()-7);
		    		var year = date.getFullYear();
		    		var day = date.getDate() > 9 ? date.getDate() : "0" + date.getDate();
		    		var month = (date.getMonth() + 1) > 9 ? (date.getMonth() + 1) : "0"+ (date.getMonth() + 1);
		    		var beginTime = year + '/' + month + '/' + day+' 00:00:00';
		    		
		    		var date00 = new Date(); 
		    		date00.setDate(date00.getDate());
		    		var year00 = date00.getFullYear();
		    		var day00 = date00.getDate() > 9 ? date00.getDate() : "0" + date00.getDate();
		    		var month00 = (date00.getMonth() + 1) > 9 ? (date00.getMonth() + 1) : "0"+ (date00.getMonth() + 1);
		    		
		    		var endTime= year00 + '/' + month00 + '/' + day00+' 00:00:00';
		    		this.$store.state.market.queryHisList = [];
					this.tradeSocket.send('{"Method":"QryHisTrade","Parameters":{"ClientNo":"'+JSON.parse(localStorage.tradeUser).username+'","BeginTime":"'+beginTime+'","EndTime":"'+endTime+'"}}');
					setTimeout(function(){
						this.eachData();
					}.bind(this),1000);
				}else{ //一月
					
					this.dataList = [];
					var date = new Date(); 
		    		date.setDate(date.getDate()-30);
		    		var year = date.getFullYear();
		    		var day = date.getDate() > 9 ? date.getDate() : "0" + date.getDate();
		    		var month = (date.getMonth() + 1) > 9 ? (date.getMonth() + 1) : "0"+ (date.getMonth() + 1);
		    		var beginTime = year + '/' + month + '/' + day+' 00:00:00';
		    		
		    		var date00 = new Date(); 
		    		date00.setDate(date00.getDate());
		    		var year00 = date00.getFullYear();
		    		var day00 = date00.getDate() > 9 ? date00.getDate() : "0" + date00.getDate();
		    		var month00 = (date00.getMonth() + 1) > 9 ? (date00.getMonth() + 1) : "0"+ (date00.getMonth() + 1);
		    		
		    		var endTime= year00 + '/' + month00 + '/' + day00+' 00:00:00';
		    		this.$store.state.market.queryHisList = [];
					this.tradeSocket.send('{"Method":"QryHisTrade","Parameters":{"ClientNo":"'+JSON.parse(localStorage.tradeUser).username+'","BeginTime":"'+beginTime+'","EndTime":"'+endTime+'"}}');
					setTimeout(function(){
						this.eachData();
						}.bind(this),1000);
						
				}
				
			}
		},
		mounted: function(){
			$("#tab_box li:first-child").addClass("current");
			var h = window.screen.height - $("#tab_box").height() - $(".search").height() - $("#topbar").height();
			$(".list_cont ul").height(h);
		},
		activated: function(){
			this.eachData();
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
	    #historyTrade{
			width: 100%;
			padding-top: 50px;
			background: @black;
		}
		.head{
			#back{
				position: fixed;
				top: 0;
				left: 0;
				z-index: 1000;
			}
		}
		.page_cont{
			width: 100%;
			position: fixed;
			top: 50px;
			left: 0;
			.search{
				height: 44px;
				padding: 0 15px;
				background: @deepblue;
				.ipt_box{
					width: 120px;
					height: 34px;
					overflow: hidden;
					background: @black;
					margin: 5px 0;
					border-radius: 4px;
					position: relative;
					img{
						float: left;
						width: 20px;
						height: 21px;
						margin: 7px;
					}
					.ipt_date{
						position: absolute;
						top: 0;
						left: 0;
						width: 34px;
						height: 34px;
						overflow: hidden;
						opacity: 0;
					}
					input{
						float: left;
						width: 85px;
						height: 34px;
						line-height: 34px;
						background: none;
						border: none;
						color: @white;
						padding: 0;
						font-size: @fs14;
					}
				}
				.date_arrow{
					width: 18px;
					height: 8px;
					margin: 18px 5px;
				}
				#conditionBtn{
					float: right;
					margin-top: 5px;	
				}
			}
			.tab_box{
				height: 42px;
				background: @deepblue;
				li{
					float: left;
					width: 33.33%;
					height: 42px;
					line-height: 42px;
					color: @lightblue;
					text-align: center;
					font-size: @fs14;
					&.current{
						color: @yellow;
					}
				}
			}
			.list_cont{
				width: 100%;
				height: auto;
				background: @deepblue;
				ul{
					width: 100%;
					overflow: scroll;
					box-sizing: content-box;
					li{
						width: 800px;
						height: 44px;
						line-height: 44px;
						border-top: 1px solid @black;
						padding-left: 15px;
						&:nth-child(1){
							background: #36394d;
						}
						span{
							display: inline-block;
							float: left;
							width: 50px;
							font-size: @fs14;
							color: @blue;
							&:nth-child(2){
								width: 90px;
							}
							&:nth-child(3){
								width: 70px;
							}
							&:nth-child(4){
								width: 90px;
							}
							&:nth-child(5), &:nth-child(6), &:nth-child(7), &:nth-child(8){
								width: 80px;
							}
							&:nth-child(9){
								width: 150px;
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
			}
		}
	}
	/*ip6*/
	@media (min-width:371px) and (max-width:410px) {
	    #historyTrade{
			width: 100%;
			padding-top: 50px*@ip6;
			background: @black;
		}
		.head{
			#back{
				position: fixed;
				top: 0;
				left: 0;
				z-index: 1000;
			}
		}
		.page_cont{
			width: 100%;
			position: fixed;
			top: 50px*@ip6;
			left: 0;
			.search{
				height: 44px*@ip6;
				padding: 0 15px*@ip6;
				background: @deepblue;
				.ipt_box{
					width: 120px*@ip6;
					height: 34px*@ip6;
					overflow: hidden;
					background: @black;
					margin: 5px*@ip6 0;
					border-radius: 4px*@ip6;
					position: relative;
					img{
						float: left;
						width: 20px*@ip6;
						height: 21px*@ip6;
						margin: 7px*@ip6;
					}
					.ipt_date{
						position: absolute;
						top: 0;
						left: 0;
						width: 34px*@ip6;
						height: 34px*@ip6;
						overflow: hidden;
						opacity: 0;
					}
					input{
						float: left;
						width: 85px*@ip6;
						height: 34px*@ip6;
						line-height: 34px*@ip6;
						background: none;
						border: none;
						color: @white;
						padding: 0;
						font-size: @fs14*@ip6;
					}
				}
				.date_arrow{
					width: 18px*@ip6;
					height: 8px*@ip6;
					margin: 18px*@ip6 5px*@ip6;
				}
				#conditionBtn{
					float: right;
					margin-top: 5px*@ip6;	
				}
			}
			.tab_box{
				height: 42px*@ip6;
				background: @deepblue;
				li{
					float: left;
					width: 33.33%;
					height: 42px*@ip6;
					line-height: 42px*@ip6;
					color: @lightblue;
					text-align: center;
					font-size: @fs14*@ip6;
					&.current{
						color: @yellow;
					}
				}
			}
			.list_cont{
				width: 100%;
				height: auto;
				background: @deepblue;
				ul{
					width: 100%;
					overflow: scroll;
					box-sizing: content-box;
					li{
						width: 800px*@ip6;
						height: 44px*@ip6;
						line-height: 44px*@ip6;
						border-top: 1px solid @black;
						padding-left: 15px*@ip6;
						&:nth-child(1){
							background: #36394d;
						}
						span{
							display: inline-block;
							float: left;
							width: 50px*@ip6;
							font-size: @fs14*@ip6;
							color: @blue;
							&:nth-child(2){
								width: 90px*@ip6;
							}
							&:nth-child(3){
								width: 70px*@ip6;
							}
							&:nth-child(4){
								width: 90px*@ip6;
							}
							&:nth-child(5), &:nth-child(6), &:nth-child(7), &:nth-child(8){
								width: 80px*@ip6;
							}
							&:nth-child(9){
								width: 150px*@ip6;
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
			}
		}
	}
	/*ip5*/
	@media(max-width:370px) {
		#historyTrade{
			width: 100%;
			padding-top: 50px*@ip5;
			background: @black;
		}
		.head{
			#back{
				position: fixed;
				top: 0;
				left: 0;
				z-index: 1000;
			}
		}
		.page_cont{
			width: 100%;
			position: fixed;
			top: 50px*@ip5;
			left: 0;
			.search{
				height: 44px*@ip5;
				padding: 0 15px*@ip5;
				background: @deepblue;
				.ipt_box{
					width: 120px*@ip5;
					height: 34px*@ip5;
					overflow: hidden;
					background: @black;
					margin: 5px*@ip5 0;
					border-radius: 4px*@ip5;
					position: relative;
					img{
						float: left;
						width: 20px*@ip5;
						height: 21px*@ip5;
						margin: 7px*@ip5;
					}
					.ipt_date{
						position: absolute;
						top: 0;
						left: 0;
						width: 34px*@ip5;
						height: 34px*@ip5;
						overflow: hidden;
						opacity: 0;
					}
					input{
						float: left;
						width: 85px*@ip5;
						height: 34px*@ip5;
						line-height: 34px*@ip5;
						background: none;
						border: none;
						color: @white;
						padding: 0;
						font-size: @fs14*@ip5;
					}
				}
				.date_arrow{
					width: 18px*@ip5;
					height: 8px*@ip5;
					margin: 18px*@ip5 5px*@ip5;
				}
				#conditionBtn{
					float: right;
					margin-top: 5px*@ip5;	
				}
			}
			.tab_box{
				height: 42px*@ip5;
				background: @deepblue;
				li{
					float: left;
					width: 33.33%;
					height: 42px*@ip5;
					line-height: 42px*@ip5;
					color: @lightblue;
					text-align: center;
					font-size: @fs14*@ip5;
					&.current{
						color: @yellow;
					}
				}
			}
			.list_cont{
				width: 100%;
				height: auto;
				background: @deepblue;
				ul{
					width: 100%;
					overflow: scroll;
					box-sizing: content-box;
					li{
						width: 800px*@ip5;
						height: 44px*@ip5;
						line-height: 44px*@ip5;
						border-top: 1px solid @black;
						padding-left: 15px*@ip5;
						&:nth-child(1){
							background: #36394d;
						}
						span{
							display: inline-block;
							float: left;
							width: 50px*@ip5;
							font-size: @fs14*@ip5;
							color: @blue;
							&:nth-child(2){
								width: 90px*@ip5;
							}
							&:nth-child(3){
								width: 70px*@ip5;
							}
							&:nth-child(4){
								width: 90px*@ip5;
							}
							&:nth-child(5), &:nth-child(6), &:nth-child(7), &:nth-child(8){
								width: 80px*@ip5;
							}
							&:nth-child(9){
								width: 150px*@ip5;
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
			}
		}
	}
	
</style>
