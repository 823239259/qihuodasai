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
<link href="${ctx}/static/css/trade.css?v=20150528" rel="stylesheet"
	type="text/css" />
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/capital.css?v=${v}">
<script language="javascript" src="${ctx}/static/script/tzdr.js"></script>
<link href="${ctx}/static/css/pagination.css" rel="stylesheet" type="text/css" />
<script src="${ctx}/static/script/common/jquery.pagination.js" type="text/javascript"></script>
<script language="javascript" src="${ctx}/static/script/monthTrade/preByDay.js?v=${v}"></script>
<title>月月操盘申请 - 配股宝</title>
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
	window.open(basepath+'monthTrade/tradeContract','实盘申请合作操盘协议','height='+htmlHeight+',innerHeight='+htmlHeight+',width='+htmlWidth+',innerWidth='+htmlWidth+',top='+iTop+',left='+iLeft+',toolbar=no,menubar=no,scrollbars=yes,resizeable=no,location=no,status=no');  
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
	<form method="POST" action="${ctx}/monthTrade/totrade">
	<input type="hidden" name="type" value="1"/>
	<input type="hidden" name="capitalMargin" id="capitalMargin" value="${capitalMargin}"/>
	<input type="hidden" name="leverBack" id="lever" value="${lever}"/>
	<input type="hidden" name="min" id="min" value="${minMoney}"/>
	<input type="hidden" name="borrowPeriodBack" id="borrowPeriod" value="${borrowPeriod}"/>
	<input type="hidden" name="backTradeStart" id="backTradeStart" value="${tradeStart}"/>
	<input type="hidden" name="max" id="max" value="${maxMoney}"/>
	<input type="hidden" name="month" id="month" />
	<input type="hidden" name="maxLever" id="maxLever" value="${maxLever}" />
	<input type="hidden" name="minLever" id="minLever" value="${minLever} "/>
	<input type="hidden" name="maxMonth" id="maxMonth" value="${maxMonth}" />
	<input type="hidden" name="minMonth" id="minMonth" value="${minMonth} "/>
    <div class="capital_ctn">    	
      
        <div class="cp_main" style="border-right:1px solid #e5e5e5; margin-right:-1px;">
            <div class="cp_m_ctn">
            	<div class="cp_m_num"><i>①</i>输入操盘保证金</div>
                <div class="cp_m_titl"><input class="font_size_22"  id="tz" type="text" placeholder="<fmt:formatNumber type="number" value="${minMoney/10000}" maxFractionDigits="3"/>万元~<fmt:formatNumber type="number" value="${maxMoney/10000}" maxFractionDigits="3"/>万元" onpaste="return false" autocomplete="off"> </div>
                <div class="cp_m_list" id="margin">
                	<ul>
                    <c:forEach var="recommendHoldMoney" items="${money}" >
           				<li  data="${recommendHoldMoney}">
                      	<p><i><fmt:formatNumber type="number" value="${recommendHoldMoney/10000}" maxFractionDigits="1"/>万</i>元</p>
                 		</li>                    	
                    	</c:forEach>
                    </ul>
                </div>
            </div>
            <div class="cp_m_ctn">      
               <h3 style="display: none;" id="lever_notify">输入倍数(<span id="min_lever">${minLever}</span><span id="link">-</span><span id="max_lever">${maxLever}</span>倍)</h3>
                  	
            	<div class="cp_m_num"><i>②</i>选择操盘倍数</div>
                <div class="cp_m_titl" style="display:none;">                   
                    <input class="font_size_22"  id="pzgg" name="lever" type="text" placeholder="1~5倍" onpaste="return false" autocomplete="off">
                </div>
                <div class="cp_m_list" id="capital_lever">
                    <ul class="cp_m_mul">
                     <c:forEach var="leverNum" items="${levers}" varStatus="lever">
                        <li data="${leverNum}" class="no">
                            <p><i>${leverNum}倍</i></p>                         
                        </li>
                        </c:forEach>
                    </ul>
                </div>
            </div>
            <div class="cp_m_ctn">            	
            	<div class="cp_m_num"><i>③</i>选择操盘月数（1个月统一为30天，含节假日）</div>
                <div class="cp_m_titl" style="height:0px">
                    <input class="font_size_22"  type="hidden" id="use_day"  name="borrowPeriod"  placeholder="2~180天" onpaste="return false" autocomplete="off">
                </div>
                <div class="cp_m_list" id="match_days">
                    <ul>
                    <c:forEach var="monthValue" items="${months}" >
                        <li data="${monthValue}">
                            <p><i>${monthValue}月</i></p>
                        </li>
                       </c:forEach>
                    </ul>
                </div>
            </div>
        </div>
        <div class="cp_sider">
        	<div class="cp_m_num"><i>④</i>确认操盘规则<a href="${ctx}/help?status=1#list" target="_blank">今日限制股</a></div>
            <div class="cp_sdfont">
                <label>操盘须知：</label>
                <span id="notify">仓位不限制，盈利全归您。</span>
            </div>
            <div class="cp_sdfont">
                <label>操盘配额：</label>
                <span><i id="pzje"></i>元</span>
                  <div class="uc_pay_promt uc_pay_promt1" style="display: block;">
                    <i class="uc_pp_arrow"></i>
                    <p>配股宝借给您炒股的资金。</p>
                </div>
            </div>
            <div class="cp_sdfont">
                <label>总操盘资金：</label>
                <span><i id="zcpzj"></i>元</span>
                  <div class="uc_pay_promt uc_pay_promt1" style="display: block;">
                    <i class="uc_pp_arrow"></i>
                    <p>操盘保证金+操配额。</p>
                </div>
            </div>
            <div class="cp_sdfont">
                <label>亏损警戒线：</label>
                <span><i id="ksbcx"></i>元</span>
                  <div class="uc_pay_promt uc_pay_promt2" style="display: block;">
                    <i class="uc_pp_arrow"></i>
                    <p>当总操盘资金低于警戒线以下时，只能平仓不能建仓，需要尽快补充风险保证金，以免低于亏损平仓线被平仓。</p>
                </div>
            </div>
             <div class="cp_sdfont">
                <label>亏损平仓钱：</label>
                <span><i id="kspcx"></i>元</span>
                 <div class="uc_pay_promt uc_pay_promt2" style="display: block;">
                    <i class="uc_pp_arrow"></i>
                    <p>当总操盘资金低于平仓线以下时，我们将有权把您的股票进行平仓，为避免平仓发生，请时刻关注风险保证金是否充足。</p>
                </div>
            </div>
            <div class="cp_sdfont">
                <label>预计结束时间：</label>
                <span><i id="end_day"></i></span>
                  <div class="uc_pay_promt uc_pay_promt5" style="display: block;">
                    <i class="uc_pp_arrow"></i>
                    <p>从今天到您选择操盘天数的实际日期。</p>
                </div>
            </div>
            <div class="cp_sdfont">
                <label>总操盘费用：</label>
                <span class=""><i id="lx"></i>元<strong style="margin-left: 14px; font-weight: 100;">一次性收取管理费用，中途终结方案不予退还</strong></span>
                
            </div>

            <div class="cp_sdfont">
                <label>开始交易时间：</label>
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
            <p>如您不清楚规则，或有其他疑问，请联系客服：400-633-9257</p>
            <div class="cp_b_btn"><a href="javascript:void(0);" id="submit">提交操盘申请</a></div>
            <div class="cp_b_link">
                <input type="checkbox" checked="checked" id="agree">
                <span>我已阅读并同意<a href="javascript:tradeContract();">《月月操盘合作协议》</a></span>
            </div>
            <c:if test="${isOpen eq '1' and loginStatus eq 1}">		
              <div class="cp_b_promt">* 为保障更多用户获得操盘，本时段每用户最大操盘配额限<i><fmt:formatNumber value="${maxLeverMoney}" pattern="###,###" ></fmt:formatNumber></i>元</div>
              </c:if>	           
            <p style="color:#f00; display:none;">配股宝遵守证监会新规，现暂停股票操盘业务，有疑问请联系客服：400-633-9257</p>
        </div>
    </div>
    </form>
 </div>
	<%@ include file="../common/personfooter.jsp"%>
<%-- <%@ include file="../common/dsp.jsp"%> --%>
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

