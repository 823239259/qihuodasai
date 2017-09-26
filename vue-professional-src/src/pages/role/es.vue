<template>
	<div id="role">
		<topbar title="迷你标准普尔交易规则"></topbar>
		<back></back>
		<div class="page_cont">
			<h3>操盘流程：</h3>
			<div class="list">
				<table>
					<tr>
						<td>交易品种</td>
						<td>迷你标准普尔</td>				
					</tr>
					<tr>
						<td>币种单位</td>
						<td>美元</td>				
					</tr>
					<tr>
						<td>交易单位</td>
						<td>1个指数点</td>				
					</tr>
					<tr>
						<td>最小波动</td>
						<td>12.5美元/0.25点</td>				
					</tr>
					<tr>
						<td>波动盈亏</td>
						<td>波动一次=12.5美元</td>				
					</tr>
					<tr>
						<td>交易时间</td>
						<td>买入时间：白天06:00:00 – 16:15:00,夜间16:30:00-05:00:00,卖出时间：白天06:00:00 – 16:15:00,夜间16:30:00-04:50:00</td>				
					</tr>
					<tr>
						<td>清仓时间</td>
						<td>夜间 04:50:00</td>				
					</tr>
					<tr>
						<td>交易综合费</td>
						<td>119/手</td>				
					</tr>
				</table>
			</div>
			<p>芝加哥商业交易所迷你标准普尔，最小波动12.5美元，可买涨买跌。</p>
			<h3>什么是买涨？</h3>
			<p>当您买涨时，价格涨了你就赚钱，跌了亏钱。</p>
			<h3>什么是买跌？</h3>
			<p>当您买跌时，价格跌了你就赚钱，涨了亏钱。</p>
			<h3>什么是止盈单？</h3>
			<p>当单笔交易盈利金额大于等于设定的止盈金额时，该笔交易会被按市价自动强制平仓。由于市场价格在实时变动，不保证最终盈利金额与止盈金额一致，有可能小于设置的止盈金额。</p>
			<h3>什么是止损单？</h3>
			<p>当单笔交易亏损金额大于等于指定的止损金额时，该笔交易会被按市价自动强制平仓。由于市场价格的实时变动，不保证最终亏损金额与止损金额一致，有可能大于设置的止损金额。</p>
			<h3>什么是时间条件单</h3>
			<p>即预先设置某一价位（开仓或平仓），一旦触及该价格，系统自动提交您的委托，从而实现自动委托。保存于客户设备本地，当行情满足客户设定的条件，软件自动触发委托。注意：关机或者退出软件会导致条件单失效。</p>
			<h3>什么是价格条件单？</h3>
			<p>即预先设置某一价位（开仓或平仓），一旦触及该价格，系统自动提交您的委托，从而实现自动委托。保存于客户设备本地，当行情满足客户设定的条件，软件自动触发委托。注意：关机或者退出软件会导致条件单失效。</p>
			<h3>什么是持仓时间？</h3>
			<p>迷你标准普尔最后持仓时间：04:50:00；当持仓时间到点后，持仓中的交易会被强制平仓，不保证成交价格，请务必在到期前自己选择卖出。</p>
			<h3>什么是强平线？</h3>
			<p>平台会根据交易所提提供的结果每天对您的盈亏状况进行结算，当您的账户余额达到设置的强平线时，系统将会把账户中的持仓合约强平掉，撤销掉所有挂单合约，并冻结账户。所以您在交易时，要时刻注意自己的资金状况。</p>
			<h3>什么是风险度？</h3>
			<p>风险度越高标志着您距离被强平的越近，所以您在交易时，要时刻注意自己的资金状况。所以当保证金不能在规定时间内补足，期货价格波动较大的话，您可能面临强行平仓风险。</p>
			<h3>涨跌幅限制</h3>
			<p>7%、13%、20%(仅跌停)三级熔段。</p>
			<h3>交易综合费</h3>
			<p>迷你标准普尔买手交易综合费：119元（买进卖出只收取一次）</p>
			<h3>履约保证金</h3>
			<p>履约保证金为您委托平台冻结用于履行交易亏损赔付义务的保证金。以冻结的履约保证金作为承担交易亏损赔付，但交易亏损可能超出保证金金额，超出部分的亏损全部由您承担，平台不承担交易亏损。合作交易结束后，根据清结算结果，如交易盈利，您冻结的履约保证金全额退还。如交易亏损，从冻结的履约保证金中，扣减您所应承担的亏损赔付额，扣减后余额退还。如亏损超出保证金，则直接在您账户余额中扣除。</p>
			<h3>盈利如何分配？</h3>
			<p>盈利100%归用户所有，平台不参与盈利分成。若用户亏损超出保证金，平台也不承担超出部分的亏损金额。</p>
			<h3>实盘交易下单</h3>
			<p>您的所有迷你标准普尔交易，全部经由平台的期货交易账户，下单到芝加哥商业交易所。</p>
		</div>
	</div>
