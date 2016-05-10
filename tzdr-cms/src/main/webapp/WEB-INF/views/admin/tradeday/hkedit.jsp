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
			<input  type="hidden" name="id" value="${tradeDay.id}"/>
					<table class="formTable" >
						<tr>
							<td>名称:</td>
							<td>${tradeDay.dateValue}</td>
						</tr>
						<tr>
							<td>是否交易日:</td>
							<td>
							<c:choose>
								<c:when test="${tradeDay.isTrade==false}">
										<input type="radio" name="isTrade" value="true"><span>是</span></input>
										<input type="radio" name="isTrade" value="false" checked="checked"><span>否</span></input>
								</c:when>
								<c:otherwise>
									<input type="radio" name="isTrade" value="true" checked="checked"><span>是</span></input>
									<input type="radio" name="isTrade" value="false"><span>否</span></input>
								</c:otherwise>
							</c:choose>
							</td>
						</tr>
						<tr>
							<td colspan="2" align="center">
								<a href="javascript:void(0)" id="saveOrUpdate" class="easyui-linkbutton" data-options="iconCls:'icon-edit'"  onclick="hkTradeDay.saveOrUpdate('admin/hkstock/hkTradeDay/update')">修改</a>
							</td>
						</tr>
					</table>
		</form>
	</body>
</html>