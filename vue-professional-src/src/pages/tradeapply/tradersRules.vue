<template>
	<div id="tradersRules">
		<topbar title="国际综合操盘细则"></topbar>
		<back></back>
		<cs title="客服"></cs>
		<div class="page_cont">
			<h3>操盘流程：</h3>
			<p>①选择方案 → ②支付保证金 → ③获取操盘账号 → ④在指定软件里操盘 → ⑤终结方案、结算到账</p>
			<h3>操盘合约：</h3>
			<p>可同时操盘17种国际期货的当期主力合约，分别为：富时A50、恒指期货、国际原油、迷你纳指、迷你道指、迷你标普、德国DAX、日经225、小恒指、美黄金、H股指数、小H股指数、美铜、美白银、小原油、迷你德国DAX指数、天然气。</p>
			<h3>交易时间： </h3>
			<p>所有种类的期货交易时间基本与交易所同步。由于风控限制，分别于交易所收市时间提前5分钟执行强平；因新加坡交易所的特殊交易规则，富时A50、日经225需在收市前10分钟执行强平。不同期货品种具体交易时间段以方案申请页公示为准。</p>
			<h3>操盘保证金：</h3>
			<p>保证金越多，可同时交易的期货品种越多，可持仓手数也越多；结束时若亏损，用保证金赔付，若无亏损，则全额退还。</p>
			<h3>总操盘资金： </h3>
			<p>总操盘资金=操盘保证金+平台授信额度</p>
			<h3>亏损平仓线：</h3>
			<p>总操盘资金低于亏损平仓线时，系统将自动平仓；低于平仓线后无法开仓，需要追加保证金到平仓线以上才可继续交易。</p>
			<h3>账户管理费： </h3>
			<p>目前黄金期货通不收取账户管理费。</p>
			<h3>交易手续费： </h3>
			<p>每手开仓、平仓的手续费，不同期货品种具体手续费不同，由交易软件直接扣取。</p>
			<h3>操盘账号：</h3>
			<p>方案申请成功后，交易日30分钟内分配操盘账号，非交易日时下个交易日开盘前15分钟分配操盘账号。</p>
			<h3>模拟操盘：  </h3>
			<p>目前国际综合模拟操盘账号有限，请致电<a href="#">400-180-1860</a>申请。</p>
			<h3>追加保证金： </h3>
			<p>当您暂时亏损时，可选择追加保证金，始终保持操盘账号里实盘资金充足，在下次看准方向，狠狠地赚回来。</p>
			<h3>终结方案： </h3>
			<p>交易日内8:00-23:30的申请，当天结算到账；23:30-次日8:00的申请，次日9:00前结算到账。非交易日的申请，下个交易日9:00前结算到账。</p>
			<h3>结算汇率：  </h3>
			<p>操盘盈亏为美元，结算时换算成人民币。结算汇率采用您申请终结方案当天的美元兑人民币的第一笔现钞卖出价。</p>
			<h3>国际综合介绍：</h3>
			<div class="list">
				<table> 
					<thead>
						<td>期货产品</td>
						<td>上市交易所</td>
						<td>交易标的</td>
						<td>最小波动价</td>
						<td>涨跌幅限制</td>
					</thead>
					<tr>
						<td>富时A50</td>
						<td>新加坡交易所</td>				
						<td>新华富时A50指数</td>
						<td>2.5个指数点(2.5美元)</td>
						<td>当涨跌幅±10%和±15%时，分别熔断10分钟，之后无涨跌幅限制</td>
					</tr>
					<tr>
						<td>恒指期货</td>
						<td>香港交易所</td>				
						<td>香港恒生指数</td>
						<td>1个指数点（50港元）</td>
						<td>无涨跌幅限制</td>
					</tr>
					<tr>
						<td>国际原油</td>
						<td>纽约商业交易所</td>				
						<td>轻质低硫原油即WTI</td>
						<td>0.01个指数点（10美元）</td>
						<td>±10美元，暂停交易5分钟后再扩大±10美元，以此循环</td>
					</tr>
					<tr>
						<td>迷你道指</td>
						<td>芝加哥交易所</td>				
						<td>美国道琼斯指数</td>
						<td>1个指数点（5美元）</td>
						<td>7%、13%、20%(仅跌停)三级熔断</td>
					</tr>
					<tr>
						<td>迷你纳指</td>
						<td>芝加哥交易所</td>				
						<td>美国纳斯达克指数</td>
						<td>0.25个指数点（5美元）</td>
						<td>7%、13%、20%(仅跌停)三级熔断</td>
					</tr>
					<tr>
						<td>迷你标普</td>
						<td>芝加哥交易所</td>				
						<td>美国标准普尔指数</td>
						<td>0.25个指数点（12.5美元）</td>
						<td>7%、13%、20%(仅跌停)三级熔断</td>
					</tr>
					<tr>
						<td>德国DAX</td>
						<td>欧洲期货交易所</td>				
						<td>德国DAX指数</td>
						<td>0.5个指数点（12.5欧元）</td>
						<td>无涨跌幅限制</td>
					</tr>
					<tr>
						<td>日经225</td>
						<td>新加坡交易所</td>				
						<td>日本日经225指数</td>
						<td>5个指数点（2500日元）</td>
						<td>当涨跌幅±7.5%和±12.5%时，分别熔断15分钟，即月合约在最后交易日无涨跌停</td>
					</tr>
					<tr>
						<td>小恒指</td>
						<td>香港交易所</td>				
						<td>香港恒生指数</td>
						<td>1个指数点（10港元）</td>
						<td>无涨跌停限制</td>
					</tr>
					<tr>
						<td>美精铜</td>
						<td>芝加哥交易所</td>				
						<td>国际市场美精铜价格</td>
						<td>0.0005个指数点（12.5美元）</td>
						<td>±0.40美元,所有合约熔断2分钟</td>
					</tr>
					
					<tr>
						<td>小原油</td>
						<td>芝加哥交易所</td>				
						<td>轻质低硫原油即WTI</td>
						<td>0.025个指数点（12.5美元）</td>
						<td>±10美元若最近的三个合约同时出现涨跌停，所有合约熔断5分钟</td>
					</tr>
					<tr>
						<td>美白银</td>
						<td>芝加哥交易所</td>				
						<td>国际白银市场白银价格</td>
						<td>0.005个指数点（25美元）</td>
						<td>±3美元, 所有合约熔断2分钟</td>
					</tr>
					<tr>
						<td>美黄金</td>
						<td>芝加哥交易所</td>				
						<td>黄金</td>
						<td>0.1个指数点（10美元）</td>
						<td>无涨跌停限制</td>
					</tr>
					<tr>
						<td>H股指数</td>
						<td>香港交易所</td>				
						<td>H股指数</td>
						<td>1个指数点（50港元）</td>
						<td>T+1时段的可委托价格区间为 期货合约在T时段最后成交价 的5%</td>
					</tr>
					<tr>
						<td>小H股指数</td>
						<td>香港交易所</td>				
						<td>H股指数</td>
						<td>1个指数点（10港元）</td>
						<td>T+1时段的可委托价格区间为 期货合约在T时段最后成交价 的5%</td>
					</tr>
					<tr>
						<td>迷你德国DAX指数</td>
						<td>欧洲期货交易所</td>				
						<td>迷你德国指数</td>
						<td>1个指数点（5欧元元）</td>
						<td>无涨跌幅限制</td>
					</tr>
					<tr>
						<td>天然气</td>
						<td>纽约商业交易所</td>				
						<td>天然气</td>
						<td>0.001个指数点（10美元）</td>
						<td>当涨跌幅+-10%和15%时，分别熔断10分钟，之后无涨跌幅限制</td>
					</tr>
				</table>
			</div>	
		</div>
	</div>
