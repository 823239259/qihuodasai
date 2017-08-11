<template>
	<div id="tradeCenter">
		<!--topbar-->
		<!--tab切换-->
		<changealert :price="bindPrice" :num="bindNum"></changealert>
		<!--<alert title="确认下单" line1="确认下单吗？" :line2="insertOrder" addr="127.0.0.1" :objstr='objst'></alert>-->
		<alert title="确认下单" line1="确认下单吗？" :line2="insertOrder" :objstr='objst'></alert>
		<div class="money_total border_bottom">
			<span>总资产</span>
			<span class="white">{{this.jCacheTotalAccount.TodayBalance | fixNum}}</span>
			<i>|</i>
			<span>余额</span>
			<span class="white">{{this.jCacheTotalAccount.TodayCanUse | fixNum}}</span>
			<i>|</i>
			<span>平仓线</span>
			<span class="white">{{forceLine}}</span>
			<div class="icon_arrow fr">
				<a href="#" class="icon"></a>
			</div>
		</div>
		<div class="order_type border_bottom">
			<div class="order_type_left fl">
				<div class="cont">
					<span class="white">{{commodityName00}}</span>
					<span>{{commodityNo00}}</span>
					<i class="icon_search"></i>
					<select v-model="selectId">
						<option  v-for="v in parameters" :value="v.CommodityName + '&' + v.CommodityNo + '&' + v.MainContract">{{v.CommodityName}} {{v.CommodityNo}} {{v.MainContract}}</option>
					</select>
				</div>
			</div>
			<div class="order_type_right fl">
				<ul>
					<li>
						<span>新</span>
						<span :class="color1">{{detail.LastQuotation.LastPrice | fixNum2(orderTemplist[detail.LastQuotation.CommodityNo].DotSize)}}</span>
						<span>{{detail.LastQuotation.LastVolume}}</span>
					</li>
					<li>
						<span>买</span>
						<span :class="color2">{{detail.LastQuotation.BidPrice1 | fixNum2(orderTemplist[detail.LastQuotation.CommodityNo].DotSize)}}</span>
						<span>{{detail.LastQuotation.BidQty1}}</span>
					</li>
					<li>
						<span>卖</span>
						<span :class="color3">{{detail.LastQuotation.AskPrice1 | fixNum2(orderTemplist[detail.LastQuotation.CommodityNo].DotSize)}}</span>
						<span>{{detail.LastQuotation.AskQty1}}</span>
					</li>
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
					<input type="number" class="ipt fl" v-model="tradeNum" />
				</div>
				<div class="limit_cont_col">
					<span>价格：</span>
					<input type="number" class="ipt fl" v-model="tradePrices"/>
					<span class="white">(元)</span>
				</div>
			</div>
			<div class="order_price fr" @tap="showPrice">
				<span>市价</span>
				<i class="icon_triangle"></i>
			</div>
		</div>
		<div class="trade_btn mt10">
			<tradebtn :marketprice="marketpriceEvent" transaction='buy' @tap.native='buy'></tradebtn>
			<tradebtn :marketprice="marketpriceEvent" transaction='sell' @tap.native='sell'></tradebtn>
		</div>
		<div class="tab_box mt10" id="tabBox">
			<template v-for="key in tabList">
				<div class="tab_box_col" @tap="showCont">
					<span>{{key.nav}}</span>
				</div>
			</template>
		</div>
		<orderlist :val="positionContEvent" id="positionCont" v-if="positionShow"></orderlist>
		<!--挂单 s-->
		<div class="list list_order" v-else-if="orderShow">
			<ul class="list_cont_box">
				<li class="list_head">
					<span>合约名称</span>
					<span>买卖</span>
					<span>委托价</span>
					<span>委托量</span>
					<span>挂单量</span>
					<span>挂单时间</span>
				</li>
				<template v-for="k in orderListCont">
					<li @tap="listTap" :class="[{current:k.showbar}]" :id="k.OrderID">
						<span>{{k.commodityName}}</span>
						<span>{{k.buyOrSell}}</span>
						<span>{{k.delegatePrice}}</span>
						<span>{{k.delegateNum}}</span>
						<span>{{k.ApplyOrderNum}}</span>
						<span>{{k.InsertDateTime}}</span>
					</li>
				</template>
			</ul>
			<div class="list_tools">
				<cbtn name="全撤" @tap.native="cancelAllOrder"></cbtn>
				<cbtn name="撤单" @tap.native="cancelOrder"></cbtn>
				<cbtn name="改单" @tap.native="openChangealert"></cbtn>
			</div>
		</div>
		<!--挂单 e-->
		
		<!--委托 s-->
		<div class="list list_entrust" v-else-if="entrustShow">
			<ul class="list_cont_box">
				<li class="list_head">
					<span>合约名称</span>
					<span>状态</span>
					<span>买卖</span>
					<span>委托价</span>
					<span>委托量</span>
					<span>已成交</span>
					<span>已撤单</span>
					<span>下单时间</span>
				</li>
				<template v-for="k in obj">
					<li :class="[{current:k.showbar}]">
						<!--<div class="list_cont" >-->
							<span>{{k.commodityName}}</span>
							<!--<span :class="{red: k.type_color == 'red', green: k.type_color == 'green'}">{{k.commodityStatus}}</span>-->
							<span>{{k.commodityStatus}}</span>
							<span>{{k.buyOrSell}}</span>
							<span>{{k.delegatePrice}}</span>
							<span>{{k.delegateNum}}</span>
							<span>{{k.TradeNum}}</span>
							<span>{{k.RevokeNum}}</span>
							<span>{{k.InsertDateTime}}</span>
						<!--</div>-->
					</li>
				</template>
			</ul>
		</div>
		<!--委托 s-->
		
		<!--成交 s-->
		<div class="list list_del" v-else>
			<ul class="list_cont_box">
				<li class="list_head">
					<span>合约名称</span>
					<span>买卖</span>
					<span>成交价</span>
					<span>成交量</span>
					<span>成交时间</span>
				</li>
				<template v-for="k in dealListCont">
					<li>
						<div class="list_cont" :class="[{current:k.showbar}]">
							<span>{{k.commodityName}}</span>
							<span>{{k.buyOrSell}}</span>
							<span>{{k.tradePrice}}</span>
							<span>{{k.tradeNum}}</span>
							<span>{{k.tradeDateTime}}</span>
						</div>
						<!--<transition name="fade" mode="out-in">
							<div class="list_tools" v-show="k.showbar">
								<cbtn name="暂停"></cbtn>
								<cbtn name="修改"></cbtn>
								<cbtn name="删除"></cbtn>
							</div>
						</transition>-->
					</li>
				</template>
			</ul>
		</div>
		<!--成交 s-->
		<!--<orderlist :val="orderContEvent" id="orderCont" v-else-if="orderShow"></orderlist>
		<orderlist :val="entrustContEvent" id="entrustCont" v-else-if="entrustShow"></orderlist>
		<orderlist :val="dealContEvent" id="dealCont" v-else></orderlist>-->
		<alert title="确认撤单" :line2="cancelOrderAlert" :objstr='cancelOrderAlertObj'></alert>
		<alert title="确认全部撤单"  :line2="cancelAllOrderAlert" :objstr='cancelAllOrderAlertObj' type="1"></alert>
		<tipsDialog :msg="msgTips"></tipsDialog>
	</div>
