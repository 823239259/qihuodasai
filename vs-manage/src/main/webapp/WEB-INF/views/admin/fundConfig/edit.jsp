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
	<input type="hidden" id="dataID" name="id"  value="${dataMap.id}">
	<!-- missingMessage:'不能为空' 可以修改为空时的提示语 -->
		<table class="formTable">
			<tr>
				<td>倍数:</td>
				<td>
				<c:choose>
					<c:when test="${fromType=='add'}">
					<input class="easyui-numberbox" value="${dataMap.times}" id="times"  name="times" data-options="required:true">&nbsp;倍
					</c:when>
					<c:otherwise>
					${dataMap.times}&nbsp;倍
					</c:otherwise>
				</c:choose>
				</td>
			</tr>
			<tr>
				<td>配资金额:</td>
				<td>
					<input class="easyui-numberbox" value="${dataMap.fundAmount}" id="fundAmount"  name="fundAmount" data-options="required:true,min:1,groupSeparator:',',decimalSeparator:','">&nbsp;元
				</td>
			</tr>
			<tr>
				<td colspan="2" align="center">
				<c:choose>
					<c:when test="${fromType=='add'}">
						<a href="javascript:void(0)" id="saveOrUpdate" class="easyui-linkbutton" data-options="iconCls:'icon-save'"  onclick="baseUtils.saveOrUpdate('admin/fundConfig/create')">保存</a>
					</c:when>
					<c:otherwise>
						<a href="javascript:void(0)" id="saveOrUpdate" class="easyui-linkbutton" data-options="iconCls:'icon-edit'"  onclick="baseUtils.saveOrUpdate('admin/fundConfig/update')">修改</a>
					</c:otherwise>
				</c:choose>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>