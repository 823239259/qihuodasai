<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>
<%@include file="../common/import-artDialog-js.jspf"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>发起股票合买 - 维胜</title>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/buy.css?v=${v}">
<script language="javascript" src="${ctx}/static/script/tzdr.js"></script>
<script src="${ctx}/static/script/togetherTrade/index.js?v=${v}" type="text/javascript"></script>
</head>
<body>
<!--顶部 -->
	<%@ include file="../common/personheader.jsp"%>
<!-- 浮动层 -->
<div class="floatlayer" style="display:none;"></div>
<!-- 隐藏域值：最大总操盘金额、最小总操盘金额-->
	<input type="hidden" name="minMoney" id="minMoney" value="${togetherConfig.minMoney}"/>
	<input type="hidden" name="maxMoney" id="maxMoney" value="${togetherConfig.maxMoney}"/>
<%-- 	<!-- 隐藏域值：点击返回修改之前选择的值-->
	<input type="hidden" name="backlever" id="backlever" value="${lever}"/>
	<input type="hidden" name="backtradeStart" id="backtradeStart" value="${tradeStart}"/>
	<input type="hidden" name="backbailMoney" id="backbailMoney" value="${bailMoney}"/>
	<input type="hidden" name="backborrowPeriod" id="backborrowPeriod" value="${borrowPeriod}"/>
	<input type="hidden" name="backtotalMoney" id="backtotalMoney" value="${totalMoney}"/> --%>