</template>

<script>
	import tipsDialog from '../components/tipsDialog.vue'
	import tradebtn from '../components/tradeButton.vue'
	import cbtn from '../components/conditionBtn.vue'
	import operatenum from '../components/oprtateNum.vue'
	import orderlist from '../components/orderList.vue'
	import changealert from '../components/changealert.vue'
	import alert from '../components/Tradealert.vue'
	export default{
		name: 'tradeCenter',
		components: {tradebtn, cbtn, operatenum, orderlist, changealert, alert, tipsDialog},
		data(){
			return {
				msg: '',
				selectId:'',
				isShow: true,
				positionShow: true,
				orderShow: false,
				entrustShow: false,
				tradeNum:1,
				tradePrices:0,
				commodityName00: '',
				commodityNo00: '',
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
				orderListId: '',
				buyText:{},
				obj: []
			}
		},
		filters:{
			fixNum:function(num){
				return num.toFixed(2);
			},
			fixNum2:function(num,dotsize){
				return num.toFixed(dotsize);
			}
		},
		computed:{
			marketpriceEvent: function(){
				if(this.isShow == true){
					return '市价';
				}else{
					return parseFloat(this.detail.LastQuotation.LastPrice).toFixed(this.orderTemplist[this.detail.CommodityNo].DotSize);
				}
			},
			forceLine(){
				return this.$store.state.market.forceLine;
			},
			msgTips: function(){
				return this.msg;
			},
			layer(){
				return this.$store.state.market.layer;
			},
			cancelAllOrderAlertObj:function(){
				if(this.buyText){
					return JSON.stringify(this.buyText);
				}
			},
			cancelAllOrderAlert:function(){
				return '此操作将撤销挂单中所有合约,请你慎重选择。是否确认将所有合约全部撤销？';
			},
			cancelOrderAlertObj:function(){
				if(this.buyText){
					return JSON.stringify(this.buyText);
				}
				
			},
			cancelOrderAlert:function(){
				
				var obj = this.buyText.Parameters;
				
				if(obj!=undefined){
					var contract=obj.CommodityNo+obj.ContractNo;
					var orderNum = obj.OrderNum;
					var text = '确认撤单:合约【'+contract+'】,手数【'+orderNum+'】';
					return text;
				}
			},
			objst: function(){
				if(this.buyText){
					return JSON.stringify(this.buyText);
				}
			},
			insertOrder: function(){
				var obj = this.buyText.Parameters;
				if(obj !=undefined){
					var contract=obj.CommodityNo+obj.ContractNo;
					var LimitPrice;
					if(obj.PriceType==1){
						LimitPrice='市价';
					}else{
						LimitPrice = obj.LimitPrice;
					}
					var orderNum = obj.OrderNum;
					var drection;
					if(obj.Drection==0){
						drection = '买';
					}else{
						drection = '卖';
					}
					
					var text = '确认提交订单:【'+contract+'】,价格【'+LimitPrice +'】,手数【'+orderNum+'】,方向【'+drection+'】？';
					return  text;
				}
				
				
			},
			bindPrice: function(){
				if(this.$store.state.market.openChangealertCurrentObj){
					return  this.$store.state.market.openChangealertCurrentObj.delegatePrice;
				}
			},
			bindNum:function(){
				if(this.$store.state.market.openChangealertCurrentObj){
					return  this.$store.state.market.openChangealertCurrentObj.delegateNum;
				}
			},
			
			OrderType(){
				return this.$store.state.market.OrderType;
			},
			CacheHoldFloatingProfit(){
				return this.$store.state.market.CacheHoldFloatingProfit;
			},
			jCacheTotalAccount(){
				return this.$store.state.market.CacheAccount.jCacheTotalAccount;
			},
			OnRspQryTradeDealListCont(){
				return this.$store.state.market.OnRspQryTradeDealListCont;
			},
			dealListCont(){
				return this.$store.state.market.dealListCont;
			},
			OnRspOrderInsertOrderListCont(){
				return this.$store.state.market.OnRspOrderInsertOrderListCont;
			},
			orderListCont(){
				return this.$store.state.market.orderListCont;
			},
			OnRspOrderInsertEntrustCont(){
				return this.$store.state.market.OnRspOrderInsertEntrustCont;
			},
			entrustCont(){ //委托列表数组
				return this.$store.state.market.entrustCont;
			},
			positionListCont(){
				return this.$store.state.market.positionListCont;
			},
			orderTemplist(){
				return	this.$store.state.market.orderTemplist;
			},
			qryHoldTotalArr(){
				return this.$store.state.market.qryHoldTotalArr;
			},
			templateList(){
				return this.$store.state.market.templateList;
			},
			tradeSocket() {
				return this.$store.state.tradeSocket;
			},
			parameters(){
				return this.$store.state.market.Parameters;
			},
			color1:function(){
				return this.Parameters.LastPrice-this.Parameters.OpenPrice >=0 ?  'red' :  'green'
			},
			color2:function(){
				return this.Parameters.BidPrice1-this.Parameters.OpenPrice >=0 ?  'red' :  'green'
			},
			color3:function(){
				return this.Parameters.AskPrice1-this.Parameters.OpenPrice >=0 ?  'red' :  'green'
			},
			Parameters(){
				return this.$store.state.market.jsonTow.Parameters;
			},
			positionContEvent: function(){
				return JSON.stringify(this.positionListCont);
			},
			detail(){
//				return this.$parent.detail;
				return this.$store.state.market.currentdetail
			},
			Parameters00(){   //合约详情obj
				return this.$store.state.market.Parameters;
			},
			tradePrice(){
				return this.detail.LastQuotation.LastPrice;
			}
		},
		watch:{
			layer: function(n, o){
				this.$children[8].isShow = true;
				this.msg = n;
			},
			selectId:function(n,o){
				if(n != undefined){
					var arr = n.split('&');
					this.$store.state.market.currentNo=arr[1];
					this.commodityName00 = arr[0];
					this.commodityNo00 = arr[1] + arr[2];
					
					var orderTemplist = this.orderTemplist;
					this.Parameters00.forEach(function(o, i){
						if(o.CommodityName == this.commodityName00){
							this.$store.state.market.currentdetail = o;
							this.tradePrices = dealDotSize(o.LastQuotation.LastPrice,o.LastQuotation);
						}
					}.bind(this));
					function dealDotSize(price,LastQuotation){
					  	return  parseFloat(price).toFixed(orderTemplist[LastQuotation.CommodityNo].DotSize) ;
					}
				}
			},
			tradePrices: function(n, o){
					if(n.length<1){
						$(".redbtn li:first-child, .greenbtn li:first-child").text('');
					}else{
						$(".redbtn li:first-child, .greenbtn li:first-child").text(n);
					}
					
			},
			/*
			qryHoldTotalArr:function(n,o){
				this.$store.state.market.positionListCont=[];
				this.qryHoldTotalArr.forEach(function(e){
					var obj={};
					obj.name=this.orderTemplist[e.CommodityNo].CommodityName;
					obj.type=function(){
						if(e.Drection==0){
							return '多'
						}else{
							return '空'
						}
					}();
					obj.num=e.HoldNum;
					obj.price=e.HoldAvgPrice.toFixed(this.orderTemplist[e.CommodityNo].DotSize);
					obj.total=0;
					obj.showbar=false;
					obj.type_color=function(){
						if(e.Drection==0){
							return 'red'
						}else{
							return 'green'
						}
					}();
					obj.total_color='green';
					obj.commodityNocontractNo = this.orderTemplist[e.CommodityNo].LastQuotation.CommodityNo
												+this.orderTemplist[e.CommodityNo].LastQuotation.ContractNo;
					this.$store.state.market.positionListCont.unshift(obj);
				}.bind(this));
			},
			*/
			OnRspOrderInsertEntrustCont:function(n,o){
				this.appendOrderList(n);
			},
			OnRspOrderInsertOrderListCont:function(n,o){
				this.$store.state.market.orderListCont=[];
				this.OnRspOrderInsertOrderListCont.forEach(function(e){
					var obj={};
					obj.commodityName=this.orderTemplist[e.CommodityNo].CommodityName;
					obj.buyOrSell = function(){
						if(e.Drection==0){
							return '买';
						}else{
							return '卖';
						}
					}();
					obj.delegatePrice = function(){
						if(e.OrderPrice==0){
							return '市价';
						}else{
							return e.OrderPrice;
						}
					}();
					
					obj.delegateNum = e.OrderNum;
					obj.ApplyOrderNum = e.OrderNum-e.TradeNum;
					obj.InsertDateTime = e.InsertDateTime;
					obj.ContractCode = e.ContractCode;
					obj.OrderID = e.OrderID;
					
					this.$store.state.market.orderListCont.unshift(obj);
				}.bind(this));
			},
			OnRspQryTradeDealListCont:function(n,o){
				this.$store.state.market.dealListCont=[];
				this.OnRspQryTradeDealListCont.forEach(function(e){
					var obj={};
					obj.commodityName=this.orderTemplist[e.CommodityNo].CommodityName;
					obj.buyOrSell = function(){
						if(e.Drection==0){
							return '买';
						}else{
							return '卖';
						}
					}();
					obj.tradePrice = e.TradePrice;
					
					obj.tradeNum = e.TradeNum;
					obj.tradeDateTime = e.TradeDateTime;
					obj.ContractCode = e.ContractCode;
					obj.OrderID = e.OrderID;
					
					this.$store.state.market.dealListCont.unshift(obj);
					
				}.bind(this));
			}
		},
		methods: {
			cancelAllOrder:function(){
				if(this.isShow == false){
					this.$children[5].isshow = true;
				}else{
					this.$children[6].isshow = true;
				}
				var arr=[];
				this.$store.state.market.orderListCont.forEach(function(e,i){
					var CurrentObj = e;
					var Contract = CurrentObj.ContractCode.substring(0,CurrentObj.ContractCode.length-4);
					var b={
							"Method":'CancelOrder',
							"Parameters":{
								"OrderSysID":'',
								"OrderID":CurrentObj.OrderID,
								"ExchangeNo":this.templateList[Contract].LastQuotation.ExchangeNo,
								"CommodityNo":this.templateList[Contract].LastQuotation.CommodityNo,
								"ContractNo":this.templateList[Contract].LastQuotation.ContractNo,
								"OrderNum":parseFloat(CurrentObj.delegateNum),
								"Direction":function(){
												if(CurrentObj.buyOrSell=='买'){
													return 0;
												}else{
													return 1;
												}
											},
								"OrderPrice":parseFloat(CurrentObj.delegatePrice)
							}
					};
					
//					this.tradeSocket.send(JSON.stringify(b));
					arr.push(b);
					this.buyText = arr;
				}.bind(this));
				
			},
			cancelOrder:function(){
				this.$children[5].isshow = true;
				var orderListId= this.orderListId;
				var isExist = false;
				var CurrentObj = null;
				var index =0;
				this.$store.state.market.orderListCont.forEach(function(e,i){
					if(e.OrderID==orderListId){
						CurrentObj = e;
						index = i;
						isExist = true;
					}
				}.bind(this));
				if(isExist==true){
					var Contract = CurrentObj.ContractCode.substring(0,CurrentObj.ContractCode.length-4);
					var b={
							"Method":'CancelOrder',
							"Parameters":{
								"OrderSysID":'',
								"OrderID":CurrentObj.OrderID,
								"ExchangeNo":this.templateList[Contract].LastQuotation.ExchangeNo,
								"CommodityNo":this.templateList[Contract].LastQuotation.CommodityNo,
								"ContractNo":this.templateList[Contract].LastQuotation.ContractNo,
								"OrderNum":parseFloat(CurrentObj.delegateNum),
								"Direction":function(){
												if(CurrentObj.buyOrSell=='买'){
													return 0;
												}else{
													return 1;
												}
											},
								"OrderPrice":parseFloat(CurrentObj.delegatePrice)
							}
						};
						this.buyText = b;
//					this.tradeSocket.send(JSON.stringify(b));
				}
				
			},
			openChangealert: function(){
				var orderListId= this.orderListId;
				var isExist = false;
				this.$store.state.market.orderListCont.forEach(function(e){
					if(e.OrderID==orderListId){
						this.$store.state.market.openChangealertCurrentObj = e;
						isExist = true;
					}
				}.bind(this));
				
				if(isExist==true){
					this.$children[0].isshow = true;
				}
				
			},
			listTap: function(obj){
				if(!$(obj.currentTarget).hasClass("current")){
					$(obj.currentTarget).addClass("current");
					$(obj.currentTarget).siblings().removeClass("current");
					this.orderListId = $(obj.currentTarget).attr("id");
				}else{
					$(obj.currentTarget).removeClass("current");
				}
			},
			sell:function(){
				this.$children[1].isshow = true;
				var commodityNo = this.detail.CommodityNo;
				if(this.isShow==true){
					var buildIndex=0;
					if(buildIndex>100){
						buildIndex=0;
					}
					var b={
							"Method":'InsertOrder',
							"Parameters":{
								"ExchangeNo":this.templateList[commodityNo].ExchangeNo,
								"CommodityNo":this.templateList[commodityNo].CommodityNo,
								"ContractNo":this.detail.LastQuotation.ContractNo,
								"OrderNum":this.$children[2].defaultNum,
								"Drection":1,
								"PriceType":1,
								"LimitPrice":0.00,
								"TriggerPrice":0,
								"OrderRef":this.$store.state.market.tradeConfig.client_source+ new Date().getTime()+(buildIndex++)
							}
						};
					this.buyText = b;	
//					this.tradeSocket.send(JSON.stringify(b));
					
				}else{
						var buildIndex=0;
						if(buildIndex>100){
							buildIndex=0;
						}
						var b={
							"Method":'InsertOrder',
							"Parameters":{
								"ExchangeNo":this.templateList[commodityNo].ExchangeNo,
								"CommodityNo":this.templateList[commodityNo].CommodityNo,
								"ContractNo":this.detail.LastQuotation.ContractNo,
								"OrderNum": parseInt(this.tradeNum),
								"Drection":1,
								"PriceType":0,
								"LimitPrice":parseFloat(this.tradePrice),
								"TriggerPrice":0,
								"OrderRef":this.$store.state.market.tradeConfig.client_source+ new Date().getTime()+(buildIndex++)
							}
						};
						this.buyText = b;
//						this.tradeSocket.send(JSON.stringify(b));
				}
			},
			buy:function(){
					this.$children[1].isshow = true;
					var commodityNo = this.detail.CommodityNo;
					if(this.isShow==true){
						
							var buildIndex=0;
							if(buildIndex>100){
								buildIndex=0;
							}
							var b={
								"Method":'InsertOrder',
								"Parameters":{
									"ExchangeNo":this.templateList[commodityNo].ExchangeNo,
									"CommodityNo":this.templateList[commodityNo].CommodityNo,
									"ContractNo":this.detail.LastQuotation.ContractNo,
									"OrderNum":this.$children[2].defaultNum,
									"Drection":0,
									"PriceType":1,
									"LimitPrice":0.00,
									"TriggerPrice":0,
									"OrderRef":this.$store.state.market.tradeConfig.client_source+ new Date().getTime()+(buildIndex++)
								}
							};
							this.buyText = b;
//							this.tradeSocket.send(JSON.stringify(b));
						
					}else{
							var buildIndex=0;
							if(buildIndex>100){
								buildIndex=0;
							}
							var b={
								"Method":'InsertOrder',
								"Parameters":{
									"ExchangeNo":this.templateList[commodityNo].ExchangeNo,
									"CommodityNo":this.templateList[commodityNo].CommodityNo,
									"ContractNo":this.detail.LastQuotation.ContractNo,
									"OrderNum": parseInt(this.tradeNum),
									"Drection":0,
									"PriceType":0,
									"LimitPrice":parseFloat(this.tradePrice),
									"TriggerPrice":0,
									"OrderRef":this.$store.state.market.tradeConfig.client_source+ new Date().getTime()+(buildIndex++)
								}
							};
							this.buyText = b;
//							this.tradeSocket.send(JSON.stringify(b));
						
					}
				
			},
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
			appendOrderList: function(obj){
				this.obj = [];
				obj.forEach(function(e){
					var orderTemplist = this.orderTemplist;
					if(e.CommodityNo!=''){
						var obj={};
						obj.commodityName=this.orderTemplist[e.CommodityNo].CommodityName;
						obj.commodityStatus=this.OrderType[e.OrderStatus];
						obj.buyOrSell = function(){
							if(e.Drection==0){
								return '买';
							}else{
								return '卖';
							}
						}();
						obj.delegatePrice = function(){
							if(e.OrderPrice==0){
								return '市价';
							}else{
								return parseFloat(e.OrderPrice).toFixed(orderTemplist[e.CommodityNo].DotSize);
							}
						}();
						
						obj.delegateNum = e.OrderNum;
						obj.TradeNum = e.TradeNum;
						obj.RevokeNum=function(){
							if(e.OrderStatus==4){
								return e.OrderNum - e.TradeNum;
							}else{
								return 0;
							}
						}();
						obj.InsertDateTime = e.InsertDateTime;
						obj.ContractCode = e.ContractCode;
						obj.OrderID = e.OrderID;
						this.obj.unshift(obj);
	//					this.$store.state.market.entrustCont.push(obj);
					}
				}.bind(this));
			}
		},
		mounted: function(){
			$("#tabBox .tab_box_col:first-child span").addClass("current");
//			$("#tradeCenter").css("height",window.screen.height - $("#detailTopbar").height() - $("#detailselectbar").height() - 10 + "px");
			var h = $("#detailTopbar").height() + $("#detailselectbar").height() + $(".money_total").height() + 
					$(".order_type").height() + $(".order_num").height() + $(".trade_btn").height() +
					$(".tab_box").height() + $(".list ul:first-child").height();
			var screenHeight = window.screen.height;
			switch (screenHeight){
				case 736:
					$(".list_cont_box").css("height", screenHeight - h - 20 - 41 + 'px');
					break;
				case 667:
					$(".list_cont_box").css("height", screenHeight - h - 20 - 40 + 'px');
					break;
				case 568:
					$(".list_cont_box").css("height", screenHeight - h - 20 - 37 + 'px');
					break;
				default:
					break;
			}
			this.$store.state.market.positionListCont=[];
			this.qryHoldTotalArr.forEach(function(e){
					var obj={};
					obj.name=this.orderTemplist[e.CommodityNo].CommodityName;
					obj.type=function(){
						if(e.Drection==0){
							return '多'
						}else{
							return '空'
						}
					}();
					obj.num=e.HoldNum;
					obj.price=e.HoldAvgPrice.toFixed(this.orderTemplist[e.CommodityNo].DotSize);
					var currentCommodity = this.orderTemplist[e.CommodityNo];
					var CacheHoldFloatingProfit = this.CacheHoldFloatingProfit;
					obj.total=function(){
						var diff = currentCommodity.LastPrice - e.HoldAvgPrice;
						var mult = currentCommodity.ContractSize/currentCommodity.MiniTikeSize;
						var tmpFloatingProfit = parseFloat(diff * mult * e.HoldNum).toFixed(2);
						if(isNaN(tmpFloatingProfit)){
							tmpFloatingProfit=0;
						}
						if(e.Drection === 1) { // 空反向
							tmpFloatingProfit = -tmpFloatingProfit;
						}
						if(tmpFloatingProfit>=0){
							obj.total_color = 'red';
						}else{
							obj.total_color = 'green';
						}
						var floatingProfit=tmpFloatingProfit+':'+currentCommodity.CurrencyNo;
						CacheHoldFloatingProfit.jHoldFloatingProfit[e.ContractCode] 
							= {"currencyNo" : currentCommodity.CurrencyNo, "floatingProfit" : tmpFloatingProfit};
						return floatingProfit;
					}();
					obj.showbar=false;
					obj.type_color=function(){
						if(e.Drection==0){
							return 'red'
						}else{
							return 'green'
						}
					}();
					obj.ExchangeNo = this.orderTemplist[e.CommodityNo].ExchangeNo;
					obj.CommodityNo=this.orderTemplist[e.CommodityNo].LastQuotation.CommodityNo;
					obj.ContractNo=this.orderTemplist[e.CommodityNo].LastQuotation.ContractNo;
					obj.Drection = e.Drection;
					
					obj.commodityNocontractNo = this.orderTemplist[e.CommodityNo].LastQuotation.CommodityNo
												+this.orderTemplist[e.CommodityNo].LastQuotation.ContractNo;
					this.$store.state.market.positionListCont.unshift(obj);
				}.bind(this));
			
			//委托
			this.appendOrderList(this.OnRspOrderInsertEntrustCont);
			
			this.$store.state.market.orderListCont=[];
			this.OnRspOrderInsertOrderListCont.forEach(function(e){
				var obj={};
				obj.commodityName=this.orderTemplist[e.CommodityNo].CommodityName;
				obj.buyOrSell = function(){
					if(e.Drection==0){
						return '买';
					}else{
						return '卖';
					}
				}();
				obj.delegatePrice = function(){
					if(e.OrderPrice==0){
						return '市价';
					}else{
						return e.OrderPrice;
					}
				}();
				
				obj.delegateNum = e.OrderNum;
				obj.ApplyOrderNum = e.OrderNum-e.TradeNum;
				obj.InsertDateTime = e.InsertDateTime;
				obj.ContractCode = e.ContractCode;
				obj.OrderID = e.OrderID;
				this.$store.state.market.orderListCont.unshift(obj);
			}.bind(this));
			
			
			this.$store.state.market.dealListCont=[];
			this.OnRspQryTradeDealListCont.forEach(function(e){
				var obj={};
				obj.commodityName=this.orderTemplist[e.CommodityNo].CommodityName;
				obj.buyOrSell = function(){
					if(e.Drection==0){
						return '买';
					}else{
						return '卖';
					}
				}();
				obj.tradePrice = e.TradePrice;
				
				obj.tradeNum = e.TradeNum;
				obj.tradeDateTime = e.TradeDateTime;
				obj.ContractCode = e.ContractCode;
				obj.OrderID = e.OrderID;
				
				this.$store.state.market.dealListCont.unshift(obj);
				
			}.bind(this));
			//初始合约名称
			this.commodityName00 = this.detail.CommodityName;
			this.commodityNo00 = this.detail.CommodityNo + this.detail.LastQuotation.ContractNo;
			
			this.tradePrices = parseFloat(this.tradePrice).toFixed(this.orderTemplist[this.detail.CommodityNo].DotSize);
			
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
			position: fixed;
			top: 90px;
			left: 0;
		}
		.border_bottom{
			border-bottom: 1px solid @black;
		}
		.money_total{
			height: 42px;
			overflow: hidden;
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
					select{
						position: absolute;
						top: 0;
						left: 0;
						right: 0;
						bottom: 0;
						background: transparent;
						border: none;
						outline: none;
						color: transparent;
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
						text-align: left;
						padding: 5px;
						background: none;
						border: none;
						text-align: left;
						font-size: @fs14;
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
			.list_cont_box{
				overflow-y: scroll;
			}
			&.list_del{
				ul{
					width: 100%;
					padding: 0;
					height: 330px;
					overflow-x: scroll;
					li{
						width: 480px;
						padding-left: 15px;
						span{
							&:nth-child(1){
								width: 100px;
							}
							&:nth-child(2){
								width: 50px;
							}
							&:nth-child(3){
								width: 50px;
							}
							&:nth-child(4){
								width: 50px;
							}
							&:nth-child(5){
								width: 150px;
							}
						}
					}
				}
			} 
			&.list_entrust{
				ul{
					width: 100%;
					padding: 0;
					height: 330px;
					overflow: scroll;
					li{
						width: 700px;
						padding-left: 15px;
						span{
							&:nth-child(1){
								width: 100px;
							}
							&:nth-child(2){
								width: 90px;
							}
							&:nth-child(3){
								width: 50px;
							}
							&:nth-child(4){
								width: 50px;
							}
							&:nth-child(5){
								width: 50px;
							}
							&:nth-child(6){
								width: 50px;
							}
							&:nth-child(7){
								width: 50px;
							}
							&:nth-child(8){
								width: 150px;
							}
						}
					}
				}
			}
			&.list_order{
				ul{
					width: 100%;
					padding: 0;
					height: 330px;
					overflow-x: scroll;
					li{
						width: 530px;
						padding-left: 15px;
						span{
							&:nth-child(1){
								width: 100px;
							}
							&:nth-child(2){
								width: 50px;
							}
							&:nth-child(3){
								width: 50px;
							}
							&:nth-child(4){
								width: 50px;
							}
							&:nth-child(5){
								width: 50px;
							}
							&:nth-child(6){
								width: 150px;
							}
						}
					}
				}
			}  
			li{
				width: 100%;
				background: @deepblue;
				height: 44px;
				border-top: 1px solid @black;
				padding: 0 15px;
				&.list_head{
					background: #36394d;
				}
				&.current{
					background: #2d3040;
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
						width: 24%;
					}
					&:nth-child(2){
						width: 20%;
					}
					&:nth-child(3){
						width: 20%;
					}
					&:nth-child(4){
						width: 20%;
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
	
	/*ip6*/
	@media (min-width:371px) and (max-width:410px) {
		#tradeCenter{
			width: 100%;
			background: @black;
			position: fixed;
			top: 90px*@ip6;
			left: 0;
		}
		.border_bottom{
			border-bottom: 1px solid @black;
		}
		.money_total{
			height: 42px*@ip6;
			overflow: hidden;
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
					border-radius: 5px*@ip6;
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
							margin-top: 6px;
						}
					}
					select{
						position: absolute;
						top: 0;
						left: 0;
						right: 0;
						bottom: 0;
						background: transparent;
						border: none;
						outline: none;
						color: transparent;
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
						text-align: left;
						padding: 5px*@ip6;
						background: none;
						border: none;
						text-align: left;
						font-size: @fs14*@ip6;
						color: @white;
					}
					&:last-child{
						margin-left: 13px*@ip6;
						.ipt{
							width: 65px*@ip6;
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
			.list_cont_box{
				overflow-y: scroll;
			}
			&.list_del{
				ul{
					width: 100%;
					padding: 0;
					height: 330px*@ip6;
					overflow-x: scroll;
					li{
						width: 480px*@ip6;
						padding-left: 15px*@ip6;
						span{
							&:nth-child(1){
								width: 100px*@ip6;
							}
							&:nth-child(2){
								width: 50px*@ip6;
							}
							&:nth-child(3){
								width: 50px*@ip6;
							}
							&:nth-child(4){
								width: 50px*@ip6;
							}
							&:nth-child(5){
								width: 150px*@ip6;
							}
						}
					}
				}
			} 
			&.list_entrust{
				ul{
					width: 100%;
					padding: 0;
					height: 330px*@ip6;
					overflow: scroll;
					li{
						width: 700px*@ip6;
						padding-left: 15px*@ip6;
						span{
							&:nth-child(1){
								width: 100px*@ip6;
							}
							&:nth-child(2){
								width: 90px*@ip6;
							}
							&:nth-child(3){
								width: 50px*@ip6;
							}
							&:nth-child(4){
								width: 50px*@ip6;
							}
							&:nth-child(5){
								width: 50px*@ip6;
							}
							&:nth-child(6){
								width: 50px*@ip6;
							}
							&:nth-child(7){
								width: 50px*@ip6;
							}
							&:nth-child(8){
								width: 150px*@ip6;
							}
						}
					}
				}
			}
			&.list_order{
				ul{
					width: 100%;
					padding: 0;
					height: 330px*@ip6;
					overflow-x: scroll;
					li{
						width: 530px*@ip6;
						padding-left: 15px*@ip6;
						span{
							&:nth-child(1){
								width: 100px*@ip6;
							}
							&:nth-child(2){
								width: 50px*@ip6;
							}
							&:nth-child(3){
								width: 50px*@ip6;
							}
							&:nth-child(4){
								width: 50px*@ip6;
							}
							&:nth-child(5){
								width: 50px*@ip6;
							}
							&:nth-child(6){
								width: 150px*@ip6;
							}
						}
					}
				}
			}  
			li{
				width: 100%;
				background: @deepblue;
				height: 44px*@ip6;
				border-top: 1px solid @black;
				padding: 0 15px*@ip6;
				&.list_head{
					background: #36394d;
				}
				&.current{
					background: #2d3040;
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
						width: 24%;
					}
					&:nth-child(2){
						width: 20%;
					}
					&:nth-child(3){
						width: 20%;
					}
					&:nth-child(4){
						width: 20%;
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
	
	/*ip5*/
	@media(max-width:370px) {
		#tradeCenter{
			width: 100%;
			background: @black;
			position: fixed;
			top: 90px*@ip5;
			left: 0;
		}
		.border_bottom{
			border-bottom: 1px solid @black;
		}
		.money_total{
			height: 42px*@ip5;
			overflow: hidden;
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
					border-radius: 5px*@ip5;
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
							margin-top: 6px;
						}
					}
					select{
						position: absolute;
						top: 0;
						left: 0;
						right: 0;
						bottom: 0;
						background: transparent;
						border: none;
						outline: none;
						color: transparent;
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
						text-align: left;
						padding: 5px*@ip5;
						background: none;
						border: none;
						text-align: left;
						font-size: @fs14*@ip5;
						color: @white;
					}
					&:last-child{
						margin-left: 13px*@ip5;
						.ipt{
							width: 65px*@ip5;
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
			.list_cont_box{
				overflow-y: scroll;
			}
			&.list_del{
				ul{
					width: 100%;
					padding: 0;
					height: 330px*@ip5;
					overflow-x: scroll;
					li{
						width: 480px*@ip5;
						padding-left: 15px*@ip5;
						span{
							&:nth-child(1){
								width: 100px*@ip5;
							}
							&:nth-child(2){
								width: 50px*@ip5;
							}
							&:nth-child(3){
								width: 50px*@ip5;
							}
							&:nth-child(4){
								width: 50px*@ip5;
							}
							&:nth-child(5){
								width: 150px*@ip5;
							}
						}
					}
				}
			} 
			&.list_entrust{
				ul{
					width: 100%;
					padding: 0;
					height: 330px*@ip5;
					overflow: scroll;
					li{
						width: 710px*@ip5;
						padding-left: 15px*@ip5;
						span{
							&:nth-child(1){
								width: 100px*@ip5;
							}
							&:nth-child(2){
								width: 90px*@ip5;
							}
							&:nth-child(3){
								width: 50px*@ip5;
							}
							&:nth-child(4){
								width: 50px*@ip5;
							}
							&:nth-child(5){
								width: 50px*@ip5;
							}
							&:nth-child(6){
								width: 50px*@ip5;
							}
							&:nth-child(7){
								width: 50px*@ip5;
							}
							&:nth-child(8){
								width: 150px*@ip5;
							}
						}
					}
				}
			}
			&.list_order{
				ul{
					width: 100%;
					padding: 0;
					height: 330px*@ip5;
					overflow-x: scroll;
					li{
						width: 540px*@ip5;
						padding-left: 15px*@ip5;
						span{
							&:nth-child(1){
								width: 100px*@ip5;
							}
							&:nth-child(2){
								width: 50px*@ip5;
							}
							&:nth-child(3){
								width: 50px*@ip5;
							}
							&:nth-child(4){
								width: 50px*@ip5;
							}
							&:nth-child(5){
								width: 50px*@ip5;
							}
							&:nth-child(6){
								width: 150px*@ip5;
							}
						}
					}
				}
			}  
			li{
				width: 100%;
				background: @deepblue;
				height: 44px*@ip5;
				border-top: 1px solid @black;
				padding: 0 15px*@ip5;
				&.list_head{
					background: #36394d;
				}
				&.current{
					background: #2d3040;
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
						width: 24%;
					}
					&:nth-child(2){
						width: 20%;
					}
					&:nth-child(3){
						width: 20%;
					}
					&:nth-child(4){
						width: 20%;
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
	

</style>