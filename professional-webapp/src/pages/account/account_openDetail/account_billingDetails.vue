<template>
	<div id="billingDetails">
		<div class="bg"></div>
		<div class="billingDetails">
			<p class="title">终结方案<i class="ifont ifont_x" v-on:click="close">&#xe624;</i></p>
			<div class="details">
				<p>结算金额明细</p>
				<p v-if="tradeSell<0"><span>{{endAmount | fixNumTwo}}</span>元（结算金额）<span>={{traderBond | fixNumTwo}}元</span>（操盘保证金）+<span>{{appendTraderBond | fixNumTwo}}元</span>（追加保证金）<span >{{tradeSell | fixNumTwo}}元</span>（交易盈亏）-<span>{{tranFeesTotal | fixNumTwo}}元</span>（手续费）</p>
				<p v-else="tradeSell !<0"><span>{{endAmount | fixNumTwo}}</span>元（结算金额）<span>={{traderBond | fixNumTwo}}元</span>（操盘保证金）+<span>{{appendTraderBond | fixNumTwo}}元</span>（追加保证金）<span >+{{tradeSell | fixNumTwo}}元</span>（交易盈亏）-<span>{{tranFeesTotal | fixNumTwo}}元</span>（手续费）</p>
				<p><i>注意：</i>交易手续费= 合约手续费x交易手数</p>
			</div>
			<div class="handDetails">
				<p>交易明细手数</p>
				<table >
					<thead>
						<tr>
							<td>合约名称</td>
							<td v-for="(key,value) in handList">{{value}}</td>
							<!--<td>国际原油</td>
							<td>恒指期货</td>
							<td>迷你恒指</td>-->
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>交易手数</td>
							<td v-for="(key,value) in handList">{{key}}</td>
							<!--<td>10</td>
							<td>10</td>
							<td>10</td>-->
						</tr>
					</tbody>
				</table>
			</div>
			<div class="historyRecord">
				<p>成交记录</p>
				<table>
					<thead>
						<tr>
							<td>序号</td>
							<td>成交时间</td>
							<td>交易账号</td>
							<td>币种</td>
							<td>交易所</td>
							<td>品种</td>
							<td>买</td>
							<td>卖</td>
							<td>成交价</td>
							<td>手续费</td>
						</tr>
					</thead>
					<tbody>
						<tr v-for="(item,index) in historyList">
							<td>{{index+1}}</td>
							<td>{{item.tradeDate}}</td>
							<td>{{item.userNo}}</td>
							<td>{{item.currencyNo}}</td>
							<td>{{item.exchangeNo}}</td>
							<td>{{item.commodityNo}}</td>
							<td v-if="item.buyNum!=''">{{item.buyNum}}</td>
							<td v-else="item.buyNum==''">0</td>
							<td v-if="item.sellNum!=''">{{item.sellNum}}</td>
							<td v-else="item.sellNum==''">0</td>
							<td>{{item.tradePrice}}</td>
							<td>{{item.free}}</td>
						</tr>
						<!--<tr>
							<td>1</td>
							<td>2017-11-07 23：12:12</td>
							<td>qd0030</td>
							<td>usd</td>
							<td>hed</td>
							<td>hsi1071</td>
							<td>+</td>
							<td>-</td>
							<td>23165.00</td>
							<td>59.00</td>
							<td>55.00</td>
							<td>10</td>
							<td>0</td>
							<td>强制平仓</td>
						</tr>
						<tr>
							<td>1</td>
							<td>2017-11-07 23：12:12</td>
							<td>qd0030</td>
							<td>usd</td>
							<td>hed</td>
							<td>hsi1071</td>
							<td>+</td>
							<td>-</td>
							<td>23165.00</td>
							<td>59.00</td>
							<td>55.00</td>
							<td>10</td>
							<td>0</td>
							<td>强制平仓</td>
						</tr>
						<tr>
							<td>1</td>
							<td>2017-11-07 23：12:12</td>
							<td>qd0030</td>
							<td>usd</td>
							<td>hed</td>
							<td>hsi1071</td>
							<td>+</td>
							<td>-</td>
							<td>23165.00</td>
							<td>59.00</td>
							<td>55.00</td>
							<td>10</td>
							<td>0</td>
							<td>强制平仓</td>
						</tr>-->
					</tbody>
				</table>
				<div class="pager">
				    <button class="btn_span"   v-show="currentPage != 1" @click="currentPage-- && goIndex(currentPage--,'last')">上一页</button>
				    <span  v-for="index in pages" @click="goIndex(index)" :class="{active:currentPage == index}" :key="index">{{index}}</span>
				    <button class="btn_span"  v-show="pageCount != currentPage && pageCount != 0 " @click="currentPage++ && goIndex(currentPage++,'!last')">下一页</button>
				  </div>
			</div>
		</div>
	</div>
