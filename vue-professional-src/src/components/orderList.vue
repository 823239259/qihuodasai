<template>
	<div id="orderList" class="list" :val="getData">
		<alert title="确认全部平仓" :line1="closeAllOutAlert" :objstr='closeAllOutAlertObj' type="1"></alert>
		<alert title="确认平仓"  :line1="closeOutAlert" :objstr='closeOutAlertObj'></alert>
		<ul class="list_cont_box">
			<li class="list_head">
				<span>合约名称</span>
				<span>多空</span>
				<span>手数</span>
				<span>持仓均价</span>
				<span>浮动盈利</span>
			</li>
			<template v-for="k in datas">
				<li @tap="listTap" :id="k.commodityNocontractNo">
					<div :class="[list_cont,{current:k.showbar}]">
						<span>{{k.name}}</span>
						<span :class="{red: k.type_color == 'red', green: k.type_color == 'green'}">{{k.type}}</span>
						<span>{{k.num}}</span>
						<span>{{k.price}}</span>
						<span :class="{red: k.total_color == 'red', green: k.total_color == 'green'}">{{k.total}}</span>
					</div>
				</li>
			</template>
		</ul>
		<div class="list_tools">
			<cbtn name="全部平仓" @tap.native="closeAllOut"></cbtn>
			<cbtn name="平仓"  @tap.native="closeOut"></cbtn>
			<cbtn name="止损止赢" @tap.native="stopLossStopProfit"></cbtn>
		</div>
		<tipsDialog :msg="msgTips"></tipsDialog>
		<stopmoneyalert :val="selectedOrderLists" ref="stopmoneyalert"></stopmoneyalert>
	</div>
</template>

