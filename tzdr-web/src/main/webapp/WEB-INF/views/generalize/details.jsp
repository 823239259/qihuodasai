<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.tzdr.common.utils.ConfUtil"%> 
<%@ include file="../common/common.jsp"%>
<%@include file="../common/import-artDialog-js.jspf"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="keywords" content="维胜，国际期货，期货，投资达人，金勺子，高盛，都城，南华期货，配资，期货配资，期货开户，外盘，富时A50，国际原油，恒指期货，期货公司，期货平台，炒期货，模拟盘，赚钱，头寸，持仓，成都盈透科技有限公司"/>
<meta name="description" content="维胜（www.vs.com）-致力于成为中国领先的国际期货及衍生品互联网交易平台，提供恒指期货、国际原油、富时A50等主流国际期货产品，开户操盘快捷方便，交易费用全网最低。"/>
<title>我的邀请码 - 维胜金融-中国领先的国际期货及衍生品互联网交易平台</title>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/uc.css?version=20150721">
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/public.css">
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/tzdr.css">
<link href="${ctx}/static/css/pagination.css" rel="stylesheet" type="text/css" /> 
<script type="text/javascript" src="${ctx}/static/script/common/dateUtils.js"></script>
<script type="text/javascript" src="${ctx}/static/script/generalize/details.js?version=20150721"></script>
<script type='text/javascript' src="${ctx}/static/script/tzdr.js"></script>
<script src="${ctx}/static/script/My97DatePicker/WdatePicker.js"
	type="text/javascript"></script>
<script src="${ctx}/static/script/common/jquery.pagination.js" type="text/javascript"></script> 
<script type='text/javascript' src="${ctx}/static/script/common/ZeroClipboard.min.js"></script>
<script type="text/javascript" charset="utf-8" src="http://static.bshare.cn/b/buttonLite.js#style=-1&amp;uuid=fc88a5c2-48c9-4d9d-9c01-ec40b668977f&amp;pophcol=2&amp;lang=zh"></script>
<script type="text/javascript" charset="utf-8" src="http://static.bshare.cn/b/bshareC2P.js"></script>
<script type="text/javascript">
$(document).ready(function() {
	$('#uniontype').click(function(event) {
				event.stopPropagation();
				$("#uniondiv").show();
	});
	
	$("#uniondiv a").each(function() {
		$(this).click(function() {
			var value = $(this).html();
			$('#union').attr("data-id", $(this).attr("data-id"));
			$('#union').val(value);
			$('#uniondiv').hide();
		});

	});
	
	$(document).click(function() {
		$("#uniondiv").hide();
	});
	
});
</script>
<style>
	#nav_my {color: #ffcc33; border-bottom:2px solid #ffcc33; padding-bottom: 26px;}
