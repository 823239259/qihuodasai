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
<script type="text/javascript" src="${ctx}/static/script/message/list.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/dataStyle.css">

</head>
<body>
	<shiro:hasPermission name="sys:customerService:message:view">
	<div id="messageTab" class="easyui-tabs" data-options="tools:'#tab-tools',border:false,fit:true" style="margin-top: 5px;">
			<div title="未处理" data-options="tools:'#p-tools'" style="padding:20px;">
					<div id="untreatedtb" style="padding: 5px; height: auto">
					<div>
						<form id="searchForm">
						<input type="hidden" value="0" name="search_EQ_status">
						<table border="0" style="font-size:12px;" class="conn"  width="100%" cellpadding="0" cellspacing="0">
								<tr>
								<td class="label right">
									<span>手机号:</span>
								</td>
								<td>
								<input id="mobile1" name="search_LIKE_wuser.mobile">
									&nbsp;&nbsp;&nbsp;<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="datagridUtils.datagridQuery('untreatedData','searchForm')">查询</a>
								</td>
							</tr>
						</table>
						</form>						
					</div>
					<div style="margin-bottom: 5px">
						<shiro:hasPermission name="sys:customerService:message:update">  
							<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="message.openWin(600,350,'回复反馈','edit','untreatedData')">回复反馈</a>
						</shiro:hasPermission>
					</div>
				</div>
				<table id="untreatedData" data-options="toolbar:untreatedtb"></table>
			</div>
			<div title="已处理" data-options="tools:'#p-tools'" style="padding:20px;">
				<div id="processingtb" style="padding: 5px; height: auto">
					<div>
					<form id="searchForm1">
						<input type="hidden" value="1" name="search_EQ_status">
						<table border="0" style="font-size:12px;" class="conn"  width="100%" cellpadding="0" cellspacing="0">
								<tr>
								<td class="label right">
									<span>手机号:</span>
								</td>
								<td>
									<input id="mobile1" name="search_LIKE_wuser.mobile">
									&nbsp;&nbsp;&nbsp;<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="datagridUtils.datagridQuery('processingData','searchForm1')">查询</a>
								</td>
							</tr>
						</table>
					</form>				
					</div>
					<div style="margin-bottom: 5px">
						<shiro:hasPermission name="sys:customerService:message:view">  
							<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" plain="true" onclick="message.openWin(600,350,'查看回复内容','add','processingData')">查看回复内容</a>
						</shiro:hasPermission>
					</div>
				</div>
				<table id="processingData" data-options="toolbar:processingtb"></table>
			</div>
	</div>	
	<!-- ADD window -->
	<div id="addWin" style="padding:10px;top: 20px;"></div>
	<!-- window -->
	</shiro:hasPermission>
	<shiro:lacksPermission name="sys:customerService:message:view">
		<%@ include file="../../common/noPermission.jsp"%>
	</shiro:lacksPermission>
</body>
</html>