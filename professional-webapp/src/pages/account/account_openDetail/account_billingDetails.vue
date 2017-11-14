<template>
	<div id="billingDetails">
		<div class="bg"></div>
		<div class="billingDetails">
			<p class="title">终结方案<i class="ifont ifont_x" v-on:click="close">&#xe624;</i></p>
			<div class="details">
				<p>结算金额明细</p>
				<p><span>{{endAmount}}</span>元（结算金额）<span>={{traderBond}}元</span>（操盘保证金）+<span>{{appendTraderBond}}元</span>（追加保证金）+<span>{{tradeSell}}元</span>（交易盈亏）-<span>{{tranFees}}元</span>（手续费）</p>
				<p><i>注意：</i>交易手续费= 合约手续费x交易手数</p>
			</div>
			<div class="handDetails">
				<p>交易明细手数</p>
				<table >
					<thead>
						<tr>
							<td>合约名称</td>
							<td>服饰a50</td>
							<td>国际原油</td>
							<td>恒指期货</td>
							<td>迷你恒指</td>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>交易手数</td>
							<td>10</td>
							<td>10</td>
							<td>10</td>
							<td>10</td>
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
							<td>平仓盈亏</td>
							<td>平仓手数</td>
							<td>开仓手数</td>
							<td>类型</td>
						</tr>
					</thead>
					<tbody>
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
						</tr>
					</tbody>
				</table>
				<div class="pager">
				    <button class="btn_span"  @click="prePage">上一页</button>
				    <span  @click="toIndexPage" v-for="(n,index) in pageCount" :class="{active:current1 == index}">{{n}}</span>
				    <button class="btn_span"  @click="nextPage">下一页</button>
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
				pageCount:'',
				id:'',
				endAmount:'',
				traderBond:'',
				appendTraderBond:'',
				tranProfitLoss:'',
				endParities:'',
				tranFees:"",
				tradeSell:''
			}
		},
		methods:{
			close:function(){
				
			},
			toIndexPage:function(){
				
			},
			prePage:function(){
				
			},
			nextPage:function(){
				
			},
			//获取成交详情
			details:function(a){
				var headers = {
					token:JSON.parse(localStorage.user).token,
					secret:JSON.parse(localStorage.user).secret
				}
				var data = {
					id:this.id
				}
				console.log(data)
				pro.fetch("post","/ user/ftrade/details",data,headers).then((res)=>{
					console.log(res)
					if(res.success == true){
						if(res.code == 1){
							this.endAmount = res.data.endAmount.toFixed(2)
							this.traderBond = res.data.traderBond
							this.appendTraderBond = res.data.appendTraderBond
							this.tranProfitLoss=res.data.tranProfitLoss,
							this.endParities=res.data.endParities,
							this.tradeSell = (this.tranProfitLoss*this.endParities).toFixed(2)
							this.tranFees = res.data.tranFees
						}
					}
				}).catch((err)=>{
					console.log(err)
//					if(err.data.success == false){
//						switch (err.data.success){
//							case value:
//								break;
//							default:
//								break;
//						}
//					}else {
//						layer.msg("网络不给力，请稍后再试",{time:2000})
//					}
				})
			}
		},
		mounted:function(){
			this.id = this.$route.query.id;
			//获取成交详情
			this.details(this.id);
		},
		actived:function(){
			this.id = this.$route.query.id;
		}
	}
</script>

<style lang="scss" scoped type="text/css">
@import "../../../assets/css/common.scss";
	#billingDetails{
		height: 800px;
		text-indent: 10px;
	}
	.billingDetails{
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