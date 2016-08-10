<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>
<%@include file="../common/import-fileupload-js.jspf"%>
<%@include file="../common/import-artDialog-js.jspf"%>
<%
	String tab = request.getParameter("tab");
%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="zh">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>我的邀请码 - 维胜</title>
<link rel="stylesheet" href="${ctx}/static/css/uc.css?version=20150721" type="text/css">
<link href="${ctx}/static/css/public.css" rel="stylesheet"
	type="text/css">
<link href="${ctx}/static/css/pagination.css" rel="stylesheet"
	type="text/css" />
<script src="${ctx}/static/script/common/jquery.pagination.js"
	type="text/javascript"></script>
	<!-- 
<link href="common/tablesorter.css" rel="stylesheet" type="text/css" />
	 -->
<script src="${ctx}/static/script/My97DatePicker/WdatePicker.js"
	type="text/javascript"></script>

<script type='text/javascript'
	src="${ctx}/static/script/fund/invitationCode.js?version=20150721"></script>
<script type='text/javascript'
	src="${ctx}/static/script/fund/share.js?version=20150721"></script>
<script type='text/javascript'
	src="${ctx}/static/script/common/dateUtils.js"></script>
<script type='text/javascript'
	src="${ctx}/static/script/common/ZeroClipboard.min.js"></script>
<script type="text/javascript" src="${ctx}/static/script/tzdr.js"></script>
<title>维胜</title>
<script type="text/javascript">
	var tab =<%=tab%>;

	var isOut = false;

	$(document).ready(function() {
		$('#paytype').click(function(event) {
			event.stopPropagation();
			$("#paytypediv").show();
		});

		$(document).click(function() {
			if (isOut == false) {
				$("#alltypediv").hide();
				$("#paytypediv").hide();
			}
		});

		$('#alltype').click(function(event) {
			event.stopPropagation();
			$("#alltypediv").show();
		});
		
		$("#alltypediv a").each(function() {
			$(this).click(function() {
				$('#fundtype').attr("data-id", $(this).attr("data-id"));
				var value = $(this).html();
				$('#fundtype').val(value);
				$('#alltypediv').hide();
			});

		});
		$("#Pagination").html("");
		var iphone=$("#iphone").val();
		var starttime=$("#allstarttime").val();
		var endtime=$("#allendtime").val();
		var type=$("#fundtype").attr("data-id");
		if(starttime!="" && endtime!=""){
			if(starttime>endtime){
				showMsgDialog("提示","开始时间不能大于结束时间");
				return;
			}
		}
		
		getTitleData(starttime,endtime,type,"incount","insummoney");
		
		var index="${index}";
		if(index){
			$("#fundtab ul.uc_paynav a").eq(index).trigger("click");
		}
		
		
	});
</script>
<style>
	#nav_my {color: #ffcc33; border-bottom:2px solid #ffcc33; padding-bottom: 26px;}
	.uc_fsmoney {width: 100%; height: 30px; line-height: 30px;}
	.uc_fssearch {width: 100%;}
	.uc_fs_type {left: 239px; top: 80px; width: 115px;}
