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
<script type="text/javascript" src="${ctx}/static/script/wuser/public.js"></script>
<script type="text/javascript" src="${ctx}/static/script/hundsunAccount/parentList.js?v=20150625"></script>
<script type="text/javascript" src="${ctx}/static/plugins/my97DatePicker/WdatePicker.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/dataStyle.css">

</head>
<body>
	<shiro:hasPermission name="sys:accountmanager:parentaccount:view">
	
	<div id="tb" style="padding: 5px; height: auto">
		<div>
	<form id="queryForm" method="post">
			<table border="0" style="font-size:12px;" class="conn"  width="100%" cellpadding="0" cellspacing="0">
				<tr>
					<td class="label right">
						账户名称:
					</td>
					<td><input name="accountName"></td>
					<td class="label right">账户编号:</td>
					<td><input name="accountNo"></td>
					<td class="label right">
						账户类型:
					</td>
					<td>
					<input class="easyui-combobox" name="accountType" data-options="
										url:'${ctx}/admin/dataDic/getDicDatas?typeKey=parentAccountType',
										valueField:'valueField',
										panelHeight:100,
										textField:'textField'">
					</td>
				</tr>
				<tr>
					<td class="label right">
					创建时间:
					</td>
					<td colspan="3">
						<table class="many">
	                        <tr>
	                            <td><input class="easyui-datebox" name="createDateStr_start"></td>
	                            <td>至</td>
	                            <td><input class="easyui-datebox" name="createDateStr_end"></td>
	                        </tr>
	                    </table>
					</td>
					<td  class="label right">
					创建人:
					</td>
					<td><input name="user"></td>
				</tr>
				
				<tr>
					<td class="label right">
					母账号交易通道:
					</td>
					<td colspan="5">
					    <input class="easyui-combobox" name="accountGenre" data-options="
										url:'${ctx}/admin/dataDic/getDicDatas?typeKey=accountGenre',
										valueField:'valueField',
										panelHeight:100,
										textField:'textField'">
					</td>
				</tr>
				
				<tr>
					<td class="label right">
					</td>
					<td colspan="5">
						<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="$.easyui.datagridQuery('edatagrid','queryForm')">查询</a>
					</td>
				</tr>
				
			</table>				
		</form>
		</div>
		<div style="margin-bottom: 5px">
			<shiro:hasPermission name="sys:accountmanager:parentaccount:create">  
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="baseUtils.openAddwin(600,550,'新增母账户','parentAccount')">新增</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="sys:accountmanager:parentaccount:delete"> 
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="baseUtils.deleteData('parentAccount')">删除</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="sys:accountmanager:parentaccount:update"> 
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="baseUtils.openEditwin(600,550,'修改母账户','parentAccount')">修改</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="sys:accountmanager:parentaccount:update"> 
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="parentAccount.openEditBalanceWin()">修改账户余额</a>
			</shiro:hasPermission>
			<%-- <shiro:hasPermission name="sys:accountmanager:parentaccount:reset"> 
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="parentAccount.openChangePwdWin()">修改密码</a>
			</shiro:hasPermission> --%>
		</div>
	</div>
	<table id="edatagrid"></table>

	<!-- ADD window -->
	<div id="addWin" style="padding:10px;top: 20px;"></div>
	<!-- window -->
	</shiro:hasPermission>
	<shiro:lacksPermission name="sys:accountmanager:parentaccount:view">
		<%@ include file="../../common/noPermission.jsp"%>
	</shiro:lacksPermission>
</body>
</html>