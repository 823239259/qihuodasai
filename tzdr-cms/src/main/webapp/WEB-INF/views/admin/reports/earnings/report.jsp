<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../../common/common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Content-Language" content="zh-CN" />
<!-- X-UA-Compatible 设置ie8/ie9 的兼容模式 -->
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title></title>
<%@include file="../../../common/import-easyui-js.jspf"%>
<script type="text/javascript"
	src="${ctx}/static/script/wuser/public.js"></script>
<script type="text/javascript"
	src="${ctx}/static/script/wuser/end_list.js"></script>
<script type="text/javascript"
	src="${ctx}/static/script/wuser/activity_list.js"></script>
<script type="text/javascript"
	src="${ctx}/static/script/hkstock/hkEarnings/list.js"></script>
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/css/dataStyle.css">
</head>
<body>
	<shiro:hasPermission name="sys:accountant:earnings:view">
		<div id="earningsTab" class="easyui-tabs"
			data-options="tools:'#tab-tools',border:false,fit:true"
			style="margin-top: 5px;">
			<div title="A股收益日报表" data-options="tools:'#p-tools'"
				style="padding: 20px;">
				<div id="tb" style="padding: 5px; height: auto">
					<form id="queryForm" method="post">
						<table border="0" style="font-size: 12px;" class="conn"
							width="100%" cellpadding="0" cellspacing="0">
							<tr>
								<td class="label right">用户名:</td>
								<td><input name="mobile" type="text" /></td>
								<td class="label right">实名:</td>
								<td><input name="tname" type="text" /></td>
							</tr>
							<tr>
								<td class="label right">恒生账户:</td>
								<td><input name="account" type="text" /></td>
								<td class="label right">方案号:</td>
								<td><input name="groupId" type="text" /></td>
							</tr>
							<tr>
								<td class="label right">起止日期:<span class="req">*</span></td>
								<td>
									<table class="many">
										<tr>
											<td><input class="easyui-datebox"
												name="starttimeStr_start"></td>
											<td>至</td>
											<td><input class="easyui-datebox"
												name="starttimeStr_end"></td>
										</tr>
									</table>
								</td>
								<td class="label right">
									<span>方案类型：</span>
								</td>
								<td>
								<input class="easyui-combobox" id="activityType" name="search_EQ_activityType" data-options="valueField: 'id', textField:'text',
				                     url:'${ctx}/admin/component/dictCombobox?typeKey=activityType'">
								</td>
							</tr>
							<tr>
								<td class="label">&nbsp;</td>
								<td class="label" colspan="3"><a id="btn" href="#"
									onclick="datagridUtils.datagridQuery('edatagrid','queryForm')"
									class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a></td>
							</tr>
						</table>
					</form>
				 <shiro:hasPermission name="sys:accountant:earnings:export"> 
					
					<div style="margin-bottom: 5px">
						<a href="javascript:void(0)" class="easyui-linkbutton"
							iconCls="icon-excel" plain="true"
							onclick="$.easyui.exportExcel('edatagrid','queryForm')">导出</a>
					</div>
				</shiro:hasPermission>
				</div>
				<table id="edatagrid"></table>
			</div>
			<div title="港股收益日报表" data-options="tools:'#p-tools'"
				style="padding: 20px;">
				<div id="hktb" style="padding: 5px; height: auto">
					<form id="hkqueryForm" method="post">
						<table border="0" style="font-size: 12px;" class="conn"
							width="100%" cellpadding="0" cellspacing="0">
							<tr>
								<td class="label right">用户名:</td>
								<td><input name="mobile" type="text" /></td>
								<td class="label right">实名:</td>
								<td><input name="tname" type="text" /></td>
							</tr>
							<tr>
								<td class="label right">操盘账户:</td>
								<td><input name="account" type="text" /></td>
								<td class="label right">方案号:</td>
								<td><input name="groupId" type="text" /></td>
							</tr>
							<tr>
								<td class="label right">起止日期:<span class="req">*</span></td>
								<td>
									<table class="many">
										<tr>
											<td><input class="easyui-datebox"
												name="starttimeStr_start"></td>
											<td>至</td>
											<td><input class="easyui-datebox"
												name="starttimeStr_end"></td>
										</tr>
									</table>
								</td>
								<td colspan="2">&nbsp;</td>
							</tr>
							<tr>
								<td class="label">&nbsp;</td>
								<td class="label" colspan="3"><a id="btn" href="#"
									onclick="datagridUtils.datagridQuery('hkedatagrid','hkqueryForm')"
									class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a></td>
							</tr>
						</table>
					</form>
				 <shiro:hasPermission name="sys:accountant:earnings:export"> 
					<div style="margin-bottom: 5px">
						<a href="javascript:void(0)" class="easyui-linkbutton"
							iconCls="icon-excel" plain="true"
							onclick="$.easyui.exportExcel('hkedatagrid','hkqueryForm')">导出</a>
					</div>
				</shiro:hasPermission>
				</div>
				<table id="hkedatagrid"></table>
			</div>
		</div>
	</shiro:hasPermission>
	  <shiro:lacksPermission name="sys:accountant:earnings:view">
		<%@ include file="../../../common/noPermission.jsp"%>
	</shiro:lacksPermission>
</body>
</html>