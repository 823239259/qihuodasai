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
<%
String tranFees = "";
String futureLoanMoney = null;
ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());  
DataMapService data = context.getBean(DataMapService.class);
if (data != null) {
	List<DataMap> datas =  data.findByTypeKey("futureDayTranFees");
	List<DataMap> futureLoanMoneyDatas =  data.findByTypeKey("futureLoanMoney");
	if (datas.size() > 0) {
		DataMap mapObj = datas.get(0);
		tranFees = mapObj.getValueKey();
	}
	if (futureLoanMoneyDatas.size() > 0) {
		DataMap mapObj = futureLoanMoneyDatas.get(0);
		futureLoanMoney = mapObj.getValueKey();
	}
} 
%>
<link href="${ctx}/static/css/public.css?v=${v}" rel="stylesheet"
	type="text/css">
<link href="${ctx}/static/css/trade.css?v=${v}" rel="stylesheet"
	type="text/css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/css/capital.css?v=${v}">
<script language="javascript" src="${ctx}/static/script/tzdr.js?v=${v}"></script>
<link href="${ctx}/static/css/pagination.css?v=${v}" rel="stylesheet"
	type="text/css" />
<script src="${ctx}/static/script/common/jquery.pagination.js?v=${v}"
	type="text/javascript"></script>
<script language="javascript"
	src="${ctx}/static/script/trade/preByDay.js?v=${v}"></script>
<script type="text/javascript"
	src="${ctx}/static/script/futrue/futrue.js?v=${v}"></script>
<script type="text/javascript">
    function submitSetting() {
    	if ($("#checkbox_agree").attr("checked")) {
    		if(!isLoginSSO){
    			window.location.href=basepath+"/toFutureDayIndexSSO"; 
    		}else{
	    		$.easyui.submitForm("settingForm");
    		}
    	}
    	else {
    		showMsgDialog("提示","请勾选\"我已阅读并同意《投资达人股指期货合作协议》\"");
    		return false;
    	}
    }
    $(document).ready(function(){
    	$('.navlist li a').removeClass('on');
    	$("#future-index").addClass("on");
    	init();
		var traderBand ;
		var tranLever  ;
		//总操盘金额
		var traderTotal =<%=futureLoanMoney%>;
		//亏损平仓线=总操盘金-用户保证金+300*手数*10
		var warningLoss = 0;
    	$.easyui.rechangeSetValue(".zk_ul_futrue_traderBond li","#input_traderBond",function(tagObj){
    		var value = $(tagObj).find(".defaultVal");
    		var bond = $(value).val();
    		 $(".zk_ul_futrue_tranLever li" ).each(function(){
			    	$(this).removeClass("on");
			    });
    		if(bond==20000){
    			$("#20000").addClass("on");
    			 $("#input_tranLever").val("3");
    		}else if(bond==40000){
    			$("#40000").addClass("on");
    			$("#input_tranLever").val("5");
    		}else if(bond==70000){
    			$("#70000").addClass("on");
    			$("#input_tranLever").val("8");
    		}else if(bond==100000){
    			$("#100000").addClass("on");
    			$("#input_tranLever").val("10");
    		}
    		traderBand = $("#input_traderBond").val() - 0;
    		tranLever = $("#input_tranLever").val() - 0;
    		warningLoss = traderTotal*tranLever - traderBand+ 300*tranLever*10;
    		$("#totalTraderBond_id").html($.formatMoney(Number(traderBand)));
    		$("#totalTradeMoney_id").html($.formatMoney(Number(traderTotal*tranLever)));
    		$("#lineLoss_id").html($.formatMoney(Number(warningLoss)));
    		//$("#tranFees_id").html(tranFees);
    	});
        });
