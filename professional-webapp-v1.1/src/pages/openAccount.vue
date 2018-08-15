<template>
	<div id="openAccount">
		<div class="openAccount_top">
			<img src="../assets/images/icon_openaccount_01.png" v-if="showpage == true"/>
			<img src="../assets/images/icon_openaccount_02.png" v-else="showpage == false"/>
		</div>
		<div class="openAccount_center" v-if="isshow_openAccount_1">
			<div class="title">
				<span>开户入金</span>
				<span>操盘保证金越多，可持仓手数越多</span>
				<span @click="toTradersRules">国际操盘规则</span>
			</div>
			<div class="openAccount_center_left">
				<ul>
					<li>
						<button v-for="(item,index) in item" class="btn btn2" :class="{btn1:current1 == index}" @click="chose">￥{{item.traderBond}}</button>
					</li>
					<li>
						<button class="btn yellow" v-on:click="to_openAccount_2">下一步</button>
					</li>
					<li>
						<!--<p>提交申请即代表你已阅读并同意<span @click="toAgreement">《国际期货综合操盘合作协议》</span></p>-->
					</li>
				</ul>
			</div>
			<div class="openAccount_center_right">
				<ul>
					<li>您的投资本金：<label>{{show_price}}元</label><i>(固定汇率{{rate}}，1美元={{rate}}元人民币)</i></li>
					<li>总操盘资金<i>（盈利全归你）</i></li>
					<li>{{traderTotal}}美元={{(show_price/rate).toFixed(0)}}美元<i>（本金）</i>+{{traderTotal-(show_price/rate).toFixed(0)}}美元<i>（获得资金）</i></li>
					<li>亏损平仓线：<i class="ifont" @mouseenter="showLossMark" @mouseleave="hideLossMark">&#xe66d;</i><span>{{lineLoss*rate}}元（{{lineLoss}}美元）</span><i>（平仓线=总操盘资金-风险保证金x0.6）</i></li>
					<li>管理费：<span>免费</span></li>
					<li>交易时间：<span>请参照交易规则</span></li>
				</ul>
			</div>
			<p class="loss_mark" v-show="lossMark">当账户总资产低于平仓线时，我们有权将您的持仓全部平仓，为避免强制平仓，请及时追加保证金。</p>
		</div>
		<div class="openAccount_center_step2" v-if="isshow_openAccount_2">
			<div class="title">
				<span>确认方案信息</span>
			</div>
			<div>
				<table>
					<thead>
						<tr>
							<td>总操盘资金</td>
							<td>亏损平仓线</td>
							<td>投资本金</td>
							<td>账户管理费</td>
							<td></td>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>{{traderTotal}}美元</td>
							<td>{{lineLoss}}美元</td>
							<td>{{show_price}}元</td>
							<td>免费</td>
							<td></td>
						</tr>
					</tbody>
				</table>
			</div>
			<div class="to_openAccount">
				<span>支付金额：<i>{{show_price}}</i>元</span>
				<button class="btn yellow" v-on:click="to_comfirmPayment">立即开户</button>
				<label v-on:click="back">返回修改</label>
			</div>
		</div>
		<div class="openAccount_btm">
			<div class="openAccount_btm_top">
				<span>交易规则</span>
				<span>（一个账号可同时交易多种期货产品）</span>
			</div>
			<div class="openAccount_btm_center">
				<div class="product_list">
					<p class="loss_mark loss_mark_left" v-show="procedures">买卖期货成交后按成交合约总价值的一定比例，支付的单边交易费用。</p>
					<p class="loss_mark loss_mark_right" v-show="proceduresR">买卖期货成交后按成交合约总价值的一定比例，支付的单边交易费用。</p>
					<table>
						<thead>
							<tr class="color_deepblue">
								<td>期货产品</td>
								<td>交易时间段</td>
								<td>初始持仓手数</td>
								<td>单边手续费<i class="ifont" @mouseenter="showProcedures" @mouseleave="hideProcedures">&#xe66d;</i></td>
							</tr>
							<tr class="color_deepblue1" >
								<td>期货产品</td>
								<td>交易时间段</td>
								<td>初始持仓手数</td>
								<td>单边手续费 <i class="ifont" @mouseenter="showProceduresR" @mouseleave="hideProceduresR">&#xe66d;</i></td>
							</tr>
						</thead>
						<tbody class="show_list">
							<tr v-for="k in temp.contractList" class="show_list_td">
								<td>{{k.tradeType | cnname}}</br><i> {{k.mainContract}}</i></td>
								<td>
									<p v-for="time in k.tradTime">{{time}}</p>
								</td>
								<td>{{k.shoushu | filtershoushu(chooseType)}}</td>
								<td>{{k.price}}元/手</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
			<div class="btm_btm">
				<span v-on:click="show_listAll">展开</span>
			</div>
		</div>
		<div class="openAccount_btm_btm">
			<span>投资有风险，入市需谨慎</span>
		</div>
		<!--操盘协议-->
		<div class="dialog_trade_rules" id="trade_rules" v-show="showTradeRules">
			<div class="trade_rules">
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
				<p>目前国际综合模拟操盘账号有限，请致电<a href="#">{{hotLine}}</a>申请。</p>
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
							<td>德国指数</td>
							<td>1个指数点（5欧元）</td>
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
	</div>
