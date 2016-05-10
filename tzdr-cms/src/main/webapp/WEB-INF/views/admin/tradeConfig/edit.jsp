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
<title>编辑配资参数</title>
</head>
<body>
		<form method="post" id="addForm" style="padding-left:11%;">
		<input type="hidden" id="dataID" name="id"  value="${config.id}">
		<!-- missingMessage:'不能为空' 可以修改为空时的提示语 -->
					<table class="formTable">
						<tr>
							<td>期限起止天数:</td>
							<td>
								<input class="easyui-validatebox" id="dayRangeStart" value="${config.dayRangeStart}" name="dayRangeStart" data-options="required:true,validType:'number'">
								~
								<input class="easyui-validatebox" id="dayRangeEnd" value="${config.dayRangeEnd}"  name="dayRangeEnd" data-options="required:true,validType:'number'">
								
							</td>
						</tr>
						<tr>
							<td>保证金金额范围:</td>
							<td>
								<input class="easyui-validatebox" id="depositRangeStart" value='<fmt:formatNumber value="${config.depositRangeStart}" pattern="##.##"/>'  name="depositRangeStart" data-options="required:true,validType:'money'">
								~
								<input class="easyui-validatebox" id="depositRangeEnd" value='<fmt:formatNumber value="${config.depositRangeEnd}" pattern="##.##"/>'   name="depositRangeEnd" data-options="required:true,validType:'money'">
								
							</td>
						</tr>
						<tr>
							<td>倍数范围:</td>
							<td>
								<input class="easyui-validatebox" id="multipleRangeStart" value="${config.multipleRangeStart}"   name="multipleRangeStart" data-options="required:true,validType:'number'">
								~
								<input class="easyui-validatebox" id="multipleRangeEnd"  value="${config.multipleRangeEnd}"  name="multipleRangeEnd" data-options="required:true,validType:'number'">
							</td>
						</tr>
						<tr>
							<td>利息(年):</td>
							<td>
								<input class="easyui-validatebox" id="yearInterest" value="${config.yearInterest}"   name="yearInterest" data-options="required:true,validType:'money'">	
							</td>
						</tr>
		<%-- 
						<tr>
							<td>(日)利息:</td>
							<td>
								<input class="easyui-validatebox" id="dailyInterest" value='<fmt:formatNumber value="${config.dailyInterest}" pattern="#0.#####"/>'   name="dailyInterest" data-options="required:true,validType:'money'">	
							</td>
						</tr> --%>
						<tr>
							<td>管理费(年):</td>
							<td>
								<input class="easyui-validatebox" id="yearManagementFee"  value="${config.yearManagementFee}"  name="yearManagementFee" data-options="required:true,validType:'money'">	
							</td>
						</tr>
						<%-- <tr>
							<td>(日)管理费:</td>
							<td>
								<input class="easyui-validatebox" id="dailyManagementFee" value='<fmt:formatNumber value="${config.dailyManagementFee}" pattern="#0.#####"/>'  name="dailyManagementFee" data-options="required:true,validType:'money'">	
							</td>
						</tr> --%>
						<tr>
								<td colspan="2" align="center">
								<c:choose>
									<c:when test="${fromType=='add'}">
										<a href="javascript:void(0)" id="saveOrUpdate" class="easyui-linkbutton" data-options="iconCls:'icon-save'"  onclick="baseUtils.saveOrUpdate('admin/tradeConfig/create')">保存</a>
									</c:when>
									<c:otherwise>
										<a href="javascript:void(0)" id="saveOrUpdate" class="easyui-linkbutton" data-options="iconCls:'icon-edit'"  onclick="baseUtils.saveOrUpdate('admin/tradeConfig/update')">修改</a>
									</c:otherwise>
								</c:choose>
								</td>
						</tr>
					</table>
		</form>
</body>
</html>