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
<script type="text/javascript" src="${ctx}/static/script/common/dateUtils.js"></script>
<script type="text/javascript" src="${ctx}/static/script/withdrawal/auditList.js"></script>

<title>设置提现审核金额</title>
</head>
<body>
		<form method="post" id="addForm" style="padding-left: 10%;padding-top:7px;">
		<input type="hidden" id="accountID" name="id" value="${drawList.id}">
		<!-- missingMessage:'不能为空' 可以修改为空时的提示语 -->
					<table class="formTable">
					   <c:if test="${fromType==2}">
						<tr>
							<td>审核人:</td>
							<td>
								<c:choose>
									<c:when test="${empty drawList.updateUser }">
										${drawList.firstAuditUser}
									</c:when>
									<c:otherwise>
										${drawList.updateUser}
									</c:otherwise>
								</c:choose>
							</td>
						</tr>
						<tr>
							<td>审核时间:</td>
							<td>
								<c:choose>
									<c:when test="${empty drawList.updateUser }">
										<script type="text/javascript">
											document.write(getFormatDateByLong('${drawList.firstAuditTime}',"yyyy-MM-dd hh:mm:ss"));
										</script>
									</c:when>
									<c:otherwise>
										<script type="text/javascript">
											document.write(getFormatDateByLong('${drawList.updateTime}',"yyyy-MM-dd hh:mm:ss"));
										</script>
									</c:otherwise>
								</c:choose>
								
							</td>
						</tr>
						
						</c:if>
						
						
						    <tr>
								<td>原因:</td>
								<td>
									<textarea data-options="required:true"  rows="8" cols="50" name="aremark" id="aremark">${drawList.aremark}</textarea>
								</td>
							</tr>
							<c:if test="${fromType!=2}">
									<tr>
										<td colspan="2" align="center">
											<a href="javascript:void(0)" id="saveOrUpdate" class="easyui-linkbutton" data-options="iconCls:'icon-edit'"  onclick="auditWithDraw.saveOrUpdate('admin/withdrawAudit/setNotPass?type=${fromType}','iframe','${datagridId}')">确定</a>
										<a href="javascript:void(0)" 
						class="easyui-linkbutton" data-options="iconCls:'icon-cancel'"
						onclick="parent.$('#addWin').window('close');">取消</a>
										</td>
									</tr>
							</c:if>
					</table>
		</form>
</body>
</html>