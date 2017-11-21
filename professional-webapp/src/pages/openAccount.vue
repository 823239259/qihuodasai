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
						<button v-for="(item,index) in item" class="btn" :class="{btn1:current1 == index}" @click="chose">￥{{item.traderBond}}</button>
					</li>
					<li>
						<button class="btn yellow" v-on:click="to_openAccount_2">下一步</button>
					</li>
					<li>
						<p>提交申请即代表你已阅读并同意<span @click="toAgreement">《国际期货综合操盘合作协议》</span></p>
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
								<td>{{k.tradeType | cnname}}</br><i>{{k.mainContract}}</i></td>
								<td>{{k.tradTime}}</td>
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
		<div class="dialog_agreement" id="agreement" v-show="showAgreement">
			<div class="agreement">
				<!--<h3>国际期货操盘委托协议</h3>-->
				<p>本合作操盘协议（下称“本协议”）由以下甲乙丙三方于 ____年__月__日签署：</p>
				<p>甲方：INVTOOL TECHNOLOGY LIMITED（以下或称“受托人”）</p>
				<p>乙方：</p>
				<p>丙方：成都维胜智慧科技有限公司 </p>
				<p>住所地：成都市金牛区西北桥东街6号1幢1单元2楼附1-9号</p>
				<p>鉴于</p>
				<p>1、甲方接受直达期货股份有限公司（以下简称“委托人”）委托，担任投资顾问，受托管理该资产。为更好管理资产，维护委托人权益，经委托人同意，甲方有意委托若干自然人提供国际期货投资策略服务；</p>
				<p>2、乙方有意接受甲方前述委托，申请提供该服务；</p>
				<p>3、丙方系本APP（下称“丙方应用”或“维胜应用”）的运营商，丙方具有应用运营及技术服务优势，可使乙方通过丙方应用申请提供前述服务。</p>
				<p>各方根据《期货交易管理条例》、《中华人民共和国合同法》等法律法规，经友好协商，订立本合作协议（下称“本协议”），供各方遵照执行。</p>
				<p>第一条 本协议成立及生效</p>
				<p>1、乙方可以通过丙方应用相应页面申请向甲方提供国际期货投资策略服务（下称“服务”），本协议各方确认，一旦乙方在丙方应用相关页面点击确认申请，则本协议在协议各方之间成立，且本协议于乙方按照本协议规定完成相应操盘保证金支付时生效。本协议各方应本着诚实信用原则按照本协议约定全面履行。</p>
				<p>2、根据本协议，在甲方与乙方之间形成甲方委托乙方提供国际期货投资策略服务法律关系，乙方申请行为并不在乙方与甲方、丙方或任何其他第三方之间形成借贷关系。</p>
				<p>第二条 合作方式</p>
				<p>1、 乙方以保证金方式取得甲方期货交易账户的操作权限，且乙方独立承担操盘的全部风险。甲方授权给乙方操盘的期货交易账户可交易金额为乙方保证金的一定比例。具体约定如下：</p>
				<p>乙方保证金 ：</p>
				<p>甲方提供(期货账户) 初始金额：</p>
				<p>2、  乙方可以使用上述期货交易账户内资金总额购买国际期货市场主流期货品种，共计17种，具体如下：美原油（CME CL）、富时A50（SGX CN）、恒生指数期货（HKE HSI）、日经指数225（SGX NK）、迷你道琼（CME-CBOT YM）、迷你纳指（CME NQ）、迷你标普（CME ES）、德国DAX指数（EUREX FDAX）、小恒指、美黄金、H股指数、小H股指数、美铜、美白银、小原油、迷你德国DAX指数（FDXM）、天然气。盈利归乙方所有，亏损由乙方承担，即乙方对上述账户进行期货买卖等操作产生的一切风险和后果由乙方承担，与甲方无关。</p>
				<p>第三条 操盘规则</p>
				<p>1、各方确认，国际综合操盘规则（以下简称“规则”）由丙方制定并于丙方应用相应页面展示且丙方有权单方变更规则内容。规则包括但不限于操盘保证金规则、申请规则、平仓规则、交易费用规则、结算规则及其他丙方认为必要的规则。</p>
				<p>2、乙方应在提交申请前仔细阅读规则全文，清楚理解并同意接受规则内容后方可提交申请。乙方一旦提交申请，即表示乙方完全认可规则内容并同意按照规则内容执行。乙方确认修订后的规则对乙方具备约束力，如乙方对修订后的规则有异议，则乙方应立即通过丙方应用相关页面申请终止提供服务。</p>		
				<p>3、乙方必须年满18周岁并具备完全民事行为能力，且乙方应确保其申请及提供服务行为并不违反法律法规等规定。</p>
				<p>4、乙方必须事先注册成为维胜网注册用户，并同意遵守维胜网服务协议及所有规则。</p>
				<p>5、乙方必须具备证券、期货丰富的操作经验并具备一定的风险承受能力。</p>
				<p>6、内幕信息知情人员（包括但不限于上市公司董事、高级管理人员、监事、会计、审计、律师等知情人员）不得利用内幕信息进行操作。</p>
				<p>7、申请及服务提供规则：</p>
				<p>1)交易品种：美原油（CME CL）、富时A50（SGX CN）、恒生指数期货（HKE HSI）、日经指数225（SGX NK）、迷你道琼（CME-CBOT YM）、迷你纳指（CME NQ）、迷你标普（CME ES）、德国DAX指数（EUREX FDAX）、小恒指、美黄金、H股指数、小H股指数、美铜、美白银、小原油、迷你德国DAX指数（FDXM）为当月主力合约，以平台实际公示可交易品种为准。</p>
				<p>2) 交易时段内申请并成功支付操盘保证金的，该时段内可提供商品综合投资策略服务；非交易时段内申请并成功支付操盘保证金的，在下一交易时段，可提供商品综合投资交易策略服务。</p>
				<p>3)如交易时间内乙方需对账户进行结算的，须实盘账户已清仓，才可申请结算。</p>
				<p>4)交易时长：恒生指数期货、H股指数合约时间为：09:20-11:55，13:05-16:25，17:20-23:40；富时A50合约时间为：9:05-16:30,17:05-02:00；日经指数225合约时间为：09:05-14:25，15:20-23:55；迷你道琼、迷你纳指、迷你标普合约时间为：09:05-23:55合约时间为09:05-02:00；美原油;德国DAX指数合约时间为：13:55-23:55；
					小H股指数合约时间为：09:20-16:25,17:20-23:40；美铜、美白银、小原油合约时间为：06:05-04:55；迷你德国DAX指数合约时间为：13:55-23:55；
					申请人在交易日该时间段届满前应对账户中所有交易进行平仓，未能按时平仓的所有仓单系统将强制平仓。交易实行T+1日结算制度。
					根据风控需求，在各品种停止交易的5分钟内，所有品种只能平仓，不能开仓，即11:55-12:00,14:25-14:30,16:25-16:30,16:30-16:35,11:40-11:45,11:55-12:00六个时段，所有品种只能平仓，不能开仓，</p>
				<p>申请人在交易日该时间段届满前应对账户中所有交易进行平仓，未能按时平仓的所有仓单系统将强制平仓。</p>
				<p>根据风控需求，在各品种停止交易的5分钟内，所有品种只能平仓，不能开仓，具体时间段以平台公示为主。</p>
				<p>5)当乙方操盘账户内的总操盘金少于或等于亏损平仓线时，系统将触发强制平仓指令，自动对账户内所有合约按照市价平仓，强平单的最终成交价以实际成交价为准，强平完成后，账户将被限制进行新的开仓交易。</p>
				<p>6)乙方应遵循下列交易限制：</p>
				<p>a）不得参与集合竞价买入和卖出；</p>
				<p>b）可持仓最大手数由申请并成功支付的操盘保证金决定；一天之内可多次开仓，次数不限。</p>
				<p>c）乙方自行设定的与交易有关的各项规则如与丙方在系统中设置的规则冲突时，乙方设置内容无效。</p>
				<p>7)结算规则：操盘盈亏=账户所有持仓平仓结算后账户余额-操盘初始资金；如操盘盈利，则盈利作为报酬归乙方所有，如操盘亏损且亏损额度小于或等于操盘保证金金额的，则亏损由乙方承担，直接从操盘保证金中扣除；超出操盘保证金部分丙方保留追讨权利；乙方同意结算金额以交易系统后台清算数据为准。</p>
				<p>第四条 丙方的服务</p>
				<p>1、丙方在丙方应用相应页面（具体位置由丙方决定），推出“国际综合” 操盘项目（以下简称“项目”），相应具有国际期货操作经验的乙方注册成为丙方应用注册用户后可通过该页面申请提供国际期货投资策略服务。</p>
				<p>2、项目名称由丙方单方决定且丙方独立享有该名称商标申请权。</p>
				<p>3、丙方负责应用相应页面的开发及维护。</p>
				<p>第五条 费用</p>
				<p>1.丙方提供本协议下的服务，按照本条约定收取技术服务费，收费标准以页面展示为准。</p>
				<p>2.期货合约交易时，交易系统自动扣除每手技术服务费，其中含代缴期货公司手续费。</p>
				<p>3. 丙方有权单方调整技术服务费，对此乙方表示理解与同意。</p>
				<p>第六条 期货账户</p>
				<p>1、乙方的操盘账户按照操盘规则进行分配，乙方应妥善保管账户名及密码，通过该账户名及密码产生的责任由乙方自行承担。</p>
				<p>2、账户使用原则上无期限限制。乙方申请停止向甲方提供服务后，其操盘账户将被收回。</p>
				<p>第七条 操盘保证金</p>
				<p>1、乙方在支付操盘保证金后可向甲方提供相应的国际期货投资策略服务。</p>
				<p>2、乙方按照约定支付操盘保证金，金额以丙方应用展示为准。</p>
				<p>3、交易资金按美元计价，人民币兑换美元，汇率按照中国银行当天第一开市时间来算，出入金按照现钞卖出价。</p>
				<p>4、乙方同意如交易发生亏损，则亏损从操盘保证金中扣除，如有剩余保证金可退还乙方。</p>
				<p>第八条 争议解决</p>
				<p>与本协议有关的争议，各方友好协商解决。协商不成，任何一方应向丙方实际经营地人民法院提起诉讼。</p>
				<p>第九条 本协议终止</p>
				<p>本协议出现下列情形时终止：</p>
				<p>1、乙方违反规则的，丙方有权单方解除本协议，通知到达甲方、乙方或通知发出之日起5个自然日届满时，本协议解除。</p>
				<p>2、无论出于任何原因，乙方多日未使用期货账户进行交易，甲方和丙方都有权单方面终止本协议。</p>
				<p>3、各方协商一致终止本协议。</p>
				<p>4、其他法定情形。</p>
				<p>第十条 其他约定</p>
				<p>1、如乙方盈利，盈利归属于乙方。丙方按照规则配合与乙方进行结算，且丙方据此受让乙方该笔盈利的债权，丙方有权要求资产管理产品的受托人支付该笔盈利。</p>
				<p>2、乙方同意接受包括但不限于以下所列风险并同意自行承担因此产生的损害：</p>
				<p>1）不可抗力风险：例如地震、火灾、水灾、战争等不可抗力因素可能导致的系统瘫痪，证券营业部、服 务器托管机房和网络运营商等无法预测及控制的系统故障、设备故障、通讯故障、电力故障等导致提供服务。</p>
				<p>2）技术风险：网络及软件等技术存在被黑客或计算机病毒攻击等可能造成的损害；以及应用故障、系统维护可能造成的损害。</p>
				<p>3）政策风险：证券及期货市场的法律、法规及相关政策发生变化以及政府行为，可能引起的证券市场价格波动而产生的亏损可能。</p>
				<p>4）宏观经济风险：国家宏观经济形势的变化以及周边国家、地区经济环境和周边证券市场的变化，可能引起证券市场的波动而产生的亏损可能。</p>
				<p>3、经各方确认以下为其准确、合法通讯方式：</p>
				<p>甲方通讯地址：ROOM 603E, 6/F, HANG PONT COMMERCIAL BUILDING 31 TOKIN STREET, CHEUNG SHA WAN KOWLOON, HONGKONG  <p>
				<p>甲方邮箱：cs@invtool.com<p>
				<p>乙方通讯地址：</p>
				<p>乙方邮箱：<p>
				<p>丙方通讯地址：成都市金牛区西北桥东街6号1幢1单元2楼附1-9号</p>
				<p>丙方邮箱：contract@vs.com</p>
				<p>任何乙方改变上述地址的，需提前三个工作日以书面（包括但不限于电子邮件等）形式向其余相对方告知，未履行告知义务的，任何相对方向上述地址寄送文书材料的，视为送达。</p>
			</div>
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
							<td>7%、13%、20%(仅跌停)三级熔</td>
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
							<td>香港交易所</td>				
							<td>H股指数</td>
							<td>1个指数点（10港元）</td>
							<td>T+1时段的可委托价格区间为 期货合约在T时段最后成交价 的5%</td>
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
			}
		},
		methods: {
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
					$(".product_list").css("overflow-y","scroll");
					$(".btm_btm>span").html("关闭")
					return this.show_list = false
				}else if(this.show_list == false){
					$(".product_list").css("overflow-y","visible");
					$(".btm_btm>span").html("展开");
					return this.show_list = true;
				}
			},
			toAgreement: function(){
				this.showAgreement = true;
				layer.open({
					type: 1,
					title: '国际期货操盘委托协议',
					area: ['1000px','680px'],
					content: $("#agreement"),
					cancel: function(){
						this.showAgreement = false;
					}.bind(this)
				});
			},
			toTradersRules: function(){
				this.showTradeRules = true;
				layer.open({
					type: 1,
					title: '国际期货操盘规则',
					area: ['1000px','680px'],
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
				if(err.success ==false ){
					layer.msg(err.data.message,{time:2000});
				}else{
					layer.msg("网络不给力，请稍后再试",{time:2000});
				}
			})
			//获取数据列表
			
		},
		filters:{
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
			this.getBtnList();
		}
	}
</script>

<style scoped lang="scss">
@import "../assets/css/common.scss";
	#openAccount {
		width: 80%;
		margin: auto;
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
		td{
			width: 25%;
			float: left;
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
	 		width: 70%;
	 	}
	}
</style>