</template>

<script>
	import pro from "../../../assets/js/common.js"
	export default{
		name: "openDetail_billingDetails",
		data(){
			return{
				current1:0,
				id:'',
				endAmount:'',
				traderBond:'',
				appendTraderBond:'',
				tranProfitLoss:'',
				endParities:'',
				tranFeesTotal:"",
				tradeSell:'',
				handList:{},
				historyList:[],//显示数据
				pageCount:'',//总页数
				eachPage:5,//每页条数
				showPage:true,//是否显示分页
				currentPage:1,//当前页
				totalHistoryList:[]//总数据
			}
		},
		filters:{
			fixNumTwo: function(num){
				return Number(num).toFixed(2);
			},
			fixNum: function(num, dotsize){
				return Number(num).toFixed(dotsize);
			}
		},
		methods:{
			goIndex:function(index,el){
	            if(index == this.currentPage) return;
	            this.currentPage = index;
	            var curtotal=(this.currentPage-1)*this.eachPage;//上一页显示的最后一条
	            var tiaoshu=this.currentPage*this.eachPage;//当前页显示的最后一条
	            this.historyList=this.totalHistoryList.slice(curtotal,tiaoshu); //当前页应显示的数据
	        },
			close:function(){
				this.$router.push({path:'/account_openDetail'});
				this.$store.state.account.currentNav = 6;
			},
			//获取成交详情
			details:function(){
				var headers = {
					token:JSON.parse(localStorage.user).token,
					secret:JSON.parse(localStorage.user).secret
				}
				var data = {
					id:this.id
				}
				pro.fetch("post","/user/ftrade/details",data,headers).then((res)=>{
					var data = res.data.details
					if(res.success == true){
						if(res.code == 1){
							this.endAmount = data.endAmount;
							this.traderBond = data.traderBond;
							this.appendTraderBond = data.appendTraderBond;
							this.tranProfitLoss=data.tranProfitLoss;
							this.endParities=data.endParities;
							this.tradeSell = this.tranProfitLoss*this.endParities;
							this.tranFeesTotal = data.tranFeesTotal;
							if(data.tranActualLever!=0){
								this.handList.富时A50 = data.tranActualLever
							}
							if(data.crudeTranActualLever!=0){
								this.handList.国际原油 = data.crudeTranActualLever
							}
							if(data.hsiTranActualLever!=0){
								this.handList.恒指期货 = data.hsiTranActualLever
							}
							if(data.mdtranActualLever!=0){
								this.handList.迷你道指 = data.mdtranActualLever
							}
							if(data.mntranActualLever!=0){
								this.handList.迷你纳指 = data.mntranActualLever
							}
							if(data.mbtranActualLever!=0){
								this.handList.迷你标普 = data.mbtranActualLever
							}
							if(data.daxtranActualLever!=0){
								this.handList.德国DAX = data.daxtranActualLever
							}
							if(data.nikkeiTranActualLever!=0){
								this.handList.日经225 = data.nikkeiTranActualLever
							}
							if(data.lhsiTranActualLever!=0){
								this.handList.小恒指 = data.lhsiTranActualLever
							}
							if(data.agTranActualLever!=0){
								this.handList.美黄金 = data.agTranActualLever
							}
							if(data.hIndexActualLever!=0){
								this.handList.H股指数 = data.hIndexActualLever
							}
							if(data.xHStockMarketLever!=0){
								this.handList.小H股指数 = data.xHStockMarketLever
							}
							if(data.ameCopperMarketLever!=0){
								this.handList.美铜 = data.ameCopperMarketLever
							}
							if(data.aSilverActualLever!=0){
								this.handList.美白银 = data.aSilverActualLever
							}
							if(data.smallCrudeOilMarketLever!=0){
								this.handList.小原油 = data.smallCrudeOilMarketLever
							}
							if(data.daxtranMinActualLever!=0){
								this.handList.迷你德国DAX = data.daxtranMinActualLever
							}
							if(data.naturalGasActualLever!=0){
								this.handList.天然气 = data.naturalGasActualLever
							}
						}
					}
				}).catch((err)=>{
					if(err.success == false){
						switch (err.code){
							case value:
								break;
							default:
								break;
						}
					}else {
						layer.msg("网络不给力，请稍后再试",{time:2000})
					}
				})
			},
			//获取历史成交
			getHistory:function(){
				var headers = {
					token:JSON.parse(localStorage.user).token,
					secret:JSON.parse(localStorage.user).secret
				}
				var data = {
					id:this.id
				}
				pro.fetch("post","/user/ftrade/getFstTradeDetail",data,headers).then((res)=>{
					if(res.success == true){
						if(res.code == ""){
							this.totalHistoryList = res.data;
							this.pageCount = Math.ceil(this.totalHistoryList.length/5);
							var curtotal=(this.currentPage-1)*this.eachPage;//上一页显示的最后一条
				            var tiaoshu=this.currentPage*this.eachPage;//当前页显示的最后一条
				            this.historyList=this.totalHistoryList.slice(curtotal,tiaoshu); //当前页应显示的数据
						}
					}
				}).catch((err)=>{
					if(err.success == false){
						
					}else{
						layer.msg("网络不给力，请稍后再试",{time:2000})
					}
				})
			}
		},
		mounted:function(){
			//初始化高度
			var h = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;
			var _h = h - 80 - 47;
			var contH = $(".billingDetails").height();
			if(contH > _h){
				$(".billingDetails").height(_h);
			}
			$(window).resize(function(){
				var h = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;
				var _h = h - 80 - 47;
				if(contH > _h){
					$(".billingDetails").height(_h);
				}
			});
			this.id = this.$route.query.id;
			//获取成交详情
			this.details();
			//获取历史成交
			this.getHistory();
		},
		actived:function(){
			this.id = this.$route.query.id;
		},
		computed:{
			pages:function(){
                var pag = [];
                    if( this.currentPage < this.eachPage ){
                      
                       var i = Math.min(this.eachPage,this.pageCount);
                       while(i){
                           pag.unshift(i--);
                       }
                    }else{ 
                       var middle = this.currentPage - Math.floor(this.eachPage / 2 ),
                           i = this.eachPage;
                       if( middle >  (this.pageCount - this.eachPage)  ){
                           middle = (this.pageCount - this.eachPage) + 1
                       }
                       while(i--){
                           pag.push( middle++ );
                       }
                   }
                 return pag
                }
		}
	}