</style>
</head>
<body>
	<!--顶部 -->
	<%@include file="../common/header.jsp"%>
	<c:choose>
		<c:when test="${!empty rebate}"><input type="hidden" value="<fmt:formatNumber value="${rebate}" pattern="###" minFractionDigits="0"></fmt:formatNumber>" class="myRebate"/></c:when>
		<c:otherwise><input type="hidden" value="0" class="myRebate"/></c:otherwise>
	</c:choose>
	<c:choose>
		<c:when test="${!empty subordinateDefaultRebate}"><input type="hidden" value="<fmt:formatNumber value="${subordinateDefaultRebate}" pattern="###" minFractionDigits="0"></fmt:formatNumber>" class="subordinateDefaultRebate"/></c:when>
		<c:otherwise><input type="hidden" value="0" class="subordinateDefaultRebate"/></c:otherwise>
	</c:choose>
	<div class="uc">
		<!--个人中心导航 -->
		<%@ include file="../common/leftnav.jsp"%>
		<!-- 浮动层 -->
		<div class="floatlayer updateRebateDiv" style="display: none">
			<div class="fl_mask" style="display: block;"></div>
			<div class="fl_box fl_lower">
				<div class="fl_navtitle">
					<h3 class="updateRebateTitle">设置下级默认返点</h3>
					<a href="javascript:void(0)" class="close"></a>
				</div>
				<div class="fl_uc_main">
					<ul class="fl_uc_list">
						<li><label>佣金返点：</label> <input class="rebate" data-id="" data-value="" maxlength="3" type="text"> <span>%</span>
						</li>
					</ul>
				</div>
				<div class="fl_uc_btn">
					<a href="javascript:void(0);" class="fl_uc_surebtn updateRebate">确定</a> 
					<a href="javascript:void(0);" class="fl_uc_cancelbtn close">取消</a>
				</div>

			</div>
		</div>
		<!--推广挣钱-->
		<div class="uc_mianbar">
			<div class="uc_pay">
				<ul class="uc_paynav">
					<!-- <li class="generalize" id="detail"><a
						href="javascript:void(0);" class="on">代理详情</a></li> -->
					<li class="generalize" id="generalize"><a
						href="javascript:void(0);" class="on">推广链接</a></li>
					<li class="generalize" id="subordinates"><a
						href="javascript:void(0);" >我的下级</a></li>
					<!-- <li class="generalize" id="visitors"><a
						href="javascript:void(0);">访问记录</a></li> -->
					
					<!-- <li class="generalize" id="incomeQuery"><a
					    href="javascript:void(0);">收益查询</a></li> -->
					<!-- <li class="generalize" id="generalizemoney"><a
					    href="javascript:void(0);">推广佣金明细</a></li> -->
				</ul>
				<!--推广详情  -->
				<%-- <div class="generalizeSubtab" id="detailDiv" style="display:">
					<ul class="uc_sp_num">
						<li><label>我的返点：</label>
						<i>
						<c:choose>
							<c:when test="${!empty rebate}"><fmt:formatNumber value="${rebate}" pattern="##" minFractionDigits="0"></fmt:formatNumber></c:when>
							<c:otherwise>0</c:otherwise>
						</c:choose>
						</i>
							<span>%</span></li>
						<li>
							<label>我的称号：</label>
							<i>
								<c:choose>
									<c:when test="${empty userGrade or userGrade==0}">股民1级</c:when>
									<c:when test="${userGrade==1}">股师</c:when>
									<c:otherwise>股神</c:otherwise>
								</c:choose>
							</i>
						</li>
						<li class="uc_sp_numspace"><label>佣金收入：</label><i
							class="color1">
							<c:choose>
								<c:when test="${!empty totalCommission}"><fmt:formatNumber value="${totalCommission}" pattern="#,###.##" minFractionDigits="2" /></c:when>
								<c:otherwise>0.00</c:otherwise>
							</c:choose></i> <span>元</span></li>
						<li><span class="uc_sp_sspace">下一级数量：</span>
							<i>
							<c:choose>
								<c:when test="${!empty totalSubordinate}">${totalSubordinate}</c:when>
								<c:otherwise>0</c:otherwise>
							</c:choose>
							</i>
						</li>
						<li><label>访问数量：</label>
							<i>${totalVisit}</i>
						</li>
						<li class="uc_sp_numspace"><label>访问IP：</label><i>${totalVisitClieantIp}</i>
							<span></span></li>
					</ul>
					<div class="uc_sp_info">
						<div class="uc_sp_list">
							<h3>代理佣金榜</h3>
							<ul>
								<ol class="uc_spl_title">
									<li class="uc_spl_num"></li>
									<li class="uc_spl_name">代理用户</li>
									<li class="uc_spl_money">佣金收入</li>
								</ol>
								<c:forEach items="${generalizeVisitUserVoList}" var="generalizeVisitUserVo" varStatus="status">
									<ol>
										<li class="uc_spl_num">${status.index+1}</li>
										<li class="uc_spl_name">${generalizeVisitUserVo.mobile}</li>
										<li class="uc_spl_money">
										<c:choose>
											<c:when test="${!empty generalizeVisitUserVo.totalCommission}"><fmt:formatNumber type="number" value="${generalizeVisitUserVo.totalCommission}" pattern="#,###.##" minFractionDigits="2" /></c:when>
											<c:otherwise>0.00</c:otherwise>
										</c:choose>
										元</li>
									</ol>
								</c:forEach>
							</ul>
						</div>
						<div class="uc_sp_font">
							<h3>什么是代理赚钱</h3>
							<p><span>代理赚钱是维胜独创的股票操盘行业无限级代理制度，成功邀请下级操盘，即可获得超高收益分成。下级用户再邀请下级用户，所有上级用户均可获得收益分成，轻松赚大钱！<br></span><span>您可以通过朋友、QQ、微信、微博等各种方式进行推广，所有通过您的推广链接访问而来的用户，注册后都将成为您的下级用户，当这些用户在维胜网站股票操盘时，您便可以赚取下线用户一定比例的返点。</span></p>
							<h3>为什么要做维胜代理</h3>
		           			<p><span>客户提成上不封顶：获得代理资格后，每成功邀请一名下线用户，股票操盘产生管理费后，即可获得相应的返点，提成金额上不封顶，平台将提成自动转至您的达人账户余额中。<br></span><span>限下线无限收益：您不仅可以获得直接下级所产生的收益提成，还可以获得下级的下级、无限下级的所有收益提成。<br></span><span>快捷的操作流程：将您的推广链接通过各种渠道进行宣传，点击您的推广链接进入网站并注册的用户即自动成为您的下级用户。 </span></p>
						</div>

					</div>
					<div class="uc_sp_way">
				        <h3><i>如何成为代理</i><img src="${ctx}/static/images/uc/sp_03.gif"></h3>
				        <div class="uc_sp_wayctn"><p><span>请联系维胜的<i>客服电话400-852-8008</i>或<i>客服qq 400-852-8008</i>，选择您想获得的返点级别，完成相应的审核过程，即可在您的个人中心-代理赚钱栏目看到您的返点数额。<br></span><span>您自己股票操盘和所有下级用户股票操盘时产生的管理费*您选择的代理返点=您的佣金收入。<br></span><span>您可以给下级开设返点数，最高不超过您选择的代理返点级别。</span></p></div>
				    </div>
				    <div class="uc_sp_way">
				        <h3><i>代理返点级别</i><img src="${ctx}/static/images/uc/sp_03.gif"></h3>
				        <table width="608px" border="0" cellspacing="0" cellpadding="0" class="uc_sp_talbe">
				          <tr>
				            <td colspan="5">维胜代理体系返点级别</td>
				          </tr>
				          <tr>
				            <td>代理分级</td>
				            <td>直接下级人数</td>
				            <td>总下级人数</td>
				            <td>日均操盘配额</td>
				            <td>代理返点</td>
				          </tr>
				          <tr>
				            <td>股民1级</td>
				            <td>不限</td>
				            <td>不限</td>
				            <td>不限</td>
				            <td rowspan="10"  width="130px"><i>具体返点由上级代理设置，或使用系统默认代理返点值，详询客服。</i></td>
				          </tr>
				          <tr>
				            <td>股民2级</td>
				            <td>3人</td>
				            <td>5人</td>
				            <td>30万元</td>
				          </tr>
				          <tr>
				            <td>股民3级</td>
				            <td>6人</td>
				            <td>10人</td>
				            <td>100万元</td>
				          </tr>
				          <tr>
				            <td>股师1级</td>
				            <td>10人</td>
				            <td>20人</td>
				            <td>200万元</td>
				          </tr>
				          <tr>
				            <td>股师2级</td>
				            <td>20人</td>
				            <td>40人</td>
				            <td>400万元</td>
				          </tr>
				          <tr>
				            <td>股师3级</td>
				            <td>30人</td>
				            <td>60人</td>
				            <td>600万元</td>
				          </tr>
				          <tr>
				            <td>股仙1级</td>
				            <td>50人</td>
				            <td>100人</td>
				            <td>1000万元</td>
				          </tr>
				          <tr>
				            <td>股仙2级</td>
				            <td>80人</td>
				            <td>200人</td>
				            <td>2000万元</td>
				          </tr>
				          <tr>
				            <td>股仙3级</td>
				            <td>100人</td>
				            <td>300人</td>
				            <td>3000万元</td>
				          </tr>
				          <tr>
				             <td colspan="5">更高代理级别请联系客服转相关部门接洽</td>
				          </tr>
				        </table>
		
		
				    </div>
				    <div class="uc_sp_way">
		        		<h3><i>返点结算方式</i><img src="${ctx}/static/images/uc/sp_03.gif"></h3>
		        		<div class="uc_sp_wayctn"><p>您的下级用户每发生一笔股票操盘，在操盘完成后您就获得一笔返点，当天的返点将在第二天凌晨0点统一为您充值到维胜网站的账户中。当您的佣金到达10元时，您就可以随时兑换并提现，当然也可以在本站进行股票操盘。</p></div>

		    		</div>
				    <div class="uc_sp_way">
				        <h3><i>代理级别升降规则</i><img src="${ctx}/static/images/uc/sp_03.gif"></h3>
				        <div class="uc_sp_wayctn"><p><span>如果想提升您的代理返点级别，您可以随时联系客服进行商议。建议在首次选择返点级别一个月后，根据相应任务的完成情况来决定是否申请提升代理级别。<br></span><span>如果在协议时间段内，您没有达到所选择代理级别的任务目标，则有可能被降级。如果您的代理级别下降，则返点数额会相应下调，您的所有下级的返点数也会下调至最高不超过您的返点数。<br></span><span>*任务目标为三个：直接下级人数、总下级人数、总操盘配额，一个月内任意完成其中一项则视为完成任务。</span></p></div>
				    </div>
					<div class="uc_sp_way">
						<h3>
							<i>如何赚钱</i><img src="${ctx}/static/images/uc/sp_03.gif">
						</h3>
						<div class="uc_sp_wayctn"><img src="${ctx }/static/images/uc/rhzq.jpg"/></div>
					</div>
					<div class="uc_sp_way">
				        <h3><i>如何推广</i><img src="${ctx }/static/images/uc/sp_03.gif"></h3>
				        <div class="uc_sp_wayctn"><img src="${ctx}/static/images/generalize.png"></div>
				    </div>
				</div>
				<!--我的下级  -->
			<div class="generalizeSubtab" id="subordinatesDiv" style="display: none">
				<!-- <li class="uc_lowerbtn updateDefaultRebate"><a href="javascript:void(0);">设置下级默认返点</a></li> -->
				<div class="uc_lower">
					<div class="uc_lw_btn"><a class="updateDefaultRebate" href="javascript:void(0);">设置下级默认返点</a><span id="mySubordinateDefaultRebate">下级默认返点：<fmt:formatNumber value="${subordinateDefaultRebate}" pattern="###" minFractionDigits="0"></fmt:formatNumber>%</span></div>
					<ul class="uc_retitle">
						<li class="uc_tl120">手机号</li>
						<li class="uc_tl50">姓名</li>
						<li class="uc_tl50">级别</li>
						<li class="uc_tl50">返点%</li>
						<li class="uc_tl90">直接下线(人)</li>
						<li class="uc_tl90">所有下线(人)</li>
						<li class="uc_tl110">累计佣金收入(元)</li>
						<!--li class="uc_tl139">注册时间</li-->
						<li class="uc_lowerbtn"></li>
					</ul>
					<ul class="uc_relist subordinatesList">
					</ul>
					<div id="PagSubordinatesInation"></div> 
				</div>
			</div> --%>
			<!--访问记录  -->
			<!-- <div class="generalizeSubtab" id="visitorsDiv" style="display: none">
				<div class="uc_record">
					<p class="uc_rdnum">
						访问IP共<i class="totalVisitClieantIp">4</i>个，访问次数共<i class="totalVisit">4</i>个
					</p>
					<ul class="uc_retitle">
						<li class="uc_tl120">IP地址</li>
						<li class="uc_tl120">访问时间</li>
					</ul>
					<ul class="uc_relist visitorsList">
					</ul>
					<div id="PagVisitorsInation"></div> 
				</div>
			</div> -->
			<!--推广链接  -->
			<div class="generalizeSubtab" id="generalizeDiv" style="display: block">
				<div class="uc_splink">
					<p class="uc_spl_font">以下网址是您对外界进行推广的地址，您可以通过朋友、QQ、微信、博客进行推广，所有通过该地址访问过来的人，注册后就都属于您的用户，而当这些用户在本站股票操盘时，您就可以赚取佣金了，详细的推广情况可到访问记录里查看。</p>
					<div class="uc_spl_link link">
						<p><%=ConfUtil.getContext("web.address")%>/${id}</p>
						<a href="javascript:void(0);" class="copy" data-clipboard-target="popText">复制链接地址</a>
						<input type="hidden" id="popText" value="<%=ConfUtil.getContext("web.address")%>/${id}">
					</div>
					<div class="uc_spl_share content">
						<p class="uc_spl_sfont">
							牛市来了，小资金如何做大？我最近在维胜网站股票操盘炒股，收益可扩大15倍，你也可以来试试！<%-- <a href="http://www.vs.com?uid=${id}">http://www.vs.com?uid=${id}</a> --%>
						</p>
						<div class="uc_spl_sharelist">
							<span>分享到：</span>
							<div class="bshare-custom icon-medium" style="margin-left: 11px;margin-top: inherit;" bshareUrl="<%=ConfUtil.getContext("web.address") %>/${id}" bshareTitle="股票操盘炒股，放大收益" bshareSummary="牛市来了，小资金如何做大？我最近在维胜网站股票操盘，收益可扩大15倍，你也可以来试试！" bsharePic="<%=ConfUtil.getContext("web.address") %>/static/images/common-new/new_logo.png">
								<div class="bsPromo bsPromo2"></div>
								<div class="bsPromo bsPromo2"></div>           	 
							</div>
						</div>
					</div>
				</div>
			</div>
				<!--我的下级  -->
			<div class="generalizeSubtab" id="subordinatesDiv" style="display: none">
				<!-- <li class="uc_lowerbtn updateDefaultRebate"><a href="javascript:void(0);">设置下级默认返点</a></li> -->
				<div class="uc_lower">
					<%-- <div class="uc_lw_btn"><a class="updateDefaultRebate" href="javascript:void(0);">设置下级默认返点</a><span id="mySubordinateDefaultRebate">下级默认返点：<fmt:formatNumber value="${subordinateDefaultRebate}" pattern="###" minFractionDigits="0"></fmt:formatNumber>%</span></div> --%>
					<ul class="uc_retitle">
						<li class="uc_tl120">手机号</li>
						<li class="uc_tl50">姓名</li>
						<li class="uc_tl50">级别</li>
						<!-- <li class="uc_tl50">返点%</li> -->
						<li class="uc_tl90">直接下线(人)</li>
						<li class="uc_tl90">所有下线(人)</li>
						<!-- <li class="uc_tl110">累计佣金收入(元)</li>
						li class="uc_tl139">注册时间</li
						<li class="uc_lowerbtn"></li> -->
					</ul>
					<ul class="uc_relist subordinatesList">
					</ul>
					<div id="PagSubordinatesInation"></div> 
				</div>
			</div>
			<!--收益查询 -->
			<!-- <div class="generalizeSubtab" id="incomeQueryDiv" style="display:none;">
			
			
					
				<div class="uc_trade">
					<div class="uc_fsdetails">
						
						<div class="uc_fssearch" style="float: left;">
							 <em>时间：</em> <input
								type="text" class="uc_fsiptime" id="unionstarttime_a"
								readonly="readonly" name="unionstarttime"
								onclick="WdatePicker();"> <i>—</i> <input type="text"
								class="uc_fsiptime" id="unionendtime_a" name="unionendtime"
								readonly="readonly" onclick="WdatePicker();"> <span><a
								href="javascript:void(0)"
								onclick="agentReturnData('unionSearchresult_agent','PagVisitorsInation_agent');">查询</a></span>
						</div>
					</div>
					<ul class="uc_fslist">
						<ol class="uc_fsl_title">
							<li class="uc_fsl165">时间</li>
							<li class="uc_fsl100">直接下线</li>
							<li class="uc_fsl100">所有下线</li>
							<li class="uc_fsl100">操盘总额</li>
							<li class="uc_fsl200">每日累计收入佣金(元)</li>
						</ol>
						<div>
							<div id="unionSearchresult_agent"></div>
						</div>
					</ul>
					ul class="uc_relist visitorsList">
					</ul
					<div id="PagVisitorsInation_agent"></div> 
					
				</div>
			
			</div> -->
			<div class="generalizeSubtab"  id="generalizemoneyDiv" style="display: none">
						<!--推广佣金明细 -->
				<div class="uc_trade">
					<div class="uc_floatlayer">
						<div class="uc_bc_option uc_sp_type" id="uniondiv"
							style="display: none;">
							<a href="javascript:void(0)" data-id="13">全部明细</a> <a
								href="javascript:void(0)" data-id="13">佣金收入</a>


						</div>
					</div>
					<div class="uc_fsdetails" style="height:110px;">
						<div class="uc_fsmoney" style="height:auto;">
							<p>
								收入<i class="color5" id="inunioncount"> <c:choose>
										<c:when test="${!empty inunionfund.count}">
								${inunionfund.count}
							</c:when>
										<c:otherwise>0</c:otherwise>
									</c:choose>
								</i>笔，共<i class="color5" id="inunionsum"> <c:choose>
										<c:when test="${!empty inunionfund.summoney}">
											<fmt:formatNumber value="${inunionfund.summoney}"
												pattern="##.##" minFractionDigits="2"></fmt:formatNumber>
										</c:when>
										<c:otherwise>0.00</c:otherwise>
									</c:choose>
								</i>元
							</p>
						</div>
						<div class="uc_fssearch" style="width:780px;">
						   <em>手机后四位：</em>
						   <input type="text" maxlength="4" id="fourthMobile" class="uc_fsiptime uc_spmipsp">
							<em>收支类型：</em> 
							<input type="text" class="uc_fs_ip uc_sp_type" id="union"
								name="union" data-id="13" value="全部明细"> <a
								href="javascript:void(0)" id="uniontype"
								class="uc_fssdown uc_spiptype"></a> <em>时间：</em> <input
								type="text" class="uc_fsiptime" id="unionstarttime"
								readonly="readonly" name="unionstarttime"
								onclick="WdatePicker();"> <i>—</i> <input type="text"
								class="uc_fsiptime" id="unionendtime" name="unionendtime"
								readonly="readonly" onclick="WdatePicker();"> <span><a
								href="javascript:void(0)"
								onclick="finduniondata('unionSearchresult','unionPagination');">查询</a></span>
						</div>
					</div>
					<ul class="uc_fslist">
						<ol class="uc_fsl_title">
							<li class="uc_fsl165">时间</li>
							<li class="uc_fsl165">手机号码</li>
							<li class="uc_fsl100">姓名</li>
							<li class="uc_fsl100">类型</li>
							<li class="uc_fsl100">收入</li>
							<!--li class="uc_fsl100">支出</li-->
						</ol>
						<div>
							<div id="unionSearchresult"></div>
						</div>
					</ul>
					<div class="uc_tpage uc_fspage">
						<div id="unionPagination"></div>
					</div>
				</div>
			</div>
			</div>
			
		</div>
	</div>
	<!--底部 -->
	<%@include file="../common/footer.jsp"%>
	<script type="text/javascript">
		var tab = '${tab}';
		if($.trim(tab) != null && $.trim(tab).length > 0 && $("#"+tab) != null && $("#"+tab).length > 0){
			$("#"+tab).parent().find("a").removeClass("on");
			$("#"+tab).find("a").addClass("on");
			$(".generalizeSubtab").css({display:"none"});
			$("#"+tab+"Div").css({display:""});
		}
	</script>
</body>
</html>