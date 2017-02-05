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
<title>更新交易日</title>
<%@include file="../../common/import-easyui-js.jspf"%>
<script type="text/javascript" src="${ctx}/static/script/wuser/public.js"></script>
<script type="text/javascript" src="${ctx}/static/script/notice/edit.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/dataStyle.css">
</head>
<body>
	<!-- add organization window -->
	<form method="post" id="addForm" style="padding-left: 4%;">
		<input type="hidden" name="id" value="${notice.id}" />
		<table class="formTable">
			<tr>
				<td>公告内容:</td>
				<td>
					<textarea id="showContent" name="content" class="easyui-textbox" rows="15" style="width: 400px;"
						 data-options="required:true">${notice.content }</textarea>
				</td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<a href="javascript:void(0)" id="saveOrUpdate"
						class="easyui-linkbutton" data-options="iconCls:'icon-save'"
						onclick="option.save()">确定</a>
					<a href="javascript:void(0)" id="saveOrUpdate"
						class="easyui-linkbutton" data-options="iconCls:'icon-cancel'"
						onclick="option.cancel()">取消</a>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>