<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>
<%@include file="../common/import-artDialog-js.jspf"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>港股操盘申请 - 配股宝</title>
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
    <div class="capital_ctn">
   		<form method="post" action="${ctx}/userhkstock/confirm">
   		<!-- 倍数处理 --><input id="sublever" name="lever" type="hidden"></input>
		<!-- 总操盘资金--><input id="subTotalMoney"  name="totalMoney" type="hidden"></input>
		<!-- 保证金--><input id="subBailMoney"  name="bailMoney" type="hidden"></input>        
        <!--<div class="cp_title">港股操盘</div>-->
        <div class="cp_main">
            <div class="cp_m_ctn">            	
            	<div class="cp_m_num"><i>①</i>输入总操盘金</div>
                <div class="cp_m_titl">                   
                    <%-- <fmt:formatNumber type="number" value="${hkStockParams.minTotalMoney/10000}" maxFractionDigits="0"/>万
                    -
                    <fmt:formatNumber type="number" value="${hkStockParams.maxTotalMoney/10000}" maxFractionDigits="0"/>
                                                 万港元)</h3>  --%>
                    <input class="font_size_22"  id="totalMoney" type="text" value="" onpaste="return false" autocomplete="off">
                </div>
                <div class="cp_m_list" id="margin">
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
            	<div class="cp_m_num"><i>②</i>选择操盘保证金<em>(保证金越高，平仓风险越低)</em></div>
            	<span id="baildata" style="display: none;"></span>
                <div class="cp_m_list" id="bailMoney_div">
                    <ul id="bailMoney">
                      
                    </ul>

                </div>
            </div>
            <div class="cp_m_ctn">
            	<div class="cp_m_num"><i>③</i>输入操盘天数</div>
                <div class="cp_m_titl">
                    <input class="font_size_22"  type="text" name="borrowPeriod"  id="use_day" value="" onpaste="return false" autocomplete="off">
                </div>
                <div class="cp_m_list" id="match_days">
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
        <div class="cp_sider" style="border-left:1px solid #e5e5e5; margin-left:-1px; ">            
        	<div class="cp_m_num"><i>④</i>确认操盘规则</div>
            <div class="cp_sdfont">
                <label>操盘须知：</label>
                <span><span style="width:initial; display:initial;"   id="operatorsInfo"></span><a style="color:#0aaff4;" href="http://www.peigubao.com/help?status=2">查看更多港股规则>></a></span>
            </div>
            <div class="cp_sdfont">
                <label>交易时间：</label>
                <span><i >9:30-12:00,13:00-16:00</i></span>
            </div>
            <div class="cp_sdfont">
                <label>总操盘资金(HK$)：</label>
                <span><i id="show_total_money">2200</i>港元</span>
            </div>
            <div class="cp_sdfont">
                <label>操盘保证金(￥)：</label>
                <span><i id="show_bail_money">2200</i>元</span>
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
            <p>如您不清楚规则，或有其他疑问，请联系客服：400-633-9257</p>
            <div class="cp_b_btn"><a href="javascript:void(0);" id="submit">提交操盘申请</a></div>
            <div class="cp_b_link">
                <input type="checkbox" checked="checked" id="agree">
                <span>我已阅读并同意<a href="javascript:hkTradeContract();">《港股操盘合作协议》</a></span>
            </div>            
        </div>
       </form>
    </div>
</div>
<%@ include file="../common/personfooter.jsp"%>
</body>
</html>