</template>
<script>
	import pro from "../assets/js/common.js"
	export default{
		name:'openAccount',
		data(){
			return {
				isshow_openAccount_1: true, 
				isshow_openAccount_2: false,
				show_price : 3000,
				item: '',
				lineLoss:'',
				listLeft : '',
				listRight :'',
				rate:0,
				traderTotal:'',
				temp:{},
				chooseType: 3000,
				show_list:true,
				showpage:true,
				current1:0,
				lossMark: false,
				procedures: false,
				proceduresR: false,
				showAgreement: false,
				showTradeRules: false,
				hotLine:''
			}
		},
		methods: {
			getHotLine: function(){   //获取客服热线
				pro.fetch('post', '/hotline', '', '').then(function(res){
					if(res.success == true && res.code == 1){
						this.hotLine = res.data.hotline;
					}
				}.bind(this)).catch(function(err){
					var data = err.data;
					layer.msg(data.message, {time: 1000});
				});
			},
			showLossMark: function(){
				this.lossMark = true;
			},
			hideLossMark: function(){
				this.lossMark = false;
			},
			showProcedures: function(){
				this.procedures = true;
			},
			hideProcedures: function(){
				this.procedures = false;
			},
			showProceduresR: function(){
				this.proceduresR = true;
			},
			hideProceduresR: function(){
				this.proceduresR = false;
			},
			//返回修改
			back:function(){
				this.isshow_openAccount_2 = false,
				this.isshow_openAccount_1 = true,
				this.showpage = true
			},
			//展开
			show_listAll:function(){
				if(this.show_list == true){
					$(".btm_btm>span").html("关闭");
					$(".product_list, .openAccount_btm_center").css('height', 'auto');
					this.show_list = false;
					var h = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;
					var _h = h - 90;
					var contH = $("#openAccount").height();
					if(contH > _h){
						$("#openAccount").height(_h);
					}
				}else if(this.show_list == false){
					$(".btm_btm>span").html("展开");
					this.show_list = true;
					$(".product_list, .openAccount_btm_center").css('height', '234px');
				}
			},
//			toAgreement: function(){
//				this.showAgreement = true;
//				layer.open({
//					type: 1,
//					title: '国际期货操盘委托协议',
//					area: ['1000px','600px'],
//					content: $("#agreement"),
//					cancel: function(){
//						this.showAgreement = false;
//					}.bind(this)
//				});
//			},
			toTradersRules: function(){
				this.showTradeRules = true;
				layer.open({
					type: 1,
					title: '国际期货操盘规则',
					area: ['1000px','600px'],
					content: $("#trade_rules"),
					cancel: function(){
						this.showTradeRules = false;
					}.bind(this)
				});
			},
			to_comfirmPayment : function(){
				this.payMoney = this.chooseType
				this.$router.push({path:"/confirmPayment",query:{"payMoney":this.payMoney}});
			},
			to_openAccount_2 :function(){
				this.isshow_openAccount_2 = true,
				this.isshow_openAccount_1 = false,
				this.showpage = false
			},
			//选择不同价格
			chose:function(e){
				var index = $(e.currentTarget).index();
				this.current1 = index;
				//截取价格并显示
				var show_price=$(e.currentTarget).html().substring(1);
				this.show_price = show_price;
				this.chooseType = parseInt(show_price);
				$(e.currentTarget).addClass("btn1").siblings().removeClass("btn1");
				switch (index){
					case 0:
						this.lineLoss = this.item[0].lineLoss;
						this.traderTotal = this.item[0].traderTotal;
						break;
					case 1:
						this.lineLoss = this.item[1].lineLoss;
						this.traderTotal = this.item[1].traderTotal;
						break;
					case 2:
						this.lineLoss = this.item[2].lineLoss;
						this.traderTotal = this.item[2].traderTotal;
						break;
					case 3:
						this.lineLoss = this.item[3].lineLoss;
						this.traderTotal = this.item[3].traderTotal;
						break;
					case 4:
						this.lineLoss = this.item[4].lineLoss;
						this.traderTotal = this.item[4].traderTotal;
						break;
					case 5:
						this.lineLoss = this.item[5].lineLoss;
						this.traderTotal = this.item[5].traderTotal;
						break;
					case 6:
						this.lineLoss = this.item[6].lineLoss;
						this.traderTotal = this.item[6].traderTotal;
						break;
					case 7:
						this.lineLoss = this.item[7].lineLoss;
						this.traderTotal = this.item[7].traderTotal;
						break;
				}
			},
			//获取button列表
			getBtnList:function(){
				pro.fetch("post",'/ftrade/params',{businessType:8},'').then((res)=>{
					var data = res.data;
					if(res.success == true){
						if(res.code == 1){
							this.traderTotal = data.paramList[0].traderTotal;
							this.lineLoss = data.paramList[0].lineLoss;
							this.temp = data;
							this.item = data.paramList;
							this.$store.state.tempTradeapply = this.temp;
							
							this.temp.contractList.forEach(function(o, i) {
								switch(o.tradeType) {
									case 0:   //return '富时A50'
										var arr = [];
										this.temp.paramList.forEach(function(a) {
											arr.push(a.tranLever);
											o.shoushu = arr;
										}.bind(this));
										break;
									case 6:   //return '国际原油'
										var arr = [];
										this.temp.paramList.forEach(function(a) {
											arr.push(a.crudeTranLever);
											o.shoushu = arr;
										}.bind(this));
										break;
									case 7:   //return '恒指期货'
										var arr = [];
										this.temp.paramList.forEach(function(a) {
											arr.push(a.hsiTranLever);
											o.shoushu = arr;
										}.bind(this));
										break;
									case 9:   //return '迷你道指'
										var arr = [];
										this.temp.paramList.forEach(function(a) {
											arr.push(a.mdtranLever);
											o.shoushu = arr;
										}.bind(this));
										break;
									case 10:   //return '迷你纳指'
										var arr = [];
										this.temp.paramList.forEach(function(a) {
											arr.push(a.mntranLever);
											o.shoushu = arr;
										}.bind(this));
										break;
									case 11:   //return '迷你标普'
										var arr = [];
										this.temp.paramList.forEach(function(a) {
											arr.push(a.mbtranLever);
											o.shoushu = arr;
										}.bind(this));
										break;
									case 12:   //return '德国DAX'
										var arr = [];
										this.temp.paramList.forEach(function(a) {
											arr.push(a.daxtranLever);
											o.shoushu = arr;
										}.bind(this));
										break;
									case 13:   //return '日经225'
										var arr = [];
										this.temp.paramList.forEach(function(a) {
											arr.push(a.nikkeiTranLever);
											o.shoushu = arr;
										}.bind(this));
										break;
									case 14:   //return '小恒指'
										var arr = [];
										this.temp.paramList.forEach(function(a) {
											arr.push(a.lhsiTranActualLever);
											o.shoushu = arr;
										}.bind(this));
										break;
									case 15:   //return '美黄金'
										var arr = [];
										this.temp.paramList.forEach(function(a) {
											arr.push(a.agTranActualLever);
											o.shoushu = arr;
										}.bind(this));
										break;
									case 16:   //return 'H股指数'
										var arr = [];
										this.temp.paramList.forEach(function(a) {
											arr.push(a.hIndexActualLever);
											o.shoushu = arr;
										}.bind(this));
										break;
									case 17:   //return '小H股指数'
										var arr = [];
										this.temp.paramList.forEach(function(a) {
											arr.push(a.xhIndexActualLever);
											o.shoushu = arr;
										}.bind(this));
										break;
									case 18:   //return '美铜'
										var arr = [];
										this.temp.paramList.forEach(function(a) {
											arr.push(a.aCopperActualLever);
											o.shoushu = arr;
										}.bind(this));
										break;
									case 19:   //return '美白银'
										var arr = [];
										this.temp.paramList.forEach(function(a) {
											arr.push(a.aSilverActualLever);
											o.shoushu = arr;
										}.bind(this));
										break;
									case 20:   //return '小原油'
										var arr = [];
										this.temp.paramList.forEach(function(a) {
											arr.push(a.smaActualLever);
											o.shoushu = arr;
										}.bind(this));
										break;
									case 21:   //迷你德国DAX指数
										var arr = [];
										this.temp.paramList.forEach(function(a) {
											arr.push(a.daxtranMinActualLever);
											o.shoushu = arr;
										}.bind(this));
										break;
									case 22:   //return '天然气'
										var arr = [];
										this.temp.paramList.forEach(function(a) {
											if(a.naturalGasActualLever==null){
												arr.push(0);
											}else{
												arr.push(a.naturalGasActualLever);
											}
											o.shoushu = arr;
										}.bind(this));
										break;
								}
								o.tradTime = o.tradTime.split("，");
							}.bind(this));
						}
					}
				}).catch((err)=>{
					if(err.success == false){
						layer.msg("网络不给力，请稍后再试",{time:2000});
					}else{
						layer.msg(err.data.message,{time:2000});
					}
				})
			}
		},
		beforeCreate:function(){
			//获取汇率
			var headers ={
				token:JSON.parse(localStorage.user).token,
				secret:JSON.parse(localStorage.user).secret
			}
			pro.fetch("post","/user/getbalancerate",{businessType:1},headers).then((res)=>{
				if(res.success == true){
					if(res.code == 1){
						this.rate = res.data.rate;
					}
				}
			}).catch((err)=>{
				var data = err.data;
					if(data == undefined || data == "" || data == null){
						layer.msg('网络不给力，请稍后再试', {time: 1000});
					}else if(data.code == "-1"){
						layer.msg('您的用户名或密码错误，请联系客服', {time: 1000});
					}else if(data.code == "-3"){
						layer.msg('用户信息不存在，请重新登录', {time: 1000});
					}
			})
			//获取数据列表
			
		},
		filters:{
			change_time:function(e){
				var a = e.split("，")
				if(a.length == 1){
					var b= a[0];
//					console.log(b);
					return b
				}else if(a.length == 2){
					var b = a[0]+a[1]
//					console.log(b);
					return b;
				}else {
					var b=a[0]+a[1]+a[2]
//					console.log(b);
					return b;
				}
			},
			filtershoushu: function(arr,chooseType){
				switch(chooseType){
					case 3000:
						return arr[0];
						break;
					case 6000:
						return arr[1];
						break;
					case 10000:
						return arr[2];
						break;
					case 12000:
						return arr[3];
						break;
					case 15000:
						return arr[4];
						break;
					case 50000:
						return arr[5];
						break;
					case 100000:
						return arr[6];
						break;
					case 200000:
						return arr[7];
						break;
				}
			},
			moneytype: function(num) {
				if(num) return num.toLocaleString();
			},
			cnname: function(a) {
				switch(a) {
					case 0:
						return '富时A50'
						break;
					case 6:
						return '国际原油'
						break;
					case 7:
						return '恒指期货'
						break;
					case 9:
						return '迷你道指'
						break;
					case 10:
						return '迷你纳指'
						break;
					case 11:
						return '迷你标普'
						break;
					case 12:
						return '德国DAX'
						break;
					case 13:
						return '日经225'
						break;
					case 14:
						return '小恒指'
						break;
					case 15:
						return '美黄金'
						break;
					case 16:
						return 'H股指数'
						break;
					case 17:
						return '小H股指数'
						break;
					case 18:
						return '美铜'
						break;
					case 19:
						return '美白银'
						break;
					case 20:
						return '小原油'
						break;
					case 21:
						return '迷你德国DAX指数'  //迷你德国DAX指数
						break;
					case 22:
						return '天然气'
						break;
				}
			},
			varieties: function(e){    //交易品种
				switch(e) {
					case 8:
						return "国际综合";
						break;
					case 7:
						return "恒指期货";
						break;
					case 6:
						return "国际原油";
						break;
					case 0:
						return "富时A50";
						break;
				}
			}
		},
		activated: function(){
			this.isshow_openAccount_2 = false;
			this.isshow_openAccount_1 = true;
			this.showpage = true;
		},
		mounted:function(){
			this.getHotLine();
			this.getBtnList();
			//初始化高度
			var h = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;
			var _h = h - 90;
			var contH = $("#openAccount").height();
			if(contH > _h){
				$("#openAccount").height(_h);
			}
			$(window).resize(function(){
				var h = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;
				var _h = h - 80 - 47;
				if(contH > _h){
					$("#openAccount").height(_h);
				}
			});
		}
	}
