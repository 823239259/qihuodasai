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
<script type="text/javascript" src="${ctx}/static/script/activity/activitylist.js?version=20160111"></script>
<script type="text/javascript" src="${ctx}/static/plugins/my97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/static/script/wuser/public.js"></script>

<link rel="stylesheet" type="text/css" href="${ctx}/static/css/dataStyle.css">
</head>
<body>
	<shiro:hasPermission name="sys:operationalConfig:Activity:view">
	<div id="activityTab" class="easyui-tabs" data-options="tools:'#tab-tools',border:false,fit:true" style="margin-top: 5px;">
			<div title="开箱壕礼" data-options="tools:'#p-tools'" style="padding:20px;">
					<div id="audittb" style="padding: 5px; height: auto">
					<form id="queryForm" method="post">
					<input name="search_GTE_status" value="0" type="hidden"> 
					<div>
						<table border="0" style="font-size:12px;" class="conn"  width="100%" cellpadding="0" cellspacing="0">
							<tr>
								<td class="label right">
									<span>手机号：</span>
								</td>
								<td>
								<input class="easyui-validatebox" id="search_LIKE_mobile" name="search_LIKE_mobile">
								</td>
								
								
								<td class="label right">
									<span>获奖时间:</span>
								</td>
								<td>
										<input id="startTime3" name="search_datetime_GTE_appendDate" class="" type="text" onFocus="var endTime3=$dp.$('endTime3');WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',onpicked:function(){endTime3.focus();},maxDate:'#F{$dp.$D(\'endTime3\')}'})"/>
									      至  <input id="endTime3" name="search_datetime_LTE_appendDate" class="" type="text" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'startTime3\')}'})"/>							      
							
								</td>
								
							</tr>
							
							<tr>
								<td class="label right">
									<span>状态:</span>
								</td>
								<td>
									<select id="kudoStatus" class="easyui-combobox" name="kudoStatus" style="width:100px;height:30px;">
									    <option value="">请选择</option>
									    <option value="1">未使用</option>
									    <option  value="2">已使用</option>
									    <option  value="3">申请使用</option>
									</select>
								</td>
								
								<td class="label right">
									<span>活动类型:</span>
								</td>
								<td>
									<select id="activityType" class="easyui-combobox" name="activityType" style="width:100px;height:30px;">
									    <option value="">请选择</option>
									    <option value="2">开箱壕礼</option>
									    <option  value="1">微信抽奖</option>
									    <option  value="3">新春礼包</option>
									</select>
								</td>
							</tr>
							<tr>
								<td class="label right">
								</td>
								<td>
									<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="Activity.list.doSearch1()">查询</a>
								</td>
							</tr>
						</table>			
					</div>
					
					<div style="margin-bottom: 5px">
						<shiro:hasPermission name="sys:operationalConfig:Activity:update">  
							<a href="javascript:void(0)" class="easyui-linkbutton"  iconCls="icon-edit"  plain="true" onclick="Activity.list.usekudo()">使用</a>
						</shiro:hasPermission>
						<shiro:hasPermission name="sys:operationalConfig:Activity:update">  
							<a href="javascript:void(0)" class="easyui-linkbutton"  iconCls="icon-edit"  plain="true" onclick="Activity.list.changekudo()">未使用</a>
						</shiro:hasPermission>
					</div>
					</form>
				</div>
				
				<table id="auditData" data-options="toolbar:audittb"></table>
			</div>
			
	</div>	
	<!-- ADD window -->
	<div id="addWin" style="padding:10px;top: 20px;">
		
	</div>
	<!-- window -->
	</shiro:hasPermission>
	<shiro:lacksPermission name="sys:operationalConfig:Activity:view">
		<%@ include file="../../common/noPermission.jsp"%>
	</shiro:lacksPermission>
</body>
</html>