<div class="buy">
<form method="post" action="${ctx}/usertogether/totrade">
		<!-- 总操盘资金--><input id="recommendMoney"  type="hidden"  name="recommendMoney" value ='${recommendMoney[0]}'></input>
		<!-- 倍数处理 --><input id="lever"  name="lever" type="hidden" value="${moneyRatio[0]}"></input>
		<!-- 天数--><input id="days"  name="days"  type="hidden" value="${recommendDay[0]}" ></input>
    <div class="buy_choose">
        <div class="buy_c_one buy_c_two">
            <h3><em>①</em>选择合买模式</h3>
            <ul class="buy_c_list buy_c_mode">
                <li class="on"><p>少分利</p><span>合买者有保底收益</span></li>
                <li class="buy_c_noopen"><p>高分利</p><span>合买者没有保底收益</span><img src="${ctx}/static/images/together/icon_01.gif" class="buy_c_icon"></li>
            </ul>
        </div>
    <div class="buy_c_one" id="margin">
            <h3><em>②</em>选择总操盘资金<%-- (
                    <fmt:formatNumber type="number" value="${togetherConfig.minMoney/10000}" maxFractionDigits="0"/>万元
                    -
                    <fmt:formatNumber type="number" value="${togetherConfig.maxMoney/10000}" maxFractionDigits="0"/>
                                                 万元) --%></h3>
            <ul class="buy_c_list" >
            <c:forEach var="recommendMoney" items="${recommendMoney}" varStatus="money">
                    		<c:choose>
                    			<c:when test="${money.index==0}">
                    				<li class="on" data="${recommendMoney}">
			                            <p><fmt:formatNumber type="number" value="${recommendMoney/10000}" maxFractionDigits="0"/>万元</p>
		                        	</li>
                    			</c:when>
                    			<c:otherwise>
                    				<li  data="${recommendMoney}">
			                            <p><fmt:formatNumber type="number" value="${recommendMoney/10000}" maxFractionDigits="0"/>万元</p>
		                        	</li>
                    			</c:otherwise>
                    		</c:choose>
                    	</c:forEach>
         		 <li data=""> <p><input  id="otherRecommendMoney" type="text" class="buy_c_money" focucmsg="其他金额" onpaste="return false" autocomplete="off">元</p></li>
            </ul>
        </div>        
        <div class="buy_c_one" id="bailMoney_div">
            <h3><em>③</em>选择操盘手出资</h3>
            <ul  id="bailMoney" class="buy_c_list">
            <c:forEach var="moneyRatio" items="${moneyRatio}" varStatus="moneyLever">
                    		<c:choose>
                    			<c:when test="${moneyLever.index==0}">
                    				<li class="on" data="${moneyRatio}">
			                            <p><fmt:formatNumber type="number" value="${recommendMoney[0]/(moneyRatio+1)}"  maxFractionDigits="0"/>元</p>
		                        	</li>
                    			</c:when>
                    			<c:otherwise>
                    				<li  data="${moneyRatio}">
			                            <p><fmt:formatNumber type="number" value="${recommendMoney[0]/(moneyRatio+1)}"  maxFractionDigits="0"/>元</p>
		                        	</li>
                    			</c:otherwise>
                    		</c:choose>
                    	</c:forEach>
            </ul>
        </div>           
         
        <div class="buy_c_one" id="match_days">
            <h3><em>④</em>选择操盘天数</h3>
            <ul class="buy_c_list">
                <c:forEach var="recommendDay" items="${recommendDay}" varStatus="day">
                    		<c:choose>
                    			<c:when test="${day.index==0}">
			                        <li class="on" data="${recommendDay}">
			                            <p>${recommendDay}天</p>
			                        </li>
			                     </c:when>
			                     <c:otherwise>
			                     	<li data="${recommendDay}">
			                            <p>${recommendDay}天</p>
			                        </li>
			                     </c:otherwise>
			                 </c:choose>
			             </c:forEach>
            </ul>
        </div>
    </div>
    <div class="buy_rule">        
        <h3><em>⑤</em>确认操盘规则</h3>
        <ul class="buy_r_list">
            <li>
                <label>总操盘资金</label>
                <span><i id ="totalMoney"></i>元</span>
                <p class="line">操盘手出资+合买者出资形成总操盘资金</p>
            </li>
            <li>
                <label>亏损补仓线</label>
                <span><i id="shortLine"></i>元</span>
                <p>当操盘资金低于补仓线时，需操盘手尽快使用自有资金进行补仓</p>
            </li>
            <li class="last">
                <label>亏损平仓线</label>
                <span><i id="openLine"></i>元</span>
                <p class="line">当操盘资金低于平仓线，系统将自动平仓</p>
            </li>
        </ul>
        <ul class="buy_r_list">
        	<li>
                <label>合买者出资</label>
                <span><i id="hemaiMoney"></i>元</span>
                <p class="line">受邀的达人用户将积极参与您的股票合买</p>
            </li>
            <li>
                <label>合买者收益</label>
                <span id="totalInterestFee" ><i></i>元</span>
                <p>所有合买者的资金成本，按操盘天数一次性收取，延期后再按天收取</p>
            </li>
            <li>
                <label>合买者分利</label>
                <span><i>纯利润*<fmt:formatNumber type="number" value="${togetherConfig.profitRatio*100}" pattern="0.00" maxFractionDigits="2"/>%</i></span>
                <p>当方案盈利时，参与该方案的所有合买者将分得净利润的<fmt:formatNumber type="number" value="${togetherConfig.profitRatio*100}" pattern="0.00" maxFractionDigits="2"/>%</p>
            </li>
            <li class="last">
                <label>账户管理费</label>
                <span><i id = "manageFee"></i>元/交易日</span>
                <p>由维胜平台按实际操盘天数收取，非交易日免费</p>
            </li>
        </ul>
        <ul class="buy_r_list">
            <li>
                <label>持仓须知</label>
                <span><i>沪深A股</i></span>
                <p id="operatorsInfo" class="line"></p>
            </li>
            <li>
                <label>操盘天数</label>
                <span><i id="daysShow">${recommendDay[0]}</i>天</span>
                <p class="line">到期后，若账户余额充足，方案自动延期</p>
            </li>
            <li class="last">
                <label>开始交易时间</label>
                <span><i>合买满标</i></span>
                <p class="line">若3个交易日未满标，该方案将流标</p>
            </li>
        </ul>
    </div>
    
    <div class="buy_btn"><a href="javascript:void(0);" id="submit" >立即发起合买</a></div>
    <div class="buy_agree"><input type="checkbox" checked="checked" id="agree"><em>我已阅读并同意</em><a href="javascript:lookContract();">《股票合买操盘合作协议》</a></div>
    </form>
</div>
<%@ include file="../common/personfooter.jsp"%>
</body>
<script type="text/javascript">
function lookContract(){
	var htmlHeight = 800;  //网页高度
	var htmlWidth = 1221;  //网页宽度
	var iTop = (window.screen.height-30-htmlHeight)/2; //获得窗口的垂直位置;  
	var iLeft = (window.screen.width-10-htmlWidth)/2;  //获得窗口的水平位置;  
	window.open(basepath+'static/together/together.html','实盘申请合作操盘协议','height='+htmlHeight+',innerHeight='+htmlHeight+',width='+htmlWidth+',innerWidth='+htmlWidth+',top='+iTop+',left='+iLeft+',toolbar=no,menubar=no,scrollbars=yes,resizeable=no,location=no,status=no');  
}
</script>
</html>
