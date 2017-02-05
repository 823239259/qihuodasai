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
<script type="text/javascript" src="${ctx}/static/plugins/my97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/static/script/common/dateUtils.js"></script>
<script type="text/javascript" src="${ctx}/static/script/tradeConfig/list.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/dataStyle.css">

</head>
<body>
	<shiro:hasPermission name="sys:settingParams:tradeConfig:view">
	<div id="tb" style="padding: 5px; height: auto">
		<div>
			<table border="0" style="font-size:12px;" class="conn"  width="100%" cellpadding="0" cellspacing="0">
				<tr>
					<td  class="label right">
					<span>配资天数:</span>
					</td>
					<td>
						<input id="dayRangeStart"  class="easyui-validatebox"  type="text"/>
					      至  <input id="dayRangeEnd"  class="easyui-validatebox"  type="text"/>
					</td>
					<td  class="label right">
					<span>倍数:</span>
					</td>
					<td>
						<input id="multipleRangeStart"  class="easyui-validatebox"  type="text"/>
					      至  <input id="multipleRangeEnd" class="easyui-validatebox"  type="text"/>
					</td>
				</tr>
				<tr>
					<td  class="label right">
					<span>保证金:</span>
					</td>
					<td>
						<input id="depositRangeStart"  class="easyui-validatebox"  type="text"/>
					      至  <input id="depositRangeEnd" class="easyui-validatebox"  type="text"/>
					</td>
					<td class="label right">
					</td>
					<td class="label">
						<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="tradeConfig.doSearch()">查询</a>
					</td>
				</tr>
			</table>				
		</div>
		<div style="margin-bottom: 5px">
			<shiro:hasPermission name="sys:settingParams:tradeConfig:create">  
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="baseUtils.openAddwin(600,300,'新增配资参数','tradeConfig')">新增</a>
			</shiro:hasPermission>
				
			<shiro:hasPermission name="sys:settingParams:tradeConfig:update"> 
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="baseUtils.openEditwin(600,300,'修改配资参数','tradeConfig')">修改</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="tradeConfig.showUpdateDaysWin('设置计算利息天数',2)">设置计算利息天数</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="tradeConfig.showUpdateDaysWin('设置计算管理费天数',1)">设置计算管理费天数</a>
			</shiro:hasPermission>
			
		</div>
	</div>
	<table id="edatagrid"></table>

	<!-- ADD window -->
	<div id="addWin" style="padding:10px;top: 20px;"></div>
	<div id="updateDaysWin"  class="easyui-window"  style="padding:10px;top:100%;" data-options="collapsible:false,minimizable:false,maximizable:false,width:400,height:150,iconCls:'icon-edit',closed:true,modal:true">
		<form method="post" id="updateDaysForm" style="padding-left:13%;padding-top:6%">
			<span>请输入天数:</span>
			<input class="easyui-validatebox" id="days"  name="days" data-options="required:true,validType:'number'">
			<a href="javascript:void(0)" id="submitDays" class="easyui-linkbutton">提交</a>
		</form>
	</div>
	<!-- window -->
	</shiro:hasPermission>
	<shiro:lacksPermission name="sys:settingParams:tradeConfig:view">
		<%@ include file="../../common/noPermission.jsp"%>
	</shiro:lacksPermission>
</body>
</html>