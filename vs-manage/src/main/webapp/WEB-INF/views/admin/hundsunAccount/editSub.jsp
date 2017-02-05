<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../common/common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Content-Language" content="zh-CN" />
<!-- X-UA-Compatible 设置ie8/ie9 的兼容模式 -->
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title>登录</title>
</head>
<body>
		<form method="post" id="addForm" style="padding-left: 22%;">
		<input type="hidden" id="dataID" name="id"  value="${account.id}">
		<!-- missingMessage:'不能为空' 可以修改为空时的提示语 -->
					<table class="formTable">
						<tr>
							<td>恒生帐户名称:</td>
							<td>
									<input class="easyui-validatebox" value="${account.accountName}" id="accountName"  name="accountName" data-options="required:true">
							</td>
						</tr>
						<tr>
							<td>恒生帐号:</td>
							<td>
									<input class="easyui-validatebox" value="${account.account}" id="account"  name="account" data-options="required:true">
							</td>
						</tr>
						<tr>
								<td>恒生密码:</td>
								<td>
									<input class="easyui-validatebox"  id="password" type="text" value="${account.password}"   name="password" data-options="required:true,validType:'safepass'">
								</td>
						</tr>
						<tr>
							<td>单元序号:</td>
							<td><input class="easyui-validatebox" value="${account.assetId}" id="assetId"  name="assetId" data-options="required:true"></td>
						</tr>
						<tr>
							<td>母账户名称:</td>
							<td>
									<input class="easyui-combobox" id="parentAccount" value="${account.parentAccount.id}" name="parentAccount.id" data-options="
										url:'${ctx}/admin/parentAccount/getComboboxData',
										valueField:'id',
										panelHeight:100,
										required:true,
										textField:'accountName'">
							</td>
						</tr>
						<tr>
							<td>保险单号:</td>
							<td><input class="easyui-validatebox" value="${account.insuranceNo}" id="insuranceNo"  name="insuranceNo" data-options=""></td>
						</tr>
						<tr>
								<td colspan="2" align="center">
								<c:choose>
									<c:when test="${fromType=='add'}">
										<a href="javascript:void(0)" id="saveOrUpdate" class="easyui-linkbutton" data-options="iconCls:'icon-save'"  onclick="baseUtils.saveOrUpdate('admin/subAccount/create')">保存</a>
									</c:when>
									<c:otherwise>
										<a href="javascript:void(0)" id="saveOrUpdate" class="easyui-linkbutton" data-options="iconCls:'icon-edit'"  onclick="baseUtils.saveOrUpdate('admin/subAccount/update')">修改</a>
									</c:otherwise>
								</c:choose>
								</td>
						</tr>
					</table>
		</form>
</body>
</html>