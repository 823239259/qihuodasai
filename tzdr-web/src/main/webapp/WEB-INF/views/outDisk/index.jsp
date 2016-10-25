<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="../common/common.jsp"%>
<%@include file="../common/import-artDialog-js.jspf"%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="zh">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="${ctx}/static/css/public.css?v=${v}" rel="stylesheet" type="text/css">
<link href="${ctx}/static/css/trade.css?v=${v}" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/capital.css?v=${v}">
<script language="javascript" src="${ctx}/static/script/tzdr.js?v=${v}"></script>

<script type="text/javascript" src="${ctx}/static/script/outDisk/index.js?v=${v}"></script>
<script type="text/javascript" src="${ctx}/static/script/ftse/ftseCommon.js?v=${v}"></script>
<title>国际综合 - 维胜金融-中国领先的国际期货及衍生品互联网交易平台</title>
<meta name="keywords" content="维胜，国际期货，期货，投资达人，金勺子，高盛，都城，南华期货，配资，期货配资，期货开户，外盘，富时A50，国际原油，恒指期货，期货公司，期货平台，炒期货，模拟盘，赚钱，头寸，持仓，成都盈透科技有限公司"/>
<meta name="description" content="维胜（www.vs.com）-致力于成为中国领先的国际期货及衍生品互联网交易平台，提供恒指期货、国际原油、富时A50等主流国际期货产品，开户操盘快捷方便，交易费用全网最低。"/>
<style>
	#guojizonghe {color: #ffcc33; border-bottom:2px solid #ffcc33; padding-bottom: 26px;}