</script>

<style lang="scss" scoped type="text/css">
@import "../../../assets/css/common.scss";
	#billingDetails{
		height: 700px;
		text-indent: 10px;
	}
	.billingDetails{
		overflow-y: scroll;
		float: left;
		position: absolute;
		top: 50%;
		left: 50%;
		margin:-300px 0 0 -500px;
		width: 1000px;
		height: 600px;
		z-index: 120;
		border-radius: 10px;
		background-color: $deepblue;
	}
	.title{
		height: 40px;
		background-color: $bottom_color;
		border-radius: 10px 10px 0 0;
		line-height: 40px;
		text-align: center;
	}
	.ifont_x {
		float: right;
		margin-right: 10px;
		color: $lightblue;
	}
	.details{
		height: 110px;
		p{
			&:nth-child(1){
				height: 40px;
				line-height: 40px;
				color: $white;
			}
			&:nth-child(2){
				height: 35px;
				background-color: $blue;
				padding-top: 20px;
			}
			&:nth-child(3){
				height: 35px;
				background-color: $blue;
			}
			
		}
		i{
			color: $yellow;
		}
		span{
			color: $white;
			font-weight: 500;
		}
	}
	.handDetails{
		height: 120px;
		p{
			height: 40px;
			line-height: 40px;
			color: $white;
		}
		tr{
			height: 40px;
		}
		td{
			border: 1px solid $bottom_color;
		}
	}
	.historyRecord{
		height: 330px;
		p{
			height: 40px;
			line-height: 40px;
			color: $white;
		}
		table{
			height: 230px;
			background-color: $blue;
		}
		thead{
			height: 30px;
			background-color: $bottom_color;
		}
		tbody{
			tr{
				height: 40px;
			}
		}
	}
	.pager{
		float: right;
		span{
			margin: 0 10px;
		}
	}
	.btn_span{
		color: $white;
		background-color: $blue;
		border: none;
		margin: 0 10px;
	}
	.active {
		color: $yellow;
	}
</style>