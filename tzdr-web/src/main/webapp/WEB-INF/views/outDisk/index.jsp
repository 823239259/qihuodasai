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
<title>国际综合 - 维胜</title>
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
            <h2 class="cpx_title cpx_t_icon1"><b>操盘保证金：</b><i>(可操盘8种期货产品，操盘保证金越多，可持仓手数越多)</i></h2>
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
            <h2 class="cpx_title cpx_t_icon3"><b>交易规则：</b><i>(一个账号可同时交易8种期货产品)</i></h2>
            <table border="0" cellspacing="0" cellpadding="0" class="cpx_ru_list">
                <thead>
                    <td width="11%">期货产品</td>
                    <td width="11%">主力合约</td>
                    <td width="33%">交易时间段</td>
                    <td wdith="32%">初始可持仓手数</td>
                    <td width="13%">交易手续费(￥)</td>
                </thead>
                <tr>
                    <td>富时A50</td>
                    <td>${outDiskPrice[0].mainContract}</td>
                    <td>9:05-15:50，16:45-23:55</td>
                    <td>只交易富时A50时，初始最大可持仓<i id='ATranActualLever'>${outDiskParameters[0].atranActualLever}</i>手</td>
                    <td><i>${outDiskPrice[0].price}</i>元/手</td>
                </tr>
                <tr>
                    <td>恒指期货</td>
                    <td>${outDiskPrice[2].mainContract}</td>
                    <td>9:20-11:55，13:05-16:10，17:05-23:40</td>
                    <td>只交易恒指期货时，初始最大可持仓<i id='HTranActualLever'>${outDiskParameters[0].htranActualLever}</i>手</td>
                    <td><i>${outDiskPrice[2].price}</i>元/手</td>
                </tr>
                <tr>
                    <td>国际原油</td>
                    <td>${outDiskPrice[1].mainContract}</td>
                    <td>9:05-23:55</td>
                    <td>只交易国际原油时，初始最大可持仓<i id='YTranActualLever'>${outDiskParameters[0].ytranActualLever}</i>手</td>
                    <td><i>${outDiskPrice[1].price}</i>元/手</td>
                </tr>
                <tr>
                    <td>迷你道琼</td>
                    <td>${outDiskPrice[3].mainContract}</td>
                    <td>9:05-23:55</td>
                    <td>只交易迷你道琼时，初始最大可持仓<i id='mdtranActualLever'>${outDiskParameters[0].mdtranActualLever}</i>手</td>
                    <td><i>${outDiskPrice[3].price}</i>元/手</td>
                </tr>
                
                <tr>
                    <td>迷你纳斯达克</td>
                    <td>${outDiskPrice[4].mainContract}</td>
                    <td>9:05-23:55</td>
                    <td>只交易迷你纳斯达克时，初始最大可持仓<i id='mntranActualLever'>${outDiskParameters[0].mntranActualLever}</i>手</td>
                    <td><i>${outDiskPrice[4].price}</i>元/手</td>
                </tr>
                
                <tr>
                    <td>迷你标准普尔</td>
                    <td>${outDiskPrice[5].mainContract}</td>
                    <td>9:05-23:55</td>
                    <td>只交易迷你标准普尔时，初始最大可持仓<i id='mbtranActualLever'>${outDiskParameters[0].mbtranActualLever}</i>手</td>
                    <td><i>${outDiskPrice[5].price}</i>元/手</td>
                </tr>
                
                <tr>
                    <td>德国DAX</td>
                    <td>${outDiskPrice[6].mainContract}</td>
                    <td>14:05-23:55</td>
                    <td>只交易德国DAX时，初始最大可持仓<i id='daxtranActualLever'>${outDiskParameters[0].daxtranActualLever}</i>手</td>
                    <td><i>${outDiskPrice[6].price}</i>元/手</td>
                </tr>
                
                <tr>
                    <td>日经225</td>
                    <td>${outDiskPrice[7].mainContract}</td>
                    <td>09:05-14:20，15:20-23:55</td>
                    <td>只交易日经225时，初始最大可持仓<i id='nikkeiTranActualLever'>${outDiskParameters[0].nikkeiTranActualLever}</i>手</td>
                    <td><i>${outDiskPrice[7].price}</i>元/手</td>
                </tr>
                <tr>
                	<td colspan="5">在各品种停止交易的5分钟内，即<i style="font-size:14px;">11:55-12:00, 14:20-14:25, 15:50-15:55, 16:10-16:15, 23:40-23:45</i>五个时段所有品种只能平仓，不能开仓。</td>
                </tr>
            </table>
        </div>  
        <div class="cp_bom">
            <p>如您不清楚规则，或有其他疑问，请联系客服：400-852-8008</p>
            <div class="cp_b_link">
                <input type="checkbox" checked="checked" id="checkbox_agree"ame="agree"> 
                <span>我已阅读并同意<a href="javascript:lookContract();">《国际期货综合操盘合作协议》</a></span>
            </div>
            <div class="cp_b_btn"><a  href="javascript:void();" onclick="submitSetting()">提交操盘申请</a></div>
        </div>
    </div>
    </form>
    
    <!--操盘须知  -->
    <div class="capital_rule cpx_rule">
        <h2 class="cpx_title cpx_t_icon4"><b>操盘规则：</b></h2>     
        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="cp_ru_list2">
            <thead>
                <td width="8%">期货产品</td>
                <td width="10%">上市交易所</td>
                <td>本期主力合约</td>
                <td>最小波动点</td>
                <td>最小波动价</td>
                <td wdith="10%">交易时间</td>
                <td>交易周期</td>
                <td>双持持仓</td>
                <td>涨跌幅限制</td>
            </thead>
            <tr>
                <td>富时A50</td>
                <td>新加坡交易所</td>
                <td>${outDiskPrice[0].mainContract}</td>
                <td>2.5</td>
                <td>2.5美元</td>
                <td class="base">9:05-15:50<br>16:45-23:55<br>不能跨时段持仓</td>
                <td>日内，不能隔夜</td>
                <td>不能双向持仓</td>
                <td class="base">当涨跌幅±10%和±15%时，<br>分别熔断10分钟，<br>之后无涨跌幅限制</td>
            </tr>
            <tr>
                <td>恒指期货</td>
                <td>香港交易所</td>
                <td>${outDiskPrice[2].mainContract}</td>
                <td>1</td>
                <td>50港元</td>
                <td class="base">9:20-11:55<br>13:05-16:10<br>17:05-23:40<br>不能跨时段持仓</td>
                <td>日内，不能隔夜</td>
                <td>不能双向持仓</td>
                <td>无涨跌幅限制</td>
            </tr>
            <tr>
                <td>国际原油</td>
                <td>纽约商业交易所</td>
                <td>${outDiskPrice[1].mainContract}</td>
                <td>0.01</td>
                <td>10美元</td>
                <td>9:05-23:55</td>
                <td>日内，不能隔夜</td>
                <td>不能双向持仓</td>
                <td class="base">±10美元，暂停交易5分钟<br>后再扩大±10美元，<br>以此循环</td>
            </tr>
            <tr>
                <td>迷你道琼</td>
                <td>芝加哥交易所</td>
                <td>${outDiskPrice[3].mainContract}</td>
                <td>1</td>
                <td>5美元</td>
                <td>9:05-23:55</td>
                <td>日内，不能隔夜</td>
                <td>不能双向持仓</td>
                <td class="base">7%,13%,20%(仅跌停)三级熔断</td>
            </tr>
            <tr>
                <td>迷你纳斯达克</td>
                <td>芝加哥交易所</td>
                <td>${outDiskPrice[4].mainContract}</td>
                <td>0.25</td>
                <td>5美元</td>
                <td>9:05-23:55</td>
                <td>日内，不能隔夜</td>
                <td>不能双向持仓</td>
                <td class="base">7%,13%,20%(仅跌停)三级熔断</td>
            </tr>
            <tr>
                <td>迷你标普</td>
                <td>芝加哥交易所</td>
                <td>${outDiskPrice[5].mainContract}</td>
                <td>0.25</td>
                <td>12.5美元</td>
                <td>9:05-23:55</td>
                <td>日内，不能隔夜</td>
                <td>不能双向持仓</td>
                <td class="base">7%,13%,20%(仅跌停)三级熔断</td>
            </tr>
            <tr>
                <td>德国DAX</td>
                <td>欧洲期货交易所</td>
                <td>${outDiskPrice[6].mainContract}</td>
                <td>0.5</td>
                <td>12.5欧元</td>
                <td>14:05-23:55</td>
                <td>日内，不能隔夜</td>
                <td>不能双向持仓</td>
                <td class="base">无</td>
            </tr>
            <tr>
                <td>日经225</td>
                <td>新加坡交易所</td>
                <td>${outDiskPrice[7].mainContract}</td>
                <td>5</td>
                <td>2500日元</td>
                <td class="base">09:05-14:20<Br>15:20-23:55</td>
                <td>日内，不能隔夜</td>
                <td>不能双向持仓</td>
                <td class="base">±7.5%,±12.5%<br>(熔断均为15分钟;即月合约在最后交易日无涨跌停)</td>
            </tr>
        </table>
        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="cp_ru_list">
          <tr>
            <td class="first">操盘保证金</td>
            <td class="font">用户出现亏损时的赔付，结束时如无亏损全额退还，保证金越高，平仓风险越低。</td>
          </tr>
          <tr>
            <td class="first">亏损平仓线</td>
            <td class="font">总操盘资金低于亏损平仓线时，系统自动平仓。</td>
          </tr>
          <tr>
            <td class="first">账户管理费</td>
            <td class="font">按交易日计算，当前免费。</td>
          </tr>
          <tr>
            <td class="first">交易手续费</td>
            <td class="font">用于支付交易佣金、印花税、过户费和实盘资金占用费。</td>
          </tr>
          <tr>
            <td class="first">终结方案结算</td>
            <td class="font">根据您的操盘盈亏，美元兑换人民币，汇率取中国银行当天的第一笔现钞卖出价。</td>
          </tr>
          <tr>
            <td class="first">交易软件</td>
            <td class="font"><a href="${ctx}/help?tab=software&leftMenu=8" style="color:#fc3; padding-right:20px;"  target="_blank">交易软件下载</a><a href="${ctx}/help?tab=rule&leftMenu=5" target="_blank"  style="color:#fc3; padding-right:20px;">查看交易说明</a></td>
          </tr>
        </table>
    </div>
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