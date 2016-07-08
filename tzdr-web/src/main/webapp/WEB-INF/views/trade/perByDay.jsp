<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>
<%@include file="../common/import-artDialog-js.jspf"%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="zh">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style type="text/css">
 .font_size_15 {font-size: 15px; color: #f60; }
 .font_size_22 {font-size: 22px; color: #f60; }                       
</style>
<link href="${ctx}/static/css/public.css" rel="stylesheet"
	type="text/css">
<link href="${ctx}/static/css/trade.css?v=20150528" rel="stylesheet"
	type="text/css" />
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/capital.css?v=${v}">
<script language="javascript" src="${ctx}/static/script/tzdr.js"></script>
<link href="${ctx}/static/css/pagination.css" rel="stylesheet" type="text/css" />
<script src="${ctx}/static/script/common/jquery.pagination.js" type="text/javascript"></script>
<script language="javascript" src="${ctx}/static/script/trade/preByDay.js?v=${v}"></script>
<title>随心操盘 - 维胜</title>
<%-- <%
   response.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
   response.setHeader("location",basePath); 
%> --%>
<script type="text/javascript">
function tradeContract(){
	var htmlHeight = 800;  //网页高度
	var htmlWidth = 1221;  //网页宽度
	var iTop = (window.screen.height-30-htmlHeight)/2; //获得窗口的垂直位置;  
	var iLeft = (window.screen.width-10-htmlWidth)/2;  //获得窗口的水平位置;  
	window.open(basepath+'tradeContract','实盘申请合作操盘协议','height='+htmlHeight+',innerHeight='+htmlHeight+',width='+htmlWidth+',innerWidth='+htmlWidth+',top='+iTop+',left='+iLeft+',toolbar=no,menubar=no,scrollbars=yes,resizeable=no,location=no,status=no');  
}

$(document).ready(function(){
	 $('.cp_sdblink').click(function() {
		 $.ajax({   
		        type: "POST",   
		        url: basepath+"limitstock/stocks",  
		        dataType: 'json', 
		        contentType: "application/x-www-form-urlencoded", 
		        success: function(msg){  
		            var html = '';   
		            $.each(msg, function(i) {
		            	html+="<li>"+msg[i].code+"&nbsp;&nbsp;&nbsp;&nbsp;"+msg[i].name+"</li>";
	              });
		           
		          $('.fl_cpdblist').html(html); 
		        }   
		    }); 
	        $('.fl_cpdb').show();
	        $('.fl_mask').show();
	    });
	 $('.fl_cpdbtn a').click(function() {
	        $('.fl_cpdb').hide();
	        $('.fl_mask').hide();
	    });

});

</script>

</head>
<body>

	<!--顶部 -->
	<%@ include file="../common/personheader.jsp"%>
	
	<!-- 最大操盘额限制弹框 -->
	<c:if test="${isOpen eq 1 and loginStatus eq 1}">
		<div class="fl_mnpromt" style="display: block;">
	        <p><span>为保障更多用户获得操盘，每位用户每天只能新增<i>${limitTradeNum}</i>个操盘方案</span><br>本时段最大操盘配额限<i><fmt:formatNumber value="${maxLeverMoney}" pattern="#,###.##" minFractionDigits="2" ></fmt:formatNumber></i>元</p>
	        <a href="javascript:void(0);" id="OK">确定</a>
	    </div>
	</c:if>
	
	<div class="floatlayer">
		 <!-- 操盘金额提示框 -->
    <%-- <div class="fl_moneypromt">
        <a href="javascript:void(0);"><img src="${ctx}/static/images/cp/promtbtn.gif"></a>
        <div class="fl_mpromtfont" style="display:none;">
             <h2>操盘提示</h2>
             <h3>放大倍数对应最大金额</h3>
             <ul>
                <li class="fl_mpromttitle">
                    <label>倍数</label>
                    <span>操盘金额</span>
                </li>              
               <c:forEach items="${findConfigList}" var="findConfig" varStatus="status">
		  		<li>
                    <label>${findConfig.times}</label>
                    <span><fmt:formatNumber value="${findConfig.fundAmount/10000.0}" maxFractionDigits="4" ></fmt:formatNumber>W</span>
                </li>
				</c:forEach>
             </ul>
        </div>
    </div> --%>
		 <c:choose>
		 	<c:when test="${isOpen eq 1 and loginStatus eq 1}">
		 		<div class="fl_mask" style="display: block;"></div>
		 	</c:when>
		 	<c:otherwise>
		 		<div class="fl_mask" style="display:none;"></div>
		 	</c:otherwise>
		 </c:choose>
	    <!-- 限制股弹出框 -->
	    <div class="fl_cpdb" style="display:none;">
	        <h2>今日限购</h2>
	        <ul class="fl_cpdblist">
	       
	        </ul>
	        <div id="Pagination"></div> 
	        <div class="fl_cpdbtn"><a href="javascript:void(0);">我知道了</a></div>
	    </div>
		
	</div>
	<div class="capital">
	<form method="POST" action="${ctx}/totrade">
	<input type="hidden" name="type" value="0"/>
	<input type="hidden" name="capitalMargin" id="capitalMargin" value="${capitalMargin}"/>
	<input type="hidden" name="leverBack" id="lever" value="${lever}"/>
	<input type="hidden" name="borrowPeriodBack" id="borrowPeriod" value="${borrowPeriod}"/>
	<input type="hidden" name="backTradeStart" id="backTradeStart" value="${tradeStart}"/>
	<input type="hidden" name="max" id="max" value="${max}"/>
	<input type="hidden" name="enter" id="enter" value="${enter}"/>
    <div class="capital_ctn">
        <div class="cp_main">
            <div class="cp_m_ctn">
                <div class="cp_m_titl">
                    <i></i>
                    <h3 class="cp_h1">输入保证金额(250元-600万元)</h3>
                    <input class="font_size_22"  id="tz" type="text" value="" onpaste="return false" autocomplete="off">
                    <em>元</em>
                </div>
                <div class="cp_m_list" id="margin">
                    <h4><i>推荐保证金：</i></h4>
                    <c:choose>
						<c:when test="${enter == 1}">
							<ul>
                        <li class="on"  data="10000">
                            <p><i>1万</i>元</p>
                            <span>保证金额</span>
                        </li>
                        <li data="20000">
                            <p><i>2万</i>元</p>
                            <span>保证金额</span>
                        </li>
                        <li data="30000">
                            <p><i>3万</i>元</p>
                            <span>保证金额</span>
                        </li>
                        <li data="50000">
                            <p><i>5万</i>元</p>
                            <span>保证金额</span>
                        </li>
                    </ul>
						</c:when>
						<c:when test="${enter == 2}">
							<ul>
                        <li class="on"  data="50000">
                            <p><i>5万</i>元</p>
                            <span>保证金额</span>
                        </li>
                        <li data="100000">
                            <p><i>10万</i>元</p>
                            <span>保证金额</span>
                        </li>
                        <li data="200000">
                            <p><i>20万</i>元</p>
                            <span>保证金额</span>
                        </li>
                        <li data="300000">
                            <p><i>30万</i>元</p>
                            <span>保证金额</span>
                        </li>
                    </ul>
						</c:when>
						<c:when test="${enter == 3}">
							<ul>
                        <li class="on"  data="500000">
                            <p><i>50万</i>元</p>
                            <span>保证金额</span>
                        </li>
                        <li data="1000000">
                            <p><i>100万</i>元</p>
                            <span>保证金额</span>
                        </li>
                        <li data="3000000">
                            <p><i>300万</i>元</p>
                            <span>保证金额</span>
                        </li>
                        <li data="5000000">
                            <p><i>500万</i>元</p>
                            <span>保证金额</span>
                        </li>
                    </ul>
						</c:when>
						<c:otherwise>
							<ul>
                        <li  data="10000">
                            <p><i>1万</i>元</p>
                            <span>保证金额</span>
                        </li>
                        <li data="100000">
                            <p><i>10万</i>元</p>
                            <span>保证金额</span>
                        </li>
                        <li data="300000">
                            <p><i>30万</i>元</p>
                            <span>保证金额</span>
                        </li>
                        <li data="500000">
                            <p><i>50万</i>元</p>
                            <span>保证金额</span>
                        </li>
                    </ul>
						</c:otherwise>
					</c:choose>
                    

                </div>
            </div>
            <div class="cp_m_ctn">
                <div class="cp_m_titl">
                    <i></i>
                    <h3 class="cp_h2" id="lever_notify">输入倍数(<span id="min_lever">1</span><span id="link">-</span><span id="max_lever">6</span>倍)</h3>
                    <input class="font_size_22"  id="pzgg" name="lever" type="text" value="" onpaste="return false" autocomplete="off">
                    <em>倍</em>
                </div>
                <div class="cp_m_list" id="capital_lever">
                    <h4><i>推荐操盘倍数：</i></h4>
                    <ul>
                        <li data="2">
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
                        <li data="6">
                            <p><i>6</i>倍</p>
                            <span>放大倍数</span>
                        </li>
                    </ul>

                </div>
            </div>
            <div class="cp_m_ctn">
                <div class="cp_m_titl">
                    <i></i>
                    <h3 class="cp_h3">输入操盘天数(2-180天)</h3>
                    <input class="font_size_22"  type="text" id="use_day"  name="borrowPeriod"  value="" onpaste="return false" autocomplete="off">
                    <em>天</em>
                </div>
                <div class="cp_m_list" id="match_days">
                    <h4><i>推荐操盘天数：</i></h4>
                    <ul>
                        <li data="8">
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
            <h2>确认操盘规则<a href="javascript:void(0);" class="cp_sdblink">今日限购</a></h2>
            <div class="cp_sdfont">
                <label>操盘须知：</label>
                <span id="notify">仓位不限制，盈利全归您</span>
            </div>
            <div class="cp_sdfont">
                <label>操盘配额<a href="javascript:void(0);"></a>：</label>
                <span><i id="pzje"></i>元</span>
                  <div class="uc_pay_promt uc_pay_promt1" style="display: block;">
                    <i class="uc_pp_arrow"></i>
                    <p>维胜借给您炒股的资金。</p>
                </div>
            </div>
            <div class="cp_sdfont">
                <label>总操盘资金<a href="javascript:void(0);"></a>：</label>
                <span><i id="zcpzj"></i>元</span>
                  <div class="uc_pay_promt uc_pay_promt1" style="display: block;">
                    <i class="uc_pp_arrow"></i>
                    <p>操盘保证金+操配额。</p>
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
                <label>预计结束日<a href="javascript:void(0);"></a>：</label>
                <span id="end_day"></span>
                  <div class="uc_pay_promt uc_pay_promt5" style="display: block;">
                    <i class="uc_pp_arrow"></i>
                    <p>从今天到您选择操盘天数的实际日期。</p>
                </div>
            </div>
            <div class="cp_sdfont">
                <label>总操盘利息<a href="javascript:void(0);"></a>：</label>
                <span class="cp_color"><i id="lx"></i>元</span><div class="cp_ticket"><a href="${ctx}/help?tab=configuration&leftMenu=4" target="_blank"><img src="${ctx }/static/images/cp/ticket.gif"></a></div>
                 <div class="uc_pay_promt uc_pay_promt1" style="display: block;">
                    <i class="uc_pp_arrow"></i>
                    <p>维胜借给您炒股的资金利息。</p>
                </div>
            </div>
            <div class="cp_sdfont">
                <label>账户管理费<a href="javascript:void(0);"></a>：</label>
                <span><i id="pzglf"></i>元/天</span><em>按天收取，周末节假日免费</em>              
                
                 <div class="uc_pay_promt uc_pay_promt1" style="display: block;">
                    <i class="uc_pp_arrow"></i>
                    <p>如果当天余额不足将自动终止方案</p>
                </div>
            </div>
            <div class="cp_sdfont">
                <label>开始交易时间<a href="javascript:void(0);"></a>：</label>
                <i class="cp_sd_time"><input type="radio" class="tday" name="tradeStart" value="0">
				<b class="tday">立即交易</b></i>
				<i class="cp_sd_time"><input type="radio" class="nday" name="tradeStart" value="1">
				<b class="nday">下个交易日</b></i>
				  <div class="uc_pay_promt uc_pay_promt4" style="display: block;">
                    <i class="uc_pp_arrow"></i>
                    <p>一般选择下个交易日，如看中行情急需交易，可直接选择今天交易（今天开始收取账户管理费并扣除利息）</p>
                </div>
            </div>
        </div>
        <div class="cp_bom">
            <p>如您不清楚规则，或有其他疑问，请联系客服：400-852-8008</p>
            <div class="cp_b_link">
                <input type="checkbox" checked="checked" id="agree">
                <span>我已阅读并同意<a href="javascript:tradeContract();">《随心操盘合作协议》</a></span>
            </div>
            <c:if test="${isOpen eq '1' and loginStatus eq 1}">		
              <div class="cp_b_promt">* 为保障更多用户获得操盘，本时段每用户最大操盘配额限<i><fmt:formatNumber value="${maxLeverMoney}" pattern="###,###" ></fmt:formatNumber></i>元</div>
              </c:if>	
            <div class="cp_b_btn"><a href="javascript:void(0);" style="background:#eae9e9;" <%--id="submit"--%>>提交申请</a></div>
            <p style="color:#f00;">维胜遵守证监会新规，现暂停股票操盘业务，有疑问请联系客服：400-852-8008</p>
        </div>
    </div>
    </form>
 </div>
	<%@ include file="../common/personfooter.jsp"%>
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

