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
<script type="text/javascript" src="${ctx}/static/script/hundsunAccount/parentList.js"></script>

<title>母账户编辑</title>
</head>
<body>
		<form method="post" id="addForm" style="padding-left: 22%;padding-top: 35px;">
		<input type="hidden" id="accountID" name="id" value="${parentAccount.id}">
		<!-- missingMessage:'不能为空' 可以修改为空时的提示语 -->
					<table class="formTable">
						<tr>
							<td>账户余额:</td>
							<td>
								<input class="easyui-validatebox" value="<fmt:formatNumber value="${parentAccount.fundsBalance}" pattern="##.##"/>" id="fundsBalance"  name="fundsBalance" data-options="required:true,validType:'money'">
							</td>
							<td>
								<a href="javascript:void(0)" id="saveOrUpdate" class="easyui-linkbutton" data-options="iconCls:'icon-edit'"  onclick="parentAccount.saveOrUpdate('admin/parentAccount/updateBalance')">修改</a>
							</td>
						</tr>
					</table>
		</form>
</body>
</html>