<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../common/common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
		<meta http-equiv="Content-Language" content="zh-CN"/> 
		<!-- X-UA-Compatible 设置ie8/ie9 的兼容模式 -->
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
		<title>更新交易日</title>
	</head>
	<body>
	<!-- add organization window -->
		<form method="post" id="addForm" style="padding-left: 22%;">
			<input  type="hidden" name="id" value="${investor.id}"/>
					<table class="formTable" >
						<tr>
							<td>真实姓名:</td>
							<td>
								<input class="easyui-validatebox" value="${investor.realName}" id="realName"  name="realName" data-options="required:true,validType:['length[2,10]']">
							</td>
						</tr>
						<tr>
							<td>身份证号:</td>
							<td>
								<input class="easyui-validatebox" value="${investor.idCard}" id="idCard"  name="idCard">
							</td>
						</tr>
						<tr>
							<td colspan="2" align="center">
								<c:choose>
									<c:when test="${fromType=='add'}">
										<a href="javascript:void(0)" id="saveOrUpdate" class="easyui-linkbutton" data-options="iconCls:'icon-save'"  onclick="baseUtils.saveOrUpdate('admin/investor/create')">保存</a>
									</c:when>
									<c:otherwise>
										<a href="javascript:void(0)" id="saveOrUpdate" class="easyui-linkbutton" data-options="iconCls:'icon-edit'"  onclick="baseUtils.saveOrUpdate('admin/investor/update')">修改</a>
									</c:otherwise>
								</c:choose>
							</td>
						</tr>
					</table>
		</form>
	</body>
</html>