</style>
</head>
<body>
<!--顶部 -->
	<%@include file="../common/header.jsp"%>
	<div class="capital">
	<form action="${ctx}/userOutDisk/pay" id="settingForm" method="post">
	<input type="hidden" id="traderBondAttr" name="traderBondAttr" value="${outDiskParameters[0].traderBond}" >
    <div class="mc_nav">
        <a href="${ctx}/hsi/index">恒指期货</a>
		<a href="${ctx}/crudeoil/index">国际原油</a>
		<a href="${ctx}/ftse/index">富时A50</a>
        <a href="${ctx}/outDisk/index" class="on">国际综合<i></i></a>
    </div>
    <div class="capital_ctn cpx_ctn">
        <div class="cpx_main">
            <h2 class="cpx_title cpx_t_icon1"><b>操盘保证金：</b><i>(可操盘15种期货产品，操盘保证金越多，可持仓手数越多)</i></h2>
            <ul class="cplx_mianlist">
            <c:forEach items="${outDiskParameters}" var="outDiskParameters" varStatus="status">
              		<c:choose>
              			<c:when test="${status.first==true}">
              				<li class="on" data='${outDiskParameters.traderBond}'><fmt:formatNumber value="${outDiskParameters.traderBond}"  type="number" maxFractionDigits="2"  ></fmt:formatNumber>元</li>
                		</c:when>
                			<c:otherwise>
                			<li class data='${outDiskParameters.traderBond}'><fmt:formatNumber value="${outDiskParameters.traderBond}"  type="number" maxFractionDigits="2"  ></fmt:formatNumber>元</li>
               			</c:otherwise>
               		</c:choose>
            </c:forEach>
            </ul>           
            <h2 class="cpx_title cpx_t_icon2"><b>账户规则：</b></h2>
            <ul class="cpx_m_rule">
                <li>
                    <h3>操盘保证金(￥)</h3>
                    <span><i id='traderBond'><fmt:formatNumber value="${outDiskParameters[0].traderBond}"  type="number" maxFractionDigits="2"  ></fmt:formatNumber></i>元</span>
                </li>
                <li>
                    <h3>总操盘资金($)</h3>
                    <span><i id='traderTotal'><fmt:formatNumber value="${outDiskParameters[0].traderTotal}"  type="number" maxFractionDigits="2"  ></fmt:formatNumber></i>美元</span>
                </li>
                <li>
                    <h3>亏损平仓线($)</h3>
                    <span><i id='lineLoss'><fmt:formatNumber value="${outDiskParameters[0].lineLoss}"  type="number" maxFractionDigits="2"  ></fmt:formatNumber></i>美元</span>
                </li>
                <li>
                    <h3>账户管理费(￥)</h3>
                    <span><i>免费</i></span>
                </li>
            </ul>
            <h2 class="cpx_title cpx_t_icon3"><b>交易规则：</b><i>(一个账号可同时交易15种期货产品)<c style="font-size: 14px; color: #666;">（注意：请不要在交易时间外持单，以免被系统强制平仓）</c></i></h2>
            <table border="0" cellspacing="0" cellpadding="0" class="cpx_ru_list">
                <thead>
                    <td width="11%">期货产品</td>
                    <td width="11%">主力合约</td>
                    <td width="33%">维胜交易时间段</td>
                    <td wdith="32%">初始可持仓手数</td>
                    <td width="13%">交易手续费(￥)</td>
                </thead>
                <tr>
                    <td>富时A50</td>
                    <td>${outDiskPrice[0].mainContract}</td>
                    <td>${outDiskPrice[0].tradTime}</td>
                    <td>只交易富时A50时，初始最大可持仓<i id='ATranActualLever'>${outDiskParameters[0].atranActualLever}</i>手</td>
                    <td><i>${outDiskPrice[0].price}</i>元/手</td>
                </tr>
                <tr>
                    <td>恒指期货</td>
                    <td>${outDiskPrice[2].mainContract}</td>
                    <td>${outDiskPrice[2].tradTime}</td>
                    <td>只交易恒指期货时，初始最大可持仓<i id='HTranActualLever'>${outDiskParameters[0].htranActualLever}</i>手</td>
                    <td><i>${outDiskPrice[2].price}</i>元/手</td>
                </tr>
                <tr>
                    <td>国际原油</td>
                    <td>${outDiskPrice[1].mainContract}</td>
                    <td>${outDiskPrice[1].tradTime}</td>
                    <td>只交易国际原油时，初始最大可持仓<i id='YTranActualLever'>${outDiskParameters[0].ytranActualLever}</i>手</td>
                    <td><i>${outDiskPrice[1].price}</i>元/手</td>
                </tr>
                <tr>
                    <td>迷你道琼</td>
                    <td>${outDiskPrice[3].mainContract}</td>
                    <td>${outDiskPrice[3].tradTime}</td>
                    <td>只交易迷你道琼时，初始最大可持仓<i id='mdtranActualLever'>${outDiskParameters[0].mdtranActualLever}</i>手</td>
                    <td><i>${outDiskPrice[3].price}</i>元/手</td>
                </tr>
                
                <tr>
                    <td>迷你纳斯达克</td>
                    <td>${outDiskPrice[4].mainContract}</td>
                    <td>${outDiskPrice[4].tradTime}</td>
                    <td>只交易迷你纳斯达克时，初始最大可持仓<i id='mntranActualLever'>${outDiskParameters[0].mntranActualLever}</i>手</td>
                    <td><i>${outDiskPrice[4].price}</i>元/手</td>
                </tr>
                
                <tr>
                    <td>迷你标准普尔</td>
                    <td>${outDiskPrice[5].mainContract}</td>
                    <td>${outDiskPrice[5].tradTime}</td>
                    <td>只交易迷你标准普尔时，初始最大可持仓<i id='mbtranActualLever'>${outDiskParameters[0].mbtranActualLever}</i>手</td>
                    <td><i>${outDiskPrice[5].price}</i>元/手</td>
                </tr>
                
                <tr>
                    <td>德国DAX</td>
                    <td>${outDiskPrice[6].mainContract}</td>
                    <td>${outDiskPrice[6].tradTime}</td>
                    <td>只交易德国DAX时，初始最大可持仓<i id='daxtranActualLever'>${outDiskParameters[0].daxtranActualLever}</i>手</td>
                    <td><i>${outDiskPrice[6].price}</i>元/手</td>
                </tr>
                
                <tr>
                    <td>日经225</td>
                    <td>${outDiskPrice[7].mainContract}</td>
                    <td>${outDiskPrice[7].tradTime}</td>
                    <td>只交易日经225时，初始最大可持仓<i id='nikkeiTranActualLever'>${outDiskParameters[0].nikkeiTranActualLever}</i>手</td>
                    <td><i>${outDiskPrice[7].price}</i>元/手</td>
                </tr>
                
                <tr>
                    <td>小恒指</td>
                    <td>${outDiskPrice[8].mainContract}</td>
                    <td>${outDiskPrice[8].tradTime}</td>
                    <td>只交易小恒指时，初始最大可持仓<i id='hstranActualLever'>${outDiskParameters[0].hstranActualLever}</i>手</td>
                    <td><i>${outDiskPrice[8].price}</i>元/手</td>
                </tr>
                
                <tr>
                    <td>美黄金</td>
                    <td>${outDiskPrice[9].mainContract}</td>
                    <td>${outDiskPrice[9].tradTime}</td>
                    <td>只交易美黄金时，初始最大可持仓<i id='agtranActualLever'>${outDiskParameters[0].agtranActualLever}</i>手</td>
                    <td><i>${outDiskPrice[9].price}</i>元/手</td>
                </tr>
                
                <tr>
                    <td>H股指数</td>
                    <td>${outDiskPrice[10].mainContract}</td>
                    <td>${outDiskPrice[10].tradTime}</td>
                    <td>只交易H股指数时，初始最大可持仓<i id='hIndexActualLever'>${outDiskParameters[0].hIndexActualLever}</i>手</td>
                    <td><i>${outDiskPrice[10].price}</i>元/手</td>
                </tr>
                
                <tr>
                    <td>小H股指数</td>
                    <td>${outDiskPrice[11].mainContract}</td>
                    <td>${outDiskPrice[11].tradTime}</td>
                    <td>只交易小H股指数时，初始最大可持仓<i id='xhIndexActualLever'>${outDiskParameters[0].xhIndexActualLever}</i>手</td>
                    <td><i>${outDiskPrice[11].price}</i>元/手</td>
                </tr>
                
                <tr>
                    <td>美铜</td>
                    <td>${outDiskPrice[12].mainContract}</td>
                    <td>${outDiskPrice[12].tradTime}</td>
                    <td>只交易美铜时，初始最大可持仓<i id='aCopperActualLever'>${outDiskParameters[0].aCopperActualLever}</i>手</td>
                    <td><i>${outDiskPrice[12].price}</i>元/手</td>
                </tr>
                
                <tr>
                    <td>美白银</td>
                    <td>${outDiskPrice[13].mainContract}</td>
                    <td>${outDiskPrice[13].tradTime}</td>
                    <td>只交美白银时，初始最大可持仓<i id='aSilverActualLever'>${outDiskParameters[0].aSilverActualLever}</i>手</td>
                    <td><i>${outDiskPrice[13].price}</i>元/手</td>
                </tr>
                
                <tr>
                    <td>小原油</td>
                    <td>${outDiskPrice[14].mainContract}</td>
                    <td>${outDiskPrice[14].tradTime}</td>
                    <td>只交小原油时，初始最大可持仓<i id='smaActualLever'>${outDiskParameters[0].smaActualLever}</i>手</td>
                    <td><i>${outDiskPrice[14].price}</i>元/手</td>
                </tr>
                
                 <tr>
                    <td>迷你德国DAX指数</td>
                    <td>${outDiskPrice[15].mainContract}</td>
                    <td>${outDiskPrice[15].tradTime}</td>
                    <td>只交迷你德国DAX指数时，初始最大可持仓<i id='daxtranMinActualLever'>${outDiskParameters[0].daxtranMinActualLever}</i>手</td>
                    <td><i>${outDiskPrice[15].price}</i>元/手</td>
                </tr>
                
                <tr>
                	<td colspan="5">在各品种停止交易的5分钟内，即<i style="font-size:14px;">${transTime}</i>六个时段所有品种只能平仓，不能开仓。</td>
                </tr>
            </table>
        </div>  
        <div class="cp_bom">
            <p><a href="${ctx}/help?tab=rule&leftMenu=5" style="color:#fc3;" target="_blank">查看国际期货操盘细则,</a>若还有疑问，请联系客服：400-852-8008</p>
            <div class="cp_b_link">
                <input type="checkbox" checked="checked" id="checkbox_agree"ame="agree"> 
                <span>我已阅读并同意<a href="javascript:lookContract();">《国际期货综合操盘合作协议》</a></span>
            </div>
            <div class="cp_b_btn"><a  href="javascript:void();" onclick="submitSetting()">提交操盘申请</a></div>
        </div>
    </div>
    </form>
