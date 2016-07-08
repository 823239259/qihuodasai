<%@page
	import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@page import="com.tzdr.domain.entity.DataMap"%>
<%@page import="com.tzdr.business.service.datamap.DataMapService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>
<%@include file="../common/import-artDialog-js.jspf"%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="zh">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style type="text/css">
.font_size_15 {
	font-size: 15px;
	color: #f60;
}
.font_size_22 {
	font-size: 22px;
	color: #f60;
}
</style>
<link href="${ctx}/static/css/public.css?v=${v}" rel="stylesheet" type="text/css">
<link href="${ctx}/static/css/trade.css?v=${v}" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/capital.css?v=${v}">
<script language="javascript" src="${ctx}/static/script/tzdr.js?v=${v}"></script>
<link href="${ctx}/static/css/pagination.css?v=${v}" rel="stylesheet" type="text/css" />
<script src="${ctx}/static/script/common/jquery.pagination.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript" src="${ctx}/static/script/product/common.js?v=${v}"></script>
<script type="text/javascript" src="${ctx}/static/script/product/index.js?v=${v}"></script>
<script type="text/javascript">
    
</script>
<title>沪银 - 维胜</title>
</head>
<body>
	<!--顶部 -->
	<%@ include file="../common/personheader.jsp"%>
	<input type="hidden" id="inputTraderBaseBond" name="inputTraderBaseBond" value="${fSimpleProductConfig.traderBond}"/>
	<input type="hidden" id="inputTraderBaseMoney" name="inputTraderBaseMoney" value="${fSimpleProductConfig.traderMoney}"/>
	<input type="hidden" id="inputTraderBaseLineLoss" name="inputTraderBaseLineLoss" value="${fSimpleProductConfig.lineLoss}"/>
	<input type="hidden" id="inputTraderBaseFeeManage" name="inputTraderBaseFeeManage" value="${fSimpleProductConfig.feeManage}"/>
	<input type="hidden" id="inputTraderBaseFees" name="inputTraderBaseFees" value="${fSimpleProductConfig.tranFees}"/>
	<form action="${ctx}/userproduct/pay" id="settingForm" method="post">
	<input type="hidden" id="type" name="type" value="2"/>
	<input type="hidden" id="input_tranFees" name ="inputTranFees" value="${tranFeesArray[0]}" >
		<div class="capital">
			<div class="mc_nav">
				<a href="${ctx}/commodity/index">商品综合</a>
		        <a href="${ctx}/product/gold_index">沪金期货</a>
		        <a href="${ctx}/product/sliver_index" class="on">沪银期货</a>
		        <a href="${ctx}/product/copper_index">沪铜期货</a>
		        <a href="${ctx}/product/rubber_index">橡胶期货</a>
		    </div>
			<div class="capital_ctn">
				<div class="cp_main sif_main">
					<div class="cp_m_ctn">
		                <div class="cp_m_titl sif_m_title">
		                    <i></i>
		                    <h3 class="cp_h2">选择开仓手数<span>(可持仓的最大手数)</span></h3>
                    		<input type="text" id="input_tranLever" name="inputTranLever" value="${tranLevers[0]}"  style="font-size:0;">
		                </div>
		                <div class="cp_m_list sif_m_list">
		                    <ul class="zk_ul_futrue_tranLever">
		                    	<c:forEach items="${tranLevers}" var="tranLever" varStatus="status">
		                    		<c:choose>
		                    			<c:when test="${status.first==true}">
		                    				<li class="on">
					                        	<input type="hidden"  name="tranLever"  class="defaultVal" value="${tranLever}" />
					                            <input  type="hidden" name="tranFees"   class="defaultVal" value="${tranFeesArray[status.index]}" />
					                            <p><i>${tranLever}</i>手</p>
					                            <span>可开仓1-${tranLever}手</span></p>
					                        </li>
		                    			</c:when>
		                    			<c:otherwise>
		                    				<li>
					                        	<input type="hidden"  name="tranLever"  class="defaultVal" value="${tranLever}" />
					                            <input  type="hidden" name="tranFees"   class="defaultVal" value="${tranFeesArray[status.index]}" />
					                            <p><i>${tranLever}</i>手</p>
					                            <span>可开仓1-${tranLever}手</span></p>
					                        </li>
		                    			</c:otherwise>
		                    		</c:choose>
		                    	</c:forEach>
		                    </ul>
		                </div>
		            </div>
		            <div class="cp_m_ctn">
		                <div class="cp_m_titl sif_m_title">
		                    <i></i>
		                    <h3 class="cp_h1">单手保证金<span>(操盘保证金越多，平仓风险越低)</span></h3>
	                    		<input type="text" id="input_traderBond" name="inputTraderBond" value="${fSimpleProductConfig.traderBond}"  readonly="readonly" style="font-size:0;">
		                </div>
		                <div class="cp_m_list sif_m_list">
		                    <ul class="zk_ul_futrue_traderBond">
		                    	<c:if test="${!empty fSimpleProductConfig}">
		                    		<li class="on">
			                        	<input type="hidden" class="defaultVal" value="${fSimpleProductConfig.traderBond}" />
			                            <p><i><fmt:formatNumber value="${fSimpleProductConfig.traderBond}" pattern="#,###"></fmt:formatNumber></i>元</p>
			                            <span>操盘保证金额</span>
			                        </li>
		                    	</c:if>
		                    </ul>
		                </div>
		            </div>
		        </div>
				<div class="cp_sider sif_sider">
					<h2>确认操盘规则</h2>
					<div class="cp_sdfont">
						<label>操盘须知：</label><span style="color:#ff6500; font-size:16px;">投资沪银当期主力合约（${contract}）</span>
					</div>					
					<div class="cp_sdfont">
						<label>交易时间：</label><span><i>9:05-14:55，21:05-23:55</i></span>
					</div>
					<div class="cp_sdfont">
		                <label>操盘保证金：</label>
		                <span>
		                	<i id="totalTraderBond_id">
	                			<fmt:formatNumber value="${fSimpleProductConfig.traderBond*tranLevers[0]}" pattern="#,###"></fmt:formatNumber>
		                	</i>元
		                </span>
		            </div>
					<div class="cp_sdfont">
						<label>总操盘资金：</label> 
						<span>
							<i id="totalTradeMoney_id">
	                			<fmt:formatNumber value="${fSimpleProductConfig.traderMoney*tranLevers[0]}" pattern="#,###"></fmt:formatNumber>
							</i>元
						</span>
					</div>
					<div class="cp_sdfont">
						<label>亏损平仓线<!-- <a href="javascript:void(0);"></a> -->：</label> 
						<span>
							<i id="lineLoss_id">
	                			<fmt:formatNumber value="${(fSimpleProductConfig.traderMoney-fSimpleProductConfig.traderBond+fSimpleProductConfig.lineLoss)*tranLevers[0]} " pattern="#,###"></fmt:formatNumber>
							</i>元
						</span>
						<div class="uc_pay_promt uc_pay_promt2" style="display: none;">
							<i class="uc_pp_arrow"></i>
							<p>当总操盘资金低于平仓线以下时，我们将有权把您的股票进行平仓，为避免平仓发生，请时刻关注风险保证金是否充足。</p>
						</div>
					</div>
					<div class="cp_sdfont">
		                <label>账户管理费：</label>
		                <span>
		                	<i id="feeManage_id">
	                			<c:choose>
	                				<c:when test="${fSimpleProductConfig.feeManage gt 0}">
	                					<fmt:formatNumber value="${fSimpleProductConfig.feeManage}" pattern="#,###"></fmt:formatNumber>
	                				</c:when>
	                				<c:otherwise>
	                					免费
	                				</c:otherwise>
	                			</c:choose>
		                	</i>
               				<c:if test="${fSimpleProductConfig.feeManage gt 0}">
               					元/交易日，14:00前即日计费，14:00后次日计费(以操盘账户短信发送时间为准)
               				</c:if>
		                </span>
		            </div>
					<div class="cp_sdfont">
						<label>交易手续费：</label> 
						<span>
							<i id="tranFees_id">
	                			<fmt:formatNumber value="${tranFeesArray[0]}" pattern="#,###"></fmt:formatNumber>
							</i>元/手，每手交易开仓和平仓的手续费。
						</span>
					</div>
				</div>
				<div class="cp_bom">
					<p>如您不清楚规则，或有其他疑问，请联系客服：400-852-8008</p>
					<div class="cp_b_link">
						<input type="checkbox" checked="checked" id="checkbox_agree"ame="agree"> 
						<span>我已阅读并同意<a href="${ctx}/static/product/ag.html" target="_blank">《沪银期货操盘合作协议》</a></span>
					</div>
					<div class="cp_b_btn">
						<a href="javascript:void();" onclick="submitSetting(2)">提交操盘申请</a>
					</div>
				</div>
			</div>
			<div class="capital_rule">
		        <div class="cp_ru_icon"><img src="${ctx}/static/images/cp/icon.png"></div>
		        <h2>沪银期货介绍</h2>        
		        <p>2012年5月10日，白银期货正式挂牌上海期货交易所，这也是我国上市的第二个贵金属期货品种。沪银期货是指以未来某一时点的白银价格为标的物的期货合约，合约设计大小适中，且简化了交易保证金比例，降低最高比例，有利于提高套保资金使用效率。白银价格具有高波动性的特征，波动幅度不仅大于黄金价格的波动幅度，同时异常波动较多，幅度较大，请合理选择交易方向和入市时间。</p>
		        <h2>交易细则</h2>        
		        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="cp_ru_list">
		          <tr>
		            <td class="first">风险提示</td>
		            <td class="font">股市有风险，投资需谨慎</td>
		          </tr>
		          <tr>
		            <td class="first">交易品种</td>
		            <td class="font">上海期货交易所白银主力合约，交易代码AGM</td>
		          </tr>
		          <tr>
		            <td class="first">最小波动价</td>
		            <td class="font">1个指数点（15元）</td>
		          </tr>
		          <tr>
		            <td class="first">合约乘数</td>
		            <td class="font">15元/点</td>
		          </tr>
		          <tr>
		            <td class="first">交易时间</td>
		            <td class="font">9:05-14:55，21:05-23:55。不能跨时段交易。</td>
		          </tr>
		          <tr>
		            <td class="first">涨跌幅限制</td>
		            <td class="font">开盘价格上升或下跌6%</td>
		          </tr>
		          <tr>
		            <td class="first">操盘保证金</td>
		            <td class="font">用户出现亏损时的赔付，结束时如无亏损全额退还，保证金越高，平仓风险越低</td>
		          </tr>
		          <tr>
		            <td class="first">交易手续费</td>
		            <td class="font">用于支付交易佣金、印花税、过户费和实盘资金占用费</td>
		          </tr>
		          <tr>
		            <td class="first">账户管理费</td>
		            <td class="font">按交易日计算，当前免费。</td>
		          </tr>
		          <tr>
		            <td class="first">亏损平仓线</td>
		            <td class="font">总操盘资金低于亏损平仓线时，系统自动平仓。</td>
		          </tr>
		          <tr>
		            <td class="first">收益计算</td>
		            <td class="font">看多时：交易盈亏=（卖出价-买入价）* 15<br>看空时：交易盈亏=（买入价-卖出价）* 15</td>
		          </tr>		          
		          <tr>
		            <td class="first">交易软件</td>
		            <td class="font"><a href="${ctx}/help?tab=software&leftMenu=9" target="_blank">交易软件下载</a><a href="${ctx}/help?tab=rule&leftMenu=7" target="_blank">查看交易说明</a></td>
		          </tr>
		        </table>
		
		    </div>
		
		
		</div>
	</form>
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

