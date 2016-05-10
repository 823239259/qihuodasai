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
		<form method="post" id="addForm" style="padding-left: 20%;">
			<input  type="hidden" name="id" value="${message.id}"/>
					<table class="formTable" >
						<tr>
							<td>反馈时间:</td>
							<td>
								${addtime}
							</td>
						</tr>
						<tr>
							<td>反馈内容:</td>
							<td>
								<textarea class="easyui-validatebox" rows="5" cols="30" disabled="disabled">${message.content}</textarea>
							</td>
						</tr>
						<c:if test="${fromType=='add'}">
							<tr>
								<td>回复时间:</td>
								<td>
									${endtime}
								</td>
							</tr>
							<tr>
								<td>回复人员:</td>
								<td>
									${message.replyUserName}
								</td>
							</tr>
						</c:if>
						<tr>
							<td>回复内容:</td>
							<td>
							    <c:choose>
									<c:when test="${fromType=='edit'}">
										<textarea class="easyui-validatebox" rows="7" cols="30"  name="recontent"  maxlength="120" data-options="required:true"></textarea>
									</c:when>
									<c:otherwise>
										<textarea class="easyui-validatebox" rows="5" cols="30" disabled="disabled">${message.recontent}</textarea>
									</c:otherwise>
								</c:choose>
							</td>
						</tr>
						
						<tr>
							<td colspan="2" align="center">
								<c:choose>
									<c:when test="${fromType=='edit'}">
										<a href="javascript:void(0)" id="saveOrUpdate" class="easyui-linkbutton" data-options="iconCls:'icon-save'"  onclick="message.saveOrUpdate('admin/message/reply')">回复</a>
									</c:when>
								</c:choose>
							</td>
						</tr>
					</table>
		</form>
	</body>
</html>