</style>
</head>
<body>
	<!--顶部 -->
	<!--个人中心导航 -->
	<%@include file="../common/header.jsp"%>
	<div class="uc">
		<!--个人中心导航 -->
		<%@ include file="../common/leftnav.jsp"%>
		<!--网银支付-->
		<div class="uc_mianbar">
			<div class="uc_pay" id="fundtab">
				<ul class="uc_paynav">
					<li><a href="javascript:void(0)" class="on">我的邀请码</a></li>
					<li><a href="javascript:void(0)">佣金明细</a></li>
				</ul>
				<div class="tabcon">
					<div class="subtab">
						<div class="my_invitationCode">
							<p class="my_jieshao">以下网址是 您对外界进行推广的地址，您可以通过朋友、QQ、微信、博客进行推广，所有通过该地址访问过来的人，注册就都属于您的用户，而当这些用户在本站股票操盘时，您就可以赚取佣金了。</p>
							<p class="fzwz"><span class="wangzhi">http//www.vs.com</span><span class="copy">复制链接地址</span></p>
							<textarea maxlength="3000"></textarea>
							<p><span class="fenxiang">分享到: </span>
                    			<a href="#" onclick="shareToSinaWB(event)" title="分享到新浪微博"><img alt="" src="${ctx}/static/images/uc/fenxiang_sina.jpg"></a>
                    			<a href="#" onclick="shareToQQwb(event)" title="分享到腾讯微博"><img alt="" src="${ctx}/static/images/uc/fenxiang_tengxun.jpg"></a>
                    			<a href="#"  title="分享到微信"><img alt="" src="${ctx}/static/images/uc/fenxiang_weixin.jpg"></a>
                    			<a href="#" onclick="shareQQ(event)"  title="分享到QQ好友"><img alt="" src="${ctx}/static/images/uc/fenxiang_qq.jpg"></a>
                    			<a href="#" onclick="shareToQzone(event)" title="分享到QQ空间"><img alt="" src="${ctx}/static/images/uc/fenxiang_zone.jpg"></a>
                    		</p>
						</div>
					</div>
					<div class="subtab">
						<!-- 全部明细 -->
						<div class="uc_trade">
							<!-- 浮动层 -->
							<div class="uc_floatlayer">
								<!-- 类型 -->
								<div class="uc_bc_option uc_fs_type" id="alltypediv"
									style="display: none;">
									<a href="javascript:void(0)" data-id="">佣金收入</a>
									<a href="javascript:void(0)" data-id="3">佣金收入</a>
									<c:forEach items="${data}" var="type" varStatus="status">
										<a href="javascript:void(0)" data-id="${type['valueKey']}">${type['valueName']}</a>
									</c:forEach>
								</div>

							</div>
							<div class="uc_fsdetails">
								<div class="uc_fsmoney">
									<p>
										收入<i class="color1" id="incount"><c:choose><c:when test="${!empty infund.count}">${infund.count}</c:when><c:otherwise>0</c:otherwise></c:choose></i>笔，共<i class="color1" id="insummoney"><c:choose><c:when test="${!empty infund.summoney}"><fmt:formatNumber value="${infund.summoney}" pattern="##.##" minFractionDigits="2"></fmt:formatNumber></c:when><c:otherwise>0.00</c:otherwise></c:choose></i>元
									</p>
									<%-- <p>
										支出<i class="color5" id="outcount"><c:choose><c:when test="${!empty outfund.count}">${outfund.count}</c:when><c:otherwise>0</c:otherwise></c:choose></i>笔，共<i class="color5" id="outsummoney"><c:choose><c:when test="${!empty outfund.summoney}"><fmt:formatNumber value="${outfund.summoney}" pattern="##.##" minFractionDigits="2"></fmt:formatNumber></c:when><c:otherwise>0.00</c:otherwise></c:choose></i>元
									</p> --%>
								</div>
								<div class="uc_fssearch">
									<em>手机后4位：</em> <input type="text" class=""
										id="iphone" name="iphone" value="" maxlength="4" style="padding: 0; padding-left: 5px;">
									<em style="margin-left: 5px;">收支类型：</em> <input type="text" class="uc_fs_ip"
										id="fundtype" name="fundtype" value="佣金收入"> <a
										href="javascript:void(0)" id="alltype"
										class="uc_fssdown uc_fsiptype"></a> <em>时间：</em> <input
										type="text" name="allstarttime" readonly="readonly"
										onclick="WdatePicker();" id="allstarttime" class="uc_fsiptime">
									<i>—</i> <input type="text" name="allendtime" id="allendtime"
										readonly="readonly" onclick="WdatePicker();"
										class="uc_fsiptime"> <span><a
										href="javascript:void(0)"
										onclick="findAllData('Searchresult','Pagination');">查询</a></span>
								</div>
							</div>
							<ul class="uc_fslist">
								<ol class="uc_fsl_title">
									<li class="uc_fsl165">时间</li>
									<li class="uc_fsl200">手机号码</li>
									<li class="uc_fsl100">姓名</li>
									<li class="uc_fsl100">佣金类型</li>
									<li class="uc_fsl100">金额</li>
								</ol>
								<div>
									<div id="Searchresult"></div>
								</div>
							</ul>
							<div class="uc_tpage uc_fspage">
								<div id="Pagination"></div>
							</div>
						</div>
					</div>

				</div>
			</div>

		</div>
	</div>

	<%@include file="../common/footer.jsp"%>
	<%@ include file="../common/dsp.jsp"%>
</body>

</html>

