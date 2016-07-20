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
 #guojiyuanyou {color: #ffcc33; border-bottom:2px solid #ffcc33; padding-bottom: 26px;}
</style>
<link href="${ctx}/static/css/public.css?v=${v}" rel="stylesheet" type="text/css">
<link href="${ctx}/static/css/trade.css?v=${v}" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/capital.css?v=${v}">
<script language="javascript" src="${ctx}/static/script/tzdr.js?v=${v}"></script>
<link href="${ctx}/static/css/pagination.css?v=${v}" rel="stylesheet" type="text/css" />
<script src="${ctx}/static/script/common/jquery.pagination.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript" src="${ctx}/static/script/crudeoil/crudeoilCommon.js?v=${v}"></script>
<script type="text/javascript" src="${ctx}/static/script/crudeoil/crudeoilIndex.js?v=${v}"></script>
<title>国际原油 - 维胜</title>
</head>
<body>
	<!--顶部 -->
	<%@include file="../common/header.jsp"%>
	<form action="${ctx}/usercrudeoil/pay" id="settingForm" method="post">
		<div class="capital">
			<div class="mc_nav">
				<a href="${ctx}/hsi/index">恒指期货</a>
				<a href="${ctx}/crudeoil/index" class="on">国际原油</a>
				<a href="${ctx}/ftse/index">富时A50</a>
		        <a href="${ctx}/outDisk/index">国际综合<i></i></a>
		    </div>
			<div class="capital_ctn">
				<div class="cp_main sif_main">
					<div class="cp_m_ctn">
		                <div class="cp_m_titl sif_m_title">
		                    <!-- <i></i> -->
		                    <h3 class="cp_h2">选择开仓手数<span>(可持仓的最大手数)</span></h3>
                    		<input type="hidden" id="input_tranLever" name="inputTranLever" value="${fSimpleConfigList[0].tranLever}" readonly="readonly" style="font-size:0;">
		                </div>
		                <div class="cp_m_list sif_m_list">
		                    <ul class="zk_ul_futrue_tranLever">
		                    	<c:forEach items="${fSimpleConfigList}" var="fSimpleConfig" varStatus="status">
		                    		<c:choose>
		                    			<c:when test="${status.first==true}">
		                    				<li class="on">
					                        	<input type="hidden" class="defaultVal"  data_traderBond="${fSimpleConfig.traderBond}" data_traderMoney="${fSimpleConfig.traderMoney}" data_lineLoss="${fSimpleConfig.lineLoss}" data_feeManage="${fSimpleConfig.feeManage}" data_tranFees="${fSimpleConfig.tranFees}" value="${fSimpleConfig.tranLever}" />
					                            <p><i>${fSimpleConfig.tranLever}</i>手</p>
					                            <span>
					                            	<c:choose>
					                            		<c:when test="${fSimpleConfig.tranLever eq 1}">可开仓${fSimpleConfig.tranLever}手</c:when>
					                            		<c:otherwise>可开仓1-${fSimpleConfig.tranLever}手</c:otherwise>
					                            	</c:choose>
					                            </span>
					                        </li>
		                    			</c:when>
		                    			<c:otherwise>
		                    				<li>
					                        	<input type="hidden" class="defaultVal" data_traderBond="${fSimpleConfig.traderBond}" data_traderMoney="${fSimpleConfig.traderMoney}" data_lineLoss="${fSimpleConfig.lineLoss}" data_feeManage="${fSimpleConfig.feeManage}" data_tranFees="${fSimpleConfig.tranFees}" value="${fSimpleConfig.tranLever}" />
					                            <p><i>${fSimpleConfig.tranLever}</i>手</p>
					                            <span>可开仓1-${fSimpleConfig.tranLever}手</span>
					                        </li>
		                    			</c:otherwise>
		                    		</c:choose>
		                    	</c:forEach>
		                    </ul>
		                </div>
		            </div>
		            <div class="cp_m_ctn">
		                <div class="cp_m_titl sif_m_title">
		                    <!-- <i></i> -->
		                    <h3 class="cp_h1">单手保证金<span>(操盘保证金越多，平仓风险越低)</span></h3>
	                   		<input type="hidden" id="input_traderBond" name="inputTraderBond" value="${fSimpleConfigList[0].traderBond}"  readonly="readonly" style="font-size:0;">
		                </div>
		                <div class="cp_m_list sif_m_list">
		                    <ul class="zk_ul_futrue_traderBond">
		                    	<c:if test="${!empty fSimpleConfigList}">
		                    		<li class="on">
			                        	<input type="hidden" class="defaultVal" value="${fSimpleConfigList[0].traderBond}" />
			                            <p><i><fmt:formatNumber value="${fSimpleConfigList[0].traderBond/fSimpleConfigList[0].tranLever}" pattern="#,###"></fmt:formatNumber></i>元</p>
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
						<label>操盘须知：</label> <span style="color:#333; font-size:16px;">美国轻质原油期货当期主力合约（${contract}）</span>
					</div>
					<div class="cp_sdfont">
						<label>交易时间：</label><span><i>9:05-23:55</i></span>
					</div>
					<div class="cp_sdfont">
		                <label>操盘保证金(¥)：</label>
		                <span>
		                	<i id="totalTraderBond_id">
	                			<fmt:formatNumber value="${fSimpleConfigList[0].traderBond}" pattern="#,###"></fmt:formatNumber>
		                	</i>元
		                </span>
		            </div>
					<div class="cp_sdfont">
						<label>总操盘资金($)：</label> 
						<span>
							<i id="totalTradeMoney_id">
	                			<fmt:formatNumber value="${fSimpleConfigList[0].traderMoney}" pattern="#,###"></fmt:formatNumber>
							</i>美元
						</span>
					</div>
					<div class="cp_sdfont">
						<label>亏损平仓线($)<!-- <a href="javascript:void(0);"></a> -->：</label> 
						<span>
							<i id="lineLoss_id">
	                			<fmt:formatNumber value="${fSimpleConfigList[0].lineLoss}" pattern="#,###"></fmt:formatNumber>
							</i>美元
						</span>
						<div class="uc_pay_promt uc_pay_promt2" style="display: none;">
							<i class="uc_pp_arrow"></i>
							<p>当总操盘资金低于平仓线以下时，我们将有权把您的股票进行平仓，为避免平仓发生，请时刻关注风险保证金是否充足。</p>
						</div>
					</div>
					<div class="cp_sdfont">
		                <label>账户管理费(¥)：</label>
		                <span>
		                	<i id="feeManage_id">
	                			<c:choose>
	                				<c:when test="${fSimpleConfigList[0].feeManage gt 0}">
	                					<fmt:formatNumber value="${fSimpleConfigList[0].feeManage}" pattern="#,###"></fmt:formatNumber>
	                				</c:when>
	                				<c:otherwise>
	                					免费
	                				</c:otherwise>
	                			</c:choose>
		                	</i>
               				<c:if test="${fSimpleConfigList[0].feeManage gt 0}">
               					元/交易日，14:00前即日计费，14:00后次日计费(以操盘账户短信发送时间为准)
               				</c:if>
		                </span>
		            </div>
					<div class="cp_sdfont">
						<label>交易手续费(¥)：</label> 
						<span>
							<i id="tranFees_id">
	                			<fmt:formatNumber value="${fSimpleConfigList[0].tranFees}" pattern="#,###"></fmt:formatNumber>
							</i>元/手，每手交易开仓和平仓的手续费。
						</span>
					</div>
				</div>
				<div class="cp_bom">
					<p>如您不清楚规则，或有其他疑问，请联系客服：400-852-8008</p>
					<div class="cp_b_link">
						<input type="checkbox" checked="checked" id="checkbox_agree"ame="agree"> 
						<span>我已阅读并同意<a href="${ctx}/static/crudeoil/crudeoil.html" target="_blank">《国际原油操盘合作协议》</a></span>
					</div>
					<div class="cp_b_btn">
						<a href="javascript:void();" onclick="submitSetting()">提交操盘申请</a>
					</div>
				</div>
			</div>
			<!--操盘须知  -->
			<div class="capital_rule">
		        <div class="cp_ru_icon"><img src="${ctx}/static/images/cp/icon_1.png"></div>
		        <h2>国际原油介绍：</h2>
		        <p>在石油期货合约之中，原油期货是交易量最大的品种，我们推出的国际原油是投资世界交易量最大、影响力最广泛的纽约商业交易所轻原油期货合约。该合约规格为每手1000桶，报价单位为美元/桶，价格波动最小单位为1美分，当初一推出后交易活跃，为有史以来最成功的国际商品期货合约，它的成交价格成为国际石油市场关注的焦点。国际原油投资特点：1，全球性市场，操作简单  2，T+0模式，每天可多次交易，增加获利几率  3，杠杆交易原理，以小博大  4，几乎24小时交易，时间自由   5，无涨停板和交割时间限制，特别适合做短线投资。</p>
		        <h2>交易细则</h2>        
		        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="cp_ru_list">
		          <tr>
		            <td class="first">风险提示</td>
		            <td class="font">投资有风险，入市需谨慎</td>
		          </tr>
		          <tr>
		            <td class="first">人民币兑换</td>
		            <td class="font">操盘资金按美元计价，人民币兑换美元，汇率按照中行当天第一开市时间来算，出入金按照现钞卖出价</td>
		          </tr>
		          <tr>
		            <td class="first">指数标的</td>
		            <td class="font">纽约商业交易所，原油期货</td>
		          </tr>
		          <tr>
		            <td class="first">最后交易日</td>
		            <td class="font">交割月份前一个月的25日是工作日，则该日之前倒数第三个交易日是最后交易日：如果交割月份的25日不是工作日，那么25日前倒数第四个交易日是最后交易日</td>
		          </tr>
		          <tr>
		            <td class="first">最小波动价</td>
		            <td class="font">0.01个指数点</td>
		          </tr>
		          <tr>
		            <td class="first">交易时间</td>
		            <td class="font">9:05-23:55</td>
		          </tr>
		          <tr>
		            <td class="first">涨跌幅限制</td>
		            <td class="font">±10美元，暂停交易5分钟后再扩大±10美元，以此循环</td>
		          </tr>
		          <tr>
		            <td class="first">操盘保证金</td>
		            <td class="font">用户出现亏损时的赔付，结束时如无亏损全额退还，保证金越高，平仓风险越低。</td>
		          </tr>
		          <tr>
		            <td class="first">交易手续费</td>
		            <td class="font">用于支付交易佣金、印花税、过户费和实盘资金占用费。</td>
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
		            <td class="first">交易软件</td>
		            <td class="font"><a href="${ctx}/help?tab=software&leftMenu=1" style="color:#fc3; padding-right:20px;" target="_blank">交易软件下载</a><a href="${ctx}/help?tab=rule&leftMenu=8" target="_blank"  style="color:#fc3; padding-right:20px;">查看交易说明</a></td>
		          </tr>
		        </table>
		
		    </div>
		</div>
	</form>
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