</script>

<style scoped lang="scss">
@import "../assets/css/common.scss";
	#openAccount {
		width: 80%;
		margin: auto;
		overflow-y: auto;
	}
	.openAccount_top {
		text-align: center;
		height: 140px;
		background-color: $blue;
		img {
			width: 723px;
			height: 63px;
			margin-top: 40px;
		}
	}
	.openAccount_center {
		width: 100%;
		height: 280px;
		margin-top: 5px;
		position: relative;
	}
	.loss_mark{
		position: absolute;
		top: 200px;
		right: 400px;
		width: 300px;
		height: 60px;
		line-height: 20px;
		overflow: hidden;
		background: #596080;
		border-radius: 5px;
		color: $white;
		font-size: $fs12;
		padding: 10px;
	}
	.openAccount_center_left {
		width: 50%;
		background-color: $blue;
		height: 240px;
		float: left;
		text-align: center;
		span {
			&:hover{
				color: $yellow;
			}
			color: $white;
		}
		.btn.yellow{
			color: $black;
		}
		li {
			&:nth-child(1){
				padding-top: 15px;
				width: 50%;
				margin: auto;
			}
			&:nth-child(2){
				margin-top: 20px;
			}
			&:nth-child(3){
				margin-top: 25px;
			}
			&:nth-child(4){
				margin-top: 20px;
			}
			span{
				cursor: pointer;
			}
		}
	}
	.title {
		height: 40px;
		width: 100%;
		line-height: 40px;
		background-color: $bottom_color;
		padding: 0 10px;
		span {
			&:nth-child(1){
				color : $white;
			}
			&:nth-child(2){
				font-size: $fs12;
			}
			&:nth-child(3){
				font-size: $fs14;
				float: right;
				cursor: pointer;
				color: $white;
				&:hover{
					color: $yellow;
				}
			}
		}
	}
	.btn{
		margin:0px 5px;
		margin-top: 15px;
		width: 80px;
		height: 40px;
		color: $white;
		border: 5px;
		background-image: url(../assets/images/icon_choseMoneyNo.png);
	}
	.btn2{
		&:hover{
			background-image: url(../assets/images/icon_choseMoney.png);
		}
	}
	.btn1 {
		width: 80px;
		height: 40px;
		color: $white;	
		border-radius: 5px;
		background-image: url(../assets/images/icon_choseMoney.png);
	}
	.yellow {
		width: 120px;
		height: 30px;
	}
	.openAccount_center_right {
		border-left: 1px solid $bottom_color;
		width: 50%;
		background-color: $blue;
		height: 240px;
		float: left;
		li {
			height:40px; 
			line-height: 40px;
			padding-left: 10px;
			&:nth-child(4){
				border-bottom: 1px solid $bottom_color;
				border-top: 1px solid $bottom_color;
			}
			&:nth-child(6){
				border-bottom: 1px solid $bottom_color;
				border-top: 1px solid $bottom_color;
			}
			span {
				color : $white;
			}
			i {
				font-size: $fs12;
			}
			.ifont{
				font-size: $fs16;
				color: $yellow;
				margin: 0 5px 0 0;
				cursor: pointer;
			}
			label {
				color: $yellow;
			}
		}
	}
	.openAccount_btm {
		width: 100%;
		margin-top: 5px;
		background-color: $bottom_color;
		/*overflow: scroll;*/
	}
	.openAccount_btm_top {
		height: 40px;
		width: 100%;
		line-height: 40px;
		background-color: $bottom_color;
		padding-left: 10px;
		span {
			&:first-child {
				font-size: $fs16;
				color: $white;
			}
			&:last-child {
				font-size: $fs12;
			}
		}
	}
	.openAccount_btm_center {
		width: 100%;
		height: 234px;
		overflow: hidden;
		background-color: $bottom_color;
	}
	.btm_btm{
		width: 100%;
		height: 60px;
		line-height: 60px;
		text-align: center;
		overflow: hidden;
		background-color: $blue;
		border-top: 1px solid $bottom_color;
		span{
			&:hover{
				color: $yellow;
				cursor:pointer;
			}
		}
	}
	.btm_btm span{
		cursor: pointer;
	}
	.product_list {
		width: 100%;
		height: 234px;
		position: relative;
		thead tr .ifont{
			color: $yellow;
			font-size: $fs16;
			margin-left: 5px;
			cursor: pointer;
		}
		tr{
			cursor: default;
			text-indent: 10px;
		}
		.loss_mark{
			position: absolute;
			top: 30px;
		}
		.loss_mark_left{
			left: 380px;
		}
		.loss_mark_right{
			right: 40px;
		}
	}
	.show_list{
		display: block;
		width: 100%;
		overflow: hidden;
		td{
			width: 25%;
			float: left;
			p{
				line-height: 12px;
				height: 33%;
			}
		}
	}
	.show_list_td{
		width:49.5%; 
		td{
			padding-top: 10px;
		}
		&:nth-child(odd){
			float: left;
			background-color: $blue;
		}
		&:nth-child(even){
			float: right;
			background-color: $blue;
			&:hover{
				background-color:$blue;
			}
		}
	}
	table{
	 	tr{
	 		/*background-color: $blue;*/
	 		height: 50px;
	 		border-bottom: 1px solid $bottom_color;
	 		i{
	 			margin-left: 10px;
	 		}
	 	}
	 }
	 .openAccount_btm_btm{
	 	width: 100%;
	 	height: 40px;
	 	line-height: 40px;
	 	overflow: hidden;
	 	text-align: center;
	 	margin-top: 5px;
	 	background-color: $bottom_color;
	 }
	 .openAccount_btm_btm span{
	 	color: #7a7f99;
	 	font-size: $fs12;
	 }
	 .color_deepblue {
	 	&:hover{
	 		background-color: $deepblue;
	 	}
	 	float: left;
	 	background-color: $deepblue;
	 	height: 30px;
	 	width: 49.5%;
	 	display: block;
	 	line-height: 30px;
	 	td{
	 		width: 25%;
	 		float: left;
	 	}
	 }
	  .color_deepblue1 {
	  	&:hover{
	 		background-color: $deepblue;
	 	}
	  	float: right;
	 	background-color: $deepblue;
	 	height: 30px;
	 	width: 49.5%;
	 	display: block;
	 	line-height: 30px;
	 	td{
	 		width: 25%;
	 		float: left;
	 	}
	 }
	 .openAccount_center_step2 {
	 	margin-top: 10px;
	 	width: 100%;
	 	height: 210px;
	 	background-color: $blue;
	 	tr{
	 		text-indent: 10px;
	 	}
	 }
	 .to_openAccount {
	 	height: 90px;
	 	line-height: 90px;
	 	text-align: center;
	 	span{
	 			color: $white;
	 			font-size: $fs14;
	 	}
	 	label{
	 		font-size: $fs12;
	 		&:hover{
	 			color: $yellow;
	 			cursor: pointer;
	 		}
	 	}
	 	.btn {
	 		width: 120px;
	 		height: 30px;
	 	}
	 	i{
	 		color: $yellow;
	 		font-size: $fs16;
	 		font-weight: 500;
	 	}
	 	.yellow{
	 		margin-bottom: 20px;
	 		color: #242633;
	 	}
	 }
	 .agreement{
		background: $blue;
		margin: 0 auto;
		padding: 20px 10px 0 10px;
		h3{
			height: 42px;
			line-height: 42px;
			text-align: center;
			border-bottom: 1px solid $black;
			padding: 0 15px;
			color: $yellow;
			font-size: $fs16;
		}
		p{
			line-height: 26px;
			color: $lightblue;
			font-size: $fs14;;
			padding: 8px 15px;
		}
	}
	.trade_rules{
    	background: $blue;
    	margin: 0 auto;
    	padding: 10px 10px 0 10px;
		h3{
			height: 42px;
			line-height: 42px;
			border-bottom: 1px solid $blue;
			padding: 0 15px;
			color: $yellow;
			font-size: $fs16;
		}
		p{
			line-height: 26px;
			color: $lightblue;
			font-size: $fs14;;
			padding: 8px 15px;
			border-bottom: 5px solid $blue;
			a{
				color: $yellow;
			}
		}
		.list{
			width: 100%;
			padding: 15px;
			table{
				width: 900px;
				td{
					text-align: center;
					border: 1px solid $white;
					font-size: $fs14;
					color: $white;
					padding: 10px;
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
	@media only screen and (min-width: 1280px) and (max-width: 1366px) {
	 	.openAccount_center_left li:nth-child(1){
	 		width: 75%;
	 	}
	}
</style>