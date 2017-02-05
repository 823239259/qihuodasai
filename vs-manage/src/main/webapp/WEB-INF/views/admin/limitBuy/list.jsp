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
<script type="text/javascript" src="${ctx}/static/script/limitBuy/list.js"></script>
</head>
<body>
	<shiro:hasPermission name="sys:riskmanager:limitbuy:view">
	<div id="limitbuyTab" class="easyui-tabs" data-options="tools:'#tab-tools',border:false,fit:true" style="margin-top: 5px;">
			<div title="未处理限制买入" data-options="tools:'#p-tools'" style="padding:20px;">
					<div id="notlimit" style="padding: 5px; height: auto">
					<div>
						<table>
							<tr>
								<td>
									<span>Name:</span> <input id="name">
								<td>
								<td>
									<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="user.list.doSearch()">Search</a>
								</td>
							</tr>
						</table>				
					</div>
					<div style="margin-bottom: 5px">
						<shiro:hasPermission name="sys:riskmanager:limitbuy:update">  
							<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="limitbuy.changeLimitBuyStatus(2,'notlimitData')">已限制</a>
						</shiro:hasPermission>
					</div>
				</div>
				<table id="notlimitData" data-options="toolbar:notlimit"></table>
			</div>
			<div title="已处理限制买入" data-options="tools:'#p-tools'" style="padding:20px;">
				<div id="haslimit" style="padding: 5px; height: auto">
					<div>
						<table>
							<tr>
								<td>
									<span>Name:</span> <input id="name">
								<td>
								<td>
									<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="user.list.doSearch()">Search</a>
								</td>
							</tr>
						</table>				
					</div>
				</div>
				<table id="haslimitData" data-options="toolbar:haslimit"></table>
			</div>
	</shiro:hasPermission>
	<shiro:lacksPermission name="sys:riskmanager:limitbuy:view">
		<%@ include file="../../common/noPermission.jsp"%>
	</shiro:lacksPermission>
</body>
</html>