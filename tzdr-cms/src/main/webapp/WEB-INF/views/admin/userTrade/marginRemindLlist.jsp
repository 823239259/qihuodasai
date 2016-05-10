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
<title>补仓提醒</title>
<%@include file="../../common/import-easyui-js.jspf"%>
<script type="text/javascript" src="${ctx}/static/plugins/my97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/static/script/userTrade/marginRemindLlist.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/dataStyle.css">
<script type="text/javascript" src="${ctx}/static/script/common/dateUtils.js"></script>

</head>
<body>
	<shiro:hasPermission name="sys:customerService:marginremind:view">
	<div id="tb" style="padding: 5px; height: auto">
		<div>
			<table border="0" style="font-size:12px;" class="conn"  width="100%" cellpadding="0" cellspacing="0">
				<tr>
					<td class="label right">
						<span>通知状态：</span>
					</td>
					<td>
						<input class="easyui-combobox" id="callNoticeStatus" name="callNoticeStatus" data-options="
										url:'${ctx}/admin/dataDic/getDicDatas?typeKey=callNoticeStatus',
										valueField:'valueField',
										panelHeight:100,
										textField:'textField'">
					</td>
					<td class="label right">
						<span>上次通知时间：</span>
					</td>
					<td>
						<input id="lastNoticeTime1" class="Wdate" type="text" onFocus="var lastNoticeTime2=$dp.$('lastNoticeTime2');WdatePicker({onpicked:function(){lastNoticeTime2.focus();},maxDate:'#F{$dp.$D(\'lastNoticeTime2\')}'})"/>
					    - <input id="lastNoticeTime2" class="Wdate" type="text" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'lastNoticeTime1\')}'})"/>
					</td>
				</tr>
				<tr>
					<td class="label right"></td>
					<td colspan="3">
						<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="marginRemind.doSearch()">查询</a>
					</td>
				</tr>
			</table>			
		</div>
		<div style="margin-bottom: 5px">
			<shiro:hasPermission name="sys:customerService:marginremind:notConnected">  
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-no" plain="true" onclick="marginRemind.changeNoticeStatus(2)">未接通</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="sys:customerService:marginremind:notified">  
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" plain="true" onclick="marginRemind.changeNoticeStatus(1)">已通知</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="sys:customerService:marginremind:view">  
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload" plain="true" onclick="marginRemind.reloadData()">刷新</a>
			</shiro:hasPermission>
			
			<shiro:hasPermission name="sys:customerService:marginremind:sendSms">  
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" plain="true" onclick="marginRemind.sendSms(1)">补仓短信</a>
			</shiro:hasPermission>
			
			<shiro:hasPermission name="sys:customerService:marginremind:sendSms">  
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" plain="true" onclick="marginRemind.sendSms(2)">平仓短信</a>
			</shiro:hasPermission>
			
		</div>
	</div>
	<table id="edatagrid"></table>

	<!-- ADD window -->
	<div id="addWin" style="padding:10px;top: 20px;"></div>
	<!-- window -->
	</shiro:hasPermission>
	<shiro:lacksPermission name="sys:customerService:marginremind:view">
		<%@ include file="../../common/noPermission.jsp"%>
	</shiro:lacksPermission>
</body>
</html>