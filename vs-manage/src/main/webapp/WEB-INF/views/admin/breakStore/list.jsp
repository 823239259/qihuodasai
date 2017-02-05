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
<script type="text/javascript" src="${ctx}/static/script/breakStore/list.js"></script>
<script type="text/javascript" src="${ctx}/static/plugins/my97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/static/script/wuser/public.js"></script>

<link rel="stylesheet" type="text/css" href="${ctx}/static/css/dataStyle.css">

</head>
<body>
	<shiro:hasPermission name="sys:riskmanager:breakStore:view">
	<div id="withdrawalTab" data-options="tools:'#tab-tools',border:false,fit:true" style="margin-top: 5px;">
		<form id="searchAllForm" method="post">
			<table border="0" style="font-size:12px;" class="conn"  width="100%" cellpadding="0" cellspacing="0">
				<tr>
					<td class="label right">
						<span>手机号：</span>
					</td>
					<td>
					<input name="search_LIKE_mobile" class="easyui-validatebox" id="mobile">
					</td>
					<td class="label right">
						<span>真实姓名：</span>
					</td>
					<td>
					<input name="search_LIKE_tname" class="easyui-validatebox" id="tname">
					</td>
				</tr>
				<tr>
					<td class="label right">
						<span>交易账户名：</span>
					</td>
					<td>
						<input name="search_LIKE_accountName" class="easyui-validatebox" id="accountName">
					</td>
					<td class="label right">
						<span>方案类型：</span>
					</td>
					<td>
						<input class="easyui-combobox" id="activityType" name="search_EQ_activityType" 
							data-options="valueField: 'id', textField:'text', url:'${ctx}/admin/component/dictCombobox?typeKey=activityType'">
					</td>
				</tr>
				<tr>
					<td class="label right">
					</td>
					<td colspan="3">
						<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="withdrawal.list.doSearch1()">查询</a>
						<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-excel" onclick="$.easyui.exportExcel('allData','searchAllForm')">导出</a>
					</td>
				</tr>
			</table>	
			</form>		
		</div>
	</div>
	<table id="allData"></table>
	</div>	
	</shiro:hasPermission>
	<shiro:lacksPermission name="sys:riskmanager:breakStore:view">
		<%@ include file="../../common/noPermission.jsp"%>
	</shiro:lacksPermission>
</body>
</html>