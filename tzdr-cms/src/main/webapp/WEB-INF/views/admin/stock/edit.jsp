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
			<input  type="hidden" name="id" value="${stock.id}"/>
			<input  type="hidden" name="type" value="${type}"/>
					<table class="formTable" >
						<tr>
							<td>股票代码:</td>
							<td>
								<input class="easyui-validatebox" value="${stock.code}" id="code"  name="code" data-options="required:true,validType:['length[2,20]']">
							</td>
						</tr>
						<tr>
							<td>股票名称:</td>
							<td>
								<input class="easyui-validatebox" value="${stock.name}" id="name"  name="name" data-options="required:true,validType:['length[2,20]']">
							</td>
						</tr>
						
						<tr>
							<c:if test="${type==1}">
							<td>限制日期:</td>
							</c:if>
							<c:if test="${type==2}">
							<td>停牌日期:</td>
							</c:if>
							<td>
								<input type="text" id="effectiveDate" name="effectiveDate" value="${stock.effectiveDate}"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="Wdate"/>
							</td>
						</tr>
						
						<tr>
							<td colspan="2" align="center">
								<c:choose>
									<c:when test="${fromType=='add'}">
										<a href="javascript:void(0)" id="saveOrUpdate" class="easyui-linkbutton" data-options="iconCls:'icon-save'"  onclick="stock.saveInfo('${type}')">保存</a>
									</c:when>
									<c:otherwise>
										<a href="javascript:void(0)" id="saveOrUpdate" class="easyui-linkbutton" data-options="iconCls:'icon-edit'"  onclick="stock.updateInfo('${type}')">修改</a>
									</c:otherwise>
								</c:choose>
							</td>
						</tr>
					</table>
		</form>
	</body>
</html>