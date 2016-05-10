<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>
<%@include file="../common/import-artDialog-js.jspf"%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="zh">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta content="投资达人提供股票操盘、P2P贷款、投资理财、股指期货等交易服务，更高的收益，更加安全可靠；投资达人致力于打造中国领先的互联网金融交易平台." name="description">
<style type="text/css">
 .font_size_15 {font-size: 15px; color: #f60; }
 .font_size_22 {font-size: 22px; color: #f60; }                       
</style>
<link href="${ctx}/static/css/common.css?v=${v}" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/css/trade.css?v=${v}" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/css/tzdr.css?v=${v}" rel="stylesheet" type="text/css" />	
<script language="javascript" src="${ctx}/static/script/tzdr.js?v=${v}"></script>
<script language="javascript" src="${ctx}/static/script/common/tzdr.user.js?v=${v}"></script>


<script type="text/javascript" src="${ctx}/static/script/product/commodity_pay.js?v=${v}"></script>
<script type="text/javascript" src="${ctx}/static/script/ftse/ftseCommon.js?v=${v}"></script>
<%-- <script type="text/javascript" src="${ctx}/static/script/outDisk/pay.js?v=${v}"></script>
<script type="text/javascript" src="${ctx}/static/script/ftse/ftseCommon.js?v=${v}"></script> --%>
<!-- 商品综合支付 -->
<title>支付确认 - 投资达人</title>
</head>
<body>
	<!--顶部 -->
	<%@ include file="../common/personheader.jsp"%>
	<div class="floatlayer" id="notPay" style="display:none;">
		<div class="fl_mask"></div>
		<div class="fl_box fl_uc_bank">
            <div class="fl_navtitle">
                <h3 class="fl_logintitle"></h3><a href="javascript:void(0);" class="close" id="notPay_close" ></a>
            </div>
            <div class="fl_uc_main">
                <p class="fl_promtfont">您的账户余额只剩<i>${avlBal_user}</i>元<br>本次支付还差<i>${payable_avlBal_user}</i>元</p>
            </div>
            <div class="fl_loginbtn">
                <a href="${ctx}/pay/payinfo" class="fl_loginbtn">立即充值</a>
            </div>
        </div>	
	</div>
    <!-- 确认支付 -->
    <div class="floatlayer" id="confirmPayDiv" style="display:none;">
		<div class="fl_mask"></div>
		<div class="fl_box fl_pay">
            <div class="fl_navtitle">
                <h3 class="fl_logintitle">确认支付</h3><a href="javascript:void(0);" class="close" onclick="colseConfirmPayDiv();"></a>
            </div>
            <div class="fl_uc_main">
            	<ul class="fl_paylist">
            		<li><label>账户余额：</label><span><i id="banlance"><fmt:formatNumber value="${avlBal}" pattern="###.##" /></i>元</span></li>
            		<li><label>支付金额：</label><span><i id="payable"><fmt:formatNumber value="${payable}" pattern="###.##" /></i>元</span></li>
            		<input type="hidden" id="payableFiexd" value="${payable}" />
            		<li>
            			<label>代金券：</label>
	            		<select id="voucher">
	                		<c:choose>
	                			<c:when test="${fn:length(voucher) == 0 }">
	                				<option value="">无代金券</option>
	                			</c:when>
	                			<c:otherwise>
	                				<c:forEach var="vo" items="${voucher }">
			                			<option value="${vo.id }">${vo.money }元代金券</option>
			                		</c:forEach>
		                			<option value="">不使用代金券</option>
	                			</c:otherwise>
	                		</c:choose>
	                	</select>
                	</li>
            	</ul>
            	<p id="notEnoughPay" class="fl_cgpromt" style="display: none;">余额不足，<a href="${ctx}/pay/payinfo" class="fl_loginbtn">请立即充值！</a></p>       
            </div>
            <div class="fl_loginbtn">
                <a href="javascript:void(0);" onclick="colseConfirmPayDiv();" class="fl_pb_cancle">取消</a>
                <a href="javascript:void(0);" status="true" id="confirmPay" class="fl_pb_sure">确定支付</a>
            </div>            
            <p class="fl_promtfont">注：代金券一般由达人平台活动发放，请大家多多关注。</p>
        </div>	
    </div>
	<div class="capital">
		<form action="${ctx}/userCommodity/paySuccessful" id="payableForm" method="post">
			<input type="hidden" name="traderBondAttr" value="${traderBond}" />
			<input type="hidden" name="tokenTzdr" value="${tokenTzdr}" />
			<input type="hidden" id="voucherId" name="voucherId" value="" />
			<div class="cp_ctn">
			<input type="hidden" id="myhidden" value="${comprehensiveCommodityPrice}"/>
			
			
				<!-- 支付配资费用 -->	
				<div class="cp_payctn">	
					<div class="cp_paytitle">
						方案详情
					</div>
					<ul class="cp_paylist cp_ovlist cpp_ovlist">
						<li>
							<h3>总操盘资金（￥）</h3>
							<p><i>${traderTotal}</i>元</p>
						</li>
						<li class="big">
							<h3>交易手续费(￥)</h3>
							<p >
								<a  href="${ctx}/commodity/index#font" target="_blank">查看各种交易手续费</a>
								<%-- <c:forEach items="${comprehensiveCommodityPrice}" var="comprehensiveCommodityPrice" varStatus="status">
				              		<c:if test="${comprehensiveCommodityPrice.tradeType==0}">
				              			白银<i><fmt:formatNumber value="${comprehensiveCommodityPrice.price}" pattern="#,###"></fmt:formatNumber></i>元/手、
									</c:if>
									<c:if test="${comprehensiveCommodityPrice.tradeType==1}">
				              			铝<i><fmt:formatNumber value="${comprehensiveCommodityPrice.price}" pattern="#,###"></fmt:formatNumber></i>元/手、
									</c:if>
									<c:if test="${comprehensiveCommodityPrice.tradeType==2}">
				              			黄金<i><fmt:formatNumber value="${comprehensiveCommodityPrice.price}" pattern="#,###"></fmt:formatNumber></i>元/手、
									</c:if>
									<c:if test="${comprehensiveCommodityPrice.tradeType==3}">
				              			沥青<i><fmt:formatNumber value="${comprehensiveCommodityPrice.price}" pattern="#,###"></fmt:formatNumber></i>元/手、
									</c:if>
									<c:if test="${comprehensiveCommodityPrice.tradeType==4}">
				              			铜<i><fmt:formatNumber value="${comprehensiveCommodityPrice.price}" pattern="#,###"></fmt:formatNumber></i>元/手、
									</c:if>
									<c:if test="${comprehensiveCommodityPrice.tradeType==5}">
				              			热卷<i><fmt:formatNumber value="${comprehensiveCommodityPrice.price}" pattern="#,###"></fmt:formatNumber></i>元/手、
									</c:if>
									<c:if test="${comprehensiveCommodityPrice.tradeType==6}">
				              			镍<i><fmt:formatNumber value="${comprehensiveCommodityPrice.price}" pattern="#,###"></fmt:formatNumber></i>元/手、
									</c:if>
									<c:if test="${comprehensiveCommodityPrice.tradeType==7}">
				              			螺纹钢<i><fmt:formatNumber value="${comprehensiveCommodityPrice.price}" pattern="#,###"></fmt:formatNumber></i>元/手、
									</c:if>
									<c:if test="${comprehensiveCommodityPrice.tradeType==8}">
				              			锌<i><fmt:formatNumber value="${comprehensiveCommodityPrice.price}" pattern="#,###"></fmt:formatNumber></i>元/手、
									</c:if>
									<c:if test="${comprehensiveCommodityPrice.tradeType==9}">
				              			橡胶<i><fmt:formatNumber value="${comprehensiveCommodityPrice.price}" pattern="#,###"></fmt:formatNumber></i>元/手、
									</c:if>
									<c:if test="${comprehensiveCommodityPrice.tradeType==10}">
				              			豆一<i><fmt:formatNumber value="${comprehensiveCommodityPrice.price}" pattern="#,###"></fmt:formatNumber></i>元/手、
									</c:if>
									<c:if test="${comprehensiveCommodityPrice.tradeType==11}">
				              			玉米<i><fmt:formatNumber value="${comprehensiveCommodityPrice.price}" pattern="#,###"></fmt:formatNumber></i>元/手、
									</c:if>
									<c:if test="${comprehensiveCommodityPrice.tradeType==12}">
				              			玉米淀粉<i><fmt:formatNumber value="${comprehensiveCommodityPrice.price}" pattern="#,###"></fmt:formatNumber></i>元/手、
									</c:if>
									<c:if test="${comprehensiveCommodityPrice.tradeType==13}">
				              			铁矿石<i><fmt:formatNumber value="${comprehensiveCommodityPrice.price}" pattern="#,###"></fmt:formatNumber></i>元/手、
									</c:if>
									<c:if test="${comprehensiveCommodityPrice.tradeType==14}">
				              			焦炭<i><fmt:formatNumber value="${comprehensiveCommodityPrice.price}" pattern="#,###"></fmt:formatNumber></i>元/手、
									</c:if>
									<c:if test="${comprehensiveCommodityPrice.tradeType==15}">
				              			鸡蛋<i><fmt:formatNumber value="${comprehensiveCommodityPrice.price}" pattern="#,###"></fmt:formatNumber></i>元/手、
									</c:if>
									<c:if test="${comprehensiveCommodityPrice.tradeType==16}">
				              			焦煤<i><fmt:formatNumber value="${comprehensiveCommodityPrice.price}" pattern="#,###"></fmt:formatNumber></i>元/手、
									</c:if>
									<c:if test="${comprehensiveCommodityPrice.tradeType==17}">
				              			塑料<i><fmt:formatNumber value="${comprehensiveCommodityPrice.price}" pattern="#,###"></fmt:formatNumber></i>元/手、
									</c:if>
									<c:if test="${comprehensiveCommodityPrice.tradeType==18}">
				              			豆粕<i><fmt:formatNumber value="${comprehensiveCommodityPrice.price}" pattern="#,###"></fmt:formatNumber></i>元/手、
									</c:if>
									<c:if test="${comprehensiveCommodityPrice.tradeType==19}">
				              			棕榈油<i><fmt:formatNumber value="${comprehensiveCommodityPrice.price}" pattern="#,###"></fmt:formatNumber></i>元/手、
									</c:if>
									<c:if test="${comprehensiveCommodityPrice.tradeType==20}">
				              			聚丙烯<i><fmt:formatNumber value="${comprehensiveCommodityPrice.price}" pattern="#,###"></fmt:formatNumber></i>元/手、
									</c:if>
									<c:if test="${comprehensiveCommodityPrice.tradeType==21}">
				              			豆油<i><fmt:formatNumber value="${comprehensiveCommodityPrice.price}" pattern="#,###"></fmt:formatNumber></i>元/手、
									</c:if>
									<c:if test="${comprehensiveCommodityPrice.tradeType==22}">
				              			棉花<i><fmt:formatNumber value="${comprehensiveCommodityPrice.price}" pattern="#,###"></fmt:formatNumber></i>元/手、
									</c:if>
									<c:if test="${comprehensiveCommodityPrice.tradeType==23}">
				              			玻璃<i><fmt:formatNumber value="${comprehensiveCommodityPrice.price}" pattern="#,###"></fmt:formatNumber></i>元/手、
									</c:if>
									<c:if test="${comprehensiveCommodityPrice.tradeType==24}">
				              			甲醇<i><fmt:formatNumber value="${comprehensiveCommodityPrice.price}" pattern="#,###"></fmt:formatNumber></i>元/手、
									</c:if>
									<c:if test="${comprehensiveCommodityPrice.tradeType==25}">
				              			菜油<i><fmt:formatNumber value="${comprehensiveCommodityPrice.price}" pattern="#,###"></fmt:formatNumber></i>元/手、
									</c:if>
									<c:if test="${comprehensiveCommodityPrice.tradeType==26}">
				              			菜粕<i><fmt:formatNumber value="${comprehensiveCommodityPrice.price}" pattern="#,###"></fmt:formatNumber></i>元/手、
									</c:if>
									<c:if test="${comprehensiveCommodityPrice.tradeType==27}">
				              			白糖<i><fmt:formatNumber value="${comprehensiveCommodityPrice.price}" pattern="#,###"></fmt:formatNumber></i>元/手、
									</c:if>
									<c:if test="${comprehensiveCommodityPrice.tradeType==28}">
				              			PTA<i><fmt:formatNumber value="${comprehensiveCommodityPrice.price}" pattern="#,###"></fmt:formatNumber></i>元/手、
									</c:if>
									<c:if test="${comprehensiveCommodityPrice.tradeType==29}">
				              			动力煤<i><fmt:formatNumber value="${comprehensiveCommodityPrice.price}" pattern="#,###"></fmt:formatNumber></i>元/手、
									</c:if>
									<c:if test="${comprehensiveCommodityPrice.tradeType==30}">
				              			5年期国债<i><fmt:formatNumber value="${comprehensiveCommodityPrice.price}" pattern="#,###"></fmt:formatNumber></i>元/手、
									</c:if>
									<c:if test="${comprehensiveCommodityPrice.tradeType==31}">
				              			10年期国债<i><fmt:formatNumber value="${comprehensiveCommodityPrice.price}" pattern="#,###"></fmt:formatNumber></i>元/手、
									</c:if>
				            	</c:forEach> --%>
				            	
				            	
				            	
				            	
				            	
				            	
				            	
				            	
				            	
								<%-- 富时A50<i><fmt:formatNumber value="${outDiskPrice[0].price}" pattern="#,###"></fmt:formatNumber></i>元/手、
								恒指期货<i><fmt:formatNumber value="${outDiskPrice[2].price}" pattern="#,###"></fmt:formatNumber></i></i>元/手、
								国际原油<i><fmt:formatNumber value="${outDiskPrice[1].price}" pattern="#,###"></fmt:formatNumber></i></i>元/手、
								迷你道琼<i><fmt:formatNumber value="${outDiskPrice[3].price}" pattern="#,###"></fmt:formatNumber></i></i>元/手、
								迷你纳斯达克<i><fmt:formatNumber value="${outDiskPrice[4].price}" pattern="#,###"></fmt:formatNumber></i></i>元/手、
								迷你普尔<i><fmt:formatNumber value="${outDiskPrice[5].price}" pattern="#,###"></fmt:formatNumber></i></i>元/手、
								德国DAX<i><fmt:formatNumber value="${outDiskPrice[6].price}" pattern="#,###"></fmt:formatNumber></i></i>元/手、
								日经225<i><fmt:formatNumber value="${outDiskPrice[7].price}" pattern="#,###"></fmt:formatNumber></i></i>元/手 --%>
							</p>
						</li>
						<li>
							<h3>账户管理费(￥)</h3>
							<p><i>免费</i></p>
						</li>
					</ul>
					<p class="cp_paypromt" style="display:none;"></p>		
					<div class="cp_paytitle">风险规则</div>
					<ul class="cp_paylist cp_ovpayrule cpp_ovpayrule">
						<li>							
							<h3>操盘须知</h3>
							<p class="big">
							<a href="${ctx}/help?tab=rule&leftMenu=2" target="_blank">查看各品种操盘细则</a>
							<%-- <c:forEach items="${comprehensiveCommodityPrice}" var="comprehensiveCommodityPrice" varStatus="status">
				              		<c:if test="${comprehensiveCommodityPrice.tradeType==0}">
				              			白银<i>(${comprehensiveCommodityPrice.mainContract})</i>、
									</c:if>
									<c:if test="${comprehensiveCommodityPrice.tradeType==1}">
				              			铝<i>(${comprehensiveCommodityPrice.mainContract})</i>、
									</c:if>
									<c:if test="${comprehensiveCommodityPrice.tradeType==2}">
				              			黄金<i>(${comprehensiveCommodityPrice.mainContract})</i>、
									</c:if>
									<c:if test="${comprehensiveCommodityPrice.tradeType==3}">
				              			沥青<i>(${comprehensiveCommodityPrice.mainContract})</i>、
									</c:if>
									<c:if test="${comprehensiveCommodityPrice.tradeType==4}">
				              			铜<i>(${comprehensiveCommodityPrice.mainContract})</i>、
									</c:if>
									<c:if test="${comprehensiveCommodityPrice.tradeType==5}">
				              			热卷<i>(${comprehensiveCommodityPrice.mainContract})</i>、
									</c:if>
									<c:if test="${comprehensiveCommodityPrice.tradeType==6}">
				              			镍<i>(${comprehensiveCommodityPrice.mainContract})</i>、
									</c:if>
									<c:if test="${comprehensiveCommodityPrice.tradeType==7}">
				              			螺纹钢<i>(${comprehensiveCommodityPrice.mainContract})</i>、
									</c:if>
									<c:if test="${comprehensiveCommodityPrice.tradeType==8}">
				              			锌<i>(${comprehensiveCommodityPrice.mainContract})</i>、
									</c:if>
									<c:if test="${comprehensiveCommodityPrice.tradeType==9}">
				              			橡胶<i>(${comprehensiveCommodityPrice.mainContract})</i>、
									</c:if>
									<c:if test="${comprehensiveCommodityPrice.tradeType==10}">
				              			豆一<i>(${comprehensiveCommodityPrice.mainContract})</i>、
									</c:if>
									<c:if test="${comprehensiveCommodityPrice.tradeType==11}">
				              			玉米<i>(${comprehensiveCommodityPrice.mainContract})</i>、
									</c:if>
									<c:if test="${comprehensiveCommodityPrice.tradeType==12}">
				              			玉米淀粉<i>(${comprehensiveCommodityPrice.mainContract})</i>、
									</c:if>
									<c:if test="${comprehensiveCommodityPrice.tradeType==13}">
				              			铁矿石<i>(${comprehensiveCommodityPrice.mainContract})</i>、
									</c:if>
									<c:if test="${comprehensiveCommodityPrice.tradeType==14}">
				              			焦炭<i>(${comprehensiveCommodityPrice.mainContract})</i>、
									</c:if>
									<c:if test="${comprehensiveCommodityPrice.tradeType==15}">
				              			鸡蛋<i>(${comprehensiveCommodityPrice.mainContract})</i>、
									</c:if>
									<c:if test="${comprehensiveCommodityPrice.tradeType==16}">
				              			焦煤<i>(${comprehensiveCommodityPrice.mainContract})</i>、
									</c:if>
									<c:if test="${comprehensiveCommodityPrice.tradeType==17}">
				              			塑料<i>(${comprehensiveCommodityPrice.mainContract})</i>、
									</c:if>
									<c:if test="${comprehensiveCommodityPrice.tradeType==18}">
				              			豆粕<i>(${comprehensiveCommodityPrice.mainContract})</i>、
									</c:if>
									<c:if test="${comprehensiveCommodityPrice.tradeType==19}">
				              			棕榈油<i>(${comprehensiveCommodityPrice.mainContract})</i>、
									</c:if>
									<c:if test="${comprehensiveCommodityPrice.tradeType==20}">
				              			聚丙烯<i>(${comprehensiveCommodityPrice.mainContract})</i>、
									</c:if>
									<c:if test="${comprehensiveCommodityPrice.tradeType==21}">
				              			豆油<i>(${comprehensiveCommodityPrice.mainContract})</i>、
									</c:if>
									<c:if test="${comprehensiveCommodityPrice.tradeType==22}">
				              			棉花<i>(${comprehensiveCommodityPrice.mainContract})</i>、
									</c:if>
									<c:if test="${comprehensiveCommodityPrice.tradeType==23}">
				              			玻璃<i>(${comprehensiveCommodityPrice.mainContract})</i>、
									</c:if>
									<c:if test="${comprehensiveCommodityPrice.tradeType==24}">
				              			甲醇<i>(${comprehensiveCommodityPrice.mainContract})</i>、
									</c:if>
									<c:if test="${comprehensiveCommodityPrice.tradeType==25}">
				              			菜油<i>(${comprehensiveCommodityPrice.mainContract})</i>、
									</c:if>
									<c:if test="${comprehensiveCommodityPrice.tradeType==26}">
				              			菜粕<i>(${comprehensiveCommodityPrice.mainContract})</i>、
									</c:if>
									<c:if test="${comprehensiveCommodityPrice.tradeType==27}">
				              			白糖<i>(${comprehensiveCommodityPrice.mainContract})</i>、
									</c:if>
									<c:if test="${comprehensiveCommodityPrice.tradeType==28}">
				              			PTA<i>(${comprehensiveCommodityPrice.mainContract})</i>、
									</c:if>
									<c:if test="${comprehensiveCommodityPrice.tradeType==29}">
				              			动力煤<i>(${comprehensiveCommodityPrice.mainContract})</i>、
									</c:if>
									<c:if test="${comprehensiveCommodityPrice.tradeType==30}">
				              			5年期国债<i>(${comprehensiveCommodityPrice.mainContract})</i>、
									</c:if>
									<c:if test="${comprehensiveCommodityPrice.tradeType==31}">
				              			10年期国债<i>(${comprehensiveCommodityPrice.mainContract})</i>、
									</c:if>
				            	</c:forEach>
								当期主力合约 --%>
							
							
							
							
							
							
							
							
							
							
							
							
							<%-- 投资国际期货富时A50<i>(${outDiskPrice[0].mainContract})</i>、
							恒指期货<i>(${outDiskPrice[2].mainContract})</i>、
							国际原油<i>(${outDiskPrice[1].mainContract})</i>、
							迷你道琼<i>(${outDiskPrice[3].mainContract})</i>、
							迷你纳斯达克<i>(${outDiskPrice[4].mainContract})</i>、
							迷你标普<i>(${outDiskPrice[5].mainContract})</i>、
							德国DAX<i>(${outDiskPrice[6].mainContract})</i>、
							日经225<i>(${outDiskPrice[7].mainContract})</i>当期主力合约 --%>
							</p>
						</li>
						<li>							
							<h3>亏损平仓线(￥)</h3>
							<p><i>${lineLoss}</i>元</p>
						</li>
					</ul>
					<div class="cp_paytitle">支付清单</div>
					<div class="cp_paymoneybox">
						<ul class="cp_paylist cp_paymoneylist">
							<li>
								<h3>操盘保证金(￥)</h3>
								<p><i>${traderBond}</i>元</p>
							</li>
							<li>
								<h3>账户管理费(￥)</h3>
								<p><i>免费</i></p>
							</li>
						</ul>
						<div class="cp_paymoneyic">=</div>
						<ul class="cp_paylist cp_paymoneyinfo">
							<li>
								<h3>应付金额(￥)</h3>
								<p><i>￥${payable}</i>元</p>
							</li>
						</ul>
					</div>
					<div class="cp_paybtn">
						<input type="submit" style="display:none;" id="forPaySubmit" />
						<div class="uc_paybtn cp_btnsure"><a href="javascript:void(0);" id="pay_button" >下一步</a></div>
						<div class="cp_btnback"><a href="${ctx}/commodity/index">返回修改</a></div>
					</div>
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

