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
							<td>类型key:</td>
							<td>
									<input class="easyui-validatebox" value="${dataMap.typeKey}" id="typeKey"  name="typeKey" data-options="required:true,validType:['length[1,100]']">
							</td>
						</tr>
						<tr>
							<td>类型名称:</td>
							<td>
									<input class="easyui-validatebox" value="${dataMap.typeName}" id="typeName"  name="typeName" data-options="required:true,validType:['length[1,100]']">
							</td>
						</tr>
						<tr>
								<td>值key:</td>
								<td>
									<input class="easyui-validatebox"  id="password" type="text"   value="${dataMap.valueKey}" id="valueKey"  name="valueKey" data-options="required:true,validType:['length[1,20]']" >
								</td>
						</tr>
						<tr>
							<td>值名称:</td>
							<td><textarea class="easyui-validatebox" value="${dataMap.valueName}" id="valueName"  name="valueName" data-options="required:true"></textarea></td>
						</tr>
						<tr>
							<td>权重:</td>
							<td><input class="easyui-validatebox" value="${dataMap.weight}" id="weight"  name="weight" data-options="required:true,validType:['number']"></td>
						</tr>
						<tr>
								<td colspan="2" align="center">
								<c:choose>
									<c:when test="${fromType=='add'}">
										<a href="javascript:void(0)" id="saveOrUpdate" class="easyui-linkbutton" data-options="iconCls:'icon-save'"  onclick="baseUtils.saveOrUpdate('admin/dataDic/create')">保存</a>
									</c:when>
									<c:otherwise>
										<a href="javascript:void(0)" id="saveOrUpdate" class="easyui-linkbutton" data-options="iconCls:'icon-edit'"  onclick="baseUtils.saveOrUpdate('admin/dataDic/update')">修改</a>
									</c:otherwise>
								</c:choose>
								</td>
						</tr>
					</table>
		</form>
</body>
</html>