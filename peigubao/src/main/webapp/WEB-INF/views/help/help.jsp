<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>
<%@include file="../common/import-artDialog-js.jspf"%>
<%
String tab=(String)request.getParameter("tab");
tab=tab==null?"":tab;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta content="投资达人提供股票操盘、P2P贷款、投资理财、股指期货等交易服务，更高的收益，更加安全可靠；投资达人致力于打造中国领先的互联网金融交易平台." name="description">
<title>新手指引,投资达人,中国领先的互联网金融交易平台 </title>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/public.css">
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/help.css?20150806">
<script type="text/javascript" src="${ctx}/static/script/common/swfobject.js"></script>

<script type="text/javascript" src="${ctx}/static/script/help/help.js"></script>
<script type="text/javascript">
var tab="<%=tab%>";

</script>



</head>
<body>
<!--顶部 -->
<%@ include file="../common/personheader.jsp"%>
<div class="help">
    <div class="hp_nav">
        <ul>
            <li><a href="javascript:void(0);" class="helpTitle" data-type="newbie">新手指南</a></li>
            <li><a href="javascript:void(0);" class=" helpTitle" data-type="configuration">股票操盘</a></li>
            <!-- <li><a href="javascript:void(0);" class="helpTitle" data-type="heart">实盘申请</a></li> -->
            <!-- <li><a href="javascript:void(0);" class="helpTitle" data-type="demo">操盘演示</a></li> -->
            <li><a href="javascript:void(0);" class="helpTitle" data-type="rule">期货操盘</a></li>
            <li><a href="javascript:void(0);" class="helpTitle" data-type="software">交易软件下载</a></li>
            <li><a href="javascript:void(0);" class="helpTitle" data-type="safety">安全保障</a></li>
        </ul>
    </div>
    <!--股票操盘-->
    <div class="hp_ctn configuration hp_content">
        <div class="hp_siderbar">
            <a href="javascript:void(0);"  data="1">什么是股票操盘</a>
            <a href="javascript:void(0);" data="2">随心操盘</a>
            <a href="javascript:void(0);" data="3">免息操盘</a>
            <a href="javascript:void(0);" data="4">操盘规则</a>
            <a href="javascript:void(0);" data="5">超额亏损处理</a>
            <a href="javascript:void(0);" data="6">停牌股处理</a>
            <a href="javascript:void(0);" data="7">操盘限制</a>
            <a href="javascript:void(0);" data="8">收费介绍</a>
            <a href="javascript:void(0);" data="9">常见问题</a>
            <!-- <a href="javascript:void(0);" data="10">抵扣券</a> -->
        </div>
        <div class="hp_mainbar  hp_mbox1">
            <h1>什么是股票操盘</h1>
            <p>用最少的资金获得最大收益！</p>
            <p>随心操盘是一种创新的资金放大工具，通过少量的自有资金当本金，向平台借入本金一定倍数的资金。从而让投资者可以更好的抓住盈利机会，更高效的利用自有资金，进而实现更快捷、更丰富的投资收益。</p>
            <p>简单来说假如您有1万本金，到投资达人放大至5万，共6万资金供您操盘，放大后一个涨停板就可以赚50%，收益全归您，投资达人只收取超低利息和管理费。</p>
        	<p style="color:#f00;">投资达人遵守证监会新规，现暂停股票操盘业务，有疑问请联系客服：400-633-9257</p>        	
        </div>
        <div class="hp_mainbar hp_mbox2" style="display:none;">
            <h1>什么是“随心操盘”</h1>
            <p>传统的股票操盘是指在客户原有资金的基础上按照一定比例给额外的资金供客户进行股票交易，而投资达人网推出的“随心操盘”是在此基础上极大优化保证金、倍数和天数这“三要素”自由度的新型股票操盘业务。</p>
            <p><b>保证金：</b>随心操盘的保证金200元起配，也就是您最低只需要花费200元左右（含利息和管理费）就可以完成一次股票的操盘。</p>
            <p><b>操盘倍数：</b>随心操盘的倍数为1-8倍，杠杆的数额和保证金的数额成反比，也就是杠杆倍数会随着保证金的增加而减少，当保证金到达上限600万时，杠杆变成1倍。在5倍及5倍以下时，您需要缴纳的相关费用（管理费、利息）会相对较少，性价比较高。</p>
            <p><b>操盘天数：</b>随心操盘的操盘天数区间为2-180天。</p>
        </div>
        <div class="hp_mainbar hp_mbox3" style="display:none;">
            <h1>免息操盘</h1>
            <h2>投资达人网推出的“免息配”活动有以下四种方案：</h2>
            <style type="text/css">
            	.hp_chooselist { width:800px; margin-bottom:20px;}
            	.hp_chooselist li { display:block; width:100%; height:40px; line-height:40px;}
            	.hp_chooselist li label { display:inline-block; width:100px; height:40px; line-height:40px; text-align:right; font-size:14px; color:#333; vertical-align: middle;}
            	.hp_chooselist li span { display:inline-block; width:250px; height:40px; line-height:40px; text-align:left; font-size:14px; color:#333; vertical-align: middle; }
           		.hp_chooselist ol { display:block; float:left; width:350px;}
            </style>
            <ul class="hp_chooselist">	
            	<ol>            		
	            	<li><label>方案一：</label><span>0倍免息操盘</span></li>
	            	<li><label></label><span>保证金为50万—600万</span></li>
	            	<li><label></label><span>操盘时长30天</span></li>
	            	<li><label></label><span>最短操盘时长为10个交易日</span></li>
	            	<li><label></label><span>管理费和利息均为0</span></li> 
            	</ol>        
            	<ol>
            		<li><label>方案二：</label><span>1倍免息操盘</span></li>
            		<li><label></label><span>保证金为15万—600万</span></li>
            		<li><label></label><span>操盘时长30天</span></li>
            		<li><label></label><span>最短操盘时长为10个交易日</span></li>
            		<li><label></label><span>管理费7折利息为0</span></li>     	
            	</ol>   	
            	<ol>	
            		<li><label>方案三：</label><span>2倍免息操盘</span></li>
            		<li><label></label><span>保证金为8万—300万</span></li>
            		<li><label></label><span>操盘时长30天</span></li>
            		<li><label></label><span>最短操盘时长为10个交易日</span></li>
            		<li><label></label><span>管理费7折利息为0</span></li>        	
            	</ol>
            	<ol>
            		<li><label>方案四：</label><span>3倍免息操盘</span></li>
            		<li><label></label><span>保证金为5万—200万</span></li>
            		<li><label></label><span>操盘时长30天</span></li>
            		<li><label></label><span>最短保留时长为10个交易日</span></li>
            		<li><label></label><span>管理费7折利息为0</span></li>  
            	</ol>
            </ul>
            <p>活动期间，每位投资者均可使用投资达人网的定制化“免息操盘”产品。需要注意的是，免息操盘不能进行方案延期，操盘时间结束后，需要终止方案。</p>
        </div>
        <div class="hp_mainbar hp_mbox4" style="display:none;">
            <h1>操盘规则</h1>
            <p>操盘方案在交易日的14点45分之前生成可以当日交易，之后生成只能是次日交易，当日无法登陆且不会扣除当日的相关费用。</p>
            <p>在工作时间内提交的操盘申请，通常会在半个小时内完成审核，审核成功后会有短信通知客户，可以登录达人网-我的账户-操盘账户-操盘详细-股票交易账户页面查询到账号和密码。</p>
            <p>股票交易使用T+1的规则，当日购买的股票只能在次日卖出。可以购买沪深A股及创业板且无仓位限制，盈利100%归您所有，为了保证您的权益，部分限制股无法购买。</p>
            <p>当账户净资产小于补仓线，系统会限制客户买入；当客户的账户净资产触及到平仓线，系统会进行股票平仓。（当日购买的股票或者持仓股票跌停可能无法平仓，只能次日平仓）。</p>
            <p>当您的方案到期，系统会对您的方案自动延期，最长延期时间可以在达人网的操盘详细里查询。方案还未到期就终止操盘，已缴纳的利息不退，管理费从终止操盘后的次日不再扣除。</p>
            <p>当您的净资产触及到补仓线或者平仓线，需要通过达人网追加保证金到交易账户，追加的金额必须要大于总操盘资金的百分之一。如果您的方案到期时间大于或者等于七个交易日，那么可以通过追加方案的形式扩大总操盘资金。</p>
            
        </div>        
        <div class="hp_mainbar hp_mbox5" style="display:none;">
            <h1>超额亏损处理</h1>
            <p>如果您买的股票出现快速下跌，您来不及或忘了卖出股票，我们也来不及平仓处理（我们有权平仓，但不保证平仓价格），出现的超额亏损由操盘人承担超额亏损金，所以请养成及时关注股票情况，及时追加操盘保证金的良好投资习惯。</p>
        </div>      
        <div class="hp_mainbar hp_mbox6" style="display:none;">
            <h1>停牌股处理</h1>
            <p>如果您买的股票出现快速下跌，您来不及或忘了卖出股票，我们也来不及平仓处理（我们有权平仓，但不保证平仓价格），出现的超额亏损由操盘人承担超额亏损金，所以请养成及时关注股票情况，及时追加操盘保证金的良好投资习惯。</p>
       		<h1>停牌股处理流程</h1>
       		<p>1.客户持有停牌股欲转让给达人，需同时满足以下两个条件，可以联系投资达人客服：a.停牌4个交易日及以上；b.持有停牌股的方案已到期。</p>
       		<p>2.系统发送短信给客户确认转让细节。</p>
       		<p>3.投资达人立即冻结相应股票，并于次日开盘前完成证券及资金划转。</p>
        </div>  
        <div class="hp_mainbar hp_mbox7" style="display:none;">
            <h1>操盘限制</h1>
            <p>不得购买S、ST、*ST、S*ST、SST以及被交易所特别处理的股票；</p>
            <p>不得购买权证类可以T+0交易的证券；</p>
            <p>不得购买首日上市新股（或复牌首日股票）等当日不设涨跌停板限制的股票；</p>	
            <p>不得进行坐庄、对敲、接盘、大宗交易、内幕信息等违反股票交易法律法规及证券公司规定的交易；</p>
            <p>不得购买券商公布的风险较大的股票，具体股票可参照网站内今日限制股公示；</p>
            <p>借款金额50万以上单只股票不得超过账户总资产的50%（50万或以下不受限制）；</p>
            <p>借款金额100万以上创业板单只股票不得超过账户总资产的33%（100万或以下不受限制）；单只股票持仓总市值不得超过该股前5个交易日日均成交额的30%；</p>
        </div>
        <div class="hp_mainbar hp_mbox8" style="display:none;">
            <h1>收费介绍</h1>
            <p><b>管理费：</b>即交易账户的使用费，会在国家规定的股票交易日每天从平台余额自动扣除固定的费用。如果您达人网平台余额不够扣除管理费，那么达人网会按照随心操盘用户协议对交易账户强制平仓并终止方案。</p>
            <p><b>利息：</b>利息就是指公司配给客户的那部分资金所要收取的利息费用。利息会在方案生成的时候按照客户选择的天数一次性扣除，而当客户方案在自动延期后，利息的收取规则是按天（自然日）扣除，具体数额 =总利息/天数。</p>
            <p><b>交易手续费：</b>手续费包含印花税、过户费和交易佣金，印花税和过户费按财政部和交易所规定收取，交易佣金0.08%（万8）由券商收取。</p>	            
        </div>
        <div class="hp_mainbar hp_mbox9" style="display:none;">
            <h1>常见问题</h1>
            <h2 class="hp_mbtitle">1、配资炒股风险高吗？</h2>
            <p>股票投资会有一定的风险的，配资进行炒股的话也会有相应的风险，配资比例越高，您的收益比例越高，那么风险相对也会越高。您需要选择一个合适比例的配资方案来进行投资，如果触发亏损警告线的话，您需要及时补仓避免被自动平仓。</p>
            <h2 class="hp_mbtitle">2、我的资金安全吗？</h2>
            <p>我们对客户交易资金的管理完全按照"专户专款专用"的标准模式进行运作，并且以三方监管模式——资金由银行监管，交易由券商监管，风控由投资达人监管。交易委托通过恒生电子交易系统实时进入合作券商在市场上成交，确保交易安全、快捷。</p>
            <h2 class="hp_mbtitle">3、如何能降低配资的风险？</h2>
            <p>建议您在进行配资的时候选取低杠杆倍率的配资方案，并且在购入股票的时候不要进行满仓购买，这样投资风险会相对较低。也建议您预留一定的资金作为备用金，在触发亏损警告时，能及时进行补仓操作，避免因股市波动造成平仓的风险。</p>
            <h2 class="hp_mbtitle">4、收费怎么会有利息和管理费两项？</h2>
            <p>股票操盘收费是由利息和管理费两部分组成，利息是您借用资金收取的费用，而管理费则是对您账户管理收取的费用。利息是在申请账户时一次性收取，管理费比较灵活，是按交易日每天收取。</p>
            <h2 class="hp_mbtitle">5、申请方案成功如何查询交易账户和密码？</h2>
            <p>申请方案成功后，达人将通过短信进行提醒，收到短信后就可在您的达人账户查看您的交易账号和密码，首先点击我的账户；然后找到操盘账户，点击“股票操盘”；最后在操盘详细下点击“交易账户”即可查看。</p>
            <h2 class="hp_mbtitle">6、为什么会限制买入，如何解除？</h2>
            <p>为了控制您的风险，当您方案的资产总值触及到补仓线（预警值）时，系统会自动限制买入，如您补充保证金到补仓线之上或反弹后资产到补仓线之上，可以进行限制买入的解除，解除时间在每天的两次收市之后，当然需要提前解除可随时联系客户。此外，但
您的账户处于欠费状态，同样会限制买入的。补费后即可解除。</p>
			<h2 class="hp_mbtitle">7、为什么补交管理费后仍不能买入？</h2>
            <p>每日管理费将在早上8:00扣除，如您在8:00后补交当日管理费，则顺延至下一个交易日统一扣除，补交后请及时联系客服解除限制买入。</p>
            <h2 class="hp_mbtitle">8、管理费欠费会被平仓吗？</h2>
            <p>当您欠当日管理费时，及时补充则不影响您的任何操作；如您欠费2个交易日，则系统将在欠费的第二个交易日开盘时平仓。</p>
            <h2 class="hp_mbtitle">9、为什么委托单显示是废单？</h2>
            <p>当您买入时，如购买的股票为限制购买股票或该笔委托为对敲时，会出现废单的情况，如对敲请稍后重新进行委托；当您卖出时，如卖出的数量不是100的整数倍，则会出现废单。</p> 
            <h2 class="hp_mbtitle">10、登录交易软件提示账户密码错误怎么办？</h2>
            <p>首先请注意您密码的大小写更替，如还是不能登录可联系客服更改密码。</p>
            <h2 class="hp_mbtitle">11、被平仓后怎么办？</h2>
            <p>为了降低您的风险，当您交易账户的资产总值触及到平仓线时，系统将自动平仓；平仓后可追加保证金至补仓线之上，继续买卖其他的股票，方案仍可继续。</p>	
            <h2 class="hp_mbtitle">12、方案到期后如何延期？</h2>
            <p>当方案到期后，只要您的达人平台余额足够扣除您的延期利息和管理费，并且您没有申请终止方案，系统都是默认为自动延期的，延期利息按您原方案的标准核算成每天的扣除，以自然日计算，管理费不变。</p>	
            <h2 class="hp_mbtitle">13、为什么无法终止操盘？</h2>
            <p>当您的交易账户有持仓股票或委托单时，将无法终止操盘，请在终止操盘前将您该方案的所有股票清仓并撤销委托单，即可终止操盘。</p>	
         </div>
         <div class="hp_mainbar hp_mbox10" style="display:none;">
         	<h3>在支付随心操盘费用时，勾选“是否使用抵扣券”，在下拉框中选择要使用的抵扣券。</h3>
            <img src="${ctx }/static/images/help/main_pic.jpg" width="700">
            <h1>抵扣券使用规则说明</h1>
            <h3>1、用户操盘时，选择使用抵扣券，则可以使用抵扣券金额抵扣操盘利息;</h3>
            <h3>2、用户实际利息金额高于利息抵扣券金额，则用户需补足差额。实际利息金额低于利息抵扣券金额，利息抵扣券一次性使用，差额不补；</h3>
            <h3>3、获得的抵扣券，可以在我的账户-我的抵扣券查看;</h3>
            <h3>4、如发现活动期间利息抵扣券无法使用，请首先确保利息抵扣券在使用期限内，如仍无法使用请联系我们的客服400-633-9257；</h3>
         </div>
    </div>
    <!-- 期指操盘 -->    
    <div class="hp_ctn rule hp_content" style="display: none">
    	<div class="hp_siderbar rule_siderbar">
            <!-- <a href="javascript:void(0)"  data="6">什么是期指操盘</a>
            <a href="javascript:void(0)" data="1">期指随心乐</a>
            <a href="javascript:void(0)" data="2">期指天天乐</a>
            <a href="javascript:void(0)" data="3">期指操盘规则</a>
            <a href="javascript:void(0)" data="4">收费介绍</a>             
            <a href="javascript:void(0)" data="5">常见问题</a> -->
            <a href="javascript:void(0)" data="0">富时A50</a>
            <a href="javascript:void(0)" data="8">国际原油</a>
            <a href="javascript:void(0)" data="9">恒指操盘</a>
            <a href="javascript:void(0)" data="7">商品期货</a>
            <a href="javascript:void(0)" data="5">国际期货</a>
        </div>
        <div class="hp_mainbar hp_mbox6" style="display:none;">
            <h1>什么是期指操盘？</h1>
            <p>期指操盘是一款可以买卖股指期货的期货投资产品，实际上就是操作沪深300主力合约的股指期货买卖，随买随卖操作方便，产品收益高但风险也高。</p>
            <h2>门槛低，看得见的高收益!</h2>
            <p>以沪深300主力合约指数3000点为例：普通的股指期货15万元交易一手；开户更是高达50万以上。投资达人期指操盘最低6000元交易一手；门槛直接降低25倍；收益就扩大25倍。</p>
            <h2>随买随卖 你操盘你做主</h2>
            <p>股指操盘灵活买卖，可以买入做多，可以卖出做空，多空都赚钱（前提是你买对方向）。</p>
        </div>
        <div class="hp_mainbar hp_mbox1" style="display:none;">
            <h1>期指随心乐</h1>
            <h2>期指随心乐是投资达人创新性的结合股指期货产品特性，为广大客户量身打造的股指期货产品，期指随心乐可选择多种保证金，开仓手数以及操盘时间，玩法多种多样。</h2>
            <h2>期指随心乐操盘流程：</h2>
            <p>1、申请操盘：进入首页-期指操盘-期指随心乐，您可以随心随意的选择不同的开仓数量、单手保证金以及账户使用时间，操盘方案灵活多变。</p>
            <img src="${ctx }/static/images/help/new_01.png" width="700">
            <p>2、操盘账号：操盘发起成功后，进入我的账户-期指操盘-期指随心乐等待发放账号</p>
            <img src="${ctx }/static/images/help/new_02.png" width="700">
            <p>3、开户处理时间：交易时间，系统将在30分钟内下发操盘账户；非交易时间，系统将在次日开盘前下发操盘账户。</p>
            <p>注意事项：如遇市场行情好，开户数多时可能遇到排队的情况，开户可能会有延迟。我们会按照发起时间顺序在第一时间为您开出。在等待开户时，您可以先去了解<a href="#">期货交易软件</a></p>
        </div>
        <div class="hp_mainbar hp_mbox2" style="display:none;">
            <h1>期指天天乐</h1>
            <h2>期指天天乐是投资达人创新性的结合股指期货产品特性，为广大客户量身打造的股指期货产品，您可以选择不同的操盘保证金，选择操盘保证金越多，风险越低，可开仓的最大手数越多，无账户使用时间限制。</h2>
            <h2>期指天天乐操盘流程：</h2>
            <p>1、申请操盘：进入首页-期指操盘-期指天天乐，您可以根据您的实际需求选择不同的操盘保证金，对应您可开仓手数。</p>
            <img src="${ctx }/static/images/help/new_03.png" width="700">
            <p>2、操盘账号：操盘发起成功后，进入我的账户-期指操盘-期指天天乐等待发放账号</p>
            <img src="${ctx }/static/images/help/new_04.png" width="700">
            <p>3、开户处理时间：交易时间：系统将在30分钟内下发操盘账户；非交易时间：系统将在次日开盘前下发操盘账户。</p>
            <p>注意事项：如遇市场行情好，开户数多时可能遇到排队的情况，开户可能会有延迟。我们会按照发起时间顺序在第一时间为您开出。在等待开户时，您可以先去了解<a href="#">期货交易软件</a></p>
        </div>
        <div class="hp_mainbar hp_mbox3" style="display:none;">
            <h1>期指操盘规则</h1>           
            <p>1)交易品种为沪深300股指期货当期主力合约，以平台实际公示可交易品种为准。</p>
            <p>2)交易日00:00-14:00期间申请并成功支付操盘保证金的，当天可提供股指期货投资策略服务，14:00以后申请并成功支付操盘保证金的，则于次日开始提供股指期货投资策略服务。</p>
            <p>3)如交易时间内您需对账户进行结算的，须实盘账户已清仓，才可申请结算。</p>
            <p>4)交易股指期货合约时间为9:25-15:05，每日该时间段届满前应对账户中所有交易平仓，未能按时平仓的所有仓单系统将强制平仓。交易实行每日无负债结算制度。</p>
            <p>5)系统平仓线以您支付的操盘保证金每手少于或等于3000元时触发强制平仓线指令，系统自动对账户内所有合约按照市价平仓，最终成交价以实际成交为准。</p>
        	<p>6)您应遵循下列交易限制：</p>
        	<p>a）不得参与集合竞价买入和卖出；</p>
        	<p>b）可选择可开仓最大手数、单手保证金和账户使用时间；</p>
        	<p>c）实盘交易期货合约每次开仓手数不大于可开仓最大手数；最大持仓手数也不能大于可开仓最大手数；一天之内可多次开仓，次数不限；</p>
        	<p>d）股指期货交易标的当日涨跌幅达到8%时，禁止开仓；当涨跌幅达到9%时，将强行平仓所有持仓合约，盈亏自负；</p>
        	<p>e）您自行设定的与交易有关的各项规则如与投资达人网在系统中设置的规则冲突时，您设置内容无效；</p>
        	<p>7) 结算规则：系统在账户结算日股指期货合约平仓后对账户进行结算；操盘盈亏=账户所有持仓平仓结算后账户余额-操盘初始资金；如操盘盈利，则盈利作为报酬归您所有，如操盘亏损且亏损额度小于或等于操盘保证金金额的，则亏损由您承担，直接从操盘保证金中扣除；超出操盘保证金部分投资达人网保留追讨权利；您同意结算金额以交易系统后台清算数据为准；当选择账户使用时间大于一个交易日时，不能在申请当日申请终结方案。</p>
			<h2>期指天天乐规则：</h2>
			<p>1)交易品种为沪深300股指期货当期主力合约，以平台实际公示可交易品种为准。</p>
			<p>2)交易日00:00-14:00期间申请并成功支付操盘保证金的，当天可提供股指期货投资策略服务，14:00以后申请并成功支付操盘保证金的，则于次日开始提供股指期货投资策略服务。</p>
			<p>3)如交易时间内您需对账户进行结算的，须实盘账户已清仓，才可申请结算。</p>
			<p>4)交易股指期货合约时间为9:25-15:05，每日该时间段届满前应对账户中所有交易平仓，未能按时平仓的所有仓单系统将强制平仓。交易实行每日无负债结算制度。</p>
        	<p>5)系统平仓线以您支付的操盘保证金每手少于或等于3000元时触发强制平仓线指令，系统自动对账户内所有合约按照市价平仓，最终成交价以实际成交为准。</p>
        	<p>6)您应遵循下列交易限制：</p>
        	<p>a）不得参与集合竞价买入和卖出；</p>
        	<p>b）可开仓最大手数由申请并成功支付的操盘保证金决定；</p>
        	<p>c）实盘交易期货合约每次开仓手数只能小于或等于可开仓最大手数；最大持仓手数也只能小于或等于可开仓最大手数；一天之内可多次开仓，次数不限。</p>
        	<p>d）股指期货交易标的当日涨跌幅达到8%时，禁止开仓；当涨跌幅达到9%时，将强行平仓所有持仓合约，盈亏自负。</p>
        	<p>e）您自行设定的与交易有关的各项规则如与投资达人在系统中设置的规则冲突时，您设置内容无效。</p>
        	<p>7)结算规则：操盘盈亏=账户所有持仓平仓结算后账户余额-操盘初始资金；如操盘盈利，则盈利作为报酬归您所有，如操盘亏损且亏损额度小于或等于操盘保证金金额的，则亏损由您承担，直接从操盘保证金中扣除；超出操盘保证金部分投资达人网保留追讨权利；您同意结算金额以交易系统后台清算数据为准。</p>
        </div>
        <div class="hp_mainbar hp_mbox4" style="display:none;">        	
            <h1>收费介绍</h1>       
            <p><b>管理费：</b>即交易账户的使用费，会在国家规定的期指交易日每天从平台余额自动扣除固定的费用。如果您达人网平台余额不够扣除管理费，那么达人网会按照期指随心乐、天天乐操盘用户协议对交易账户强制平仓并终止方案。</p>    
            <p><b>交易手续费：</b>期货手续费是指期货交易者买卖期货成交后按成交合约总价值的一定比例所支付的费用。</p>
            <p><b>申报费用: </b>中金所规定沪深300股指期货合约将收取申报费，每笔申报费1元；申报是指买入、卖出及撤销委托。</p>
        </div>
        <div class="hp_mainbar hp_mbox0" style="display:none;">        	
            <h1>富时A50</h1>     
            <p>新华富时中国A50指数包含了中国A股市场市值最大 的50家公司，其总市值占A股总市值的33%，是最能代表中国A股市场的指数，许多国际投资者把这一指数看作是衡量中国市场的精确指标。</p>  
            <h2>富时A50操盘流程：</h2>	
            <p>1、申请操盘：进入首页-富时A50，您可以随心随意的选择不同的开仓手数，支付不同的保证金。</p>            
            <img src="${ctx }/static/images/help/new_05.gif" width="700">           
            <p>2、操盘账号：操盘发起成功后，进入我的账户-富时A50等待发放账号。</p>           
            <img src="${ctx }/static/images/help/new_06.gif" width="700">    
            <p>3、开户处理时间：交易时间：系统将在30分钟内下发操盘账户；非交易时间：系统将在次日开盘前下发操盘账户。</p>
            <p>注意事项：如遇市场行情好，开户数多时可能遇到排队的情况，开户可能会有延迟。我们会按照发起时间顺序在第一时间为您开出。在等待开户时，您可以先去了解<a href="${ctx}/help?tab=software&leftMenu=8">富时A50交易软件</a></p>
       		<h2>富时A50操盘规则：</h2>
       		<p>1)交易品种为富时A50当期主力合约，以平台实际公示可交易品种为准。</p>	
       		<p>2)交易日00:00-22:00期间申请并成功支付操盘保证金的，当天可提供富时A50投资策略服务，22:00以后申请并成功支付操盘保证金的，则于次日开始提供富时A50投资策略服务。</p>	
       		<p>3)如交易时间内您需对账户进行结算的，须实盘账户已清仓，才可申请结算。</p>
       		<p>4)交易A50合约时间为9:05-15:50和16:45-23:55，每日该时间段届满前应对账户中所有交易平仓，未能按时平仓的所有仓单系统将强制平仓。交易实行每日无负债结算制度。</p>	
       		<p>5)系统平仓线以乙方操盘账户内的总操盘金每手少于或等于2500美元时触发强制平仓线指令，系统自动对账户内所有合约按照市价平仓，最终成交价以实际成交为准。</p>	
       		<p>6)您应遵循下列交易限制：</p>
       		<p>a）不得参与集合竞价买入和卖出；</p>	
       		<p>b）可开仓最大手数由申请并成功支付的操盘保证金决定；一天之内可多次开仓，次数不限。</p>
       		<p>c）乙方自行设定的与交易有关的各项规则如与丙方在系统中设置的规则冲突时，乙方设置内容无效。</p>
       		<p>7)结算规则：操盘盈亏=账户所有持仓平仓结算后账户余额-操盘初始资金；如操盘盈利，则盈利作为报酬归乙方所有，如操盘亏损且亏损额度小于或等于操盘保证金金额的，则亏损由乙方承担，直接从操盘保证金中扣除；超出操盘保证金部分丙方保留追讨权利；乙方同意结算金额以交易系统后台清算数据为准。</p>
        	<p>8)富时A50收取技术服务费每手58元。</p>        	
        </div>              
        <div class="hp_mainbar hp_mbox8" style="display:none;">        	
            <h1>国际原油</h1>     
            <p>在石油期货合约之中，原油期货是交易量最大的品种，我们推出的国际原油是投资世界交易量最大、影响力最广泛的纽约商业交易所轻原油期货合约。该合约规格为每手1000桶，报价单位为美元/桶，价格波动最小单位为1美分，当初一推出后交易活跃，为有史以来最成功的国际商品期货合约，它的成交价格成为国际石油市场关注的焦点。国际原油投资特点：1，全球性市场，操作简单  2，T+0模式，每天可多次交易，增加获利几率  3，杠杆交易原理，以小博大  4，几乎24小时交易，时间自由   5，无涨停板和交割时间限制，特别适合做短线投资。</p>  
            <h2>国际原油操盘流程：</h2>	
            <p>1、申请操盘：进入首页-国际原油，您可以随心随意的选择不同的开仓手数，支付不同的保证金。</p>            
            <img src="${ctx }/static/images/help/cl_01.gif" width="700">           
            <p>2、操盘账号：操盘发起成功后，进入我的账户-国际原油等待发放账号。</p>           
            <img src="${ctx }/static/images/help/cl_02.gif" width="700">    
            <p>3、开户处理时间：交易时间：系统将在30分钟内下发操盘账户；非交易时间：系统将在次日开盘前下发操盘账户。</p>
            <p>注意事项：如遇市场行情好，开户数多时可能遇到排队的情况，开户可能会有延迟。我们会按照发起时间顺序在第一时间为您开出。在等待开户时，您可以先去了解<a href="${ctx}/help?tab=software&leftMenu=8">易盛外盘交易软件</a></p>
       		<h2>国际原油操盘规则：</h2>
       		<p>1)交易品种为CME美原油期货当期主力合约，以平台实际公示可交易品种为准。</p>	
       		<p>2)交易日00:00-22:00期间申请并成功支付操盘保证金的，当天可提供原油期货投资策略服务；22:00以后申请并成功支付操盘保证金的，则于次日开始提供美原油期货投资策略服务。</p>	
       		<p>3)如交易时间内乙方需对账户进行结算的，须实盘账户已清仓，才可申请结算。</p>
       		<p>4)交易美原油期货合约时间为9:05-23:55，申请人在交易日该时间段届满前应对账户中所有交易进行平仓，未能按时平仓的所有仓单系统将强制平仓。交易实行T+2日结算制度。</p>	
       		<p>5)当乙方操盘账户内的总操盘金少于或等于亏损平仓线时，系统将触发强制平仓指令，自动对账户内所有合约按照市价平仓，强平单的最终成交价以实际成交价为准，强平完成后，账户将被限制进行新的开仓交易。</p>	
       		<p>6)您应遵循下列交易限制：</p>
       		<p>a）不得参与集合竞价买入和卖出；</p>	
       		<p>b）可开仓最大手数由申请并成功支付的操盘保证金决定；一天之内可多次开仓，次数不限。</p>
       		<p>c）乙方自行设定的与交易有关的各项规则如与丙方在系统中设置的规则冲突时，乙方设置内容无效。</p>
       		<p>7)结算规则：操盘盈亏=账户所有持仓平仓结算后账户余额-操盘初始资金；如操盘盈利，则盈利作为报酬归乙方所有，如操盘亏损且亏损额度小于或等于操盘保证金金额的，则亏损由乙方承担，直接从操盘保证金中扣除；超出操盘保证金部分丙方保留追讨权利；乙方同意结算金额以交易系统后台清算数据为准。</p>
        	<p>8)国际原油收取技术服务费每手148元。</p>        	
        </div>                      
        <div class="hp_mainbar hp_mbox9" style="display:none;">        	
            <h1>恒指期货</h1>     
            <p>香港股市价格的重要指标，指数由若干只成份股（即蓝筹股）市值计算出来的，代表了香港交易所所有上市公司的12个月平均市值涵盖率的63%，恒生指数由恒生银行下属恒生指数有限公司负责计算及按季检讨，公布成份股调整。该指数于1969年11月24日首次公开发布。</p>  
            <h2>恒指期货操盘流程：</h2>	
            <p>1、申请操盘：进入首页-恒指期货，您可以随心随意的选择不同的开仓手数，支付不同的保证金。</p>            
            <img src="${ctx }/static/images/help/hsi_01.gif" width="700">           
            <p>2、操盘账号：操盘发起成功后，进入我的账户-恒指期货等待发放账号。</p>           
            <img src="${ctx }/static/images/help/hsi_02.gif" width="700">    
            <p>3、开户处理时间：交易时间：系统将在30分钟内下发操盘账户；非交易时间：系统将在次日开盘前下发操盘账户。</p>
            <p>注意事项：如遇市场行情好，开户数多时可能遇到排队的情况，开户可能会有延迟。我们会按照发起时间顺序在第一时间为您开出。在等待开户时，您可以先去了解<a href="${ctx}/help?tab=software&leftMenu=8">易盛外盘交易软件</a></p>
       		<h2>恒指期货操盘规则：</h2>
       		<p>1)交易品种为香港交易所恒生指数期货当期主力合约，以平台实际公示可交易品种为准。</p>	
       		<p>2)交易日00:00-22:00期间申请并成功支付操盘保证金的，当天可提供股指期货投资策略服务；22:00以后申请并成功支付操盘保证金的，则于次日开始提供股指期货投资策略服务。</p>	
       		<p>3)如交易时间内乙方需对账户进行结算的，须实盘账户已清仓，才可申请结算。</p>
       		<p>4)交易恒生指数期货合约时间为9:20-11:55,13:05-16:00，17:05-22:55，申请人在交易日该时间段届满前应对账户中所有交易进行平仓，未能按时平仓的所有仓单系统将强制平仓。交易实行T+1日结算制度。</p>	
       		<p>5)当乙方操盘账户内的总操盘金少于或等于亏损平仓线时，系统将触发强制平仓指令，自动对账户内所有合约按照市价平仓，强平单的最终成交价以实际成交价为准，强平完成后，账户将被限制进行新的开仓交易。</p>	
       		<p>6)您应遵循下列交易限制：</p>
       		<p>a）不得参与集合竞价买入和卖出；</p>	
       		<p>b）可开仓最大手数由申请并成功支付的操盘保证金决定；一天之内可多次开仓，次数不限。</p>
       		<p>c）乙方自行设定的与交易有关的各项规则如与丙方在系统中设置的规则冲突时，乙方设置内容无效。</p>
       		<p>7)结算规则：操盘盈亏=账户所有持仓平仓结算后账户余额-操盘初始资金；如操盘盈利，则盈利作为报酬归乙方所有，如操盘亏损且亏损额度小于或等于操盘保证金金额的，则亏损由乙方承担，直接从操盘保证金中扣除；超出操盘保证金部分丙方保留追讨权利；乙方同意结算金额以交易系统后台清算数据为准。</p>
        	<p>8)恒生指数收取技术服务费每手118元。</p>        	
        </div>      
        <div class="hp_mainbar hp_mbox7" style="display:none;">        	
            <h1>商品期货操盘细则</h1>     
            <table width="98%" border="0" cellspacing="0" cellpadding="0" class="hp_m_list">
			  <tr>
			    <td width="20%"></td>
			    <td width="20%;">沪金</td>
			    <td width="20%">沪银</td>
			    <td width="20%">沪铜</td>
			    <td width="20%">橡胶</td>
			  </tr>
			  <tr>
			    <td>最小波动价</td>
			    <td>0.05指数点(50元)</td>
			    <td>1个指数点(15元)</td>
			    <td>10个指数点(50元)</td>
			    <td>1个指数点(50元)</td>
			  </tr>
			  <tr>
			    <td>合约乘数</td>
			    <td>1000元/点</td>
			    <td>15元/点</td>
			    <td>5元/点</td>
			    <td>10元/点</td>
			  </tr>
			  <tr>
			    <td>交易时间</td>
			    <td>9:05-14:55,21:05-23:55</td>
			    <td>9:05-14:55,21:05-23:55</td>
			    <td>9:05-14:55,21:05-23:55</td>
			    <td>9:05-14:55,21:05-22:25</td>
			  </tr>
			  <tr>
			    <td>涨跌幅限制</td>
			    <td>开盘价格上升或下跌5%</td>
			    <td>开盘价格上升或下跌6%</td>
			    <td>开盘价格上升或下跌6%</td>
			    <td>开盘价格上升或下跌6%</td>
			  </tr>
			  <tr>
			    <td>单手保证金</td>
			    <td>1600元</td>
			    <td>400元</td>
			    <td>1700元</td>
			    <td>1000元</td>
			  </tr>
			  <tr>
			    <td>手续费</td>
			    <td>50元/手</td>
			    <td>30元/手</td>
			    <td>50元/手</td>
			    <td>50元/手</td>
			  </tr>
			</table>
			<br>
            <h2>商品期货操盘流程：</h2>	
            <p>1、选择品种申请操盘：进入首页-商品期货，在下拉导航选择您要选择的交易品种，您可以随心随意的选择不同的开仓手数，支付不同的保证金。</p>            
            <img src="${ctx }/static/images/help/pro_01.gif" width="700">           
            <p>2、操盘账号：操盘发起成功后，进入我的账户-商品期货，选择不同的交易品种，等待发放账号。</p>           
            <img src="${ctx }/static/images/help/pro_02.gif" width="700">    
            <p>3、开户处理时间：交易时间：系统将在30分钟内下发操盘账户；非交易时间：系统将在次日开盘前下发操盘账户。</p>
            <p>注意事项：如遇市场行情好，开户数多时可能遇到排队的情况，开户可能会有延迟。我们会按照发起时间顺序在第一时间为您开出。在等待开户时，您可以先去了解<a href="${ctx}/help?tab=software&leftMenu=9" target="_blank">鑫管家博易大师交易软件</a></p>
       		<h2>商品期货注意事项：</h2>
       		<p>1)交易品种为沪金期货、沪银期货、沪铜期货、橡胶期货当期主力合约，以平台实际公示可交易品种为准。</p>	
       		<p>2)交易日00:00-14:00期间申请并成功支付操盘保证金的，当天可提供商品期货投资策略服务，14:00-22:00期间申请并成功支付操盘保证金的，当天夜盘可提供商品期货投资策略服；22:00以后申请并成功支付操盘保证金的，则于次日开始提供商品期货投资策略服务。</p>	
       		<p>3)如交易时间内您需对账户进行结算的，须实盘账户已清仓，才可申请结算。</p>
       		<p>4)当乙方操盘账户内的总操盘金少于或等于亏损平仓线时，系统将触发强制平仓指令，自动对账户内所有合约按照市价平仓，强平单的最终成交价以实际成交价为准，强平完成后，账户将被限制进行新的开仓交易。</p>	
       		<p>5)您应遵循下列交易限制：</p>
       		<p>a)不得参与集合竞价买入和卖出；</p>	
       		<p>b)可开仓最大手数由申请并成功支付的操盘保证金决定；</p>
       		<p>c)实盘交易期货合约每次开仓手数不能大于可开仓最大手数；最大持仓手数也不能大于可开仓最大手数；一天之内可多次开仓，次数不限。</p>
       		<p>d)沪金期货交易标的当日涨跌幅达到4.5%时，禁止开仓；当涨跌幅达到4.8%时，将强行平仓所有持仓合约，盈亏自负。沪银、沪铜、橡胶期货交易标的当日涨跌幅达到5.5%时，禁止开仓；当涨跌幅达到5.8%时，将强行平仓所有持仓合约，盈亏自负。</p>
       		<p>e)乙方自行设定的与交易有关的各项规则如与丙方在系统中设置的规则冲突时，乙方设置内容无效。</p>
       		<p>6)结算规则：操盘盈亏=账户所有持仓平仓结算后账户余额-操盘初始资金；如操盘盈利，则盈利作为报酬归乙方所有，如操盘亏损且亏损额度小于或等于操盘保证金金额的，则亏损由乙方承担，直接从操盘保证金中扣除；超出操盘保证金部分丙方保留追讨权利；乙方同意结算金额以交易系统后台清算数据为准。</p>
        </div>
        <div class="hp_mainbar hp_mbox2" style="display:none;">        	
            <h1>常见问题</h1>       
            <h2>1、登录交易软件提示账户密码错误怎么办？</h2>
            <p>首先请注意您密码的大小写更替，如还是不能登录可联系客服更改密码。</p>
            <h2>2、股指期货方案选定交易日后可提前终止方案吗？</h2>
            <p>可以的，收费标准以您交易的时间为准。</p>
            <h2>3、股指期货让日追加保证金，当日可以使用吗？</h2>
            <p>当日9点-12点45分追加的保证金，当日13点前处理。<br>当日12点45分-次日9点追加的保证金，将于次日9点25分前处理</p>
        </div>        
        <div class="hp_mainbar hp_mbox5" style="display:none;">        	   	
            <h1>国际期货操盘细则</h1>    
            <table width="95%" border="0" cellspacing="0" cellpadding="0" class="hp_m_list">
			  <tr>
			    <td width="22%"></td>
			    <td width="26%;">富时A50</td>
			    <td width="26%">恒指期货</td>
			    <td width="26%">国际原油</td>
			  </tr>
			  <tr>
			    <td>最小波动价</td>
			    <td>2.5个指数点(2.5美元)</td>
			    <td>1个指数点(50港元)</td>
			    <td>0.01个指数点</td>
			  </tr>
			  <tr>
			    <td>合约乘数</td>
			    <td>1美元/点</td>
			    <td>50港币/点</td>
			    <td>1000桶/手</td>
			  </tr>
			  <tr>
			    <td>交易时间</td>
			    <td>9:05-15:50,16:45-23:55</td>
			    <td style="line-height:20px;">9:20-11:55,13:05-16:10,<br>17:05-23:40</td>
			    <td>9:05-23:55</td>
			  </tr>
			  <tr>
			    <td>涨跌幅限制</td>
			    <td>无涨跌幅限制</td>
			    <td>无涨跌幅限制</td>
			    <td style="line-height:20px;">±10美元，暂停交易5分钟后再扩大±10美元，以此循环</td>
			  </tr>
			  <tr>
			    <td>单手保证金</td>
			    <td>687.5元</td>
			    <td>5000元</td>
			    <td>2150元</td>
			  </tr>
			  <tr>
			    <td>手续费</td>
			    <td>58元/手</td>
			    <td>95元/手</td>
			    <td>125元/手</td>
			  </tr>
			</table>
        </div>      
    </div>
    <!--安全保障  -->
    <div class="hp_ctn safety hp_content" style="display: none">
        <div class="hp_siderbar safety_siderbar">
            <a href="javascript:void(0);" class="on" data="1">安全保障</a>
            <a href="javascript:void(0);" data="2">法律保障</a>
            <a href="javascript:void(0);" data="3">资金安全保障</a>
            <a href="javascript:void(0);" data="4">信息安全保障</a>
            <a href="javascript:void(0);" data="5">交易安全保障</a>
            <a href="javascript:void(0);" data="6">专业风控保障</a>
             <a href="javascript:void(0);" data="7">协议安全保障</a>
        </div>
        <div class="hp_mainbar hp_mbox1">
        	<h1>安全保障</h1>
            <p>投资达人对客户交易资金的管理完全按照"专户专款专用"的标准模式进行运作，并且实行三方监管——资金由银行监管，交易由券商监管，风控由投资达人监管。交易委托使用交易软件系统实时进入股市，通过合作券商进行股票交易，确保交易安全、快捷。并通过自主企业出资，引入多级风控管理机制，降低投资风险和门槛。投资达人提供的随心操盘服务效率高、收费透明、流程简单，用心帮助每一位客户实现财富梦想、走上人生巅峰。</p>
        </div>
        <div class="hp_mainbar hp_mbox2" style="display:none;">
        	<h1>法律保障</h1>
            <p>配资属于民间借贷的一种形式，根据《合同法》和《电子签名法》，合同严格地受法律保护，双方权益得到有力保障。电子合同和纸质合同具备同等法律效力。</p>
        </div>
        <div class="hp_mainbar hp_mbox3" style="display:none;">
        	<h1>资金安全保障</h1>
            <p>投资达人网与招商银行达成合作关系，招商银行作为投资达人网的第三方账户存管银行，有权对存管账户资金进出进行管理、对账户资金进行确认，确保客户的资金安全；客户在投资达人的交易资金是可以完全放心的。</p>
        </div>
        <div class="hp_mainbar hp_mbox4" style="display:none;">
        	<h1>信息安全保障</h1>
            <p>我们严格遵守国家相关的法律法规，保护客户的隐私。未经您的同意，我们永不会向任何第三方公司、组织和个人披露您的个人信息、账户信息以及交易信息（法律法规另有规定的除外）。</p>
        </div>
        <div class="hp_mainbar hp_mbox5" style="display:none;">
        	<h1>交易安全保障</h1>
            <p>交易指令通过交易软件直接到券商席位下单，和直接在券商开户炒股完全一样；并受合作券商、沪深交易所和证监会三重监管。客户挂单真实可见，每一笔交易都可以在开通了Level2”功能的大智慧、同花顺等股票行情软件中查询。</p>
        </div>
        <div class="hp_mainbar hp_mbox6" style="display:none;">
        	<h1>专业风控保障</h1>
            <h2>为了保护操盘资金的安全，同时帮您养成良好的投资习惯，交易账户会设置亏损警戒线和亏损平仓线。</h2>
            <p>亏损警戒线：当总操盘资金低于警戒线以下时，只能平仓不能建仓，需要尽快补充风险保证金，以免低于亏损平仓线被平仓。</p>
            <p>亏损平仓线：当总操盘资金低于平仓线以下时，我们将有权把您的股票进行平仓，为避免平仓发生，请时刻关注风险保证金是否充足。</p>
        </div>
        <div class="hp_mainbar hp_mbox7" style="display:none;">
        	<h1>协议安全保障</h1>
            <div class="hp_safetitle">一、投融保交易电子数据保全，把交易合同锁进保险箱</div>
            <p>上海信闳投资管理有限公司联手重庆邮电大学电子数据保全中心，为投资者提供交易凭证保全服务。交易凭证（担保函、担保合同等信息）一旦保全，其内容、生成时间等信息将被加密固定，任何细微的修改都会 导致保全证书函数值的变化，预防遭人更改，且生成唯一的保全证书供下载。如发生司法纠纷，保全证书持有人可以通过电子数据保全中心提供的认证证书向法院或仲裁机构提供有效、可靠 的证据，从而获得举证的优势地位。</p>
            <div class="hp_safetitle">二、答题解惑</div>
            <h4>1.投资者如何检验保全证书真伪？</h4>
            <p>投资者拿到保全证书后，可以登录www.ebaoquan.org，录入证书上的备案号及上传被保全文件（如电子合同）进行真伪验证。或者从收到证书生成的邮件中， 点击查看“查看我的保全证书”，进入证书页面使用验证功能。</p>
            <h4>2.电子数据保全是否具有法律效力？</h4>
            <p>最新修正的《刑事诉讼法》《民事诉讼法》均将电子数据列为证据的一种。电子数据保全中心提供的保全证书，可作为司法人员和律师分析、认定案件事实的有效证据，让受保者在司法纠纷中占据有利地位。根据受保者需要，电子数据保全中心还可以为受保者提供合作机构出具的公证书或司法鉴定书。</p>
            <h4>3.为什么选择电子数据保全？</h4>
            <p>相较传统取证手段，电子数据保全具有低成本，高效率，保密（系统仅仅在本地生成数据的数字摘要，绝无泄露隐私、商业秘密、内容的风险）合法、权威等优势， 并且可通过事先存证来预防和化解纠纷，是互联网投资者保护交易证据安全的首选。</p>
            <div class="hp_safetitle">三、重庆邮电大学电子数据保全中心</div>
            <p>重庆邮电大学电子数据保全中心（ ebaoquan.org )是重庆邮电大学利用自研专利技术，建立的以电子数据第三方证明为核心的平台。该平台目前已获得三项专利，6项国家CNAS资格认证《电子数据保全及电子文件搜索》《录音真实性鉴定》《电子数据恢复和提取》《图像真实性鉴定》《电子数据的分析与鉴定》《电子邮件真实性鉴定》，拥有独立司法鉴定资格（国家颁发司法鉴定许可证）、与重庆版权局、公证处等单位达成合作协议，拥有专属绿色通道。</p>
            <div class="hp_safelink"><a href="https://www.ebaoquan.org/handbook" target="_blank">手把手教你做保全</a><a href="https://www.ebaoquan.org/" target="_blank">了解详情</a></div>
            <h4>重庆邮电大学电子数据保全中心提供什么？</h4>
            <div class="hp_safecqpic">
                <ul class="hp_safecqone">
                    <li class="hp_sfcq_img"><img src="${ctx }/static/images/help/cre_0.gif"></li>
                    <li class="hp_sfcq_font">
                        <em>电子数据保全证书</em>
                        <span>电子数据在线保全是指对以电子数据形式(包括文字、图形、字母、数字、三维标志、颜色组合和声音以及上述要素的组合等,下同)存在的各类电子数据信息，运用专利技术进行运算、加密固定，载明保全生成的标准时间、运算值、档案编号等，以防止被人篡改，确保电子数据的原始性、客观性的程序及方法。</span>
                    </li>
                </ul>
                <div class="hp_safecqtow">
                    <img src="${ctx }/static/images/help/cre_1.gif">
                    <span>获得国家颁发司法鉴定证书，拥有司法鉴定资格</span>
                </div>

            </div>
            <div class="hp_safetitle">四、鉴定中心资质</div>
            <div class="hp_safepic">
                <img src="${ctx }/static/images/help/cre_3.gif">
                <img src="${ctx }/static/images/help/cre_4.gif">
                <img src="${ctx }/static/images/help/cre_5.gif">
                <img src="${ctx }/static/images/help/cre_6.gif">
                <img src="${ctx }/static/images/help/cre_7.gif">
                <img src="${ctx }/static/images/help/cre_8.gif">
            </div>        	
        </div>        
    </div>
    <!--交易软件下载  -->
    <div class="hp_ctn software hp_content" style="display:none;">
        <div class="hp_siderbar software_siderbar">
            <a href="javascript:void(0)" class="on" data="1">股票交易系统</a>            
            <a href="javascript:void(0)" data="12">股票掌上交易系统</a>
             <!-- <a href="javascript:void(0)" data="2">钱江版</a>
            <a href="javascript:void(0)" data="5">涌金版</a>
             <a href="javascript:void(0)" data="6">同花顺</a>
             <a href="javascript:void(0)" data="4">钱隆TTS</a>
           <a href="javascript:void(0)" data="7">期指操盘交易软件</a> -->
            <a href="javascript:void(0)" data="8">富时A50交易系统</a>
            <a href="javascript:void(0)" data="10">国际期货交易系统</a>
            <a href="javascript:void(0)" data="9">商品期货交易系统</a>
            <a href="javascript:void(0)" data="11">期货行情软件</a>
        </div>
        <div class="hp_mainbar hp_mbox3">
            <h1>恒生交易软件</h1>
            <div class="hp_soft">
                投资达人的所有股票交易委托均采用上市公司<a href="#">《恒生电子》</a>（股票交易代码：<a href="">600570</a>是中国最专业的证券交易系统服务商，其系统与各大证券公司进行了无缝对接。
            </div>
            <div class="hp_soft">投资达人与恒生电子股份有限公司强强联合，为客户提供更加专业、高效、安全的投资管理服务。投资达人的客户可通过下载恒生电子提供的客户端进行股票交易，交易单子通过恒生电子交易系统实时进入合作券商在市场上成交。</div>
            <h1>同花顺交易软件</h1>
            <div class="hp_soft"><a href="">同花顺</a>（股票交易代码：<a href="">300033</a>）是中国最专业的证券交易系统服务商，浙江核新同花顺（<a href="">300033</a>）网络信息股份有限公司，是国内第一家互联网金融信息服务行业上市公司(股票代码：<a href="">300033</a>)，国家规划布局内重点软件企业、国家信息化试点工程单位。目前公司注册资金26880万元，是专业从事金融大数据处理、金融信息云服务的高新技术企业。</div>
            <div class="hp_soft">投资达人与同花顺强强联合，为客户提供更加专业、高效、安全的投资管理服务。投资达人的客户可通过下载同花顺提供的客户端进行股票交易，交易单子通过同花顺交易系统实时进入合作券商在市场上成交。</div>
            <h1>大智慧交易软件</h1>
            <div class="hp_soft"><a href="">大智慧</a>（股票交易代码：<a href="">601519</a>）是中国最专业的证券交易系统服务商，其系统与各大证券公司进行了无缝对接。</div>
            <div class="hp_soft">投资达人与大智慧强强联合，为客户提供更加专业、高效、安全的投资管理服务。投资达人的客户可通过下载大智慧提供的钱隆TTS客户端进行股票交易，交易单子大智慧交易系统实时进入合作券商在市场上成交。</div>
        </div>
        <div class="hp_mainbar hp_mbox2" style="display:none;">
            <h1>钱江版下载</h1>
            <div class="hp_sf_down">
                <div class="hp_sf_pcdown">
                    <h6>电脑版下载</h6>
                    <p class="hp_sf_pcdownpic"><a href="http://www.ihoms.com/file/xunlei/downloadxunlei/HOMS_QiJian/ZhengShi/DuLi/homs_duli.exe?fileCode=10&amp;sourceType=4">独立委托版</a></p>
                    <p class="hp_sf_pcdownfont">
                        <span>该版本是独立的委托下单系统，不包含行情功能。</span>
                        <span>包含功能如下：</span>
                        <span>1、支持股票交易相关买入、卖出、信息查询等所有功能；</span>
                        <span>2、支持股票交易特色功能；</span>
                        <span>双向买卖功能（对买对卖功能）</span>
                        <span>批量买入、批量卖出</span>
                        <span>埋单交易（定时埋单，价格埋单）</span>
                        <span>组合交易</span>
                        <span>债券回购、债券逆回购功能</span>
                        <span>支持多账户交易、当账户交易功能</span>
                    </p>
                </div>  
                <div class="hp_sf_teldown">                    
                    <h6>手机端下载</h6>
                    <ul>
                         <li>
                            <a href="http://bcs.duapp.com/ihoms2bucket/HOMS客户端/HOMS_IPhone正式.ipa"><img src="${ctx }/static/images/help/down_03.gif"></a>
                            <img src="${ctx }/static/images/help/hdownload-iphone2.gif" width="170" height='170'>
                            <em>扫描二维码安装到手机</em>
                        </li>
                        <li>
                            <a href="http://bcs.duapp.com/ihoms2bucket/HOMS客户端/HOMS_android正式.apk"><img src="${ctx }/static/images/help/down_02.gif"></a>
                            <img src="${ctx }/static/images/help/hdownload-android2.gif" width="170" height='170'>
                            <em>扫描二维码安装到手机</em>
                        </li>
                    </ul>
                </div>
                <div class="hp_sf_link">
                    <h6>温馨提示：</h6>
                    <p>您也可以到指定交易软件官网下载：<a href="http://www.ihoms.com/www/download/download.do?highlight=1&modelName=1" target="_blank">http://www.ihoms.com/www/download/download.do?highlight=1&modelName=1</a></p>
                </div>
            </div>
        </div>
        <div class="hp_mainbar hp_mbox5" style="display:none;">
           <h1>涌金版下载</h1>
            <div class="hp_sf_down">
                <div class="hp_sf_pcdown">
                    <h6>电脑版下载</h6>
                    <p class="hp_sf_pcdownpic"><a href="static/apply/HOMS_YONGJINBAN_jiaoyiduan.exe">涌金版</a></p>
                    <p class="hp_sf_pcdownfont">
                        <span>该版本是委托下单系统，包含行情功能。</span>
                        <span>包含功能如下：</span>
                        <span>1、支持股票交易相关买入、卖出、信息查询等所有功能；</span>
                        <span>2、支持股票交易特色功能；</span>
                        <span>双向买卖功能（对买对卖功能）</span>
                        <span>批量买入、批量卖出</span>
                        <span>埋单交易（定时埋单，价格埋单）</span>
                        <span>组合交易</span>
                        <span>债券回购、债券逆回购功能</span>
                        <span>支持多账户交易、当账户交易功能</span>
                    </p>
                </div>  
                <div class="hp_sf_teldown">                    
                    <h6>手机端下载</h6>
                    <ul>
                         <li>
                            <a href="https://appsto.re/cn/Xv6P4.i"><img src="${ctx }/static/images/help/down_03.gif"></a>
                            <img src="${ctx }/static/images/help/yjb_iphone.png" width="170" height='170'>
                            <em>扫描二维码安装到手机</em>
                        </li>
                        <li>
                            <a href="http://apps.wandoujia.com/apps/com.hundsun.homs.yj/download"><img src="${ctx }/static/images/help/down_02.gif"></a>
                            <img src="${ctx }/static/images/help/yjb_and.png" width="170" height='170'>
                            <em>扫描二维码安装到手机</em>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
        <div class="hp_mainbar hp_mbox6" style="display:none;">
            <h1>同花顺下载</h1>
            <div class="hp_sf_down">
                <div class="hp_sf_pcdown">
                    <h6>电脑版下载</h6>
                    <p class="hp_sf_pcdownpic"><a href="http://download.10jqka.com.cn/index/download/id/84/">同花顺</a></p>
                    <p class="hp_sf_pcdownfont">
                       市场上行情交易最快、数据最全、性能最优、最受股民欢迎的免费炒股软件：具有沪深两市股票(免费主力增仓、资金流向）、基金行情；在线股票买卖委托交易功能等强大功能。
                    </p>
                </div>  
                <div class="hp_sf_teldown">                    
                    <h6>手机端下载</h6>
                    <ul>
                         <li>
                            <a href="http://wapyd.hexin.cn/download_web/ths_am_iphone_V8_70_02.ipa"><img src="${ctx }/static/images/help/down_03.gif"></a>
                            <img src="${ctx }/static/images/help/ip2.jpg" width="170" height='170'>
                            <em>扫描二维码安装到手机</em>
                        </li>
                        <li>
                            <a href="http://zjcmpp.hexin.com.cn/download/mobile/ths_android_V8_92_01.apk"><img src="${ctx }/static/images/help/down_02.gif"></a>
                            <img src="${ctx }/static/images/help/and2.jpg" width="170" height='170'>
                            <em>扫描二维码安装到手机</em>
                        </li>
                    </ul>
                </div>
            </div>
           	<div class="hp_sf_link">
                <h6>温馨提示：</h6>
                <p>电脑端您也可以到指定交易软件官网下载：<a href="http://activity.ths123.com/html/free/150323" target="_blank">http://activity.ths123.com/html/free/150323</a></p>
                <p>手机端您也可以到指定交易软件官网下载：<a href="http://mobile.10jqka.com.cn" target="_blank">http://mobile.10jqka.com.cn</a></p>
            </div>
        </div>
        <div class="hp_mainbar hp_mbox1" style="display:none;">
            <h1 style="border-bottom:1px solid #d1d1d1;">钱隆TTS股票交易系统<span style="font-size:16px; color:#f00; padding-left:10px;">(请A股操盘和港股操盘的用户下载此软件！)</span></h1>    
            <div class="hp_tts">               
                <h6 style="font-weight:bold;color:#f00; margin-bottom:10px;">win XP用户安装时需注意</h6>   
                <p style="margin-bottom:40px; text-indent:0;">1，先下载并安装“<a href="http://update.tzdr.com/Future/download/.netFramework3.5.exe">.NET Framework 3.5</a>”；<br>2，再下载并安装“<a href="http://update.tzdr.com/Future/download/%E9%92%B1%E9%9A%86TTS.exe">钱隆TTS股票交易系统</a>”，然后启动程序。<br>若程序仍无法运行，请致电客服400-633-9257。</p>  
                <h6 style="font-weight:bold;color:#f00; margin-bottom:10px;">win 7用户安装时需注意</h6>  
                <p style="margin-bottom:40px; text-indent:0;">1，直接下载安装“<a href="http://update.tzdr.com/Future/download/%E9%92%B1%E9%9A%86TTS.exe">钱隆TTS股票交易系统</a>”，然后启动程序；<br>	2，若程序无法运行，请补充下载并安装“<a href="http://update.tzdr.com/Future/download/.netFramework3.5.exe">.NET Framework 3.5</a>”，再运行TTS系统。<br>若程序仍无法运行，请致电客服400-020-0158。</p> 
                <h6 style="font-weight:bold;color:#f00; margin-bottom:10px;">win 8用户安装时需注意</h6>      
                <p style="margin-bottom:40px; text-indent:0;">1，先下载并安装“<a href="http://update.tzdr.com/Future/download/vcredist_x86.zip">VC2005运行库</a>”；<br>2，再下载并安装“<a href="http://update.tzdr.com/Future/download/%E9%92%B1%E9%9A%86TTS.exe">钱隆TTS股票交易系统</a>”，然后启动程序。<br>若程序仍无法运行，请致电客服400-633-9257。</p>            
            </div>
       			
                <ul class="hp_tts_down">
                    <li>
                    	<a href="javascript:void(0);" style="cursor:text;">钱隆TTS介绍</a>
                    	<p>钱隆TTS资管之家客户端，是资管专用的投资交易委托下单系统， 支持股票交易相关买入、卖出、信息查询等功能，具有交易灵活可靠、交易速度快、 系统稳定等特点。</p>	
                    </li>
                    <li>
                        <a href="javascript:void(0);" style="cursor:text;">.Net Framework3.5介绍</a>
                        <p>.Net Framework3.5是支持和运行应用程序内部Windows的组件。运行钱隆TTS交易软件，电脑操作系统必须已安装.Net Framework3.5。</p>
                    </li>
                    <li>
                        <a href="javascript:void(0);" style="cursor:text;">VC2005运行库介绍</a>
                        <p>运行库是一个经过封装的程序模块，如果不使用运行库，操作系统在运行程序时找不到对应的运行库程序就无法运行。运行钱隆TTS交易软件，电脑系统必须已经安装VC运行库。</p>
                    </li>
                </ul>    
        </div>
        <div class="hp_mainbar hp_mbox7" style="display:none;">
            <h1>博易大师</h1>
            <p>博易大师是国内主流的期货、证券及外汇行情显示软件，支持国内、国际期货、金融指数、外汇、期权仿真等市场的实时行情及图表显示，支持24小时全球品种看盘需求。</p>
            <a href="${ctx}/topic/byds/byds.exe"><img src="${ctx}/static/images/help/by.png"></a>
            <p><a href="http://www.pobo.net.cn/helponline5/byds/mainindex.htm" target="_blank">点击查看使用说明</a> </p>
            <h1>博易大师登录说明</h1>
            <h3>1. 双击博易大师，可以弹出登陆界面。安装包自带了账户和密码，可以直接点击登录。</h3>
            <img src="${ctx}/static/images/help/by_01.jpg" width="706">
            <h3>2. 点击客户端上方的交易按键。点击博易大师交易（或直接点击键盘F12），弹出交易账户登录界面。</h3>
            <img src="${ctx}/static/images/help/by_02.png" width="706">
            <h3>3. 输入客户号和交易密码，即可登录进行交易。注：需要选择或手动输入期货产品（如IF1508），才能进行买入/卖出。</h3>
            <img src="${ctx}/static/images/help/by_03.png" width="706">
        </div>        
        <div class="hp_mainbar hp_mbox8" style="display:none;">
            <h1 style="border-bottom:1px solid #d1d1d1;">钱隆TTS期货交易系统<span style="font-size:16px; color:#f00; padding-left:10px;">请富时A50操盘的用户下载此软件！</span></h1>   
            <div class="hp_tts">               
                <h6 style="font-weight:bold;color:#f00; margin-bottom:10px;">win XP用户安装时需注意</h6>   
                <p style="margin-bottom:40px; text-indent:0;">1，先下载并安装“<a href="http://update.tzdr.com/Future/download/Microsoft.NET.exe">.NET Framework 4.0</a>”；<br>2，再下载并安装“<a href="http://update.tzdr.com/Future/download/钱隆TTS期货交易系统.zip">钱隆TTS期货交易系统</a>”，然后启动程序。<br>若程序仍无法运行，请致电客服400-020-0158。</p>  
                <h6 style="font-weight:bold;color:#f00; margin-bottom:10px;">win 7用户安装时需注意</h6>  
                <p style="margin-bottom:40px; text-indent:0;">1，先下载并安装“<a href="http://update.tzdr.com/Future/download/Microsoft.NET.exe">.NET Framework 4.0</a>”；<br>2，再下载并安装“<a href="http://update.tzdr.com/Future/download/钱隆TTS期货交易系统.zip">钱隆TTS期货交易系统</a>”，然后启动程序。<br>若程序仍无法运行，请致电客服400-633-9257。</p>
                <h6 style="font-weight:bold;color:#f00; margin-bottom:10px;">win 8/8.1、win10用户安装时需注意</h6>      
                <p style="margin-bottom:40px; text-indent:0;">1，先下载并安装“<a href="http://update.tzdr.com/Future/download/钱隆TTS期货交易系统.zip">钱隆TTS期货交易系统</a>”，安装时有风险预警，请继续选择安装；<br>2，若启动无反应或报错，请修改兼容性，修改步骤如下图：<br><img src="${ctx}/static/images/help/jr_01.gif" style="height:407px;margin-right: 20px;"><img src="${ctx}/static/images/help/jr_02.gif" style="width:320px;"><br>若程序仍无法运行，请致电客服400-633-9257。</p>            
            </div>            
            <h1>钱隆TTS期货交易系统使用说明</h1>
            <h3 style="line-height:24px; margin:10px 0 15px; color:#333;">1.打开交易软件，输入交易账号和交易密码。交易账号或交易密码输入错误，请关闭交易软件，重新打开登录。</h3>
            <img src="${ctx}/static/images/help/tts_01.png">
            <h3 style="line-height:24px; margin:10px 0 15px; color:#333;">2.选择交易市场及品种，国际原油：cms—原油，恒指期货：HKEX—恒指，富时A50：SGXQ—恒生指数。<br><span style="color:#f00;">注：行情信息属于公共信息，当行情波动大时，各个行情软件都可能会出现不能及时刷新等问题，具体成交点位，以交易软件成交为准。</span></h3>
            <img src="${ctx}/static/images/help/tts_02.gif" width="700">
            <h3 style="line-height:24px; margin:10px 0 15px; color:#333;">3.在操作栏，点击买/卖框，选择买入或卖出，选择订单类型，填写委托价格和委托数量，点击买入或卖出。<span style="color:#f00;">注：恒生指数订单类型只能使用“限价”。</span></h3>
            <img src="${ctx}/static/images/help/tts_03.gif" width="700">
            <h3 style="line-height:24px; margin:10px 0 15px; color:#333;">4.下单不成功，状态栏显示“已请求”，说明单子没有下进交易所，请及时联系客服400-633-9257取消订单。</h3>	
            <h3 style="line-height:24px; margin:10px 0 15px; color:#333;">5.若有持仓，在操作栏进行反向开仓，会对之前持仓进行平仓。如：已有买入持仓，选择卖出，即可平仓。</h3>	
            <h3 style="line-height:24px; margin:10px 0 15px; color:#333;">6.详细使用说明，见软件包“钱隆TTS期货交易系统使用说明.pdf”和“【操作必读】钱隆TTS期货交易系统注意事项.doc”。</h3>	
        </div>        
        <div class="hp_mainbar hp_mbox9" style="display:none;">
            <h1 style="border-bottom:1px solid #d1d1d1;">先融期货-鑫管家博易大师<span style="font-size:16px; color:#f00; padding-left:10px;">请商品期货（沪金、沪银、沪铜、橡胶）操盘的用户下载此软件！ </span></h1>
            <a href="http://update.tzdr.com/Future/download/boyidashi.exe"><img src="${ctx}/static/images/help/byds.gif" style="display:block; margin:30px auto 10px;" width="216" height="66"></a>
            <h1>博易大师说明</h1>
            <h3 style="line-height:24px; margin:10px 0 15px; color:#333;">1，启动博易大师软件，弹出登录界面，填写一个公用的软件登录账号和密码。登录账号：10018019，登录密码：210926。</h3>
            <img src="${ctx}/static/images/help/by_01.jpg" width="606">
            <h3 style="line-height:24px; margin:10px 0 15px; color:#333;">2，请注意查看软甲最顶部的菜单栏，点击『交易』按钮，或者直接按键盘上的F12键，系统会弹出交易账户登录界面。</h3>
            <img src="${ctx}/static/images/help/by_02.png" width="606">
            <h3 style="line-height:24px; margin:10px 0 15px; color:#333;">3，在交易账户登录界面，使用我们分配给您的交易账号和密码进行登录。</h3>
            <img src="${ctx}/static/images/help/by_03.png" width="606">
            <h3 style="line-height:24px; margin:10px 0 15px; color:#333;">4，在交易软件的最左边，请选择『商品期货』标签，然后在黑色主界面选择商品期货（沪金、沪银、沪铜、橡胶）对应的主力合约。<br><span style="color:#f00;">注：行情信息属于公共信息，当行情波动大时，各个行情软件都可能会出现不能及时刷新等问题，具体成交点位，以交易软件成交为准。</span></h3>
            <img src="${ctx}/static/images/help/pro_04.gif" width="606">
            <h3 style="line-height:24px; margin:10px 0 15px; color:#333;">5，在软件左下方的下单区域，进行『买入』或『卖出』，开始正式的商品期货操盘。</h3>
            <img src="${ctx}/static/images/help/pro_05.gif" width="606">
        </div>        
        <div class="hp_mainbar hp_mbox10" style="display:none;">
            <h1 style="border-bottom:1px solid #d1d1d1;">极星外盘期货交易系统<span style="font-size:16px; color:#f00; padding-left:10px;">请恒指期货、国际原油操盘的用户下载此软件！</span></h1>
            <a href="http://update.tzdr.com/Future/download/极星期货交易软件.zip"><img src="${ctx}/static/images/help/ys.gif" style="margin: 30px auto 10px; display:block;"></a>           
            <h1>极星外盘期货交易系统使用说明</h1>
            <h3>1. 在登录栏选择交易服务器站点，站点推荐5或者5以后，防止因交易拥堵挂不上单，</h3>
            <img src="${ctx}/static/images/help/ys_05.jpg" width="700">
            <h3>2. 在登录栏，填写交易账号和密码。</h3>
            <img src="${ctx}/static/images/help/ys_01.jpg" width="700">
            <h3>3. 勾选同步登录行情，在弹出框填写登录账号和密码。登录账号为50728，登录密码为315975。</h3>
            <img src="${ctx}/static/images/help/ys_02.jpg" width="700">
            <h3>4. 点击行情版面，输入合约代码，如：富时A50合约代码 “cn”、恒生指数合约代码“hsi”、国际原油的合约代码“cl”，在按键精灵展示栏选择当期主力合约，查看当期主力合约行情。<br><span style="color:#f00;">注：行情信息属于公共信息，当行情波动大时，各个行情软件都可能会出现不能及时刷新等问题，具体成交点位，以交易软件成交为准。</span></h3>
            <img src="${ctx}/static/images/help/ys_03.gif" width="700">
            <h3>5. 在操作栏，点击合约代码详情按钮，在弹出详情框选择当期主力合约(如1509)，点击买/卖框，选择买入或卖出，填写委托价格和委托数量，选择订单类型，点击确定按钮，买入成功。注：<span style="color:#f00;">恒生指数订单类型只能使用“限价”</span></h3> 
            <img src="${ctx}/static/images/help/ys_04.gif" width="700">
            <h3>6. 若有持仓，在操作栏进行反向开仓，会对之前持仓进行平仓。如：已有买入持仓，选择卖出，即可平仓。</h3>  
        </div>   
        <!-- 行情软件下载 -->
        <div class="hp_mainbar hp_mbox11" style="display:none;">
        	<h1 style="border-bottom:1px solid #d1d1d1;">期货行情软件简版下载 </h1>
        	<h3 style="line-height:24px; margin:10px 0 15px; color:#333;">目前支持期货产品：富时A50、恒指期货、国际原油、沪金、沪银、沪铜、橡胶</h3>
		    <img src="static/images/home-new/download_01.gif" width="707">
		    <div class="hd_font">
		        <p style="text-indent:0;">更新日期：2015-10-13<br />文件大小：3.40MB<br />操作系统：操作系统：32/64位  WinXP/Vista/Win7/Win8</p>
		        <%
		       		if(request.getSession().getAttribute("userName")!=null){
		       	%>
		       	<div class="hd_fontbtn hd_nofontbtn"><a href="http://update.tzdr.com/Future/download/易盛五档行情工具.zip" style="background: #F60 none repeat scroll 0% 0%;"><em>立即下载</em></a></div>
		       	<%
		       		}else{
		       	%>
		       	<div class="hd_fontbtn hd_nofontbtn"><a href="javascript: void(0);"><em>立即下载</em></a></div>
		       	<span>目前仅提供网站会员下载，请先<a href="user/account">登录</a>或<a href="signin">注册</a></span>
		       	<%
		       		}
		       	%>
		    </div>
        </div>  
        <!-- 
        <div class="hp_mainbar hp_mbox5" style="display:none;">
            <p>交易指令通过交易软件直接到券商席位下单，和直接在券商开户炒股完全一样；并受合作券商、沪深交易所和证监会三重监管。客户挂单真实可见，每一笔交易都可以在开通了Level2”功能的大智慧、同花顺等股票行情软件中查询。</p>
        </div>
        <div class="hp_mainbar hp_mbox6" style="display:none;">
            <h2>为了保护操盘资金的安全，同时帮您养成良好的投资习惯，交易账户会设置亏损警戒线和亏损平仓线。</h2>
            <p>亏损警戒线：当总操盘资金低于警戒线以下时，只能平仓不能建仓，需要尽快补充风险保证金，以免低于亏损平仓线被平仓。</p>
            <p>亏损平仓线：当总操盘资金低于平仓线以下时，我们将有权把您的股票进行平仓，为避免平仓发生，请时刻关注风险保证金是否充足。</p>
        </div>
         -->
        <div class="hp_mainbar hp_mbox12" style="display:none;">
        	<h1 style="border-bottom:1px solid #d1d1d1;">股票掌上交易系统</h1>
        	<h1>一、如何进入掌上交易</h1>
        	<h4>1、关注微信（推荐）</h4>
        	<h3>第一步：在微信中搜索投资达人或shxhtzdr，关注投资达人微信公众号，或扫描下图二维码直接关注。</h3>
        	<img src="${ctx}/static/images/help/wx_tzdr.jpg" widht="280">
        	<h3>第二步：点击投资达人微信菜单“我要操盘”-->“掌上交易”</h3>
        	<img src="${ctx}/static/images/help/wx_01.gif">        	
        	<h4>2、或直接扫描二维码进入掌上交易</h4>
        	<img src="${ctx}/static/images/help/wx.png">   	
        	<h4>3、在移动端直接输入网址：<a href="http://wxjy.peigubao.com/MTrader/logonentry.php" target="_blank">http://dwz.cn/tzdrwsjy</a></h4>
        	<h1>二、如何交易</h1>
        	<h3>1、登录：输入操盘账号和密码，点击登录即可。如您已申请操盘账号，请在“我的账户”中查看。如您未申请操盘，可在网站申请并领取账号、密码后登录。</h3>        	
        	<img src="${ctx}/static/images/help/wx_02.gif"> 
        	<h3>2、交易：如图所示“买入”即为买入股票操作；“卖出”即为卖出股票操作；撤单查询即为撤单操作。</h3>        	
        	<img src="${ctx}/static/images/help/wx_03.gif"> 
        	<h3>3、查询：如图所示“查询”可查看资金、股票持仓、当日委托和当日成交等数据。</h3>
        	<img src="${ctx}/static/images/help/wx_04.gif"> 
        	<h3>如在使用过程中有任何疑问，欢迎致电客服电话或咨询客服QQ。</h3>
        </div>  
    </div>
    <!--新手指南  -->
    <div class=" hp_ctn hp_guide newbie hp_content" style="display: none">
        <div class="hp_siderbar newbie_siderbar ">
            <a href="javascript:void(0)" class="on" data="1">注册</a>
            <a href="javascript:void(0)" data="2">登录</a>
            <a href="javascript:void(0)" data="3">实名</a>
            <a href="javascript:void(0)" data="4">充值</a>
            <a href="javascript:void(0)" data="5">提现</a>
            <!-- <a href="javascript:void(0)" data="6">加入实战直播间</a> -->
        </div>
        <div class="hp_mainbar hp_mbox1" >
        	<h2>如何注册成为投资达人用户？</h2>
        	<p>第一步：打开投资达人首页(www.tzdr.com)，点击首页头部“注册”，跳转注册页面；</p>
        	<img src="${ctx}/static/images/help/rg_01.jpg" width="600" style="margin-left:30px;">
        	<p>第二步：填写手机号码、手机验证码、密码、确认密码、推广码等信息；</p>
        	<img src="${ctx}/static/images/help/rg_02.png" width="600" style="margin-left:30px;">
        	<p>第三步：提交信息，注册成功。</p>
        	<img src="${ctx}/static/images/help/rg_03.jpg" width="600" style="margin-left:30px;">
        </div>
        <div class="hp_mainbar hp_mbox2" style="display:none;">
        	<h2>如何登录投资达人网站？</h2>
        	<p>方法一：在首页顶登录框，填写手机号和密码，点击登录；</p>
        	<img src="${ctx}/static/images/help/lg_01.jpg" width="600" style="margin-left:30px;">
        	<p>方法二：</p>
        	<p>1.在首页顶部点击登录，跳转登录页；</p>
        	<img src="${ctx}/static/images/help/lg_02.jpg" width="600" style="margin-left:30px;">
        	<p>2.填写手机号码、密码，点击登录；</p>
        	<img src="${ctx}/static/images/help/lg_03.jpg" width="600" style="margin-left:30px;">
        </div>
        <div class="hp_mainbar hp_mbox3" style="display:none;">
        	<h2>如何完成实名认证？</h2>
        	<p>第一步，进入个人中心—安全信息，实名认证模块，点击立即认证；</p>
        	<img src="${ctx}/static/images/help/name_01.png" width="600" style="margin-left:30px;">
        	<p>第二步，填写真实实名、身份证号码提交，认证成功</p>
        	<img src="${ctx}/static/images/help/name_02.png" width="600" style="margin-left:30px;">
        </div>
        <div class="hp_mainbar hp_mbox4" style="display:none;">
        	<h2>如何充值？</h2>
        	<p>第一步，进入个人中心—账户充值，选择充值方式；</p>
        	<img src="${ctx}/static/images/help/charge_01.jpg" width="600" style="margin-left:30px;">
        	<p>第二步，填写充值金额和相关资料，提交；</p>
        	<img src="${ctx}/static/images/help/charge_02.png" width="600" style="margin-left:30px;">
        	<p>第三步，完成支付，等待转账。</p>        	
        </div>
        <div class="hp_mainbar hp_mbox5" style="display:none;">
        	<h2>如何提现？</h2>
        	<p>第一步，进入个人中心—我要提现，选择银行卡管理；添加银行卡，选择开户银行，填写银行卡号和确认卡号，点击保存；</p>
        	<img src="${ctx}/static/images/help/wd_01.jpg" width="600" style="margin-left:30px;">
        	<p>第二步，设置提现密码：</p>
        	<p>a.进入个人中心—安全信息页面，在提块密码模块点击“立即设置”；</p>
        	<img src="${ctx}/static/images/help/wd_02.jpg" width="600" style="margin-left:30px;">
        	<p>b.填写提现密码和确认提现密码。</p>
        	<img src="${ctx}/static/images/help/wd_03.gif" style="margin-left:30px;">
        	<p>第三步，选择我要提现选项卡，填写提现金额、提现银行、提现密码，点击下一步；</p>
        	<img src="${ctx}/static/images/help/wd_04.gif" width="600" style="margin-left:30px;">
       		<p>第四步，等待提现到账。</p>
       </div>       
       <div class="hp_mainbar hp_mbox6" >
       		<h2>如何加入实战直播间？</h2>       		
        	<p>第一步：下载安装腾讯QT语言软件<a href="http://dldir1.qq.com/qt/QT4.5.44.15806.exe">（http://dldir1.qq.com/qt/QT4.5.44.15806.exe）</a>;</p>       		
       		<p>第二步：登陆QQ账号和QQ密码。进入软件界面；</p>
       		<img src="${ctx}/static/images/help/qt_01.png" style="margin-left:30px;">
       		<p>第三步：输入房间号码40139352（投资达人交流俱乐部），点击输入框右侧快速加入；</p>
       		<img src="${ctx}/static/images/help/qt_02.jpg" width="600" style="margin-left:30px;">
       		<p>第四步：进入房间以后，如下图所示加入投资达人A50实战；</p>
       		<img src="${ctx}/static/images/help/qt_03.png" width="600" style="margin-left:30px;">
       </div>
    </div>
</div>
<!--底部 -->
<%@ include file="../common/personfooter.jsp"%>
<script type="text/javascript">
	var tab = '${tab}';
	var leftMenu = '${leftMenu}';
	if($.trim(tab) != null && $.trim(tab).length > 0 && $("."+tab) != null && $("."+tab).length > 0){
		$('.helpTitle').each(function() {
			if($.trim($(this).attr("data-type")) == tab){
				$('.helpTitle').removeClass('on');
				$(this).addClass('on'); 
			}
	    });
		$(".hp_ctn").css({display: "none"});
		$("."+tab).css({display: ""});
		if($.trim(leftMenu) != null && $.trim(leftMenu).length > 0){
			$("."+tab).find("div").find("a").each(function() {
				if($.trim($(this).attr("data")) == leftMenu){
					$("."+tab).find("div").find("a").removeClass('on');
					$(this).addClass('on'); 
					$("."+tab).find('.hp_mainbar').hide();
					$("."+tab).find('.hp_mbox'+leftMenu).show();
				}
			});
		}
	}
</script>
<%@ include file="../common/dsp.jsp"%>
</body>
</html>