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
<script type="text/javascript" src="${ctx}/static/script/crudeActive/crudeoilCommon.js?v=${v}"></script>
<script type="text/javascript" src="${ctx}/static/script/crudeActive/crudeoilIndex.js?v=${v}"></script>
<title>国际原油 - 投资达人</title>
<style type="text/css">
.step { width: 1000px; height: 75px; margin: 20px auto -10px; position: relative; }
.step_line { width: 1000px; height:2px; background:#eee; position:absolute; top:11px; left:0; }
.step ul { display: block; width: 100%; height: 75px; overflow: hidden;}
.step ul li { display: block; width:333px; height: 75px; position: relative; float: left; }
.step ul li span { display: block; width:24px; height: 24px; background: url(${ctx}/static/images/capital/step.png) no-repeat; text-align: center; line-height: 24px; color: #fff; margin:0 auto; position:relative; }
.step ul li p { width: 100%; height:50px; line-height: 50px; font-size: 16px; color: #999; text-align: center;  }
.step ul li.on span { background: url(${ctx}/static/images/capital/stepon.png) no-repeat;}
.step ul li.on p { color:#ff6500;font-weight: bold; }
.step ul li.on i { display: block; width: 100%; height: 2px; background: #ff6500; position: absolute; top: 11px; left: 0; }
</style>
</head>
<body>
	<!--顶部 -->
	<%@ include file="../common/personheader.jsp"%>
	<div class="step">
	  <div class="step_line"></div>
	  <ul>
	      <li class="on">
	        <i></i>
	        <span>1</span>
	        <p>参与免费体验</p>
	      </li>
	      <li>
	        <span>2</span>
	        <p>支付1元体验金</p>
	      </li>
	      <li>
	        <span>3</span>
	        <p>开始操盘</p>
	      </li>
	  </ul>  	
	</div>
	<form action="${ctx}/crudeActive/pay" id="settingForm" method="post">
		<div class="capital">
			<div class="capital_ctn">
				<div class="cp_main sif_main">
					<div class="cp_m_ctn">
		                <div class="cp_m_titl sif_m_title">
		                    <i></i>
		                    <h3 class="cp_h2">选择开仓手数<span>(可持仓的最大手数)</span></h3>
                    		<input type="text" id="input_tranLever" name="inputTranLever" value="1" readonly="readonly" style="font-size:0;">
		                </div>
		                <div class="cp_m_list sif_m_list">
		                    <ul class="zk_ul_futrue_tranLever">
		                    	<li class="on">
		                        	<input type="hidden" class="defaultVal" data_traderbond="2150.00" data_tradermoney="6500.00" data_lineloss="6440.00" data_feemanage="0.00" data_tranfees="125.00" value="1">
		                            <p><i>1</i>手</p>
		                            <span>可开仓1手</span>
		                        </li>
		                    	<li>
		                        	<input type="hidden" class="defaultVal" data_traderbond="6450.00" data_tradermoney="19500.00" data_lineloss="18990.00" data_feemanage="0.00" data_tranfees="125.00" value="3">
		                            <p><i>3</i>手</p>
		                            <span>可开仓1-3手</span>
		                        </li>
		                    	<li>
		                        	<input type="hidden" class="defaultVal" data_traderbond="10750.00" data_tradermoney="32500.00" data_lineloss="31650.00" data_feemanage="0.00" data_tranfees="125.00" value="5">
		                            <p><i>5</i>手</p>
		                            <span>可开仓1-5手</span>
		                        </li>
		                    	<li>
		                        	<input type="hidden" class="defaultVal" data_traderbond="17200.00" data_tradermoney="52000.00" data_lineloss="50640.00" data_feemanage="0.00" data_tranfees="125.00" value="8">
		                            <p><i>8</i>手</p>
		                            <span>可开仓1-8手</span>
		                        </li>
		                        <li>
		                        	<input type="hidden" class="defaultVal" data_traderbond="21500.00" data_tradermoney="65000.00" data_lineloss="63300.00" data_feemanage="0.00" data_tranfees="125.00" value="10">
		                            <p><i>10</i>手</p>
		                            <span>可开仓1-10手</span>
		                        </li>
		                        <li>
		                        	<input type="hidden" class="defaultVal" data_traderbond="32250.00" data_tradermoney="97500.00" data_lineloss="94950.00" data_feemanage="0.00" data_tranfees="125.00" value="15">
		                            <p><i>15</i>手</p>
		                            <span>可开仓1-15手</span>
		                        </li>
		                        <li>
		                        	<input type="hidden" class="defaultVal" data_traderbond="43000.00" data_tradermoney="130000.00" data_lineloss="126600.00" data_feemanage="0.00" data_tranfees="125.00" value="20">
		                            <p><i>20</i>手</p>
		                            <span>可开仓1-20手</span>
		                        </li>
		                    </ul>
		                </div>
		            </div>
		            <div class="cp_m_ctn">
		                <div class="cp_m_titl sif_m_title">
		                    <i></i>
		                    <h3 class="cp_h1">单手保证金<span>(操盘保证金越多，平仓风险越低)</span></h3>
	                    		<input type="text" id="input_traderBond" name="inputTraderBond" value="2150.00"  readonly="readonly" style="font-size:0;">
		                </div>
		                <div class="cp_m_list sif_m_list">
		                    <ul class="zk_ul_futrue_traderBond">
		                    	<li class="on">
		                        	<input type="hidden" class="defaultVal" value="2150">
		                            <p><i>2,150</i>元</p>
		                            <span>操盘保证金额</span>
		                        </li>
		                    </ul>
		                </div>
		            </div>
		        </div>
				<div class="cp_sider sif_sider">
					<h2>确认操盘规则</h2>
					<div class="cp_sdfont">
						<label>操盘须知：</label> <span style="color:#ff6500; font-size:16px;">投资美国轻质原油期货当期主力合约（${contract}）</span>
					</div>
					<div class="cp_sdfont">
						<label>交易时间：</label><span><i>9:05-2:25</i></span>
					</div>
					<div class="cp_sdfont">
		                <label>操盘保证金(¥)：</label>
		                <span>
		                	<i id="totalTraderBond_id">
	                			<fmt:formatNumber value="2150.00" pattern="#,###"></fmt:formatNumber>
		                	</i>元
		                </span>
		            </div>
					<div class="cp_sdfont">
						<label>总操盘资金($)：</label> 
						<span>
							<i id="totalTradeMoney_id">
	                			<fmt:formatNumber value="6500.00" pattern="#,###"></fmt:formatNumber>
							</i>美元
						</span>
					</div>
					<div class="cp_sdfont">
						<label>亏损平仓线($)：</label> 
						<span>
							<i id="lineLoss_id">
	                			<fmt:formatNumber value="6440" pattern="#,###"></fmt:formatNumber>
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
		                	<i id="feeManage_id">免费</i>
		                </span>
		            </div>
					<div class="cp_sdfont">
						<label>交易手续费(¥)：</label> 
						<span>
							<i id="tranFees_id">
	                			<fmt:formatNumber value="125.00" pattern="#,###"></fmt:formatNumber>
							</i>元/手，每手交易开仓和平仓的手续费。
						</span>
					</div>
				</div>
				<div class="cp_bom">
					<p>如您不清楚规则，或有其他疑问，请联系客服：400-633-9257</p>
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
		        <div class="cp_ru_icon"><img src="${ctx}/static/images/cp/icon.png"></div>
		        <h2>国际原油介绍：</h2>
		        <p>在石油期货合约之中，原油期货是交易量最大的品种，我们推出的国际原油是投资世界交易量最大、影响力最广泛的纽约商业交易所轻原油期货合约。该合约规格为每手1000桶，报价单位为美元/桶，价格波动最小单位为1美分，当初一推出后交易活跃，为有史以来最成功的国际商品期货合约，它的成交价格成为国际石油市场关注的焦点。国际原油投资特点：1，全球性市场，操作简单  2，T+0模式，每天可多次交易，增加获利几率  3，杠杆交易原理，以小博大  4，几乎24小时交易，时间自由   5，无涨停板和交割时间限制，特别适合做短线投资。</p>
		        <h2>交易细则</h2>        
		        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="cp_ru_list">
		          <tr>
		            <td class="first">风险提示</td>
		            <td class="font">股市有风险，投资需谨慎</td>
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
		            <td class="font">9:05-2:25</td>
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
		            <td class="font">用于支付交易佣金、印花税、过户费和实盘资金占用费。每一手交易手续费为180元。</td>
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
		            <td class="font"><a href="${ctx}/help?tab=software&leftMenu=8" style="color:#3d9fe1; padding-right:20px;" target="_blank">交易软件下载</a><a href="${ctx}/help?tab=rule&leftMenu=8" target="_blank"  style="color:#3d9fe1; padding-right:20px;">查看交易说明</a></td>
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