<script>
	import cbtn from '../components/conditionBtn.vue'
	import alert from '../components/Tradealert.vue'
	import tipsDialog from '../components/tipsDialog.vue'
	import stopmoneyalert from '../components/stopmoneyalert.vue'
	export default{
		name: 'orderList',
		components: {cbtn, alert, tipsDialog,stopmoneyalert},
		props: ['val'],
		data(){
			return {
				msg: '',
				datas: '',
				orderListId: '',
				tempText:{},
				selectedOrderList: ''
			}
		},
		computed: {
			msgTips: function(){
				return this.msg;
			},
			closeOutAlertObj:function(){
				if(this.tempText){
					return JSON.stringify(this.tempText);
				}
			},
			closeOutAlert:function(){
				var obj = this.tempText.Parameters;
				if(obj!=undefined){
					var contract=obj.CommodityNo+obj.ContractNo;
					var orderNum = obj.OrderNum
					var text = '确认平仓合约【'+contract+'】,价格【市价】,手数【'+orderNum+'】';
					return text;
					
				}
			},
			closeAllOutAlertObj:function(){
				if(this.tempText){
					return JSON.stringify(this.tempText);
				}
			},
			closeAllOutAlert:function(){
				
				return '此操作将平掉持仓列表中所有合约,请你慎重选择。是否确认将所有合约全部平仓？';
			},
			list_cont: function(){
				return 'list_cont'
			},
			getData: function(){
				if(this.val){
					this.datas = JSON.parse(this.val);
				}
			},
			positionListCont(){
				return this.$store.state.market.positionListCont;
			},
			tradeSocket() {
				return this.$store.state.tradeSocket;
			},
			qryHoldTotalArr(){
				return this.$store.state.market.qryHoldTotalArr;
			},
			selectedOrderLists: function(){
				return JSON.stringify(this.selectedOrderList);
			},
		},
		methods: {
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
			closeAllOut:function(){
				if(this.qryHoldTotalArr.length > 0){
					this.$children[0].isshow = true;
					var arr=[];
					for(var i in this.qryHoldTotalArr){
						var buildIndex=0;
						var drection;
						if(this.qryHoldTotalArr[i].Drection==0){
							drection = 1;
						}else if(this.qryHoldTotalArr[i].Drection==1){
							drection = 0;
						}
						var b = {
							"Method":'InsertOrder',
							"Parameters":{
								"ExchangeNo":this.qryHoldTotalArr[i].ExchangeNo,
								"CommodityNo":this.qryHoldTotalArr[i].CommodityNo,
								"ContractNo":this.qryHoldTotalArr[i].ContractNo,
								"OrderNum":this.qryHoldTotalArr[i].HoldNum,
								"Drection":drection,
								"PriceType":1,
								"LimitPrice":0.00,
								"TriggerPrice":0,
								"OrderRef":this.$store.state.market.tradeConfig.client_source+ new Date().getTime()+(buildIndex++)
							}
						};
						arr.push(b);
						this.tempText = arr;
					}
				}else{
					this.$children[5].isShow = true;
					this.msg = '暂无合约需要平仓';
				}
			},
			stopLossStopProfit:function(obj){
				var i = 0;
				var positionCurrent=0;
				var length= this.qryHoldTotalArr.length;
				var qryHoldTotalArr = this.qryHoldTotalArr;
				for(positionCurrent in this.positionListCont){
					if(this.orderListId == qryHoldTotalArr[length-1-positionCurrent].ContractCode){
						i++;
						this.$refs.stopmoneyalert.isshow = true;
						this.selectedOrderList = qryHoldTotalArr[length-1-positionCurrent];
						this.$refs.stopmoneyalert.inputPrice = this.selectedOrderList.OpenAvgPrice; 
						return;
					}
				}
				if(i < 1){
					this.$children[5].isShow = true;
					this.msg = '请选择一条数据';
				}
			},
			closeOut:function(obj){
				var i = 0;
				var positionCurrent=0;
				var length= this.qryHoldTotalArr.length;
				var qryHoldTotalArr = this.qryHoldTotalArr;
				for(positionCurrent in this.positionListCont){
					if(this.orderListId == qryHoldTotalArr[length-1-positionCurrent].ContractCode){
						i++;
						this.$children[1].isshow = true;
						var buildIndex=0;
						if(buildIndex>100){
							buildIndex=0;
						}
						var drection;
						if(qryHoldTotalArr[length-1-positionCurrent].Drection==0){
							drection = 1;
						}else if(qryHoldTotalArr[length-1-positionCurrent].Drection==1){
							drection = 0;
						}
						var b={
							"Method":'InsertOrder',
							"Parameters":{
								"ExchangeNo":qryHoldTotalArr[length-1-positionCurrent].ExchangeNo,
								"CommodityNo":qryHoldTotalArr[length-1-positionCurrent].CommodityNo,
								"ContractNo":qryHoldTotalArr[length-1-positionCurrent].ContractNo,
								"OrderNum": qryHoldTotalArr[length-1-positionCurrent].HoldNum,
								"Drection":drection,
								"PriceType":1,
								"LimitPrice":0.00,
								"TriggerPrice":0,
								"OrderRef":this.$store.state.market.tradeConfig.client_source+ new Date().getTime()+(buildIndex++)
							}
						};
						this.tempText = b;
						return false;
						
					}
				}
				if(i < 1){
					this.$children[5].isShow = true;
					this.msg = '请选择一条数据';
				}
				
			}
			
		}
	}
</script>

<style scoped lang="less">
	@import url("../assets/css/main.less");
	@import url("../assets/css/base.less");
	/*ip6p及以上*/
	@media (min-width:411px) {
		.list{
			ul{
				width: 100%;
				padding: 0;
				overflow-y: scroll;
			}
			li{
				width: 480px;
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
						width: 50px;
					}
					&:nth-child(3){
						width: 50px;
					}
					&:nth-child(4){
						width: 65px;
					}
					&:nth-child(5){
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
	
	/*ip6*/
	@media (min-width:371px) and (max-width:410px) {
		.list{
			ul{
				width: 100%;
				padding: 0;
				overflow-y: scroll;
			}
			li{
				width: 480px*@ip6;
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
						width: 50px*@ip6;
					}
					&:nth-child(3){
						width: 50px*@ip6;
					}
					&:nth-child(4){
						width: 65px*@ip6;
					}
					&:nth-child(5){
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
	
	/*ip5*/
	@media(max-width:370px) {
		.list{
			ul{
				width: 100%;
				padding: 0;
				overflow-y: scroll;
			}
			li{
				width: 540px*@ip5;
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
						width: 60px*@ip5;
					}
					&:nth-child(3){
						width: 60px*@ip5;
					}
					&:nth-child(4){
						width: 70px*@ip5;
					}
					&:nth-child(5){
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