</template>

<script>
	import topbar from '../../components/Topbar.vue'
	import back from '../../components/back.vue'
	import cs from '../../components/customerService.vue'
	export default{
		name:'tradersRules',
		components: {topbar, back, cs}
	}
</script>

<style scoped lang="less">
@import url("../../assets/css/base.less");
/*ip6p及以上*/
@media (min-width:411px) {
    #tradersRules{
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
    			font-size: @fs14;;
    			padding: 8px 15px;
    			border-bottom: 5px solid @black;
    		}
    		.list{
    			width: 398px;
    			overflow-x: scroll;
    			padding:  15px;
    			table{
    				width: 900px;
    				td{
    					text-align: center;
    					border: 1px solid @white;
    					font-size: @fs14;
    					color: @white;
    					&:nth-child(1){
    						width: 120px;
    					}
    					&:nth-child(2){
    						width: 120px;
    					}
    					&:nth-child(3){
    						width: 140px;
    					}
    					&:nth-child(4){
    						width: 190px;
    					}
    					&:nth-child(5){
    						width: 250px;
    					}
    				}
    			}
    		}
    	}
    }
}
/*ip6*/
@media (min-width:371px) and (max-width:410px) {
	#tradersRules{
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
    			padding:  15px*@ip6;
    			table{
    				width: 900px*@ip6;
    				td{
    					text-align: center;
    					border: 1px solid @white;
    					font-size: @fs14*@ip6;
    					color: @white;
    					&:nth-child(1){
    						width: 120px*@ip6;
    					}
    					&:nth-child(2){
    						width: 120px*@ip6;
    					}
    					&:nth-child(3){
    						width: 140px*@ip6;
    					}
    					&:nth-child(4){
    						width: 190px*@ip6;
    					}
    					&:nth-child(5){
    						width: 250px*@ip6;
    					}
    				}
    			}
    		}
    	}
    }
}
/*ip5*/
@media(max-width:370px) {
	#tradersRules{
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
    			padding:  15px*@ip5;
    			table{
    				width: 900px*@ip5;
    				td{
    					text-align: center;
    					border: 1px solid @white;
    					font-size: @fs14*@ip5;
    					color: @white;
    					&:nth-child(1){
    						width: 120px*@ip5;
    					}
    					&:nth-child(2){
    						width: 120px*@ip5;
    					}
    					&:nth-child(3){
    						width: 140px*@ip5;
    					}
    					&:nth-child(4){
    						width: 190px*@ip5;
    					}
    					&:nth-child(5){
    						width: 250px*@ip5;
    					}
    				}
    			}
    		}
    	}
    }
}
</style>