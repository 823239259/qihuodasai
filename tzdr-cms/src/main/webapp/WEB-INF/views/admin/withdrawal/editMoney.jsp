<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../common/common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%@include file="../../common/import-easyui-js.jspf"%>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Content-Language" content="zh-CN" />
<!-- X-UA-Compatible 设置ie8/ie9 的兼容模式 -->
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<script type="text/javascript" src="${ctx}/static/script/withdrawal/moneyList.js"></script>

<title>设置提现审核金额</title>
</head>
<body>
		<form method="post" id="addForm" style="padding-left: 10%;padding-top: 35px;">
		<input type="hidden" id="accountID" name="id" value="${dataMap.id}">
		<!-- missingMessage:'不能为空' 可以修改为空时的提示语 -->
					<table class="formTable">
						<tr>
							<td>提现审核金额:</td>
							<td>
								<input class="easyui-validatebox" value="<fmt:formatNumber value="${dataMap['minMoney']}" pattern="##.##"/>" id="minMoney"  name="minMoney" data-options="required:true,validType:'money'">
								- <input class="easyui-validatebox" value="<fmt:formatNumber value="${dataMap['maxMoney']}" pattern="##.##"/>" id="maxMoney"  name="maxMoney" data-options="required:true,validType:'money'">
							</td>
						</tr>
						<tr>
							<td colspan="2" align="center">
								<a href="javascript:void(0)" id="saveOrUpdate" class="easyui-linkbutton" data-options="iconCls:'icon-edit'"  onclick="withDrawMoney.submitInfo()">设置</a>
							</td>
						</tr>
					</table>
		</form>
</body>
</html>