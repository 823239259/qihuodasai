<template>
	<div id="orderList" class="list" :val="getData">
		<alert title="确认全部平仓" :line2="closeAllOutAlert" :objstr='closeAllOutAlertObj' type="1"></alert>
		<alert title="确认平仓"  :line2="closeOutAlert" :objstr='closeOutAlertObj'></alert>
		<ul>
			<li class="list_head">
				<span>合约名称</span>
				<span>多空</span>
				<span>手数</span>
				<span>持仓均价</span>
				<span>浮动盈利</span>
			</li>
		</ul>
		<ul class="list_cont_box">
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
			<!--<cbtn name="止损止赢"></cbtn>-->
		</div>
		<tipsDialog :msg="msgTips"></tipsDialog>
	</div>
</template>

<script>
	import cbtn from '../components/conditionBtn.vue'
	import alert from '../components/Tradealert.vue'
	import tipsDialog from '../components/tipsDialog.vue'
	export default{
		name: 'orderList',
		components: {cbtn, alert, tipsDialog},
		props: ['val'],
		data(){
			return {
				msg: '',
				datas: '',
				orderListId: '',
				tempText:{}
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
			}
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
				this.$children[0].isshow = true;
				var arr=[];
				for(var i in this.positionListCont){
					var buildIndex=0;
					var drection;
					if(this.positionListCont[i].Drection==0){
						drection = 1;
					}
						
					if(this.positionListCont[i].Drection==1){
						drection = 0;
					}
					var b={
							"Method":'InsertOrder',
							"Parameters":{
								"ExchangeNo":this.positionListCont[positionCurrent].ExchangeNo,
								"CommodityNo":this.positionListCont[positionCurrent].CommodityNo,
								"ContractNo":this.positionListCont[positionCurrent].ContractNo,
								"OrderNum":this.positionListCont[positionCurrent].num,
								"Drection":drection,
								"PriceType":1,
								"LimitPrice":0.00,
								"TriggerPrice":0,
								"OrderRef":this.$store.state.market.tradeConfig.client_source+ new Date().getTime()+(buildIndex++)
							}
					};
					
//					this.$store.state.market.positionListCont.splice(positionCurrent,1);
//					this.$store.state.market.qryHoldTotalArr.splice(this.qryHoldTotalArr.length-1-positionCurrent,1);
					arr.push(b);
					this.tempText = arr;
//					this.tradeSocket.send(JSON.stringify(b));
					
				}
			},
			closeOut:function(obj){
				for(var positionCurrent in this.positionListCont){
					if(this.orderListId == this.positionListCont[positionCurrent].commodityNocontractNo){
						console.log(1);
						this.$children[1].isshow = true;
						var buildIndex=0;
						if(buildIndex>100){
							buildIndex=0;
						}
						var drection;
						if(this.positionListCont[positionCurrent].Drection==0){
							drection = 1;
						}else if(this.positionListCont[positionCurrent].Drection==1){
							drection = 0;
						}
						var b={
							"Method":'InsertOrder',
							"Parameters":{
								"ExchangeNo":this.positionListCont[positionCurrent].ExchangeNo,
								"CommodityNo":this.positionListCont[positionCurrent].CommodityNo,
								"ContractNo":this.positionListCont[positionCurrent].ContractNo,
								"OrderNum":this.positionListCont[positionCurrent].num,
								"Drection":drection,
								"PriceType":1,
								"LimitPrice":0.00,
								"TriggerPrice":0,
								"OrderRef":this.$store.state.market.tradeConfig.client_source+ new Date().getTime()+(buildIndex++)
							}
						};
//						this.$store.state.market.positionListCont.splice(positionCurrent,1);
//						
//						this.$store.state.market.qryHoldTotalArr.splice(this.qryHoldTotalArr.length-1-positionCurrent,1);
						
						this.tempText = b;
						return false;
//						this.tradeSocket.send(JSON.stringify(b));
					}else{
						console.log(2);
						this.$children[4].isShow = true;
						this.msg = '请选择一条数据';
					}
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
			.list_cont_box{
				overflow-y: scroll;
			}
			li{
				width: 100%;
				background: @deepblue;
				border-top: 1px solid @black;
				&.current{
					background: #2d3040;
				}
				&.list_head{
					height: 44px;
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
						width: 24%;
					}
					&:nth-child(2){
						width: 12%;
					}
					&:nth-child(3){
						width: 12%;
					}
					&:nth-child(4){
						width: 18%;
					}
					&:nth-child(5){
						width: 24%;
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
			.list_cont_box{
				overflow-y: scroll;
			}
			li{
				width: 100%;
				background: @deepblue;
				border-top: 1px solid @black;
				&.current{
					background: #2d3040;
				}
				&.list_head{
					height: 44px*@ip6;
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
						width: 24%;
					}
					&:nth-child(2){
						width: 12%;
					}
					&:nth-child(3){
						width: 12%;
					}
					&:nth-child(4){
						width: 18%;
					}
					&:nth-child(5){
						width: 24%;
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
			.list_cont_box{
				overflow-y: scroll;
			}
			li{
				width: 100%;
				background: @deepblue;
				border-top: 1px solid @black;
				&.current{
					background: #2d3040;
				}
				&.list_head{
					height: 44px*@ip5;
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
						width: 24%;
					}
					&:nth-child(2){
						width: 12%;
					}
					&:nth-child(3){
						width: 12%;
					}
					&:nth-child(4){
						width: 18%;
					}
					&:nth-child(5){
						width: 22%;
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