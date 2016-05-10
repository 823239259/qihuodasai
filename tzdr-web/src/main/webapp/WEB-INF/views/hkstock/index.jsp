<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>
<%@include file="../common/import-artDialog-js.jspf"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>港股操盘 - 投资达人</title>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/cphk.css?v=${v}">
<script language="javascript" src="${ctx}/static/script/tzdr.js"></script>
<script src="${ctx}/static/script/hkstock/index.js" type="text/javascript"></script>
</head>

<body>

	<!--顶部 -->
	<%@ include file="../common/personheader.jsp"%>
<!-- 浮动层 -->
<div class="capital">
<!-- 隐藏域值：最大总操盘金额、最小总操盘金额、最大使用天数、最小使用天数-->
	<input type="hidden" name="minTotalMoney" id="minTotalMoney" value="${hkStockParams.minTotalMoney}"/>
	<input type="hidden" name="maxTotalMoney" id="maxTotalMoney" value="${hkStockParams.maxTotalMoney}"/>
	<input type="hidden" name="minHoldDays" id="minHoldDays" value="${hkStockParams.minHoldDays}"/>
	<input type="hidden" name="maxHoldDays" id="maxHoldDays" value="${hkStockParams.maxHoldDays}"/>
	
	<!-- 隐藏域值：点击返回修改之前选择的值-->
	<input type="hidden" name="backlever" id="backlever" value="${lever}"/>
	<input type="hidden" name="backtradeStart" id="backtradeStart" value="${tradeStart}"/>
	<input type="hidden" name="backbailMoney" id="backbailMoney" value="${bailMoney}"/>
	<input type="hidden" name="backborrowPeriod" id="backborrowPeriod" value="${borrowPeriod}"/>
	<input type="hidden" name="backtotalMoney" id="backtotalMoney" value="${totalMoney}"/>