</template>

<script>
	import topbar from '../../components/Topbar.vue'
	import back from '../../components/back.vue'
	export default{
		name:'role',
		components: {topbar, back},
		activated: function(){
			//不更新画图
			this.$store.state.isshow.isklineshow = false;
			this.$store.state.isshow.isfensshow = false;
			this.$store.state.isshow.islightshow =  false;
		}
	}
</script>

<style scoped lang="less">
@import url("../../assets/css/base.less");
/*ip6p及以上*/
@media (min-width:411px) {
    #role{
    	width: 100%;
    	overflow: hidden;
    	background: @black;
    	padding-top: 50px;
    	.page_cont{
    		overflow: hidden;
    		background: @deepblue;
    		h3{
    			height: 42px;
    			line-height: 42px;
    			border-bottom: 1px solid @black;
    			padding: 0 15px;
    			color: @yellow;
    			font-size: @fs16;
    		}
    		p{
    			line-height: 26px;
    			color: @white;
    			font-size: @fs14;
    			padding: 8px 15px;
    			border-bottom: 5px solid @black;
    		}
    		.list{
    			width: 398px;
    			overflow-x: scroll;
    			padding: 0 15px;
    			table{
    				width: 382px;
    				td{
    					text-align: left;
    					padding: 5px;
    					border: 1px solid @white;
    					font-size: @fs14;
    					color: @white;
    					&:nth-child(1){
    						width: 120px;
    					}
    					&:nth-child(2){
    						width: 200px;
    					}
    				}
    			}
    		}
    	}
    }
}
/*ip6*/
@media (min-width:371px) and (max-width:410px) {
	#role{
    	width: 100%;
    	overflow: hidden;
    	background: @black;
    	padding-top: 50px*@ip6;
    	.page_cont{
    		overflow: hidden;
    		background: @deepblue;
    		h3{
    			height: 42px*@ip6;
    			line-height: 42px*@ip6;
    			border-bottom: 1px solid @black;
    			padding: 0 15px*@ip6;
    			color: @yellow;
    			font-size: @fs16*@ip6;
    		}
    		p{
    			line-height: 26px*@ip6;
    			color: @white;
    			font-size: @fs14*@ip6;
    			padding: 8px*@ip6 15px*@ip6;
    			border-bottom: 5px*@ip6 solid @black;
    		}
    		.list{
    			width: 398px*@ip6;
    			overflow-x: scroll;
    			padding: 0 15px*@ip6;
    			table{
    				width: 382px*@ip6;
    				td{
    					text-align: left;
    					padding: 5px;
    					border: 1px solid @white;
    					font-size: @fs14*@ip6;
    					color: @white;
    					&:nth-child(1){
    						width: 120px*@ip6;
    					}
    					&:nth-child(2){
    						width: 200px*@ip6;
    					}
    				}
    			}
    		}
    	}
    }
}
/*ip5*/
@media(max-width:370px) {
	#role{
    	width: 100%;
    	overflow: hidden;
    	background: @black;
    	padding-top: 50px*@ip5;
    	.page_cont{
    		overflow: hidden;
    		background: @deepblue;
    		h3{
    			height: 42px*@ip5;
    			line-height: 42px*@ip5;
    			border-bottom: 1px solid @black;
    			padding: 0 15px*@ip5;
    			color: @yellow;
    			font-size: @fs16*@ip5;
    		}
    		p{
    			line-height: 26px*@ip5;
    			color: @white;
    			font-size: @fs14*@ip5;
    			padding: 8px*@ip5 15px*@ip5;
    			border-bottom: 5px*@ip5 solid @black;
    		}
    		.list{
    			width: 398px*@ip5;
    			overflow-x: scroll;
    			padding: 0 15px*@ip5;
    			table{
    				width: 382px*@ip5;
    				td{
    					text-align: left;
    					padding: 5px;
    					border: 1px solid @white;
    					font-size: @fs14*@ip5;
    					color: @white;
    					&:nth-child(1){
    						width: 120px*@ip5;
    					}
    					&:nth-child(2){
    						width: 200px*@ip5;
    					}
    				}
    			}
    		}
    	}
    }
}
</style>