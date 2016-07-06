<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>
<%@include file="../common/import-artDialog-js.jspf"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<%@ include file="../common/commonkeyword.jsp"%>
	<link rel="stylesheet" type="text/css" href="${ctx}/static/css/newyearspree.css">
</head>
<body>
<!--顶部 -->
<%@ include file="../common/personheader.jsp"%>

<div class="tp_main1"><img src="${ctx}/static/images/newyearspree/img_01.jpg" ></div>
<div class="tp_main1"><img src="${ctx}/static/images/newyearspree/img_02.jpg" ></div>
<div class="tp_main1"><img src="${ctx}/static/images/newyearspree/img_03.jpg" ></div>
<div class="tp_main2"><div class="tp_main2_in"></div></div>
<div class="tp_main3">
	<div class="tp_main3_in">
		<div class="tp_main3_in_ctn">
			<img src="${ctx}/static/images/newyearspree/step_01.jpg" class="step1">
			<p>自2月15日起，凡新注册用户即赠送388元大礼包，里面含抵扣红包，用户只有在期货操盘后才能让礼包生效</p>
			<c:choose>
				<c:when test="${islogin }">
					<p>尊敬的 <span>${mobile }</span> 用户：</p>
					<c:choose>
						<c:when test="${isRecivie }">
						<div class="content2">
							<div class="content2_top">
								<h3>我的礼包</h3>
							</div>
							<div class="content2_bottom">
								<table cellspacing="0" cellpadding="0">
									<tr class="first_tr"><td class="name">奖励名称</td><td class="price">金额</td><td class="condition">使用条件</td><td class="status">状态</td></tr>
									<c:forEach items="${mykudo }" var="kudo">
										<tr>
											<td class="name">${kudo.kudoName }</td>
											<td class="price">${kudo.kudoMoney }元</td>
											<td class="condition">${kudo.kudoUseCondition }</td>
											<td class="status">
												<c:choose>
												<c:when test="${kudo.kudoStatus==1 }"><a href="javascript: void(0);" onclick="useMyKudoJson('${kudo.id }')">立即使用</a></c:when>
												<c:when test="${kudo.kudoStatus==2 }">已使用</c:when>
												<c:when test="${kudo.kudoStatus==3 }">申请使用</c:when>
												<c:otherwise>未获得</c:otherwise>
												</c:choose>
											</td>
										</tr>
									</c:forEach>
								</table>
							</div>
						</div>
						</c:when>
						<c:otherwise>
							<div class="content1">
								<img src="${ctx}/static/images/newyearspree/libao.jpg">
								<a href="javascript: void(0);" onclick="recivieMyKudo()"><img src="${ctx}/static/images/newyearspree/button.jpg"></a>
							</div>
						</c:otherwise>
					</c:choose>
				</c:when>
				<c:otherwise>
					<p><a href="${ctx}/activity/newyearspree/indexSSO">请登录</a></p>
					<div class="content1">
						<img src="${ctx}/static/images/newyearspree/libao.jpg">
						<a href="${ctx}/activity/newyearspree/indexSSO"><img src="${ctx}/static/images/newyearspree/button.jpg"></a>
					</div>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
</div>
<div class="tp_main4"><div class="tp_main4_in"></div></div>
<div class="tp_main5">
	<div class="tp_main5_in">
		<div class="tp_main5_in_ctn">
			<img src="${ctx}/static/images/newyearspree/step_02.jpg" class="step2">
			<p class="p1">自2月15日起，凡新注册用户即赠送388元大礼包，里面含抵扣红包，用户只有在期货操盘后才能让礼包生效。</p>
			<p class="p2">大礼包中的红包不可提现，只有当用户完成操盘交易后即生效，并可提现。</p>
			<p class="p3">每个新注册用户只有享受一次大礼包的机会。</p>
			<p class="p4">礼包发放时间：2月15日-2月29日。</p>
			<p class="p5">礼包使用时间：2月15日-3月31日。</p>
		</div>
	</div>
</div>
<div class="tp_foot">Copyright © 2015 上海信闳投资管理有限公司 版权所有 沪ICP备14048395号-1</div>

<script type="text/javascript">
	var flag = false;

	function recivieMyKudo() {
		if(flag) {
			return;
		}
		$.post(basepath+"activity/newyearspree/recivieMyKudo.json", {ajax:1}, function(data) {
			flag = true;
			if(data.success) {
				if("0" == data.code) {
					showMsgDialog("提示","您未登录！");
					return;
				}
				if("1" == data.code) {
					winSucessRefresh("提示", "恭喜你，领取388元新手大礼包！", window.location.href);
					return;
				}
				if("2" == data.code) {
					showMsgDialog("提示","老用户不能领取新春大礼包！");
					return;
				}
				if("3" == data.code) {
					showMsgDialog("提示","您已领过新春大礼包！");
					return;
				}
			} else {
				showMsgDialog("提示","系统繁忙，请稍候重试......");
			}
		}, "json");
	}
	function useMyKudoJson(aid) {
		if(flag) {
			return;
		}
		$.post(basepath+"activity/newyearspree/useMyKudo.json", {ajax:1, id:aid}, function(data) {
			flag = true;
			if(data.success && "1" == data.code) {
				winSucessRefresh("提示", "你已申请使用现金红包，工作人员会尽快与你联系！", window.location.href);
			} else {
				flag = false;
				showMsgDialog("提示","系统繁忙，请稍候重试......");
			}
		}, "json");
	}
</script>
<!-- <span style="display:none"><script src='http://w.cnzz.com/q_stat.php?id=1256807294&l=3' language='JavaScript'></script></span> -->
<span style="display:none"><script src="https://s95.cnzz.com/z_stat.php?id=1259839078&web_id=1259839078" language="JavaScript"></script></span>
</body>
</html>