<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>
<%@include file="../common/import-artDialog-js.jspf"%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="zh">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="../common/commonkeyword.jsp"%>
<link href="${ctx}/static/css/public.css" rel="stylesheet"
	type="text/css">
<link href="${ctx}/static/css/trade.css" rel="stylesheet"
	type="text/css" />
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/capital.css">
<style type="text/css">
 .font_size_15 {font-size: 15px; color: #f60; }
 .font_size_22 {font-size: 22px; color: #f60; }                       
</style>
<script language="javascript" src="${ctx}/static/script/tzdr.js?version=20150710"></script>
<script language="javascript" src="${ctx}/static/script/trade/tradeAddMore.js?version=20150710"></script>
<script type="text/javascript">
function tradeContract(){
	var htmlHeight = 800;  //网页高度
	var htmlWidth = 1221;  //网页宽度
	var iTop = (window.screen.height-30-htmlHeight)/2; //获得窗口的垂直位置;  
	var iLeft = (window.screen.width-10-htmlWidth)/2;  //获得窗口的水平位置;  
	window.open(basepath+'tradeContract','操盘协议','height='+htmlHeight+',innerHeight='+htmlHeight+',width='+htmlWidth+',innerWidth='+htmlWidth+',top='+iTop+',left='+iLeft+',toolbar=no,menubar=no,scrollbars=auto,resizeable=no,location=no,status=no');  
}
</script>

</head>
<body>

	<!--顶部 -->
	<%@ include file="../common/personheader.jsp"%>

<div class="capital">
	<form method="POST" id="sub"   action="${ctx}/trade/moretrade/${groupId}">
	<input type="hidden" name="type" value="2"/>
	<input type="hidden" name="capitalMargin" id="capitalMargin" value="${capitalMargin}"/>
	<input type="hidden" name="moreMaxMoney" id="moreMaxMoney" value="${moreMaxMoney}"/>
	<input type="hidden" name="backtype" id="backtype" value="addMore"/>
	<input type="hidden" name="groupId" id="groupId" value="${groupId}"/>
	<input type="hidden" name="backTradeStart" id="backTradeStart" value="${backTradeStart}"/>
    <div class="capital_ctn">
        <div class="cp_main">
            <div class="cp_m_ctn">
                <div class="cp_m_titl">
                    <i></i>
                    <h3 class="cp_h1">输入保证金额(100元-600万元)</h3>
                    <input id="tz" class="font_size_22" type="text" value="" autocomplete="off">
                    <em>元</em>
                </div>
                <div class="cp_m_list" id="margin">
                    <h4><i>推荐保证金：</i></h4>
                    <ul>
                        <li class="on"  data="2000">
                            <p><i>2,000</i>元</p>
                            <span>保证金额</span>
                        </li>
                        <li data="10000">
                            <p><i>1万</i>元</p>
                            <span>保证金额</span>
                        </li>
                        <li data="100000">
                            <p><i>10万</i>元</p>
                            <span>保证金额</span>
                        </li>
                        <li data="500000">
                            <p><i>50万</i>元</p>
                            <span>保证金额</span>
                        </li>
                    </ul>

                </div>
            </div>
            <div class="cp_m_ctn">
                <div class="cp_m_titl">
                    <i></i>
                    <h3 class="cp_h2" id="lever_notify">放大倍数(${lever}倍)</h3>                 
                    
                      <input id="pzgg" class="font_size_22" name="lever" type="text" value="${lever}" readonly="readonly" autocomplete="off">
                    <em>倍</em>
                </div>
                <div class="cp_m_list" id="capital_lever">
                    <h4><i>推荐放大倍数：</i></h4>
                    <ul>
                        <li class="on" data="2">
                            <p><i>2</i>倍</p>
                            <span>放大倍数</span>
                        </li>
                        <li data="3"> 
                            <p><i>3</i>倍</p>
                            <span>放大倍数</span>
                        </li>
                        <li data="5">
                            <p><i>5</i>倍</p>
                            <span>放大倍数</span>
                        </li>
                        <li data="8">
                            <p><i>8</i>倍</p>
                            <span>放大倍数</span>
                        </li>
                    </ul>

                </div>
            </div>
            <div class="cp_m_ctn">
                <div class="cp_m_titl">
                    <i></i>
                    <h3 class="cp_h3">操盘天数(<span id="use_day_lable">${borrowPeriod}</span>天)</h3>
                     <input type="text" class="font_size_22" id="use_day"  name="borrowPeriod"  value="${borrowPeriod}" readonly="readonly" autocomplete="off">
                    <em>天</em>
                </div>
                <div class="cp_m_list" id="match_days">
                    <h4><i>推荐操盘天数：</i></h4>
                    <ul>
                       <li class="on" data="8">
                            <p><i>8</i>天</p>
                            <span>操盘天数</span>
                        </li>
                        <li data="14">
                            <p><i>14</i>天</p>
                            <span>操盘天数</span>
                        </li>
                        <li data="30">
                            <p><i>30</i>天</p>
                            <span>操盘天数</span>
                        </li>
                        <li data="60">
                            <p><i>60</i>天</p>
                            <span>操盘天数</span>
                        </li>
                    </ul>

                </div>
            </div>

        </div>
        <div class="cp_sider">
            <h2>确认操盘规则</h2>
            <div class="cp_sdfont">
                <label>操盘须知：</label>
                <span id="notify">投资沪深A股，仓位不限制，盈利全归您</span>
            </div>
            <div class="cp_sdfont">
                <label>操盘配额<a href="javascript:void(0);"></a>：</label>
                <span><i id="pzje"></i>元</span>
                 <div class="uc_pay_promt uc_pay_promt1" style="display: block;">
                    <i class="uc_pp_arrow"></i>
                    <p>配股宝借给您炒股的资金。</p>
                </div>
            </div>
            <div class="cp_sdfont">
                <label>总操盘资金<a href="javascript:void(0);"></a>：</label>
                <span><i id="zcpzj"></i>元</span>
                 <div class="uc_pay_promt uc_pay_promt1" style="display: block;">
                    <i class="uc_pp_arrow"></i>
                    <p>操盘保证金+操盘配额。</p>
                </div>
            </div>
            <div class="cp_sdfont">
                <label>亏损补仓钱<a href="javascript:void(0);"></a>：</label>
                <span><i id="ksbcx"></i>元</span>
                  <div class="uc_pay_promt uc_pay_promt2" style="display: block;">
                    <i class="uc_pp_arrow"></i>
                    <p>当总操盘资金低于警戒线以下时，只能平仓不能建仓，需要尽快补充风险保证金，以免低于亏损平仓线被平仓。</p>
                </div>
            </div>
             <div class="cp_sdfont">
                <label>亏损平仓钱<a href="javascript:void(0);"></a>：</label>
                <span><i id="kspcx"></i>元</span>
                 <div class="uc_pay_promt uc_pay_promt2" style="display: block;">
                    <i class="uc_pp_arrow"></i>
                    <p>当总操盘资金低于平仓线以下时，我们将有权把您的股票进行平仓，为避免平仓发生，请时刻关注风险保证金是否充足。</p>
                </div>
            </div>
            <div class="cp_sdfont">
                <label>预计结束日期<a href="javascript:void(0);"></a>：</label>
                <span id="end_day">${endDayString}</span>
                 <div class="uc_pay_promt uc_pay_promt5" style="display: block;">
                    <i class="uc_pp_arrow"></i>
                    <p>从今天到您选择借款天数的实际日期。</p>
                </div>
            </div>
            <div class="cp_sdfont">
                <label>总操盘利息<a href="javascript:void(0);"></a>：</label>
                <span class="cp_color"><i id="lx"></i>元</span><em>按自然日收费</em>
                  <div class="uc_pay_promt uc_pay_promt1" style="display: block;">
                    <i class="uc_pp_arrow"></i>
                    <p>配股宝借给您炒股的资金利息。</p>
                </div>
            </div>
            <div class="cp_sdfont">
                <label>账户管理费<a href="javascript:void(0);"></a>：</label>
                <span><i id="pzglf"></i>元/天</span><em>按交易日收费，周末节假日免费</em>
                <div class="uc_pay_promt uc_pay_promt1" style="display: block;">
                    <i class="uc_pp_arrow"></i>
                    <p>如果当天余额不足将自动终止方案</p>
                </div>
            </div>
            <div class="cp_sdfont">
                <label>开始交易时间<a href="javascript:void(0);"></a>：</label>
                <input type="radio" class="tday" name="tradeStart" value="0">
				<i class="tday">立即交易</i>
				<input type="radio" class="nday" name="tradeStart" value="1">
				<i class="nday">下个交易日</i>
				  <div class="uc_pay_promt uc_pay_promt4" style="display: block;">
                    <i class="uc_pp_arrow"></i>
                    <p>一般选择下个交易日，如看中行情急需交易，可直接选择今天交易（今天开始收取账户管理费并扣除利息）</p>
                </div>
            </div>
        </div>
        <div class="cp_bom">
            <p>如您不清楚规则，或有其他疑问，请联系客服：400-633-9257</p>
            <div class="cp_b_link">
                <input type="checkbox" checked="checked" id="agree">
                <span>我已阅读并同意<a href="javascript:tradeContract();">《实盘申请合作操盘协议》</a></span>
            </div>
            <div class="cp_b_btn"><a href="javascript:void(0);" id="submit">提交申请</a></div>
        </div>
    </div>
    </form>
 </div>

	<%@ include file="../common/personfooter.jsp"%>
<%@ include file="../common/dsp.jsp"%>
</body>
</html>

