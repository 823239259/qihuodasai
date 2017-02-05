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
		<input type="hidden" id="dataID" name="id"  value="${config.id}">
		<input type="hidden" id="type" name="type"  value="2">
		<!-- missingMessage:'不能为空' 可以修改为空时的提示语 -->
					<table class="formTable">
						<tr>
							<td>栏目名称:</td>
							<td>
									<input class="easyui-validatebox" value="${config.name}" id="name"  name="name" data-options="required:true,validType:['length[2,20]']">
							</td>
						</tr>
						<tr>
							<td>启用状态:</td>
							<td>
							<c:choose>
								<c:when test="${config.isEnable==false}">
										<input type="radio" name="isEnable" value="true"><span>启用</span></input>
										<input type="radio" name="isEnable" value="false" checked="checked"><span>禁用</span></input>
								</c:when>
								<c:otherwise>
									<input type="radio" name="isEnable" value="true" checked="checked"><span>启用</span></input>
									<input type="radio" name="isEnable" value="false"><span>禁用</span></input>
								</c:otherwise>
							</c:choose>
							</td>	
						</tr>
						<tr>
								<td colspan="2" align="center">
								<c:choose>
									<c:when test="${fromType=='add'}">
										<a href="javascript:void(0)" id="saveOrUpdate" class="easyui-linkbutton" data-options="iconCls:'icon-save'"  onclick="baseUtils.saveOrUpdate('admin/config/newsColumn/create')">保存</a>
									</c:when>
									<c:otherwise>
										<a href="javascript:void(0)" id="saveOrUpdate" class="easyui-linkbutton" data-options="iconCls:'icon-edit'"  onclick="baseUtils.saveOrUpdate('admin/config/newsColumn/update')">修改</a>
									</c:otherwise>
								</c:choose>
								</td>
						</tr>
					</table>
		</form>
</body>
</html>