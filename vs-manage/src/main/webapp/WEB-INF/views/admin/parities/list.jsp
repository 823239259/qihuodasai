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
<title>汇率信息维护</title>
<%@include file="../../common/import-easyui-js.jspf"%>
<script type="text/javascript" src="${ctx}/static/plugins/my97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/static/script/common/dateUtils.js"></script>
<script type="text/javascript" src="${ctx}/static/script/parities/list.js?v=${v}"></script>
</head>
<body>
	<shiro:hasPermission name="sys:finance:parities:view">
	<div id="spifTab" class="easyui-tabs"  style="margin-top: 5px;"> 
	
	<div title="结算交易手续费汇率维护" data-options="tools:'#p-tools'" style="padding:20px;">
		<div id="tb2" style="padding: 5px; height: auto">
				
				<div style="margin-bottom: 5px">
					<shiro:hasPermission name="sys:finance:parities:update"> 
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="showCreateWin2(2);">修改</a>
					</shiro:hasPermission>
					
					<shiro:hasPermission name="sys:finance:parities:create"> 
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="showCreateWin2(1);">新增汇率</a>
					</shiro:hasPermission>
				</div>
			</div>
			<table id="edatagrid2"></table>
			
			<div id="addWin2" style="padding:10px;top: 20px;"></div>
	</div>
	<div title="每日结算汇率维护" data-options="tools:'#p-tools'" style="padding:20px;">
			<div id="tb" style="padding: 5px; height: auto">
				<div>
					<table>
						<tr>
							<td>
							<span>新增日期:</span>
								<input id="startTime" class="" type="text" onFocus="var endTime=$dp.$('endTime');WdatePicker({onpicked:function(){endTime.focus();},maxDate:'#F{$dp.$D(\'endTime\')}'})"/>
							      至  <input id="endTime" class="" type="text" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'startTime\')}'})"/>
							</td>
							<td>
								<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="doSearch()">查询</a>
							</td>
						</tr>
					</table>				
				</div>
				<div style="margin-bottom: 5px">
					<shiro:hasPermission name="sys:finance:parities:update"> 
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="showCreateWin(2);">修改</a>
					</shiro:hasPermission>
					
					<shiro:hasPermission name="sys:finance:parities:create"> 
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="showCreateWin(1);">新增汇率</a>
					</shiro:hasPermission>
				</div>
			</div>
			<table id="edatagrid"></table>
			
			<div id="addWin" style="padding:10px;top: 20px;"></div>
	
		</div>
 	</div>
	
	</shiro:hasPermission>
	<shiro:lacksPermission name="sys:finance:parities:view">
		<%@ include file="../../common/noPermission.jsp"%>
	</shiro:lacksPermission>
</body>
</html>