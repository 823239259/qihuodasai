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
<title></title>
<%@include file="../../common/import-easyui-js.jspf"%>
<script type="text/javascript" src="${ctx}/static/plugins/my97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/static/script/common/dateUtils.js"></script>
<script type="text/javascript" src="${ctx}/static/script/tradeday/list.js"></script>
</head>
<body>
	<shiro:hasPermission name="sys:system:tradeday:view">
	<div id="tradeDayTab" class="easyui-tabs" data-options="tools:'#tab-tools',border:false,fit:true" style="margin-top: 5px;">
		<div title="A股交易日" data-options="tools:'#p-tools'" style="padding:20px;">
			<div id="tb" style="padding: 5px; height: auto">
				<div>
					<table>
						<tr>
							<td>
							<span>交易日期:</span>
								<input id="startTime" class="" type="text" onFocus="var endTime=$dp.$('endTime');WdatePicker({onpicked:function(){endTime.focus();},maxDate:'#F{$dp.$D(\'endTime\')}'})"/>
							      至  <input id="endTime" class="" type="text" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'startTime\')}'})"/>
							</td>
							<td>
								<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="tradeDay.doSearch()">查询</a>
							</td>
						</tr>
					</table>				
				</div>
				<div style="margin-bottom: 5px">
					<shiro:hasPermission name="sys:system:tradeday:update"> 
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="baseUtils.openEditwin(350,200,'修改交易日','tradeDay')">修改</a>
					</shiro:hasPermission>
					
					<shiro:hasPermission name="sys:system:tradeday:create"> 
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="tradeDay.showCreateWin()">生成日历</a>
					</shiro:hasPermission>
				</div>
			</div>
			<table id="edatagrid"></table>
		</div>
		<div title="港股股交易日" data-options="tools:'#p-tools'" style="padding:20px;">
			<div id="hktb" style="padding: 5px; height: auto">
				<div>
					<table>
						<tr>
							<td>
							<span>交易日期:</span>
								<input id="hkstartTime" class="" type="text" onFocus="var hkendTime=$dp.$('hkendTime');WdatePicker({onpicked:function(){hkendTime.focus();},maxDate:'#F{$dp.$D(\'hkendTime\')}'})"/>
							      至  <input id="hkendTime" class="" type="text" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'hkstartTime\')}'})"/>
							</td>
							<td>
								<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="hkTradeDay.doSearch()">查询</a>
							</td>
						</tr>
					</table>				
				</div>
				<div style="margin-bottom: 5px">
					<shiro:hasPermission name="sys:system:tradeday:update"> 
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="hkTradeDay.openEditwin(350,200,'修改交易日','hkstock/hkTradeDay')">修改</a>
					</shiro:hasPermission>
					
					<shiro:hasPermission name="sys:system:tradeday:create"> 
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="hkTradeDay.showCreateWin()">生成日历</a>
					</shiro:hasPermission>
				</div>
			</div>
			<table id="hkedatagrid"></table>
		</div>
	</div>
	<div id="createWin"  class="easyui-window"  style="padding:10px;top:100%;" data-options="title:'生成日历',collapsible:false,minimizable:false,maximizable:false,width:400,height:150,iconCls:'icon-edit',closed:true,modal:true">
		<form method="post" id="createForm" style="padding-left:13%;padding-top:6%">
			<span>请输入年份:</span>
			<input class="easyui-validatebox" id="year"  name="year" data-options="required:true,validType:'number'">
			<a href="javascript:void(0)" onclick="tradeDay.createCalenr()" class="easyui-linkbutton">提交</a>
		</form>
	</div>
	<div id="hkcreateWin"  class="easyui-window"  style="padding:10px;top:100%;" data-options="title:'生成日历',collapsible:false,minimizable:false,maximizable:false,width:400,height:150,iconCls:'icon-edit',closed:true,modal:true">
		<form method="post" id="hkcreateForm" style="padding-left:13%;padding-top:6%">
			<span>请输入年份:</span>
			<input class="easyui-validatebox" id="hkyear"  name="year" data-options="required:true,validType:'number'">
			<a href="javascript:void(0)" onclick="hkTradeDay.createCalenr()" class="easyui-linkbutton">提交</a>
		</form>
	</div>
	<!-- ADD window -->
	<div id="addWin" style="padding:10px;top: 20px;"></div>
	<!-- window -->
	</shiro:hasPermission>
	<shiro:lacksPermission name="sys:system:tradeday:view">
		<%@ include file="../../common/noPermission.jsp"%>
	</shiro:lacksPermission>
</body>
</html>