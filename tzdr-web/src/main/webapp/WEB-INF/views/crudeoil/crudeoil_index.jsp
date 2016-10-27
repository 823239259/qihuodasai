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
.font_size_15 {font-size: 15px; color: #f60;}
.font_size_22 {font-size: 22px; color: #f60;}
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
<title>国际原油-维胜金融-中国领先的国际期货及衍生品互联网交易平台</title>
<meta name="keywords" content="维胜，国际期货，原油，国际原油，期货，配资，原油配资，期货开户，外盘，期货公司，期货平台，炒期货，模拟盘"/>
<meta name="description" content="国际原油-国际原油是最重要的石油期货，是以远期石油价格为标的物品的期货品种。具有交易灵活，资金杠杆适合短线投资等特性。"/>
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
						<label>交易时间：</label><span><i>09:05-23:55<c style="font-size: 14px; color: #666;">（注意：请不要在交易时间外持单，以免被系统强制平仓）</c></i></span>
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
							</i>元/单边，买入、卖出一手时各收取该数额（人民币）的交易手续费。。
						</span>
					</div>
				</div>
				<div class="cp_bom">
					<p><a href="${ctx}/help?tab=rule&leftMenu=8" style="color:#fc3;" target="_blank">查看国际原油操盘细则,</a>若还有疑问，请联系客服：400-852-8008</p>
					<div class="cp_b_link">
						<input type="checkbox" checked="checked" id="checkbox_agree"ame="agree"> 
						<span>我已阅读并同意<a href="${ctx}/static/crudeoil/crudeoil.html" target="_blank">《国际原油操盘合作协议》</a></span>
					</div>
					<div class="cp_b_btn">
						<a href="javascript:void();" onclick="submitSetting()">提交操盘申请</a>
					</div>
				</div>
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

