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
<title>拥有停牌的用户列表</title>
<%@include file="../../common/import-easyui-js.jspf"%>
<script type="text/javascript" src="${ctx}/static/plugins/my97DatePicker/WdatePicker.js"></script>

<link rel="stylesheet" type="text/css" href="${ctx}/static/css/dataStyle.css">
</head>
<body>
	<shiro:hasPermission name="sys:multipleQuery:userSuspended:view">
	<div id="tb" style="padding: 5px; height: auto">
		<div>
			<form id="searchForm">
				<table border="0" style="font-size:12px;" class="conn"  width="100%" cellpadding="0" cellspacing="0">
					<tr>
						<td class="label right">
							<span>手机号：</span>
						</td>
						<td>
							<input class="easyui-validatebox" id="search_LIKE_mobile" name="search_LIKE_mobile">					
						</td>
						<td class="label right">
							<span>股票代码：</span>
						</td>
						<td>
							<input class="easyui-validatebox" id="search_LIKE_code" name="search_LIKE_code">					
						</td>
					</tr>
					<tr>
						<td class="label right">
							<span>客户姓名：</span>
						</td>
						<td colspan="3">
							<input class="easyui-validatebox" id="search_LIKE_username" name="search_LIKE_username">					
						</td>
					</tr>
					<tr>
					<td class="label right">
						<span></span>
					</td>
					<td colspan="3">
						<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="datagridUtils.datagridQuery('edatagrid','searchForm')">查询</a>
					</td>
				</tr>
				</table>	
			</form>	
		</div>
		<div style="margin-bottom: 5px">
		
		</div>
	</div>
	
	<table id="edatagrid" class="easyui-datagrid"
            width="100%"
            url="${ctx}/admin/stock/userSuspended/getDatas" pagination="true"
            data-options="checkOnSelect:true,toolbar:'#tb',
            onLoadSuccess:function(data){
				datagridUtils.loadSuccess(data,'edatagrid');
			}"
            rownumbers="true" fitColumns="true" singleSelect="true">
	        <thead>
	            <tr>
	            	<th field="id" width="150" hidden="true"></th>
					<th field="mobile" width="150" sortable="true">手机号码</th>
					<th field="username" width="150">客户姓名</th>
					<th field="groupId" width="150">方案号</th>
					<th field="code" width="150">股票代码</th>
					<th field="stockName" width="150">股票名称</th>
					<th field="effectiveDate" width="150">停牌日期</th>
	            </tr>
	        </thead>
   </table>

	<!-- ADD window -->
	<div id="addWin" style="padding:10px;top: 20px;"></div>
	<!-- window -->
	</shiro:hasPermission>
	<shiro:lacksPermission name="sys:multipleQuery:userSuspended:view">
		<%@ include file="../../common/noPermission.jsp"%>
	</shiro:lacksPermission>
</body>
</html>