</script>
<title>期指操盘手 - 投资达人</title>
<script type="text/javascript">
function tradeContract(){
	var htmlHeight = 800;  //网页高度
	var htmlWidth = 1221;  //网页宽度
	var iTop = (window.screen.height-30-htmlHeight)/2; //获得窗口的垂直位置;  
	var iLeft = (window.screen.width-10-htmlWidth)/2;  //获得窗口的水平位置;  
	window.open(basepath+'tradeContract','随心配合作操盘协议','height='+htmlHeight+',innerHeight='+htmlHeight+',width='+htmlWidth+',innerWidth='+htmlWidth+',top='+iTop+',left='+iLeft+',toolbar=no,menubar=no,scrollbars=yes,resizeable=no,location=no,status=no');  
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
	<div class="floatlayer">
		<div class="fl_mask" style="display: none;"></div>
		<!-- 限制股弹出框 -->
		<div class="fl_cpdb" style="display: none;">
			<h2>今日限制股</h2>
			<ul class="fl_cpdblist">

			</ul>
			<div id="Pagination"></div>
			<div class="fl_cpdbtn">
				<a href="javascript:void(0);">我知道了</a>
			</div>
		</div>
	</div>
	<form action="${ctx}/future/day_pay" id="settingForm" method="post">
		<div class="capital">
			<div class="capital_ctn">
				<div class="cp_main sif_main">
		            <div class="cp_m_ctn">
		                <div class="cp_m_titl sif_m_title">
		                    <i></i>
		                    <h3 class="cp_h1">选择保证金<span>(保证金越多，可开仓最大手数越多)</span></h3>
		                    <input type="text" id="input_traderBond" name="inputTraderBond" value="20000"  readonly="readonly"  style="font-size:0;" />
		                </div>
		                <div class="cp_m_list sif_m_list">
		                    <ul class="zk_ul_futrue_traderBond">
		                        <li class="on">
		                        	<input type="hidden" class="defaultVal" value="20000" />
		                            <p><i>20,000</i>元</p>
		                            <span>操盘保证金额</span>
		                        </li>
		                        <li>
		                        	<input type="hidden" class="defaultVal" value="40000" />
		                            <p><i>40,000</i>元</p>
		                            <span>操盘保证金额</span>
		                        </li>
		                        <li>
		                        	<input type="hidden" class="defaultVal" value="70000" />
		                            <p><i>70,000</i>元</p>
		                            <span>操盘保证金额</span>
		                        </li>
		                        <li>
		                        	<input type="hidden" class="defaultVal" value="100000" />
		                            <p><i>100,000</i>元</p>
		                            <span>操盘保证金额</span>
		                        </li>
		                    </ul>
		                </div>
		            </div>
		            <div class="cp_m_ctn cp_m_ctn2">
		                <div class="cp_m_titl sif_m_title">
		                    <i></i>
		                    <h3 class="cp_h2">可开仓最大手数<span>(可持仓的最大手数)</span></h3>
		                    <input type="text" id="input_tranLever" name="inputTranLever" value="3" readonly="readonly"  style="font-size:0;" />
		                </div>
		                <div class="cp_m_list sif_m_list">
		                    <ul class="zk_ul_futrue_tranLever">
		                        <li class="on" id="20000">
		                        	<input type="hidden" class="defaultVal" value="3" />
		                            <p><i>3</i>手
		                            <span>可开仓1-3手</span></p>
		                        </li>
		                        <li id="40000">
		                        	<input type="hidden" class="defaultVal" value="5" />
		                            <p><i>5</i>手
		                            <span>可开仓1-5手</span></p>
		                        </li>
		                        <li id="70000">
		                        	<input type="hidden" class="defaultVal" value="8" />
		                            <p><i>8</i>手
		                            <span>可开仓1-8手</span></p>
		                        </li>
		                        <li id="100000">
		                        	<input type="hidden" class="defaultVal" value="10" />
		                            <p><i>10</i>手
		                            <span>可开仓1-10手</span></p>
		                        </li>
		                    </ul>
		                </div>
		            </div>
				</div>
				<div class="cp_sider sif_sider">
					<h2>确认操盘规则</h2>
					<div class="cp_sdfont">
						<label>操盘须知：</label> <span>投资沪深300当期主力合约，盈利全归您</span>
					</div>
					<div class="cp_sdfont">
		                <label>操盘保证金：</label>
		                <span><i id="totalTraderBond_id">9,000</i>元</span>
		            </div>
					<div class="cp_sdfont">
						<label>总操盘资金：</label> 
						<span><i id="totalTradeMoney_id">170,000</i>元</span>
					</div>
					<div class="cp_sdfont">
						<label>亏损平仓线<!-- <a href="javascript:void(0);"></a> -->：</label> 
						<span><i id="lineLoss_id">164,000</i>元</span>
						<div class="uc_pay_promt uc_pay_promt2" style="display: none;">
							<i class="uc_pp_arrow"></i>
							<p>当总操盘资金低于平仓线以下时，我们将有权把您的股票进行平仓，为避免平仓发生，请时刻关注风险保证金是否充足。</p>
						</div>
					</div>
					<div class="cp_sdfont">
						<label>交易手续费：</label> 
						<span><i id="tranFees_id"><%=tranFees%></i>元/手，每手交易开仓和平仓的手续费。</span>
					</div>
					<div class="cp_sdfont">
						<label>申报费用：
						</label> <span>中金所规定沪深300股指期货合约将收取申报费，每笔申报费1元；申报是指买入、卖出及撤销委托。</span>
					</div>
				</div>
				<div class="cp_bom">
					<p>如您不清楚规则，或有其他疑问，请联系客服：400-020-0158</p>
					<div class="cp_b_link">
						<input type="checkbox" checked="checked" id="checkbox_agree"
							name="agree"> <span>我已阅读并同意<a
							href="${ctx}/static/futrue/agree_day.html" target="_blank">《期指天天乐操盘委托协议》</a></span>
					</div>
					<div class="cp_b_btn">
						<a style="background:#eae9e9;">提交操盘申请</a>
					</div>
					<p style="color:#f00;">因中金所新规限制，投资达人期指操盘暂停申请，新开放时间待定</p>
				</div>
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