</div>
<%@include file="../common/footer.jsp"%>
<%@ include file="../common/dsp.jsp"%>
<script>
!function(w,d,e){
var _orderno='${tzdrUser.id}';
var b=location.href,c=d.referrer,f,s,g=d.cookie,h=g.match(/(^|;)\s*ipycookie=([^;]*)/),i=g.match(/(^|;)\s*ipysession=([^;]*)/);if (w.parent!=w){f=b;b=c;c=f;};u='//stats.ipinyou.com/cvt?a='+e('BJ.ga.SUo2xRgMiNvhrAY2-R67L_')+'&c='+e(h?h[2]:'')+'&s='+e(i?i[2].match(/jump\%3D(\d+)/)[1]:'')+'&u='+e(b)+'&r='+e(c)+'&rd='+(new Date()).getTime()+'&OrderNo='+e(_orderno)+'&e=';
function _(){if(!d.body){setTimeout(_(),100);}else{s= d.createElement('script');s.src = u;d.body.insertBefore(s,d.body.firstChild);}}_();
}(window,document,encodeURIComponent);
</script>
	<script>
!function(w,d,e){
var _orderno='${tzdrUser.id}';
var b=location.href,c=d.referrer,f,s,g=d.cookie,h=g.match(/(^|;)\s*ipycookie=([^;]*)/),i=g.match(/(^|;)\s*ipysession=([^;]*)/);if (w.parent!=w){f=b;b=c;c=f;};u='//stats.ipinyou.com/cvt?a='+e('BJ._F.3ag4T-uuz-3qugl7_8Ab6_')+'&c='+e(h?h[2]:'')+'&s='+e(i?i[2].match(/jump\%3D(\d+)/)[1]:'')+'&u='+e(b)+'&r='+e(c)+'&rd='+(new Date()).getTime()+'&OrderNo='+e(_orderno)+'&e=';
function _(){if(!d.body){setTimeout(_(),100);}else{s= d.createElement('script');s.src = u;d.body.insertBefore(s,d.body.firstChild);}}_();
}(window,document,encodeURIComponent);
</script>

</body>
</html>