<!-- 隐藏域值 -->	
	<div style="width:998px;margin:0 auto;border:1px solid #e1e1e1;border-bottom:none;color:red;">
		<div style="text-align:center;font-weight:bold;margin:15px 0px;">港股免费操盘活动规则</div>
		<div style="margin:0px 20px;padding-bottom:10px;line-height:180%;"><p>1，活动时间：2016.04.20 09:30 - 2016.04.28 12:00；</p>
		<p>2，在此活动期间申请的港股方案一律免除通道使用费和账户管理费，您只需要支付操盘本金；</p>
		<p>3，每个用户最多可持有一个免费操盘方案；</p>
		<p>4，活动期间所有的方案将于2016.04.28 15:30开始执行平仓，并终结方案，请您在此之前卖出股票；</p>
		<p>5，若您要申请总操盘资金大于10万港币的方案，请联系客服：400-020-0158。</p></div>
	</div>
    <div class="capital_ctn">
   		<form method="post" action="${ctx}/userhkstock/confirm">
   		<!-- 倍数处理 --><input id="sublever" name="lever" type="hidden"></input>
		<!-- 总操盘资金--><input id="subTotalMoney"  name="totalMoney" type="hidden"></input>
		<!-- 保证金--><input id="subBailMoney"  name="bailMoney" type="hidden"></input>        
        <div class="cp_main">
            <div class="cp_m_ctn">
                <div class="cp_m_titl">
                    <i></i>
                    <h3 class="cp_h1">输入总操盘资金(
                    <fmt:formatNumber type="number" value="${hkStockParams.minTotalMoney/10000}" maxFractionDigits="1"/>万
                    -
                    <fmt:formatNumber type="number" value="${hkStockParams.maxTotalMoney/10000}" maxFractionDigits="1"/>
                                                 万港元)</h3> 
                    <input class="font_size_22"  id="totalMoney" type="text" value="" onpaste="return false" autocomplete="off">
                    <em>港元</em>
                </div>
                <div class="cp_m_list" id="margin">
                    <h4><i>推荐操盘金额：</i></h4>
                    <ul>
                    	<c:forEach var="recommendHoldMoney" items="${recommendHoldMoney}" varStatus="money">
                    		<c:choose>
                    			<c:when test="${money.index==0}">
                    				<li class="on" data="${recommendHoldMoney}">
			                            <p><i><fmt:formatNumber type="number" value="${recommendHoldMoney/10000}" maxFractionDigits="1"/>万</i>港元</p>
		                        	</li>
                    			</c:when>
                    			<c:otherwise>
                    				<li  data="${recommendHoldMoney}">
			                            <p><i><fmt:formatNumber type="number" value="${recommendHoldMoney/10000}" maxFractionDigits="1"/>万</i>港元</p>
		                        	</li>
                    			</c:otherwise>
                    		</c:choose>
                    		
                    	</c:forEach>
                    </ul>
                </div>
            </div>
            <div class="cp_m_ctn">
                <div class="cp_m_titl">
                    <i></i>
                    <h3 class="cp_h2">选择操盘保证金</h3>
                   
                    <span id="baildata" ></span>
                    <em>元</em>
                </div>
                <div class="cp_m_list" id="bailMoney_div">
                    <h4><i>保证金越高，平仓风险越低</i></h4> 
                    <ul id="bailMoney">
                      
                    </ul>

                </div>
            </div>
            <div class="cp_m_ctn">
                <div class="cp_m_titl">
                    <i></i>
                    <h3 class="cp_h3">输入操盘天数(${hkStockParams.minHoldDays}-${hkStockParams.maxHoldDays}天)</h3>
                    <input class="font_size_22"  type="text" name="borrowPeriod"  id="use_day" value="" onpaste="return false" autocomplete="off">
                    <em>天</em>
                </div>
                <div class="cp_m_list" id="match_days">
                    <h4><i>推荐操盘天数：</i></h4>
                    <ul>
                    	<c:forEach var="recommendHoldDay" items="${recommendHoldDays}" varStatus="day">
                    		<c:choose>
                    			<c:when test="${day.index==0}">
			                        <li data="${recommendHoldDay}" class="on">
			                            <p><i>${recommendHoldDay}</i>天</p>
			                        </li>
			                     </c:when>
			                     <c:otherwise>
			                     	<li data="${recommendHoldDay}">
			                            <p><i>${recommendHoldDay}</i>天</p>
			                        </li>
			                     </c:otherwise>
			                 </c:choose>
			             </c:forEach>
                    </ul>

                </div>
            </div>

        </div>
        <div class="cp_sider">
            <h2>确认操盘规则<!-- <a href="javascript:void(0);" class="cp_sdblink">今日限制股</a> --></h2>
            <div class="cp_sdfont">
                <label>操盘须知：</label>
                <span id="operatorsInfo"><a href="${ctx}/help?tab=configuration&leftMenu=10" target="_blank">港股黑名单>></a></span>
            </div>            
            <div class="cp_sdfont">
                <label>交易时间：</label>
                <span><i>9:30-12:00，13:00-16:00</i></span>
            </div>
            <div class="cp_sdfont">
                <label>总操盘资金(HK$)：</label>
                <span><i id="show_total_money">2200</i>港元</span>
            </div>
            <div class="cp_sdfont">
                <label>亏损警戒线(HK$)：</label>
                <span><i id="show_waring_money">2200</i>港元</span>
            </div>
            <div class="cp_sdfont">
                <label>亏损平仓线(HK$)：</label>
                <span><i id="show_open_money">2200</i>港元</span>
            </div>
            <div class="cp_sdfont" style="display:none;">
                <label>预计结束日：</label>
                <span id="end_day"></span>
            </div>            
            <div class="cp_sdfont">
                <label>操盘保证金(￥)：</label>
                <span><i id="show_bail_money">2200</i>元</span>
            </div>
            <div class="cp_sdfont">
                <label>通道使用费(￥)：</label>
                <span id="totalInterestFee"><i>0.0</i>元</span>
            </div>
            <div class="cp_sdfont">
                <label>账户管理费(￥)：</label>
                <span><i id="manageFee">0.0</i>元/交易日</span>
            </div>
            <div class="cp_sdfont">
                <label>开始交易时间：</label>
                <span><input type="radio" class="tday" name="tradeStart" value="0">
					<b class="tday">立即交易</b><input type="radio" class="nday" name="tradeStart" value="1">
					<b class="nday">下个交易日</b>
                </span>
            </div>
        </div>
        <div class="cp_bom">
            <p>如您不清楚规则，或有其他疑问，请联系客服：400-020-0158</p>
            <div class="cp_b_link">
                <input type="checkbox" checked="checked" id="agree">
                <span>我已阅读并同意<a href="javascript:hkTradeContract();">《港股操盘合作协议》</a></span>
            </div>
            <div class="cp_b_btn"><a href="javascript:void(0);" id="submit">提交操盘申请</a></div>
        </div>
       </form>
    </div>
</div>
<%@ include file="../common/personfooter.jsp"%>
</body>
</html>

