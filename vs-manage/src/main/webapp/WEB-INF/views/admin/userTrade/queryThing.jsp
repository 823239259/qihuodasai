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
<%@include file="../../common/import-easyui-js.jspf"%>
<script type="text/javascript" src="${ctx}/static/script/common/dateUtils.js"></script>
<script type="text/javascript" src="${ctx}/static/plugins/my97DatePicker/WdatePicker.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/dataStyle.css">
</head>
<body>
 <table id="datagrid" class="easyui-datagrid" width="100%" 
		             url="${ctx}/admin/allTrades/queryAgentInfo?uid=${uid}" 
		            rownumbers="true" fitColumns="true" singleSelect="true">
		        <thead>
		            <tr>
						<th field="mobile" width="150">手机号</th>
						<th field="tname" width="150">姓名</th>
						<th field="rebate" width="150">代理返点</th>
		            </tr>
		        </thead>
		    </table>
</body>
</html>