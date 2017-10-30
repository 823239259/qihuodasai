<template>
	<div id="account_openDetail">
				<div class="account_openDetail_top">
					<ul>
						<li>
							<span>今天</span>
							<span>一个月</span>
							<span>三个月</span>
							<span>一年</span>
							<span>起始时间</span>
							<span>2017-07-15</span>
							<span>></span>
							<span>2017-07-16</span>
						</li>
						<li>
							<span>全部</span>
							<span>开户中</span>
							<span>操盘中</span>
							<span>已结算</span>
						</li>
					</ul>
				</div>
				<div class="account_openDetail_center">
					<table>
						<thead>
							<tr>
								<td>交易品种</td>
								<td>状态</td>
								<td>初始保证金</td>
								<td>追加保证金</td>
								<td>总操盘资金</td>
								<td>亏损平仓线</td>
								<td>申请时间</td>
								<td>结算时间</td>
								<td>结算金额</td>
								<td>操作</td>
							</tr>
						</thead>
						<tbody>
							<tr v-for="item in item" v-if="show_detail">
								<td>{{item.businessTypeStr}}</td>
								<td>{{item.stateTypeStr}}</td>
								<td>{{item.traderBond}}元</td>
								<td v-if="item.appendTraderBond!=''">{{item.appendTraderBond}}</td>
								<td v-else>-</td>
								<td>{{item.traderTotal}}元</td>
								<td>{{item.lineLoss}}美元</td>
								<td>2017-07-06</br>16:29:55</td>
								<td>-</td>
								<td v-if="item.endAmount!=''">{{item.endAmount}}</td>
								<td v-else="item.endAmount == ''">-</td>
								<td v-if="item.stateTypeStr =='开户中'"></td>
								<td v-else="item.stateTypeStr == '操盘中'">
									<span v-on:click="toOpenDetailTrade">查看账户</span></br>
									<span v-on:click="toAdditionlMargin">补充保证金</span></br>
									<span v-on:click="toSettlementScheme">结算方案</span>
								</td>
								<td v-else="item.stateTypeStr == '已结算'" v-on:click="toParticulars">结算明细</td>
							</tr>
							<!--<tr>
								<td>国际综合</td>
								<td>开户中</td>
								<td>3000元</td>
								<td>-</td>
								<td>3000元</td>
								<td>5270美元</td>
								<td>2017-07-06</br>16:29:55</td>
								<td>-</td>
								<td>-</td>
								<td v-on:click="toAdditionlMargin">查看账号</td>
							</tr>
							<tr>
								<td>国际综合</td>
								<td>开户中</td>
								<td>3000元</td>
								<td>-</td>
								<td>3000元</td>
								<td>5270美元</td>
								<td>2017-07-06</br>16:29:55</td>
								<td>-</td>
								<td>-</td>
								<td v-on:click="toParticulars">结算明细</td>
							</tr>-->
						</tbody>
					</table>
					<div v-if="show_button" class="show_button">
						<button class="yellow  btn" v-on:click="toOpenAccount">我要开户</button>
						<p>(一个账号可同时交易多种期货产品)</p>
					</div>
				</div>
				<div class="account_openDetail_notice">
					<p>注意：</p>
					<ul>
						<li>1.交易手续费=合约手续费x手数</li>
						<li>2.买卖各算一手</li>
						<li>3.结算金额=操盘保证金+补充保证金+交易盈亏-交易手续费</li>
					</ul>
				</div>
				<div class="account_openDetail_btm">
					<p>投资有风险，入市需谨慎</p>
				</div>
			</div>
</template>

<script>
	import pro from "../../../assets/js/common.js"
	export default {
		name : "account_openDetail",
		data(){
			return{
				item : [],
				show_detail : false,
				show_button : false
			}
		},
		methods:{
			//查看账号
			toOpenDetailTrade:function(){
				this.$router.push({path:'/openDetail_additionlMargin'})
			},
			//追加保证金
			toAdditionlMargin:function(){
				
			},
			//结算明细
			toParticulars:function(){
				
			},
			//结算方案
			toSettlementScheme:function(){
				
			},
			//去开户
			toOpenAccount :function(){
				this.$router.push({path:'/openAccount'})
			}
		},
		mounted :function(){
			//获取初始开户记录
			var data = {
				page:1,
				rows:20,
			    startTime:'',
			    endTime:'',
			    stateType:'',
			}
			var headers = {
				token : JSON.parse(localStorage.user).token,
				secret : JSON.parse(localStorage.user).secret
			}
			pro.fetch("post",'/user/ftrade/list',data,headers).then(function(res){
				console.log(res);
				if(res.success == true){
					if(res.code == 1){
						if(res.data.tradeList == ''){
							this.show_button = true;
						}
						else {
							this.show_detail = true;
							this.item = res.data.tradeList;
						}
					}
				}
			}.bind(this)).catch(function(err){
				layer.msg('网络不给力，请稍后重试',{time:1000})
			})
		}
	}
</script>

<style lang="scss" scoped type="text/css">
@import "../../../assets/css/common.scss";
	#account_openDetail{
			width: 100%;
			margin-top: 5px;
			.account_openDetail_top {
				height: 110px;
				background-color: $blue;
				li {
					&:nth-child(1){
						height: 70px;
						border-bottom: 1px solid $bottom_color;
						line-height: 70px;
						span {
							&:nth-child(1) {
								color: $yellow;
							}
						}
					}
					&:nth-child(2){
						height: 40px;
						line-height: 40px;
						span {
							&:nth-child(1) {
								color: $yellow;
							}
						}
					}
				}
			}
			.account_openDetail_center{
				background-color: $blue;
			}
			.account_openDetail_notice {
				height: 120px;
				background-color: $blue;
				p {
					color: $yellow;
					font-size: $fs14;
					padding-top: 20px;
				}
				li {
					margin-top: 10px;
					font-size: $fs12;
				}
			}
			.account_openDetail_btm{
				p {
					width: 100%;
					height: 40px;
					line-height: 40px;
					margin-top: 10px;
					background-color: $blue;
					text-align: center;
					font-size: $fs12;
				}
			}
		}
		.yellow {
			width: 120px;
			height: 30px;
			margin-top: 85px;
			margin-bottom: 10px;
			
		}
		.show_button {
			text-align: center;
			p{
				font-size: $fs12;
			}
		}
</style>